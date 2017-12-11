package httpclient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

/**
 * HttpClient工具
 * 
 * @author ZHUFUKUN
 * 
 */
public class HttpClientUtil
{
    private final static Log log = LogFactory.getLog(HttpClientUtil.class);

    private static HttpClientUtil instance = null;

    private HttpClientUtil()
    {
    }

    public synchronized static HttpClientUtil getInstance()
    {
        if (instance == null)
        {
            instance = new HttpClientUtil();
        }
        return instance;
    }

    /**
     * get方法获取流
     * 
     * @param url
     *            地址
     * @param headers
     *            请求头，可为null
     * @param params
     *            参数，可为null
     * @return
     */
    public static InputStream doGet2Stream(String url, Header[] headers, List<NameValuePair> params)
    {
        return doGet2Stream(url, headers, params, 60, 60, 1);
    }

    /**
     * get方法获取流
     * 
     * @param url
     *            地址
     * @param headers
     *            请求头，可为null
     * @param params
     *            参数，可为null
     * @param connectionTimeout
     *            连接超时，数字，单位秒
     * @param soTimeout
     *            响应超时，数字，单位秒
     * @param tryCount
     *            参数，尝试次数
     * @return
     */
    public static InputStream doGet2Stream(String url, Header[] headers, List<NameValuePair> params,
            int connectionTimeout, int soTimeout, int tryCount)
    {
        HttpGet get = null;
        try
        {
            CloseableHttpClient httpclient = HttpClients.createDefault();

            // 此代理不保证你看到的时候还存活
            // HttpHost proxy = new HttpHost("u120-227.static.grapesc.cz",
            // 8080);
            // httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
            // proxy);

            // 如果代理要认证，则加上以下语句
            // httpclient.getCredentialsProvider().setCredentials(new
            // AuthScope("proxy adress", proxy port),
            // new UsernamePasswordCredentials("username", "password"));

            // 记得将网址拆成以下形式
            // HttpHost targetHost = new HttpHost("www.cnblogs.com"); //
            // 网站名前面不要加http://
            // get = new HttpGet("/FengYan/");

            get = new HttpGet(url);
            // 设置连接超时60秒
            get.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout * 1000);
            // 设置响应超时60秒
            get.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout * 1000);

            // 设置请求头
            if (headers != null)
            {
                for (Header header : headers)
                {
                    get.setHeader(header);
                }
            }
            // 设置参数
            if (params != null && params.size() > 0)
            {

                String paramsStr = EntityUtils.toString(new UrlEncodedFormEntity(params));
                get.setURI(new URI(url + "?" + paramsStr));
            }
            HttpResponse response = httpclient.execute(get);
            StatusLine status = response.getStatusLine();
            if (200 != status.getStatusCode())
            {
                if (tryCount > 1)
                {
                    doGet2Stream(url, headers, params, connectionTimeout, soTimeout, --tryCount);
                }
                return null;
            }
            HttpEntity entity = response.getEntity();
            InputStream in = entity.getContent();
            return in;
        } catch (Exception e)
        {
            log.error("HttpClientUtil.doGet2Stream()  " + e.getMessage());
            if (tryCount > 1)
            {
                doGet2Stream(url, headers, params, connectionTimeout, soTimeout, --tryCount);
            }
            return null;
        } finally
        {
            if (get != null)
            {
                // get.releaseConnection();
            }
        }
    }

    /**
     * post方法获取流
     * 
     * @param url
     * 
     * @param headers
     *            消息头，可为null
     * @param params
     *            参数集合，可为null
     * @param body
     *            请求体，可为null，支持String，byte[]，InputStream类型
     * @return
     */
    public static InputStream doPost2Stream(String url, Header[] headers, List<NameValuePair> params, Object body)
    {
        return doPost2Stream(url, headers, params, body, 60, 60, 1);
    }

    /**
     * post方法获取流
     * 
     * @param url
     * 
     * @param headers
     *            消息头，可为null
     * @param params
     *            参数集合，可为null
     * @param body
     *            请求体，可为null，支持String，byte[]，InputStream类型
     * @param connectionTimeout
     *            连接超时，数字，单位秒
     * @param soTimeout
     *            响应超时，数字，单位秒
     * @param tryCount
     *            参数，尝试次数
     * @return
     */
    public static InputStream doPost2Stream(String url, Header[] headers, List<NameValuePair> params, Object body,
            int connectionTimeout, int soTimeout, int tryCount)
    {
        HttpPost post = null;
        try
        {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            post = new HttpPost(url);
            // 设置连接超时60秒
            post.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout * 1000);
            // 设置响应超时60秒
            post.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout * 1000);

            // 设置请求头
            if (headers != null)
            {
                for (Header header : headers)
                {
                    post.setHeader(header);
                }
            }
            // 参数
            if (params != null)
            {
                post.setEntity(new UrlEncodedFormEntity(params));
            }
            // 请求体
            if (body != null)
            {

                if (body instanceof String)
                {
                    StringEntity strEntity = new StringEntity(body.toString(), "UTF-8");
                    post.setEntity(strEntity);
                } else if (body instanceof byte[])
                {
                    ByteArrayEntity bytesEntity = new ByteArrayEntity((byte[]) body);
                    post.setEntity(bytesEntity);
                } else if (body instanceof InputStream)
                {
                    InputStreamEntity streamEntity = new InputStreamEntity((InputStream) body);
                    post.setEntity(streamEntity);
                }
            }
            HttpResponse response = httpclient.execute(post);
            StatusLine status = response.getStatusLine();
            if (200 != status.getStatusCode())
            {
                if (tryCount > 1)
                {
                    doPost2Stream(url, headers, params, body, connectionTimeout, soTimeout, --tryCount);
                }
                return null;
            }
            HttpEntity entity = response.getEntity();
            InputStream in = entity.getContent();
            return in;
        } catch (IOException e)
        {
            log.error("HttpClientUtil.doPost2Stream()  " + e.getMessage());
            if (tryCount > 1)
            {
                doPost2Stream(url, headers, params, body, connectionTimeout, soTimeout, --tryCount);
            }
            return null;
        } finally
        {
            if (post != null)
            {
                // post.releaseConnection();
            }
        }
    }

    /**
     * get方法获取字节数组
     * 
     * @param url
     *            地址
     * @param headers
     *            请求头，可为null
     * @param params
     *            参数，可为null
     * @return
     */
    public static byte[] doGet2Byte(String url, Header[] headers, List<NameValuePair> params)
    {
        return doGet2Byte(url, headers, params, 60, 60, 1);
    }

    /**
     * get方法获取字节数组
     * 
     * @param url
     *            地址
     * @param headers
     *            请求头，可为null
     * @param params
     *            参数，可为null
     * @param connectionTimeout
     *            连接超时，数字，单位秒
     * @param soTimeout
     *            响应超时，数字，单位秒
     * @param tryCount
     *            参数，尝试次数
     * @return
     */
    public static byte[] doGet2Byte(String url, Header[] headers, List<NameValuePair> params,
            int connectionTimeout, int soTimeout, int tryCount)
    {
        HttpGet get = null;
        CloseableHttpClient httpclient = null;
        try
        {
            httpclient = HttpClients.createDefault();
            get = new HttpGet(url);
            // 设置连接超时60秒
            get.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout * 1000);
            // 设置响应超时60秒
            get.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout * 1000);

            // 设置请求头
            if (headers != null)
            {
                for (Header header : headers)
                {
                    get.setHeader(header);
                }
            }
            // 设置参数
            if (params != null && params.size() > 0)
            {
                String paramsStr = EntityUtils.toString(new UrlEncodedFormEntity(params));
                get.setURI(new URI(url + "?" + paramsStr));
            }
            HttpResponse response = httpclient.execute(get);
            StatusLine status = response.getStatusLine();
            if (200 != status.getStatusCode())
            {
                if (tryCount > 1)
                {
                    doGet2Byte(url, headers, params, connectionTimeout, soTimeout, --tryCount);
                }
                return null;
            }
            HttpEntity entity = response.getEntity();
            byte[] bytes = EntityUtils.toByteArray(entity);
            return bytes;
        } catch (Exception e)
        {
            log.error("HttpClientUtil.doGet2Byte()  " + e.getMessage());
            if (tryCount > 1)
            {
                doGet2Byte(url, headers, params, connectionTimeout, soTimeout, --tryCount);
            }
            return null;
        } finally
        {
            if (get != null)
                get.releaseConnection();
            if (httpclient != null)
                try
                {
                    httpclient.close();
                } catch (IOException e)
                {
                    log.error("方法异常", e);
                }
        }
    }

    /**
     * get方法获取字节数组
     * 
     * @param url
     *            地址
     * @param headers
     *            请求头，可为null
     * @param params
     *            参数，可为null
     * @param connectionTimeout
     *            连接超时，数字，单位秒
     * @param soTimeout
     *            响应超时，数字，单位秒
     * @param limitByteLong
     *            限制获取数据长度(字节)
     * @return
     */
    public static byte[] doGet2ByteLimit(String url, Header[] headers, List<NameValuePair> params,
            int connectionTimeout, int soTimeout, int limitByteLong)
    {
        HttpGet get = null;
        CloseableHttpClient httpclient = null;
        InputStream in = null;
        ByteArrayOutputStream bos = null;
        try
        {
            httpclient = HttpClients.createDefault();
            get = new HttpGet(url);
            // 设置连接超时60秒
            get.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout * 1000);
            // 设置响应超时60秒
            get.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout * 1000);

            // 设置请求头
            if (headers != null)
            {
                for (Header header : headers)
                {
                    get.setHeader(header);
                }
            }
            // 设置参数
            if (params != null && params.size() > 0)
            {

                String paramsStr = EntityUtils.toString(new UrlEncodedFormEntity(params));
                get.setURI(new URI(url + "?" + paramsStr));
            }
            HttpResponse response = httpclient.execute(get);
            StatusLine status = response.getStatusLine();
            if (200 != status.getStatusCode())
            {
                return null;
            }
            // 获取返回体
            HttpEntity entity = response.getEntity();
            in = entity.getContent();
            long clong = entity.getContentLength();
            if (clong >= limitByteLong)
            {
                return null;
            }
            bos = new ByteArrayOutputStream();
            // 对input进行解析
            byte[] bs = new byte[1024 * 1024];
            int len = 0;
            while ((len = in.read(bs)) != -1)
            {
                bos.write(bs, 0, len);
                if (bos.size() >= limitByteLong)
                {
                    return new byte[0];
                }
            }
            // HttpEntity entity = response.getEntity();
            // byte[] bytes = EntityUtils.toByteArray(entity);
            byte[] bytes = bos.toByteArray();
            return bytes;
        } catch (Exception e)
        {
            log.error("HttpClientUtil.doGet2Byte()  " + e.getMessage());
            return null;
        } finally
        {
            if (get != null)
                get.releaseConnection();
            if (httpclient != null)
                try
                {
                    httpclient.close();
                } catch (IOException e)
                {
                    log.error("方法异常", e);
                }
            try
            {
                if (in != null)
                    in.close();
                if (bos != null)
                    bos.close();
            } catch (IOException e)
            {
                log.error("方法异常", e);
            }
        }
    }

    /**
     * post方法获取字节数组
     * 
     * @param url
     * 
     * @param headers
     *            消息头，可为null
     * @param params
     *            参数集合，可为null
     * @param body
     *            请求体，可为null，支持String，byte[]，InputStream类型
     * @return
     */
    public static byte[] doPost2Byte(String url, Header[] headers, List<NameValuePair> params, Object body)
    {
        return doPost2Byte(url, headers, params, body, 60, 60, 1);
    }

    /**
     * post方法获取字节数组
     * 
     * @param url
     * 
     * @param headers
     *            消息头，可为null
     * @param params
     *            参数集合，可为null
     * @param body
     *            请求体，可为null，支持String，byte[]，InputStream类型
     * @param connectionTimeout
     *            连接超时，数字，单位秒
     * @param soTimeout
     *            响应超时，数字，单位秒
     * @param tryCount
     *            参数，尝试次数
     * @return
     */
    public static byte[] doPost2Byte(String url, Header[] headers, List<NameValuePair> params, Object body,
            int connectionTimeout, int soTimeout, int tryCount)
    {
        HttpPost post = null;
        CloseableHttpClient httpclient = null;
        try
        {
            httpclient = HttpClients.createDefault();
            post = new HttpPost(url);
            // 设置连接超时60秒
            post.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout * 1000);
            // 设置响应超时60秒
            post.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout * 1000);

            // 设置请求头
            if (headers != null)
            {
                for (Header header : headers)
                {
                    post.setHeader(header);
                }
            }
            // 参数
            if (params != null)
            {
                post.setEntity(new UrlEncodedFormEntity(params));
            }
            // 请求体
            if (body != null)
            {

                if (body instanceof String)
                {
                    StringEntity strEntity = new StringEntity(body.toString(), "UTF-8");
                    post.setEntity(strEntity);
                } else if (body instanceof byte[])
                {
                    ByteArrayEntity bytesEntity = new ByteArrayEntity((byte[]) body);
                    post.setEntity(bytesEntity);
                } else if (body instanceof InputStream)
                {
                    InputStreamEntity streamEntity = new InputStreamEntity((InputStream) body);
                    post.setEntity(streamEntity);
                }
            }
            HttpResponse response = httpclient.execute(post);
            StatusLine status = response.getStatusLine();
            if (200 != status.getStatusCode())
            {
                if (tryCount > 1)
                {
                    doPost2Byte(url, headers, params, body, connectionTimeout, soTimeout, --tryCount);
                }
                return null;
            }
            HttpEntity entity = response.getEntity();
            byte[] bytes = EntityUtils.toByteArray(entity);
            return bytes;
        } catch (IOException e)
        {
            log.error("HttpClientUtil.doPost2Stream()  " + e.getMessage());
            if (tryCount > 1)
            {
                doPost2Byte(url, headers, params, body, connectionTimeout, soTimeout, --tryCount);
            }
            return null;
        } finally
        {
            if (post != null)
                post.releaseConnection();
            if (httpclient != null)
                try
                {
                    httpclient.close();
                } catch (IOException e)
                {
                    log.error("方法异常", e);
                }
        }
    }

    /**
     * get方法获取文本
     * 
     * @param url
     *            地址
     * @param headers
     *            请求头，可为null
     * @param params
     *            参数，可为null
     * @return
     */
    public static String doGet2Content(String url, Header[] headers, List<NameValuePair> params)
    {
        return doGet2Content(url, headers, params, "UTF-8", 60, 60, 1);
    }

    /**
     * get方法获取文本
     * 
     * @param url
     *            地址
     * @param headers
     *            请求头，可为null
     * @param params
     *            参数，可为null
     * @param charset
     *            编码格式
     * @return
     */
    public static String doGet2Content(String url, Header[] headers, List<NameValuePair> params, String charset)
    {
        return doGet2Content(url, headers, params, charset, 60, 60, 1);
    }

    /**
     * get方法获取文本
     * 
     * @param url
     *            地址
     * @param headers
     *            请求头，可为null
     * @param params
     *            参数，可为null
     * @param connectionTimeout
     *            连接超时，数字，单位秒
     * @param soTimeout
     *            响应超时，数字，单位秒
     * @param tryCount
     *            参数，尝试次数
     * @return
     */
    public static String doGet2Content(String url, Header[] headers, List<NameValuePair> params,
            int connectionTimeout, int soTimeout, int tryCount)
    {
        return doGet2Content(url, headers, params, "UTF-8", connectionTimeout, soTimeout, tryCount);
    }

    /**
     * get方法获取文本
     * 
     * @param url
     *            地址
     * @param headers
     *            请求头，可为null
     * @param params
     *            参数，可为null
     * @param charset
     *            编码格式
     * @param connectionTimeout
     *            连接超时，数字，单位秒
     * @param soTimeout
     *            响应超时，数字，单位秒
     * @param tryCount
     *            参数，尝试次数
     * @return
     */
    public static String doGet2Content(String url, Header[] headers, List<NameValuePair> params, String charset,
            int connectionTimeout, int soTimeout, int tryCount)
    {
        HttpGet get = null;
        InputStream in = null;
        ByteArrayOutputStream bos = null;
        try
        {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            get = new HttpGet(url);
            // 设置连接超时60秒
            get.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout * 1000);
            // 设置响应超时60秒
            get.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout * 1000);

            // 设置请求头
            if (headers != null)
            {
                for (Header header : headers)
                {
                    get.setHeader(header);
                }
            }
            // 设置参数
            if (params != null && params.size() > 0)
            {

                String paramsStr = EntityUtils.toString(new UrlEncodedFormEntity(params));
                get.setURI(new URI(url + "?" + paramsStr));
            }

            HttpResponse response = httpclient.execute(get);
            StatusLine status = response.getStatusLine();
            if (200 != status.getStatusCode())
            {
                if (tryCount > 1)
                {
                    doGet2Content(url, headers, params, charset, connectionTimeout, soTimeout, --tryCount);
                }
                return null;
            }
            // 获取返回体
            HttpEntity entity = response.getEntity();
            in = entity.getContent();
            bos = new ByteArrayOutputStream();
            // 对input进行解析
            byte[] bs = new byte[1024 * 1024];
            int len = 0;
            while ((len = in.read(bs)) != -1)
            {
                bos.write(bs, 0, len);
            }
            String content = new String(bos.toByteArray(), charset);
            return content;
        } catch (Exception e)
        {
            log.error("HttpClientUtil.doGet2Content()  " + e.getMessage());
            if (tryCount > 1)
            {
                doGet2Content(url, headers, params, charset, connectionTimeout, soTimeout, --tryCount);
            }
            return null;
        } finally
        {
            if (get != null)
                get.releaseConnection();
            try
            {
                if (in != null)
                    in.close();
                if (bos != null)
                    bos.close();
            } catch (IOException e)
            {
                log.error("方法异常", e);
            }
        }
    }

    /**
     * post方法获取文本
     * 
     * @param url
     * 
     * @param headers
     *            消息头，可为null
     * @param params
     *            参数集合，可为null
     * @param body
     *            请求体，可为null，支持String，byte[]，InputStream类型
     * @return
     */
    public static String doPost2Content(String url, Header[] headers, List<NameValuePair> params, Object body)
    {
        return doPost2Content(url, headers, params, body, "UTF-8", 60, 60, 1);
    }

    /**
     * post方法获取文本
     * 
     * @param url
     * 
     * @param headers
     *            消息头，可为null
     * @param params
     *            参数集合，可为null
     * @param body
     *            请求体，可为null，支持String，byte[]，InputStream类型
     * @param charset
     *            编码格式
     * @return
     */
    public static String doPost2Content(String url, Header[] headers, List<NameValuePair> params, Object body,
            String charset)
    {
        return doPost2Content(url, headers, params, body, charset, 60, 60, 1);
    }

    /**
     * post方法获取文本
     * 
     * @param url
     * 
     * @param headers
     *            消息头，可为null
     * @param params
     *            参数集合，可为null
     * @param body
     *            请求体，可为null，支持String，byte[]，InputStream类型
     * @param connectionTimeout
     *            连接超时，数字，单位秒
     * @param soTimeout
     *            响应超时，数字，单位秒
     * @param tryCount
     *            尝试次数
     * @return
     */
    public static String doPost2Content(String url, Header[] headers, List<NameValuePair> params, Object body,
            int connectionTimeout, int soTimeout, int tryCount)
    {
        return doPost2Content(url, headers, params, body, "UTF-8", connectionTimeout, soTimeout, tryCount);
    }

    /**
     * post方法获取文本
     * 
     * @param url
     * 
     * @param headers
     *            消息头，可为null
     * @param params
     *            参数集合，可为null
     * @param body
     *            请求体，可为null，支持String，byte[]，InputStream类型
     * @param charset
     *            编码格式
     * @param connectionTimeout
     *            连接超时，数字，单位秒
     * @param soTimeout
     *            响应超时，数字，单位秒
     * @param tryCount
     *            尝试次数
     * @return
     */
    public static String doPost2Content(String url, Header[] headers, List<NameValuePair> params, Object body,
            String charset, int connectionTimeout, int soTimeout, int tryCount)
    {
        HttpPost post = null;
        InputStream in = null;
        try
        {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            post = new HttpPost(url);
            // 设置连接超时60秒
            post.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout * 1000);
            // 设置响应超时60秒
            post.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout * 1000);

            // 设置请求头
            if (headers != null)
            {
                for (Header header : headers)
                {
                    post.setHeader(header);
                }
            }
            if (params != null)
            {
                // 参数
                post.setEntity(new UrlEncodedFormEntity(params));
            }
            if (body != null)
            {
                // 创建发送内容
                if (body instanceof String)
                {
                    StringEntity strEntity = new StringEntity(body.toString(), charset);
                    post.setEntity(strEntity);
                } else if (body instanceof byte[])
                {
                    ByteArrayEntity bytesEntity = new ByteArrayEntity((byte[]) body);
                    post.setEntity(bytesEntity);
                } else if (body instanceof InputStream)
                {
                    InputStreamEntity streamEntity = new InputStreamEntity((InputStream) body);
                    post.setEntity(streamEntity);
                }
            }
            HttpResponse response = httpclient.execute(post);
            StatusLine status = response.getStatusLine();
            if (200 != status.getStatusCode())
            {
                if (tryCount > 1)
                {
                    doPost2Content(url, headers, params, body, charset, connectionTimeout, soTimeout, --tryCount);
                }
                return null;
            }
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity, "UTF-8");
            return content;
        } catch (IOException e)
        {
            log.error("HttpClientUtil.doPost2Stream()  " + e.getMessage());
            if (tryCount > 1)
            {
                doPost2Content(url, headers, params, body, charset, connectionTimeout, soTimeout, --tryCount);
            }
            return null;
        } finally
        {
            if (post != null)
                post.releaseConnection();
            try
            {
                if (in != null)
                    in.close();

            } catch (IOException e)
            {
                log.error("方法异常", e);
            }
        }
    }

//    /**
//     * 文件上传到gers系统
//     * 
//     * @param url
//     * @param headers
//     * @param jsonParam
//     * @param bytes
//     * @return
//     */
//    public static String upload2Gers(String url, Header[] headers, JSONObject jsonParam, byte[] fileBytes)
//    {
//        HttpPost post = null;
//        InputStream in = null;
//        try
//        {
//            CloseableHttpClient httpclient = HttpClients.createDefault();
//
//            post = new HttpPost(url);
//            // 设置连接超时60秒
//            post.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
//            // 设置响应超时60秒
//            post.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5 * 60000);
//
//            // 设置请求头
//            if (headers != null)
//            {
//                for (Header header : headers)
//                {
//                    post.setHeader(header);
//                }
//            }
//
//            // 请求参数字符
//            if (jsonParam.toString() == null)
//            {
//                return "请添加参数";
//            }
//            if (fileBytes == null)
//            {
//                fileBytes = new byte[0];
//            }
//            // 请求参数字节
//            byte[] paramsByte = jsonParam.toString().getBytes("UTF-8");
//            // 请求参数长度
//            byte[] paramsLenByte = NetDataTypeTransform.intToByteArray(paramsByte.length);
//            // 上传文件
//            byte[] bodyByte = new byte[4 + paramsByte.length + fileBytes.length];
//
//            System.arraycopy(paramsLenByte, 0, bodyByte, 0, 4);
//            System.arraycopy(paramsByte, 0, bodyByte, 4, paramsByte.length);
//            System.arraycopy(fileBytes, 0, bodyByte, 4 + paramsByte.length, fileBytes.length);
//
//            ByteArrayEntity bytesEntity = new ByteArrayEntity(bodyByte);
//            post.setEntity(bytesEntity);
//
//            HttpResponse response = httpclient.execute(post);
//            StatusLine status = response.getStatusLine();
//            if (200 != status.getStatusCode())
//            {
//                return null;
//            }
//            HttpEntity entity = response.getEntity();
//            String content = EntityUtils.toString(entity, "UTF-8");
//            return content;
//        } catch (Exception e)
//        {
//            log.error("HttpClientUtil.upload2Gers()  " + e.getMessage());
//            return null;
//        } finally
//        {
//            if (post != null)
//                post.releaseConnection();
//            try
//            {
//                if (in != null)
//                    in.close();
//
//            } catch (IOException e)
//            {
//                log.error("方法异常", e);
//            }
//        }
//    }
//
//    /**
//     * 多文件上传到gers系统
//     * 
//     * @param url
//     * @param headers
//     * @param jsonParam
//     * @param bytes
//     * @return
//     */
//    public static String uploadFiles2Gers(String url, Header[] headers, JSONObject jsonParam,
//            List<byte[]> fileByteList)
//    {
//        HttpPost post = null;
//        InputStream in = null;
//        try
//        {
//            CloseableHttpClient httpclient = HttpClients.createDefault();
//
//            post = new HttpPost(url);
//            // 设置连接超时60秒
//            post.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
//            // 设置响应超时60秒
//            post.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5 * 60000);
//
//            // 设置请求头
//            if (headers != null)
//            {
//                for (Header header : headers)
//                {
//                    post.setHeader(header);
//                }
//            }
//            /*
//             * 1.字符
//             */
//            // 请求参数字符
//            if (jsonParam.toString() == null)
//            {
//                return "请添加参数";
//            }
//
//            // 文件参数字符，文件长度：（1024,2048）
//            String fileParam = "0";
//            // 文件字节
//            byte[] fileBytes = new byte[0];
//            if (fileByteList != null)
//            {
//                fileParam = "";
//                int allByteLen = 0;
//                for (int i = 0; i < fileByteList.size(); i++)
//                {
//                    int len = fileByteList.get(i) == null ? 0 : fileByteList.get(i).length;
//                    allByteLen += len;
//                    if (fileParam.length() == 0)
//                    {
//                        fileParam += len;
//                    } else
//                    {
//                        fileParam += ("," + len);
//                    }
//                }
//                fileBytes = new byte[allByteLen];
//                int index = 0;
//                for (byte[] fileByte : fileByteList)
//                {
//                    if (fileByte != null)
//                    {
//                        System.arraycopy(fileByte, 0, fileBytes, index, fileByte.length);
//                        index += fileByte.length;
//                    }
//                }
//            }
//            /*
//             * 2.字节
//             */
//            // 请求参数
//            byte[] paramByte = jsonParam.toString().getBytes("UTF-8");
//            // 请求参数长度
//            byte[] paramLenByte = NetDataTypeTransform.intToByteArray(paramByte.length);
//
//            // 文件参数
//            byte[] fileParamByte = fileParam.getBytes("UTF-8");
//            // 文件参数长度
//            byte[] fileParamLenByte = NetDataTypeTransform.intToByteArray(fileParamByte.length);
//
//            // 请求体
//            byte[] bodyByte = new byte[4 + paramByte.length + 4 + fileParamByte.length + fileBytes.length];
//
//            int index = 0;
//            System.arraycopy(paramLenByte, 0, bodyByte, index, 4);
//            index += 4;
//            System.arraycopy(paramByte, 0, bodyByte, index, paramByte.length);
//            index += paramByte.length;
//            System.arraycopy(fileParamLenByte, 0, bodyByte, index, 4);
//            index += 4;
//            System.arraycopy(fileParamByte, 0, bodyByte, index, fileParamByte.length);
//            index += fileParamByte.length;
//            System.arraycopy(fileBytes, 0, bodyByte, index, fileBytes.length);
//            index += fileBytes.length;
//
//            ByteArrayEntity bytesEntity = new ByteArrayEntity(bodyByte);
//            post.setEntity(bytesEntity);
//
//            HttpResponse response = httpclient.execute(post);
//            StatusLine status = response.getStatusLine();
//            if (200 != status.getStatusCode())
//            {
//                return null;
//            }
//            HttpEntity entity = response.getEntity();
//            String content = EntityUtils.toString(entity, "UTF-8");
//            return content;
//        } catch (Exception e)
//        {
//            log.error("HttpClientUtil.upload2Gers()  " + e.getMessage());
//            return null;
//        } finally
//        {
//            if (post != null)
//                post.releaseConnection();
//            try
//            {
//                if (in != null)
//                    in.close();
//
//            } catch (IOException e)
//            {
//                log.error("方法异常", e);
//            }
//        }
//    }

   
        

}
