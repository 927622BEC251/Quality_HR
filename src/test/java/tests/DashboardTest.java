package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import utilities.ConfigReader;

public class DashboardTest extends BaseTest {
    @Test
    public void verifyDashboardCoreMenuItemsAreVisible() {
        DashboardPage dashboardPage = loginAsAdmin();

        Assert.assertTrue(dashboardPage.areCoreMenuItemsVisible(), "Core dashboard menu items should be visible");
    }

    @Test
    public void verifyUserDropdownIsVisibleAfterLogin() {
        DashboardPage dashboardPage = loginAsAdmin();

        Assert.assertTrue(dashboardPage.isUserDropdownVisible(), "User dropdown should be visible after login");
    }

    private DashboardPage loginAsAdmin() {
        new LoginPage(getDriver()).login(ConfigReader.get("username"), ConfigReader.get("password"));
        DashboardPage dashboardPage = new DashboardPage(getDriver());
        Assert.assertTrue(dashboardPage.isLoaded(), "Dashboard should load");
        return dashboardPage;
    }
}
