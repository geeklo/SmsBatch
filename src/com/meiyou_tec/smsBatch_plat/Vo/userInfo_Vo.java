package com.meiyou_tec.smsBatch_plat.Vo;

import java.io.Serializable;

import com.meiyou_tec.IF.HttpMsg;

public class userInfo_Vo implements HttpMsg, Serializable{
	
	private String login_username;
	private String login_password;
	private String user_key;
	private String category;
	private String group_id;
	private String uid;

	public String getLoginUserName() {
		return login_username;
	}
	public void setLoginUserName(String login_username) {
		this.login_username = login_username;
	}
	
	public String getLoginPassword() {
		return login_password;
	}
	public void setLoginPassword(String login_password) {
		this.login_password = login_password;
	}
	
	public String getUserKey() {
		return user_key;
	}
	public void setUserKey(String user_key) {
		this.user_key = user_key;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getGroupId() {
		return this.group_id;
	}
	public void setGroupId(String group_id) {
		this.group_id = group_id;
	}
	
	public String getUid() {
		return this.uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}

}
