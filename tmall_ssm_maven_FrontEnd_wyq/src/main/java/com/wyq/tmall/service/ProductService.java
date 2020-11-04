package com.wyq.tmall.service;
  
import java.util.List;
 
import com.wyq.tmall.pojo.Category;
import com.wyq.tmall.pojo.Product;
 
public interface ProductService {
    void add(Product p);
    void delete(int id);
    void update(Product p);
    Product get(int id);
    List list(int cid);
    
    //用于产品页面显示一张图片
    void setFirstProductImage(Product p);
    
    //为多个分类填充产品集合
    void fill(List<Category> cs);
    
    // 为分类填充产品集合
    void fill(Category c);
 

    //为多个分类填充推荐产品集合，即把分类下的产品集合，按照8个为一行，拆成多行，以利于后续页面上进行显示
    void fillByRow(List<Category> cs);
    
    //增加为产品设置销量和评价数量的方法
    void setSaleAndReviewNumber(Product p);
    void setSaleAndReviewNumber(List<Product> ps);
    
    //增加search方法
    List<Product> search(String keyword);
}