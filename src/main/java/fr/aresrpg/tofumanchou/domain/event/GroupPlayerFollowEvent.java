package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class GroupPlayerFollowEvent implements Event<GroupPlayerFollowEvent> {

	private static final EventBus<GroupPlayerFollowEvent> BUS = new EventBus<>(GroupPlayerFollowEvent.class);
	private Account client;
	private String followed;

	/**
	 * @param client
	 * @param followed
	 */
	public GroupPlayerFollowEvent(Account client, String followed) {
		this.client = client;
		this.followed = followed;
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

	/**
	 * @return the followed
	 */
	public String getFollowed() {
		return followed;
	}

	/**
	 * @param followed
	 *            the followed to set
	 */
	public void setFollowed(String followed) {
		this.followed = followed;
	}

	@Override
	public EventBus<GroupPlayerFollowEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
