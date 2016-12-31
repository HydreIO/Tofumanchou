package fr.aresrpg.tofumanchou.domain.event.player;

import fr.aresrpg.commons.domain.event.Event;
import fr.aresrpg.commons.domain.event.EventBus;
import fr.aresrpg.dofus.structures.Rank;
import fr.aresrpg.dofus.structures.game.Alignement;
import fr.aresrpg.dofus.structures.stat.Stat;
import fr.aresrpg.dofus.structures.stat.StatValue;
import fr.aresrpg.tofumanchou.domain.data.Account;

import java.util.Map;

/**
 * An event triggered when the server send a statistique update
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
	 * @param client
	 *            the client to set
	 */
	public void setClient(Account client) {
		this.client = client;
	}

	/**
	 * @param xp
	 *            the xp to set
	 */
	public void setXp(int xp) {
		this.xp = xp;
	}

	/**
	 * @param xpMin
	 *            the xpMin to set
	 */
	public void setXpMin(int xpMin) {
		this.xpMin = xpMin;
	}

	/**
	 * @param xpMax
	 *            the xpMax to set
	 */
	public void setXpMax(int xpMax) {
		this.xpMax = xpMax;
	}

	/**
	 * @param kamas
	 *            the kamas to set
	 */
	public void setKamas(int kamas) {
		this.kamas = kamas;
	}

	/**
	 * @param statsPoints
	 *            the statsPoints to set
	 */
	public void setStatsPoints(int statsPoints) {
		this.statsPoints = statsPoints;
	}

	/**
	 * @param spellsPoints
	 *            the spellsPoints to set
	 */
	public void setSpellsPoints(int spellsPoints) {
		this.spellsPoints = spellsPoints;
	}

	/**
	 * @param alignment
	 *            the alignment to set
	 */
	public void setAlignment(Alignement alignment) {
		this.alignment = alignment;
	}

	/**
	 * @param fakeAlignment
	 *            the fakeAlignment to set
	 */
	public void setFakeAlignment(Alignement fakeAlignment) {
		this.fakeAlignment = fakeAlignment;
	}

	/**
	 * @param rank
	 *            the rank to set
	 */
	public void setRank(Rank rank) {
		this.rank = rank;
	}

	/**
	 * @param life
	 *            the life to set
	 */
	public void setLife(int life) {
		this.life = life;
	}

	/**
	 * @param lifeMax
	 *            the lifeMax to set
	 */
	public void setLifeMax(int lifeMax) {
		this.lifeMax = lifeMax;
	}

	/**
	 * @param energy
	 *            the energy to set
	 */
	public void setEnergy(int energy) {
		this.energy = energy;
	}

	/**
	 * @param energyMax
	 *            the energyMax to set
	 */
	public void setEnergyMax(int energyMax) {
		this.energyMax = energyMax;
	}

	/**
	 * @param initiative
	 *            the initiative to set
	 */
	public void setInitiative(int initiative) {
		this.initiative = initiative;
	}

	/**
	 * @param prospection
	 *            the prospection to set
	 */
	public void setProspection(int prospection) {
		this.prospection = prospection;
	}

	/**
	 * @param stats
	 *            the stats to set
	 */
	public void setStats(Map<Stat, StatValue> stats) {
		this.stats = stats;
	}

	/**
	 * @param extradatas
	 *            the extradatas to set
	 */
	public void setExtradatas(String extradatas) {
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
