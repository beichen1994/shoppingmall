package tmall.util;



//DateUtil������ڹ�������Ҫ������java.util.Date����java.sql.Timestamp ��Ļ���ת����
//��Ϊ��ʵ�������������͵����ԣ�ʹ�õĶ���java.util.Date�ࡣ
//��Ϊ����MySQL�е����ڸ�ʽ�ﱣ��ʱ����Ϣ������ʹ��datetime���͵��ֶΣ�
//��jdbcҪ��ȡdatetime�����ֶε���Ϣ����Ҫ����java.sql.Timestamp����ȡ

public class DateUtil {
	
	public static java.sql.Timestamp d2t(java.util.Date d){
		if(null==d){
			return null;
		}else{
			return new java.sql.Timestamp(d.getTime());
		}
	}
	
	public static java.util.Date t2d(java.sql.Timestamp t){
		if(null==t){
			return null;
		}else{
			return new java.util.Date(t.getTime());
		}
	}
	
}
