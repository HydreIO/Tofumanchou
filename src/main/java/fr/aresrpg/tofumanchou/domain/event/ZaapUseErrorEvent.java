package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class ZaapUseErrorEvent implements Event<ZaapUseErrorEvent> {

	private static final EventBus<ZaapUseErrorEvent> BUS = new EventBus<>(ZaapUseErrorEvent.class);
	private Account client;

	public ZaapUseErrorEvent(Account client) {
		this.client = client;
	}

	/**
	 * @return the client
	 */
	public Account getClient() {
		return client;
	}

	@Override
	public EventBus<ZaapUseErrorEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
