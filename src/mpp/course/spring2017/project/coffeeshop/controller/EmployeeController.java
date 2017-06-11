package mpp.course.spring2017.project.coffeeshop.controller;

import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import mpp.course.spring2017.project.coffeeshop.dao.EmployeeDaoFactory;
import mpp.course.spring2017.project.coffeeshop.dao.RoleDaoFactory;
import mpp.course.spring2017.project.coffeeshop.model.Account;
import mpp.course.spring2017.project.coffeeshop.model.Employee;
import mpp.course.spring2017.project.coffeeshop.model.Role;

class KeyValuePair {
	private final int key;
	private final String value;
	public KeyValuePair(int key, String value) {
		this.key = key;
		this.value = value;
	}

	public int getKey() { return key; }

	public String toString() { return value; }
}

public class EmployeeController implements Initializable {
	private List<Role> listRole;
	
	@FXML ChoiceBox<KeyValuePair> chBoxGroup;
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
		lblErrorMsg.setVisible(false);
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
	}
	
	@FXML protected void handleCreateAction(ActionEvent event) {
		if(!validateForm()) return;
		
		Employee emp = new Employee();
		emp.setFirstName(txtFirstName.getText().trim());
		emp.setLastName(txtLastName.getText().trim());
		emp.setDateOfBirth(datePickerDOB.getValue());
		emp.setPhone(txtPhone.getText().trim());
		emp.setAddress(txtAddress.getText().trim());
		emp.setJoinDate(LocalDate.now());
		
		if(!txtAccountID.getText().trim().equals("")) {
			Role r = new Role();
			r.setID(chBoxGroup.getValue().getKey());
			r.setRoleName(chBoxGroup.getValue().toString());
			
			String hashtext = "";
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
			Account acc = new Account();
			acc.setUserName(txtAccountID.getText().trim());
			acc.setPassword(hashtext);
			acc.setRole(r);
				
			emp.setAccount(acc);
		}		
		
		Alert alert;
		if(EmployeeDaoFactory.getInstance().newEmployee(emp)) {
			alert = new Alert(AlertType.INFORMATION, "Create the employee successfully!", ButtonType.OK);
        	alert.showAndWait();
        	resetForm();
		}
		else {
			alert = new Alert(AlertType.ERROR, "Failed to create the employee!", ButtonType.OK);
        	alert.showAndWait();
		}
	}
	
	@FXML protected void handleResetAction(ActionEvent event) {
		resetForm();
	}
}
