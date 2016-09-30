package com.crawler.controller;

import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.crawler.util.Crawlering;
import com.crawler.util.FileOperate;

public class Crawler01 {
	private static final Crawlering crawler = new  Crawlering();
	private static final FileOperate fileOperate = new FileOperate();
	
	public static void main(String[] args) throws IOException  {
//		HttpClient httpClient = new HttpClient();
//		GetMethod getMethod = new GetMethod("https://www.zhihu.com/");
//		int statusCode = httpClient.executeMethod(getMethod);
//		String HTML = getMethod.getResponseBodyAsString();
//		getMethod.releaseConnection();
		
		
		String loginUrl = "https://www.zhihu.com/";
		String html = crawler.getHtmlByUrl(loginUrl);
		Document doc = Jsoup.parse(html);
		//System.out.println(doc);
		//Elements  ele = doc.getElementsByClass("question_link");
		Elements es=doc.body().select("a");
		for (Iterator it = es.iterator(); it.hasNext();) {
			   Element e = (Element) it.next();
			   System.out.println(e.attr("href"));
		}
		  
//		Element ele = doc.select("a").first();
//		
//		System.out.println(ele);
//		String relHref = ele.attr("href");
//		System.out.println(relHref);
//		//String absHref = doc.attr("abs:href");
	//	FileOperate.saveDataAsText(ele.html());
	}
}
