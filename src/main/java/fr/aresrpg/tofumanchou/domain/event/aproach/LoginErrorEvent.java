package fr.aresrpg.tofumanchou.domain.event.aproach;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.protocol.account.server.AccountLoginErrPacket.Error;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * An event triggered when the login return an error
 * 
 * @since
 */
public class LoginErrorEvent implements Event<LoginErrorEvent> {

	private static final EventBus<LoginErrorEvent> BUS = new EventBus<>(LoginErrorEvent.class);
	private Account client;
	private Error error;
	private int minutes;
	private String version;

	/**
	 * @param client
	 * @param error
	 * @param minutes
	 * @param version
	 */
	public LoginErrorEvent(Account client, Error error, int minutes, String version) {
		this.client = client;
		this.error = error;
		this.minutes = minutes;
		this.version = version;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(Account client) {
		this.client = client;
	}

	/**
	 * @param error
	 *            the error to set
	 */
	public void setError(Error error) {
		this.error = error;
	}

	/**
	 * @param minutes
	 *            the minutes to set
	 */
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the error
	 */
	public Error getError() {
		return error;
	}

	/**
	 * @return the minutes
	 */
	public int getMinutes() {
		return minutes;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @return the client
	 */
	public Account getClient() {
		return client;
	}

	@Override
	public EventBus<LoginErrorEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return true;
	}

}
