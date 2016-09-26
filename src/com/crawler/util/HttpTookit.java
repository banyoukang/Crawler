package com.crawler.util;

import java.util.List;
import java.util.Map;


public class HttpTookit {
	 	
	public static String getCookie(Map<String,String> params){
		String url,userName,password;
		if(params == null){
			return "";
		}
		 url = params.get("url");
		 userName = params.get("userName");
		 password = params.get("password");
		 return null;
	} 
	
	/** 
	* @ClassName: HttpTookit 
	* @Description: TODO(Java模拟http get请求) 
	* @author A18ccms a18ccms_gmail_com 
	* @date 2016年9月26日 上午9:16:31 
	*  
	*/
	public static String sendGet(String url,List<String> params){
		return null;
		
	}
	
	/** 
	* @ClassName: HttpTookit 
	* @Description: TODO(java模拟http post请求) 
	* @author A18ccms a18ccms_gmail_com 
	* @date 2016年9月26日 上午9:16:34 
	*  
	*/
	public static String sendPost(String url,List<String> params){
		return null;
	}
}
