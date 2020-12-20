import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.*;
import java.nio.file.Path;
import java.util.Base64;

public class Utility {

    public static void savePDFFromBase64(String base64String, String pdfName) {
        File file = new File("./resources/" + pdfName + ".pdf");

        byte[] decoder = Base64.getDecoder().decode(base64String);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(decoder);
            System.out.println("PDF saved at " + file.getCanonicalPath());

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveJson(JsonElement jsonToSave, String jsonName) {

        Path savePath = Path.of("./resources/" + jsonName + ".json");

        byte[] jsonByteArray = jsonToSave.getAsJsonObject().toString().getBytes();

        try {
            FileOutputStream fos = new FileOutputStream(savePath.toFile());
            fos.write(jsonByteArray);
            System.out.println("JSON saved at " + savePath.toFile().getCanonicalPath());

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static JsonElement readJsonFile(File file) {
        try {
            FileReader fileReader = new FileReader(file);

            return JsonParser.parseReader(fileReader);

        } catch(IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}