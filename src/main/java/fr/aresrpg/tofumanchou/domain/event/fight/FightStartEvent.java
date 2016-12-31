package fr.aresrpg.tofumanchou.domain.event.fight;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class FightStartEvent implements Event<FightStartEvent> {

	private static final EventBus<FightStartEvent> BUS = new EventBus<>(FightStartEvent.class);
	private Account client;

	public FightStartEvent(Account client) {
		this.client = client;
	}

	/**
	 * @return the client
	 */
	public Account getClient() {
		return client;
	}

	@Override
	public EventBus<FightStartEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
