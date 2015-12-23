package dss.views.sections.detail;

import dss.models.base.Model;
import dss.models.device.Device;
import dss.models.review.Review;
import dss.views.base.State;
import dss.views.base.Widget;
import dss.views.base.components.Rating;
import dss.views.sections.detail.fields.*;
import dss.views.sections.detail.fields.base.DeviceBinding;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class Detail extends Widget
        implements
            Initializable,
            State.LevelListener,
            Model.Observer.Listener<Review> {

    @FXML
    ImageView image;

    @FXML
    Rating overallRating;

    @FXML
    Rating responsivenessRating;

    @FXML
    Rating screenRating;

    @FXML
    Rating batteryRating;

    @FXML
    NameField name;

    @FXML
    ManufacturerField manufacturer;

    @FXML
    PlatformField platform;

    @FXML
    SIMField sim;

    @FXML
    DisplayField display;

    @FXML
    BodyField body;

    @FXML
    ColorField color;

    @FXML
    BatteryField battery;

    @FXML
    CameraField camera;

    @FXML
    ComField com;

    @FXML
    SensorField sensor;

    @FXML
    MemoryField memory;

    @FXML
    SlotField slot;

    @FXML
    NetworkField network;

    @FXML
    SoundField sound;

    @FXML
    Button save;

    @FXML
    Button delete;

    @FXML
    Label login;

    @FXML
    ListView<Review> reviews;

    @FXML
    VBox review;

    @FXML
    TextArea text;

    @FXML
    Button submit;

    private DeviceBinding[] bindings() {
        return new DeviceBinding[] {
            name,
            manufacturer,
            platform,
            sim,
            display,
            body,
            color,
            battery,
            camera,
            com,
            sensor,
            memory,
            slot,
            network,
            sound
        };
    }

    private Device device;

    public void setDevice(Device device) {
        this.device = device;

        File file = device.getImageFile();

        if (file == null) {
            image.setVisible(false);
            image.setManaged(false);
        } else {
            image.setVisible(true);
            image.setManaged(true);

            try {
                image.setImage(new Image(new FileInputStream(file)));
            } catch (FileNotFoundException exception) {
                // Image not found
            }
        }

        for (DeviceBinding binding : bindings()) {
            binding.syncFromDevice(device);
        }

        overallRating.setValue(device.overallRating);
        responsivenessRating.setValue(device.responsivenessRating);
        screenRating.setValue(device.screenRating);
        batteryRating.setValue(device.batteryRating);

        setReviewVisible(false);

        // Load reviews
        reviews.setItems(FXCollections.observableArrayList(device.getReviews()));
    }

    @FXML
    public void initialize(URL location, ResourceBundle resourceBundle) {
        responsivenessRating.addListener((value) -> setReviewVisible(true));
        screenRating.addListener((value) -> setReviewVisible(true));
        batteryRating.addListener((value) -> setReviewVisible(true));

        // Listen to state
        State.get().addLevelListener(this);

        // Listen to review changes
        Review.observer.addListener(this);

        // Set reviews list cell factory
        reviews.setCellFactory(new Callback<ListView<Review>, ListCell<Review>>() {
            @Override
            public ListCell<Review> call(ListView<Review> reviewListView) {
                return new ReviewCell();
            }
        });
    }

    @Override
    public void onLevelChange(State.Level level) {
        switch (level) {
            case ANONYMOUS:
                save.setVisible(false);
                save.setManaged(false);

                delete.setVisible(false);
                delete.setManaged(false);

                login.setVisible(true);
                login.setManaged(true);

                break;

            case AUTHORIZED:
                save.setVisible(true);
                save.setManaged(true);

                delete.setVisible(true);
                delete.setManaged(true);

                login.setVisible(false);
                login.setManaged(false);

                break;
        }
    }

    @Override
    public void onModelEvent(Model.Observer.Event event, Review review) {
        switch (event) {
            case DELETE:
                reviews.getItems().remove(review);
                break;

            case INSERT:
                if (review.deviceId == device.id) {
                    reviews.getItems().add(review);
                }
                break;
        }
    }

    @FXML
    public void onDelete() {
        // Delete device
        device.delete();

        // Leave detail view
        State state = State.get();
        state.setLocation(new State.Location(State.Location.Section.LISTING));
    }

    @FXML
    public void onSave() {
        for (DeviceBinding binding : bindings()) {
            binding.syncToDevice(device);
        }

        // Save device
        device.save();

        // Leave detail view
        State state = State.get();
        state.setLocation(new State.Location(State.Location.Section.LISTING));
    }

    private void setReviewVisible(boolean visible) {
        review.setVisible(visible);
        review.setManaged(visible);

        text.setText("");

        if (visible) {
            setReviewDone(false);
        }
    }

    private void setReviewDone(boolean done) {
        text.setDisable(done);
        submit.setDisable(done);
        submit.setText(done ? "Submitted" : "Submit");
    }

    @FXML
    public void onSubmit() {
        setReviewDone(true);

        // Create review
        Review review = new Review();
        review.deviceId = device.id;
        review.text = text.getText();
        review.responsiveness = responsivenessRating.getValue();
        review.screen = screenRating.getValue();
        review.battery = batteryRating.getValue();
        review.save();
    }
}
