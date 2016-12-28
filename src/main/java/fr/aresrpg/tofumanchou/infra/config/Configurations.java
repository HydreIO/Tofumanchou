package fr.aresrpg.tofumanchou.infra.config;

import static fr.aresrpg.tofumanchou.domain.Manchou.LOGGER;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class Configurations {

	private Configurations() {

	}

	/**
	 * 
	 * @param file
	 * @param bundle
	 * @param ifnewconfig
	 *            actions to exec if the config file doesn't exist
	 * @return
	 */
	public static Config generate(File file, Class<?> bundle, Optional<Runnable> ifnewconfig, Optional<Runnable> beforeApply) {
		return new Config(file, bundle, ifnewconfig, beforeApply);
	}

	/**
	 * 
	 * @param file
	 * @param bundle
	 * @param ifnewconfig
	 *            actions to exec if the config file doesn't exist
	 * @return
	 */
	public static Config generate(String file, Class<?> bundle, Optional<Runnable> ifnewconfig, Optional<Runnable> beforeApply) {
		return generate(new File(file), bundle, ifnewconfig, beforeApply);
	}

	public static class Config {
		private Configuration conf;
		private File file;
		private Class<?> bundle;

		private Config(File file, Class<?> configBundle, Optional<Runnable> r, Optional<Runnable> before) {
			this.file = file;
			this.bundle = configBundle;
			LOGGER.info("Loading configuration...");
			boolean mkdir = true;
			try {
				mkdir = file.exists() ? false : file.createNewFile();
				setConf(load(file));
			} catch (IOException e3) {
				e3.printStackTrace();
			}
			if (mkdir) {
				before.ifPresent(Runnable::run);
				apply();
				r.ifPresent(Runnable::run);
			}
			pull();
		}

		public void pull() {
			LOGGER.info("Pulling configuration file...");
			Arrays.stream(getBundle().getFields()).filter(f -> {
				f.setAccessible(true);
				return f.isAnnotationPresent(Configured.class);
			}).forEach(f -> { // NOSONAR
				Configured an = f.getAnnotation(Configured.class);
				Object t = null;
				try {
					t = getConf().get(an.value() + f.getName(), f.get(null));
					f.set(null, t);
				} catch (Exception e) {
					LOGGER.info("Unable to set field " + f.getName() + " with type " + t);
					e.printStackTrace();
				}
			});
		}

		public void apply() {
			LOGGER.info("Applying configuration file...");
			Arrays.stream(getBundle().getFields()).filter(f -> f.isAnnotationPresent(Configured.class)).forEach(f -> { // NOSONAR
				Configured an = f.getAnnotation(Configured.class);
				try {
					getConf().set(an.value() + f.getName(), f.get(null));
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			save();
		}

		public Class<?> getBundle() {
			return bundle;
		}

		public Configuration getConf() {
			return conf;
		}

		public Configuration load(File file) throws IOException {
			return ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
		}

		private void setConf(Configuration conf) {
			this.conf = conf;
		}

		public void save() {
			try {
				ConfigurationProvider.getProvider(YamlConfiguration.class).save(getConf(), getFile());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public File getFile() {
			return file;
		}

	}

}
