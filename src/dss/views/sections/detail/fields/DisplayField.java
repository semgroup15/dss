package dss.views.sections.detail.fields;

import dss.models.device.Device;
import dss.views.Widget;
import dss.views.sections.detail.fields.base.DeviceBinding;
import dss.views.sections.detail.fields.base.UnitTextField;
import javafx.fxml.FXML;

public class DisplayField extends Widget implements DeviceBinding {

    @FXML
    UnitTextField width;

    @FXML
    UnitTextField height;

    @FXML
    UnitTextField density;

    @Override
    public void syncToDevice(Device device) {
        try {
            device.display.width = Integer.valueOf(width.getValue());
        } catch (NumberFormatException exception) {
            // Invalid value
        }

        try {
            device.display.height = Integer.valueOf(height.getValue());
        } catch (NumberFormatException exception) {
            // Invalid value
        }

        try {
            device.display.density = Integer.valueOf(density.getValue());
        } catch (NumberFormatException exception) {
            // Invalid value
        }
    }

    @Override
    public void syncFromDevice(Device device) {
        width.setValue(device.display.width + "");
        height.setValue(device.display.height + "");
        density.setValue(device.display.density + "");
    }
}
