package mpp.course.spring2017.project.coffeeshop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class AdminController {	
	@FXML BorderPane contentPane;

	@FXML protected void newEmployee(ActionEvent event) {
		try {  
			Pane newLoadedPane = FXMLLoader.load(AdminController.class.getResource("../view/FXMLNewEmployee.fxml")); //FXMLLoader.load(getClass().getResource("../view/FXMLNewEmployee.fxml"));
			contentPane.getChildren().clear();
			contentPane.setCenter(newLoadedPane);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	@FXML protected void viewEmployees(ActionEvent event) {
		try {  
			Pane newLoadedPane = FXMLLoader.load(AdminController.class.getResource("../view/FXMLViewEmployees.fxml")); //FXMLLoader.load(getClass().getResource("../view/FXMLNewEmployee.fxml"));
			contentPane.getChildren().clear();
			contentPane.setCenter(newLoadedPane);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	@FXML protected void updateEmployee(ActionEvent event) {
		try {  
			Pane newLoadedPane = FXMLLoader.load(AdminController.class.getResource("../view/FXMLUpdateEmployee.fxml")); //FXMLLoader.load(getClass().getResource("../view/FXMLNewEmployee.fxml"));
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
			contentPane.setCenter(newLoadedPane);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
