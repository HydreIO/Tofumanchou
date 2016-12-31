package fr.aresrpg.tofumanchou.domain.data;

import fr.aresrpg.dofus.structures.job.Jobs;

/**
 * 
 * @since
 */
public interface Job {

	Jobs getType();

	int getXp();

	int getMinXp();

	int getMaxXp();

}
