package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class EmployeePage extends BasePage {
    private final By pimMenu = By.xpath("//span[text()='PIM']");
    private final By addEmployeeLink = By.xpath("//a[normalize-space()='Add Employee']");
    private final By employeeListLink = By.xpath("//a[normalize-space()='Employee List']");
    private final By firstNameInput = By.name("firstName");
    private final By middleNameInput = By.name("middleName");
    private final By lastNameInput = By.name("lastName");
    private final By saveButton = By.cssSelector("button[type='submit']");
    private final By personalDetailsHeader = By.xpath("//h6[normalize-space()='Personal Details']");
    private final By employeeInformationHeader = By.xpath("//h5[normalize-space()='Employee Information']");
    private final By employeeNameSearchInput = By.xpath("//label[normalize-space()='Employee Name']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private final By searchButton = By.xpath("//button[normalize-space()='Search']");
    private final By resultRows = By.cssSelector(".oxd-table-card");
    private final By invalidSearchText = By.xpath("//*[contains(text(),'No Records Found')]");
    private final By requiredMessages = By.cssSelector(".oxd-input-field-error-message");
    private final By autocompleteOption = By.cssSelector(".oxd-autocomplete-option");

    public EmployeePage(WebDriver driver) {
        super(driver);
    }

    public void openPimModule() {
        click(pimMenu);
    }

    public void openAddEmployee() {
        click(addEmployeeLink);
    }

    public void openEmployeeList() {
        click(pimMenu);
        click(employeeListLink);
        waitForElement(employeeInformationHeader);
    }

    public void addEmployee(String firstName, String middleName, String lastName) {
        type(firstNameInput, firstName);
        type(middleNameInput, middleName);
        type(lastNameInput, lastName);
        click(saveButton);
        waitForElement(personalDetailsHeader);
    }

    public void submitEmptyAddEmployeeForm() {
        type(firstNameInput, "");
        type(lastNameInput, "");
        click(saveButton);
        waitForElement(requiredMessages);
    }

    public int getRequiredValidationCount() {
        return count(requiredMessages);
    }

    public void searchEmployee(String employeeName) {
        openEmployeeList();
        type(employeeNameSearchInput, employeeName);
        selectAutocompleteIfAvailable();
        click(searchButton);
        waitForLoaderToFinish();
    }

    public boolean hasSearchResults() {
        return count(resultRows) > 0;
    }

    public void openFirstSearchResult() {
        click(resultRows);
        waitForElement(personalDetailsHeader);
    }

    public boolean isPersonalDetailsDisplayed() {
        return isDisplayed(personalDetailsHeader);
    }

    public boolean isNoRecordsFoundDisplayed() {
        return count(invalidSearchText) > 0 || pageContains("No Records Found");
    }

    private void selectAutocompleteIfAvailable() {
        try {
            waits.until(currentDriver -> count(autocompleteOption) > 0 || count(invalidSearchText) > 0);
            elements(autocompleteOption).stream()
                    .filter(WebElement::isDisplayed)
                    .findFirst()
                    .ifPresent(WebElement::click);
        } catch (RuntimeException ignored) {
            // Invalid searches do not always produce autocomplete options; search can still continue.
        }
    }
}
