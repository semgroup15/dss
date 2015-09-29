package dss.interop;

/**
 * Information source capable of providing a {@code DeviceResult}.
 */
public interface DeviceResultExtractor {
    /**
     * Request device information.
     * @param id Device id
     * @return {@code DeviceResult}
     * @throws Exception
     */
    DeviceResult getDevice(long id) throws Exception;
}
