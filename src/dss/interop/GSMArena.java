package dss.interop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.Pair;
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
import dss.models.manufacturer.Manufacturer;

/**
 * GSMArena data extraction.
 */
public class GSMArena implements DeviceLoader.Extractor {

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

    @Override
    public DeviceResult getDevice(long id) throws Exception {
        DeviceResult result = new DeviceResult();

        String content = get(String.format(DEVICE_URL, id));
        Document document = Jsoup.parse(content);

        extractDeviceYear(document, result);
        extractSIMType(document, result);
        extractDisplayType(document, result);
        extractDisplayProtection(document, result);
        extractCameraFeature(document, result);
        extractCameraVideo(document, result);
        extractInternalMemory(document, result);
        extractMemorySlot(document, result);
        extractColor(document, result);
        extractNetworkTechnology(document, result);
        extractPlatformChipset(document, result);
        extractPlatformCPUType(document, result);
        extractPlatformGPU(document, result);
        extractPlatformOS(document, result);
        extractDeviceBattery(document, result);
        extractDeviceBody(document, result);
        extractPrimaryDeviceCamera(document, result);
        extractSecondaryDeviceCamera(document, result);
        extractMessagingFeature(document, result);
        extractSensorFeature(document, result);
        extractDeviceCom(document, result);
        extractDeviceDisplay(document, result);
        extractDeviceSound(document, result);

        return result;
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

    private void extractDeviceYear(
            Document document, DeviceResult result) {

        // Device -> Year
        Matcher matcher = extractFieldRegex(
                document, "announced", "^([0-9]*)");
        if (matcher != null) {
            try {
                result.year = Integer.parseInt(matcher.group(1));
            } catch (NumberFormatException e) {
            }
        }
    }

    private void extractSIMType(
            Document document, DeviceResult result) {

        // SIM type
        result.simTypeName = extractField(document, "sim");
    }

    private void extractDisplayType(
            Document document, DeviceResult result) {

        // Display type
        result.displayTypeName = extractField(document, "type");
    }

    private void extractDisplayProtection(
            Document document, DeviceResult result) {

        // Display protection
        result.displayProtectionName = extractField(document, "protection");
    }

    private void extractCameraFeature(
            Document document, DeviceResult result) {

        // CameraFeature
        String field = extractField(document, "features");
        for (String value : field.split(",")) {
            result.cameraFeatureNames.add(value.trim().toLowerCase());
        }
    }

    private void extractCameraVideo(
            Document document, DeviceResult result) {

        // CameraVideo
        String field = extractField(document, "video");
        for (String value : field.split(",")) {
            result.cameraVideoNames.add(value.trim().toLowerCase());
        }
    }

    private void extractInternalMemory(
            Document document, DeviceResult result) {

        // InternalMemory, DeviceRAM
        Matcher matcher = extractFieldRegex(
                document, "internal",
                "([0-9\\/]*) (GB|MB), ([0-9]*) (GB|MB) RAM");
        if (matcher != null) {
            String unit = matcher.group(2);
            for (String value : matcher.group(1).split("/")) {
                int mb = Integer.valueOf(value);
                if (unit.equals("GB")) mb *= 1000;
                result.internalMemoryPairs.add(Pair.of(value + " " + unit, mb));
            }

            try {
                int ram = Integer.parseInt(matcher.group(3));
                unit = matcher.group(4);
                if (unit.equals("GB")) ram *= 1000;
                result.ram.size = ram;
            } catch (NumberFormatException e) {
            }
        }
    }

    private void extractMemorySlot(
            Document document, DeviceResult result) {

        // MemorySlot
        Matcher matcher = extractFieldRegex(
                document, "card slot", "([a-zA-Z]*)");
        if (matcher != null) {
            String field = matcher.group(1).trim();
            if (!field.toLowerCase().equals("no")) {
                result.memorySlotNames.add(field);
            }
        }
    }

    private void extractColor(
            Document document, DeviceResult result) {

        // Color
        String field = extractField(document, "colors");
        for (String value : field.split(",")) {
            result.colorNames.add(value.trim().toLowerCase());
        }
    }

    private void extractNetworkTechnology(
            Document document, DeviceResult result) {

        // NetworkTechnology
        String field = extractField(document, "technology");
        for (String value : field.split("/")) {
            result.networkTechnologyNames.add(value.trim().toLowerCase());
        }
    }

    private void extractPlatformChipset(
            Document document, DeviceResult result) {

        // PlatformChipset
        result.chipsetName = extractField(document, "chipset");
    }

    private void extractPlatformCPUType(
            Document document, DeviceResult result) {

        // PlatformCPUType
        result.platform.cpu.raw = extractField(document, "cpu");
        Matcher matcher = extractFieldRegex(
                document, "cpu",
                "([a-zA-Z\\- ]*)([0-9]*.[0-9]) (GHz|MHz)");
        if (matcher != null) {
            result.cpuTypeName = matcher.group(1).trim();

            try {
                double freq = Double.parseDouble(matcher.group(2));
                String unit = matcher.group(3);
                if (unit.equals("MHz")) freq /= 1000;
                result.platform.cpu.freq = freq;
            } catch (NumberFormatException e) {
            }
        }
    }

    private void extractPlatformGPU(
            Document document, DeviceResult result) {

        // PlatformGPU
        result.gpuName = extractField(document, "gpu");
    }

    private void extractPlatformOS(
            Document document, DeviceResult result) {

        // PlatformOS, PlatformOSVersion
        Matcher matcher = extractFieldRegex(
                document, "os",
                "([a-zA-Z]*)([^,]*)?," +
                "( ([^,^u]*),?)?( ?upgradable to (.*))?");
        if (matcher != null) {
            result.osName = matcher.group(1);
            result.osCurrentVersionName = matcher.group(4);
            result.osUpgradeVersionName = matcher.group(6);
        }
    }

    private void extractDeviceBattery(
            Document document, DeviceResult result) {

        Matcher matcher;

        // DeviceBattery
        matcher = extractFieldRegex(document, "stand-by", "([0-9]*) h");
        if (matcher != null) {
            result.battery.sleep = Integer.valueOf(matcher.group(1));
        }
        matcher = extractFieldRegex(document, "talk time", "([0-9]*) h");
        if (matcher != null) {
            result.battery.talk = Integer.valueOf(matcher.group(1));
        }
        matcher = extractFieldRegex(document, "music play", "([0-9]*) h");
        if (matcher != null) {
            result.battery.music = Integer.valueOf(matcher.group(1));
        }
    }

    private void extractDeviceBody(
            Document document, DeviceResult result) {

        // DeviceBody -> height, width, depth
        Matcher matcher = extractFieldRegex(
                document, "dimensions",
                "([0-9]*.?[0-9]*) x ([0-9]*.?[0-9]*) x ([0-9]*.?[0-9]*) mm");
        if (matcher != null) {
            try {
                result.body.height = Double.parseDouble(matcher.group(1));
                result.body.width = Double.parseDouble(matcher.group(2));
                result.body.depth = Double.parseDouble(matcher.group(3));
            } catch (NumberFormatException e) {
            }
        }

        // DeviceBody -> weight
        matcher = extractFieldRegex(document, "weight", "([0-9]*.?[0-9]*) g");
        if (matcher != null) {
            try {
                result.body.weight = Double.parseDouble(matcher.group(1));
            } catch (NumberFormatException e) {
            }
        }
    }

    private void extractPrimaryDeviceCamera(
            Document document, DeviceResult result) {

        // DeviceCamera -> primary
        Matcher matcher = extractFieldRegex(
                document, "primary",
                "([0-9]*) MP, ([0-9]*) x ([0-9]*) pixels");
        if (matcher != null) {
            try {
                result.camera.primary.mp =
                        Integer.parseInt(matcher.group(1));
                result.camera.primary.width =
                        Integer.parseInt(matcher.group(2));
                result.camera.primary.height =
                        Integer.parseInt(matcher.group(3));
            } catch (NumberFormatException e) {
            }
        }
    }

    private void extractSecondaryDeviceCamera(
            Document document, DeviceResult result) {

        // DeviceCamera -> secondary
        Matcher matcher = extractFieldRegex(
                document, "secondary", "([0-9]*) MP(, ([^,]*))?");
        if (matcher != null) {
            result.camera.secondary.mp = Integer.valueOf(matcher.group(1));
            result.camera.secondary.has = result.camera.secondary.mp > 0;
            result.secondaryCameraVideoName = matcher.group(3);
        }
    }

    private void extractMessagingFeature(
            Document document, DeviceResult result) {

        // MessagingFeature
        String field = extractField(document, "messaging");
        for (String value : field.split(",")) {
            result.messagingFeatureNames.add(value.trim().toLowerCase());
        }
    }

    private static void extractSensorFeature(
            Document document, DeviceResult result) {

        // SensorFeature
        String field = extractField(document, "sensors");
        for (String value : field.split(",")) {
            result.sensorFeatureNames.add(value.trim().toLowerCase());
        }
    }

    private static void extractDeviceCom(
            Document document, DeviceResult result) {

        // DeviceCom
        result.com.wlan = !extractField(
                document, "wlan").trim().toLowerCase().equals("no");
        result.com.bluetooth = !extractField(
                document, "bluetooth").trim().toLowerCase().equals("no");
        result.com.gps = !extractField(
                document, "gps").trim().toLowerCase().equals("no");
        result.com.nfc = !extractField(
                document, "nfc").trim().toLowerCase().equals("no");
        result.com.radio = !extractField(
                document, "radio").trim().toLowerCase().equals("no");
        result.com.usb = !extractField(
                document, "usb").trim().toLowerCase().equals("no");
    }

    private void extractDeviceDisplay(
            Document document, DeviceResult result) {

        Matcher matcher;

        // DeviceDisplay -> size, ratio
        matcher = extractFieldRegex(
                document, "size",
                "([0-9]*.[0-9]*) inches " +
                "\\(~?([0-9]*.[0-9]*)% screen-to-body ratio\\)");
        if (matcher != null) {
            try {
                result.display.size = Double.parseDouble(matcher.group(1));
                result.display.ratio = Double.parseDouble(matcher.group(2));
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
                result.display.width = Integer.parseInt(matcher.group(1));
                result.display.height = Integer.parseInt(matcher.group(2));
                result.display.density = Integer.parseInt(matcher.group(3));
            } catch (NumberFormatException e) {
            }
        }

        // DeviceDisplay -> multitouch
        result.display.multitouch = extractField(
                document, "multitouch").trim().toLowerCase().equals("yes");
    }

    private void extractDeviceSound(
            Document document, DeviceResult result) {

        // DeviceSound
        result.sound.loudspeaker = extractField(
                document, "loudspeaker").trim().toLowerCase().equals("yes");
        result.sound.jack35 = extractField(
                document, "3.5mm jack").trim().toLowerCase().equals("yes");
    }
}