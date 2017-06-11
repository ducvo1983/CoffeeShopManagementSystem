package mpp.course.spring2017.project.coffeeshop.view;

import java.io.IOException;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mpp.course.spring2017.project.coffeeshop.controller.ChefBartenderController;

public class ChefBartenderView {
	private FXMLLoader loader = null;
	private Stage stage = null;
	public FXMLLoader getLoader() {
		return loader;
	}

	public void show(String title) throws IOException {
        stage = new Stage();
		loader = new FXMLLoader(getClass().getResource("FXMLChefBartenderForm.fxml"));
		Parent root = (Parent) loader.load();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 300, 275));
        stage.setFullScreen(true);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                ((ChefBartenderController) loader.getController()).showParent();
            }
        });
        stage.show();
        stage.setResizable(false);
	}
	public void unhide() {
		stage.show();
	}
	
	public void hide() {
		stage.hide();
	}
}
