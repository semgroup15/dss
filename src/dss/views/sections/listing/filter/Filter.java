package dss.views.sections.listing.filter;

import dss.models.device.Device;
import dss.views.State;
import dss.views.Widget;
import dss.views.sections.Rating;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Filter extends Widget implements Initializable {

    @FXML
    ComboBox<Device.Platform> platform;

    @FXML
    Rating responsivenessRating;

    @FXML
    Rating screenRating;

    @FXML
    Rating batteryRating;

    @FXML
    TextField width;

    @FXML
    TextField height;

    @FXML
    TextField depth;

    @FXML
    public void initialize(URL location, ResourceBundle resourceBundle) {
        platform.setItems(
                FXCollections.observableArrayList(Device.Platform.ALL));

        /*
         * All criteria changes consist of 4 steps
         *
         * 1. Get global application state
         * 2. Get current filtering criteria
         * 3. Update criteria values
         * 4. Publish criteria to global state
         */

        platform.setOnAction((event) -> {
            State state = State.get();
            State.Criteria criteria = state.getCriteria();
            criteria.setPlatform(platform.getValue());
            state.setCriteria(criteria);
        });

        responsivenessRating.addListener((value) -> {
            State state = State.get();
            State.Criteria criteria = state.getCriteria();
            criteria.setResponsivenessRating(value);
            state.setCriteria(criteria);
        });

        screenRating.addListener((value) -> {
            State state = State.get();
            State.Criteria criteria = state.getCriteria();
            criteria.setScreenRating(value);
            state.setCriteria(criteria);
        });

        batteryRating.addListener((value) -> {
            State state = State.get();
            State.Criteria criteria = state.getCriteria();
            criteria.setBatteryRating(value);
            state.setCriteria(criteria);
        });

        width.setOnKeyReleased((event) -> {
            State state = State.get();
            State.Criteria criteria = state.getCriteria();
            double value;
            try {
                value = Double.valueOf(width.getText());
            } catch (NumberFormatException exception) {
                value = 0;
            }
            criteria.setWidth(value);
            state.setCriteria(criteria);
        });

        height.setOnKeyReleased((event) -> {
            State state = State.get();
            State.Criteria criteria = state.getCriteria();
            double value;
            try {
                value = Double.valueOf(height.getText());
            } catch (NumberFormatException exception) {
                value = 0;
            }
            criteria.setHeight(value);
            state.setCriteria(criteria);
        });

        depth.setOnKeyReleased((event) -> {
            State state = State.get();
            State.Criteria criteria = state.getCriteria();
            double value;
            try {
                value = Double.valueOf(depth.getText());
            } catch (NumberFormatException exception) {
                value = 0;
            }
            criteria.setDepth(value);
            state.setCriteria(criteria);
        });
    }
}
