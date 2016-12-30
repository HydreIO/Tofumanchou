package fr.aresrpg.tofumanchou.domain.event;

import static fr.aresrpg.tofumanchou.domain.Manchou.ERATZ;
import static fr.aresrpg.tofumanchou.domain.Manchou.HENUAL;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.structures.server.DofusServer;
import fr.aresrpg.dofus.structures.server.Server;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * An event triggered when a server change his state
 * 
 * @since
 */
public class ServerStateEvent implements Event<ServerStateEvent> {

	private static final EventBus<ServerStateEvent> BUS = new EventBus<>(ServerStateEvent.class);
	private Account client;
	private Server server;

	public ServerStateEvent(Account client, Server s) {
		this.client = client;
		this.server = s;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(Account client) {
		this.client = client;
	}

	/**
	 * @param server
	 *            the server to set
	 */
	public void setServer(Server server) {
		this.server = server;
	}

	/**
	 * @return the server
	 */
	public DofusServer getServer() {
		if (server == Server.ERATZ) return ERATZ;
		else if (server == Server.HENUAL) return HENUAL;
		throw new NullPointerException("Ankama has openned a new server ? lmao");
	}

	/**
	 * @return the client
	 */
	public Account getClient() {
		return client;
	}

	@Override
	public EventBus<ServerStateEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
