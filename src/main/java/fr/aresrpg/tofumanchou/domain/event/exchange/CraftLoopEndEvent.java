package fr.aresrpg.tofumanchou.domain.event.exchange;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.structures.CraftLoopEndResult;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class CraftLoopEndEvent implements Event<CraftLoopEndEvent> {

	private static final EventBus<CraftLoopEndEvent> BUS = new EventBus<>(CraftLoopEndEvent.class);
	private Account client;
	private CraftLoopEndResult result;

	/**
	 * @param client
	 * @param result
	 */
	public CraftLoopEndEvent(Account client, CraftLoopEndResult result) {
		this.client = client;
		this.result = result;
	}

	/**
	 * @return the result
	 */
	public CraftLoopEndResult getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(CraftLoopEndResult result) {
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
	public EventBus<CraftLoopEndEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
