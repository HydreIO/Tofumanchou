package fr.aresrpg.tofumanchou.domain.util;

import static fr.aresrpg.tofumanchou.domain.Manchou.LOGGER;

import fr.aresrpg.dofus.protocol.game.actions.GameMoveAction.PathFragment;
import fr.aresrpg.dofus.structures.Orientation;
import fr.aresrpg.dofus.util.Maps;
import fr.aresrpg.dofus.util.Pathfinding;
import fr.aresrpg.dofus.util.Pathfinding.Node;
import fr.aresrpg.tofumanchou.infra.data.ManchouMap;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @since
 */
public class Functions {

	public static List<Node> getNodes(int originCell, List<PathFragment> frags, ManchouMap map) {
		List<Node> nodes = new ArrayList<>();
		Node last = getNode(originCell, map);
		nodes.add(last); // origin
		LOGGER.debug("Calculing reverse nodes.. " + originCell);
		for (PathFragment f : frags) {
			Orientation dir = f.getDirection();
			Node current = last;
			while (Maps.getIdRotated(current.getX(), current.getY(), map.getWidth(), map.getHeight()) != f.getCellId()) {
				current = Pathfinding.getNeighborNodeInDirection(current.getX(), current.getY(), dir);
				nodes.add(current);
			}
			last = current;
		}
		LOGGER.debug("Nodes calculed !");
		return nodes;
	}

	public static Node getNode(int id, ManchouMap map) {
		return new Node(Maps.getXRotated(id, map.getWidth(), map.getHeight()), Maps.getYRotated(id, map.getWidth(), map.getHeight()));
	}

}
