package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Property;
import tmall.bean.Product;
import tmall.bean.PropertyValue;
import tmall.util.DBUtil;



public class PropertyValueDAO {

//基础CRUD方法
	
	//增加
	public void add(PropertyValue bean){
		String sql = "insert into PropertyValue values(null,?,?,?)";
		
		// try-with-resources 语句，称为 ARM 块(Automatic Resource Management) ，自动资源管理。
		//try 执行完毕后自动被关闭
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setString(1, bean.getValue());
			ps.setInt(2, bean.getProduct().getId());
			ps.setInt(3, bean.getProperty().getId());
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
			String sql = "delete from PropertyValue where id ="+id;
			s.execute(sql);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	//修改
	public void update(PropertyValue bean){
		String sql = "update PropertyValue set value=?,pid=?,ptid=? where id = ?";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql);){
			ps.setString(1, bean.getValue());
			ps.setInt(2, bean.getProduct().getId());
			ps.setInt(3, bean.getProperty().getId());
			ps.setInt(4, bean.getId());
			ps.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	//根据id查询
	public PropertyValue get(int id){
		PropertyValue bean = new PropertyValue();
		try(Connection c = DBUtil.getConnection();Statement s = c.createStatement();){
			String sql = "select * from PropertyValue where id = "+id;
			ResultSet rs = s.executeQuery(sql);
			if(rs.next()){
				String value = rs.getString("value");
				
				int pid = rs.getInt("pid");
				Product product = new ProductDAO().get(pid);
				
				int ptid = rs.getInt("ptid");
				Property property = new PropertyDAO().get(ptid);
				
				bean.setId(id);
				bean.setValue(value);
				bean.setProduct(product);
                bean.setProperty(property);
					
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
			String sql = "select count(*) from PropertyValue";
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
	
	//根据属性id和产品id，获取一个PropertyValue对象
	public PropertyValue get(int pid,int ptid){		
		PropertyValue bean = null;    
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {             
            String sql = "select * from PropertyValue where ptid = " + ptid + " and pid = " + pid;             
            ResultSet rs = s.executeQuery(sql);
             
            if (rs.next()) {
            	
                bean= new PropertyValue();
                int id = rs.getInt("id"); 
                String value = rs.getString("value");         
                Product product = new ProductDAO().get(pid);
                Property property = new PropertyDAO().get(ptid);
                
                bean.setId(id);               
                bean.setValue(value);
                bean.setProduct(product);
                bean.setProperty(property);                
            }          
        } catch (SQLException e) {  
            e.printStackTrace();
        }
        return bean;
		
	}
	
	//分页查询
	public List<PropertyValue>list(int start,int end){
		List<PropertyValue> beans = new ArrayList<PropertyValue>();
		
		String sql = "select * from PropertyValue order by id desc limit ?,?";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql);){
			ps.setInt(1, start);
			ps.setInt(2, end);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				PropertyValue bean = new PropertyValue();
                int id = rs.getInt(1);
                String value = rs.getString("value");            
                int pid = rs.getInt("pid");
                Product product = new ProductDAO().get(pid);
                int ptid = rs.getInt("ptid");
                Property property = new PropertyDAO().get(ptid);
                
                bean.setId(id);
                bean.setValue(value);   
                bean.setProduct(product);
                bean.setProperty(property);
                        
                beans.add(bean);
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return beans;
	}
	
	//查询所有
	public List<PropertyValue>list(){
		//调用上面定义的list方法
		return list(0,Short.MAX_VALUE); 
	}
	
	//查询某个产品下所有的属性值
    public List<PropertyValue> list(int pid) {
        List<PropertyValue> beans = new ArrayList<PropertyValue>();
         
        String sql = "select * from PropertyValue where pid = ? order by ptid desc";
  
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
  
            ps.setInt(1, pid);
  
            ResultSet rs = ps.executeQuery();
  
            while (rs.next()) {
                PropertyValue bean = new PropertyValue();
                int id = rs.getInt(1);
                String value = rs.getString("value");
                Product product = new ProductDAO().get(pid);             
                int ptid = rs.getInt("ptid");    
                Property property = new PropertyDAO().get(ptid);
                bean.setProduct(product);
                bean.setProperty(property);
                bean.setValue(value);
                bean.setId(id);          
                beans.add(bean);
            }
        } catch (SQLException e) {
  
            e.printStackTrace();
        }
        return beans;
    }
    
    //初始化某个产品对应的属性值
    public void init(Product p) {
    	
    	//根据分类获取所有的属性 
        List<Property> pts= new PropertyDAO().list(p.getCategory().getId());
        
        //遍历每一个属性

        for (Property pt: pts) {
        	//根据属性和产品，获取属性值
            PropertyValue pv = get(pt.getId(),p.getId());
            //如果属性值不存在，就创建一个属性值对象
            if(null==pv){
                pv = new PropertyValue();
                pv.setProduct(p);
                pv.setProperty(pt);
                this.add(pv);
            }
        }
    }
  
}

