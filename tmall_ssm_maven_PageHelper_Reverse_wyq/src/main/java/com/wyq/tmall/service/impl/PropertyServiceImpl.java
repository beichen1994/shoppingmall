package com.wyq.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyq.tmall.mapper.PropertyMapper;
import com.wyq.tmall.pojo.Property;
import com.wyq.tmall.pojo.PropertyExample;
import com.wyq.tmall.service.PropertyService;

@Service
public class PropertyServiceImpl implements PropertyService{
	@Autowired
	PropertyMapper propertyMapper;

	@Override
	public List list(int cid) {
		//查询的时候用到了辅助查询类：PropertyExample
		PropertyExample example =new PropertyExample();
		//表示查询cid字段
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id desc");
        return propertyMapper.selectByExample(example);
	}

	@Override
	public void add(Property p) {
		propertyMapper.insert(p);
	}

	@Override
	public void delete(int id) {
		propertyMapper.deleteByPrimaryKey(id);
		
	}

	@Override
	public Property get(int id) {
		return propertyMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(Property p) {
		propertyMapper.updateByPrimaryKey(p);
		
	}
	
	
}
