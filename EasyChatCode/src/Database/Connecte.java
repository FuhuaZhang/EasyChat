package Database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connecte {

	String url="jdbc:mysql://127.0.0.1:3306/qq?useUnicode=true&characterEncoding=UTF-8";
	String username="root";
	String password="";
	
	//��������
	static{
		//String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String driver = "com.mysql.jdbc.Driver";
		//1.������������
		try{
			Class.forName(driver);
			System.out.println("������������ɹ�...");
		}catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	//2.���ݿ�����
	public Connection getConn(){
		Connection conn = null;
		try{
			conn = DriverManager.getConnection(url,username,password);
			System.out.println("���ݿ����ӳɹ�...");
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return conn;
		//����Ҫ�رգ���Ϊ���Ǿֲ������������Զ��ر�
	}

}
