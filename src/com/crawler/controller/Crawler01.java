package com.crawler.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.crawler.util.Crawlering;
import com.crawler.util.FileOperate;

public class Crawler01 {
	private static final Crawlering crawler = new  Crawlering();
	private static final FileOperate fileOperate = new FileOperate();
	private static int tempFlag = 0;
	
	public static void main(String[] args) throws IOException  {
//		HttpClient httpClient = new HttpClient();
//		GetMethod getMethod = new GetMethod("https://www.zhihu.com/");
//		int statusCode = httpClient.executeMethod(getMethod);
//		String HTML = getMethod.getResponseBodyAsString();
//		getMethod.releaseConnection();
		getHtmlByUrl();
		
	}
	
	private static void getHtmlByUrl() throws IOException{
		String url = "https://www.zhihu.com/";
		String html = crawler.getHtmlByUrl(url);
		Document doc = Jsoup.parse(html);
		Elements  ele = doc.getElementsByClass("question_link");
		for (Iterator it = ele.iterator(); it.hasNext();) {
			   Element e = (Element) it.next();
			   System.out.println(e.text());
		}
		FileOperate.saveDataAsText(ele.html());
	//	requestNextPage();
		if(tempFlag<10){
			getHtmlByUrl();
			tempFlag++;
		}
	}
	
	private static void requestNextPage() throws IOException{
		String url = "https://www.zhihu.com/node/TopStory2FeedList";
		Map<String,String> params = new HashMap<String,String>();
		params.put("offset", "30");
		params.put("start", "0");
		System.out.println(crawler.httpRequest(url, params,"GBK",true));
	}
}
