package com.crawler.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.crawler.util.Crawlering;
import com.crawler.util.FileOperate;

public class Crawler01 {
	private static final Crawlering crawler = new  Crawlering();
	private static final FileOperate fileOperate = new FileOperate();
	public static void main(String[] args) throws IOException {
		String loginUrl = "https://www.zhihu.com/";
		String loginHtml = crawler.getHtmlBuUrl(loginUrl);
		Document doc = Jsoup.parse(loginHtml);
		Elements  xx = doc.getElementsByClass("question_link");
		String xx2 = xx.html();
		String xx1 = xx.text();
		saveDataAsText("D:/parseZHIHU","/title.txt",xx2);
		
	}
	
	public static void saveDataAsText(String savePath,String fileName,String html) throws IOException{
		if(!StringUtils.isNotEmpty(savePath)||!StringUtils.isNotEmpty(html)){
			return;
		}
		File file = new File(savePath+fileName);
		 if(!file.exists()){
			 new File(savePath).mkdirs();
			 file.createNewFile();
		 }
		try {
			BufferedWriter  writer = new BufferedWriter(new FileWriter(file , true));
			writer.write(html);
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
