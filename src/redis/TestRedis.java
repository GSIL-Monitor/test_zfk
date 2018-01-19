
package redis;

import redis.clients.jedis.Jedis;

public class TestRedis {
	public static void main(String[] args) throws Exception {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
        System.out.println(jedis);
		
		Redis.getInstance().setValue("way_robot_num_6e770679f9c811e792d400505687a0a9", "0");
//		Redis.getInstance().setValue("user-zfk-name", "zhufukun1");
//		Redis.getInstance().setValueEx("user-zfk-age", "22", 60);
        
        System.out.println(Redis.getInstance().getValue("way_robot_num_6e770679f9c811e792d400505687a0a9"));
        
	}

	
}