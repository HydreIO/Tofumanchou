package fr.aresrpg.tofumanchou.domain.event.group;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class PlayerLeaveGroupEvent implements Event<PlayerLeaveGroupEvent> {

	private static final EventBus<PlayerLeaveGroupEvent> BUS = new EventBus<>(PlayerLeaveGroupEvent.class);
	private Account client;
	private String player;

	/**
	 * @param client
	 * @param player
	 */
	public PlayerLeaveGroupEvent(Account client, String player) {
		this.client = client;
		this.player = player;
	}

	/**
	 * @return the player
	 */
	public String getPlayer() {
		return player;
	}

	/**
	 * @param player
	 *            the player to set
	 */
	public void setPlayer(String player) {
		this.player = player;
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
	public EventBus<PlayerLeaveGroupEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
