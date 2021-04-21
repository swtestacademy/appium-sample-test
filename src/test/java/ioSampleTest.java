import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ioSampleTest {

    public AndroidDriver<MobileElement> driver;
    public WebDriverWait                wait;

    //Elements By
    By jobsBy           = By.id("com.isinolsun.app:id/rootRelativeView");
    By allowWhenUsingBy = By.id("com.android.permissioncontroller:id/permission_allow_foreground_only_button");
    By searchingJobBy   = By.id("com.isinolsun.app:id/bluecollar_type_button");
    By animationBy      = By.id("com.isinolsun.app:id/animation_view");
    By toolBarTitleBy   = By.id("com.isinolsun.app:id/toolbarTitle");
    
    @BeforeMethod
    public void setup() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", "Pixel XL API 30");
        caps.setCapability("udid", "emulator-5554"); //DeviceId from "adb devices" command
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "11.0");
        caps.setCapability("skipUnlock", "true");
        caps.setCapability("appPackage", "com.isinolsun.app");
        caps.setCapability("appActivity", "com.isinolsun.app.activities.SplashActivity");
        caps.setCapability("noReset", "false");
        driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void basicTest() throws InterruptedException {
        //Click and pass Splash
        wait.until(ExpectedConditions.visibilityOfElementLocated(animationBy)).click();

        //Click I am searching a job
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchingJobBy)).click();

        //Notification Allow
        if (wait.until(ExpectedConditions.visibilityOfElementLocated(allowWhenUsingBy)).isDisplayed()) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(allowWhenUsingBy)).click();
        }

        //Click Second Job
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(jobsBy)).get(1).click();

        //Do a simple assertion
        String toolBarTitleStr = wait.until(ExpectedConditions.visibilityOfElementLocated(toolBarTitleBy)).getText();
        Assert.assertTrue(toolBarTitleStr.toLowerCase().contains("detay"));
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}
