package dss.views.sections.listing;

import dss.models.device.Device;
import dss.views.State;
import dss.views.Widget;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Listing extends Widget
        implements Initializable, State.SearchListener {

    @FXML
    ListView<Device> list;

    @FXML
    public void initialize(URL location, ResourceBundle resourceBundle) {
        State.get().addSearchListener(this);

        list.setCellFactory(new Callback<ListView<Device>, ListCell<Device>>() {
            @Override
            public ListCell<Device> call(ListView<Device> deviceListView) {
                return new ListingCell();
            }
        });
    }

    @Override
    public void onSearchChange(State.Criteria criteria) {
        Device.QueryBuilder query = criteria.asDeviceQuery();

        if (query.isValid()) {
            List<Device> devices = Device.manager.select(query);
            list.setItems(FXCollections.observableArrayList(devices));
        }
    }
}
