package mpp.course.spring2017.project.coffeeshop.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.sun.prism.impl.Disposer.Record;

import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.StringConverter;
import mpp.course.spring2017.project.coffeeshop.dao.BeverageSizePriceDaoFactory;
import mpp.course.spring2017.project.coffeeshop.dao.CustomerOrderDaoFactory;
import mpp.course.spring2017.project.coffeeshop.dao.OrderLineDaoFactory;
import mpp.course.spring2017.project.coffeeshop.dao.TokenDaoFactory;
import mpp.course.spring2017.project.coffeeshop.model.Account;
import mpp.course.spring2017.project.coffeeshop.model.BeverageSizePrice;
import mpp.course.spring2017.project.coffeeshop.model.CustomerOrder;
import mpp.course.spring2017.project.coffeeshop.model.OrderLine;
import mpp.course.spring2017.project.coffeeshop.model.Product;
import mpp.course.spring2017.project.coffeeshop.model.Token;
import mpp.course.spring2017.project.coffeeshop.view.CoffeeShopButton;
import mpp.course.spring2017.project.coffeeshop.view.CoffeeShopUtils;
import mpp.course.spring2017.project.coffeeshop.view.PDFView;

public class OrderManagementController implements Initializable {
	@FXML private TableView<OrderTableItem> tblOrderLine;
	@FXML private TableColumn<OrderTableItem,Product> colProduct;
	@FXML private TableColumn<OrderTableItem,String> colSize;
	@FXML private TableColumn<OrderTableItem,Double> colPrice;
	@FXML private TableColumn<OrderTableItem,String> colQuantity;
	@FXML private TableColumn<Record,Boolean> colDeleteButton;
	@FXML private AnchorPane firstAnchorPane;
	@FXML private TextField txtCustomer;
	@FXML private ComboBox<Token> cboToken;
	@FXML private ComboBox<String> cboOrderType;
	@FXML private ComboBox<String> cboStatus;
	@FXML private Text txtTax;
	@FXML private Text txtTotal;
	@FXML private Button btnCancel;
	@FXML private Button btnSave;
	@FXML private Button btnPrint;
	@FXML private GridPane gridOrders;
	
	private final String CUSTOMER_IMAGE = "file:images/customer.png";
	private final String IMAGE_DEL = "file:images/delete.png";
	private final int COLS = 5;
	private Account loginAccount = null;
	private CustomerOrder curOrder = null;
	public Account getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(Account loginAccount) {
		this.loginAccount = loginAccount;
	}

	void calculateTotal() {
		double total = 0;
		for(OrderTableItem item : data) {
			total += (item.getProductPrice().get() * Integer.parseInt(item.getQuantity().get()));
		}
		total += (total * 0.07);
		txtTotal.setText(String.valueOf(total));
	}
	
	private void load2GridPane(GridPane target) {
		if (target.getColumnConstraints() != null)
			target.getColumnConstraints().clear();
		if (target.getRowConstraints() != null)
			target.getRowConstraints().clear();
		if (target.getChildren() != null) {
			target.getChildren().clear();
		}
		// Add row and column
		List<CustomerOrder> orders = CustomerOrderDaoFactory.getInstance().getAllCustomerOrders();
 		int ROWS = orders.size() / COLS;
 		if ((orders.size() % COLS) != 0) ROWS += 1; 
		for (int i = 0; i < COLS; i++) {
		     ColumnConstraints colConst = new ColumnConstraints();
		     target.getColumnConstraints().add(colConst);
		}
		for (int i = 0; i < ROWS; i++) {
		     RowConstraints rowConst = new RowConstraints();
		     target.getRowConstraints().add(rowConst);         
		}

		int pos = 0;
		for (int i = 0; i < ROWS; i++) {
			if (pos >= orders.size()) break;
			for (int j = 0; j < COLS; j++) {
				if (pos >= orders.size()) break;
				CustomerOrder p = orders.get(pos++);
				Button btn = new CoffeeShopButton(p.getOrderNo() + "(" + p.getCustomerName() + ")", new ImageView(new Image(CUSTOMER_IMAGE)), p);
				btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
				btn.setContentDisplay(ContentDisplay.TOP);
				btn.setOnAction(new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent e) {
		            	curOrder = (CustomerOrder) ((CoffeeShopButton)e.getSource()).getObject();
		                loadOrder(curOrder);
		            }
		        });
				target.add(btn, j, i);
			}
		}
	}

	private void loadOrder(CustomerOrder co) {
		txtCustomer.setText(co.getCustomerName());
		cboOrderType.setValue(co.getOrderType());
		cboToken.setValue(co.getToken());
		cboStatus.setValue(co.getStatus());
		txtTotal.setText(String.valueOf(co.getAmount()));
		data.clear();
		List<OrderLine> odls = co.getListOrderLine();//OrderLineDaoFactory.getInstance().getOrderLines(co.getOrderNo());
		if (odls != null) {
			for (OrderLine odl : odls) {
				List<BeverageSizePrice> bsps = BeverageSizePriceDaoFactory.getInstance().getBeverageSizePrices(odl.getProduct().getID());
				if (bsps == null) {
					addRow(odl, null);
				} else {
					boolean found = false;
					if (bsps.size() > 0) {
						for (BeverageSizePrice bsp : bsps) {
							if (bsp.getBeverageSize().getDescription().equals(odl.getBeverageSize())) {
								addRow(odl, bsp);
								found = true;
								break;
							}
						}
					}
					
					if (!found) {
						addRow(odl, null);
					}
				}
			}
		}
	}
	//Define the delete button cell
    private class DelButtonCell extends TableCell<Record, Boolean> {
        final Button delButton = new Button("", new ImageView(new Image(IMAGE_DEL)));
        
        DelButtonCell() {
        	delButton.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent t) {
                	OrderTableItem currentOdl = (OrderTableItem) DelButtonCell.this.getTableView().getItems().get(DelButtonCell.this.getIndex());
                	data.remove(currentOdl);
                }
            });
        }

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(delButton);
            } else {
            	setGraphic(null);
            }
        }
    }
	void initializeTableView() {
			colProduct.setCellValueFactory(
		        new PropertyValueFactory<OrderTableItem,Product>("product") {
		        	@Override
		            public ObservableValue<Product> call(CellDataFeatures<OrderTableItem, Product> data) {
		                return new ReadOnlyObjectWrapper<Product>(data.getValue().getProduct().get()) ;
		            }
		        });
			colSize.setCellValueFactory(
				    new PropertyValueFactory<OrderTableItem, String>("productSize") {
				    	@Override
			            public ObservableValue<String> call(CellDataFeatures<OrderTableItem, String> data) {
			                return new ReadOnlyStringWrapper(data.getValue().getProductSize().get()) ;
			            }
				    }
				);
			colPrice.setCellValueFactory(                
		        new PropertyValueFactory<OrderTableItem,Double>("productPrice") {
		        	@Override
			        public ObservableValue<Double> call(CellDataFeatures<OrderTableItem, Double> data) {
			            return (new ReadOnlyDoubleWrapper(data.getValue().getProductPrice().get())).asObject() ;
			        }
		        });
			colQuantity.setCellValueFactory(
		    new PropertyValueFactory<OrderTableItem,String>("quantity") {
		    	@Override
		        public ObservableValue<String> call(CellDataFeatures<OrderTableItem, String> data) {
		            return (new ReadOnlyStringWrapper(data.getValue().getQuantity().get()));
		        }
		    });
			colQuantity.setCellFactory(TextFieldTableCell.<OrderTableItem>forTableColumn());
			colQuantity.setOnEditCommit(
				new EventHandler<CellEditEvent<OrderTableItem, String>>() {
					@Override
			        public void handle(CellEditEvent<OrderTableItem,String> t) {
						try {
							Integer.parseInt(t.getNewValue());
							((OrderTableItem) t.getTableView().getItems().get(
			                        t.getTablePosition().getRow())
			                        ).getQuantity().set(t.getNewValue());
							calculateTotal();
						} catch (NumberFormatException e) {
							CoffeeShopUtils.showErrorMessgge(e.getMessage());
							//((TextFieldTableCell) t.getSource()).setText(t.getOldValue());
						}
					}
	        });
			colDeleteButton.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Record, Boolean>, 
	                ObservableValue<Boolean>>() {

	            @Override
	            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Record, Boolean> p) {
	                return new SimpleBooleanProperty(p.getValue() != null);
	            }
	        });
	        colDeleteButton.setCellFactory( new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {
	            @Override
	            public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> p) {
	                return new DelButtonCell();
	            }
	        
	        });
	        data = FXCollections.observableArrayList();
	
	}
	
	void initializeOthers() {
		firstAnchorPane.setMinWidth(380);
		firstAnchorPane.setMaxWidth(380);
		ObservableList<String> ways = FXCollections.observableArrayList();
		ways.addAll(new String[] { "At Coffee Shop",
				"Take away"
		});
		cboOrderType.setItems(ways);
		List<Token> tokens = TokenDaoFactory.getInstance().getAllTokens();
		cboToken.setItems(FXCollections.observableArrayList(tokens));
		cboToken.setConverter(new StringConverter<Token>() {

			@Override
			public String toString(Token object) {
				// TODO Auto-generated method stub
				return String.valueOf(object.getID());
			}

			@Override
			public Token fromString(String string) {
				// TODO Auto-generated method stub
				return null;
			}
			
		});
		ObservableList<String> statuses = FXCollections.observableArrayList();
		statuses.addAll(new String[] { "New",
				"Processing",
				"Token is alarm",
				"Done"
		});
		cboStatus.setItems(statuses);
		//loginAccount = AccountDaoFactory.getInstance().findAccount("tri");
		//txtTax.setText("7%");
		//btnSave.setDisable(false);
		//btnPrint.setDisable(true);

	}

	private ObservableList<OrderTableItem> data;

	public void addRow(OrderLine o, BeverageSizePrice bsp){        
	    try{      
	    		Product p = o.getProduct();
	            OrderTableItem odl = new OrderTableItem();
	            odl.getProduct().set(p);
	            if (bsp == null) {
	            	odl.getProductSize().set("");
	            	odl.getProductPrice().set(new Double(p.getUnitPrice() == null ? 0 : p.getUnitPrice()));
	            } else {
	            	odl.getProductSize().set(bsp.getBeverageSize().getDescription());
	            	odl.getProductPrice().set(new Double(bsp.getUnitPrice()));
	            }
	            odl.getQuantity().set(String.valueOf(o.getQuantity()));
	            data.add(odl);                  
	            tblOrderLine.setItems(data);
	    }
	    catch(Exception e){
	          e.printStackTrace();
	          System.out.println("Error on Building Data");            
	    }
	}
	
	public void updateOrInsertRow(OrderLine o, BeverageSizePrice bsp) {
		try{      
            boolean found = false;
            Product p = o.getProduct();
            for (OrderTableItem odl : data) {
            	if (odl.getProduct().get().getName().equals(p.getName())) {
            		odl.getProduct().set(p);
            		int newQuantity = 0;
            		if (bsp == null) {
            			odl.getProductSize().set("");
            			odl.getProductPrice().set(new Double(p.getUnitPrice() == null ? 0 : p.getUnitPrice()));
            			newQuantity = Integer.parseInt(odl.getQuantity().get()) + 1;
            			odl.getQuantity().set(String.valueOf(newQuantity));
            			found = true;
            		} else {
            			if (bsp.getBeverageSize().getDescription().equals(odl.getProductSize().get())) {
            				newQuantity = Integer.parseInt(odl.getQuantity().get()) + 1;
            				odl.getQuantity().set(String.valueOf(newQuantity));
            				found = true;
            			}
            		}
            	}
            }
            if (!found) {
            	addRow(o, bsp);
            } else {
            	tblOrderLine.refresh();
            }
	    }
	    catch(Exception e){
	          e.printStackTrace();
	          System.out.println("Error on Building Data");            
	    }
	}
	
	void clearOrder() {
		data.clear();
		tblOrderLine.refresh();
		txtCustomer.setText("");
		cboToken.getSelectionModel().selectFirst();
		cboOrderType.getSelectionModel().selectFirst();
		cboStatus.getSelectionModel().selectFirst();
		//txtTax.setText("0.0");
		txtTotal.setText("0.0");
		curOrder = null;
	}
	
	boolean validateFields() {
		if (txtCustomer.getText().trim().equals("")) {
			CoffeeShopUtils.showErrorMessgge("Customer name is required");
			return false;
		}
		if (cboOrderType.getValue() == null || cboOrderType.getValue().equals("")) {
			CoffeeShopUtils.showErrorMessgge("Order type is required");
			return false;
		}
		if (cboToken.getValue() == null || cboToken.getValue().equals("")) {
			CoffeeShopUtils.showErrorMessgge("Token is required");
			return false;
		}
		if (cboStatus.getValue() == null || cboStatus.getValue().equals("")) {
			CoffeeShopUtils.showErrorMessgge("Status is required");
			return false;
		}
		if (tblOrderLine.getItems().size() == 0) {
			CoffeeShopUtils.showErrorMessgge("Please select the product first");
			return false;
		}
		return true;
	}
	String generateOrderNo() {
		return LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
	}
	
	CustomerOrder buildNewCustomerOrder() {
		CustomerOrder o = curOrder;
		o.setCustomerName(txtCustomer.getText());
		o.setAccount(loginAccount);
		o.setAmount(Float.parseFloat(txtTotal.getText()));
		o.setListOrderLine(getOrderLines());
		o.setOrderDate(LocalDate.now());
		o.setOrderType(cboOrderType.getSelectionModel().getSelectedItem());
		o.setStatus(cboStatus.getSelectionModel().getSelectedItem());
		o.setTax(0.07f);
		o.setToken(cboToken.getValue());
		o.setUpdateTime(LocalDate.now());
		return o;
	}
	
	List<OrderLine> getOrderLines() {
		List<OrderLine> orderLines = new ArrayList<OrderLine>();
		for (OrderTableItem item : data) {
			orderLines.add(item.toOrderLine());
		}
		return orderLines;
	}
	@FXML protected void handleCancelButtonAction(ActionEvent event) {
		if (curOrder != null) {
			OrderLineDaoFactory.getInstance().deleteOrderLines(curOrder.getOrderNo());
			CustomerOrderDaoFactory.getInstance().deleteCustomerOrder(curOrder);
			clearOrder();
			load2GridPane(gridOrders);
		}
    }
	
	@FXML protected void handleSaveButtonAction(ActionEvent event) {
		if (validateFields()) {
			if (curOrder != null) {
				CustomerOrder co = buildNewCustomerOrder();
				CustomerOrderDaoFactory.getInstance().updateCustomerOrder(co);
			} else {
				CoffeeShopUtils.showErrorMessgge("Please select an order");
			}
		}
	}

	
	@FXML private void handlePrintButtonAction(ActionEvent event) {
		try {
			if (curOrder != null) {
	            PDFView pdfV = new PDFView();
	            pdfV.show("PDF");
	            ((PDFReportController)pdfV.getLoader().getController()).showPDF(curOrder);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		initializeTableView();
		initializeOthers();
        load2GridPane(gridOrders);
		
	}
}
