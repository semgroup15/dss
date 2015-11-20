package dss.views;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import dss.models.device.Device;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class ComparisonRootController {

	@FXML private ScrollPane deviceColumnScrollPane;
	@FXML private HBox deviceHBox;

    // Reference to the main views.
	private MainView mainApp;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    private FXMLLoader loader;
    private HashMap<Device,DetailsController> columnControllers;

    @FXML
    public void initialize() {
        loader = new FXMLLoader(MainView.class.getResource("ComparisonColumn.fxml"));
    }

    public void displayComparison(List<Device> deviceList)
    {
    	// TODO - clear children
    	// We always want three comparison columns so an empty
    	// one is generated if we don't have enough devices
    	deviceHBox.getChildren().clear();
        for(int i = 0; i < deviceList.size(); i++)
        {
    		displayColumn(deviceList.get(i));
        }
    }

    public void displayColumn(Device device)
    {
        try {
            ScrollPane devicePane = loader.load();
            deviceHBox.getChildren().add(devicePane);

            DetailsController controller = loader.getController();
            controller.setMainApp(this.mainApp);
            if(device != null)
            	controller.displayDevice(device);
            else
            	System.out.println("Device is null");

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
