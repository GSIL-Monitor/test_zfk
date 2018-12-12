package third;

public class WeatherModel {
	//城市
	String city;
	
	//时间-日期
	String date;
	
	//时间-星期
	String week;
	
	//天气
	String type;
	
	//最高温度
	String highTmp;
	
	//最低温度
	String lowTmp;
	
	//风向
	String windDir;
	
	//风力，级数
	String windSc;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHighTmp() {
		return highTmp;
	}

	public void setHighTmp(String highTmp) {
		this.highTmp = highTmp;
	}

	public String getLowTmp() {
		return lowTmp;
	}

	public void setLowTmp(String lowTmp) {
		this.lowTmp = lowTmp;
	}

	public String getWindDir() {
		return windDir;
	}

	public void setWindDir(String windDir) {
		this.windDir = windDir;
	}

	public String getWindSc() {
		return windSc;
	}

	public void setWindSc(String windSc) {
		this.windSc = windSc;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WeatherModel {city=");
		builder.append(city);
		builder.append(", date=");
		builder.append(date);
		builder.append(", week=");
		builder.append(week);
		builder.append(", type=");
		builder.append(type);
		builder.append(", highTmp=");
		builder.append(highTmp);
		builder.append(", lowTmp=");
		builder.append(lowTmp);
		builder.append(", windDir=");
		builder.append(windDir);
		builder.append(", windSc=");
		builder.append(windSc);
		builder.append("}");
		return builder.toString();
	}
	
	
}
