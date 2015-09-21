package dss.models.device.memory;

import java.util.Set;

import dss.models.Base;

public class DeviceMemory extends Base {

    public Set<DeviceMemorySlot> slot;
    public Set<DeviceMemoryInternal> internal;
    public int ram;
}
