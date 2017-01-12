package fr.aresrpg.tofumanchou.domain.data.enums;

import fr.aresrpg.tofumanchou.infra.data.ManchouCell;

import java.awt.Point;
import java.util.Arrays;

/**
 * 
 * @since
 */
public enum Zaapi {

	BONTA_BANQUE(4308, City.BONTA, new Point(-29, -58)),
	BONTA_BAGRUTTE(4218, City.BONTA, new Point(-32, -60)),
	BONTA_HDV_CHASSEUR(4287, City.BONTA, new Point(-30, -50)),
	BONTA_HDV_BOULANGER(2221, City.BONTA, new Point(-26, -52)),
	BONTA_ZAAP(4263, City.BONTA, new Point(-32, -58)),
	BONTA_MILICE(6159, City.BONTA, new Point(-33, -57)),
	BONTA_HDV_SCULPTEUR(4174, City.BONTA, new Point(-29, -60)),
	BONTA_HOTEL_METIERS(8758, City.BONTA, new Point(-37, -55)),
	BONTA_HDV_PAYSAN(4299, City.BONTA, new Point(-29, -54)),
	BONTA_ATELIER_MINEUR(4180, City.BONTA, new Point(-28, -57)),
	BONTA_HDV_ANIMAUX(8759, City.BONTA, new Point(-36, -55)),
	BONTA_HDV_CORDO(4183, City.BONTA, new Point(-29, -57)),
	BONTA_ATELIER_CORDO(4300, City.BONTA, new Point(-30, -57)),
	BONTA_ATELIER_BIJOU(4217, City.BONTA, new Point(-33, -59)),
	BONTA_HDV_FORGERON(4098, City.BONTA, new Point(-28, -55)),
	BONTA_RUNE(8757, City.BONTA, new Point(-38, -55)),
	BONTA_TOUR_ORDRES(4223, City.BONTA, new Point(-30, -53)),
	BONTA_HDV_PARCHO(8760, City.BONTA, new Point(-36, -56)),
	BONTA_ATELIER_BOULANGER(2214, City.BONTA, new Point(-27, -50)),
	BONTA_HDV_MINEUR(4179, City.BONTA, new Point(-28, -58)),
	BONTA_FERAYEUR(4229, City.BONTA, new Point(-26, -58)),
	BONTA_BRICOLEUR(4232, City.BONTA, new Point(-27, -59)),
	BONTA_ARENE(8478, City.BONTA, new Point(-27, -60)),
	BONTA_TABASSE(4238, City.BONTA, new Point(-32, -51)),
	BONTA_HDV_BIJOUTIER(4216, City.BONTA, new Point(-33, -60)),
	BONTA_HDV_TAILLEUR(4172, City.BONTA, new Point(-30, -56)),
	BONTA_HDV_PECHEUR(4247, City.BONTA, new Point(-35, -55)),
	BONTA_ATELIER_ALCHI(4272, City.BONTA, new Point(-32, -55)),
	BONTA_HDV_ALCHI(4271, City.BONTA, new Point(-33, -55)),
	BONTA_ATELIER_PECHEUR(4250, City.BONTA, new Point(-35, -54)),
	BONTA_HDV_BUCHERON(4178, City.BONTA, new Point(-30, -60)),
	BONTA_ATELIER_TAILLEUR(4106, City.BONTA, new Point(-31, -55)),
	BONTA_ATELIER_SCULPTEUR(4181, City.BONTA, new Point(-30, -59)),
	BONTA_PLACE_MARCHANDE(4259, City.BONTA, new Point(-30, -52)),
	BONTA_FEUBUK(4090, City.BONTA, new Point(-27, -56)),
	BONTA_HDV_RESSOURCE(4262, City.BONTA, new Point(-32, -57)),
	BONTA_ATELIER_BOUCHER(4240, City.BONTA, new Point(-32, -50)),
	BONTA_ATELIER_FORGERON(4074, City.BONTA, new Point(-26, -55)),
	TODO_33(8756, City.BRAKMAR, null),
	TODO_34(8755, City.BRAKMAR, null),
	TODO_35(8493, City.BRAKMAR, null),
	TODO_36(5304, City.BRAKMAR, null),
	TODO_37(5311, City.BRAKMAR, null),
	TODO_38(5277, City.BRAKMAR, null),
	TODO_39(5317, City.BRAKMAR, null),
	TODO_40(4612, City.BRAKMAR, null),
	TODO_41(4618, City.BRAKMAR, null),
	TODO_42(5112, City.BRAKMAR, null),
	TODO_43(4639, City.BRAKMAR, null),
	TODO_44(4637, City.BRAKMAR, null),
	TODO_45(5116, City.BRAKMAR, null),
	TODO_46(5332, City.BRAKMAR, null),
	TODO_47(4579, City.BRAKMAR, null),
	TODO_48(4588, City.BRAKMAR, null),
	TODO_49(4549, City.BRAKMAR, null),
	TODO_50(4562, City.BRAKMAR, null),
	TODO_51(5334, City.BRAKMAR, null),
	TODO_52(5295, City.BRAKMAR, null),
	TODO_53(4646, City.BRAKMAR, null),
	TODO_54(4629, City.BRAKMAR, null),
	TODO_55(4601, City.BRAKMAR, null),
	TODO_56(4551, City.BRAKMAR, null),
	TODO_57(4607, City.BRAKMAR, null),
	TODO_58(4930, City.BRAKMAR, null),
	TODO_59(4622, City.BRAKMAR, null),
	TODO_60(4620, City.BRAKMAR, null),
	TODO_61(4615, City.BRAKMAR, null),
	TODO_62(4595, City.BRAKMAR, null),
	TODO_63(4627, City.BRAKMAR, null),
	TODO_64(4623, City.BRAKMAR, null),
	TODO_65(4604, City.BRAKMAR, null),
	TODO_66(8754, City.BRAKMAR, null),
	TODO_67(8753, City.BRAKMAR, null),
	TODO_68(4630, City.BRAKMAR, null),;

	private int mapid;
	private City city;
	private Point coords;

	private Zaapi(int mapid, City city, Point coords) {
		this.coords = coords;
		this.mapid = mapid;
		this.city = city;
	}

	public static int[] getMapsIds(City city) {
		return Arrays.stream(values()).filter(c -> c.city == city).mapToInt(Zaapi::getMapid).toArray();
	}

	public static Zaapi getWithMap(int mapid) {
		for (Zaapi z : values())
			if (z.getMapid() == mapid) return z;
		return null;
	}

	public int findCellId(ManchouCell[] cells) {
		for (ManchouCell c : cells)
			if (c.isZaapi()) return c.getId();
		return -1;
	}

	/**
	 * @return the coords
	 */
	public Point getCoords() {
		return coords;
	}

	/**
	 * @return the mapid
	 */
	public int getMapid() {
		return mapid;
	}

	/**
	 * @return the city
	 */
	public City getCity() {
		return city;
	}

}
