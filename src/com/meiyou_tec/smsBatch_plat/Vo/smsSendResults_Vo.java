package com.meiyou_tec.smsBatch_plat.Vo;

import java.io.Serializable;
import java.util.List;

import com.meiyou_tec.IF.HttpMsg;

/**
 * 群发短信的结果VO
 * @author linkang
 * @date 2016-12-15
 * @version 1.0
 */

public class smsSendResults_Vo implements HttpMsg, Serializable{
	
	private int total_count;
	private String total_fee;
	private String unit;
	private List<smsSendResultsData_Vo> data;
	
	public int getTotalCount() {
		return this.total_count;
	}
	public void setTotalCount(int total_count) {
		this.total_count = total_count;
	}
	
	public String getTotalFee() {
		return this.total_fee;
	}
	public void setTotalFee(String total_fee) {
		this.total_fee = total_fee;
	}
	
	public String getUnit() {
		return this.unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public List<smsSendResultsData_Vo> getData() {
		return this.data;
	}
	public void setData(List<smsSendResultsData_Vo> data) {
		this.data = data;
	}

}
