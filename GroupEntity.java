package com.aim.questionnaire.dao.entity;

public class GroupEntity {

	private String id;
	private String groupname;
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
	public GroupEntity(String id, String groupname) {
		super();
		this.id = id;
		this.groupname = groupname;
	}
	public GroupEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public GroupEntity(GroupEntity groupEntity) {
		super();
		this.id = groupEntity.getId();
		this.groupname = groupEntity.getGroupname();

	}
}
