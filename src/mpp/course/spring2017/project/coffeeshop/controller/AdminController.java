package mpp.course.spring2017.project.coffeeshop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import mpp.course.spring2017.project.coffeeshop.model.Account;
import mpp.course.spring2017.project.coffeeshop.view.CoffeeShopLoginView;

public class AdminController {	
	@FXML BorderPane contentPane;
	@FXML AnchorPane anchorPaneLeft;
	@FXML AnchorPane anchorPaneRight;
	private CoffeeShopLoginView coffeeShopLoginView = null;
	private Account loginAccount = null;
	
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

	@FXML protected void handleOrders(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(AdminController.class.getResource("../view/FXMLOrderManagementForm.fxml")); //FXMLLoader.load(getClass().getResource("../view/FXMLNewEmployee.fxml"));
			SplitPane newLoadedPane = (SplitPane) loader.load();
			((OrderManagementController)loader.getController()).setLoginAccount(loginAccount);
			contentPane.getChildren().clear();
			contentPane.setCenter(newLoadedPane);
		} catch (Exception ex) {
			ex.printStackTrace();
			//System.out.println(ex.getMessage());
		}
	}
	
	@FXML private void initialize() {
		anchorPaneLeft.setMaxWidth(202);
		anchorPaneLeft.setMinWidth(202);
		contentPane.setMaxSize(anchorPaneRight.getWidth(), anchorPaneRight.getHeight());
	}

	public void setLoginView(CoffeeShopLoginView coffeeShopLoginView) {
		this.coffeeShopLoginView = coffeeShopLoginView;
	}
	
	public void showParent() {
		if (coffeeShopLoginView != null) {
			coffeeShopLoginView.unhide();
		}
	}

	public Account getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(Account loginAccount) {
		this.loginAccount = loginAccount;
	}
}
