package fr.aresrpg.tofumanchou.domain.event.entity;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.entity.Entity;

/**
 * 
 * @since
 */
public class EntityPmChangeEvent implements Event<EntityPmChangeEvent> {

	private static final EventBus<EntityPmChangeEvent> BUS = new EventBus<>(EntityPmChangeEvent.class);
	private Account client;
	private Entity entity;
	private int pm;

	/**
	 * @param client
	 * @param entity
	 * @param pa
	 */
	public EntityPmChangeEvent(Account client, Entity entity, int pm) {
		this.client = client;
		this.entity = entity;
		this.pm = pm;
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
	 * @return the pa
	 */
	public int getPm() {
		return pm;
	}

	/**
	 * @param pa
	 *            the pa to set
	 */
	public void setPm(int pm) {
		this.pm = pm;
	}

	@Override
	public EventBus<EntityPmChangeEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

	@Override
	public String toString() {
		return "EntityPaChangeEvent [client=" + client + ", entity=" + entity + ", pm=" + pm + "]";
	}

}
