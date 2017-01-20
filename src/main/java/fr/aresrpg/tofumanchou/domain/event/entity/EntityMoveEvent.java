package fr.aresrpg.tofumanchou.domain.event.entity;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.protocol.game.actions.GameMoveAction.PathFragment;
import fr.aresrpg.dofus.util.Pathfinding.Node;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.entity.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @since
 */
public class EntityMoveEvent implements Event<EntityMoveEvent> {

	private static final EventBus<EntityMoveEvent> BUS = new EventBus<>(EntityMoveEvent.class);
	private Account client;
	private Entity entity;
	private long time;
	private List<PathFragment> path = new ArrayList<>();
	private List<Node> nodes = new ArrayList<>();

	public EntityMoveEvent(Account client, Entity entity, long time, List<PathFragment> path, List<Node> nodes) {
		this.client = client;
		this.entity = entity;
		this.time = time;
		this.path = path;
		this.nodes = nodes;
	}

	/**
	 * @return the time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * @return the nodes
	 */
	public List<Node> getNodes() {
		return nodes;
	}

	/**
	 * @param nodes
	 *            the nodes to set
	 */
	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	/**
	 * @return the entity
	 */
	public Entity getEntity() {
		return entity;
	}

	/**
	 * @param entity
	 *            the entity to set
	 */
	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	/**
	 * @return the path
	 */
	public List<PathFragment> getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(List<PathFragment> path) {
		this.path = path;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(Account client) {
		this.client = client;
	}

	/**
	 * @return the client
	 */
	public Account getClient() {
		return client;
	}

	@Override
	public EventBus<EntityMoveEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

	@Override
	public String toString() {
		return "EntityMoveEvent [client=" + client + ", entity=" + entity + ", path=" + path + "]";
	}

}
