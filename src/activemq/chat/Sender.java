package activemq.chat;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Sender {
	ConnectionFactory connectionFactory; // ConnectionFactory--连接工厂，JMS用它创建连接
	// Provider 的连接
	Connection connection = null; // Connection ：JMS 客户端到JMS
	Session session; // Session： 一个发送或接收消息的线程
	Destination destination; // Destination ：消息的目的地;消息发送给谁.
	MessageProducer producer; // MessageProducer：消息发送者

	public void init(String queueName) {
		try {
			// 构造ConnectionFactory实例对象，此处采用ActiveMq的实现jar
			connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
					ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");
			// 构造从工厂得到连接对象
			connection = connectionFactory.createConnection();
			// 启动
			connection.start();
			// 获取操作连接
			// 第一个参数是是否是事务型消息，设置为true,第二个参数无效
			// 第二个参数是
			// Session.AUTO_ACKNOWLEDGE为自动确认，客户端发送和接收消息不需要做额外的工作。异常也会确认消息，应该是在执行之前确认的
			// Session.CLIENT_ACKNOWLEDGE为客户端确认。客户端接收到消息后，必须调用javax.jms.Message的acknowledge方法。jms服务器才会删除消息。可以在失败的
			// 时候不确认消息,不确认的话不会移出队列，一直存在，下次启动继续接受。接收消息的连接不断开，其他的消费者也不会接受（正常情况下队列模式不存在其他消费者）
			// DUPS_OK_ACKNOWLEDGE允许副本的确认模式。一旦接收方应用程序的方法调用从处理消息处返回，会话对象就会确认消息的接收；而且允许重复确认。在需要考虑资源使用时，这种模式非常有效。
			// 待测试
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue(queueName);//"q.one"
			// 得到消息生成者【发送者】
			producer = session.createProducer(destination);
			// 设置不持久化，此处学习，实际根据项目决定
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
		if (null != connection)
			try {
				connection.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
	}

	public void sendMessage(String msg) throws JMSException {
		TextMessage message = session.createTextMessage(msg);
		// 发送消息到目的地方
		producer.send(message);
		session.commit();
	}
	
	
	public static void main(String[] args) throws Exception {
		Sender sender = new Sender();
    	sender.init("qq.one");
    	for (int i = 1; i <= 10; i++) {
    		System.out.println("发送消息===========哈喽_"+i);
    		sender.sendMessage("哈喽_"+i);
    		Thread.sleep(3000);
    	}
    	sender.destroy();
	}
	
}