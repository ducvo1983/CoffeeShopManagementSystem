package mpp.course.spring2017.project.coffeeshop.view;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mpp.course.spring2017.project.coffeeshop.controller.CoffeeShopLoginController;

public class CoffeeShopLoginView extends Application {
	private FXMLLoader loader = null;
	private Stage stage = null;
		
	public void load(Stage stage) throws IOException {
		if (loader == null) {
			loader = new FXMLLoader(getClass().getResource("FXMLCoffeeShopLoginForm.fxml"));
		}
		Parent root = (Parent) loader.load();
        stage.setTitle("CoffeeShop Welcome");
        stage.setScene(new Scene(root, 300, 275));
        stage.setFullScreen(false);
        ((CoffeeShopLoginController) loader.getController()).setLoginView(this);
        stage.show();
        stage.setResizable(false);
        stage.setMaximized(false);
        this.stage = stage;
	}

	public void unhide() {
		stage.setFullScreen(false);
		stage.setMaxWidth(300);
		stage.setMinWidth(300);
		stage.setMinHeight(275);
		stage.setMaxHeight(275);
		stage.show();
	}
	
	public void hide() {
		stage.hide();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		load(primaryStage);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
