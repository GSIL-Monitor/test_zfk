package activemq.chat2.entity;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

public class Session {
	String robotId;
	String robotName;
	
	String userId;
	String userName;
	
	//消息队列
	Queue<MessageData> msgDataQueue = new LinkedList<MessageData>();
	//是否被用户占用
	boolean busy;
	//是否被用户关闭
	boolean close;
	
	Date lastTime;

	public String getRobotId() {
		return robotId;
	}

	public void setRobotId(String robotId) {
		this.robotId = robotId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRobotName() {
		return robotName;
	}

	public void setRobotName(String ronbotName) {
		this.robotName = ronbotName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Queue<MessageData> getMsgDataQueue() {
		return msgDataQueue;
	}

	public boolean getBusy() {
		return busy;
	}

	public void setBusy(boolean busy) {
		this.busy = busy;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	
	public boolean getClose() {
		return close;
	}

	public void setClose(boolean close) {
		this.close = close;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Session [robotId=").append(robotId).append(", userId=").append(userId).append(", ronbotName=")
				.append(robotName).append(", userName=").append(userName).append(", msgDataQueue=").append(msgDataQueue)
				.append(", busy=").append(busy).append(", lastTime=").append(lastTime).append("]");
		return builder.toString();
	}
	
	
	
}
