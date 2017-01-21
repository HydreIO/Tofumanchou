package fr.aresrpg.tofumanchou.infra.data;

import static fr.aresrpg.tofumanchou.domain.Manchou.LOGGER;

import fr.aresrpg.commons.domain.concurrent.Threads;
import fr.aresrpg.commons.domain.util.exception.NotImplementedException;
import fr.aresrpg.dofus.protocol.DofusConnection;
import fr.aresrpg.dofus.protocol.Packet;
import fr.aresrpg.dofus.protocol.ProtocolRegistry.Bound;
import fr.aresrpg.dofus.protocol.basic.client.BasicChatMessageSendPacket;
import fr.aresrpg.dofus.protocol.basic.client.BasicUseSmileyPacket;
import fr.aresrpg.dofus.protocol.chat.ChatSubscribeChannelPacket;
import fr.aresrpg.dofus.protocol.conquest.client.WorldInfosJoinPacket;
import fr.aresrpg.dofus.protocol.dialog.DialogLeavePacket;
import fr.aresrpg.dofus.protocol.dialog.client.DialogCreatePacket;
import fr.aresrpg.dofus.protocol.dialog.client.DialogResponsePacket;
import fr.aresrpg.dofus.protocol.emote.client.EmoteUsePacket;
import fr.aresrpg.dofus.protocol.exchange.ExchangeLeavePacket;
import fr.aresrpg.dofus.protocol.exchange.client.*;
import fr.aresrpg.dofus.protocol.exchange.client.ExchangeMoveItemsPacket.MovedItem;
import fr.aresrpg.dofus.protocol.fight.client.*;
import fr.aresrpg.dofus.protocol.friend.client.*;
import fr.aresrpg.dofus.protocol.game.actions.*;
import fr.aresrpg.dofus.protocol.game.actions.GameMoveAction.PathFragment;
import fr.aresrpg.dofus.protocol.game.actions.client.*;
import fr.aresrpg.dofus.protocol.game.client.*;
import fr.aresrpg.dofus.protocol.game.movement.MovementPlayer;
import fr.aresrpg.dofus.protocol.game.movement.MovementPlayer.PlayerInFight;
import fr.aresrpg.dofus.protocol.game.movement.MovementPlayer.PlayerOutsideFight;
import fr.aresrpg.dofus.protocol.guild.client.GuildRefuseInvitPacket;
import fr.aresrpg.dofus.protocol.item.client.*;
import fr.aresrpg.dofus.protocol.mount.client.MountPlayerPacket;
import fr.aresrpg.dofus.protocol.party.PartyRefusePacket;
import fr.aresrpg.dofus.protocol.party.client.*;
import fr.aresrpg.dofus.protocol.subway.SubwayLeavePacket;
import fr.aresrpg.dofus.protocol.subway.client.SubwayUsePacket;
import fr.aresrpg.dofus.protocol.waypoint.ZaapLeavePacket;
import fr.aresrpg.dofus.protocol.waypoint.client.ZaapUsePacket;
import fr.aresrpg.dofus.structures.*;
import fr.aresrpg.dofus.structures.character.Character;
import fr.aresrpg.dofus.structures.game.Alignement;
import fr.aresrpg.dofus.structures.game.Effect;
import fr.aresrpg.dofus.structures.item.Accessory;
import fr.aresrpg.dofus.structures.job.Jobs;
import fr.aresrpg.dofus.structures.server.Server;
import fr.aresrpg.dofus.structures.stat.Stat;
import fr.aresrpg.dofus.structures.stat.StatValue;
import fr.aresrpg.dofus.util.Maps;
import fr.aresrpg.dofus.util.Pathfinding;
import fr.aresrpg.dofus.util.Pathfinding.Node;
import fr.aresrpg.dofus.util.Pathfinding.PathValidator;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.Job;
import fr.aresrpg.tofumanchou.domain.data.Spell;
import fr.aresrpg.tofumanchou.domain.data.entity.Entity;
import fr.aresrpg.tofumanchou.domain.data.entity.EntityColor;
import fr.aresrpg.tofumanchou.domain.data.entity.mob.MobGroup;
import fr.aresrpg.tofumanchou.domain.data.entity.player.Perso;
import fr.aresrpg.tofumanchou.domain.data.enums.*;
import fr.aresrpg.tofumanchou.domain.event.*;
import fr.aresrpg.tofumanchou.domain.event.player.PersoMoveEndEvent;
import fr.aresrpg.tofumanchou.domain.util.Validators;
import fr.aresrpg.tofumanchou.domain.util.concurrent.Executors;
import fr.aresrpg.tofumanchou.infra.io.*;

import java.awt.Point;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 
 * @since
 */
public class ManchouPerso implements Perso {

	private ManchouAccount account;
	private long uuid;
	private int cellId;
	private Server server;
	private String pseudo;
	private int lvl;
	private int lvlMax;
	private Classe classe;
	private EntityColor colors;
	private int life;
	private int lifeMax;
	private int initiative;
	private Alignement alignement;
	private Rank rank;
	private int prospection;
	private Map<Stat, StatValue> stats = new HashMap<>();
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
	private String restrictions;
	private PlayerInventory inventory = new PlayerInventory();
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
	private Set<Effect> effects = new HashSet<>();
	private ManchouMap map;
	private Map<Spells, Spell> spells = new HashMap<>();
	private int scaleX;
	private int scaleY;
	private Orientation orientation;
	private int sprite;
	private ManchouJob job;
	private Map<Jobs, Job> jobs = new HashMap<>();
	private int pods;
	private int maxPods;

	private Exchange currentInv;
	private boolean mitm = true;
	private ScheduledFuture moveListener;
	private boolean moving = false;
	private long lastMoved;
	private int lastAction;
	private boolean mount;

	private boolean defied;
	private long defierId;
	private boolean invitedGuild;
	private long guildinvitId;
	private boolean invitedGrp;
	private boolean invitedExchange;
	private int annoyedCount;

	public ManchouPerso(Account account, String pseudo, Server server) {
		this.account = (ManchouAccount) account;
		this.pseudo = pseudo;
		this.server = server;
	}

	public ManchouPerso(Account account, Server server, Character c) {
		this.account = (ManchouAccount) account;
		this.pseudo = c.getPseudo();
		this.server = server;
		this.uuid = c.getId();
		this.lvl = c.getLevel();
		this.guild = c.getGuild();
		this.classe = Classe.getClasse(c.getGfxId());
		this.colors = new ManchouColors(c.getColor1(), c.getColor2(), c.getColor3());
		if (c.getItems() != null)
			inventory.replaceContent(Arrays.stream(c.getItems()).collect(Collectors.toList()));
	}

	public MovementPlayer serialize() {
		MovementPlayer pl = new MovementPlayer(uuid, pseudo, sprite, cellId, scaleX, scaleY, orientation, 9, alignement, rank.getValue(), null, null);
		if (!getMap().isEnded()) {
			PlayerInFight inf = new PlayerInFight(lvl, colors.getFirstColor(), colors.getSecondColor(), colors.getThirdColor(), accessories, life, getStat(Stat.PA).getTotal(),
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

	public boolean hasMount() {
		return mount;
	}

	public void mount() {
		mount = !mount;
		sendPacketToServer(new MountPlayerPacket());
	}

	public void endAction() {
		if (lastAction != -1)
			sendPacketToServer(new GameActionACKPacket().setActionId(lastAction));
	}

	public void setLastAction(int lastAction) {
		this.lastAction = lastAction;
	}

	public int getLastAction() {
		return lastAction;
	}

	public void updateMovement(MovementPlayer player) {
		if (player.getId() != uuid)
			throw new IllegalArgumentException("Bad player !");
		cellId = player.getCellId();
		pseudo = player.getPseudo();
		scaleX = player.getScaleX();
		scaleY = player.getScaleY();
		orientation = player.getOrientation();
		sprite = player.getSprite();
		this.classe = Classe.getClasse(sprite);
		alignement = player.getAlignement();
		if (player.isFight()) {
			PlayerInFight inf = player.getPlayerInFight();
			lvl = inf.getLvl();
			colors = new ManchouColors(inf.getColor1(), inf.getColor2(), inf.getColor3());
			accessories = inf.getAccessories();
			life = inf.getLife();
			team = inf.getTeam();
		} else {
			PlayerOutsideFight of = player.getPlayerOutsideFight();
			colors = new ManchouColors(of.getColor1(), of.getColor2(), of.getColor3());
			accessories = of.getAccessories();
			aura = of.getAura();
			emot = of.getEmot();
			emotTimer = of.getEmotTimer();
			guildName = of.getGuildname();
			emblem = of.getEmblem();
			restrictions = of.getRestrictions();
		}
	}

	public void askMap() {
		sendPacketToServer(new WorldInfosJoinPacket());
	}

	@Override
	public void connect() {
		this.mitm = false;
		account.getLogger().info("Connection de " + pseudo + "..");
		try {
			SocketChannel channel = SocketChannel.open(ManchouBridge.SERVER_ADRESS);
			this.account.setPerso(this);
			BaseServerPacketHandler han = new BaseServerPacketHandler(account);
			DofusConnection<SocketChannel> dofusConnection = new DofusConnection<>(getPseudo(), channel, han, Bound.SERVER);
			account.setConnection(dofusConnection);
			Executors.FIXED.execute(() -> {
				try {
					dofusConnection.start();
				} catch (Exception e) {
					ClientCrashEvent event = new ClientCrashEvent(account, e);
					event.send();
					if (event.isShowException()) LOGGER.error(e);
					if (event.isShutdownClient()) dofusConnection.closeConnection();
				}
			});
		} catch (IOException e) {
			LOGGER.error(e);
		}
		new BotConnectEvent(account, this).send();
	}

	@Override
	public void disconnect() {
		LOGGER.success("Déconnection de " + pseudo);
		new BotDisconnectEvent(account, this).send();
		ManchouProxy proxy = account.getProxy();
		if (proxy != null) proxy.shutdown();
		else account.getConnection().closeConnection();
		LOGGER.success(pseudo + " déconnecté !");
	}

	/**
	 * @param mitm
	 *            the mitm to set
	 */
	public void setMitm(boolean mitm) {
		this.mitm = mitm;
	}

	/**
	 * @return the currentInv
	 */
	public Exchange getCurrentInv() {
		return currentInv;
	}

	/**
	 * @param currentInv
	 *            the currentInv to set
	 */
	public void setCurrentInv(Exchange currentInv) {
		this.currentInv = currentInv;
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
	 * @param map
	 *            the map to set
	 */
	public void setMap(ManchouMap map) {
		this.map = map;
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
	 * @return the inventory
	 */
	public PlayerInventory getInventory() {
		return inventory;
	}

	/**
	 * @param inventory
	 *            the inventory to set
	 */
	public void setInventory(PlayerInventory inventory) {
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
	public int getLevel() {
		return lvl;
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
		return obj instanceof Entity && ((Entity) obj).getUUID() == uuid;
	}

	@Override
	public int hashCode() {
		return (int) uuid;
	}

	@Override
	public Set<Effect> getEffects() {
		return effects;
	}

	@Override
	public ManchouMap getMap() {
		return map;
	}

	@Override
	public Map<Spells, Spell> getSpells() {
		return spells;
	}

	@Override
	public int getCellId() {
		return cellId;
	}

	public ManchouCell getCell() {
		return map.getCells()[cellId];
	}

	/**
	 * @param cellId
	 *            the cellId to set
	 */
	public void setCellId(int cellId) {
		this.cellId = cellId;
	}

	@Override
	public String getRestriction() {
		return restrictions;
	}

	@Override
	public int getScaleX() {
		return scaleX;
	}

	@Override
	public int getScaleY() {
		return scaleY;
	}

	@Override
	public Orientation getOrientation() {
		return orientation;
	}

	@Override
	public ManchouJob getJob() {
		return job;
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

	@Override
	public Map<Jobs, Job> getJobs() {
		return jobs;
	}

	public void updateJob(Jobs j) {
		if (jobs.containsKey(j)) setJob((ManchouJob) jobs.get(j));
	}

	@Override
	public void sendPacketToClient(Packet pkt) {
		try {
			getAccount().getProxy().getLocalConnection().send(pkt);
		} catch (IOException e) {
			ClientCrashEvent event = new ClientCrashEvent(getAccount(), e);
			event.send();
			if (event.isShowException()) LOGGER.error(e);
		}
	}

	@Override
	public void sendPacketToServer(Packet pkt) {
		try {
			getAccount().getConnection().send(pkt);
		} catch (IOException e) {
			ClientCrashEvent event = new ClientCrashEvent(getAccount(), e);
			event.send();
			if (event.isShowException()) LOGGER.error(e);
		}
	}

	/**
	 * @param job
	 *            the job to set
	 */
	public void setJob(ManchouJob job) {
		this.job = job;
	}

	@Override
	public int getPods() {
		return pods;
	}

	@Override
	public int getMaxPods() {
		return maxPods;
	}

	/**
	 * @param pods
	 *            the pods to set
	 */
	public void setPods(int pods) {
		this.pods = pods;
	}

	/**
	 * @param maxPods
	 *            the maxPods to set
	 */
	public void setMaxPods(int maxPods) {
		this.maxPods = maxPods;
	}

	@Override
	public void startCraft() {
		sendPacketToServer(new ExchangeSendReadyPacket());
	}

	@Override
	public void startCraft(int nbr) {
		startCraft();
		if (nbr == 1) return;
		sendPacketToServer(new ExchangeRepeatCraftPacket(nbr - 1));
	}

	public boolean isMitm() {
		return mitm;
	}

	@Override
	public void cancelCraft() {
		sendPacketToServer(new ExchangeRepeatCraftPacket(true)); // ui jsai c chelou
	}

	@Override
	public void replaceCraft() {
		sendPacketToServer(new ExchangeReplayCraftPacket());
	}

	@Override
	public void moveToCell(int cellid, boolean diagonals, boolean avoidMobs) {
		LOGGER.debug("moving = " + moving);
		if (moving) {
			long timeToWait = (lastMoved + 250) - System.currentTimeMillis();
			LOGGER.debug("time to wait = " + timeToWait);
			if (timeToWait > 0) Threads.uSleep(timeToWait, TimeUnit.MILLISECONDS);
			LOGGER.debug("cancel on " + getCellId());
			if (moveListener != null) moveListener.cancel(true);
			sendPacketToServer(new GameActionCancelPacket(0, getCellId() + ""));
		}
		move(searchPath(cellid, avoidMobs, diagonals));
	}

	@SuppressWarnings("deprecation")
	@Deprecated
	@Override
	public void move(List<Node> p) {
		if (p == null) throw new NullPointerException("The path is null !");
		long time = (long) (Pathfinding.getPathTime(p, getMap().getProtocolCells(), getMap().getWidth(), getMap().getHeight(), false) * 30);
		List<PathFragment> shortpath = Pathfinding.makeShortPath(p, getMap().getWidth(), getMap().getHeight());
		if (shortpath == null) {
			LOGGER.error("Unable to find a path ! The point list is invalid ! " + p);
			return;
		}
		LOGGER.debug("moving from " + cellId + " to " + shortpath.get(shortpath.size() - 1).getCellId() + " list node = " + p);
		LOGGER.severe("le path = " + shortpath);
		new BotStartMoveEvent(getAccount(), shortpath).send();
		if (map.isEnded()) setMoving(true);
		GameClientActionPacket gameClientActionPacket = new GameClientActionPacket(GameActions.MOVE, new GameMoveAction().setPath(shortpath));
		sendPacketToServer(gameClientActionPacket);
		if (moveListener != null) moveListener.cancel(true);
		return;
	}

	public void setMoving(boolean moving) {
		LOGGER.debug("set moving " + moving);
		this.moving = moving;
	}

	public void setLastMoved(long lastMoved) {
		this.lastMoved = lastMoved;
	}

	public void cancelRunner() {
		setMoving(false);
		LOGGER.debug("moving false, canceling");
		new PersoMoveEndEvent(getAccount(), this).send();
		if (moveListener != null) moveListener.cancel(true);
	}

	public void positionRunner(Queue<Node> path) {
		Node poll = path.poll();
		if (poll == null) {
			if (!isMitm()) endAction();
			cancelRunner();
			return;
		}
		setCellId(Maps.getIdRotated(poll.getX(), poll.getY(), map.getWidth(), map.getHeight()));
	}

	public void setMoveListener(ScheduledFuture moveListener) {
		if (this.moveListener != null) this.moveListener.cancel(true);
		this.moveListener = moveListener;
	}

	private List<Node> searchPath(int cellid, boolean avoidMobs, boolean diagonals) {
		if (cellid == -1) return null;
		int width = getMap().getWidth();
		int height = getMap().getHeight();
		return Pathfinding.getCellPath(getCellId(), cellid, getMap().getProtocolCells(), width, height, diagonals ? Pathfinding::getNeighbors : Pathfinding::getNeighborsWithoutDiagonals,
				avoidMobs ? Validators.avoidingMobs(map) : PathValidator.alwaysTrue());
	}

	public boolean canGoOnCellAvoidingMobs(Point p) {
		if (!Maps.isInMapRotated(p.x, p.y, map.getWidth(), map.getHeight())) return false;
		Map<Long, Entity> entities = getMap().getEntities();
		int cellId = Maps.getIdRotated(p.x, p.y, map.getWidth(), map.getHeight());
		ManchouCell c = getMap().getCells()[cellId];
		if (c.isTeleporter()) return true;
		if (c.hasMobGroupOn()) return false;
		for (Entity en : getMap().getEntities().values()) {
			if (!(en instanceof MobGroup)) continue;
			MobGroup grp = (MobGroup) en;
			for (int type : grp.getEntitiesTypes()) {
				int distanceAgro = AgressiveMobs.getDistanceAgro(type);
				if (distanceAgro == 0) continue;
				distanceAgro++; // increment car la distance manathan par de 0
				if (Maps.distanceManathan(grp.getCellId(), c.getId(), map.getWidth(), map.getHeight()) <= distanceAgro) return false;
			}
		}
		return true;
	}

	public ManchouCell findRandomCellExept(List<fr.aresrpg.tofumanchou.domain.data.map.Cell> cells) {
		for (ManchouCell cell : getMap().getCells())
			if (!cells.contains(cell) && cell.isWalkeable() && !cell.isTeleporter() && !cell.hasMobGroupOn()) return cell;
		return null;
	}

	public ManchouCell getNearestWalkableCell(ManchouCell current) {
		int width = map.getWidth();
		int height = map.getHeight();
		int dist = Integer.MAX_VALUE;
		ManchouCell cell = null;
		for (ManchouCell c : map.getCells()) {
			int di = c.distanceManathan(current);
			if (cell == null || di < dist) {
				cell = c;
				dist = di;
			}
		}
		return cell;
	}

	public ManchouCell[] getAllTeleporters() {
		return Arrays.stream(map.getCells()).filter(ManchouCell::isTeleporter).toArray(ManchouCell[]::new);
	}

	public ManchouCell[] getNearestTeleporters() {
		return Arrays.stream(getAllTeleporters()).sorted((o1, o2) -> o1.distance(cellId) - o2.distance(cellId)).toArray(ManchouCell[]::new);
	}

	public ManchouCell[] getFarestTeleporters() {
		return Arrays.stream(getAllTeleporters()).sorted((o1, o2) -> o2.distance(cellId) - o1.distance(cellId)).toArray(ManchouCell[]::new);
	}

	@Override
	public void moveToRandomCell() {
		ManchouCell c = null;
		List<fr.aresrpg.tofumanchou.domain.data.map.Cell> cells = new ArrayList<>();
		List<Node> path = null;
		int security = 0;
		do {
			c = findRandomCellExept(cells);
			if (c != null)
				path = searchPath(c.getId(), true, true);
			if (++security > 300) break;
		} while (c == null || path == null);
		if (c == null || path == null) {
			LOGGER.warning("Impossible de bouger sur une cell random ! | aucune cell trouvée");
			return;
		}
		move(path);
	}

	@Override
	public void launchSpell(Spell spell, int relance, int cellid) {
		if (spell.getRelance() != 0) return;
		((ManchouSpell) spell).setRelance(relance);
		GameLaunchSpellAction action = new GameLaunchSpellAction(spell.getType().getId(), cellid);
		sendPacketToServer(new GameClientActionPacket(GameActions.LAUNCH_SPELL, action));
	}

	@Override
	public void setFightPosition(int pos) {
		GameSetPlayerPositionPacket pkt = new GameSetPlayerPositionPacket();
		pkt.setCellNum(pos);
		sendPacketToServer(pkt);
	}

	@Override
	public void endTurn() {
		sendPacketToServer(new GameEndTurnPacket());
	}

	@Override
	public void beReady(boolean ready) {
		GameClientReadyPacket pkt = new GameClientReadyPacket();
		pkt.setReady(ready);
		sendPacketToServer(pkt);
	}

	@Override
	public void blockSpec() {
		sendPacketToServer(new FightBlockSpectatePacket());
	}

	@Override
	public void blockCombat() {
		sendPacketToServer(new FightBlockAllPacket());
	}

	@Override
	public void blockToGroup() {
		sendPacketToServer(new FightRestrictGroupPacket());
	}

	@Override
	public void joinFight(int fightId) {
		GameJoinFightAction action = new GameJoinFightAction();
		action.setFightId(fightId);
		sendPacketToServer(new GameClientActionPacket(GameActions.JOIN_FIGHT, action));
	}

	@Override
	public void sit(boolean sit) {
		sendPacketToServer(new EmoteUsePacket().setEmot(Emotes.SIT));
	}

	@Override
	public void speakToNpc(int npcid) {
		DialogCreatePacket pkt = new DialogCreatePacket();
		pkt.setNpcId(npcid);
		sendPacketToServer(pkt);
	}

	@Override
	public void followGroupMember(int player) {
		PartyFollowPacket pkt = new PartyFollowPacket();
		pkt.setFollow(true);
		pkt.setPlayerId(player);
		sendPacketToServer(pkt);
	}

	@Override
	public void buyToNpc(int npcid) {
		ExchangeRequestPacket pkt = new ExchangeRequestPacket(Exchange.NPC_SHOP, npcid);
		sendPacketToServer(pkt);
	}

	@Override
	public void npcTalkChoice(int questionId, int responseId) {
		sendPacketToServer(new DialogResponsePacket(questionId, responseId));
	}

	@Override
	public void addFriend(String name) {
		FriendAddPacket pkt = new FriendAddPacket();
		pkt.setName(name);
		sendPacketToServer(pkt);
	}

	@Override
	public void removeFriend(String name) {
		FriendRemovePacket pkt = new FriendRemovePacket();
		pkt.setName(name);
		sendPacketToServer(pkt);
	}

	@Override
	public void getFriendList() {
		sendPacketToServer(new FriendGetListPacket());
	}

	@Override
	public void npcBuyChoice(int itemId, int quantity) {
		ExchangeBuyToNpcPacket pkt = new ExchangeBuyToNpcPacket(itemId, quantity);
		sendPacketToServer(pkt);
	}

	@Override
	public void useZaap(int cell, Zaap destination) {
		interract(Skills.UTILISER, cell);
		Threads.uSleep(6, TimeUnit.SECONDS); // avoid server error
		ZaapUsePacket pkt = new ZaapUsePacket();
		pkt.setWaypointId(destination.getMapId());
		LOGGER.debug("Want to use zaap to go to " + destination);
		sendPacketToServer(pkt);
	}

	@Override
	public void useZaapi(int cell, Zaapi destination) {
		interract(Skills.SE_FAIRE_TRANSPORTER, cell);
		Threads.uSleep(6, TimeUnit.SECONDS);
		SubwayUsePacket subwayUsePacket = new SubwayUsePacket();
		subwayUsePacket.setMapid(destination.getMapid());
		sendPacketToServer(subwayUsePacket);
	}

	@Override
	public void leaveZaap() {
		sendPacketToServer(new ZaapLeavePacket());
	}

	@Override
	public void leaveZaapi() {
		sendPacketToServer(new SubwayLeavePacket());
	}

	@Override
	public void moveItem(MovedItem item) {
		Map<Long, fr.aresrpg.tofumanchou.domain.data.item.Item> inv = getInventory().getContents();
		if (item.getType() == ExchangeMove.ADD) {
			if (!inv.containsKey(item.getItemUid())) throw new IllegalArgumentException("Can't move item " + item + " | The inventory of the player doesn't contain it");
			fr.aresrpg.tofumanchou.domain.data.item.Item mov = inv.get(item.getItemUid());
			if (item.getAmount() > mov.getAmount()) throw new IllegalArgumentException("Can't move item " + item + " | There are not as many item in the inventory ! (" + mov.getAmount() + ")");
			if (item.getAmount() < 0) throw new IllegalArgumentException("Unable to move " + item.getAmount() + " of " + item);
		}
		ExchangeMoveItemsPacket pkt = new ExchangeMoveItemsPacket();
		Set<MovedItem> it = new HashSet<>();
		it.add(item);
		pkt.setItems(it);
		sendPacketToServer(pkt);
	}

	@Override
	public void moveKama(int amount) {
		ExchangeMoveKamasPacket pkt = new ExchangeMoveKamasPacket(amount);
		sendPacketToServer(pkt);
	}

	@Override
	public void destroyItem(long uid, int amount) {
		sendPacketToClient(new ItemDestroyPacket(uid, amount));
	}

	@Override
	public void useItem(long itemuid) {
		ItemUsePacket pkt = new ItemUsePacket();
		pkt.setItemId(itemuid);
		sendPacketToServer(pkt);
	}

	@Override
	public void interract(Skills s, int cell) {
		GameInteractionAction action = new GameInteractionAction(cell, s);
		sendPacketToServer(new GameClientActionPacket(GameActions.INTERRACT, action));
	}

	@Override
	public void leaveDialog() {
		sendPacketToServer(new DialogLeavePacket());
	}

	@Override
	public void leaveExchange() {
		sendPacketToServer(new ExchangeLeavePacket());
	}

	@Override
	public void confirmExchange() {
		sendPacketToServer(new ExchangeSendReadyPacket());
	}

	@Override
	public void speak(Chat canal, String msg) {
		BasicChatMessageSendPacket pkt = new BasicChatMessageSendPacket();
		pkt.setChat(canal);
		pkt.setMsg(msg);
		sendPacketToServer(pkt);
	}

	@Override
	public void sendPm(String playername, String msg) {
		BasicChatMessageSendPacket pkt = new BasicChatMessageSendPacket();
		pkt.setDest(playername);
		pkt.setMsg(msg);
		sendPacketToServer(pkt);
	}

	@Override
	public void equip(EquipmentPosition pos, int itemId) {
		ItemMovementPacket pkt = new ItemMovementPacket();
		pkt.setItemid(itemId);
		pkt.setPosition(pos.getPosition());
		pkt.setQuantity(1);
		sendPacketToServer(pkt);
	}

	@Override
	public void dismantle(EquipmentPosition pos) {
		ItemMovementPacket pkt = new ItemMovementPacket();
		pkt.setItemid(getInventory().getItemAtPos(pos).getUUID());
		pkt.setPosition(EquipmentPosition.NO_EQUIPED.getPosition());
		pkt.setQuantity(1);
		sendPacketToServer(pkt);
	}

	@Override
	public void invitPlayerToGroup(String pname) {
		PartyInvitePacket pkt = new PartyInvitePacket();
		pkt.setPname(pname);
		sendPacketToServer(pkt);
	}

	@Override
	public void invitPlayerToGroupAndCancel(String name, long cancelAfter, TimeUnit unit) {
		invitPlayerToGroup(name);
		if (cancelAfter != 0) Executors.SCHEDULED.schedule(() -> sendPacketToServer(new PartyRefusePacket()), cancelAfter, unit);
	}

	@Override
	public void acceptGroupInvitation(boolean accept) {
		if (accept) sendPacketToServer(new PartyAcceptPacket());
		else sendPacketToServer(new PartyRefusePacket());
	}

	@Override
	public void defiPlayer(long id) {
		GameDuelAction action = new GameDuelAction(id);
		GameClientActionPacket ga = new GameClientActionPacket(GameActions.DUEL, action);
		sendPacketToServer(ga);
	}

	@Override
	public void defiPlayerAndCancel(long id, long cancelAfter, TimeUnit unit) {
		defiPlayer(id);
		if (cancelAfter != 0) Executors.SCHEDULED.schedule(() -> {
			GameRefuseDuelAction actionr = new GameRefuseDuelAction();
			actionr.setTargetId(uuid);
			sendPacketToServer(new GameClientActionPacket(GameActions.REFUSE_DUEL, actionr));
		}, cancelAfter, unit);
	}

	/**
	 * Refuse une invitation de défi provenant d'un autre joueur
	 */
	public void cancelDefiInvit() {
		GameRefuseDuelAction actionr = new GameRefuseDuelAction();
		actionr.setTargetId(defierId);
		sendPacketToServer(new GameClientActionPacket(GameActions.REFUSE_DUEL, actionr));
		defied = false;
	}

	public void cancelExchangeInvit() {
		sendPacketToServer(new ExchangeLeavePacket());
		invitedExchange = false;
	}

	/**
	 * Refuse une invitation de guilde provenant d'un autre joueur
	 */
	public void cancelGuildInvit() {
		GuildRefuseInvitPacket pkt = new GuildRefuseInvitPacket();
		pkt.setPlayerid(guildinvitId);
		sendPacketToServer(pkt);
		invitedGuild = false;
	}

	public void cancelGroupInvit() {
		sendPacketToServer(new PartyRefusePacket());
		invitedGrp = false;
	}

	@Override
	public void acceptDefiRequest(long playerid, boolean accept) {
		GameAction a = accept ? new GameAcceptDuelAction(playerid) : new GameRefuseDuelAction(playerid);
		GameActions ac = accept ? GameActions.ACCEPT_DUEL : GameActions.REFUSE_DUEL;
		sendPacketToServer(new GameClientActionPacket(ac, a));
	}

	@Override
	public void echangeWith(long id) {
		ExchangeRequestPacket pkt = new ExchangeRequestPacket(Exchange.EXCHANGE, id);
		sendPacketToServer(pkt);
	}

	@Override
	public void acceptEchangeRequest(boolean accept) {
		sendPacketToServer(new ExchangeAcceptPacket());
	}

	@Override
	public void acceptGuildInvitation(boolean accept) {
		throw new NotImplementedException();
	}

	@Override
	public void sendSmiley(Smiley emot) {
		BasicUseSmileyPacket pkt = new BasicUseSmileyPacket();
		pkt.setSmileyId(emot.getId());
		sendPacketToServer(pkt);
	}

	@Override
	public void activateChat(Chat... chts) {
		ChatSubscribeChannelPacket pkt = new ChatSubscribeChannelPacket();
		pkt.setAdd(true);
		pkt.setChannels(chts);
		sendPacketToServer(pkt);
	}

	@Override
	public void desactivateChat(Chat... chts) {
		ChatSubscribeChannelPacket pkt = new ChatSubscribeChannelPacket();
		pkt.setAdd(false);
		pkt.setChannels(chts);
		sendPacketToServer(pkt);
	}

	public boolean isDefied() {
		return defied;
	}

	public boolean isInvitedExchange() {
		return invitedExchange;
	}

	public boolean isInvitedGrp() {
		return invitedGrp;
	}

	public boolean isInvitedGuild() {
		return invitedGuild;
	}

	public void setDefied(boolean defied, long defier) {
		annoyedCount++;
		this.defied = defied;
		this.defierId = defier;
	}

	public void setInvitedExchange(boolean invitedExchange) {
		annoyedCount++;
		this.invitedExchange = invitedExchange;
		setInvitedGrp(false);
	}

	public void setInvitedGrp(boolean invitedGrp) {
		annoyedCount++;
		this.invitedGrp = invitedGrp;
	}

	public void setInvitedGuild(boolean invitedGuild, long inviter) {
		annoyedCount++;
		this.invitedGuild = invitedGuild;
		this.guildinvitId = inviter;
	}

	public int getAnnoyedCount() {
		return annoyedCount;
	}

	public void resetAnnoyedCount() {
		this.annoyedCount = 0;
	}

	@Override
	public String toString() {
		return "ManchouPerso [uuid=" + uuid + ", cellId=" + cellId + ", server=" + server + ", pseudo=" + pseudo + ", lvl=" + lvl + ", lvlMax=" + lvlMax + ", classe=" + classe + ", colors=" + colors
				+ ", life=" + life + ", lifeMax=" + lifeMax + ", initiative=" + initiative + ", alignement=" + alignement + ", rank=" + rank + ", prospection=" + prospection
				+ ", stats=" + stats + ", accessories=" + Arrays.toString(accessories) + ", merchant=" + merchant + ", dead=" + dead + ", deathCount=" + deathCount + ", guild=" + guild + ", team="
				+ team + ", aura=" + aura + ", emot=" + emot + ", emotTimer=" + emotTimer + ", guildName=" + guildName + ", emblem=" + Arrays.toString(emblem) + ", restrictions=" + restrictions
				+ ", inventory=" + inventory + ", xp=" + xp + ", xpLow=" + xpLow + ", xpHight=" + xpHight + ", statsPoints=" + statsPoints + ", spellsPoints=" + spellsPoints + ", energy=" + energy
				+ ", energyMax=" + energyMax + ", offlineFriends=" + offlineFriends + ", onlineFriends=" + onlineFriends + ", channels=" + channels + ", effects=" + effects + ", map=" + map
				+ ", spells=" + spells + ", scaleX=" + scaleX + ", scaleY=" + scaleY + ", orientation=" + orientation + ", sprite=" + sprite + ", job=" + job + ", jobs=" + jobs + ", pods=" + pods
				+ ", maxPods=" + maxPods + ", currentInv=" + currentInv + ", mitm=" + mitm + "]";
	}

}
