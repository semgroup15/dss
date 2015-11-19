package dss.views;

import dss.models.device.Device;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton providing a global application state.
 *
 * <ul>
 *   <li>Visible location</li>
 *   <li>Device selection</li>
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
     * Access level
     */
    public enum Level {
        USER,
        ADMIN,
    }

    public interface LocationListener {
        /**
         * Change current location.
         * @param location Location
         */
        void onLocationChange(Location location);
    }

    public interface DeviceListener {
        /**
         * Add device.
         * @param device Device to add
         */
        void onDeviceAdd(Device device);

        /**
         * Remove device.
         * @param device Device to remove
         */
        void onDeviceRemove(Device device);
    }

    public interface LevelListener {
        /**
         * Change access level.
         * @param level Access level
         */
        void onLevelChange(Level level);
    }

    private List<LocationListener> locationListeners = new ArrayList<>();
    private List<DeviceListener> deviceListeners = new ArrayList<>();
    private List<LevelListener> levelListeners = new ArrayList<>();

    // Initial state
    private Location location = new Location(Location.Section.LISTING);
    private List<Device> devices = new ArrayList<>();
    private Level level = Level.USER;

    /*
     * Location
     */

    public void addLocationListener(LocationListener listener) {
        locationListeners.add(listener);
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
     * Devices
     */

    public void addDeviceListener(DeviceListener listener) {
        deviceListeners.add(listener);
    }

    public void removeDeviceListener(DeviceListener listener) {
        deviceListeners.remove(listener);
    }

    /**
     * Add device to selection.
     * @param device Device to add
     */
    public void addDevice(Device device) {
        if (!devices.contains(device)) {
            devices.add(device);

            for (DeviceListener listener : deviceListeners) {
                listener.onDeviceAdd(device);
            }
        }
    }

    /**
     * Remove device from selection.
     * @param device Device to remove
     */
    public void removeDevice(Device device) {
        if (devices.contains(device)) {
            devices.remove(device);

            for (DeviceListener listener : deviceListeners) {
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
