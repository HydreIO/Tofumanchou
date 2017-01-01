package fr.aresrpg.tofumanchou.domain.command;

/**
 * 
 * @since
 */
public interface Command {

	String getCmd();

	void trigger(String[] args);

}
