import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {
	@FXML private HBox subMenu;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("mpp/course/spring2017/project/coffeeshop/view/FXMLAdminForm.fxml"));
	        
			primaryStage.setTitle("Administrator screen");
			primaryStage.setScene(new Scene(root));
			primaryStage.setFullScreen(true);
			primaryStage.show();

			/*
			HBox fileRoot = new HBox();
	        VBox menu = new VBox();
	        menu.setStyle("-fx-background-color: blue;");
	        menu.setFillWidth(true);
	        Button backBtn = new Button("Left Arrow");
	        backBtn.setPrefWidth(100);
	        backBtn.getStyleClass().add("custom-menu-button");
	        backBtn.setOnAction(new EventHandler<ActionEvent>(){
	            @Override
	            public void handle(ActionEvent event) {
	                FadeTransition hideFileRootTransition = new FadeTransition(Duration.millis(500), fileRoot);
	                hideFileRootTransition.setFromValue(1.0);
	                hideFileRootTransition.setToValue(0.0);
	
	                FadeTransition showEditorRootTransition = new FadeTransition(Duration.millis(500), editorRoot);
	                showEditorRootTransition.setFromValue(0.0);
	                showEditorRootTransition.setToValue(1.0);
	
	                showEditorRootTransition.play();
	                hideFileRootTransition.play();
	                root.getChildren().remove(fileRoot);
	            }
	        });
	        Button infoBtn = new Button("Info");
	        infoBtn.setPrefWidth(100);
	        infoBtn.getStyleClass().add("custom-menu-button");
	        Button newBtn = new Button("New");
	        newBtn.setPrefWidth(100);
	        newBtn.getStyleClass().add("custom-menu-button");
	        Button openBtn = new Button("Open");
	        openBtn.setPrefWidth(100);
	        openBtn.getStyleClass().add("custom-menu-button");
	        menu.getChildren().addAll(backBtn,infoBtn, newBtn, openBtn);
	        VBox.setVgrow(infoBtn, Priority.ALWAYS);
	        fileRoot.getChildren().add(menu);
	
	        btn.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	                root.getChildren().add(fileRoot);
	                FadeTransition hideEditorRootTransition = new FadeTransition(Duration.millis(500), editorRoot);
	                hideEditorRootTransition.setFromValue(1.0);
	                hideEditorRootTransition.setToValue(0.0);
	
	                FadeTransition showFileRootTransition = new FadeTransition(Duration.millis(500), fileRoot);
	                showFileRootTransition.setFromValue(0.0);
	                showFileRootTransition.setToValue(1.0);
	                hideEditorRootTransition.play();
	                showFileRootTransition.play();
	            }
	        }); 
			*/
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/*@Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Menu");

        final StackPane root = new StackPane();
        AnchorPane editorRoot = new AnchorPane();
        editorRoot.getChildren().add(btn);
        root.getChildren().add(editorRoot);

        Scene scene = new Scene(root, 300, 250);
        scene.getStylesheets().add("mpp/course/spring2017/project/coffeeshop/view/styles.css");

        primaryStage.setScene(scene);
        primaryStage.show();

        HBox fileRoot = new HBox();
        VBox menu = new VBox();
        menu.setStyle("-fx-background-color: blue;");
        menu.setFillWidth(true);
        Button backBtn = new Button("Left Arrow");
        backBtn.setPrefWidth(100);
        backBtn.getStyleClass().add("custom-menu-button");
        backBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                FadeTransition hideFileRootTransition = new FadeTransition(Duration.millis(500), fileRoot);
                hideFileRootTransition.setFromValue(1.0);
                hideFileRootTransition.setToValue(0.0);

                FadeTransition showEditorRootTransition = new FadeTransition(Duration.millis(500), editorRoot);
                showEditorRootTransition.setFromValue(0.0);
                showEditorRootTransition.setToValue(1.0);

                showEditorRootTransition.play();
                hideFileRootTransition.play();
                root.getChildren().remove(fileRoot);
            }
        });
        Button infoBtn = new Button("Info");
        infoBtn.setPrefWidth(100);
        infoBtn.getStyleClass().add("custom-menu-button");
        Button newBtn = new Button("New");
        newBtn.setPrefWidth(100);
        newBtn.getStyleClass().add("custom-menu-button");
        Button openBtn = new Button("Open");
        openBtn.setPrefWidth(100);
        openBtn.getStyleClass().add("custom-menu-button");
        menu.getChildren().addAll(backBtn,infoBtn, newBtn, openBtn);
        VBox.setVgrow(infoBtn, Priority.ALWAYS);
        fileRoot.getChildren().add(menu);

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                root.getChildren().add(fileRoot);
                FadeTransition hideEditorRootTransition = new FadeTransition(Duration.millis(500), editorRoot);
                hideEditorRootTransition.setFromValue(1.0);
                hideEditorRootTransition.setToValue(0.0);

                FadeTransition showFileRootTransition = new FadeTransition(Duration.millis(500), fileRoot);
                showFileRootTransition.setFromValue(0.0);
                showFileRootTransition.setToValue(1.0);
                hideEditorRootTransition.play();
                showFileRootTransition.play();
            }
        });

    }*/
	
	public static void main(String[] args) {
		/*Role r = new Role();
		r.setRoleName("bartender");
		
		Account acc = new Account();
		acc.setUserName("lan");
		acc.setPassword("123duc");
		acc.setRole(r);
		
		Employee e=new Employee();
		e.setFirstName("Lan");
		e.setLastName("Hong");
		e.setDateOfBirth(LocalDate.of(1992, Month.JULY, 28));
		e.setAddress("California");
		e.setJoinDate(LocalDate.now());
		e.setPhone("644-567-3213");		
		e.setAccount(acc);
		boolean flag = TestEmployee.newEmployee(e);
		System.out.println("New employee result: " + flag);
	
		List<Employee> listE = TestEmployee.getAllEmployees();
		for(Employee em : listE) {
			System.out.println(em.getFirstName());
		}
		*/
		/*Employee e = TestEmployee.findEmployee("Tri");
		System.out.println(e.getDateOfBirth().toString());*/
		
		
		/*Role r = new Role();
		//r.setID(2);
		r.setRoleName("chef");
		Account acc = new Account();
		acc.setUserName("duc");
		acc.setPassword("123duc");
		acc.setRole(r);
		System.out.println("New account result: " + TestEmployee.newAccount(acc));*/
		
		/*Account acc = TestEmployee.findAccount("tritiamo");
		System.out.println(acc.getUserName() + "-" + acc.getRole().getRoleName());*/
		
		/*CustomerOrder or = TestEmployee.findOrder("1");
		System.out.println("Customer order:" + or.getCustomerName());
		System.out.println("Total items: " + or.getListOrderLine().size());
		for(OrderLine ol : or.getListOrderLine()) {
			System.out.println("\t"+ol.getProduct().getName() + ":" + ol.getBeverageSize() + ":" + ol.getQuantity() + ":" + ol.getUnitPrice() + ":" + ol.getPrice());
		}*/
		
		/*Token t = new Token(); //TestEmployee.findToken(2);
		t.setID(2);
		t.setStatus("A");
		Account acc = HibernateFactory.findAccount("abc");
		
		Product p1 = HibernateFactory.findProduct(1);		
		OrderLine ol1 = new OrderLine();
		ol1.setProduct(p1);
		ol1.setQuantity(1);
		ol1.setUnitPrice((float)1.5);
		ol1.setPrice((float)1.5);
		
		Product p2 = HibernateFactory.findProduct(2);		
		OrderLine ol2 = new OrderLine();
		ol2.setProduct(p2);
		ol2.setQuantity(2);
		ol2.setUnitPrice((float)2.45);
		ol2.setPrice((float)4.9);
		
		List<OrderLine> listOL = new ArrayList<OrderLine>();
		listOL.add(ol1);
		listOL.add(ol2);
		
		CustomerOrder order = new CustomerOrder();
		order.setOrderNo("ORDER4");
		order.setOrderDate(LocalDate.now());
		order.setStatus("NEW");
		order.setCustomerName("Duc");
		order.setToken(t);
		order.setTax((float)0.45);
		order.setAmount((float)6.4);
		order.setAccount(acc);
		order.setListOrderLine(listOL);
		
		System.out.println("New order: " + HibernateFactory.newOrder(order));*/
		
		launch(args);
	}
	
}
