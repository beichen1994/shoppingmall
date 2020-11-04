package com.wyq.tmall.service;

import java.util.List;

import com.wyq.tmall.pojo.ProductImage;

public interface ProductImageService {
	
	String type_single = "type_single"; //表示单个图片
	String type_detail = "type_detail"; //表示详情图片
	
	void add(ProductImage pi);
	void delete (int id);
	void update(ProductImage pi);
	ProductImage get(int id);
	List list(int pid,String type);
}	
