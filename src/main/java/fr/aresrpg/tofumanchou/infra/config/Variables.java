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

	@Configured("internal.passerelle.ip")
	public static String PASSERELLE_IP = "127.0.0.1";
	@Configured("internal.passerelle.port")
	public static int PASSERELLE_PORT = 2727;
	@Configured("internal.server.ip")
	public static String SERVER_IP = "80.239.173.166";
	@Configured("mongo.use_custom_langs")
	public static boolean CUSTOM_LANGS = false;
	@Configured("mongo.ip")
	public static String MONGO_IP = "127.0.0.1";
	@Configured("mongo.port")
	public static int MONGO_PORT = 27017;
	@Configured("mongo.db_name")
	public static String DB_NAME = "dofus";
	@Configured("mongo.auth.user")
	public static String MONGO_USER = "admin";
	@Configured("mongo.auth.pass")
	public static String MONGO_PASS = "admin";
	@Configured("option.balking")
	public static boolean DROP_PKT = true;
	@Configured("data.accounts")
	public static List<PlayerBean> ACCOUNTS = new ArrayList<>();
	@Configured("data.groups")
	public static List<GroupBean> GROUPS = new ArrayList<>();

	private Variables() {

	}

}
