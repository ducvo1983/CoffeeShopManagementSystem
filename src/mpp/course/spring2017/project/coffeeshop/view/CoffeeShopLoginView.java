package mpp.course.spring2017.project.coffeeshop.view;

import java.io.IOException;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mpp.course.spring2017.project.coffeeshop.controller.CoffeeShopLoginController;
import mpp.course.spring2017.project.coffeeshop.dao.HibernateFactory;

public class CoffeeShopLoginView {
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
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent e) {
            	HibernateFactory.getInstance().closeSession();
            }
        });
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
}
