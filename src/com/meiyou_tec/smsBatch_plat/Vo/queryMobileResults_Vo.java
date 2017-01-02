package com.meiyou_tec.smsBatch_plat.Vo;

import java.io.Serializable;

import com.meiyou_tec.IF.HttpMsg;

public class queryMobileResults_Vo implements HttpMsg, Serializable {

	private String mobile;
	private String uid;
	private String user_name;
	private String report_status;
	private String user_receive_time;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getStatus() {
		return report_status;
	}
	public void setStatus(String report_status) {
		this.report_status = report_status;
	}
	
	public String getUser_receive_time() {
		return user_receive_time;
	}
	public void setUser_receive_time(String user_receive_time) {
		this.user_receive_time = user_receive_time;
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
