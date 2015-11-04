package dss.view;

import dss.models.device.Device;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DetailsController {
    // Reference to the main view.
	private MainView mainApp;

    @FXML private ImageView picture;
    @FXML private Text model;
    @FXML private Text manufacturer;
    @FXML private Text displayDimensions;
    @FXML private Text bodyDimensions;
    @FXML private Text colors;
    @FXML private Text battery;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    public void initialize() {

    }
    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainView mainApp) {
        this.mainApp = mainApp;
    }

    public void displayDevice(Device device)
    {
        this.mainApp.getMainController().setScreen(MainController.Screen.Details);
        this.model.setText(device.name);
        this.manufacturer.setText(device.getManufacturer().name);
        this.displayDimensions.setText("Display dimensions: " +
        		device.display.width + " x " +
        		device.display.height);

        this.bodyDimensions.setText("Body dimensions: " +
        		device.body.width + " mm x " +
        		device.body.height + " mm x " +
        		device.body.depth + " mm");

        String colors = "";

        colors += device.color.black ? "black, " : "";
        colors += device.color.white ? "white, " : "";
        colors += device.color.blue ? "blue, " : "";
        colors += device.color.red ? "red, " : "";
        colors += device.color.pink ? "pink, " : "";
        colors += device.color.silver ? "silver, " : "";
        colors += device.color.gray ? "gray, " : "";
        colors += device.color.yellow ? "yellow, " : "";
        colors += device.color.green ? "green, " : "";
        colors += device.color.gold ? "gold, " : "";
        colors += device.color.orange ? "orange, " : "";

        // Remove last ", "
        if(colors.length() > 0)
        	colors = colors.substring(0, colors.length() - 2);

        this.colors.setText("Colors: " + colors);
        this.battery.setText("Idle battery life: " + device.battery.sleep + " hours");
        //this.picture.setImage(device.getImageFile()); Todo
    }
}