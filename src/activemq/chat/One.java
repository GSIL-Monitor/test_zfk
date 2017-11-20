package activemq.chat;

import java.util.Scanner;

import activemq.chat2.Receiver;
import activemq.chat2.Sender;

public class One {
	public static void main(String[] args) throws Exception {
		new Thread(){
			@Override
			public void run() {
				Sender sender = new Sender("One");
				sender.init("q.one");
				for (;;) {
					Scanner scan = new Scanner(System.in);
					System.out.println("请输入信息：");
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
			}
		}.start();
		
		Receiver receiver = new Receiver();
		receiver.init("q.two");
		receiver.receive(null);
	}
}
