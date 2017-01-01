package fr.aresrpg.tofumanchou.domain;

import fr.aresrpg.dofus.structures.server.Server;
import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.entity.player.Perso;
import fr.aresrpg.tofumanchou.infra.data.ManchouAccount;
import fr.aresrpg.tofumanchou.infra.data.ManchouPerso;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @since
 */
public class Accounts {

	private static final Accounts instance = new Accounts();
	private final Map<Integer, Account> accounts = new HashMap<>();

	private Accounts() {

	}

	/**
	 * @return the accounts
	 */
	public Map<Integer, Account> getAccounts() {
		return accounts;
	}

	/**
	 * @return the instance
	 */
	public static Accounts getInstance() {
		return instance;
	}

	public static Account getAccount(int id) {
		return instance.accounts.get(id);
	}

	public static Perso getPersoWithAccountId(int id) {
		Account account = instance.accounts.get(id);
		if (account == null) return null;
		return account.getPerso();
	}

	public static Perso getPersoWithPseudo(String pseudo, Server server) {
		for (Account a : instance.accounts.values()) {
			Perso perso = a.getPerso();
			if (perso == null) continue;
			if (perso.getServer() == server && perso.getPseudo().equals(pseudo)) return perso;
		}
		return null;
	}

	public static Account getAccount(String accountName) {
		for (Account a : instance.accounts.values())
			if (a.getAccountName().equals(accountName)) return a;
		return null;
	}

	public static Account registerAccount(String accountname) {
		Account account = instance.createAccount(accountname, "");
		instance.accounts.put(account.getId(), account);
		return account;
	}

	public static Perso registerPerso(String pseudo, String accountname, String password, Server server) {
		Perso perso = instance.createPerso(pseudo, accountname, password, server);
		instance.accounts.put(perso.getAccount().getId(), perso.getAccount());
		return perso;
	}

	private Account createAccount(String accountname, String password) {
		int id = accounts.size();
		return new ManchouAccount(id, null, accountname, password, null);
	}

	private Perso createPerso(String pseudo, String accountname, String password, Server server) {
		Account account = createAccount(accountname, password);
		return new ManchouPerso(account, pseudo, server);
	}

}
