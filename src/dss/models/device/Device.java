package dss.models.device;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

//import dss.models.price.Price; Todo?
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import dss.models.Model;
import dss.models.manufacturer.Manufacturer;

public class Device extends Model {

    public static class Battery {
        public int sleep;
        public int talk;
        public int music;
    }

    public static class Body {
        public double width;
        public double height;
        public double depth;
        public double weight;
    }

    public static class Camera {

        public static class Primary {
            public int mp;
            public int width;
            public int height;
        }

        public static class Secondary {
            public boolean has;
            public int mp;
        }

        public Primary primary = new Primary();
        public Secondary secondary = new Secondary();
    }

    public static class Com {
        public boolean wlan;
        public boolean bluetooth;
        public boolean gps;
        public boolean radio;
        public boolean usb;
        public boolean nfc;
    }

    public static class Sensor {
        public boolean accelerometer;
        public boolean barometer;
        public boolean compass;
        public boolean gyro;
        public boolean magnetometer;
        public boolean proximity;
    }

    public static class Memory {
        public long ramSize;
        public long internalSize;

        public boolean sd;
        public boolean microSD;
        public boolean microSDHC;
        public boolean mmc;
        public boolean mmcMobile;
    }

    public static class Network {
        public boolean gsm;
        public boolean umts;
        public boolean hspa;
        public boolean evdo;
        public boolean cdma;
        public boolean lte;
    }

    public static class Sound {
        public boolean loudspeaker;
        public boolean jack35;
    }

    public static class Display {
        public double size;
        public double ratio;

        public int width;
        public int height;
        public int density;

        public boolean multitouch;

        public String type;
        public String protection;
    }

    public static class Color {
        public boolean black;
        public boolean white;
        public boolean blue;
        public boolean red;
        public boolean pink;
        public boolean silver;
        public boolean gray;
        public boolean yellow;
        public boolean green;
        public boolean gold;
        public boolean orange;
    }

    public enum SIMType {
        UNKNOWN,
        FULL_SIZE,
        MINI_SIM,
        MICRO_SIM,
        NANO_SIM;
    }

    public enum Platform {
        UNKNOWN,
        ANDROID,
        IOS,
        WINDOWS;
    }

    public long id;

    public String name;
    public String imageUrl;
    public int year;

    public long manufacturerId;

    public Battery battery = new Battery();
    public Body body = new Body();
    public Camera camera = new Camera();
    public Com com = new Com();
    public Sensor sensor = new Sensor();
    public Memory memory = new Memory();
    public Network network = new Network();
    public Sound sound = new Sound();
    public Display display = new Display();
    public Color color = new Color();

    public SIMType simType = SIMType.UNKNOWN;
    public Platform platform = Platform.UNKNOWN;

    /*
     * Query
     */

    public static class QueryBuilder extends Manager.QueryBuilder {

        public QueryBuilder() {
            super();

            this.select().add("SELECT * FROM device").done();
        }

        public QueryBuilder limit(int limit) {
            return (QueryBuilder) this
                    .limit()
                        .add(String.format("LIMIT %d", limit))
                        .done();
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

    public Manufacturer getManufacturer() {
        return Manufacturer.cache.get(manufacturerId);
    }

    /*
    public List<Price> getPrices() {
        return Price.manager.select(Price.SELECT_DEVICE, id);
    }
    */

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

    public static Model.Cache<Device, Long> cache =
            new Model.Cache<>(new Loader());

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

        id = result.nextLong();

        name = result.nextString();
        imageUrl = result.nextString();
        year = result.nextInt();

        manufacturerId = result.nextLong();

        /*
         * Battery
         */

        battery.sleep = result.nextInt();
        battery.talk = result.nextInt();
        battery.music = result.nextInt();

        /*
         * Body
         */

        body.width = result.nextDouble();
        body.height = result.nextDouble();
        body.depth = result.nextDouble();
        body.weight = result.nextDouble();

        /*
         * Camera
         */

        camera.primary.mp = result.nextInt();
        camera.primary.width = result.nextInt();
        camera.primary.height = result.nextInt();

        camera.secondary.has = result.nextBoolean();
        camera.secondary.mp = result.nextInt();

        /*
         * Com
         */

        com.wlan = result.nextBoolean();
        com.bluetooth = result.nextBoolean();
        com.gps = result.nextBoolean();
        com.radio = result.nextBoolean();
        com.usb = result.nextBoolean();
        com.nfc = result.nextBoolean();

        /*
         * Sensor
         */

        sensor.accelerometer = result.nextBoolean();
        sensor.barometer = result.nextBoolean();
        sensor.compass = result.nextBoolean();
        sensor.gyro = result.nextBoolean();
        sensor.magnetometer = result.nextBoolean();
        sensor.proximity = result.nextBoolean();

        /*
         * Memory
         */

        memory.ramSize = result.nextLong();
        memory.internalSize = result.nextLong();

        memory.sd = result.nextBoolean();
        memory.microSD = result.nextBoolean();
        memory.microSDHC = result.nextBoolean();
        memory.mmc = result.nextBoolean();
        memory.mmcMobile = result.nextBoolean();

        /*
         * Network
         */

        network.gsm = result.nextBoolean();
        network.umts = result.nextBoolean();
        network.hspa = result.nextBoolean();
        network.evdo = result.nextBoolean();
        network.cdma = result.nextBoolean();
        network.lte = result.nextBoolean();

        /*
         * Sound
         */

        sound.loudspeaker = result.nextBoolean();
        sound.jack35 = result.nextBoolean();

        /*
         * Display
         */

        display.size = result.nextDouble();
        display.ratio = result.nextDouble();

        display.width = result.nextInt();
        display.height = result.nextInt();
        display.density = result.nextInt();

        display.multitouch = result.nextBoolean();

        display.type = result.nextString();
        display.protection = result.nextString();

        /*
         * Color
         */

        color.black = result.nextBoolean();
        color.white = result.nextBoolean();
        color.blue = result.nextBoolean();
        color.red = result.nextBoolean();
        color.pink = result.nextBoolean();
        color.silver = result.nextBoolean();
        color.gray = result.nextBoolean();
        color.yellow = result.nextBoolean();
        color.green = result.nextBoolean();
        color.gold = result.nextBoolean();
        color.orange = result.nextBoolean();

        /*
         * Other
         */

        simType = SIMType.valueOf(result.nextString());
        platform = Platform.valueOf(result.nextString());
    }

    /*
     * Prepare
     */

    private void prepareCommon(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setNextString(name);
        statement.setNextString(imageUrl);
        statement.setNextInt(year);

        statement.setNextLong(manufacturerId);

        /*
         * Battery
         */

        statement.setNextInt(battery.sleep);
        statement.setNextInt(battery.talk);
        statement.setNextInt(battery.music);

        /*
         * Body
         */

        statement.setNextDouble(body.width);
        statement.setNextDouble(body.height);
        statement.setNextDouble(body.depth);
        statement.setNextDouble(body.weight);

        /*
         * Camera
         */

        statement.setNextInt(camera.primary.mp);
        statement.setNextInt(camera.primary.width);
        statement.setNextInt(camera.primary.height);

        statement.setNextBoolean(camera.secondary.has);
        statement.setNextInt(camera.secondary.mp);

        /*
         * Com
         */

        statement.setNextBoolean(com.wlan);
        statement.setNextBoolean(com.bluetooth);
        statement.setNextBoolean(com.gps);
        statement.setNextBoolean(com.radio);
        statement.setNextBoolean(com.usb);
        statement.setNextBoolean(com.nfc);

        /*
         * Sensor
         */

        statement.setNextBoolean(sensor.accelerometer);
        statement.setNextBoolean(sensor.barometer);
        statement.setNextBoolean(sensor.compass);
        statement.setNextBoolean(sensor.gyro);
        statement.setNextBoolean(sensor.magnetometer);
        statement.setNextBoolean(sensor.proximity);

        /*
         * Memory
         */

        statement.setNextLong(memory.ramSize);
        statement.setNextLong(memory.internalSize);

        statement.setNextBoolean(memory.sd);
        statement.setNextBoolean(memory.microSD);
        statement.setNextBoolean(memory.microSDHC);
        statement.setNextBoolean(memory.mmc);
        statement.setNextBoolean(memory.mmcMobile);

        /*
         * Network
         */

        statement.setNextBoolean(network.gsm);
        statement.setNextBoolean(network.umts);
        statement.setNextBoolean(network.hspa);
        statement.setNextBoolean(network.evdo);
        statement.setNextBoolean(network.cdma);
        statement.setNextBoolean(network.lte);

        /*
         * Sound
         */

        statement.setNextBoolean(sound.loudspeaker);
        statement.setNextBoolean(sound.jack35);

        /*
         * Display
         */

        statement.setNextDouble(display.size);
        statement.setNextDouble(display.ratio);

        statement.setNextInt(display.width);
        statement.setNextInt(display.height);
        statement.setNextInt(display.density);

        statement.setNextBoolean(display.multitouch);

        statement.setNextString(display.type);
        statement.setNextString(display.protection);

        /*
         * Color
         */

        statement.setNextBoolean(color.black);
        statement.setNextBoolean(color.white);
        statement.setNextBoolean(color.blue);
        statement.setNextBoolean(color.red);
        statement.setNextBoolean(color.pink);
        statement.setNextBoolean(color.silver);
        statement.setNextBoolean(color.gray);
        statement.setNextBoolean(color.yellow);
        statement.setNextBoolean(color.green);
        statement.setNextBoolean(color.gold);
        statement.setNextBoolean(color.orange);

        /*
         * Other
         */

        statement.setNextString(simType.name());
        statement.setNextString(platform.name());
    }

    @Override
    protected void prepareInsert(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setNextLong(id);

        prepareCommon(statement);
    }

    @Override
    protected void prepareUpdate(Manager.RestrictedStatement statement)
            throws SQLException {

        prepareCommon(statement);

        statement.setNextLong(id);
    }

    @Override
    protected void prepareDelete(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setNextLong(id);
    }

    /*
     * Queries
     */

    public static final String SELECT_ID = "id";
    public static final String SELECT_ALL = "all";
}
