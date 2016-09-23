package com.crawler.bean;

import java.util.List;
import java.util.Map;

public class Rule {
	
	private int id;
	 
	private Map<String,String> params;//参数
	
	private List<String> filterTagName;//过滤标签

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public List<String> getFilterTagName() {
		return filterTagName;
	}

	public void setFilterTagName(List<String> filterTagName) {
		this.filterTagName = filterTagName;
	}
	
	
}
