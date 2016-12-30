package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class SpecializationEvent implements Event<SpecializationEvent> {

	private static final EventBus<SpecializationEvent> BUS = new EventBus<>(SpecializationEvent.class);
	private Account client;
	private int specialization;

	/**
	 * @param client
	 * @param specialization
	 */
	public SpecializationEvent(Account client, int specialization) {
		this.client = client;
		this.specialization = specialization;
	}

	/**
	 * @return the specialization
	 */
	public int getSpecialization() {
		return specialization;
	}

	/**
	 * @param specialization
	 *            the specialization to set
	 */
	public void setSpecialization(int specialization) {
		this.specialization = specialization;
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
	public EventBus<SpecializationEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

	@Override
	public String toString() {
		return "SpecializationEvent [client=" + client + ", specialization=" + specialization + "]";
	}

}
