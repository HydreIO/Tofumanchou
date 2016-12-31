package fr.aresrpg.tofumanchou.domain.event.player;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class PlayerTacledEvent implements Event<PlayerTacledEvent> {

	private static final EventBus<PlayerTacledEvent> BUS = new EventBus<>(PlayerTacledEvent.class);
	private Account client;

	public PlayerTacledEvent(Account client) {
		this.client = client;
	}

	/**
	 * @return the client
	 */
	public Account getClient() {
		return client;
	}

	@Override
	public EventBus<PlayerTacledEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
