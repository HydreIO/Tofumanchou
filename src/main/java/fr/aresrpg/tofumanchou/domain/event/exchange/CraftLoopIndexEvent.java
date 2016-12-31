package fr.aresrpg.tofumanchou.domain.event.exchange;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class CraftLoopIndexEvent implements Event<CraftLoopIndexEvent> {

	private static final EventBus<CraftLoopIndexEvent> BUS = new EventBus<>(CraftLoopIndexEvent.class);
	private Account client;
	private int index;

	/**
	 * @param client
	 * @param index
	 */
	public CraftLoopIndexEvent(Account client, int index) {
		this.client = client;
		this.index = index;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
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
	public EventBus<CraftLoopIndexEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
