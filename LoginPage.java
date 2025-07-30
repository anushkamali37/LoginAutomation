package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    WebDriver driver;
    WebDriverWait wait;

    // Element locators
    private By userIdField = By.name("email");
    private By passwordField = By.name("password");

    // More flexible locator for Login button
    private By loginButton = By.xpath("//button[contains(., 'Login') or .//span[contains(., 'Login')]]");

    private By eyeIcon = By.cssSelector("button[aria-label='toggle password visibility']");
    private By errorMsg = By.cssSelector(".MuiFormHelperText-root");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Ensure the page is ready
    public void waitForPageToLoad() {
        wait.until(ExpectedConditions.presenceOfElementLocated(userIdField));
    }

    public boolean isLoginButtonEnabled() {
        WebElement loginBtn = wait.until(ExpectedConditions.presenceOfElementLocated(loginButton));
        return loginBtn.isEnabled();
    }

    public void enterCredentials(String user, String pass) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(userIdField)).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(userIdField)).sendKeys(user);
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).sendKeys(pass);
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public void togglePasswordVisibility() {
        wait.until(ExpectedConditions.elementToBeClickable(eyeIcon)).click();
    }

    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMsg)).getText();
    }

    public boolean isPasswordMasked() {
        return driver.findElement(passwordField).getAttribute("type").equals("password");
    }
}
