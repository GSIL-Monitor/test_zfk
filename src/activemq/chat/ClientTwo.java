package activemq.chat;

import java.util.Scanner;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import activemq.chat2.service.Receiver;
import activemq.chat2.service.Sender;

public class ClientTwo {
	public static void main(String[] args) throws JMSException {
		new Thread(){
			@Override
			public void run() {
				Sender sender = new Sender();
				sender.init("q.two");
				for (;;) {
					Scanner scan = new Scanner(System.in);
					System.out.println("Two请输入信息：");
					String msg = scan.nextLine();
		    		if(msg.equals("exit")){
		    			break;
		    		}
		    		try {
						sender.sendMessage("Two:"+msg);
					} catch (Exception e) {
						e.printStackTrace();
					}
		    	}
				sender.destroy();
				System.exit(0);
			}
		}.start();
		
		Receiver receiver = new Receiver();
		receiver.init("q.one");
		receiver.receive(new MessageListener(){
            @Override
            public void onMessage(Message msg) {
                TextMessage message = (TextMessage)msg;  
                try {
					System.out.println("接收消息=====" + message.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
            }
        });
	}
}
