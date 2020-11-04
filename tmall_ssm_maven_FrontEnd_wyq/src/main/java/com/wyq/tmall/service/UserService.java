package com.wyq.tmall.service;
  
import java.util.List;
 
import com.wyq.tmall.pojo.User;
 
public interface UserService {
    void add(User c);
    void delete(int id);
    void update(User c);
    User get(int id);
    List list();
    
    //判断某个名称是否已经被使用过了。
    boolean isExist(String name);
    
    User get(String name, String password);
}