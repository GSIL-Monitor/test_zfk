package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * 
 * <p>
 * Description:
 * </p>
 * 
 * @date 2017年12月11日
 * @author 朱富昆
 * @version 1.0.0
 *          <p>
 *          Company:workway
 *          </p>
 *          <p>
 *          Copyright:Copyright(c)2017
 *          </p>
 */
public class HttpRequestUtil {

	/**
	 * 发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendGet(String url) {
		return sendGet(url, "UTF-8");
	}

	/**
	 * 发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param charset
	 *            编码格式
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String charset) {
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			// 遍历所有的响应头字段
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，json字符串
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendJsonPost(String url, String json) {
		return sendJsonPost(url, json, "UTF-8");
	}

	/**
	 * 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，json字符串
	 * @param charset
	 *            编码格式
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendJsonPost(String url, String json, String charset) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			connection.setDoOutput(true);
			connection.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(connection.getOutputStream());
			// 发送请求参数
			out.print(json);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param MapParam
	 *            请求参数
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, Map<String, Object> MapParam) {
		return sendPost(url, MapParam, "UTF-8");
	}

	/**
	 * 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param MapParam
	 *            请求参数
	 * @param charset
	 *            编码格式
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, Map<String, Object> MapParam, String charset) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			StringBuffer param = new StringBuffer();
			if ((MapParam != null) && (MapParam.size() > 0)) {
				for (Map.Entry<String, Object> e : MapParam.entrySet()) {
					param.append((String) e.getKey());
					param.append("=");
					param.append(e.getValue());
					param.append("&");
				}
			}
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			connection.setDoOutput(true);
			connection.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(connection.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		return sendPost(url, param, "UTF-8");
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @param charset
	 *            编码格式
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param, String charset) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			connection.setDoOutput(true);
			connection.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(connection.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

}
