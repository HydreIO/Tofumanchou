package fr.aresrpg.tofumanchou.domain.event.player;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.entity.npc.Npc;

/**
 * 
 * @since
 */
public class DialogCreateEvent implements Event<DialogCreateEvent> {

	private static final EventBus<DialogCreateEvent> BUS = new EventBus<>(DialogCreateEvent.class);
	private Account client;
	private Npc npc;

	/**
	 * @param client
	 * @param npc
	 */
	public DialogCreateEvent(Account client, Npc npc) {
		this.client = client;
		this.npc = npc;
	}

	/**
	 * @return the npc
	 */
	public Npc getNpc() {
		return npc;
	}

	/**
	 * @param npc
	 *            the npc to set
	 */
	public void setNpc(Npc npc) {
		this.npc = npc;
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
	public EventBus<DialogCreateEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
