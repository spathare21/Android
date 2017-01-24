package testpackage.tests.basicplaybacksampleapp;

import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import testpackage.pageobjects.BasicPlaybackSampleApp;
import testpackage.utils.*;

import java.io.IOException;
import java.lang.annotation.Target;
import java.util.Properties;

/**
 * Created by Sachin on 4/18/2016.
 */
public class DeepTests2 extends EventLogTest{

    final static Logger logger = Logger.getLogger(DeepTests2.class);

    @BeforeClass
    public void beforeTest() throws Exception {
        // closing all recent app from background.
        CloserecentApps.closeApps();
        //driver.quit();

        logger.info("BeforeTest \n");

        logger.debug(System.getProperty("user.dir"));
        // Get Property Values
        LoadPropertyValues prop = new LoadPropertyValues();
        Properties p = prop.loadProperty();

        //logger.debug("Device id from properties file " + p.getProperty("deviceName"));
        //logger.debug("PortraitMode from properties file " + p.getProperty("PortraitMode"));
        //logger.debug("Path where APK is stored"+ p.getProperty("appDir"));
        //logger.debug("APK name is "+ p.getProperty("app"));
        //logger.debug("Platform under Test is "+ p.getProperty("platformName"));
        //logger.debug("Mobile OS Version is "+ p.getProperty("OSVERSION"));
        //logger.debug("Package Name of the App is "+ p.getProperty("appPackage"));
        //logger.debug("Activity Name of the App is "+ p.getProperty("appActivity"));

        SetUpAndroidDriver setUpdriver = new SetUpAndroidDriver();
        driver = setUpdriver.setUpandReturnAndroidDriver(p.getProperty("udid"), p.getProperty("appDir"), p.getProperty("appValue"), p.getProperty("platformName"), p.getProperty("platformVersion"), p.getProperty("appPackage"), p.getProperty("appActivity"));
        Thread.sleep(2000);
    }

    @BeforeMethod
    //public void beforeTest() throws Exception{
    public void beforeMethod() throws Exception {
        logger.info("beforeMethod \n");
        driver.manage().logs().get("logcat");
        PushLogFileToDevice logpush = new PushLogFileToDevice();
        logpush.pushLogFile();
        if (driver.currentActivity() != "com.ooyala.sample.lists.BasicPlaybackListActivity") {
            driver.startActivity("com.ooyala.sample.BasicPlaybackSampleApp", "com.ooyala.sample.lists.BasicPlaybackListActivity");
        }

        // Get Property Values
        LoadPropertyValues prop1 = new LoadPropertyValues();
        Properties p1 = prop1.loadProperty();

        logger.debug(" Screen Mode " + p1.getProperty("ScreenMode"));

//        if(p1.getProperty("ScreenMode") != "P"){
//            logger.info("Inside landscape Mode ");
//            driver.rotate(ScreenOrientation.LANDSCAPE);
//        }
//
//        driver.rotate(ScreenOrientation.LANDSCAPE);
//        driver.rotate(ScreenOrientation.LANDSCAPE);

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

    //TODO : create unique file names for snapshots taken .

    @org.testng.annotations.Test
    public void OoyalaAdPreroll() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            BasicPlaybackSampleApp po = new BasicPlaybackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("BasicPlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnTextScrollTo(driver, "Ooyala Ad Pre-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            Thread.sleep(3000);

            //waitting for start screen
            po.waitForTextView(driver,"00:00");
            Thread.sleep(1000);

            // move to full screen
            po.gotoFullScreen(driver);
            Thread.sleep(2000);

            // event verification for full screen
            EventVerification ev = new EventVerification();
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in full screen", 30000);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(3000);

            //Play video in full screen
            po.playInFullScreen(driver);
            //Thread.sleep(1000);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 35000);
            Thread.sleep(5000);
            // AD completed event  verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 40000);
            Thread.sleep(1000);

            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 40000);
            Thread.sleep(5000);

            //Tapping on screen
            po.screenTap(driver);
            Thread.sleep(1000);

            //Pausing video in full screen
            po.pauseInFullScreen(driver);
            //Thread.sleep(1000);
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 47000);
            Thread.sleep(1000);

            //Seeking the video in full screen
            po.seekVideoFullscreen(driver);
            //Thread.sleep(1000);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 50000);
            Thread.sleep(3000);

            // going back again in normal screen
            po.gotoNormalScreen(driver);
           // Thread.sleep(2000);

            // event verification for normal screen
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in normal screen", 53000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 53000);
            Thread.sleep(2000);

            //Play video in normal screen
            po.playInNormalScreen(driver);
            //Thread.sleep(1000);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 57000);
            Thread.sleep(3000);

            //Tapping on screen
            po.screenTap(driver);
            Thread.sleep(500);

            //Pausing video in normal screen
            po.pauseInNormalScreen(driver);
            //Thread.sleep(1000);
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 61000);
            Thread.sleep(1000);

            //Seeking video in normal screen
            po.seekVideo(driver);
            //Thread.sleep(1000);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 65000);
            Thread.sleep(3000);

            // playing video in normal screen
            po.playInNormalScreen(driver);
            //verifing event for play
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 70000);
            Thread.sleep(5000);

            // video completed event verificaiton
            ev.verifyEvent("playCompleted", " Video Completed Play ", 80000);

        } catch (Exception e) {
            logger.error("OoyalaAdPreroll throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"OoyalaAdPreroll");
            Assert.assertTrue(false, "This will fail!");
        }


    }

    @org.testng.annotations.Test
    public void OoyalaAdMidroll() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            BasicPlaybackSampleApp po = new BasicPlaybackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("BasicPlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnTextScrollTo(driver, "Ooyala Ad Mid-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //Thread.sleep(3000);

            //waitting for start screen
            po.waitForTextView(driver,"00:00");
            Thread.sleep(1000);

            // move to full screen
            po.gotoFullScreen(driver);
            //Thread.sleep(2000);

            // event verification for full screen
            EventVerification ev = new EventVerification();
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in full screen", 30000);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(3000);

            //Play video in full screen
            po.playInFullScreen(driver);
            //Thread.sleep(1000);

            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
            Thread.sleep(4000);

            //Tapping on screen
            po.screenTap(driver);
            Thread.sleep(1000);

            //Pausing video in full screen
            po.pauseInFullScreen(driver);
            //Thread.sleep(1000);
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 40000);
            Thread.sleep(2000);

            //Seeking the video in full screen
            po.seekVideoFullscreen(driver);
            //Thread.sleep(1000);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 45000);
            Thread.sleep(3000);

            // going back again in normal screen
            po.gotoNormalScreen(driver);
           // Thread.sleep(2000);

            // event verification for normal screen
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in normal screen", 50000);
            Thread.sleep(1000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 52000);
            Thread.sleep(3000);

            //Play video in normal screen
            po.playInNormalScreen(driver);
           // Thread.sleep(1000);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 60000);
            Thread.sleep(3000);

            //Tapping on screen
            po.screenTap(driver);
            Thread.sleep(1000);

            //Pausing video in normal screen
            po.pauseInNormalScreen(driver);
            //Thread.sleep(1000);
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 65000);
            Thread.sleep(2000);

            //Seeking video in normal screen
            po.seekVideo(driver);
            //Thread.sleep(1000);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 70000);
            Thread.sleep(3000);

            // playing video in normal screen
            po.playInNormalScreen(driver);
            //verifing event for play
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 75000);
            Thread.sleep(8000);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 90000);
            Thread.sleep(5000);

            // AD completed event  verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 100000);

            // event verification of video is start playing
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 110000);
            Thread.sleep(10000);
            // video completed event verification
            ev.verifyEvent("playCompleted", " Video Completed Play ", 200000);

        } catch (Exception e) {
            logger.error("OoyalaAdMidroll throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"OoyalaAdMidroll");
            Assert.assertTrue(false, "This will fail!");
        }


    }

    @org.testng.annotations.Test
    public void OoyalaAdPostroll() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            BasicPlaybackSampleApp po = new BasicPlaybackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("BasicPlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnTextScrollTo(driver, "Ooyala Ad Post-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //Thread.sleep(2000);

            //waitting for start screen
            po.waitForTextView(driver,"00:00");
            Thread.sleep(1000);

            // move to full screen
            po.gotoFullScreen(driver);
            //Thread.sleep(2000);

            // event verification for full screen
            EventVerification ev = new EventVerification();
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in full screen", 30000);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(3000);

            //Play video in full screen
            po.playInFullScreen(driver);
            //Thread.sleep(1000);
            ev.verifyEvent("playStarted", " Video Started to Play ", 35000);
            Thread.sleep(4000);

            //Tapping on screen
            po.screenTap(driver);
            Thread.sleep(1000);

            //Pausing video in full screen
            po.pauseInFullScreen(driver);
            //Thread.sleep(1000);
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 45000);
            Thread.sleep(2000);

            //Seeking the video in full screen
            po.seekVideoFullscreen(driver);
            //Thread.sleep(1000);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 50000);
            Thread.sleep(3000);

            // going back again in normal screen
            po.gotoNormalScreen(driver);
            //Thread.sleep(2000);
            // event verification for normal screen
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in normal screen", 55000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 55000);
            Thread.sleep(3000);

            //Play video in normal screen
            po.playInNormalScreen(driver);
            //Thread.sleep(1000);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 60000);
            Thread.sleep(3000);

            //Tapping on screen
            po.screenTap(driver);
            Thread.sleep(1000);

            //Pausing video in normal screen
            po.pauseInNormalScreen(driver);
            //Thread.sleep(1000);
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 65000);
            Thread.sleep(1000);

            //Seeking video in normal screen
            po.seekVideo(driver);
            //Thread.sleep(1000);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 70000);
            Thread.sleep(3000);

            // playing video in normal screen
            po.playInNormalScreen(driver);
            //verifing event for play
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 75000);
            Thread.sleep(8000);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 90000);
            Thread.sleep(5000);

            // AD completed event  verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 100000);
            Thread.sleep(1000);

            // video completed event verification
            ev.verifyEvent("playCompleted", " Video Completed Play ", 120000);


        } catch (Exception e) {
            logger.error("OoyalaAdPostroll throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"OoyalaAdPostroll");
            Assert.assertTrue(false, "This will fail!");
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
            logger.debug("BasicPlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnTextScrollTo(driver, "VAST2 Ad Pre-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            Thread.sleep(2000);

            //waitting for start screen
            po.waitForTextView(driver,"00:00");
            Thread.sleep(1000);

            // move to full screen
            po.gotoFullScreen(driver);
            Thread.sleep(2000);

            // event verification for full screen
            EventVerification ev = new EventVerification();
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in full screen", 30000);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(3000);

            //Play video in full screen
            po.playInFullScreen(driver);
            //Thread.sleep(1000);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 35000);
            Thread.sleep(5000);
            // AD completed event  verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 40000);
            Thread.sleep(1000);

            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 40000);
            Thread.sleep(5000);

            //Tapping on screen
            po.screenTap(driver);
            Thread.sleep(1000);

            //Pausing video in full screen
            po.pauseInFullScreen(driver);
            //Thread.sleep(1000);
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 47000);
            Thread.sleep(1000);

            //Seeking the video in full screen
            po.seekVideoFullscreen(driver);
            //Thread.sleep(1000);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 50000);
            Thread.sleep(3000);

            // going back again in normal screen
            po.gotoNormalScreen(driver);
            // Thread.sleep(2000);

            // event verification for normal screen
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in normal screen", 53000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 53000);
            Thread.sleep(2000);

            //Play video in normal screen
            po.playInNormalScreen(driver);
            //Thread.sleep(1000);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 57000);
            Thread.sleep(3000);

            //Tapping on screen
            po.screenTap(driver);
            Thread.sleep(500);

            //Pausing video in normal screen
            po.pauseInNormalScreen(driver);
            //Thread.sleep(1000);
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 61000);
            Thread.sleep(1000);

            //Seeking video in normal screen
            po.seekVideo(driver);
            //Thread.sleep(1000);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 65000);
            Thread.sleep(3000);

            // playing video in normal screen
            po.playInNormalScreen(driver);
            //verifing event for play
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 70000);
            Thread.sleep(5000);

            // video completed event verificaiton
            ev.verifyEvent("playCompleted", " Video Completed Play ", 80000);

        }
        catch (Exception e) {
            logger.error("VASTAdPreRollTest throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VASTAdPreRollTest");
            Assert.assertTrue(false, "This will fail!");
        }

    }

    @org.testng.annotations.Test
    public void VASTAdMidroll() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            BasicPlaybackSampleApp po = new BasicPlaybackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("BasicPlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "VAST2 Ad Mid-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //waitting for start screen
            po.waitForTextView(driver,"00:00");
            Thread.sleep(1000);

            // move to full screen
            po.gotoFullScreen(driver);
            //Thread.sleep(2000);

            // event verification for full screen
            EventVerification ev = new EventVerification();
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in full screen", 30000);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(3000);

            //Play video in full screen
            po.playInFullScreen(driver);
            //Thread.sleep(1000);

            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
            Thread.sleep(4000);

            //Tapping on screen
            po.screenTap(driver);
            Thread.sleep(1000);

            //Pausing video in full screen
            po.pauseInFullScreen(driver);
            //Thread.sleep(1000);
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 40000);
            Thread.sleep(2000);

            //Seeking the video in full screen
            po.seekVideoFullscreen(driver);
            //Thread.sleep(1000);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 45000);
            Thread.sleep(3000);

            // going back again in normal screen
            po.gotoNormalScreen(driver);
            // Thread.sleep(2000);

            // event verification for normal screen
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in normal screen", 50000);
            Thread.sleep(1000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 52000);
            Thread.sleep(3000);

            //Play video in normal screen
            po.playInNormalScreen(driver);
            // Thread.sleep(1000);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 60000);
            Thread.sleep(3000);

            //Tapping on screen
            po.screenTap(driver);
            Thread.sleep(1000);

            //Pausing video in normal screen
            po.pauseInNormalScreen(driver);
            //Thread.sleep(1000);
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 65000);
            Thread.sleep(2000);

            //Seeking video in normal screen
            po.seekVideo(driver);
            //Thread.sleep(1000);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 70000);
            Thread.sleep(3000);

            // playing video in normal screen
            po.playInNormalScreen(driver);
            //verifing event for play
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 75000);
            Thread.sleep(8000);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 90000);
            Thread.sleep(5000);

            // AD completed event  verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 100000);

            // event verification of video is start playing
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 110000);
            Thread.sleep(10000);
            // video completed event verification
            ev.verifyEvent("playCompleted", " Video Completed Play ", 200000);

        } catch (Exception e) {
            logger.error("VASTAdMidroll throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VASTAdMidroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void VASTAdPostroll() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            BasicPlaybackSampleApp po = new BasicPlaybackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("BasicPlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "VAST2 Ad Post-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //waitting for start screen
            po.waitForTextView(driver,"00:00");
            Thread.sleep(1000);

            // move to full screen
            po.gotoFullScreen(driver);
            //Thread.sleep(2000);

            // event verification for full screen
            EventVerification ev = new EventVerification();
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in full screen", 30000);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(3000);

            //Play video in full screen
            po.playInFullScreen(driver);
            //Thread.sleep(1000);
            ev.verifyEvent("playStarted", " Video Started to Play ", 35000);
            Thread.sleep(4000);

            //Tapping on screen
            po.screenTap(driver);
            Thread.sleep(1000);

            //Pausing video in full screen
            po.pauseInFullScreen(driver);
            //Thread.sleep(1000);
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 45000);
            Thread.sleep(2000);

            //Seeking the video in full screen
            po.seekVideoFullscreen(driver);
            //Thread.sleep(1000);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 50000);
            Thread.sleep(3000);

            // going back again in normal screen
            po.gotoNormalScreen(driver);
            //Thread.sleep(2000);
            // event verification for normal screen
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in normal screen", 55000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 55000);
            Thread.sleep(3000);

            //Play video in normal screen
            po.playInNormalScreen(driver);
            //Thread.sleep(1000);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 60000);
            Thread.sleep(3000);

            //Tapping on screen
            po.screenTap(driver);
            Thread.sleep(1000);

            //Pausing video in normal screen
            po.pauseInNormalScreen(driver);
            //Thread.sleep(1000);
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 65000);
            Thread.sleep(1000);

            //Seeking video in normal screen
            po.seekVideo(driver);
            //Thread.sleep(1000);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 70000);
            Thread.sleep(3000);

            // playing video in normal screen
            po.playInNormalScreen(driver);
            //verifing event for play
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 75000);
            Thread.sleep(8000);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 90000);
            Thread.sleep(5000);

            // AD completed event  verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 100000);
            Thread.sleep(1000);

            // video completed event verification
            ev.verifyEvent("playCompleted", " Video Completed Play ", 120000);


        } catch (Exception e) {
            logger.error("VASTAdPostroll throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VASTAdPostroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void multiAdCombination() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            BasicPlaybackSampleApp po = new BasicPlaybackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("BasicPlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnTextScrollTo(driver, "Multi Ad combination");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");

            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //Thread.sleep(3000);

            //waitting for start screen
            po.waitForTextView(driver,"00:00");
            Thread.sleep(1000);

            // move to full screen
            po.gotoFullScreen(driver);
            //Thread.sleep(2000);

            // event verification for full screen
            EventVerification ev = new EventVerification();
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in full screen", 30000);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(3000);

            //Play video in full screen
            po.playInFullScreen(driver);
            //Thread.sleep(1000);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
            Thread.sleep(5000);
            // AD completed event  verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);
            Thread.sleep(1000);

            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 40000);
            Thread.sleep(5000);

            //Tapping on screen
            po.screenTap(driver);
            Thread.sleep(1000);

            //Pausing video in full screen
            po.pauseInFullScreen(driver);
            //Thread.sleep(1000);
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 47000);
            Thread.sleep(2000);

            //Seeking the video in full screen
            po.seekVideoFullscreen(driver);
            //Thread.sleep(1000);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 50000);
            Thread.sleep(3000);

            // going back again in normal screen
            po.gotoNormalScreen(driver);
            //Thread.sleep(2000);

            // event verification for normal screen
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in normal screen", 55000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 55000);
            Thread.sleep(3000);

            //Play video in normal screen
            po.playInNormalScreen(driver);
            //Thread.sleep(1000);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 60000);
            Thread.sleep(3000);

            //Tapping on screen
            po.screenTap(driver);
            Thread.sleep(1000);

            //Pausing video in normal screen
            po.pauseInNormalScreen(driver);
            //Thread.sleep(1000);
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 65000);
            Thread.sleep(1000);

            //Seeking video in normal screen
            po.seekVideo(driver);
            //Thread.sleep(1000);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 70000);
            Thread.sleep(3000);

            // playing video in normal screen
            po.playInNormalScreen(driver);
            //verifing event for play
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 75000);
            Thread.sleep(8000);

            // Ad playing strat event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 90000);
            Thread.sleep(5000);
            // Ad completed verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 100000);
            Thread.sleep(1000);

            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 110000);
            Thread.sleep(10000);

            // video completed event verification
            ev.verifyEvent("playCompleted", " Video Completed Play ", 220000);


        } catch (Exception e) {
            logger.error("multiAdCombination throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"multiAdCombination");
            Assert.assertTrue(false, "This will fail!");
        }

    }
}
