package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * An event triggered when the position in queue to connect on a server is updated
 * 
 * @since
 */
public class ServerQueuePositionEvent implements Event<ServerQueuePositionEvent> {

	private static final EventBus<ServerQueuePositionEvent> BUS = new EventBus<>(ServerQueuePositionEvent.class);
	private Account client;
	private int position;

	/**
	 * @param client
	 * @param position
	 */
	public ServerQueuePositionEvent(Account client, int position) {
		this.client = client;
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
	 * @param position
	 *            the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @return the client
	 */
	public Account getClient() {
		return client;
	}

	@Override
	public EventBus<ServerQueuePositionEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return true;
	}

	@Override
	public String toString() {
		return "ServerQueuePositionEvent [client=" + client + ", position=" + position + "]";
	}

}
