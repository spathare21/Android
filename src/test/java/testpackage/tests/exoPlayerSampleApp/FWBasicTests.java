package testpackage.tests.exoPlayerSampleApp;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import testpackage.pageobjects.exoPlayerSampleApp;
import testpackage.utils.*;
import java.io.IOException;
import java.util.Properties;

public class FWBasicTests extends EventLogTest{

    LoadPropertyValues prop = new LoadPropertyValues();
    Properties p;
    exoPlayerSampleApp exoPlayerSampleApp = new exoPlayerSampleApp();
    final static Logger logger = Logger.getLogger(FWBasicTests.class);

    @BeforeClass
    public void beforeTest() throws Exception {
        // closing all recent app from background.
        CloserecentApps.closeApps();
        // Get Property Values
        p = prop.loadProperty("exoPlayerSampleApp.properties");
        //setup and initialize android driver
        SetUpAndroidDriver setUpdriver = new SetUpAndroidDriver();
        driver = setUpdriver.setUpandReturnAndroidDriver(p.getProperty("udid"), p.getProperty("appDir"), p.getProperty("appValue"), p.getProperty("platformName"), p.getProperty("platformVersion"), p.getProperty("appPackage"), p.getProperty("appActivity"));
        Thread.sleep(2000);
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        PushLogFileToDevice logpush=new PushLogFileToDevice();
        logpush.pushLogFile();
        if(driver.currentActivity()!= "com.ooyala.sample.complete.MainExoPlayerActivity") {
            driver.startActivity("com.ooyala.sample.ExoPlayerSampleApp","com.ooyala.sample.complete.MainExoPlayerActivity");
        }
        // Get Property Values
        p = prop.loadProperty();
        // Display the screen mode to console
        logger.debug(" Screen Mode "+ p.getProperty("ScreenMode"));
    }
    @AfterClass
    public void afterTest() throws InterruptedException, IOException {
        //close the app
        driver.closeApp();
        // quit the android driver
        driver.quit();
        //load the property values
        p = prop.loadProperty();
        String prop = p.getProperty("appPackage");
        //uninstall the app
        Appuninstall.uninstall(prop);
    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws Exception {
        // Remove the events log file from the device
        RemoveEventsLogFile.removeEventsFileLog();
        Thread.sleep(10000);
    }

    @Test
    public void FreeWheelIntegrationPreRoll() throws Exception{
        try {
            // wait till home screen of ExoPlayerSampleApp is opened
            exoPlayerSampleApp.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");

            // Wrire to console activity name of home screen app
            logger.debug("Exo Player Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //click on freewheel Integration
            exoPlayerSampleApp.clickBasedOnText(driver, "Freewheel Integration");

            // Assert if current activity is Freewheel list activity
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");

            // Selecting FW Preroll asset
            exoPlayerSampleApp.clickBasedOnText(driver, "Freewheel Preroll");

            //verify if player was loaded
            exoPlayerSampleApp.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");

            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //wait for the start screen of the video to appear
            exoPlayerSampleApp.waitForPresenceOfText(driver,"h");

            //Clicking on Play button in Ooyala Skin
            exoPlayerSampleApp.getPlay(driver);

            //Play Started Event Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            // Verify event for ad completed
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);

            // tap on the screen
            exoPlayerSampleApp.screentapping(driver);

            //pause the playing video
            exoPlayerSampleApp.pausingVideo(driver);

            //verify pause event
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            //seek the video
            exoPlayerSampleApp.seek_video(driver,600);

            //verify seek completed event
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);

            //handle the loading spinner
            exoPlayerSampleApp.loadingSpinner(driver);

            //resume the video playback
            exoPlayerSampleApp.getPlay(driver);
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 70000);
        }
        catch(Exception e){
            logger.error("FreeWheelIntegrationPreRoll throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"FreeWheelIntegrationPreRoll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void freeWheelIntegrationMidroll() throws Exception {
        try {
            // wait till home screen of ExoPlayerSampleApp is opened
            exoPlayerSampleApp.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");

            // Wrire to console activity name of home screen app
            logger.debug("Exo Player Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //click on freewheel Integration
            exoPlayerSampleApp.clickBasedOnText(driver, "Freewheel Integration");

            // Assert if current activity is Freewheel list activity
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");

            // Selecting FW Midroll asset
            exoPlayerSampleApp.clickBasedOnText(driver, "Freewheel Midroll");

            //verify if player was loaded
            exoPlayerSampleApp.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");

            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //wait for the start screen to appear
            exoPlayerSampleApp.waitForPresenceOfText(driver, "h");

            //Clicking on Play button in Ooyala Skin
            exoPlayerSampleApp.getPlay(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);

            //tap on the screen
            exoPlayerSampleApp.screentapping(driver);

            //pause the video
            exoPlayerSampleApp.pausingVideo(driver);

            //verify paused event
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 40000);

            //seek video in normal screen
            exoPlayerSampleApp.seek_video(driver,600);

            //verify seek completed event
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 50000);

            //handle the loading spinner
            exoPlayerSampleApp.loadingSpinner(driver);

            //resume the video playback in normal screen
            exoPlayerSampleApp.getPlay(driver);

            //verify playing event
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 60000);

            //verify ad started event
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            // verify ad completed event
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        } catch (Exception e){
            logger.error("FreeWheelIntegrationMidroll throws Exception " + e);
            ScreenshotDevice.screenshot(driver,"FreeWheelIntegrationMidroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void freeWheelIntegrationPostroll() throws Exception {
        try {
            // wait till home screen of ExoPlayerSampleApp is opened
            exoPlayerSampleApp.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");

            // Wrire to console activity name of home screen app
            logger.debug("Exo Player Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //click on freewheel Integration
            exoPlayerSampleApp.clickBasedOnText(driver, "Freewheel Integration");

            // Assert if current activity is Freewheel list activity
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");

            // Selecting FW Postroll asset
            exoPlayerSampleApp.clickBasedOnText(driver, "Freewheel Postroll");

            //verify if player was loaded
            exoPlayerSampleApp.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");

            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //wait for the start screen to appear
            exoPlayerSampleApp.waitForPresenceOfText(driver, "h");

            //Clicking on Play button in Ooyala Skin
            exoPlayerSampleApp.getPlay(driver);

            //Play Started event Verification
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);

            //tap on the screen
            exoPlayerSampleApp.screentapping(driver);

            //pause the video in normal screen
            exoPlayerSampleApp.pausingVideo(driver);

            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 40000);

            //seek the video in normal screen
            exoPlayerSampleApp.seek_video(driver,600);

            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 50000);

            //handle the loading spinner
            exoPlayerSampleApp.loadingSpinner(driver);

            //resume the video playback in normal screen
            exoPlayerSampleApp.getPlay(driver);

            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 60000);

            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            // Ad completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        } catch (Exception e){
            logger.error("FreeWheelIntegrationPostroll throws Exception " + e);
            ScreenshotDevice.screenshot(driver,"FreeWheelIntegrationPostroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void freeWheelIntegrationPreMidPostroll() throws Exception {
        try {
            // wait till home screen of ExoPlayerSampleApp is opened
            exoPlayerSampleApp.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");

            // Wrire to console activity name of home screen app
            logger.debug("Exo Player Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //click on freewheel Integration
            exoPlayerSampleApp.clickBasedOnText(driver, "Freewheel Integration");

            // Assert if current activity is Freewheel list activity
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");

            // Selecting FW PreMisPost asset
            exoPlayerSampleApp.clickBasedOnText(driver, "Freewheel PreMidPost");

            //verify if player was loaded
            exoPlayerSampleApp.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");

            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //wait for the start screen to appear
            exoPlayerSampleApp.waitForPresenceOfText(driver, "h");

            //Clicking on Play button in Ooyala Skin
            exoPlayerSampleApp.getPlay(driver);

            //create an object of event verification
            EventVerification ev = new EventVerification();

            //Ad Started event Verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            // Ad completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);

            //tapping on the screen
            exoPlayerSampleApp.screentapping(driver);

            //pause the video in normal screen
            exoPlayerSampleApp.pausingVideo(driver);

            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 40000);

            //seek video in normal screen
            exoPlayerSampleApp.seek_video(driver,300);

            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 50000);

            //handle the loading spinner
            exoPlayerSampleApp.loadingSpinner(driver);

            //resume the video playback in normal screen
            exoPlayerSampleApp.getPlay(driver);

            //video playing event verfication
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 60000);

            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            // Ad completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            // Ad completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        } catch (Exception e){
            logger.error("FreeWheelIntegrationPreMidPostroll throws Exception " + e);
            ScreenshotDevice.screenshot(driver,"FreeWheelIntegrationPreMidPostroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void freeWheelIntegrationOverlay() throws Exception {
        try {
            // wait till home screen of ExoPlayerSampleApp is opened
            exoPlayerSampleApp.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");

            // Write to console activity name of home screen app
            logger.debug("Exo Player Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //click on freewheel Integration
            exoPlayerSampleApp.clickBasedOnText(driver, "Freewheel Integration");
            // Assert if current activity is Freewheel list activity
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");

            // Selecting FW Overlay asset
            exoPlayerSampleApp.clickBasedOnText(driver, "Freewheel Overlay");

            //verify if player was loaded
            exoPlayerSampleApp.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");

            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //wait for the start screen to appear
            exoPlayerSampleApp.waitForPresenceOfText(driver, "h");

            //Clicking on Play button in Ooyala Skin
            exoPlayerSampleApp.getPlay(driver);

            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);

            //verify overlay
            exoPlayerSampleApp.verifyOverlay(driver);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 50000);
        } catch (Exception e){
            logger.error("FreeWheelIntegrationOverlay throws Exception " + e);
            ScreenshotDevice.screenshot(driver,"FreeWheelIntegrationOverlay");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void freeWheelIntegrationMultiMidroll() throws Exception {
        try {
            // wait till home screen of ExoPlayerSampleApp is opened
            exoPlayerSampleApp.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");

            // Write to console activity name of home screen app
            logger.debug("Exo Player Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //click on freewheel Integration
            exoPlayerSampleApp.clickBasedOnText(driver, "Freewheel Integration");

            // Assert if current activity is Freewheel list activity
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");

            // Selecting FW MultiMidroll asset
            exoPlayerSampleApp.clickBasedOnText(driver, "Freewheel Multi Midroll");

            //verify if player was loaded
            exoPlayerSampleApp.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");

            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //wait for the start screen to appear
            exoPlayerSampleApp.waitForPresenceOfText(driver, "h");

            //Clicking on Play button in Ooyala Skin
            exoPlayerSampleApp.getPlay(driver);

            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);

            //tapping on screen
            exoPlayerSampleApp.screentapping(driver);

            //pause the video in normal screen
            exoPlayerSampleApp.pausingVideo(driver);

            //pause state event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 40000);

            //seek video in normal screen
            exoPlayerSampleApp.seek_video(driver,200);

            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 45000);

            //handling the loading spinner
            exoPlayerSampleApp.loadingSpinner(driver);

            //resume the video playback in normal screen
            exoPlayerSampleApp.getPlay(driver);

            //video playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 50000);

            //Wait for Ad to start and verify the adStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);

            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 55000);

            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 55000);

            //ad completed event verifcation
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 65000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        } catch (Exception e){
            logger.error("FreeWheelIntegrationMultiMidroll throws Exception " + e);
            ScreenshotDevice.screenshot(driver,"FreeWheelIntegrationMultiMidroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void freeWheelIntegrationPreMidPostroll_overlay() throws Exception {
        try {
            // wait till home screen of ExoPlayerSampleApp is opened
            exoPlayerSampleApp.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");

            // Write to console activity name of home screen app
            logger.debug("Exo Player Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //click on freewheel Integration
            exoPlayerSampleApp.clickBasedOnText(driver, "Freewheel Integration");

            // Assert if current activity is Freewheel list activity
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");

            // Selecting FW PreMidPost asset
            exoPlayerSampleApp.clickBasedOnText(driver, "Freewheel PreMidPost");

            //verify if player was loaded
            exoPlayerSampleApp.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");

            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            exoPlayerSampleApp.waitForPresenceOfText(driver, "h");

            //Clicking on Play button in Ooyala Skin
            exoPlayerSampleApp.getPlay(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();

            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            //ad completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 35000);

            //verify overlay is appearing or not
            exoPlayerSampleApp.verifyOverlay(driver);

            //Wait for Ad to start and verify the adStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);

            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 55000);
            Thread.sleep(2000);

            // tapping on screen
            exoPlayerSampleApp.screentapping(driver);

            //pause video
            exoPlayerSampleApp.pausingVideo(driver);

            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 55000);

            //seek video in normal screen
            exoPlayerSampleApp.seek_video(driver, 100);

            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 60000);

            //handling loading spinner
            exoPlayerSampleApp.loadingSpinner(driver);

            //resume the video playback in normal screen
            exoPlayerSampleApp.getPlay(driver);

            //video playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 65000);

            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 70000);

            //ad completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 75000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        } catch (Exception e){
            logger.error("FreeWheelIntegrationPreMidPostroll_overlay throws Exception " + e);
            ScreenshotDevice.screenshot(driver,"FreeWheelIntegrationPreMidPostroll_overlay");
            Assert.assertTrue(false, "This will fail!");
        }
    }
}