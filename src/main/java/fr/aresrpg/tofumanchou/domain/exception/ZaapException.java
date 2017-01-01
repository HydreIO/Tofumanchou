package fr.aresrpg.tofumanchou.domain.exception;

import fr.aresrpg.tofumanchou.domain.data.enums.Zaap;

/**
 * 
 * @since
 */
public class ZaapException extends Exception {

	private ZaapErrorCause cause;
	private Zaap taken, dest;

	public ZaapException(ZaapErrorCause cause, Zaap taken, Zaap dest) {
		super("Impossible d'utiliser ce Zaap | " + cause.getMsg() + " Zaap:" + taken + "|Destination:" + dest);
		this.cause = cause;
		this.taken = taken;
		this.dest = dest;
	}

	/**
	 * @return the taken
	 */
	public Zaap getTaken() {
		return taken;
	}

	/**
	 * @return the dest
	 */
	public Zaap getDest() {
		return dest;
	}

	/**
	 * @return the cause
	 */
	public ZaapErrorCause getZaapCause() {
		return cause;
	}

	public static enum ZaapErrorCause {
		UNKNOW_ZAAP("Le Zaap de destination est inconnu"),
		NO_MONEY("Le Zaap est trop cher");
		private String msg;

		private ZaapErrorCause(String msg) {
			this.msg = msg;
		}

		/**
		 * @return the msg
		 */
		public String getMsg() {
			return msg;
		}
	}

}
