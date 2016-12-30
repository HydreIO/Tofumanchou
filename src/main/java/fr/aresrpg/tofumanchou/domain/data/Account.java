package fr.aresrpg.tofumanchou.domain.data;

import fr.aresrpg.commons.domain.log.Logger;
import fr.aresrpg.tofumanchou.domain.data.entity.player.Perso;
import fr.aresrpg.tofumanchou.domain.data.inventory.Inventory;

import java.net.SocketAddress;

/**
 * 
 * @since
 */
public interface Account {

	int getId();

	SocketAddress getAdress();

	String getAccountName();

	String getPassword();

	Perso getPerso();

	Logger getLogger();

	Inventory getBank();

}
