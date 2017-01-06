package fr.aresrpg.tofumanchou.infra.config.dao;

import fr.aresrpg.dofus.structures.server.Server;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @since
 */
public class PlayerBean {

	private String accountName;
	private String password;
	private List<PersoBean> persos;

	public PlayerBean(String account, String pass, PersoBean... persos) {
		this.accountName = account;
		this.password = pass;
		this.persos = Arrays.stream(persos).collect(Collectors.toList());
	}

	public PlayerBean() {
	}

	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @param accountName
	 *            the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the persos
	 */
	public List<PersoBean> getPersos() {
		return persos;
	}

	/**
	 * @param persos
	 *            the persos to set
	 */
	public void setPersos(List<PersoBean> persos) {
		this.persos = persos;
	}

	@Override
	public String toString() {
		return "PlayerBean [accountName=" + accountName + ", password=" + password + ", persos=" + persos + "]";
	}

	public static class PersoBean {
		private String pseudo;
		private String server;
		private long uuid;

		/**
		 * @param pseudo
		 * @param server
		 * @param uuid
		 */
		public PersoBean(String pseudo, String server, long uuid) {
			this.pseudo = pseudo;
			this.server = server;
			this.uuid = uuid;
		}

		public PersoBean() {
		}

		/**
		 * @param pseudo2
		 * @param henual
		 */
		public PersoBean(String pseudo, Server server, long uuid) {
			this(pseudo, server.name().toLowerCase(), uuid);
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
		 * @return the server
		 */
		public String getServer() {
			return server;
		}

		/**
		 * @param server
		 *            the server to set
		 */
		public void setServer(String server) {
			this.server = server;
		}

		public Server getDofusServer() {
			return Server.valueOf(getServer().toUpperCase());
		}

		@Override
		public String toString() {
			return "PersoBean [pseudo=" + pseudo + ", server=" + server + "]";
		}

		/**
		 * @return
		 */
		public long getUUID() {
			return uuid;
		}

	}
}
