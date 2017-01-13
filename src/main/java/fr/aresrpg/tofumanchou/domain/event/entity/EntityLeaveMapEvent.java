package fr.aresrpg.tofumanchou.domain.event.entity;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class EntityLeaveMapEvent implements Event<EntityLeaveMapEvent> {

	private static final EventBus<EntityLeaveMapEvent> BUS = new EventBus<>(EntityLeaveMapEvent.class);
	private Account client;
	private long entityId;

	/**
	 * @param client
	 * @param entityId
	 */
	public EntityLeaveMapEvent(Account client, long entityId) {
		this.client = client;
		this.entityId = entityId;
	}

	/**
	 * @return the entityId
	 */
	public long getEntityId() {
		return entityId;
	}

	/**
	 * @param entityId
	 *            the entityId to set
	 */
	public void setEntityId(long entityId) {
		this.entityId = entityId;
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
	public EventBus<EntityLeaveMapEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
