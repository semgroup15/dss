package dss.views.sections;

import dss.views.Widget;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Rating extends Widget implements Initializable {

    @FXML
    VBox container;

    @FXML
    Label label;

    @FXML
    Label stars;

    @FXML
    public void initialize(URL location, ResourceBundle resourceBundle) {
        stars.setText("★★★★★");
    }

    public BooleanProperty labelVisibleProperty() {
        return label.visibleProperty();
    }

    public boolean isLabelVisible() {
        return label.isVisible();
    }

    public void setLabelVisible(boolean visible) {
        label.setVisible(visible);
    }

    public BooleanProperty labelManagedProperty() {
        return label.visibleProperty();
    }

    public boolean isLabelManaged() {
        return label.isManaged();
    }

    public void setLabelManaged(boolean managed) {
        label.setManaged(managed);
    }

    public ObjectProperty<Pos> alignmentProperty() {
        return container.alignmentProperty();
    }

    public Pos getAlignment() {
        return container.getAlignment();
    }

    public void setAlignment(Pos alignment) {
        container.setAlignment(alignment);
    }

    public StringProperty labelProperty() {
        return label.textProperty();
    }

    public String getLabel() {
        return label.getText();
    }

    public void setLabel(String text) {
        label.setText(text);
    }

    public StringProperty starsProperty() {
        return stars.textProperty();
    }

    public String getStars() {
        return stars.getText();
    }

    public void setStars(String text) {
        stars.setText(text);
    }
}
