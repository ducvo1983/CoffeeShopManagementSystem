package mpp.course.spring2017.project.coffeeshop.view;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class ShopCoffeeUtils {
	public static Image convertByteArray2JavaFXImage(byte[] raw, final int width, final int height) {
        WritableImage image = new WritableImage(width, height);
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(raw);
            BufferedImage read = ImageIO.read(bis);
            image = SwingFXUtils.toFXImage(read, null);
        } catch (IOException ex) {
        }
        return image;
    }
	
	public static void showErrorMessgge(String msg) {
		new Alert(Alert.AlertType.ERROR, msg).showAndWait();
	}
}
