package tmall.bean;

//User����Ҫ�����ṩid, name ,password��getter��setter

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
	
	//��ȡ���û����������ƣ�������ʱ��ʾ�û���ʹ��
	public String getAnoymousName(){
		
		//C������Ԥ����������ϰ��
		if(null==name){
			return null;
		}
		if(name.length()<=1){
			
			//ֱ�ӷ���*
			return "*";
		}
		if(name.length()==2){
			
			//���ص�һ���Ӵ���*
			return name.substring(0, 1)+"*";
		}
		
		//name���ȳ���2ʱ
		char[] cs =name.toCharArray();
		//�м䲿����Ϊ*
		for(int i=1;i<cs.length-1;i++){
			cs[i]='*';
		}
		return new String(cs);
	}
}
