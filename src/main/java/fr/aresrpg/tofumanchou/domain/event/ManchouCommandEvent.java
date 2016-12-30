package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.command.Command;

/**
 * 
 * @since
 */
public class ManchouCommandEvent implements Event<ManchouCommandEvent> {

	private static final EventBus<ManchouCommandEvent> BUS = new EventBus<>(ManchouCommandEvent.class);
	private final Command command;

	/**
	 * @param command
	 */
	public ManchouCommandEvent(Command command) {
		this.command = command;
	}

	/**
	 * @return the command
	 */
	public Command getCommand() {
		return command;
	}

	@Override
	public EventBus<ManchouCommandEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return true;
	}

	@Override
	public String toString() {
		return "ManchouCommandEvent [command=" + command + "]";
	}

}
