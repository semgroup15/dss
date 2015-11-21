package dss.views.sections.detail.fields.base;

import dss.models.device.Device;

/**
 * Two-way data binding for fields displaying device information.
 */
public interface DeviceBinding {
    /**
     * Set values to device.
     * @param device Device
     */
    void syncToDevice(Device device);

    /**
     * Get values from device.
     * @param device Device
     */
    void syncFromDevice(Device device);
}
