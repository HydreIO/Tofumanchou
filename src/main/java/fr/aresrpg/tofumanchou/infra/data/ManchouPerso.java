package fr.aresrpg.tofumanchou.infra.data;

import fr.aresrpg.dofus.protocol.DofusConnection;
import fr.aresrpg.dofus.protocol.ProtocolRegistry.Bound;
import fr.aresrpg.dofus.structures.*;
import fr.aresrpg.dofus.structures.game.Alignement;
import fr.aresrpg.dofus.structures.item.Accessory;
import fr.aresrpg.dofus.structures.server.Server;
import fr.aresrpg.dofus.structures.stat.Stat;
import fr.aresrpg.dofus.structures.stat.StatValue;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.entity.Entity;
import fr.aresrpg.tofumanchou.domain.data.entity.EntityColor;
import fr.aresrpg.tofumanchou.domain.data.entity.player.Perso;
import fr.aresrpg.tofumanchou.domain.data.enums.Classe;
import fr.aresrpg.tofumanchou.domain.data.enums.Genre;
import fr.aresrpg.tofumanchou.domain.data.inventory.Inventory;
import fr.aresrpg.tofumanchou.domain.util.concurrent.Executors;
import fr.aresrpg.tofumanchou.infra.io.BaseServerPacketHandler;
import fr.aresrpg.tofumanchou.infra.io.ManchouBridge;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.*;

/**
 * 
 * @since
 */
public class ManchouPerso implements Perso {

	private ManchouAccount account;
	private long uuid;
	private Server server;
	private String pseudo;
	private int lvl;
	private int lvlMax;
	private Classe classe;
	private EntityColor colors;
	private Genre genre;
	private int life;
	private int lifeMax;
	private int initiative;
	private Alignement alignement;
	private Rank rank;
	private int prospection;
	private Map<Stat, StatValue> stats;
	private Accessory[] accessories;
	private boolean merchant;
	private boolean dead;
	private int deathCount;
	private int guild;
	private int team;
	private int aura;
	private int emot;
	private int emotTimer;
	private String guildName;
	private String[] emblem;
	private PlayerRestriction restrictions;
	private Inventory inventory;
	private int xp;
	private int xpLow;
	private int xpHight;
	private int statsPoints;
	private int spellsPoints;
	private int energy;
	private int energyMax;
	private List<String> offlineFriends = new ArrayList<>();
	private List<Friend> onlineFriends = new ArrayList<>();
	private Map<Chat, Boolean> channels = new HashMap<>();

	public ManchouPerso(Account account, String pseudo, Server server) {
		this.account = (ManchouAccount) account;
		this.pseudo = pseudo;
		this.server = server;
	}

	@Override
	public void connect() {
		account.getLogger().info("Connection de " + pseudo + "..");
		try {
			SocketChannel channel = SocketChannel.open(ManchouBridge.SERVER_ADRESS);
			this.account.setPerso(this);
			BaseServerPacketHandler han = new BaseServerPacketHandler(account);
			DofusConnection<SocketChannel> dofusConnection = new DofusConnection<>(getPseudo(), channel, han, Bound.SERVER);
			account.setConnection(dofusConnection);
			Executors.CACHED.execute(() -> {
				try {
					dofusConnection.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e) {

		}
	}

	@Override
	public StatValue getStat(Stat stat) {
		return stats.get(stat);
	}

	public void setColors(int color1, int color2, int color3) {
		this.colors = new ManchouColors(color1, color2, color3);
	}

	public void setAccessories(int[] accessories) {
		this.accessories = Arrays.stream(accessories).mapToObj(i -> new Accessory(-1, i, -1)).toArray(Accessory[]::new);
	}

	/**
	 * @return the account
	 */
	public ManchouAccount getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            the account to set
	 */
	public void setAccount(ManchouAccount account) {
		this.account = account;
	}

	/**
	 * @param uuid
	 *            the uuid to set
	 */
	public void setUuid(long uuid) {
		this.uuid = uuid;
	}

	/**
	 * @return the server
	 */
	public Server getServer() {
		return server;
	}

	/**
	 * @param server
	 *            the server to set
	 */
	public void setServer(Server server) {
		this.server = server;
	}

	/**
	 * @return the pseudo
	 */
	public String getPseudo() {
		return pseudo;
	}

	/**
	 * @param pseudo
	 *            the pseudo to set
	 */
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	/**
	 * @param lvl
	 *            the lvl to set
	 */
	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

	/**
	 * @return the lvlMax
	 */
	public int getLvlMax() {
		return lvlMax;
	}

	/**
	 * @param lvlMax
	 *            the lvlMax to set
	 */
	public void setLvlMax(int lvlMax) {
		this.lvlMax = lvlMax;
	}

	/**
	 * @return the classe
	 */
	public Classe getClasse() {
		return classe;
	}

	/**
	 * @param classe
	 *            the classe to set
	 */
	public void setClasse(Classe classe) {
		this.classe = classe;
	}

	/**
	 * @return the colors
	 */
	public EntityColor getColors() {
		return colors;
	}

	/**
	 * @param colors
	 *            the colors to set
	 */
	public void setColors(EntityColor colors) {
		this.colors = colors;
	}

	/**
	 * @param genre
	 *            the genre to set
	 */
	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	/**
	 * @return the life
	 */
	public int getLife() {
		return life;
	}

	/**
	 * @param life
	 *            the life to set
	 */
	public void setLife(int life) {
		this.life = life;
	}

	/**
	 * @return the lifeMax
	 */
	public int getLifeMax() {
		return lifeMax;
	}

	/**
	 * @param lifeMax
	 *            the lifeMax to set
	 */
	public void setLifeMax(int lifeMax) {
		this.lifeMax = lifeMax;
	}

	/**
	 * @return the initiative
	 */
	public int getInitiative() {
		return initiative;
	}

	/**
	 * @param initiative
	 *            the initiative to set
	 */
	public void setInitiative(int initiative) {
		this.initiative = initiative;
	}

	/**
	 * @return the alignement
	 */
	public Alignement getAlignement() {
		return alignement;
	}

	/**
	 * @param alignement
	 *            the alignement to set
	 */
	public void setAlignement(Alignement alignement) {
		this.alignement = alignement;
	}

	/**
	 * @return the rank
	 */
	public Rank getRank() {
		return rank;
	}

	/**
	 * @param rank
	 *            the rank to set
	 */
	public void setRank(Rank rank) {
		this.rank = rank;
	}

	/**
	 * @return the prospection
	 */
	public int getProspection() {
		return prospection;
	}

	/**
	 * @param prospection
	 *            the prospection to set
	 */
	public void setProspection(int prospection) {
		this.prospection = prospection;
	}

	/**
	 * @return the stats
	 */
	public Map<Stat, StatValue> getStats() {
		return stats;
	}

	/**
	 * @param stats
	 *            the stats to set
	 */
	public void setStats(Map<Stat, StatValue> stats) {
		this.stats = stats;
	}

	/**
	 * @return the accessories
	 */
	public Accessory[] getAccessories() {
		return accessories;
	}

	/**
	 * @param accessories
	 *            the accessories to set
	 */
	public void setAccessories(Accessory[] accessories) {
		this.accessories = accessories;
	}

	/**
	 * @return the merchant
	 */
	public boolean isMerchant() {
		return merchant;
	}

	/**
	 * @param merchant
	 *            the merchant to set
	 */
	public void setMerchant(boolean merchant) {
		this.merchant = merchant;
	}

	/**
	 * @return the dead
	 */
	public boolean isDead() {
		return dead;
	}

	/**
	 * @param dead
	 *            the dead to set
	 */
	public void setDead(boolean dead) {
		this.dead = dead;
	}

	/**
	 * @return the deathCount
	 */
	public int getDeathCount() {
		return deathCount;
	}

	/**
	 * @param deathCount
	 *            the deathCount to set
	 */
	public void setDeathCount(int deathCount) {
		this.deathCount = deathCount;
	}

	/**
	 * @return the guild
	 */
	public int getGuild() {
		return guild;
	}

	/**
	 * @param guild
	 *            the guild to set
	 */
	public void setGuild(int guild) {
		this.guild = guild;
	}

	/**
	 * @return the team
	 */
	public int getTeam() {
		return team;
	}

	/**
	 * @param team
	 *            the team to set
	 */
	public void setTeam(int team) {
		this.team = team;
	}

	/**
	 * @return the aura
	 */
	public int getAura() {
		return aura;
	}

	/**
	 * @param aura
	 *            the aura to set
	 */
	public void setAura(int aura) {
		this.aura = aura;
	}

	/**
	 * @return the emot
	 */
	public int getEmot() {
		return emot;
	}

	/**
	 * @param emot
	 *            the emot to set
	 */
	public void setEmot(int emot) {
		this.emot = emot;
	}

	/**
	 * @return the emotTimer
	 */
	public int getEmotTimer() {
		return emotTimer;
	}

	/**
	 * @param emotTimer
	 *            the emotTimer to set
	 */
	public void setEmotTimer(int emotTimer) {
		this.emotTimer = emotTimer;
	}

	/**
	 * @return the guildName
	 */
	public String getGuildName() {
		return guildName;
	}

	/**
	 * @param guildName
	 *            the guildName to set
	 */
	public void setGuildName(String guildName) {
		this.guildName = guildName;
	}

	/**
	 * @return the emblem
	 */
	public String[] getEmblem() {
		return emblem;
	}

	/**
	 * @param emblem
	 *            the emblem to set
	 */
	public void setEmblem(String[] emblem) {
		this.emblem = emblem;
	}

	/**
	 * @param restrictions
	 *            the restrictions to set
	 */
	public void setRestrictions(PlayerRestriction restrictions) {
		this.restrictions = restrictions;
	}

	/**
	 * @return the inventory
	 */
	public Inventory getInventory() {
		return inventory;
	}

	/**
	 * @param inventory
	 *            the inventory to set
	 */
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
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
	 * @param xpLow
	 *            the xpLow to set
	 */
	public void setXpLow(int xpLow) {
		this.xpLow = xpLow;
	}

	/**
	 * @param xpHight
	 *            the xpHight to set
	 */
	public void setXpHight(int xpHight) {
		this.xpHight = xpHight;
	}

	/**
	 * @return the statsPoints
	 */
	public int getStatsPoints() {
		return statsPoints;
	}

	/**
	 * @param statsPoints
	 *            the statsPoints to set
	 */
	public void setStatsPoints(int statsPoints) {
		this.statsPoints = statsPoints;
	}

	/**
	 * @return the spellsPoints
	 */
	public int getSpellsPoints() {
		return spellsPoints;
	}

	/**
	 * @param spellsPoints
	 *            the spellsPoints to set
	 */
	public void setSpellsPoints(int spellsPoints) {
		this.spellsPoints = spellsPoints;
	}

	/**
	 * @return the energy
	 */
	public int getEnergy() {
		return energy;
	}

	/**
	 * @param energy
	 *            the energy to set
	 */
	public void setEnergy(int energy) {
		this.energy = energy;
	}

	/**
	 * @return the energyMax
	 */
	public int getEnergyMax() {
		return energyMax;
	}

	/**
	 * @param energyMax
	 *            the energyMax to set
	 */
	public void setEnergyMax(int energyMax) {
		this.energyMax = energyMax;
	}

	@Override
	public Genre getSex() {
		return genre;
	}

	@Override
	public int getLevel() {
		return lvl;
	}

	@Override
	public PlayerRestriction getRestriction() {
		return restrictions;
	}

	@Override
	public long getUUID() {
		return uuid;
	}

	@Override
	public int getXpMin() {
		return xpLow;
	}

	@Override
	public int getXpMax() {
		return xpHight;
	}

	@Override
	public List<String> getOfflinesFriends() {
		return offlineFriends;
	}

	@Override
	public List<Friend> getOnlineFriends() {
		return onlineFriends;
	}

	/**
	 * @param offlineFriends
	 *            the offlineFriends to set
	 */
	public void setOfflineFriends(List<String> offlineFriends) {
		this.offlineFriends = offlineFriends;
	}

	/**
	 * @param onlineFriends
	 *            the onlineFriends to set
	 */
	public void setOnlineFriends(List<Friend> onlineFriends) {
		this.onlineFriends = onlineFriends;
	}

	@Override
	public Map<Chat, Boolean> getChannels() {
		return channels;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj == this) return true;
		if (obj instanceof Entity) {
			Entity e = (Entity) obj;
			return e.getUUID() == uuid;
		}
		return false;
	}

}
