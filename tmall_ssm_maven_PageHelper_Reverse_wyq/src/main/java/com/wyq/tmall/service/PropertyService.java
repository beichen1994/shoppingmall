package com.wyq.tmall.service;
import com.wyq.tmall.pojo.Property;

import java.util.List;

public interface PropertyService{
	
	//修改后的list方法
	List list(int cid);
	
	//新增add方法
	void add(Property p);
	
	//新增delete方法
	void delete(int id);
	
	//新增get方法
	Property get(int id);
	
	//新增update方法
	void update(Property p);

}