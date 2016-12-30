package fr.aresrpg.tofumanchou.domain.data.entity.player;

import fr.aresrpg.tofumanchou.domain.data.Account;
import fr.aresrpg.tofumanchou.domain.data.inventory.Inventory;

/**
 * 
 * @since
 */
public interface Perso extends Player {

	Account getAccount();

	Inventory getInventory();

	void connect();

}
