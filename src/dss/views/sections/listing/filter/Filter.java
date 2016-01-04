package dss.views.sections.listing.filter;

import dss.Developer;
import dss.models.device.Device;
import dss.views.base.State;
import dss.views.base.Widget;
import dss.views.base.components.Rating;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import org.controlsfx.control.RangeSlider;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

@Developer({
    Developer.Value.SEBASTIAN,
    Developer.Value.TRIXIE,
})
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
    RangeSlider display;

    @FXML
    RangeSlider price;

    @FXML
    @Developer({
        Developer.Value.JONATAN,
        Developer.Value.AHMED,
    })
    RangeSlider memory;

    @FXML
    public void initialize(URL location, ResourceBundle resourceBundle) {
        List<Device.Platform> platforms = new ArrayList<>();
        platforms.addAll(Arrays.asList(Device.Platform.ALL));
        platforms.add(0, Device.Platform.NONE);
        platform.setItems(FXCollections.observableArrayList(platforms));

        /*
         * All criteria changes consist of 4 steps
         *
         * 1. Get global application state
         * 2. Get current filtering criteria
         * 3. Update criteria values
         * 4. Publish criteria to global state
         */

        platform.setOnAction((event) -> {
            Device.Platform platform = this.platform.getValue();
            if (platform == Device.Platform.NONE) {
                platform = null;
            }

            State state = State.get();
            State.Criteria criteria = state.getCriteria();
            criteria.setPlatform(platform);
            state.setCriteria(criteria);
        });

        responsivenessRating.addListener((value) -> {
            State state = State.get();
            State.Criteria criteria = state.getCriteria();
            criteria.setResponsivenessRating(value > 1 ? value : 0);
            state.setCriteria(criteria);
        });

        screenRating.addListener((value) -> {
            State state = State.get();
            State.Criteria criteria = state.getCriteria();
            criteria.setScreenRating(value > 1 ? value : 0);
            state.setCriteria(criteria);
        });

        batteryRating.addListener((value) -> {
            State state = State.get();
            State.Criteria criteria = state.getCriteria();
            criteria.setBatteryRating(value > 1 ? value : 0);
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

        /*
         * Display
         */
        display.setMin(State.Criteria.MIN_DISPLAY_SIZE);
        display.setMax(State.Criteria.MAX_DISPLAY_SIZE);
        display.setMajorTickUnit(State.Criteria.INC_DISPLAY_SIZE);
        display.setLabelFormatter(new StringConverter<Number>() {
            @Override
            public String toString(Number number) {
                return String.format("%d\"", number.intValue());
            }

            @Override
            public Number fromString(String s) {
                return null;
            }
        });

        display.adjustLowValue(State.Criteria.MIN_DISPLAY_SIZE);
        display.adjustHighValue(State.Criteria.MAX_DISPLAY_SIZE);

        display.setOnMouseReleased((event) -> {
            State state = State.get();
            State.Criteria criteria = state.getCriteria();
            criteria.setDisplaySize(
                    display.getLowValue(), display.getHighValue());
            state.setCriteria(criteria);
        });

        price.setMin(State.Criteria.MIN_PRICE);
        price.setMax(State.Criteria.MAX_PRICE);
        price.setMajorTickUnit(State.Criteria.INC_PRICE);
        price.setLabelFormatter(new StringConverter<Number>() {
            @Override
            public String toString(Number number) {
                return String.format("$%d", number.intValue());
            }

            @Override
            public Number fromString(String s) {
                return null;
            }
        });

        price.adjustLowValue(State.Criteria.MIN_PRICE);
        price.adjustHighValue(State.Criteria.MAX_PRICE);

        price.setOnMouseReleased((event) -> {
            State state = State.get();
            State.Criteria criteria = state.getCriteria();
            criteria.setPrice(price.getLowValue(), price.getHighValue());
            state.setCriteria(criteria);
        });

        /*
         * Memory
         */
        memory.setMin(State.Criteria.MIN_MEMORY_RAM_SIZE);
        memory.setMax(State.Criteria.MAX_MEMORY_RAM_SIZE);
        memory.setMajorTickUnit(State.Criteria.INC_MEMORY_RAM_SIZE);
        memory.setLabelFormatter(new StringConverter<Number>() {
            @Override
            public String toString(Number number) {
                double value = number.doubleValue();
                if (value >= 1000) {
                    return String.format("%.1f GB", (value / 1000));
                } else {
                    return String.format("%.0f MB", (value));
                }
            }

            @Override
            public Number fromString(String s) {
                return null;
            }
        });

        memory.adjustLowValue(State.Criteria.MIN_MEMORY_RAM_SIZE);
        memory.adjustHighValue(State.Criteria.MAX_MEMORY_RAM_SIZE);

        memory.setOnMouseReleased((event) -> {
            State state = State.get();
            State.Criteria criteria = state.getCriteria();
            criteria.setMemoryRAMSize(
                    memory.getLowValue(), memory.getHighValue());
            state.setCriteria(criteria);
        });
    }
}
