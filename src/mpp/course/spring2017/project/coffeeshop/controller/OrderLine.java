package mpp.course.spring2017.project.coffeeshop.controller;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class OrderLine {
	private SimpleStringProperty productName = new SimpleStringProperty();
	private SimpleStringProperty productSize = new SimpleStringProperty();
	private SimpleDoubleProperty productPrice = new SimpleDoubleProperty();
	private SimpleStringProperty quantity = new SimpleStringProperty();
	public SimpleStringProperty getProductName() {
		return productName;
	}
	public void setProductName(SimpleStringProperty productName) {
		this.productName = productName;
	}
	public SimpleDoubleProperty getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(SimpleDoubleProperty productPrice) {
		this.productPrice = productPrice;
	}
	public SimpleStringProperty getQuantity() {
		return quantity;
	}
	public void setQuantity(SimpleStringProperty quantity) {
		this.quantity = quantity;
	}
	public SimpleStringProperty getProductSize() {
		return productSize;
	}
	public void setProductSize(SimpleStringProperty productSize) {
		this.productSize = productSize;
	}
	
}
