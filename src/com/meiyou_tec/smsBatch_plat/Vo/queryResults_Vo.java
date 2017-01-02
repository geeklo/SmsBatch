package com.meiyou_tec.smsBatch_plat.Vo;

import java.io.Serializable;

import com.meiyou_tec.IF.HttpMsg;

public class queryResults_Vo implements HttpMsg, Serializable{
	
	private String status;
	private String content;
	private String uid;
	private int commit_num;
	private int accept_num;
	private int userget_num;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getContent() {
		return this.content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getUid() {
		return this.uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public int getCommit_num() {
		return this.commit_num;
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
	
	public int getUserget_num() {
		return userget_num;
	}
	public void setUserget_num(int userget_num) {
		this.userget_num = userget_num;
	}

}
