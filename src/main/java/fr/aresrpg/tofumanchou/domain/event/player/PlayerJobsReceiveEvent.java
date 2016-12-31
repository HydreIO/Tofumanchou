package fr.aresrpg.tofumanchou.domain.event.player;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.structures.job.Job;
import fr.aresrpg.dofus.structures.job.Jobs;
import fr.aresrpg.tofumanchou.domain.data.Account;

import java.util.Arrays;

/**
 * 
 * @since
 */
public class PlayerJobsReceiveEvent implements Event<PlayerJobsReceiveEvent> {

	private static final EventBus<PlayerJobsReceiveEvent> BUS = new EventBus<>(PlayerJobsReceiveEvent.class);
	private Account client;
	private Jobs[] jobs;

	/**
	 * @param client
	 * @param jobs
	 */
	public PlayerJobsReceiveEvent(Account client, Jobs[] jobs) {
		this.client = client;
		this.jobs = jobs;
	}

	public PlayerJobsReceiveEvent(Account client, Job[] jobs) {
		this(client, Arrays.stream(jobs).map(Job::getType).toArray(Jobs[]::new));
	}

	/**
	 * @return the jobs
	 */
	public Jobs[] getJobs() {
		return jobs;
	}

	/**
	 * @param jobs
	 *            the jobs to set
	 */
	public void setJobs(Jobs[] jobs) {
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
	public EventBus<PlayerJobsReceiveEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
