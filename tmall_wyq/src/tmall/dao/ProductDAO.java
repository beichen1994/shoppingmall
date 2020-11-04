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

//基础CRUD方法
	
	//增加
	public void add(Product bean){
		String sql = "insert into product values(null,?,?,?,?,?,?,?)";
		
		// try-with-resources 语句，称为 ARM 块(Automatic Resource Management) ，自动资源管理。
		//try 执行完毕后自动被关闭
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setString(1, bean.getName());
            ps.setString(2, bean.getSubTitle());
            ps.setFloat(3, bean.getOriginalPrice());
            ps.setFloat(4, bean.getPromotePrice());
            ps.setInt(5, bean.getStock());
            ps.setTimestamp(6, DateUtil.d2t(bean.getCreateDate()));
            ps.setInt(7, bean.getCategory().getId());
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
			 String sql = "delete from Product where id = " + id;
			s.execute(sql);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	//修改
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

	//根据id查询
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

	//获取某种分类下的产品数量
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

//用于支持业务的方法	
	//分页查询
	
	//查询所有商品
	public List<Product> list() {
        return list(0,Short.MAX_VALUE);
    }
	
	//查询指定页数的商品
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
 
    //查询某种分类下的产品
    public List<Product> list(int cid) {
        return list(cid,0, Short.MAX_VALUE);
    }
  
    //查询某种分类下的指定页数的商品
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

    //为分类填充产品集合
    public void fill(List<Category> cs) {
        for (Category c : cs)
            fill(c);
    }
   
    public void fill(Category c) {
            List<Product> ps = this.list(c.getId());
            c.setProducts(ps);
    }
    
    //为多个分类设置productsByRow属性
    /*
            假设一个分类恰好对应40种产品，那么这40种产品本来是放在一个集合List里。 
           可是，在页面上显示的时候，需要每8种产品，放在一列
            为了显示的方便，我把这40种产品，按照每8种产品方在一个集合里的方式，拆分成了5个小的集合
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
    
    //一个产品有多个图片，但是只有一个主图片，把第一个图片设置为主图片
    public void setFirstProductImage(Product p) {
        List<ProductImage> pis= new ProductImageDAO().list(p, ProductImageDAO.type_single);
        if(!pis.isEmpty())
            p.setFirstProductImage(pis.get(0));    
    }
    
    //为产品设置销售和评价数量
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
    
    //根据关键字查询产品
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
