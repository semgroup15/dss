package dss.views.sections.detail.fields;

import dss.models.device.Device;
import dss.models.manufacturer.Manufacturer;
import dss.views.Widget;
import dss.views.sections.detail.fields.base.DeviceBinding;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ManufacturerField extends Widget
        implements Initializable, DeviceBinding {

    @FXML
    ComboBox<Manufacturer> combo;

    @FXML
    public void initialize(URL location, ResourceBundle resourceBundle) {
        // Get common manufacturer list
        List<Manufacturer> manufacturers =
                Manufacturer.manager.select(Manufacturer.SELECT_COMMON);

        // Populate combo box
        combo.setItems(FXCollections.observableArrayList(manufacturers));
    }

    @Override
    public void syncToDevice(Device device) {
        device.manufacturerId = combo.getValue().id;
    }

    @Override
    public void syncFromDevice(Device device) {
        combo.setValue(device.getManufacturer());
    }
}
