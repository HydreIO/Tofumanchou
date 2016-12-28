package fr.aresrpg.tofumanchou.infra.config.dao;

import java.util.*;

/**
 * 
 * @since
 */
public class GroupBean {

	private String label;
	private String chef;
	private List<String> members = new ArrayList<>();

	/**
	 * @param chef
	 * @param members
	 */
	public GroupBean(String label, String chef, String... members) {
		this.chef = chef;
		this.label = label;
		Arrays.stream(members).forEach(this.members::add);
	}

	public GroupBean() {
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
