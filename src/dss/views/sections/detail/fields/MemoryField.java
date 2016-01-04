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
public class MemoryField extends Widget implements DeviceBinding {

    @FXML
    UnitTextField ram;

    @FXML
    UnitTextField internal;

    @Override
    public void syncToDevice(Device device) {
        try {
            device.memory.ramSize = Long.valueOf(ram.getValue());
        } catch (NumberFormatException exception) {
            // Invalid value
        }

        try {
            device.memory.internalSize = Long.valueOf(internal.getValue());
        } catch (NumberFormatException exception) {
            // Invalid value
        }
    }

    @Override
    public void syncFromDevice(Device device) {
        if (device.memory.ramSize > 0) {
            ram.setValue(device.memory.ramSize + "");
        } else {
            ram.setValue("");
        }

        if (device.memory.internalSize > 0) {
            internal.setValue(device.memory.internalSize + "");
        } else {
            internal.setValue("");
        }
    }
}
