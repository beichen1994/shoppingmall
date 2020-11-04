package com.wyq.tmall.service;
  
import java.util.List;
 
import com.wyq.tmall.pojo.Order;
 
public interface OrderService {
	
    void add(Order c);
    void delete(int id);
    void update(Order c);
    Order get(int id);
    List list();
    
    
   	//提供订单状态的常量值
   String waitPay = "waitPay";
   String waitDelivery = "waitDelivery";
   String waitConfirm = "waitConfirm";
   String waitReview = "waitReview";
   String finish = "finish";
   String delete = "delete";
}