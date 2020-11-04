package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Category;
import tmall.util.DBUtil;



public class CategoryDAO {

//基础CRUD方法
	
	//增加
	public void add(Category bean){
		String sql = "insert into category values(null,?)";
		
		// try-with-resources 语句，称为 ARM 块(Automatic Resource Management) ，自动资源管理。
		//try 执行完毕后自动被关闭
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setString(1, bean.getName());
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
			String sql = "delete from Category where id ="+id;
			s.execute(sql);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	//修改
	public void update(Category bean){
		String sql = "update category set name=? where id = ?";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql);){
			ps.setString(1, bean.getName());
			ps.setInt(2, bean.getId());
			
			ps.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	//根据id查询
	public Category get(int id){
		Category bean = null;
		try(Connection c = DBUtil.getConnection();Statement s = c.createStatement();){
			String sql = "select * from Category where id = "+id;
			ResultSet rs = s.executeQuery(sql);
			if(rs.next()){
				bean = new Category();
				bean.setId(id);	
				String name = rs.getString("name");
				bean.setName(name);
					
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
			String sql = "select count(*) from Category";
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
	public List<Category>list(int start,int end){
		List<Category> beans = new ArrayList<Category>();
		
		String sql = "select * from Category order by id desc limit ?,?";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql);){
			ps.setInt(1, start);
			ps.setInt(2, end);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				Category bean = new Category();
				int id = rs.getInt(1);
				bean.setId(id);
				String name = rs.getString("name");				
				bean.setName(name);
				beans.add(bean);
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return beans;
	}
	
	//查询所有
	public List<Category>list(){
		//调用上面定义的list方法
		return list(0,Short.MAX_VALUE); 
	}
	

}
