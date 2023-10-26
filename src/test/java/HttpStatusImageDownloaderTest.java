import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HttpStatusImageDownloaderTest {
    private HttpStatusImageDownloader downloader;

    @BeforeEach
    void setUp() {
        downloader = new HttpStatusImageDownloader();
    }

    @ParameterizedTest
    @ValueSource(ints = {101, 200, 300, 500, 404})
    void downloadStatusImageCorrectTest(int code) throws FileNotFoundException {
        downloader.downloadStatusImage(code);
        assertTrue(Files.exists(Paths.get("img/" + code + ".jpg")));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1000, 1000})
    void downloadStatusImageFailTest(int code) {
        assertThrows(FileNotFoundException.class, () -> downloader.downloadStatusImage(code));
    }
}