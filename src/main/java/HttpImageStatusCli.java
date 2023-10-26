import java.io.FileNotFoundException;
import java.util.Scanner;

public class HttpImageStatusCli {
    public void askStatus() {
        int code = getUserInput();

        HttpStatusImageDownloader downloader = new HttpStatusImageDownloader();
        try {
            downloader.downloadStatusImage(code);
            System.out.println("Image for HTTP status " + code + " has been downloaded");
        } catch (FileNotFoundException e) {
            System.out.println("There is not image for HTTP status " + code);
        }
    }

    private int getUserInput() {
        System.out.println("Enter HTTP status code");
        try (Scanner scanner = new Scanner(System.in)) {
            while (!scanner.hasNextInt()) {
                System.out.println("Please enter valid number");
                scanner.next();
            }
            return scanner.nextInt();
        }
    }
}
