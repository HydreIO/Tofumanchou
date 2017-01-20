package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.infra.data.ManchouPerso;

/**
 * An event triggered when a server change his state
 * 
 * @since
 */
public class AdminCommandEvent implements Event<AdminCommandEvent> {

	private static final EventBus<AdminCommandEvent> BUS = new EventBus<>(AdminCommandEvent.class);
	private Account client;
	private ManchouPerso perso;
	private String cmd;

	public AdminCommandEvent(Account client, ManchouPerso perso, String cmd) {
		this.client = client;
		this.perso = perso;
		this.cmd = cmd;
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
	 * @return the perso
	 */
	public ManchouPerso getPerso() {
		return perso;
	}

	/**
	 * @param perso
	 *            the perso to set
	 */
	public void setPerso(ManchouPerso perso) {
		this.perso = perso;
	}

	/**
	 * @return the cmd
	 */
	public String getCmd() {
		return cmd;
	}

	/**
	 * @param cmd
	 *            the cmd to set
	 */
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	@Override
	public EventBus<AdminCommandEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
