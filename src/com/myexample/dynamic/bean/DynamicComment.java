package com.myexample.dynamic.bean;

import java.io.Serializable;

public class DynamicComment implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4609309963793818156L;
	private String commendId;
	private String userId;
	private String nickname;
	private String content;
	private String reuserId="-1";
	private String reuserName;
	public String getCommendId() {
		return commendId;
	}
	public void setCommendId(String commendId) {
		this.commendId = commendId;
	}
	 
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReuserId() {
		return reuserId;
	}
	public void setReuserId(String reuserId) {
		this.reuserId = reuserId;
	}
	public String getReuserName() {
		return reuserName;
	}
	public void setReuserName(String reuserName) {
		this.reuserName = reuserName;
	}
	
	
	
}
