package com.meiyou_tec.smsBatch_plat.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.meiyou_tec.smsBatch_plat.Vo.queryResults_Vo;
import com.meiyou_tec.smsBatch_plat.Vo.smsSendResults_Vo;
import com.meiyou_tec.smsBatch_plat.service.ISmsCommitDbService;
import com.meiyou_tec.util.BaseUtil;

@Service("smsCommitService")
public class SmsCommitDbServiceImpl implements ISmsCommitDbService {
	private static Logger logger = Logger.getLogger(SmsCommitDbServiceImpl.class);
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	public int insertSmsCommit(queryResults_Vo results_Vo) {
		// TODO Auto-generated method stub
		
		String sql = "insert into tb_smsbatch_user_commit(commit_num,accept_num,content,uid,status) values(?,?,?,?,?)";
		Object[] param = new Object[]{results_Vo.getCommit_num(),
				results_Vo.getAccept_num(), results_Vo.getContent(), results_Vo.getUid(),
				results_Vo.getStatus()
				};
		int count = jdbcTemplate.update(sql, param);
		logger.info(String.format("[结果录入数据库: %s]", BaseUtil.logSQL(sql, param)));
		return count;
	}
}