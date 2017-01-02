package com.meiyou_tec.smsBatch_plat.service;

import com.meiyou_tec.smsBatch_plat.Vo.queryResults_Vo;
import com.meiyou_tec.smsBatch_plat.Vo.queryStatus_Vo;

public interface IQuerySendStatusService {

	public queryResults_Vo queryBatchStatus(queryStatus_Vo results_Vo);
}
