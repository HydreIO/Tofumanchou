package fr.aresrpg.tofumanchou.domain.event.group;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class GroupLeaderUpdateEvent implements Event<GroupLeaderUpdateEvent> {

	private static final EventBus<GroupLeaderUpdateEvent> BUS = new EventBus<>(GroupLeaderUpdateEvent.class);
	private Account client;
	private long leaderId;

	/**
	 * @param client
	 * @param leaderId
	 */
	public GroupLeaderUpdateEvent(Account client, long leaderId) {
		this.client = client;
		this.leaderId = leaderId;
	}

	/**
	 * @return the leaderId
	 */
	public long getLeaderId() {
		return leaderId;
	}

	/**
	 * @param leaderId
	 *            the leaderId to set
	 */
	public void setLeaderId(long leaderId) {
		this.leaderId = leaderId;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(Account client) {
		this.client = client;
	}

	/**
	 * @return the client
	 */
	public Account getClient() {
		return client;
	}

	@Override
	public EventBus<GroupLeaderUpdateEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
