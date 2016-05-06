package testpackage.tests.advancePlayBackSampleApp;

import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import testpackage.pageobjects.advancePlayBackSampleApp;
import testpackage.utils.*;

import java.io.IOException;
import java.util.Properties;
/**
 * Created by Sameer on 5/4/2016.
 */
public class DeepTests {
    private static AndroidDriver driver;

    @BeforeClass
    public void beforeTest() throws Exception {
        // closing all recent app from background.
        CloserecentApps.closeApps();
        System.out.println("BeforeTest \n");

        System.out.println(System.getProperty("user.dir"));
        // Get Property Values
        LoadPropertyValues prop = new LoadPropertyValues();
        Properties p = prop.loadProperty("advanceplaybacksampleapp.properties");

        System.out.println("Device id from properties file " + p.getProperty("deviceName"));
        System.out.println("PortraitMode from properties file " + p.getProperty("PortraitMode"));
        System.out.println("Path where APK is stored" + p.getProperty("appDir"));
        System.out.println("APK name is " + p.getProperty("app"));
        System.out.println("Platform under Test is " + p.getProperty("platformName"));
        System.out.println("Mobile OS Version is " + p.getProperty("OSVERSION"));
        System.out.println("Package Name of the App is " + p.getProperty("appPackage"));
        System.out.println("Activity Name of the App is " + p.getProperty("appActivity"));

        SetUpAndroidDriver setUpdriver = new SetUpAndroidDriver();
        driver = setUpdriver.setUpandReturnAndroidDriver(p.getProperty("udid"), p.getProperty("appDir"), p.getProperty("appValue"), p.getProperty("platformName"), p.getProperty("platformVersion"), p.getProperty("appPackage"), p.getProperty("appActivity"));
        Thread.sleep(2000);
    }

    @BeforeMethod
    //public void beforeTest() throws Exception{
    public void beforeMethod() throws Exception {
        System.out.println("beforeMethod \n");
        //removeEventsLogFile.removeEventsFileLog(); create events file
        PushLogFileToDevice logpush = new PushLogFileToDevice();
        logpush.pushLogFile();
        if (driver.currentActivity() != "com.ooyala.sample.lists.AdvancedPlaybackListActivity") {
            driver.startActivity("com.ooyala.sample.AdvancedPlaybackSampleApp", "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
        }

        // Get Property Values
        LoadPropertyValues prop1 = new LoadPropertyValues();
        Properties p1 = prop1.loadProperty();

        System.out.println(" Screen Mode " + p1.getProperty("ScreenMode"));

        //if(p1.getProperty("ScreenMode") != "P"){
        //    System.out.println("Inside landscape Mode ");
        //    driver.rotate(ScreenOrientation.LANDSCAPE);
        //}

        //driver.rotate(ScreenOrientation.LANDSCAPE);
        //driver.rotate(ScreenOrientation.LANDSCAPE);

    }

    @AfterClass
    public void afterTest() throws InterruptedException, IOException {
        System.out.println("AfterTest \n");
        driver.closeApp();
        driver.quit();
        LoadPropertyValues prop1 = new LoadPropertyValues();
        Properties p1 = prop1.loadProperty();
        String prop = p1.getProperty("appPackage");
        Appuninstall.uninstall(prop);


    }

    @AfterMethod
    //public void afterTest() throws InterruptedException, IOException {
    public void afterMethod() throws InterruptedException, IOException {
        // Waiting for all the events from sdk to come in .
        System.out.println("AfterMethod \n");
        //ScreenshotDevice.screenshot(driver);
        RemoveEventsLogFile.removeEventsFileLog();
        Thread.sleep(10000);

    }

   /* @org.testng.annotations.Test
    public void playWithIntitialTime() throws Exception {

        try
        {
            // Creating an Object of BasicPlaybackSampleApp class
            advancePlayBackSampleApp po = new advancePlayBackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Play With InitialTime");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            System.out.println("after wait for presence");

            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PlayWithInitialTimePlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
            Thread.sleep(1500);

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

            //seeking video backwards
            po.backSeekInFullScreen(driver);
            Thread.sleep(2000);

            //Playing Video in Full screen
            po.playVideoFullScreen(driver);
            Thread.sleep(2000);

            //Pausing Video in Full screen
            po.pauseVideoFullScreen(driver);
            Thread.sleep(2000);

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
            ev.verifyEvent("playCompleted - state: LOADING", "video play completed",90000);

        }
        catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }


    }

  @org.testng.annotations.Test
    public void multipleVideoPlayback() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            advancePlayBackSampleApp po = new advancePlayBackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Multiple Video Playback");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            System.out.println("after wait for presence");

            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.MultipleVideosPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //1st video Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
            Thread.sleep(1000);

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

            //seeking video backwards
            po.backSeekInFullScreen(driver);
            Thread.sleep(2000);

            //Playing Video in Full screen
            po.playVideoFullScreen(driver);
            Thread.sleep(2000);

            //Verify Playing video
            ev.verifyEvent("PLAYING", "Video is played in full screen", 30000);
            Thread.sleep(1000);

            //Pausing Video in Full screen
            po.pauseVideoFullScreen(driver);
            Thread.sleep(2000);

            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Second Video Was Paused ", 30000);
            // Pause the running of the test for a brief amount of time
            Thread.sleep(2000);

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
            Thread.sleep(2000);

            //2nd video start playing in queue
            ev.verifyEvent(" playStarted - state: READY", "2nd video start playing in queue", 60000);

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

            // Clicking on Power button to lock screen
            po.powerKeyClick(driver);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready to play 2nd Video", 30000);

            //Switching Video to full screen
            po.clickFullScreen(driver);
            Thread.sleep(2000);

            // event verification for full screen
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in full screen for 2nd video", 30000);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready to play 2nd Video", 30000);

            // After pausing clicking on recent app button and getting sample app back
            po.getBackFromRecentApp(driver);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready to play 2nd Video", 30000);

            // Clicking on Power button to lock screen
            po.powerKeyClick(driver);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready to play 2nd Video", 30000);

            //Playing Video in Full screen
            po.playVideoFullScreen(driver);
            Thread.sleep(2000);

            //Verify Playing video
            ev.verifyEvent("PLAYING", "Video is played in full screen", 30000);
            Thread.sleep(1000);

            //Pausing Video in Full screen
            po.pauseVideoFullScreen(driver);
            Thread.sleep(2000);

            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Second Video Was Paused ", 30000);
            // Pause the running of the test for a brief amount of time
            Thread.sleep(2000);

            //Switching video to normal screen
            po.clickNormalScreen(driver);
            Thread.sleep(2000);

            // event verification for normal screen
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in normal screen for 2nd video", 30000);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready to play 2nd Video", 30000);

            // Resuming the playback
            po.playVideo(driver);
            Thread.sleep(1000);

            //Verifing Video Resume
            ev.verifyEvent("stateChanged - state: PLAYING", "2nd Video is resumed", 30000);
            Thread.sleep(1000);

            // After pausing clicking on recent app button and getting sample app back
            po.getBackFromRecentApp(driver);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready to play 2nd Video", 30000);

            // Clicking on Power button to lock screen
            po.powerKeyClick(driver);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready to play 2nd Video", 30000);

            // video completed event verification.
            ev.verifyEvent("playCompleted - state: LOADING", "video play completed", 90000);
            Thread.sleep(50000);

        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }
    }

    @org.testng.annotations.Test
    public void insertAdAtRunTime() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            advancePlayBackSampleApp po = new advancePlayBackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Insert Ad at Runtime");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            System.out.println("after wait for presence");

            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.InsertAdPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
            Thread.sleep(2000);

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

            //Playing Video in Full screen
            po.playVideoFullScreen(driver);
            Thread.sleep(2000);

            //Verify Playing video
            ev.verifyEvent("PLAYING", "Video is played in full screen", 30000);
            Thread.sleep(1000);

            //Pausing Video in Full screen
            po.pauseVideoFullScreen(driver);
            Thread.sleep(2000);

            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Second Video Was Paused ", 30000);
            // Pause the running of the test for a brief amount of time
            Thread.sleep(2000);

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

            //Clicking on Vast Ad button to insert vast Ad
            po.clickOnVastAd(driver);
            Thread.sleep(1000);

            //Verifying Ad started event
            ev.verifyEvent("adStarted", "Vast Ad is started", 30000);
            Thread.sleep(1000);

            //Verifying ad completed event
            ev.verifyEvent("adCompleted", "Vast Ad is Completed", 30000);
            Thread.sleep(1000);

            ev.verifyEvent("playCompleted - state: LOADING", "video play completed", 90000);

        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }

    }

    @org.testng.annotations.Test
    public void changeVideoProgramatically() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            advancePlayBackSampleApp po = new advancePlayBackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Change Video Programatically");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            System.out.println("after wait for presence");

            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.ChangeVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

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

            //Playing Video in Full screen
            po.playVideoFullScreen(driver);
            Thread.sleep(2000);

            //Verify Playing video
            ev.verifyEvent("PLAYING", "Video is played in full screen", 30000);
            Thread.sleep(1000);

            //Pausing Video in Full screen
            po.pauseVideoFullScreen(driver);
            Thread.sleep(2000);

            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Second Video Was Paused ", 30000);
            // Pause the running of the test for a brief amount of time
            Thread.sleep(2000);

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

            //Clicking on Play Video 1
            po.clickOnP1(driver);
            Thread.sleep(1000);

            //Verifing Video 1 Play
            ev.verifyEvent("stateChanged - state: PLAYING", "Played video 1", 30000);
            Thread.sleep(1000);

            //Clicking on Video 2
            po.clickOnP2(driver);
            Thread.sleep(1000);

            //Verifing Video 2 Play
            ev.verifyEvent("stateChanged - state: PLAYING", "Played video 2", 30000);
            Thread.sleep(1000);

            ev.verifyEvent("playCompleted - state: LOADING", "video play completed", 90000);

        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }
    }

    @org.testng.annotations.Test
    public void customPluginSample() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            advancePlayBackSampleApp po = new advancePlayBackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Custom Plugin Sample");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            System.out.println("after wait for presence");

            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PluginPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Verifying Ad started event
            ev.verifyEvent("stateChanged - state: PLAYING", "Ad is started", 30000);
            Thread.sleep(1000);

            //Verifying ad completed event
            ev.verifyEvent("adCompleted", "Ad is Completed", 30000);
            Thread.sleep(1000);

            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
            Thread.sleep(1000);

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

            //Playing Video in Full screen
            po.playVideoFullScreen(driver);
            Thread.sleep(2000);

            //Verify Playing video
            ev.verifyEvent("PLAYING", "Video is played in full screen", 30000);
            Thread.sleep(1000);

            //Pausing Video in Full screen
            po.pauseVideoFullScreen(driver);
            Thread.sleep(2000);

            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Second Video Was Paused ", 30000);
            // Pause the running of the test for a brief amount of time
            Thread.sleep(2000);

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

            ev.verifyEvent("playCompleted - state: LOADING", "video play completed", 120000);

        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }
    }

    @org.testng.annotations.Test
    public void customControls() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            advancePlayBackSampleApp po = new advancePlayBackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Custom Controls");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            System.out.println("after wait for presence");

            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.CustomControlsPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

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

            // Clicking on Power button to lock screen
            po.powerKeyClick(driver);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            ev.verifyEvent("playCompleted - state: LOADING", "video play completed", 90000);

        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }

    }
*/
    @org.testng.annotations.Test
    public void customOverlay() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            advancePlayBackSampleApp po = new advancePlayBackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Custom Overlay");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            System.out.println("after wait for presence");

            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.CustomOverlayPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
            Thread.sleep(1000);

            po.overlay(driver);
            Thread.sleep(1000);

            ev.verifyEvent("playCompleted - state: LOADING", "video play completed", 90000);

        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }

    }
}
