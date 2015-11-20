package dss.views.sections.detail;

import dss.models.device.Device;
import dss.views.State;
import dss.views.Widget;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Detail extends Widget {

    @FXML
    ImageView image;

    @FXML
    Text manufacturer;

    @FXML
    Text name;

    @FXML
    Field display;

    @FXML
    Field body;

    @FXML
    Field colors;

    @FXML
    Field battery;

    @FXML
    Field camera;

    @FXML
    Field com;

    @FXML
    Field sensor;

    @FXML
    Field memory;

    @FXML
    Field network;

    @FXML
    Field sound;

    @FXML
    Field sim;

    @FXML
    Field platform;

    public void setDevice(Device device) {

        try {
            image.setImage(new Image(
                    new FileInputStream(device.getImageFile())));
        } catch (FileNotFoundException exception) {
            // Image not found
        }

        name.setText(device.name);
        manufacturer.setText(device.getManufacturer().name);

        display.setValue(
            device.display.width + " x " +
            device.display.height);

        body.setValue(
            device.body.width + " mm x " +
            device.body.height + " mm x " +
            device.body.depth + " mm");

        colors.setValue(device.color.toString());

        battery.setValue(
            "Idle: " + device.battery.sleep + " hours, " +
            "Music: " + device.battery.music + " hours, " +
            "Talk: " + device.battery.talk + " hours");

        camera.setValue(
            "Primary: " + device.camera.primary.mp + " MP, " +
            device.camera.primary.height + " x " +
            device.camera.primary.width + " pixels");

        com.setValue(device.com.toString());

        sensor.setValue(device.sensor.toString());

        memory.setValue(
            "Size: " + device.memory.ramSize + " MB RAM, " +
            "Internal: " + device.memory.internalSize + " MB" + " " +
            device.memory.toString());

        network.setValue(device.network.toString());

        sound.setValue(
            "Loudspeaker: " + device.sound.loudspeaker + ", " +
            "3.5mm jack: " + device.sound.jack35);

        sim.setValue(device.simType.toString());

        platform.setValue(device.platform.toString());
    }

    @FXML
    public void onBack() {
        State.get().setLocation(
                new State.Location(State.Location.Section.LISTING));
    }
}
