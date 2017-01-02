package com.meiyou_tec.smsBatch_plat.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import com.meiyou_tec.smsBatch_plat.Vo.queryMobileResults_Vo;
import com.meiyou_tec.smsBatch_plat.Vo.queryStatus_Vo;
import com.meiyou_tec.smsBatch_plat.service.IQueryMobileStatusService;
import com.meiyou_tec.util.BaseUtil;

@Service("queryMobileStatusService")
public class IQueryMobileStatusServiceImpl implements IQueryMobileStatusService{
	
	private static Logger logger = Logger.getLogger(IQueryMobileStatusServiceImpl.class);
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<queryMobileResults_Vo> queryBatchStatus(queryStatus_Vo query_Vo) {
		// TODO Auto-generated method stub
		
		String query_mobile_param, query_uid_param;
		
		if((query_Vo.getQueryMobile() == "") || (query_Vo.getQueryMobile() == null)) {
			query_mobile_param = "%";
		}
		else {
			query_mobile_param = "%"+query_Vo.getQueryMobile()+"%";
		}
		
		if(query_Vo.getUid().endsWith("0000")) {
			query_uid_param = query_Vo.getUid().substring(0, query_Vo.getUid().length()-4) + "%";
		}
		else {
			query_uid_param = query_Vo.getUid();
		}
		
		Object[] param = new Object[]{query_Vo.getStartTime(),query_Vo.getEndTime(), query_mobile_param, query_uid_param};
		String sql = "select distinct tb_bill.sid, tb_bill.mobile, tb_bill.user_receive_time, tb_bill.report_status, tb_bill.uid, tb_info.login_username user_name from tb_smsbatch_user_bill as tb_bill, tb_smsbatch_user_info as tb_info where tb_bill.user_receive_time between ? and ? and tb_bill.uid = tb_info.uid and tb_bill.mobile LIKE ? and tb_bill.uid LIKE ? order by tb_bill.user_receive_time DESC";
		
		logger.info(String.format("[按号码查询用户接收数据库: %s]", BaseUtil.logSQL(sql, param)));
		try {
			
			List<queryMobileResults_Vo> mobileResultsVoList = jdbcTemplate.query(sql, param, tempMapper);
			if(mobileResultsVoList != null && mobileResultsVoList.size() > 0){
				return mobileResultsVoList;
			}
		}catch(Exception e) {
			logger.info(String.format("[按号码查询用户接收数据库: %s出错]", BaseUtil.logSQL(sql, param)));
		}
	
		return null;
	}
	
	private ParameterizedRowMapper<queryMobileResults_Vo> tempMapper = new ParameterizedRowMapper<queryMobileResults_Vo>() {
		@Override
		public queryMobileResults_Vo mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			queryMobileResults_Vo mobileResultsVo = new queryMobileResults_Vo();
			mobileResultsVo.setMobile(rs.getString("mobile"));
			mobileResultsVo.setUser_receive_time(rs.getString("user_receive_time"));
			mobileResultsVo.setStatus(rs.getString("report_status"));
			mobileResultsVo.setUid(rs.getString("uid"));
			mobileResultsVo.setUser_Name(rs.getString("user_name"));
			
			
			return mobileResultsVo;
		}
	};

}
