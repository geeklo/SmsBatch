package com.meiyou_tec.smsBatch_plat.Vo;

import java.io.Serializable;

import com.meiyou_tec.IF.HttpMsg;

public class queryDayTimeResults_Vo implements HttpMsg, Serializable{

	private int hur_time;
	private int hur_count;
	
	public int getHur_time() {
		return hur_time;
	}
	public void setHur_time(int hur_time) {
		this.hur_time = hur_time;
	}
	
	public int getHur_count() {
		return hur_count;
	}
	public void setHur_count(int hur_count) {
		this.hur_count = hur_count;
	}
}
