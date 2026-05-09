package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends BasePage {
    private final By dashboardHeader = By.xpath("//h6[text()='Dashboard']");
    private final By userDropdown = By.cssSelector(".oxd-userdropdown-tab");
    private final By logoutLink = By.xpath("//a[normalize-space()='Logout']");
    private final By pimMenu = By.xpath("//span[text()='PIM']");
    private final By adminMenu = By.xpath("//span[text()='Admin']");
    private final By leaveMenu = By.xpath("//span[text()='Leave']");
    private final By myInfoMenu = By.xpath("//span[text()='My Info']");

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isDisplayed(dashboardHeader);
    }

    public boolean areCoreMenuItemsVisible() {
        return isDisplayed(pimMenu) && isDisplayed(adminMenu) && isDisplayed(leaveMenu) && isDisplayed(myInfoMenu);
    }

    public boolean isUserDropdownVisible() {
        return isDisplayed(userDropdown);
    }

    public void logout() {
        click(userDropdown);
        click(logoutLink);
    }
}
