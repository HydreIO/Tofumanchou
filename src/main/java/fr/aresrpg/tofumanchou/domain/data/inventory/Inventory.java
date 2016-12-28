package fr.aresrpg.tofumanchou.domain.data.inventory;

import fr.aresrpg.tofumanchou.domain.data.item.Item;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * 
 * @since
 */
public interface Inventory {

	Item getItem(long itemUid);

	Set<Item> getItems(int itemTypeId);

	ConcurrentMap<Long, Item> getContents();

	int getKamas();

	void setKamas(int kamas);

	void addKamas(int kamas);

	void removeKamas(int kamas);

}
