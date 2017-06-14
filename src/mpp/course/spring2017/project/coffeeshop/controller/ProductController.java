package mpp.course.spring2017.project.coffeeshop.controller;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.jms.JMSException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import mpp.course.spring2017.project.coffeeshop.activemq.IMessageSender;
import mpp.course.spring2017.project.coffeeshop.activemq.MessageSender;
import mpp.course.spring2017.project.coffeeshop.dao.BeverageSizeDaoFactory;
import mpp.course.spring2017.project.coffeeshop.dao.BeverageSizePriceDaoFactory;
import mpp.course.spring2017.project.coffeeshop.dao.ProductCategoryDaoFactory;
import mpp.course.spring2017.project.coffeeshop.dao.ProductDaoFactory;
import mpp.course.spring2017.project.coffeeshop.model.BeverageSize;
import mpp.course.spring2017.project.coffeeshop.model.BeverageSizePrice;
import mpp.course.spring2017.project.coffeeshop.model.Product;
import mpp.course.spring2017.project.coffeeshop.model.ProductCatelogy;
import mpp.course.spring2017.project.coffeeshop.view.CoffeeShopButton;
import mpp.course.spring2017.project.coffeeshop.view.CoffeeShopUtils;

class KeyValuePair3 {
	private final ProductCatelogy key;
	private final String value;
	public KeyValuePair3(ProductCatelogy key, String value) {
		this.key = key;
		this.value = value;
	}

	public ProductCatelogy getKey() { return key; }

	public String toString() { return value; }
}

public class ProductController implements Initializable {
	private final int COLS = 5;
	private final int FOOD = 1;
	private final int STIMULANT_DRINK = 2;
	private final int FRUIT_DRINK = 3;
	private final int SOFT_DRINK = 4;
	private Product selectedProd = null;
	private List<Product> listFood;
	private List<Product> listStimulantDrink;
	private List<Product> listFruitDrink;
	private List<Product> listSoftDrink;
	private List<ProductCatelogy> listCatelogy;
	private List<BeverageSize> listSize;
	private List<TextField> listTxtSizePrice;
	
	@FXML private SplitPane rootSplitPane;
	@FXML private GridPane gridStimulantDrink;
	@FXML private GridPane gridFruitDrink;
	@FXML private GridPane gridSoftDrink;
	@FXML private GridPane gridFood;
	@FXML private TabPane productTabPane;
	
	@FXML private TextField txtName;
	@FXML private TextField txtUnitPrice;
	@FXML private ChoiceBox<KeyValuePair3> chBoxCategory;
	@FXML private DatePicker createdDatePicker;
	@FXML private DatePicker updatedDatePicker;
	@FXML private ImageView imageProduct;
	@FXML private GridPane gridSizePrice;
	@FXML private FileChooser imageChooser;
	@FXML private Label lblError;
	
	private IMessageSender msgSender;
	
	public IMessageSender getMsgSender() {
		return msgSender;
	}

	public void setMsgSender(IMessageSender msgSender) {
		this.msgSender = msgSender;
	}

	@Override
    public void initialize(URL url, ResourceBundle rb) {
		imageChooser = new FileChooser();
		imageChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png")
            //new FileChooser.ExtensionFilter("PNG", "*.png")
        );
		imageChooser.setTitle("Select Product's Image");
		
		Label lblSize;
		TextField txtPrice;
		int rowIndex = 0;
		listSize = BeverageSizeDaoFactory.getInstance().getAllBeverageSizes();
		if(listSize != null) {
			listTxtSizePrice = new ArrayList<TextField>();
			for(BeverageSize bs : listSize) {
				lblSize = new Label(bs.getDescription() + " / ");
				txtPrice = new TextField();
				txtPrice.setId("txtUnitPrice" + bs.getID());
				txtPrice.setMaxWidth(70);
				gridSizePrice.add(lblSize, 0, rowIndex);
				gridSizePrice.add(txtPrice, 1, rowIndex);
				rowIndex++;
				
				listTxtSizePrice.add(txtPrice);
			}
		}
		
		listCatelogy = ProductCategoryDaoFactory.getInstance().getAllProductCategories();
		if(listCatelogy != null) {
			chBoxCategory.getItems().add(null);
			for(ProductCatelogy pc : listCatelogy) {
				chBoxCategory.getItems().add(new KeyValuePair3(pc, pc.getName()));
			}
		}
		
		listFood = ProductDaoFactory.getInstance().getProducts(FOOD);
		listStimulantDrink = ProductDaoFactory.getInstance().getProducts(STIMULANT_DRINK);
		listFruitDrink = ProductDaoFactory.getInstance().getProducts(FRUIT_DRINK);
		listSoftDrink = ProductDaoFactory.getInstance().getProducts(SOFT_DRINK);
		
		load2GridPane(STIMULANT_DRINK);
        load2GridPane(FRUIT_DRINK);
        load2GridPane(SOFT_DRINK);
        load2GridPane(FOOD);
        
        productTabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {
            	switch(newTab.getId()) {
            	case "tabFruitDrink":
            		load2GridPane(FRUIT_DRINK);
            		break;
            	case "tabSoftDrink":
            		load2GridPane(SOFT_DRINK);
            		break;
            	case "tabStimulantDrink":
            		load2GridPane(STIMULANT_DRINK);
            		break;
            	case "tabFood":
            		load2GridPane(FOOD);
            		break;
            	}
            }
        });
        
      //new activemq message sender
        try {
        	msgSender = new MessageSender(CoffeeShopUtils.getConfig("activeMQ_URL"), CoffeeShopUtils.getConfig("productQName"));
            if(!msgSender.createConnection()) {
	        	System.out.println("Failed to create connection to ActiveMQ.");
	        }
        }
        catch(JMSException jmsEx) {
        	jmsEx.printStackTrace();
        }
	}
	
	private boolean validateForm() {
		try {
			lblError.setVisible(false);
			String sProdName = txtName.getText().trim();
			if(sProdName.equals("")) {
				lblError.setText("Please input all required fields (*)!");
				lblError.setVisible(true);
	        	txtName.requestFocus();
				return false;
			} else if(sProdName.length() > 20) {
				lblError.setText("Maximum size of Product Name is 20 characters!");
				lblError.setVisible(true);
	        	txtName.requestFocus();
				return false;
			}
			
			if(chBoxCategory.getValue() == null) {
				lblError.setText("Please select the Product Category!");
				lblError.setVisible(true);
				chBoxCategory.requestFocus();
				return false;
			}
			
			boolean flagUnitPrice = false, flagSizePrice = false;
			if(!txtUnitPrice.getText().trim().equals("")) {
				flagUnitPrice = true;
				if(Float.parseFloat(txtUnitPrice.getText().trim()) < 1) {
					lblError.setText("Unit Price must be greater than Zero!");
					lblError.setVisible(true);
					txtUnitPrice.requestFocus();
					return false;
				}
			}

			for(TextField txt : listTxtSizePrice) {		
				if(!txt.getText().trim().equals("")) {
					flagSizePrice = true;
					if(Float.parseFloat(txt.getText().trim()) < 1) {
						lblError.setText("Size Price must be greater than Zero!");
						lblError.setVisible(true);
						txt.requestFocus();
						return false;
					}
					
					if(flagUnitPrice) {
						lblError.setText("The product either has Unit Price or Size Price!");
						lblError.setVisible(true);
						return false;
					}
				}
			}
			if(!flagUnitPrice && !flagSizePrice) {
				lblError.setText("The product has either Unit Price or Size Price!");
				lblError.setVisible(true);
				return false;
			}
			
			if(imageProduct.getImage() == null) {
				lblError.setText("Please select a product's image!");
				lblError.setVisible(true);
				return false;
			}
		} 
		catch (NumberFormatException nfex) {
			lblError.setText("The Price must be numberic!");
			lblError.setVisible(true);
			return false;
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
		
		return true;
	}
	
	private void resetForm() {
		txtName.setText("");
		txtUnitPrice.setText("");
		chBoxCategory.getSelectionModel().select(0);
		createdDatePicker.setValue(LocalDate.now());
		updatedDatePicker.setValue(null);
		for(TextField txt : listTxtSizePrice) {
			txt.setText("");
		}
		imageProduct.setImage(null);
		lblError.setVisible(false);
	}
	
	private void displayProductInfo(Product p) {
		resetForm();
		txtName.setText(p.getName());
    	if(p.getUnitPrice() != null)	txtUnitPrice.setText(p.getUnitPrice().toString());
    	int index = 0;
    	if(listCatelogy != null) {
			for(int i=0; i<listCatelogy.size(); i++) {
				if(listCatelogy.get(i).getID() == p.getProductCatelogy().getID()) {
					index = i+1;
					break;
				}
			}
		}
		chBoxCategory.getSelectionModel().select(index);
    	createdDatePicker.setValue(p.getCreateDate());
    	updatedDatePicker.setValue(p.getUpdateDate());
    	imageProduct.setImage(CoffeeShopUtils.convertByteArray2JavaFXImage(p.getImage(), 100, 100));
    	
    	List<BeverageSizePrice> listSizePrice = BeverageSizePriceDaoFactory.getInstance().getBeverageSizePrices(p.getID());
    	if(listSizePrice != null) {
    		for(BeverageSizePrice sizePrice : listSizePrice) {
    			for(TextField txt : listTxtSizePrice) {
    				if(txt.getId().equals("txtUnitPrice" + sizePrice.getBeverageSize().getID())) {
    					txt.setText(String.valueOf(sizePrice.getUnitPrice()));
    					break;
    				}
    			}
    		}
    	}
  }
	
	private void load2GridPane(int categoryID) {
		List<Product> products = null; // = ProductDaoFactory.getInstance().getProducts(categoryID);
		GridPane target = null;
		switch(categoryID) {
		case STIMULANT_DRINK:
			listStimulantDrink = ProductDaoFactory.getInstance().getProducts(STIMULANT_DRINK);
			products = listStimulantDrink;
			target = gridStimulantDrink;
			gridStimulantDrink.getChildren().clear();
			break;
		case FRUIT_DRINK:
			listFruitDrink = ProductDaoFactory.getInstance().getProducts(FRUIT_DRINK);
			products = listFruitDrink;
			target = gridFruitDrink;
			gridFruitDrink.getChildren().clear();
			break;
		case SOFT_DRINK:
			listSoftDrink = ProductDaoFactory.getInstance().getProducts(SOFT_DRINK);
			products = listSoftDrink;
			target = gridSoftDrink;
			gridSoftDrink.getChildren().clear();
			break;
		case FOOD:
			listFood = ProductDaoFactory.getInstance().getProducts(FOOD);
			products = listFood;
			target = gridFood;
			gridFood.getChildren().clear();
			break;
		}
		target.getChildren().clear();
		
 		int ROWS = products.size() / COLS;
 		if ((products.size() % COLS) != 0) ROWS += 1;
		int pos = 0;
		for (int i = 0; i < ROWS; i++) {
			if (pos >= products.size()) break;
			for (int j = 0; j < COLS; j++) {
				if (pos >= products.size()) break;
				Product p = products.get(pos++);
				Button btn = new CoffeeShopButton(p.getName(), new ImageView(CoffeeShopUtils.convertByteArray2JavaFXImage(p.getImage(), 100,100)), p);
				//btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
				btn.setContentDisplay(ContentDisplay.TOP);
				
				btn.setOnMousePressed(new EventHandler<MouseEvent>() {
				    @Override
				    public void handle(MouseEvent event) {
				        if (event.isPrimaryButtonDown()) {
				        	CoffeeShopButton cfBtn = ((CoffeeShopButton) event.getSource());
				        	selectedProd = (Product) cfBtn.getObject();
				        	if(selectedProd != null) displayProductInfo(selectedProd);
				        }
				    }
				});
				btn.setPrefSize(100, 100);
				target.add(btn, j, i);
			}
		}
	}
	
	private void sendToProductQ() {
		try {
			msgSender.sendMessage("Product");
		}
		catch(JMSException jmsEx) {
			jmsEx.printStackTrace();
		}
	}
	
	@FXML protected void browseFile(MouseEvent event) {
		try {  
			imageChooser.setTitle("Open Resource File");
			File file = imageChooser.showOpenDialog(rootSplitPane.getScene().getWindow());
			if(file != null) {
				imageProduct.setImage(CoffeeShopUtils.convertByteArray2JavaFXImage(Files.readAllBytes(file.toPath()), 100, 100));
			}
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	@FXML protected void resetProduct(ActionEvent event) {
		resetForm();
		selectedProd = null;
	}
	
	@FXML protected void deleteProduct(ActionEvent event) {
		Alert alert;
		if(selectedProd == null) {
			alert = new Alert(AlertType.WARNING, "Please choose a product to delete!", ButtonType.OK);
        	alert.showAndWait();
		}
		else {
			alert = new Alert(AlertType.CONFIRMATION, "Are you sure to delete this product?", ButtonType.YES, ButtonType.NO);
        	alert.showAndWait();
        	if (alert.getResult() == ButtonType.YES) {
        	    if(ProductDaoFactory.getInstance().deleteProduct(selectedProd)) {
        	    	load2GridPane(selectedProd.getProductCatelogy().getID());
        	    	selectedProd = null;
        	    	resetForm();
        	    	
        	    	alert = new Alert(AlertType.INFORMATION, "Delete the product successfully!", ButtonType.OK);
                	alert.showAndWait();
        	    }
        	    else {
        	    	alert = new Alert(AlertType.ERROR, "Failed to delete the product!", ButtonType.OK);
                	alert.showAndWait();
        	    }	
        	}
		}	
	}
	
	@FXML protected void saveProduct(ActionEvent event) {
		if(!validateForm()) return;
		
		boolean flagEdit = true;
		int prodCategoryID = 1; //to refresh tab on UI
		//in case click reset, consider new product
		if(selectedProd == null) {
			selectedProd = new Product();
			selectedProd.setCreateDate(LocalDate.now());
			flagEdit = false;
		}
		else {
			selectedProd.setUpdateDate(LocalDate.now());
		}
		
		try {
			selectedProd.setName(txtName.getText().trim());
			if(!txtUnitPrice.getText().trim().equals("")) selectedProd.setUnitPrice(Float.parseFloat(txtUnitPrice.getText().trim()));
			else selectedProd.setUnitPrice(null);			
			selectedProd.setImage(CoffeeShopUtils.convertJavaFXImage2ByteArray(imageProduct));
			if(flagEdit) prodCategoryID = selectedProd.getProductCatelogy().getID();
			selectedProd.setProductCatelogy(chBoxCategory.getValue().getKey());
			if(!flagEdit) prodCategoryID = selectedProd.getProductCatelogy().getID();
			
			//in case there is unit price, then do not get size price
			Alert alert;
			if(!txtUnitPrice.getText().trim().equals("")) {
				if(flagEdit) {
					if(ProductDaoFactory.getInstance().updateProduct(selectedProd)) {
						sendToProductQ();
						
						alert = new Alert(AlertType.INFORMATION, "Update the product successfully!", ButtonType.OK);
			        	alert.showAndWait();
					}
				}
				else {
					if(ProductDaoFactory.getInstance().newProduct(selectedProd)) {
						sendToProductQ();
						
						alert = new Alert(AlertType.INFORMATION, "Create the new product successfully!", ButtonType.OK);
			        	alert.showAndWait();
			        	resetForm();
					}
				}
			}
			else {
				List<BeverageSizePrice> listBSP = new ArrayList<BeverageSizePrice>();
				BeverageSizePrice bsp = null;
				BeverageSize bs = null;
				String txtId;
				int sizeId;
				float sizePrice;
				for(TextField txt : listTxtSizePrice) {
					txtId = txt.getId();
					sizeId = Integer.parseInt(String.valueOf(txtId.charAt(txtId.length()-1)));
					
					if(!txt.getText().trim().equals("")) {
						sizePrice = Float.parseFloat(txt.getText().trim());
						bs = new BeverageSize();
						bs.setID(sizeId);
						bsp = new BeverageSizePrice();
						bsp.setProduct(selectedProd);
						bsp.setBeverageSize(bs);
						bsp.setUnitPrice(sizePrice);
						listBSP.add(bsp);
					}
				}
				
				if(flagEdit) {
					if(ProductDaoFactory.getInstance().updateProductWithSizePrice(selectedProd, listBSP)) {
						sendToProductQ();
						
						alert = new Alert(AlertType.INFORMATION, "Update the product successfully!", ButtonType.OK);
			        	alert.showAndWait();
					}
				}
				else {
					if(ProductDaoFactory.getInstance().newProductWithSizePrice(selectedProd, listBSP)) {
						sendToProductQ();
						
						alert = new Alert(AlertType.INFORMATION, "Create the new product successfully!", ButtonType.OK);
			        	alert.showAndWait();
			        	resetForm();
					}
				}
			}
			
			load2GridPane(prodCategoryID);
		}
		catch (Exception ex) {
			System.out.println("saveProduct: " + ex.getMessage());
		}
	}
}
