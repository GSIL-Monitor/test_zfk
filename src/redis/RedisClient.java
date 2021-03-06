package redis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import util.SerializeUtil;

public class RedisClient {

	private static final Log log = LogFactory.getLog(RedisClient.class);

	private static RedisClient instance = null;

	// 非切片连接池
	private JedisPool jedisPool = null;

	private String host = "127.0.0.1";

	private int port = 6379;

	private String password = "";

	private int timeout = 10 * 1000;

	public synchronized static RedisClient getInstance() {
		if (instance == null) {
			instance = new RedisClient();
		}
		return instance;
	}

	private RedisClient() {
		initialPool();
	}

	/**
	 * 初始化非切片池
	 */
	private void initialPool() {
		try {
			// 池基本配置
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxIdle(20);
			config.setMaxIdle(5);
			config.setMaxTotal(100);
			config.setMaxWaitMillis(5000L);
			config.setTestOnBorrow(false);
			jedisPool = new JedisPool(config, host, port, timeout, password);
		} catch (Exception e) {
			log.error("redis.Redis.initialPool()", e);
			e.printStackTrace();
		}
	}

	/**
	 * 获取jedis连接，方便自己操作
	 * 
	 * @return
	 */
	public Jedis getJedis() {
		return jedisPool.getResource();
	}

	/**
	 * 根据键获取缓存中的值
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String getValue(String key) throws Exception {
		String value = "";
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			value = jedis.get(key);
		} catch (Exception e) {
			instance = null;
			log.error("get jedis exception", e);
			e.printStackTrace();
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return value;

	}

	/**
	 * 根据键获取缓存中的值
	 * 
	 * @param key
	 * @return
	 */
	public Object getObject(String key) {
		Object obj = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			byte[] value = jedis.get(SerializeUtil.serialize(key));

			if (null != value && value.length != 0) {
				obj = SerializeUtil.unserialize(value);
			}
		} catch (Exception e) {
			instance = null;
			e.printStackTrace();
			log.error("get jedis exception", e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}

		return obj;
	}

	/**
	 * 设置数据，不存在则添加
	 * 
	 * @param key
	 *            键名称
	 * @param value
	 *            值
	 */
	public void setValue(String key, String value) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(key, value);
		} catch (Exception e) {
			instance = null;
			e.printStackTrace();
			log.error("set jedis exception", e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 设置数据，不存在则添加
	 * 
	 * @param key
	 *            键名称
	 * @param value
	 *            值
	 */
	public void setValue(Object key, Object value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(SerializeUtil.serialize(key), SerializeUtil.serialize(value));
		} catch (Exception e) {
			instance = null;
			e.printStackTrace();
			log.error("set jedis exception", e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 设置有失效时间的数据
	 * 
	 * @param key
	 *            键名称
	 * @param value
	 *            值
	 * @param seconds
	 *            失效时间，单位秒
	 * @throws Exception
	 */
	public String setValueEx(String key, String value, int seconds) throws Exception {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.setex(key, seconds, value);
		} catch (Exception e) {
			e.printStackTrace();
			instance = null;
			log.error("=============缓存有失效时间的数据失败=============", e);
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 设置有失效时间的数据
	 * 
	 * @param key
	 *            键名称
	 * @param value
	 *            值
	 * @param seconds
	 *            失效时间，单位秒
	 */
	public void setValueEx(Object key, Object value, int seconds) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			// jedis.setex(SerializeUtil.serialize(key), seconds,
			// SerializeUtil.serialize(key));
			jedis.setex(SerializeUtil.serialize(key), seconds, SerializeUtil.serialize(value));
		} catch (Exception e) {
			instance = null;
			e.printStackTrace();
			log.error("set jedisEx exception", e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 根据键删除对应的缓存
	 * 
	 * @param key
	 */
	public void delValue(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.del(key);
		} catch (Exception e) {
			e.printStackTrace();
			instance = null;
			log.error("del jedis exception", e);
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	public Boolean existsObject(Object key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(SerializeUtil.serialize(key));
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }finally{
            if(jedis != null)
            {
                jedis.close();
            }
        }
    }
	
	public Boolean existsObject(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }finally{
            if(jedis != null)
            {
                jedis.close();
            }
        }
    }

}
