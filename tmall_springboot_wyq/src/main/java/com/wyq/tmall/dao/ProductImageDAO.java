package com.wyq.tmall.dao;
  
import java.util.List;
 
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.wyq.tmall.pojo.Product;
import com.wyq.tmall.pojo.ProductImage;
 
public interface ProductImageDAO extends JpaRepository<ProductImage,Integer>{
    public List<ProductImage> findByProductAndTypeOrderByIdDesc(Product product, String type);
     
}