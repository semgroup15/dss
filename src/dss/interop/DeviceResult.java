package dss.interop;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import dss.models.device.battery.DeviceBattery;
import dss.models.device.body.DeviceBody;
import dss.models.device.camera.DeviceCamera;
import dss.models.device.com.DeviceCom;
import dss.models.device.display.DeviceDisplay;
import dss.models.device.memory.DeviceRAM;
import dss.models.device.platform.DevicePlatform;
import dss.models.device.sound.DeviceSound;

/**
 * Device information pending normalization.
 */
public class DeviceResult {

    int year;

    String simTypeName;

    String displayTypeName;
    String displayProtectionName;

    /*
     * Many-to-many relations
     */

    Set<String> cameraFeatureNames = new HashSet<>();
    Set<String> cameraVideoNames = new HashSet<>();
    String secondaryCameraVideoName;

    Set<String> messagingFeatureNames = new HashSet<>();
    Set<String> sensorFeatureNames = new HashSet<>();

    Set<Pair<String, Integer>> internalMemoryPairs = new HashSet<>();
    Set<String> memorySlotNames = new HashSet<>();

    Set<String> colorNames = new HashSet<>();
    Set<String> networkTechnologyNames = new HashSet<>();

    /*
     * Platform information
     */

    String chipsetName;
    String cpuTypeName;
    String gpuName;

    String osName;
    String osCurrentVersionName;
    String osUpgradeVersionName;

    /*
     * Device information
     */

    DeviceBattery battery = new DeviceBattery();
    DeviceBody body = new DeviceBody();
    DeviceCamera camera = new DeviceCamera();
    DeviceCom com = new DeviceCom();
    DeviceDisplay display = new DeviceDisplay();
    DeviceRAM ram = new DeviceRAM();
    DevicePlatform platform = new DevicePlatform();
    DeviceSound sound = new DeviceSound();
}