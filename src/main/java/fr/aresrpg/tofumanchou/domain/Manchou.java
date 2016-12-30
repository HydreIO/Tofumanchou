package fr.aresrpg.tofumanchou.domain;

import fr.aresrpg.commons.domain.condition.Option;
import fr.aresrpg.commons.domain.log.Logger;
import fr.aresrpg.commons.domain.log.LoggerBuilder;
import fr.aresrpg.dofus.structures.server.*;
import fr.aresrpg.tofumanchou.domain.command.Command;
import fr.aresrpg.tofumanchou.domain.event.ManchouCommandEvent;
import fr.aresrpg.tofumanchou.domain.util.concurrent.Executors;
import fr.aresrpg.tofumanchou.infra.command.ManchouCommand;
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
import java.util.Optional;
import java.util.Scanner;

/**
 * 
 * @since
 */
public class Manchou {

	public static final Logger LOGGER = new LoggerBuilder("TofuM").setUseConsoleHandler(true, true, Option.none(), Option.none()).build();
	public static final DofusServer ERATZ = new DofusServer(Server.ERATZ.getId(), ServerState.ONLINE, -1, true);
	public static final DofusServer HENUAL = new DofusServer(Server.HENUAL.getId(), ServerState.ONLINE, -1, true);
	private static final Manchou instance = new Manchou();
	private static Selector selector;
	private final Config config;
	private boolean running;

	public Manchou() {
		this.running = true;
		this.config = Configurations.generate("Tofumanchou.yml", Variables.class, Optional.of(() -> {
			LOGGER.info("Configuration created ! please configure and then restart.");
			System.exit(0);
		}), Optional.of(() -> {
			Variables.ACCOUNTS.add(new PlayerBean("compte1", "password1", new PersoBean("Xx-Immortal-xX", Server.HENUAL), new PersoBean("Frahiko", Server.ERATZ)));
			Variables.ACCOUNTS.add(new PlayerBean("compte2", "password2"));
			Variables.GROUPS.add(new GroupBean("testgroup", "Jawad", "Tthomax", "Goodyxx", "Juste-puissant"));
		}));

		try {
			PluginLoader.getInstance().loadPlugins();
		} catch (IOException e) {
			LOGGER.error(e, "Error while loading plugins !");
			shutdown();
		}
		try {
			selector = Selector.open();
			ServerSocketChannel socket = ServerSocketChannel.open();
			InetSocketAddress addr = new InetSocketAddress(Variables.PASSERELLE_IP, Variables.PASSERELLE_PORT);
			socket.bind(addr);
			socket.configureBlocking(false);
			socket.register(selector, socket.validOps());
			Executors.CACHED.execute(() -> new ManchouBridge("Passerelle", socket));
		} catch (IOException e) {
			LOGGER.error(e, "Unable to open the connection ! please contact DeltaEvo");
			shutdown();
		}
		startScanner();
	}

	/**
	 * @return the selector
	 */
	public static Selector getSelector() {
		return selector;
	}

	public void shutdown() {
		LOGGER.warning("Closing Tofumanchou !");
		running = false;
		PluginLoader.getInstance().unloadPlugins();
		Executors.shutdown();
		LOGGER.warning("Bye.");
		System.exit(0);
	}

	private void startScanner() {
		Scanner sc = new Scanner(System.in);
		while (isRunning()) {
			if (!sc.hasNext()) continue;
			Command cmd = ManchouCommand.parse(sc.nextLine());
			if (cmd != null) new ManchouCommandEvent(cmd).send();
		}
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
