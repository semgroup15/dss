package dss.commands;

import dss.models.device.Device;
import dss.models.device.battery.DeviceBattery;
import dss.models.device.body.DeviceBody;
import dss.models.device.body.SIMType;
import dss.models.device.camera.CameraFeature;
import dss.models.device.camera.CameraVideo;
import dss.models.device.camera.DeviceCamera;
import dss.models.device.camera.DeviceCameraPrimaryFeature;
import dss.models.device.camera.DeviceCameraPrimaryVideo;
import dss.models.device.com.DeviceCom;
import dss.models.device.display.DeviceDisplay;
import dss.models.device.display.DisplayProtection;
import dss.models.device.display.DisplayType;
import dss.models.device.feature.DeviceMessagingFeature;
import dss.models.device.feature.DeviceSensorFeature;
import dss.models.device.feature.MessagingFeature;
import dss.models.device.feature.SensorFeature;
import dss.models.device.memory.DeviceInternalMemory;
import dss.models.device.memory.DeviceMemorySlot;
import dss.models.device.memory.DeviceRAM;
import dss.models.device.memory.InternalMemory;
import dss.models.device.memory.MemorySlot;
import dss.models.device.misc.Color;
import dss.models.device.misc.DeviceColor;
import dss.models.device.network.DeviceNetworkTechnology;
import dss.models.device.network.NetworkTechnology;
import dss.models.device.platform.DevicePlatform;
import dss.models.device.platform.PlatformCPUType;
import dss.models.device.platform.PlatformChipset;
import dss.models.device.platform.PlatformGPU;
import dss.models.device.platform.PlatformOS;
import dss.models.device.platform.PlatformOSVersion;
import dss.models.device.sound.DeviceSound;
import dss.models.manufacturer.Manufacturer;

public class MigrateCommand implements Runnable {
    @Override
    public void run() {
        /*
         * Main models
         */

        System.out.println("* Main models");

        Manufacturer.manager.createTable();
        Device.manager.createTable();

        /*
         * Generic choices
         */

        System.out.println("* Generic choices");

        SIMType.manager.createTable();
        CameraFeature.manager.createTable();
        CameraVideo.manager.createTable();
        DisplayProtection.manager.createTable();
        DisplayType.manager.createTable();
        MessagingFeature.manager.createTable();
        SensorFeature.manager.createTable();
        InternalMemory.manager.createTable();
        MemorySlot.manager.createTable();
        Color.manager.createTable();
        NetworkTechnology.manager.createTable();

        /*
         * Platform information
         */

        System.out.println("* Platform information");

        PlatformChipset.manager.createTable();
        PlatformCPUType.manager.createTable();
        PlatformGPU.manager.createTable();
        PlatformOS.manager.createTable();
        PlatformOSVersion.manager.createTable();

        /*
         * Device relations
         */

        System.out.println("* Device relations");

        DeviceBattery.manager.createTable();
        DeviceBody.manager.createTable();
        DeviceCamera.manager.createTable();
        DeviceCameraPrimaryFeature.manager.createTable();
        DeviceCameraPrimaryVideo.manager.createTable();
        DeviceCom.manager.createTable();
        DeviceDisplay.manager.createTable();
        DeviceMessagingFeature.manager.createTable();
        DeviceSensorFeature.manager.createTable();
        DeviceRAM.manager.createTable();
        DeviceInternalMemory.manager.createTable();
        DeviceMemorySlot.manager.createTable();
        DeviceColor.manager.createTable();
        DeviceNetworkTechnology.manager.createTable();
        DevicePlatform.manager.createTable();
        DeviceSound.manager.createTable();

        System.out.println("Finished");
    }
}
