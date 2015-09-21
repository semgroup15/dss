package dss.models.device.platform;

import dss.models.Base;

public class DevicePlatform extends Base {

    public DevicePlatformOS os;
    public DevicePlatformVersion version;
    public DevicePlatformVersion upgrade;

    public DevicePlatformChipset chipset;
    public DevicePlatformGPU gpu;
    public DevicePlatformCPU cpu;
}
