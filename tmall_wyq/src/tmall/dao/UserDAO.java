package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.User;
import tmall.util.DBUtil;

public class UserDAO {
	
//基础的CRUD方法
	
	//增加
	public void add(User bean){
		String sql = "insert into User values(null,?,?)";
		
		// try-with-resources 语句，称为 ARM 块(Automatic Resource Management) ，自动资源管理。
		//try 执行完毕后自动被关闭
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getPassword());
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
			String sql = "delete from User where id ="+id;
			s.execute(sql);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	//修改
	public void update(User bean){
		String sql = "update user set name=?,password=? where id = ?";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql);){
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getPassword());
			ps.setInt(3, bean.getId());
			
			ps.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	//根据id查询并返回User对象
	public User get(int id){
		User bean = null;
		try(Connection c = DBUtil.getConnection();Statement s = c.createStatement();){
			String sql = "select * from User where id = "+id;
			ResultSet rs = s.executeQuery(sql);
			if(rs.next()){
				bean = new User();
				String name = rs.getString("name");
				bean.setName(name);
				String password = rs.getString("password");
				bean.setPassword(password);
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
			String sql = "select count(*) from User";
			ResultSet rs = s.executeQuery(sql);
			if(rs.next()){
				total = rs.getInt(1);
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		return total;
	}	


	
//用于支持业务的方法，注册的时候，需要判断某个用户是否已经存在，账号密码是否正确等操作	
	
	//分页查询并返回范围内的User对象
	public List<User>list(int start,int end){
		List<User> beans = new ArrayList<User>();
		
		String sql = "select * from User order by id desc limit ?,?";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql);){
			ps.setInt(1, start);
			ps.setInt(2, end);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				User bean = new User();
				int id = rs.getInt(1);
				String name = rs.getString("name");
				String password = rs.getString("password");
	
				bean.setId(id);
				bean.setName(name);
				bean.setPassword(password);
				beans.add(bean);
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return beans;
	}
	
	//查询所有并返回User对象
	public List<User>list(){
		//调用上面定义的list方法
		return list(0,Short.MAX_VALUE); 
	}
	
	//根据name查询并返回User对象
	public User get(String name) {
        User bean = null;
          
        String sql = "select * from User where name = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs =ps.executeQuery();
  
            if (rs.next()) {
                bean = new User();
                int id = rs.getInt("id");
                bean.setId(id);
                bean.setName(name);
                String password = rs.getString("password");
                bean.setPassword(password);
                
            }
  
        } catch (SQLException e) {
  
            e.printStackTrace();
        }
        return bean;
    }
	
	//以boolean形式返回某个用户名是否已经存在
	public boolean isExist(String name) {
        User user = get(name);
        return user!=null;
 
    }
	
	//根据name和password查询并返回User对象
	public User get(String name, String password) {
        User bean = null;
          
        String sql = "select * from User where name = ? and password=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, password);
            ResultSet rs =ps.executeQuery();
  
            if (rs.next()) {
                bean = new User();
                int id = rs.getInt("id");
                bean.setId(id);
                bean.setName(name);
                bean.setPassword(password);
                
            }
  
        } catch (SQLException e) {
  
            e.printStackTrace();
        }
        return bean;
    }

}
