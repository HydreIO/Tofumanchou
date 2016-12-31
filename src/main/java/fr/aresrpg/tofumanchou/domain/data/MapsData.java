package fr.aresrpg.tofumanchou.domain.data;

import fr.aresrpg.commons.domain.util.Pair;
import fr.aresrpg.dofus.util.Lang;

import java.awt.Point;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * @since
 */
public class MapsData {

	private static final MapsData instance = new MapsData();
	private final Map<Integer, Pair<Point, Integer>> coords = new HashMap<>(); // <mapid,<coords,subarea>>
	private final Map<Integer, String> area = new HashMap<>(); // <areaid,name>
	private final Map<Integer, Pair<String, Integer>> subarea = new HashMap<>(); // <subareaid,<name,areaid>>

	private MapsData() {
		coords.put(5867, new Pair<>(new Point(-27, -50), 44));
	}

	public void init() throws IOException {
		Map<String, Object> datas = Lang.getDatas("fr", "maps");
		for (Entry<String, Object> d : datas.entrySet()) {
			if (d.getKey().startsWith("MA.m.")) parseCoords(d);
			else if (d.getKey().startsWith("MA.a.")) parseArea(d);
			else if (d.getKey().startsWith("MA.sa.")) parseSubarea(d);

		}
	}

	private void parseCoords(Entry<String, Object> entry) {
		int id = Integer.parseInt(entry.getKey().substring(5));
		if (id == 1) return; // FIXME
		Map<String, Object> val = (Map<String, Object>) entry.getValue();
		int x = Integer.parseInt(String.valueOf(val.get("x")));
		int y = Integer.parseInt(String.valueOf(val.get("y")));
		int subarea = Integer.parseInt(String.valueOf(val.get("sa")));
		coords.put(id, new Pair<>(new Point(x, y), subarea));
	}

	private void parseArea(Entry<String, Object> entry) {
		int id = Integer.parseInt(entry.getKey().substring(5));
		Map<String, Object> val = (Map<String, Object>) entry.getValue();
		area.put(id, String.valueOf(val.get("n")));
	}

	private void parseSubarea(Entry<String, Object> entry) {
		int id = Integer.parseInt(entry.getKey().substring(6));
		Map<String, Object> val = (Map<String, Object>) entry.getValue();
		subarea.put(id, new Pair<>(String.valueOf(val.get("n")), Integer.parseInt(String.valueOf(val.get("a")))));
	}

	public static Point getCoords(int mapid) {
		return instance.coords.get(mapid).getFirst();
	}

	public static String getArea(int mapid) {
		return instance.area.get(instance.areaId(instance.subareaId(mapid)));
	}

	private int subareaId(int mapId) {
		return coords.get(mapId).getSecond();
	}

	private int areaId(int subareaId) {
		return subarea.get(subareaId).getSecond();
	}

	public static String getSubArea(int mapid) {
		return instance.subarea.get(instance.subareaId(mapid)).getFirst();
	}

	/**
	 * @return the instance
	 */
	public static MapsData getInstance() {
		return instance;
	}

	public static void main(String[] args) throws IOException {
		System.out.println(Lang.getLangVersion("fr"));
		Map<String, Object> datas = Lang.getDatas("fr", "maps");
		System.out.println(datas);
	}

}
