package fr.aresrpg.tofumanchou.domain.data;

import fr.aresrpg.dofus.structures.Skills;
import fr.aresrpg.dofus.structures.job.Jobs;

/**
 * 
 * @since
 */
public interface Job {

	Jobs getType();

	int getLvl();

	int getXp();

	int getMinXp();

	int getMaxXp();

	default boolean hasLevelToUse(Skills s) {
		if (s.getMinLvlToUse() == 1) return true;
		return getLvl() >= s.getMinLvlToUse();
	}

}
