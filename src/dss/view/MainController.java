package dss.view;

import java.util.Collections;

import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/* * *
 * This class will control the top bar as well as swapping between different views.
 */
public class MainController {

	/* * *
	 * View elements must be defined with the @FXML tag to be accessed
	 */
    @FXML
    private Label testLabel;
    @FXML
    private TextField searchBar;
    @FXML
    private Button searchButton;
    @FXML
    private Button gotoList; // Temp
    @FXML
    private Button gotoDetails; // Temp
    @FXML
    private StackPane screenSwitch;


	private DetailsController detailsController;
	private DeviceListController deviceListController;

    // Reference to the main view.
	private MainView mainApp;

	private Screen currentScreen;

	public void AddPaneToStack(Node p)
	{
		screenSwitch.getChildren().add(p);
	}

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

    	// Temporary for debugging
    	gotoList.setOnAction(new EventHandler<ActionEvent>(){
    	    @Override public void handle(ActionEvent e) {
    	    	setScreen(Screen.List);
    	    }
    	});
    	gotoDetails.setOnAction(new EventHandler<ActionEvent>(){
    	    @Override public void handle(ActionEvent e) {
    	    	setScreen(Screen.Details);
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

    public void setScreen(Screen scr)
    {
    	if(currentScreen == scr) return;

    	ObservableList<Node> children = FXCollections.observableArrayList(screenSwitch.getChildren());
    	for(int i = 0; i < children.size(); i++)
    	{
    		System.out.print(children.get(i).getId() + " - ");
    		System.out.println(scr.toString());
    		boolean check = children.get(i).getId().equals(scr.toString());
    		children.get(i).setVisible(check);
    		children.get(i).setDisable(!check); // May not be necessary, but just in case
    	}
		screenSwitch.getChildren().setAll(children);

    	currentScreen = scr;
    }

    int findIndexOfScreen(Screen scr, ObservableList<Node> col)
    {
    	for(int i = 0; i < col.size(); i++)
    	{
    		// Enum tostring is a total hack I'm so sorry
    		if(col.get(i).getId().equals(scr.toString()))
    			return i;
    	}
    	return 0;
    }

    // Don't look at this please
    public enum Screen {
    	Details ("detailsScrollPane"),
    	List ("deviceListScrollPane"),
    	Comparison ("Mode3");

        private final String name;

        private Screen(String s) {
            name = s;
        }

        public boolean equalsName(String otherName) {
            return (otherName == null) ? false : name.equals(otherName);
        }

        public String toString() {
           return this.name;
        };
    }
}











