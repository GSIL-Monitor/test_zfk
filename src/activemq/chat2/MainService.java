package activemq.chat2;

import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import activemq.chat2.entity.MessageData;
import activemq.chat2.entity.Session;
import activemq.chat2.entity.SessionStatus;
import activemq.chat2.entity.User;
import activemq.chat2.service.ReceiverService;
import activemq.chat2.service.SenderService;

public class MainService {
	public static void main(String[] args) {
		/**
		 * 1.初始化，不断的接受机器人消息
		 */
		ReceiverService reService = new ReceiverService();
		reService.init();

		SenderService seService = new SenderService();
		seService.init();

		/**
		 * 2、监视状态信息返回前台
		 */
		new Thread() {
			public void run() {
				while (true) {
					System.out.println("#######会话状态监视#######");
					List<SessionStatus> statusList = reService.getSessionStatusList();
					System.out.println(statusList);
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		}.start();

		/**
		 * 3.前台用户选择回答robotId1的问题
		 */
		while (true) {
			User user = new User();
			user.setUserId("userId1");
			user.setUserName("朱富昆");
			Scanner scan = new Scanner(System.in);
			System.out.println("Main请输入你要回答的robotId：");
			String robotId = scan.nextLine();
			if (robotId.equals("exit")) {
				break;
			}
			Session session = reService.dispatchSession(user, robotId);
			/**
			 * 4.具体问答过程
			 */
			if (session != null) {
				// 模拟收前台定时收
				new Thread() {
					public void run() {
						while (true) {
							Session session = reService.getSession(robotId);
							Queue<MessageData> queue = session.getMsgDataQueue();
							while (queue.peek() != null) {
								System.out.println(this.getName() + "收到消息：" + queue.poll().getContent());
							}
							try {
								Thread.sleep(5000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					};
				}.start();

				// 模拟前台用户发
				new Thread() {
					public void run() {
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						MessageData data = new MessageData();
						data.setRobotId(session.getRobotId());
						data.setRobotName(session.getRobotName());
						data.setUserId(session.getUserId());
						data.setUserName(session.getUserName());
						data.setContent("你好，我是" + session.getUserName() + "！");
						data.setTime(new Date());
						session.setLastTime(new Date());
						seService.sendMessage(data);

					};
				}.start();

			} else {
				System.out.println("没找到对应的机器人或者有人正在回答他");
			}

		}

		System.out.println("########MAIN-END#############");

	}
}
