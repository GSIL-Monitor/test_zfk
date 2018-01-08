package gkbCenter.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import util.HttpRequestUtil;

@Service
public class TulingService {
	private static final String TuLingURL = "http://www.tuling123.com/openapi/api";
	private static final String KEY = "58d5790fe5434a71acac5de4f9885bb4";

	public String getAnswer(String question) {
		return getAnswer(question, null);
	}

	public String getAnswer(String question, String user) {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("key", KEY);
			paramMap.put("info", question);
			if ((user != null) && (user.length() > 0))
				paramMap.put("userid", user);
			String result = HttpRequestUtil.sendPost(TuLingURL, paramMap);

			JSONObject jsonObject = JSONObject.parseObject(result);

			String err_code = jsonObject.getString("code");
			if (err_code.equals("100000")) {
				return jsonObject.getString("text");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("图灵数据接口错误:" + e.getMessage() + " question:" + question);
		}
		return null;
	}
}
