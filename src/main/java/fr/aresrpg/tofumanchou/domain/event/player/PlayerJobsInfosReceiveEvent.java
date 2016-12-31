package fr.aresrpg.tofumanchou.domain.event.player;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.Job;

import java.util.Collection;

/**
 * 
 * @since
 */
public class PlayerJobsInfosReceiveEvent implements Event<PlayerJobsInfosReceiveEvent> {

	private static final EventBus<PlayerJobsInfosReceiveEvent> BUS = new EventBus<>(PlayerJobsInfosReceiveEvent.class);
	private Account client;
	private Collection<Job> jobs;

	/**
	 * @param client
	 * @param jobs
	 */
	public PlayerJobsInfosReceiveEvent(Account client, Collection<Job> jobs) {
		this.client = client;
		this.jobs = jobs;
	}

	/**
	 * @return the jobs
	 */
	public Collection<Job> getJobs() {
		return jobs;
	}

	/**
	 * @param jobs
	 *            the jobs to set
	 */
	public void setJobs(Collection<Job> jobs) {
		this.jobs = jobs;
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
	public EventBus<PlayerJobsInfosReceiveEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
