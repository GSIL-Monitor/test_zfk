package activemq.chat2;

import java.util.Date;
import java.util.Scanner;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQBytesMessage;
import org.apache.activemq.util.ByteSequence;

import activemq.base.Receiver;
import activemq.base.Sender;
import activemq.chat2.entity.MessageData;

public class Robot1 {
	public static void main(String[] args) throws JMSException {
		new Thread(){
			@Override
			public void run() {
				Sender sender = new Sender();
				//sender.init("q.one",1);
				sender.init("chat-one",2);
				for (int i=1;i<1000;i++) {
					Scanner scan = new Scanner(System.in);
					System.out.println("Robot1：请输入信息：回车");
					String lineStr = scan.nextLine();
					if(lineStr.equals("exit")){
		    			break;
		    		}
					MessageData data = new MessageData();
					data.setRobotId("robotId1");
					data.setRobotName("小龙人1号");
					data.setUserId("userId1");
					data.setUserName("朱富昆");
					data.setTime(new Date());
					data.setContent("你好，我是小龙人1号-"+i);
					String msg = data.toString();
		    		try {
		    			System.out.println("Robot1发送信息:"+msg);
						sender.sendMessage(msg);
					} catch (JMSException e) {
						e.printStackTrace();
					}
		    		try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		    	}
				sender.destroy();
				//System.exit(0);
			}
		}.start();
		
		Receiver receiver = new Receiver();
		//receiver.init("q.two",1);
		receiver.init("chat-two",2);
		receiver.receive(new MessageListener(){
            @Override
            public void onMessage(Message msg) {  
            	if(msg instanceof ActiveMQBytesMessage){
            		ActiveMQBytesMessage message = (ActiveMQBytesMessage)msg;
            		ByteSequence byteSequence = message.getContent();
            		System.out.println("Robot1：接收消息====="+new String(byteSequence.data));
            	}else{
            		TextMessage message = (TextMessage)msg;  
                    try {
    					System.out.println("Robot1：接收消息=====" + message.getText());
    				} catch (JMSException e) {
    					e.printStackTrace();
    				}
            	}
            }
        });
	}
}
