package fr.aresrpg.tofumanchou.infra.config;

import fr.aresrpg.tofumanchou.infra.config.dao.GroupBean;
import fr.aresrpg.tofumanchou.infra.config.dao.PlayerBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @since
 */
public class Variables {

	@Configured("internal.")
	public static String PASSERELLE_IP = "127.0.0.1";
	@Configured("internal.")
	public static int PASSERELLE_PORT = 2727;
	@Configured("data.")
	public static List<PlayerBean> ACCOUNTS = new ArrayList<>();
	@Configured("data.")
	public static List<GroupBean> GROUPS = new ArrayList<>();

	private Variables() {

	}

}
