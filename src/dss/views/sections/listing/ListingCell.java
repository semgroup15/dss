package dss.views.sections.listing;

import dss.models.device.Device;
import dss.views.State;
import dss.views.sections.Rating;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * {@code ListCell} displaying device information.
 */
public class ListingCell extends ListCell<Device>
        implements State.SelectionListener {

    // Image size
    private static final double IMAGE_WIDTH = 70;
    private static final double IMAGE_HEIGHT = 96;

    BorderPane graphic = new BorderPane();

    // Description
    ImageView image = new ImageView();
    Label manufacturer = new Label();
    Label name = new Label();
    Rating overallRating = new Rating();

    // Actions
    Button add = new Button("+");
    Button remove = new Button("−");

    /**
     * Initialize {@code ListingCell}.
     */
    public ListingCell() {
        super();

        image.setFitWidth(IMAGE_WIDTH);
        image.setFitHeight(IMAGE_HEIGHT);

        manufacturer.getStyleClass().add("manufacturer");
        name.getStyleClass().add("name");
        overallRating.setLabelVisible(false);
        overallRating.setLabelManaged(false);

        add.getStyleClass().add("add");
        remove.getStyleClass().add("remove");

        VBox center = new VBox();
        center.getChildren().add(manufacturer);
        center.getChildren().add(name);
        center.getChildren().add(overallRating);

        HBox right = new HBox();
        right.getChildren().add(add);
        right.getChildren().add(remove);

        graphic.setLeft(image);
        graphic.setCenter(center);
        graphic.setRight(right);

        setGraphic(graphic);

        // Connect actions
        add.setOnAction((event) -> State.get().addDevice(getItem()));
        remove.setOnAction((event) -> State.get().removeDevice(getItem()));

        setOnMouseClicked((event) -> State.get().setLocation(
                new State.Location(State.Location.Section.DETAIL, getItem())));

        State.get().addSelectionListener(this);
    }

    private void setSelected(boolean selected) {
        add.setDisable(selected);
        remove.setDisable(!selected);

        add.setVisible(!selected);
        add.setManaged(!selected);

        remove.setVisible(selected);
        remove.setManaged(selected);
    }

    @Override
    protected void updateItem(Device device, boolean empty) {
        super.updateItem(device, empty);

        graphic.setVisible(!empty);

        if (device != null) {
            try {
                image.setImage(new Image(
                        new FileInputStream(device.getImageFile())));
            } catch (FileNotFoundException exception) {
                // Image not found
            }

            manufacturer.setText(device.getManufacturer().name);
            name.setText(device.name);
            overallRating.setValue(device.overallRating);

            setSelected(State.get().getDevices().contains(device));
        }
    }

    @Override
    public void onDeviceAdd(Device device) {
        if (device == getItem()) {
            setSelected(true);
        }
    }

    @Override
    public void onDeviceRemove(Device device) {
        if (device == getItem()) {
            setSelected(false);
        }
    }
}
