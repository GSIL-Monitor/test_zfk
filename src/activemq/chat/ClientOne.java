package activemq.chat;

import java.util.Scanner;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import activemq.base.Receiver;
import activemq.base.Sender;


public class ClientOne {
	public static void main(String[] args) throws Exception {
		new Thread(){
			@Override
			public void run() {
				Sender sender = new Sender();
				sender.init("q.one", 1);
				for (;;) {
					Scanner scan = new Scanner(System.in);
					System.out.println("One请输入信息：");
					String msg = scan.nextLine();
		    		if(msg.equals("exit")){
		    			break;
		    		}
		    		try {
						sender.sendMessage("One:"+msg);
					} catch (Exception e) {
						e.printStackTrace();
					}
		    	}
				sender.destroy();
				System.exit(0);
			}
		}.start();
		
		Receiver receiver = new Receiver();
		receiver.init("q.two", 1);
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
