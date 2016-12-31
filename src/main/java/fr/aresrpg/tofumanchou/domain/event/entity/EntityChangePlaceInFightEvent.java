package fr.aresrpg.tofumanchou.domain.event.entity;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.entity.Entity;

/**
 * 
 * @since
 */
public class EntityChangePlaceInFightEvent implements Event<EntityChangePlaceInFightEvent> {

	private static final EventBus<EntityChangePlaceInFightEvent> BUS = new EventBus<>(EntityChangePlaceInFightEvent.class);
	private Account client;
	private Entity entity;
	private int position;

	/**
	 * @param client
	 * @param entity
	 * @param position
	 */
	public EntityChangePlaceInFightEvent(Account client, Entity entity, int position) {
		this.client = client;
		this.entity = entity;
		this.position = position;
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
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
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
	public EventBus<EntityChangePlaceInFightEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

	@Override
	public String toString() {
		return "EntityChangePlaceInFightEvent [client=" + client + ", entity=" + entity + ", position=" + position + "]";
	}

}
