package mpp.course.spring2017.project.coffeeshop.activemq;

import javax.jms.JMSException;

public interface IMessageReceiver {
	public boolean createConnection() throws JMSException;
	public String hasMessage() throws JMSException;
	public void closeConnection() throws JMSException;
}
