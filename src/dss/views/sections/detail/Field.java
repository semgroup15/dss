package dss.views.sections.detail;

import dss.views.Widget;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Field extends Widget {

    @FXML
    Label label;

    @FXML
    Label value;

    public StringProperty labelProperty() {
        return label.textProperty();
    }

    public String getLabel() {
        return label.getText();
    }

    public void setLabel(String text) {
        label.setText(text);
    }

    public StringProperty valueProperty() {
        return value.textProperty();
    }

    public String getValue() {
        return value.getText();
    }

    public void setValue(String text) {
        value.setText(text);
    }
}
