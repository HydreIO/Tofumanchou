package fr.aresrpg.tofumanchou.domain.event.exchange;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.structures.CraftResult;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class CraftResultEvent implements Event<CraftResultEvent> {

	private static final EventBus<CraftResultEvent> BUS = new EventBus<>(CraftResultEvent.class);
	private Account client;
	private CraftResult result;

	/**
	 * @param client
	 * @param result
	 */
	public CraftResultEvent(Account client, CraftResult result) {
		this.client = client;
		this.result = result;
	}

	/**
	 * @return the result
	 */
	public CraftResult getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(CraftResult result) {
		this.result = result;
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
	public EventBus<CraftResultEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
