package dss.interop;

import java.util.List;

import dss.models.Model;
import dss.models.device.Device;
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
                new Model.Cache<>(new Manufacturer.Loader());;
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
    private Handler handler;

    private DeviceResultExtractor extractor;

    /**
     * Initialize {@code DeviceResultLoader}.
     * @param cache Model cache
     * @param handler Loader listener
     * @param extractor Third-party provider
     */
    public DeviceResultLoader(
            Cache cache, Handler handler,
            DeviceResultExtractor extractor) {

        this.cache = cache;
        this.handler = handler;

        this.extractor = extractor;
    }

    /**
     * Load all the devices specified
     * @param devices Devices to load
     */
    public void loadAll(List<Device> devices) {
        for (Device device : devices) {
            load(device);
        }
    }

    /**
     * Load the specified device
     * @param device Device to load
     */
    public void load(Device device) {
        handler.onStart(device);

        // TODO: Call extractor

        handler.onFinish(device);
    }
}
