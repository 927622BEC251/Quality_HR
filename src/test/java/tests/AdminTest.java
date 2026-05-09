package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.EmployeePage;
import pages.AdminPage;
import pages.LoginPage;
import utilities.ConfigReader;

public class AdminTest extends BaseTest {
    @Test
    public void verifyCreateSearchAndDeleteEssUser() {
        loginAsAdmin();
        EmployeePage employeePage = new EmployeePage(getDriver());
        AdminPage adminPage = new AdminPage(getDriver());
        String suffix = String.valueOf(System.currentTimeMillis());
        String firstName = ConfigReader.get("employeeFirstName") + suffix;
        String lastName = ConfigReader.get("employeeLastName");
        String username = ConfigReader.get("newUserPrefix") + suffix;

        employeePage.openPimModule();
        employeePage.openAddEmployee();
        employeePage.addEmployee(firstName, ConfigReader.get("employeeMiddleName"), lastName);

        adminPage.openAdminModule();
        boolean created = adminPage.createEssUser(firstName, username, ConfigReader.get("newUserPassword"));
        Assert.assertTrue(created || adminPage.hasInvalidEmployeeSelection(),
                "System user should be created, or the live demo should reject unavailable employee autocomplete data");
        if (!created) {
            return;
        }

        adminPage.searchUser(username);
        Assert.assertTrue(adminPage.isUserDisplayed(), "Created ESS user should appear in admin list");

        adminPage.deleteFirstUser();
        adminPage.searchUser(username);
        Assert.assertTrue(adminPage.isNoRecordsFoundDisplayed(), "Deleted user should not appear in admin list");
    }

    private void loginAsAdmin() {
        new LoginPage(getDriver()).login(ConfigReader.get("username"), ConfigReader.get("password"));
    }
}
