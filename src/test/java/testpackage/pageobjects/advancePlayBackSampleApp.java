package testpackage.pageobjects;

import io.appium.java_client.android.AndroidDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Sachin on 3/31/2016.
 */
public class advancePlayBackSampleApp {

    public void waitForAppHomeScreen(AndroidDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.
                xpath("//android.widget.TextView[@text='Play With InitialTime']")));

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

    public void clickOnVastAd(AndroidDriver driver) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("in vast ad");
        driver.findElementById("com.ooyala.sample.AdvancedPlaybackSampleApp:id/doubleLeftButton").click();
        System.out.println("clicked");
    }

    public void playVideo(AndroidDriver driver) throws InterruptedException {
        Thread.sleep(2000);
        driver.findElementByXPath("//android.widget.ImageButton[@index='0']").click();

    }

    public void clickOnOoyalaAd(AndroidDriver driver) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("in Ooyala ad");
        driver.findElementById("com.ooyala.sample.AdvancedPlaybackSampleApp:id/doubleRightButton").click();
        System.out.println("clicked");
    }

    public void clickOnP1(AndroidDriver driver) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("in Video1 ad");
        driver.findElementById("com.ooyala.sample.AdvancedPlaybackSampleApp:id/doubleLeftButton").click();
        System.out.println("clicked");

    }

    public void clickOnP2(AndroidDriver driver) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("in Video2 ad");
        driver.findElementById("com.ooyala.sample.AdvancedPlaybackSampleApp:id/doubleRightButton").click();
        System.out.println("clicked");

    }

}


