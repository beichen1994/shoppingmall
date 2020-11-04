package tmall.bean;

public class Property {
	
	//基本属性
	private int id;
	private String name;	
	//与Category的多对一关系
	private Category category;

	
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	

}
