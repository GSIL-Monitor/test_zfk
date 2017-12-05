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

public class Robot2 {
	public static void main(String[] args) throws JMSException {
		new Thread(){
			@Override
			public void run() {
				Sender sender = new Sender();
				sender.init("chat-one",2);
				for (;;) {
					Scanner scan = new Scanner(System.in);
					System.out.println("Robot2：请输入信息：回车");
					String lineStr = scan.nextLine();
					if(lineStr.equals("exit")){
		    			break;
		    		}
					MessageData data = new MessageData();
					data.setRobotId("robotId2");
					data.setRobotName("小龙人2号");
					data.setContent("你好，我是小龙人2号！");
					data.setTime(new Date());
					String msg = data.toString();
		    		try {
		    			System.out.println("Robot2发送信息:"+msg);
						sender.sendMessage(msg);
					} catch (Exception e) {
						e.printStackTrace();
					}
		    	}
				sender.destroy();
				System.exit(0);
			}
		}.start();
		
		Receiver receiver = new Receiver();
		receiver.init("chat-two",2);
		receiver.receive(new MessageListener(){
            @Override
            public void onMessage(Message msg) {  
            	if(msg instanceof ActiveMQBytesMessage){
            		ActiveMQBytesMessage message = (ActiveMQBytesMessage)msg;
            		ByteSequence byteSequence = message.getContent();
            		System.out.println("Robot2：接收消息====="+new String(byteSequence.data));
            	}else{
            		TextMessage message = (TextMessage)msg;  
                    try {
    					System.out.println("Robot2：接收消息=====" + message.getText());
    				} catch (JMSException e) {
    					e.printStackTrace();
    				}
            	}
                
            }
        });
	}
}
