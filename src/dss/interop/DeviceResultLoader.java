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
