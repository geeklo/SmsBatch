package com.meiyou_tec.smsBatch_plat.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import com.meiyou_tec.smsBatch_plat.Vo.queryContentResults_Vo;
import com.meiyou_tec.smsBatch_plat.Vo.queryMobileResults_Vo;
import com.meiyou_tec.smsBatch_plat.Vo.queryStatus_Vo;
import com.meiyou_tec.smsBatch_plat.service.IQueryContentStatusService;
import com.meiyou_tec.util.BaseUtil;


@Service("queryContentStatusService")
public class IQueryContentStatusServiceImpl implements IQueryContentStatusService{
	
private static Logger logger = Logger.getLogger(IQueryMobileStatusServiceImpl.class);
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<queryContentResults_Vo> queryBatchStatus(queryStatus_Vo query_Vo) {
		// TODO Auto-generated method stub
		
		String query_uid_param;
		
		if(query_Vo.getUid().endsWith("0000")) {
			query_uid_param = query_Vo.getUid().substring(0, query_Vo.getUid().length()-4) + "%";
		}
		else {
			query_uid_param = query_Vo.getUid();
		}
		Object[] param = new Object[]{query_Vo.getStartTime(),query_Vo.getEndTime(),"%"+query_Vo.getQueryContent()+"%",query_uid_param};
		String sql = "select tb_commit.commit_num, tb_commit.accept_num, tb_commit.uid, tb_info.login_username user_name, tb_commit.content, date_format(tb_commit.add_date, '%y-%m-%d %H:%i:%s') commit_time from tb_smsbatch_user_commit as tb_commit, tb_smsbatch_user_info as tb_info where tb_commit.add_date between ? and ? and tb_info.uid = tb_commit.uid and tb_commit.content LIKE ? and tb_commit.uid LIKE ? order by tb_commit.add_date DESC";
		
		logger.info(String.format("[查询用户提交数据库: %s]", BaseUtil.logSQL(sql, param)));
		
		try {
			
			List<queryContentResults_Vo> contentResultsVoList = jdbcTemplate.query(sql, param, tempMapper);
			if(contentResultsVoList != null && contentResultsVoList.size() > 0){
				return contentResultsVoList;
			}
		}catch(Exception e) {
			logger.info(String.format("查询用户提交数据库[%s]出错！", BaseUtil.logSQL(sql, param)));
		}
		return null;
	}
	
	private ParameterizedRowMapper<queryContentResults_Vo> tempMapper = new ParameterizedRowMapper<queryContentResults_Vo>() {
		@Override
		public queryContentResults_Vo mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			queryContentResults_Vo contentResultsVo = new queryContentResults_Vo();
			contentResultsVo.setCommit_num(rs.getInt("commit_num"));
			contentResultsVo.setAccept_num(rs.getInt("accept_num"));
			contentResultsVo.setUid(rs.getString("uid"));
			contentResultsVo.setUser_Name(rs.getString("user_name"));
			contentResultsVo.setContent(rs.getString("content"));
			contentResultsVo.setCommit_time(rs.getString("commit_time"));
			
			return contentResultsVo;
		}
	};
	
}
