package dss.views.sections.listing;

import dss.models.device.Device;
import dss.views.State;
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

    // Controls
    ImageView image = new ImageView();
    Label manufacturer = new Label();
    Label name = new Label();
    Label stars = new Label();
    Button add = new Button("Add");
    Button remove = new Button("Remove");
    Button view = new Button("View");

    /**
     * Initialize {@code ListingCell}.
     */
    public ListingCell() {
        super();

        image.setFitWidth(IMAGE_WIDTH);
        image.setFitHeight(IMAGE_HEIGHT);

        BorderPane graphic = new BorderPane();

        VBox center = new VBox();
        center.getChildren().add(name);
        center.getChildren().add(manufacturer);

        HBox right = new HBox();
        right.getChildren().add(stars);
        right.getChildren().add(add);
        right.getChildren().add(remove);
        right.getChildren().add(view);

        graphic.setLeft(image);
        graphic.setCenter(center);
        graphic.setRight(right);

        setGraphic(graphic);

        // Connect actions
        add.setOnAction((event) -> State.get().addDevice(getItem()));
        remove.setOnAction((event) -> State.get().removeDevice(getItem()));
        view.setOnAction((event) -> State.get().setLocation(
                new State.Location(State.Location.Section.DETAIL, getItem())));

        State.get().addSelectionListener(this);
    }

    private void setSelected(boolean selected) {
        add.setDisable(selected);
        remove.setDisable(!selected);
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
