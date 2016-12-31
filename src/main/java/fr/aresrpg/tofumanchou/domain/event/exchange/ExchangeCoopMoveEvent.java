package fr.aresrpg.tofumanchou.domain.event.exchange;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.item.Item;

/**
 * 
 * @since
 */
public class ExchangeCoopMoveEvent implements Event<ExchangeCoopMoveEvent> {

	private static final EventBus<ExchangeCoopMoveEvent> BUS = new EventBus<>(ExchangeCoopMoveEvent.class);
	private Account client;
	private Item moved;
	private int kamas = -1;
	private boolean add;

	/**
	 * @param client
	 * @param moved
	 * @param kamas
	 * @param add
	 */
	public ExchangeCoopMoveEvent(Account client, Item moved, int kamas, boolean add) {
		this.client = client;
		this.moved = moved;
		this.kamas = kamas;
		this.add = add;
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
	public EventBus<ExchangeCoopMoveEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
