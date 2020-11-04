package tmall.bean;

//User类主要就是提供id, name ,password的getter和setter

public class User {
	private int id;
	private String name;
	private String password;
	
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	//获取该用户的匿名名称，在评价时显示用户名使用
	public String getAnoymousName(){
		
		//C语言中预防编译出错的习惯
		if(null==name){
			return null;
		}
		if(name.length()<=1){
			
			//直接返回*
			return "*";
		}
		if(name.length()==2){
			
			//返回第一个子串加*
			return name.substring(0, 1)+"*";
		}
		
		//name长度超过2时
		char[] cs =name.toCharArray();
		//中间部分设为*
		for(int i=1;i<cs.length-1;i++){
			cs[i]='*';
		}
		return new String(cs);
	}
}
