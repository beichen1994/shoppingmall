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
}