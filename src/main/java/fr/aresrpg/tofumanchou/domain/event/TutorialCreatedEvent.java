package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * An event triggered when a server change his state
 * 
 * @since
 */
public class TutorialCreatedEvent implements Event<TutorialCreatedEvent> {

	private static final EventBus<TutorialCreatedEvent> BUS = new EventBus<>(TutorialCreatedEvent.class);
	private Account client;
	private int tutoId;
	private long longId;

	public TutorialCreatedEvent(Account client, int tutoId, long longId) {
		this.client = client;
		this.tutoId = tutoId;
		this.longId = longId;
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
	 * @return the tutoId
	 */
	public int getTutoId() {
		return tutoId;
	}

	/**
	 * @param tutoId
	 *            the tutoId to set
	 */
	public void setTutoId(int tutoId) {
		this.tutoId = tutoId;
	}

	/**
	 * @return the longId
	 */
	public long getLongId() {
		return longId;
	}

	/**
	 * @param longId
	 *            the longId to set
	 */
	public void setLongId(long longId) {
		this.longId = longId;
	}

	@Override
	public EventBus<TutorialCreatedEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

	@Override
	public String toString() {
		return "TutorialCreatedEvent [client=" + client + ", tutoId=" + tutoId + ", longId=" + longId + "]";
	}

}
