package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AdminPage;
import pages.EmployeePage;
import pages.LeavePage;
import pages.LoginPage;
import utilities.ConfigReader;

public class ValidationTest extends BaseTest {
    @Test
    public void verifyAddEmployeeMandatoryFieldValidation() {
        loginAsAdmin();
        EmployeePage employeePage = new EmployeePage(getDriver());

        employeePage.openPimModule();
        employeePage.openAddEmployee();
        employeePage.submitEmptyAddEmployeeForm();

        Assert.assertTrue(employeePage.getRequiredValidationCount() >= 2,
                "Add Employee should show mandatory field validation");
    }

    @Test
    public void verifyInvalidDateFormatValidation() {
        loginAsAdmin();
        LeavePage leavePage = new LeavePage(getDriver());

        leavePage.openLeaveModule();
        boolean submitted = leavePage.submitInvalidDateFormat(ConfigReader.get("invalidDateFormat"));

        Assert.assertTrue(!submitted || leavePage.getDateValidationCount() > 0, "Invalid date should show validation");
    }

    @Test
    public void verifyDropdownFieldIsSelectable() {
        loginAsAdmin();
        AdminPage adminPage = new AdminPage(getDriver());

        adminPage.openAdminModule();

        Assert.assertTrue(adminPage.isEssRoleSelectable(), "User role dropdown should show ESS as a selectable option");
    }

    private void loginAsAdmin() {
        new LoginPage(getDriver()).login(ConfigReader.get("username"), ConfigReader.get("password"));
    }
}
