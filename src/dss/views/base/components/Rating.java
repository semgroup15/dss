package dss.views.base.components;

import dss.Developer;
import dss.views.base.Widget;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Star rating widget.
 */
@Developer({
    Developer.Value.JONATAN,
})
public class Rating extends Widget implements Initializable {

    /**
     * Change {@code Listener}.
     */
    public interface Listener {
        /**
         * Change rating value.
         *
         * @param value New value
         */
        void onValueChange(int value);
    }

    private static final int SIZE = 5;
    private static final int INITIAL = 1;

    @FXML
    VBox container;

    @FXML
    Label label;

    @FXML
    HBox stars;

    private int value;
    private List<Listener> listeners = new ArrayList<>();

    /**
     * {@code Label} displaying a single star.
     */
    private static class StarLabel extends Label {

        private static final String LABEL = "★";

        private static final String STYLE_CLASS = "star";
        private static final String STYLE_CLASS_ACTIVE = "active";

        /**
         * Initialize {@code StarLabel}.
         *
         * @param rating Parent {@code Rating}
         * @param value  Value of the star
         */
        public StarLabel(Rating rating, int value) {
            super(LABEL);

            this.getStyleClass().add(STYLE_CLASS);

            this.setOnMouseEntered((event) -> rating.showValue(value));
            this.setOnMouseExited((event) -> rating.showValue(rating.value));
            this.setOnMouseClicked((event) -> rating.setValue(value));
        }

        /**
         * Set whether the star is active changing its look.
         *
         * @param active Active
         */
        public void setActive(boolean active) {
            if (active) {
                this.getStyleClass().add(STYLE_CLASS_ACTIVE);
            } else {
                this.getStyleClass().remove(STYLE_CLASS_ACTIVE);
            }
        }
    }

    /**
     * Add {@code Listener}.
     *
     * @param listener Action listener
     */
    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    /**
     * Remove {@code Listener}
     *
     * @param listener Action listener
     */
    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    @FXML
    public void initialize(URL location, ResourceBundle resourceBundle) {
        for (int i = 0; i < SIZE; i++) {
            StarLabel label = new StarLabel(this, i + 1);
            stars.getChildren().add(label);
        }

        this.setValue(INITIAL);
    }

    /**
     * Set value.
     *
     * @param value Value
     */
    public void setValue(int value) {
        this.value = value;
        this.showValue(this.value);

        if (listeners != null) {
            for (Listener listener : listeners) {
                listener.onValueChange(value);
            }
        }
    }

    /**
     * Get value.
     *
     * @return Value
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Show the specified value without changing the real value.
     *
     * @param value Value to show
     */
    public void showValue(int value) {
        ObservableList<Node> stars = this.stars.getChildren();

        if (value > stars.size()) {
            throw new IllegalArgumentException(
                    String.format("Cannot display %d with %d stars",
                            value, stars.size()));
        }

        for (int i = 0; i < stars.size(); i++) {
            StarLabel star = (StarLabel) stars.get(i);
            star.setActive(false);
            if (i < value) {
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
