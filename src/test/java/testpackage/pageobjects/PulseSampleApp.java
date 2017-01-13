package testpackage.pageobjects;

import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import testpackage.utils.CommandLine;

import java.io.IOException;
import java.util.List;

public class PulseSampleApp {
    Point play;
    //Defining logger class for pulsesampleapp
    final static Logger logger = Logger.getLogger(PulseSampleApp.class);

    public void waitForAppHomeScreen(AndroidDriver driver) {
        //waiting for homescreen to load specific text to ensure homescreen is loaded
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.
                xpath("//android.widget.TextView[@text='No ads']")));
    }

    public void assertCurrentActivityAgainst(AndroidDriver driver, String activityName) {
        //Verifying correct current activity is loading
        Assert.assertEquals(driver.currentActivity(), activityName);
    }

    public void clickBasedOnText(AndroidDriver driver, String clickText) {
        //Selecting the asset based on displayed text
        WebElement clickTextField = driver.findElement(By.xpath("//android.widget.TextView[@text='" + clickText + "']"));
        clickTextField.click();
    }

    public void waitForPresence(AndroidDriver driver, String typeOf, String waitString) {
        //Waiting for presence of specific element using classname
        WebDriverWait wait = new WebDriverWait(driver, 30);
        if (typeOf == "className") {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className(waitString)));
        }
    }

    public void waitForPresenceOfText(AndroidDriver driver, String waitString) {
        //Waiting for presence of specific element using Text
        WebDriverWait wait = new WebDriverWait(driver, 30);
        String xpath = "//android.widget.TextView[@text='" + waitString + "']";
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
    }

    public void seek_video (AndroidDriver driver,int time) throws Exception {
        try {
            //Seeking the Video
            List<WebElement> views = driver.findElements(By.className("android.view.ViewGroup"));
            logger.info("number of views present are : " + views.size());
            Point p = views.get(6).getLocation();
            driver.swipe(p.getX(), p.getY(), p.getX() + time, p.getY(), 5);
        } catch (Exception e) {
            List<WebElement> views = driver.findElements(By.className("android.view.View"));
            logger.info("number of views present are : " + views.size());
            Point p1 = views.get(6).getLocation();
            driver.swipe(p1.getX(), p1.getY(), p1.getX() + time, p1.getY(), 5);
        }
    }

    public void pauseVideo(AndroidDriver driver) throws InterruptedException {
        logger.info("moved to pause method");
        logger.info("play.x value is " + play.getX());
        logger.info("play.y value is " + play.getY());
        Thread.sleep(2000);
        driver.tap(1, play.getX(), play.getY(), 5);
        //Clicking on screen to display pause button
        Thread.sleep(2000);
        //Clicking on Pause button to pause video
        driver.tap(1,play.getX(),play.getY(),5);
        logger.info("clicked pause");
    }

    public void getPlay (AndroidDriver driver) throws Exception {
        //Click on Play button to play video
        WebDriverWait wait = new WebDriverWait(driver,30);
        String path  = "//android.widget.TextView[@text='h']";
        WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
        play= ele.getLocation();
        logger.info("play.x value is " + play.getX());
        logger.info("play.y value is " + play.getY());
        Thread.sleep(1000);
        driver.tap(1, play.getX(),play.getY(),2);
    }
}

