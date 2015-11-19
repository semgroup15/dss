package dss.views.search;

import dss.models.manufacturer.Manufacturer;
import dss.views.State;
import dss.views.Widget;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Search extends Widget implements Initializable {

    @FXML
    ComboBox<Manufacturer> manufacturer;

    @FXML
    TextField query;

    @FXML
    public void initialize(URL location, ResourceBundle resourceBundle) {
        // Get common manufacturer list
        List<Manufacturer> manufacturers =
                Manufacturer.manager.select(Manufacturer.SELECT_COMMON);

        // Populate combo box
        manufacturer.setItems(FXCollections.observableArrayList(manufacturers));
    }

    @FXML
    private void onSearch() {
        State state = State.get();

        // Get current search criteria
        State.Criteria criteria = state.getCriteria();

        // Update criteria with new values
        criteria.setQuery(query.getText());
        criteria.setManufacturer(manufacturer.getValue());

        // Change to listing and perform search
        state.setLocation(new State.Location(State.Location.Section.LISTING));
        state.setCriteria(criteria);
    }
}
