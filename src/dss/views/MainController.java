package dss.views;

import java.util.ArrayList;
import java.util.List;

import dss.models.device.Device;
import dss.models.manufacturer.Manufacturer;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

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
    @FXML
    private Text comparisonCart;
    @FXML
    private Button compareButton;
    @FXML
    private Button clearButton;

    private DetailsController detailsController;
	private DeviceListController deviceListController;
	private ComparisonRootController comparisonRootController;

	private ArrayList<Device> comparisonDeviceCart = new ArrayList<Device>();

    public void setDetailsController(DetailsController controller) {
        this.detailsController = controller;
    }

    public void setDeviceListController(DeviceListController controller) {
        this.deviceListController = controller;
    }

    public void setComparisonRootController(ComparisonRootController controller) {
        this.comparisonRootController = controller;
    }

    // Reference to the main views.
	private MainView mainApp;

	private Screen currentScreen;

	public void AddPaneToStack(Node p)
	{
		screenSwitch.getChildren().add(p);
	}

	public void addDeviceToComparisonCart(Device device)
	{
		if(comparisonDeviceCart.contains(device)) return;
		if(!canAddToComparisonCart()) return;

		comparisonDeviceCart.add(device);
		if(comparisonDeviceCart.size() > 1)
			comparisonCart.setText(comparisonCart.getText() + ", " + device.name);
		else
			comparisonCart.setText(device.name);
	}

	// Check if more devices can be added
	public boolean canAddToComparisonCart()
	{
		return comparisonDeviceCart.size() < 3;
	}

	// Clears the comparison cart
	public void clearComparisonCart()
	{
		comparisonDeviceCart.clear();
		comparisonCart.setText("");
	}

	public void compareDevicesInCart()
	{
		// We want at least two devices to compare
		if(comparisonDeviceCart.size() < 2) return;

		comparisonRootController.displayComparison(comparisonDeviceCart);
		clearComparisonCart();
		setScreen(Screen.Comparison);
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
        	this.setScreen(MainController.Screen.List);

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

        searchButton.setOnAction(event -> {
        	this.setScreen(MainController.Screen.List);

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

        compareButton.setOnAction(event -> { this.compareDevicesInCart(); });

        clearButton.setOnAction(event -> { this.clearComparisonCart(); });
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

    public DetailsController getDetailsController() {
        return detailsController;
    }

    public DeviceListController getDeviceListController() {
        return deviceListController;
    }

    // Don't look at this please
    public enum Screen {
    	Details ("detailsScrollPane"),
    	List ("deviceListScrollPane"),
    	Comparison ("comparisonScrollPane");

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











