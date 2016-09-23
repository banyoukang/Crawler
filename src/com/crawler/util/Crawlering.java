package com.crawler.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//核心爬取类
public class Crawlering implements Runnable{
	private Map<String,ArrayList<String>> disallowLinkList = new HashMap<String,ArrayList<String>>();
	private List<String> errorList = new ArrayList<String>();
	private List<String> resultList = new ArrayList<String>();
	boolean caseSensitive=false; 
	boolean limitHost=false; 
	String startUrl;
	int maxUrl;
	String searchString; 
	
	public Crawlering(String startUrl, int maxUrl, String searchString) {
		this.startUrl = startUrl;
		this.maxUrl = maxUrl;
		this.searchString = searchString;
	}

	public List<String> getResult(){
		return resultList;
	}
	
	@Override
	public void run() {
		startCrawler(startUrl,searchString,maxUrl,caseSensitive,limitHost);
	}
	
	public ArrayList<String> startCrawler(String startUrl,String searchString,int maxUrl,
			boolean caseSensitive,boolean limitHost){
		return null;
	}
	
	/** 
	* @Title: verifyUrl 
	* @Description: TODO(验证URL正确性) 
	* @param @param url
	* @param @return    设定文件 
	* @return URL    返回类型 
	* @throws 
	*/
	private URL verifyUrl(String url){
		if(!url.toLowerCase().startsWith("http://")){
			System.out.println("<-------------非合法URL---------->");
			return null;
		}
		URL verifiedUrl = null;
		try {
			 verifiedUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return verifiedUrl;
	}

	/** 
	* @Title: isRebootAllowed 
	* @Description: TODO(验证不能访问的节点) 
	* @param @param checkUrl
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws 
	*/
	private boolean isRebootAllowed(URL checkUrl){
		String host = checkUrl.getHost().toLowerCase();
		System.out.println("主机："+host);
		ArrayList<String> illegalHost = disallowLinkList.get(host);
		if(illegalHost == null){
			illegalHost = new ArrayList<String>();
			try {
				URL robootsUrl = new URL("http://"+host+"roboots.txt");
				BufferedReader reader = new BufferedReader(new InputStreamReader(robootsUrl.openStream()));;
				String lineData;
				while((lineData = reader.readLine())!=null){
					if((lineData.indexOf("Disallow:") == 0)){
						 String disallowPath =lineData.substring("Disallow:".length()); 
						 int commentIndex = disallowPath.indexOf("#");
						 if(commentIndex != -1){
							 disallowPath.substring(0, commentIndex);
						 }
						 disallowPath = disallowPath.trim();
						 illegalHost.add(disallowPath);
					}
				}
				disallowLinkList.put(host, illegalHost);
				reader.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				return true;
			}
		}
		String file = checkUrl.getFile();
		for (int i = 0; i < illegalHost.size(); i++) {
			String disallow = illegalHost.get(i);
			if (file.startsWith(disallow)) {
				return false;
			}
		}
		return true;
	}

	private String downloadPage(URL pageUrl){
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(pageUrl.openStream()));
			String lineData;
			StringBuffer pageBuffer = new StringBuffer();
			while((lineData = reader.readLine())!=null){
				pageBuffer.append(lineData);
			}
			reader.close();
			return pageBuffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	 /** 
	* @Title: removeWwwFromUrl 
	* @Description: TODO(去除www) 
	* @param @param url
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	private String removeWwwFromUrl(String url) {
		    int index = url.indexOf("://www.");
		    if (index != -1) {
		      return url.substring(0, index + 3) +
		        url.substring(index + 7);
		    }
		    return (url);
		  }
	
	
}
