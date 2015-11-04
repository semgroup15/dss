package dss.view;

import dss.models.device.Device;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.apache.commons.lang3.ObjectUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class DeviceController implements Initializable {

	/* * *
	 * View elements must be defined with the @FXML tag to be accessed
	 */
    @FXML
    private Text modelNameText;

    @FXML
    private Button viewDetailsButton;

    @FXML
    private Rectangle starRatingBackgroundBox;

    @FXML
    private ImageView deviceImageView;
    // Reference to the main view.
	private MainView mainApp;
    private Device device;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    public void initialize(URL location, ResourceBundle resourceBundle) {

    }

    public void loadDevice(Device device)
    {
        this.device = device;
        modelNameText.setText(device.getManufacturer().name + " " + device.name);
        DetailsController dc = this.mainApp.getMainController().getDetailsController();
        viewDetailsButton.setOnAction(actionEvent -> dc.displayDevice(this.device));
        starRatingBackgroundBox.setWidth((Math.ceil(Math.random()*5)) * 20);
        try{
            File deviceFile = device.getImageFile();
            FileInputStream fis = new FileInputStream(deviceFile);
            Image image = new Image(fis);
            deviceImageView.setImage(image);
        }
        catch (NullPointerException e)
        {
            //continue
        }
        catch (FileNotFoundException e)
        {
            //continue;
        }

    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainView mainApp) {
        this.mainApp = mainApp;
    }
}