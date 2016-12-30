package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.structures.Friend;
import fr.aresrpg.tofumanchou.domain.data.Account;

import java.util.List;

/**
 * An event triggered when the server send the friend list (basically when you ask for it or open the friend list in mitm)
 * 
 * @since
 */
public class FriendListsEvent implements Event<FriendListsEvent> {

	private static final EventBus<FriendListsEvent> BUS = new EventBus<>(FriendListsEvent.class);
	private Account client;
	private List<String> offlinesFriends;
	private List<Friend> onlinesFriends;

	/**
	 * @param client
	 * @param offlinesFriends
	 * @param onlinesFriends
	 */
	public FriendListsEvent(Account client, List<String> offlinesFriends, List<Friend> onlinesFriends) {
		this.client = client;
		this.offlinesFriends = offlinesFriends;
		this.onlinesFriends = onlinesFriends;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(Account client) {
		this.client = client;
	}

	/**
	 * @param offlinesFriends
	 *            the offlinesFriends to set
	 */
	public void setOfflinesFriends(List<String> offlinesFriends) {
		this.offlinesFriends = offlinesFriends;
	}

	/**
	 * @param onlinesFriends
	 *            the onlinesFriends to set
	 */
	public void setOnlinesFriends(List<Friend> onlinesFriends) {
		this.onlinesFriends = onlinesFriends;
	}

	/**
	 * @return the offlinesFriends
	 */
	public List<String> getOfflinesFriends() {
		return offlinesFriends;
	}

	/**
	 * @return the onlinesFriends
	 */
	public List<Friend> getOnlinesFriends() {
		return onlinesFriends;
	}

	/**
	 * @return the client
	 */
	public Account getClient() {
		return client;
	}

	@Override
	public EventBus<FriendListsEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return true;
	}

	@Override
	public String toString() {
		return "FriendListsEvent [client=" + client + ", offlinesFriends=" + offlinesFriends + ", onlinesFriends=" + onlinesFriends + "]";
	}

}
