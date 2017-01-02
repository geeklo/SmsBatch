package com.meiyou_tec.smsBatch_plat.Vo;

import java.io.Serializable;

import com.meiyou_tec.IF.HttpMsg;

public class queryContentResults_Vo implements HttpMsg, Serializable{

	private String content;
	private String commit_time;
	private int commit_num;
	private int accept_num;
	private String uid;
	private String user_name;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getCommit_time() {
		return commit_time;
	}
	public void setCommit_time(String commit_time) {
		this.commit_time = commit_time;
	}
	
	public int getCommit_num() {
		return commit_num;
	}
	public void setCommit_num(int commit_num) {
		this.commit_num = commit_num;
	}
	
	public int getAccept_num() {
		return accept_num;
	}
	public void setAccept_num(int accept_num) {
		this.accept_num = accept_num;
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
}
