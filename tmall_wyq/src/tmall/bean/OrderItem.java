package tmall.bean;



public class OrderItem {
	
	//��������
	private int id;
	private int number;
	
	//��Product�Ķ��һ��ϵ
	private Product product;
	//��User�Ķ��һ��ϵ
	private User user;
	//��Order�Ķ��һ��ϵ	
	private Order order;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
	
}
