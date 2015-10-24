package dss.ga;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
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
import dss.models.manufacturer.Manufacturer;

/**
 * GSMArena data extraction.
 */
public class GSMArena {

    private static final String ENCODING = "UTF-8";

    private static final String QUICK_SEARCH_URL =
            "http://www.gsmarena.com/quicksearch-6376.jpg";
    private static final String DEVICE_URL =
            "http://www.gsmarena.com/X-%d.php";

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
    private String get(String url) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);

        CloseableHttpResponse response;
        try {
            response = client.execute(request);
        } catch (IOException exception) {
            throw new Exception(exception);
        }

        String content;
        try {
            content = IOUtils.toString(
                    response.getEntity().getContent(), ENCODING);
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
    public QuickSearchResult getQuickSearch() throws Exception {
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

    public Device updateDevice(Device device) throws Exception {
        String content = get(String.format(DEVICE_URL, device.id));
        Document document = Jsoup.parse(content);

        extractDeviceImage(document, device);
        extractDeviceYear(document, device);
        extractSIMType(document, device);
        extractDisplayType(document, device);
        extractDisplayProtection(document, device);
        extractInternalMemory(document, device);
        extractMemorySlot(document, device);
        extractColor(document, device);
        extractNetworkTechnology(document, device);
        extractPlatformOS(document, device);
        extractDeviceBattery(document, device);
        extractDeviceBody(document, device);
        extractPrimaryDeviceCamera(document, device);
        extractSecondaryDeviceCamera(document, device);
        extractSensor(document, device);
        extractDeviceCom(document, device);
        extractDeviceDisplay(document, device);
        extractDeviceSound(document, device);

        return device;
    }

    /**
     * Extract the specified field.
     * @param document Parsed HTML document
     * @param field GSMArena field
     * @return Field value
     */
    private static String extractField(Document document, String field) {
        String selector = String.format(
                "tr:has(.ttl:matches((?i)^%s$))", field);
        return document.select(selector).select(".nfo").text();
    }

    /**
     * Extract the specified field value and match against regex.
     * @param document Parsed HTML document
     * @param field GSMArena field
     * @param regex Regular expression to match
     * @return Regex {@code Matcher}
     */
    private static Matcher extractFieldRegex(
            Document document, String field, String regex) {

        String value = extractField(document, field);

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        if (matcher.find()) {
            return matcher;
        } else {
            return null;
        }
    }

    /*
     * Extraction methods
     */

    private void extractDeviceImage(
            Document document, Device device) {

        String url = document.select(".specs-photo-main img").attr("src");
        device.imageUrl = url;
    }

    private void extractDeviceYear(
            Document document, Device device) {

        // Device -> Year
        Matcher matcher = extractFieldRegex(
                document, "announced", "([0-9]{4})");
        if (matcher != null) {
            try {
                device.year = Integer.parseInt(matcher.group(1));
            } catch (NumberFormatException e) {
            }
        }
    }

    private void extractSIMType(
            Document document, Device device) {

        // SIM type
        String value = extractField(document, "sim").toLowerCase();

        if (value.contains("nano")) {
            device.simType = Device.SIMType.NANO_SIM;
        } else if (value.contains("micro")) {
            device.simType = Device.SIMType.MICRO_SIM;
        } else if (value.contains("mini")) {
            device.simType = Device.SIMType.MINI_SIM;
        } else {
            device.simType = Device.SIMType.FULL_SIZE;
        }
    }

    private void extractDisplayType(
            Document document, Device device) {

        // Display type
        device.display.type = extractField(document, "type");
    }

    private void extractDisplayProtection(
            Document document, Device device) {

        // Display protection
        device.display.protection = extractField(document, "protection");
    }

    private void extractInternalMemory(
            Document document, Device device) {

        // InternalMemory, DeviceRAM
        Matcher matcher = extractFieldRegex(
                document, "internal",
                "([0-9\\/?]*) (GB|MB)(, ([0-9]*) (GB|MB) RAM)?");
        if (matcher != null) {
            String unit = matcher.group(2);
            for (String value : matcher.group(1).split("/")) {
                try {
                    int mb = Integer.parseInt(value);
                    if (unit.equals("GB")) mb *= 1000;
                    device.memory.internalSize = mb;
                } catch (NumberFormatException e) {
                }
            }

            try {
                int mb = Integer.parseInt(matcher.group(4));
                unit = matcher.group(5);
                if (unit.equals("GB")) mb *= 1000;
                device.memory.ramSize = mb;
            } catch (NumberFormatException e) {
            }
        }
    }

    private void extractMemorySlot(
            Document document, Device device) {

        // MemorySlot
        Matcher matcher = extractFieldRegex(
                document, "card slot", "([a-zA-Z]*)");
        if (matcher != null) {
            String value = matcher.group(1).trim().toLowerCase();

            if (value.contains("mmc")) {
                if (value.contains("mobile")) {
                    device.memory.mmcMobile = true;
                } else {
                    device.memory.mmc = true;
                }
            }

            if (value.contains("sd")) {
                if (value.contains("micro")) {
                    device.memory.microSD = true;
                } else if (value.contains("sdhc")) {
                    device.memory.microSDHC = true;
                } else {
                    device.memory.sd = true;
                }
            }
        }
    }

    private void extractColor(
            Document document, Device device) {

        // Color
        String value = extractField(document, "colors").toLowerCase();
        device.color.black = value.contains("black");
        device.color.white = value.contains("white");
        device.color.blue = value.contains("blue");
        device.color.red = value.contains("red");
        device.color.pink = value.contains("pink");
        device.color.silver = value.contains("silver");
        device.color.gray = value.contains("gray");
        device.color.yellow = value.contains("yellow");
        device.color.green = value.contains("green");
        device.color.gold = value.contains("gold");
        device.color.orange = value.contains("orange");
    }

    private void extractNetworkTechnology(
            Document document, Device device) {

        // NetworkTechnology
        String value = extractField(document, "technology").toLowerCase();
        device.network.gsm = value.contains("gsm");
        device.network.umts = value.contains("umts");
        device.network.hspa = value.contains("hspa");
        device.network.evdo = value.contains("evdo");
        device.network.cdma = value.contains("cdma");
        device.network.lte = value.contains("lte");
    }

    private void extractPlatformOS(
            Document document, Device device) {

        // NetworkTechnology
        String value = extractField(document, "os").toLowerCase();

        if (value.contains("android")) {
            device.platform = Device.Platform.ANDROID;
        }

        if (value.contains("ios")) {
            device.platform = Device.Platform.IOS;
        }

        if (value.contains("windows")) {
            device.platform = Device.Platform.WINDOWS;
        }
    }

    private void extractDeviceBattery(
            Document document, Device device) {

        Matcher matcher;

        // DeviceBattery
        matcher = extractFieldRegex(document, "stand-by", "([0-9]*) h");
        if (matcher != null) {
            device.battery.sleep = Integer.valueOf(matcher.group(1));
        }
        matcher = extractFieldRegex(document, "talk time", "([0-9]*) h");
        if (matcher != null) {
            device.battery.talk = Integer.valueOf(matcher.group(1));
        }
        matcher = extractFieldRegex(document, "music play", "([0-9]*) h");
        if (matcher != null) {
            device.battery.music = Integer.valueOf(matcher.group(1));
        }
    }

    private void extractDeviceBody(
            Document document, Device device) {

        // DeviceBody -> height, width, depth
        Matcher matcher = extractFieldRegex(
                document, "dimensions",
                "([0-9]*.?[0-9]*) x ([0-9]*.?[0-9]*) x ([0-9]*.?[0-9]*) mm");
        if (matcher != null) {
            try {
                device.body.height = Double.parseDouble(matcher.group(1));
                device.body.width = Double.parseDouble(matcher.group(2));
                device.body.depth = Double.parseDouble(matcher.group(3));
            } catch (NumberFormatException e) {
            }
        }

        // DeviceBody -> weight
        matcher = extractFieldRegex(document, "weight", "([0-9]*.?[0-9]*) g");
        if (matcher != null) {
            try {
                device.body.weight = Double.parseDouble(matcher.group(1));
            } catch (NumberFormatException e) {
            }
        }
    }

    private void extractPrimaryDeviceCamera(
            Document document, Device device) {

        // DeviceCamera -> primary
        Matcher matcher = extractFieldRegex(
                document, "primary",
                "([0-9]*) MP, ([0-9]*) x ([0-9]*) pixels");
        if (matcher != null) {
            try {
                device.camera.primary.mp =
                        Integer.parseInt(matcher.group(1));
                device.camera.primary.width =
                        Integer.parseInt(matcher.group(2));
                device.camera.primary.height =
                        Integer.parseInt(matcher.group(3));
            } catch (NumberFormatException e) {
            }
        }
    }

    private void extractSecondaryDeviceCamera(
            Document document, Device device) {

        // DeviceCamera -> secondary
        Matcher matcher = extractFieldRegex(
                document, "secondary", "([0-9]*) MP(, ([^,]*))?");
        if (matcher != null) {
            device.camera.secondary.mp = Integer.valueOf(matcher.group(1));
            device.camera.secondary.has = device.camera.secondary.mp > 0;
        }
    }

    private static void extractSensor(
            Document document, Device device) {

        // Sensor
        String value = extractField(document, "sensors").toLowerCase();
        device.sensor.accelerometer = value.contains("accelerometer");
        device.sensor.barometer = value.contains("barometer");
        device.sensor.compass = value.contains("compass");
        device.sensor.gyro = value.contains("gyro");
        device.sensor.magnetometer = value.contains("magnetometer");
        device.sensor.proximity = value.contains("proximity");
    }

    private static void extractDeviceCom(
            Document document, Device device) {

        // DeviceCom
        String value;

        value = extractField(document, "wlan").trim().toLowerCase();
        device.com.wlan = !(value.isEmpty() || value.equals("no"));

        value = extractField(document, "bluetooth").trim().toLowerCase();
        device.com.bluetooth = !(value.isEmpty() || value.equals("no"));

        value = extractField(document, "gps").trim().toLowerCase();
        device.com.gps = !(value.isEmpty() || value.equals("no"));

        value = extractField(document, "nfc").trim().toLowerCase();
        device.com.nfc = !(value.isEmpty() || value.equals("no"));

        value = extractField(document, "radio").trim().toLowerCase();
        device.com.radio = !(value.isEmpty() || value.equals("no"));

        value = extractField(document, "usb").trim().toLowerCase();
        device.com.usb = !(value.isEmpty() || value.equals("no"));
    }

    private void extractDeviceDisplay(
            Document document, Device device) {

        Matcher matcher;

        // DeviceDisplay -> size, ratio
        matcher = extractFieldRegex(
                document, "size",
                "([0-9]*.[0-9]*) inches " +
                "\\(~?([0-9]*.[0-9]*)% screen-to-body ratio\\)");
        if (matcher != null) {
            try {
                device.display.size = Double.parseDouble(matcher.group(1));
                device.display.ratio = Double.parseDouble(matcher.group(2));
            } catch (NumberFormatException e) {
            }
        }

        // DeviceDisplay -> width, height
        matcher = extractFieldRegex(
                document, "resolution",
                "([0-9]*) x ([0-9]*) pixels " +
                "\\(~([0-9]*) ppi pixel density\\)");
        if (matcher != null) {
            try {
                device.display.width = Integer.parseInt(matcher.group(1));
                device.display.height = Integer.parseInt(matcher.group(2));
                device.display.density = Integer.parseInt(matcher.group(3));
            } catch (NumberFormatException e) {
            }
        }

        // DeviceDisplay -> multitouch
        device.display.multitouch = extractField(
                document, "multitouch").trim().toLowerCase().equals("yes");
    }

    private void extractDeviceSound(
            Document document, Device device) {

        // DeviceSound
        device.sound.loudspeaker = !extractField(
                document, "loudspeaker").trim().toLowerCase().equals("no");
        device.sound.jack35 = !extractField(
                document, "3.5mm jack").trim().toLowerCase().equals("no");
    }
}
