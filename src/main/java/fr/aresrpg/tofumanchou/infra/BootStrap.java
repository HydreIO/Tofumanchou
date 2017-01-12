package fr.aresrpg.tofumanchou.infra;

import static fr.aresrpg.tofumanchou.domain.Manchou.LOGGER;

import fr.aresrpg.commons.domain.concurrent.Threads;
import fr.aresrpg.commons.domain.log.AnsiColors.AnsiColor;
import fr.aresrpg.tofumanchou.domain.Manchou;
import fr.aresrpg.tofumanchou.domain.data.*;
import fr.aresrpg.tofumanchou.domain.plugin.ManchouPlugin;
import fr.aresrpg.tofumanchou.domain.util.BenchTime;
import fr.aresrpg.tofumanchou.domain.util.concurrent.Executors;
import fr.aresrpg.tofumanchou.infra.config.Variables;

import java.io.IOException;
import java.util.Set;

/**
 * 
 * @since
 */
public class BootStrap {
	public static void main(String[] args) throws IOException {
		new Manchou();
		LOGGER.info("Initialising items..");
		BenchTime t = new BenchTime();
		ItemsData.getInstance().init(Variables.CUSTOM_LANGS);
		LOGGER.info("Items initialized ! (" + t.getAsLong() + "ms)");
		LOGGER.info("Initialising maps..");
		BenchTime t2 = new BenchTime();
		MapsData.getInstance().init(Variables.CUSTOM_LANGS);
		LOGGER.info("Maps initialized ! (" + t2.getAsLong() + "ms)");
		LOGGER.info("Initialising langs..");
		BenchTime t3 = new BenchTime();
		InfosData.getInstance().init(Variables.CUSTOM_LANGS);
		LOGGER.info("Langs initialized ! (" + t3.getAsLong() + "ms)");
		Set<ManchouPlugin> plugins = Manchou.getPlugins();
		if (plugins != null) plugins.forEach(p -> {
			LOGGER.info(AnsiColor.GREEN + "Enabling plugin " + AnsiColor.PURPLE + p.getName() + AnsiColor.GREEN + " v" + AnsiColor.PURPLE + p.getVersion() + AnsiColor.GREEN + "."
					+ AnsiColor.PURPLE + p.getSubVersion());
			Executors.FIXED.execute(Threads.threadContextSwitch("Main %1$d", p::onEnable));
		});
	}

}
