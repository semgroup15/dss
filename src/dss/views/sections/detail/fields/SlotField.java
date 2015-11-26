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

public class SlotField extends Widget implements Initializable, DeviceBinding {

    @FXML
    MultipleSelector<Device.Memory.Item> selector;

    @FXML
    public void initialize(URL location, ResourceBundle resourceBundle) {
        selector.setItems(Device.Memory.Item.ALL);
    }

    public Set<Device.Memory.Item> getValue() {
        return selector.getSelectedItems();
    }

    public void setValue(Set<Device.Memory.Item> items) {
        selector.setSelectedItems(items);
    }

    @Override
    public void syncToDevice(Device device) {
        device.memory.set(getValue());
    }

    @Override
    public void syncFromDevice(Device device) {
        setValue(device.memory.get());
    }
}
