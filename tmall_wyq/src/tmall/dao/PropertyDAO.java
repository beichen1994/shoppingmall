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
import tmall.bean.Property;
import tmall.util.DBUtil;
import tmall.util.DateUtil;



public class PropertyDAO {

//基础CRUD方法
	
	//增加
	public void add(Property bean){
		String sql = "insert into Property values(null,?,?)";
		
		// try-with-resources 语句，称为 ARM 块(Automatic Resource Management) ，自动资源管理。
		//try 执行完毕后自动被关闭
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			//在数据库中属性字段为 id name cid，所以第一个？的位置为name
			ps.setString(1, bean.getName());
			
			//这是外键cid，在第二个?的位置
			ps.setInt(2, bean.getCategory().getId());
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
			String sql = "delete from Property where id ="+id;
			s.execute(sql);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	//修改
	public void update(Property bean){
		String sql = "update Property set name=?,cid=? where id = ?";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql);){
			ps.setString(1, bean.getName());
			ps.setInt(2, bean.getCategory().getId());
			ps.setInt(3, bean.getId());
			
			ps.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	//根据id查询
	public Property get(int id){
		Property bean = null;
		try(Connection c = DBUtil.getConnection();Statement s = c.createStatement();){
			String sql = "select * from Property where id = "+id;
			ResultSet rs = s.executeQuery(sql);
			if(rs.next()){
				bean = new Property();
				bean.setId(id);
				String name = rs.getString("name");
				bean.setName(name);
				int cid = rs.getInt("cid");
				//新建CategoryDAO对象根据cid进行查询返回category对象
				Category category = new CategoryDAO().get(cid);
				bean.setCategory(category);
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		return bean;
		
	}

	//获取总数,这里要根据外键cid,即某种分类下的属性总数
	public int getTotal(int cid){
		int total =0;
		try(Connection c = DBUtil.getConnection();Statement s = c.createStatement();){
			String sql = "select count(*) from Property where cid="+cid;
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
	//分页查询,这里要根据外键cid，即某种分类下的属性总数
	public List<Property>list(int cid,int start,int end){
		List<Property> beans = new ArrayList<Property>();
		
		String sql = "select * from Property where cid=? order by id desc limit ?,?";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql);){
			ps.setInt(1, cid);
			ps.setInt(2, start);
			ps.setInt(3, end);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				Property bean = new Property();
				int id = rs.getInt(1);
				bean.setId(id);
				String name = rs.getString("name");
				bean.setName(name);
				Category category = new CategoryDAO().get(cid);
				bean.setCategory(category);
				beans.add(bean);
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return beans;
	}
	
	//查询所有，这里要根据外键cid,即某种分类下的属性总数
	public List<Property>list(int cid){
		//调用上面定义的list方法
		return list(cid,0,Short.MAX_VALUE); 
	}
		

}
