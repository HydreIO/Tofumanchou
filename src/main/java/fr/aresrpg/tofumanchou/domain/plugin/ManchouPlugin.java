package fr.aresrpg.tofumanchou.domain.plugin;

/**
 * 
 * @since
 */
public interface ManchouPlugin {

	int getVersion();

	int getSubVersion();

	String getAuthor();

	String getName();

	void onEnable();

	void onDisable();

}
