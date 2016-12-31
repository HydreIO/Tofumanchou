package fr.aresrpg.tofumanchou.domain.event.item;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.item.Item;

/**
 * 
 * @since
 */
public class ItemMovedEvent implements Event<ItemMovedEvent> {

	private static final EventBus<ItemMovedEvent> BUS = new EventBus<>(ItemMovedEvent.class);
	private Account client;
	private Item item;
	private int position;

	/**
	 * @param client
	 * @param item
	 * @param position
	 */
	public ItemMovedEvent(Account client, Item item, int position) {
		this.client = client;
		this.item = item;
		this.position = position;
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
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
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
	public EventBus<ItemMovedEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
