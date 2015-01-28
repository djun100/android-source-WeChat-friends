package com.myexample.dynamic.bean;

import java.io.Serializable;

public class Dynamic implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5564165575483463086L;
	
	private String dynamicId;

	public String getDynamicId() {
		return dynamicId;
	}

	public void setDynamicId(String dynamicId) {
		this.dynamicId = dynamicId;
	}

	
	
}
