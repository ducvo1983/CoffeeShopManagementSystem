package mpp.course.spring2017.project.coffeeshop.controller;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AdminController {
	private Stage stage;
	
	@FXML private Text actiontarget;
	@FXML private StackPane menu;
	@FXML private HBox subMenu;
	@FXML ImageView actionMenu;
	@FXML Label lblTest;
	@FXML BorderPane contentPane;
	
	@FXML protected void handleResetButtonAction(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getClassLoader().getResource("../fxml/fxml_login.fxml"));
	        /* 
	         * if "fx:controller" is not set in fxml
	         * fxmlLoader.setController(NewWindowController);
	         */
	        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
	        stage = new Stage();
	        stage.setTitle("New Window");
	        stage.setScene(scene);
	        stage.show();
		} catch (IOException e) {
			System.out.println("File not found exception: " + e.getMessage());
		}
    }
	
	@FXML protected void showMenu(MouseEvent event) {
		lblTest.setText("image clicked.");
		//menu.getChildren().add(subMenu);
        FadeTransition hideEditorRootTransition = new FadeTransition(Duration.millis(500), actionMenu);
        hideEditorRootTransition.setFromValue(1.0);
        hideEditorRootTransition.setToValue(0.0);

        /*FadeTransition showFileRootTransition = new FadeTransition(Duration.millis(500), subMenu);
        showFileRootTransition.setFromValue(0.0);
        showFileRootTransition.setToValue(1.0);
        hideEditorRootTransition.play();
        showFileRootTransition.play();*/
        
        Path path = new Path();
        path.getElements().add(new MoveTo(20,20));
        //path.getElements().add(new CubicCurveTo(380, 0, 380, 120, 200, 120));
        //path.getElements().add(new CubicCurveTo(0, 120, 0, 240, 380, 240));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(4000));
        pathTransition.setPath(path);
        pathTransition.setNode(subMenu);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        pathTransition.setAutoReverse(true);
        pathTransition.play();
	}
	
	@FXML protected void hideMenu(ActionEvent event) {
		lblTest.setText("button clicked.");
		
		FadeTransition hideEditorRootTransition = new FadeTransition(Duration.millis(500), subMenu);
        hideEditorRootTransition.setFromValue(1.0);
        hideEditorRootTransition.setToValue(0.0);

        FadeTransition showFileRootTransition = new FadeTransition(Duration.millis(500), actionMenu);
        showFileRootTransition.setFromValue(0.0);
        showFileRootTransition.setToValue(1.0);
        hideEditorRootTransition.play();
        showFileRootTransition.play();
	}

	@FXML protected void newEmployee(ActionEvent event) {
		try {  
			Pane newLoadedPane = FXMLLoader.load(AdminController.class.getResource("../view/FXMLNewEmployee.fxml")); //FXMLLoader.load(getClass().getResource("../view/FXMLNewEmployee.fxml"));
			//contentPane.getChildren().add(newLoadedPane);
			contentPane.setCenter(newLoadedPane);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	@FXML protected void viewEmployees(ActionEvent event) {
		try {  
			Pane newLoadedPane = FXMLLoader.load(AdminController.class.getResource("../view/FXMLViewEmployees.fxml")); //FXMLLoader.load(getClass().getResource("../view/FXMLNewEmployee.fxml"));
			contentPane.getChildren().clear();
			//contentPane.getChildren().add(newLoadedPane);
			contentPane.setCenter(newLoadedPane);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	@FXML protected void updateEmployee(ActionEvent event) {
		try {  
			Pane newLoadedPane = FXMLLoader.load(AdminController.class.getResource("../view/FXMLUpdateEmployee.fxml")); //FXMLLoader.load(getClass().getResource("../view/FXMLNewEmployee.fxml"));
			//contentPane.getChildren().add(newLoadedPane);
			contentPane.getChildren().clear();
			contentPane.setCenter(newLoadedPane);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	@FXML protected void listProducts(ActionEvent event) {
		try {
			SplitPane newLoadedPane = FXMLLoader.load(AdminController.class.getResource("../view/FXMLListProducts.fxml")); //FXMLLoader.load(getClass().getResource("../view/FXMLNewEmployee.fxml"));
			contentPane.getChildren().clear();
			//contentPane.getChildren().add(newLoadedPane);
			contentPane.setCenter(newLoadedPane);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
