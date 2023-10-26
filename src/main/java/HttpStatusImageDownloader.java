import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HttpStatusImageDownloader {
    public static final String DIRECTORY_FOR_SAVE = "img/";

    public void downloadStatusImage(int code) throws FileNotFoundException {
        Path directoryPath = Paths.get(DIRECTORY_FOR_SAVE);
        if(!Files.exists(directoryPath)){
            try {
                Files.createDirectory(directoryPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        String fileUrl = new HttpStatusChecker().getStatusImage(code);
        Path path = Paths.get(DIRECTORY_FOR_SAVE + code + ".jpg");

        try (InputStream inputStream = new URL(fileUrl).openStream()) {
            if (!Files.exists(path)) {
                Files.copy(inputStream, path);
            } else {
                System.out.println("File with code " + code + " already exist!");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
