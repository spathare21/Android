package testpackage.tests.freewheelsampleapp;

import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import testpackage.pageobjects.FreewheelSampleApp;
import testpackage.utils.*;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Sachin on 3/2/2016.
 */
public class DeepTests4 extends EventLogTest{
    final static Logger logger = Logger.getLogger(DeepTests4.class);



    @BeforeClass
    public void beforeTest() throws Exception {
        // closing all recent app from background.
        CloserecentApps.closeApps();

        logger.info("BeforeTest \n");

        logger.info(System.getProperty("user.dir"));
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
/*
    @org.testng.annotations.Test
    public void FWPreroll_learnmore() throws Exception {
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
            po.clickBasedOnText(driver, "Freewheel Preroll");
            Thread.sleep(2000);


            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            logger.info("FWPreroll_learnmore");

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            WebDriverWait wait = new WebDriverWait(driver,30);
            wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//android.widget.TextView[@text='Learn More']"))));
            logger.info("learn more displayed");
            Thread.sleep(1000);


            // clicking on learn more button
            po.clickLearnMore(driver);

            // verifing event that we have clicked on learn more
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Clicked on learn more", 40000);


            Thread.sleep(5000);
            // getting back to SDK
            driver.navigate().back();

            // verifing event that get back to SDK and ad start playing again
            logger.info("Back to SDK");

            Thread.sleep(1000);

            // verifing that ad has been played completely
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 50000);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 50000);

            Thread.sleep(15000);

            po.getBackFromRecentApp(driver);

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

            po.powerKeyClick(driver);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

            Thread.sleep(2000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);

        } catch (Exception e) {
            logger.error("FWPreroll_learnmore throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"FWPreroll_learnmore");
            Assert.assertTrue(false, "This will fail!");
        }


    }

    @org.testng.annotations.Test
    public void FWMidRoll_learnmore() throws Exception {

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
            po.clickBasedOnText(driver, "Freewheel Midroll");
            Thread.sleep(2000);


            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            logger.info("FWMidroll_learnmore");
            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);

            Thread.sleep(2000);

            // clicking on recent app button and getting abck to SDK
            po.getBackFromRecentApp(driver);

            Thread.sleep(2000);

            // verifing event that video player get ready or not
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            // truing off and on the screen
            po.powerKeyClick(driver);

            // verifing event that player get ready or not
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            //Thread.sleep(20000);

            // verifing that ad has been started or not
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            WebDriverWait wait = new WebDriverWait(driver,30);
            wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//android.widget.TextView[@text='Learn More']"))));
            logger.info("learn more displayed");
            Thread.sleep(1000);

            //clicking on learn more
            po.clickLearnMore(driver);
            Thread.sleep(5000);

            // verifing event that we have clicked on learn more
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Clicked on learn more", 30000);


            Thread.sleep(5000);
            // getting back to SDK
            driver.navigate().back();


            Thread.sleep(2000);

            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);

            ev.verifyEvent("playCompleted", " Video Completed Play ", 50000);

        } catch (Exception e) {
            logger.error("FWMidRoll_learnmore throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"FWMidRoll_learnmore");
            Assert.assertTrue(false, "This will fail!");
        }

    }

    @org.testng.annotations.Test
    public void FWPostroll_learnmore() throws Exception {

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
            po.clickBasedOnText(driver, "Freewheel Postroll");
            Thread.sleep(2000);


            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            logger.info("FWPostroll_learnmore");
            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);

            Thread.sleep(5000);
            // clicking on recent app button in video play state
            po.getBackFromRecentApp(driver);

            Thread.sleep(2000);

            // verifing event
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            // clicking on power off button
            po.powerKeyClick(driver);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            Thread.sleep(2000);

            //Wait for Ad to start and verify the adStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 49000);

            WebDriverWait wait = new WebDriverWait(driver,30);
            wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//android.widget.TextView[@text='Learn More']"))));
            logger.info("learn more displayed");
            Thread.sleep(1000);


            //clicking on learn more
            po.clickLearnMore(driver);

            Thread.sleep(5000);

            // verifing event that we have clicked on learn more
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Clicked on learn more", 30000);


            Thread.sleep(5000);
            // getting back to SDK
            driver.navigate().back();

            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);

            Thread.sleep(1000);

            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 40000);

        } catch (Exception e) {
            logger.error("FWPostroll_learnmore throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"FWPostroll_learnmore");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void FWPreMidPost_learnmore() throws Exception {

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

            logger.info("FWPreMidPostroll_learnmore");
            //Play Started Verification
            EventVerification ev = new EventVerification();

            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            WebDriverWait wait = new WebDriverWait(driver,30);
            wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//android.widget.TextView[@text='Learn More']"))));
            logger.info("learn more displayed");
            Thread.sleep(1000);

            // clicking on learn more button
            po.clickLearnMore(driver);

            // verifing event that we have clicked on learn more
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Clicked on learn more", 30000);


            Thread.sleep(5000);
            // getting back to SDK
            driver.navigate().back();

            logger.info("Back to SDK");
            // verifing event that get back to SDK and ad start playing again
           //ev.verifyEvent("adStarted - state: PLAYING", "Back to SDK and ad start playing again", 30000);

            Thread.sleep(1000);

            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);


            Thread.sleep(5000);

            po.getBackFromRecentApp(driver);

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            po.powerKeyClick(driver);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            Thread.sleep(2000);


            //Wait for Ad to start and verify the adStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 49000);


            wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//android.widget.TextView[@text='Learn More']"))));
            logger.info("learn more displayed");
            Thread.sleep(1000);

            // clicking on learn more button
            po.clickLearnMore(driver);

            // verifing event that we have clicked on learn more
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Clicked on learn more", 30000);


            Thread.sleep(5000);
            // getting back to SDK
            driver.navigate().back();

            logger.info("Back to SDK");
            // verifing event that get back to SDK and ad start playing again
           // ev.verifyEvent("adStarted - state: PLAYING", "Back to SDK and ad start playing again", 30000);

            Thread.sleep(1000);

            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);


            wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//android.widget.TextView[@text='Learn More']"))));
            logger.info("learn more displayed");
            Thread.sleep(1000);

            // clicking on learn more button
            po.clickLearnMore(driver);

            // verifing event that we have clicked on learn more
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Clicked on learn more", 30000);

            Thread.sleep(5000);
            // getting back to SDK
            driver.navigate().back();

            logger.info("Back to SDK");
            // verifing event that get back to SDK and ad start playing again
            //ev.verifyEvent("adStarted - state: PLAYING", "Back to SDK and ad start playing again", 30000);

            Thread.sleep(1000);

            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 30000);

        } catch (Exception e) {
            logger.error("FWPreMidPost_learnmore throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"FWPreMidPost_learnmore");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void FWMultiMidRoll_leanrmore() throws Exception {

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

            logger.info("FWMultiMidroll_learnmore");
            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);

            //Wait for Ad to start and verify the adStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 40000);

            WebDriverWait wait = new WebDriverWait(driver,30);
            wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//android.widget.TextView[@text='Learn More']"))));
            logger.info("learn more displayed");
            Thread.sleep(1000);

            // clicking on learn more button
            po.clickLearnMore(driver);

            // verifing event that we have clicked on learn more
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Clicked on learn more", 30000);


            Thread.sleep(5000);
            // getting back to SDK
            driver.navigate().back();

            logger.info("Back to SDK");
            // verifing event that get back to SDK and ad start playing again
           // ev.verifyEvent("adStarted - state: PLAYING", "Back to SDK and ad start playing again", 30000);

            Thread.sleep(4000);

            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);


            ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);


            wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//android.widget.TextView[@text='Learn More']"))));
            logger.info("learn more displayed");
            Thread.sleep(1000);


            // clicking on learn more button
            po.clickLearnMore(driver);

            // verifing event that we have clicked on learn more
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Clicked on learn more", 30000);


            Thread.sleep(5000);
            // getting back to SDK
            driver.navigate().back();

            logger.info("Back to SDK");

            // verifing event that get back to SDK and ad start playing again
          //  ev.verifyEvent("adStarted - state: PLAYING", "Back to SDK and ad start playing again", 30000);

            Thread.sleep(4000);

            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);


            Thread.sleep(6000);

            po.getBackFromRecentApp(driver);

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            po.powerKeyClick(driver);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            Thread.sleep(2000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 50000);

        } catch (Exception e) {
            logger.error("FWMultiMidRoll_leanrmore throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"FWMultiMidRoll_leanrmore");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void FWPreMidPostRollOverlay_learnmore() throws Exception {

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

            logger.info("FWPreMidPostOverlay_learnmore");
            //Play Started Verification
            EventVerification ev = new EventVerification();

            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            WebDriverWait wait = new WebDriverWait(driver,30);
            wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//android.widget.TextView[@text='Learn More']"))));
            logger.info("learn more displayed");
            Thread.sleep(1000);
            // clicking on learn more button
            po.clickLearnMore(driver);

            // verifing event that we have clicked on learn more
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Clicked on learn more", 30000);


            Thread.sleep(5000);
            // getting back to SDK
            driver.navigate().back();

            logger.info("Back to SDK");
            // verifing event that get back to SDK and ad start playing again
           // ev.verifyEvent("adStarted - state: PLAYING", "Back to SDK and ad start playing again", 30000);

            Thread.sleep(4000);

            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);


            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);

            Thread.sleep(5000);

            po.verifyOverlay(driver);

            Thread.sleep(5000);


            po.getBackFromRecentApp(driver);

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            po.powerKeyClick(driver);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            Thread.sleep(2000);


            //Wait for Ad to start and verify the adStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 49000);


            wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//android.widget.TextView[@text='Learn More']"))));
            logger.info("learn more displayed");
            Thread.sleep(1000);


            // clicking on learn more button
            po.clickLearnMore(driver);

            // verifing event that we have clicked on learn more
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Clicked on learn more", 30000);


            Thread.sleep(5000);
            // getting back to SDK
            driver.navigate().back();

            logger.info("Back to SDK");
            // verifing event that get back to SDK and ad start playing again
            //ev.verifyEvent("adStarted - state: PLAYING", "Back to SDK and ad start playing again", 30000);

            Thread.sleep(4000);

            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);


            wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//android.widget.TextView[@text='Learn More']"))));
            logger.info("learn more displayed");
            Thread.sleep(1000);


            // clicking on learn more button
            po.clickLearnMore(driver);

            // verifing event that we have clicked on learn more
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Clicked on learn more", 30000);


            Thread.sleep(5000);
            // getting back to SDK
            driver.navigate().back();

            logger.info("Back to SDK");
            // verifing event that get back to SDK and ad start playing again
           // ev.verifyEvent("adStarted - state: PLAYING", "Back to SDK and ad start playing again", 30000);

            Thread.sleep(4000);


            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 30000);

        } catch (Exception e) {
            logger.error("FWPreMidPostRollOverlay_learnmore throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"FWPreMidPostRollOverlay_learnmore");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void FreeWheelApplicationConfigured_learnmore() throws Exception {
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

            logger.info("FWApplicationConfigured_learnmore");
            //Play Started Verification
            EventVerification ev = new EventVerification();

            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            WebDriverWait wait = new WebDriverWait(driver,30);
            wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//android.widget.TextView[@text='Learn More']"))));
            logger.info("learn more displayed");
            Thread.sleep(1000);

            // clicking on learn more button
            po.clickLearnMore(driver);

            // verifing event that we have clicked on learn more
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Clicked on learn more", 30000);


            Thread.sleep(5000);
            // getting back to SDK
            driver.navigate().back();

            logger.info("Back to SDK");
            // verifing event that get back to SDK and ad start playing again
           // ev.verifyEvent("adStarted - state: PLAYING", "Back to SDK and ad start playing again", 30000);

            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);


            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);

            Thread.sleep(7000);

            po.getBackFromRecentApp(driver);

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            po.powerKeyClick(driver);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            Thread.sleep(2000);

            //Wait for Ad to start and verify the adStarted event .

            ev.verifyEvent("adStarted", " Ad Started to Play ", 49000);


            wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//android.widget.TextView[@text='Learn More']"))));
            logger.info("learn more displayed");
            Thread.sleep(1000);

            // clicking on learn more button
            po.clickLearnMore(driver);

            // verifing event that we have clicked on learn more
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Clicked on learn more", 30000);


            Thread.sleep(5000);
            // getting back to SDK
            driver.navigate().back();

            logger.info("Back to SDK");
            // verifing event that get back to SDK and ad start playing again
           // ev.verifyEvent("adStarted - state: PLAYING", "Back to SDK and ad start playing again", 30000);


            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);


            wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//android.widget.TextView[@text='Learn More']"))));
            logger.info("learn more displayed");
            Thread.sleep(1000);

            // clicking on learn more button
            po.clickLearnMore(driver);

            // verifing event that we have clicked on learn more
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Clicked on learn more", 30000);


            Thread.sleep(5000);
            // getting back to SDK
            driver.navigate().back();

            logger.info("Back To SDK");
            // verifing event that get back to SDK and ad start playing again
            //ev.verifyEvent("adStarted - state: PLAYING", "Back to SDK and ad start playing again", 30000);

            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 50000);

        } catch (Exception e) {
            logger.error("FreeWheelApplicationConfigured_learnmore throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"FreeWheelApplicationConfigured_learnmore");
            Assert.assertTrue(false, "This will fail!");
        }
    }
*/


}
