package dss.views.input;

import dss.views.MainController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by jpp on 11/13/15.
 */
public class InputClientMain extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        this.primaryStage.setTitle("Smartphone DSS Administration");

        initRootLayout();

        mainController.setDeviceListController(initializeDeviceList());
        mainController.setDetailsController(initializeDetails());

        mainController.setScreen(MainController.Screen.List);
    }

    public static void launchApp()
    {
        InputClientMain.launch();
    }
}
