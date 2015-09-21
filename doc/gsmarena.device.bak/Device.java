package dss.models.device;

import dss.models.Base;
import dss.models.device.battery.DeviceBattery;
import dss.models.device.body.DeviceBody;
import dss.models.device.camera.DeviceCamera;
import dss.models.device.com.DeviceCom;
import dss.models.device.display.DeviceDisplay;
import dss.models.device.feature.DeviceFeature;
import dss.models.device.memory.DeviceMemory;
import dss.models.device.misc.DeviceMisc;
import dss.models.device.network.DeviceNetwork;
import dss.models.device.platform.DevicePlatform;
import dss.models.device.sound.DeviceSound;
import dss.models.manufacturer.Manufacturer;

public class Device extends Base {
    
    public Manufacturer manufacturer;

    public DeviceNetwork network;
    public DeviceBody body;
    public DeviceDisplay display;
    public DevicePlatform platform;
    public DeviceMemory memory;
    public DeviceCamera camera;
    public DeviceSound sound;
    public DeviceCom com;
    public DeviceFeature feature;
    public DeviceBattery battery;
    public DeviceMisc misc;
}
