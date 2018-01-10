
package redis;

import redis.clients.jedis.Jedis;

public class TestRedis {
	public static void main(String[] args) throws Exception {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
        System.out.println(jedis);
		
		Redis.getInstance().setValue("user-zfk-name", "zhufukun");
		Redis.getInstance().setValue("user-zfk-name", "zhufukun1");
		Redis.getInstance().setValueEx("user-zfk-age", "22", 60);
	}

	
}