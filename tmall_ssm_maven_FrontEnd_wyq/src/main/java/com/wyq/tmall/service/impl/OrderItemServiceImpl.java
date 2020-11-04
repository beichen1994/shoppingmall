package com.wyq.tmall.service.impl;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.wyq.tmall.mapper.OrderItemMapper;
import com.wyq.tmall.pojo.Order;
import com.wyq.tmall.pojo.OrderItem;
import com.wyq.tmall.pojo.OrderItemExample;
import com.wyq.tmall.pojo.Product;
import com.wyq.tmall.service.OrderItemService;
import com.wyq.tmall.service.ProductService;
 
@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    OrderItemMapper orderItemMapper;
    @Autowired
    ProductService productService;
 
    @Override
    public void add(OrderItem c) {
        orderItemMapper.insert(c);
    }
 
    @Override
    public void delete(int id) {
        orderItemMapper.deleteByPrimaryKey(id);
    }
 
    @Override
    public void update(OrderItem c) {
        orderItemMapper.updateByPrimaryKeySelective(c);
    }
 
    @Override
    public OrderItem get(int id) {
        OrderItem result = orderItemMapper.selectByPrimaryKey(id);
        setProduct(result);
        return result;
    }
 
    public List<OrderItem> list(){
        OrderItemExample example =new OrderItemExample();
        example.setOrderByClause("id desc");
        return orderItemMapper.selectByExample(example);
 
    }
  
    private void setProduct(OrderItem oi) {
        Product p = productService.get(oi.getPid());
        oi.setProduct(p);
    }
 
    public void setProduct(List<OrderItem> ois){
        for (OrderItem oi: ois) {
            setProduct(oi);
        }
    }
 
    /*
     * 
     *  因为在订单管理界面，首先是遍历多个订单，然后遍历这个订单下的多个订单项。
     *  而由MybatisGenerator逆向工程所创建的一套自动生成代码，是不具备一对多关系的，需要自己去二次开发。 这里就是做订单与订单项的一对多关系。
     */
    public void fill(Order o) {
    	
    	//根据订单id查询出其对应的所有订单项
        OrderItemExample example =new OrderItemExample();
        example.createCriteria().andOidEqualTo(o.getId());
        example.setOrderByClause("id desc");
        List<OrderItem> ois =orderItemMapper.selectByExample(example);
        
        //通过setProduct为所有的订单项设置Product属性
        setProduct(ois);
 
        //遍历所有的订单项，然后计算出该订单的总金额和总数量
        float total = 0;
        int totalNumber = 0;
        for (OrderItem oi : ois) {
            total+=oi.getNumber()*oi.getProduct().getPromotePrice();
            totalNumber+=oi.getNumber();
        }
        //最后再把订单项设置在订单的orderItems属性上
        o.setTotal(total);
        o.setTotalNumber(totalNumber);
        o.setOrderItems(ois);
 
    }
    
    //在fill(List<Order> orders) 中，就是遍历每个订单，然后挨个调用fill(Order order)
    @Override
    public void fill(List<Order> os) {
        for (Order o : os) {
            fill(o);
        }
    }
    
    @Override
    public int getSaleCount(int pid) {
        OrderItemExample example =new OrderItemExample();
        example.createCriteria().andPidEqualTo(pid);
        List<OrderItem> ois =orderItemMapper.selectByExample(example);
        int result =0;
        for (OrderItem oi : ois) {
            result+=oi.getNumber();
        }
        return result;
    }
    
    @Override
    public List<OrderItem> listByUser(int uid) {
        OrderItemExample example =new OrderItemExample();
        example.createCriteria().andUidEqualTo(uid).andOidIsNull();
        List<OrderItem> result =orderItemMapper.selectByExample(example);
        setProduct(result);
        return result;
    }

    
 
}