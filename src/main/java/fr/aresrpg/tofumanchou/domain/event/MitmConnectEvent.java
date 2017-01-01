package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * An event triggered when a server change his state
 * 
 * @since
 */
public class MitmConnectEvent implements Event<MitmConnectEvent> {

	private static final EventBus<MitmConnectEvent> BUS = new EventBus<>(MitmConnectEvent.class);
	private Account client;

	/**
	 * @param client
	 */
	public MitmConnectEvent(Account client) {
		this.client = client;
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
	public EventBus<MitmConnectEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
