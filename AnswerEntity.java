package com.aim.questionnaire.dao.entity;

public class AnswerEntity {
	private String id;
	private String answername;
	private String password;
	private String groupname;
	private String username;
	private String isdelete;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAnswername() {
		return answername;
	}
	public void setAnswername(String answername) {
		this.answername = answername;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getIsdelete() {
		return isdelete;
	}
	public void setIsdelete(String isdelete) {
		this.isdelete = isdelete;
	}
	public AnswerEntity(String id, String answername, String password, String groupname, String username,
			String isdelete) {
		super();
		this.id = id;
		this.answername = answername;
		this.password = password;
		this.groupname = groupname;
		this.username = username;
		this.isdelete = isdelete;
	}
	public AnswerEntity() {
	}
	
	public AnswerEntity(AnswerEntity answerEntity) {
		super();
		this.id = answerEntity.getId();
		this.answername = answerEntity.getAnswername();
		this.password = answerEntity.getPassword();
		this.groupname = answerEntity.getGroupname();
		this.username = answerEntity.getUsername();
		this.isdelete = answerEntity.getIsdelete();
	}
	
	
}
