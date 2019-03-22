package Database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connecte {

	String url="jdbc:mysql://127.0.0.1:3306/qq?useUnicode=true&characterEncoding=UTF-8";
	String username="root";
	String password="";
	
	//驱动程序
	static{
		//String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String driver = "com.mysql.jdbc.Driver";
		//1.加载驱动程序
		try{
			Class.forName(driver);
			System.out.println("调入驱动程序成功...");
		}catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	//2.数据库连接
	public Connection getConn(){
		Connection conn = null;
		try{
			conn = DriverManager.getConnection(url,username,password);
			System.out.println("数据库连接成功...");
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return conn;
		//不需要关闭，因为他是局部变量，用完自动关闭
	}

}
