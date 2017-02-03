package testpackage.tests.exoPlayerSampleApp;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import testpackage.pageobjects.exoPlayerSampleApp;
import testpackage.utils.*;
import java.io.IOException;
import java.util.Properties;

public class IMABasicTests2 extends EventLogTest {
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
        //get the property values
        p = prop.loadProperty();
        String prop = p.getProperty("appPackage");
        //uninstall the app from the device
        Appuninstall.uninstall(prop);
    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws Exception {
        // remove or delete events log file from the device
        RemoveEventsLogFile.removeEventsFileLog();
        Thread.sleep(10000);
    }

    @Test
    public void imaNonAdRulesPreroll() throws Exception{
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
            exoPlayerSampleApp.waitForPresenceOfText(driver,"IMA Non Ad-Rules Preroll");

            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");

            // Write to console activity name of home screen app
            logger.debug("Ooyala Skin - Google IMA List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video IMA Non Ad-Rules Preroll.
            exoPlayerSampleApp.clickBasedOnTextScrollTo(driver, "IMA Ad-Rules Preroll");
            Thread.sleep(2000);

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
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        }
        catch(Exception e){
            logger.error("imaNonAdRulesPreroll throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"IMA Non Ad-Rules Preroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void imaNonAdRulesMidroll() throws Exception{
        try {
            // wait till home screen of ExoPlayerApp is opened
            exoPlayerSampleApp.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");

            // Write to console activity name of home screen app
            logger.debug("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //click on Google IMA Integration
            exoPlayerSampleApp.clickBasedOnTextScrollTo(driver, "Google IMA Integration");
            logger.debug(" Print current activity name"+driver.currentActivity());

            //wait for the assets to load properly
            exoPlayerSampleApp.waitForPresenceOfText(driver,"IMA Non Ad-Rules Midroll");

            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");

            // Write to console activity name of home screen app
            logger.debug("Ooyala Skin - Google IMA List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video as IMA Non Ad-Rules Midroll.
            exoPlayerSampleApp.clickBasedOnTextScrollTo(driver, "IMA Non Ad-Rules Midroll");

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
            logger.error("imaNonAdRulesMidroll throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"IMA Non Ad-Rules Midroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void imaNonAdRulesPostoll() throws Exception{
        try {
            // wait till home screen of ExoPlayerApp is opened
            exoPlayerSampleApp.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");

            // Write to console activity name of home screen app
            logger.debug("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //click on Google IMA Integration
            exoPlayerSampleApp.clickBasedOnTextScrollTo(driver, "Google IMA Integration");
            logger.debug(" Print current activity name"+driver.currentActivity());

            //wait for the assets to load properly
            exoPlayerSampleApp.waitForPresenceOfText(driver,"IMA Non Ad-Rules Postroll");

            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");

            // Write to console activity name of home screen app
            logger.debug("Ooyala Skin - Google IMA List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video as IMA Non Ad-Rules Postroll
            exoPlayerSampleApp.clickBasedOnTextScrollTo(driver, "IMA Non Ad-Rules Postroll");
            Thread.sleep(2000);

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
            exoPlayerSampleApp.seek_video(driver,700);

            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 50000);

            //handle the loading spinner
            exoPlayerSampleApp.loadingSpinner(driver);

            //resume the video playback in normal screen
            exoPlayerSampleApp.getPlay(driver);

            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 60000);

            //ad started event verification
            ev.verifyEvent("adStarted", "Ad Started to Play", 60000);

            // Ad playback has been completed.
            ev.verifyEvent("adCompleted", "Ad Playback Completed", 70000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        }
        catch(Exception e){
            logger.error("imaNonAdRulesPostoll throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"IMA Non Ad-Rules Postroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void imaNonAdRulesPreMidMidPost() throws Exception{
        try {
            // wait till home screen of ExoPlayerApp is opened
            exoPlayerSampleApp.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");

            // Write to console activity name of home screen app
            logger.debug("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //click on Google IMA Integration
            exoPlayerSampleApp.clickBasedOnTextScrollTo(driver, "Google IMA Integration");

            logger.debug(" Print current activity name"+driver.currentActivity());

            //wait for the assets to load properly
            exoPlayerSampleApp.waitForPresenceOfText(driver,"IMA Non Ad-Rules Preroll");

            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");

            // Write to console activity name of home screen app
            logger.debug("Ooyala Skin - Google IMA List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video IMA Non Ad-Rules Pre-Mid-Mid-exoPlayerSampleAppst
            exoPlayerSampleApp.clickBasedOnTextScrollTo(driver, "IMA Non Ad-Rules Pre-Mid-Mid-Post");
            Thread.sleep(2000);

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
            ev.verifyEvent("playStarted", " Video Started Play ", 40000);
            Thread.sleep(2000);

            //tapping on the video screen
            exoPlayerSampleApp.screentapping(driver);

            //pausing the video in normal screen
            exoPlayerSampleApp.pausingVideo(driver);

            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 40000);

            //seek video in normal screen
            exoPlayerSampleApp.seek_video(driver,80);

            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 50000);

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

            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            // Ad playback has been completed.
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        }
        catch(Exception e){
            logger.error("imaNonAdRulesPreMidMidPost throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"IMA Non Ad-Rules Pre-Mid-Mid-Post");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void noAds() throws Exception{
        try {
            // wait till home screen of ExoPlayerApp is opened
            exoPlayerSampleApp.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");

            // Write to console activity name of home screen app
            logger.debug("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //click on Google IMA Integration
            exoPlayerSampleApp.clickBasedOnTextScrollTo(driver, "Google IMA Integration");

            //display the current activity to console
            logger.debug(" Print current activity name"+driver.currentActivity());

            //wait for the assets to load properly
            exoPlayerSampleApp.waitForPresenceOfText(driver,"IMA Non Ad-Rules Preroll");

            // Assert if current activity is indeed equal to the activity name of app home screen
            exoPlayerSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");

            // Write to console activity name of home screen app
            logger.debug("Ooyala Skin - Google IMA List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video No Ads.
            exoPlayerSampleApp.clickBasedOnTextScrollTo(driver, "No Ads");
            Thread.sleep(2000);

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
            exoPlayerSampleApp.seek_video(driver,1000);

            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);

            //handle the loading spinner
            exoPlayerSampleApp.loadingSpinner(driver);

            //resume the playback in normal screen
            exoPlayerSampleApp.getPlay(driver);

            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 190000);
        }
        catch(Exception e){
            logger.error("NoAds throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"No Ads");
            Assert.assertTrue(false, "This will fail!");
        }
    }
}
