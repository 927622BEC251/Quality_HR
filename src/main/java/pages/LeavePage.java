package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LeavePage extends BasePage {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final By leaveMenu = By.xpath("//span[text()='Leave']");
    private final By leaveHeader = By.xpath("//h6[normalize-space()='Leave']");
    private final By applyLink = By.xpath("//a[normalize-space()='Apply']");
    private final By leaveListLink = By.xpath("//a[normalize-space()='Leave List']");
    private final By leaveTypeDropdown = By.xpath("//label[normalize-space()='Leave Type']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class,'oxd-select-text')]");
    private final By firstLeaveTypeOption = By.xpath("//div[@role='option'][not(.//span[normalize-space()='-- Select --'])][1]");
    private final By fromDateInput = By.xpath("//label[normalize-space()='From Date']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private final By toDateInput = By.xpath("//label[normalize-space()='To Date']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private final By commentsTextArea = By.tagName("textarea");
    private final By applyButton = By.cssSelector("button[type='submit']");
    private final By toastMessage = By.cssSelector(".oxd-toast-content-text");
    private final By leaveListRows = By.cssSelector(".oxd-table-card");
    private final By dateValidationMessages = By.cssSelector(".oxd-input-field-error-message");
    private final By noLeaveTypesMessage = By.xpath("//*[contains(text(),'No Leave Types with Leave Balance')]");

    public LeavePage(WebDriver driver) {
        super(driver);
    }

    public void openLeaveModule() {
        click(leaveMenu);
    }

    public boolean isLoaded() {
        return isDisplayed(leaveHeader);
    }

    public boolean applyLeave(LocalDate fromDate, LocalDate toDate, String comment) {
        click(applyLink);
        waitForLeaveFormState();
        if (isNoLeaveTypesDisplayed()) {
            return false;
        }
        if (!selectFirstLeaveType()) {
            return false;
        }
        type(fromDateInput, DATE_FORMATTER.format(fromDate));
        type(toDateInput, DATE_FORMATTER.format(toDate));
        type(commentsTextArea, comment);
        click(applyButton);
        return true;
    }

    public boolean submitPastDateLeave(LocalDate pastDate) {
        click(applyLink);
        waitForLeaveFormState();
        if (isNoLeaveTypesDisplayed()) {
            return false;
        }
        if (!selectFirstLeaveType()) {
            return false;
        }
        type(fromDateInput, DATE_FORMATTER.format(pastDate));
        type(toDateInput, DATE_FORMATTER.format(pastDate));
        click(applyButton);
        return true;
    }

    public boolean submitInvalidDateFormat(String invalidDate) {
        click(applyLink);
        waitForLeaveFormState();
        if (isNoLeaveTypesDisplayed()) {
            return false;
        }
        if (!selectFirstLeaveType()) {
            return false;
        }
        type(fromDateInput, invalidDate);
        type(toDateInput, invalidDate);
        click(applyButton);
        return true;
    }

    public boolean isLeaveTypeSelectable() {
        click(applyLink);
        waitForLeaveFormState();
        if (isNoLeaveTypesDisplayed()) {
            return true;
        }
        return selectFirstLeaveType();
    }

    public void openLeaveList() {
        click(leaveListLink);
    }

    public boolean hasLeaveListRows() {
        return count(leaveListRows) > 0;
    }

    public boolean hasToastMessage() {
        return isDisplayed(toastMessage);
    }

    public int getDateValidationCount() {
        return count(dateValidationMessages);
    }

    public boolean isNoLeaveTypesDisplayed() {
        return pageContains("No Leave Types with Leave Balance")
                || count(noLeaveTypesMessage) > 0 && elements(noLeaveTypesMessage).stream().anyMatch(WebElement::isDisplayed);
    }

    private void waitForLeaveFormState() {
        waits.until(currentDriver -> currentDriver.getPageSource().contains("No Leave Types with Leave Balance")
                || currentDriver.findElements(leaveTypeDropdown).stream().anyMatch(WebElement::isDisplayed));
    }

    private boolean selectFirstLeaveType() {
        waitForLoaderToFinish();
        try {
            if (!isEnabled(leaveTypeDropdown)) {
                return false;
            }
            click(leaveTypeDropdown);
            WebElement option = waitForElement(firstLeaveTypeOption);
            option.click();
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }
}
