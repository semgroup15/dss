package dss.views.sections.detail.fields.base;

import dss.Developer;
import dss.models.device.Device;

/**
 * Two-way data binding for fields displaying device information.
 */
@Developer({
    Developer.Value.SEBASTIAN,
})
public interface DeviceBinding {
    /**
     * Set values to device.
     *
     * @param device Device
     */
    void syncToDevice(Device device);

    /**
     * Get values from device.
     *
     * @param device Device
     */
    void syncFromDevice(Device device);
}
