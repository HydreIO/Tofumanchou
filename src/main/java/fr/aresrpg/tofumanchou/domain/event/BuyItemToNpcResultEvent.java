package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class BuyItemToNpcResultEvent implements Event<BuyItemToNpcResultEvent> {

	private static final EventBus<BuyItemToNpcResultEvent> BUS = new EventBus<>(BuyItemToNpcResultEvent.class);
	private Account client;
	private boolean success;

	/**
	 * @param client
	 * @param success
	 */
	public BuyItemToNpcResultEvent(Account client, boolean success) {
		this.client = client;
		this.success = success;
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success
	 *            the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
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
	public EventBus<BuyItemToNpcResultEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
