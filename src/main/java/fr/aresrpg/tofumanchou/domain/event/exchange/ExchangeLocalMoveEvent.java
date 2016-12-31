package fr.aresrpg.tofumanchou.domain.event.exchange;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class ExchangeLocalMoveEvent implements Event<ExchangeLocalMoveEvent> {

	private static final EventBus<ExchangeLocalMoveEvent> BUS = new EventBus<>(ExchangeLocalMoveEvent.class);
	private Account client;
	private int itemType;
	private int itemAmount;
	private int localKama;
	private boolean add;

	/**
	 * @param client
	 * @param itemType
	 * @param itemAmount
	 * @param localKama
	 * @param add
	 */
	public ExchangeLocalMoveEvent(Account client, int itemType, int itemAmount, int localKama, boolean add) {
		this.client = client;
		this.itemType = itemType;
		this.itemAmount = itemAmount;
		this.localKama = localKama;
		this.add = add;
	}

	/**
	 * @return the itemType
	 */
	public int getItemType() {
		return itemType;
	}

	/**
	 * @param itemType
	 *            the itemType to set
	 */
	public void setItemType(int itemType) {
		this.itemType = itemType;
	}

	/**
	 * @return the itemAmount
	 */
	public int getItemAmount() {
		return itemAmount;
	}

	/**
	 * @param itemAmount
	 *            the itemAmount to set
	 */
	public void setItemAmount(int itemAmount) {
		this.itemAmount = itemAmount;
	}

	/**
	 * @return the localKama
	 */
	public int getLocalKama() {
		return localKama;
	}

	/**
	 * @param localKama
	 *            the localKama to set
	 */
	public void setLocalKama(int localKama) {
		this.localKama = localKama;
	}

	/**
	 * @return the add
	 */
	public boolean isAdd() {
		return add;
	}

	/**
	 * @param add
	 *            the add to set
	 */
	public void setAdd(boolean add) {
		this.add = add;
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
	public EventBus<ExchangeLocalMoveEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
