package dss.view;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import dss.view.ExampleController;
import dss.models.device.Device;

public class MainView extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    /**
     * The data as an observable list of Devices.
     * Not necessary in main view. Will be located in the controller classes.
     */
    private ObservableList<Device> smartphoneData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainView() {

    }

    public static void launchApp()
    {

        MainView.launch();
    }

    /**
     * Returns the data as an observable list of Devices.
     * @return
     */
    public ObservableList<Device> getDeviceData() {
        return smartphoneData;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Group 15 Smartphone DSS");

        initLayouts();
        showDeviceListLayout();
    }

    /**
     * Initializes the root layout and hooks it up to the example controller.
     */
    public void initLayouts() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainView.class.getResource("RootLayout.fxml"));
            rootLayout = loader.load(); // Load the top element in the fxml document

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

            // Give the controller access to the main app.
            /**
             * --
             */
            ExampleController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDeviceListLayout() {
        try {
            // Load device overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainView.class.getResource("DeviceListLayout.fxml"));
            // Store the layout gotten from the fxml document
            ScrollPane deviceList = (ScrollPane) loader.load();

            // Sub-components will be placed into RootLayout using this method
            rootLayout.setCenter(deviceList);

            // Give the controller access to the main app.
            DeviceListController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    /*//-- Example of how to add sub-layouts to the root
    public void showSubLayout() {
        try {
            // Load device overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MyLayout.fxml"));
            // Store the layout gotten from the fxml document
            AnchorPane myLayout = (AnchorPane) loader.load();

            // Sub-components will be placed into RootLayout using this method
            rootLayout.setCenter(myLayout);

            // Give the controller access to the main app.
            MyController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
}