import com.google.gson.*;

import java.io.*;

/*
    1. Find en måde at håndtere JSON i java. F.eks. via et library
    2. Mulighed for at generere JSON i java. F.eks. hvis library kan konvertere objekter til JSON:
        i. Lav objekt(er)
        ii. Konverter objekt(er) til JSON
    3. Send JSON + api key til EDI endpoint der returnerer PDF
    4. Find en måde at læse værdier fra JSON. I dette tilfælde evt. gem de forskellige relevante værdier + hent base64 string og lav pdf af den
 */

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