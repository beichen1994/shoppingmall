package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.util.DBUtil;



public class ProductImageDAO {
	
	//两种静态属性分别表示单个图片和详情图片
	public static final String type_single = "type_single";
    public static final String type_detail = "type_detail";
	
//基础CRUD方法
	
	//增加
	public void add(ProductImage bean){
		String sql = "insert into ProductImage values(null,?,?)";
		
		// try-with-resources 语句，称为 ARM 块(Automatic Resource Management) ，自动资源管理。
		//try 执行完毕后自动被关闭
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setString(1, bean.getType());
			ps.setInt(2, bean.getProduct().getId());
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
			String sql = "delete from ProductImage where id ="+id;
			s.execute(sql);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	//修改
	public void update(ProductImage bean){
	
	}

	//根据id查询
	public ProductImage get(int id){
		ProductImage bean = new ProductImage();;
		try(Connection c = DBUtil.getConnection();Statement s = c.createStatement();){
			String sql = "select * from ProductImage where id = "+id;
			ResultSet rs = s.executeQuery(sql);
			if(rs.next()){
				bean.setId(id);
				String type = rs.getString("type");
				bean.setType(type);
				int pid = rs.getInt("pid");
				//新建ProductDAO对象根据pid进行查询返回Porduct对象
				Product product = new ProductDAO().get(pid);
				bean.setProduct(product);
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		return bean;
		
	}

	public int getTotal(){
		int total =0;
		try(Connection c = DBUtil.getConnection();Statement s = c.createStatement();){
			String sql = "select count(*) from ProductImage";
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
	//分页查询,,这里要根据Product，以及type,即指定产品下，某种类型的ProductImage
	public List<ProductImage>list(Product p,String type,int start,int end){
		List<ProductImage> beans = new ArrayList<ProductImage>();
		
		String sql = "select * from ProductImage where pid=?,type=? order by id desc limit ?,?";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql);){
			ps.setInt(1, p.getId());
			ps.setString(2, type);
			ps.setInt(3, start);
			ps.setInt(4, end);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				
				ProductImage bean = new ProductImage();
				int id = rs.getInt(1);
				bean.setId(id);
				bean.setProduct(p);
				bean.setType(type);
				
				beans.add(bean);
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return beans;
	}
		
	//查询所有
	public List<ProductImage>list(Product p,String type){
		//调用上面定义的list方法
		return list(p,type,0,Short.MAX_VALUE); 
	}
	
	//获取总数

}
