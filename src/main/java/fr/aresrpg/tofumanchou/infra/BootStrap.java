package fr.aresrpg.tofumanchou.infra;

import static fr.aresrpg.tofumanchou.domain.Manchou.LOGGER;

import fr.aresrpg.commons.domain.log.AnsiColors.AnsiColor;
import fr.aresrpg.tofumanchou.domain.Manchou;
import fr.aresrpg.tofumanchou.domain.data.*;
import fr.aresrpg.tofumanchou.domain.plugin.ManchouPlugin;
import fr.aresrpg.tofumanchou.domain.util.BenchTime;
import fr.aresrpg.tofumanchou.domain.util.concurrent.Executors;

import java.io.IOException;
import java.util.Set;

/**
 * 
 * @since
 */
public class BootStrap {
	public static void main(String[] args) throws IOException {
		LOGGER.info("Initialisating items..");
		BenchTime t = new BenchTime();
		ItemsData.getInstance().init();
		LOGGER.info("Items initialized ! (" + t.getAsLong() + "ms)");
		LOGGER.info("Initialisating maps..");
		BenchTime t2 = new BenchTime();
		MapsData.getInstance().init();
		LOGGER.info("Maps initialized ! (" + t2.getAsLong() + "ms)");
		LOGGER.info("Initialisating langs..");
		BenchTime t3 = new BenchTime();
		InfosData.getInstance().init();
		LOGGER.info("Langs initialized ! (" + t3.getAsLong() + "ms)");
		Set<ManchouPlugin> plugins = Manchou.getPlugins();
		if (plugins != null) plugins.forEach(p -> {
			LOGGER.info(AnsiColor.GREEN + "Enabling plugin " + AnsiColor.PURPLE + p.getName() + AnsiColor.GREEN + " v" + AnsiColor.PURPLE + p.getVersion() + AnsiColor.GREEN + "."
					+ AnsiColor.PURPLE + p.getSubVersion());
			Executors.CACHED.execute(p::onEnable);
		});
	}

}