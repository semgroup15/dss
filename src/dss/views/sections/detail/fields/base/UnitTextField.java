package dss.views.sections.detail.fields.base;

import dss.Developer;
import dss.views.base.Widget;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

@Developer({
    Developer.Value.JONATAN,
})
public class UnitTextField extends Widget {

    @FXML
    TextField value;

    @FXML
    Label unit;

    public StringProperty valueProperty() {
        return value.textProperty();
    }

    public String getValue() {
        return value.getText();
    }

    public void setValue(String text) {
        value.setText(text);
    }

    public StringProperty valuePromptText() {
        return value.promptTextProperty();
    }

    public String getValuePromptText() {
        return value.getPromptText();
    }

    public void setValuePromptText(String text) {
        value.setPromptText(text);
    }

    public StringProperty unitProperty() {
        return unit.textProperty();
    }

    public String getUnit() {
        return unit.getText();
    }

    public void setUnit(String text) {
        unit.setText(text);
    }
}
