package dss.views.sections.detail.fields;

import dss.models.device.Device;
import dss.views.base.Widget;
import dss.views.sections.detail.fields.base.DeviceBinding;
import dss.views.sections.detail.fields.base.UnitTextField;
import javafx.fxml.FXML;

public class BatteryField extends Widget implements DeviceBinding {

    @FXML
    UnitTextField sleep;

    @FXML
    UnitTextField music;

    @FXML
    UnitTextField talk;

    @Override
    public void syncToDevice(Device device) {
        try {
            device.battery.sleep = Integer.valueOf(sleep.getValue());
        } catch (NumberFormatException exception) {
            // Invalid value
        }

        try {
            device.battery.music = Integer.valueOf(music.getValue());
        } catch (NumberFormatException exception) {
            // Invalid value
        }

        try {
            device.battery.talk = Integer.valueOf(talk.getValue());
        } catch (NumberFormatException exception) {
            // Invalid value
        }
    }

    @Override
    public void syncFromDevice(Device device) {
        if (device.battery.sleep > 0) {
            sleep.setValue(device.battery.sleep + "");
        } else {
            sleep.setValue("");
        }

        if (device.battery.music > 0) {
            music.setValue(device.battery.music + "");
        } else {
            music.setValue("");
        }

        if (device.battery.talk > 0) {
            talk.setValue(device.battery.talk + "");
        } else {
            talk.setValue("");
        }
    }
}
