package dss.views.sections.listing;

import dss.models.device.Device;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ListingCell extends ListCell<Device> {

    private static final double IMAGE_WIDTH = 70;
    private static final double IMAGE_HEIGHT = 96;

    ImageView image = new ImageView();
    Label manufacturer = new Label();
    Label name = new Label();
    Label stars = new Label();

    public ListingCell() {
        super();

        image.setFitWidth(IMAGE_WIDTH);
        image.setFitHeight(IMAGE_HEIGHT);

        BorderPane graphic = new BorderPane();

        VBox center = new VBox();
        center.getChildren().add(name);
        center.getChildren().add(manufacturer);

        graphic.setLeft(image);
        graphic.setCenter(center);
        graphic.setRight(stars);

        setGraphic(graphic);
    }

    @Override
    protected void updateItem(Device device, boolean empty) {
        super.updateItem(device, empty);
        if (device != null) {
            try {
                image.setImage(new Image(
                        new FileInputStream(device.getImageFile())));
            } catch (FileNotFoundException exception) {
                // Image not found
            }

            manufacturer.setText(device.getManufacturer().name);
            name.setText(device.name);
            stars.setText("★★★★★");
        }
    }
}
