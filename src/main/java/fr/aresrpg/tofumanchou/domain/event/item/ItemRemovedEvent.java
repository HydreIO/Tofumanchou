package fr.aresrpg.tofumanchou.domain.event.item;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.item.Item;

/**
 * 
 * @since
 */
public class ItemRemovedEvent implements Event<ItemRemovedEvent> {

	private static final EventBus<ItemRemovedEvent> BUS = new EventBus<>(ItemRemovedEvent.class);
	private Account client;
	private Item item;

	/**
	 * @param client
	 * @param item
	 */
	public ItemRemovedEvent(Account client, Item item) {
		this.client = client;
		this.item = item;
	}

	/**
	 * @return the item
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * @param item
	 *            the item to set
	 */
	public void setItem(Item item) {
		this.item = item;
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
	public EventBus<ItemRemovedEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
