package com.crawler.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Test_BaiduIndex {
	public static void main(String[] args) {
		System.out.println(getHtmlFromBaiduIndex());
	}
	
	private static String getHtmlFromBaiduIndex(){
		StringBuilder result = new StringBuilder();
		BufferedReader in ;
		try{
		URL realUrl = new URL("http://www.baidu.com");
		URLConnection connection = realUrl.openConnection();
		connection.connect();
	    in = new BufferedReader(new InputStreamReader(
			     connection.getInputStream()));
	   // 用来临时存储抓取到的每一行的数据
	    String line;
	    while ((line = in.readLine()) != null) {
	     //遍历抓取到的每一行并将其存储到result里面
		     result.append(line);
	    }
	    in.close();
		}catch(Exception e){
			e.printStackTrace();
		} 
		return result.toString();
	}
}
