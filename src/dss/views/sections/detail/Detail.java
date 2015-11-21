package dss.views.sections.detail;

import dss.models.device.Device;
import dss.models.manufacturer.Manufacturer;
import dss.views.State;
import dss.views.Widget;
import dss.views.sections.Rating;
import dss.views.sections.detail.fields.*;
import dss.views.sections.detail.fields.base.DeviceBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Detail extends Widget {

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
    }

    @FXML
    public void onDelete() {
        // Delete device
        device.delete();

        // Leave detail view
        State state = State.get();
        state.setLocation(new State.Location(State.Location.Section.LISTING));

        // Refresh list
        state.setCriteria(state.getCriteria());
    }

    @FXML
    public void onSave() {
        for (DeviceBinding binding : bindings()) {
            binding.syncToDevice(device);
        }

        device.save();

        // Leave detail view
        State state = State.get();
        state.setLocation(new State.Location(State.Location.Section.LISTING));

        // Refresh list
        state.setCriteria(state.getCriteria());
    }
}
