package fr.aresrpg.tofumanchou.domain.event.entity;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.entity.Entity;

/**
 * 
 * @since
 */
public class EntityPaChangeEvent implements Event<EntityPaChangeEvent> {

	private static final EventBus<EntityPaChangeEvent> BUS = new EventBus<>(EntityPaChangeEvent.class);
	private Account client;
	private Entity entity;
	private int pa;

	/**
	 * @param client
	 * @param entity
	 * @param pa
	 */
	public EntityPaChangeEvent(Account client, Entity entity, int pa) {
		this.client = client;
		this.entity = entity;
		this.pa = pa;
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
	public int getPa() {
		return pa;
	}

	/**
	 * @param pa
	 *            the pa to set
	 */
	public void setPa(int pa) {
		this.pa = pa;
	}

	@Override
	public EventBus<EntityPaChangeEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

	@Override
	public String toString() {
		return "EntityPaChangeEvent [client=" + client + ", entity=" + entity + ", pa=" + pa + "]";
	}

}
