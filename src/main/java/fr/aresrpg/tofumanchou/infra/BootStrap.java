package fr.aresrpg.tofumanchou.infra;

import static fr.aresrpg.tofumanchou.domain.Manchou.LOGGER;

import fr.aresrpg.tofumanchou.domain.data.*;
import fr.aresrpg.tofumanchou.domain.util.BenchTime;

import java.io.IOException;

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
	}

}
