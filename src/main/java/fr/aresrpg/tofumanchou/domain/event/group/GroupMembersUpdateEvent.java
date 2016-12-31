package fr.aresrpg.tofumanchou.domain.event.group;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.structures.ExchangeMove;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.entity.player.Player;

/**
 * 
 * @since
 */
public class GroupMembersUpdateEvent implements Event<GroupMembersUpdateEvent> {

	private static final EventBus<GroupMembersUpdateEvent> BUS = new EventBus<>(GroupMembersUpdateEvent.class);
	private Account client;
	private ExchangeMove update;
	private Player[] members;

	/**
	 * @param client
	 * @param update
	 * @param members
	 */
	public GroupMembersUpdateEvent(Account client, ExchangeMove update, Player[] members) {
		this.client = client;
		this.update = update;
		this.members = members;
	}

	/**
	 * @return the update
	 */
	public ExchangeMove getUpdate() {
		return update;
	}

	/**
	 * @param update
	 *            the update to set
	 */
	public void setUpdate(ExchangeMove update) {
		this.update = update;
	}

	/**
	 * @return the members
	 */
	public Player[] getMembers() {
		return members;
	}

	/**
	 * @param members
	 *            the members to set
	 */
	public void setMembers(Player[] members) {
		this.members = members;
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
	public EventBus<GroupMembersUpdateEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
