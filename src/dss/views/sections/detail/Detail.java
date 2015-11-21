package dss.views.sections.detail;

import dss.models.device.Device;
import dss.models.manufacturer.Manufacturer;
import dss.views.Widget;
import dss.views.sections.Rating;
import dss.views.sections.detail.fields.*;
import dss.views.sections.detail.fields.base.DeviceBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

        try {
            image.setImage(new Image(
                    new FileInputStream(device.getImageFile())));
        } catch (FileNotFoundException exception) {
            // Image not found
        }

        for (DeviceBinding binding : bindings()) {
            binding.syncFromDevice(device);
        }

        overallRating.setValue(device.overallRating);
        responsivenessRating.setValue(device.responsivenessRating);
        screenRating.setValue(device.screenRating);
        batteryRating.setValue(device.batteryRating);
    }

    public void save() {
        for (DeviceBinding binding : bindings()) {
            binding.syncToDevice(device);
        }

        device.save();
    }
}
