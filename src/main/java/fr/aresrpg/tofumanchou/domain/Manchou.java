package fr.aresrpg.tofumanchou.domain;

import fr.aresrpg.commons.domain.concurrent.Threads;
import fr.aresrpg.commons.domain.condition.Option;
import fr.aresrpg.commons.domain.database.Database;
import fr.aresrpg.commons.domain.event.Events;
import fr.aresrpg.commons.domain.event.Listener;
import fr.aresrpg.commons.domain.log.AnsiColors.AnsiColor;
import fr.aresrpg.commons.domain.log.Logger;
import fr.aresrpg.commons.domain.log.LoggerBuilder;
import fr.aresrpg.commons.infra.database.mongodb.MongoDBDatabase;
import fr.aresrpg.dofus.structures.server.*;
import fr.aresrpg.tofumanchou.domain.command.Command;
import fr.aresrpg.tofumanchou.domain.plugin.ManchouPlugin;
import fr.aresrpg.tofumanchou.domain.util.concurrent.Executors;
import fr.aresrpg.tofumanchou.infra.config.Configurations;
import fr.aresrpg.tofumanchou.infra.config.Configurations.Config;
import fr.aresrpg.tofumanchou.infra.config.Variables;
import fr.aresrpg.tofumanchou.infra.config.dao.GroupBean;
import fr.aresrpg.tofumanchou.infra.config.dao.PlayerBean;
import fr.aresrpg.tofumanchou.infra.config.dao.PlayerBean.PersoBean;
import fr.aresrpg.tofumanchou.infra.io.ManchouBridge;
import fr.aresrpg.tofumanchou.infra.plugin.PluginLoader;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.*;
import java.util.stream.IntStream;

/**
 * 
 * @since
 */
public class Manchou {

	public static final Logger LOGGER = new LoggerBuilder("TofuM").setUseConsoleHandler(true, true, Option.none(), Option.none()).build();
	public static final DofusServer ERATZ = new DofusServer(Server.ERATZ.getId(), ServerState.ONLINE, -1, true);
	public static final DofusServer HENUAL = new DofusServer(Server.HENUAL.getId(), ServerState.ONLINE, -1, true);
	public static ServerSocketChannel SOCKET_SERVER;
	private static Database database;
	private static final Map<String, Command> commands = new HashMap<>();
	private static Set<ManchouPlugin> plugins;
	private static Manchou instance;
	private static Selector selector;
	private final Config config;
	private boolean running;

	public Manchou() {
		LOGGER.success("Starting Tofumanchou..");
		instance = this;
		this.running = true;

		this.config = Configurations.generate("Tofumanchou.yml", Variables.class, Optional.of(() -> {
			LOGGER.info("Configuration created ! please configure and then restart.");
			System.exit(0);
		}), Optional.of(() -> {
			Variables.ACCOUNTS.add(new PlayerBean("compte1", "password1", new PersoBean("Xx-Immortal-xX", Server.HENUAL, 0), new PersoBean("Frahiko", Server.ERATZ, 0)));
			Variables.ACCOUNTS.add(new PlayerBean("compte2", "password2"));
			Variables.GROUPS.add(new GroupBean("testgroup", Server.HENUAL, "Jawad", "Tthomax", "Goodyxx", "Juste-puissant"));
		}));
		if (Variables.CUSTOM_LANGS) initDb();
		try {
			plugins = PluginLoader.getInstance().loadPlugins();
		} catch (IOException e) {
			LOGGER.error(e, "Error while loading plugins !");
			shutdown();
		}
		try {
			selector = Selector.open();
			SOCKET_SERVER = ServerSocketChannel.open();
			InetSocketAddress addr = new InetSocketAddress(Variables.PASSERELLE_IP, Variables.PASSERELLE_PORT);
			SOCKET_SERVER.bind(addr);
			SOCKET_SERVER.configureBlocking(false);
			SOCKET_SERVER.register(selector, SOCKET_SERVER.validOps());
			Executors.FIXED.execute(() -> new ManchouBridge("Passerelle", SOCKET_SERVER));
		} catch (IOException e) {
			LOGGER.error(e, "Unable to open the connection ! please contact DeltaEvo");
			shutdown();
		}
		LOGGER.success("Tofumanchou started !");
		Executors.FIXED.execute(this::startScanner);
	}

	public static void registerEvent(Listener listener) {
		try {
			Events.register(listener);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static DofusServer getServer(Server srv) {
		if (srv == Server.ERATZ) return ERATZ;
		else if (srv == Server.HENUAL) return HENUAL;
		else throw new IllegalArgumentException("The server '" + srv + "' is invalid !");
	}

	public static void directRegistry(ManchouPlugin p) {
		LOGGER.info(AnsiColor.GREEN + "Enabling plugin " + AnsiColor.PURPLE + p.getName() + AnsiColor.GREEN + " v" + AnsiColor.PURPLE + p.getVersion() + AnsiColor.GREEN + "."
				+ AnsiColor.PURPLE + p.getSubVersion());
		Executors.FIXED.execute(p::onEnable);
	}

	private void initDb() {
		try {
			database = new MongoDBDatabase(Variables.DB_NAME);
			database.connect(Variables.MONGO_IP, Variables.MONGO_PORT, Variables.MONGO_USER, Variables.MONGO_PASS);
		} catch (Exception e) {
			LOGGER.warning("Unable to use the database ! CustomLangs disabled.");
			LOGGER.debug(e);
			database = null;
		}
	}

	/**
	 * @return the database
	 */
	public static Database getDatabase() {
		return database;
	}

	/**
	 * @return the plugins
	 */
	public static Set<ManchouPlugin> getPlugins() {
		return plugins;
	}

	/**
	 * @return the selector
	 */
	public static Selector getSelector() {
		return selector;
	}

	/**
	 * @return the instance
	 */
	public static Manchou getInstance() {
		return instance;
	}

	public void shutdown() {
		LOGGER.warning("Closing Tofumanchou !");
		running = false;
		PluginLoader.getInstance().unloadPlugins();
		Executors.shutdown();
		LOGGER.warning("Bye.");
		System.exit(0);
	}

	public static void registerCommand(Command cmd) {
		commands.put(cmd.getCmd(), cmd);
	}

	public static void unregisterCommand(String cmd) {
		commands.remove(cmd);
	}

	private void startScanner() {
		Scanner sc = new Scanner(System.in);
		while (isRunning()) {
			if (!sc.hasNext()) continue;
			String[] args = parse(sc.nextLine());
			if (args == null) {
				LOGGER.warning("Unknow command !");
				continue;
			}
			String[] cmdargs = new String[args.length - 1];
			IntStream.range(1, args.length).forEach(i -> cmdargs[i - 1] = args[i]);
			Executors.FIXED.execute(Threads.threadContextSwitch("Cmd:" + args[0], () -> commands.get(args[0]).trigger(cmdargs)));
		}
	}

	private String[] parse(String line) {
		if (line.isEmpty()) return null;
		String[] all = line.split(" ");
		if (all.length < 1) return null;
		String cmd = all[0];
		if (cmd.isEmpty() || !commands.containsKey(cmd)) return null;
		return all;
	}

	/**
	 * @return the configuration
	 */
	public Config getConfig() {
		return config;
	}

	/**
	 * @return true if TofuManchou is running
	 */
	public boolean isRunning() {
		return running;
	}

}
