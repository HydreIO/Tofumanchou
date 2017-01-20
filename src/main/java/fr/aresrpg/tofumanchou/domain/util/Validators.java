package fr.aresrpg.tofumanchou.domain.util;

import fr.aresrpg.dofus.util.Maps;
import fr.aresrpg.dofus.util.Pathfinding;
import fr.aresrpg.dofus.util.Pathfinding.Node;
import fr.aresrpg.dofus.util.Pathfinding.PathValidator;
import fr.aresrpg.tofumanchou.domain.data.entity.Entity;
import fr.aresrpg.tofumanchou.domain.data.entity.mob.MobGroup;
import fr.aresrpg.tofumanchou.domain.data.enums.AgressiveMobs;
import fr.aresrpg.tofumanchou.infra.data.ManchouCell;
import fr.aresrpg.tofumanchou.infra.data.ManchouMap;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * 
 * @since
 */
public class Validators {

	/**
	 * Return a pathvalidator who avoid all mobs on a map<br>
	 * Careful ! this method compute all cells on the map at invocation, so if the given map has no mob initialized yet then the path can fail.
	 * 
	 * @param map
	 *            the map
	 * @return the validator
	 */
	public static PathValidator avoidingMobs(ManchouMap map) {
		Set<Integer> dangerousCells = new HashSet<>();
		for (Entity e : map.getEntities().values()) {
			if (!(e instanceof MobGroup)) continue;
			MobGroup g = (MobGroup) e;
			int distanceAgro = AgressiveMobs.getDistanceAgro(g);
			if (distanceAgro == 0) continue;
			ManchouCell eCell = map.getCells()[e.getCellId()];
			Node[] nb = Pathfinding.getNeighborsWithoutDiagonals(new Node(eCell.getX(), eCell.getY()));
			Function<Node, Integer> nodeToId = n -> Maps.getIdRotated(n.getX(), n.getY(), map.getWidth(), map.getHeight());
			Function<Node, Node[]> nodeToNB = Pathfinding::getNeighborsWithoutDiagonals;
			dangerousCells.addAll(recursiveSearch(nb, distanceAgro, 0, nodeToId, nodeToNB));
		}
		return (x1, y1, x2, y2) -> !dangerousCells.contains(Maps.getIdRotated(x2, y2, map.getWidth(), map.getHeight()));
	}

	/**
	 * @return true if the dest coords are inside the big dofus map
	 */
	public static PathValidator insideDofusMap() {
		return (x1, y1, x2, y2) -> x2 > -71 && x2 < 38 && y2 < 51 && y2 > -107;
	}

	// internal use
	private static Set<Integer> recursiveSearch(Node[] node, int deep, int current, Function<Node, Integer> nodeToId, Function<Node, Node[]> NodeToNeighbors) {
		Set<Integer> set = new HashSet<>();
		for (Node n : node)
			set.add(nodeToId.apply(n));
		if (current == deep) return set;
		final int finalCurrent = ++current;
		for (Node n : node) {
			Set<Integer> recursiveSearch = recursiveSearch(NodeToNeighbors.apply(n), deep, finalCurrent, nodeToId, NodeToNeighbors);
			set.addAll(recursiveSearch);
		}
		return set;
	}

}
