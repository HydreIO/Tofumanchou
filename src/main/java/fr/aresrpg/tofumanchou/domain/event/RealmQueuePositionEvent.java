package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class RealmQueuePositionEvent implements Event<RealmQueuePositionEvent> {

	private static final EventBus<RealmQueuePositionEvent> BUS = new EventBus<>(RealmQueuePositionEvent.class);
	private Account client;
	private int position, totalSub, totalNoSub, positionInQueue;
	private boolean sub;

	/**
	 * @param client
	 * @param position
	 * @param totalSub
	 * @param totalNoSub
	 * @param positionInQueue
	 * @param sub
	 */
	public RealmQueuePositionEvent(Account client, int position, int totalSub, int totalNoSub, int positionInQueue, boolean sub) {
		super();
		this.client = client;
		this.position = position;
		this.totalSub = totalSub;
		this.totalNoSub = totalNoSub;
		this.positionInQueue = positionInQueue;
		this.sub = sub;
	}

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @return the totalSub
	 */
	public int getTotalSub() {
		return totalSub;
	}

	/**
	 * @return the totalNoSub
	 */
	public int getTotalNoSub() {
		return totalNoSub;
	}

	/**
	 * @return the positionInQueue
	 */
	public int getPositionInQueue() {
		return positionInQueue;
	}

	/**
	 * @return the sub
	 */
	public boolean isSub() {
		return sub;
	}

	/**
	 * @return the client
	 */
	public Account getClient() {
		return client;
	}

	@Override
	public EventBus<RealmQueuePositionEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return true;
	}

	@Override
	public String toString() {
		return "RealmQueuePositionEvent [client=" + client + ", position=" + position + ", totalSub=" + totalSub + ", totalNoSub=" + totalNoSub + ", positionInQueue=" + positionInQueue + ", sub="
				+ sub + "]";
	}
}
