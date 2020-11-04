package com.wyq.tmall.dao;
  
import java.util.List;
 
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.wyq.tmall.pojo.Product;
import com.wyq.tmall.pojo.Review;
 
public interface ReviewDAO extends JpaRepository<Review,Integer>{
 
    List<Review> findByProductOrderByIdDesc(Product product);
    int countByProduct(Product product);
 
}