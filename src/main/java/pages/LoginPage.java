package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private final By usernameInput = By.name("username");
    private final By passwordInput = By.name("password");
    private final By loginButton = By.cssSelector("button[type='submit']");
    private final By invalidCredentialsMessage = By.cssSelector(".oxd-alert-content-text");
    private final By requiredMessages = By.cssSelector(".oxd-input-field-error-message");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String username, String password) {
        type(usernameInput, username);
        type(passwordInput, password);
        click(loginButton);
    }

    public void submitEmptyLoginForm() {
        click(loginButton);
    }

    public boolean isLoginPageLoaded() {
        return isDisplayed(usernameInput) && isDisplayed(passwordInput);
    }

    public String getInvalidCredentialsMessage() {
        return text(invalidCredentialsMessage);
    }

    public int getRequiredValidationCount() {
        return count(requiredMessages);
    }
}
