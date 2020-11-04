package com.wyq.tmall.service;

import java.util.List;

import com.wyq.tmall.pojo.Product;
import com.wyq.tmall.pojo.PropertyValue;

public interface PropertyValueService {
	
	void init(Product p);
	void update(PropertyValue pv);
	
	PropertyValue get(int ptid,int pid);
	List<PropertyValue> list(int pid);
	
}
