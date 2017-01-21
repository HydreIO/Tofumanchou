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
import fr.aresrpg.dofus.structures.game.Effect;
import fr.aresrpg.dofus.util.*;
import fr.aresrpg.tofumanchou.domain.Manchou;
import fr.aresrpg.tofumanchou.domain.data.EffectsData.LangEffect;
import fr.aresrpg.tofumanchou.domain.data.enums.Element;
import fr.aresrpg.tofumanchou.infra.db.DbAccessor;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 
 * @since
 */
public class SpellData {

	private static final SpellData instance = new SpellData();
	private final Map<Integer, LangSpell> spells = new HashMap<>();

	private SpellData() {
	}

	public void init(boolean fromDb) throws IOException {
		if (fromDb) {
			LOGGER.info("Using mongodb..");
			initFromDb();
			return;
		}
		LOGGER.info("Using dofus server..");
		Map<String, Object> datas = Lang.getDatas("fr", "spells");
		List<LangSpell> deserialize = deserialize(datas);
		deserialize.forEach(l -> spells.put(l.getSpellid(), l));
	}

	/**
	 * @return the spells
	 */
	public Map<Integer, LangSpell> getSpells() {
		return spells;
	}

	private void initFromDb() throws IOException {
		DbAccessor<LangSpell> coll = DbAccessor.create(Manchou.getDatabase(), "spellsLang", LangSpell.class);
		Collection<LangSpell> collection = coll.get();
		if (collection.isEmpty()) publishToDb(collection);
		else {
			int count = (int) collection.count();
			for (LangSpell b : collection.find(null, count))
				spells.put(b.getSpellid(), b);
			LOGGER.info("[" + count + "] SpellsData loaded !");
		}
	}

	private void publishToDb(Collection<LangSpell> collection) throws IOException {
		init(false);
		AtomicInteger in = new AtomicInteger();
		spells.forEach((k, v) -> {
			collection.putOrUpdate(Filter.eq("spellid", k), v);
			LOGGER.debug("[" + in.incrementAndGet() + "] Publish to database | " + v);
		});
	}

	/**
	 * @return the instance
	 */
	public static SpellData getInstance() {
		return instance;
	}

	public static LangSpell get(int itemType) {
		return instance.spells.get(itemType);
	}

	public static void main(String[] args) throws IOException {
		EffectsData.getInstance().init(false);
		List<LangSpell> deserialize = deserialize(Lang.getDatas("fr", "spells"));
		System.out.println(deserialize.get(1));
	}

	public static List<LangSpell> deserialize(Map<String, Object> datas) {
		List<LangSpell> list = new ArrayList<>();
		for (Entry<String, Object> e : datas.entrySet()) {
			if (!(e.getValue() instanceof Map)) continue;
			int spellid = Integer.parseInt(e.getKey().substring(2));
			String name = "", desc = "";
			Object[] lvl1 = null, lvl2 = null, lvl3 = null, lvl4 = null, lvl5 = null, lvl6 = null;
			Map<String, Object> map = (Map) e.getValue();
			for (Entry<String, Object> en : map.entrySet()) {
				switch (en.getKey()) {
					case "n":
						name = String.valueOf(en.getValue());
						break;
					case "d":
						desc = String.valueOf(en.getValue());
						break;
					case "l1":
						lvl1 = (Object[]) en.getValue();
						break;
					case "l2":
						lvl2 = (Object[]) en.getValue();
						break;
					case "l3":
						lvl3 = (Object[]) en.getValue();
						break;
					case "l4":
						lvl4 = (Object[]) en.getValue();
						break;
					case "l5":
						lvl5 = (Object[]) en.getValue();
						break;
					case "l6":
						lvl6 = (Object[]) en.getValue();
						break;
					default:
						break;
				}
			}
			list.add(new LangSpell(spellid, name, desc, lvl1, lvl2, lvl3, lvl4, lvl5, lvl6));
		}
		return list;
	}

	public static List<Effect> getSpellEffects(Object[] effects, int spellid) {
		List<Effect> effs = new ArrayList<>();
		for (int i = 0; i < effects.length; i++) {
			Object[] datas = (Object[]) effects[i];
			int id = (int) datas[0];
			Object d1 = datas[1];
			Object d2 = datas[2];
			Object d3 = datas[3];
			Object d4 = datas[4];
			Object d5 = datas[5];
			Object d6 = datas.length < 7 ? null : datas[6];
			int di1 = d1 == null ? -1 : (int) d1;
			int di2 = d2 == null ? -1 : (int) d2;
			int di3 = d3 == null ? -1 : (int) d3;
			String di6 = d6 == null ? "" : (String) d6;
			int di4 = d4 == null ? -1 : (int) d4;
			effs.add(new Effect(id, di1, di2, di3, di6, di4, spellid));
		}
		return effs;
	}

	public static class LangSpellProperty {
		int lvl, paCost, rangeMin, rangeMax, CC, EC, classid, launchByTurn, launchByPlayerTurn, delayBetweenLaunch, minPlayerLvl;
		boolean lineOnly, lineOfSight, needFreeCell, poModifiable, ecEndTurn, summon, glyph, trap;
		List<Integer> requiredStates = new ArrayList<>(), forbiddenStates = new ArrayList<>();
		List<ZoneEffect> effectsNormal = new ArrayList<>(), effectsCritical = new ArrayList<>();

		/**
		 * @param lvl
		 * @param paCost
		 * @param rangeMin
		 * @param rangeMax
		 * @param cC
		 * @param eC
		 * @param classid
		 * @param launchByTurn
		 * @param launchByPlayerTurn
		 * @param delayBetweenLaunch
		 * @param minPlayerLvl
		 * @param lineOnly
		 * @param lineOfSight
		 * @param needFreeCell
		 * @param poModifiable
		 * @param ecEndTurn
		 * @param summon
		 * @param glyph
		 * @param trap
		 * @param requiredStates
		 * @param forbiddenStates
		 * @param effectsNormal
		 * @param effectsCritical
		 */
		public LangSpellProperty(int lvl, int paCost, int rangeMin, int rangeMax, int cC, int eC, int classid, int launchByTurn, int launchByPlayerTurn, int delayBetweenLaunch, int minPlayerLvl,
			boolean lineOnly, boolean lineOfSight, boolean needFreeCell, boolean poModifiable, boolean ecEndTurn, boolean summon, boolean glyph, boolean trap, List<Integer> requiredStates,
			List<Integer> forbiddenStates, List<ZoneEffect> effectsNormal, List<ZoneEffect> effectsCritical) {
			this.lvl = lvl;
			this.paCost = paCost;
			this.rangeMin = rangeMin;
			this.rangeMax = rangeMax;
			CC = cC;
			EC = eC;
			this.classid = classid;
			this.launchByTurn = launchByTurn;
			this.launchByPlayerTurn = launchByPlayerTurn;
			this.delayBetweenLaunch = delayBetweenLaunch;
			this.minPlayerLvl = minPlayerLvl;
			this.lineOnly = lineOnly;
			this.lineOfSight = lineOfSight;
			this.needFreeCell = needFreeCell;
			this.poModifiable = poModifiable;
			this.ecEndTurn = ecEndTurn;
			this.summon = summon;
			this.glyph = glyph;
			this.trap = trap;
			this.requiredStates = requiredStates;
			this.forbiddenStates = forbiddenStates;
			this.effectsNormal = effectsNormal;
			this.effectsCritical = effectsCritical;
		}

		/**
		 * @return the lvl
		 */
		public int getLvl() {
			return lvl;
		}

		/**
		 * @return the paCost
		 */
		public int getPaCost() {
			return paCost;
		}

		/**
		 * @return the rangeMin
		 */
		public int getRangeMin() {
			return rangeMin;
		}

		/**
		 * @return the rangeMax
		 */
		public int getRangeMax() {
			return rangeMax;
		}

		/**
		 * @return the cC
		 */
		public int getCC() {
			return CC;
		}

		/**
		 * @return the eC
		 */
		public int getEC() {
			return EC;
		}

		/**
		 * @return the classid
		 */
		public int getClassid() {
			return classid;
		}

		/**
		 * @return the launchByTurn
		 */
		public int getLaunchByTurn() {
			return launchByTurn;
		}

		/**
		 * @return the launchByPlayerTurn
		 */
		public int getLaunchByPlayerTurn() {
			return launchByPlayerTurn;
		}

		/**
		 * @return the delayBetweenLaunch
		 */
		public int getDelayBetweenLaunch() {
			return delayBetweenLaunch;
		}

		/**
		 * @return the minPlayerLvl
		 */
		public int getMinPlayerLvl() {
			return minPlayerLvl;
		}

		/**
		 * @return the lineOnly
		 */
		public boolean isLineOnly() {
			return lineOnly;
		}

		/**
		 * @return the lineOfSight
		 */
		public boolean isLineOfSight() {
			return lineOfSight;
		}

		/**
		 * @return the needFreeCell
		 */
		public boolean isNeedFreeCell() {
			return needFreeCell;
		}

		/**
		 * @return the poModifiable
		 */
		public boolean isPoModifiable() {
			return poModifiable;
		}

		/**
		 * @return the ecEndTurn
		 */
		public boolean isEcEndTurn() {
			return ecEndTurn;
		}

		/**
		 * @return the summon
		 */
		public boolean isSummon() {
			return summon;
		}

		/**
		 * @return the glyph
		 */
		public boolean isGlyph() {
			return glyph;
		}

		/**
		 * @return the trap
		 */
		public boolean isTrap() {
			return trap;
		}

		/**
		 * @return the requiredStates
		 */
		public List<Integer> getRequiredStates() {
			return requiredStates;
		}

		/**
		 * @return the forbiddenStates
		 */
		public List<Integer> getForbiddenStates() {
			return forbiddenStates;
		}

		/**
		 * @return the effectsNormal
		 */
		public List<ZoneEffect> getEffectsNormal() {
			return effectsNormal;
		}

		/**
		 * @return the effectsCritical
		 */
		public List<ZoneEffect> getEffectsCritical() {
			return effectsCritical;
		}

		@Override
		public String toString() {
			return "LangSpellProperty [lvl=" + lvl + ", paCost=" + paCost + ", rangeMin=" + rangeMin + ", rangeMax=" + rangeMax + ", CC=" + CC + ", EC=" + EC + ", classid=" + classid
					+ ", launchByTurn=" + launchByTurn + ", launchByPlayerTurn=" + launchByPlayerTurn + ", delayBetweenLaunch=" + delayBetweenLaunch + ", minPlayerLvl=" + minPlayerLvl + ", lineOnly="
					+ lineOnly + ", lineOfSight=" + lineOfSight + ", needFreeCell=" + needFreeCell + ", poModifiable=" + poModifiable + ", ecEndTurn=" + ecEndTurn + ", summon=" + summon + ", glyph="
					+ glyph + ", trap=" + trap + ", requiredStates=" + requiredStates + ", forbiddenStates=" + forbiddenStates + ", effectsNormal=" + effectsNormal + ", effectsCritical="
					+ effectsCritical + "]";
		}
	}

	public static class ZoneEffect {
		Effect effect;
		Element element;
		int shape, zone;

		/**
		 * @param effect
		 * @param element
		 * @param shape
		 * @param zone
		 */
		public ZoneEffect(Effect effect, Element element, int shape, int zone) {
			this.effect = effect;
			this.element = element;
			this.shape = shape;
			this.zone = zone;
		}

		/**
		 * @return the effect
		 */
		public Effect getEffect() {
			return effect;
		}

		/**
		 * @return the element
		 */
		public Element getElement() {
			return element;
		}

		/**
		 * @return the shape
		 */
		public int getShape() {
			return shape;
		}

		/**
		 * @return the zone
		 */
		public int getZone() {
			return zone;
		}

		@Override
		public String toString() {
			return "ZoneEffect [effect=" + effect + ", element=" + element + ", shape=" + shape + ", zone=" + zone + "]";
		}

	}

	public static class LangSpell {
		private int spellid;
		private String name;
		private String description;
		private List<LangSpellProperty> properties = new ArrayList<>();

		public LangSpell(int spellid, String name, String description, Object[] lvl1, Object[] lvl2, Object[] lvl3, Object[] lvl4, Object[] lvl5, Object[] lvl6) {
			this.spellid = spellid;
			this.name = name;
			this.description = description;
			if (lvl1 != null) properties.add(parse(1, lvl1));
			if (lvl2 != null) properties.add(parse(2, lvl2));
			if (lvl3 != null) properties.add(parse(3, lvl3));
			if (lvl4 != null) properties.add(parse(4, lvl4));
			if (lvl5 != null) properties.add(parse(5, lvl5));
			if (lvl6 != null) properties.add(parse(6, lvl6));
		}

		/**
		 * @return the spellid
		 */
		public int getSpellid() {
			return spellid;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}

		/**
		 * @return the properties
		 */
		public List<LangSpellProperty> getProperties() {
			return properties;
		}

		LangSpellProperty parse(int lvl, Object[] array) {
			List<Pair<Effect, Element>> normal = getElements(array, lvl, false);
			List<Pair<Effect, Element>> critical = getElements(array, lvl, true);
			return new LangSpellProperty(lvl, getPaCost(array), getRangeMin(array), getRangeMax(array), getCC(array), getEC(array), getClasseId(array), getLaunchCountByTurn(array),
					getLaunchCountByPlayerTurn(array), getDelayBetweenLaunch(array), getMinPlayerLvl(array), isLineOnly(array), isLineOfSight(array), needFreeCell(array), isPoModifiable(array),
					isECendTurn(array), isSummon(array), isGlyph(array), isTrap(array), getRequiredStates(array), getForbiddenStates(array), getEffectsNormalHitWithArea(array, normal),
					getEffectsCriticalHitWithArea(array, critical));
		}

		private List<Pair<Effect, Element>> getElements(Object[] o, int lvl, boolean critical) {
			List<Effect> effectsHit = critical ? getEffectsCriticalHit(o, lvl) : getEffectsNormalHit(o, lvl);
			List<Pair<Effect, Element>> elems = new ArrayList<>();
			for (Effect e : effectsHit) {
				LangEffect effect = EffectsData.getInstance().getEffect(e.getTypeId());
				elems.add(new Pair(e, effect.getElementType()));
			}
			return elems;
		}

		private List<ZoneEffect> getEffectsNormalHitWithArea(Object[] o, List<Pair<Effect, Element>> elems) {
			List<Pair<Integer, Integer>> effectZone = getEffectZone(o);
			List<ZoneEffect> zones = new ArrayList<>();
			for (int i = 0; i < elems.size(); i++) {
				Pair<Effect, Element> pair = elems.get(i);
				Effect effect = pair.getFirst();
				Element ele = pair.getSecond();
				Pair<Integer, Integer> aezone = effectZone.get(i);
				zones.add(new ZoneEffect(effect, ele, aezone.getFirst(), aezone.getSecond()));
			}
			return zones;
		}

		private List<ZoneEffect> getEffectsCriticalHitWithArea(Object[] o, List<Pair<Effect, Element>> elems) {
			List<Pair<Integer, Integer>> effectZone = getEffectZone(o);
			List<ZoneEffect> zones = new ArrayList<>();
			for (int i = 0; i < elems.size(); i++) {
				Pair<Effect, Element> pair = elems.get(i);
				Effect effect = pair.getFirst();
				Element ele = pair.getSecond();
				Pair<Integer, Integer> aezone = effectZone.get(i);
				zones.add(new ZoneEffect(effect, ele, aezone.getFirst(), aezone.getSecond()));
			}
			return zones;
		}

		private int getPaCost(Object[] o) {
			return (int) o[2];
		}

		private int getRangeMin(Object[] o) {
			return (int) o[3];
		}

		private int getRangeMax(Object[] o) {
			return (int) o[4];
		}

		private int getCC(Object[] o) {
			return (int) o[5];
		}

		private int getEC(Object[] o) {
			return (int) o[6];
		}

		private List<Effect> getEffectsNormalHit(Object[] o, int lvl) {
			if (o[0] == null || !o[0].getClass().isArray()) return new ArrayList<>();
			return getSpellEffects((Object[]) o[0], lvl);
		}

		private List<Effect> getEffectsCriticalHit(Object[] o, int lvl) {
			if (o[1] == null || !o[1].getClass().isArray()) return new ArrayList<>();
			return getSpellEffects((Object[]) o[1], lvl);
		}

		private List<Pair<Integer, Integer>> getEffectZone(Object[] o) {
			String shapesize = (String) o[15];
			char[] charArray = shapesize.toCharArray();
			List<Pair<Integer, Integer>> l = new ArrayList<>();
			for (int i = 0; i < charArray.length; i += 2) {
				int shape = Crypt.indexOfHash(charArray[i]);
				int size = Crypt.indexOfHash(charArray[i + 1]);
				l.add(new Pair(shape, size));
			}
			return l;
		}

		private List<Integer> getRequiredStates(Object[] o) {
			if (o[16] == null || ((Object[]) o[16]).length == 0) return new ArrayList<>();
			Object[] st = (Object[]) o[16];
			return Arrays.stream(st).map(obj -> (int) obj).collect(Collectors.toList());
		}

		private List<Integer> getForbiddenStates(Object[] o) {
			if (o[17] == null || ((Object[]) o[17]).length == 0) return new ArrayList<>();
			Object[] st = (Object[]) o[17];
			return Arrays.stream(st).map(obj -> (int) obj).collect(Collectors.toList());
		}

		private boolean isLineOnly(Object[] o) {
			return (boolean) o[7];
		}

		private boolean isLineOfSight(Object[] o) {
			return (boolean) o[8];
		}

		private boolean needFreeCell(Object[] o) {
			return (boolean) o[9];
		}

		private boolean isPoModifiable(Object[] o) {
			return (boolean) o[10];
		}

		private int getClasseId(Object[] o) {
			return (int) o[11];
		}

		private int getLaunchCountByTurn(Object[] o) {
			return (int) o[12];
		}

		private int getLaunchCountByPlayerTurn(Object[] o) {
			return (int) o[13];
		}

		private int getDelayBetweenLaunch(Object[] o) {
			return (int) o[14];
		}

		private int getMinPlayerLvl(Object[] o) {
			return (int) o[18];
		}

		private boolean isECendTurn(Object[] o) {
			return (boolean) o[19];
		}

		private boolean isSummon(Object[] lvl1) {
			for (Object o : (Object[]) lvl1[0]) {
				if (o == null || o instanceof Object[]) continue;
				if (o instanceof Number && (((Number) o).intValue() == 180 || ((Number) o).intValue() == 181)) return true;
			}
			return false;
		}

		private boolean isGlyph(Object[] lvl1) {
			for (Object o : (Object[]) lvl1[0]) {
				if (o == null || o instanceof Object[]) continue;
				if (o instanceof Number && ((Number) o).intValue() == 401) return true;
			}
			return false;
		}

		private boolean isTrap(Object[] lvl1) {
			for (Object o : (Object[]) lvl1[0]) {
				if (o == null || o instanceof Object[]) continue;
				if (o instanceof Number && ((Number) o).intValue() == 400) return true;
			}
			return false;
		}

		@Override
		public String toString() {
			return "LangSpell [spellid=" + spellid + ", name=" + name + ", description=" + description + ", properties=" + properties + "]";
		}

	}

}
