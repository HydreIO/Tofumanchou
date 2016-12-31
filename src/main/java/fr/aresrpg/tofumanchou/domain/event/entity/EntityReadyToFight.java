package fr.aresrpg.tofumanchou.domain.event.entity;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.entity.Entity;

/**
 * 
 * @since
 */
public class EntityReadyToFight implements Event<EntityReadyToFight> {

	private static final EventBus<EntityReadyToFight> BUS = new EventBus<>(EntityReadyToFight.class);
	private Account client;
	private Entity entity;
	private boolean ready;

	/**
	 * @param client
	 * @param entity
	 * @param ready
	 */
	public EntityReadyToFight(Account client, Entity entity, boolean ready) {
		this.client = client;
		this.entity = entity;
		this.ready = ready;
	}

	/**
	 * @return the entity
	 */
	public Entity getEntity() {
		return entity;
	}

	/**
	 * @param entity
	 *            the entity to set
	 */
	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	/**
	 * @return the ready
	 */
	public boolean isReady() {
		return ready;
	}

	/**
	 * @param ready
	 *            the ready to set
	 */
	public void setReady(boolean ready) {
		this.ready = ready;
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
	public EventBus<EntityReadyToFight> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
