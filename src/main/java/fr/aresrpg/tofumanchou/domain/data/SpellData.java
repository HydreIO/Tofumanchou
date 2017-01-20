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
import fr.aresrpg.dofus.util.Lang;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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

	}

	private void initFromDb() throws IOException {
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
		System.out.println(Lang.getDatas("fr", "Spells"));
	}

	public static class LangSpell {
		private int spellid;
		private String name;
		private String description;
		private Object[][] lvl1, lvl2, lvl3, lvl4, lvl5, lvl6;
		private LangSpellProperties[] properties = new LangSpellProperties[5];

		public LangSpell() {
			Arrays.fill(properties, new LangSpellProperties());
		}

		private int getPaCost(int lvl) {
			return (int) getSpellLvlText(2, lvl);
		}

		private int getRangeMin(int lvl) {
			return (int) getSpellLvlText(3, lvl);
		}

		private int getRangeMax(int lvl) {
			return (int) getSpellLvlText(4, lvl);
		}

		private int getCC(int lvl) {
			return (int) getSpellLvlText(5, lvl);
		}

		private int getEC(int lvl) {
			return (int) getSpellLvlText(6, lvl);
		}
		
		private boolean isLineOnly(int lvl) {
			return (boolean) getSpellLvlText(7, lvl);
		}
		
		private boolean isLineOfSight(int lvl) {
			return (boolean) getSpellLvlText(8, lvl);
		}
		
		private boolean needFreeCell(int lvl) {
			return (boolean) getSpellLvlText(9, lvl);
		}
		
		private boolean isPoModifiable(int lvl) {
			return (boolean) getSpellLvlText(10, lvl);
		}
		
		private int getClasseId(int lvl) {
			return (int) getSpellLvlText(11, lvl);
		}
		
		private int getLaunchCountByTurn(int lvl) {
			return (int) getSpellLvlText(12, lvl);
		}
		
		private int getLaunchCountByPlayerTurn(int lvl) {
			return (int) getSpellLvlText(13, lvl);
		}
		
		private int getDelayBetweenLaunch(int lvl) {
			return (int) getSpellLvlText(14, lvl);
		}

		private Object getSpellLvlText(int index, int lvl) {
			switch (lvl) {
				case 1:
					return lvl1[index];
				case 2:
					return lvl2[index];
				case 3:
					return lvl3[index];
				case 4:
					return lvl4[index];
				case 5:
					return lvl5[index];
				case 6:
					return lvl6[index];
				default:
					throw new IllegalArgumentException("the lvl " + lvl + " is invalid");
			}
		}

		private boolean isGlyph() {
			for (Object o : lvl1[0]) {
				if (o == null || o instanceof Object[]) continue;
				if (o instanceof Number && ((Number) o).intValue() == 401) return true;
			}
			return false;
		}

		private boolean isTrap() {
			for (Object o : lvl1[0]) {
				if (o == null || o instanceof Object[]) continue;
				if (o instanceof Number && ((Number) o).intValue() == 400) return true;
			}
			return false;
		}

	}

	public static class LangSpellProperties {
		private int rangeMin;
		private int paCost;
		private boolean glyph;
		private int maxLevel;
		private Object forbiddenStates;
		private String descNormalHit;
		private boolean canBoostRange;
		private boolean ECendTurn;
		private Object effectsCCwithArea;
		private Object elements;
		private int lvl;
		private int classID;
		private boolean lineOnly;
		private int CCactuel;
		private boolean valid;
		private Object requiredStates;
		private int animId;
		private int rangeMax;
		private int currentLaunchByTurn;
		private boolean summonSpell;
		private int icon;
		private boolean lineOfSight;
		private Object effectZones;
		private int normalMinPlayerLvl;
		private int id;
		private Object effectsNormalHit;
		private int CC;
		private String descCC;
		private int EC;
		private String rangeStr;
		private Object effectsNormalHitWithArea;
		private boolean needStates;
		private int maxLaunchByTurn;
		private String desc;
		private boolean trapSpell;
		private int delayBetweenLaunch;
		private String name;
		private boolean needFreeCell;
		private int minPlayerLvl;
		private Object effectsCriticalHit;
	}

}
