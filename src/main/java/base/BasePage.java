package base;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utilities.WaitUtils;

import java.util.List;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final WaitUtils waits;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.waits = new WaitUtils(driver);
    }

    protected WebElement waitForElement(By locator) {
        return waits.visible(locator);
    }

    protected void click(By locator) {
        try {
            waits.clickable(locator).click();
        } catch (StaleElementReferenceException exception) {
            waits.clickable(locator).click();
        }
    }

    protected void type(By locator, String value) {
        WebElement element = waitForElement(locator);
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(Keys.BACK_SPACE);
        element.sendKeys(value);
    }

    protected String text(By locator) {
        return waitForElement(locator).getText().trim();
    }

    protected boolean isDisplayed(By locator) {
        try {
            return waitForElement(locator).isDisplayed();
        } catch (RuntimeException exception) {
            return false;
        }
    }

    protected boolean isEnabled(By locator) {
        try {
            return waitForElement(locator).isEnabled();
        } catch (RuntimeException exception) {
            return false;
        }
    }

    protected int count(By locator) {
        return driver.findElements(locator).size();
    }

    protected int visibleCount(By locator) {
        return waits.until(currentDriver -> currentDriver.findElements(locator).stream()
                .filter(WebElement::isDisplayed)
                .toList()
                .size());
    }

    protected void waitForLoaderToFinish() {
        By loader = By.cssSelector(".oxd-loading-spinner");
        if (count(loader) > 0) {
            waits.invisible(loader);
        }
    }

    protected List<WebElement> elements(By locator) {
        return driver.findElements(locator);
    }

    protected boolean pageContains(String value) {
        return driver.getPageSource().contains(value);
    }

    protected void selectByVisibleText(By locator, String visibleText) {
        new Select(waitForElement(locator)).selectByVisibleText(visibleText);
    }

    protected void scrollTo(By locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", waitForElement(locator));
    }
}
