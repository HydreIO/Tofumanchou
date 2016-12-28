/*******************************************************************************
 * BotFather (C) - Dofus 1.29
 * This class is part of an AresRPG Project.
 *
 * @author Sceat {@literal <sceat@aresrpg.fr>}
 * 
 *         Created 2016
 *******************************************************************************/
package fr.aresrpg.tofumanchou.domain.util.concurrent;

import static fr.aresrpg.tofumanchou.domain.Manchou.LOGGER;

import fr.aresrpg.commons.domain.concurrent.ThreadBuilder;
import fr.aresrpg.commons.domain.concurrent.ThreadPoolBuilder;
import fr.aresrpg.commons.domain.concurrent.ThreadPoolBuilder.PoolType;
import fr.aresrpg.commons.domain.util.schedule.Scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 
 * @since
 */
public class Executors {

	public static ExecutorService CACHED = new ThreadPoolBuilder(new ThreadBuilder()
			.handleException(LOGGER)
			.setName("ThreadManchou[%1$]"))
					.setType(PoolType.CACHED)
					.buildAsService();
	public static ScheduledExecutorService SCHEDULED = new ThreadPoolBuilder(new ThreadBuilder()
			.handleException(LOGGER)
			.setName("ScheduleManchou[%1$]"))
					.setType(PoolType.SCHEDULED)
					.setParallelism(40)
					.buildAsScheduled();
	public static Scheduler SCHEDULER = new Scheduler(SCHEDULED);
}
