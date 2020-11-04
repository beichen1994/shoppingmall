package com.wyq.tmall.service;
import com.wyq.tmall.pojo.Category;
import com.wyq.tmall.util.Page;

import java.util.List;

public interface CategoryService{
	
	//修改后的list方法
	List<Category> list();
	
	//新增add方法
	void add(Category category);
	
	//新增delete方法
	void delete(int id);
	
	//新增get方法
	Category get(int id);
	
	//新增update方法
	void update(Category category);

}