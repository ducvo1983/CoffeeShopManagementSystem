package mpp.course.spring2017.project.coffeeshop.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class MessageSender implements IMessageSender {
	private String url;
	private String queueName;
	private ConnectionFactory factory = null;
	private Connection con = null;
	private Session session = null;
	
	public MessageSender(String url, String queueName) {
		this.url = url;
		this.queueName = queueName;
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
	public void sendMessage(String s) throws JMSException {
		try {
			Queue queue = session.createQueue(queueName);
			MessageProducer producer = session.createProducer(queue);
			Message msg = session.createTextMessage(s);  // text message (more)
			producer.send(msg);
		}
		catch(Exception ex) {
			con.close();
			System.out.println("sendMessage: " + ex.getMessage());
		}
	}

	@Override
	public void closeConnection() throws JMSException {
		con.close();
	}
}
