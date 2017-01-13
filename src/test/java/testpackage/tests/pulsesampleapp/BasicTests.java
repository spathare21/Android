package testpackage.tests.pulsesampleapp;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import testpackage.pageobjects.PulseSampleApp;
import testpackage.utils.*;

import java.io.IOException;
import java.util.Properties;

public class BasicTests extends EventLogTest {
    //Creating an object of Logger Class
    final static Logger logger = Logger.getLogger(BasicTests.class);
    //Creating an object of Properties class
    Properties properties;
    // Creating an Object of pulsesampleapp pageobject class
    PulseSampleApp pulseSampleApp = new PulseSampleApp();
    @BeforeClass
    public void beforeTest() throws Exception {
        // closing all recent app from background.
        CloserecentApps.closeApps();
        logger.info("BeforeTest \n");
        logger.debug(System.getProperty("user.dir"));
        // Get Property Values
        LoadPropertyValues loadPropertyValues = new LoadPropertyValues();
        properties = loadPropertyValues.loadProperty("pulsesampleapp.properties");
        //Printing device details
        logger.debug("Device id from properties file " + properties.getProperty("deviceName"));
        logger.debug("PortraitMode from properties file " + properties.getProperty("PortraitMode"));
        logger.debug("Path where APK is stored"+ properties.getProperty("appDir"));
        logger.debug("APK name is "+ properties.getProperty("appValue"));
        logger.debug("Platform under Test is "+ properties.getProperty("platformName"));
        logger.debug("Mobile OS Version is "+ properties.getProperty("OSVERSION"));
        logger.debug("Package Name of the App is "+ properties.getProperty("appPackage"));
        logger.debug("Activity Name of the App is "+ properties.getProperty("appActivity"));
        //setup and initialize android driver
        SetUpAndroidDriver setUpdriver = new SetUpAndroidDriver();
        driver = setUpdriver.setUpandReturnAndroidDriver(properties.getProperty("udid"), properties.getProperty("appDir"), properties.getProperty("appValue"), properties.getProperty("platformName"), properties.getProperty("platformVersion"), properties.getProperty("appPackage"), properties.getProperty("appActivity"));
        Thread.sleep(2000);
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        logger.info("beforeMethod \n");
        driver.manage().logs().get("logcat");
        //push the log file to device
        PushLogFileToDevice logpush = new PushLogFileToDevice();
        logpush.pushLogFile();
        if (driver.currentActivity() != "com.ooyala.sample.lists.PulseListActivity") {
            driver.startActivity("com.ooyala.sample.pulsesampleapp", "com.ooyala.sample.lists.PulseListActivity");
        }
    }

    @AfterClass
    public void afterTest() throws InterruptedException, IOException {
        //closing app after completion on script
        driver.closeApp();
        //end the driver session
        driver.quit();
        //Uninstalling the app
        String prop = properties.getProperty("appPackage");
        Appuninstall.uninstall(prop);
    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws Exception {
        //Removing the events log file after test is completed for single asset.
        RemoveEventsLogFile.removeEventsFileLog();
        Thread.sleep(10000);
    }

    @Test
    public void NoAds() throws Exception{
        try {
            // Wait till home screen of PulseSampleApp is opened
            pulseSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            pulseSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.PulseListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("Pulse Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select No Ads Video from Pulse Sample App
            pulseSampleApp.clickBasedOnText(driver, "No ads");
            //verify if player was loaded
            pulseSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            pulseSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PulsePlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            // Wait for the presence of Play button
            pulseSampleApp.waitForPresenceOfText(driver,"h");
            //Clicking on Play button in Pulse
            pulseSampleApp.getPlay(driver);
            //Creating object of event verification class
            EventVerification eventVerification = new EventVerification();
            //Play Started Verification
            eventVerification.verifyEvent("playStarted", " Video Started to Play ", 30000);
            //clicking on pause button to pause the video.
            pulseSampleApp.pauseVideo(driver);
            //Verification of Paused event
            eventVerification.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);
            //seeking the video
            pulseSampleApp.seek_video(driver, 800);
            // Verifing seek complete event
            eventVerification.verifyEvent("seekCompleted", " Video seeking completed ", 70000);
            // resume the playback of video
            pulseSampleApp.getPlay(driver);
            // verifing the playing event
            eventVerification.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);
            //Wait for video to finish and verify the playCompleted event .
            eventVerification.verifyEvent("playCompleted", " Video Completed Play ", 150000);
        }
        catch(Exception e)
        {
            logger.error("No ads throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"Noads");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void PrerollDemo() throws Exception{
        try {
            // wait till home screen of Pulse is opened
            pulseSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            pulseSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.PulseListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("Pulse Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select Preroll Demo asset to play
            pulseSampleApp.clickBasedOnText(driver, "Preroll demo");
            //verify if player was loaded
            pulseSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            pulseSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PulsePlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            // Wait for the presence of Play button
            pulseSampleApp.waitForPresenceOfText(driver,"h");
            //Clicking on Play button in Pulse
            pulseSampleApp.getPlay(driver);
            //Creating object of event verification class
            EventVerification eventVerification = new EventVerification();
            //Ad Started event Verification
            eventVerification.verifyEvent("adStarted", " Ad Started to Play ", 30000);
            //Ad Completed event Verification
            eventVerification.verifyEvent("adCompleted", " Ad Playback Completed ", 40000);
            //Play Started event Verification
            eventVerification.verifyEvent("playStarted", " Video Started to Play ", 60000);
            //pausing the video by clicking on pause button
            pulseSampleApp.pauseVideo(driver);
            // Verifing Paused event
            eventVerification.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);
            //seeking the video
            pulseSampleApp.seek_video(driver, 700);
            // Verifing Seek completed issue
            eventVerification.verifyEvent("seekCompleted", " Video seeking completed ", 70000);
            // Resume the playback of video
            pulseSampleApp.getPlay(driver);
            // Verifing the playing video
            eventVerification.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);
            //Wait for video to finish and verify the playCompleted event .
            eventVerification.verifyEvent("playCompleted", " Video Completed Play ", 150000);
        }
        catch(Exception e)
        {
            logger.error("PrerollDemo throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"PrerollDemo");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void MidrollDemo() throws Exception{
        try {
            // wait till home screen of Pulse is opened
            pulseSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            pulseSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.PulseListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("Pulse Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select Midroll demo asset from PulseSampleApp
            pulseSampleApp.clickBasedOnText(driver, "Midroll demo");
            //verify if player was loaded
            pulseSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            pulseSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PulsePlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            // Waiting for Playbutton to appear on screen
            pulseSampleApp.waitForPresenceOfText(driver,"h");
            //Clicking on Play button in Pulse
            pulseSampleApp.getPlay(driver);
            // creating object of Event verification class
            EventVerification eventVerification = new EventVerification();
            //Verifying Play started event
            eventVerification.verifyEvent("playStarted", " Video Started to Play ", 30000);
            //Verifying Ad started Event
            eventVerification.verifyEvent("adStarted", " Ad Started to Play ", 50000);
            //Verifying Ad Completed Event
            eventVerification.verifyEvent("adCompleted", " Ad Playback Completed ", 60000);
            // Clicking on pause button to pause playing video
            pulseSampleApp.pauseVideo(driver);
            // Verifying Paused Event
            eventVerification.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);
            //Seeking the video
            pulseSampleApp.seek_video(driver, 700);
            // Verifying seek complete event
            eventVerification.verifyEvent("seekCompleted", " Video seeking completed ", 70000);
            // resuming the playback of video
            pulseSampleApp.getPlay(driver);
            // Verifying playing event
            eventVerification.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);
            //Wait for video to finish and verify the playCompleted event .
            eventVerification.verifyEvent("playCompleted", " Video Completed Play ", 150000);
        }
        catch(Exception e)
        {
            logger.error("MidrollDemo throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"MidrollDemo");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void PostrollDemo() throws Exception{
        try {
            // wait till home screen of Pulse is opened
            pulseSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            pulseSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.PulseListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("Pulse Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select Postroll demo asset from PulseSampleApp
            pulseSampleApp.clickBasedOnText(driver, "Postroll demo");
            //verify if player was loaded
            pulseSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            pulseSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PulsePlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            // Waiting for Playbutton to appear on screen
            pulseSampleApp.waitForPresenceOfText(driver,"h");
            //Clicking on Play button in Pulse
            pulseSampleApp.getPlay(driver);
            // creating object of Event verification class
            EventVerification eventVerification = new EventVerification();
            //Play Started event Verification
            eventVerification.verifyEvent("playStarted", " Video Started to Play ", 30000);
            //pausing the video by clicking on pause button
            pulseSampleApp.pauseVideo(driver);
            // Verifing Paused event
            eventVerification.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 40000);
            //seeking the video
            pulseSampleApp.seek_video(driver, 700);
            // Verifing Seek completed issue
            eventVerification.verifyEvent("seekCompleted", " Video seeking completed ", 50000);
            // Resume the playback of video
            pulseSampleApp.getPlay(driver);
            // Verifing the playing video
            eventVerification.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 60000);
            //Verifying Ad started Event
            eventVerification.verifyEvent("adStarted", " Ad Started to Play ", 70000);
            //Verifying Ad Completed Event
            eventVerification.verifyEvent("adCompleted", " Ad Playback Completed ", 80000);
            //Wait for video to finish and verify the playCompleted event .
            eventVerification.verifyEvent("playCompleted", " Video Completed Play ", 250000);
        }
        catch(Exception e)
        {
            logger.error("PostrollDemo throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"PostrollDemo");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void PreMidPostrollDemo() throws Exception{
        try {
            // wait till home screen of Pulse is opened
            pulseSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            pulseSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.PulseListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("Pulse Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select PreMidPost Demo asset.
            pulseSampleApp.clickBasedOnText(driver, "Pre-/mid-/postroll demo");
            //verify if player was loaded
            pulseSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            pulseSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PulsePlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            // waiting for play button to display on screen
            pulseSampleApp.waitForPresenceOfText(driver,"h");
            //Clicking on Play button
            pulseSampleApp.getPlay(driver);
            // Creating object of Event Verification class
            EventVerification eventVerification = new EventVerification();
            //Ad Started Verification
            eventVerification.verifyEvent("adStarted", " Pre - Ad Started to Play ", 30000);
            // Ad Completed Verification
            eventVerification.verifyEvent("adCompleted", " Pre - Ad Playback Completed ", 45000);
            // Play started event verification
            eventVerification.verifyEvent("playStarted", " Video Started to Play ", 50000);
            //Ad Started Verification
            eventVerification.verifyEvent("adPodStarted", " Mid - Ad Started to Play ", 70000);
            //Ad Completed Verification
            eventVerification.verifyEvent("adCompleted", " Mid - Ad Playback Completed ", 80000);
            //Ad Started Verification
            eventVerification.verifyEvent("adPodStarted", "Second Mid - Ad  Started to Play ", 100000);
            //Ad Completed Verification
            eventVerification.verifyEvent("adCompleted", " Post - Ad Playback Completed ", 110000);
            // Video playing event verification
            eventVerification.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video Started play", 70000);
            // clicking on pause button
            pulseSampleApp.pauseVideo(driver);
            // Verifying Paused event
            eventVerification.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 40000);
            //Seeking the video to specific location
            pulseSampleApp.seek_video(driver,700);
            //Verification of seek event
            eventVerification.verifyEvent("seekCompleted", " Playing Video was Seeked " , 50000);
            //resume the playback of video
            pulseSampleApp.getPlay(driver);
            //Verification of Playing event
            eventVerification.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 70000);
            //Ad started verification
            eventVerification.verifyEvent("adPodStarted", "Post - Ad  Started to Play ", 150000);
            //Ad Completed Verification
            eventVerification.verifyEvent("adCompleted", " Post - Ad Playback Completed ", 60000);
            //Wait for video to finish and verify the playCompleted event .
            eventVerification.verifyEvent("playCompleted", " Video Completed Play ", 250000);
        }
        catch(Exception e)
        {
            logger.error("PreMidPostrollDemo throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"PreMidPostrollDemo");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void PreMidPostSkippable() throws Exception{
        try {
            // wait till home screen of Pulse is opened
            pulseSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            pulseSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.PulseListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("Pulse Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select PreMidPost Skippable asset
            pulseSampleApp.clickBasedOnText(driver, "Pre-/mid-/postroll skippable");
            //verify if player was loaded
            pulseSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            pulseSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PulsePlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            // Waiting for Play button
            pulseSampleApp.waitForPresenceOfText(driver,"h");
            //Clicking on Play button in Pulse
            pulseSampleApp.getPlay(driver);
            //Creating Object for Eventverification class
            EventVerification eventVerification = new EventVerification();
            //Ad Started Verification
            eventVerification.verifyEvent("adStarted", " Pre - Ad Started to Play ", 30000);
            // Clicking on skip button
            String skiptext="Skip Ad";
            driver.findElementByAndroidUIAutomator("new UiSelector().textContains(\""+skiptext+"\")");
            pulseSampleApp.clickBasedOnText(driver,"Skip Ad");
            Thread.sleep(5000);
            //Ad Completed Verification
            eventVerification.verifyEvent("adCompleted", " Pre - Ad Playback Completed ", 30000);
            //Verification of Play start event
            eventVerification.verifyEvent("playStarted", " Video Started to Play ", 30000);
            //Ad Started Verification
            eventVerification.verifyEvent("adPodStarted", " Mid - Ad Started to Play ", 30000);
            //Ad Completed Verification
            eventVerification.verifyEvent("adCompleted", " Mid - Ad Playback Completed ", 30000);
            //Ad Started Verification
            eventVerification.verifyEvent("adPodStarted", "Second Mid - Ad  Started to Play ", 90000);
            //Ad Completed Verification
            eventVerification.verifyEvent("adCompleted", " Post - Ad Playback Completed ", 60000);
            //Verification of Playing event
            eventVerification.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video Started play", 70000);
            // Clicking on pause button
            pulseSampleApp.pauseVideo(driver);
            // Verification of Paused event
            eventVerification.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 40000);
            // Seek the video to specific location
            pulseSampleApp.seek_video(driver, 700);
            // Verification of seekcomplete event
            eventVerification.verifyEvent("seekCompleted", " Playing Video was Seeked ", 50000);
            // Resume the playback of paused video
            pulseSampleApp.getPlay(driver);
            // verification of playing event
            eventVerification.verifyEvent("Notification Received: stateChanged - state: PLAYING", "Video resumed", 70000);
            //Ad started verification
            eventVerification.verifyEvent("adPodStarted", "Post - Ad  Started to Play ", 90000);
            //Ad Completed Verification
            eventVerification.verifyEvent("adCompleted", " Post - Ad Playback Completed ", 60000);
            //Wait for video to finish and verify the playCompleted event .
            eventVerification.verifyEvent("playCompleted", " Video Completed Play ", 250000);
        }
        catch(Exception e)
        {
            logger.error("PreMidPostSkippable throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"PreMidPostSkippable");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void FrequencyCappingDemo() throws Exception{
        try {
            // wait till home screen of Pulse is opened
            pulseSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            pulseSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.PulseListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("Pulse Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video .
            pulseSampleApp.clickBasedOnText(driver, "Frequency capping demo");
            //verify if player was loaded
            pulseSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            pulseSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PulsePlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            // Wait for Play buttomn to display
            pulseSampleApp.waitForPresenceOfText(driver,"h");
            //Clicking on Play button in Pulse
            pulseSampleApp.getPlay(driver);
            // Creating object of event verification class
            EventVerification eventVerification = new EventVerification();
            // Ad verification
            eventVerification.verifyEvent("adStarted", " Ad Started to Play ", 30000);
            //Ad Completed Verification
            eventVerification.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);
            //Play Started
            eventVerification.verifyEvent("playStarted", " Video Started to Play ", 60000);
            // click on pause button
            pulseSampleApp.pauseVideo(driver);
            // Paused event verification
            eventVerification.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);
            //seeking the video
            pulseSampleApp.seek_video(driver, 700);
            //Seek complete event verification
            eventVerification.verifyEvent("seekCompleted", " Video seeking completed ", 70000);
            // playing video again.
            pulseSampleApp.getPlay(driver);
            // Playing event verification
            eventVerification.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);
            //Wait for video to finish and verify the playCompleted event .
            eventVerification.verifyEvent("playCompleted", " Video Completed Play ", 150000);
        }
        catch(Exception e)
        {
            logger.error("FrequencyCappingDemo throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"FrequencyCappingDemo");
            Assert.assertTrue(false, "This will fail!");
        }
    }


    //TODO: Find work around for AdError assets
    //@Test
    public void PrerolldemoAdFileInvalid() throws Exception{
        try {
            // wait till home screen of Pulse is opened
            pulseSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            pulseSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.PulseListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("Pulse Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video .
            pulseSampleApp.clickBasedOnText(driver, "Preroll demo - Ad media file invalid");
            //verify if player was loaded
            pulseSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            pulseSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PulsePlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //waiting for Play button to load
            pulseSampleApp.waitForPresenceOfText(driver,"h");
            //Clicking on Play button in Pulse
            pulseSampleApp.getPlay(driver);
            // creating object of event verification class
            EventVerification eventVerification = new EventVerification();
            //Play Started Verification
            eventVerification.verifyEvent("playStarted", " Video Started to Play ", 30000);
            //pausing the video.
            pulseSampleApp.pauseVideo(driver);
            // Paused event verification
            eventVerification.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);
            //seeking the video
            pulseSampleApp.seek_video(driver, 700);
            // seek complete event verification
            eventVerification.verifyEvent("seekCompleted", " Video seeking completed ", 70000);
            // playing video again.
            pulseSampleApp.getPlay(driver);
            // Playing event verification
            eventVerification.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);
            //Wait for video to finish and verify the playCompleted event .
            eventVerification.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        }
        catch(Exception e)
        {
            logger.error("Preroll demo - Ad media file invalid throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"Noads");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    //TODO: Find work around for AdError assets
    //@Test
    public void PrerolldemoAdtimeout() throws Exception{
        try {
            // wait till home screen of Pulse is opened
            pulseSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            pulseSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.PulseListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("Pulse Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video .
            pulseSampleApp.clickBasedOnText(driver, "Preroll demo - Ad media file timeout");
            //verify if player was loaded
            pulseSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            pulseSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PulsePlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            // Wait to load Play buttonm
            pulseSampleApp.waitForPresenceOfText(driver,"h");
            //Clicking on Play button in Pulse
            pulseSampleApp.getPlay(driver);
            // creting object of event verification class
            EventVerification eventVerification = new EventVerification();
            //Play Started Verification
            eventVerification.verifyEvent("playStarted", " Video Started to Play ", 30000);
            //pausing the video.
            pulseSampleApp.pauseVideo(driver);
            // Paused event verification
            eventVerification.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);
            //seeking the video
            pulseSampleApp.seek_video(driver, 700);
            // seek complete event verification
            eventVerification.verifyEvent("seekCompleted", " Video seeking completed ", 70000);
            // playing video again.
            pulseSampleApp.getPlay(driver);
            // Playing event verification
            eventVerification.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);
            //Wait for video to finish and verify the playCompleted event .
            eventVerification.verifyEvent("Notification Received: playCompleted - state: INIT", " Video Completed Play ", 90000);
        }
        catch(Exception e)
        {
            logger.error("Preroll demo - Ad media file timeout throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"Noads");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    //TODO: Find work around for AdError assets
    //@Test
    public void PrerolldemoAdFile404() throws Exception{
        try {
            // wait till home screen of Pulse is opened
            pulseSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            pulseSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.PulseListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("Pulse Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video .
            pulseSampleApp.clickBasedOnText(driver, "Preroll demo - Ad media file 404");
            //verify if player was loaded
            pulseSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            pulseSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PulsePlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            // Wait for the presence of Play button
            pulseSampleApp.waitForPresenceOfText(driver,"h");
            //Clicking on Play button in Pulse
            pulseSampleApp.getPlay(driver);
            // creating object for event verification class
            EventVerification eventVerification = new EventVerification();
            // Play event verification
            eventVerification.verifyEvent("playStarted", " Video Started to Play ", 30000);
            //pausing the video.
            pulseSampleApp.pauseVideo(driver);
            //Paused event verification
            eventVerification.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);
            //seeking the video
            pulseSampleApp.seek_video(driver, 700);
            // Seek complete event verification
            eventVerification.verifyEvent("seekCompleted", " Video seeking completed ", 70000);
            // playing video again.
            pulseSampleApp.getPlay(driver);
            // Playing event verification
            eventVerification.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);
            //Wait for video to finish and verify the playCompleted event .
            eventVerification.verifyEvent("Notification Received: playCompleted - state: INIT", " Video Completed Play ", 90000);
        }
        catch(Exception e)
        {
            logger.error("Preroll demo - Ad media file 404 throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"Noads");
            Assert.assertTrue(false, "This will fail!");
        }
    }



}
