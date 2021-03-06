package dss.models.device;

import dss.Developer;
import dss.models.base.Model;
import dss.models.manufacturer.Manufacturer;
import dss.models.price.Price;
import dss.models.review.Review;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * Model for device information.
 */
@Developer({
    Developer.Value.SEBASTIAN,
    Developer.Value.TRIXIE,
})
public class Device extends Model {

    /**
     * Join the string representations of the specified set of objects.
     *
     * @param objects Objects to join
     * @return Comma-separated string
     */
    public static String join(Set<?> objects) {
        return String.join(
                ", ", objects.stream()
                        .map(Object::toString)
                        .collect(Collectors.toList()));
    }

    /**
     * Battery information
     */
    public static class Battery {
        /**
         * Sleep/idle time (hours)
         */
        public int sleep;

        /**
         * Talk time (hours)
         */
        public int talk;

        /**
         * Music time (hours)
         */
        public int music;
    }

    /**
     * Body information
     */
    public static class Body {
        /**
         * Width (mm)
         */
        public double width;

        /**
         * Height (mm)
         */
        public double height;

        /**
         * Depth (mm)
         */
        public double depth;

        /**
         * Weight (grams)
         */
        public double weight;
    }

    /**
     * Camera information
     */
    public static class Camera {

        /**
         * Primary camera
         */
        public static class Primary {
            /**
             * Resolution (megapixels)
             */
            public int mp;

            /**
             * Width (pixels)
             */
            public int width;

            /**
             * Height (pixels)
             */
            public int height;
        }

        /**
         * Secondary camera
         */
        public static class Secondary {
            /**
             * Whether the device has a secondary camera
             */
            public boolean has;

            /**
             * Resolution (megapixels)
             */
            public int mp;
        }

        public Primary primary = new Primary();
        public Secondary secondary = new Secondary();
    }

    /**
     * Communication interfaces information
     */
    public static class Com {

        /**
         * Wireless LAN (WiFi)
         */
        public boolean wlan;

        /**
         * Bluetooth
         */
        public boolean bluetooth;

        /**
         * GPS
         */
        public boolean gps;

        /**
         * Radio (FM)
         */
        public boolean radio;

        /**
         * USB port
         */
        public boolean usb;

        /**
         * Near field communication
         */
        public boolean nfc;

        public enum Item {
            WLAN("WiFi"),
            BLUETOOTH("Bluetooth"),
            GPS("GPS"),
            RADIO("Radio"),
            USB("USB"),
            NFC("NFC");

            public static final Item[] ALL =
                    new Item[]{WLAN, BLUETOOTH, GPS, RADIO, USB, NFC};

            private String name;

            Item(String name) {
                this.name = name;
            }

            public String getName() {
                return name;
            }

            @Override
            public String toString() {
                return name;
            }
        }

        public Set<Item> get() {
            Set<Item> items = new HashSet<>();
            if (wlan) items.add(Item.WLAN);
            if (bluetooth) items.add(Item.BLUETOOTH);
            if (gps) items.add(Item.GPS);
            if (radio) items.add(Item.RADIO);
            if (usb) items.add(Item.USB);
            if (nfc) items.add(Item.NFC);
            return items;
        }

        public void set(Set<Item> items) {
            wlan = items.contains(Item.WLAN);
            bluetooth = items.contains(Item.BLUETOOTH);
            gps = items.contains(Item.GPS);
            radio = items.contains(Item.RADIO);
            usb = items.contains(Item.USB);
            nfc = items.contains(Item.NFC);
        }

        @Override
        public String toString() {
            return join(get());
        }
    }

    /**
     * Sensor information
     */
    public static class Sensor {
        /**
         * Accelerometer
         */
        public boolean accelerometer;

        /**
         * Barometer
         */
        public boolean barometer;

        /**
         * Compass
         */
        public boolean compass;

        /**
         * Gyro
         */
        public boolean gyro;

        /**
         * Magnetometer
         */
        public boolean magnetometer;

        /**
         * Proximity
         */
        public boolean proximity;

        public enum Item {
            ACCELEROMETER("Accelerometer"),
            BAROMETER("Barometer"),
            COMPASS("Compass"),
            GYRO("Gyro"),
            MAGNETOMETER("Magnetometer"),
            PROXIMITY("Proximity");

            public static final Item[] ALL =
                    new Item[]{
                            ACCELEROMETER, BAROMETER, COMPASS, GYRO,
                            MAGNETOMETER, PROXIMITY};

            private String name;

            Item(String name) {
                this.name = name;
            }

            public String getName() {
                return name;
            }

            @Override
            public String toString() {
                return name;
            }
        }

        public Set<Item> get() {
            Set<Item> items = new HashSet<>();
            if (accelerometer) items.add(Item.ACCELEROMETER);
            if (barometer) items.add(Item.BAROMETER);
            if (compass) items.add(Item.COMPASS);
            if (gyro) items.add(Item.GYRO);
            if (magnetometer) items.add(Item.MAGNETOMETER);
            if (proximity) items.add(Item.PROXIMITY);
            return items;
        }

        public void set(Set<Item> items) {
            accelerometer = items.contains(Item.ACCELEROMETER);
            barometer = items.contains(Item.BAROMETER);
            compass = items.contains(Item.COMPASS);
            gyro = items.contains(Item.GYRO);
            magnetometer = items.contains(Item.MAGNETOMETER);
            proximity = items.contains(Item.PROXIMITY);
        }

        @Override
        public String toString() {
            return join(get());
        }
    }

    /**
     * Memory information
     */
    public static class Memory {
        /**
         * RAM size (MB)
         */
        public long ramSize;

        /**
         * Max internal memory (MB)
         */
        public long internalSize;

        /*
         * Available slots
         */
        public boolean sd;
        public boolean microSD;
        public boolean microSDHC;
        public boolean mmc;
        public boolean mmcMobile;

        public enum Item {
            SD("SD"),
            MICRO_SD("MicroSD"),
            MICRO_SDHC("MicroSDHC"),
            MMC("MMC"),
            MMC_MOBILE("MMC Mobile");

            public static final Item[] ALL =
                    new Item[]{SD, MICRO_SD, MICRO_SDHC, MMC, MMC_MOBILE};

            private String name;

            Item(String name) {
                this.name = name;
            }

            public String getName() {
                return name;
            }

            @Override
            public String toString() {
                return name;
            }
        }

        public Set<Item> get() {
            Set<Item> items = new HashSet<>();
            if (sd) items.add(Item.SD);
            if (microSD) items.add(Item.MICRO_SD);
            if (microSDHC) items.add(Item.MICRO_SDHC);
            if (mmc) items.add(Item.MMC);
            if (mmcMobile) items.add(Item.MMC_MOBILE);
            return items;
        }

        public void set(Set<Item> items) {
            sd = items.contains(Item.SD);
            microSD = items.contains(Item.MICRO_SD);
            microSDHC = items.contains(Item.MICRO_SDHC);
            mmc = items.contains(Item.MMC);
            mmcMobile = items.contains(Item.MMC_MOBILE);
        }

        @Override
        public String toString() {
            return join(get());
        }
    }

    /**
     * Network technology information
     */
    public static class Network {
        public boolean gsm;
        public boolean umts;
        public boolean hspa;
        public boolean evdo;
        public boolean cdma;
        public boolean lte;

        public enum Item {
            GSM("GSM"),
            UMTS("UMTS"),
            HSPA("HSPA"),
            EVDO("EVDO"),
            CDMA("CDMA"),
            LTE("LTE");

            public static final Item[] ALL =
                    new Item[]{GSM, UMTS, HSPA, EVDO, CDMA, LTE};

            private String name;

            Item(String name) {
                this.name = name;
            }

            public String getName() {
                return name;
            }

            @Override
            public String toString() {
                return name;
            }
        }

        public Set<Item> get() {
            Set<Item> items = new HashSet<>();
            if (gsm) items.add(Item.GSM);
            if (umts) items.add(Item.UMTS);
            if (hspa) items.add(Item.HSPA);
            if (evdo) items.add(Item.EVDO);
            if (cdma) items.add(Item.CDMA);
            if (lte) items.add(Item.LTE);
            return items;
        }

        public void set(Set<Item> items) {
            gsm = items.contains(Item.GSM);
            umts = items.contains(Item.UMTS);
            hspa = items.contains(Item.HSPA);
            evdo = items.contains(Item.EVDO);
            cdma = items.contains(Item.CDMA);
            lte = items.contains(Item.LTE);
        }

        @Override
        public String toString() {
            return join(get());
        }
    }

    /**
     * Sound information
     */
    public static class Sound {
        /**
         * Whether loudspeaker is available
         */
        public boolean loudspeaker;

        /**
         * Whether 3.5mm jack is available
         */
        public boolean jack35;
    }

    /**
     * Display information
     */
    public static class Display {
        /**
         * Size (inches)
         */
        public double size;

        /**
         * Screen-to-body ratio (percentage)
         */
        public double ratio;

        /**
         * Width (pixels)
         */
        public int width;

        /**
         * Height (pixels)
         */
        public int height;

        /**
         * Density (ppi)
         */
        public int density;

        /**
         * Whether the screen is multitouch
         */
        public boolean multitouch;

        /**
         * Type of display
         */
        public String type;

        /**
         * Display protection
         */
        public String protection;
    }

    /**
     * Available colors
     */
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

        public enum Item {
            BLACK("Black"),
            WHITE("White"),
            BLUE("Blue"),
            RED("Red"),
            PINK("Pink"),
            SILVER("Silver"),
            GRAY("Gray"),
            YELLOW("Yellow"),
            GREEN("Green"),
            GOLD("Gold"),
            ORANGE("Orange");

            public static final Item[] ALL =
                    new Item[]{
                            BLACK, WHITE, BLUE, RED, PINK, SILVER, GRAY,
                            YELLOW, GREEN, GOLD, ORANGE};

            private String name;

            Item(String name) {
                this.name = name;
            }

            public String getName() {
                return name;
            }

            @Override
            public String toString() {
                return name;
            }
        }

        public Set<Item> get() {
            Set<Item> items = new HashSet<>();
            if (black) items.add(Item.BLACK);
            if (white) items.add(Item.WHITE);
            if (blue) items.add(Item.BLUE);
            if (red) items.add(Item.RED);
            if (pink) items.add(Item.PINK);
            if (silver) items.add(Item.SILVER);
            if (gray) items.add(Item.GRAY);
            if (yellow) items.add(Item.YELLOW);
            if (green) items.add(Item.GREEN);
            if (gold) items.add(Item.GOLD);
            if (orange) items.add(Item.ORANGE);
            return items;
        }

        public void set(Set<Item> items) {
            black = items.contains(Item.BLACK);
            white = items.contains(Item.WHITE);
            blue = items.contains(Item.BLUE);
            red = items.contains(Item.RED);
            pink = items.contains(Item.PINK);
            silver = items.contains(Item.SILVER);
            gray = items.contains(Item.GRAY);
            yellow = items.contains(Item.YELLOW);
            green = items.contains(Item.GREEN);
            gold = items.contains(Item.GOLD);
            orange = items.contains(Item.ORANGE);
        }

        @Override
        public String toString() {
            return join(get());
        }
    }

    /**
     * Type of SIM card
     */
    public enum SIMType {
        UNKNOWN("Unknown"),
        FULL_SIZE("Full size"),
        MINI_SIM("Mini SIM"),
        MICRO_SIM("Micro SIM"),
        NANO_SIM("Nano SIM");

        public static final SIMType[] ALL =
                new SIMType[]{FULL_SIZE, MINI_SIM, MICRO_SIM, NANO_SIM};

        private String name;

        SIMType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * Software platform
     */
    public enum Platform {
        NONE(""),
        UNKNOWN("Unknown"),
        ANDROID("Android"),
        IOS("iOS"),
        WINDOWS("Windows");

        public static final Platform[] ALL =
                new Platform[]{ANDROID, IOS, WINDOWS};

        private String name;

        Platform(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private static final long NEW_ID_MIN = 100000;
    private static final long NEW_ID_MAX = 200000;

    public static long newId() {
        Random random = ThreadLocalRandom.current();
        return NEW_ID_MIN + ((long) (random.nextDouble() *
                (NEW_ID_MAX - NEW_ID_MIN)));
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

    public double price;

    public int responsivenessRating;
    public int screenRating;
    public int batteryRating;
    public int overallRating;

    @Override
    public String toString() {
        return String.format("(%d) %s %s", id, getManufacturer().name, name);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }

        if (getClass() != object.getClass()) {
            return false;
        }

        Device device = (Device) object;
        return device.id == id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

    /*
     * Query
     */

    @Developer({
        Developer.Value.SEBASTIAN,
    })
    public static class QueryBuilder extends Manager.QueryBuilder {

        public QueryBuilder() {
            super();

            this.select().add("device.*");
            this.from().add("FROM device");

            this.group().add("device.id");
            this.order().add("year DESC");

            this.withPriceAverage();
            this.withReviewAverage();
            this.withReviewOverallAverage();
        }

        /**
         * Add average price to {@code SELECT} clause.
         *
         * @return This {@code QueryBuilder}
         */
        private QueryBuilder withPriceAverage() {
            return (QueryBuilder) this
                    .joinPrice()
                    .select()
                        .add("AVG(price.cost)")
                        .done();
        }

        /**
         * Add average review ratings to {@code SELECT} clause.
         *
         * @return This {@code QueryBuilder}
         */
        private QueryBuilder withReviewAverage() {
            return (QueryBuilder) this
                    .joinReview()
                    .select()
                        .add("AVG(review.responsiveness)")
                        .add("AVG(review.screen)")
                        .add("AVG(review.battery)")
                        .done();
        }

        /**
         * Add overall average of review ratings to {@code SELECT} clause.
         *
         * @return This {@code QueryBuilder}
         */
        private QueryBuilder withReviewOverallAverage() {
            return (QueryBuilder) this
                    .joinReview()
                    .select()
                        .add("AVG((" +
                                "review.responsiveness + " +
                                "review.screen + " +
                                "review.battery) / 3.0)")
                        .done();
        }

        /**
         * Apply a SQL {@code OFFSET}.
         *
         * @param offset Number of rows to skip.
         * @return This {@code QueryBuilder}
         */
        public QueryBuilder offset(int offset) {
            return (QueryBuilder) this
                    .limit()
                        .add(String.format("OFFSET %d", offset))
                        .done();
        }

        /**
         * Apply a SQL {@code LIMIT}.
         *
         * @param limit Number of rows to take.
         * @return This {@code QueryBuilder}
         */
        public QueryBuilder limit(int limit) {
            return (QueryBuilder) this
                    .limit()
                        .add(String.format("LIMIT %d", limit))
                        .done();
        }

        /**
         * Filter devices containing the specified name.
         *
         * @param name Name by which to filter
         * @return This {@code QueryBuilder}
         * @see Device#name
         */
        public QueryBuilder byName(String name) {
            return (QueryBuilder) this
                    .where()
                        .add("device.name LIKE ?", "%" + name + "%")
                        .done();
        }

        /**
         * Filter devices from the specified manufacturer.
         *
         * @param manufacturerId Manufacturer ID by which to filter
         * @return This {@code QueryBuilder}
         * @see Manufacturer#id
         */
        public QueryBuilder byManufacturerId(long manufacturerId) {
            return (QueryBuilder) this
                    .where()
                        .add("device.manufacturer_id = ?", manufacturerId)
                        .done();
        }

        /**
         * Filter devices from manufacturers containing the specified name.
         *
         * @param name Manufacturer name by which to filter
         * @return This {@code QueryBuilder}
         * @see Manufacturer#name
         */
        public QueryBuilder byManufacturerName(String name) {
            return (QueryBuilder) this
                    .joinManufacturer()
                    .where()
                        .add("manufacturer.name LIKE ?", "%" + name + "%")
                        .done();
        }

        /**
         * Filter devices from the specified platform.
         *
         * @param platform Platform by which to filter
         * @return This {@code QueryBuilder}
         * @see Device.Platform
         */
        public QueryBuilder byPlatform(Platform platform) {
            return (QueryBuilder) this
                    .where()
                        .add("platform = ?", platform.name())
                        .done();
        }

        /**
         * Filter devices by minimum display size.
         *
         * @param value Minimum display size
         * @return This {@code QueryBuilder}
         * @see Device.Display#size
         */
        public QueryBuilder byMinDisplaySize(double value) {
            return (QueryBuilder) this
                    .where()
                        .add("display_size >= ?", value)
                        .done();
        }

        /**
         * Filter devices by maximum display size.
         *
         * @param value Maximum display size
         * @return This {@code QueryBuilder}
         * @see Device.Display#size
         */
        public QueryBuilder byMaxDisplaySize(double value) {
            return (QueryBuilder) this
                    .where()
                        .add("display_size <= ?", value)
                        .done();
        }

        /**
         * Filter devices by minimum RAM size.
         *
         * @param value Minimum RAM size
         * @return This {@code QueryBuilder}
         * @see Device.Memory#ramSize
         */
        public QueryBuilder byMinMemoryRAMSize(double value) {
            return (QueryBuilder) this
                    .where()
                        .add("memory_ram_size >= ?", value)
                        .done();
        }

        /**
         * Filter devices by maximum RAM size.
         *
         * @param value Maximum RAM size
         * @return This {@code QueryBuilder}
         * @see Device.Memory#ramSize
         */
        public QueryBuilder byMaxMemoryRAMSize(double value) {
            return (QueryBuilder) this
                    .where()
                        .add("memory_ram_size <= ?", value)
                        .done();
        }

        /**
         * Filter devices by minimum average price.
         *
         * @param value Minimum price
         * @return This {@code QueryBuilder}
         * @see Price#cost
         */
        public QueryBuilder byMinPrice(double value) {
            return (QueryBuilder) this
                    .having()
                        .add("AVG(price.cost) >= ?", value)
                        .done();
        }

        /**
         * Filter devices by maximum average price.
         *
         * @param value Maximum price
         * @return This {@code QueryBuilder}
         * @see Price#cost
         */
        public QueryBuilder byMaxPrice(double value) {
            return (QueryBuilder) this
                    .having()
                        .add("AVG(price.cost) <= ?", value)
                        .done();
        }

        /**
         * Filter devices by minimum responsiveness rating.
         *
         * @param value Minimum responsiveness rating
         * @return This {@code QueryBuilder}
         * @see Review#responsiveness
         */
        public QueryBuilder byReviewResponsiveness(int value) {
            return (QueryBuilder) this
                    .having()
                        .add("AVG(review.responsiveness) >= ?", value)
                        .done();
        }

        /**
         * Filter devices by minimum screen rating.
         *
         * @param value Minimum screen rating
         * @return This {@code QueryBuilder}
         * @see Review#screen
         */
        public QueryBuilder byReviewScreen(int value) {
            return (QueryBuilder) this
                    .having()
                        .add("AVG(review.screen) >= ?", value)
                        .done();
        }

        /**
         * Filter devices by minimum battery rating.
         *
         * @param value Minimum battery rating
         * @return This {@code QueryBuilder}
         * @see Review#battery
         */
        public QueryBuilder byReviewBattery(int value) {
            return (QueryBuilder) this
                    .having()
                        .add("AVG(review.battery) >= ?", value)
                        .done();
        }

        /**
         * Filter devices by minimum body width.
         *
         * @param value Minimum body width
         * @return This {@code QueryBuilder}
         * @see Device.Body#width
         */
        public QueryBuilder byBodyWidth(double value) {
            return (QueryBuilder) this
                    .where()
                        .add("body_width >= ?", value)
                        .done();
        }

        /**
         * Filter devices by minimum body height.
         *
         * @param value Minimum body height
         * @return This {@code QueryBuilder}
         * @see Device.Body#height
         */
        public QueryBuilder byBodyHeight(double value) {
            return (QueryBuilder) this
                    .where()
                        .add("body_height >= ?", value)
                        .done();
        }

        /**
         * Filter devices by minimum body depth.
         *
         * @param value Minimum body depth
         * @return This {@code QueryBuilder}
         * @see Device.Body#depth
         */
        public QueryBuilder byBodyDepth(double value) {
            return (QueryBuilder) this
                    .where()
                        .add("body_depth <= ?", value)
                        .done();
        }

        /*
         * Join
         */

        /**
         * Join {@code Manufacturer} table.
         *
         * @return This {@code QueryBuilder}
         */
        public QueryBuilder joinManufacturer() {
            if (!joinManufacturer) {
                join().add(
                        "LEFT JOIN manufacturer " +
                        "ON manufacturer.id = device.manufacturer_id");
            }
            joinManufacturer = true;
            return this;
        }

        private boolean joinManufacturer = false;

        /**
         * Join {@code Price} table.
         *
         * @return This {@code QueryBuilder}
         */
        public QueryBuilder joinPrice() {
            if (!joinPrice) {
                join().add(
                        "LEFT JOIN price " +
                        "ON price.device_id = device.id");
            }
            joinPrice = true;
            return this;
        }

        private boolean joinPrice = false;

        /**
         * Join {@code Review} table.
         *
         * @return This {@code QueryBuilder}
         */
        public QueryBuilder joinReview() {
            if (!joinReview) {
                join().add(
                        "LEFT JOIN review " +
                        "ON review.device_id = device.id");
            }
            joinReview = true;
            return this;
        }

        private boolean joinReview = false;
    }

    /**
     * Get device {@code Manufacturer}.
     *
     * @return Manufacturer of this device
     */
    public Manufacturer getManufacturer() {
        return Manufacturer.cache.get(manufacturerId);
    }

    /**
     * Get list of {@code Price} for this device.
     *
     * @return List of available prices
     */
    public List<Price> getPrices() {
        return Price.manager.select(Price.SELECT_DEVICE, id);
    }

    /**
     * Get list of {@code Review} for this device.
     *
     * @return List of reviews
     */
    public List<Review> getReviews() {
        return Review.manager.select(Review.SELECT_DEVICE, id);
    }

    /*
     * Images
     */

    /**
     * Path to directory containing device images.
     */
    private static final String IMAGE_BASE_PATH = "./media";

    /**
     * Possible extensions for image files.
     */
    private static final String[] IMAGE_EXTENSIONS =
            {"gif", "jpeg", "jpg", "png", "bmp"};

    /**
     * Get expected path for an image of this device.
     *
     * @param extension File extension
     * @return Path to device image
     */
    private String getImageFilePath(String extension) {
        return String.format("%s/%d.%s", IMAGE_BASE_PATH, id, extension);
    }

    /**
     * Get the first available image for this device.
     *
     * @return Image {@code File}
     */
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

    /**
     * Fetch the current {@link Device#imageUrl} and save to
     * {@link Device#IMAGE_BASE_PATH}.
     */
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

    /*
     * Cache
     */

    @Developer({
        Developer.Value.SEBASTIAN,
    })
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
     * Observer
     */

    public static Observer<Device> observer = new Observer<>();

    @Override
    protected void insert() {
        super.insert();
        observer.trigger(Observer.Event.INSERT, this);
    }

    @Override
    protected void update() {
        super.update();
        observer.trigger(Observer.Event.UPDATE, this);
    }

    @Override
    public void delete() {
        Price.manager.delete(Price.manager.select(Price.SELECT_DEVICE, id));
        Review.manager.delete(Review.manager.select(Review.SELECT_DEVICE, id));

        super.delete();
        observer.trigger(Observer.Event.DELETE, this);
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

        try {
            /*
             * Price
             */
            price = result.nextDouble();

            /*
             * Reviews
             */
            responsivenessRating = (int) Math.round(result.nextDouble());
            screenRating = (int) Math.round(result.nextDouble());
            batteryRating = (int) Math.round(result.nextDouble());
            overallRating = (int) Math.round(result.nextDouble());

        } catch (SQLException exception) {
            /*
             * {@code QueryBuilder.with...()} methods not called.
             */
        }
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
