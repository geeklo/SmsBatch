package com.meiyou_tec.smsBatch_plat.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import com.meiyou_tec.smsBatch_plat.Vo.queryDayTimeResults_Vo;
import com.meiyou_tec.smsBatch_plat.Vo.queryMobileResults_Vo;
import com.meiyou_tec.smsBatch_plat.Vo.queryStatus_Vo;
import com.meiyou_tec.smsBatch_plat.service.IQueryDayTimeStatusService;
import com.meiyou_tec.smsBatch_plat.service.IQueryMobileStatusService;

@Service("queryDayTimeStatusService")
public class IQueryDayTimeStatusServiceImpl implements IQueryDayTimeStatusService {

private static Logger logger = Logger.getLogger(IQueryDayTimeStatusServiceImpl.class);
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	

	@Override
	public List<queryDayTimeResults_Vo> queryBatchStatus(queryStatus_Vo query_Vo) {
		// TODO Auto-generated method stub
		
		String query_uid_param;
		if(query_Vo.getUid().endsWith("0000")) {
			query_uid_param = query_Vo.getUid().substring(0, query_Vo.getUid().length()-4) + "%";
		}
		else {
			query_uid_param = query_Vo.getUid();
		}
		
		Object[] param = new Object[]{query_Vo.getStartTime(), query_uid_param};
		String sql = "SELECT date_format(tb_bill.user_receive_time,'%H') time, COUNT(0) count_num FROM ( select distinct uid, sid, user_receive_time from tb_smsbatch_user_bill) AS tb_bill WHERE date_format(tb_bill.user_receive_time,'%Y-%m-%d')= ? and tb_bill.uid LIKE ? GROUP BY date_format(tb_bill.user_receive_time,'%Y-%m-%d %H')";
		
		try {
			
			List<queryDayTimeResults_Vo> dayTimeResultsVoList = jdbcTemplate.query(sql, param, tempMapper);
			if(dayTimeResultsVoList != null && dayTimeResultsVoList.size() > 0){
				return dayTimeResultsVoList;
			}
		}catch(Exception e) {
			logger.info(String.format("��ѯ[%s]������ն��ŵ�����[%s]��������", query_Vo.getQueryMobile(), sql));
		}
	
		return null;
	}
	
	private ParameterizedRowMapper<queryDayTimeResults_Vo> tempMapper = new ParameterizedRowMapper<queryDayTimeResults_Vo>() {
		@Override
		public queryDayTimeResults_Vo mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			queryDayTimeResults_Vo dayTimeResultsVo = new queryDayTimeResults_Vo();
			dayTimeResultsVo.setHur_time(rs.getInt("time"));
			dayTimeResultsVo.setHur_count(rs.getInt("count_num"));
			
			return dayTimeResultsVo;
		}
	};
}
