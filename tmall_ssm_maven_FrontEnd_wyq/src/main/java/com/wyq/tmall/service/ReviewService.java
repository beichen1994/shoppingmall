package com.wyq.tmall.service;
  
import java.util.List;
 
import com.wyq.tmall.pojo.Review;
 
public interface ReviewService {
      
    void add(Review c);
    void delete(int id);
    void update(Review c);
    Review get(int id);

    //通过产品获取评价方法
    List list(int pid);
    int getCount(int pid);
}