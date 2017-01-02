package com.meiyou_tec.smsBatch_plat.service;

import java.util.List;

import com.meiyou_tec.smsBatch_plat.Vo.queryReplyResults_Vo;
import com.meiyou_tec.smsBatch_plat.Vo.queryStatus_Vo;

public interface IQueryReplyStatusService {
	
	public List<queryReplyResults_Vo> queryBatchStatus(queryStatus_Vo results_Vo);
}
