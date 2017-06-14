package mpp.course.spring2017.project.coffeeshop.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class MessageReceiver implements IMessageReceiver {
	private String url;
	private String queueName;
	private ConnectionFactory factory = null;
	private Connection con = null;
	private Session session = null;
	private String message;
	
	public MessageReceiver(String url, String queueName) {
		this.url = url;
		this.queueName = queueName;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public boolean createConnection() throws JMSException {
		try {
			factory = new ActiveMQConnectionFactory(url);  // ActiveMQ-specific (more)
			con = factory.createConnection();
			session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);  // non-transacted session (more)
			
			return true;
		}
		catch(Exception ex) {
			con.close();
			System.out.println("createConnection: " + ex.getMessage());
			return false;
		}
	}

	@Override
	public void closeConnection() throws JMSException {
		session.close();
		con.close();
	}

	@Override
	public void registerListener(MessageListener listener) throws JMSException {
		Queue queue = session.createQueue(queueName);
		MessageConsumer consumer = session.createConsumer(queue);
		consumer.setMessageListener(listener);
		con.start();
	}
}
