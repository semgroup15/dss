package dss.interop;

import java.util.List;

import dss.models.Model;
import dss.models.device.Device;
import dss.models.manufacturer.Manufacturer;

/**
 * Helper for loading devices provided by a {@code DeviceResultExtractor}.
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

    public DeviceResultLoader(
            Cache cache, Handler handler,
            DeviceResultExtractor extractor) {

        this.cache = cache;
        this.handler = handler;

        this.extractor = extractor;
    }

    public void loadAll(List<Device> devices) {
        for (Device device : devices) {
            load(device);
        }
    }

    public void load(Device device) {
        handler.onStart(device);

        // TODO: Call extractor

        handler.onFinish(device);
    }
}
