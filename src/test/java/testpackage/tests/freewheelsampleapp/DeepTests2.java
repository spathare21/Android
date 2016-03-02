package testpackage.tests.freewheelsampleapp;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;
import testpackage.pageobjects.FreewheelSampleApp;
import testpackage.utils.*;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Sachin on 2/29/2016.
 */
public class DeepTests2 {

    private static AndroidDriver driver;

    @BeforeClass
    public void beforeTest() throws Exception {
        System.out.println("BeforeTest in DeepTests\n");

        System.out.println(System.getProperty("user.dir"));
        // Get Property Values
        LoadPropertyValues prop = new LoadPropertyValues();
        Properties p = prop.loadProperty("freewheelsampleapp.properties");

        System.out.println("Device id from properties file " + p.getProperty("deviceName"));
        System.out.println("PortraitMode from properties file " + p.getProperty("PortraitMode"));
        System.out.println("Path where APK is stored" + p.getProperty("appDir"));
        System.out.println("APK name is " + p.getProperty("app"));
        System.out.println("Platform under Test is " + p.getProperty("platformName"));
        System.out.println("Mobile OS Version is " + p.getProperty("OSVERSION"));
        System.out.println("Package Name of the App is " + p.getProperty("appPackage"));
        System.out.println("Activity Name of the App is " + p.getProperty("appActivity"));

        SetUpAndroidDriver setUpdriver = new SetUpAndroidDriver();
        driver = setUpdriver.setUpandReturnAndroidDriver(p.getProperty("udid"), p.getProperty("appDir"), p.getProperty("appValue"), p.getProperty("platformName"), p.getProperty("platformVersion"), p.getProperty("appPackage"), p.getProperty("appActivity"));
        Thread.sleep(2000);
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        System.out.println("beforeMethod \n");
        //removeEventsLogFile.removeEventsFileLog(); create events file
        PushLogFileToDevice logpush = new PushLogFileToDevice();
        logpush.pushLogFile();
        if (driver.currentActivity() != "com.ooyala.sample.lists.FreewheelListActivity") {
            driver.startActivity("com.ooyala.sample.FreewheelSampleApp", "com.ooyala.sample.lists.FreewheelListActivity");
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

    @AfterClass
    public void afterTest() throws InterruptedException, IOException {
        System.out.println("AfterTest \n");
        driver.closeApp();
        driver.quit();

    }

    @AfterMethod
    public void afterMethod() throws InterruptedException, IOException {
        // Waiting for all the events from sdk to come in .
        System.out.println("AfterMethod \n");
        //ScreenshotDevice.screenshot(driver);
        RemoveEventsLogFile.removeEventsFileLog();
        Thread.sleep(10000);

    }

    @org.testng.annotations.Test
    public void FreeWheelPreMidPostRoll() throws Exception {

        try {
            // Creating an Object of FreeWheelSampleApp class
            FreewheelSampleApp po = new FreewheelSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);


            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("FreeWheelSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Freewheel PreMidPost");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            System.out.println("FreeWheel PreMidPostroll");
            //Play Started Verification
            EventVerification ev = new EventVerification();

            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            Thread.sleep(5000);

            // Click on the web area so that player screen shows up
            WebElement viewarea = driver.findElementByClassName("android.view.View");
            viewarea.click();

            Thread.sleep(1000);

            //pausing ad
            po.adPause(driver);

            //verifing event for pause
            ev.verifyEvent("stateChanged - state: PAUSED", "Ad paused", 3000);

            Thread.sleep(2000);

            po.getBackFromRecentApp(driver);

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);

            po.powerKeyClick(driver);

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);

            // Click on the web area so that player screen shows up

            viewarea.click();

            po.adPlay(driver);

            Thread.sleep(4000);

            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 35000);


            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);

            Thread.sleep(5000);

            // Tap coordinates to pause
            String dimensions = driver.manage().window().getSize().toString();
            //System.out.println(" Dimensions are "+dimensions);
            String[] dimensionsarray = dimensions.split(",");
            int length = dimensionsarray[1].length();
            String ydimensions = dimensionsarray[1].substring(0, length - 1);
            String ydimensionstrimmed = ydimensions.trim();
            int ydimensionsInt = Integer.parseInt(ydimensionstrimmed);
            viewarea.click();
            driver.tap(1, 35, (ydimensionsInt - 25), 0);

            //verifing pause event
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);

            Thread.sleep(5000);

            po.getBackFromRecentApp(driver);

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            // click on power button
            po.powerKeyClick(driver);

            Thread.sleep(2000);

            // again starting play
            po.videoPlay(driver);

            ev.verifyEvent("stateChanged - state: PLAYING", "video start playing again", 30000);

            Thread.sleep(5000);


            //Wait for Ad to start and verify the adStarted event .

            ev.verifyEvent("adStarted", " Ad Started to Play ", 49000);

            Thread.sleep(5000);

            // Click on the web area so that player screen shows up
            viewarea.click();

            Thread.sleep(1000);

            //pausing ad
            po.adPause(driver);

            //verifing event for pause
            ev.verifyEvent("stateChanged - state: PAUSED", "Ad paused", 3000);

            Thread.sleep(2000);

            po.getBackFromRecentApp(driver);

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);

            po.powerKeyClick(driver);

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);

            // Click on the web area so that player screen shows up

            viewarea.click();

            po.adPlay(driver);

            Thread.sleep(4000);

            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 35000);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);

            Thread.sleep(5000);

            viewarea.click();

            Thread.sleep(1000);

            //pausing ad
            po.adPause(driver);

            //verifing event for pause
            ev.verifyEvent("stateChanged - state: PAUSED", "Ad paused", 3000);

            Thread.sleep(2000);


            po.getBackFromRecentApp(driver);

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);

            po.powerKeyClick(driver);

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);

            // Click on the web area so that player screen shows up

            viewarea.click();

            po.adPlay(driver);

            Thread.sleep(4000);

            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 35000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 50000);

        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }
    }

        @org.testng.annotations.Test
    public void FreeWheelApplicationConfigured() throws Exception {
        try {
            // Creating an Object of FreeWheelSampleApp class
            FreewheelSampleApp po = new FreewheelSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);


            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("FreeWheelSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Freewheel Application-Configured");
            Thread.sleep(2000);


            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.CustomConfiguredFreewheelPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started Verification
            EventVerification ev = new EventVerification();

            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            Thread.sleep(5000);

            // Click on the web area so that player screen shows up
            WebElement viewarea = driver.findElementByClassName("android.view.View");
            viewarea.click();

            Thread.sleep(1000);

            //pausing ad
            po.adPause(driver);

            //verifing event for pause
            ev.verifyEvent("stateChanged - state: PAUSED", "Ad paused", 3000);

            Thread.sleep(2000);


            po.getBackFromRecentApp(driver);

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);

            po.powerKeyClick(driver);

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);

            // Click on the web area so that player screen shows up
            viewarea.click();

            po.adPlay(driver);


            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 30000);


            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);

            Thread.sleep(5000);

            // Tap coordinates to pause
            String dimensions = driver.manage().window().getSize().toString();
            //System.out.println(" Dimensions are "+dimensions);
            String[] dimensionsarray = dimensions.split(",");
            int length = dimensionsarray[1].length();
            String ydimensions = dimensionsarray[1].substring(0, length - 1);
            String ydimensionstrimmed = ydimensions.trim();
            int ydimensionsInt = Integer.parseInt(ydimensionstrimmed);
            viewarea.click();
            driver.tap(1, 35, (ydimensionsInt - 25), 0);

            //verifing pause event
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);

            Thread.sleep(5000);

            po.getBackFromRecentApp(driver);

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            // click on power button
            po.powerKeyClick(driver);

            Thread.sleep(2000);

            // again starting play
            po.videoPlay(driver);

            ev.verifyEvent("stateChanged - state: PLAYING", "video start playing again", 30000);

            Thread.sleep(2000);

            //Wait for Ad to start and verify the adStarted event .

            ev.verifyEvent("adStarted", " Ad Started to Play ", 49000);

            Thread.sleep(5000);

            // Click on the web area so that player screen shows up
            viewarea.click();

            Thread.sleep(1000);

            //pausing ad
            po.adPause(driver);

            //verifing event for pause
            ev.verifyEvent("stateChanged - state: PAUSED", "Ad paused", 3000);

            Thread.sleep(2000);

            po.getBackFromRecentApp(driver);

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);

            po.powerKeyClick(driver);

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);

            // Click on the web area so that player screen shows up

            viewarea.click();

            po.adPlay(driver);


            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 35000);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);

            Thread.sleep(5000);

            viewarea.click();

            Thread.sleep(1000);

            //pausing ad
            po.adPause(driver);

            //verifing event for pause
            ev.verifyEvent("stateChanged - state: PAUSED", "Ad paused", 3000);

            Thread.sleep(2000);


            po.getBackFromRecentApp(driver);

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);

            po.powerKeyClick(driver);

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);

            // Click on the web area so that player screen shows up

            viewarea.click();

            po.adPlay(driver);

            Thread.sleep(4000);

            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 35000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 50000);

        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }
    }

    @org.testng.annotations.Test
    public void FreeWheelMultiMidRoll() throws Exception {

        try {
            // Creating an Object of FreeWheelSampleApp class
            FreewheelSampleApp po = new FreewheelSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);


            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("FreeWheelSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Freewheel Multi Midroll");
            Thread.sleep(2000);


            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);

            //Wait for Ad to start and verify the adStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 40000);
            Thread.sleep(5000);

            // Click on the web area so that player screen shows up
            WebElement viewarea = driver.findElementByClassName("android.view.View");
            viewarea.click();

            Thread.sleep(1000);

            //pausing ad
            po.adPause(driver);

            //verifing event for pause
            ev.verifyEvent("stateChanged - state: PAUSED", "Ad paused", 3000);

            Thread.sleep(2000);

            po.getBackFromRecentApp(driver);

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);

            po.powerKeyClick(driver);

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);


            viewarea.click();

            po.adPlay(driver);


            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 35000);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);

            Thread.sleep(5000);

            viewarea.click();

            Thread.sleep(1000);

            //pausing ad
            po.adPause(driver);

            //verifing event for pause
            ev.verifyEvent("stateChanged - state: PAUSED", "Ad paused", 3000);

            Thread.sleep(2000);

            po.getBackFromRecentApp(driver);

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);

            po.powerKeyClick(driver);

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);

            // Click on the web area so that player screen shows up
            viewarea.click();

            po.adPlay(driver);

            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 35000);

            Thread.sleep(6000);

            // Tap coordinates to pause
            String dimensions = driver.manage().window().getSize().toString();
            //System.out.println(" Dimensions are "+dimensions);
            String[] dimensionsarray = dimensions.split(",");
            int length = dimensionsarray[1].length();
            String ydimensions = dimensionsarray[1].substring(0, length - 1);
            String ydimensionstrimmed = ydimensions.trim();
            int ydimensionsInt = Integer.parseInt(ydimensionstrimmed);
            viewarea.click();
            driver.tap(1, 35, (ydimensionsInt - 25), 0);

            //verifing pause event
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);

            Thread.sleep(10000);

            po.getBackFromRecentApp(driver);

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            // click on power button
            po.powerKeyClick(driver);

            Thread.sleep(2000);

            // again starting play
            po.videoPlay(driver);

            ev.verifyEvent("stateChanged - state: PLAYING", "video start playing again", 30000);

            Thread.sleep(2000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 50000);

        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }
    }


}
