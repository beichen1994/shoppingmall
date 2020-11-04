package com.wyq.tmall.mapper;
 
import com.wyq.tmall.pojo.Category;
import com.wyq.tmall.util.Page;

import java.util.List;


//提供一个支持分页的查询方法list(Page page)和获取总数的方法total
public interface CategoryMapper {
	
	//对应CategoryMapper.xml中 id=“list”的sql语句
	public List<Category> list(Page page);
	
	//对应CategoryMapper.xml中 id=“total”的sql语句
	public int total();
	
	//对应CategoryMapper.xml中 id=“add”的sql语句
	void add(Category category);
	
	//对应CategoryMapper.xml中 id=“delete”的sql语句
	void delete(int id);
	
	//对应CategoryMapper.xml中 id=“get”的sql语句
	Category get(int id);
	
	//对应CategoryMapper.xml中 id=“update”的sql语句
	void update(Category category);
}