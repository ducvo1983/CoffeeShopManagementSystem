package mpp.course.spring2017.project.coffeeshop.activemq;

import javax.jms.*;

import mpp.course.spring2017.project.coffeeshop.controller.ChefBartenderController;

public class OrderListener implements MessageListener {
	private ChefBartenderController parentController;
	
	public OrderListener(ChefBartenderController controller) {
		parentController = controller;
	}
	
	@Override
	public void onMessage(Message msg) {
		try {
			System.out.println("OrderListener has a message comming..............");
			if (! (msg instanceof TextMessage))
				throw new RuntimeException("no text message");
			TextMessage tm = (TextMessage) msg;
			parentController.handleNewCommingOrder(tm.getText());
		}
		catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
