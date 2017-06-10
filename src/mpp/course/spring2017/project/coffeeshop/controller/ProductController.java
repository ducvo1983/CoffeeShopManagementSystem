package mpp.course.spring2017.project.coffeeshop.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import mpp.course.spring2017.project.coffeeshop.dao.BeverageSizeDaoFactory;
import mpp.course.spring2017.project.coffeeshop.dao.BeverageSizePriceDaoFactory;
import mpp.course.spring2017.project.coffeeshop.dao.ProductCategoryDaoFactory;
import mpp.course.spring2017.project.coffeeshop.dao.ProductDaoFactory;
import mpp.course.spring2017.project.coffeeshop.model.BeverageSize;
import mpp.course.spring2017.project.coffeeshop.model.BeverageSizePrice;
import mpp.course.spring2017.project.coffeeshop.model.Product;
import mpp.course.spring2017.project.coffeeshop.model.ProductCatelogy;
import mpp.course.spring2017.project.coffeeshop.view.CoffeeShopButton;
import mpp.course.spring2017.project.coffeeshop.view.CoffeeShopMenuItem;

public class ProductController implements Initializable {
	private final int COLS = 5;
	private final int FOOD = 1;
	private final int STIMULANT_DRINK = 2;
	private final int FRUIT_DRINK = 3;
	private final int SOFT_DRINK = 4;
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
	@FXML private ChoiceBox<KeyValuePair> chBoxCategory;
	@FXML private DatePicker createdDatePicker;
	@FXML private DatePicker updatedDatePicker;
	@FXML private ImageView imageProduct;
	@FXML private GridPane gridSizePrice;
	@FXML private FileChooser imageChooser;
	
	
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
				chBoxCategory.getItems().add(new KeyValuePair(pc.getID(), pc.getName()));
			}
		}
		
		listFood = ProductDaoFactory.getInstance().getProducts(FOOD);
		listStimulantDrink = ProductDaoFactory.getInstance().getProducts(STIMULANT_DRINK);
		listFruitDrink = ProductDaoFactory.getInstance().getProducts(FRUIT_DRINK);
		listSoftDrink = ProductDaoFactory.getInstance().getProducts(SOFT_DRINK);
		
		load2GridPane(STIMULANT_DRINK, gridStimulantDrink);
        load2GridPane(FRUIT_DRINK, gridFruitDrink);
        load2GridPane(SOFT_DRINK, gridSoftDrink);
        load2GridPane(FOOD, gridFood);
        
        productTabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {
            	switch(newTab.getId()) {
            	case "tabFruitDrink":
            		load2GridPane(FRUIT_DRINK, gridFruitDrink);
            		break;
            	case "tabSoftDrink":
            		load2GridPane(SOFT_DRINK, gridSoftDrink);
            		break;
            	case "tabStimulantDrink":
            		load2GridPane(STIMULANT_DRINK, gridStimulantDrink);
            		break;
            	case "tabFood":
            		load2GridPane(FOOD, gridFood);
            		break;
            	}
            }
        });
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
    	imageProduct.setImage(convertByteArray2JavaFXImage(p.getImage(), 100, 100));
    	
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
	
	private static Image convertByteArray2JavaFXImage(byte[] raw, final int width, final int height) {
        WritableImage image = new WritableImage(width, height);
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(raw);
            BufferedImage read = ImageIO.read(bis);
            image = SwingFXUtils.toFXImage(read, null);
        } catch (IOException ex) {
            //Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        return image;
    }
	
	private void load2GridPane(int categoryID, GridPane target) {
		List<Product> products = null; // = ProductDaoFactory.getInstance().getProducts(categoryID);
		switch(categoryID) {
		case STIMULANT_DRINK:
			products = listStimulantDrink;
			break;
		case FRUIT_DRINK:
			products = listFruitDrink;
			break;
		case SOFT_DRINK:
			products = listSoftDrink;
			break;
		case FOOD:
			products = listFood;
			break;
		}
 		int ROWS = products.size() / COLS;
 		if ((products.size() % COLS) != 0) ROWS += 1;
		int pos = 0;
		for (int i = 0; i < ROWS; i++) {
			if (pos >= products.size()) break;
			for (int j = 0; j < COLS; j++) {
				if (pos >= products.size()) break;
				Product p = products.get(pos++);
				Button btn = new CoffeeShopButton(p.getName(), new ImageView(convertByteArray2JavaFXImage(p.getImage(), 100,100)), p);
				//btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
				btn.setContentDisplay(ContentDisplay.TOP);
				ContextMenu contextMenu = new ContextMenu();
				if (p.getProductCatelogy().getID() == STIMULANT_DRINK ||
			    	p.getProductCatelogy().getID() == FRUIT_DRINK) {
	            	// Create ContextMenu
			        List<BeverageSizePrice> bsps = BeverageSizePriceDaoFactory.getInstance().getBeverageSizePrices(p.getID());
			        if (bsps != null) {
			        	for (BeverageSizePrice bsp : bsps) {
			        		MenuItem item = new CoffeeShopMenuItem(bsp.getBeverageSize().getDescription(), p, bsp);
			        		item.setOnAction(new EventHandler<ActionEvent>() {
			                    @Override
			                    public void handle(ActionEvent event) {
			                        CoffeeShopMenuItem item = (CoffeeShopMenuItem) event.getSource();
			                        //CashierController.this.updateOrInsertRow(item.getProduct(), item.getBeverageSizePrice());
			                        //calculateTotal();
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
				        	Product selectedProd = cfBtn.getProduct();
				        	if(selectedProd != null) displayProductInfo(selectedProd);
				        }
				    }
				});
				btn.setPrefSize(100, 100);
				target.add(btn, j, i);
			}
		}
	}
	
	@FXML protected void browseFile(MouseEvent event) {
		try {  
			imageChooser.setTitle("Open Resource File");
			File file = imageChooser.showOpenDialog(rootSplitPane.getScene().getWindow());
			if(file != null) {
				imageProduct.setImage(convertByteArray2JavaFXImage(Files.readAllBytes(file.toPath()), 100, 100));
			}
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
