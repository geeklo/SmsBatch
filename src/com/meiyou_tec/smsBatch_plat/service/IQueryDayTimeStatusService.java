package com.meiyou_tec.smsBatch_plat.service;

import java.util.List;

import com.meiyou_tec.smsBatch_plat.Vo.queryDayTimeResults_Vo;
import com.meiyou_tec.smsBatch_plat.Vo.queryStatus_Vo;

public interface IQueryDayTimeStatusService {

	public List<queryDayTimeResults_Vo> queryBatchStatus(queryStatus_Vo results_Vo);
}
