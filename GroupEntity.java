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
	
	
	public GroupEntity(String id, String groupname, String username) {
		super();
		this.id = id;
		this.groupname = groupname;
		this.username = username;
	}
	public GroupEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public GroupEntity(GroupEntity groupEntity) {
		super();
		this.id = groupEntity.getId();
		this.groupname = groupEntity.getGroupname();
		this.username = groupEntity.getUsername();
	}
}
