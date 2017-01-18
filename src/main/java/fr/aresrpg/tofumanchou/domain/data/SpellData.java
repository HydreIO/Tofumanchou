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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
	}

	private void initFromDb() throws IOException {
	}

	private void publishToDb(Collection<LangSpell> collection) throws IOException {
		init(false);
		AtomicInteger in = new AtomicInteger();
		spells.forEach((k, v) -> {
			collection.putOrUpdate(Filter.eq("typeId", k), v);
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

	private int parseId(String data) {
		return Integer.valueOf(data.split("\\.")[2]);
	}

	public static void main(String[] args) throws IOException {
		instance.init(false);
	}

	public static class LangSpell {
		private int id;
		private String name;
		private String description;
		private Object[][] lvl1, lvl2, lvl3, lvl4, lvl5, lvl6;

	}

}
