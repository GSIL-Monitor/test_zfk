package activemq.chat2.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

import activemq.chat2.User;
import activemq.chat2.entity.MessageData;
import activemq.chat2.entity.Session;
import activemq.chat2.entity.SessionStatus;

public class ReceiverService {
	Map<String, Session> sessionMap = new ConcurrentHashMap<String, Session>();

	//Receiver receiver = new Receiver();

	public void init() {
		new Thread() {
			@Override
			public void run() {
				Receiver receiver = new Receiver();
				receiver.init("q.one");
				try {
					receiver.receive(new MessageListener() {
						@Override
						public void onMessage(Message msg) {
							TextMessage message = (TextMessage) msg;
							try {
								System.out.println("接收消息=====" + message.getText());
								MessageData data = new ObjectMapper().readValue(message.getText(), MessageData.class);
								String robotId = data.getRobotId();
								if (sessionMap.get(robotId) != null) {
									sessionMap.get(robotId).getMsgDataQueue().offer(data);
									sessionMap.get(robotId).setLastTime(new Date());
								} else {
									Session session = new Session();
									session.setRobotId(robotId);
									session.setRobotName(data.getRobotName());
									session.getMsgDataQueue().offer(data);
									session.setLastTime(new Date());
									sessionMap.put(robotId, session);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}.start();
		
		//删除那些长期没回答的session
		new Thread(){
			public void run() {
				while(true){
					try {
						Thread.sleep(60000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Set<String> set = sessionMap.keySet();
					Iterator<String> it = set.iterator();
					while(it.hasNext()){
						String key = it.next();
						Session session = sessionMap.get(key);
						long lastTimeLong = session.getLastTime().getTime();
						long nowTimeLong = System.currentTimeMillis();
						if(nowTimeLong - lastTimeLong > 2*60000){
							sessionMap.remove(key);
						}
					}
				}
				
			};
		}.start();

	}

	/**
	 * 
	 * 方法用途: 获取对话状态，用于机器人状态列表展示<br>
	 * 实现步骤: <br>
	 * 
	 * @return
	 */
	public List<SessionStatus> getSessionStatusList() {
		List<SessionStatus> list = new ArrayList<SessionStatus>();
		Set<String> set = sessionMap.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String key = it.next();
			Session session = sessionMap.get(key);
			SessionStatus sessionStatus = new SessionStatus();
			sessionStatus.setRobotId(session.getRobotId());
			sessionStatus.setRobotName(session.getRobotName());
			sessionStatus.setUserId(session.getUserId());
			sessionStatus.setUserName(session.getUserName());
			sessionStatus.setBusy(session.getBusy());
			sessionStatus.setLastTime(session.getLastTime());
			sessionStatus.setMessageDataSize(session.getMsgDataQueue().size());

			list.add(sessionStatus);
		}
		return list;
	}

	/**
	 * 获取会话
	 * 方法用途: <br>
	 * 实现步骤: <br>
	 * @param robotId
	 * @return
	 */
	public Session getSession(String robotId) {
		Session session = sessionMap.get(robotId);
		return session;
	}
	
	/**
	 * 
	 * 方法用途: 得到当前用户所进行的对话<br>
	 * 实现步骤: <br>
	 * @param userId
	 * @return
	 */
	public List<Session> getSessionListByUserId(String userId)
	{
		List<Session> list = new ArrayList<Session>();
		Set<String> set = sessionMap.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String key = it.next();
			Session session = sessionMap.get(key);
			if(userId.equals(session.getUserId())){
				list.add(session);
			}
		}
		return list;
	}
	

	/**
	 * 
	 * 方法用途: 分配会话<br>
	 * 实现步骤: <br>
	 * 
	 * @param user
	 * @param robotId
	 */
	public Session dispatchSession(User user, String robotId) {
		Session session = getSessionNoBusy(robotId);
		if (session == null) {
			return null;
		}
		session.setUserId(user.getUserId());
		session.setUserName(user.getUserName());
		return session;
	}
	
	/**
	 * 
	 * 方法用途: 获取不忙的对话<br>
	 * 实现步骤: <br>
	 * 
	 * @param robotId
	 * @return
	 */
	public Session getSessionNoBusy(String robotId) {
		Session session = sessionMap.get(robotId);
		if(session == null){
			return null;
		}
		if (session.getBusy()) {
			return null;
		}
		session.setBusy(true);
		return session;
	}
	
	
}
