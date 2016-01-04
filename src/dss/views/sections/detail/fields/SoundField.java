package dss.views.sections.detail.fields;

import dss.Developer;
import dss.models.device.Device;
import dss.views.base.Widget;
import dss.views.sections.detail.fields.base.DeviceBinding;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

@Developer({
    Developer.Value.JONATAN,
    Developer.Value.AHMED,
    Developer.Value.TRIXIE,
})
public class SoundField extends Widget implements DeviceBinding {

    @FXML
    CheckBox loudspeaker;

    @FXML
    CheckBox jack35;

    @Override
    public void syncToDevice(Device device) {
        device.sound.loudspeaker = loudspeaker.isSelected();
        device.sound.jack35 = jack35.isSelected();
    }

    @Override
    public void syncFromDevice(Device device) {
        loudspeaker.setSelected(device.sound.loudspeaker);
        jack35.setSelected(device.sound.jack35);
    }
}
