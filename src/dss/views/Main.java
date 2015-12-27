package dss.views;

import dss.views.root.Root;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    private static final String TITLE = "DSS";
    private static final int WIDTH = 1020;
    private static final int HEIGHT = 620;

    private static final String STYLES = "styles.css";

    private static final String[] FONTS = new String[]{
            "Karla-Bold.ttf",
            "Karla-BoldItalic.ttf",
            "Karla-Italic.ttf",
            "Karla-Regular.ttf",
    };
    private static final int[] FONT_SIZES =
            new int[]{8, 10, 12, 14, 16, 18, 20, 24};

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        for (String font : FONTS) {
            for (int fontSize : FONT_SIZES) {
                Font.loadFont(font, fontSize);
            }
        }

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

        root.setCenter(new Root());

        stage.show();
        stage.requestFocus();
    }
}
