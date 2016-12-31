package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.structures.PartyErrorReason;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class GroupCreateErrorEvent implements Event<GroupCreateErrorEvent> {

	private static final EventBus<GroupCreateErrorEvent> BUS = new EventBus<>(GroupCreateErrorEvent.class);
	private Account client;
	private PartyErrorReason reason;

	/**
	 * @param client
	 * @param reason
	 */
	public GroupCreateErrorEvent(Account client, PartyErrorReason reason) {
		this.client = client;
		this.reason = reason;
	}

	/**
	 * @return the reason
	 */
	public PartyErrorReason getReason() {
		return reason;
	}

	/**
	 * @param reason
	 *            the reason to set
	 */
	public void setReason(PartyErrorReason reason) {
		this.reason = reason;
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
	public EventBus<GroupCreateErrorEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
