package mpp.course.spring2017.project.coffeeshop.controller;

import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import mpp.course.spring2017.project.coffeeshop.dao.EmployeeDaoFactory;
import mpp.course.spring2017.project.coffeeshop.dao.RoleDaoFactory;
import mpp.course.spring2017.project.coffeeshop.model.Account;
import mpp.course.spring2017.project.coffeeshop.model.Employee;
import mpp.course.spring2017.project.coffeeshop.model.Role;

class KeyValuePair2 {
	private final Employee key;
	private final String value;
	public KeyValuePair2(Employee key, String value) {
		this.key = key;
		this.value = value;
	}

	public Employee getKey() { return key; }

	public String toString() { return value; }
}

public class UpdateEmployeeController implements Initializable {
	private List<Role> listRole;
	private List<Employee> listEmployee;
	private Employee objEmployee;
	
	@FXML ChoiceBox<KeyValuePair> chBoxGroup;
	@FXML ChoiceBox<KeyValuePair2> chBoxEmployee;
	@FXML TextField txtFirstName;
	@FXML TextField txtLastName;
	@FXML DatePicker datePickerDOB;
	@FXML TextField txtPhone;
	@FXML TextField txtAddress;
	@FXML TextField txtAccountID;
	@FXML PasswordField txtPassword;
	@FXML Label lblErrorMsg;

	private void resetForm() {
		txtFirstName.setText("");
		txtLastName.setText("");
		datePickerDOB.setValue(LocalDate.now());
		txtPhone.setText("");
		txtAddress.setText("");
		txtAccountID.setText("");
		txtPassword.setText("");
		lblErrorMsg.setText("");
		chBoxGroup.getSelectionModel().select(0);
	}
	
	private boolean validateForm() {
		if(txtFirstName.getText().trim().equals("") ||
				txtLastName.getText().trim().equals("") ||
				txtPhone.getText().trim().equals("") ||
				txtAddress.getText().trim().equals("")) {
			lblErrorMsg.setText("Please enter the require fields (*)!");
			lblErrorMsg.setVisible(true);
			return false;
		}

		Period period = Period.between(datePickerDOB.getValue(), LocalDate.now());
		if(period.getYears() < 16) {
			lblErrorMsg.setText("Employee must be at least 16 years old!");
			lblErrorMsg.setVisible(true);
		}
		
		if (!txtPhone.getText().trim().matches("\\d{3}-\\d{3}-\\d{4}")) {
			lblErrorMsg.setText("Phone number's format is xxx-xxx-xxxx!");
			lblErrorMsg.setVisible(true);
			return false;
		}
		
		if(!txtAccountID.getText().trim().equals("")) {
			if(txtPassword.getText().trim().equals("")) {
				lblErrorMsg.setText("Please enter the password!");
				lblErrorMsg.setVisible(true);
				return false;
			}
			if(chBoxGroup.getValue() == null) {
				lblErrorMsg.setText("Please select a group!");
				lblErrorMsg.setVisible(true);
				return false;
			}
		}
		if(!txtPassword.getText().trim().equals("")) {
			if(txtAccountID.getText().trim().equals("")) {
				lblErrorMsg.setText("Please enter the account ID!");
				lblErrorMsg.setVisible(true);
				return false;
			}
			if(chBoxGroup.getValue() == null) {
				lblErrorMsg.setText("Please select a group!");
				lblErrorMsg.setVisible(true);
				return false;
			}
		}
		if(chBoxGroup.getValue() != null) {
			if(!chBoxGroup.getValue().equals("")) {
				if(txtAccountID.getText().trim().equals("")) {
					lblErrorMsg.setText("Please enter the account ID!");
					lblErrorMsg.setVisible(true);
					return false;
				}
				if(txtPassword.getText().trim().equals("")) {
					lblErrorMsg.setText("Please enter the password!");
					lblErrorMsg.setVisible(true);
					return false;
				}
			}			
		}
		
		lblErrorMsg.setVisible(false);
		return true;
	}
	
	@Override
    public void initialize(URL url, ResourceBundle rb) {
		datePickerDOB.setValue(LocalDate.now());
		
		listRole = RoleDaoFactory.getInstance().getAllRoles();
		if(listRole != null) {
			chBoxGroup.getItems().add(null);
			for(Role r : listRole) {
				chBoxGroup.getItems().add(new KeyValuePair(r.getID(), r.getRoleName()));
			}
		}
		
		listEmployee = EmployeeDaoFactory.getInstance().getAllEmployees();
		if(listEmployee != null) {
			chBoxEmployee.getItems().add(null);
			for(Employee emp : listEmployee) {
				chBoxEmployee.getItems().add(new KeyValuePair2(emp, emp.getFirstName() + " " + emp.getLastName()));
			}
		}
		
		chBoxEmployee.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<KeyValuePair2>() {
			@Override public void changed(ObservableValue<? extends KeyValuePair2> selected, KeyValuePair2 oldFruit, KeyValuePair2 newFruit) {
				resetForm();
				
				if(chBoxEmployee.getValue() != null) {
					objEmployee = chBoxEmployee.getValue().getKey();
										
					txtFirstName.setText(objEmployee.getFirstName());
					txtLastName.setText(objEmployee.getLastName());
					datePickerDOB.setValue(objEmployee.getDateOfBirth());
					txtPhone.setText(objEmployee.getPhone());
					txtAddress.setText(objEmployee.getAddress());
					
					if(objEmployee.getAccount() != null) {
						txtAccountID.setText(objEmployee.getAccount().getUserName());
						txtPassword.setText(objEmployee.getAccount().getPassword());
						
						int index = 0;
						for(int i=0; i<listRole.size(); i++) {
							if(listRole.get(i).getID() == objEmployee.getAccount().getRole().getID()) {
								index = i+1;
								break;
							}
						}
						chBoxGroup.getSelectionModel().select(index); //first element is blank//chBoxGroup.getSelectionModel().select(new KeyValuePair(objEmployee.getAccount().getRole().getID(), objEmployee.getAccount().getRole().getRoleName()));
					}
				}					
			}
	    });
	}
	
	@FXML protected void handleUpdateAction(ActionEvent event) {
		if(!validateForm()) return;
		
		objEmployee.setFirstName(txtFirstName.getText().trim());
		objEmployee.setLastName(txtLastName.getText().trim());
		objEmployee.setDateOfBirth(datePickerDOB.getValue());
		objEmployee.setPhone(txtPhone.getText().trim());
		objEmployee.setAddress(txtAddress.getText().trim());
		objEmployee.setUpdateDate(LocalDate.now());
		
		if(txtAccountID.getText().trim().equals("")) {
			objEmployee.setAccount(null);
		} 
		else {
			Role r = new Role();
			r.setID(chBoxGroup.getValue().getKey());
			r.setRoleName(chBoxGroup.getValue().toString());
			
			Account acc = new Account();
			acc.setUserName(txtAccountID.getText().trim());
			acc.setRole(r);
			
			String hashtext = "";
			if(objEmployee.getAccount() != null) hashtext = objEmployee.getAccount().getPassword();
			if(!hashtext.equals(txtPassword.getText().trim())) {
				try {
					MessageDigest m = MessageDigest.getInstance("MD5");
					m.reset();
					m.update(txtPassword.getText().trim().getBytes());
					byte[] digest = m.digest();
					BigInteger bigInt = new BigInteger(1, digest);
					hashtext = bigInt.toString(16);
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
			acc.setPassword(hashtext);
				
			objEmployee.setAccount(acc);
		}		
		
		Alert alert;
		if(EmployeeDaoFactory.getInstance().updateEmployee(objEmployee)) {
			alert = new Alert(AlertType.INFORMATION, "Update the employee successfully!", ButtonType.OK);
        	alert.showAndWait();
        	resetForm();
        	chBoxEmployee.getSelectionModel().select(0);
		}
		else {
			alert = new Alert(AlertType.ERROR, "Failed to update the employee!", ButtonType.OK);
        	alert.showAndWait();
		}
	}
}
