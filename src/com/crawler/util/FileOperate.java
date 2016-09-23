package com.crawler.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.crawler.bean.SaveFile;

public class FileOperate {
	
	/** 
	* @Title: getFileByPath 
	* @Description: TODO(通过文件路径获取文件) 
	* @param @param filePath
	* @param @return    设定文件 
	* @return File    返回类型 
	* @throws 
	*/
	public static String getStringByPath(String filePath){
		String resultStr = "";
		if(!StringUtils.isNotEmpty(filePath)){
			System.out.println("<---------文件路径为空！------------->");
			return null;
		}
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"utf-8"));
			String tempLine = "";
			while((tempLine = reader.readLine())!=null){
				resultStr += tempLine;
			}
			reader.close();
		} catch (UnsupportedEncodingException | FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultStr;
	}
	
	
	/** 
	* @Title: getDataFromFile 
	* @Description: TODO(从文件中读取相关数据) 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void getDataFromFile(String filePath){
		if(!StringUtils.isNotEmpty(filePath)){
			System.out.println("<---------文件路径为空！------------->");
			return;
		}
		File file=new File(filePath);
		if(file.isFile() && file.exists()){ 
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8")); 
				String lineData = null;
				while((lineData = reader.readLine()) != null){
					System.out.println(lineData);
				}
				reader.close();
			} catch (UnsupportedEncodingException | FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}else{
			System.out.println("<------------文件不存在-----------》");
			return;
		}
	}
	
	/** 
	* @Title: saveData 
	* @Description: TODO(将数据保存成文件) 
	* @param @param saveFiles    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void saveData(List<SaveFile> saveFiles,String saveType){
		String savePath = SaveFile.savePath;
		if(!StringUtils.isNotEmpty(savePath)){
			System.out.println("<---------保存路径为空！---------------->");
			return;
		}
		for( SaveFile tempFile : saveFiles){
			File file = new File(savePath+tempFile.getFileName()+tempFile.getSuffix());
			file.mkdirs();
			try {
				FileWriter writer = new FileWriter(file);
				switch (saveType) {
					case SaveFile.saveType_title:
						writer.write(tempFile.getTitle());
						break;
					case SaveFile.saveType_head:
						writer.write(tempFile.getHead());		
						break;
					case SaveFile.saveType_body:
						writer.write(tempFile.getBody());
						break;
					case SaveFile.saveType_content:
						writer.write(tempFile.getContent());
						break;
					default:
						break;
				}
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
