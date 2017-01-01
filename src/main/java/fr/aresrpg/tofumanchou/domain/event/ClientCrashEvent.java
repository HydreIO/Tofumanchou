package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * An event triggered when a server change his state
 * 
 * @since
 */
public class ClientCrashEvent implements Event<ClientCrashEvent> {

	private static final EventBus<ClientCrashEvent> BUS = new EventBus<>(ClientCrashEvent.class);
	private Account client;
	private Exception exception;
	private boolean showException = true;
	private boolean shutdownClient = false;

	/**
	 * @param client
	 * @param exception
	 */
	public ClientCrashEvent(Account client, Exception exception) {
		this.client = client;
		this.exception = exception;
	}

	/**
	 * @return the showException
	 */
	public boolean isShowException() {
		return showException;
	}

	/**
	 * @param showException
	 *            the showException to set
	 */
	public void setShowException(boolean showException) {
		this.showException = showException;
	}

	/**
	 * @return the shutdownClient
	 */
	public boolean isShutdownClient() {
		return shutdownClient;
	}

	/**
	 * @param shutdownClient
	 *            the shutdownClient to set
	 */
	public void setShutdownClient(boolean shutdownClient) {
		this.shutdownClient = shutdownClient;
	}

	/**
	 * @return the exception
	 */
	public Exception getException() {
		return exception;
	}

	/**
	 * @param exception
	 *            the exception to set
	 */
	public void setException(Exception exception) {
		this.exception = exception;
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
	public EventBus<ClientCrashEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
