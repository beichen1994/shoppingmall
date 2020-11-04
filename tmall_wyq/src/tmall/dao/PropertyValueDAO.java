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

//����CRUD����
	
	//����
	public void add(PropertyValue bean){
		String sql = "insert into PropertyValue values(null,?,?,?)";
		
		// try-with-resources ��䣬��Ϊ ARM ��(Automatic Resource Management) ���Զ���Դ����
		//try ִ����Ϻ��Զ����ر�
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setString(1, bean.getValue());
			ps.setInt(2, bean.getProduct().getId());
			ps.setInt(3, bean.getProperty().getId());
			ps.execute();
			
			//��ȡ�����¼����������
			ResultSet rs = ps.getGeneratedKeys();
			//��������α�����ƶ�����һ��
			if(rs.next()){
				
				//��ȡ������id
				int id = rs.getInt(1);
				bean.setId(id);
				
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	//ɾ��
	public void delete(int id){
		try(Connection c = DBUtil.getConnection();Statement s = c.createStatement();){
			String sql = "delete from PropertyValue where id ="+id;
			s.execute(sql);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	//�޸�
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

	//����id��ѯ
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

	//��ȡ����
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

//����֧��ҵ��ķ���	
	
	//��������id�Ͳ�Ʒid����ȡһ��PropertyValue����
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
	
	//��ҳ��ѯ
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
	
	//��ѯ����
	public List<PropertyValue>list(){
		//�������涨���list����
		return list(0,Short.MAX_VALUE); 
	}
	
	//��ѯĳ����Ʒ�����е�����ֵ
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
    
    //��ʼ��ĳ����Ʒ��Ӧ������ֵ
    public void init(Product p) {
    	
    	//���ݷ����ȡ���е����� 
        List<Property> pts= new PropertyDAO().list(p.getCategory().getId());
        
        //����ÿһ������

        for (Property pt: pts) {
        	//�������ԺͲ�Ʒ����ȡ����ֵ
            PropertyValue pv = get(pt.getId(),p.getId());
            //�������ֵ�����ڣ��ʹ���һ������ֵ����
            if(null==pv){
                pv = new PropertyValue();
                pv.setProduct(p);
                pv.setProperty(pt);
                this.add(pv);
            }
        }
    }
  
}

