package fr.aresrpg.tofumanchou.infra.data;

import fr.aresrpg.dofus.protocol.DofusConnection;
import fr.aresrpg.dofus.protocol.ProtocolRegistry.Bound;
import fr.aresrpg.dofus.structures.PlayerRestriction;
import fr.aresrpg.dofus.structures.Rank;
import fr.aresrpg.dofus.structures.game.Alignement;
import fr.aresrpg.dofus.structures.item.Accessory;
import fr.aresrpg.dofus.structures.server.Server;
import fr.aresrpg.dofus.structures.stat.Stat;
import fr.aresrpg.dofus.structures.stat.StatValue;
import fr.aresrpg.tofumanchou.domain.data.Account;
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
import java.util.Arrays;
import java.util.Map;

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
	public String getPseudo() {
		// TODO
		return null;
	}

	public void setUuid(long uuid) {
		this.uuid = uuid;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

	public void setLvlMax(int lvlMax) {
		this.lvlMax = lvlMax;
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
		return genre;
	}

	@Override
	public int getLevel() {
		return this.lvl;
	}

	@Override
	public int getLife() {
		return this.life;
	}

	@Override
	public int getLifeMax() {
		return this.lifeMax;
	}

	@Override
	public int getInitiative() {
		return this.initiative;
	}

	@Override
	public Alignement getAlignement() {
		return this.alignement;
	}

	@Override
	public Rank getRank() {
		return this.rank;
	}

	@Override
	public int getProspection() {
		return this.prospection;
	}

	@Override
	public Map<Stat, StatValue> getStats() {
		return this.stats;
	}

	@Override
	public StatValue getStat(Stat stat) {
		return stats.get(stat);
	}

	@Override
	public Accessory[] getAccessories() {
		return this.accessories;
	}

	@Override
	public Server getServer() {
		return this.server;
	}

	@Override
	public boolean isMerchant() {
		return this.merchant;
	}

	@Override
	public boolean isDead() {
		return this.dead;
	}

	@Override
	public int getDeathCount() {
		return this.deathCount;
	}

	@Override
	public int getLvlMax() {
		return this.lvlMax;
	}

	@Override
	public int getGuild() {
		return this.guild;
	}

	@Override
	public int getTeam() {
		return this.team;
	}

	@Override
	public int getAura() {
		return this.aura;
	}

	@Override
	public int getEmot() {
		return this.emot;
	}

	@Override
	public int getEmotTimer() {
		return this.emotTimer;
	}

	@Override
	public String getGuildName() {
		return this.guildName;
	}

	@Override
	public String[] getEmblem() {
		return this.emblem;
	}

	@Override
	public PlayerRestriction getRestriction() {
		return this.restrictions;
	}

	@Override
	public long getUUID() {
		return this.uuid;
	}

	@Override
	public Account getAccount() {
		return this.account;
	}

	@Override
	public Inventory getInventory() {
		return this.inventory;
	}

	public void setColors(int color1, int color2, int color3) {
		this.colors = new ManchouColors(color1, color2, color3);
	}

	public void setAccessories(int[] accessories) {
		this.accessories = Arrays.stream(accessories).mapToObj(i -> new Accessory(-1, i, -1)).toArray(Accessory[]::new);
	}

	public void setMerchant(boolean mrcht) {
		this.merchant = mrcht;
	}

	public void setServer(Server srv) {
		this.server = srv;
	}

	public void setDead(boolean d) {
		this.dead = d;
	}

	public void setDeathCount(int dc) {
		this.deathCount = dc;
	}

}
