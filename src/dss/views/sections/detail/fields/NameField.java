package dss.views.sections.detail.fields;

import dss.models.device.Device;
import dss.views.Widget;
import dss.views.sections.detail.fields.base.DeviceBinding;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class NameField extends Widget implements DeviceBinding {

    @FXML
    TextField text;

    @Override
    public void syncToDevice(Device device) {
    }

    @Override
    public void syncFromDevice(Device device) {
    }
}
