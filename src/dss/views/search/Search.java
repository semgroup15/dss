package dss.views.search;

import dss.models.device.Device;
import dss.models.manufacturer.Manufacturer;
import dss.views.base.State;
import dss.views.base.Widget;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Search bar with manufacturer {@code ComboBox} and query {@code TextField}.
 */
public class Search extends Widget implements Initializable {

    @FXML
    Label label;

    @FXML
    ComboBox<Manufacturer> manufacturer;

    @FXML
    TextField query;

    @FXML
    public void initialize(URL location, ResourceBundle resourceBundle) {
        // Get common manufacturer list
        List<Manufacturer> manufacturers =
                Manufacturer.manager.select(Manufacturer.SELECT_COMMON);
        manufacturers.add(0, Manufacturer.NONE);

        // Populate combo box
        manufacturer.setItems(FXCollections.observableArrayList(manufacturers));
    }

    @FXML
    private void onGoHome() {
        State.get().setLocation(
                new State.Location(State.Location.Section.LISTING));
    }

    @FXML
    private void onSearch() {
        State state = State.get();

        String query = this.query.getText();
        Manufacturer manufacturer = this.manufacturer.getValue();
        if (manufacturer == Manufacturer.NONE) {
            manufacturer = null;
        }

        // Update criteria with new values
        State.Criteria criteria = state.getCriteria();
        criteria.setQuery(query);
        criteria.setManufacturer(manufacturer);
        state.setCriteria(criteria);

        // Change to listing
        state.setLocation(new State.Location(State.Location.Section.LISTING));
    }

    @FXML
    private void onCreate() {
        Manufacturer manufacturer = this.manufacturer.getValue();
        String name = query.getText();

        // Require manufacturer and name
        if (manufacturer == null || name.isEmpty()) {
            return;
        }

        // Create device
        Device device = new Device();
        device.id = Device.newId();
        device.name = name;
        device.manufacturerId = manufacturer.id;
        device.save();

        // Refresh list
        State state = State.get();
        state.setCriteria(state.getCriteria());
    }
}
