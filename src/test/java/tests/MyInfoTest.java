package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MyInfoPage;
import utilities.ConfigReader;

public class MyInfoTest extends BaseTest {
    @Test
    public void verifyMyInfoPageLoads() {
        MyInfoPage myInfoPage = openMyInfo();

        Assert.assertTrue(myInfoPage.isLoaded(), "My Info personal details page should load");
    }

    @Test
    public void verifyMyInfoNameFieldsAreVisible() {
        MyInfoPage myInfoPage = openMyInfo();

        Assert.assertTrue(myInfoPage.areNameFieldsVisible(), "First name and last name fields should be visible");
    }

    @Test
    public void verifyGenderOptionsAreAvailable() {
        MyInfoPage myInfoPage = openMyInfo();

        Assert.assertTrue(myInfoPage.getGenderOptionCount() >= 2, "Gender radio options should be available");
    }

    private MyInfoPage openMyInfo() {
        new LoginPage(getDriver()).login(ConfigReader.get("username"), ConfigReader.get("password"));
        MyInfoPage myInfoPage = new MyInfoPage(getDriver());
        myInfoPage.openMyInfoModule();
        return myInfoPage;
    }
}
