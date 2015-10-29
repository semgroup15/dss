package dss.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DeviceController {

	/* * *
	 * View elements must be defined with the @FXML tag to be accessed
	 */
    @FXML
    private Label testLabel;

    @FXML
    private TextField searchBar;
    @FXML
    private Button searchButton;

    // Reference to the main view.
	private MainView mainApp;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	// Example for defining button behavior
    	searchButton.setOnAction(new EventHandler<ActionEvent>(){
    	    @Override public void handle(ActionEvent e) {
    	    	testLabel.setText(searchBar.getText());
    	    }
    	});
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