package mpp.course.spring2017.project.coffeeshop.activemq;

import javax.jms.JMSException;
import javax.jms.MessageListener;

public interface IMessageReceiver {
	public boolean createConnection() throws JMSException;
	public void registerListener(MessageListener listener) throws JMSException;
	public void closeConnection() throws JMSException;
}
