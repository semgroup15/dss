package dss.views.sections.detail.fields;

import dss.Developer;
import dss.models.device.Device;
import dss.views.base.Widget;
import dss.views.sections.detail.fields.base.DeviceBinding;
import dss.views.sections.detail.fields.base.UnitTextField;
import javafx.fxml.FXML;

@Developer({
    Developer.Value.JONATAN,
    Developer.Value.AHMED,
    Developer.Value.TRIXIE,
})
public class CameraField extends Widget implements DeviceBinding {

    @FXML
    UnitTextField mp;

    @FXML
    UnitTextField width;

    @FXML
    UnitTextField height;

    @Override
    public void syncToDevice(Device device) {
        try {
            device.camera.primary.mp = Integer.valueOf(mp.getValue());
        } catch (NumberFormatException exception) {
            // Invalid value
        }

        try {
            device.camera.primary.width = Integer.valueOf(width.getValue());
        } catch (NumberFormatException exception) {
            // Invalid value
        }

        try {
            device.camera.primary.height = Integer.valueOf(height.getValue());
        } catch (NumberFormatException exception) {
            // Invalid value
        }
    }

    @Override
    public void syncFromDevice(Device device) {
        if (device.camera.primary.mp > 0) {
            mp.setValue(device.camera.primary.mp + "");
        } else {
            mp.setValue("");
        }

        if (device.camera.primary.width > 0) {
            width.setValue(device.camera.primary.width + "");
        } else {
            width.setValue("");
        }

        if (device.camera.primary.height > 0) {
            height.setValue(device.camera.primary.height + "");
        } else {
            height.setValue("");
        }
    }
}
