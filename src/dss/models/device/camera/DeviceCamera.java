package dss.models.device.camera;

public class DeviceCamera {

    public static class Primary {

        public int mp;

        public int width;
        public int height;
    }

    public static class Secondary {

        public boolean has;
        public int mp;

        public long videoId;
    }

    public long deviceId;

    public Primary primary;
    public Secondary secondary;
}
