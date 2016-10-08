package com.crawler.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

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
	
	public Crawlering() {
		super();
	}

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
		if(!(url.toLowerCase().startsWith("http://")||url.toLowerCase().startsWith("https://"))){
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
	
	public String getHtmlByUrl(String url){
		URL queryUrl = verifyUrl(url);
		StringBuilder result;
		if(queryUrl == null){
			return null;
		}
		HttpURLConnection resumeConnection;
		try {
			StringBuffer cookie = new StringBuffer();
			cookie.append("q_c1=6523909093114d16924b3e2f39a2e6d4|1474604722000|1474604722000; _xsrf=726a2d961d4b3e262b6ef4ec583f628d; d_c0=\"AECA5UZmlQqPTkeIPIhVp1P1Exs_HqR16LA=|1474604723\";");
			cookie.append("_zap=262d6cc6-c39f-4968-b17e-626e88683e87; _za=dd8e8838-fd04-44da-8e5a-60bf5428e079; l_n_c=1; __utma=51854390.2070989570.1474604657.1474604657.1474607792.2;");
			cookie.append("__utmc=51854390; __utmz=51854390.1474607792.2.2.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; ");
			cookie.append("__utmv=51854390.000--|2=registration_date=20151130=1^3=entry_date=20160923=1; ");
			cookie.append("l_cap_id=\"NWNmODJiNTFlOGY5NDM2MDhmZDA5OTE1OWY1YzBjZTE=|1474611983|d50c898dfd0b395a4caa42328cf1cd1a367c098e\"; ");
			cookie.append("cap_id=\"NGRkYjdhNzBhMTEzNDAyMWEyOTU3ZWNiODIxYTQwOTM=|1474611983|4ca7041b0c91b00b066a4d0c0ff39214a8a1fca1\"; ");
			cookie.append("login=\"NGVkZDY2YzVhNWI3NDgxMTk1MTJkN2ZjOGYzOThjZWU=|1474612000|c5a93de6a4353c6e97c43e6fe038a989689ff2ec\"; ");
			cookie.append("a_t=\"2.0ABCKaFDzFQkXAAAAIFgMWAAQimhQ8xUJAECA5UZmlQoXAAAAYQJVTSBYDFgASdssqTEScyOF-hqYmtvpZZe5-tQe0YxcemDhoUsQ5Uqi7wOWSm1DHw==\"; ");
			cookie.append("z_c0=Mi4wQUJDS2FGRHpGUWtBUUlEbFJtYVZDaGNBQUFCaEFsVk5JRmdNV0FCSjJ5eXBNUkp6STRYNkdwaWEyLWxsbDduNjFB|1474612000|91434ba539ae0f746b5539a702d70fa475c2cf4a");
			resumeConnection = (HttpURLConnection) queryUrl.openConnection();
			resumeConnection.setRequestProperty("Accept-Charset","utf-8");  
	        resumeConnection.setRequestProperty("Content-Type","text/html;utf-8");  
	        resumeConnection.setRequestProperty("Cookie",cookie.toString());  
	     //   resumeConnection.setRequestProperty("Cookie","PortalAuth=77u/PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiPz48U1A+MCMuZnxpaWNwfDAwMDgyMzkwNSwwIy5mfGlpY3B8MDAwODIzOTA1LDEzMDc2NzA1NzM3NTI3MDY4NCxUcnVlLFFldU1Fa2xDelI0bEZaTTJkbVVtZGxPVmhsUVdwQWMzQlk2TCtWdlVOb1ZsRjVHZ1BMRVhMTTAwcHBKWW5WTGZLYzFPTTh2aGRydmRIVWVLR3JOb255dWpTS2lMeEhyQUlBbmtYZTVBTWlFVGpFMlF4bzRjWVRKeEhjNU5ScEhMSWJOWHdWckFTWHhuNUd5bURST0xTK2d3cUFWbThFUllPM3J1enR4aGgwT1VrTDJGMGkrUDdWcHViRm84blFrTXp4MFNyMXdtQzE3UEJkcGpGVU1nOW8xRkJoeHhzWElDdHhLVEpVSHRGMmpDNmNKS285bGJtTXZJZnlwR0k1VGpLd29TTUpaenhyb1BkQ3VOVW13Wk01T0ZEUExSK1lqajVCRitJSFc1enV0UlpXM08wWHhNaldIWk1nWHhncjF0dUc1b3E3RlRwOGhCMFVCWjAydDlGQT09LGh0dHA6Ly9vYS5zZGMuaWNiYy88L1NQPg==");  
	        resumeConnection.connect();  
	        InputStream urlStream = resumeConnection.getInputStream();  
	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlStream,"utf-8"));
	        String ss = null;  
	        result = new StringBuilder();  
	        while ((ss = bufferedReader.readLine()) != null) {  
	        	result.append(ss);  
	        }  
	        bufferedReader.close();       
	        resumeConnection.disconnect();  
	        return result.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String httpRequest(String url, Map<String, String> params,String charset, boolean pretty) throws IOException {
		URL queryUrl = verifyUrl(url);
		StringBuffer response = new StringBuffer();
		HttpClient client = new HttpClient();
		client.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
		HttpMethod method = new PostMethod(queryUrl.toString());
		if (params != null) {
			HttpMethodParams p = new HttpMethodParams();
			for (Map.Entry<String, String> entry : params.entrySet()) {
				p.setParameter(entry.getKey(), entry.getValue());
			}
			method.setParams(p);
		}
		try {
			client.executeMethod(method);
System.out.println(method.getStatusCode());
			if (method.getStatusCode() == HttpStatus.SC_OK) {
				System.out.println("输出结构："+method.getResponseBodyAsString());
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(method.getResponseBodyAsStream(),
								charset));
				String line;
				while ((line = reader.readLine()) != null) {
					if (pretty)
						response.append(line).append(
								System.getProperty("line.separator"));
					else
						response.append(line);
				}
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}

		return response.toString();
	}
	
}
