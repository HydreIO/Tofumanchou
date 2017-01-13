package fr.aresrpg.tofumanchou.domain.event.entity;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.entity.mob.Mob;

/**
 * 
 * @since
 */
public class MonsterJoinMapEvent implements Event<MonsterJoinMapEvent> {

	private static final EventBus<MonsterJoinMapEvent> BUS = new EventBus<>(MonsterJoinMapEvent.class);
	private Account client;
	private Mob mob;

	/**
	 * @param client
	 * @param mob
	 */
	public MonsterJoinMapEvent(Account client, Mob mob) {
		this.client = client;
		this.mob = mob;
	}

	/**
	 * @return the mob
	 */
	public Mob getMob() {
		return mob;
	}

	/**
	 * @param mob
	 *            the mob to set
	 */
	public void setMob(Mob mob) {
		this.mob = mob;
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
	public EventBus<MonsterJoinMapEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

	@Override
	public String toString() {
		return "MonsterJoinMapEvent [client=" + client + ", mob=" + mob + "]";
	}

}
