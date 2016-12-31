package fr.aresrpg.tofumanchou.infra.data;

import fr.aresrpg.dofus.protocol.game.movement.MovementPlayer;
import fr.aresrpg.dofus.protocol.game.movement.MovementPlayer.PlayerInFight;
import fr.aresrpg.dofus.protocol.game.movement.MovementPlayer.PlayerOutsideFight;
import fr.aresrpg.dofus.structures.Orientation;
import fr.aresrpg.dofus.structures.Rank;
import fr.aresrpg.dofus.structures.game.Alignement;
import fr.aresrpg.dofus.structures.game.Effect;
import fr.aresrpg.dofus.structures.item.Accessory;
import fr.aresrpg.dofus.structures.server.Server;
import fr.aresrpg.dofus.structures.stat.Stat;
import fr.aresrpg.dofus.structures.stat.StatValue;
import fr.aresrpg.tofumanchou.domain.data.entity.EntityColor;
import fr.aresrpg.tofumanchou.domain.data.entity.player.Player;
import fr.aresrpg.tofumanchou.domain.data.enums.Classe;
import fr.aresrpg.tofumanchou.domain.data.enums.Genre;

import java.util.*;

/**
 * 
 * @since
 */
public class ManchouPlayerEntity implements Player {

	private long uuid;
	private Set<Effect> effects = new HashSet<>();
	private int cellId;
	private String pseudo;
	private Classe classe;
	private EntityColor colors;
	private Genre sex;
	private int level;
	private int life;
	private int lifeMax;
	private int initiative;
	private Alignement alignement;
	private Rank rank;
	private int prospection;
	private Map<Stat, StatValue> stats;
	private Accessory[] accessories;
	private Server server;
	private boolean merchant;
	private boolean dead;
	private int deathCount;
	private int lvlMax;
	private int guild;
	private int team;
	private int aura;
	private int emot;
	private int emotTimer;
	private String guildName;
	private String[] emblem;
	private String restrictions;
	private int scaleX;
	private int scaleY;
	private Orientation orientation;

	private int sprite;
	private boolean inFight;
	private String gfx;
	private int side;

	public MovementPlayer serialize() {
		MovementPlayer pl = new MovementPlayer(uuid, pseudo, sprite, cellId, scaleX, scaleY, orientation, sex.ordinal(), alignement, rank.getValue(), null, null);
		if (inFight) {
			PlayerInFight inf = new PlayerInFight(level, colors.getFirstColor(), colors.getSecondColor(), colors.getThirdColor(), accessories, life, getStat(Stat.PA).getTotal(),
					getStat(Stat.PM).getTotal(), null, team);
			int[] resi = new int[7];
			resi[0] = getStat(Stat.RES_NEUTRE_PER).getTotal();
			resi[1] = getStat(Stat.RES_TERRE_PER).getTotal();
			resi[2] = getStat(Stat.RES_FEU_PER).getTotal();
			resi[3] = getStat(Stat.RES_EAU_PER).getTotal();
			resi[4] = getStat(Stat.RES_AIR_PER).getTotal();
			resi[5] = getStat(Stat.ESQUIVE_PA).getTotal();
			resi[6] = getStat(Stat.ESQUIVE_PM).getTotal();
			inf.setResis(resi);
			pl.setPlayerInFight(inf);
		} else {
			PlayerOutsideFight pof = new PlayerOutsideFight(colors.getFirstColor(), colors.getSecondColor(), colors.getThirdColor(), accessories, aura, emot, emotTimer, guildName, emblem,
					restrictions);
			pl.setPlayerOutsideFight(pof);
		}
		return pl;
	}

	public static ManchouPlayerEntity parseMovement(MovementPlayer player) {
		ManchouPlayerEntity e = new ManchouPlayerEntity();
		e.uuid = player.getId();
		e.cellId = player.getCellId();
		e.pseudo = player.getPseudo();
		e.scaleX = player.getScaleX();
		e.scaleY = player.getScaleY();
		e.orientation = player.getOrientation();
		e.sex = Genre.valueOf(player.getSex());
		e.sprite = player.getSprite();
		e.alignement = player.getAlignement();
		e.rank = player.getRank() == -1 ? null : new Rank(player.getRank(), 0, 0, true);
		if (player.isFight()) {
			e.inFight = true;
			PlayerInFight inf = player.getPlayerInFight();
			e.level = inf.getLvl();
			e.colors = new ManchouColors(inf.getColor1(), inf.getColor2(), inf.getColor3());
			e.accessories = inf.getAccessories();
			e.life = inf.getLife();
			e.stats.put(Stat.PA, new StatValue(0, 0, 0, 0, inf.getPa()));
			e.stats.put(Stat.PM, new StatValue(0, 0, 0, 0, inf.getPm()));
			e.stats.put(Stat.RES_NEUTRE_PER, new StatValue(0, 0, 0, 0, inf.getResis()[0]));
			e.stats.put(Stat.RES_TERRE_PER, new StatValue(0, 0, 0, 0, inf.getResis()[1]));
			e.stats.put(Stat.RES_FEU_PER, new StatValue(0, 0, 0, 0, inf.getResis()[2]));
			e.stats.put(Stat.RES_EAU_PER, new StatValue(0, 0, 0, 0, inf.getResis()[3]));
			e.stats.put(Stat.RES_AIR_PER, new StatValue(0, 0, 0, 0, inf.getResis()[4]));
			e.stats.put(Stat.ESQUIVE_PA, new StatValue(0, 0, 0, 0, inf.getResis()[5]));
			e.stats.put(Stat.ESQUIVE_PM, new StatValue(0, 0, 0, 0, inf.getResis()[6]));
			e.team = inf.getTeam();
		} else {
			e.inFight = false;
			PlayerOutsideFight of = player.getPlayerOutsideFight();
			e.colors = new ManchouColors(of.getColor1(), of.getColor2(), of.getColor3());
			e.accessories = of.getAccessories();
			e.aura = of.getAura();
			e.emot = of.getEmot();
			e.emotTimer = of.getEmotTimer();
			e.guildName = of.getGuildname();
			e.emblem = of.getEmblem();
			e.restrictions = of.getRestrictions();
		}
		return e;
	}

	/**
	 * @return the uuid
	 */
	public long getUuid() {
		return uuid;
	}

	/**
	 * @param uuid
	 *            the uuid to set
	 */
	public void setUuid(long uuid) {
		this.uuid = uuid;
	}

	/**
	 * @return the sprite
	 */
	public int getSprite() {
		return sprite;
	}

	/**
	 * @param sprite
	 *            the sprite to set
	 */
	public void setSprite(int sprite) {
		this.sprite = sprite;
	}

	/**
	 * @return the inFight
	 */
	public boolean isInFight() {
		return inFight;
	}

	/**
	 * @param inFight
	 *            the inFight to set
	 */
	public void setInFight(boolean inFight) {
		this.inFight = inFight;
	}

	/**
	 * @return the gfx
	 */
	public String getGfx() {
		return gfx;
	}

	/**
	 * @param gfx
	 *            the gfx to set
	 */
	public void setGfx(String gfx) {
		this.gfx = gfx;
	}

	/**
	 * @return the side
	 */
	public int getSide() {
		return side;
	}

	/**
	 * @param side
	 *            the side to set
	 */
	public void setSide(int side) {
		this.side = side;
	}

	/**
	 * @return the restrictions
	 */
	public String getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(String restrictions) {
		this.restrictions = restrictions;
	}

	@Override
	public long getUUID() {
		return uuid;
	}

	@Override
	public Set<Effect> getEffects() {
		return effects;
	}

	@Override
	public int getCellId() {
		return cellId;
	}

	@Override
	public String getPseudo() {
		return pseudo;
	}

	@Override
	public Classe getClasse() {
		return classe;
	}

	@Override
	public EntityColor getColors() {
		return colors;
	}

	@Override
	public Genre getSex() {
		return sex;
	}

	@Override
	public int getLevel() {
		return level;
	}

	@Override
	public int getLife() {
		return life;
	}

	/**
	 * @param effects
	 *            the effects to set
	 */
	public void setEffects(Set<Effect> effects) {
		this.effects = effects;
	}

	/**
	 * @param pseudo
	 *            the pseudo to set
	 */
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	/**
	 * @param classe
	 *            the classe to set
	 */
	public void setClasse(Classe classe) {
		this.classe = classe;
	}

	/**
	 * @param colors
	 *            the colors to set
	 */
	public void setColors(EntityColor colors) {
		this.colors = colors;
	}

	/**
	 * @param sex
	 *            the sex to set
	 */
	public void setSex(Genre sex) {
		this.sex = sex;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @param lifeMax
	 *            the lifeMax to set
	 */
	public void setLifeMax(int lifeMax) {
		this.lifeMax = lifeMax;
	}

	/**
	 * @param initiative
	 *            the initiative to set
	 */
	public void setInitiative(int initiative) {
		this.initiative = initiative;
	}

	/**
	 * @param alignement
	 *            the alignement to set
	 */
	public void setAlignement(Alignement alignement) {
		this.alignement = alignement;
	}

	/**
	 * @param rank
	 *            the rank to set
	 */
	public void setRank(Rank rank) {
		this.rank = rank;
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
	 * @param server
	 *            the server to set
	 */
	public void setServer(Server server) {
		this.server = server;
	}

	/**
	 * @param merchant
	 *            the merchant to set
	 */
	public void setMerchant(boolean merchant) {
		this.merchant = merchant;
	}

	/**
	 * @param dead
	 *            the dead to set
	 */
	public void setDead(boolean dead) {
		this.dead = dead;
	}

	/**
	 * @param deathCount
	 *            the deathCount to set
	 */
	public void setDeathCount(int deathCount) {
		this.deathCount = deathCount;
	}

	/**
	 * @param lvlMax
	 *            the lvlMax to set
	 */
	public void setLvlMax(int lvlMax) {
		this.lvlMax = lvlMax;
	}

	/**
	 * @param guild
	 *            the guild to set
	 */
	public void setGuild(int guild) {
		this.guild = guild;
	}

	/**
	 * @param team
	 *            the team to set
	 */
	public void setTeam(int team) {
		this.team = team;
	}

	/**
	 * @param aura
	 *            the aura to set
	 */
	public void setAura(int aura) {
		this.aura = aura;
	}

	/**
	 * @param emot
	 *            the emot to set
	 */
	public void setEmot(int emot) {
		this.emot = emot;
	}

	/**
	 * @param emotTimer
	 *            the emotTimer to set
	 */
	public void setEmotTimer(int emotTimer) {
		this.emotTimer = emotTimer;
	}

	/**
	 * @param guildName
	 *            the guildName to set
	 */
	public void setGuildName(String guildName) {
		this.guildName = guildName;
	}

	/**
	 * @param emblem
	 *            the emblem to set
	 */
	public void setEmblem(String[] emblem) {
		this.emblem = emblem;
	}

	/**
	 * @param orientation
	 *            the orientation to set
	 */
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	@Override
	public int getLifeMax() {
		return lifeMax;
	}

	@Override
	public int getInitiative() {
		return initiative;
	}

	@Override
	public Alignement getAlignement() {
		return alignement;
	}

	@Override
	public Rank getRank() {
		return rank;
	}

	@Override
	public int getProspection() {
		return prospection;
	}

	@Override
	public Map<Stat, StatValue> getStats() {
		return stats;
	}

	@Override
	public StatValue getStat(Stat stat) {
		return getStats().get(stat);
	}

	@Override
	public Accessory[] getAccessories() {
		return accessories;
	}

	@Override
	public Server getServer() {
		return server;
	}

	@Override
	public boolean isMerchant() {
		return merchant;
	}

	@Override
	public boolean isDead() {
		return dead;
	}

	@Override
	public int getDeathCount() {
		return deathCount;
	}

	@Override
	public int getLvlMax() {
		return lvlMax;
	}

	@Override
	public int getGuild() {
		return guild;
	}

	@Override
	public int getTeam() {
		return team;
	}

	@Override
	public int getAura() {
		return aura;
	}

	@Override
	public int getEmot() {
		return emot;
	}

	@Override
	public int getEmotTimer() {
		return emotTimer;
	}

	@Override
	public String getGuildName() {
		return guildName;
	}

	@Override
	public String[] getEmblem() {
		return emblem;
	}

	@Override
	public String getRestriction() {
		return restrictions;
	}

	@Override
	public String toString() {
		return "ManchouPlayerEntity [uuid=" + uuid + ", effects=" + effects + ", cellId=" + cellId + ", pseudo=" + pseudo + ", classe=" + classe + ", colors=" + colors + ", sex=" + sex + ", level="
				+ level + ", life=" + life + ", lifeMax=" + lifeMax + ", initiative=" + initiative + ", alignement=" + alignement + ", rank=" + rank + ", prospection=" + prospection + ", stats="
				+ stats + ", accessories=" + Arrays.toString(accessories) + ", server=" + server + ", merchant=" + merchant + ", dead=" + dead + ", deathCount=" + deathCount + ", lvlMax=" + lvlMax
				+ ", guild=" + guild + ", team=" + team + ", aura=" + aura + ", emot=" + emot + ", emotTimer=" + emotTimer + ", guildName=" + guildName + ", emblem=" + Arrays.toString(emblem)
				+ ", restrictions=" + restrictions + "]";
	}

	@Override
	public int getScaleX() {
		return scaleX;
	}

	@Override
	public int getScaleY() {
		return scaleY;
	}

	/**
	 * @param scaleX
	 *            the scaleX to set
	 */
	public void setScaleX(int scaleX) {
		this.scaleX = scaleX;
	}

	/**
	 * @param scaleY
	 *            the scaleY to set
	 */
	public void setScaleY(int scaleY) {
		this.scaleY = scaleY;
	}

	@Override
	public Orientation getOrientation() {
		return orientation;
	}

	@Override
	public void setCellId(int cellid) {
		this.cellId = cellid;
	}

	@Override
	public void setLife(int life) {
		this.life = life;
	}

	/**
	 * @param accessories
	 *            the accessories to set
	 */
	public void setAccessories(Accessory[] accessories) {
		this.accessories = accessories;
	}

	@Override
	public int getPa() {
		return getStat(Stat.PA).getTotal();
	}

	@Override
	public void setPa(int pa) {
		getStat(Stat.PA).setTotal(pa);
	}

	@Override
	public int getPm() {
		return getStat(Stat.PM).getTotal();
	}

	@Override
	public void setPm(int pm) {
		getStat(Stat.PM).setTotal(pm);
	}

}
