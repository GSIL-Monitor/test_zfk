package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnect {
	public static void main(String[] args) {
		try {
			//将ojdbc6.jar资源载入类加载器
			//注册驱动类
			Class.forName("oracle.jdbc.OracleDriver");
//			System.setProperty("jdbc.drivers", "oracle.jdbc.OracleDriver");
//			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
//			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			//编写一个连接字符窜
			String url = "jdbc:oracle:thin:@192.168.1.109:1521:ora11g";
//			String url = "jdbc:sqlserver://localhost:1433;DatabaseName=test01";
			String user = "robot";
			String password = "123456";
			//获取Connection对象
			Connection conn = DriverManager.getConnection(url, user, password);
			System.out.println(conn);
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
