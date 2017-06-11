package mpp.course.spring2017.project.coffeeshop.view;

import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;

public class CoffeeShopToggleButton extends ToggleButton {
	private Object obj = null;
	
	public CoffeeShopToggleButton(String buttonText, ImageView img, Object o) {
		super(buttonText, img);
		this.obj = o;
	}

	public Object getObject() {
		return obj;
	}	
}
