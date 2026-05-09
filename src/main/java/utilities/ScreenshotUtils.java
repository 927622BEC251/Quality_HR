package utilities;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ScreenshotUtils {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private ScreenshotUtils() {
    }

    public static String capture(WebDriver driver, String testName) {
        try {
            Files.createDirectories(Path.of("screenshots"));
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String safeName = testName.replaceAll("[^a-zA-Z0-9._-]", "_");
            Path destination = Path.of("screenshots",
                    safeName + "_" + LocalDateTime.now().format(FORMATTER) + ".png");
            Files.copy(source.toPath(), destination);
            return destination.toAbsolutePath().toString();
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to capture screenshot", exception);
        }
    }
}
