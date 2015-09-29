package dss.interop;

import java.util.ArrayList;
import java.util.List;

import dss.models.device.Device;
import dss.models.device.battery.DeviceBattery;
import dss.models.device.body.DeviceBody;
import dss.models.device.body.SIMType;
import dss.models.device.camera.CameraFeature;
import dss.models.device.camera.CameraVideo;
import dss.models.device.camera.DeviceCamera;
import dss.models.device.com.DeviceCom;
import dss.models.device.display.DeviceDisplay;
import dss.models.device.display.DisplayProtection;
import dss.models.device.display.DisplayType;
import dss.models.device.feature.MessagingFeature;
import dss.models.device.feature.SensorFeature;
import dss.models.device.memory.DeviceRAM;
import dss.models.device.memory.InternalMemory;
import dss.models.device.memory.MemorySlot;
import dss.models.device.misc.Color;
import dss.models.device.network.NetworkTechnology;
import dss.models.device.platform.DevicePlatform;
import dss.models.device.platform.PlatformCPUType;
import dss.models.device.platform.PlatformChipset;
import dss.models.device.platform.PlatformGPU;
import dss.models.device.platform.PlatformOS;
import dss.models.device.platform.PlatformOSVersion;
import dss.models.device.sound.DeviceSound;
import dss.models.manufacturer.Manufacturer;

/**
 * Device information pending normalization.
 */
public class DeviceResult {

    Manufacturer manufacturer = new Manufacturer();
    Device device = new Device();

    SIMType simType = new SIMType();

    DisplayType displayType = new DisplayType();
    DisplayProtection displayProtection = new DisplayProtection();

    /*
     * Many-to-many relations
     */

    List<CameraFeature> cameraFeature = new ArrayList<>();
    List<CameraVideo> cameraVideo = new ArrayList<>();
    CameraVideo secondaryCameraVideo = new CameraVideo();

    List<MessagingFeature> messagingFeature = new ArrayList<>();
    List<SensorFeature> sensorFeature = new ArrayList<>();

    List<InternalMemory> internalMemory = new ArrayList<>();
    List<MemorySlot> memorySlot = new ArrayList<>();

    List<Color> color = new ArrayList<>();
    List<NetworkTechnology> networkTechnology = new ArrayList<>();

    /*
     * Platform information
     */

    PlatformChipset chipset = new PlatformChipset();
    PlatformCPUType cpuType = new PlatformCPUType();
    PlatformGPU gpu = new PlatformGPU();

    PlatformOS os = new PlatformOS();
    PlatformOSVersion currentVersion = new PlatformOSVersion();
    PlatformOSVersion upgradeVersion = new PlatformOSVersion();

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