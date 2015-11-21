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
import javafx.util.StringConverter;
import org.controlsfx.control.RangeSlider;

import java.net.URL;
import java.util.ResourceBundle;

public class Filter extends Widget implements Initializable {

    private static final double MIN_DISPLAY_SIZE = 0;
    private static final double MAX_DISPLAY_SIZE = 20;
    private static final double INC_DISPLAY_SIZE = 5;

    private static final double MIN_PRICE = 0;
    private static final double MAX_PRICE = 1000;
    private static final double INC_PRICE = 250;

    private static final double MIN_MEMORY_RAM_SIZE = 500;
    private static final double MAX_MEMORY_RAM_SIZE = 2000;
    private static final double INC_MEMORY_RAM_SIZE = 500;

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
    RangeSlider memory;

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

        /*
         * Display
         */
        display.setMin(MIN_DISPLAY_SIZE);
        display.setMax(MAX_DISPLAY_SIZE);
        display.setMajorTickUnit(INC_DISPLAY_SIZE);
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

        display.adjustLowValue(MIN_DISPLAY_SIZE);
        display.adjustHighValue(MAX_DISPLAY_SIZE);

        display.setOnMouseReleased((event) -> {
            State state = State.get();
            State.Criteria criteria = state.getCriteria();
            criteria.setDisplaySize(
                    display.getLowValue(), display.getHighValue());
            state.setCriteria(criteria);
        });

        price.setMin(MIN_PRICE);
        price.setMax(MAX_PRICE);
        price.setMajorTickUnit(INC_PRICE);
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

        price.adjustLowValue(MIN_PRICE);
        price.adjustHighValue(MAX_PRICE);

        price.setOnMouseReleased((event) -> {
            State state = State.get();
            State.Criteria criteria = state.getCriteria();
            criteria.setPrice(price.getLowValue(), price.getHighValue());
            state.setCriteria(criteria);
        });

        /*
         * Memory
         */
        memory.setMin(MIN_MEMORY_RAM_SIZE);
        memory.setMax(MAX_MEMORY_RAM_SIZE);
        memory.setMajorTickUnit(INC_MEMORY_RAM_SIZE);
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

        memory.adjustLowValue(MIN_MEMORY_RAM_SIZE);
        memory.adjustHighValue(MAX_MEMORY_RAM_SIZE);

        memory.setOnMouseReleased((event) -> {
            State state = State.get();
            State.Criteria criteria = state.getCriteria();
            criteria.setMemoryRAMSize(
                    memory.getLowValue(), memory.getHighValue());
            state.setCriteria(criteria);
        });
    }
}
