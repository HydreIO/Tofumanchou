package fr.aresrpg.tofumanchou.domain.event.player;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class ZaapGuiLeaveEvent implements Event<ZaapGuiLeaveEvent> {

	private static final EventBus<ZaapGuiLeaveEvent> BUS = new EventBus<>(ZaapGuiLeaveEvent.class);
	private Account client;

	public ZaapGuiLeaveEvent(Account client) {
		this.client = client;
	}

	/**
	 * @return the client
	 */
	public Account getClient() {
		return client;
	}

	@Override
	public EventBus<ZaapGuiLeaveEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
