package dss.views.sections.detail.fields;

import dss.models.device.Device;
import dss.views.Widget;
import dss.views.sections.detail.fields.base.DeviceBinding;
import dss.views.sections.detail.fields.base.MultipleSelector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class NetworkField extends Widget
        implements Initializable, DeviceBinding {

    @FXML
    MultipleSelector<Device.Network.Item> selector;

    @FXML
    public void initialize(URL location, ResourceBundle resourceBundle) {
        selector.setItems(Device.Network.Item.ALL);
    }

    public Set<Device.Network.Item> getValue() {
        return selector.getSelectedItems();
    }

    public void setValue(Set<Device.Network.Item> items) {
        selector.setSelectedItems(items);
    }

    @Override
    public void syncToDevice(Device device) {
    }

    @Override
    public void syncFromDevice(Device device) {
    }
}
