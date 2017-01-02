package com.meiyou_tec.smsBatch_plat.Vo;

import java.io.Serializable;

import com.meiyou_tec.IF.HttpMsg;

public class userInfoNode_Vo implements HttpMsg, Serializable{
	
	private String login_username;
	private String category;
	private String uid;

	public String getLoginUserName() {
		return login_username;
	}
	public void setLoginUserName(String login_username) {
		this.login_username = login_username;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getUid() {
		return this.uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}

}
