package mpp.course.spring2017.project.coffeeshop.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


import com.sun.prism.impl.Disposer.Record;

import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.util.Callback;
import javafx.util.StringConverter;

import mpp.course.spring2017.project.coffeeshop.dao.BeverageSizePriceDaoFactory;
import mpp.course.spring2017.project.coffeeshop.dao.CustomerOrderDaoFactory;
import mpp.course.spring2017.project.coffeeshop.dao.ProductDaoFactory;
import mpp.course.spring2017.project.coffeeshop.dao.TokenDaoFactory;
import mpp.course.spring2017.project.coffeeshop.model.Account;
import mpp.course.spring2017.project.coffeeshop.model.BeverageSizePrice;
import mpp.course.spring2017.project.coffeeshop.model.CashierModel;
import mpp.course.spring2017.project.coffeeshop.model.CustomerOrder;
import mpp.course.spring2017.project.coffeeshop.model.OrderLine;
import mpp.course.spring2017.project.coffeeshop.model.Product;
import mpp.course.spring2017.project.coffeeshop.model.Token;
import mpp.course.spring2017.project.coffeeshop.view.CoffeeShopButton;
import mpp.course.spring2017.project.coffeeshop.view.CoffeeShopLoginView;
import mpp.course.spring2017.project.coffeeshop.view.CoffeeShopMenuItem;
import mpp.course.spring2017.project.coffeeshop.view.CoffeeShopUtils;
import mpp.course.spring2017.project.coffeeshop.view.PDFView;

public class CashierController {
	@FXML private TableView<OrderTableItem> tblOrderLine;
	@FXML private TableColumn<OrderTableItem,Product> colProduct;
	@FXML private TableColumn<OrderTableItem,String> colSize;
	@FXML private TableColumn<OrderTableItem,Double> colPrice;
	@FXML private TableColumn<OrderTableItem,String> colQuantity;
	@FXML private TableColumn<Record,Boolean> colDeleteButton;
	@FXML private Tab tabStimulantDrink;
	@FXML private Tab tabFruitDrink;
	@FXML private Tab tabSoftDrink;
	@FXML private Tab tabFood;
	@FXML private GridPane gridStimulantDrink;
	@FXML private GridPane gridFruitDrink;
	@FXML private GridPane gridSoftDrink;
	@FXML private GridPane gridFood;
	@FXML private AnchorPane firstAnchorPane;
	@FXML private TextField txtCustomer;
	@FXML private ComboBox<Token> cboToken;
	@FXML private ComboBox<String> cboOrderType;
	@FXML private Label lblTax;
	@FXML private Label lblTotal;
	@FXML private Button btnNew;
	@FXML private Button btnOrder;
	@FXML private Button btnPrint;
	
	private final String IMAGE_DEL = "file:images/delete.png";
	private final int COLS = 5;
	private final int FOOD = 1;
	private final int STIMULANT_DRINK = 2;
	private final int FRUIT_DRINK = 3;
	private final int SOFT_DRINK = 4;
	private Account loginAccount = null;
	private CashierModel model = new CashierModel();
	
	public Account getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(Account loginAccount) {
		this.loginAccount = loginAccount;
	}

	private CoffeeShopLoginView coffeeShopLoginView = null;
	
	void calculateTotal() {
		double total = 0;
		for(OrderTableItem item : data) {
			total += (item.getProductPrice().get() * Integer.parseInt(item.getQuantity().get()));
		}
		total += (total * 0.07);
		lblTotal.setText(String.valueOf(total));
	}
	
	private void load2GridPane(int categoryID, GridPane target) {
		if (target.getColumnConstraints() != null)
			target.getColumnConstraints().clear();
		if (target.getRowConstraints() != null)
			target.getRowConstraints().clear();
		if (target.getChildren() != null) {
			target.getChildren().clear();
		}
		// Add row and column
		List<Product> products = ProductDaoFactory.getInstance().getProducts(categoryID);
 		int ROWS = products.size() / COLS;
 		if ((products.size() % COLS) != 0) ROWS += 1; 
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
			if (pos >= products.size()) break;
			for (int j = 0; j < COLS; j++) {
				if (pos >= products.size()) break;
				Product p = products.get(pos++);
				Button btn = new CoffeeShopButton(p.getName(), new ImageView(CoffeeShopUtils.convertByteArray2JavaFXImage(p.getImage(), 100,100)), p);
				btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
				btn.setContentDisplay(ContentDisplay.TOP);
				ContextMenu contextMenu = new ContextMenu();
				if (p.getProductCatelogy().getID() == STIMULANT_DRINK ||
			    	p.getProductCatelogy().getID() == FRUIT_DRINK) {
	            	// Create ContextMenu
			        List<BeverageSizePrice> bsps = BeverageSizePriceDaoFactory.getInstance().getBeverageSizePrices(p.getID());
			        if (bsps != null) {
			        	for (BeverageSizePrice bsp : bsps) {
			        		MenuItem item = new CoffeeShopMenuItem(bsp.getBeverageSize().getDescription(), new Object[] {p, bsp});
			        		item.setOnAction(new EventHandler<ActionEvent>() {
			                    @Override
			                    public void handle(ActionEvent event) {
			                        CoffeeShopMenuItem item = (CoffeeShopMenuItem) event.getSource();
			                        CashierController.this.updateOrInsertRow((Product) item.getObject(0), (BeverageSizePrice) item.getObject(1));
			                        calculateTotal();
			                    }
			                });
			        		contextMenu.getItems().add(item);
			        	}
			        }
				}
				
				btn.setOnMousePressed(new EventHandler<MouseEvent>() {
				    @Override
				    public void handle(MouseEvent event) {
				        if (event.isPrimaryButtonDown()) {
				        	CoffeeShopButton cfBtn = ((CoffeeShopButton) event.getSource());
				        	Product selectedProd = (Product) cfBtn.getObject();
				            if (selectedProd.getProductCatelogy().getID() == STIMULANT_DRINK ||
				            	selectedProd.getProductCatelogy().getID() == FRUIT_DRINK) {
				        		contextMenu.show(cfBtn, event.getScreenX(), event.getScreenY());
				        	} else {
				        		CashierController.this.updateOrInsertRow(selectedProd, null);
				        		calculateTotal();
				        	}
				        }
				    }
				});
				target.add(btn, j, i);
			}
		}
	}

	ObservableList<String> sizeChoice = FXCollections.observableArrayList (
		    new String("Small"),
		    new String("Medium"),
		    new String("Large")
	);

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
		//loginAccount = AccountDaoFactory.getInstance().findAccount("tri");
		lblTax.setText("7%");
		model.setCurrentOrder(null);
		model.setNewOrder(true);
		btnOrder.setDisable(false);
		btnPrint.setDisable(true);

	}
	
	@FXML
	void initialize(){
		initializeTableView();
		initializeOthers();
		load2GridPane(STIMULANT_DRINK, gridStimulantDrink);
        load2GridPane(FRUIT_DRINK, gridFruitDrink);
        load2GridPane(SOFT_DRINK, gridSoftDrink);
        load2GridPane(FOOD, gridFood);
	}

	private ObservableList<OrderTableItem> data;

	public void addRow(Product p, BeverageSizePrice bsp){        
	    try{      
	            OrderTableItem odl = new OrderTableItem();
	            odl.getProduct().set(p);
	            if (bsp == null) {
	            	odl.getProductSize().set("");
	            	odl.getProductPrice().set(new Double(p.getUnitPrice() == null ? 0 : p.getUnitPrice()));
	            } else {
	            	odl.getProductSize().set(bsp.getBeverageSize().getDescription());
	            	odl.getProductPrice().set(new Double(bsp.getUnitPrice()));
	            }
	            odl.getQuantity().set(String.valueOf(1));
	            data.add(odl);                  
	            tblOrderLine.setItems(data);
	    }
	    catch(Exception e){
	          e.printStackTrace();
	          System.out.println("Error on Building Data");            
	    }
	}
	
	public void updateOrInsertRow(Product p, BeverageSizePrice bsp) {
		try{      
            boolean found = false;
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
            	addRow(p, bsp);
            } else {
            	tblOrderLine.refresh();
            }
	    }
	    catch(Exception e){
	          e.printStackTrace();
	          System.out.println("Error on Building Data");            
	    }
	}
	
	@FXML
	public void handleSelectionChanged(Event t) {
		if (tabStimulantDrink.isSelected()) {
			load2GridPane(STIMULANT_DRINK, gridStimulantDrink);
		} else if (tabFruitDrink.isSelected()) {
			load2GridPane(FRUIT_DRINK, gridFruitDrink);
		} else if (tabSoftDrink.isSelected()) {
			load2GridPane(SOFT_DRINK, gridSoftDrink);
		} else if (tabFood.isSelected()) {
			load2GridPane(FOOD, gridFood);
		}
    }
	
	void clearOrder() {
		data.clear();
		tblOrderLine.refresh();
		txtCustomer.setText("");
		cboToken.getSelectionModel().selectFirst();
		cboOrderType.getSelectionModel().selectFirst();
		//lblTax.setText("0.0");
		lblTotal.setText("0.0");
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
		CustomerOrder o = new CustomerOrder();
		o.setCustomerName(txtCustomer.getText());
		o.setAccount(loginAccount);
		o.setAmount(Float.parseFloat(lblTotal.getText()));
		o.setListOrderLine(getOrderLines());
		o.setOrderDate(LocalDate.now());
		o.setOrderNo(generateOrderNo());
		o.setOrderType(cboOrderType.getSelectionModel().getSelectedItem());
		o.setStatus("New");
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
	@FXML protected void handleNewButtonAction(ActionEvent event) {
		clearOrder();
		model.setCurrentOrder(null);
		model.setNewOrder(true);
		btnOrder.setDisable(false);
		btnPrint.setDisable(true);
    }
	
	@FXML protected void handleOrderButtonAction(ActionEvent event) {
		if (validateFields()) {
			CustomerOrder co = buildNewCustomerOrder();
			CustomerOrderDaoFactory.getInstance().newCustomerOrder(co);
			model.setCurrentOrder(co);
			model.setNewOrder(false);
			btnOrder.setDisable(true);
			btnPrint.setDisable(false);
		}
	}

	public void setLoginView(CoffeeShopLoginView coffeeShopLoginView) {
		this.coffeeShopLoginView = coffeeShopLoginView;
	}
	
	public void showParent() {
		if (coffeeShopLoginView != null) {
			coffeeShopLoginView.unhide();
		}
	}
	
	@FXML private void print(ActionEvent event) {
		try {
            PDFView pdfV = new PDFView();
            pdfV.show("PDF");
            ((PDFReportController)pdfV.getLoader().getController()).showPDF(model.getCurrentOrder());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
