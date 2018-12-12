package third;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import util.HttpClientUtil;

public class WeatherTest {

	private String weatherUrl = "http://wthrcdn.etouch.cn/weather_mini?city=";
	
	private String weatherUrl2 = "https://free-api.heweather.com/s6/weather/forecast?key=e4cf784a68f24e098524a30cb0c1fb1c&location=";
	
	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	
	public static void main(String[] args) {
		
		try {
			int i = 0/0;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.error("访问天气接口1错误:"+ e.getMessage(), e);
			logger.error("访问天气接口{}错误:{}", "22", e.getMessage(), e);
			logger.error("访问天气接口2错误", e);
		}
		
		List<WeatherModel> list = new WeatherTest().getDataOne("深圳", 0, 1);
		
		for(WeatherModel model : list){
			System.out.println(model);
		}
	}
	
	/**
	 * 中华万年历接口
	 * @param city
	 * @param start
	 * @param end
	 * @return
	 */
	private List<WeatherModel> getDataOne(String city, int start, int end){
		List<WeatherModel> tempList = new ArrayList<WeatherModel>();
		List<WeatherModel> list = new ArrayList<WeatherModel>();
		try {
			//http://wthrcdn.etouch.cn/weather_mini?city=深圳
			String url = weatherUrl+city;
			String response = HttpClientUtil.sendHttpGet(url);
			logger.info("访问天气接口1返回:{}", response);
			if(StringUtils.isEmpty(response)){
				return list;
			}
			//当前时间
			Date date = new Date();
			
			JSONObject json = JSON.parseObject(response);
			int status = json.getInteger("status");
			if(status == 1000){
				//昨天
				WeatherModel model = new WeatherModel();
				JSONObject data = json.getJSONObject("data");
				String resCity = data.getString("city");
				String type = data.getJSONObject("yesterday").getString("type");
				String highTmp = data.getJSONObject("yesterday").getString("high");
				String lowTmp = data.getJSONObject("yesterday").getString("low");
				String windDir = data.getJSONObject("yesterday").getString("fx");
				String windSc = data.getJSONObject("yesterday").getString("fl").replaceAll("<!\\[CDATA\\[|\\]\\]>", "");
				//String strDate = data.getJSONObject("yesterday").getString("date");
				
				model.setCity(resCity);
				model.setType(type);
				model.setHighTmp(highTmp);
				model.setLowTmp(lowTmp);
				model.setWindDir(windDir);
				model.setWindSc(windSc);
				//时间
				model.setDate(DateUtils.format(addDate(date, -1), DateUtils.ISO8601_DATE_PATTERN));
				model.setWeek(getWeek4Date(addDate(date, -1)));
				tempList.add(model);
				
				//今天，明天，后天
				JSONArray forecast = data.getJSONArray("forecast");
				for(int i=0; i<forecast.size(); i++){
					model = new WeatherModel();
					type = forecast.getJSONObject(i).getString("type");
					highTmp = forecast.getJSONObject(i).getString("high");
					lowTmp = forecast.getJSONObject(i).getString("low");
					windDir = forecast.getJSONObject(i).getString("fengxiang");
					windSc = forecast.getJSONObject(i).getString("fengli").replaceAll("<!\\[CDATA\\[|\\]\\]>", "");
					
					model.setCity(resCity);
					model.setType(type);
					model.setHighTmp(highTmp);
					model.setLowTmp(lowTmp);
					model.setWindDir(windDir);
					model.setWindSc(windSc);
					//时间
					model.setDate(DateUtils.format(addDate(date, i), DateUtils.ISO8601_DATE_PATTERN));
					model.setWeek(getWeek4Date(addDate(date, i)));
					tempList.add(model);
				}
			}
			
			//取出想要的
			for(int i=0; i<tempList.size();i++){
				if(start <i && i <= end){
					list.add(tempList.get(i));
				}
			}
			
		} catch (Exception e) {
			logger.error("访问天气接口1错误:{}", e.getMessage());
		}
		
        return list;
	}
	
	
	/**
	 * 和风网接口
	 * @param city
	 * @param start
	 * @param end
	 * @return
	 */
	private List<WeatherModel> getDataTwo(String city, int start, int end) {
		List<WeatherModel> tempList = new ArrayList<WeatherModel>();
		List<WeatherModel> list = new ArrayList<WeatherModel>();
		try {
			//https://free-api.heweather.com/s6/weather/forecast?key=e4cf784a68f24e098524a30cb0c1fb1c&location=深圳
			String url = weatherUrl2+city;
			String response = HttpClientUtil.sendHttpGet(url);
			logger.info("访问天气接口2返回:{}", response);
			if(StringUtils.isEmpty(response)){
				return list;
			}
			//当前时间
			Date date = new Date();
			
			JSONObject json = JSON.parseObject(response);
			JSONObject HeWeather6 = json.getJSONArray("HeWeather6").getJSONObject(0);
			
			String status = HeWeather6.getString("status");
	        if("ok".equals(status)){
	        	String resCity = HeWeather6.getJSONObject("basic").getString("location");
				//今天，明天，后天
				JSONArray forecast = HeWeather6.getJSONArray("daily_forecast");
				for(int i=0; i<forecast.size(); i++){
					WeatherModel model = new WeatherModel();
					String type = forecast.getJSONObject(i).getString("cond_txt_d");
					String highTmp = forecast.getJSONObject(i).getString("tmp_max");
					String lowTmp = forecast.getJSONObject(i).getString("tmp_min");
					String windDir = forecast.getJSONObject(i).getString("wind_dir");
					String windSc = forecast.getJSONObject(i).getString("wind_sc");
					
					model.setCity(resCity);
					model.setType(type);
					model.setHighTmp(highTmp);
					model.setLowTmp(lowTmp);
					model.setWindDir(windDir);
					model.setWindSc(windSc);
					//时间
					model.setDate(DateUtils.format(addDate(date, i), DateUtils.ISO8601_DATE_PATTERN));
					model.setWeek(getWeek4Date(addDate(date, i)));
					//昨天
					if(i == 0){
						WeatherModel model0 = new WeatherModel();
						model0.setCity(resCity);
						model0.setType(type);
						model0.setHighTmp(highTmp);
						model0.setLowTmp(lowTmp);
						model0.setWindDir(windDir);
						model0.setWindSc(windSc);
						//时间
						model0.setDate(DateUtils.format(addDate(date, -1), DateUtils.ISO8601_DATE_PATTERN));
						model0.setWeek(getWeek4Date(addDate(date, -1)));
						tempList.add(model0);
					}
					tempList.add(model);
				}
	        }
	        //取出想要的
			for(int i=0; i<tempList.size();i++){
				if(start <i && i <= end){
					list.add(tempList.get(i));
				}
			}
		} catch (Exception e) {
			logger.error("访问天气接口2错误:{}", e.getMessage());
		}
		
        return list;
	}
	
	
	private Date addDate(Date date, int amount){
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(date);
		calendar.add(Calendar.DATE, amount);
		date = calendar.getTime();
		return date;
	}
	

	
	private String getWeek4Date(Date date){
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(date);
		String dayOfWeek[] = {"", "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"}; 
        return dayOfWeek[calendar.get(Calendar.DAY_OF_WEEK)]; 
	}

}
