package com.myexample.dynamic.bean;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jialn 用户_bean（个人全部字段）
 */
public class User implements Serializable {
	private Long userId;
 
	private String name;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	 
	
	 

}
