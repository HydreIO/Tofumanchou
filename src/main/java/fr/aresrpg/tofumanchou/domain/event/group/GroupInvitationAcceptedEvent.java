package fr.aresrpg.tofumanchou.domain.event.group;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class GroupInvitationAcceptedEvent implements Event<GroupInvitationAcceptedEvent> {

	private static final EventBus<GroupInvitationAcceptedEvent> BUS = new EventBus<>(GroupInvitationAcceptedEvent.class);
	private Account client;
	private String inviter, invited;

	/**
	 * @param client
	 * @param inviter
	 * @param invited
	 */
	public GroupInvitationAcceptedEvent(Account client, String inviter, String invited) {
		this.client = client;
		this.inviter = inviter;
		this.invited = invited;
	}

	/**
	 * @return the inviter
	 */
	public String getInviter() {
		return inviter;
	}

	/**
	 * @param inviter
	 *            the inviter to set
	 */
	public void setInviter(String inviter) {
		this.inviter = inviter;
	}

	/**
	 * @return the invited
	 */
	public String getInvited() {
		return invited;
	}

	/**
	 * @param invited
	 *            the invited to set
	 */
	public void setInvited(String invited) {
		this.invited = invited;
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
	public EventBus<GroupInvitationAcceptedEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
