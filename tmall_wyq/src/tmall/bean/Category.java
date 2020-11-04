package tmall.bean;

import java.util.List;

public class Category {
	
	//��������
	private int id;
	private String name;
	
	//�ṩһ�Զ��ϵProduct��getter��setter
	List<Product> products;
	
	//��һ�������ֶ�Ӧ��� List<Product>���ṩ������ԣ�
	//��Ϊ������ҳ��״�����ķ��������ұ���ʾ��Ʒ�б�
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
	
	//��ʾ��дtoString����������ӡCategory�����ʱ�򣬻��ӡ������
	@Override
	public String toString() {
		return "Category [name=" + name + "]";
	}
	
	
}
