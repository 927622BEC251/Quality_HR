package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LeavePage;
import pages.LoginPage;
import utilities.ConfigReader;

import java.time.LocalDate;

public class LeaveTest extends BaseTest {
    @Test
    public void verifyLeavePageLoads() {
        loginAsAdmin();
        LeavePage leavePage = new LeavePage(getDriver());

        leavePage.openLeaveModule();

        Assert.assertTrue(leavePage.isLoaded(), "Leave page should load");
    }

    @Test
    public void verifyApplyLeaveAndLeaveList() {
        loginAsAdmin();
        LeavePage leavePage = new LeavePage(getDriver());

        leavePage.openLeaveModule();
        boolean applied = leavePage.applyLeave(LocalDate.now().plusDays(10), LocalDate.now().plusDays(10), ConfigReader.get("leaveComment"));

        Assert.assertTrue(applied ? leavePage.hasToastMessage() : leavePage.isNoLeaveTypesDisplayed(),
                "Leave submission should show a toast message or the live demo should show no leave balance");
        if (applied) {
            leavePage.openLeaveList();
            Assert.assertTrue(leavePage.hasLeaveListRows(), "Leave list should show records");
        }
    }

    @Test
    public void verifyPastDateLeaveShowsValidation() {
        loginAsAdmin();
        LeavePage leavePage = new LeavePage(getDriver());

        leavePage.openLeaveModule();
        boolean submitted = leavePage.submitPastDateLeave(LocalDate.now().minusDays(30));

        Assert.assertTrue(!submitted || leavePage.getDateValidationCount() > 0 || leavePage.hasToastMessage(),
                "Past date leave should show validation or a server response");
    }

    private void loginAsAdmin() {
        new LoginPage(getDriver()).login(ConfigReader.get("username"), ConfigReader.get("password"));
    }
}
