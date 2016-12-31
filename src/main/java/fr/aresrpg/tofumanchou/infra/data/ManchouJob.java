package fr.aresrpg.tofumanchou.infra.data;

import fr.aresrpg.dofus.structures.job.JobInfo;
import fr.aresrpg.dofus.structures.job.Jobs;
import fr.aresrpg.tofumanchou.domain.data.Job;

/**
 * 
 * @since
 */
public class ManchouJob implements Job {

	private Jobs type;
	private int lvl;
	private int minXp;
	private int xp;
	private int maxXp;

	/**
	 * @param type
	 */
	public ManchouJob(Jobs type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj == this) return true;
		return obj instanceof ManchouJob && ((ManchouJob) obj).getType() == type;
	}

	public JobInfo serializeProtocol() {
		return new JobInfo(type, lvl, minXp, xp, maxXp);
	}

	/**
	 * @return the type
	 */
	public Jobs getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(Jobs type) {
		this.type = type;
	}

	/**
	 * @return the minXp
	 */
	public int getMinXp() {
		return minXp;
	}

	/**
	 * @param minXp
	 *            the minXp to set
	 */
	public void setMinXp(int minXp) {
		this.minXp = minXp;
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
	 * @return the xp
	 */
	public int getXp() {
		return xp;
	}

	/**
	 * @param xp
	 *            the xp to set
	 */
	public void setXp(int xp) {
		this.xp = xp;
	}

	/**
	 * @return the maxXp
	 */
	public int getMaxXp() {
		return maxXp;
	}

	/**
	 * @param maxXp
	 *            the maxXp to set
	 */
	public void setMaxXp(int maxXp) {
		this.maxXp = maxXp;
	}

	@Override
	public String toString() {
		return "DofusJob [type=" + type + ", lvl=" + lvl + ", minXp=" + minXp + ", xp=" + xp + ", maxXp=" + maxXp + "]";
	}

}
