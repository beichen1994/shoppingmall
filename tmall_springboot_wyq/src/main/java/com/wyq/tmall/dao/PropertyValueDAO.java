package com.wyq.tmall.dao;
  
import java.util.List;
 
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.wyq.tmall.pojo.Product;
import com.wyq.tmall.pojo.Property;
import com.wyq.tmall.pojo.PropertyValue;
 
public interface PropertyValueDAO extends JpaRepository<PropertyValue,Integer>{
 
    List<PropertyValue> findByProductOrderByIdDesc(Product product);
    PropertyValue getByPropertyAndProduct(Property property, Product product);
 
}