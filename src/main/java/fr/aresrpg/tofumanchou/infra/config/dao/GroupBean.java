package fr.aresrpg.tofumanchou.infra.config.dao;

import fr.aresrpg.dofus.structures.server.Server;

import java.util.*;

/**
 * 
 * @since
 */
public class GroupBean {

	private String label;
	private String chef;
	private String server;
	private List<String> members = new ArrayList<>();

	/**
	 * @param chef
	 * @param members
	 */
	public GroupBean(String label, Server srv, String chef, String... members) {
		this.chef = chef;
		this.label = label;
		this.server = srv.name().toLowerCase();
		Arrays.stream(members).forEach(this.members::add);
	}

	public GroupBean() {
	}

	/**
	 * @return the server
	 */
	public String getServer() {
		return server;
	}

	/**
	 * @param server
	 *            the server to set
	 */
	public void setServer(Server server) {
		this.server = server.name().toLowerCase();
	}

	public Server getServerObject() {
		return Server.valueOf(server.toUpperCase());
	}

	public GroupBean addMember(String... members) {
		Arrays.stream(members).forEach(this.members::add);
		return this;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the chef
	 */
	public String getChef() {
		return chef;
	}

	/**
	 * @param chef
	 *            the chef to set
	 */
	public void setChef(String chef) {
		this.chef = chef;
	}

	/**
	 * @return the members
	 */
	public List<String> getMembers() {
		return members;
	}

	/**
	 * @param members
	 *            the members to set
	 */
	public void setMembers(List<String> members) {
		this.members = members;
	}

	@Override
	public String toString() {
		return "GroupBean [chef=" + chef + ", members=" + members + "]";
	}

}
