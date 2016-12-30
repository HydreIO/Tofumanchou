package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
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
		super();
		this.client = client;
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
