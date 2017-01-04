package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.protocol.game.actions.GameMoveAction.PathFragment;
import fr.aresrpg.tofumanchou.domain.data.Account;

import java.util.List;

/**
 * An event triggered when a server change his state
 * 
 * @since
 */
public class BotStartMoveEvent implements Event<BotStartMoveEvent> {

	private static final EventBus<BotStartMoveEvent> BUS = new EventBus<>(BotStartMoveEvent.class);
	private Account client;
	private List<PathFragment> path;

	/**
	 * @param client
	 * @param path
	 */
	public BotStartMoveEvent(Account client, List<PathFragment> path) {
		this.client = client;
		this.path = path;
	}

	/**
	 * @return the client
	 */
	public Account getClient() {
		return client;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(Account client) {
		this.client = client;
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

	@Override
	public EventBus<BotStartMoveEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

	@Override
	public String toString() {
		return "BotStartMoveEvent [client=" + client + ", path=" + path + "]";
	}
}
