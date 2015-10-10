package dss.models.device;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import dss.models.Model;
import dss.models.device.battery.DeviceBattery;
import dss.models.device.body.DeviceBody;
import dss.models.device.camera.CameraFeature;
import dss.models.device.camera.CameraVideo;
import dss.models.device.camera.DeviceCamera;
import dss.models.device.com.DeviceCom;
import dss.models.device.display.DeviceDisplay;
import dss.models.device.feature.MessagingFeature;
import dss.models.device.feature.SensorFeature;
import dss.models.device.memory.DeviceRAM;
import dss.models.device.memory.InternalMemory;
import dss.models.device.memory.MemorySlot;
import dss.models.device.misc.Color;
import dss.models.device.network.NetworkTechnology;
import dss.models.device.platform.DevicePlatform;
import dss.models.device.sound.DeviceSound;
import dss.models.manufacturer.Manufacturer;

public class Device extends Model {

    public long id;

    public String name;
    public String imageUrl;
    public int year;

    public long manufacturerId;

    /*
     * Query
     */

    public static class QueryBuilder extends Manager.QueryBuilder {

        public QueryBuilder() {
            super();

            this
                .select().add("SELECT * FROM device").done();
        }

        public QueryBuilder byName(String name) {
            return (QueryBuilder) this
                    .where()
                        .add("device.name LIKE ?", "%" + name + "%")
                        .done();
        }

        public QueryBuilder byManufacturerId(long manufacturerId) {
            return (QueryBuilder) this
                    .where()
                        .add("device.manufacturer_id = ?", manufacturerId)
                        .done();
        }

        public QueryBuilder byManufacturerName(String name) {
            return (QueryBuilder) this
                    .joinManufacturer()
                    .where()
                        .add("manufacturer.name LIKE ?", "%" + name + "%")
                        .done();
        }

        /*
         * Join
         */

        private boolean joinManufacturer = false;

        public QueryBuilder joinManufacturer() {
            if (!joinManufacturer) {
                join().add(
                        "JOIN manufacturer " +
                        "ON manufacturer.id = device.manufacturer_id");
            }
            joinManufacturer = true;
            return this;
        }
    }

    /*
     * FK
     */

    public Manufacturer getManufacturer() throws Model.DoesNotExist {
        return Manufacturer.manager.get(Manufacturer.SELECT_ID, manufacturerId);
    }

    public DeviceBattery getDeviceBattery() throws Model.DoesNotExist {
        return DeviceBattery.manager.get(DeviceBattery.SELECT_ID, id);
    }

    public DeviceBody getDeviceBody() throws Model.DoesNotExist {
        return DeviceBody.manager.get(DeviceBody.SELECT_ID, id);
    }

    public DeviceCamera getDeviceCamera() throws Model.DoesNotExist {
        return DeviceCamera.manager.get(DeviceCamera.SELECT_ID, id);
    }

    public DeviceCom getDeviceCom() throws Model.DoesNotExist {
        return DeviceCom.manager.get(DeviceCom.SELECT_ID, id);
    }

    public DeviceDisplay getDeviceDisplay() throws Model.DoesNotExist {
        return DeviceDisplay.manager.get(DeviceDisplay.SELECT_ID, id);
    }

    public DeviceRAM getDeviceRAM() throws Model.DoesNotExist {
        return DeviceRAM.manager.get(DeviceRAM.SELECT_ID, id);
    }

    public DevicePlatform getDevicePlatform() throws Model.DoesNotExist {
        return DevicePlatform.manager.get(DevicePlatform.SELECT_ID, id);
    }

    public DeviceSound getDeviceSound() throws Model.DoesNotExist {
        return DeviceSound.manager.get(DeviceSound.SELECT_ID, id);
    }

    /*
     * M2M
     */

    public List<CameraFeature> getCameraPrimaryFeature() {
        return CameraFeature.manager.select(
                CameraFeature.SELECT_DEVICE_PRIMARY, id);
    }

    public List<CameraVideo> getCameraPrimaryVideo() {
        return CameraVideo.manager.select(
                CameraVideo.SELECT_DEVICE_PRIMARY, id);
    }

    public List<MessagingFeature> getMessagingFeature() {
        return MessagingFeature.manager.select(
                MessagingFeature.SELECT_DEVICE, id);
    }

    public List<SensorFeature> getSensorFeature() {
        return SensorFeature.manager.select(
                SensorFeature.SELECT_DEVICE, id);
    }

    public List<InternalMemory> getInternalMemory() {
        return InternalMemory.manager.select(
                InternalMemory.SELECT_DEVICE, id);
    }

    public List<MemorySlot> getMemorySlot() {
        return MemorySlot.manager.select(
                MemorySlot.SELECT_DEVICE, id);
    }

    public List<NetworkTechnology> getNetworkTechnology() {
        return NetworkTechnology.manager.select(
                NetworkTechnology.SELECT_DEVICE, id);
    }

    public List<Color> getColor() {
        return Color.manager.select(Color.SELECT_DEVICE, id);
    }

    /*
     * Images
     */

    private static final String IMAGE_BASE_PATH = "./media";
    private static final String[] IMAGE_EXTENSIONS =
        {"gif", "jpeg", "jpg", "png", "bmp"};

    private String getImageFilePath(String extension) {
        return String.format("%s/%d.%s", IMAGE_BASE_PATH, id, extension);
    }

    public File getImageFile() {
        File imageFile;

        for (String extension : IMAGE_EXTENSIONS) {
            imageFile = new File(getImageFilePath(extension));
            if (imageFile.exists()) {
                return imageFile;
            }
        }

        return null;
    }

    public void fetchImageFile() {
        for (String extension : IMAGE_EXTENSIONS) {
            if (imageUrl.endsWith(extension)) {
                String path = getImageFilePath(extension);

                CloseableHttpClient client = HttpClients.createDefault();
                HttpGet request = new HttpGet(imageUrl);

                CloseableHttpResponse response = null;
                try {
                    response = client.execute(request);
                } catch (ClientProtocolException exception) {
                    exception.printStackTrace(System.err);
                    return;
                } catch (IOException exception) {
                    exception.printStackTrace(System.err);
                    return;
                }

                try {
                    InputStream httpStream = response.getEntity().getContent();
                    FileOutputStream fileStream =
                            new FileOutputStream(new File(path));
                    IOUtils.copy(httpStream, fileStream);
                    fileStream.close();
                    client.close();
                } catch (IOException exception) {
                    exception.printStackTrace(System.err);
                    return;
                }

                return;
            }
        }
    }

    /*
     * Manager
     */

    public static Manager<Device> manager = new Manager<>(Device.class);

    @Override
    protected Manager<?> getManager() {
        return manager;
    }

    public static class Loader implements Cache.Loader<Device, Long> {
        @Override
        public Device load(Long key) throws DoesNotExist {
            return manager.get(SELECT_ID, key);
        }

        @Override
        public Device create(Long key) {
            return new Device();
        }
    }

    /*
     * Sync
     */

    @Override
    protected void syncGeneratedKey(Manager.RestrictedResult result)
            throws SQLException {

        throw new Model.NotApplicable();
    }

    @Override
    protected void syncResultSet(Manager.RestrictedResult result)
            throws SQLException {

        id = result.getLong(1);

        name = result.getString(2);
        imageUrl = result.getString(3);
        year = result.getInt(4);

        manufacturerId = result.getLong(5);
    }

    /*
     * Prepare
     */

    @Override
    protected void prepareInsert(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setLong(1, id);

        statement.setString(2, name);
        statement.setString(3, imageUrl);
        statement.setInt(4, year);

        statement.setLong(5, manufacturerId);
    }

    @Override
    protected void prepareUpdate(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setString(1, name);
        statement.setString(2, imageUrl);
        statement.setInt(3, year);

        statement.setLong(4, manufacturerId);

        statement.setLong(5, id);
    }

    @Override
    protected void prepareDelete(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setLong(1, id);
    }

    /*
     * Queries
     */

    public static final String SELECT_ID = "id";
    public static final String SELECT_ALL = "all";
}
