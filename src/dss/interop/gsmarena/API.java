package dss.interop.gsmarena;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dss.models.device.Device;
import dss.models.device.battery.DeviceBattery;
import dss.models.device.body.DeviceBody;
import dss.models.device.body.SIMType;
import dss.models.device.camera.CameraFeature;
import dss.models.device.camera.CameraVideo;
import dss.models.device.camera.DeviceCamera;
import dss.models.device.com.DeviceCom;
import dss.models.device.display.DeviceDisplay;
import dss.models.device.display.DisplayProtection;
import dss.models.device.display.DisplayType;
import dss.models.device.feature.MessagingFeature;
import dss.models.device.feature.SensorFeature;
import dss.models.device.memory.DeviceRAM;
import dss.models.device.memory.InternalMemory;
import dss.models.device.memory.MemorySlot;
import dss.models.device.misc.Color;
import dss.models.device.network.NetworkTechnology;
import dss.models.device.platform.DevicePlatform;
import dss.models.device.platform.PlatformCPUType;
import dss.models.device.platform.PlatformChipset;
import dss.models.device.platform.PlatformGPU;
import dss.models.device.platform.PlatformOS;
import dss.models.device.platform.PlatformOSVersion;
import dss.models.device.sound.DeviceSound;
import dss.models.manufacturer.Manufacturer;

/**
 * GSMArena data extraction.
 */
public class API {

    private static final String ENCODING = "UTF-8";

    private static final String QUICK_SEARCH_URL =
            "http://www.gsmarena.com/quicksearch-6376.jpg";
    private static final String DEVICE_URL =
            "http://www.gsmarena.com/X-%d.php";
    private static final String RELATED_URL =
            "http://www.gsmarena.com/related.php3?idPhone=%d";

    /**
     * API exception.
     */
    public static class Exception extends java.lang.Exception {
        private static final long serialVersionUID = 1L;

        public Exception(Throwable caught) {
            super(caught);
        }
    }

    /**
     * Make HTTP {@code GET} request.
     * @param url URL
     * @return Response
     * @throws Exception
     */
    private static String get(String url) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);

        CloseableHttpResponse response;
        try {
            response = client.execute(request);
        } catch (ClientProtocolException exception) {
            throw new Exception(exception);
        } catch (IOException exception) {
            throw new Exception(exception);
        }

        String content;
        try {
            content = IOUtils.toString(
                    response.getEntity().getContent(), ENCODING);
        } catch (UnsupportedOperationException exception) {
            throw new Exception(exception);
        } catch (IOException exception) {
            throw new Exception(exception);
        }

        return content;
    }

    /**
     * Result of a quick search request.
     */
    public static class QuickSearchResult {
        public List<Manufacturer> manufacturers = new ArrayList<>();
        public List<Device> devices = new ArrayList<>();
    }

    /**
     * Request quick search.
     * @return Search result
     * @throws Exception
     */
    public static QuickSearchResult getQuickSearch() throws Exception {
        QuickSearchResult result = new QuickSearchResult();

        String content = get(QUICK_SEARCH_URL);
        JsonParser parser = new JsonParser();
        JsonArray data = parser.parse(content).getAsJsonArray();

        JsonObject manufacturers = data.get(0).getAsJsonObject();

        for (Entry<String, JsonElement> entry : manufacturers.entrySet()) {
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.id = Long.parseLong(entry.getKey());
            manufacturer.name = entry.getValue().getAsString();

            result.manufacturers.add(manufacturer);
        }

        JsonArray devices = data.get(1).getAsJsonArray();

        for (JsonElement element : devices) {
            JsonArray array = element.getAsJsonArray();

            Device device = new Device();
            device.manufacturerId = array.get(0).getAsLong();
            device.id = array.get(1).getAsLong();
            device.name = array.get(2).getAsString();

            result.devices.add(device);
        }

        return result;
    }

    /**
     * Result of a device request.
     */
    public static class DeviceResult {

        Manufacturer manufacturer = new Manufacturer();
        Device device = new Device();

        SIMType simType = new SIMType();

        DisplayType displayType = new DisplayType();
        DisplayProtection displayProtection = new DisplayProtection();

        /*
         * Many-to-many relations
         */

        List<CameraFeature> cameraFeature = new ArrayList<>();
        List<CameraVideo> cameraVideo = new ArrayList<>();

        List<MessagingFeature> messagingFeature = new ArrayList<>();
        List<SensorFeature> sensorFeature = new ArrayList<>();

        List<InternalMemory> internalMemory = new ArrayList<>();
        List<MemorySlot> memorySlot = new ArrayList<>();

        List<Color> color = new ArrayList<>();
        List<NetworkTechnology> networkTechnology = new ArrayList<>();

        /*
         * Platform information
         */

        PlatformChipset chipset = new PlatformChipset();
        PlatformCPUType cpuType = new PlatformCPUType();
        PlatformGPU gpu = new PlatformGPU();

        PlatformOS os = new PlatformOS();
        PlatformOSVersion currentVersion = new PlatformOSVersion();
        PlatformOSVersion upgradeVersion = new PlatformOSVersion();

        /*
         * Device information
         */

        DeviceBattery battery = new DeviceBattery();
        DeviceBody body = new DeviceBody();
        DeviceCamera camera = new DeviceCamera();
        DeviceCom com = new DeviceCom();
        DeviceDisplay display = new DeviceDisplay();
        DeviceRAM ram = new DeviceRAM();
        DevicePlatform platform = new DevicePlatform();
        DeviceSound sound = new DeviceSound();
    }

    private static String extractField(Document document, String field) {
        String selector = String.format(
                "tr:has(.ttl:matches((?i)^%s$))", field);
        return document.select(selector).select(".nfo").text();
    }

    private static Matcher extractFieldRegex(
            Document document, String field, String regex) {

        String value = extractField(document, field);

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        matcher.find();

        return matcher;
    }

    /**
     * Request device.
     * @param id GSMArena id
     * @return Device data
     * @throws Exception
     */
    public static DeviceResult getDevice(long id) throws Exception{
        DeviceResult result = new DeviceResult();

        String content = get(String.format(DEVICE_URL, id));
        Document document = Jsoup.parse(content);

        String field;
        Matcher matcher;

        // Device -> Year
        matcher = extractFieldRegex(document, "announced", "^([0-9]*)");
        result.device.year = Integer.parseInt(matcher.group(1));

        // SIM type
        result.simType.name = extractField(document, "sim");

        // Display type
        result.displayType.name = extractField(document, "type");

        // Protection type
        result.displayProtection.name = extractField(document, "protection");

        // NetworkTechnology
        field = extractField(document, "technology");
        for (String value : field.split("/")) {
            NetworkTechnology networkTechnology = new NetworkTechnology();
            networkTechnology.name = value.trim();
            result.networkTechnology.add(networkTechnology);
        }

        // PlatformChipset
        result.chipset.name = extractField(document, "chipset");

        // PlatformCPUType
        result.platform.cpu.raw = extractField(document, "cpu");
        matcher = extractFieldRegex(
                document, "cpu",
                "([a-zA-Z\\- ]*)([0-9]*.[0-9]) (GHz|MHz)");
        result.cpuType.name = matcher.group(1).trim();
        double freq = Double.parseDouble(matcher.group(2));
        String unit = matcher.group(3);
        if (unit.equals("MHz")) freq /= 1000;
        result.platform.cpu.freq = freq;

        // PlatformGPU
        result.gpu.name = extractField(document, "gpu");

        // PlatformOS, PlatformOSVersion
        matcher = extractFieldRegex(
                document, "os",
                "([a-zA-Z ]*)([^,]*)?,( ([^,^u]*),?)?( ?upgradable to (.*))?");
        result.os.name = matcher.group(1);
        result.currentVersion.name = matcher.group(4);
        result.upgradeVersion.name = matcher.group(6);

        // DeviceBody -> height, width, depth
        matcher = extractFieldRegex(
                document, "dimensions",
                "([0-9]*.?[0-9]*) x ([0-9]*.?[0-9]*) x ([0-9]*.?[0-9]*) mm");
        result.body.height = Double.parseDouble(matcher.group(1));
        result.body.width = Double.parseDouble(matcher.group(2));
        result.body.depth = Double.parseDouble(matcher.group(3));

        // DeviceBody -> weight
        matcher = extractFieldRegex(document, "weight", "([0-9]*.?[0-9]*) g");
        result.body.weight = Double.parseDouble(matcher.group(1));

        // DeviceDisplay -> size, ratio
        matcher = extractFieldRegex(
                document, "size",
                "([0-9]*.[0-9]*) inches " +
                "\\(~?([0-9]*.[0-9]*)% screen-to-body ratio\\)");
        result.display.size = Double.parseDouble(matcher.group(1));
        result.display.ratio = Double.parseDouble(matcher.group(2));

        // DeviceDisplay -> width, height
        matcher = extractFieldRegex(
                document, "resolution",
                "([0-9]*) x ([0-9]*) pixels \\(~([0-9]*) ppi pixel density\\)");
        result.display.width = Integer.parseInt(matcher.group(1));
        result.display.height = Integer.parseInt(matcher.group(2));
        result.display.density = Integer.parseInt(matcher.group(3));

        // DeviceDisplay -> multitouch
        result.display.multitouch = extractField(
                document, "multitouch").trim().toLowerCase().equals("yes");

        return result;
    }
}
