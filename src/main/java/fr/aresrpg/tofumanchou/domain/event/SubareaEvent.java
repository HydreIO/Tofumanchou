package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.structures.map.Subarea;
import fr.aresrpg.tofumanchou.domain.data.Account;

import java.util.Arrays;

/**
 * 
 * @since
 */
public class SubareaEvent implements Event<SubareaEvent> {

	private static final EventBus<SubareaEvent> BUS = new EventBus<>(SubareaEvent.class);
	private Account client;
	private Subarea[] subareas;

	/**
	 * @param client
	 * @param subareas
	 */
	public SubareaEvent(Account client, Subarea[] subareas) {
		this.client = client;
		this.subareas = subareas;
	}

	/**
	 * @return the subareas
	 */
	public Subarea[] getSubareas() {
		return subareas;
	}

	/**
	 * @param subareas
	 *            the subareas to set
	 */
	public void setSubareas(Subarea[] subareas) {
		this.subareas = subareas;
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
	public EventBus<SubareaEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

	@Override
	public String toString() {
		return "SubareaEvent [client=" + client + ", subareas=" + Arrays.toString(subareas) + "]";
	}

}
