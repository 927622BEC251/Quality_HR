package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MyInfoPage extends BasePage {
    private final By myInfoMenu = By.xpath("//span[text()='My Info']");
    private final By personalDetailsHeader = By.xpath("//h6[normalize-space()='Personal Details']");
    private final By genderOptions = By.cssSelector(".oxd-radio-wrapper");
    private final By firstNameInput = By.name("firstName");
    private final By lastNameInput = By.name("lastName");

    public MyInfoPage(WebDriver driver) {
        super(driver);
    }

    public void openMyInfoModule() {
        click(myInfoMenu);
        waitForElement(personalDetailsHeader);
    }

    public boolean isLoaded() {
        return isDisplayed(personalDetailsHeader);
    }

    public int getGenderOptionCount() {
        return count(genderOptions);
    }

    public boolean areNameFieldsVisible() {
        return isDisplayed(firstNameInput) && isDisplayed(lastNameInput);
    }
}
