package activemq.chat2.service;

import activemq.chat2.entity.MessageData;

public class SenderService {
	Sender sender = new Sender();
	
	/**
	 * 初始化
	 * 方法用途: <br>
	 * 实现步骤: <br>
	 */
	public void init(){
		sender.init("q.two");
	}
	
	/**
	 * 发送消息
	 * 方法用途: <br>
	 * 实现步骤: <br>
	 * @param data
	 */
	public void sendMessage(MessageData data){
		String msg = data.toString();
		try {
			System.out.println("发送信息====="+msg);
			sender.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
