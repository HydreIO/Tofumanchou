package fr.aresrpg.tofumanchou.domain.event.item;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.item.Item;

/**
 * 
 * @since
 */
public class ItemQuantityUpdateEvent implements Event<ItemQuantityUpdateEvent> {

	private static final EventBus<ItemQuantityUpdateEvent> BUS = new EventBus<>(ItemQuantityUpdateEvent.class);
	private Account client;
	private Item item;
	private int lastAmount, newAmount;

	/**
	 * @param client
	 * @param item
	 * @param lastAmount
	 * @param newAmount
	 */
	public ItemQuantityUpdateEvent(Account client, Item item, int lastAmount, int newAmount) {
		this.client = client;
		this.item = item;
		this.lastAmount = lastAmount;
		this.newAmount = newAmount;
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
	 * @return the lastAmount
	 */
	public int getLastAmount() {
		return lastAmount;
	}

	/**
	 * @param lastAmount
	 *            the lastAmount to set
	 */
	public void setLastAmount(int lastAmount) {
		this.lastAmount = lastAmount;
	}

	/**
	 * @return the newAmount
	 */
	public int getNewAmount() {
		return newAmount;
	}

	/**
	 * @param newAmount
	 *            the newAmount to set
	 */
	public void setNewAmount(int newAmount) {
		this.newAmount = newAmount;
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
	public EventBus<ItemQuantityUpdateEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
