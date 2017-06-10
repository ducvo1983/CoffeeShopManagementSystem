package mpp.course.spring2017.project.coffeeshop.view;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class CoffeeButton extends Button {
	private String productName = null;
	private String productSize = null;
	private double price = 0.0;
	private int quantity = 0;
	
	public CoffeeButton(String buttonText, ImageView img, String productName, String productSize, double price, int quantity) {
		super(buttonText, img);
		setProductName(productName);
		setProductSize(productSize);
		setPrice(price);
		setQuantity(quantity);
	}
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getProductSize() {
		return productSize;
	}

	public void setProductSize(String productSize) {
		this.productSize = productSize;
	}
	
}
