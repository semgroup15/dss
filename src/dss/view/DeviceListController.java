package dss.view;

import dss.models.device.Device;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DeviceListController {
    // Reference to the main view.
	private MainView mainApp;

    @FXML private URL location;
    @FXML private VBox deviceListVBox;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    public void initialize() {
        displayDeviceList(Device.manager.select(new Device.QueryBuilder()
                .byManufacturerName("samsung")
                .byName("galaxy")));
    }



    public void displayDeviceList(List<Device> deviceList)
    {
        for(Device device : deviceList)
        {
            displayDevice(device);
        }
    }

    public void displayDevice(Device device)
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainView.class.getResource("DeviceLayout.fxml"));
            AnchorPane devicePane = loader.load();

            deviceListVBox.getChildren().add(devicePane);

            DeviceController controller = loader.getController();
            controller.loadDevice(device);
            controller.setMainApp(this.mainApp);


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