package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.entity.mob.MobGroup;

/**
 * 
 * @since
 */
public class MonsterGroupSpawnEvent implements Event<MonsterGroupSpawnEvent> { // ASYNCHRONE

	private static final EventBus<MonsterGroupSpawnEvent> BUS = new EventBus<>(MonsterGroupSpawnEvent.class);
	private Account client;
	private MobGroup group;

	/**
	 * @param client
	 * @param group
	 */
	public MonsterGroupSpawnEvent(Account client, MobGroup group) {
		this.client = client;
		this.group = group;
	}

	/**
	 * @return the group
	 */
	public MobGroup getGroup() {
		return group;
	}

	/**
	 * @param group
	 *            the group to set
	 */
	public void setGroup(MobGroup group) {
		this.group = group;
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
	public EventBus<MonsterGroupSpawnEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return true;
	}

	@Override
	public String toString() {
		return "MonsterGroupSpawnEvent [client=" + client + ", group=" + group + "]";
	}

}
