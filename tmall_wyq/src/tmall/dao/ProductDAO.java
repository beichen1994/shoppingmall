package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.util.DBUtil;
import tmall.util.DateUtil;



public class ProductDAO {

//����CRUD����
	
	//����
	public void add(Product bean){
		String sql = "insert into product values(null,?,?,?,?,?,?,?)";
		
		// try-with-resources ��䣬��Ϊ ARM ��(Automatic Resource Management) ���Զ���Դ����
		//try ִ����Ϻ��Զ����ر�
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setString(1, bean.getName());
            ps.setString(2, bean.getSubTitle());
            ps.setFloat(3, bean.getOriginalPrice());
            ps.setFloat(4, bean.getPromotePrice());
            ps.setInt(5, bean.getStock());
            ps.setTimestamp(6, DateUtil.d2t(bean.getCreateDate()));
            ps.setInt(7, bean.getCategory().getId());
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
			 String sql = "delete from Product where id = " + id;
			s.execute(sql);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	//�޸�
	public void update(Product bean){
		String sql = "update Product set name= ?, subTitle=?, orignalPrice=?,promotePrice=?,stock=?, createDate=?, cid = ? where id = ?";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql);){
			ps.setString(1, bean.getName());
            ps.setString(2, bean.getSubTitle());
            ps.setFloat(3, bean.getOriginalPrice());
            ps.setFloat(4, bean.getPromotePrice());
            ps.setInt(5, bean.getStock());
            ps.setTimestamp(6, DateUtil.d2t(bean.getCreateDate()));
            ps.setInt(7, bean.getCategory().getId());
            ps.setInt(8, bean.getId());
            ps.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	//����id��ѯ
	public Product get(int id){
		Product bean = new Product();
		try(Connection c = DBUtil.getConnection();Statement s = c.createStatement();){
			String sql = "select * from Product where id = " + id;
			ResultSet rs = s.executeQuery(sql);
			if(rs.next()){
				String name = rs.getString("name");
                String subTitle = rs.getString("subTitle");
                float orignalPrice = rs.getFloat("orignalPrice");
                float promotePrice = rs.getFloat("promotePrice");
                int stock = rs.getInt("stock");
                Date createDate = DateUtil.t2d( rs.getTimestamp("createDate"));
                int cid = rs.getInt("cid");
                
                bean.setId(id);

                bean.setName(name);
                bean.setSubTitle(subTitle);
                bean.setOriginalPrice(orignalPrice);
                bean.setPromotePrice(promotePrice);
                bean.setStock(stock);
                bean.setCreateDate(createDate);
                Category category = new CategoryDAO().get(cid);
                bean.setCategory(category);
                setFirstProductImage(bean);
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		return bean;
		
	}

	//��ȡĳ�ַ����µĲ�Ʒ����
	public int getTotal(int cid){
		int total =0;
		try(Connection c = DBUtil.getConnection();Statement s = c.createStatement();){
			String sql = "select count(*) from Product where cid = " + cid;
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
	//��ҳ��ѯ
	
	//��ѯ������Ʒ
	public List<Product> list() {
        return list(0,Short.MAX_VALUE);
    }
	
	//��ѯָ��ҳ������Ʒ
    public List<Product> list(int start, int count) {
        List<Product> beans = new ArrayList<Product>();
 
        String sql = "select * from Product limit ?,? ";
  
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
 
            ps.setInt(1, start);
            ps.setInt(2, count);
  
            ResultSet rs = ps.executeQuery();
  
            while (rs.next()) {
                Product bean = new Product();
                int id = rs.getInt(1);
                int cid = rs.getInt("cid");
                String name = rs.getString("name");
                String subTitle = rs.getString("subTitle");
                float orignalPrice = rs.getFloat("orignalPrice");
                float promotePrice = rs.getFloat("promotePrice");
                int stock = rs.getInt("stock");
                Date createDate = DateUtil.t2d( rs.getTimestamp("createDate"));
 
                bean.setName(name);
                bean.setSubTitle(subTitle);
                bean.setOriginalPrice(orignalPrice);
                bean.setPromotePrice(promotePrice);
                bean.setStock(stock);
                bean.setCreateDate(createDate);
                bean.setId(id);
 
                Category category = new CategoryDAO().get(cid);
                bean.setCategory(category);
                beans.add(bean);
            }
        } catch (SQLException e) {
  
            e.printStackTrace();
        }
        return beans;
    }   
 
    //��ѯĳ�ַ����µĲ�Ʒ
    public List<Product> list(int cid) {
        return list(cid,0, Short.MAX_VALUE);
    }
  
    //��ѯĳ�ַ����µ�ָ��ҳ������Ʒ
    public List<Product> list(int cid, int start, int count) {
        List<Product> beans = new ArrayList<Product>();
        Category category = new CategoryDAO().get(cid);
        String sql = "select * from Product where cid = ? order by id desc limit ?,? ";
  
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setInt(1, cid);
            ps.setInt(2, start);
            ps.setInt(3, count);
  
            ResultSet rs = ps.executeQuery();
  
            while (rs.next()) {
                Product bean = new Product();
                int id = rs.getInt(1);
                String name = rs.getString("name");
                String subTitle = rs.getString("subTitle");
                float orignalPrice = rs.getFloat("orignalPrice");
                float promotePrice = rs.getFloat("promotePrice");
                int stock = rs.getInt("stock");
                Date createDate = DateUtil.t2d( rs.getTimestamp("createDate"));
 
                bean.setName(name);
                bean.setSubTitle(subTitle);
                bean.setOriginalPrice(orignalPrice);
                bean.setPromotePrice(promotePrice);
                bean.setStock(stock);
                bean.setCreateDate(createDate);
                bean.setId(id);
                bean.setCategory(category);
                setFirstProductImage(bean);
                beans.add(bean);
            }
        } catch (SQLException e) {
  
            e.printStackTrace();
        }
        return beans;
    }	

    //Ϊ��������Ʒ����
    public void fill(List<Category> cs) {
        for (Category c : cs)
            fill(c);
    }
   
    public void fill(Category c) {
            List<Product> ps = this.list(c.getId());
            c.setProducts(ps);
    }
    
    //Ϊ�����������productsByRow����
    /*
            ����һ������ǡ�ö�Ӧ40�ֲ�Ʒ����ô��40�ֲ�Ʒ�����Ƿ���һ������List� 
           ���ǣ���ҳ������ʾ��ʱ����Ҫÿ8�ֲ�Ʒ������һ��
            Ϊ����ʾ�ķ��㣬�Ұ���40�ֲ�Ʒ������ÿ8�ֲ�Ʒ����һ��������ķ�ʽ����ֳ���5��С�ļ���
     */
    public void fillByRow(List<Category> cs) {
        int productNumberEachRow = 8;
        for (Category c : cs) {
            List<Product> products =  c.getProducts();
            List<List<Product>> productsByRow =  new ArrayList<>();
            for (int i = 0; i < products.size(); i+=productNumberEachRow) {
                int size = i+productNumberEachRow;
                size= size>products.size()?products.size():size;
                List<Product> productsOfEachRow =products.subList(i, size);
                productsByRow.add(productsOfEachRow);
            }
            c.setProductsByRow(productsByRow);
        }
    }
    
    //һ����Ʒ�ж��ͼƬ������ֻ��һ����ͼƬ���ѵ�һ��ͼƬ����Ϊ��ͼƬ
    public void setFirstProductImage(Product p) {
        List<ProductImage> pis= new ProductImageDAO().list(p, ProductImageDAO.type_single);
        if(!pis.isEmpty())
            p.setFirstProductImage(pis.get(0));    
    }
    
    //Ϊ��Ʒ�������ۺ���������
    public void setSaleAndReviewNumber(Product p) {
        int saleCount = new OrderItemDAO().getSaleCount(p.getId());
        p.setSaleCount(saleCount);         
 
        int reviewCount = new ReviewDAO().getCount(p.getId());
        p.setReviewCount(reviewCount);
         
    }
  
    public void setSaleAndReviewNumber(List<Product> products) {
        for (Product p : products) {
            setSaleAndReviewNumber(p);
        }
    }
    
    //���ݹؼ��ֲ�ѯ��Ʒ
    public List<Product> search(String keyword, int start, int count) {
        List<Product> beans = new ArrayList<Product>();
         
        if(null==keyword||0==keyword.trim().length())
            return beans;
           String sql = "select * from Product where name like ? limit ?,? ";
     
           try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
               ps.setString(1, "%"+keyword.trim()+"%");
               ps.setInt(2, start);
               ps.setInt(3, count);
     
               ResultSet rs = ps.executeQuery();
     
               while (rs.next()) {
                   Product bean = new Product();
                   int id = rs.getInt(1);
                   int cid = rs.getInt("cid");
                   String name = rs.getString("name");
                   String subTitle = rs.getString("subTitle");
                   float orignalPrice = rs.getFloat("orignalPrice");
                   float promotePrice = rs.getFloat("promotePrice");
                   int stock = rs.getInt("stock");
                   Date createDate = DateUtil.t2d( rs.getTimestamp("createDate"));

                   bean.setName(name);
                   bean.setSubTitle(subTitle);
                   bean.setOriginalPrice(orignalPrice);
                   bean.setPromotePrice(promotePrice);
                   bean.setStock(stock);
                   bean.setCreateDate(createDate);
                   bean.setId(id);

                   Category category = new CategoryDAO().get(cid);
                   bean.setCategory(category);
                   setFirstProductImage(bean);               
                   beans.add(bean);
               }
           } catch (SQLException e) {
     
               e.printStackTrace();
           }
           return beans;
   }
}
