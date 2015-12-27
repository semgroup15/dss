package dss.views.base;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;

/**
 * Base {@code Widget} assumed to have a corresponding {@code .fxml} layout
 * file with the same name.
 */
public class Widget extends BorderPane {

    /**
     * Initialize {@code Widget}.
     */
    public Widget() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getLayoutResource());
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        getStyleClass().add(getClass().getSimpleName());
    }

    /**
     * Get layout {@code URL}.
     *
     * @return Layout resource
     */
    private URL getLayoutResource() {
        Class<?> type = getClass();

        // Get path to resource
        String path = String.format("%s.fxml", type.getSimpleName());

        // Find resource
        URL resource = type.getResource(path);
        if (resource == null) {
            throw new RuntimeException(String.format(
                    "Layout file '%s' not found for view '%s'",
                    path, type.getName()));
        }

        return resource;
    }
}
