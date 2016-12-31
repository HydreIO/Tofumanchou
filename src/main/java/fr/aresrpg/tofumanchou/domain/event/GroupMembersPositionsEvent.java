package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.protocol.info.server.InfoCoordinatePacket.MovingPlayer;
import fr.aresrpg.tofumanchou.domain.data.Account;

import java.util.List;

/**
 * 
 * @since
 */
public class GroupMembersPositionsEvent implements Event<GroupMembersPositionsEvent> {

	private static final EventBus<GroupMembersPositionsEvent> BUS = new EventBus<>(GroupMembersPositionsEvent.class);
	private Account client;
	private List<MovingPlayer> players;

	/**
	 * @param client
	 * @param players
	 */
	public GroupMembersPositionsEvent(Account client, List<MovingPlayer> players) {
		this.client = client;
		this.players = players;
	}

	/**
	 * @return the players
	 */
	public List<MovingPlayer> getPlayers() {
		return players;
	}

	/**
	 * @param players
	 *            the players to set
	 */
	public void setPlayers(List<MovingPlayer> players) {
		this.players = players;
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
	public EventBus<GroupMembersPositionsEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
