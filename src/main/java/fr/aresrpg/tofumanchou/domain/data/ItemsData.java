/*******************************************************************************
 * BotFather (C) - Dofus 1.29 bot
 * This class is part of an AresRPG Project.
 *
 * @author Sceat {@literal <sceat@aresrpg.fr>}
 * 
 *         Created 2016
 *******************************************************************************/
package fr.aresrpg.tofumanchou.domain.data;

import static fr.aresrpg.tofumanchou.domain.Manchou.LOGGER;

import fr.aresrpg.commons.domain.database.Collection;
import fr.aresrpg.commons.domain.database.Filter;
import fr.aresrpg.dofus.structures.item.Item;
import fr.aresrpg.dofus.structures.item.ItemCategory;
import fr.aresrpg.dofus.util.Lang;
import fr.aresrpg.tofumanchou.domain.Manchou;
import fr.aresrpg.tofumanchou.infra.db.DbAccessor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @since
 */
public class ItemsData {

	private static final ItemsData instance = new ItemsData();
	private final Map<Integer, LangItem> items = new HashMap<>();

	private ItemsData() {
	}

	public void init(boolean fromDb) throws IOException {
		if (fromDb) {
			LOGGER.info("Using mongodb..");
			initFromDb();
			return;
		}
		LOGGER.info("Using dofus server..");
		Map<String, Object> datas = Lang.getDatas("fr", "items");
		for (Entry<String, Object> d : datas.entrySet()) {
			if (d.getKey().length() < 5 || d.getKey().charAt(4) == 'u' || !d.getKey().startsWith("I.u.")) continue;
			Map<String, Object> vv = (Map<String, Object>) d.getValue();
			int id = parseId(d.getKey());
			int cate = Integer.parseInt(vv.get("t").toString());
			String name = vv.get("n").toString();
			String desc = vv.get("d").toString();
			int pods = Integer.parseInt(vv.get("w").toString());
			items.put(id, new LangItem(id, cate, name, desc, pods));
		}
	}

	private void initFromDb() throws IOException {
		DbAccessor<LangItem> coll = DbAccessor.create(Manchou.getDatabase(), "itemslang", LangItem.class);
		Collection<LangItem> collection = coll.get();
		if (collection.isEmpty()) publishToDb(collection);
		else {
			int count = (int) collection.count();
			for (LangItem b : collection.find(null, count))
				items.put(b.getTypeId(), b);
			LOGGER.info("[" + count + "] ItemsData loaded !");
		}
	}

	private void publishToDb(Collection<LangItem> collection) throws IOException {
		init(false);
		AtomicInteger in = new AtomicInteger();
		items.forEach((k, v) -> {
			collection.putOrUpdate(Filter.eq("typeId", k), v);
			LOGGER.debug("[" + in.incrementAndGet() + "] Publish to database | " + v);
		});
	}

	public static boolean hasCategory(Item item, ItemCategory c) {
		LangItem l = get(item.getItemTypeId());
		return l.getCategory() == c;
	}

	/**
	 * @return the instance
	 */
	public static ItemsData getInstance() {
		return instance;
	}

	public static LangItem get(int itemType) {
		return instance.items.get(itemType);
	}

	private int parseId(String data) {
		return Integer.valueOf(data.split("\\.")[2]);
	}

	public static void main(String[] args) throws IOException {
		instance.init(false);
		System.out.println(instance.get(39).getDesc());
	}

	public static class LangItem {
		private int typeId;
		private int categoryId;
		private String name;
		private String desc;
		private int pod;

		/**
		 * @param typeId
		 * @param categoryId
		 * @param name
		 * @param desc
		 * @param pod
		 */
		public LangItem(int typeId, int categoryId, String name, String desc, int pod) {
			this.typeId = typeId;
			this.categoryId = categoryId;
			this.name = name;
			this.desc = desc;
			this.pod = pod;
		}

		public ItemCategory getCategory() {
			return ItemCategory.valueOf(categoryId);
		}

		/**
		 * @return the typeId
		 */
		public int getTypeId() {
			return typeId;
		}

		/**
		 * @param typeId
		 *            the typeId to set
		 */
		public void setTypeId(int typeId) {
			this.typeId = typeId;
		}

		/**
		 * @return the categoryId
		 */
		public int getCategoryId() {
			return categoryId;
		}

		/**
		 * @param categoryId
		 *            the categoryId to set
		 */
		public void setCategoryId(int categoryId) {
			this.categoryId = categoryId;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name
		 *            the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the desc
		 */
		public String getDesc() {
			return desc;
		}

		/**
		 * @param desc
		 *            the desc to set
		 */
		public void setDesc(String desc) {
			this.desc = desc;
		}

		/**
		 * @return the pod
		 */
		public int getPod() {
			return pod;
		}

		/**
		 * @param pod
		 *            the pod to set
		 */
		public void setPod(int pod) {
			this.pod = pod;
		}

		@Override
		public String toString() {
			return "LangItem [typeId=" + typeId + ", categoryId=" + categoryId + ", name=" + name + ", desc=" + desc + ", pod=" + pod + "]";
		}

	}

}
