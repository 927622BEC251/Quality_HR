package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.EmployeePage;
import pages.LoginPage;
import utilities.ConfigReader;

public class EmployeeTest extends BaseTest {
    @Test
    public void verifyAddSearchAndOpenEmployeeRecord() {
        loginAsAdmin();
        EmployeePage employeePage = new EmployeePage(getDriver());
        String suffix = String.valueOf(System.currentTimeMillis());
        String firstName = ConfigReader.get("employeeFirstName") + suffix;
        String middleName = ConfigReader.get("employeeMiddleName");
        String lastName = ConfigReader.get("employeeLastName");

        employeePage.openPimModule();
        employeePage.openAddEmployee();
        employeePage.addEmployee(firstName, middleName, lastName);
        employeePage.searchEmployee(firstName + " " + lastName);

        Assert.assertTrue(employeePage.hasSearchResults(), "Added employee should appear in the employee list");
        employeePage.openFirstSearchResult();
        Assert.assertTrue(employeePage.isPersonalDetailsDisplayed(), "Employee personal details should open");
    }

    @Test
    public void verifyInvalidEmployeeSearchShowsNoResult() {
        loginAsAdmin();
        EmployeePage employeePage = new EmployeePage(getDriver());

        employeePage.openPimModule();
        employeePage.searchEmployee(ConfigReader.get("invalidEmployeeName"));

        Assert.assertTrue(employeePage.isNoRecordsFoundDisplayed(), "Invalid employee search should show no result");
    }

    private void loginAsAdmin() {
        new LoginPage(getDriver()).login(ConfigReader.get("username"), ConfigReader.get("password"));
    }
}
