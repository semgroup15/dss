package dss.views;

import dss.models.device.Device;
import dss.models.manufacturer.Manufacturer;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton providing a global application state.
 *
 * <ul>
 *   <li>Visible location</li>
 *   <li>Item selection</li>
 *   <li>Access level</li>
 * </ul>
 */
public class State {

    private static State state;

    /**
     * Get single {@code State} instance.
     * @return Single instance
     */
    public static State get() {
        if (state == null) {
            state = new State();
        }

        return state;
    }

    /**
     * Location
     */
    public static class Location {

        public enum Section {
            LISTING,
            DETAIL,
            COMPARISON,
        }

        private Section section;
        private Object[] data;

        /**
         * Initialize {@code Location}.
         * @param section Section to be displayed
         * @param data Arbitrary data
         */
        public Location(Section section, Object... data) {
            this.section = section;
            this.data = data;
        }

        /**
         * Get displayed {@code Section}.
         * @return Section
         */
        public Section getSection() {
            return section;
        }

        /**
         * Get arbitrary data.
         * @return Data
         */
        public Object[] getData() {
            return data;
        }
    }

    /**
     * Search criteria.
     */
    public static class Criteria {

        private Manufacturer manufacturer;
        private String query;

        private Device.Platform platform;

        private int responsivenessRating;
        private int screenRating;
        private int batteryRating;

        private double width;
        private double height;
        private double depth;

        private double minDisplaySize;
        private double maxDisplaySize;

        private double minMemoryRAMSize;
        private double maxMemoryRAMSize;

        private double minPrice;
        private double maxPrice;

        public void setManufacturer(Manufacturer manufacturer) {
            this.manufacturer = manufacturer;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        public void setPlatform(Device.Platform platform) {
            this.platform = platform;
        }

        public void setResponsivenessRating(int responsivenessRating) {
            this.responsivenessRating = responsivenessRating;
        }

        public void setScreenRating(int screenRating) {
            this.screenRating = screenRating;
        }

        public void setBatteryRating(int batteryRating) {
            this.batteryRating = batteryRating;
        }

        public void setWidth(double width) {
            this.width = width;
        }

        public void setHeight(double height) {
            this.height = height;
        }

        public void setDepth(double depth) {
            this.depth = depth;
        }

        public void setDisplaySize(double min, double max) {
            this.minDisplaySize = min;
            this.maxDisplaySize = max;
        }

        public void setMemoryRAMSize(double min, double max) {
            this.minMemoryRAMSize = min;
            this.maxMemoryRAMSize = max;
        }

        public void setPrice(double min, double max) {
            this.minPrice = min;
            this.maxPrice = max;
        }

        /**
         * Get criteria as a device query.
         * @return Device query
         */
        public Device.QueryBuilder asDeviceQuery() {
            Device.QueryBuilder query = new Device.QueryBuilder();

            if (this.manufacturer != null) {
                query.byManufacturerId(this.manufacturer.id);
            }

            if (this.query != null && !this.query.isEmpty()) {
                query.byName(this.query);
            }

            if (this.platform != null &&
                    this.platform != Device.Platform.UNKNOWN) {
                query.byPlatform(this.platform);
            }

            /*
             * Review ratings
             */

            if (responsivenessRating > 0) {
                query.byReviewResponsiveness(responsivenessRating);
            }

            if (screenRating > 0) {
                query.byReviewScreen(screenRating);
            }

            if (batteryRating > 0) {
                query.byReviewBattery(batteryRating);
            }

            /*
             * Dimensions
             */

            if (width > 0) {
                query.byBodyWidth(width);
            }

            if (height > 0) {
                query.byBodyHeight(height);
            }

            if (depth > 0) {
                query.byBodyDepth(depth);
            }

            /*
             * Display size
             */

            if (minDisplaySize > 0 || maxDisplaySize > 0) {
                query.byDisplaySize(minDisplaySize, maxDisplaySize);
            }

            /*
             * RAM size
             */

            if (minMemoryRAMSize > 0 || maxMemoryRAMSize > 0) {
                query.byMemoryRAMSize(minMemoryRAMSize, maxMemoryRAMSize);
            }

            /*
             * Price
             */

            if (maxPrice > 0 || minPrice > 0) {
                query.byPrice(minPrice, maxPrice);
            }

            return query;
        }
    }

    /**
     * Access level
     */
    public enum Level {
        USER,
        ADMIN,
    }

    /**
     * Location change listener.
     */
    public interface LocationListener {
        /**
         * Change current location.
         * @param location Location
         */
        void onLocationChange(Location location);
    }

    /**
     * Search criteria change listener.
     */
    public interface SearchListener {
        /**
         * Change current search criteria.
         * @param criteria Criteria
         */
        void onSearchChange(Criteria criteria);
    }

    /**
     * Item selection listener.
     */
    public interface SelectionListener {
        /**
         * Add device.
         * @param device Item to add
         */
        void onDeviceAdd(Device device);

        /**
         * Remove device.
         * @param device Item to remove
         */
        void onDeviceRemove(Device device);
    }

    /**
     * Access level change listener.
     */
    public interface LevelListener {
        /**
         * Change access level.
         * @param level Access level
         */
        void onLevelChange(Level level);
    }

    private List<LocationListener> locationListeners = new ArrayList<>();
    private List<SearchListener> searchListeners = new ArrayList<>();
    private List<SelectionListener> selectionListeners = new ArrayList<>();
    private List<LevelListener> levelListeners = new ArrayList<>();

    // Initial state
    private Location location = new Location(Location.Section.LISTING);
    private Criteria criteria = new Criteria();
    private List<Device> devices = new ArrayList<>();
    private Level level = Level.USER;

    /*
     * Location
     */

    public void addLocationListener(LocationListener listener) {
        locationListeners.add(listener);

        listener.onLocationChange(location);
    }

    public void removeLocationListener(LocationListener listener) {
        locationListeners.remove(listener);
    }

    public void setLocation(Location location) {
        this.location = location;

        for (LocationListener listener : locationListeners) {
            listener.onLocationChange(location);
        }
    }

    public Location getLocation() {
        return location;
    }

    /*
     * Search
     */

    public void addSearchListener(SearchListener listener) {
        searchListeners.add(listener);

        listener.onSearchChange(criteria);
    }

    public void removeSearchListener(SearchListener listener) {
        searchListeners.remove(listener);
    }

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;

        for (SearchListener listener : searchListeners) {
            listener.onSearchChange(criteria);
        }
    }

    public Criteria getCriteria() {
        return criteria;
    }

    /*
     * Devices
     */

    public void addSelectionListener(SelectionListener listener) {
        selectionListeners.add(listener);

        for (Device device : devices) {
            listener.onDeviceAdd(device);
        }
    }

    public void removeSelectionListener(SelectionListener listener) {
        selectionListeners.remove(listener);
    }

    /**
     * Add device to selection.
     * @param device Item to add
     */
    public void addDevice(Device device) {
        if (!devices.contains(device)) {
            devices.add(device);

            for (SelectionListener listener : selectionListeners) {
                listener.onDeviceAdd(device);
            }
        }
    }

    /**
     * Remove device from selection.
     * @param device Item to remove
     */
    public void removeDevice(Device device) {
        if (devices.contains(device)) {
            devices.remove(device);

            for (SelectionListener listener : selectionListeners) {
                listener.onDeviceRemove(device);
            }
        }
    }

    /**
     * Get selected devices.
     * @return Devices
     */
    public List<Device> getDevices() {
        return devices;
    }

    /*
     * Level
     */

    public void addLevelListener(LevelListener listener) {
        levelListeners.add(listener);

        listener.onLevelChange(level);
    }

    public void removeLevelListener(LevelListener listener) {
        levelListeners.remove(listener);
    }

    /**
     * Set access level.
     * @param level Access level
     */
    public void setLevel(Level level) {
        if (level != this.level) {
            this.level = level;

            for (LevelListener listener : levelListeners) {
                listener.onLevelChange(level);
            }
        }
    }

    /**
     * Get access level.
     * @return Access level
     */
    public Level getLevel() {
        return level;
    }
}
