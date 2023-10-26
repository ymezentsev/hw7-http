import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpStatusCheckerTest {
    private HttpStatusChecker checker;

    @BeforeEach
    void setUp() {
        checker = new HttpStatusChecker();
    }

    @ParameterizedTest
    @ValueSource(ints = {101, 200, 300, 500, 404})
    void getStatusImageCorrectTest(int code) throws FileNotFoundException {
        String catUri = "https://http.cat";
        String expected = catUri + "/" + code + ".jpg";

        assertEquals(expected, checker.getStatusImage(code));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1000, 1000})
    void getStatusImageFailTest(int code) {
        assertThrows(FileNotFoundException.class, () -> checker.getStatusImage(code));
    }
}