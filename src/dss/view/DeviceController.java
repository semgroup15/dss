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
import javafx.scene.text.Text;

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
        viewDetailsButton.setOnAction(actionEvent -> this.mainApp.getDetailsController().displayDevice(this.device));
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