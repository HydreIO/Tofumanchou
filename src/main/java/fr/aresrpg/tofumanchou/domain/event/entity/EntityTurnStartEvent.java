package fr.aresrpg.tofumanchou.domain.event.entity;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.entity.Entity;

/**
 * 
 * @since
 */
public class EntityTurnStartEvent implements Event<EntityTurnStartEvent> {

	private static final EventBus<EntityTurnStartEvent> BUS = new EventBus<>(EntityTurnStartEvent.class);
	private Account client;
	private Entity entity;
	private int time;

	/**
	 * @param client
	 * @param entity
	 * @param time
	 */
	public EntityTurnStartEvent(Account client, Entity entity, int time) {
		this.client = client;
		this.entity = entity;
		this.time = time;
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
	 * @return the time
	 */
	public int getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(int time) {
		this.time = time;
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
	public EventBus<EntityTurnStartEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
