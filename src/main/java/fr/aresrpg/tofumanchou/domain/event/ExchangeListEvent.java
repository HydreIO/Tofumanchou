package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.structures.Exchange;
import fr.aresrpg.dofus.structures.item.Item;
import fr.aresrpg.tofumanchou.domain.data.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @since
 */
public class ExchangeListEvent implements Event<ExchangeListEvent> {

	private static final EventBus<ExchangeListEvent> BUS = new EventBus<>(ExchangeListEvent.class);
	private Account client;
	private Exchange invType;
	private List<Item> items = new ArrayList<>();
	private int kamas = -1;

	/**
	 * @param client
	 * @param invType
	 * @param items
	 * @param kamas
	 */
	public ExchangeListEvent(Account client, Exchange invType, List<Item> items, int kamas) {
		this.client = client;
		this.invType = invType;
		this.items = items;
		this.kamas = kamas;
	}

	/**
	 * @return the invType
	 */
	public Exchange getInvType() {
		return invType;
	}

	/**
	 * @param invType
	 *            the invType to set
	 */
	public void setInvType(Exchange invType) {
		this.invType = invType;
	}

	/**
	 * @return the items
	 */
	public List<Item> getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(List<Item> items) {
		this.items = items;
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
	public EventBus<ExchangeListEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

	@Override
	public String toString() {
		return "ExchangeListEvent [client=" + client + ", invType=" + invType + ", items=" + items + ", kamas=" + kamas + "]";
	}

}
