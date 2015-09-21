package dss.models.device.camera;

import java.util.Set;

import dss.models.Base;

public class DeviceCameraPrimary extends Base {

    public int mp;

    public int width;
    public int height;

    public Set<DeviceCameraFeature> feature;
    public Set<DeviceCameraVideo> video;
}
