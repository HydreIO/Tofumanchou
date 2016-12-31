package fr.aresrpg.tofumanchou.domain.event.aproach;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * An event triggered when the position in queue to connect on realm is updated
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
	 * @param totalSub
	 *            the totalSub to set
	 */
	public void setTotalSub(int totalSub) {
		this.totalSub = totalSub;
	}

	/**
	 * @param totalNoSub
	 *            the totalNoSub to set
	 */
	public void setTotalNoSub(int totalNoSub) {
		this.totalNoSub = totalNoSub;
	}

	/**
	 * @param positionInQueue
	 *            the positionInQueue to set
	 */
	public void setPositionInQueue(int positionInQueue) {
		this.positionInQueue = positionInQueue;
	}

	/**
	 * @param sub
	 *            the sub to set
	 */
	public void setSub(boolean sub) {
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
