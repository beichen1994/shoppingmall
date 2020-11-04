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
	
	//���־�̬���Էֱ��ʾ����ͼƬ������ͼƬ
	public static final String type_single = "type_single";
    public static final String type_detail = "type_detail";
	
//����CRUD����
	
	//����
	public void add(ProductImage bean){
		String sql = "insert into ProductImage values(null,?,?)";
		
		// try-with-resources ��䣬��Ϊ ARM ��(Automatic Resource Management) ���Զ���Դ����
		//try ִ����Ϻ��Զ����ر�
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setString(1, bean.getType());
			ps.setInt(2, bean.getProduct().getId());
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
			String sql = "delete from ProductImage where id ="+id;
			s.execute(sql);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	//�޸�
	public void update(ProductImage bean){
	
	}

	//����id��ѯ
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
				//�½�ProductDAO�������pid���в�ѯ����Porduct����
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

	//����֧��ҵ��ķ���
	//��ҳ��ѯ,,����Ҫ����Product���Լ�type,��ָ����Ʒ�£�ĳ�����͵�ProductImage
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
		
	//��ѯ����
	public List<ProductImage>list(Product p,String type){
		//�������涨���list����
		return list(p,type,0,Short.MAX_VALUE); 
	}
	
	//��ȡ����

}
