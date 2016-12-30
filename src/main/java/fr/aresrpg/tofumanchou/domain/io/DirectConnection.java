package fr.aresrpg.tofumanchou.domain.io;

import fr.aresrpg.dofus.protocol.Packet;
import fr.aresrpg.tofumanchou.domain.data.entity.player.Perso;

/**
 * 
 * @since
 */
public interface DirectConnection {

	Perso getPerso();

	void sendPacket(Packet pkt);

}
