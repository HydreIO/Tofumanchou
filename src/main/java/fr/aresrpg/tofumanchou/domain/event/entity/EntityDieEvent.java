package fr.aresrpg.tofumanchou.domain.event.entity;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.entity.Entity;

/**
 * 
 * @since
 */
public class EntityDieEvent implements Event<EntityDieEvent> {

	private static final EventBus<EntityDieEvent> BUS = new EventBus<>(EntityDieEvent.class);
	private Account client;
	private Entity entity;

	/**
	 * @param client
	 * @param entity
	 */
	public EntityDieEvent(Account client, Entity entity) {
		this.client = client;
		this.entity = entity;
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
	public EventBus<EntityDieEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

	@Override
	public String toString() {
		return "EntityDieEvent [client=" + client + ", entity=" + entity + "]";
	}

}
