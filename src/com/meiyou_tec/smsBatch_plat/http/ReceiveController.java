package com.meiyou_tec.smsBatch_plat.http;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.google.gson.Gson;
import com.meiyou_tec.IF.HttpMsg;
import com.meiyou_tec.smsBatch_plat.Vo.queryContentResults_Vo;
import com.meiyou_tec.smsBatch_plat.Vo.queryDayTimeResults_Vo;
import com.meiyou_tec.smsBatch_plat.Vo.queryMobileResults_Vo;
import com.meiyou_tec.smsBatch_plat.Vo.queryReplyResults_Vo;
import com.meiyou_tec.smsBatch_plat.Vo.queryResults_Vo;
import com.meiyou_tec.smsBatch_plat.Vo.queryStatus_Vo;
import com.meiyou_tec.smsBatch_plat.Vo.smsSendResults_Vo;
import com.meiyou_tec.smsBatch_plat.Vo.userInfoNode_Vo;
import com.meiyou_tec.smsBatch_plat.Vo.userInfo_Vo;
import com.meiyou_tec.smsBatch_plat.cache.Cacheable;
import com.meiyou_tec.smsBatch_plat.service.IQueryContentStatusService;
import com.meiyou_tec.smsBatch_plat.service.IQueryDayTimeStatusService;
import com.meiyou_tec.smsBatch_plat.service.IQueryMobileStatusService;
import com.meiyou_tec.smsBatch_plat.service.IQueryReplyStatusService;
import com.meiyou_tec.smsBatch_plat.service.IQuerySendStatusService;
import com.meiyou_tec.smsBatch_plat.service.ISmsCommitDbService;
import com.meiyou_tec.util.HttpClientUtil;
import com.meiyou_tec.util.SpringServiceUtil;
/**
 * 鎺ユ敹璁块棶鐭俊鍒嗗彂缃戝叧鏁版嵁鍏ュ彛
 * @author linkang
 *
 */
@Component("receiveController")
public class ReceiveController implements Controller {
	
	private static Logger logger = Logger.getLogger(ReceiveController.class);
	
	@Autowired
	@Qualifier("userInfoCache")
	private Cacheable userInfoCache;
	
	@Autowired
	private ISmsCommitDbService commitDbService;
	
	@Autowired
	private IQuerySendStatusService querySendStatusService;
	
	@Autowired
	private IQueryMobileStatusService queryMobileStatusService;
	
	@Autowired
	private IQueryContentStatusService queryContentStatusService;
	
	@Autowired
	private IQueryReplyStatusService queryReplyStatusService;
	
	@Autowired
	private IQueryDayTimeStatusService queryDayTimeStatusService;
	
//	@Autowired
//	@Qualifier("serviceLocator")
//	private SpringServiceUtil springServiceUtil;
//	@Autowired
//	@Qualifier("gatewayCache")
//	private Cacheable gatewayCache;
	
	@SuppressWarnings("unchecked")
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/plain;charset=utf-8");
		PrintWriter out = response.getWriter();
		JSONObject json_results = new JSONObject();
//		Map<String, String> results = new HashMap<String, String>();
		
		/**鑾峰緱璇锋眰璺緞*/
		String servletPath = request.getServletPath();
		/**鑾峰緱璇锋眰IP*/
		String ip = request.getRemoteAddr();
		
		/** 瀹氫箟鐧诲綍鎺ュ彛鐨勬牸寮� */
		Pattern login_pattern = Pattern.compile("^/smsBatch_userLogin\\.action$");
		Matcher login_pattern_matcher = login_pattern.matcher(servletPath);
		
		/** 瀹氫箟鐭俊鍙戦�佹帴鍙ｇ殑鏍煎紡 */
		Pattern sendsms_pattern = Pattern.compile("^/smsBatch_sendSms\\.action$");
		Matcher sendsms_matcher = sendsms_pattern.matcher(servletPath);
		
		Pattern querysendsms_pattern = Pattern.compile("^/smsBatch_querySendSms\\.action$");
		Matcher querysendsms_matcher = querysendsms_pattern.matcher(servletPath);
		
		Pattern querycontentsms_pattern = Pattern.compile("^/smsBatch_queryContentSms\\.action$");
		Matcher querycontentsms_matcher = querycontentsms_pattern.matcher(servletPath);
		
		Pattern queryMobilesms_pattern = Pattern.compile("^/smsBatch_queryMobileSms\\.action$");
		Matcher queryMobilesms_matcher = queryMobilesms_pattern.matcher(servletPath);
		
		Pattern queryOneDaySms_pattern = Pattern.compile("^/smsBatch_queryOneDaySms\\.action$");
		Matcher queryOneDaySms_matcher = queryOneDaySms_pattern.matcher(servletPath);
		
		Pattern queryReplySms_pattern = Pattern.compile("^/smsBatch_queryReplySms\\.action$");
		Matcher queryReplySms_matcher = queryReplySms_pattern.matcher(servletPath);
		
		if(login_pattern_matcher.matches()) {
			
			ConcurrentHashMap<String, userInfo_Vo> userInfoMap = (ConcurrentHashMap<String, userInfo_Vo>) userInfoCache.getCache();
			
			String login_name = request.getParameter("user_name");
			String login_password = request.getParameter("password");
			userInfo_Vo userInfoVo = userInfoMap.get(login_name);
			
			if((userInfoVo != null) && (userInfoVo.getLoginPassword().equals(login_password)) ) {
				json_results.put("status", "ok");
				json_results.put("category", userInfoVo.getCategory());
				json_results.put("uid", userInfoVo.getUid());
				
				if(userInfoVo.getCategory().equals("manager")) {
					
					String group_id = userInfoVo.getGroupId();
					JSONArray json_group = new JSONArray();
					
					userInfo_Vo userInfo_node;
					for (Map.Entry<String, userInfo_Vo> entry : userInfoMap.entrySet()) {
						userInfo_node = (userInfo_Vo)entry.getValue();
						if(userInfo_node.getGroupId().equals(group_id)) {
							userInfoNode_Vo node = new userInfoNode_Vo();
							node.setLoginUserName(userInfo_node.getLoginUserName());
							node.setCategory(userInfo_node.getCategory());
							node.setUid(userInfo_node.getUid());
							json_group.add(node);
						}
					}
					json_results.element("Users", json_group);	
				}
				
			}
			else {
				json_results.put("status", "fail");
			}
			logger.info(String.format("登录账号user_name=%s, password=%s]", login_name, login_password));
			
		}
		else if(sendsms_matcher.matches()) {
			
			ConcurrentHashMap<String, userInfo_Vo> userInfoMap = (ConcurrentHashMap<String, userInfo_Vo>) userInfoCache.getCache();
			
			String text = request.getParameter("sms_content");
			String mobile = request.getParameter("sms_mobile");
			String commit_uid = request.getParameter("uid");
			String test_content = request.getParameter("test_content");
			
			userInfo_Vo curr_userInfo = null;
			for (Map.Entry<String, userInfo_Vo> entry : userInfoMap.entrySet()) {
				curr_userInfo = (userInfo_Vo)entry.getValue();
				
				if(curr_userInfo.getUid().equals(commit_uid)) {
					break;
				}
			}
			
			if(curr_userInfo == null) {
				logger.info(String.format("短信扩展号(%s)不对应，短信无法发送！]", commit_uid));
				json_results.put("status", "fail");
				json_results.put("accept_number", 0);
				json_results.put("commit_number", 0);
			}
			else {
			
//				HttpClientUtil httpClientUtil = new HttpClientUtil();
				
//				String sendResults = httpClientUtil.SendSms(curr_userInfo.getUserKey(), mobile, text, commit_uid);
				
				String sendResults = test_content;
				logger.info(String.format("[发送短信接口返回: %s]", sendResults));
				Gson results_gson = new Gson();
				
				smsSendResults_Vo smsSendResultsVo = results_gson.fromJson(sendResults, smsSendResults_Vo.class);
				queryResults_Vo queryResults_vo = new queryResults_Vo();
				queryResults_vo.setContent(text);
				queryResults_vo.setUid(commit_uid);
				queryResults_vo.setCommit_num(mobile.split(",").length);
				queryResults_vo.setAccept_num(smsSendResultsVo.getTotalCount());
				queryResults_vo.setStatus("ok");
				
				int count = commitDbService.insertSmsCommit(queryResults_vo);
				if(count > 0) {
					json_results.put("status", "ok");
					json_results.put("accept_number", smsSendResultsVo.getData().size());
					json_results.put("commit_number", mobile.split(",").length);
				}
				else {
					json_results.put("status", "fail");
					json_results.put("accept_number", 0);
					json_results.put("commit_number", 0);
				}
			}

		}
		else if (querysendsms_matcher.matches()) {
			
			queryStatus_Vo sendStatus_query_vo = new queryStatus_Vo();
			
			String start_time = request.getParameter("start_time");
			String end_time = request.getParameter("end_time");
			String uid = request.getParameter("query_uid");
			
			sendStatus_query_vo.setStartTime(start_time);
			sendStatus_query_vo.setEndTime(end_time);
			sendStatus_query_vo.setUid(uid);
			
			queryResults_Vo queryresults = querySendStatusService.queryBatchStatus(sendStatus_query_vo);
			
			json_results.put("status", queryresults.getStatus());
			json_results.put("start_time", start_time);
			json_results.put("end_time", end_time);
			json_results.put("accept_number", queryresults.getAccept_num());
			json_results.put("arrive_number", queryresults.getUserget_num());
			
		}
		else if (querycontentsms_matcher.matches()){
			
			queryStatus_Vo querycontent_vo = new queryStatus_Vo();
			
			String start_time = request.getParameter("start_time");
			String end_time = request.getParameter("end_time");
			String query_content = request.getParameter("query_content");
			String query_uid = request.getParameter("query_uid");
			
			querycontent_vo.setStartTime(start_time);
			querycontent_vo.setEndTime(end_time);
			querycontent_vo.setContent(query_content);
			querycontent_vo.setUid(query_uid);
			
			List<queryContentResults_Vo> contentResultsVoList = queryContentStatusService.queryBatchStatus(querycontent_vo);
			
			if(contentResultsVoList != null && contentResultsVoList.size() > 0) {
				json_results.put("status", "ok");
			
				JSONArray json_group = new JSONArray();
				for (int i = 0; i < contentResultsVoList.size(); i++) {
					json_group.add(contentResultsVoList.get(i));
				}
				json_results.element("Array", json_group);
			}
			else {
				json_results.put("status", "fail");
				json_results.put("start_time", start_time);
				json_results.put("end_time", end_time);
			}
		}
		else if (queryMobilesms_matcher.matches()){
			
			queryStatus_Vo querymobile_vo = new queryStatus_Vo();
			
			String start_time = request.getParameter("start_time");
			String end_time = request.getParameter("end_time");
			String query_mobile = request.getParameter("query_mobile");
			String query_uid = request.getParameter("query_uid");
			
			querymobile_vo.setStartTime(start_time);
			querymobile_vo.setEndTime(end_time);
			querymobile_vo.setQueryMobile(query_mobile);
			querymobile_vo.setUid(query_uid);
			
			List<queryMobileResults_Vo> mobileResultsVoList = queryMobileStatusService.queryBatchStatus(querymobile_vo);
			
			if(mobileResultsVoList != null && mobileResultsVoList.size() > 0) {
				json_results.put("status", "ok");
			
				JSONArray json_group = new JSONArray();
				for (int i = 0; i < mobileResultsVoList.size(); i++) {
					json_group.add(mobileResultsVoList.get(i));
				}
				json_results.element("Array", json_group);
			}
			else {
				json_results.put("status", "fail");
				json_results.put("start_time", start_time);
				json_results.put("end_time", end_time);
			}
		}
		else if (queryOneDaySms_matcher.matches()){
		
			queryStatus_Vo querydaytime_vo = new queryStatus_Vo();
			
			String date = request.getParameter("date");
			String query_uid = request.getParameter("query_uid");
			querydaytime_vo.setStartTime(date);
			querydaytime_vo.setUid(query_uid);
			
			List<queryDayTimeResults_Vo> mobileResultsVoList = queryDayTimeStatusService.queryBatchStatus(querydaytime_vo);
			
			JSONArray json_group = new JSONArray();
			int list_len = 0;
			int index = 0;
			
			if(mobileResultsVoList != null) {
				list_len = mobileResultsVoList.size();
			}
			for(int i = 0; i < 24; i++){
				
				if((index >= list_len ) || (mobileResultsVoList == null) || (i != mobileResultsVoList.get(index).getHur_time())) {
					json_group.add(0);
				}
				else {
					json_group.add(mobileResultsVoList.get(index).getHur_count());
					index++;
				}
			}
			
			json_results.put("status", "ok");
			json_results.element("sms_hour_num", json_group);
			
		}
		else if (queryReplySms_matcher.matches()){
			
			queryStatus_Vo queryReply_vo = new queryStatus_Vo();
			
			String start_time = request.getParameter("start_time");
			String end_time = request.getParameter("end_time");
			String query_mobile = request.getParameter("query_mobile");
			String query_uid = request.getParameter("query_uid");
			
			queryReply_vo.setStartTime(start_time);
			queryReply_vo.setEndTime(end_time);
			queryReply_vo.setQueryMobile(query_mobile);
			queryReply_vo.setUid(query_uid);
			
			List<queryReplyResults_Vo> replyResultsVoList = queryReplyStatusService.queryBatchStatus(queryReply_vo);
			
			if(replyResultsVoList != null && replyResultsVoList.size() > 0) {
				json_results.put("status", "ok");
			
				JSONArray json_group = new JSONArray();
				for (int i = 0; i < replyResultsVoList.size(); i++) {
					json_group.add(replyResultsVoList.get(i));
				}
				json_results.element("Array", json_group);
			}
			else {
				json_results.put("status", "fail");
				json_results.put("start_time", start_time);
				json_results.put("end_time", end_time);
			}
			
		}
		else {
			json_results.put("status", "fail");
		}
		
		
		writeResponse(out, json_results.toString());
		return null;
	}
	
	private void writeResponse(PrintWriter out, String content){
		try{
			out.write(content);
		}catch(Exception e){
			if(out != null){
				out.write(ExceptionUtils.getFullStackTrace(e));
			}
		}finally{
			if(out != null){
				out.flush();
				out.close();
			}
		}
	}
	
}
