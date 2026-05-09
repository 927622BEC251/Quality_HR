package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AdminPage extends BasePage {
    private final By adminMenu = By.xpath("//span[text()='Admin']");
    private final By addButton = By.xpath("//button[normalize-space()='Add']");
    private final By userRoleDropdown = By.xpath("//label[normalize-space()='User Role']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class,'oxd-select-text')]");
    private final By essOption = By.xpath("//div[@role='option']//span[normalize-space()='ESS']");
    private final By statusDropdown = By.xpath("//label[normalize-space()='Status']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class,'oxd-select-text')]");
    private final By enabledOption = By.xpath("//div[@role='option']//span[normalize-space()='Enabled']");
    private final By employeeNameInput = By.xpath("//label[normalize-space()='Employee Name']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private final By usernameInput = By.xpath("//label[normalize-space()='Username']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private final By passwordInput = By.xpath("//label[normalize-space()='Password']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private final By confirmPasswordInput = By.xpath("//label[normalize-space()='Confirm Password']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private final By saveButton = By.cssSelector("button[type='submit']");
    private final By usernameSearchInput = By.xpath("//label[normalize-space()='Username']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private final By searchButton = By.xpath("//button[normalize-space()='Search']");
    private final By deleteIcon = By.cssSelector(".oxd-table-card .bi-trash");
    private final By confirmDeleteButton = By.xpath("//button[normalize-space()='Yes, Delete']");
    private final By resultRows = By.cssSelector(".oxd-table-card");
    private final By noRecordsFound = By.xpath("//*[contains(text(),'No Records Found')]");
    private final By autocompleteOption = By.cssSelector(".oxd-autocomplete-option");
    private final By successToast = By.cssSelector(".oxd-toast-content-text");
    private final By validationMessages = By.cssSelector(".oxd-input-field-error-message");
    private final By invalidAutocompleteSelection = By.xpath("//*[contains(normalize-space(),'Invalid')]");

    public AdminPage(WebDriver driver) {
        super(driver);
    }

    public void openAdminModule() {
        click(adminMenu);
    }

    public boolean isEssRoleSelectable() {
        click(addButton);
        return selectDropdownOption(userRoleDropdown, essOption);
    }

    public boolean createEssUser(String employeeSearchText, String username, String password) {
        click(addButton);
        if (!selectDropdownOption(userRoleDropdown, essOption)) {
            return false;
        }
        type(employeeNameInput, employeeSearchText);
        selectFirstAutocomplete(employeeNameInput);
        if (!selectDropdownOption(statusDropdown, enabledOption)) {
            return false;
        }
        type(usernameInput, username);
        type(passwordInput, password);
        type(confirmPasswordInput, password);
        click(saveButton);
        try {
            waits.until(currentDriver -> count(successToast) > 0 || count(validationMessages) > 0);
        } catch (RuntimeException exception) {
            return false;
        }
        waitForLoaderToFinish();
        return count(successToast) > 0;
    }

    public void searchUser(String username) {
        type(usernameSearchInput, username);
        click(searchButton);
        waitForLoaderToFinish();
        waits.until(currentDriver -> count(resultRows) > 0
                || currentDriver.findElements(noRecordsFound).stream().anyMatch(WebElement::isDisplayed));
    }

    public boolean isUserDisplayed() {
        return count(resultRows) > 0;
    }

    public void deleteFirstUser() {
        click(deleteIcon);
        click(confirmDeleteButton);
        waits.invisible(confirmDeleteButton);
    }

    public boolean isNoRecordsFoundDisplayed() {
        return count(noRecordsFound) > 0 || pageContains("No Records Found");
    }

    public boolean hasInvalidEmployeeSelection() {
        return count(invalidAutocompleteSelection) > 0 || pageContains("Invalid");
    }

    private boolean selectDropdownOption(By dropdown, By option) {
        try {
            click(dropdown);
            click(option);
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    private void selectFirstAutocomplete(By inputLocator) {
        waits.until(currentDriver -> count(autocompleteOption) > 0 || count(validationMessages) > 0);
        elements(autocompleteOption).stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .ifPresent(WebElement::click);
    }
}
