package testpackage.tests.exoPlayerSampleApp;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import testpackage.pageobjects.exoPlayerSampleApp;
import testpackage.utils.*;
import java.io.IOException;
import java.util.Properties;

public class BasicPlayBackBasicTests extends EventLogTest{
    LoadPropertyValues prop = new LoadPropertyValues();
    Properties p;
    final static Logger logger = Logger.getLogger(BasicPlayBackBasicTests.class);
    exoPlayerSampleApp exoPlayerSampleApp = new exoPlayerSampleApp();
    @BeforeClass
    public void beforeTest() throws Exception {
        // closing all recent app from background.
        CloserecentApps.closeApps();
        // Get Property Values
        p = prop.loadProperty("exoPlayerSampleApp.properties");
        //setup and update android driver
        SetUpAndroidDriver setUpdriver = new SetUpAndroidDriver();
        driver = setUpdriver.setUpandReturnAndroidDriver(p.getProperty("udid"), p.getProperty("appDir"), p.getProperty("appValue"), p.getProperty("platformName"), p.getProperty("platformVersion"), p.getProperty("appPackage"), p.getProperty("appActivity"));
        Thread.sleep(2000);
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        //push the log file to the device
        PushLogFileToDevice logpush=new PushLogFileToDevice();
        logpush.pushLogFile();
        if(driver.currentActivity()!= "com.ooyala.sample.complete.MainExoPlayerActivity") {
            driver.startActivity("com.ooyala.sample.ExoPlayerSampleApp","com.ooyala.sample.complete.MainExoPlayerActivity");
        }
        // Get Property Values
        p=prop.loadProperty();
        //display the screen mode to the console
        logger.debug(" Screen Mode "+ p.getProperty("ScreenMode"));
    }

    @AfterClass
    public void afterTest() throws InterruptedException, IOException {
        //close the app
        driver.closeApp();
        //quit the android driver
        driver.quit();
        //load the property values
        p = prop.loadProperty();
        String prop = p.getProperty("appPackage");
        //uninstall the app
        Appuninstall.uninstall(prop);
    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws Exception {
        // remove or delete the log file from the device
        RemoveEventsLogFile.removeEventsFileLog();
        Thread.sleep(10000);
    }

    @Test
    public void aspectratio() throws Exception{
        try {
            // wait till home screen of ExoPlayerApp is opened
            exoPlayerSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
            // Write to console activity name of home screen app
            logger.debug("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //click on basic playback
            exoPlayerSampleApp.clickBasedOnText(driver, "Basic Playback");
            //display the current activity to console
            logger.debug(" Print current activity name"+driver.currentActivity());
            //wait for the assets to load properly
            exoPlayerSampleApp.waitForPresenceOfText(driver,"4:3 Aspect Ratio");
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Write to console activity name of home screen app
            logger.debug("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video 4:3 Aspect Ratio
            exoPlayerSampleApp.clickBasedOnText(driver, "4:3 Aspect Ratio");
            //verify if player was loaded
            exoPlayerSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //wait for the presence of start screen
            exoPlayerSampleApp.waitForPresenceOfText(driver,"h");
            //Clicking on Play button
            exoPlayerSampleApp.getPlay(driver);
            //creating object of EventVerification Class
            EventVerification ev = new EventVerification();
            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);
            //tapping on video screen
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
            //resume the playback in normal screen
            exoPlayerSampleApp.getPlay(driver);
            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        }
        catch(Exception e){
            logger.error("Aspect Ratio throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"4:3 Aspect Ratio");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void mp4() throws Exception{
        try {
            // wait till home screen of ExoPlayerApp is opened
            exoPlayerSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
            // Write to console activity name of home screen app
            logger.debug("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //click on basic playback
            exoPlayerSampleApp.clickBasedOnText(driver, "Basic Playback");
            //display the current activity to console
            logger.debug(" Print current activity name"+driver.currentActivity());
            //wait for the assets to load properly
            exoPlayerSampleApp.waitForPresenceOfText(driver,"4:3 Aspect Ratio");
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Write to console activity name of home screen app
            logger.debug("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video MP4 Video.
            exoPlayerSampleApp.clickBasedOnTextScrollTo(driver, "MP4 Video");
            //verify if player was loaded
            exoPlayerSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //wait for the presence of start screen
            exoPlayerSampleApp.waitForPresenceOfText(driver,"h");
            //Clicking on Play button in Ooyala Skin
            exoPlayerSampleApp.getPlay(driver);
            //creating object of EventVerification Class
            EventVerification ev = new EventVerification();
            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);
            //tapping on video screen
            exoPlayerSampleApp.screentapping(driver);
            //pause the video in normal screen
            exoPlayerSampleApp.pausingVideo(driver);
            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);
            //seek video in normal screen
            exoPlayerSampleApp.seek_video(driver,700);
            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);
            //handling loading spinner
            exoPlayerSampleApp.loadingSpinner(driver);
            //resume playback in normal screen
            exoPlayerSampleApp.getPlay(driver);
            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING", "Video resumed", 80000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        }
        catch(Exception e)
        {
            logger.error("MP4 throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"MP4");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void hls() throws Exception{
        try {
            // wait till home screen of ExoPlayerApp is opened
            exoPlayerSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
            // Write to console activity name of home screen app
            logger.debug("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //click on basic playback
            exoPlayerSampleApp.clickBasedOnText(driver, "Basic Playback");
            //display the current activity to console
            logger.debug(" Print current activity name"+driver.currentActivity());
            //wait for the assets to load properly
            exoPlayerSampleApp.waitForPresenceOfText(driver,"4:3 Aspect Ratio");
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Write to console activity name of home screen app
            logger.debug("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video HLS Video.
            exoPlayerSampleApp.clickBasedOnTextScrollTo(driver, "HLS Video");
            //verify if player was loaded
            exoPlayerSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //wait for the presence of start screen
            exoPlayerSampleApp.waitForPresenceOfText(driver,"h");
            //Clicking on Play button
            exoPlayerSampleApp.getPlay(driver);
            //creating object of EventVerification Class
            EventVerification ev = new EventVerification();
            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);
            //tapping on video screen
            exoPlayerSampleApp.screentapping(driver);
            //pause the video in normal screen
            exoPlayerSampleApp.pausingVideo(driver);
            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);
            //seek video in normal screen
            exoPlayerSampleApp.seek_video(driver,700);
            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);
            //handling loading spinner
            exoPlayerSampleApp.loadingSpinner(driver);
            //resume playback in normal screen
            exoPlayerSampleApp.getPlay(driver);
            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING", "Video resumed", 80000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        }
        catch(Exception e){
            logger.error("HLS throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"HLS");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void encrypted_HLS() throws Exception{
        try {
            // wait till home screen of ExoPlayerApp is opened
            exoPlayerSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
            // Write to console activity name of home screen app
            logger.debug("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //click on basic playback
            exoPlayerSampleApp.clickBasedOnText(driver, "Basic Playback");
            //display the current activity to console
            logger.debug(" Print current activity name"+driver.currentActivity());
            //wait for the assets to load properly
            exoPlayerSampleApp.waitForPresenceOfText(driver,"4:3 Aspect Ratio");
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Write to console activity name of home screen app
            logger.debug("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video HLS,MP4 etc .
            exoPlayerSampleApp.clickBasedOnTextScrollTo(driver, "Ooyala Encrypted HLS");
            //verify if player was loaded
            exoPlayerSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //wait for the presence of start screen
            exoPlayerSampleApp.waitForPresenceOfText(driver,"h");
            //Clicking on Play button
            exoPlayerSampleApp.getPlay(driver);
            //creating object of EventVerification Class
            EventVerification ev = new EventVerification();
            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);
            //tapping on video screen
            exoPlayerSampleApp.screentapping(driver);
            //pause the video in normal screen
            exoPlayerSampleApp.pausingVideo(driver);
            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);
            //seek video in normal screen
            exoPlayerSampleApp.seek_video(driver,700);
            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);
            //handling loading spinner
            exoPlayerSampleApp.loadingSpinner(driver);
            //resume playback in normal screen
            exoPlayerSampleApp.getPlay(driver);
            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING", "Video resumed", 80000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        }
        catch(Exception e){
            logger.error("encrypted_HLS throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"encrypted_HLS");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void VAST2_Preroll() throws Exception{
        try {
            // wait till home screen of ExoPlayerApp is opened
            exoPlayerSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
            // Write to console activity name of home screen app
            logger.debug("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //click on basic playback
            exoPlayerSampleApp.clickBasedOnText(driver, "Basic Playback");
            //display the current activity to console
            logger.debug(" Print current activity name"+driver.currentActivity());
            //wait for the assets to load properly
            exoPlayerSampleApp.waitForPresenceOfText(driver,"4:3 Aspect Ratio");
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Write to console activity name of home screen app
            logger.debug("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video VAST2 Ad Pre-roll
            exoPlayerSampleApp.clickBasedOnTextScrollTo(driver, "VAST2 Ad Pre-roll");
            //verify if player was loaded
            exoPlayerSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //wait for the presence of start screen
            exoPlayerSampleApp.waitForPresenceOfText(driver,"h");
            //Clicking on Play button in Ooyala Skin
            exoPlayerSampleApp.getPlay(driver);
            //creating object of EventVerification Class
            EventVerification ev = new EventVerification();
            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
            // Ad playback has been completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);
            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);
            //tapping on video screen
            exoPlayerSampleApp.screentapping(driver);
            //pause the video in normal screen
            exoPlayerSampleApp.pausingVideo(driver);
            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);
            //seek video in normal screen
            exoPlayerSampleApp.seek_video(driver,600);
            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);
            //handling loading spinner
            exoPlayerSampleApp.loadingSpinner(driver);
            //resume playback in normal screen
            exoPlayerSampleApp.getPlay(driver);
            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 70000);
        }
        catch(Exception e){
            logger.error("VAST2_Preroll throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"VAST2_Preroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void vast2_Midroll() throws Exception{
        try {
            // wait till home screen of ExoPlayerApp is opened
            exoPlayerSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
            // Write to console activity name of home screen app
            logger.debug("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //click on basic playback
            exoPlayerSampleApp.clickBasedOnText(driver, "Basic Playback");
            //display the current activity to console
            logger.debug(" Print current activity name"+driver.currentActivity());
            //wait for the assets to load properly
            exoPlayerSampleApp.waitForPresenceOfText(driver,"4:3 Aspect Ratio");
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Write to console activity name of home screen app
            logger.debug("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video VAST2 Ad Mid-roll
            exoPlayerSampleApp.clickBasedOnTextScrollTo(driver, "VAST2 Ad Mid-roll");
            Thread.sleep(2000);
            //verify if player was loaded
            exoPlayerSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //wait for the presence of start screen
            exoPlayerSampleApp.waitForPresenceOfText(driver,"h");
            //Clicking on Play button in Ooyala Skin
            exoPlayerSampleApp.getPlay(driver);
            //creating object of EventVerification Class
            EventVerification ev = new EventVerification();
            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);
            //tapping on video screen
            exoPlayerSampleApp.screentapping(driver);
            //pause the video in normal screen
            exoPlayerSampleApp.pausingVideo(driver);
            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 40000);
            //seek video in normal screen
            exoPlayerSampleApp.seek_video(driver,300);
            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 50000);
            //handling loading spinner
            exoPlayerSampleApp.loadingSpinner(driver);
            //resume playback in normal screen
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
            logger.error("VAST2_Midroll throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"VAST2_Midroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void VAST2_Postroll() throws Exception{
        try {
            // wait till home screen of ExoPlayerApp is opened
            exoPlayerSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
            // Write to console activity name of home screen app
            logger.debug("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //click on basic playback
            exoPlayerSampleApp.clickBasedOnText(driver, "Basic Playback");
            //display the current activity to console
            logger.debug(" Print current activity name"+driver.currentActivity());
            //wait for the assets to load properly
            exoPlayerSampleApp.waitForPresenceOfText(driver,"4:3 Aspect Ratio");
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Write to console activity name of home screen app
            logger.debug("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video VAST2 Ad Post-roll
            exoPlayerSampleApp.clickBasedOnTextScrollTo(driver, "VAST2 Ad Post-roll");
            Thread.sleep(2000);
            //verify if player was loaded
            exoPlayerSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //wait for the presence of start screen
            exoPlayerSampleApp.waitForPresenceOfText(driver,"h");
            //Clicking on Play button
            exoPlayerSampleApp.getPlay(driver);
            //creating object of EventVerification Class
            EventVerification ev = new EventVerification();
            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);
            //tapping on video screen
            exoPlayerSampleApp.screentapping(driver);
            //pause the video in normal screen
            exoPlayerSampleApp.pausingVideo(driver);
            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 40000);
            //seek video in normal screen
            exoPlayerSampleApp.seek_video(driver,600);
            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 50000);
            //handling loading spinner
            exoPlayerSampleApp.loadingSpinner(driver);
            //resume playback in normal screen
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
            logger.error("VAST2_Postroll throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"VAST2_Postroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void vast_Wrapper() throws Exception{
        try {
            // wait till home screen of ExoPlayerApp is opened
            exoPlayerSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
            // Write to console activity name of home screen app
            logger.debug("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //click on basic playback
            exoPlayerSampleApp.clickBasedOnText(driver, "Basic Playback");
            //display the current activity to console
            logger.debug(" Print current activity name"+driver.currentActivity());
            //wait for the assets to load properly
            exoPlayerSampleApp.waitForPresenceOfText(driver,"4:3 Aspect Ratio");
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Write to console activity name of home screen app
            logger.debug("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video VAST2 Ad Wrapper
            exoPlayerSampleApp.clickBasedOnTextScrollTo(driver, "VAST2 Ad Wrapper");
            Thread.sleep(2000);
            //verify if player was loaded
            exoPlayerSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //wait for the presence of start screen
            exoPlayerSampleApp.waitForPresenceOfText(driver,"h");
            //Clicking on Play button
            exoPlayerSampleApp.getPlay(driver);
            //creating object of EventVerification Class
            EventVerification ev = new EventVerification();
            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
            // Ad playback has been completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);
            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);
            //tapping on video screen
            exoPlayerSampleApp.screentapping(driver);
            //pause the video in normal screen
            exoPlayerSampleApp.pausingVideo(driver);
            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);
            //seek video in normal screen
            exoPlayerSampleApp.seek_video(driver,200);
            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);
            //handling loading spinner
            exoPlayerSampleApp.loadingSpinner(driver);
            //resume playback in normal screen
            exoPlayerSampleApp.getPlay(driver);
            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 70000);
        }
        catch(Exception e){
            logger.error("VAST_Wrapper throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"VAST_Wrapper");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void ooyalaAd_Preroll() throws Exception{
        try {
            // wait till home screen of ExoPlayerApp is opened
            exoPlayerSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
            // Write to console activity name of home screen app
            logger.debug("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //click on basic playback
            exoPlayerSampleApp.clickBasedOnText(driver, "Basic Playback");
            //display the current activity to console
            logger.debug(" Print current activity name"+driver.currentActivity());
            //wait for the assets to load properly
            exoPlayerSampleApp.waitForPresenceOfText(driver,"4:3 Aspect Ratio");
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Write to console activity name of home screen app
            logger.debug("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video Ooyala Ad Pre-roll
            exoPlayerSampleApp.clickBasedOnTextScrollTo(driver, "Ooyala Ad Pre-roll");
            Thread.sleep(2000);
            //verify if player was loaded
            exoPlayerSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //wait for the presence of start screen
            exoPlayerSampleApp.waitForPresenceOfText(driver,"h");
            //Clicking on Play button in Ooyala Skin
            exoPlayerSampleApp.getPlay(driver);
            //creating object of EventVerification Class
            EventVerification ev = new EventVerification();
            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
            // Ad playback has been completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);
            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);
            //tapping on video screen
            exoPlayerSampleApp.screentapping(driver);
            //pause the video in normal screen
            exoPlayerSampleApp.pausingVideo(driver);
            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);
            //seek video in normal screen
            exoPlayerSampleApp.seek_video(driver,700);
            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);
            //handling loading spinner
            exoPlayerSampleApp.loadingSpinner(driver);
            //resume playback in normal screen
            exoPlayerSampleApp.getPlay(driver);
            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 70000);
        }
        catch(Exception e){
            logger.error("ooyalaAd_Preroll throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"ooyalaAd_Preroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void ooyalaAd_Midroll() throws Exception{
        try {
            // wait till home screen of ExoPlayerApp is opened
            exoPlayerSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
            // Write to console activity name of home screen app
            logger.debug("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //click on basic playback
            exoPlayerSampleApp.clickBasedOnText(driver, "Basic Playback");
            //display the current activity to console
            logger.debug(" Print current activity name"+driver.currentActivity());
            //wait for the assets to load properly
            exoPlayerSampleApp.waitForPresenceOfText(driver,"4:3 Aspect Ratio");
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Write to console activity name of home screen app
            logger.debug("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video Ooyala Ad Mid-roll
            exoPlayerSampleApp.clickBasedOnTextScrollTo(driver, "Ooyala Ad Mid-roll");
            Thread.sleep(2000);
            //verify if player was loaded
            exoPlayerSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //wait for the presence of start screen
            exoPlayerSampleApp.waitForPresenceOfText(driver,"h");
            //Clicking on Play button
            exoPlayerSampleApp.getPlay(driver);
            //creating object of EventVerification Class
            EventVerification ev = new EventVerification();
            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);
            //tapping on video screen
            exoPlayerSampleApp.screentapping(driver);
            //pause the video in normal screen
            exoPlayerSampleApp.pausingVideo(driver);
            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 40000);
            //seek video in normal screen
            exoPlayerSampleApp.seek_video(driver,300);
            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 50000);
            //handling loading spinner
            exoPlayerSampleApp.loadingSpinner(driver);
            //resume playback in normal screen
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
            logger.error("ooyalaAd_Midroll throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"ooyalaAd_Midroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void ooyalaAd_Postroll() throws Exception{
        try {
            // wait till home screen of ExoPlayerApp is opened
            exoPlayerSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
            // Write to console activity name of home screen app
            logger.debug("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //click on basic playback
            exoPlayerSampleApp.clickBasedOnText(driver, "Basic Playback");
            //display the current activity to console
            logger.debug(" Print current activity name"+driver.currentActivity());
            //wait for the assets to load properly
            exoPlayerSampleApp.waitForPresenceOfText(driver,"4:3 Aspect Ratio");
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Write to console activity name of home screen app
            logger.debug("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video Ooyala Ad Post-roll
            exoPlayerSampleApp.clickBasedOnTextScrollTo(driver, "Ooyala Ad Post-roll");
            Thread.sleep(2000);
            //verify if player was loaded
            exoPlayerSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //wait for the presence of start screen
            exoPlayerSampleApp.waitForPresenceOfText(driver,"h");
            //Clicking on Play button
            exoPlayerSampleApp.getPlay(driver);
            //creating object of EventVerification Class
            EventVerification ev = new EventVerification();
            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);
            //tapping on video screen
            exoPlayerSampleApp.screentapping(driver);
            //pause the video in normal screen
            exoPlayerSampleApp.pausingVideo(driver);
            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 40000);
            //seek video in normal screen
            exoPlayerSampleApp.seek_video(driver,600);
            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 50000);
            //handling loading spinner
            exoPlayerSampleApp.loadingSpinner(driver);
            //resume playback in normal screen
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
            logger.error("ooyalaAd_Postroll throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"ooyalaAd_Postroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void widevineDASH() throws Exception{
        try {
            // wait till home screen of ExoPlayerApp is opened
            exoPlayerSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
            // Write to console activity name of home screen app
            logger.debug("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //click on basic playback
            exoPlayerSampleApp.clickBasedOnText(driver, "Basic Playback");
            
            //display the current activity to console
            logger.debug(" Print current activity name"+driver.currentActivity());
            
            //wait for the assets to load properly
            exoPlayerSampleApp.waitForPresenceOfText(driver,"4:3 Aspect Ratio");
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Write to console activity name of home screen app
            logger.debug("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video Widevine DASH
            exoPlayerSampleApp.clickBasedOnText(driver, "Widevine DASH");
            Thread.sleep(2000);
            //verify if player was loaded
            exoPlayerSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //wait for the presence of start screen
            exoPlayerSampleApp.waitForPresenceOfText(driver,"h");
            //Clicking on Play button
            exoPlayerSampleApp.getPlay(driver);
            //creating object of EventVerification Class
            EventVerification ev = new EventVerification();
            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);
            //tapping on video screen
            exoPlayerSampleApp.screentapping(driver);
            //pause the video in normal screen
            exoPlayerSampleApp.pausingVideo(driver);
            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);
            //seek video in normal screen
            exoPlayerSampleApp.seek_video(driver,920);
            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);
            //handling loading spinner
            exoPlayerSampleApp.loadingSpinner(driver);
            //resume playback in normal screen
            exoPlayerSampleApp.getPlay(driver);
            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 200000);
        }
        catch(Exception e){
            logger.error("widevineDASH throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"widevineDASH");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void clearHLSHigh() throws Exception{
        try {
            // wait till home screen of ExoPlayerApp is opened
            exoPlayerSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
            // Write to console activity name of home screen app
            logger.debug("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //click on basic playback
            exoPlayerSampleApp.clickBasedOnText(driver, "Basic Playback");
            //display the current activity to console
            logger.debug(" Print current activity name"+driver.currentActivity());
            //wait for the assets to load properly
            exoPlayerSampleApp.waitForPresenceOfText(driver,"4:3 Aspect Ratio");
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Write to console activity name of home screen app
            logger.debug("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video Clear HLS High Profile
            exoPlayerSampleApp.clickBasedOnText(driver, "Clear HLS High Profile");
            Thread.sleep(2000);
            //verify if player was loaded
            exoPlayerSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //wait for the presence of start screen
            exoPlayerSampleApp.waitForPresenceOfText(driver,"h");
            //Clicking on Play button
            exoPlayerSampleApp.getPlay(driver);
            //creating object of EventVerification Class
            EventVerification ev = new EventVerification();
            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);
            //tapping on video screen
            exoPlayerSampleApp.screentapping(driver);
            //pause the video in normal screen
            exoPlayerSampleApp.pausingVideo(driver);
            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);
            //seek video in normal screen
            exoPlayerSampleApp.seek_video(driver,950);
            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);
            //handling loading spinner
            exoPlayerSampleApp.loadingSpinner(driver);
            //check whether play button is visible or not
            exoPlayerSampleApp.checkPlayButton(driver);
            //resume playback in normal screen
            exoPlayerSampleApp.getPlay(driver);
            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 230000);
        }
        catch(Exception e){
            logger.error("clearHLSHigh throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"clearHLSHigh");
            Assert.assertTrue(false, "This will fail!");
        }
    }
}