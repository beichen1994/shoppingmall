package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.User;
import tmall.bean.Order;
import tmall.bean.OrderItem;

import tmall.util.DateUtil;
import tmall.util.DBUtil;



public class OrderItemDAO {

	
//基础CRUD方法	
	//增加
	public void add(OrderItem bean){
		String sql = "insert into OrderItem values(null,?,?,?,?)";
		
		// try-with-resources 语句，称为 ARM 块(Automatic Resource Management) ，自动资源管理。
		//try 执行完毕后自动被关闭
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			
			ps.setInt(1, bean.getNumber());
            ps.setInt(2, bean.getProduct().getId());           
            //订单项在创建的时候，是没有订单信息的
            if(null==bean.getOrder())
                ps.setInt(3,-1);
            else
                ps.setInt(3,bean.getOrder().getId());              
            ps.setInt(4,bean.getUser().getId());          
            ps.execute();
			
            
			//获取插入记录的自增主键
			ResultSet rs = ps.getGeneratedKeys();
			//结果集的游标可以移动到下一步
			if(rs.next()){				
				//获取并设置id
				int id = rs.getInt(1);
				bean.setId(id);
				
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	//删除
	public void delete(int id){
		try(Connection c = DBUtil.getConnection();Statement s = c.createStatement();){
			String sql = "delete from OrderItem where id ="+id;
			s.execute(sql);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	//修改
	public void update(OrderItem bean){
		String sql = "update OrderItem set number=?,pid=?,oid=?,uid=?where id = ?";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql);){
			ps.setInt(1, bean.getNumber());  
			ps.setInt(2, bean.getProduct().getId());
            if(null==bean.getOrder())
                ps.setInt(3, -1);
            else
                ps.setInt(3, bean.getOrder().getId());             
            ps.setInt(4, bean.getUser().getId());
                   
            ps.setInt(5, bean.getId());
            ps.execute();
            
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	
	//根据id查询
	public OrderItem get(int id){
		OrderItem bean = null;
		try(Connection c = DBUtil.getConnection();Statement s = c.createStatement();){
			String sql = "select * from OrderItem where id = " + id;
			ResultSet rs = s.executeQuery(sql);
			if(rs.next()){
				
				
				int number = rs.getInt("number");
				int pid = rs.getInt("pid");
				Product product = new ProductDAO().get(pid); 
                int uid = rs.getInt("uid"); 
                User user = new UserDAO().get(uid);                
                int oid = rs.getInt("oid");
                //如果存在订单编号，则创建一个订单对象
	            if(-1!=oid){
                  Order order= new OrderDAO().get(oid);
                  bean.setOrder(order);                  
	            }
	            
                bean.setId(id);
                bean.setNumber(number);
                bean.setProduct(product);
                bean.setUser(user);  
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		return bean;
		
	}

	//获取总数
	public int getTotal(){
		int total =0;
		try(Connection c = DBUtil.getConnection();Statement s = c.createStatement();){
			 String sql = "select count(*) from OrderItem";
			ResultSet rs = s.executeQuery(sql);
			if(rs.next()){
				total = rs.getInt(1);
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		return total;
	}

//用于支持业务的方法	
	
	
	//查询某个用户的未生成订单的订单项(既购物车中的订单项)
    public List<OrderItem> listByUser(int uid) {
        return listByUser(uid, 0, Short.MAX_VALUE);
    }
  
    public List<OrderItem> listByUser(int uid, int start, int count) {
        List<OrderItem> beans = new ArrayList<OrderItem>();
  
        String sql = "select * from OrderItem where uid = ? and oid=-1 order by id desc limit ?,? ";
  
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
  
            ps.setInt(1, uid);
            ps.setInt(2, start);
            ps.setInt(3, count);
  
            ResultSet rs = ps.executeQuery();
  
            while (rs.next()) {
                OrderItem bean = new OrderItem();
                
                int id = rs.getInt(1);
                int number = rs.getInt("number");
                int pid = rs.getInt("pid");
                int oid = rs.getInt("oid");               
                Product product = new ProductDAO().get(pid);
                if(-1!=oid){
                    Order order= new OrderDAO().get(oid);
                    bean.setOrder(order);                  
                }
                User user = new UserDAO().get(uid);
                
                bean.setId(id); 
                bean.setNumber(number);          
                bean.setProduct(product);
                bean.setUser(user);
                              
                beans.add(bean);
            }
        } catch (SQLException e) {
  
            e.printStackTrace();
        }
        return beans;
    }
   
    //查询某种订单下所有的订单项
    public List<OrderItem> listByOrder(int oid) {
        return listByOrder(oid, 0, Short.MAX_VALUE);
    }
     
    public List<OrderItem> listByOrder(int oid, int start, int count) {
        List<OrderItem> beans = new ArrayList<OrderItem>();
         
        String sql = "select * from OrderItem where oid = ? order by id desc limit ?,? ";
         
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
             
            ps.setInt(1, oid);
            ps.setInt(2, start);
            ps.setInt(3, count);
             
            ResultSet rs = ps.executeQuery();
             
            while (rs.next()) {
            	
                OrderItem bean = new OrderItem();
                int id = rs.getInt(1);     
                int number = rs.getInt("number");
                int pid = rs.getInt("pid");
                int uid = rs.getInt("uid");
                Product product = new ProductDAO().get(pid);
                User user = new UserDAO().get(uid);
                if(-1!=oid){
                    Order order= new OrderDAO().get(oid);
                    bean.setOrder(order);                  
                }   

                bean.setId(id); 
                bean.setNumber(number);
                bean.setProduct(product);
                bean.setUser(user);
                              
                beans.add(bean);
            }
        } catch (SQLException e) {
             
            e.printStackTrace();
        }
        return beans;
    }
    
    //查询某种产品的订单项
    public List<OrderItem> listByProduct(int pid) {
        return listByProduct(pid, 0, Short.MAX_VALUE);
    }
  
    public List<OrderItem> listByProduct(int pid, int start, int count) {
        List<OrderItem> beans = new ArrayList<OrderItem>();
  
        String sql = "select * from OrderItem where pid = ? order by id desc limit ?,? ";
  
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
  
            ps.setInt(1, pid);
            ps.setInt(2, start);
            ps.setInt(3, count);
  
            ResultSet rs = ps.executeQuery();
  
            while (rs.next()) {
                OrderItem bean = new OrderItem();
                int id = rs.getInt(1);
                int uid = rs.getInt("uid");
                int oid = rs.getInt("oid");
                int number = rs.getInt("number");
                User user = new UserDAO().get(uid);
                Product product = new ProductDAO().get(pid);
                if(-1!=oid){
                    Order order= new OrderDAO().get(oid);
                    bean.setOrder(order);                  
                }
                
                bean.setId(id);
                bean.setNumber(number); 
                bean.setProduct(product);
                bean.setUser(user);
              
                beans.add(bean);
            }
        } catch (SQLException e) {
  
            e.printStackTrace();
        }
        return beans;
    }
    
    //获取某一种产品的销量。 产品销量就是这种产品对应的订单项OrderItem的number字段的总和
    public int getSaleCount(int pid) {
        int total = 0;
           try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
     
               String sql = "select sum(number) from OrderItem where pid = " + pid;
     
               ResultSet rs = s.executeQuery(sql);
               while (rs.next()) {
                   total = rs.getInt(1);
               }
           } catch (SQLException e) {
     
               e.printStackTrace();
           }
           return total;
   }

    //为订单设置订单项集合？？？
    public void fill(List<Order> os) {
        for (Order o : os) {
            List<OrderItem> ois=listByOrder(o.getId());
            float total = 0;
            int totalNumber = 0;
            for (OrderItem oi : ois) {
                 total+=oi.getNumber()*oi.getProduct().getPromotePrice();
                 totalNumber+=oi.getNumber();
            }
            o.setTotal(total);
            o.setOrderItems(ois);
            o.setTotalNumber(totalNumber);
        }
         
    }
    //为订单设置订单项集合？？？
    public void fill(Order o) {
        List<OrderItem> ois=listByOrder(o.getId());
        float total = 0;
        for (OrderItem oi : ois) {
             total+=oi.getNumber()*oi.getProduct().getPromotePrice();
        }
        o.setTotal(total);
        o.setOrderItems(ois);
    }
}
