package dss.views.sections.listing.selection;

import dss.models.device.Device;
import dss.views.base.State;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * {@code ListCell} displaying a selected device.
 */
public class SelectionCell extends ListCell<Device> {

    BorderPane graphic = new BorderPane();

    // Controls
    Label manufacturer = new Label();
    Label name = new Label();
    Button remove = new Button("Deselect".toUpperCase());

    /**
     * Initialize {@code ListingCell}.
     */
    public SelectionCell() {
        super();

        remove.getStyleClass().add("remove");

        VBox center = new VBox();
        center.getChildren().add(name);
        center.getChildren().add(manufacturer);

        graphic.setCenter(center);
        graphic.setRight(remove);

        setGraphic(graphic);

        // Connect actions
        remove.setOnAction((event) -> State.get().removeDevice(getItem()));
    }

    @Override
    protected void updateItem(Device device, boolean empty) {
        super.updateItem(device, empty);

        graphic.setVisible(!empty);

        if (device != null) {
            manufacturer.setText(device.getManufacturer().name);
            name.setText(device.name);
        }
    }
}
