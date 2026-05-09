package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import utilities.ConfigReader;
import utilities.JsonDataReader;

import java.util.List;
import java.util.Map;

public class LoginTest extends BaseTest {
    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        List<Map<String, String>> data = JsonDataReader.readArray("testdata/loginData.json");
        Object[][] testData = new Object[data.size()][3];
        for (int index = 0; index < data.size(); index++) {
            Map<String, String> row = data.get(index);
            testData[index][0] = resolve(row.get("usernameKey"));
            testData[index][1] = resolve(row.get("passwordKey"));
            testData[index][2] = Boolean.parseBoolean(row.get("valid"));
        }
        return testData;
    }

    @Test(dataProvider = "loginData")
    public void verifyLoginWithDataProvider(String username, String password, boolean validLoginExpected) {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(username, password);

        if (validLoginExpected) {
            Assert.assertTrue(new DashboardPage(getDriver()).isLoaded(), "Dashboard should load for valid credentials");
        } else {
            Assert.assertEquals(loginPage.getInvalidCredentialsMessage(), ConfigReader.get("invalidLoginMessage"));
        }
    }

    @Test
    public void verifyLogoutRedirectsToLoginPage() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));

        DashboardPage dashboardPage = new DashboardPage(getDriver());
        Assert.assertTrue(dashboardPage.isLoaded(), "Dashboard should load before logout");

        dashboardPage.logout();
        Assert.assertTrue(loginPage.isLoginPageLoaded(), "Login page should be visible after logout");
    }

    @Test
    public void verifyEmptyUsernameAndPasswordShowsValidation() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.submitEmptyLoginForm();

        Assert.assertEquals(loginPage.getRequiredValidationCount(), 2, "Both username and password should be required");
    }

    @Test
    public void verifyEmptyUsernameShowsValidation() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login("", ConfigReader.get("password"));

        Assert.assertEquals(loginPage.getRequiredValidationCount(), 1, "Username should be required");
    }

    @Test
    public void verifyEmptyPasswordShowsValidation() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(ConfigReader.get("username"), "");

        Assert.assertEquals(loginPage.getRequiredValidationCount(), 1, "Password should be required");
    }

    private String resolve(String key) {
        if ("blank".equals(key)) {
            return "";
        }
        return ConfigReader.get(key);
    }
}
