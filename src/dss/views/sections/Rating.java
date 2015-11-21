package dss.views.sections;

import dss.views.Widget;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Rating extends Widget implements Initializable {

    private static final int SIZE = 5;

    @FXML
    VBox container;

    @FXML
    Label label;

    @FXML
    HBox stars;

    private int value;

    private static class StarLabel extends Label {

        private static final String STYLE_CLASS = "star";
        private static final String STYLE_CLASS_ACTIVE = "active";

        public StarLabel(Rating rating, int value) {
            super("★");

            this.getStyleClass().add(STYLE_CLASS);

            this.setOnMouseEntered((event) -> rating.showValue(value));
            this.setOnMouseExited((event) -> rating.showValue());
            this.setOnMouseClicked((event) -> rating.setValue(value));
        }

        public void setActive(boolean active) {
            if (active) {
                this.getStyleClass().add(STYLE_CLASS_ACTIVE);
            } else {
                this.getStyleClass().remove(STYLE_CLASS_ACTIVE);
            }
        }
    }

    @FXML
    public void initialize(URL location, ResourceBundle resourceBundle) {
        for (int i = 0; i < SIZE; i++) {
            StarLabel label = new StarLabel(this, i);
            stars.getChildren().add(label);
        }

        this.showValue();
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void showValue() {
        showValue(this.value);
    }

    public void showValue(int value) {
        ObservableList<Node> stars = this.stars.getChildren();

        if (value > stars.size()) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < stars.size(); i++) {
            StarLabel star = (StarLabel) stars.get(i);
            star.setActive(false);
            if (i <= value) {
                star.setActive(true);
            }
        }
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

    public ObjectProperty<Pos> starsAlignmentProperty() {
        return stars.alignmentProperty();
    }

    public Pos getStarsAlignment() {
        return stars.getAlignment();
    }

    public void setStarsAlignment(Pos alignment) {
        stars.setAlignment(alignment);
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
}
