import java.util.List;

import junit.framework.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Foo extends TestCase {

    private void sleep(int msecs) {
        try {
            Thread.sleep(msecs);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    private boolean isValidUser(String username, String password) {
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get("http://apt-public.appspot.com/testing-lab-login.html");
            WebElement userId = driver.findElement(By.name("userId"));
            userId.clear();
            userId.sendKeys(username);

            WebElement userPassword = driver.findElement(By.name("userPassword"));
            userPassword.clear();
            userPassword.sendKeys(password);
            userPassword.submit();
            sleep(3000);
            if (driver.findElement(By.tagName("h3")).getText().contains("Convert from Farenheit to Celsius")) {
                return true;
            } else {
                return false;
            }
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        } finally {
            driver.quit();
        }
    }

    // There are 3 valid users, andy, bob and charley, whose passwords are apple,
    // bathtub and china. 
    // User names are case-insensitive, but passwords are case-sensitive. Leading
    // and trailing white space should be immaterial for both. 
    public void testValidUsers() {
        assertTrue(isValidUser("andy", "apple"));
        assertTrue(isValidUser("bob", "bathtub"));
        assertTrue(isValidUser("charley", "china"));
        // case insensitivity for username
        assertTrue(isValidUser("ChArLeY", "china"));
        // case sensitivity for password
        assertFalse(isValidUser("charley", "ChInA"));
    }

    // Three failed logins for a user in ten seconds should lead to a 1 minute lockout.
    public void testUserLockout() {
        assertFalse(isValidUser("andy", "Apple")); // invalid login 1
        assertFalse(isValidUser("andy", "Apple")); // invalid login 2
        assertFalse(isValidUser("andy", "Apple")); // invalid login 3
        long end = System.currentTimeMillis() + 60000;
        while (System.currentTimeMillis() < end - 45000) {
            // login with correct crendentials should fail for a little while
            assertFalse(isValidUser("andy", "apple"));
        }
        // wait a little longer than 60 seconds before correct login
        while (System.currentTimeMillis() < end) { 
           System.out.println("Waiting for lockout time to pass");
           sleep(5000);
        }
        assertTrue(isValidUser("andy", "apple"));
    }

    private String testTemperatureInput(String username, String password, String temperature) {
        WebDriver driver = new FirefoxDriver();
        driver.get("http://apt-public.appspot.com/testing-lab-login.html");
        WebElement userId = driver.findElement(By.name("userId"));
        userId.clear();
        userId.sendKeys(username);

        WebElement userPassword = driver.findElement(By.name("userPassword"));
        userPassword.clear();
        userPassword.sendKeys(password);
        userPassword.submit();
        sleep(3000);

       WebElement farenheitTemperature = driver.findElement(By.name("farenheitTemperature"));
       farenheitTemperature.clear();
       farenheitTemperature.sendKeys(temperature);
       farenheitTemperature.submit();
       sleep(3000);

        String result = driver.findElement(By.tagName("h2")).getText();
        driver.quit();
        return result;
    }

    // Temperature results should be 2 places of precision 
    // for temperatures from 0 to 212 degrees Farenheit, 
    // inclusive, and 1 place of precision otherwise.
    public void testResultFormatting() {
       sleep(10000); // give time for service to refresh
       String result97 = testTemperatureInput("andy", "apple", "97");
       assertEquals("36.11", result97.split("=")[1].split(" ")[1]); // two places of precision (0 <= x <= 212)
       String resultNegativePi = testTemperatureInput("bob", "bathtub", "-3.14");
       assertEquals("-19.5", resultNegativePi.split("=")[1].split(" ")[1]); // one place of precision (< 0)
    }

    public void testInvalidResult() {
       sleep(10000); // give time for service to refresh
       String invalidString = testTemperatureInput("andy", "apple", "invalid");
       assertTrue(invalidString.contains("Got a NumberFormatException"));
       String expString = testTemperatureInput("bob", "bathtub", "9.73E2");
       assertTrue(expString.contains("Got a NumberFormatException"));
    }
}

