package mpp.course.spring2017.project.coffeeshop.controller;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import mpp.course.spring2017.project.coffeeshop.model.OrderLine;
import mpp.course.spring2017.project.coffeeshop.model.Product;

public class OrderTableItem {
	private SimpleObjectProperty<Product> product = new SimpleObjectProperty<Product>();
	private SimpleStringProperty productSize = new SimpleStringProperty();
	private SimpleDoubleProperty productPrice = new SimpleDoubleProperty();
	private SimpleStringProperty quantity = new SimpleStringProperty();
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
	
	public OrderLine toOrderLine() {
		OrderLine odl = new OrderLine();
		odl.setBeverageSize(productSize.get());
		odl.setPrice(productPrice.floatValue() * Float.parseFloat(quantity.get())); 
		odl.setUnitPrice(productPrice.floatValue());
		odl.setProduct(product.get());
		odl.setQuantity(Integer.parseInt(quantity.get()));
		return odl;
	}
	public SimpleObjectProperty<Product> getProduct() {
		return product;
	}
	public void setProduct(SimpleObjectProperty<Product> product) {
		this.product = product;
	}
}
