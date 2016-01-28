package testpackage.tests.basicplaybacksampleapp;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import testpackage.pageobjects.BasicPlaybackSampleApp;
import testpackage.utils.*;

import java.io.IOException;
import java.sql.Driver;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by Sachin on 1/28/2016.
 */
public class DeepTests {

    private static AndroidDriver driver;

    @BeforeTest
    public void beforeTest() throws Exception {
        System.out.println("BeforeTest \n");

        System.out.println(System.getProperty("user.dir"));
        // Get Property Values
        LoadPropertyValues prop = new LoadPropertyValues();
        Properties p = prop.loadProperty();

        //System.out.println("Device id from properties file " + p.getProperty("deviceName"));
        //System.out.println("PortraitMode from properties file " + p.getProperty("PortraitMode"));
        //System.out.println("Path where APK is stored"+ p.getProperty("appDir"));
        //System.out.println("APK name is "+ p.getProperty("app"));
        //System.out.println("Platform under Test is "+ p.getProperty("platformName"));
        //System.out.println("Mobile OS Version is "+ p.getProperty("OSVERSION"));
        //System.out.println("Package Name of the App is "+ p.getProperty("appPackage"));
        //System.out.println("Activity Name of the App is "+ p.getProperty("appActivity"));

        SetUpAndroidDriver setUpdriver = new SetUpAndroidDriver();
        driver = setUpdriver.setUpandReturnAndroidDriver(p.getProperty("udid"), p.getProperty("appDir"), p.getProperty("appValue"), p.getProperty("platformName"), p.getProperty("platformVersion"), p.getProperty("appPackage"), p.getProperty("appActivity"));
        Thread.sleep(2000);
    }

    @BeforeMethod
    //public void beforeTest() throws Exception{
    public void beforeMethod() throws Exception {
        System.out.println("beforeMethod \n");
        //removeEventsLogFile.removeEventsFileLog(); create events file
        PushLogFileToDevice logpush = new PushLogFileToDevice();
        logpush.pushLogFile();
        if (driver.currentActivity() != "com.ooyala.sample.lists.BasicPlaybackListActivity") {
            driver.startActivity("com.ooyala.sample.BasicPlaybackSampleApp", "com.ooyala.sample.lists.BasicPlaybackListActivity");
        }

        // Get Property Values
        LoadPropertyValues prop1 = new LoadPropertyValues();
        Properties p1 = prop1.loadProperty();

        System.out.println(" Screen Mode " + p1.getProperty("ScreenMode"));

        //if(p1.getProperty("ScreenMode") != "P"){
        //    System.out.println("Inside landscape Mode ");
        //    driver.rotate(ScreenOrientation.LANDSCAPE);
        //}

        //driver.rotate(ScreenOrientation.LANDSCAPE);
        //driver.rotate(ScreenOrientation.LANDSCAPE);

    }

    @AfterTest
    public void afterTest() throws InterruptedException, IOException {
        System.out.println("AfterTest \n");
        driver.closeApp();
        driver.quit();

    }

    @AfterMethod
    //public void afterTest() throws InterruptedException, IOException {
    public void afterMethod() throws InterruptedException, IOException {
        // Waiting for all the events from sdk to come in .
        System.out.println("AfterMethod \n");
        //ScreenshotDevice.screenshot(driver);
        RemoveEventsLogFile.removeEventsFileLog();
        Thread.sleep(10000);

    }

    //TODO : create unique file names for snapshots taken .
    EventVerification ev = new EventVerification();

    @org.testng.annotations.Test

    public void AspectRatioTest() throws Exception {


        try {// Creating an Object of BasicPlaybackSampleApp class
            BasicPlaybackSampleApp po = new BasicPlaybackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("BasicPlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "4:3 Aspect Ratio");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            // Thread.sleep(10000);
            //Play Started Verification
            System.out.println("calling event verification function");
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", "Video Started to Play", 30000);
            // Click on the web area so that player screen shows up
            WebElement viewarea = driver.findElementByClassName("android.view.View");
            viewarea.click();

            // Tap coordinates to pause
            String dimensions = driver.manage().window().getSize().toString();
            //System.out.println(" Dimensions are "+dimensions);
            String[] dimensionsarray = dimensions.split(",");
            int length = dimensionsarray[1].length();
            String ydimensions = dimensionsarray[1].substring(0, length - 1);
            String ydimensionstrimmed = ydimensions.trim();
            int ydimensionsInt = Integer.parseInt(ydimensionstrimmed);
            driver.tap(1, 35, (ydimensionsInt - 25), 0);
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);

            po.gotoFullScreen(driver);
            Thread.sleep(10000);
            System.out.println("moved to full screen");
            Thread.sleep(10000);
            po.gotoNormalScreen(driver);
            Thread.sleep(10000);
            //po.getXYSeekBarAndSeek(driver,155,50);

            po.play(driver);
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
            Thread.sleep(3000);
            viewarea.click();

            Thread.sleep(3000);
            ev.verifyEvent("playCompleted", " Video Completed Play ", 70000);
            Thread.sleep(10000);
            po.getXYSeekBarAndSeek(driver, 500, 150);
            //po.play(driver);
            //System.out.println("clicked on play button");
            //Thread.sleep(2000);
            //ev.verifyEvent("playStarted"," Video Started to Play",30000);


        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }

    }

    //TODO : create unique file names for snapshots taken .
    @org.testng.annotations.Test
    public void HLSVideoTest() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            BasicPlaybackSampleApp po = new BasicPlaybackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("BasicPlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "MP4 Video");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            ev.verifyEvent("playStarted", "Video Started to Play", 30000);
            // Click on the web area so that player screen shows up
            WebElement viewarea = driver.findElementByClassName("android.view.View");
            viewarea.click();

            // Tap coordinates to pause
            String dimensions = driver.manage().window().getSize().toString();
            //System.out.println(" Dimensions are "+dimensions);
            String[] dimensionsarray = dimensions.split(",");
            int length = dimensionsarray[1].length();
            String ydimensions = dimensionsarray[1].substring(0, length - 1);
            String ydimensionstrimmed = ydimensions.trim();
            int ydimensionsInt = Integer.parseInt(ydimensionstrimmed);
            driver.tap(1, 35, (ydimensionsInt - 25), 0);
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);

            po.gotoFullScreen(driver);
            Thread.sleep(10000);
            System.out.println("moved to full screen");
            Thread.sleep(10000);
            //po.gotoNormalScreen(driver);
            // Thread.sleep(10000);
            po.getXYSeekBarAndSeek(driver, 155, 50);

            po.play(driver);
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
            Thread.sleep(3000);
            viewarea.click();

            Thread.sleep(3000);
            ev.verifyEvent("playCompleted", " Video Completed Play ", 70000);
            Thread.sleep(10000);
            po.getXYSeekBarAndSeek(driver, 500, 150);
            po.play(driver);
            System.out.println("clicked on play button");
            Thread.sleep(2000);
            ev.verifyEvent("playStarted", " Video Started to Play", 30000);


        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }
    }

    @org.testng.annotations.Test
    public void VASTAdPreRollTest() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            BasicPlaybackSampleApp po = new BasicPlaybackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("BasicPlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            //po.clickBasedOnText(driver, "VAST Ad Pre-roll");
            po.clickBasedOnTextScrollTo(driver, "VAST Ad Pre-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            Thread.sleep(5000);
            po.clickLearnMore(driver);
            System.out.println("clicked on learn more");
            Thread.sleep(2000);
            //driver.executeScript("mobile: keyevent",{"keycode":4});
            driver.navigate().back();




        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }

    }
}





