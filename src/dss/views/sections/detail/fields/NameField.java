package dss.views.sections.detail.fields;

import dss.Developer;
import dss.models.device.Device;
import dss.views.base.Widget;
import dss.views.sections.detail.fields.base.DeviceBinding;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

@Developer({
    Developer.Value.JONATAN,
    Developer.Value.AHMED,
    Developer.Value.TRIXIE,
})
public class NameField extends Widget implements DeviceBinding {

    @FXML
    TextField text;

    @Override
    public void syncToDevice(Device device) {
        device.name = text.getText();
    }

    @Override
    public void syncFromDevice(Device device) {
        text.setText(device.name);
    }
}
