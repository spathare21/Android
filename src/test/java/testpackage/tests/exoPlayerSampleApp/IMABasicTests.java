package testpackage.tests.exoPlayerSampleApp;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import testpackage.pageobjects.exoPlayerSampleApp;
import testpackage.utils.*;
import java.io.IOException;
import java.util.Properties;


public class IMABasicTests extends EventLogTest{

    LoadPropertyValues prop = new LoadPropertyValues();
    Properties p;
    exoPlayerSampleApp exoPlayerSampleApp = new exoPlayerSampleApp();
    final static Logger logger = Logger.getLogger(IMABasicTests.class);

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
        //push log file to the device
        PushLogFileToDevice logpush=new PushLogFileToDevice();
        logpush.pushLogFile();
        if(driver.currentActivity()!= "com.ooyala.sample.complete.MainExoPlayerActivity") {
            driver.startActivity("com.ooyala.sample.ExoPlayerSampleApp","com.ooyala.sample.complete.MainExoPlayerActivity");
        }
        // Get Property Values
        Properties p=prop.loadProperty();
        logger.debug(" Screen Mode "+ p.getProperty("ScreenMode"));
    }
    @AfterClass
    public void afterTest() throws InterruptedException, IOException {
        //close the app
        driver.closeApp();
        //quit the android driver
        driver.quit();
        //get the propert values
        p = prop.loadProperty();
        String prop = p.getProperty("appPackage");
        //unistall the app from the device
        Appuninstall.uninstall(prop);
    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws Exception {
        // remove or delete events log file from the device
        RemoveEventsLogFile.removeEventsFileLog();
        Thread.sleep(10000);
    }

    @Test
    public void imaAdRulesPreroll() throws Exception{
        try {
            // wait till home screen of ExoPlayerApp is opened
            exoPlayerSampleApp.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");

            // Write to console activity name of home screen app
            logger.debug("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //click on Google IMA Integration
            exoPlayerSampleApp.clickBasedOnText(driver, "Google IMA Integration");

            //display the current activity to console
            logger.debug(" Print current activity name"+driver.currentActivity());

            //wait for the assets to load properly
            exoPlayerSampleApp.waitForPresenceOfText(driver,"IMA Ad-Rules Preroll");

            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");

            // Wrire to console activity name of home screen app
            logger.debug("Ooyala Skin - Google IMA List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Selecting IMA Adrules Preroll asset
            exoPlayerSampleApp.clickBasedOnText(driver, "IMA Ad-Rules Preroll");

            //verify if player was loaded
            exoPlayerSampleApp.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredIMAPlayerActivity");

            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //wait for the presence of start screen
            exoPlayerSampleApp.waitForPresenceOfText(driver,"h");

            //Clicking on Play button
            exoPlayerSampleApp.getPlay(driver);

            //ad Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            // Ad playback completed event verification.
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);

            //tapping on the screen
            exoPlayerSampleApp.screentapping(driver);

            //pause the video in normal screen
            exoPlayerSampleApp.pausingVideo(driver);

            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            //seek the video in normal screen
            exoPlayerSampleApp.seek_video(driver,600);

            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);

            //handle the loading spinner
            exoPlayerSampleApp.loadingSpinner(driver);

            //resume the playback in normal screen
            exoPlayerSampleApp.getPlay(driver);

            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 70000);
        }
        catch(Exception e){
            logger.error("IMAAdRulesPreroll throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"IMAAdRulesPreroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void imaAdRulesMidroll() throws Exception{
        try {
            // wait till home screen of ExoPlayerApp is opened
            exoPlayerSampleApp.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");

            // Write to console activity name of home screen app
            logger.debug("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //click on Google IMA Integration
            exoPlayerSampleApp.clickBasedOnText(driver, "Google IMA Integration");

            logger.debug(" Print current activity name"+driver.currentActivity());

            //wait for the assets to load properly
            exoPlayerSampleApp.waitForPresenceOfText(driver,"IMA Ad-Rules Midroll");

            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");

            // Write to console activity name of home screen app
            logger.debug("Ooyala Skin - Google IMA List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video as IMA Ad-Rules Midroll.
            exoPlayerSampleApp.clickBasedOnText(driver, "IMA Ad-Rules Midroll");

            //verify if player was loaded
            exoPlayerSampleApp.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredIMAPlayerActivity");

            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //wait for the presence of start screen
            exoPlayerSampleApp.waitForPresenceOfText(driver,"h");

            //Clicking on Play button
            exoPlayerSampleApp.getPlay(driver);

            //create object of EventVerification
            EventVerification ev = new EventVerification();

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
            exoPlayerSampleApp.seek_video(driver,500);

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

            // Ad playback has been completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        }
        catch(Exception e){
            logger.error("IMAAdRulesMidroll throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"IMAAdRulesMidroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void imaAdRulesPostoll() throws Exception{
        try {
            // wait till home screen of ExoPlayerApp is opened
            exoPlayerSampleApp.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");

            // Write to console activity name of home screen app
            logger.debug("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //click on Google IMA Integration
            exoPlayerSampleApp.clickBasedOnText(driver, "Google IMA Integration");

            logger.debug(" Print current activity name"+driver.currentActivity());

            //wait for the assets to load properly
            exoPlayerSampleApp.waitForPresenceOfText(driver,"IMA Ad-Rules Postroll");

            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");

            // Write to console activity name of home screen app
            logger.debug("Ooyala Skin - Google IMA List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video as IMA Ad-Rules Postroll
            exoPlayerSampleApp.clickBasedOnText(driver, "IMA Ad-Rules Postroll");
            //verify if player was loaded
            exoPlayerSampleApp.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredIMAPlayerActivity");

            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //wait for the presence of start screen
            exoPlayerSampleApp.waitForPresenceOfText(driver,"h");

            //Clicking on Play button in Ooyala Skin
            exoPlayerSampleApp.getPlay(driver);

            //Creating object of EventVerification class
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);

            //tapping on the video screen
            exoPlayerSampleApp.screentapping(driver);

            //pause the video in normal screen
            exoPlayerSampleApp.pausingVideo(driver);

            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 40000);

            //seek video in normal screen
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

            // Ad playback has been completed.
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        }
        catch(Exception e){
            logger.error("IMAAdRulesPostoll throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"IMAAdRulesPostoll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void imaSkippable() throws Exception{
        try {
            // wait till home screen of ExoPlayerApp is opened
            exoPlayerSampleApp.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");

            // Write to console activity name of home screen app
            logger.debug("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //click on Google IMA Integration
            exoPlayerSampleApp.clickBasedOnText(driver, "Google IMA Integration");

            logger.debug(" Print current activity name"+driver.currentActivity());

            //wait for the assets to load properly
            exoPlayerSampleApp.waitForPresenceOfText(driver,"IMA Skippable");

            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");

            // Write to console activity name of home screen app
            logger.debug("Ooyala Skin - Google IMA List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video as IMA Skippable
            exoPlayerSampleApp.clickBasedOnText(driver, "IMA Skippable");

            //verify if player was loaded
            exoPlayerSampleApp.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredIMAPlayerActivity");

            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //wait for start screen to appear
            exoPlayerSampleApp.waitForPresenceOfText(driver,"h");

            //Clicking on Play button
            exoPlayerSampleApp.getPlay(driver);

            //Creating object of EventVerification Class
            EventVerification ev = new EventVerification();

            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            // Ad playback has been completed.
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 40000);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);

            //tapping on the video screen
            exoPlayerSampleApp.screentapping(driver);

            //pause the video in normal screen
            exoPlayerSampleApp.pausingVideo(driver);

            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            //seek the video in normal screen
            exoPlayerSampleApp.seek_video(driver,500);

            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);

            //handle the loading spinner
            exoPlayerSampleApp.loadingSpinner(driver);

            //resume the video playabck in normal screen
            exoPlayerSampleApp.getPlay(driver);

            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 80000);

            // Ad playback has been completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 90000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        }
        catch(Exception e){
            logger.error("IMASkippable throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"IMASkippable");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void imaPreMidPostSkippable() throws Exception{
        try {
            // wait till home screen of ExoPlayerApp is opened
            exoPlayerSampleApp.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");

            // Write to console activity name of home screen app
            logger.debug("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //click on Google IMA Integration
            exoPlayerSampleApp.clickBasedOnText(driver, "Google IMA Integration");

            logger.debug(" Print current activity name"+driver.currentActivity());

            //wait for the assets to load properly
            exoPlayerSampleApp.waitForPresenceOfText(driver,"IMA Pre, Mid and Post Skippable");

            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");

            // Write to console activity name of home screen app
            logger.debug("Ooyala Skin - Google IMA List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video HLS,MP4 etc .
            exoPlayerSampleApp.clickBasedOnText(driver, "IMA Pre, Mid and Post Skippable");

            //verify if player was loaded
            exoPlayerSampleApp.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredIMAPlayerActivity");

            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //wait for start screen to appear
            exoPlayerSampleApp.waitForPresenceOfText(driver,"h");

            //Clicking on Play button
            exoPlayerSampleApp.getPlay(driver);

            //Creating object of EventVerification Class
            EventVerification ev = new EventVerification();

            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            // Ad playback has been completed.
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);

            //tapping on the video screen
            exoPlayerSampleApp.screentapping(driver);

            //pausing the video in normal screen
            exoPlayerSampleApp.pausingVideo(driver);

            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 40000);

            //seek video in normal screen
            exoPlayerSampleApp.seek_video(driver,300);

            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 50000);

            //handling the loading spinner
            exoPlayerSampleApp.loadingSpinner(driver);

            //resume the video playback in normal screen
            exoPlayerSampleApp.getPlay(driver);

            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 60000);

            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            // Ad playback has been completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            // Ad playback has been completed.
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        }
        catch(Exception e)
        {
            logger.error("IMAPreMidPostSkippable throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"IMAPreMidPostSkippable");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void imaAdRulesPoddedMidroll() throws Exception{
        try {
            // wait till home screen of ExoPlayerApp is opened
            exoPlayerSampleApp.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");

            // Write to console activity name of home screen app
            logger.debug("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //click on Google IMA Integration
            exoPlayerSampleApp.clickBasedOnText(driver, "Google IMA Integration");
            Thread.sleep(2000);

            logger.debug(" Print current activity name"+driver.currentActivity());

            //wait for the assets to load properly
            exoPlayerSampleApp.waitForPresenceOfText(driver,"IMA Podded Midroll");

            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");

            // Write to console activity name of home screen app
            logger.debug("Ooyala Skin - Google IMA List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video as IMA Podded Midroll
            exoPlayerSampleApp.clickBasedOnText(driver, "IMA Podded Midroll");

            //verify if player was loaded
            exoPlayerSampleApp.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredIMAPlayerActivity");

            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //wait for the start screen to appear
            exoPlayerSampleApp.waitForPresenceOfText(driver,"h");

            //Clicking on Play button
            exoPlayerSampleApp.getPlay(driver);

            //Creating object of EventVerification Class
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);

            //tapping on the video screen
            exoPlayerSampleApp.screentapping(driver);

            //pause the video in normal screen
            exoPlayerSampleApp.pausingVideo(driver);

            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 40000);

            //seek video in normal screen
            exoPlayerSampleApp.seek_video(driver,300);

            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 50000);

            //handling the loading spinner
            exoPlayerSampleApp.loadingSpinner(driver);

            //resume the video playback in normal screen
            exoPlayerSampleApp.getPlay(driver);

            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 60000);

            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            // Ad playback has been completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            // Ad playback has been completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        }
        catch(Exception e){
            logger.error("IMAAdRulesPoddedMidroll throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"IMAAdRulesPoddedMidroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void imaAdRulesPoddedPostroll() throws Exception{
        try {
            // wait till home screen of ExoPlayerApp is opened
            exoPlayerSampleApp.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");

            // Write to console activity name of home screen app
            logger.debug("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //click on Google IMA Integration
            exoPlayerSampleApp.clickBasedOnText(driver, "Google IMA Integration");

            logger.debug(" Print current activity name"+driver.currentActivity());

            //wait for the assets to load properly
            exoPlayerSampleApp.waitForPresenceOfText(driver,"IMA Podded Postroll");

            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");

            // Write to console activity name of home screen app
            logger.debug("Ooyala Skin - Google IMA List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video as IMA Podded Postroll
            exoPlayerSampleApp.clickBasedOnText(driver, "IMA Podded Postroll");

            //verify if player was loaded
            exoPlayerSampleApp.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredIMAPlayerActivity");

            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //wait for the start screen to appear
            exoPlayerSampleApp.waitForPresenceOfText(driver,"h");

            //Clicking on Play button in Ooyala Skin
            exoPlayerSampleApp.getPlay(driver);

            //Creating object of EventVerification Class
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);

            //tapping the video screen
            exoPlayerSampleApp.screentapping(driver);

            //pause the video screen
            exoPlayerSampleApp.pausingVideo(driver);

            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 40000);

            //seek the video in normal screen
            exoPlayerSampleApp.seek_video(driver,600);

            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 50000);

            //handling the loading spinner
            exoPlayerSampleApp.loadingSpinner(driver);

            //resume the video playback in normal screen
            exoPlayerSampleApp.getPlay(driver);

            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 60000);

            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            // Ad playback has been completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            // Ad playback has been completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        }
        catch(Exception e){
            logger.error("IMAAdRulesPoddedPostroll throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"IMAAdRulesPoddedPostroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void imaAdRulesPoddedPreMidPost() throws Exception{
        try {
            // wait till home screen of ExoPlayerApp is opened
            exoPlayerSampleApp.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");

            // Write to console activity name of home screen app
            logger.debug("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //click on Google IMA Integration
            exoPlayerSampleApp.clickBasedOnText(driver, "Google IMA Integration");

            logger.debug(" Print current activity name"+driver.currentActivity());

            //wait for the assets to load properly
            exoPlayerSampleApp.waitForPresenceOfText(driver,"IMA Podded Pre-Mid-Post");

            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");

            // Write to console activity name of home screen app
            logger.debug("Ooyala Skin - Google IMA List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video as IMA Podded Pre-mid-exoPlayerSampleAppst
            exoPlayerSampleApp.clickBasedOnText(driver, "IMA Podded Pre-Mid-Post");

            //verify if player was loaded
            exoPlayerSampleApp.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredIMAPlayerActivity");

            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //wait for the start screen to appear
            exoPlayerSampleApp.waitForPresenceOfText(driver,"h");

            //Clicking on Play button
            exoPlayerSampleApp.getPlay(driver);

            //Creating object of EventVerification Class
            EventVerification ev = new EventVerification();

            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 20000);

            // Ad playback has been completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);

            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            // Ad playback has been completed event verifaction
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 40000);

            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 40000);

            // Ad playback has been completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 50000);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 50000);
            Thread.sleep(2000);

            //tapping on the video screen
            exoPlayerSampleApp.screentapping(driver);

            //pause the video in normal screen
            exoPlayerSampleApp.pausingVideo(driver);

            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 60000);

            //seek video in normal screen
            exoPlayerSampleApp.seek_video(driver,250);

            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 60000);

            //handling the loading spinner
            exoPlayerSampleApp.loadingSpinner(driver);

            //resume the video playback in normal screen
            exoPlayerSampleApp.getPlay(driver);

            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 60000);

            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 70000);

            // Ad playback has been completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 70000);

            // Ad playback has been completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            // Ad playback has been completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

            // ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            // Ad playback has been completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            // Ad playback has been completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            // Ad playback has been completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        }
        catch(Exception e){
            logger.error("IMAAdRulesPoddedPreMidPost throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"IMAAdRulesPoddedPreMidPost");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void imaAdRulesPoddedPreroll() throws Exception{
        try {
            // wait till home screen of ExoPlayerApp is opened
            exoPlayerSampleApp.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");

            // Write to console activity name of home screen app
            logger.debug("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //click on Google IMA Integration
            exoPlayerSampleApp.clickBasedOnText(driver, "Google IMA Integration");

            logger.debug(" Print current activity name"+driver.currentActivity());

            //wait for the assets to load properly
            exoPlayerSampleApp.waitForPresenceOfText(driver,"IMA Podded Preroll");

            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");
            // Write to console activity name of home screen app
            logger.debug("Ooyala Skin - Google IMA List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video as IMA Podded Preroll
            exoPlayerSampleApp.clickBasedOnText(driver, "IMA Podded Preroll");
            Thread.sleep(2000);

            //verify if player was loaded
            exoPlayerSampleApp.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredIMAPlayerActivity");

            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //wait for the start screen to appear
            exoPlayerSampleApp.waitForPresenceOfText(driver,"h");

            //Clicking on Play button
            exoPlayerSampleApp.getPlay(driver);

            //Creating object of EventVerification Class
            EventVerification ev = new EventVerification();

            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            // Ad playback has been completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);

            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            // Ad playback has been completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);

            //tapping on the video screen
            exoPlayerSampleApp.screentapping(driver);

            //pause the video in normal screen
            exoPlayerSampleApp.pausingVideo(driver);

            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            //seek video in normal screen
            exoPlayerSampleApp.seek_video(driver,600);

            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);

            //handling the loading spinner
            exoPlayerSampleApp.loadingSpinner(driver);

            //resume playback in normal screen
            exoPlayerSampleApp.getPlay(driver);

            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 70000);
        }
        catch(Exception e){
            logger.error("IMAAdRulesPoddedPreroll throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"IMAAdRulesPoddedPreroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }
}