package dss.views.sections.detail.fields.base;

import dss.Developer;
import dss.views.base.Widget;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

@Developer({
    Developer.Value.SEBASTIAN,
})
public class GroupLabel extends Widget {

    @FXML
    Label text;

    public StringProperty textProperty() {
        return text.textProperty();
    }

    public String getText() {
        return text.getText();
    }

    public void setText(String text) {
        this.text.setText(text);
    }
}
