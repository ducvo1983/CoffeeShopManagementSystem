package mpp.course.spring2017.project.coffeeshop.view;

import java.io.IOException;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mpp.course.spring2017.project.coffeeshop.controller.CashierController;
import mpp.course.spring2017.project.coffeeshop.controller.PDFReportController;

public class PDFView {
	private FXMLLoader loader = null;
	private Stage stage = null;
	
	public void show(String title) throws IOException {
        stage = new Stage();
		loader = new FXMLLoader(getClass().getResource("FXMLPDFViewerForm.fxml"));
		Parent root = (Parent) loader.load();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 400, 600));
        //stage.setResizable(false);
        stage.setFullScreen(false);
        stage.setMinHeight(600);
        stage.setMaxHeight(600);
        stage.setMaxWidth(400);
        stage.setMinWidth(400);
        stage.show();
	}
	
	/*public void unhide() {
		stage.show();
	}
	
	public void hide() {
		stage.hide();
	}*/
	public FXMLLoader getLoader() { return loader; }
}
