import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HttpImageStatusCliTest {

    private HttpImageStatusCli cli;

    @BeforeEach
    void setUp() {
        cli = new HttpImageStatusCli();
    }

    @ParameterizedTest
    @ValueSource(strings = {"101", "200", "404"})
    void askStatusCorrectCodeTest(String inputString) {
        InputStream stdIn = System.in;
        try {
            System.setIn(new ByteArrayInputStream(inputString.getBytes()));
            cli.askStatus();
            assertTrue(Files.exists(Paths.get("img/" + inputString + ".jpg")));
        } finally {
            System.setIn(stdIn);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1000", "1000"})
    void askStatusIncorrectCodeTest(String inputString) {
        InputStream stdIn = System.in;
        try {
            System.setIn(new ByteArrayInputStream(inputString.getBytes()));
            cli.askStatus();
            assertFalse(Files.exists(Paths.get("img/" + inputString + ".jpg")));
        } finally {
            System.setIn(stdIn);
        }
    }

    @Test
    void askStatusIncorrectUserInputTest() {
        String[] inputLines = new String[]{"error", "text", "201"};
        String expected = inputLines[2];

        InputStream stdin = System.in;
        try {
            System.setIn(new ByteArrayInputStream(String.join("\n", inputLines).getBytes()));
            cli.askStatus();
            assertTrue(Files.exists(Paths.get("img/" + expected + ".jpg")));
        } finally {
            System.setIn(stdin);
        }
    }
}