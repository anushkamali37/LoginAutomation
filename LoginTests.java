package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import pages.LoginPage;

public class LoginTests {
    WebDriver driver;
    LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");



        driver = new ChromeDriver();
        driver.manage().window().maximize();

        
        driver.get("https://dev-dash.janitri.in/");



        loginPage = new LoginPage(driver);
        loginPage.waitForPageToLoad();
    }

    @Test
    public void testLoginButtonDisabledWhenFieldAreEmpty() {
        boolean isEnabled = loginPage.isLoginButtonEnabled();
        Assert.assertFalse(isEnabled, "Login button should be disabled when fields are empty.");
    }

    @Test
    public void testInvalidLoginShowErrorMsg() {
        loginPage.enterCredentials("invalid@example.com", "wrongPassword");
        loginPage.clickLogin();
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Invalid") || error.length() > 0, "Expected an error message.");
    }

    @Test
    public void testPasswordMaskedbutton() {
        boolean isMasked = loginPage.isPasswordMasked();
        Assert.assertTrue(isMasked, "Password should be masked by default.");

        loginPage.togglePasswordVisibility();
        boolean isUnmasked = !loginPage.isPasswordMasked();
        Assert.assertTrue(isUnmasked, "Password should be visible after toggling visibility.");
    }

    @Test
    public void testValidLogin() {
         
        loginPage.enterCredentials("anushkavm374@gmail.com", "Anushka@123");

        loginPage.clickLogin();

        
        boolean isLoggedIn = driver.findElements(By.xpath("//*[contains(text(),'Dashboard')]")).size() > 0
                          || driver.getCurrentUrl().contains("welcome");

        Assert.assertTrue(isLoggedIn, "Login should be successful and dashboard should be visible.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
