package fr.aresrpg.tofumanchou.domain.event.player;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.Job;

/**
 * 
 * @since
 */
public class JobLevelUpEvent implements Event<JobLevelUpEvent> {

	private static final EventBus<JobLevelUpEvent> BUS = new EventBus<>(JobLevelUpEvent.class);
	private Account client;
	private Job job;
	private int lvl;

	/**
	 * @param client
	 * @param job
	 * @param lvl
	 */
	public JobLevelUpEvent(Account client, Job job, int lvl) {
		this.client = client;
		this.job = job;
		this.lvl = lvl;
	}

	/**
	 * @return the job
	 */
	public Job getJob() {
		return job;
	}

	/**
	 * @param job
	 *            the job to set
	 */
	public void setJob(Job job) {
		this.job = job;
	}

	/**
	 * @return the lvl
	 */
	public int getLvl() {
		return lvl;
	}

	/**
	 * @param lvl
	 *            the lvl to set
	 */
	public void setLvl(int lvl) {
		this.lvl = lvl;
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
	public EventBus<JobLevelUpEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

}
