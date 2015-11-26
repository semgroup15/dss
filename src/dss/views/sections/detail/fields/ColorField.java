package dss.views.sections.detail.fields;

import dss.models.device.Device;
import dss.views.base.Widget;
import dss.views.sections.detail.fields.base.DeviceBinding;
import dss.views.sections.detail.fields.base.MultipleSelector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class ColorField extends Widget implements Initializable, DeviceBinding {

    @FXML
    MultipleSelector<Device.Color.Item> selector;

    @FXML
    public void initialize(URL location, ResourceBundle resourceBundle) {
        selector.setItems(Device.Color.Item.ALL);
    }

    public Set<Device.Color.Item> getValue() {
        return selector.getSelectedItems();
    }

    public void setValue(Set<Device.Color.Item> items) {
        selector.setSelectedItems(items);
    }

    @Override
    public void syncToDevice(Device device) {
        device.color.set(getValue());
    }

    @Override
    public void syncFromDevice(Device device) {
        setValue(device.color.get());
    }
}
