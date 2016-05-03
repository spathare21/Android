package testpackage.pageobjects;

import io.appium.java_client.android.AndroidDriver;
import org.apache.xpath.operations.And;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Created by Sachin on 4/5/2016.
 */
public class exoPlayerSampleApp {
    Point replay, more, close_button, share_asset, discovery_button, cc_button, volume_button, enablecc_button;

    public void waitForAppHomeScreen(AndroidDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.
                xpath("//android.widget.TextView[@text='Basic Playback']")));

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

    public void waitForPresenceOfText(AndroidDriver driver, String waitString) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name(waitString)));
    }


    public void verifyOverlay(AndroidDriver driver) {
        WebElement element = driver.findElement(By.xpath("//android.view.View[@index = '0']"));
        if (element.isDisplayed()) {
            System.out.println("overlay displayed");
        }
    }

    public void skipAd(AndroidDriver driver) throws InterruptedException {
        System.out.println("in skip ad");
        Thread.sleep(7000);
        WebElement element = (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.Button[@index='0']")));
        element.click();
        // driver.tap(1,585,1524,2);
    }

    public void adPause(AndroidDriver driver) {
        driver.findElement(By.className("android.view.View")).click();
    }

    public void clickBasedOnTextScrollTo(AndroidDriver driver, String clickText) {

        driver.scrollTo(clickText).click();
    }


    public void replayVideo(AndroidDriver driver) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        String path = "//android.widget.TextView[@text='c']";

        WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
        replay = ele.getLocation();
        System.out.println("replay.x value is " + replay.getX());
        System.out.println("replay.y value is " + replay.getY());

        Thread.sleep(2000);

        // more button location
        WebElement button = driver.findElement(By.xpath("//android.widget.TextView[@text='f']"));
        more = button.getLocation();
        System.out.printf("more button's X  cordinates" + more.getX());
        System.out.printf("more button's y  cordinates" + more.getY());
        driver.tap(1, more.getX(), more.getY(), 2);

        Thread.sleep(2000);
        // click on close button
        WebElement close = driver.findElement(By.xpath("//android.widget.TextView[@text='e']"));
        close_button = close.getLocation();
        System.out.printf("close button's X  cordinates" + close_button.getX());
        System.out.printf("close button's y  cordinates" + close_button.getY());
//       // driver.tap(1,close_button.getX(),close_button.getY(),2);

        Thread.sleep(2000);
        // shareAsset button location
        WebElement share = driver.findElement(By.xpath("//android.widget.TextView[@text='o']"));
        share_asset = share.getLocation();
        System.out.printf("share button's X  cordinates" + share_asset.getX());
        System.out.printf("share button's y  cordinates" + share_asset.getY());
        driver.tap(1, share_asset.getX(), share_asset.getY(), 2);
        Thread.sleep(5000);
        System.out.println("clicked on shared button");

        Thread.sleep(2000);

        System.out.println("clicking on screen");

        driver.tap(1, 0, 75, 2);
        System.out.println("tapped");

        Thread.sleep(2000);
        // Discovery button lcoation
        WebElement discovery = driver.findElementByXPath("//android.widget.TextView[@text='l']");
        discovery_button = discovery.getLocation();
        System.out.printf("discovery button's X  cordinates" + discovery_button.getX());
        System.out.printf("discovery button's y  cordinates" + discovery_button.getY());

        Thread.sleep(2000);

        //CC button location
        WebElement CC = driver.findElementByXPath("//android.widget.TextView[@text='k']");
        cc_button = CC.getLocation();
        System.out.printf("CC button's X  cordinates" + cc_button.getX());
        System.out.printf(" CC bbutton's y  cordinates" + cc_button.getY());
        driver.tap(1, cc_button.getX(), cc_button.getY(), 2);
        Thread.sleep(2000);

        //emable CC locations

        WebElement enablecc = driver.findElementByXPath("//android.widget.Switch[@index='4']");
        enablecc_button = enablecc.getLocation();
        System.out.printf("enablecc button's X  cordinates" + enablecc_button.getX());
        System.out.printf(" enablecc button's y  cordinates" + enablecc_button.getY());


        Thread.sleep(2000);
        driver.tap(1, close_button.getX(), close_button.getY(), 2);
        System.out.println("CC option closed");

        Thread.sleep(2000);
        driver.tap(1, close_button.getX(), close_button.getY(), 2);
        System.out.println("more option closed");

        Thread.sleep(2000);
        // volume button location
        WebElement volume = driver.findElementByXPath("//android.widget.TextView[@text='b']");
        volume_button = volume.getLocation();
        System.out.printf("volume button's X  cordinates" + volume_button.getX());
        System.out.printf(" volume button's y  cordinates" + volume_button.getY());

        Thread.sleep(1000);

        System.out.println("printed all the locations");

        ele.click();
    }
}