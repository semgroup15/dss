package dss.views;

import dss.models.device.Device;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class DeviceListController {
    // Reference to the main views.
	private MainView mainApp;

    @FXML private URL location;
    @FXML private VBox deviceListVBox;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    private FXMLLoader loader;
    private HashMap<Device,DeviceController> deviceListControllers;

    @FXML
    public void initialize() {
        loader = new FXMLLoader(MainView.class.getResource("DeviceLayout.fxml"));
    }

    public void displayDeviceList(List<Device> deviceList)
    {
        deviceListVBox.getChildren().clear();
        for(Device device : deviceList)
        {
            displayDevice(device);
        }
    }

    public void displayDevice(Device device)
    {
        try {
            AnchorPane devicePane = loader.load();
            deviceListVBox.getChildren().add(devicePane);

            DeviceController controller = loader.getController();
            controller.setMainApp(this.mainApp);//This one
            controller.loadDevice(device);

            loader.setRoot(null);
            loader.setController(null);

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
        this.mainApp = mainApp; //This one first
    }
}