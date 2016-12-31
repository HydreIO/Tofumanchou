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

}
