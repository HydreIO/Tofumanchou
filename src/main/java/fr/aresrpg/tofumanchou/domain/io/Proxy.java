/*******************************************************************************
 * BotFather (C) - Dofus 1.29
 * This class is part of an AresRPG Project.
 *
 * @author Sceat {@literal <sceat@aresrpg.fr>}
 * 
 *         Created 2016
 *******************************************************************************/
package fr.aresrpg.tofumanchou.domain.io;

import fr.aresrpg.tofumanchou.domain.data.Account;

public interface Proxy {

	Account getClient();

	public static enum ProxyConnectionType {
		LOCAL,
		REMOTE
	}
}
