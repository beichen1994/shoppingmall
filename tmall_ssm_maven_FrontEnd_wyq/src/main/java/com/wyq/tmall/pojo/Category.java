package com.wyq.tmall.pojo;

import java.util.List;

public class Category {
    private Integer id;

    private String name;
    
    List<Product> products;
    List<List<Product>> productsByRow;
    //是为了在首页竖状导航的分类名称右边显示推荐产品列表。

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<List<Product>> getProductsByRow() {
		return productsByRow;
	}

	public void setProductsByRow(List<List<Product>> productsByRow) {
		this.productsByRow = productsByRow;
	}
    
    
    
}