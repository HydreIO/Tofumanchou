package fr.aresrpg.tofumanchou.domain.data.enums;

/**
 * 
 * @since
 */
public enum PotionType {

	RAPPEL(DofusItems.POTION_DE_RAPPEL),
	BONTA(DofusItems.POTION_DE_CITE_BONTA),
	BRAKMAR(DofusItems.POTION_DE_CITE_BRAKMAR),
	FOYER(DofusItems.POTION_DE_FOYER),
	FOYER_GUILDE(DofusItems.POTION_DE_FOYER_DE_GUILDE),
	ENCLOS_GUILDE(DofusItems.POTION_D_ENCLOS_DE_GUILDE);

	private int itemType;

	private PotionType(int itemType) {
		this.itemType = itemType;
	}

	public int getItemType() {
		return itemType;
	}
}
