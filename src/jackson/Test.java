package jackson;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {
	public static void main(String[] args) throws Exception {
		testNull();
	}

	private static void testNull() throws Exception {
		User user = new User();
		user.setName("小龙");
		//user.setEmail("xiaolong@sina.com");
		user.setAge(20);
		//SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		//user.setBirthday(dateformat.parse("1991-07-12"));
	
		ObjectMapper mapper = new ObjectMapper();

		// User类转JSON
		String json = mapper.writeValueAsString(user);
		System.out.println("mapper======="+json);
		
		System.out.println("JSON======="+JSON.toJSON(user));
		System.out.println("JSON======="+JSON.toJSONString(user));
		System.out.println("JSON======="+JSON.toJSONString(user, SerializerFeature.WriteMapNullValue));
	}
}
