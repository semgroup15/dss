package dss.views;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import dss.models.device.Device;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

public class ComparisonRootController {

	@FXML private ScrollPane deviceColumnScrollPane;

	//

    // Reference to the main views.
	private MainView mainApp;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    private FXMLLoader loader;
    private HashMap<Device,DeviceController> columnControllers;

    @FXML
    public void initialize() {
        loader = new FXMLLoader(MainView.class.getResource("ComparisonLayout.fxml"));
    }

    public void displayComparison(List<Device> deviceList)
    {
    	// TODO - clear children
    	// We always want three comparison columns so an empty
    	// one is generated if we don't have enough devices
        for(int i = 0; i < 3; i++)
        {
        	if(deviceList.size() < i)
        		displayColumn(deviceList.get(i));
        	else
        		displayColumn(null);
        }
    }

    public void displayColumn(Device device)
    {
    	System.out.println("it worked!");
    	/*
        try {
            AnchorPane devicePane = loader.load();
            // TODO - structure this to scale correctly with 2~4 devices
            deviceColumnAnchorPane.getChildren().add(devicePane);

            ComparisonColumnController controller = loader.getController();
            controller.setMainApp(this.mainApp);//This one
            controller.loadDevice(device);

            loader.setRoot(null);
            loader.setController(null);

        } catch (IOException e) {
            e.printStackTrace();
        }
        */
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
