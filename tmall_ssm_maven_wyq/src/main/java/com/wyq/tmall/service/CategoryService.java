package com.wyq.tmall.service;
import com.wyq.tmall.pojo.Category;
import com.wyq.tmall.util.Page;

import java.util.List;

//提供一个支持分页的查询方法list(Page page)和获取总数的方法total
public interface CategoryService{

    List<Category> list(Page page);
	int total();
	
	//新增add方法
	void add(Category category);
	
	//新增delete方法
	void delete(int id);
	
	//新增get方法
	Category get(int id);
	
	//新增update方法
	void update(Category category);

}