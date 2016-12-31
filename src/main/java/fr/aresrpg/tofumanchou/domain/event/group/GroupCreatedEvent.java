package fr.aresrpg.tofumanchou.domain.event.group;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class GroupCreatedEvent implements Event<GroupCreatedEvent> {

	private static final EventBus<GroupCreatedEvent> BUS = new EventBus<>(GroupCreatedEvent.class);
	private Account client;

	public GroupCreatedEvent(Account client) {
		this.client = client;
	}

	/**
	 * @return the client
	 */
	public Account getClient() {
		return client;
	}

	@Override
	public EventBus<GroupCreatedEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
