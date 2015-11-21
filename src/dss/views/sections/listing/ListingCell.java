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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
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
    Button delete = new Button("×");

    /**
     * Initialize {@code ListingCell}.
     */
    public ListingCell() {
        super();

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
        overallRating.setLabelVisible(false);
        overallRating.setLabelManaged(false);

        VBox center = new VBox();
        center.getChildren().add(manufacturer);
        center.getChildren().add(name);
        center.getChildren().add(overallRating);

        /*
         * Actions
         */
        add.getStyleClass().add("add");
        remove.getStyleClass().add("remove");
        delete.getStyleClass().add("delete");

        HBox separator = new HBox();
        separator.setPrefWidth(6);

        HBox right = new HBox();
        right.getChildren().add(add);
        right.getChildren().add(remove);
        right.getChildren().add(separator);
        right.getChildren().add(delete);

        // Add to panel
        graphic.setLeft(image);
        graphic.setCenter(center);
        graphic.setRight(right);

        setGraphic(graphic);

        // Connect actions
        add.setOnAction((event) -> State.get().addDevice(getItem()));
        remove.setOnAction((event) -> State.get().removeDevice(getItem()));
        delete.setOnAction((event) -> {
            // Delete device
            getItem().delete();

            // Refresh list
            State state = State.get();
            state.setCriteria(state.getCriteria());
        });

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
