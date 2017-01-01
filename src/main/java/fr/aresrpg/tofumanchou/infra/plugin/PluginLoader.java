package fr.aresrpg.tofumanchou.infra.plugin;

import static fr.aresrpg.tofumanchou.domain.Manchou.LOGGER;

import fr.aresrpg.commons.domain.log.AnsiColors.AnsiColor;
import fr.aresrpg.tofumanchou.domain.plugin.ManchouPlugin;
import fr.aresrpg.tofumanchou.domain.util.concurrent.Executors;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class PluginLoader {

	private static final PluginLoader instance = new PluginLoader();
	private final Set<ManchouPlugin> plugins = new HashSet<>();

	private PluginLoader() {

	}

	/**
	 * @return the instance
	 */
	public static PluginLoader getInstance() {
		return instance;
	}

	public void unloadPlugins() {
		LOGGER.warning("Disabling plugins..");
		plugins.forEach(ManchouPlugin::onDisable);
		LOGGER.warning("All plugins disabled !");
	}

	public void loadPlugins() throws IOException {
		LOGGER.info("Loading plugins..");
		File loc = new File("plugins");
		File[] flist = loc.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.getPath().toLowerCase().endsWith(".jar");
			}
		});
		if (flist == null || flist.length == 0) {
			LOGGER.warning("No plugins detected under /plugins/*");
			return;
		}
		URL[] urls = new URL[flist.length];
		for (int i = 0; i < flist.length; i++)
			urls[i] = flist[i].toURI().toURL();
		URLClassLoader ucl = new URLClassLoader(urls);

		ServiceLoader<ManchouPlugin> sl = ServiceLoader.load(ManchouPlugin.class, ucl);
		Iterator<ManchouPlugin> apit = sl.iterator();
		while (apit.hasNext()) {
			ManchouPlugin plugin = apit.next();
			plugins.add(plugin);
			LOGGER.success(AnsiColor.GREEN + "Successfully loaded plugin " + AnsiColor.PURPLE + plugin.getName() + " " + AnsiColor.GREEN + " v" + AnsiColor.PURPLE + plugin.getVersion()
					+ AnsiColor.GREEN + "." + AnsiColor.PURPLE + plugin.getSubVersion() + AnsiColor.GREEN + " by " + AnsiColor.PURPLE + plugin.getAuthor());
			Executors.CACHED.execute(plugin::onEnable);
		}
	}

}
