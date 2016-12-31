package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class PlayerRefuseGroupInvitationEvent implements Event<PlayerRefuseGroupInvitationEvent> {

	private static final EventBus<PlayerRefuseGroupInvitationEvent> BUS = new EventBus<>(PlayerRefuseGroupInvitationEvent.class);
	private Account client;

	public PlayerRefuseGroupInvitationEvent(Account client) {
		this.client = client;
	}

	/**
	 * @return the client
	 */
	public Account getClient() {
		return client;
	}

	@Override
	public EventBus<PlayerRefuseGroupInvitationEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
