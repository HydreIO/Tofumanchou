package fr.aresrpg.tofumanchou.domain.event.item;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class PodsUpdateEvent implements Event<PodsUpdateEvent> {

	private static final EventBus<PodsUpdateEvent> BUS = new EventBus<>(PodsUpdateEvent.class);
	private Account client;
	private int currentPods, maxPods;

	/**
	 * @param client
	 * @param currentPods
	 * @param maxPods
	 */
	public PodsUpdateEvent(Account client, int currentPods, int maxPods) {
		this.client = client;
		this.currentPods = currentPods;
		this.maxPods = maxPods;
	}

	/**
	 * @return the currentPods
	 */
	public int getCurrentPods() {
		return currentPods;
	}

	/**
	 * @param currentPods
	 *            the currentPods to set
	 */
	public void setCurrentPods(int currentPods) {
		this.currentPods = currentPods;
	}

	/**
	 * @return the maxPods
	 */
	public int getMaxPods() {
		return maxPods;
	}

	/**
	 * @param maxPods
	 *            the maxPods to set
	 */
	public void setMaxPods(int maxPods) {
		this.maxPods = maxPods;
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
	public EventBus<PodsUpdateEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

	@Override
	public String toString() {
		return "PodsUpdateEvent [client=" + client + ", currentPods=" + currentPods + ", maxPods=" + maxPods + "]";
	}

}
