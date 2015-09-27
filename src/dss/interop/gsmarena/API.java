package dss.interop.gsmarena;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dss.models.device.Device;
import dss.models.manufacturer.Manufacturer;

/**
 * GSMArena data extraction.
 */
public class API {

    private static final String ENCODING = "UTF-8";

    private static final String QUICK_SEARCH_URL =
            "http://www.gsmarena.com/quicksearch-6376.jpg";
    private static final String DEVICE_URL =
            "http://www.gsmarena.com/X-{id}.php";

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
     * Result of a quick search request.
     */
    public static class QuickSearchResult {
        public List<Manufacturer> manufacturers = new ArrayList<>();
        public List<Device> devices = new ArrayList<>();
    }

    /**
     * Request quick search.
     * @return Search result
     */
    public static QuickSearchResult getQuickSearch() throws Exception {
        /*
         * Make request
         */

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(QUICK_SEARCH_URL);

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

        /*
         * Parse response
         */

        QuickSearchResult result = new QuickSearchResult();

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
     * Request device.
     * @param id GSMArena id
     * @return Device data
     */
    public static Device getDevice(int id) {
        return null;
    }
}
