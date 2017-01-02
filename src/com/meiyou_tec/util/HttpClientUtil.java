package com.meiyou_tec.util;

import java.util.Map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List; 
import java.util.Map.Entry;  
import org.apache.http.HttpEntity;  
import org.apache.http.HttpResponse;  
import org.apache.http.NameValuePair;  
import org.apache.http.client.HttpClient;  
import org.apache.http.client.entity.UrlEncodedFormEntity;  
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.message.BasicNameValuePair;  
import org.apache.http.util.EntityUtils;

import com.meiyou_tec.smsBatch_plat.Vo.smsSendResults_Vo;

public class HttpClientUtil {
	
	public String SendSms(String apikey, String mobile, String text, String uid) {
		   
		String post_url = "https://sms.yunpian.com/v2/sms/batch_send.json";
		String charset = "utf-8";
		
		Map<String, String> params = new HashMap<String, String>();//请求参数集合
	    params.put("apikey", apikey);
		params.put("text", text);
	    params.put("mobile", mobile);
	    params.put("extend", uid);
	    params.put("uid", uid);
	    
	    String result = doPost(post_url,params,charset);//请自行使用post方式请求,可使用Apache HttpClient
	   
	    return result;
	    
	}
	
	 private String doPost(String url,Map<String,String> map,String charset){  
	        HttpClient httpClient = null;  
	        HttpPost httpPost = null;  
	        String result = null;  
	        try{  
	            httpClient = new SSLClient();  
	            httpPost = new HttpPost(url);  
	            //设置参数  
	            List<NameValuePair> list = new ArrayList<NameValuePair>();  
	            Iterator iterator = map.entrySet().iterator();  
	            while(iterator.hasNext()){  
	                Entry<String,String> elem = (Entry<String, String>) iterator.next();  
	                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));  
	            }  
	            if(list.size() > 0){  
	                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);  
	                httpPost.setEntity(entity);  
	            }  
	            HttpResponse response = httpClient.execute(httpPost);  
	            if(response != null){  
	                HttpEntity resEntity = response.getEntity();  
	                if(resEntity != null){  
	                    result = EntityUtils.toString(resEntity,charset);  
	                }  
	            }  
	        }catch(Exception ex){  
	            ex.printStackTrace();  
	        }  
	        return result;  
	    }
}
