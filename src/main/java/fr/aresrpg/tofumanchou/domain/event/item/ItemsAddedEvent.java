package fr.aresrpg.tofumanchou.domain.event.item;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.item.Item;
import fr.aresrpg.tofumanchou.infra.data.ManchouAccount;
import fr.aresrpg.tofumanchou.infra.data.ManchouItem;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 
 * @since
 */
public class ItemsAddedEvent implements Event<ItemsAddedEvent> {

	private static final EventBus<ItemsAddedEvent> BUS = new EventBus<>(ItemsAddedEvent.class);
	private Account client;
	private Set<Item> items;

	/**
	 * @param client
	 * @param items
	 */
	public ItemsAddedEvent(Account client, Set<Item> items) {
		this.client = client;
		this.items = items;
	}

	public ItemsAddedEvent(ManchouAccount client, Set<ManchouItem> items) {
		this.client = client;
		this.items = items.stream().map(i -> i).collect(Collectors.toSet());
	}

	/**
	 * @return the items
	 */
	public Set<Item> getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(Set<Item> items) {
		this.items = items;
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
	public EventBus<ItemsAddedEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
