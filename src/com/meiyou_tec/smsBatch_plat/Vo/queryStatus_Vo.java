package com.meiyou_tec.smsBatch_plat.Vo;

import java.io.Serializable;

import com.meiyou_tec.IF.HttpMsg;

public class queryStatus_Vo implements HttpMsg, Serializable{
	
	private String start_time;
	private String end_time;
	private String query_content;
	private String query_mobile;
	private String query_uid;

	public String getStartTime() {
		return start_time;
	}
	public void setStartTime(String start_time) {
		this.start_time = start_time;
	}
	
	public String getEndTime() {
		return end_time;
	}
	public void setEndTime(String end_time) {
		this.end_time = end_time;
	}
	
	public String getQueryContent() {
		return query_content;
	}
	public void setQueryContent(String content) {
		this.query_content = content;
	}
	
	public String getQueryMobile() {
		return query_mobile;
	}
	public void setQueryMobile(String mobile) {
		this.query_mobile = mobile;
	}
	
	public String getContent() {
		return query_content;
	}
	public void setContent(String content) {
		this.query_content = content;
	}
	
	public String getUid() {
		return query_uid;
	}
	public void setUid(String query_uid) {
		this.query_uid = query_uid;
	}

}
