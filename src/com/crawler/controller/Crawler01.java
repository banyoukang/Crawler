package com.crawler.controller;

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
	public static void main(String[] args) {
		String loginUrl = "https://www.zhihu.com/";
		String loginHtml = crawler.getHtmlBuUrl(loginUrl);
		Document doc = Jsoup.parse(loginHtml);
//System.out.println(doc);
		Elements  xx = doc.getElementsByClass("question_link");
		String xx2 = xx.html();
		String xx1 = xx.text();
 
		saveDataAsText("D:/parseZHIHU","/title.txt",xx1);
		
	}
	
	public static void saveDataAsText(String savePath,String fileName,String html){
		//无法创建文件
		if(!StringUtils.isNotEmpty(savePath)||!StringUtils.isNotEmpty(html)){
			return;
		}
		File file1 = new File(savePath);
		if ( !file1.exists()){
			  file1.mkdirs();
			}
		File file2 = new File(savePath+fileName);
		if(!file2.exists()){
			try {
				file2.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			PrintStream ps = new PrintStream(new FileOutputStream(file2));
			ps.append(html);
			ps.flush();
			ps.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
