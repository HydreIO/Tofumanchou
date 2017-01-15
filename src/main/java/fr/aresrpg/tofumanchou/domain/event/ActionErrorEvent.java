package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * An event triggered when a server change his state
 * 
 * @since
 */
public class ActionErrorEvent implements Event<ActionErrorEvent> {

	private static final EventBus<ActionErrorEvent> BUS = new EventBus<>(ActionErrorEvent.class);
	private Account client;
	private int lastAction = -1;

	public ActionErrorEvent(Account client, int lastAction) {
		this.client = client;
		this.lastAction = lastAction;
	}

	/**
	 * @return the lastAction
	 */
	public int getLastAction() {
		return lastAction;
	}

	/**
	 * @param lastAction
	 *            the lastAction to set
	 */
	public void setLastAction(int lastAction) {
		this.lastAction = lastAction;
	}

	/**
	 * @return the client
	 */
	public Account getClient() {
		return client;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(Account client) {
		this.client = client;
	}

	@Override
	public EventBus<ActionErrorEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
