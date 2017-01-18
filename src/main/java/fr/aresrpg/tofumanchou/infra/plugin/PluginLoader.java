package fr.aresrpg.tofumanchou.infra.plugin;

import static fr.aresrpg.tofumanchou.domain.Manchou.LOGGER;

import fr.aresrpg.commons.domain.log.AnsiColors.AnsiColor;
import fr.aresrpg.tofumanchou.domain.plugin.ManchouPlugin;

import java.io.File;
import java.io.IOException;
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

	public Set<ManchouPlugin> loadPlugins() throws IOException {
		LOGGER.info("Loading plugins..");
		Set<ManchouPlugin> hashSet = new HashSet<>();
		File loc = new File("plugins");
		File[] flist = loc.listFiles(file -> file.getName().endsWith(".jar"));
		if (flist == null || flist.length == 0) {
			LOGGER.warning("No plugins detected under /plugins/*");
			return hashSet;
		}
		URL[] urls = new URL[flist.length];
		for (int i = 0; i < flist.length; i++)
			urls[i] = flist[i].toURI().toURL();
		ClassLoader ucl = new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());

		ServiceLoader<ManchouPlugin> sl = ServiceLoader.load(ManchouPlugin.class, ucl);
		Iterator<ManchouPlugin> apit = sl.iterator();
		while (apit.hasNext()) {
			ManchouPlugin plugin = apit.next();
			plugins.add(plugin);
			if (plugin.getAuthor() == null) {
				LOGGER.error("The author is null for plugin " + plugin.getClass());
				continue;
			}
			if (plugin.getName() == null) {
				LOGGER.error("The name is null for plugin " + plugin.getClass());
				continue;
			}
			LOGGER.success(AnsiColor.GREEN + "Successfully loaded plugin " + AnsiColor.PURPLE + plugin.getName() + AnsiColor.GREEN + " v" + AnsiColor.PURPLE + plugin.getVersion()
					+ AnsiColor.GREEN + "." + AnsiColor.PURPLE + plugin.getSubVersion() + AnsiColor.GREEN + " by " + AnsiColor.PURPLE + plugin.getAuthor());
			hashSet.add(plugin);
		}
		if (hashSet.isEmpty()) LOGGER.warning("No plugins are registered in the manifest under /plugins/*");
		return hashSet;
	}

}
