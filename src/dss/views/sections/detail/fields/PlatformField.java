package dss.views.sections.detail.fields;

import dss.Developer;
import dss.models.device.Device;
import dss.views.base.Widget;
import dss.views.sections.detail.fields.base.DeviceBinding;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

@Developer({
    Developer.Value.JONATAN,
    Developer.Value.AHMED,
    Developer.Value.TRIXIE,
})
public class PlatformField extends Widget
        implements Initializable, DeviceBinding {

    @FXML
    ComboBox<Device.Platform> combo;

    @FXML
    public void initialize(URL location, ResourceBundle resourceBundle) {
        combo.setItems(FXCollections.observableArrayList(Device.Platform.ALL));
    }

    @Override
    public void syncToDevice(Device device) {
        device.platform = combo.getValue();
    }

    @Override
    public void syncFromDevice(Device device) {
        combo.setValue(device.platform);
    }
}
