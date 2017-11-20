package activemq.chat;

import java.util.Scanner;

import javax.jms.JMSException;

import activemq.chat2.Receiver;
import activemq.chat2.Sender;

public class Two {
	public static void main(String[] args) throws JMSException {
		new Thread(){
			@Override
			public void run() {
				Sender sender = new Sender();
				sender.init("q.two");
				for (;;) {
					Scanner scan = new Scanner(System.in);
					System.out.println("请输入信息：");
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
			}
		}.start();
		
		Receiver receiver = new Receiver();
		receiver.init("q.one");
		receiver.receive();
	}
}
