package log;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.HttpClientUtil;

public class LogTest {

private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	
	public static void main(String[] args) {
		
		System.err.println(String.valueOf(0));
		
		try {
			int i = 0/0;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.error("访问天气接口1错误:"+ e.getMessage(), e);
			logger.error("访问天气接口{}错误:{}", "22", e.getMessage(), e);
			logger.error("访问天气接口2错误", e);
		}
	}

}
