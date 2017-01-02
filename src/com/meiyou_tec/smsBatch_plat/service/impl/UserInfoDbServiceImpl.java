package com.meiyou_tec.smsBatch_plat.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.meiyou_tec.smsBatch_plat.Vo.queryReplyResults_Vo;
import com.meiyou_tec.smsBatch_plat.Vo.smsSendResults_Vo;
import com.meiyou_tec.smsBatch_plat.Vo.userInfo_Vo;
import com.meiyou_tec.smsBatch_plat.service.ISmsCommitDbService;
import com.meiyou_tec.smsBatch_plat.service.IUserInfoDbService;

@Service("userInfoService")
public class UserInfoDbServiceImpl implements IUserInfoDbService {
	private static Logger logger = Logger.getLogger(UserInfoDbServiceImpl.class);
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<userInfo_Vo> getUserInfo() {
		// TODO Auto-generated method stub
		
		String sql = "select tb_info.login_username, tb_info.login_password, tb_info.user_key, tb_info.category, tb_info.group_id, tb_info.uid from tb_smsbatch_user_info as tb_info";
		
		try {
			
			List<userInfo_Vo> userInfoVoList = jdbcTemplate.query(sql, tempMapper);
			if(userInfoVoList != null && userInfoVoList.size() > 0){
				return userInfoVoList;
			}
		}catch(Exception e) {
			logger.info(String.format("获取用户信息[%s]出错！", sql));
		}
		return null;
		
	}
	
	private ParameterizedRowMapper<userInfo_Vo> tempMapper = new ParameterizedRowMapper<userInfo_Vo>() {
		@Override
		public userInfo_Vo mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			userInfo_Vo userInfoVo = new userInfo_Vo();
			userInfoVo.setLoginUserName(rs.getString("login_username"));
			userInfoVo.setLoginPassword(rs.getString("login_password"));
			userInfoVo.setUserKey(rs.getString("user_key"));
			userInfoVo.setCategory(rs.getString("category"));
			userInfoVo.setGroupId(rs.getString("group_id"));
			userInfoVo.setUid(rs.getString("uid"));
			
			return userInfoVo;
		}
	};
}