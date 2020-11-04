package tmall.bean;

import java.util.List;

public class Category {
	
	//基本属性
	private int id;
	private String name;
	
	//提供一对多关系Product的getter与setter
	List<Product> products;
	
	//即一个分类又对应多个 List<Product>，提供这个属性，
	//是为了在首页竖状导航的分类名称右边显示产品列表。
	List<List<Product>> productsByRow;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<List<Product>> getProductsByRow() {
		return productsByRow;
	}

	public void setProductsByRow(List<List<Product>> productsByRow) {
		this.productsByRow = productsByRow;
	}
	
	//表示重写toString方法，当打印Category对象的时候，会打印其名称
	@Override
	public String toString() {
		return "Category [name=" + name + "]";
	}
	
	
}
