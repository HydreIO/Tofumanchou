package fr.aresrpg.tofumanchou.domain.data;

import static fr.aresrpg.tofumanchou.domain.Manchou.LOGGER;

import fr.aresrpg.commons.domain.database.Collection;
import fr.aresrpg.commons.domain.database.Filter;
import fr.aresrpg.dofus.util.Lang;
import fr.aresrpg.tofumanchou.domain.Manchou;
import fr.aresrpg.tofumanchou.domain.data.enums.Element;
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
public class EffectsData {
	private static final EffectsData instance = new EffectsData();
	private Map<Integer, LangEffect> effects = new HashMap<>();

	private EffectsData() {

	}

	/**
	 * @return the effects
	 */
	public Map<Integer, LangEffect> getEffects() {
		return effects;
	}

	public LangEffect getEffect(int id) {
		return effects.get(id);
	}

	public void init(boolean fromDb) throws IOException {
		if (fromDb) {
			LOGGER.info("Using mongodb..");
			initFromDb();
			return;
		}
		LOGGER.info("Using dofus server..");
		Map<String, Object> datas = Lang.getDatas("fr", "effects");
		System.out.println(datas);
		for (Entry<String, Object> entry : datas.entrySet()) {
			if (!entry.getKey().startsWith("E.") || !(entry.getValue() instanceof Map)) continue;
			int id = Integer.parseInt(entry.getKey().substring(2));
			String desc = null;
			int carac = 0;
			String operator = null;
			String element = null;
			boolean hasDiceDmg = false;
			boolean showInToolTip = false;
			Map<String, Object> map = (Map) entry.getValue();
			for (Entry<String, Object> e : map.entrySet()) {
				switch (e.getKey()) {
					case "d":
						desc = String.valueOf(e.getValue());
						break;
					case "c":
						carac = (int) e.getValue();
						break;
					case "o":
						operator = String.valueOf(e.getValue());
						break;
					case "e":
						element = String.valueOf(e.getValue());
						break;
					case "j":
						hasDiceDmg = true;
						break;
					case "t":
						showInToolTip = true;
						break;
					default:
						break;
				}
			}
			effects.put(id, new LangEffect(id, desc, carac, operator, element, hasDiceDmg, showInToolTip));
		}
	}

	public static void main(String[] args) throws IOException {
		Map<String, Object> datas = Lang.getDatas("fr", "effects");
		for (Entry<String, Object> entry : datas.entrySet()) {
			System.out.println(entry);
		}
	}

	private void initFromDb() throws IOException {
		DbAccessor<LangEffect> coll = DbAccessor.create(Manchou.getDatabase(), "effectslang", LangEffect.class);
		Collection<LangEffect> collection = coll.get();
		if (collection.isEmpty()) publishToDb(collection);
		else {
			int count = (int) collection.count();
			for (LangEffect b : collection.find(null, count))
				effects.put(b.effid, b);
			LOGGER.info("[" + count + "] EffectsData loaded !");
		}
	}

	private void publishToDb(Collection<LangEffect> collection) throws IOException {
		init(false);
		AtomicInteger in = new AtomicInteger();
		effects.forEach((k, v) -> {
			collection.putOrUpdate(Filter.eq("effid", k), v);
			LOGGER.debug("[" + in.incrementAndGet() + "] Publish to database | " + v);
		});
	}

	/**
	 * @return the instance
	 */
	public static EffectsData getInstance() {
		return instance;
	}

	public static class LangEffect {
		int effid;
		String desc;
		int carac;
		String operator;
		String element;
		boolean hasDiceDmg; // utilisé quand on peut afficher les dgt en dés
		boolean showInToolTip;

		/**
		 * @param id
		 * @param desc
		 * @param carac
		 * @param operator
		 * @param element
		 * @param hasDiceDmg
		 * @param showInToolTip
		 */
		public LangEffect(int id, String desc, int carac, String operator, String element, boolean hasDiceDmg, boolean showInToolTip) {
			this.effid = id;
			this.desc = desc;
			this.carac = carac;
			this.operator = operator;
			this.element = element;
			this.hasDiceDmg = hasDiceDmg;
			this.showInToolTip = showInToolTip;
		}

		public Element getElementType() {
			if (element == null) return null;
			return Element.valueOf(element.charAt(0));
		}

		/**
		 * @return the effid
		 */
		public int getEffid() {
			return effid;
		}

		/**
		 * @return the desc
		 */
		public String getDesc() {
			return desc;
		}

		/**
		 * @return the carac
		 */
		public int getCarac() {
			return carac;
		}

		/**
		 * @return the operator
		 */
		public String getOperator() {
			return operator;
		}

		/**
		 * @return the element
		 */
		public String getElement() {
			return element;
		}

		/**
		 * @return the hasDiceDmg
		 */
		public boolean isHasDiceDmg() {
			return hasDiceDmg;
		}

		/**
		 * @return the showInToolTip
		 */
		public boolean isShowInToolTip() {
			return showInToolTip;
		}

		@Override
		public String toString() {
			return "LangEffect [id=" + effid + ", desc=" + desc + ", carac=" + carac + ", operator=" + operator + ", element=" + element + ", hasDiceDmg=" + hasDiceDmg + ", showInToolTip="
					+ showInToolTip + "]";
		}

	}
}
