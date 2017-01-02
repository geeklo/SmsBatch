package com.meiyou_tec.smsBatch_plat.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import com.meiyou_tec.smsBatch_plat.Vo.queryResults_Vo;
import com.meiyou_tec.smsBatch_plat.Vo.queryStatus_Vo;
import com.meiyou_tec.smsBatch_plat.service.IQuerySendStatusService;

@Service("querySendStatusService")
public class QuerySendStatusServiceImpl implements IQuerySendStatusService {
	
private static Logger logger = Logger.getLogger(QuerySendStatusServiceImpl.class);
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	public queryResults_Vo queryBatchStatus(queryStatus_Vo query_Vo) {
		// TODO Auto-generated method stub
		queryResults_Vo resluts_vo = new queryResults_Vo();
		String sql;
		Object[] param;
		List<String> result;
		
		try {
			
			String query_uid_param;
			
			if(query_Vo.getUid().endsWith("0000")) {
				query_uid_param = query_Vo.getUid().substring(0, query_Vo.getUid().length()-4) + "%";
			}
			else {
				query_uid_param = query_Vo.getUid();
			}
			param = new Object[]{query_Vo.getStartTime(),query_Vo.getEndTime(), query_uid_param};
			sql = "select SUM(tb_commit.accept_num) count_num from tb_smsbatch_user_commit as tb_commit where tb_commit.add_date between ? and ? and tb_commit.uid LIKE ?";
			
			result = jdbcTemplate.query(sql, param, rowMapper);
			if(result.isEmpty() || result.get(0).isEmpty()) {
				resluts_vo.setAccept_num(0);
			}
			else {
				resluts_vo.setAccept_num(Integer.parseInt(result.get(0)));
			}
			
			sql = "select COUNT(tb_bill.mobile) count_num from ( select distinct mobile, sid, user_receive_time, uid, report_status from tb_smsbatch_user_bill) as tb_bill where tb_bill.user_receive_time between ? and ? and tb_bill.uid LIKE ? and tb_bill.report_status='SUCCESS'";
			
			result = jdbcTemplate.query(sql, param, rowMapper);
			if(result.isEmpty() || result.get(0).isEmpty()) {
				resluts_vo.setUserget_num(0);
			}
			else {
				resluts_vo.setUserget_num(Integer.parseInt(result.get(0)));
			}
				
			resluts_vo.setStatus("ok");
			
		}catch(Exception e) {
			resluts_vo.setStatus("fail");
			resluts_vo.setAccept_num(0);
			resluts_vo.setUserget_num(0);
		}
	
		return resluts_vo;
	}
	
	private ParameterizedRowMapper<String> rowMapper = new ParameterizedRowMapper<String>() {
		@Override
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			String count = rs.getString("count_num");
			return count;
		}
	};
	
	

	
}
