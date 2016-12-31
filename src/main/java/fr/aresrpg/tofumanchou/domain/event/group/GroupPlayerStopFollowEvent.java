package fr.aresrpg.tofumanchou.domain.event.group;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class GroupPlayerStopFollowEvent implements Event<GroupPlayerStopFollowEvent> {

	private static final EventBus<GroupPlayerStopFollowEvent> BUS = new EventBus<>(GroupPlayerStopFollowEvent.class);
	private Account client;
	private String followed;

	/**
	 * @param client
	 * @param followed
	 */
	public GroupPlayerStopFollowEvent(Account client, String followed) {
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
	public EventBus<GroupPlayerStopFollowEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
