package com.aim.questionnaire.dao.entity;

public class GroupEntity {
	private String id;
	private String groupname;
	private String username;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String toString() {
		return "GroupEntity [id=" + id + ", groupname=" + groupname + ", username=" + username + "]";
	}
	
	
	

}
