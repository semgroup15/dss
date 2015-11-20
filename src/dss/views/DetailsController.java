package dss.views;

import dss.models.device.Device;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DetailsController {
    // Reference to the main views.
	private MainView mainApp;

    @FXML private ImageView picture;
    @FXML private Text model;
    @FXML private Text manufacturer;
    @FXML private Text platform;
    @FXML private Text displayDimensions;
    @FXML private Text bodyDimensions;
    @FXML private Text colors;
    @FXML private Text battery;
    @FXML private Text camera;
    @FXML private Text com;
    @FXML private Text sensor;
    @FXML private Text memory;
    @FXML private Text network;
    @FXML private Text sound;
    @FXML private Text simtype;



    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    public void initialize() {

    }
    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainView mainApp) {
        this.mainApp = mainApp;
    }

    public void displayDevice(Device device)
    {
    	if(device ==  null) return;
        this.mainApp.getMainController().setScreen(MainController.Screen.Details);
        this.model.setText(device.name);
        this.manufacturer.setText(device.getManufacturer().name);
        this.displayDimensions.setText(device.display.width + " x " +
        		device.display.height);

        this.bodyDimensions.setText(device.body.width + " mm x " +
        		device.body.height + " mm x " +
        		device.body.depth + " mm");

        // Remove last ", "

        this.colors.setText(device.color.toString());
        this.battery.setText("Idle battery life: " + device.battery.sleep + " hours, " +
                "Music play: " + device.battery.music + " hours, " +
                "Talk time: " + device.battery.talk + " hours");
        this.camera.setText("Primary: " + device.camera.primary.mp + " MP, " +
                device.camera.primary.height + " x " +
                device.camera.primary.width + " pixels");
        // this.camera.setText("Secondary: " + device.camera.secondary.mp + " MP");
        this.com.setText(device.com.toString());
        this.sensor.setText(device.sensor.toString());
        this.memory.setText("Size: " + device.memory.ramSize + " MB RAM, " +
                "Internal: " + device.memory.internalSize + " MB" + " " +
                device.memory.toString() );
        this.network.setText(device.network.toString());

        String audio = "Audio options: ";
        if(device.sound.loudspeaker) audio += "Loudspeaker, ";
        if(device.sound.jack35) audio += "3.5mm Jack, ";
        if(audio.equals("Audio options: ")) audio += "None";
        else audio = audio.substring(0, audio.length() - 2);
        this.sound.setText(audio);

        this.simtype.setText(device.simType.toString());
        this.platform.setText(device.platform.toString());







        try{
            File deviceFile = device.getImageFile();
            FileInputStream fis = new FileInputStream(deviceFile);
            Image image = new Image(fis);
            picture.setImage(image);
        }
        catch (NullPointerException e)
        {
            //continue
        }
        catch (FileNotFoundException e)
        {
            //continue;
        }
    }
}