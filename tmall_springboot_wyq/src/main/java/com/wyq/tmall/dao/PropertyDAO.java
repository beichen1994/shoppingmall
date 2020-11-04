package com.wyq.tmall.dao;
  
import java.util.List;
 
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.wyq.tmall.pojo.Category;
import com.wyq.tmall.pojo.Property;
 
public interface PropertyDAO extends JpaRepository<Property,Integer>{
    Page<Property> findByCategory(Category category, Pageable pageable);
    List<Property> findByCategory(Category category);
 
}