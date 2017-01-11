package testpackage.tests.freewheelsampleapp;

import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import testpackage.pageobjects.FreewheelSampleApp;
import testpackage.utils.*;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Sachin on 2/29/2016.
 */
public class DeepTests2 extends EventLogTest{
    final static Logger logger = Logger.getLogger(DeepTests2.class);



    @BeforeClass
    public void beforeTest() throws Exception {
        // closing all recent app from background.
        CloserecentApps.closeApps();
        logger.info("BeforeTest in DeepTests\n");

        logger.debug(System.getProperty("user.dir"));
        // Get Property Values
        LoadPropertyValues prop = new LoadPropertyValues();
        Properties p = prop.loadProperty("freewheelsampleapp.properties");

        logger.debug("Device id from properties file " + p.getProperty("deviceName"));
        logger.debug("PortraitMode from properties file " + p.getProperty("PortraitMode"));
        logger.debug("Path where APK is stored" + p.getProperty("appDir"));
        logger.debug("APK name is " + p.getProperty("app"));
        logger.debug("Platform under Test is " + p.getProperty("platformName"));
        logger.debug("Mobile OS Version is " + p.getProperty("OSVERSION"));
        logger.debug("Package Name of the App is " + p.getProperty("appPackage"));
        logger.debug("Activity Name of the App is " + p.getProperty("appActivity"));

        SetUpAndroidDriver setUpdriver = new SetUpAndroidDriver();
        driver = setUpdriver.setUpandReturnAndroidDriver(p.getProperty("udid"), p.getProperty("appDir"), p.getProperty("appValue"), p.getProperty("platformName"), p.getProperty("platformVersion"), p.getProperty("appPackage"), p.getProperty("appActivity"));
        Thread.sleep(2000);
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        logger.info("beforeMethod \n");
        driver.manage().logs().get("logcat");
        PushLogFileToDevice logpush = new PushLogFileToDevice();
        logpush.pushLogFile();
        if (driver.currentActivity() != "com.ooyala.sample.lists.FreewheelListActivity") {
            driver.startActivity("com.ooyala.sample.FreewheelSampleApp", "com.ooyala.sample.lists.FreewheelListActivity");
        }

        // Get Property Values
        LoadPropertyValues prop1 = new LoadPropertyValues();
        Properties p1 = prop1.loadProperty();

        logger.debug(" Screen Mode " + p1.getProperty("ScreenMode"));

        //if(p1.getProperty("ScreenMode") != "P"){
        //    logger.info("Inside landscape Mode ");
        //    driver.rotate(ScreenOrientation.LANDSCAPE);
        //}

        //driver.rotate(ScreenOrientation.LANDSCAPE);
        //driver.rotate(ScreenOrientation.LANDSCAPE);

    }

    @AfterClass
    public void afterTest() throws InterruptedException, IOException {
        logger.info("AfterTest \n");
        driver.closeApp();
        driver.quit();
        LoadPropertyValues prop1 = new LoadPropertyValues();
        Properties p1 = prop1.loadProperty();
        String prop = p1.getProperty("appPackage");
        Appuninstall.uninstall(prop);

    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws Exception {
        // Waiting for all the events from sdk to come in .
        logger.info("AfterMethod \n");
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
            logger.debug("FreeWheelSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

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
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            logger.info("FreeWheel PreMidPostroll");
            //Play Started Verification
            EventVerification ev = new EventVerification();

            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

//            WebDriverWait wait = new WebDriverWait(driver,30);
//            wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//android.widget.TextView[@text='Learn More']"))));
//            logger.info("learn more displayed");
//            Thread.sleep(1000);
//
//            // Click on the web area so that player screen shows up
//            WebElement viewarea = driver.findElementByClassName("android.view.View");
//            viewarea.click();
//
//            Thread.sleep(1000);
//
//            //pausing ad
//            po.adPause(driver);
//
//            //verifing event for pause
//            ev.verifyEvent("stateChanged - state: PAUSED", "Ad paused", 3000);
//
//            Thread.sleep(2000);
//
//            po.getBackFromRecentApp(driver);
//
//            Thread.sleep(2000);
//
//            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);
//
//            po.powerKeyClick(driver);
//
//            Thread.sleep(2000);
//
//            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);
//
//            // Click on the web area so that player screen shows up
//
//            viewarea.click();
//
//            po.adPlay(driver);
//
//            Thread.sleep(4000);

            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);


            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);

            Thread.sleep(5000);

            // Tap coordinates to pause
            String dimensions = driver.manage().window().getSize().toString();
            //logger.info(" Dimensions are "+dimensions);
            String[] dimensionsarray = dimensions.split(",");
            int length = dimensionsarray[1].length();
            String ydimensions = dimensionsarray[1].substring(0, length - 1);
            String ydimensionstrimmed = ydimensions.trim();
            int ydimensionsInt = Integer.parseInt(ydimensionstrimmed);
            WebElement viewarea = driver.findElementByClassName("android.view.View");
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

//            wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//android.widget.TextView[@text='Learn More']"))));
//            logger.info("learn more displayed");
//            Thread.sleep(1000);
//
//            // Click on the web area so that player screen shows up
//            viewarea.click();
//
//            Thread.sleep(1000);
//
//            //pausing ad
//            po.adPause(driver);
//
//            //verifing event for pause
//            ev.verifyEvent("stateChanged - state: PAUSED", "Ad paused", 3000);
//
//            Thread.sleep(2000);
//
//            po.getBackFromRecentApp(driver);
//
//            Thread.sleep(2000);
//
//            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);
//
//            po.powerKeyClick(driver);
//
//            Thread.sleep(2000);
//
//            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);
//
//            // Click on the web area so that player screen shows up
//
//            viewarea.click();
//
//            po.adPlay(driver);
//
//            Thread.sleep(4000);
//
//            //Wait for Ad to complete and verify the adCompleted event .
              ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);

//
               ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);
//
//
//            wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//android.widget.TextView[@text='Learn More']"))));
//            logger.info("learn more displayed");
//            Thread.sleep(1000);
//            viewarea.click();
//
//            Thread.sleep(1000);
//
//            //pausing ad
//            po.adPause(driver);

            //verifing event for pause
//            ev.verifyEvent("stateChanged - state: PAUSED", "Ad paused", 3000);

//            Thread.sleep(2000);
//
//
//            po.getBackFromRecentApp(driver);
//
//            Thread.sleep(2000);
//
//            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);
//
//            po.powerKeyClick(driver);
//
//            Thread.sleep(2000);
//
//            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);
//
//            // Click on the web area so that player screen shows up
//
//            viewarea.click();
//
//            po.adPlay(driver);
//
//            Thread.sleep(4000);

            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 50000);

        } catch (Exception e) {
            logger.error("FreeWheelPreMidPostRoll throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"FreeWheelPreMidPostRoll");
            Assert.assertTrue(false, "This will fail!");
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
            logger.debug("FreeWheelSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

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
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started Verification
            EventVerification ev = new EventVerification();

            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
//            WebDriverWait wait = new WebDriverWait(driver,30);
//            wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//android.widget.TextView[@text='Learn More']"))));
//            logger.info("learn more displayed");
//            Thread.sleep(1000);
//            // Click on the web area so that player screen shows up
//            WebElement viewarea = driver.findElementByClassName("android.view.View");
//            viewarea.click();
//
//            Thread.sleep(1000);
//
//            //pausing ad
//            po.adPause(driver);
//
//            //verifing event for pause
//            ev.verifyEvent("stateChanged - state: PAUSED", "Ad paused", 3000);
//
//            Thread.sleep(2000);
//
//
//            po.getBackFromRecentApp(driver);
//
//            Thread.sleep(2000);
//
//            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);
//
//            po.powerKeyClick(driver);
//
//            Thread.sleep(2000);
//
//            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);
//
//            // Click on the web area so that player screen shows up
//            viewarea.click();
//
//            po.adPlay(driver);


            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);


            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);

            Thread.sleep(5000);

            // Tap coordinates to pause
            String dimensions = driver.manage().window().getSize().toString();
            //logger.info(" Dimensions are "+dimensions);
            String[] dimensionsarray = dimensions.split(",");
            int length = dimensionsarray[1].length();
            String ydimensions = dimensionsarray[1].substring(0, length - 1);
            String ydimensionstrimmed = ydimensions.trim();
            int ydimensionsInt = Integer.parseInt(ydimensionstrimmed);
            WebElement viewarea = driver.findElementByClassName("android.view.View");
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

//            wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//android.widget.TextView[@text='Learn More']"))));
//            logger.info("learn more displayed");
//            Thread.sleep(1000);
//
//            // Click on the web area so that player screen shows up
//            viewarea.click();
//
//            Thread.sleep(1000);
//
//            //pausing ad
//            po.adPause(driver);
//
//            //verifing event for pause
//            ev.verifyEvent("stateChanged - state: PAUSED", "Ad paused", 3000);
//
//            Thread.sleep(2000);
//
//            po.getBackFromRecentApp(driver);
//
//            Thread.sleep(2000);
//
//            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);
//
//            po.powerKeyClick(driver);
//
//            Thread.sleep(2000);
//
//            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);
//
//            // Click on the web area so that player screen shows up
//
//            viewarea.click();
//
//            po.adPlay(driver);


            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);


//            wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//android.widget.TextView[@text='Learn More']"))));
//            logger.info("learn more displayed");
//            Thread.sleep(1000);
//
//            viewarea.click();
//
//            Thread.sleep(1000);
//
//            //pausing ad
//            po.adPause(driver);
//
//            //verifing event for pause
//            ev.verifyEvent("stateChanged - state: PAUSED", "Ad paused", 3000);
//
//            Thread.sleep(2000);
//
//
//            po.getBackFromRecentApp(driver);
//
//            Thread.sleep(2000);
//
//            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);
//
//            po.powerKeyClick(driver);
//
//            Thread.sleep(2000);
//
//            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);
//
//            // Click on the web area so that player screen shows up
//
//            viewarea.click();
//
//            po.adPlay(driver);
//
//            Thread.sleep(4000);

            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 50000);

        } catch (Exception e) {
            logger.error("FreeWheelApplicationConfigured throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"FreeWheelApplicationConfigured");
            Assert.assertTrue(false, "This will fail!");
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
            logger.debug("FreeWheelSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

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
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);

            //Wait for Ad to start and verify the adStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 40000);
//            WebDriverWait wait = new WebDriverWait(driver,30);
//            wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//android.widget.TextView[@text='Learn More']"))));
//            logger.info("learn more displayed");
//            Thread.sleep(1000);
//
//            // Click on the web area so that player screen shows up
//            WebElement viewarea = driver.findElementByClassName("android.view.View");
//            viewarea.click();
//
//            Thread.sleep(1000);
//
//            //pausing ad
//            po.adPause(driver);
//
//            //verifing event for pause
//            ev.verifyEvent("stateChanged - state: PAUSED", "Ad paused", 3000);
//
//            Thread.sleep(2000);
//
//            po.getBackFromRecentApp(driver);
//
//            Thread.sleep(2000);
//
//            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);
//
//            po.powerKeyClick(driver);
//
//            Thread.sleep(2000);
//
//            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);
//
//
//            viewarea.click();
//
//            po.adPlay(driver);


            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);


//            wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//android.widget.TextView[@text='Learn More']"))));
//            logger.info("learn more displayed");
//            Thread.sleep(1000);
//            WebElement viewarea = driver.findElementByClassName("android.view.View");
//            viewarea.click();
//
//            Thread.sleep(1000);
//
//            //pausing ad
//            po.adPause(driver);
//
//            //verifing event for pause
//            ev.verifyEvent("stateChanged - state: PAUSED", "Ad paused", 3000);
//
//            Thread.sleep(2000);
//
//            po.getBackFromRecentApp(driver);
//
//            Thread.sleep(2000);
//
//            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);
//
//            po.powerKeyClick(driver);
//
//            Thread.sleep(2000);
//
//            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);
//
//            // Click on the web area so that player screen shows up
//            viewarea.click();
//
//            po.adPlay(driver);

            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);

            Thread.sleep(6000);

            // Tap coordinates to pause
            String dimensions = driver.manage().window().getSize().toString();
            //logger.info(" Dimensions are "+dimensions);
            String[] dimensionsarray = dimensions.split(",");
            int length = dimensionsarray[1].length();
            String ydimensions = dimensionsarray[1].substring(0, length - 1);
            String ydimensionstrimmed = ydimensions.trim();
            int ydimensionsInt = Integer.parseInt(ydimensionstrimmed);
            WebElement viewarea = driver.findElementByClassName("android.view.View");
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
            logger.error("FreeWheelMultiMidRoll throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"FreeWheelMultiMidRoll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void FreeWheelPreMidPostRollOverlay() throws Exception {

        try {
            // Creating an Object of FreeWheelSampleApp class
            FreewheelSampleApp po = new FreewheelSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);


            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("FreeWheelSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Freewheel PreMidPost Overlay");
            Thread.sleep(2000);


            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");


            logger.info("FW PreMidPost overlay");
            //Play Started Verification
            EventVerification ev = new EventVerification();

            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
//            WebDriverWait wait = new WebDriverWait(driver,30);
//            wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//android.widget.TextView[@text='Learn More']"))));
//            logger.info("learn more displayed");
//            Thread.sleep(1000);
//            // Click on the web area so that player screen shows up
//            WebElement viewarea = driver.findElementByClassName("android.view.View");
//            viewarea.click();
//
//            Thread.sleep(1000);
//
//            //pausing ad
//            po.adPause(driver);
//
//            //verifing event for pause
//            ev.verifyEvent("stateChanged - state: PAUSED", "Ad paused", 3000);
//
//            Thread.sleep(2000);
//
//            po.getBackFromRecentApp(driver);
//
//            Thread.sleep(2000);
//
//            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);
//
//            po.powerKeyClick(driver);
//
//            Thread.sleep(2000);
//
//            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);
//
//            // Click on the web area so that player screen shows up
//            viewarea.click();
//
//            po.adPlay(driver);
//
//            Thread.sleep(4000);

            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);


            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);

            Thread.sleep(5000);

            // Tap coordinates to pause
            String dimensions = driver.manage().window().getSize().toString();
            //logger.info(" Dimensions are "+dimensions);
            String[] dimensionsarray = dimensions.split(",");
            int length = dimensionsarray[1].length();
            String ydimensions = dimensionsarray[1].substring(0, length - 1);
            String ydimensionstrimmed = ydimensions.trim();
            int ydimensionsInt = Integer.parseInt(ydimensionstrimmed);
            WebElement viewarea = driver.findElementByClassName("android.view.View");
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


            //Wait for Ad to start and verify the adStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 49000);


//            wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//android.widget.TextView[@text='Learn More']"))));
//            logger.info("learn more displayed");
//            Thread.sleep(1000);
//            viewarea.click();
//
//            Thread.sleep(1000);
//
//            //pausing ad
//            po.adPause(driver);
//
//            //verifing event for pause
//            ev.verifyEvent("stateChanged - state: PAUSED", "Ad paused", 3000);
//
//            Thread.sleep(2000);
//
//            po.getBackFromRecentApp(driver);
//
//            Thread.sleep(2000);
//
//            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);
//
//            po.powerKeyClick(driver);
//
//            Thread.sleep(2000);
//
//            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);
//
//            // Click on the web area so that player screen shows up
//
//            viewarea.click();
//
//            po.adPlay(driver);
//
//            Thread.sleep(4000);


            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);

//            wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//android.widget.TextView[@text='Learn More']"))));
//            logger.info("learn more displayed");
//            Thread.sleep(1000);
//
//            viewarea.click();
//
//            Thread.sleep(1000);
//
//            //pausing ad
//            po.adPause(driver);
//
//            //verifing event for pause
//            ev.verifyEvent("stateChanged - state: PAUSED", "Ad paused", 3000);
//
//            Thread.sleep(2000);
//
//            po.getBackFromRecentApp(driver);
//
//            Thread.sleep(2000);
//
//            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);
//
//            po.powerKeyClick(driver);
//
//            Thread.sleep(2000);
//
//            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);
//
//            // Click on the web area so that player screen shows up
//
//            viewarea.click();
//
//            po.adPlay(driver);
//
//            Thread.sleep(4000);

            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 30000);

        } catch (Exception e) {
            logger.error("FreeWheelPreMidPostRollOverlay throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"FreeWheelPreMidPostRollOverlay");
            Assert.assertTrue(false, "This will fail!");
        }
    }


}
