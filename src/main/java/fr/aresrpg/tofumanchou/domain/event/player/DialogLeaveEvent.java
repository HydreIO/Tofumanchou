package fr.aresrpg.tofumanchou.domain.event.player;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class DialogLeaveEvent implements Event<DialogLeaveEvent> {

	private static final EventBus<DialogLeaveEvent> BUS = new EventBus<>(DialogLeaveEvent.class);
	private Account client;

	public DialogLeaveEvent(Account client) {
		this.client = client;
	}

	/**
	 * @return the client
	 */
	public Account getClient() {
		return client;
	}

	@Override
	public EventBus<DialogLeaveEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
