package mpp.course.spring2017.project.coffeeshop.view;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import mpp.course.spring2017.project.coffeeshop.model.Product;

public class CoffeeShopButton extends Button {
	private Product p = null;
	
	public CoffeeShopButton(String buttonText, ImageView img, Product p) {
		super(buttonText, img);
		this.p = p;
	}

	public Product getProduct() {
		return p;
	}	
}
