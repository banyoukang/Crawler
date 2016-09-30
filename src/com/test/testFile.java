package com.test;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

import com.crawler.controller.Crawler01;

public class testFile {
	@Test
	public void testSaveDataAsText() throws IOException{
		Crawler01 crawler = new Crawler01();
		crawler.saveDataAsText("D:/parseZHIHU", "/title.txt", "新字段");
		fail("Not yet implemented");
	}
}
