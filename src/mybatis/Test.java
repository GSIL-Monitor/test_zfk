package mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import mybatis.dto.PageSearchDTO;
import mybatis.entity.WayMessagePublishDO;
import mybatis.entity.WayRobotActionScopeDO;

public class Test {
	static SqlSessionFactory sqlSessionFactory = MybatisDBUtil.getSqlSessionFactory();

	public static void main(String[] args) throws IOException {
		InputStream inputStream = Resources.getResourceAsStream("config.xml");
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession session = sqlSessionFactory.openSession();
		System.out.println(session);
		//###WayMessagePublishMapper
		getByIdMap();
		System.out.println("############");
		listByPage();
		System.out.println("############");
		listByPageMap();
		
		//##BaseMapper
//		List<Map> list = typeScoreGroupAvg();
//		System.out.println(list);

	}

	private static List<Map> typeScoreGroupAvg() {
		SqlSession session = sqlSessionFactory.openSession();
		Map map = new HashMap();
		Date beginTime = new Date(); 
		beginTime.setDate(25);
		System.out.println(beginTime);
		LocalDateTime endTime = LocalDateTime.now();
		System.out.println(endTime);
		
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		List<Map> list = session.selectList("mybatis.dao.BaseMapper.typeScoreGroupAvg", map);
		return list;
	}

	private static List<Map> scoreGroup() {
		SqlSession session = sqlSessionFactory.openSession();
		Map map = new HashMap();
		Date beginTime = new Date(); 
		beginTime.setDate(25);
		System.out.println(beginTime);
		LocalDateTime endTime = LocalDateTime.now();
		System.out.println(endTime);
		
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		List<Map> list = session.selectList("mybatis.dao.BaseMapper.scoreGroup", map);
		return list;
	}

	private static List<WayRobotActionScopeDO> listScopes() {
		try {
			SqlSession session = sqlSessionFactory.openSession();
			List<WayRobotActionScopeDO> list = session.selectList("mybatis.dao.WayMessagePublishMapper.listActionScopes", "3");
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

	private static void insert() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"messageTitle\":\"月租宝\",");
		sb.append("\"beginTime\": \"2017-10-23 09:09:09\",");
		sb.append("\"endTime\": \"2017-10-29 09:09:09\",");
		sb.append("\"publishStatus\": \"0\",");
		sb.append("\"messageContent\": \"月租宝是XXXX\",");
		sb.append("\"actionScopes\":[{");
		sb.append("\"scopeType\":\"1\",");
		sb.append("\"scopeId\":\"robotId1111\",");
		sb.append("\"scopeName\":\"小龙人一号\"");
		sb.append("},");
		sb.append("{");
		sb.append("\"scopeType\":\"2\",");
		sb.append("\"scopeId\":\"robotTypeId1111\",");
		sb.append("\"scopeName\":\"小龙人类型\"");
		sb.append("}],");
		sb.append("\"subsystemId\": \"subsystemId1111\",");
		sb.append("\"modifiedBy\": \"userid\"");
		sb.append("}");
		
	}

	private static void getById() throws JsonProcessingException {
		SqlSession session = sqlSessionFactory.openSession();
		WayMessagePublishDO message = session.selectOne("mybatis.dao.WayMessagePublishMapper.getById", 1126L);
		System.out.println(message);
		session.close();
	}
	
	private static void getByIdMap() throws JsonProcessingException {
		SqlSession session = sqlSessionFactory.openSession();
		Map<String,Object> message = session.selectOne("mybatis.dao.WayMessagePublishMapper.getByIdMap", 1126L);
		System.out.println(message);
		session.close();
	}

	private static void listByPage() throws JsonProcessingException {
		SqlSession session = sqlSessionFactory.openSession();

		PageSearchDTO dto = new PageSearchDTO();
		dto.setOffset(0);
		dto.setLimit(10);
		dto.setKey("aa");
		// dto.setSearchContents(map);

		List<WayMessagePublishDO> list = session.selectList("mybatis.dao.WayMessagePublishMapper.listByPage", dto);
		for (WayMessagePublishDO d : list) {
			System.out.println(d.getMessageTitle());
		}

		ObjectMapper mapper = new ObjectMapper();

		// //User类转JSON//
		// //User类转JSON//
		String json = mapper.writeValueAsString(list);
		System.out.println(json);

		session.close();

	}
	
	private static void listByPageMap() throws JsonProcessingException {
		SqlSession session = sqlSessionFactory.openSession();
		//Map<String, Object> map = new HashMap<String, Object>();
		// map.put("offset", 0);
		// map.put("limit", 10);
		// map.put("key", null);
		// map.put("messageTitle", "小龙");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("offset", 0);
		map.put("limit", 10);
		map.put("key","aa");
		// dto.setSearchContents(map);

		List<Map<String,Object>> list = session.selectList("mybatis.dao.WayMessagePublishMapper.listByPageMap", map);
		for (Map<String,Object> m : list) {
			System.out.println(m.get("messageTitle"));
		}

		System.out.println("###############");
		ObjectMapper mapper = new ObjectMapper();

		// //User类转JSON//
		// //User类转JSON//
		String json = mapper.writeValueAsString(list);
		System.out.println(json);

		session.close();

	}

}
