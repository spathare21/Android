package testpackage.pageobjects;

import io.appium.java_client.android.AndroidDriver;
import org.junit.Assert;
import org.omg.PortableInterceptor.AdapterNameHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by Vertis on 14/01/16.
 */
public class FreewheelSampleApp {


    public void waitForAppHomeScreen(AndroidDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.
                xpath("//android.widget.TextView[@text='Freewheel Preroll']")));

    }

    public void assertCurrentActivityAgainst(AndroidDriver driver, String activityName) {

        Assert.assertEquals(driver.currentActivity(), activityName);
    }

    public void clickBasedOnText(AndroidDriver driver, String clickText) {

        WebElement clickTextField = driver.findElement(By.xpath("//android.widget.TextView[@text='" + clickText + "']"));
        clickTextField.click();

    }

    public void waitForPresence(AndroidDriver driver, String typeOf, String waitString) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        if (typeOf == "className") {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className(waitString)));
        }

    }

    public void clickButtons(AndroidDriver driver, int index) {

        List<WebElement> buttons = driver.findElements(By.xpath("//android.widget.Button"));
        buttons.get(index).click();
    }

    public void waitForPresenceOfText(AndroidDriver driver,String waitString) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name(waitString)));
    }

    public void clickImagebuttons(AndroidDriver driver, int index) {

        List<WebElement> imageButtons = driver.findElements(By.xpath("//android.widget.ImageButton"));
        imageButtons.get(index).click();
    }

    public void clickLearnMore(AndroidDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.TextView[@text='Learn More']")));
        driver.findElement(By.xpath("//android.widget.TextView[@text='Learn More']")).click();

    }
        public void adPause (AndroidDriver driver)
    {
         driver.findElement(By.id("android:id/pause")).click();
    }

    public void adPlay (AndroidDriver driver)
    {
        driver.findElement(By.id("android:id/pause")).click();
    }


    public void getBackFromRecentApp (AndroidDriver driver) throws InterruptedException {


        driver.sendKeyEvent(187);   //key 187 is used to go on recent app
        System.out.println("key sent");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//android.view.View[@index= '0']")).click();  // here clicking on system ui to get back the sample app
        System.out.println("back to SDK");
    }

    public void powerKeyClick (AndroidDriver driver) throws InterruptedException {

        driver.sendKeyEvent(26);            // key 26 is used to lock the screen
        System.out.println("key sent");
        System.out.println("screen lock");
        Thread.sleep(5000);
        driver.sendKeyEvent(82);            // key 82 is used to unlock the screen
        System.out.println("key sent");
        System.out.println("screen unlock");
        Thread.sleep(2000);
    }

    public void videoPlay (AndroidDriver driver)
    {
        driver.findElement(By.xpath("//android.widget.ImageButton[@index ='0']")).click();
    }

    public void getXYSeekBarAndSeek(AndroidDriver driver, int widthOffSet1, int widthOffSet2) {
        WebElement seekBarField = driver.findElement(By.xpath("//android.widget.SeekBar"));

        int seekBarFieldWidth = seekBarField.getLocation().getX();
        int seekBarFieldHeigth = seekBarField.getLocation().getY();
        //System.out.println(" Dimensions bounds value is :-"+seekBarFieldHeigth);
        //System.out.println(" Dimensions bounds value is :-"+seekBarFieldWidth);
        System.out.println(" Seeking -------------------------  ");
        driver.swipe(seekBarFieldWidth + widthOffSet1, seekBarFieldHeigth, seekBarFieldWidth + widthOffSet2, seekBarFieldHeigth, 3);
    }

    public void verifyOverlay (AndroidDriver driver)
    {
          WebElement element = driver.findElement(By.xpath("//android.view.View[@index = '0']"));
        if (element.isDisplayed())
        {
            System.out.println("overlay displayed");
        }
    }

    public void clickOnViewarea(AndroidDriver driver)
    {
        WebDriverWait wait = new WebDriverWait(driver,30);
        String viewxpath = "//android.widget.TextView[@text='Learn More']/parent::android.widget.RelativeLayout/following-sibling::android.view.View";
        WebElement web = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(viewxpath)));
       // WebElement web = driver.findElement(By.xpath(viewxpath));

       // List<WebElement> view =  driver.findElements(By.className("android.view.View"));
        //System.out.println(">>>>>>>>>" +view);

        web.click();
    }

    public void cuepointOff (AndroidDriver driver)
    {
        driver.findElement(By.id("com.ooyala.sample.FreewheelSampleApp:id/toggleButton1")).click();
        System.out.println("cue point off");
    }

    public void adControlOff    (AndroidDriver driver)
    {
        driver.findElement(By.id("com.ooyala.sample.FreewheelSampleApp:id/toggleButton2")).click();
        System.out.println("Ad controls off");
    }
    }
