package fr.aresrpg.tofumanchou.domain.data;

import static fr.aresrpg.tofumanchou.domain.Manchou.LOGGER;

import fr.aresrpg.commons.domain.database.Collection;
import fr.aresrpg.commons.domain.database.Filter;
import fr.aresrpg.dofus.structures.InfosMsgType;
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
public class InfosData {
	private static final InfosData instance = new InfosData();
	private final Map<Integer, String> info = new HashMap<>();
	private final Map<Integer, String> error = new HashMap<>();
	private final Map<Integer, String> pvp = new HashMap<>();

	private InfosData() {

	}

	public void init(boolean fromDb) throws IOException {
		if (fromDb) {
			LOGGER.info("Using mongodb..");
			initFromDb();
			return;
		}
		LOGGER.info("Using dofus server..");

		Map<String, Object> datas = Lang.getDatas("fr", "lang");
		for (Entry<String, Object> entry : datas.entrySet()) {
			String key = entry.getKey();
			if (key.startsWith("INFOS_")) info.put(Integer.parseInt(key.substring(6)), entry.getValue().toString());
			else if (key.startsWith("ERROR_")) {
				int id = 0;
				try {
					id = Integer.parseInt(key.substring(6));
				} catch (Exception e) {
					continue;
				}
				error.put(id, entry.getValue().toString());
			} else if (key.startsWith("PVP_")) {
				int id = 0;
				try {
					id = Integer.parseInt(key.substring(4));
				} catch (Exception e) {
					continue;
				}
				pvp.put(id, entry.getValue().toString());
			}
		}
	}

	private void initFromDb() throws IOException {
		DbAccessor<MessageBean> coll = DbAccessor.create(Manchou.getDatabase(), "infoslang", MessageBean.class);
		Collection<MessageBean> collection = coll.get();
		if (collection.isEmpty()) publishToDb(collection);
		else {
			int count = (int) collection.count();
			for (MessageBean b : collection.find(null, count)) {
				switch (InfosMsgType.valueOf(b.type)) {
					case INFOS:
						info.put(b.id, b.msg);
						break;
					case ERROR:
						error.put(b.id, b.msg);
						break;
					case PVP:
						pvp.put(b.id, b.msg);
						break;
					default:
						break;
				}
			}
			LOGGER.info("[" + count + "] InfosData loaded !");
		}
	}

	private void publishToDb(Collection<MessageBean> collection) throws IOException {
		init(false);
		AtomicInteger in = new AtomicInteger();
		info.forEach((k, v) -> {
			collection.putOrUpdate(Filter.and(Filter.eq("type", InfosMsgType.INFOS.name()), Filter.eq("id", k)), new MessageBean(InfosMsgType.INFOS.name(), k, v));
			LOGGER.debug("[" + in.incrementAndGet() + "] Publish to database | " + v);
		});
		error.forEach((k, v) -> {
			collection.putOrUpdate(Filter.and(Filter.eq("type", InfosMsgType.ERROR.name()), Filter.eq("id", k)), new MessageBean(InfosMsgType.ERROR.name(), k, v));
			LOGGER.debug("[" + in.incrementAndGet() + "] Publish to database | " + v);
		});
		pvp.forEach((k, v) -> {
			collection.putOrUpdate(Filter.and(Filter.eq("type", InfosMsgType.PVP.name()), Filter.eq("id", k)), new MessageBean(InfosMsgType.PVP.name(), k, v));
			LOGGER.debug("[" + in.incrementAndGet() + "] Publish to database | " + v);
		});
	}

	/**
	 * @return the instance
	 */
	public static InfosData getInstance() {
		return instance;
	}

	public static String getMessage(InfosMsgType type, int id) {
		switch (type) {
			case INFOS:
				return instance.info.get(id);
			case ERROR:
				return instance.error.get(id);
			case PVP:
				return instance.pvp.get(id);
		}
		throw new IllegalArgumentException("The type " + type + " is invalid");
	}

	public static class MessageBean {
		String type;
		int id;
		String msg;

		/**
		 * @param type
		 * @param id
		 * @param msg
		 */
		public MessageBean(String type, int id, String msg) {
			this.type = type;
			this.id = id;
			this.msg = msg;
		}

	}
}
