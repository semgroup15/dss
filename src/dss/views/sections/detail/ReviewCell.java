package dss.views.sections.detail;

import dss.Developer;
import dss.models.base.Model;
import dss.models.review.Review;
import dss.views.base.State;
import dss.views.base.components.Rating;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

@Developer({
    Developer.Value.JONATAN,
    Developer.Value.AHMED,
    Developer.Value.ISAR,
})
public class ReviewCell extends ListCell<Review>
    implements
        State.LevelListener,
        Model.Observer.Listener<Review> {

    BorderPane graphic = new BorderPane();

    // Text
    Label text = new Label();

    // Ratings
    Rating responsivenessRating = new Rating();
    Rating screenRating = new Rating();
    Rating batteryRating = new Rating();

    // Actions
    Button delete = new Button("Delete".toUpperCase());

    /**
     * Initialize {@code ReviewCell}.
     */
    public ReviewCell() {
        super();

        // Listen to changes
        Review.observer.addListener(this);

        // Listen to state
        State.get().addLevelListener(this);

        /*
         * Text
         */
        text.getStyleClass().add("text");

        /*
         * Ratings
         */
        responsivenessRating.setLabel("Responsiveness");
        screenRating.setLabel("Screen");
        batteryRating.setLabel("Battery");

        HBox center = new HBox();
        center.getChildren().add(responsivenessRating);
        center.getChildren().add(screenRating);
        center.getChildren().add(batteryRating);

        /*
         * Actions
         */
        delete.getStyleClass().add("delete");
        delete.setOnAction((event) -> getItem().delete());

        /*
         * Add to graphic
         */
        graphic.setCenter(center);
        graphic.setBottom(text);
        graphic.setRight(delete);

        setGraphic(graphic);
    }

    @Override
    public void onModelEvent(Model.Observer.Event event, Review review) {
        if (review == getItem()) {
            switch (event) {
                case UPDATE:
                    setReview(review);
                    break;
            }
        }
    }

    @Override
    protected void updateItem(Review review, boolean empty) {
        super.updateItem(review, empty);

        graphic.setVisible(!empty);

        if (review != null) {
            setReview(review);
        }
    }

    private void setReview(Review review) {
        text.setText(review.text == null ?
                "No comment" : String.format("\"%s\"", review.text));

        responsivenessRating.setValue(review.responsiveness);
        screenRating.setValue(review.screen);
        batteryRating.setValue(review.battery);
    }

    @Override
    public void onLevelChange(State.Level level) {
        switch (level) {
            case ANONYMOUS:
                delete.setVisible(false);
                delete.setManaged(false);
                break;

            case AUTHORIZED:
                delete.setVisible(true);
                delete.setManaged(true);
                break;
        }
    }
}
