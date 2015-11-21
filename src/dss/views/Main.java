package dss.views;

import dss.views.root.Root;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    private static final String TITLE = "DSS";
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 600;
    private static final String STYLES = "styles.css";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(STYLES);

        stage.setScene(scene);
        stage.sizeToScene();
        stage.setWidth(WIDTH);
        stage.setMinWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.setMinHeight(HEIGHT);
        stage.setTitle(TITLE);
        stage.show();
        stage.requestFocus();

        root.setCenter(new Root());
    }
}
