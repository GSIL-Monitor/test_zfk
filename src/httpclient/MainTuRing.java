package httpclient;

public class MainTuRing {
	public static void main(String[] args) {
		tuRing();
	}
	
	/**
	 * 图灵机器人问答 方法用途: <br>
	 * 实现步骤: <br>
	 */
	private static void tuRing() {
		String url = "http://www.tuling123.com/openapi/api";
		String body = "{\"key\":\"58d5790fe5434a71acac5de4f9885bb4\"," + "\"info\":\"今天天气怎么样\"," + "\"userid\":\"1111\""
				+ "}";
		String response = HttpClientUtil.doPost2Content(url, null, null, body);
		System.out.println(response);

		body = "{\"key\":\"58d5790fe5434a71acac5de4f9885bb4\"," + "\"info\":\"来凤\"," + "\"userid\":\"1111\"" + "}";
		response = HttpClientUtil.doPost2Content(url, null, null, body);
		System.out.println(response);

		body = "{\"key\":\"58d5790fe5434a71acac5de4f9885bb4\"," + "\"info\":\"鱼香肉丝怎么做\"," + "\"userid\":\"1111\"" + "}";
		response = HttpClientUtil.doPost2Content(url, null, null, body);
		System.out.println(response);
	}
}
