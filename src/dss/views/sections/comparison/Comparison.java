package dss.views.sections.comparison;

import dss.models.device.Device;
import dss.views.State;
import dss.views.Widget;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class Comparison extends Widget
        implements Initializable, State.SelectionListener {

    @FXML
    ListView<Device> list;

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
                return new ComparisonCell();
            }
        });

        update();
    }

    private void update() {
        list.setItems(devices);
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
}
