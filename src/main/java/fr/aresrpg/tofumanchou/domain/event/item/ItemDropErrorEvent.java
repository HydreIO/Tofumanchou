package fr.aresrpg.tofumanchou.domain.event.item;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.protocol.item.server.ItemDropErrorPacket.DropResult;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class ItemDropErrorEvent implements Event<ItemDropErrorEvent> {

	private static final EventBus<ItemDropErrorEvent> BUS = new EventBus<>(ItemDropErrorEvent.class);
	private Account client;
	private DropResult result;

	/**
	 * @param client
	 * @param result
	 */
	public ItemDropErrorEvent(Account client, DropResult result) {
		this.client = client;
		this.result = result;
	}

	/**
	 * @return the result
	 */
	public DropResult getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(DropResult result) {
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
	public EventBus<ItemDropErrorEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

	@Override
	public String toString() {
		return "ItemDropErrorEvent [client=" + client + ", result=" + result + "]";
	}

}
