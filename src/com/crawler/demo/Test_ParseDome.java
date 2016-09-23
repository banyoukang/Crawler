package com.crawler.demo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.crawler.util.FileOperate;

public class Test_ParseDome {
	public static void main(String[] args) {
		String resource = FileOperate.getStringByPath("F:/网页文件/test.html");
		Document doc = Jsoup.parse(resource);
		System.out.println(doc.getElementById("MyCities"));
	}
}
