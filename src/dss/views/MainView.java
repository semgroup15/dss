package dss.views;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import dss.models.device.Device;

public class MainView extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    private MainController mainController;

    public static void launchApp()
    {
        MainView.launch();
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Group 15 Smartphone DSS");

        initRootLayout();

        mainController.setDeviceListController(initializeDeviceList());
        mainController.setDetailsController(initializeDetails());

        mainController.setScreen(MainController.Screen.List);
    }

    /**
     * Initializes the root layout and hooks it up to the main controller.
     */
    public void initRootLayout() {
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
            mainController = loader.getController();
            mainController.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DeviceListController initializeDeviceList() {
        try {
            // Load device overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainView.class.getResource("DeviceListLayout.fxml"));
            // Store the layout gotten from the fxml document
            ScrollPane deviceList = (ScrollPane) loader.load();
            DeviceListController controller = loader.getController();
            controller.setMainApp(this);

            // Sub-components will be placed into RootLayout using this method
            mainController.AddPaneToStack((Node)deviceList);
            mainController.setDeviceListController(controller);

            // Give the controller access to the main app.


            return controller;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public DetailsController initializeDetails() {
        try {
            // Load device overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainView.class.getResource("DetailsLayout.fxml"));
            // Store the layout gotten from the fxml document
            ScrollPane deviceList = loader.load();
            DetailsController controller = loader.getController();

            // Sub-components will be placed into RootLayout using this method
            mainController.AddPaneToStack(deviceList);
            mainController.setDetailsController(controller);

            // Give the controller access to the main app.
            controller.setMainApp(this);

            return controller;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ComparisonRootController initializeComparison() {
        try {
            // Load device overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainView.class.getResource("ComparisonLayout.fxml"));
            // Store the layout gotten from the fxml document
            ScrollPane comparisonColumns = loader.load();
            ComparisonRootController controller = loader.getController();

            // Sub-components will be placed into RootLayout using this method
            mainController.AddPaneToStack(comparisonColumns);
            mainController.setComparisonRootController(controller);

            // Give the controller access to the main app.
            controller.setMainApp(this);

            return controller;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public MainController getMainController()
    {
        return this.mainController;
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
