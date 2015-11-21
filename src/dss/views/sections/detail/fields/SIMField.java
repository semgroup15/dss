package dss.views.sections.detail.fields;

import dss.models.device.Device;
import dss.views.Widget;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class SIMField extends Widget implements Initializable {

    @FXML
    ComboBox<Device.SIMType> combo;

    @FXML
    public void initialize(URL location, ResourceBundle resourceBundle) {
        combo.setItems(FXCollections.observableArrayList(Device.SIMType.ALL));
    }
}
