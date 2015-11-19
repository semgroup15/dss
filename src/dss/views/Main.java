package dss.views;

import dss.views.root.Root;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static final String TITLE = "DSS";
    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;
    private static final String STYLES = "styles.css";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Group group = new Group();
        Scene scene = new Scene(group);
        scene.getStylesheets().add(STYLES);

        stage.setScene(scene);
        stage.sizeToScene();
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.setTitle(TITLE);
        stage.show();
        stage.requestFocus();

        group.getChildren().add(new Root());
    }
}
