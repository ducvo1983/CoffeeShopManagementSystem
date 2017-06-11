package mpp.course.spring2017.project.coffeeshop.view;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class CoffeeShopButton extends Button {
	private Object obj = null;
	
	public CoffeeShopButton(String buttonText, ImageView img, Object o) {
		super(buttonText, img);
		this.obj = o;
	}

	public Object getObject() {
		return obj;
	}	
}
