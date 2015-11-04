package dss.view;

import java.util.Collections;
import java.util.List;

import dss.models.device.Device;
import dss.models.manufacturer.Manufacturer;
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
    private ComboBox<Manufacturer> manufacturerComboBox;
    @FXML
    private TextField searchTextField;
    @FXML
    private Button searchButton;
    @FXML
    private StackPane screenSwitch;


	private DetailsController detailsController;
	private DeviceListController deviceListController;

    public void setDetailsController(DetailsController controller) {
        this.detailsController = controller;
    }

    public void setDeviceListController(DeviceListController controller) {
        this.deviceListController = controller;
    }

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
        List<Manufacturer> manufacturers = Manufacturer.manager.select(Manufacturer.SELECT_COMMON);
        manufacturerComboBox.setItems(FXCollections.observableArrayList(manufacturers));

        searchButton.setOnAction(event -> {
            Device.QueryBuilder queryBuilder = new Device.QueryBuilder();

            Manufacturer manufacturer = manufacturerComboBox.getValue();
            if (manufacturer != null) {
                queryBuilder.byManufacturerId(manufacturer.id);
            }

            String query = searchTextField.getText().trim();
            if (query != null && !query.isEmpty()) {
                queryBuilder.byName(query);
            }

            List<Device> devices = Device.manager.select(queryBuilder.limit(10));
            deviceListController.displayDeviceList(devices);
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











