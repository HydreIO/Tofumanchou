package fr.aresrpg.tofumanchou.domain.event.item;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.structures.job.Jobs;
import fr.aresrpg.tofumanchou.domain.data.Account;

/**
 * 
 * @since
 */
public class JobItemEquipEvent implements Event<JobItemEquipEvent> {

	private static final EventBus<JobItemEquipEvent> BUS = new EventBus<>(JobItemEquipEvent.class);
	private Account client;
	private Jobs job;

	/**
	 * @param client
	 * @param job
	 */
	public JobItemEquipEvent(Account client, Jobs job) {
		this.client = client;
		this.job = job;
	}

	/**
	 * @return the job
	 */
	public Jobs getJob() {
		return job;
	}

	/**
	 * @param job
	 *            the job to set
	 */
	public void setJob(Jobs job) {
		this.job = job;
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
	public EventBus<JobItemEquipEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
