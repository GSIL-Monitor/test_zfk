package mybatis;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisDBUtil {
	static SqlSessionFactory sqlSessionFactory;
	
	public static SqlSessionFactory getSqlSessionFactory(){
		if(sqlSessionFactory==null){
//			InputStream inputStream = Resources.getResourceAsStream("config.xml");
			InputStream inputStream = MybatisDBUtil.class.getClassLoader().getResourceAsStream("config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		}
		return sqlSessionFactory;
	}
	
	public static void main(String[] args) {
		System.out.println(getSqlSessionFactory());
	}
}
