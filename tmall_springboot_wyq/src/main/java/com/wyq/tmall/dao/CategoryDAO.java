package com.wyq.tmall.dao;
  
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.wyq.tmall.pojo.Category;
 
public interface CategoryDAO extends JpaRepository<Category,Integer>{
 
}