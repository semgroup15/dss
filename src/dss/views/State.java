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

        public Manufacturer getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(Manufacturer manufacturer) {
            this.manufacturer = manufacturer;
        }

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        /**
         * Get criteria as a device query.
         * @return Item query
         */
        public Device.QueryBuilder asDeviceQuery() {
            Device.QueryBuilder query = new Device.QueryBuilder();

            if (this.manufacturer != null) {
                query.byManufacturerId(this.manufacturer.id);
            }

            if (this.query != null && !this.query.isEmpty()) {
                query.byName(this.query);
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
