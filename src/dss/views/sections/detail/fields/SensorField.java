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

public class SensorField extends Widget
        implements Initializable, DeviceBinding {

    @FXML
    MultipleSelector<Device.Sensor.Item> selector;

    @FXML
    public void initialize(URL location, ResourceBundle resourceBundle) {
        selector.setItems(Device.Sensor.Item.ALL);
    }

    public Set<Device.Sensor.Item> getValue() {
        return selector.getSelectedItems();
    }

    public void setValue(Set<Device.Sensor.Item> items) {
        selector.setSelectedItems(items);
    }

    @Override
    public void syncToDevice(Device device) {
        device.sensor.set(getValue());
    }

    @Override
    public void syncFromDevice(Device device) {
        setValue(device.sensor.get());
    }
}
