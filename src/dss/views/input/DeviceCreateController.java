package dss.views.input;
import dss.models.device.Device;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;


/**
 * Created by jpp on 11/12/15.
 */
public class DeviceCreateController {
    @FXML
    private CheckBox sound_jack35;

    @FXML
    private CheckBox colors_white;

    @FXML
    private RadioButton platform_ios;

    @FXML
    private CheckBox comms_usb;

    @FXML
    private TextField camera_secondary_mp;

    @FXML
    private CheckBox sensors_gyro;

    @FXML
    private CheckBox network_lte;

    @FXML
    private TextField display_size;

    @FXML
    private CheckBox colors_pink;

    @FXML
    private CheckBox display_multitouch;

    @FXML
    private TextField model;

    @FXML
    private CheckBox colors_black;

    @FXML
    private CheckBox comms_bluetooth;

    @FXML
    private TextField camera_primary_width;

    @FXML
    private TextField memory_internalsize;

    @FXML
    private CheckBox memory_sd;

    @FXML
    private CheckBox simtype_nanosim;

    @FXML
    private RadioButton platform_windows;

    @FXML
    private CheckBox sound_loudspeaker;

    @FXML
    private CheckBox network_gsm;

    @FXML
    private CheckBox colors_orange;

    @FXML
    private CheckBox colors_gray;

    @FXML
    private TextField memory_ramsize;

    @FXML
    private CheckBox colors_red;

    @FXML
    private CheckBox sensors_compass;

    @FXML
    private CheckBox memory_microsd;

    @FXML
    private TextField display_ratio;

    @FXML
    private CheckBox comms_wlan;

    @FXML
    private TextField devicedimensions_weight;

    @FXML
    private TextField camera_primary_height;

    @FXML
    private TextField camera_primary_mp;

    @FXML
    private CheckBox colors_gold;

    @FXML
    private CheckBox comms_nfc;

    @FXML
    private TextField display_protection;

    @FXML
    private CheckBox simtype_unknown;

    @FXML
    private CheckBox network_evdo;

    @FXML
    private CheckBox colors_blue;

    @FXML
    private CheckBox colors_green;

    @FXML
    private TextField devicedimensions_height;

    @FXML
    private CheckBox sensors_magnetometer;

    @FXML
    private CheckBox sensors_proximity;

    @FXML
    private TextField display_width;

    @FXML
    private CheckBox memory_microsdhc;

    @FXML
    private CheckBox memory_mmcmobile;

    @FXML
    private TextField display_type;

    @FXML
    private CheckBox memory_mmc;

    @FXML
    private TextField devicedimensions_width;

    @FXML
    private TextField devicedimensions_depth;

    @FXML
    private CheckBox sensors_barometer;

    @FXML
    private RadioButton platform_unknown;

    @FXML
    private TextField batterytime_talk;

    @FXML
    private CheckBox network_umts;

    @FXML
    private CheckBox simtype_fullsize;

    @FXML
    private CheckBox sensors_accelerometer;

    @FXML
    private RadioButton platform_android;

    @FXML
    private CheckBox network_cdma;

    @FXML
    private CheckBox comms_radio;

    @FXML
    private TextField display_density;

    @FXML
    private CheckBox comms_gps;

    @FXML
    private CheckBox colors_yellow;

    @FXML
    private CheckBox simtype_microsim;

    @FXML
    private CheckBox colors_silver;

    @FXML
    private TextField imageurl;

    @FXML
    private TextField display_height;

    @FXML
    private CheckBox network_hspa;

    @FXML
    private CheckBox simtype_minisim;

    @FXML
    private TextField batterytime_sleep;

    @FXML
    private TextField batterytime_music;

    public Device createDevice() {
        Device device = new Device();
        device.name = model.getText();
        device.imageUrl = imageurl.getText();

        Device.Body body = new Device.Body();
        body.width = Double.parseDouble(devicedimensions_width.getText());
        body.height = Double.parseDouble(devicedimensions_height.getText());
        body.depth = Double.parseDouble(devicedimensions_depth.getText());
        body.weight = Double.parseDouble(devicedimensions_weight.getText());
        device.body = body;

        Device.Battery battery =  new Device.Battery();
        battery.sleep = Integer.parseInt(batterytime_sleep.getText());
        battery.talk = Integer.parseInt(batterytime_talk.getText());
        battery.music = Integer.parseInt(batterytime_music.getText());
        device.battery = battery;


        Device.Camera camera = new Device.Camera();
        camera.primary.mp = Integer.parseInt(camera_primary_mp.getText());
        camera.primary.width = Integer.parseInt(camera_primary_width.getText());
        camera.primary.height = Integer.parseInt(camera_primary_height.getText());
        camera.secondary.mp = Integer.parseInt(camera_secondary_mp.getText());
        device.camera = camera;

        Device.Com comms = new Device.Com();
        comms.wlan = comms_wlan.selectedProperty().get();
        comms.bluetooth = comms_bluetooth.selectedProperty().get();
        comms.gps = comms_gps.selectedProperty().get();
        comms.nfc = comms_nfc.selectedProperty().get();
        comms.radio = comms_radio.selectedProperty().get();
        comms.usb = comms_usb.selectedProperty().get();
        device.com = comms;


        final ToggleGroup group = new ToggleGroup();

            platform_unknown.setToggleGroup(group);
            platform_unknown.setSelected(true);
            platform_android.setToggleGroup(group);
            platform_android.setSelected(false);
            platform_ios.setToggleGroup(group);
            platform_ios.setSelected(false);
            platform_windows.setToggleGroup(group);
            platform_windows.setSelected(false);

// I DON'T KNOW WHAT THIS DOES!
        
            group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                @Override
                public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {

                    RadioButton chk = (RadioButton)t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button

                }
            });
        return device;

    }
}

