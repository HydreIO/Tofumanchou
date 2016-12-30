package fr.aresrpg.tofumanchou.domain.event;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.structures.Rank;
import fr.aresrpg.dofus.structures.game.Alignement;
import fr.aresrpg.dofus.structures.stat.Stat;
import fr.aresrpg.dofus.structures.stat.StatValue;
import fr.aresrpg.tofumanchou.domain.data.Account;

import java.util.Map;

/**
 * 
 * @since
 */
public class PersoStatsEvent implements Event<PersoStatsEvent> {

	private static final EventBus<PersoStatsEvent> BUS = new EventBus<>(PersoStatsEvent.class);
	private Account client;
	private int xp;
	private int xpMin;
	private int xpMax;
	private int kamas;
	private int statsPoints;
	private int spellsPoints;
	private Alignement alignment;
	private Alignement fakeAlignment;
	private Rank rank;
	private int life;
	private int lifeMax;
	private int energy;
	private int energyMax;
	private int initiative;
	private int prospection;
	private Map<Stat, StatValue> stats;
	private String extradatas = "";

	/**
	 * @param client
	 * @param xp
	 * @param xpMin
	 * @param xpMax
	 * @param kamas
	 * @param statsPoints
	 * @param spellsPoints
	 * @param alignment
	 * @param fakeAlignment
	 * @param rank
	 * @param life
	 * @param lifeMax
	 * @param energy
	 * @param energyMax
	 * @param initiative
	 * @param prospection
	 * @param stats
	 * @param extradatas
	 */
	public PersoStatsEvent(Account client, int xp, int xpMin, int xpMax, int kamas, int statsPoints, int spellsPoints, Alignement alignment, Alignement fakeAlignment, Rank rank, int life, int lifeMax,
		int energy, int energyMax, int initiative, int prospection, Map<Stat, StatValue> stats, String extradatas) {
		this.client = client;
		this.xp = xp;
		this.xpMin = xpMin;
		this.xpMax = xpMax;
		this.kamas = kamas;
		this.statsPoints = statsPoints;
		this.spellsPoints = spellsPoints;
		this.alignment = alignment;
		this.fakeAlignment = fakeAlignment;
		this.rank = rank;
		this.life = life;
		this.lifeMax = lifeMax;
		this.energy = energy;
		this.energyMax = energyMax;
		this.initiative = initiative;
		this.prospection = prospection;
		this.stats = stats;
		this.extradatas = extradatas;
	}

	/**
	 * @return the client
	 */
	public Account getClient() {
		return client;
	}

	@Override
	public EventBus<PersoStatsEvent> getBus() {
		return BUS;
	}

	@Override
	public boolean isAsynchronous() {
		return true;
	}

	/**
	 * @return the xp
	 */
	public int getXp() {
		return xp;
	}

	/**
	 * @return the xpMin
	 */
	public int getXpMin() {
		return xpMin;
	}

	/**
	 * @return the xpMax
	 */
	public int getXpMax() {
		return xpMax;
	}

	/**
	 * @return the kamas
	 */
	public int getKamas() {
		return kamas;
	}

	/**
	 * @return the statsPoints
	 */
	public int getStatsPoints() {
		return statsPoints;
	}

	/**
	 * @return the spellsPoints
	 */
	public int getSpellsPoints() {
		return spellsPoints;
	}

	/**
	 * @return the alignment
	 */
	public Alignement getAlignment() {
		return alignment;
	}

	/**
	 * @return the fakeAlignment
	 */
	public Alignement getFakeAlignment() {
		return fakeAlignment;
	}

	/**
	 * @return the rank
	 */
	public Rank getRank() {
		return rank;
	}

	/**
	 * @return the life
	 */
	public int getLife() {
		return life;
	}

	/**
	 * @return the lifeMax
	 */
	public int getLifeMax() {
		return lifeMax;
	}

	/**
	 * @return the energy
	 */
	public int getEnergy() {
		return energy;
	}

	/**
	 * @return the energyMax
	 */
	public int getEnergyMax() {
		return energyMax;
	}

	/**
	 * @return the initiative
	 */
	public int getInitiative() {
		return initiative;
	}

	/**
	 * @return the prospection
	 */
	public int getProspection() {
		return prospection;
	}

	/**
	 * @return the stats
	 */
	public Map<Stat, StatValue> getStats() {
		return stats;
	}

	/**
	 * @return the extradatas
	 */
	public String getExtradatas() {
		return extradatas;
	}

	@Override
	public String toString() {
		return "PersoStatsEvent [client=" + client + ", xp=" + xp + ", xpMin=" + xpMin + ", xpMax=" + xpMax + ", kamas=" + kamas + ", statsPoints=" + statsPoints + ", spellsPoints=" + spellsPoints
				+ ", alignment=" + alignment + ", fakeAlignment=" + fakeAlignment + ", rank=" + rank + ", life=" + life + ", lifeMax=" + lifeMax + ", energy=" + energy + ", energyMax=" + energyMax
				+ ", initiative=" + initiative + ", prospection=" + prospection + ", stats=" + stats + ", extradatas=" + extradatas + "]";
	}

}
