package fr.aresrpg.tofumanchou.domain.event.exchange;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.item.Item;

/**
 * 
 * @since
 */
public class ExchangeDistantMoveEvent implements Event<ExchangeDistantMoveEvent> {

	private static final EventBus<ExchangeDistantMoveEvent> BUS = new EventBus<>(ExchangeDistantMoveEvent.class);
	private Account client;
	private Item moved;
	private boolean add;
	private int remainingHours = -1;
	private int kamas = -1;

	/**
	 * @param client
	 * @param moved
	 * @param add
	 * @param remainingHours
	 * @param kamas
	 */
	public ExchangeDistantMoveEvent(Account client, Item moved, boolean add, int remainingHours, int kamas) {
		this.client = client;
		this.moved = moved;
		this.add = add;
		this.remainingHours = remainingHours;
		this.kamas = kamas;
	}

	/**
	 * @return the moved
	 */
	public Item getMoved() {
		return moved;
	}

	/**
	 * @param moved
	 *            the moved to set
	 */
	public void setMoved(Item moved) {
		this.moved = moved;
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
	 * @return the remainingHours
	 */
	public int getRemainingHours() {
		return remainingHours;
	}

	/**
	 * @param remainingHours
	 *            the remainingHours to set
	 */
	public void setRemainingHours(int remainingHours) {
		this.remainingHours = remainingHours;
	}

	/**
	 * @return the kamas
	 */
	public int getKamas() {
		return kamas;
	}

	/**
	 * @param kamas
	 *            the kamas to set
	 */
	public void setKamas(int kamas) {
		this.kamas = kamas;
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
	public EventBus<ExchangeDistantMoveEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
