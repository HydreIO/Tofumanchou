package fr.aresrpg.tofumanchou.domain.event.entity;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.entity.player.Player;

/**
 * 
 * @since
 */
public class EntityPlayerJoinMapEvent implements Event<EntityPlayerJoinMapEvent> { // ASYNCHRONE

	private static final EventBus<EntityPlayerJoinMapEvent> BUS = new EventBus<>(EntityPlayerJoinMapEvent.class);
	private Account client;
	private Player player;

	/**
	 * @param client
	 * @param player
	 */
	public EntityPlayerJoinMapEvent(Account client, Player player) {
		this.client = client;
		this.player = player;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param player
	 *            the player to set
	 */
	public void setPlayer(Player player) {
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
	public EventBus<EntityPlayerJoinMapEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return true;
	}

}
