package mpp.course.spring2017.project.coffeeshop.controller;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import mpp.course.spring2017.project.coffeeshop.activemq.IMessageReceiver;
import mpp.course.spring2017.project.coffeeshop.activemq.MessageReceiver;
import mpp.course.spring2017.project.coffeeshop.dao.AccountDaoFactory;
import mpp.course.spring2017.project.coffeeshop.dao.BeverageSizePriceDaoFactory;
import mpp.course.spring2017.project.coffeeshop.dao.CustomerOrderDaoFactory;
import mpp.course.spring2017.project.coffeeshop.dao.OrderLineDaoFactory;
import mpp.course.spring2017.project.coffeeshop.model.Account;
import mpp.course.spring2017.project.coffeeshop.model.BeverageSizePrice;
import mpp.course.spring2017.project.coffeeshop.model.CustomerOrder;
import mpp.course.spring2017.project.coffeeshop.model.OrderLine;
import mpp.course.spring2017.project.coffeeshop.model.Product;
import mpp.course.spring2017.project.coffeeshop.view.CoffeeShopLoginView;
import mpp.course.spring2017.project.coffeeshop.view.CoffeeShopMenuItem;
import mpp.course.spring2017.project.coffeeshop.view.CoffeeShopToggleButton;
import mpp.course.spring2017.project.coffeeshop.view.CoffeeShopUtils;

public class ChefBartenderController implements MessageListener {
	@FXML private TableView<OrderTableItem> tblOrderLine;
	@FXML private TableColumn<OrderTableItem,Product> colProduct;
	@FXML private TableColumn<OrderTableItem,String> colSize;
	@FXML private TableColumn<OrderTableItem,Double> colPrice;
	@FXML private TableColumn<OrderTableItem,String> colQuantity;
	@FXML private GridPane gridOrders;
	@FXML private AnchorPane firstAnchorPane;
	
	private final int COLS = 5;
	private final String CUSTOMER_IMAGE = "file:images/customer.png";
	private Account loginAccount = null;
	private final String ORDER_STATUS_PROCESSING = "Processing";
	private final String ORDER_STATUS_ALARM = "Token is alarm";
	private final String ORDER_STATUS_DONE = "Done";
	private CoffeeShopLoginView coffeeShopLoginView = null;
	private IMessageReceiver msgReceiver = null;
	
	MenuItem createMenuItem(String name, CustomerOrder co, ToggleButton btn) {
		MenuItem item = new CoffeeShopMenuItem(name, new Object[] {co, btn});
		item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	CoffeeShopMenuItem item = (CoffeeShopMenuItem) event.getSource();
            	CustomerOrder co = (CustomerOrder) item.getObject(0);
            	co.setStatus(item.getText());
            	ToggleButton btn = (ToggleButton) item.getObject(1);
            	CustomerOrderDaoFactory.getInstance().updateCustomerOrder(co);
            	setControlStatus(item.getText(), btn, item);
            	if (item.getText().equals(ORDER_STATUS_DONE)) {
            		data.clear();
            		load2GridPane(gridOrders);
            	}
            }
        });
		return item;
	}
	
	private void setControlStatus(String status, ToggleButton btn, MenuItem item) {
		if (status.equals(ORDER_STATUS_PROCESSING)) {
    		btn.setStyle("-fx-border-color: green");
    		item.setDisable(true);
    	} else if (status.equals(ORDER_STATUS_ALARM)) {
    		btn.setStyle("-fx-border-color: red");
    		item.setDisable(true);
    	}
	}
	private synchronized void load2GridPane(GridPane target) {
		if (target.getColumnConstraints() != null)
			target.getColumnConstraints().clear();
		if (target.getRowConstraints() != null)
			target.getRowConstraints().clear();
		if (target.getChildren() != null) {
			target.getChildren().clear();
		}
		// Add row and column
		List<CustomerOrder> orders = CustomerOrderDaoFactory.getInstance().getActiveCustomerOrders();
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
		final ToggleGroup group = new ToggleGroup();
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
		    public void changed(ObservableValue<? extends Toggle> ov,
		        Toggle toggle, Toggle new_toggle) {
		            if (new_toggle == null) {
		                data.clear();
		            }
		            else {
		            	data.clear();
		            	CoffeeShopToggleButton cfBtn = (CoffeeShopToggleButton) group.getSelectedToggle();
		            	loadOrderLines((CustomerOrder) cfBtn.getObject());
		            }
		            tblOrderLine.refresh();
		         }
		});
		for (int i = 0; i < ROWS; i++) {
			if (pos >= orders.size()) break;
			for (int j = 0; j < COLS; j++) {
				if (pos >= orders.size()) break;
				CustomerOrder co = orders.get(pos++);
				ToggleButton btn = new CoffeeShopToggleButton(co.getCustomerName(), new ImageView(new Image(CUSTOMER_IMAGE)), co);
				btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
				btn.setContentDisplay(ContentDisplay.TOP);
				ContextMenu contextMenu = new ContextMenu();
				MenuItem processingItem = createMenuItem(ORDER_STATUS_PROCESSING, co, btn);
				MenuItem alarmItem = createMenuItem(ORDER_STATUS_ALARM, co, btn);
				MenuItem doneItem = createMenuItem(ORDER_STATUS_DONE, co, btn);
        		contextMenu.getItems().add(processingItem);
        		contextMenu.getItems().add(alarmItem);
        		contextMenu.getItems().add(doneItem);
				btn.setOnMousePressed(new EventHandler<MouseEvent>() {
				    @Override
				    public void handle(MouseEvent event) {
				    	if (event.isSecondaryButtonDown()) {
				    		CoffeeShopToggleButton cfBtn = ((CoffeeShopToggleButton) event.getSource());
			        		contextMenu.show(cfBtn, event.getScreenX(), event.getScreenY());
				        }
				    }
				});
				
				if (co.getStatus().equals(ORDER_STATUS_PROCESSING)) {
					setControlStatus(co.getStatus(), btn, processingItem);
				} else if (co.getStatus().equals(ORDER_STATUS_ALARM)) {
					setControlStatus(co.getStatus(), btn, alarmItem);
				}
				btn.setToggleGroup(group);
				target.add(btn, j, i);
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
	        data = FXCollections.observableArrayList();
	
	}
	
	void initializeOthers() {
		firstAnchorPane.setMinWidth(300);
		firstAnchorPane.setMaxWidth(300);
		loginAccount = AccountDaoFactory.getInstance().findAccount("lan");
		
		try {
			msgReceiver = new MessageReceiver(CoffeeShopUtils.getConfig("activeMQ_URL"), CoffeeShopUtils.getConfig("orderQName"));
			if(!msgReceiver.createConnection()) {
				System.out.println("Failed to create connection to ActiveMQ.");
			} 
			else {
				msgReceiver.registerListener(this);
			}
		} catch(JMSException jmsEx) {
        	jmsEx.printStackTrace();
        } 
	}
	
	
	@FXML
	void initialize(){
		initializeTableView();
		initializeOthers();
		load2GridPane(gridOrders);
	}

	private ObservableList<OrderTableItem> data;

	void loadOrderLines(CustomerOrder co) {
		List<OrderLine> odls = OrderLineDaoFactory.getInstance().getOrderLines(co.getOrderNo());
		for (OrderLine odl : odls) {
			Product p = odl.getProduct();
			List<BeverageSizePrice> bsps = BeverageSizePriceDaoFactory.getInstance().getBeverageSizePrices(p.getID());
			if (bsps != null) {
				for (BeverageSizePrice bsp: bsps) {
					addRow(odl.getProduct(), bsp);
				}
			} else {
				addRow(odl.getProduct(), null);
			}
		}
	}
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
	
	void showErrorMessgge(String msg) {
		new Alert(Alert.AlertType.ERROR, msg).showAndWait();
	}

	public void setLoginView(CoffeeShopLoginView coffeeShopLoginView) {
		this.coffeeShopLoginView = coffeeShopLoginView;
	}
	
	public void showParent() {
		if (coffeeShopLoginView != null) {
			coffeeShopLoginView.unhide();
		}
	}

	@Override
	public void onMessage(Message msg) {
		if (!(msg instanceof TextMessage)) throw new RuntimeException("no text message");
		//TextMessage tm = (TextMessage) msg;
		//String msg = tm.getText(); //in case want to get value

		final Task<Void> task = new Task<Void>() {
		     @Override protected Void call() throws Exception {
		    	 Platform.runLater(new Runnable() {
	                 @Override public void run() {
	                     load2GridPane(gridOrders);
	                 }
	             });
		         return null;
		     }
		 };
		 new Thread(task).start();
	}
}
