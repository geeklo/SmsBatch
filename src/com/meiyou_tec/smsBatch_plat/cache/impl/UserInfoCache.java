package com.meiyou_tec.smsBatch_plat.cache.impl;

import java.util.AbstractMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meiyou_tec.smsBatch_plat.Vo.userInfo_Vo;
import com.meiyou_tec.smsBatch_plat.cache.Cacheable;
import com.meiyou_tec.smsBatch_plat.service.IUserInfoDbService;


@Service("userInfoCache")
public class UserInfoCache implements Cacheable {
	private static Logger logger = Logger.getLogger(UserInfoCache.class);
	
	private static AbstractMap<String, userInfo_Vo> USERINFOMAP = new ConcurrentHashMap<String, userInfo_Vo>();

	@Autowired
	private IUserInfoDbService userInfoDbService;
	
	@Override
	public void run() {
		
		/**获得用户列表*/
		List<userInfo_Vo> userInfoVoList = userInfoDbService.getUserInfo();
		AbstractMap<String, userInfo_Vo> userInfoMap = new ConcurrentHashMap<String, userInfo_Vo>();
		
		if(userInfoVoList != null && userInfoVoList.size() > 0){
			for(userInfo_Vo userInfoVo : userInfoVoList){
				/**获得用户信息*/
				String user_name = userInfoVo.getLoginUserName();
				userInfoMap.put(user_name,userInfoVo);
			}
		}
		
		USERINFOMAP = userInfoMap;
		logger.info(String.format("[userInfoCache]成功加载配置[%d]条!", USERINFOMAP.size()));
	}

	@Override
	public Object getCache() {
		return USERINFOMAP;
	}

}
