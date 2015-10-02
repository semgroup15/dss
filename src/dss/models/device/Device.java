package dss.models.device;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import dss.models.Model;
import dss.models.manufacturer.Manufacturer;

public class Device extends Model {

    public long id;

    public String name;
    public String imageUrl;
    public int year;

    public long manufacturerId;

    public Manufacturer getManufacturer() throws Model.DoesNotExist {
        return Manufacturer.manager.get(Manufacturer.SELECT_ID, manufacturerId);
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
