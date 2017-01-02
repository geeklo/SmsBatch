package com.meiyou_tec.smsBatch_plat.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import com.meiyou_tec.smsBatch_plat.Vo.queryReplyResults_Vo;
import com.meiyou_tec.smsBatch_plat.Vo.queryStatus_Vo;
import com.meiyou_tec.smsBatch_plat.service.IQueryReplyStatusService;
import com.meiyou_tec.util.BaseUtil;


@Service("queryReplyStatusService")
public class IQueryReplyStatusServiceImpl implements IQueryReplyStatusService{
	
private static Logger logger = Logger.getLogger(IQueryMobileStatusServiceImpl.class);
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<queryReplyResults_Vo> queryBatchStatus(queryStatus_Vo query_Vo) {
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
		String sql = "select distinct tb_reply._sign, tb_reply.mobile, tb_reply.reply_time, tb_reply.text, tb_reply.extend uid, tb_info.login_username user_name from tb_smsbatch_user_reply as tb_reply, tb_smsbatch_user_info as tb_info where tb_reply.reply_time between ? and ? and tb_info.uid = tb_reply.extend and tb_reply.mobile LIKE ? and tb_reply.extend LIKE ? order by tb_reply.reply_time DESC";
		
		logger.info(String.format("[查询用户回复数据库: %s]", BaseUtil.logSQL(sql, param)));
		try {
			List<queryReplyResults_Vo> ReplyResultsVoList = jdbcTemplate.query(sql, param, tempMapper);
			if(ReplyResultsVoList != null && ReplyResultsVoList.size() > 0){
				return ReplyResultsVoList;
			}
		}catch(Exception e) {
			logger.info(String.format("查询用户回复数据库[%s]出错！", BaseUtil.logSQL(sql, param)));
		}
		return null;
	}
	
	private ParameterizedRowMapper<queryReplyResults_Vo> tempMapper = new ParameterizedRowMapper<queryReplyResults_Vo>() {
		@Override
		public queryReplyResults_Vo mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			queryReplyResults_Vo replyResultsVo = new queryReplyResults_Vo();
			replyResultsVo.setUser_Name(rs.getString("user_name"));
			replyResultsVo.setMobile(rs.getString("mobile"));
			replyResultsVo.setReply_time(rs.getString("reply_time"));
			replyResultsVo.setText(rs.getString("text"));
			replyResultsVo.setUid(rs.getString("uid"));
			
			return replyResultsVo;
		}
	};
	
}
