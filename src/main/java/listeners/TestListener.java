package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utilities.DriverFactory;
import utilities.ExtentManager;
import utilities.ScreenshotUtils;

public class TestListener implements ITestListener {
    private final ExtentReports extentReports = ExtentManager.getReport();
    private final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        test.set(extentReports.createTest(result.getMethod().getMethodName()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass("Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest extentTest = test.get();
        extentTest.fail(result.getThrowable());
        try {
            WebDriver driver = DriverFactory.getDriver();
            String screenshotPath = ScreenshotUtils.capture(driver, result.getMethod().getMethodName());
            extentTest.addScreenCaptureFromPath(screenshotPath);
        } catch (RuntimeException exception) {
            extentTest.warning("Screenshot was not captured: " + exception.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().skip(result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        extentReports.flush();
    }
}
