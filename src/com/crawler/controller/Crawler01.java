package com.crawler.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.crawler.util.Crawlering;
import com.crawler.util.FileOperate;

public class Crawler01 {
	private static final Crawlering crawler = new  Crawlering();
	private static final FileOperate fileOperate = new FileOperate();
	public static void main(String[] args) {
		String loginUrl = "https://www.zhihu.com/";
		String loginHtml = crawler.getHtmlBuUrl(loginUrl);
		Document doc = Jsoup.parse(loginHtml);
		Elements  xx = doc.getElementsByClass("question_link");
System.out.println(xx);
		
		
	}
}
