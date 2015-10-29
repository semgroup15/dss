package dss.view;

import dss.models.device.Device;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class DeviceListController {
    // Reference to the main view.
	private MainView mainApp;

    @FXML
    private VBox deviceListVBox;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        displayDevice( Device.manager.select(new Device.QueryBuilder()
                        .byManufacturerName("samsung")
                        .byName("galaxy")).get(0));
    }

    public void displayDeviceList(List<Device> deviceList)
    {

    }

    public void displayDevice(Device device)
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainView.class.getResource("DeviceLayout.fxml"));
            AnchorPane devicePane = loader.load();

            deviceListVBox.getChildren().add(devicePane);
            DeviceController controller = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
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