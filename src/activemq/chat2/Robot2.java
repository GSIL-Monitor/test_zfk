package activemq.chat2;

import java.util.Date;
import java.util.Scanner;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import activemq.chat2.entity.MessageData;
import activemq.chat2.service.Receiver;
import activemq.chat2.service.Sender;

public class Robot2 {
	public static void main(String[] args) throws JMSException {
		new Thread(){
			@Override
			public void run() {
				Sender sender = new Sender();
				sender.init("q.one");
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
					data.setMesage("你好，我是小龙人2号！");
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
		receiver.init("q.two");
		receiver.receive(new MessageListener(){
            @Override
            public void onMessage(Message msg) {  
                TextMessage message = (TextMessage)msg;  
                try {
					System.out.println("Robot2：接收消息=====" + message.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
            }
        });
	}
}
