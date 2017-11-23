package activemq.chat2.entity;


import java.util.Date;
import com.alibaba.fastjson.JSONObject;

public class MessageData{
	/*机器人信息*/
	String robotId;
	String robotName;
	
	/*用户信息*/
	String userId;
	String userName;
	
	/*内容*/
	String content;
	/*时间*/
	Date time;
	
	public String getRobotId() {
		return robotId;
	}
	public void setRobotId(String robotId) {
		this.robotId = robotId;
	}
	public String getRobotName() {
		return robotName;
	}
	public void setRobotName(String robotName) {
		this.robotName = robotName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}
