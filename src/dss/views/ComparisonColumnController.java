package dss.views;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import dss.models.device.Device;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class ComparisonColumnController {

	// TODO - populate columns and allow resizing of the root node for this layout

    // Reference to the main views.
	private MainView mainApp;

	public void loadDevice(Device device)
	{
		if(device == null) return;


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
