package dss.models.device.platform;

public class DevicePlatform {

    public static class CPU {

        public long platformCPUTypeId;

        public double freq;
        public String raw;
    }

    public long deviceId;

    public long platformOSId;
    public long platformOSCurrentVersionId;
    public long platformOSUpagradeVersionId;

    public long platformChipsetId;
    public long platformGPUId;

    public CPU cpu;
}
