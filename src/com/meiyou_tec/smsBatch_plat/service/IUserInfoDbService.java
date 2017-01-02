package com.meiyou_tec.smsBatch_plat.service;

import java.util.List;

import com.meiyou_tec.smsBatch_plat.Vo.userInfo_Vo;

public interface IUserInfoDbService {
	
	public List<userInfo_Vo> getUserInfo();
}
