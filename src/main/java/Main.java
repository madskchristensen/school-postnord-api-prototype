import com.google.gson.*;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        File jsonFile = new File("./resources/jsonEdiRequest.json");

        System.out.println("Loading JSON file..");

        JsonElement jsonRequest = Utility.readJsonFile(jsonFile);

        System.out.println("Sending POST request to https://atapi2.postnord.com/rest/shipment/v3/edi/labels/pdf");
        JsonElement jsonResponse = API.sendEdiAndGenerateLabelFromRequest(jsonRequest);

        System.out.println("Extracting base64 string from response..");
        String base64FromJson = jsonResponse.getAsJsonObject().get("labelPrintout").getAsJsonArray().get(0).getAsJsonObject().get("printout").getAsJsonObject().get("data").getAsString();

        System.out.println("Converting base64 to PDF..");
        Utility.savePDFFromBase64(base64FromJson, "testpdf");

        System.out.println("Saving response..");
        Utility.saveJson(jsonResponse, "responseJSON");
    }
}