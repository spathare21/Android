package testpackage.tests.ooyalaapisampleapp;

import org.apache.log4j.Logger;
import testpackage.tests.exoPlayerSampleApp.BasicPlayBackBasicTest2;
import testpackage.utils.EventLogTest;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import testpackage.utils.*;
import java.io.IOException;
import java.util.Properties;
import testpackage.pageobjects.OoyalaApiSampleApp;

/**
 * Created by Vertis on 11/11/16.
 */
public class BasicTests extends EventLogTest {
    final static Logger logger = Logger.getLogger(BasicTests.class);

    Properties p;
    @BeforeClass
    public void beforeTest() throws Exception {
        // closing all recent app from background.
        CloserecentApps.closeApps();
        // Get Property Values
        LoadPropertyValues prop = new LoadPropertyValues();
        p = prop.loadProperty("ooyalaApiSampleApp.properties");
        //setup and initialize android driver
        SetUpAndroidDriver setUpdriver = new SetUpAndroidDriver();
        driver = setUpdriver.setUpandReturnAndroidDriver(p.getProperty("udid"), p.getProperty("appDir"), p.getProperty("appValue"), p.getProperty("platformName"), p.getProperty("platformVersion"), p.getProperty("appPackageName"), p.getProperty("appActivityName"));
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {

        driver.manage().logs().get("logcat");
        //push the log file to device
        PushLogFileToDevice logpush=new PushLogFileToDevice();
        logpush.pushLogFile();
        if(driver.currentActivity()!= p.get("appActivityName").toString()) {
            driver.startActivity(p.get("appPackageName").toString(),p.get("appActivityName").toString());
        }
        // Display the screenmode
        logger.debug(" Screen Mode "+ p.getProperty("ScreenMode"));
    }

    @AfterClass
    public void afterTest() throws InterruptedException, IOException {
        //close the app
        driver.closeApp();
        //quit the driver
        driver.quit();
    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws Exception {
        // removing the events log file after test is completed.
        RemoveEventsLogFile.removeEventsFileLog();
        Thread.sleep(10000);
    }

    @Test
    public void ooyalaEverywhere() throws Exception{

        try {
            // Creating an Object of OoyalaApiSampleApp class
            OoyalaApiSampleApp po = new OoyalaApiSampleApp();
            // wait till home screen of OoyalaApiBackApp is opened
            po.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, p.get("appActivityName").toString());
            // Wrire to console activity name of home screen app
            logger.debug("OoyalaAPISample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //Pause the running of test for a brief time .
            Thread.sleep(3000);
            //click on display main list
            po.clickBasedOnText(driver, "ContentTree for Channel");
            Thread.sleep(2000);
            // Assert if current activity is ChannelContentTreePlayerActivity
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.ChannelContentTreePlayerActivity");
            // wait for app to load
            po.waitForTextView(driver,"Ooyala Everywhere");
            // Select one of the video
            po.clickBasedOnText(driver, "Ooyala Everywhere");
            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            // wait for the player/asset to load
            po.waitForTextView(driver,"00:00");
            //play asset in normal screen
            po.playInNormalScreen(driver);
            EventVerification ev = new EventVerification();
            //verify play event
            ev.verifyEvent("playStarted - state: READY", " Video Started to Play ", 50000);
            // Click on the web area so that player screen shows up
            po.screenTap(driver);
            //Pausing Video in Normal screen.
            po.pauseInNormalScreen(driver);
            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 50000);
            //seek the asset
            po.seekVideo(driver);
            //verify seek event
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 50000);
            //resume playback in normal screen
            po.resumeInNormalScreen(driver);
            //verify playing event
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 50000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 300000);
        }
        catch(Exception e){
            logger.error(" Ooyala Everywhere throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"ooyalaEverywhere");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void ooyalaSocial() throws Exception{

        try {
            // Creating an Object of OoyalaApiSampleApp class
            OoyalaApiSampleApp po = new OoyalaApiSampleApp();
            // wait till home screen of OoyalaApiBackApp is opened
            po.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OoyalaAPIListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("OoyalaAPISample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //Pause the running of test for a brief time .
            Thread.sleep(3000);
            //click on display main list
            po.clickBasedOnText(driver, "ContentTree for Channel");
            Thread.sleep(2000);
            // Assert if current activity is ChannelContentTreePlayerActivity
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.ChannelContentTreePlayerActivity");
            //wait for app to load
            po.waitForTextView(driver,"Ooyala Social");
            // Select one of the video
            po.clickBasedOnText(driver, "Ooyala Social");
            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //wait for asset to load or start screen to appear
            po.waitForTextView(driver,"00:00");
            //play the asset in normal screen
            po.playInNormalScreen(driver);
            EventVerification ev = new EventVerification();
            //verify play started event
            ev.verifyEvent("playStarted - state: READY", " Video Started to Play ", 50000);
            // Click on the web area so that player screen shows up
            po.screenTap(driver);
            //Pausing Video in Normal screen.
            po.pauseInNormalScreen(driver);
            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 50000);
            //seek the video in normal screen
            po.seekVideo(driver);
            // verify seek event
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 50000);
            // resume the playback in normal screen
            po.resumeInNormalScreen(driver);
            //verify playing event
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 50000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 300000);
        }
        catch(Exception e){
            logger.error(" Ooyala Social throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"ooyalaSocial");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void rethinkVideo() throws Exception{

        try {
            // Creating an Object of OoyalaApiSampleApp class
            OoyalaApiSampleApp po = new OoyalaApiSampleApp();
            // wait till home screen of OoyalaApiBackApp is opened
            po.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OoyalaAPIListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("OoyalaAPISample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //Pause the running of test for a brief time .
            Thread.sleep(3000);
            //click on display main list
            po.clickBasedOnText(driver, "ContentTree for Channel");
            Thread.sleep(2000);
            // Assert if current activity is ChannelContentTreePlayerActivity
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.ChannelContentTreePlayerActivity");
            //wait for the asset list to load
            po.waitForTextView(driver,"Rethink Video");
            // Select one of the video
            po.clickBasedOnText(driver, "Rethink Video");
            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //wait for the asset start screen to load
            po.waitForTextView(driver,"00:00");
            //play the asset in normal screen
            po.playInNormalScreen(driver);
            EventVerification ev = new EventVerification();
            //verify play started event
            ev.verifyEvent("playStarted - state: READY", " Video Started to Play ", 50000);
            // Click on the web area so that player screen shows up
            po.screenTap(driver);
            //Pausing Video in Normal screen.
            po.pauseInNormalScreen(driver);
            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 50000);
            //seek the video
            po.seekVideo(driver);
            //verify the seek event
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 50000);
            //resume the playback in normal screen
            po.resumeInNormalScreen(driver);
            //verify playing event
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 50000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 300000);
        }
        catch(Exception e){
            logger.error(" Rethink Video throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"rethinkVideo");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void telegraphMediaGroup() throws Exception{

        try {
            // Creating an Object of OoyalaApiSampleApp class
            OoyalaApiSampleApp po = new OoyalaApiSampleApp();
            // wait till home screen of OoyalaApiBackApp is opened
            po.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OoyalaAPIListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("OoyalaAPISample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //Pause the running of test for a brief time .
            Thread.sleep(3000);
            //click on display main list
            po.clickBasedOnText(driver, "ContentTree for Channel");
            Thread.sleep(2000);
            // Assert if current activity is ChannelContentTreePlayerActivity
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.ChannelContentTreePlayerActivity");
            //wait for the assets list to display
            po.waitForTextView(driver,"Telegraph Media Group");
            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Telegraph Media Group");
            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //wait for the asset to load
            po.waitForTextView(driver,"00:00");
            //play the asset in normal screen
            po.playInNormalScreen(driver);
            EventVerification ev = new EventVerification();
            //verify play started event
            ev.verifyEvent("playStarted - state: READY", " Video Started to Play ", 50000);
            // Click on the web area so that player screen shows up
            po.screenTap(driver);
            //Pausing Video in Normal screen.
            po.pauseInNormalScreen(driver);
            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 50000);
            //seek the playback
            po.seekVideo(driver);
            //verify the seek completed event
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 50000);
            //resume the playback in the normal screen
            po.resumeInNormalScreen(driver);
            //verify playing event
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 50000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 300000);
        }
        catch(Exception e){
            logger.error("Telegraph Media Group throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"telegraphMediaGroup");
            Assert.assertTrue(false, "This will fail!");
        }
    }

}
