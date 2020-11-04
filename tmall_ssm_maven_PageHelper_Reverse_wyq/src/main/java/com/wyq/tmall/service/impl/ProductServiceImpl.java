package com.wyq.tmall.service.impl;
 
import com.wyq.tmall.mapper.ProductMapper;
import com.wyq.tmall.pojo.Category;
import com.wyq.tmall.pojo.Product;
import com.wyq.tmall.pojo.ProductExample;
import com.wyq.tmall.pojo.ProductImage;
import com.wyq.tmall.service.CategoryService;
import com.wyq.tmall.service.ProductImageService;
import com.wyq.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductImageService productImageService;
 
    
    public void setCategory(Product p){
        int cid = p.getCid();
        Category c = categoryService.get(cid);
        p.setCategory(c);
    }
    
    public void setCategory(List<Product> ps){
        for (Product p : ps)
            setCategory(p);
    }
    
    
    @Override
    public void add(Product p) {
        productMapper.insert(p);
    }
 
    @Override
    public void delete(int id) {
        productMapper.deleteByPrimaryKey(id);
    }
 
    @Override
    public void update(Product p) {
        productMapper.updateByPrimaryKeySelective(p);
    }

    @Override
    public Product get(int id) {
        Product p = productMapper.selectByPrimaryKey(id);
        setCategory(p);
        //在get方法中调用setFirstProductImage(Product p) 为单个产品设置图片
        setFirstProductImage(p);
        return p;
    }
 
    @Override
    public List list(int cid) {
    	
    	//get和list方法都会把取出来的Product对象设置上对应的category
        ProductExample example = new ProductExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id desc");
        List result = productMapper.selectByExample(example);
        setCategory(result);
        //在list方法中调用setFirstProductImage(List<Product> ps) 为多个产品设置图片
        setFirstProductImage(result);
        return result;
    }
    
    @Override
    public void setFirstProductImage(Product p) {
    	//根据pid和图片类型查询出所有的单个图片，然后把第一个取出来放在firstProductImage上
        List<ProductImage> pis = productImageService.list(p.getId(), ProductImageService.type_single);
        if (!pis.isEmpty()) {
            ProductImage pi = pis.get(0);
            p.setFirstProductImage(pi);
        }
    }
 
    public void setFirstProductImage(List<Product> ps) {
        for (Product p : ps) {
            setFirstProductImage(p);
        }
    }
}