/*
 * Copyright (c) 2011, 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package mpp.course.spring2017.project.coffeeshop.controller;
 
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import mpp.course.spring2017.project.coffeeshop.dao.AccountDaoFactory;
import mpp.course.spring2017.project.coffeeshop.model.Account;
import mpp.course.spring2017.project.coffeeshop.view.AdminView;
import mpp.course.spring2017.project.coffeeshop.view.CashierView;
import mpp.course.spring2017.project.coffeeshop.view.ChefBartenderView;
import mpp.course.spring2017.project.coffeeshop.view.CoffeeShopLoginView;
import mpp.course.spring2017.project.coffeeshop.view.CoffeeShopUtils;
 
public class CoffeeShopLoginController {
    @FXML private Text txtErrorMessage;
    @FXML private TextField txtUserName;
    @FXML private TextField txtPassword;
    @FXML private Button btnSignIn;
    
    private final int ADMIN_ROLE = 1;
    private final int CASHIER_ROLE = 2;
    private final int CHEF_ROLE = 3;
    private final int BARTENDER_ROLE = 4;
    private CashierView cashierView = null;
    private ChefBartenderView chefBartenderView = null;
    private AdminView adminView = null;
    private CoffeeShopLoginView coffeeShopLoginView = null;
    
    private boolean validateFields() {
    	if (txtUserName.getText().trim().equals("")) {
    		txtErrorMessage.setText("Please enter user name");
    		txtUserName.setFocusTraversable(true);
    		return false;
    	}
    	if (txtPassword.getText().trim().equals("")) {
    		txtErrorMessage.setText("Please enter password");
    		txtPassword.setFocusTraversable(true);
    		return false;
    	}
    	return true;
    }
    
    @FXML void initialize() {
    	btnSignIn.setDefaultButton(true);
    }
    
    private boolean showForm(Account acct) {
    	try {
	    	switch (acct.getRole().getID()) {
	    	case ADMIN_ROLE:
	    	{
	    		if (adminView == null) {
	    			adminView = new AdminView();
	    		}
	    		adminView.show("Welcome " + acct.getUserName());
	    		FXMLLoader loader = adminView.getLoader();
	    		AdminController controller = (AdminController) loader.getController();
	    		controller.setLoginView(coffeeShopLoginView);
	    		controller.setLoginAccount(acct);
	    	}
	    		return true;
	    	case CASHIER_ROLE:
	    	{
	    		if (cashierView == null) {
	    			cashierView = new CashierView();
	    		}
	    		cashierView.show("Welcome " + acct.getUserName());
	    		FXMLLoader loader = cashierView.getLoader();
	    		CashierController controller = (CashierController) loader.getController();
	    		controller.setLoginView(coffeeShopLoginView);
	    		controller.setLoginAccount(acct);
	    	}
	    		return true;
	    	case CHEF_ROLE:
	    	case BARTENDER_ROLE:
	    	{
	    		if (chefBartenderView == null) {
	    			chefBartenderView = new ChefBartenderView();
	    		}
	    		chefBartenderView.show("Welcome " + acct.getUserName());
	    		FXMLLoader loader = chefBartenderView.getLoader();
	    		ChefBartenderController controller = (ChefBartenderController) loader.getController();
	    		controller.setLoginView(coffeeShopLoginView);
	    		//controller.setLoginAccount(acct);
	    		
	    		/*Thread thr = new Thread(controller);
	    		thr.setDaemon(true);
	    		thr.start();*/
	    	}
	    		return true;
	    	}
    	} catch (IOException ex) {
    		ex.printStackTrace();
    	}
    	return false;
    }
    
    private boolean matchPassword(String acctPassword, String inputPassword) {
    	return acctPassword.equals(inputPassword);
    }
    @FXML protected void handleSubmitButtonAction(ActionEvent event) {
    	if (validateFields()) {
    		Account acct = AccountDaoFactory.getInstance().findAccount(txtUserName.getText());
    		String passwordHashText = "";
			try {
				if (acct != null) {
					passwordHashText = CoffeeShopUtils.getMD5(txtPassword.getText());
					if (matchPassword(acct.getPassword(), passwordHashText)) {
		    			if (showForm(acct)) {
		    				txtErrorMessage.setText("");
		    				coffeeShopLoginView.hide();
		    			} else {
		    				txtErrorMessage.setText("Invalid user login");
		    			}
		    		} else {
	    				txtErrorMessage.setText("Invalid user login");
	    			}
				} else {
    				txtErrorMessage.setText("Invalid user login");
    			}
			} catch (Exception ex) {
				ex.getMessage();
			}
    		handleResetButtonAction(event);
    	}
    }
    
    public void setLoginView(CoffeeShopLoginView coffeeShopLoginView) {
    	this.coffeeShopLoginView = coffeeShopLoginView;
    }
    @FXML protected void handleResetButtonAction(ActionEvent event) {
    	txtPassword.setText("");
    	txtUserName.setText("");
    	txtUserName.requestFocus();
    }
}
