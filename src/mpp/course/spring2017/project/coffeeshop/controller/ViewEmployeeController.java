package mpp.course.spring2017.project.coffeeshop.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import mpp.course.spring2017.project.coffeeshop.dao.EmployeeDaoFactory;
import mpp.course.spring2017.project.coffeeshop.model.Employee;

public class ViewEmployeeController implements Initializable {
	private List<Employee> listEmployee;
	private ObservableList<Employee> obListEmployee;
	private final String IMAGE_DEL = "file:images/delete.png";
	
	@FXML TableView<Employee> tblViewEmployees; 
	
	@SuppressWarnings("unchecked")
	private void loadTableView() {
		tblViewEmployees.getItems().clear();
		listEmployee = EmployeeDaoFactory.getInstance().getAllEmployees();
		obListEmployee = FXCollections.observableArrayList(listEmployee);
		tblViewEmployees.setItems(obListEmployee);
		
		TableColumn<Employee, String> firstNameCol = new TableColumn<Employee, String>("First Name");
		TableColumn<Employee, String> lastNameCol = new TableColumn<Employee, String>("Last Name");
		TableColumn<Employee, String> dobCol = new TableColumn<Employee, String>("Date Of Birth");
		TableColumn<Employee, String> phoneCol = new TableColumn<Employee, String>("Phone");
		TableColumn<Employee, String> addressCol = new TableColumn<Employee, String>("Address");
		TableColumn<Employee, String> joinDateCol = new TableColumn<Employee, String>("Join Date");
		TableColumn<Employee, String> accountIDCol = new TableColumn<Employee, String>("Account ID");
		TableColumn<Employee, String> positionCol = new TableColumn<Employee, String>("Position");
		
		firstNameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
		dobCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("dateOfBirth"));
		phoneCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("phone"));
		addressCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("address"));
		joinDateCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("joinDate"));
		accountIDCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAccount() == null ? "" : cellData.getValue().getAccount().getUserName()));
		positionCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAccount() == null ? "" : cellData.getValue().getAccount().getRole().getRoleName()));
		
		TableColumn<Employee, String> delCol = new TableColumn<Employee, String>("Delete");
		delCol.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        Callback<TableColumn<Employee, String>, TableCell<Employee, String>> cellFactory = 
	        new Callback<TableColumn<Employee, String>, TableCell<Employee, String>>()
	        {
	            @Override
	            public TableCell<Employee, String> call(final TableColumn<Employee, String> param)
	            {
	                final TableCell<Employee, String> cell = new TableCell<Employee, String>()
	                {
	                    //final Button btn = new Button("delete");
	                    final Button btn = new Button("", new ImageView(new Image(IMAGE_DEL)));
	                    @Override
	                    public void updateItem(String item, boolean empty)
	                    {
	                        super.updateItem(item, empty);
	                        if (empty)
	                        {
	                            setGraphic(null);
	                            setText(null);
	                        }
	                        else
	                        {
	                            btn.setOnAction(new EventHandler<ActionEvent>(){
	                                @Override
	                                public void handle(ActionEvent t) {
		                            	Employee emp = getSelectedEmployee();
		                            	Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure to delete " + emp.getFirstName() + " employee?", ButtonType.YES, ButtonType.NO);
		                            	alert.showAndWait();
		                            	if (alert.getResult() == ButtonType.YES) {
		                            	    if(EmployeeDaoFactory.getInstance().deleteEmployee(emp)) {
		                            	    	alert = new Alert(AlertType.INFORMATION, "Delete " + emp.getFirstName() + " employee successfully!", ButtonType.OK);
		    	                            	alert.showAndWait();
		    	                            	loadTableView();
		                            	    }
		                            	    else {
		                            	    	alert = new Alert(AlertType.ERROR, "Failed to delete " + emp.getFirstName() + " employee!", ButtonType.OK);
		    	                            	alert.showAndWait();
		                            	    }	
		                            	}
	                                }
	                                Employee getSelectedEmployee() {
	                                	return getTableView().getItems().get(getIndex());
	                                }
	                            });
	                            setContentDisplay(ContentDisplay.CENTER);
	                            setGraphic(btn);
	                            setText(null);
	                        }
	                    }
	                };
	                return cell;
	            }
	        };
        delCol.setCellFactory(cellFactory);
        
		tblViewEmployees.getColumns().setAll(accountIDCol, positionCol, firstNameCol, lastNameCol, dobCol, phoneCol, addressCol, joinDateCol, delCol);
        tblViewEmployees.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}
	
	@Override
    public void initialize(URL url, ResourceBundle rb) {
		loadTableView();
	}
}
