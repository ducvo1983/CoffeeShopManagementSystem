package mpp.course.spring2017.project.coffeeshop.model;

public class CashierModel {
	private CustomerOrder currentOrder = null;
	private boolean isNewOrder = false;
	
	public CustomerOrder getCurrentOrder() {
		return currentOrder;
	}

	public void setCurrentOrder(CustomerOrder currentOrder) {
		this.currentOrder = currentOrder;
	}

	public boolean isNewOrder() {
		return isNewOrder;
	}

	public void setNewOrder(boolean isNewOrder) {
		this.isNewOrder = isNewOrder;
	}
	
	
}
