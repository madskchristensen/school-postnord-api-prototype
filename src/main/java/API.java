import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class API {
    private static final String apiRoot = "https://atapi2.postnord.com/rest/";
    private static final String apiVersion = "v3";
    private static final String apiKey = "bdc13f1c956b6d61ffc0221b6d722482";

    public static JsonElement sendEdiAndGenerateLabelFromRequest(JsonElement request) {
        final String apiCategory = "shipment";
        final String apiEndpoint = "edi/labels/pdf";
        String papersize = "A4";
        String rotate = "0";
        boolean multiPDF = false;
        boolean pnInfoText = false;
        int labelsPerPage = 100;
        int page = 1;
        boolean processOffline = false;
        String shipmentInformation = request.getAsJsonObject().toString();

        String requestURL = apiRoot + apiCategory + "/" + apiVersion + "/" + apiEndpoint + "?apikey=" + apiKey +
                "&paperSize=" + papersize +
                "&rotate=" + rotate +
                "&multiPDF=" + multiPDF +
                "&pnInfoText=" + pnInfoText +
                "&labelsPerPage=" + labelsPerPage +
                "&page=" + page +
                "&processOffline=" + processOffline;

        try {
            URL ediEndpointUrl = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) ediEndpointUrl.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            try(OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(shipmentInformation.getBytes(StandardCharsets.UTF_8));
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            return JsonParser.parseReader(bufferedReader).getAsJsonObject();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}