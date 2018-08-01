package jackson;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonDemo {
	public static void main(String[] args) throws ParseException, IOException {
		User user = new User();
		user.setName("小龙");
		user.setEmail("xiaolong@sina.com");
		user.setAge(20);
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		user.setBirthday(dateformat.parse("1991-07-12"));

		User user2 = new User();
		user2.setName("小强");
		user2.setEmail("xiaoQ@sina.com");
		user2.setAge(20);
		user2.setBirthday(dateformat.parse("1991-04-01"));

		User user3 = new User();
		user3.setName("小明");
		user3.setEmail("xiaomin@sina.com");
		user3.setAge(20);
		user3.setBirthday(dateformat.parse("1992-10-01"));

		List<User> users = new ArrayList<User>();
		users.add(user2);
		users.add(user3);

		user.setFriends(users);

		/**
		 * ObjectMapper是JSON操作的核心，Jackson的所有JSON操作都是在ObjectMapper中实现。
		 * ObjectMapper有多个JSON序列化的方法，可以把JSON字符串保存File、OutputStream等不同的介质中。
		 * writeValue(File arg0, Object arg1)把arg1转成json序列，并保存到arg0文件中。
		 * writeValue(OutputStream arg0, Object arg1)把arg1转成json序列，并保存到arg0输出流中。
		 * writeValueAsBytes(Object arg0)把arg0转成json序列，并把结果输出成字节数组。
		 * writeValueAsString(Object arg0)把arg0转成json序列，并把结果输出成字符串。
		 */
		ObjectMapper mapper = new ObjectMapper();

		// User类转JSON
		String json = mapper.writeValueAsString(user);
		System.out.println(json);

		// Java集合转JSON
		String jsonlist = mapper.writeValueAsString(users);
		System.out.println(jsonlist);

		String jsonStr = "{\"name\":\"小民\",\"age\":20,\"birthday\":\"1991-07-12\",\"email\":\"xiaomin@sina.com\",\"friends\":[{\"name\":\"小强\",\"age\":20,\"birthday\":null,\"email\":\"xiaoQ@sina.com\",\"friends\":[]},{\"name\":\"小明\",\"age\":20,\"birthday\":null,\"email\":\"xiaomin@sina.com\",\"friends\":[]}]}";
		/**
		 * ObjectMapper支持从byte[]、File、InputStream、字符串等数据的JSON反序列化。
		 */

		User user0 = mapper.readValue(jsonStr, User.class);
		System.out.println(user0);

		// map<String,String>转json
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", "jack");
		map.put("city", "beijin");
		String mjson = mapper.writeValueAsString(map);
		System.out.println(mjson);
		// json转map<String,String>
		HashMap<String, String> mmap = mapper.readValue(mjson, HashMap.class);
		System.out.println(mmap);

		// map<String,User>转json
		HashMap<String, User> umap = new HashMap<String, User>();
		umap.put(user.getName(), user);
		String mmjson = mapper.writeValueAsString(umap);
		System.out.println(mmjson);
		// json转map<String,User>
		JavaType jvt = mapper.getTypeFactory().constructParametricType(HashMap.class, String.class, User.class);
		Map<String, User> urMap = mapper.readValue(mmjson, jvt);
		System.out.println(urMap);

		// list<String>转json
		ArrayList<String> list = new ArrayList<String>();
		list.add("jack");
		list.add("tom");
		String ljson = mapper.writeValueAsString(list);
		System.out.println(ljson);
		// json转list<String>
		ArrayList<String> sList = mapper.readValue(ljson, ArrayList.class);
		System.out.println(sList);

		// list<User>转json
		List<User> ulist = new ArrayList<User>();
		ulist.add(user);
		String ujson = mapper.writeValueAsString(ulist);
		System.out.println(ujson);
		// json转list<User>
		JavaType jt = mapper.getTypeFactory().constructParametricType(ArrayList.class, User.class);
		List<User> urlist = mapper.readValue(ujson, jt);
		System.out.println(urlist);

	}
}