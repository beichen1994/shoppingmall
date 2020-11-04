package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import tmall.bean.User;
import tmall.bean.Order;
import tmall.util.DBUtil;
import tmall.util.DateUtil;




public class OrderDAO {
	
	//public static final 修饰的常量字符串，用于表示订单类型
	//在实体类Order的getStatusDesc方法中就用到了这些属性
    public static final String waitPay = "waitPay"; //待支付
    public static final String waitDelivery = "waitDelivery"; //待交付
    public static final String waitConfirm = "waitConfirm"; //待确认收获
    public static final String waitReview = "waitReview"; //待评价
    public static final String finish = "finish"; //完成标记 
    public static final String delete = "delete"; //删除标记
    //订单有个状态叫做delete，表示该订单处于被删除状态。
    //因为订单是非常重要的商业信息，里面有支付金额，用户信息，产品相关信息等资料，通常来讲，是不会被删除的，而是做一个标记。
	
//基础CRUD方法
	
	//增加
	public void add(Order bean){
		String sql = "insert into order_ values(null,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		// try-with-resources 语句，称为 ARM 块(Automatic Resource Management) ，自动资源管理。
		//try 执行完毕后自动被关闭
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
            
			ps.setString(1, bean.getOrderCode());
            ps.setString(2, bean.getAddress());
            ps.setString(3, bean.getPost());
            ps.setString(4, bean.getReceiver());
            ps.setString(5, bean.getMobile());
            ps.setString(6, bean.getUserMessage());             
            ps.setTimestamp(7,  DateUtil.d2t(bean.getCreateDate()));
            ps.setTimestamp(8,  DateUtil.d2t(bean.getPayDate()));
            ps.setTimestamp(9,  DateUtil.d2t(bean.getDeliveryDate()));
            ps.setTimestamp(10,  DateUtil.d2t(bean.getConfirmDate()));
            ps.setString(12, bean.getStatus());
            ps.setInt(11, bean.getUser().getId());    
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
			String sql = "delete from Order_ where id ="+id;
			s.execute(sql);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	//修改
	public void update(Order bean){
		String sql = "update order_ set address= ?, post=?, receiver=?,mobile=?,userMessage=?,createDate = ?,payDate =?,deliveryDate =?,confirmDate = ?,orderCode =?,status=?,uid=? where id = ?";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql);){
			ps.setString(1, bean.getAddress());
            ps.setString(2, bean.getPost());
            ps.setString(3, bean.getReceiver());
            ps.setString(4, bean.getMobile());
            ps.setString(5, bean.getUserMessage());
            ps.setTimestamp(6, DateUtil.d2t(bean.getCreateDate()));;
            ps.setTimestamp(7, DateUtil.d2t(bean.getPayDate()));;
            ps.setTimestamp(8, DateUtil.d2t(bean.getDeliveryDate()));;
            ps.setTimestamp(9, DateUtil.d2t(bean.getConfirmDate()));;
            ps.setString(10, bean.getOrderCode());
            ps.setString(12, bean.getStatus());
            ps.setInt(11, bean.getUser().getId());
            ps.setInt(13, bean.getId());
            ps.execute();		
			ps.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	//根据id查询
	public Order get(int id){
		Order bean = null;
		try(Connection c = DBUtil.getConnection();Statement s = c.createStatement();){
			String sql = "select * from Order_ where id = " + id;
			ResultSet rs = s.executeQuery(sql);
			if(rs.next()){				
				String orderCode =rs.getString("orderCode");
                String address = rs.getString("address");
                String post = rs.getString("post");
                String receiver = rs.getString("receiver");
                String mobile = rs.getString("mobile");
                String userMessage = rs.getString("userMessage");
                String status = rs.getString("status");
                int uid =rs.getInt("uid");
                Date createDate = DateUtil.t2d( rs.getTimestamp("createDate"));
                Date payDate = DateUtil.t2d( rs.getTimestamp("payDate"));
                Date deliveryDate = DateUtil.t2d( rs.getTimestamp("deliveryDate"));
                Date confirmDate = DateUtil.t2d( rs.getTimestamp("confirmDate"));
                 
                bean.setOrderCode(orderCode);
                bean.setAddress(address);
                bean.setPost(post);
                bean.setReceiver(receiver);
                bean.setMobile(mobile);
                bean.setUserMessage(userMessage);
                bean.setCreateDate(createDate);
                bean.setPayDate(payDate);
                bean.setDeliveryDate(deliveryDate);
                bean.setConfirmDate(confirmDate);
                User user = new UserDAO().get(uid);
                bean.setUser(user);
                bean.setStatus(status);           
                bean.setId(id);					
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
			String sql = "select count(*) from Order_";
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
	//分页查询
	public List<Order>list(int start,int end){
		List<Order> beans = new ArrayList<Order>();
		
		String sql = "select * from Order_ order by id desc limit ?,? ";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql);){
			ps.setInt(1, start);
			ps.setInt(2, end);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				Order bean = new Order();
                String orderCode =rs.getString("orderCode");
                String address = rs.getString("address");
                String post = rs.getString("post");
                String receiver = rs.getString("receiver");
                String mobile = rs.getString("mobile");
                String userMessage = rs.getString("userMessage");
                String status = rs.getString("status");
                Date createDate = DateUtil.t2d( rs.getTimestamp("createDate"));
                Date payDate = DateUtil.t2d( rs.getTimestamp("payDate"));
                Date deliveryDate = DateUtil.t2d( rs.getTimestamp("deliveryDate"));
                Date confirmDate = DateUtil.t2d( rs.getTimestamp("confirmDate"));
                int uid =rs.getInt("uid");                                
                int id = rs.getInt("id");
                
                bean.setId(id);
                bean.setOrderCode(orderCode);
                bean.setAddress(address);
                bean.setPost(post);
                bean.setReceiver(receiver);
                bean.setMobile(mobile);
                bean.setUserMessage(userMessage);
                bean.setCreateDate(createDate);
                bean.setPayDate(payDate);
                bean.setDeliveryDate(deliveryDate);
                bean.setConfirmDate(confirmDate);
                User user = new UserDAO().get(uid);
                bean.setUser(user);
                bean.setStatus(status);
                beans.add(bean);
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return beans;
	}
	
	//查询所有
	public List<Order>list(){
		//调用上面定义的list方法
		return list(0,Short.MAX_VALUE); 
	}
	
    public List<Order> list(int uid,String excludedStatus) {
        return list(uid,excludedStatus,0, Short.MAX_VALUE);
    }
      
    //查询指定用户的订单(去掉某种订单状态，通常是"delete")
    public List<Order> list(int uid, String excludedStatus, int start, int count) {
        List<Order> beans = new ArrayList<Order>();
         
        String sql = "select * from Order_ where uid = ? and status != ? order by id desc limit ?,? ";
         
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
             
            ps.setInt(1, uid);
            ps.setString(2, excludedStatus);
            ps.setInt(3, start);
            ps.setInt(4, count);
             
            ResultSet rs = ps.executeQuery();
             
            while (rs.next()) {
                Order bean = new Order();
                String orderCode =rs.getString("orderCode");
                String address = rs.getString("address");
                String post = rs.getString("post");
                String receiver = rs.getString("receiver");
                String mobile = rs.getString("mobile");
                String userMessage = rs.getString("userMessage");
                String status = rs.getString("status");
                Date createDate = DateUtil.t2d( rs.getTimestamp("createDate"));
                Date payDate = DateUtil.t2d( rs.getTimestamp("payDate"));
                Date deliveryDate = DateUtil.t2d( rs.getTimestamp("deliveryDate"));
                Date confirmDate = DateUtil.t2d( rs.getTimestamp("confirmDate"));         
                int id = rs.getInt("id");
                
                bean.setId(id);
                bean.setOrderCode(orderCode);
                bean.setAddress(address);
                bean.setPost(post);
                bean.setReceiver(receiver);
                bean.setMobile(mobile);
                bean.setUserMessage(userMessage);
                bean.setCreateDate(createDate);
                bean.setPayDate(payDate);
                bean.setDeliveryDate(deliveryDate);
                bean.setConfirmDate(confirmDate);
                User user = new UserDAO().get(uid);
                bean.setStatus(status);
                bean.setUser(user);
                beans.add(bean);
            }
        } catch (SQLException e) {
             
            e.printStackTrace();
        }
        return beans;
    }
    
}
