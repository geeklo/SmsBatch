package com.meiyou_tec.smsBatch_plat.service;

import com.meiyou_tec.smsBatch_plat.Vo.queryResults_Vo;

public interface ISmsCommitDbService {
	
	public int insertSmsCommit(queryResults_Vo results_Vo);
}
