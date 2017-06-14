package mpp.course.spring2017.project.coffeeshop.view;

import java.io.IOException;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mpp.course.spring2017.project.coffeeshop.controller.CashierController;

public class CashierView {
	private FXMLLoader loader = null;
	private Stage stage = null;
	
	public void show(String title) throws IOException {
        stage = new Stage();
		loader = new FXMLLoader(getClass().getResource("FXMLCashierForm.fxml"));
		Parent root = (Parent) loader.load();

        stage.setTitle(title);
        stage.setScene(new Scene(root, 300, 275));
        stage.setFullScreen(true);
        //stage.setResizable(false);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent e) {
                ((CashierController) loader.getController()).showParent();
            }
        });
        stage.show();
        //stage.setResizable(false);
	}
	
	public void unhide() {
		stage.show();
	}
	
	public void hide() {
		stage.hide();
	}
	public FXMLLoader getLoader() { return loader; }
}
