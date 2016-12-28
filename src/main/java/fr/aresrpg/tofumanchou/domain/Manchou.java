package fr.aresrpg.tofumanchou.domain;

import fr.aresrpg.commons.domain.condition.Option;
import fr.aresrpg.commons.domain.log.Logger;
import fr.aresrpg.commons.domain.log.LoggerBuilder;
import fr.aresrpg.dofus.structures.server.Server;
import fr.aresrpg.tofumanchou.infra.config.Configurations;
import fr.aresrpg.tofumanchou.infra.config.Configurations.Config;
import fr.aresrpg.tofumanchou.infra.config.Variables;
import fr.aresrpg.tofumanchou.infra.config.dao.GroupBean;
import fr.aresrpg.tofumanchou.infra.config.dao.PlayerBean;
import fr.aresrpg.tofumanchou.infra.config.dao.PlayerBean.PersoBean;
import fr.aresrpg.tofumanchou.infra.plugin.PluginLoader;

import java.io.IOException;
import java.util.Optional;

/**
 * 
 * @since
 */
public class Manchou {

	public static final Logger LOGGER = new LoggerBuilder("TofuM").setUseConsoleHandler(true, true, Option.none(), Option.none()).build();
	private static final Manchou instance = new Manchou();
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
			Variables.GROUPS.add(new GroupBean("Testgroup", "Jawad", "Tthomax", "Goodyxx", "Juste-puissant"));
		}));

		try {
			PluginLoader.getInstance().loadPlugins();
		} catch (IOException e) {
			LOGGER.error(e, "Error while loading plugins !");
		}
	}

}
