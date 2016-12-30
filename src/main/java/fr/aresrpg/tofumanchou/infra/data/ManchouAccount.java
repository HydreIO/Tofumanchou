package fr.aresrpg.tofumanchou.infra.data;

import fr.aresrpg.commons.domain.condition.Option;
import fr.aresrpg.commons.domain.log.Logger;
import fr.aresrpg.commons.domain.log.LoggerBuilder;
import fr.aresrpg.dofus.protocol.DofusConnection;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.entity.player.Perso;

import java.net.SocketAddress;

/**
 * 
 * @since
 */
public class ManchouAccount implements Account {

	private int id;
	private SocketAddress adress;
	private String accountName;
	private String pass;
	private Perso perso;
	private Logger logger = new LoggerBuilder(String.valueOf(id)).setUseConsoleHandler(false, true, Option.none(), Option.none()).build();
	private String hc;
	private DofusConnection connection;
	private ManchouBank bank;

	public ManchouAccount(int id, SocketAddress adress, String accountName, String pass, Perso perso) {
		this.id = id;
		this.adress = adress;
		this.accountName = accountName;
		this.pass = pass;
		this.perso = perso;
	}

	/**
	 * @param pass
	 *            the pass to set
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}

	/**
	 * @return the connection
	 */
	public DofusConnection getConnection() {
		return connection;
	}

	/**
	 * @param connection
	 *            the connection to set
	 */
	public void setConnection(DofusConnection connection) {
		this.connection = connection;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param adress
	 *            the adress to set
	 */
	public void setAdress(SocketAddress adress) {
		this.adress = adress;
	}

	/**
	 * @param accountName
	 *            the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * @param perso
	 *            the perso to set
	 */
	public void setPerso(Perso perso) {
		this.perso = perso;
	}

	/**
	 * @param logger
	 *            the logger to set
	 */
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	/**
	 * @return the hc
	 */
	public String getHc() {
		return hc;
	}

	/**
	 * @param hc
	 *            the hc to set
	 */
	public void setHc(String hc) {
		this.hc = hc;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public SocketAddress getAdress() {
		return adress;
	}

	@Override
	public String getAccountName() {
		return accountName;
	}

	@Override
	public String getPassword() {
		return pass;
	}

	@Override
	public Perso getPerso() {
		return perso;
	}

	@Override
	public String toString() {
		return "ManchouAccount [id=" + id + ", adress=" + adress + ", accountName=" + accountName + ", pass=" + pass + ", perso=" + perso + "]";
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public ManchouBank getBank() {
		return this.bank;
	}

}
