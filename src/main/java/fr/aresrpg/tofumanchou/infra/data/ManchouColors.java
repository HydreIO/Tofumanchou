package fr.aresrpg.tofumanchou.infra.data;

import fr.aresrpg.tofumanchou.domain.data.entity.EntityColor;

/**
 * 
 * @since
 */
public class ManchouColors implements EntityColor {

	private int color1;
	private int color2;
	private int color3;

	/**
	 * @param color1
	 * @param color2
	 * @param color3
	 */
	public ManchouColors(int color1, int color2, int color3) {
		super();
		this.color1 = color1;
		this.color2 = color2;
		this.color3 = color3;
	}

	@Override
	public int getFirstColor() {
		return color1;
	}

	@Override
	public int getSecondColor() {
		return color2;
	}

	@Override
	public int getThirdColor() {
		return color3;
	}

	@Override
	public String toString() {
		return "ManchouColors [color1=" + color1 + ", color2=" + color2 + ", color3=" + color3 + "]";
	}

}
