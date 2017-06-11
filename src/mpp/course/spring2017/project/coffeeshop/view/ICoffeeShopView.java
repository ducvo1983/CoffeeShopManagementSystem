package mpp.course.spring2017.project.coffeeshop.view;

import java.io.IOException;

import javafx.stage.Stage;

public interface ICoffeeShopView {
	public void show(ICoffeeShopView parent);
	public void load(Stage stage) throws IOException;
}
