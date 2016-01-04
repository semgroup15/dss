package dss.views;

import dss.Developer;
import dss.views.root.Root;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Application entry point.
 */
@Developer({
    Developer.Value.SEBASTIAN,
})
public class Main extends Application {

    /*
     * Window
     */
    private static final String TITLE = "DSS";
    private static final int WIDTH = 1020;
    private static final int HEIGHT = 620;

    /*
     * Styling
     */
    private static final String STYLES = "styles.css";
    private static final String[] FONTS = new String[]{
            "Karla-Bold.ttf",
            "Karla-BoldItalic.ttf",
            "Karla-Italic.ttf",
            "Karla-Regular.ttf",
    };
    private static final int[] FONT_SIZES =
            new int[]{8, 10, 12, 14, 16, 18, 20, 24};

    /**
     * {@code main()}
     * @param args
     */
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

        Scene scene = new Scene(new Root());
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
    }
}
