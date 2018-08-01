package mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
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
//		getByIdMap();
//		System.out.println("############");
//		listByPage();
//		System.out.println("############");
//		listByPageMap();
		
		//##BaseMapper
//		List<Map> list = typeScoreGroupAvg();
//		System.out.println(list);
		
		findActionScope();

	}

	private static void findActionScope() throws IOException {
		SqlSession session = sqlSessionFactory.openSession();
		Map map = new HashMap();
		//map.put("subsystemId", "subsystemId");
		List<Map<String, Object>> datas = session.selectList("mybatis.dao.BaseMapper.findActionScope", map);
		if(datas.size() == 0){
			System.out.println("##########无数据");
			return;
		}
		
		Map<String, Map<String,List<String>>> resultMap = new HashMap<String, Map<String,List<String>>>();
		for(Map<String, Object> data : datas){
			//数据
			String subsystemId = data.get("subsystemId").toString();
			String useId = data.get("useId").toString();
			List<String> robotIds = (List<String>) data.get("robotIds");
			//外面键
			String key = "actionScope:"+subsystemId;
			
			if(resultMap.containsKey(key)){
				//包含
				Map<String,List<String>> value = resultMap.get(key);
				value.put(useId, robotIds);
			}else{
				//没有包含
				HashMap<String, List<String>> value = new HashMap<String,List<String>>();
				value.put(useId, robotIds);
				resultMap.put(key, value);
			}
		}
		System.out.println("All-Map==="+resultMap);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = mapper.writeValueAsString(resultMap);
		System.out.println("All-jsonStr==="+jsonStr);
		
		
		//单引号处理
		mapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

	    //1
		resultMap = (Map<String, Map<String, List<String>>>) mapper.readValue(jsonStr, Map.class);
		System.out.println("All-Map==="+resultMap);
		
		//更新
		HashMap<String, List<String>> valueMap = (HashMap<String, List<String>>) resultMap.get("actionScope:8771a68be48911e792d400505687a0a9");
		jsonStr = mapper.writeValueAsString(valueMap);
		System.out.println("Sub-jsonStr==="+jsonStr);
		
		//2
		HashMap<String, List<String>> value = (HashMap<String, List<String>>) mapper.readValue(jsonStr, Map.class);
		//HashMap<String, List<String>> value = mapper.readValue(jsonStr, new TypeReference<HashMap<String, List<String>>>() {});
		List<String> newRobotIds = new ArrayList<String>();
		newRobotIds.add("################");
		value.put("ea83e46f3d6711e8884400505687a0a9", newRobotIds);
		System.out.println("Sub-Map==="+value);
		value.remove("ea83e46f3d6711e8884400505687a0a9@@");
		System.out.println("Sub-Map==="+value);
		
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
