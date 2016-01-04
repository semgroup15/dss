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
public class BodyField extends Widget implements DeviceBinding {

    @FXML
    UnitTextField width;

    @FXML
    UnitTextField height;

    @FXML
    UnitTextField depth;

    @Override
    public void syncToDevice(Device device) {
        try {
            device.body.width = Double.valueOf(width.getValue());
        } catch (NumberFormatException exception) {
            // Invalid value
        }

        try {
            device.body.height = Double.valueOf(height.getValue());
        } catch (NumberFormatException exception) {
            // Invalid value
        }

        try {
            device.body.depth = Double.valueOf(depth.getValue());
        } catch (NumberFormatException exception) {
            // Invalid value
        }
    }

    @Override
    public void syncFromDevice(Device device) {
        if (device.body.width > 0) {
            width.setValue(device.body.width + "");
        } else {
            width.setValue("");
        }

        if (device.body.height > 0) {
            height.setValue(device.body.height + "");
        } else {
            height.setValue("");
        }

        if (device.body.depth > 0) {
            depth.setValue(device.body.depth + "");
        } else {
            depth.setValue("");
        }
    }
}
