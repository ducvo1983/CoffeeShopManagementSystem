package mpp.course.spring2017.project.coffeeshop.activemq;

import javax.jms.JMSException;

public interface IMessageSender {
	public boolean createConnection() throws JMSException;
	public void sendMessage(String s) throws JMSException;
	public void closeConnection() throws JMSException;
}
