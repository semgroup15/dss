package dss.views.sections.detail.fields;

import dss.models.device.Device;
import dss.views.base.Widget;
import dss.views.sections.detail.fields.base.DeviceBinding;
import dss.views.sections.detail.fields.base.UnitTextField;
import javafx.fxml.FXML;

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
        width.setValue(device.body.width + "");
        height.setValue(device.body.height + "");
        depth.setValue(device.body.depth + "");
    }
}
