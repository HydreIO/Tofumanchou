package fr.aresrpg.tofumanchou.domain.data;

import static fr.aresrpg.tofumanchou.domain.Manchou.LOGGER;

import fr.aresrpg.commons.domain.database.Collection;
import fr.aresrpg.commons.domain.database.Filter;
import fr.aresrpg.commons.domain.util.Pair;
import fr.aresrpg.dofus.util.Lang;
import fr.aresrpg.tofumanchou.domain.Manchou;
import fr.aresrpg.tofumanchou.infra.db.DbAccessor;

import java.awt.Point;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @since
 */
public class MapsData {

	private static final MapsData instance = new MapsData();
	private final Map<Integer, MapDataBean> maps = new HashMap<>();

	private MapsData() {
	}

	public void init(boolean fromDb) throws IOException {
		try {
			if (fromDb) {
				LOGGER.info("Using mongodb..");
				initFromDb();
				return;
			}
			LOGGER.info("Using dofus server..");
			Map<Integer, Pair<Point, Integer>> coords = new HashMap<>(); // <mapid,<coords,subarea>>
			Map<Integer, String> area = new HashMap<>(); // <areaid,name>
			Map<Integer, Pair<String, Integer>> subarea = new HashMap<>(); // <subareaid,<name,areaid>>
			Map<String, Object> datas = Lang.getDatas("fr", "maps");
			coords.put(5867, new Pair<>(new Point(-27, -50), 44));
			for (Entry<String, Object> d : datas.entrySet()) {
				if (d.getKey().startsWith("MA.m.")) {
					int id = Integer.parseInt(d.getKey().substring(5));
					if (id == 1) continue; // FIXME
					Map<String, Object> val = (Map<String, Object>) d.getValue();
					int x = Integer.parseInt(String.valueOf(val.get("x")));
					int y = Integer.parseInt(String.valueOf(val.get("y")));
					int subarea1 = Integer.parseInt(String.valueOf(val.get("sa")));
					coords.put(id, new Pair<>(new Point(x, y), subarea1));
				} else if (d.getKey().startsWith("MA.a.")) {
					int id = Integer.parseInt(d.getKey().substring(5));
					Map<String, Object> val = (Map<String, Object>) d.getValue();
					area.put(id, String.valueOf(val.get("n")));
				} else if (d.getKey().startsWith("MA.sa.")) {
					int id = Integer.parseInt(d.getKey().substring(6));
					Map<String, Object> val = (Map<String, Object>) d.getValue();
					subarea.put(id, new Pair<>(String.valueOf(val.get("n")), Integer.parseInt(String.valueOf(val.get("a")))));
				}
			}
			push(coords, area, subarea);
		} catch (Exception e) {
			LOGGER.error(e);
		}
	}

	private void initFromDb() throws IOException {
		DbAccessor<MapDataBean> coll = DbAccessor.create(Manchou.getDatabase(), "mapslang", MapDataBean.class);
		Collection<MapDataBean> collection = coll.get();
		if (collection.isEmpty()) publishToDb(collection);
		else {
			int count = (int) collection.count();
			for (MapDataBean b : collection.find(null, count))
				maps.put(b.mapid, b);
			LOGGER.info("[" + count + "] Mapdata loaded !");
		}
	}

	private void publishToDb(Collection<MapDataBean> collection) throws IOException {
		init(false);
		AtomicInteger in = new AtomicInteger();
		maps.forEach((k, v) -> {
			collection.putOrUpdate(Filter.eq("mapid", k), v);
			LOGGER.debug("[" + in.incrementAndGet() + "] Publish to database | " + v);
		});
	}

	private void push(Map<Integer, Pair<Point, Integer>> coords, Map<Integer, String> area, Map<Integer, Pair<String, Integer>> subarea) {
		coords.forEach((k, v) -> {
			int mapid = k;
			int x = v.getFirst().x;
			int y = v.getFirst().y;
			int subareaId = v.getSecond();
			Pair<String, Integer> areaa = subarea.get(subareaId);
			int areaId = areaa.getSecond();
			String arean = area.get(areaId);
			String subarean = areaa.getFirst();
			maps.put(k, new MapDataBean(mapid, x, y, areaId, subareaId, arean, subarean));
		});
		LOGGER.debug("[" + coords.size() + "] Mapdata loaded !");
	}

	/**
	 * @return the maps
	 */
	public Map<Integer, MapDataBean> getMaps() {
		return maps;
	}

	public static MapDataBean getData(int mapid) {
		return instance.getMaps().get(mapid);
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

	public static class MapDataBean {
		int mapid;
		int x;
		int y;
		int areaId;
		int subareaId;
		String area;
		String subarea;

		public MapDataBean(int mapid, int x, int y, int areaId, int subareaId, String area, String subarea) {
			this.mapid = mapid;
			this.x = x;
			this.y = y;
			this.areaId = areaId;
			this.subareaId = subareaId;
			this.area = area;
			this.subarea = subarea;
		}

		/**
		 * @return the areaId
		 */
		public int getAreaId() {
			return areaId;
		}

		/**
		 * @param areaId
		 *            the areaId to set
		 */
		public void setAreaId(int areaId) {
			this.areaId = areaId;
		}

		/**
		 * @return the subareaId
		 */
		public int getSubareaId() {
			return subareaId;
		}

		/**
		 * @param subareaId
		 *            the subareaId to set
		 */
		public void setSubareaId(int subareaId) {
			this.subareaId = subareaId;
		}

		/**
		 * @return the mapid
		 */
		public int getMapid() {
			return mapid;
		}

		/**
		 * @param mapid
		 *            the mapid to set
		 */
		public void setMapid(int mapid) {
			this.mapid = mapid;
		}

		/**
		 * @return the x
		 */
		public int getX() {
			return x;
		}

		/**
		 * @param x
		 *            the x to set
		 */
		public void setX(int x) {
			this.x = x;
		}

		/**
		 * @return the y
		 */
		public int getY() {
			return y;
		}

		/**
		 * @param y
		 *            the y to set
		 */
		public void setY(int y) {
			this.y = y;
		}

		/**
		 * @return the area
		 */
		public String getArea() {
			return area;
		}

		/**
		 * @param area
		 *            the area to set
		 */
		public void setArea(String area) {
			this.area = area;
		}

		/**
		 * @return the subarea
		 */
		public String getSubarea() {
			return subarea;
		}

		/**
		 * @param subarea
		 *            the subarea to set
		 */
		public void setSubarea(String subarea) {
			this.subarea = subarea;
		}

		@Override
		public String toString() {
			return "MapDataDao [mapid=" + mapid + ", x=" + x + ", y=" + y + ", area=" + area + ", subarea=" + subarea + "]";
		}

	}

}
