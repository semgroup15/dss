package dss.models.device.feature;

import java.util.Set;

import dss.models.Base;

public class DeviceFeature extends Base {

    public Set<DeviceFeatureSensor> sensor;
    public Set<DeviceFeatureMessaging> messaging;
}
