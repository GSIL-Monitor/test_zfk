package db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;

public class DBUtil {
	static String driver = null;
	static String url = null;
	static String username = null;
	static String password = null;
	//连接池
//	static DataSource ds;
	static BasicDataSource ds;
	//只加载一次
	static{
		try{
			Properties prop = new Properties();
			//将db.properties以InputStream方式载入
			InputStream inStream = DBUtil.class.getClassLoader().getResourceAsStream("db/db.properties");
			prop.load(inStream);
			//读取prop中文件信息
			driver = prop.getProperty("driver");
			url = prop.getProperty("url");
			username = prop.getProperty("username");
			password = prop.getProperty("password");
			//创建连接池对象，设置连接池参数
			BasicDataSource bds = new BasicDataSource();
			bds.setDriverClassName(driver);
			bds.setUrl(url);
			bds.setUsername(username);
			bds.setPassword(password);
			bds.setMaxActive(20);//最大连接数
			bds.setInitialSize(2);//初始连接数
			System.out.println(driver);
//			System.out.println(url);
//			System.out.println(username);
//			System.out.println(password);
			ds=bds;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/*
	 * 返回一个Connection对象
	 */
	public static Connection getConnection(){
		try {
//			Class.forName("oracle.jdbc.OracleDriver");
//			String url = "jdbc:oracle:thin:@192.168.0.23:1521:tarena";
//			jdbc:mysql:@
//			String username = "gg2014";
//			String password = "gg2014";
//			Connection con = DriverManager.getConnection(url, username, password);
			return ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * 关闭传入的Connection对象
	 */
	public static void closeConnection(Connection con){
		try {
			if(con != null){
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/*
	 * 关闭Statement对象
	 */
	public static void closeStatement(Statement state){
		try {
			if(state != null){
				state.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/*
	 * 关闭ResultSet对象
	 */
	public static void closePreparedStatement(PreparedStatement pst){
		try {
			if(pst != null){
				pst.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 关闭ResultSet对象
	 */
	public static void closeResultSet(ResultSet rs){
		try {
			if(rs != null){
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Connection con,Statement state,ResultSet rs){
		closeResultSet(rs);
		closeStatement(state);
		closeConnection(con);
	}
	
	public static void close(Connection con,Statement state){
		closeStatement(state);
		closeConnection(con);
	}
	
	public static void close(Connection con,PreparedStatement pst,ResultSet rs){
		closeResultSet(rs);
		closePreparedStatement(pst);
		closeConnection(con);
	}
	
	public static void close(Connection con,PreparedStatement pst){
		closePreparedStatement(pst);
		closeConnection(con);
	}
	
	public static void main(String[] args) {
		Connection con = DBUtil.getConnection();
		System.out.println(con);
		closeConnection(con);
	}
	
}
