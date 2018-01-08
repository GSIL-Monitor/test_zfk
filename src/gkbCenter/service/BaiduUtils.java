package gkbCenter.service;

import com.baidu.aip.nlp.AipNlp;

import util.HttpRequestUtil;

import java.util.HashMap;

public class BaiduUtils {
	private static String clientId = "I4ulSE3RkqOgu2m6U07H5Nan";
	private static String clientSecret = "MO9q04dtjo1hpQm3rTVEhGf6Db0QnM4C";
	private static String AppID = "10557815";

	private static AipNlp client = new AipNlp(AppID, clientId, clientSecret);

	public static AipNlp getAipNlp() {
		return client;
	}

	public static void setAipNlp() {
		client = new AipNlp(AppID, clientId, clientSecret);
	}

	public static String utterance(String question) {
		String talkUrl = "https://aip.baidubce.com/rpc/2.0/solution/v1/unit_utterance";
		try {
			String params = "{\"scene_id\":14981,\"session_id\":\"\",\"query\":\"1+1等于几\"}";
			String accessToken = getAuth(clientId, clientSecret);
			return HttpRequestUtil.sendJsonPost(talkUrl + "?access_token=" + accessToken, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getAuth(String ak, String sk) {
		String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
		String params = "grant_type=client_credentials&client_id=" + ak + "&client_secret=" + sk;
		try {
			String result = HttpRequestUtil.sendPost(authHost, params);
			net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(result);
			return jsonObject.getString("access_token");
		} catch (Exception e) {
			System.err.printf("获取token失败！", new Object[0]);
			e.printStackTrace(System.err);
		}
		return null;
	}

	/**
	 * 情感分析
	 * 
	 * @param question
	 * @return
	 * @throws Exception
	 */
	public static String sentiment(String question) throws Exception {
		return client.sentimentClassify(question).toString();
	}

	/**
	 * 词法分析
	 * 
	 * @param question
	 * @return
	 * @throws Exception
	 */
	public static String lexer(String question) throws Exception {
		return client.lexer(question).toString();
	}

	/**
	 * 
	 * 句法分析
	 * 
	 * @param question
	 * @param options
	 * @return
	 */
	public static String depParser(String question, HashMap<String, Object> options) {
		return client.depParser(question, options).toString();
	}

	/**
	 * dnn分析
	 * 
	 * @param question
	 * @return
	 */
	public static String dnn(String question) {
		return client.dnnlmCn(question).toString();
	}

	/**
	 * 相似度查询
	 * 
	 * @param word11
	 * @param word12
	 * @param model
	 *            使用模型，BOW词包模型，GRNN循环神经网络模型，CNN卷积神经网络模型
	 * 
	 * @return
	 */
	public static String simnet(String word11, String word12, String model) {
		if ((model == null) || (model.length() <= 1)) {
			return client.simnet(word11, word12, null).toString();
		}
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("model", model);
		return client.simnet(word11, word12, options).toString();
	}
}
