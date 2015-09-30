package dss.interop;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import dss.models.Model;
import dss.models.Model.DoesNotExist;
import dss.models.device.Device;
import dss.models.device.body.SIMType;
import dss.models.device.camera.CameraFeature;
import dss.models.device.camera.CameraVideo;
import dss.models.device.camera.DeviceCameraPrimaryFeature;
import dss.models.device.camera.DeviceCameraPrimaryVideo;
import dss.models.device.display.DisplayProtection;
import dss.models.device.display.DisplayType;
import dss.models.device.feature.DeviceMessagingFeature;
import dss.models.device.feature.DeviceSensorFeature;
import dss.models.device.feature.MessagingFeature;
import dss.models.device.feature.SensorFeature;
import dss.models.device.memory.DeviceInternalMemory;
import dss.models.device.memory.DeviceMemorySlot;
import dss.models.device.memory.InternalMemory;
import dss.models.device.memory.MemorySlot;
import dss.models.device.misc.Color;
import dss.models.device.misc.DeviceColor;
import dss.models.device.network.DeviceNetworkTechnology;
import dss.models.device.network.NetworkTechnology;
import dss.models.device.platform.PlatformCPUType;
import dss.models.device.platform.PlatformChipset;
import dss.models.device.platform.PlatformGPU;
import dss.models.device.platform.PlatformOS;
import dss.models.device.platform.PlatformOSVersion;
import dss.models.manufacturer.Manufacturer;

/**
 * Device normalization and loading compatible with
 * {@code DeviceResultExtractor}.
 *
 * <ul>
 *   <li>Extract device information</li>
 *   <li>Normalize data provided by extractor</li>
 *   <li>Store normalized data</li>
 * </ul>
 */
public class DeviceResultLoader {

    /**
     * Model cache
     */
    public static class Cache {
        public Model.Cache<Manufacturer, Long> manufacturer =
                new Model.Cache<>(new Manufacturer.Loader());

        public Model.Cache<SIMType, String> simType =
            new Model.Cache<>(
                new Model.Cache.Loader<SIMType, String>() {
                    @Override
                    public SIMType load(String key)
                            throws Model.DoesNotExist {

                        return SIMType.manager.get(SIMType.SELECT_NAME, key);
                    }

                    @Override
                    public SIMType create(String key) {
                        SIMType simType = new SIMType();
                        simType.name = key;
                        simType.save();
                        return simType;
                    }
                });

        public Model.Cache<DisplayProtection, String> displayProtection =
            new Model.Cache<>(
                new Model.Cache.Loader<DisplayProtection, String>() {
                    @Override
                    public DisplayProtection load(String key)
                            throws DoesNotExist {

                        return DisplayProtection.manager.get(
                                DisplayProtection.SELECT_NAME, key);
                    }

                    @Override
                    public DisplayProtection create(String key) {
                        DisplayProtection displayProtection =
                                new DisplayProtection();
                        displayProtection.name = key;
                        displayProtection.save();
                        return displayProtection;
                    }
                });

        public Model.Cache<DisplayType, String> displayType =
            new Model.Cache<>(
                new Model.Cache.Loader<DisplayType, String>() {
                    @Override
                    public DisplayType load(String key)
                            throws DoesNotExist {

                        return DisplayType.manager.get(
                                DisplayType.SELECT_NAME, key);
                    }

                    @Override
                    public DisplayType create(String key) {
                        DisplayType displayType = new DisplayType();
                        displayType.name = key;
                        displayType.save();
                        return displayType;
                    }
                });

        public Model.Cache<CameraFeature, String> cameraFeature =
            new Model.Cache<>(
                new Model.Cache.Loader<CameraFeature, String>() {
                    @Override
                    public CameraFeature load(String key)
                            throws DoesNotExist {

                        return CameraFeature.manager.get(
                                CameraFeature.SELECT_NAME, key);
                    }

                    @Override
                    public CameraFeature create(String key) {
                        CameraFeature cameraFeature = new CameraFeature();
                        cameraFeature.name = key;
                        cameraFeature.save();
                        return cameraFeature;
                    }
                });

        public Model.Cache<CameraVideo, String> cameraVideo =
            new Model.Cache<>(
                new Model.Cache.Loader<CameraVideo, String>() {
                    @Override
                    public CameraVideo load(String key)
                            throws DoesNotExist {

                        return CameraVideo.manager.get(
                               CameraVideo.SELECT_NAME, key);
                    }

                    @Override
                    public CameraVideo create(String key) {
                        CameraVideo cameraVideo = new CameraVideo();
                        cameraVideo.name = key;
                        cameraVideo.save();
                        return cameraVideo;
                    }
                });

        public Model.Cache<MessagingFeature, String> messagingFeature =
            new Model.Cache<>(
                new Model.Cache.Loader<MessagingFeature, String>() {
                    @Override
                    public MessagingFeature load(String key)
                            throws DoesNotExist {

                        return MessagingFeature.manager.get(
                                MessagingFeature.SELECT_NAME, key);
                    }

                    @Override
                    public MessagingFeature create(String key) {
                        MessagingFeature messagingFeature =
                                new MessagingFeature();
                        messagingFeature.name = key;
                        messagingFeature.save();
                        return messagingFeature;
                    }
                });

        public Model.Cache<SensorFeature, String> sensorFeature =
            new Model.Cache<>(
                new Model.Cache.Loader<SensorFeature, String>() {
                    @Override
                    public SensorFeature load(String key)
                            throws DoesNotExist {

                        return SensorFeature.manager.get(
                                SensorFeature.SELECT_NAME, key);
                    }

                    @Override
                    public SensorFeature create(String key) {
                        SensorFeature sensorFeature = new SensorFeature();
                        sensorFeature.name = key;
                        sensorFeature.save();
                        return sensorFeature;
                    }
                });

        public Model.Cache<InternalMemory, String> internalMemory =
            new Model.Cache<>(
                new Model.Cache.Loader<InternalMemory, String>() {
                    @Override
                    public InternalMemory load(String key)
                            throws DoesNotExist {

                        return InternalMemory.manager.get(
                                InternalMemory.SELECT_NAME, key);
                    }

                    @Override
                    public InternalMemory create(String key) {
                        InternalMemory internalMemory = new InternalMemory();
                        internalMemory.name = key;
                        internalMemory.save();
                        return internalMemory;
                    }
                });

        public Model.Cache<MemorySlot, String> memorySlot =
            new Model.Cache<>(
                new Model.Cache.Loader<MemorySlot, String>() {
                    @Override
                    public MemorySlot load(String key)
                            throws DoesNotExist {

                        return MemorySlot.manager.get(
                                MemorySlot.SELECT_NAME, key);
                    }

                    @Override
                    public MemorySlot create(String key) {
                        MemorySlot memorySlot = new MemorySlot();
                        memorySlot.name = key;
                        memorySlot.save();
                        return memorySlot;
                    }
                });

        public Model.Cache<Color, String> color =
            new Model.Cache<>(
                new Model.Cache.Loader<Color, String>() {
                    @Override
                    public Color load(String key) throws DoesNotExist {
                        return Color.manager.get(Color.SELECT_NAME, key);
                    }

                    @Override
                    public Color create(String key) {
                        Color color = new Color();
                        color.name = key;
                        color.save();
                        return color;
                    }
                });

        public Model.Cache<NetworkTechnology, String> networkTechnology =
            new Model.Cache<>(
                new Model.Cache.Loader<NetworkTechnology, String>() {
                    @Override
                    public NetworkTechnology load(String key)
                            throws DoesNotExist {

                        return NetworkTechnology.manager.get(
                                NetworkTechnology.SELECT_NAME, key);
                    }

                    @Override
                    public NetworkTechnology create(String key) {
                        NetworkTechnology networkTechnology =
                                new NetworkTechnology();
                        networkTechnology.name = key;
                        networkTechnology.save();
                        return networkTechnology;
                    }
                });

        public Model.Cache<PlatformChipset, String> platformChipset =
            new Model.Cache<>(
                new Model.Cache.Loader<PlatformChipset, String>() {
                    @Override
                    public PlatformChipset load(String key)
                            throws DoesNotExist {

                        return PlatformChipset.manager.get(
                                PlatformChipset.SELECT_NAME, key);
                    }

                    @Override
                    public PlatformChipset create(String key) {
                        PlatformChipset networkTechnology =
                                new PlatformChipset();
                        networkTechnology.name = key;
                        networkTechnology.save();
                        return networkTechnology;
                    }
                });

        public Model.Cache<PlatformCPUType, String> platformCPUType =
            new Model.Cache<>(
                new Model.Cache.Loader<PlatformCPUType, String>() {
                    @Override
                    public PlatformCPUType load(String key)
                            throws DoesNotExist {

                        return PlatformCPUType.manager.get(
                                PlatformCPUType.SELECT_NAME, key);
                    }

                    @Override
                    public PlatformCPUType create(String key) {
                        PlatformCPUType networkTechnology =
                                new PlatformCPUType();
                        networkTechnology.name = key;
                        networkTechnology.save();
                        return networkTechnology;
                    }
                });

        public Model.Cache<PlatformGPU, String> platformGPU =
            new Model.Cache<>(
                new Model.Cache.Loader<PlatformGPU, String>() {
                    @Override
                    public PlatformGPU load(String key)
                            throws DoesNotExist {

                        return PlatformGPU.manager.get(
                                PlatformGPU.SELECT_NAME, key);
                    }

                    @Override
                    public PlatformGPU create(String key) {
                        PlatformGPU networkTechnology =
                                new PlatformGPU();
                        networkTechnology.name = key;
                        networkTechnology.save();
                        return networkTechnology;
                    }
                });

        public Model.Cache<PlatformOS, String> platformOS =
            new Model.Cache<>(
                new Model.Cache.Loader<PlatformOS, String>() {
                    @Override
                    public PlatformOS load(String key)
                            throws DoesNotExist {

                        return PlatformOS.manager.get(
                                PlatformOS.SELECT_NAME, key);
                    }

                    @Override
                    public PlatformOS create(String key) {
                        PlatformOS networkTechnology =
                                new PlatformOS();
                        networkTechnology.name = key;
                        networkTechnology.save();
                        return networkTechnology;
                    }
                });

        public Model.Cache<
                PlatformOSVersion, Pair<Long, String>> platformOSVersion =
            new Model.Cache<>(
                new Model.Cache.Loader<
                        PlatformOSVersion, Pair<Long, String>>() {
                    @Override
                    public PlatformOSVersion load(Pair<Long, String> key)
                            throws DoesNotExist {

                        return PlatformOSVersion.manager.get(
                                PlatformOSVersion.SELECT_NAME, key.getRight());
                    }

                    @Override
                    public PlatformOSVersion create(
                            Pair<Long, String> key) {

                        PlatformOSVersion platformOSVersion =
                                new PlatformOSVersion();
                        platformOSVersion.name = key.getRight();
                        platformOSVersion.osId = key.getLeft();
                        platformOSVersion.save();
                        return platformOSVersion;
                    }
                });

    }

    /**
     * Information source capable of providing a {@code DeviceResult}.
     */
    public interface Extractor {
        /**
         * Request device information.
         * @param id Device id
         * @return {@code DeviceResult}
         * @throws Exception
         */
        DeviceResult getDevice(long id) throws Exception;
    }

    /**
     * Loader listener
     */
    public static interface Handler {
        /**
         * Start device extraction
         * @param device Extracted device
         */
        void onStart(Device device);

        /**
         * Finish device extraction
         * @param device Extracted device
         */
        void onFinish(Device device);
    }

    private Cache cache;
    private Extractor extractor;
    private Handler handler;

    /**
     * Initialize {@code DeviceResultLoader}.
     * @param cache Model cache
     * @param extractor Third-party provider
     * @param handler Loader listener
     */
    public DeviceResultLoader(
            Cache cache, Extractor extractor, Handler handler) {

        this.cache = cache;
        this.extractor = extractor;
        this.handler = handler;
    }

    /**
     * Load all the specified devices.
     * @param devices Devices to load
     */
    public void loadAll(List<Device> devices) {
        for (Device device : devices) {
            load(device);
        }
    }

    /**
     * Request, normalize and store the specified device.
     * @param device Device to load
     */
    public void load(Device device) {
        handler.onStart(device);

        DeviceResult result;
        try {
            result = extractor.getDevice(device.id);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }

        // Device
        device.year = result.year;
        device.save();

        // DeviceBattery
        result.battery.deviceId = device.id;
        result.battery.save();

        // DeviceBody
        result.body.deviceId = device.id;
        result.body.simTypeId = cache.simType.get(result.simType.name).id;
        result.body.save();

        // DeviceCamera
        result.camera.deviceId = device.id;
        result.camera.secondary.videoId =
                cache.cameraVideo.get(result.secondaryCameraVideo.name).id;
        result.camera.save();

        // DeviceCom
        result.com.deviceId = device.id;
        result.com.save();

        // DeviceDisplay
        result.display.deviceId = device.id;
        result.display.typeId =
                cache.displayType.get(result.displayType.name).id;
        result.display.protectionId =
                cache.displayProtection.get(result.displayProtection.name).id;
        result.display.save();

        // DeviceRAM
        result.ram.deviceId = device.id;
        result.ram.save();

        // DevicePlatform
        result.platform.deviceId = device.id;
        result.platform.platformChipsetId =
                cache.platformChipset.get(result.chipset.name).id;
        result.platform.cpu.platformCPUTypeId =
                cache.platformCPUType.get(result.cpuType.name).id;
        result.platform.platformGPUId =
                cache.platformGPU.get(result.gpu.name).id;
        result.platform.platformOSId =
                cache.platformOS.get(result.os.name).id;
        result.platform.platformOSCurrentVersionId =
                cache.platformOSVersion.get(Pair.of(
                        result.platform.platformOSId,
                        result.currentVersion.name)).id;
        result.platform.platformOSUpgradeVersionId =
                cache.platformOSVersion.get(Pair.of(
                        result.platform.platformOSId,
                        result.upgradeVersion.name)).id;
        result.platform.save();

        // DeviceSound
        result.sound.deviceId = device.id;
        result.sound.save();

        /*
         * Many-to-many relations
         */

        // CameraFeature
        List<DeviceCameraPrimaryFeature> primaryFeature = new ArrayList<>();
        for (CameraFeature cameraFeature : result.cameraFeature) {
            DeviceCameraPrimaryFeature rel = new DeviceCameraPrimaryFeature();
            rel.deviceId = device.id;
            rel.featureId = cache.cameraFeature.get(cameraFeature.name).id;
            primaryFeature.add(rel);
        }
        DeviceCameraPrimaryFeature.manager.insert(primaryFeature);

        // CameraVideo
        List<DeviceCameraPrimaryVideo> primaryVideo = new ArrayList<>();
        for (CameraVideo cameraVideo : result.cameraVideo) {
            DeviceCameraPrimaryVideo rel = new DeviceCameraPrimaryVideo();
            rel.deviceId = device.id;
            rel.videoId = cache.cameraVideo.get(cameraVideo.name).id;
            primaryVideo.add(rel);
        }
        DeviceCameraPrimaryVideo.manager.insert(primaryVideo);

        // MessagingFeature
        List<DeviceMessagingFeature> messagingFeature = new ArrayList<>();
        for (MessagingFeature feature : result.messagingFeature) {
            DeviceMessagingFeature rel = new DeviceMessagingFeature();
            rel.deviceId = device.id;
            rel.messagingFeatureId =
                    cache.messagingFeature.get(feature.name).id;
            messagingFeature.add(rel);
        }
        DeviceMessagingFeature.manager.insert(messagingFeature);

        // SensorFeature
        List<DeviceSensorFeature> sensorFeature = new ArrayList<>();
        for (SensorFeature feature : result.sensorFeature) {
            DeviceSensorFeature rel = new DeviceSensorFeature();
            rel.deviceId = device.id;
            rel.sensorFeatureId = cache.sensorFeature.get(feature.name).id;
            sensorFeature.add(rel);
        }
        DeviceSensorFeature.manager.insert(sensorFeature);

        // InternalMemory
        List<DeviceInternalMemory> internalMemory = new ArrayList<>();
        for (InternalMemory memory : result.internalMemory) {
            DeviceInternalMemory rel = new DeviceInternalMemory();
            rel.deviceId = device.id;
            rel.internalMemoryId = cache.internalMemory.get(memory.name).id;
            internalMemory.add(rel);
        }
        DeviceInternalMemory.manager.insert(internalMemory);

        // MemorySlot
        List<DeviceMemorySlot> memorySlot = new ArrayList<>();
        for (MemorySlot slot : result.memorySlot) {
            DeviceMemorySlot rel = new DeviceMemorySlot();
            rel.deviceId = device.id;
            rel.memorySlotId = cache.memorySlot.get(slot.name).id;
            memorySlot.add(rel);
        }
        DeviceMemorySlot.manager.insert(memorySlot);

        // Color
        List<DeviceColor> deviceColor = new ArrayList<>();
        List<Long> colorIds = new ArrayList<>();
        for (Color color : result.color) {
            DeviceColor rel = new DeviceColor();
            rel.deviceId = device.id;
            rel.colorId = cache.color.get(color.name).id;
            if (!colorIds.contains(rel.colorId)) {
                colorIds.add(rel.colorId);
                deviceColor.add(rel);
            }
        }
        DeviceColor.manager.insert(deviceColor);

        // NetworkTechnology
        List<DeviceNetworkTechnology> networkTechnology = new ArrayList<>();
        for (NetworkTechnology network : result.networkTechnology) {
            DeviceNetworkTechnology rel = new DeviceNetworkTechnology();
            rel.deviceId = device.id;
            rel.networkTechnologyId =
                    cache.networkTechnology.get(network.name).id;
            networkTechnology.add(rel);
        }
        DeviceNetworkTechnology.manager.insert(networkTechnology);

        handler.onFinish(device);
    }
}