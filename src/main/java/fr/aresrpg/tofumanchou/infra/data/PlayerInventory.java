package fr.aresrpg.tofumanchou.infra.data;

import fr.aresrpg.dofus.structures.EquipmentPosition;
import fr.aresrpg.dofus.structures.character.Character;
import fr.aresrpg.dofus.structures.item.ItemCategory;
import fr.aresrpg.dofus.util.StringJoiner;
import fr.aresrpg.tofumanchou.domain.data.inventory.Inventory;
import fr.aresrpg.tofumanchou.domain.data.item.Item;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 
 * @since
 */
public class PlayerInventory implements Inventory {

	private ConcurrentMap<Long, Item> contents = new ConcurrentHashMap<>(); // itemUid | item
	private int kamas;

	public String showContent() {
		StringJoiner joiner = new StringJoiner(",", "[", "]").add("Kamas: " + kamas);
		contents.values().forEach(i -> joiner.add(i.showInfos()));
		return joiner.toString();
	}

	public void replaceContent(List<fr.aresrpg.dofus.structures.item.Item> items) {
		contents.clear();
		items.stream().map(ManchouItem::fromProtocolItem).forEach(i -> contents.put(i.getUUID(), i));
	}

	public void addContent(Collection<fr.aresrpg.dofus.structures.item.Item> items) {
		items.stream().map(ManchouItem::fromProtocolItem).forEach(i -> contents.put(i.getUUID(), i));
	}

	public void addItems(Collection<Item> items) {
		items.forEach(i -> contents.put(i.getUUID(), i));
	}

	public void addKamas(int kamas) {
		if (kamas < 0) throw new IllegalArgumentException("Impossible d'ajouter un nombre négatif de kamas !");
		setKamas(getKamas() + kamas);
	}

	public void removeKamas(int kamas) {
		if (kamas < 0) throw new IllegalArgumentException("Impossible de retirer un nombre négatif de kamas !");
		int tot = getKamas() - kamas;
		if (tot < 0) throw new IllegalArgumentException("Il y a moins de " + kamas + " dans la banque !");
		setKamas(tot);
	}

	@Override
	public ConcurrentMap<Long, Item> getContents() {
		return contents;
	}

	public Item getItem(long itemUid) {
		return getContents().get(itemUid);
	}

	/**
	 * @return the kamas
	 */
	public int getKamas() {
		return kamas;
	}

	@Override
	public String toString() {
		return "Inventory [contents=" + contents + ", kamas=" + kamas + "]";
	}

	public void parseCharacter(Character c) {
		if (c.getItems() == null) return;
		Arrays.stream(c.getItems()).map(ManchouItem::fromProtocolItem).forEach(i -> getContents().put(i.getUUID(), i));
	}

	public Item getItemAtPos(EquipmentPosition pos) {
		Item i = null;
		for (Item e : getContents().values())
			if (e.getPosition() == pos.getPosition()) return i;
		return null;
	}

	public Item getHeaviestItem() {
		int pod = 0;
		Item item = null;
		for (Item i : getContents().values()) {
			if (!i.getCategory().isRessource()) continue;
			if (item == null) item = i;
			int p = i.getPods() * i.getAmount();
			if (p > pod) {
				pod = p;
				item = i;
			}
		}
		return item;
	}

	public Set<Item> getItemsByCategory(ItemCategory category) {
		Set<Item> items = new HashSet<>();
		for (Item i : getContents().values()) {
			if (i.getCategory() == category) items.add(i);
		}
		return items;
	}

	@Override
	public Set<Item> getItems(int itemTypeId) {
		Set<Item> ss = new HashSet<>();
		for (Item e : getContents().values())
			if (e.getTypeId() == itemTypeId) ss.add(e);
		return ss;
	}

	@Override
	public void setKamas(int kamas) {
		this.kamas = kamas;
	}

}
