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
	
//������CRUD����
	
	//����
	public void add(User bean){
		String sql = "insert into User values(null,?,?)";
		
		// try-with-resources ��䣬��Ϊ ARM ��(Automatic Resource Management) ���Զ���Դ����
		//try ִ����Ϻ��Զ����ر�
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getPassword());
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
			String sql = "delete from User where id ="+id;
			s.execute(sql);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	//�޸�
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

	//����id��ѯ������User����
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
	
	//��ȡ����
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


	
//����֧��ҵ��ķ�����ע���ʱ����Ҫ�ж�ĳ���û��Ƿ��Ѿ����ڣ��˺������Ƿ���ȷ�Ȳ���	
	
	//��ҳ��ѯ�����ط�Χ�ڵ�User����
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
	
	//��ѯ���в�����User����
	public List<User>list(){
		//�������涨���list����
		return list(0,Short.MAX_VALUE); 
	}
	
	//����name��ѯ������User����
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
	
	//��boolean��ʽ����ĳ���û����Ƿ��Ѿ�����
	public boolean isExist(String name) {
        User user = get(name);
        return user!=null;
 
    }
	
	//����name��password��ѯ������User����
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
