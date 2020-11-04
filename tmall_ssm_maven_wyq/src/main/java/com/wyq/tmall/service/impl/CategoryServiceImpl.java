package com.wyq.tmall.service.impl;
import com.wyq.tmall.mapper.CategoryMapper;

import com.wyq.tmall.pojo.Category;
import com.wyq.tmall.service.CategoryService;
import com.wyq.tmall.util.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

//提供一个支持分页的查询方法list(Page page)和获取总数的方法total。

//注解@Service声明当前类是一个Service类
@Service
public class CategoryServiceImpl  implements CategoryService {
	//通过自动装配@Autowired引入CategoryMapper ，在list方法中调用CategoryMapper 的list方法.
    @Autowired
    CategoryMapper categoryMapper;
    
    @Override
    public List<Category> list(Page page) {
        return categoryMapper.list(page);
    }
 
    @Override
    public int total() {
        return categoryMapper.total();
    }

	@Override
	public void add(Category category) {
		categoryMapper.add(category);
		
	}

	@Override
	public void delete(int id) {
		categoryMapper.delete(id);
		
	}

	@Override
	public Category get(int id) {		
		return categoryMapper.get(id);
	}

	@Override
	public void update(Category category) {
		categoryMapper.update(category);		
	}

	
	
    
	
    
 
}