package dss.views.sections.detail.fields;

import dss.models.device.Device;
import dss.views.Widget;
import dss.views.sections.detail.fields.base.MultipleSelector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class ComField extends Widget implements Initializable {

    @FXML
    MultipleSelector<Device.Com.Item> selector;

    @FXML
    public void initialize(URL location, ResourceBundle resourceBundle) {
        selector.setItems(Device.Com.Item.ALL);
    }

    public Set<Device.Com.Item> getValue() {
        return selector.getSelectedItems();
    }

    public void setValue(Set<Device.Com.Item> items) {
        selector.setSelectedItems(items);
    }
}
