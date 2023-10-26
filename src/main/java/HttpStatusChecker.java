import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpStatusChecker {
    public static final String CAT_URI = "https://http.cat";

    public String getStatusImage(int code) throws FileNotFoundException {
        String resultUri = CAT_URI + "/" + code + ".jpg";

        HttpClient client = HttpClient.newBuilder().build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(java.net.URI.create(resultUri))
                .GET()
                .build();

        int responseCode = 0;
        try {
            responseCode = client.send(request, HttpResponse.BodyHandlers.ofString()).statusCode();
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }

        if (responseCode == 404) {
            throw new FileNotFoundException("File with code " + code + " not found!");
        } else {
            return resultUri;
        }
    }
}
