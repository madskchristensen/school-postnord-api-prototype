import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;

public class JsonUtility {

    public static void createPdfFromBase64(String base64String, String pdfName) {
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

    public static void saveJson(String json, Path savePath) {

        byte[] jsonByteArray = json.getBytes();

        try {
            FileOutputStream fos = new FileOutputStream(savePath.toFile());
            fos.write(jsonByteArray);
            System.out.println("JSON saved at " + savePath.toAbsolutePath());

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static String readJsonFile(File file) {
        try {
            StringBuilder stringBuilder = new StringBuilder();

            List<String> jsonStringList = Files.readAllLines(file.toPath());

            for(String string : jsonStringList) {
                stringBuilder.append(string);
            }

            System.out.println("Read json at " + file.getCanonicalPath());

            return stringBuilder.toString();

        } catch(IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}