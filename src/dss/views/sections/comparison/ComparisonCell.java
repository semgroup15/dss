package dss.views.sections.comparison;

import dss.models.device.Device;
import dss.views.sections.Rating;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ComparisonCell extends ListCell<Device> {

    VBox graphic = new VBox();

    // Image size
    private static final double IMAGE_WIDTH = 140;
    private static final double IMAGE_HEIGHT = 192;

    // Description
    ImageView image = new ImageView();
    Label manufacturer = new Label();
    Label name = new Label();

    // Ratings
    Rating overallRating = new Rating();
    Rating responsivenessRating = new Rating();
    Rating screenRating = new Rating();
    Rating batteryRating = new Rating();

    /**
     * Initialize {@code ComparisonCell}.
     */
    public ComparisonCell() {
        super();

        graphic.setAlignment(Pos.TOP_CENTER);

        /*
         * Image
         */
        image.setFitWidth(IMAGE_WIDTH);
        image.setFitHeight(IMAGE_HEIGHT);

        /*
         * Content
         */
        manufacturer.getStyleClass().add("manufacturer");
        name.getStyleClass().add("name");

        /*
         * Ratings
         */
        overallRating.setLabel("Overall");
        overallRating.setAlignment(Pos.CENTER);
        overallRating.setStarsAlignment(Pos.CENTER);

        responsivenessRating.setLabel("Responsiveness");
        responsivenessRating.setAlignment(Pos.CENTER);
        responsivenessRating.setStarsAlignment(Pos.CENTER);

        screenRating.setLabel("Screen");
        screenRating.setAlignment(Pos.CENTER);
        screenRating.setStarsAlignment(Pos.CENTER);

        batteryRating.setLabel("Battery");
        batteryRating.setAlignment(Pos.CENTER);
        batteryRating.setStarsAlignment(Pos.CENTER);

        /*
         * Add to graphic
         */
        ObservableList<Node> children = graphic.getChildren();

        children.add(image);

        children.add(manufacturer);
        children.add(name);

        children.add(overallRating);
        children.add(responsivenessRating);
        children.add(screenRating);
        children.add(batteryRating);

        setGraphic(graphic);
    }

    @Override
    protected void updateItem(Device device, boolean empty) {
        super.updateItem(device, empty);

        graphic.setVisible(!empty);

        if (device != null) {
            setDevice(device);
        }
    }

    private void setDevice(Device device) {
        File file = device.getImageFile();

        if (file == null) {
            image.setVisible(false);
            image.setManaged(false);
        } else {
            image.setVisible(true);
            image.setManaged(true);

            try {
                image.setImage(new Image(new FileInputStream(file)));
            } catch (FileNotFoundException exception) {
                // Image not found
            }
        }

        manufacturer.setText(device.getManufacturer().name);
        name.setText(device.name);

        overallRating.setValue(device.overallRating);
        responsivenessRating.setValue(device.responsivenessRating);
        screenRating.setValue(device.screenRating);
        batteryRating.setValue(device.batteryRating);
    }
}
