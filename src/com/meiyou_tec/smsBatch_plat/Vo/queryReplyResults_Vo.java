package com.meiyou_tec.smsBatch_plat.Vo;

import java.io.Serializable;

import com.meiyou_tec.IF.HttpMsg;

public class queryReplyResults_Vo implements HttpMsg, Serializable {

	private String mobile;
	private String uid;
	private String text;
	private String user_name;
	private String reply_time;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String getUser_Name() {
		return user_name;
	}
	public void setUser_Name(String user_name) {
		this.user_name = user_name;
	}
	
	public String getReply_time() {
		return reply_time;
	}
	public void setReply_time(String reply_time) {
		this.reply_time = reply_time;
	}
	
}
