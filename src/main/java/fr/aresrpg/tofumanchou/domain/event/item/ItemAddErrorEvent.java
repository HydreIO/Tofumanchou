package fr.aresrpg.tofumanchou.domain.event.item;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.protocol.item.server.ItemAddErrorPacket.AddResult;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class ItemAddErrorEvent implements Event<ItemAddErrorEvent> {

	private static final EventBus<ItemAddErrorEvent> BUS = new EventBus<>(ItemAddErrorEvent.class);
	private Account client;
	private AddResult result;

	/**
	 * @param client
	 * @param result
	 */
	public ItemAddErrorEvent(Account client, AddResult result) {
		this.client = client;
		this.result = result;
	}

	/**
	 * @return the result
	 */
	public AddResult getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(AddResult result) {
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
	public EventBus<ItemAddErrorEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
