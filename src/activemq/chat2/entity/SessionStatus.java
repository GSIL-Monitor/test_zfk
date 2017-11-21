package activemq.chat2.entity;

import java.util.Date;

public class SessionStatus {
	String robotId;
	String robotName;
	
	String userId;
	String userName;
	
	int messageDataSize;
	
	//是否占用
	boolean busy;
	
	Date lastTime;

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

	public int getMessageDataSize() {
		return messageDataSize;
	}

	public void setMessageDataSize(int messageDataSize) {
		this.messageDataSize = messageDataSize;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SessionStatus [robotId=").append(robotId).append(", robotName=").append(robotName)
				.append(", userId=").append(userId).append(", userName=").append(userName).append(", messageDataSize=")
				.append(messageDataSize).append(", busy=").append(busy).append(", lastTime=").append(lastTime)
				.append("]");
		return builder.toString();
	}


	
	
	
}
