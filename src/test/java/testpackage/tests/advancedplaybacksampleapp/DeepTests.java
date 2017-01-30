package testpackage.tests.advancedplaybacksampleapp;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import testpackage.pageobjects.AdvancedPlaybackSampleApp;
import testpackage.utils.*;
import java.io.IOException;
import java.util.Properties;

public class DeepTests extends EventLogTest {
    final static Logger logger = Logger.getLogger(DeepTests.class);


    @BeforeClass
    public void beforeTest() throws Exception {
        // closing all recent app from background.
        CloserecentApps.closeApps();
       logger.info("BeforeTest \n");

        logger.debug(System.getProperty("user.dir"));
        // Get Property Values
        LoadPropertyValues prop = new LoadPropertyValues();
        Properties p = prop.loadProperty("advancedplaybacksampleapp.properties");

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
    //public void beforeTest() throws Exception{
    public void beforeMethod() throws Exception {
        logger.info("beforeMethod \n");
        driver.manage().logs().get("logcat");
        PushLogFileToDevice logpush = new PushLogFileToDevice();
        logpush.pushLogFile();
        if (driver.currentActivity() != "com.ooyala.sample.lists.AdvancedPlaybackListActivity") {
            driver.startActivity("com.ooyala.sample.AdvancedPlaybackSampleApp", "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
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
        RemoveEventsLogFile.removeEventsFileLog();
        Thread.sleep(10000);

    }

   @org.testng.annotations.Test
    public void playWithIntitialTime() throws Exception {

        try
        {
            // Creating an Object of BasicPlaybackSampleApp class
            AdvancedPlaybackSampleApp po = new AdvancedPlaybackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            //po.clickBasedOnText(driver, "Play With InitialTime");
            po.clickBasedOnIndex(driver, "0");
            Thread.sleep(2000);
            logger.info(">>>Clicked on Play With InitialTime Asset>>>");

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            logger.info("after wait for presence");

            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PlayWithInitialTimePlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
            Thread.sleep(1000);

            //Tapping on screen if pause botton not present
            po.screenTap(driver);

            //Pause the Video
            po.pauseVideo(driver);
            Thread.sleep(2000);

            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);
            // Pause the running of the test for a brief amount of time
            Thread.sleep(3000);

            // After pausing clicking on recent app button and getting sample app back
            po.getBackFromRecentApp(driver);
            Thread.sleep(3000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            // Clicking on Power button to lock screen
            po.powerKeyClick(driver);
            Thread.sleep(3000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            //Switching Video to full screen
            po.clickFullScreen(driver);
            Thread.sleep(3000);

            // event verification for full screen
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in full screen", 30000);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            // After pausing clicking on recent app button and getting sample app back
            po.getBackFromRecentApp(driver);
            Thread.sleep(3000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            // Clicking on Power button to lock screen
            po.powerKeyClick(driver);
            Thread.sleep(3000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(1000);

            //seeking video backwards
            //po.backSeekInFullScreen(driver);
            //Thread.sleep(2000);

            //Playing Video in Full screen
            //po.playVideoFullScreen(driver);
            //Thread.sleep(2000);

            //Pausing Video in Full screen
            //po.pauseVideoFullScreen(driver);
            //Thread.sleep(2000);

            //Switching video to normal screen
            po.clickNormalScreen(driver);
            Thread.sleep(3000);

            // event verification for normal screen
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in normal screen", 30000);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(3000);

            // Resuming the playback
            po.playVideo(driver);
            Thread.sleep(3000);

            //Verifing Video Resume
            ev.verifyEvent("stateChanged - state: PLAYING", "Video is resumed", 30000);
            Thread.sleep(5000);

            // After pausing clicking on recent app button and getting sample app back
            po.getBackFromRecentApp(driver);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(1000);

            // Clicking on Power button to lock screen
            po.powerKeyClick(driver);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 40000);
            Thread.sleep(1000);

            //ev.verifyEvent("seekCompleted - state: PLAYING", "video starting from predefined intial time",60000);

            // Verifying Play complete event
            ev.verifyEvent("playCompleted", "video play completed",90000);

        }
        catch (Exception e) {
            logger.error("playWithIntitialTime Exception " + e);
            logger.trace(e);
            ScreenshotDevice.screenshot(driver,"playWithIntitialTime");
            Assert.assertTrue(false, "This will fail!");
        }


    }

  @org.testng.annotations.Test
    public void multipleVideoPlayback() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            AdvancedPlaybackSampleApp po = new AdvancedPlaybackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            //po.clickBasedOnText(driver, "Multiple Video Playback");
            po.clickBasedOnIndex(driver, "1");
            Thread.sleep(2000);
            logger.info(">>>>Clicked on Multiple Video Playback Asset>>>>");

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            logger.info("after wait for presence");

            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.MultipleVideosPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //1st video Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
            Thread.sleep(1000);

            //Tapping on screen if pause botton not present
            po.screenTap(driver);

            //Pause the Video
            po.pauseVideo(driver);
            Thread.sleep(2000);

            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);
            // Pause the running of the test for a brief amount of time
            Thread.sleep(3000);

            // After pausing clicking on recent app button and getting sample app back
            po.getBackFromRecentApp(driver);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(1000);

            // Clicking on Power button to lock screen
            po.powerKeyClick(driver);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(1000);

            //Switching Video to full screen
            po.clickFullScreen(driver);
            Thread.sleep(2000);

            // event verification for full screen
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in full screen", 30000);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(1000);

            // After pausing clicking on recent app button and getting sample app back
            po.getBackFromRecentApp(driver);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(1000);

            // Clicking on Power button to lock screen
            po.powerKeyClick(driver);
            Thread.sleep(1000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(1000);

            //seeking video backwards
           // po.backSeekInFullScreen(driver);
            //Thread.sleep(2000);

            //Playing Video in Full screen
            //po.playVideoFullScreen(driver);
            //Thread.sleep(2000);

            //Verify Playing video
            //ev.verifyEvent("PLAYING", "Video is played in full screen", 30000);
            //Thread.sleep(1000);

            //Pausing Video in Full screen
            //po.pauseVideoFullScreen(driver);
            //Thread.sleep(2000);

            // Pause state verification
            //ev.verifyEvent("stateChanged - state: PAUSED", " Second Video Was Paused ", 30000);
            // Pause the running of the test for a brief amount of time
            //Thread.sleep(2000);

            //Switching video to normal screen
            po.clickNormalScreen(driver);
            Thread.sleep(2000);

            // event verification for normal screen
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in normal screen", 30000);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(1000);

            // Resuming the playback
            po.playVideo(driver);
            Thread.sleep(1000);

            //Verifing Video Resume
            ev.verifyEvent("stateChanged - state: PLAYING", "Video is resumed", 30000);
            Thread.sleep(2000);

            // After pausing clicking on recent app button and getting sample app back
            po.getBackFromRecentApp(driver);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(1000);

            // Clicking on Power button to lock screen
            po.powerKeyClick(driver);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(2000);

            //2nd video start playing in queue
            ev.verifyEvent(" playStarted - state: READY", "2nd video start playing in queue", 60000);

            //Tapping on screen if pause botton not present
            po.screenTap(driver);

            //Pause the Video
            po.pauseVideo(driver);
            Thread.sleep(2000);

            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Second Video Was Paused ", 30000);
            // Pause the running of the test for a brief amount of time
            Thread.sleep(5000);

            // After pausing clicking on recent app button and getting sample app back
            po.getBackFromRecentApp(driver);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready to play 2nd video", 30000);
            Thread.sleep(1000);

            // Clicking on Power button to lock screen
            po.powerKeyClick(driver);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready to play 2nd Video", 30000);
            Thread.sleep(1000);

            //Switching Video to full screen
            po.clickFullScreen(driver);
            Thread.sleep(2000);

            // event verification for full screen
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in full screen for 2nd video", 30000);
            Thread.sleep(1000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready to play 2nd Video", 30000);
            Thread.sleep(1000);

            // After pausing clicking on recent app button and getting sample app back
            po.getBackFromRecentApp(driver);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready to play 2nd Video", 30000);
            Thread.sleep(1000);

            // Clicking on Power button to lock screen
            po.powerKeyClick(driver);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready to play 2nd Video", 30000);
            Thread.sleep(1000);

            //Playing Video in Full screen
            //po.playVideoFullScreen(driver);
            //Thread.sleep(2000);

            //Verify Playing video
            //ev.verifyEvent("PLAYING", "Video is played in full screen", 30000);
            //Thread.sleep(1000);

            //Pausing Video in Full screen
            //po.pauseVideoFullScreen(driver);
            //Thread.sleep(2000);

            // Pause state verification
            //ev.verifyEvent("stateChanged - state: PAUSED", " Second Video Was Paused ", 30000);
            // Pause the running of the test for a brief amount of time
            //Thread.sleep(2000);

            //Switching video to normal screen
            po.clickNormalScreen(driver);
            Thread.sleep(1000);

            // event verification for normal screen
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in normal screen for 2nd video", 30000);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready to play 2nd Video", 30000);
            Thread.sleep(2000);

            // Resuming the playback
            po.playVideo(driver);
            Thread.sleep(2000);

            //Verifing Video Resume
            ev.verifyEvent("stateChanged - state: PLAYING", "2nd Video is resumed", 30000);
            Thread.sleep(3000);

            // After pausing clicking on recent app button and getting sample app back
            po.getBackFromRecentApp(driver);
            Thread.sleep(3000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready to play 2nd Video", 30000);

            // Clicking on Power button to lock screen
            po.powerKeyClick(driver);
            Thread.sleep(3000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready to play 2nd Video", 30000);

            // video completed event verification.
            ev.verifyEvent("playCompleted", "video play completed", 90000);
            Thread.sleep(50000);

        } catch (Exception e) {
            logger.error("multipleVideoPlayback throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"multipleVideoPlayback");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void insertAdAtRunTime() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            AdvancedPlaybackSampleApp po = new AdvancedPlaybackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
           // po.clickBasedOnText(driver, "Insert Ad at Runtime");
            po.clickBasedOnIndex(driver, "2");
            Thread.sleep(2000);
            logger.info(">>>>Clicked on Insert Ad at Runtime Asset>>>>");

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            logger.info("after wait for presence");

            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.InsertAdPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
            Thread.sleep(2000);

            //Tapping on screen if pause botton not present
            po.screenTap(driver);

            //Pausing the video
            po.pauseSmallPlayer(driver);
            Thread.sleep(2000);

            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Video Was Paused ", 30000);
            // Pause the running of the test for a brief amount of time
            Thread.sleep(5000);

            // After pausing clicking on recent app button and getting sample app back
            po.getBackFromRecentApp(driver);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(3000);

            // Clicking on Power button to lock screen
            po.powerKeyClick(driver);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(2000);

            //Switching Video to full screen
            po.clickFullScreen(driver);
            Thread.sleep(2000);

            // event verification for full screen
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in full screen", 30000);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(3000);

            // After pausing clicking on recent app button and getting sample app back
            po.getBackFromRecentApp(driver);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(2000);

            // Clicking on Power button to lock screen
            po.powerKeyClick(driver);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(2000);

            //Playing Video in Full screen
            //po.playVideoFullScreen(driver);
            //Thread.sleep(2000);

            //Verify Playing video
            //ev.verifyEvent("PLAYING", "Video is played in full screen", 30000);
            //Thread.sleep(1000);

            //Pausing Video in Full screen
            //po.pauseVideoFullScreen(driver);
            //Thread.sleep(2000);

            // Pause state verification
            //ev.verifyEvent("stateChanged - state: PAUSED", " Video Was Paused ", 30000);
            // Pause the running of the test for a brief amount of time
            //Thread.sleep(2000);

            //Switching video to normal screen
            po.clickNormalScreen(driver);
            Thread.sleep(2000);

            // event verification for normal screen
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in normal screen", 30000);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(2000);

            // Resuming the playback
            po.playVideo(driver);
            Thread.sleep(2000);

            //Verifing Video Resume
            ev.verifyEvent("stateChanged - state: PLAYING", "Video is resumed", 30000);
            Thread.sleep(3000);

            // After pausing clicking on recent app button and getting sample app back
            po.getBackFromRecentApp(driver);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(4000);

            // Clicking on Power button to lock screen
            po.powerKeyClick(driver);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(3000);

            //Clicking on Vast Ad button to insert vast Ad
            po.clickOnVastAd(driver);
            Thread.sleep(2000);

            //Verifying Ad started event
            ev.verifyEvent("adStarted", "Vast Ad is started", 30000);
            Thread.sleep(1000);

            //Verifying ad completed event
            ev.verifyEvent("adCompleted", "Vast Ad is Completed", 30000);
            Thread.sleep(2000);

            ev.verifyEvent("playCompleted", "video play completed", 90000);

        } catch (Exception e) {
            logger.error(" insertAdAtRunTime throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"insertAdAtRunTime");
            Assert.assertTrue(false, "This will fail!");
        }

    }

   @org.testng.annotations.Test
    public void changeVideoProgramatically() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            AdvancedPlaybackSampleApp po = new AdvancedPlaybackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            //po.clickBasedOnText(driver, "Change Video Programatically");
            po.clickBasedOnIndex(driver, "3");
            Thread.sleep(2000);
            logger.info(">>>>Clicked on Change Video Programatically Asset>>>>");

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            logger.info("after wait for presence");

            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.ChangeVideoPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
            Thread.sleep(1000);

            //Tapping on screen if pause botton not present
            po.screenTap(driver);

            //Pausing the video
            po.pauseSmallPlayer(driver);
            Thread.sleep(2000);

            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Video Was Paused ", 30000);
            // Pause the running of the test for a brief amount of time
            Thread.sleep(5000);

            // After pausing clicking on recent app button and getting sample app back
            po.getBackFromRecentApp(driver);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(3000);

            // Clicking on Power button to lock screen
            po.powerKeyClick(driver);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(2000);

            //Switching Video to full screen
            po.clickFullScreen(driver);
            Thread.sleep(2000);

            // event verification for full screen
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in full screen", 30000);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(3000);

            // After pausing clicking on recent app button and getting sample app back
            po.getBackFromRecentApp(driver);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(3000);

            // Clicking on Power button to lock screen
            po.powerKeyClick(driver);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(2000);

            //Playing Video in Full screen
           // po.playVideoFullScreen(driver);
           // Thread.sleep(2000);

            //Verify Playing video
            //ev.verifyEvent("PLAYING", "Video is played in full screen", 30000);
            //Thread.sleep(1000);

            //Pausing Video in Full screen
            //po.pauseVideoFullScreen(driver);
            //Thread.sleep(2000);

            // Pause state verification
            //ev.verifyEvent("stateChanged - state: PAUSED", " Second Video Was Paused ", 30000);
            // Pause the running of the test for a brief amount of time
            //Thread.sleep(2000);

            //Switching video to normal screen
            po.clickNormalScreen(driver);
            Thread.sleep(3000);

            // event verification for normal screen
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in normal screen", 30000);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(2000);

            // Resuming the playback
            po.playVideo(driver);
            Thread.sleep(3000);

            //Verifing Video Resume
            ev.verifyEvent("stateChanged - state: PLAYING", "Video is resumed", 30000);
            Thread.sleep(2000);

            //Clicking on Play Video 1
            po.clickOnP1(driver);
            Thread.sleep(2000);

            //Verifing Video 1 Play
            ev.verifyEvent("stateChanged - state: PLAYING", "Played video 1", 30000);
            Thread.sleep(2000);

            //Clicking on Video 2
            po.clickOnP2(driver);
            Thread.sleep(2000);

            //Verifing Video 2 Play
            ev.verifyEvent("stateChanged - state: PLAYING", "Played video 2", 30000);
            Thread.sleep(3000);

            ev.verifyEvent("playCompleted", "video play completed", 90000);

        } catch (Exception e) {
            logger.error("changeVideoProgramatically throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"changeVideoProgramatically");
            Assert.assertTrue(false, "This will fail!");
        }
    }

/*    @org.testng.annotations.Test
    public void customPluginSample() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            AdvancedPlaybackSampleApp po = new AdvancedPlaybackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
           // po.clickBasedOnText(driver, "Custom Plugin Sample");
            po.clickBasedOnIndex(driver, "4");
            Thread.sleep(2000);
            logger.info(">>>>Clicked on Custom Plugin Asset>>>>");

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            logger.info("after wait for presence");

            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PluginPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Verifying Ad started event
            ev.verifyEvent("stateChanged - state: PLAYING", "Ad is started", 30000);
            Thread.sleep(1000);

            //Verifying ad completed event
            ev.verifyEvent("adCompleted", "Ad is Completed", 30000);
            Thread.sleep(1000);

            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
            Thread.sleep(2000);

            //Tapping on screen if pause botton not present
            po.screenTap(driver);

            //Pause the Video
            po.pauseVideo(driver);
            Thread.sleep(2000);

            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);
            // Pause the running of the test for a brief amount of time
            Thread.sleep(3000);

            // After pausing clicking on recent app button and getting sample app back
            po.getBackFromRecentApp(driver);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(3000);

            // Clicking on Power button to lock screen
            po.powerKeyClick(driver);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(2000);

            //Switching Video to full screen
            po.clickFullScreen(driver);
            Thread.sleep(3000);

            // event verification for full screen
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in full screen", 30000);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(2000);

            // After pausing clicking on recent app button and getting sample app back
            po.getBackFromRecentApp(driver);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(3000);

            // Clicking on Power button to lock screen
            po.powerKeyClick(driver);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(2000);

            //Playing Video in Full screen
           // po.playVideoFullScreen(driver);
           // Thread.sleep(2000);

            //Verify Playing video
            //ev.verifyEvent("PLAYING", "Video is played in full screen", 30000);
            //Thread.sleep(1000);

            //Pausing Video in Full screen
            // po.pauseVideoFullScreen(driver);
            // Thread.sleep(2000);

            // Pause state verification
            //ev.verifyEvent("stateChanged - state: PAUSED", " Second Video Was Paused ", 30000);
            // Pause the running of the test for a brief amount of time
            //Thread.sleep(2000);

            //Switching video to normal screen
            po.clickNormalScreen(driver);
            Thread.sleep(3000);

            // event verification for normal screen
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in normal screen", 30000);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(2000);

            // Resuming the playback
            po.playVideo(driver);
            Thread.sleep(2000);

            //Verifing Video Resume
            ev.verifyEvent("stateChanged - state: PLAYING", "Video is resumed", 30000);
            Thread.sleep(2000);

            // After pausing clicking on recent app button and getting sample app back
            po.getBackFromRecentApp(driver);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(1000);

            // Clicking on Power button to lock screen
            po.powerKeyClick(driver);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(1000);

            ev.verifyEvent("playCompleted", "video play completed", 200000);

        } catch (Exception e) {
            logger.error(" customPluginSample throws Exception " + e);
             e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"customPluginSample");
            Assert.assertTrue(false, "This will fail!");
        }
    }*/

    @org.testng.annotations.Test
    public void customControls() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            AdvancedPlaybackSampleApp po = new AdvancedPlaybackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
           // po.clickBasedOnText(driver, "Custom Controls");
            po.clickBasedOnIndex(driver, "5");
            Thread.sleep(2000);
            logger.info(">>>>Clicked on Custom Controls Asset>>>>");

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            logger.info("after wait for presence");

            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.CustomControlsPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //Pausing Video
            po.customControlPauseButton(driver);
            Thread.sleep(2000);

            //Verifing Pausing event
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);
            Thread.sleep(2000);

            po.customControlPlayButton(driver);
            Thread.sleep(2000);

            //Verifying Play event
            ev.verifyEvent("PLAYING","Video is resumed", 30000);
            Thread.sleep(2000);

            //Going to resent App
            po.getBackFromRecentApp(driver);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(1000);

            // Clicking on Power button to lock screen
            po.powerKeyClick(driver);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(1000);

            ev.verifyEvent("playCompleted", "video play completed", 90000);

        } catch (Exception e) {
            logger.error("customControls throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"customControls");
            Assert.assertTrue(false, "This will fail!");
        }

    }

   @org.testng.annotations.Test
    public void customOverlay() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            AdvancedPlaybackSampleApp po = new AdvancedPlaybackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            //po.clickBasedOnText(driver, "Custom Overlay");
            po.clickBasedOnIndex(driver, "6");
            Thread.sleep(2000);
            logger.info(">>>>Clicked on Custom Overlay Asset>>>>");

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            logger.info("after wait for presence");

            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.CustomOverlayPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
            Thread.sleep(1000);

            po.overlay(driver);
            Thread.sleep(1000);

            ev.verifyEvent("playCompleted", "video play completed", 90000);

        } catch (Exception e) {
            logger.error("customOverlay throws Exception \n" + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"customOverlay");
            Assert.assertTrue(false, "This will fail!");
        }
    }

/*   @org.testng.annotations.Test
    public void unbundled() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            AdvancedPlaybackSampleApp po = new AdvancedPlaybackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Unbundled");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            logger.info("after wait for presence");

            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.UnbundledPlayerActivity");
            // Print to console output current player activity
            logger.info("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
            //Thread.sleep(1500);

            //Pause the Video
            po.pauseVideo(driver);
            Thread.sleep(2000);

            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);
            // Pause the running of the test for a brief amount of time
            Thread.sleep(3000);

            // After pausing clicking on recent app button and getting sample app back
            po.getBackFromRecentApp(driver);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            // Clicking on Power button to lock screen
            po.powerKeyClick(driver);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            //Switching Video to full screen
            po.clickFullScreen(driver);
            Thread.sleep(2000);

            // event verification for full screen
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in full screen", 30000);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            // After pausing clicking on recent app button and getting sample app back
            po.getBackFromRecentApp(driver);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            // Clicking on Power button to lock screen
            po.powerKeyClick(driver);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(1000);

            //Playing Video in Full screen
            po.playVideoFullScreen(driver);
            Thread.sleep(2000);

            //Verifing Video play in full screen
            ev.verifyEvent("stateChanged - state: PLAYING", "Video is resumed in full screen", 30000);
            Thread.sleep(1000);

            //Pausing Video in Full screen
            po.pauseVideoFullScreen(driver);
            Thread.sleep(2000);

            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);
            // Pause the running of the test for a brief amount of time
            Thread.sleep(3000);

            //Switching video to normal screen
            po.clickNormalScreen(driver);
            Thread.sleep(2000);

            // event verification for normal screen
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in normal screen", 30000);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            // Resuming the playback
            po.playVideo(driver);
            Thread.sleep(1000);

            //Verifing Video Resume
            ev.verifyEvent("stateChanged - state: PLAYING", "Video is resumed", 30000);
            Thread.sleep(1000);

            // After pausing clicking on recent app button and getting sample app back
            po.getBackFromRecentApp(driver);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            // Clicking on Power button to lock screen
            po.powerKeyClick(driver);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            //ev.verifyEvent("seekCompleted - state: PLAYING", "video starting from predefined intial time",60000);

            // Verifying Play complete event
            ev.verifyEvent("playCompleted", "video play completed",90000);

        } catch (Exception e) {
            logger.error("unbundled throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"unbundled");
            Assert.assertTrue(false, "This will fail!");
        }

    }*/

}
