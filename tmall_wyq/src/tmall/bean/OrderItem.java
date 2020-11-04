package tmall.bean;



public class OrderItem {
	
	//基本属性
	private int id;
	private int number;
	
	//与Product的多对一关系
	private Product product;
	//与User的多对一关系
	private User user;
	//与Order的多对一关系	
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
