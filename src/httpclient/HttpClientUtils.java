package httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author evan
 * @version 1.0.0
 *          <p>
 *          Company:workway
 *          </p>
 *          <p>
 *          Copyright:Copyright(c)2018
 *          </p>
 * @date 2018/7/9
 */
public class HttpClientUtils {
	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

	public static final String CHARSET_UTF_8 = "UTF-8";
	public static final String CONTENT_TYPE_TEXT_HTML = "text/xml";
	public static final String CONTENT_TYPE_FORM_URL = "application/x-www-form-urlencoded";
	public static final String CONTENT_TYPE_JSON_URL = "application/json;charset=utf-8";
	private static PoolingHttpClientConnectionManager pool;
	private static RequestConfig requestConfig;

	static {
		try {
			SSLContext sslContext = SSLContext.getDefault();
			SSLConnectionSocketFactory sslSf = new SSLConnectionSocketFactory(sslContext);

			Registry socketFactoryRegistry = RegistryBuilder.create()
					.register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslSf).build();

			pool = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

			pool.setMaxTotal(200);

			pool.setDefaultMaxPerRoute(2);

			int socketTimeout = 10000;
			int connectTimeout = 10000;
			int connectionRequestTimeout = 10000;

			requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout)
					.setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
		} catch (NoSuchAlgorithmException e) {
			logger.error("Http client init Exception_" + e.getMessage(), e);
		}

		requestConfig = RequestConfig.custom().setSocketTimeout(50000).setConnectTimeout(50000)
				.setConnectionRequestTimeout(50000).build();
	}

	/**
	 * 初始化
	 *
	 * @return
	 */
	private static CloseableHttpClient getHttpClient() {
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(pool)
				.setDefaultRequestConfig(requestConfig).setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
				.build();

		return httpClient;
	}

	private static String sendHttpBase(HttpEntityEnclosingRequestBase httpBase) {
		CloseableHttpClient httpClient;
		CloseableHttpResponse response = null;

		String responseContent = null;
		try {
			httpClient = getHttpClient();

			httpBase.setConfig(requestConfig);

			response = httpClient.execute(httpBase);

			HttpEntity entity = response.getEntity();

			if (response.getStatusLine().getStatusCode() >= 300) {
				throw new Exception(
						"HTTP Request is not success, Response code is " + response.getStatusLine().getStatusCode());
			}

			if (200 == response.getStatusLine().getStatusCode()) {
				responseContent = EntityUtils.toString(entity, "utf-8");
				EntityUtils.consume(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseContent;
	}

	public static String sendHttpPost(String httpUrl, String params) {
		HttpPost httpPost = new HttpPost(httpUrl);
		try {
			if ((params != null) && (params.trim().length() > 0)) {
				StringEntity stringEntity = new StringEntity(params, CHARSET_UTF_8);
				stringEntity.setContentType(CONTENT_TYPE_FORM_URL);
				httpPost.setEntity(stringEntity);
			}
		} catch (Exception e) {
			logger.error("sendHttpPost Exception_" + e.getMessage(), e);
		}
		return sendHttpBase(httpPost);
	}

	public static String sendHttpPost(String httpUrl, Map<String, String> maps) {
		String params = convertStringParams(maps);
		return sendHttpPost(httpUrl, params);
	}

	public static String sendHttpPostJson(String httpUrl, String paramsJson) {
		HttpPost httpPost = new HttpPost(httpUrl);
		try {
			if ((paramsJson != null) && (paramsJson.trim().length() > 0)) {
				StringEntity stringEntity = new StringEntity(paramsJson, CHARSET_UTF_8);
				stringEntity.setContentType(CONTENT_TYPE_JSON_URL);
				httpPost.setEntity(stringEntity);
			}
		} catch (Exception e) {
			logger.error("sendHttpPostJson Exception_" + e.getMessage(), e);
		}
		return sendHttpBase(httpPost);
	}

	public static String sendHttpPostXml(String httpUrl, String paramsXml) {
		HttpPost httpPost = new HttpPost(httpUrl);
		try {
			if ((paramsXml != null) && (paramsXml.trim().length() > 0)) {
				StringEntity stringEntity = new StringEntity(paramsXml, CHARSET_UTF_8);
				stringEntity.setContentType(CONTENT_TYPE_TEXT_HTML);
				httpPost.setEntity(stringEntity);
			}
		} catch (Exception e) {
			logger.error("sendHttpPostXml Exception_" + e.getMessage(), e);
		}
		return sendHttpBase(httpPost);
	}

	private static String convertStringParams(Map parameterMap) {
		StringBuffer parameterBuffer = new StringBuffer();
		if (parameterMap != null && parameterMap.size() > 0) {
			Iterator iterator = parameterMap.keySet().iterator();
			String key;
			String value;
			while (iterator.hasNext()) {
				key = (String) iterator.next();
				if (parameterMap.get(key) != null) {
					value = (String) parameterMap.get(key);
				} else {
					value = "";
				}
				parameterBuffer.append(key).append("=").append(value);
				if (iterator.hasNext()) {
					parameterBuffer.append("&");
				}
			}
		}
		return parameterBuffer.toString();
	}

	// public static String httpPostWithJSON(String url, String json) throws
	// Exception {
	// HttpPost httpPost = new HttpPost(url);
	// CloseableHttpClient client = HttpClients.createDefault();
	// String respContent = null;
	// json = URLDecoder.decode(json, CHARSET_UTF_8);
	// StringEntity entity = new StringEntity(json, CHARSET_UTF_8);
	// entity.setContentEncoding(CHARSET_UTF_8);
	// entity.setContentType("application/json");
	// httpPost.setEntity(entity);
	// HttpResponse resp = client.execute(httpPost);
	// if (resp.getStatusLine().getStatusCode() == 200) {
	// HttpEntity he = resp.getEntity();
	// respContent = EntityUtils.toString(he, CHARSET_UTF_8);
	// }
	// return respContent;
	// }
}