package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.nio.file.Files;
import java.nio.file.Path;

public final class ExtentManager {
    private static ExtentReports extentReports;

    private ExtentManager() {
    }

    public static synchronized ExtentReports getReport() {
        if (extentReports == null) {
            try {
                Files.createDirectories(Path.of("reports"));
            } catch (Exception exception) {
                throw new IllegalStateException("Unable to create reports directory", exception);
            }
            ExtentSparkReporter reporter = new ExtentSparkReporter("reports/QualityHR_ExtentReport.html");
            reporter.config().setDocumentTitle("QualityHR Automation Report");
            reporter.config().setReportName("QualityHR Test Execution");

            extentReports = new ExtentReports();
            extentReports.attachReporter(reporter);
            extentReports.setSystemInfo("Application", "OrangeHRM");
            extentReports.setSystemInfo("Browser", ConfigReader.get("browser"));
        }
        return extentReports;
    }
}
