package fr.aresrpg.tofumanchou.domain.data;

import fr.aresrpg.dofus.structures.InfosMsgType;
import fr.aresrpg.dofus.util.Lang;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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

	public void init() throws IOException {
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
}
