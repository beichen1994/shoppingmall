package com.wyq.tmall.dao;
  
import java.util.List;
 
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.wyq.tmall.pojo.Order;
import com.wyq.tmall.pojo.OrderItem;
import com.wyq.tmall.pojo.Product;
import com.wyq.tmall.pojo.User;
 
public interface OrderItemDAO extends JpaRepository<OrderItem,Integer>{
    List<OrderItem> findByOrderOrderByIdDesc(Order order);
    List<OrderItem> findByProduct(Product product);
    List<OrderItem> findByUserAndOrderIsNull(User user);
}