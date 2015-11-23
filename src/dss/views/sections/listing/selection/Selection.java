package dss.views.sections.listing.selection;

import dss.models.device.Device;
import dss.views.State;
import dss.views.Widget;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class Selection extends Widget
        implements Initializable, State.SelectionListener {

    private static final int REQUIRED = 2;

    @FXML
    ListView<Device> list;

    @FXML
    Button button;

    private ObservableList<Device> devices =
            FXCollections.observableArrayList();

    @FXML
    public void initialize(URL location, ResourceBundle resourceBundle) {
        // Listen to selection changes
        State.get().addSelectionListener(this);

        // Configure cell factory
        list.setCellFactory(new Callback<ListView<Device>, ListCell<Device>>() {
            @Override
            public ListCell<Device> call(ListView<Device> deviceListView) {
                return new SelectionCell();
            }
        });

        update();
    }

    private void update() {
        list.setItems(devices);
        button.setDisable(devices == null || devices.size() < REQUIRED);
        if (devices != null) {
            button.setText(String.format("Compare (%d)", devices.size()));
        }
    }

    @Override
    public void onDeviceAdd(Device device) {
        devices.add(device);
        update();
    }

    @Override
    public void onDeviceRemove(Device device) {
        devices.remove(device);
        update();
    }

    @FXML
    public void onCompare() {
        State.get().setLocation(
                new State.Location(State.Location.Section.COMPARISON));
    }
}
