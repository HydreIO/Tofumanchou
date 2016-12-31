/*******************************************************************************
 * BotFather (C) - Dofus 1.29
 * This class is part of an AresRPG Project.
 *
 * @author Sceat {@literal <sceat@aresrpg.fr>}
 * 
 *         Created 2016
 *******************************************************************************/
package fr.aresrpg.tofumanchou.domain.data.enums;

import fr.aresrpg.dofus.structures.item.Interractable;

/**
 * 
 * @since
 */
public enum Poissons {

	GOUJON(LieuPeche.RIVIERE, Interractable.PETITS_POISSONS_RIVIERE),
	TRUITE(LieuPeche.RIVIERE, Interractable.PETITS_POISSONS_RIVIERE, Interractable.POISSONS_RIVIERE),
	POISSON_CHATON(LieuPeche.RIVIERE, Interractable.PETITS_POISSONS_RIVIERE, Interractable.POISSONS_RIVIERE, Interractable.GROS_POISSONS_RIVIERE),
	BROCHET(LieuPeche.RIVIERE, Interractable.POISSONS_RIVIERE, Interractable.GROS_POISSONS_RIVIERE, Interractable.POISSONS_GEANTS_RIVIERE),
	CARPE_DIEM(LieuPeche.RIVIERE, Interractable.POISSONS_RIVIERE, Interractable.GROS_POISSONS_RIVIERE, Interractable.POISSONS_GEANTS_RIVIERE),
	BAR_RIKAIN(LieuPeche.RIVIERE, Interractable.GROS_POISSONS_RIVIERE, Interractable.POISSONS_GEANTS_RIVIERE),
	PERCHE(LieuPeche.RIVIERE, Interractable.POISSONS_GEANTS_RIVIERE),
	GREU_VETTE(LieuPeche.MER, Interractable.PETITS_POISSONS_MER),
	CRABE_SOURIMI(LieuPeche.MER, Interractable.PETITS_POISSONS_MER, Interractable.POISSONS_MER),
	POISSON_PANE(LieuPeche.MER, Interractable.PETITS_POISSONS_MER, Interractable.POISSONS_MER, Interractable.GROS_POISSONS_MER),
	SARDINE_BRILLANTE(LieuPeche.MER, Interractable.POISSONS_MER, Interractable.GROS_POISSONS_MER, Interractable.POISSONS_GEANTS_MER),
	KRALAMOURE(LieuPeche.MER, Interractable.POISSONS_MER, Interractable.GROS_POISSONS_MER, Interractable.POISSONS_GEANTS_MER),
	RAIE_BLEUE(LieuPeche.MER, Interractable.GROS_POISSONS_MER, Interractable.POISSONS_GEANTS_MER),
	REQUIN_MARTEAU(LieuPeche.MER, Interractable.POISSONS_GEANTS_MER);

	private LieuPeche lieu;
	private Interractable[] ressource;

	private Poissons(LieuPeche lieu, Interractable... ressource) {
		this.ressource = ressource;
		this.lieu = lieu;
	}

	/**
	 * @return the lieu
	 */
	public LieuPeche getLieu() {
		return lieu;
	}

	/**
	 * @return the ressource
	 */
	public Interractable[] getRessource() {
		return ressource;
	}

	public static enum LieuPeche {
		RIVIERE,
		MER
	}

}
