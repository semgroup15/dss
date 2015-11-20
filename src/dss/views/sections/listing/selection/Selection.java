package dss.views.sections.listing.selection;

import dss.models.device.Device;
import dss.views.State;
import dss.views.Widget;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class Selection extends Widget
        implements Initializable, State.SelectionListener {

    @FXML
    ListView<Device> list;

    @FXML
    Button button;

    private ObservableList<Device> devices =
            FXCollections.observableArrayList();

    @FXML
    public void initialize(URL location, ResourceBundle resourceBundle) {
        State.get().addSelectionListener(this);
    }

    @Override
    public void onDeviceAdd(Device device) {
        devices.add(device);
        list.setItems(devices);
    }

    @Override
    public void onDeviceRemove(Device device) {
        devices.remove(device);
        list.setItems(devices);
    }

    @FXML
    public void onCompare() {
        State.get().setLocation(
                new State.Location(State.Location.Section.COMPARISON));
    }
}
