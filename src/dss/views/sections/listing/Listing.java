package dss.views.sections.listing;

import dss.models.device.Device;
import dss.views.State;
import dss.views.Widget;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Listing extends Widget
        implements Initializable, State.SearchListener {

    @FXML
    public void initialize(URL location, ResourceBundle resourceBundle) {
        State.get().addSearchListener(this);
    }

    @Override
    public void onSearchChange(State.Criteria criteria) {
        Device.QueryBuilder query = criteria.asDeviceQuery();
        if (query.isValid()) {
            List<Device> devices = Device.manager.select(query);
            System.out.println(String.format("%d devices", devices.size()));
        }
    }
}
