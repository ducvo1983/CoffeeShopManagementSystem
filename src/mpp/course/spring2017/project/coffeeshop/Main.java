package mpp.course.spring2017.project.coffeeshop;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import mpp.course.spring2017.project.coffeeshop.view.CoffeeShopLoginView;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Platform.setImplicitExit(false);
			new CoffeeShopLoginView().load(primaryStage);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
