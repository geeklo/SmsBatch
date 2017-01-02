package com.meiyou_tec.smsBatch_plat.Vo;

import java.io.Serializable;

import com.meiyou_tec.IF.HttpMsg;

/**
 * 群发短信的结果VO
 * @author linkang
 * @date 2016-12-15
 * @version 1.0
 */

public class smsSendResultsData_Vo implements HttpMsg, Serializable{
	
	private int code;
	private String msg;
	private int count;
	private float fee;
	private String unit;
	private String mobile;
	private String sid;
	
	public int getCode() {
		return this.code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	public String getMsg() {
		return this.msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public int getCount() {
		return this.count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public float getFee() {
		return this.fee;
	}
	public void setFee(float fee) {
		this.fee = fee;
	}
	
	public String getUnit() {
		return this.unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public String getMobile() {
		return this.mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getSid() {
		return this.sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	
	

}
