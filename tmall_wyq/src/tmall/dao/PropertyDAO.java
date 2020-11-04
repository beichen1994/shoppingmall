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

//����CRUD����
	
	//����
	public void add(Property bean){
		String sql = "insert into Property values(null,?,?)";
		
		// try-with-resources ��䣬��Ϊ ARM ��(Automatic Resource Management) ���Զ���Դ����
		//try ִ����Ϻ��Զ����ر�
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			//�����ݿ��������ֶ�Ϊ id name cid�����Ե�һ������λ��Ϊname
			ps.setString(1, bean.getName());
			
			//�������cid���ڵڶ���?��λ��
			ps.setInt(2, bean.getCategory().getId());
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
			String sql = "delete from Property where id ="+id;
			s.execute(sql);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	//�޸�
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
	
	//����id��ѯ
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
				//�½�CategoryDAO�������cid���в�ѯ����category����
				Category category = new CategoryDAO().get(cid);
				bean.setCategory(category);
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		return bean;
		
	}

	//��ȡ����,����Ҫ�������cid,��ĳ�ַ����µ���������
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
	
	
//����֧��ҵ��ķ���
	//��ҳ��ѯ,����Ҫ�������cid����ĳ�ַ����µ���������
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
	
	//��ѯ���У�����Ҫ�������cid,��ĳ�ַ����µ���������
	public List<Property>list(int cid){
		//�������涨���list����
		return list(cid,0,Short.MAX_VALUE); 
	}
		

}
