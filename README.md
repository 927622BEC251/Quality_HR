# QualityHR Selenium Java Automation Framework

This project automates OrangeHRM workflows for the QualityHR hackathon problem statement.

## Tech Stack

- Java 17
- Selenium WebDriver
- TestNG
- Maven
- WebDriverManager
- ExtentReports
- Jackson JSON data reader

## Project Structure

```text
src/main/java
  base
    BasePage.java
    BaseTest.java
  pages
    LoginPage.java
    DashboardPage.java
    EmployeePage.java
    LeavePage.java
    AdminPage.java
    MyInfoPage.java
  utilities
    ConfigReader.java
    DriverFactory.java
    WaitUtils.java
    ExtentManager.java
    ScreenshotUtils.java
    JsonDataReader.java
  listeners
    TestListener.java

src/test/java/tests
  LoginTest.java
  EmployeeTest.java
  LeaveTest.java
  AdminTest.java
  ValidationTest.java

src/test/resources
  config/config.properties
  testdata/loginData.json
  testdata/employeeData.json
  testdata/leaveData.json
```

## Run

```bash
mvn test
```

The suite is configured in `testng.xml` and runs Chrome by default. Reports are generated at:

```text
reports/QualityHR_ExtentReport.html
```

Failure screenshots are saved under:

```text
screenshots/

<img width="1920" height="905" alt="verifyAddEmployeeMandatoryFieldValidation_20260509_100831" src="https://github.com/user-attachments/assets/883d87a9-830b-4c3f-950d-f0551f9ef5c8" />

```
