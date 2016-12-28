package testpackage.tests.advancedplaybacksampleapp;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import testpackage.pageobjects.AdvancedPlaybackSampleApp;
import testpackage.utils.*;
import java.io.IOException;
import java.util.Properties;

public class BasicTests extends EventLogTest {

    //Creating an object of Properties class
    Properties properties;
    // Creating an Object of AdvancedPlaybackSampleApp pageobject class
    AdvancedPlaybackSampleApp advancedPlaybackSampleApp = new AdvancedPlaybackSampleApp();

    @BeforeClass
    public void beforeTest() throws Exception {
        // closing all recent app from background.
        CloserecentApps.closeApps();
        // Get Property Values
        LoadPropertyValues loadPropertyValues = new LoadPropertyValues();
        properties = loadPropertyValues.loadProperty("advancedplaybacksampleapp.properties");
        //setup and initialize android driver
        SetUpAndroidDriver setUpdriver = new SetUpAndroidDriver();
        driver = setUpdriver.setUpandReturnAndroidDriver(properties.getProperty("udid"), properties.getProperty("appDir"), properties.getProperty("appValue"), properties.getProperty("platformName"), properties.getProperty("platformVersion"), properties.getProperty("appPackage"), properties.getProperty("appActivity"));
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        driver.manage().logs().get("logcat");
        //push the log file to device
        PushLogFileToDevice logpush = new PushLogFileToDevice();
        logpush.pushLogFile();
        if (driver.currentActivity() != "com.ooyala.sample.lists.AdvancedPlaybackListActivity") {
            driver.startActivity("com.ooyala.sample.AdvancedPlaybackSampleApp", "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
        }
        // Get Property Values
        System.out.println(" Screen Mode " + properties.getProperty("ScreenMode"));
    }

    @AfterClass
    public void afterTest() throws InterruptedException, IOException {
        //closing app after completion on script
        driver.closeApp();
        //end the driver session
        driver.quit();
    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws Exception {
        //Removing the events log file after test is completed for single asset.
        RemoveEventsLogFile.removeEventsFileLog();
        //Waiting to remove log file.
        Thread.sleep(10000);
    }

    @Test
    public void playWithIntitialTime() throws Exception {
        try
        {
            // wait till home screen of AdvancedPlaybackSampleApp is opened
            advancedPlaybackSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            advancedPlaybackSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //Wait till name of assets are displaying
            advancedPlaybackSampleApp.waitForTextView(driver,"Play With InitialTime");
            // Select Play with initial time video
            advancedPlaybackSampleApp.clickBasedOnText(driver, "Play With InitialTime");
            //verify if player was loaded
            advancedPlaybackSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            System.out.println("after wait for presence");
            advancedPlaybackSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PlayWithInitialTimePlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            Thread.sleep(1000);
            //Creating an object of EventVerification class
            EventVerification eventVerification = new EventVerification();
            //Play Started Verification
            eventVerification.verifyEvent("playStarted", " Video Started to Play ", 30000);
            //Play completed event verification
            eventVerification.verifyEvent("playCompleted", "video play completed",70000);
        }
        catch (Exception e) {
            System.out.println("playWithIntitialTime throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"playWithIntitialTime");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void multipleVideoPlayback() throws Exception {
        try {
            // wait till home screen of AdvancedPlaybackSampleApp is opened
            advancedPlaybackSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            advancedPlaybackSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //Wait till name of assets are displaying
            advancedPlaybackSampleApp.waitForTextView(driver,"Play With InitialTime");
            // Select Multiple Video Playback video
            advancedPlaybackSampleApp.clickBasedOnText(driver, "Multiple Video Playback");
            //verify if player was loaded
            advancedPlaybackSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            System.out.println("after wait for presence");
            advancedPlaybackSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.MultipleVideosPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            Thread.sleep(1000);
            // Waiting to display start screen of video
            advancedPlaybackSampleApp.waitForTextView(driver, "00:00");
            //click on play button to start playback of video
            advancedPlaybackSampleApp.playInNormalScreen(driver);
            //Creating an object of EventVerification class
            EventVerification eventVerification = new EventVerification();
            //Verifying play started event for 1st video
            eventVerification.verifyEvent("playStarted", " Video Started to Play ", 30000);
            //Tapping on screen to display control bar
            advancedPlaybackSampleApp.screenTap(driver);
            // clicking on pause button to pause the video
            advancedPlaybackSampleApp.pauseInNormalScreen(driver);
            //Verifying pause event after video is paused
            eventVerification.verifyEvent("stateChanged - state: PAUSED", "Video has been paused", 70000);
            //Read the time when video is paused
            advancedPlaybackSampleApp.readTime(driver);
            //Seek the video to specific location
            advancedPlaybackSampleApp.seekVideo(driver);
            //Verifying seek completed event after completion of seek
            eventVerification.verifyEvent("seekCompleted", " Playing Video was Seeked ", 75000);
            //Handing occurrence of loading spinner
            advancedPlaybackSampleApp.loadingSpinner(driver);
            //Verifying the current time of scrubber pointer
            advancedPlaybackSampleApp.readTime(driver);
            //Clicking on play button to resume the playback
            advancedPlaybackSampleApp.resumeVideoInNormalscreen(driver);
            //Verifying playing event to confirm that video is resumed
            eventVerification.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 50000);
            //2nd video start playing in queue
            eventVerification.verifyEvent(" playStarted - state: READY", "2nd video start playing in queue",90000);
            // video completed event verification.
            eventVerification.verifyEvent("playCompleted", "video play completed", 150000);
        } catch (Exception e) {
            System.out.println("multipleVideoPlayback throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"multipleVideoPlayback");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void insertAtRunTime() throws Exception {
        try {
            // wait till home screen of AdvancedPlaybackSampleApp is opened
            advancedPlaybackSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            advancedPlaybackSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //Wait till name of assets are displaying
            advancedPlaybackSampleApp.waitForTextView(driver,"Play With InitialTime");
            // Select Insert Ad at Runtime video
            advancedPlaybackSampleApp.clickBasedOnText(driver, "Insert Ad at Runtime");
            //verify if player was loaded
            advancedPlaybackSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            System.out.println("after wait for presence");
            advancedPlaybackSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.InsertAdPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            Thread.sleep(1000);
            // Waiting to display start screen of video
            advancedPlaybackSampleApp.waitForTextView(driver, "00:00");
            //click on play button to start playback of video
            advancedPlaybackSampleApp.playInNormalScreen(driver);
            //Creating an object of EventVerification class
            EventVerification eventVerification = new EventVerification();
            //Verifying play started event
            eventVerification.verifyEvent("playStarted", " Video Started to Play ", 30000);
            //Tapping on screen to display control bar
            advancedPlaybackSampleApp.screenTap(driver);
            // clicking on pause button to pause the video
            advancedPlaybackSampleApp.pauseInNormalScreen(driver);
            //Verifying pause event after video is paused
            eventVerification.verifyEvent("stateChanged - state: PAUSED", "Video has been paused", 40000);
            //Read the time when video is paused
            advancedPlaybackSampleApp.readTime(driver);
            //Seek the video to specific location
            advancedPlaybackSampleApp.seekVideo(driver);
            //Verifying seek completed event after completion of seek
            eventVerification.verifyEvent("seekCompleted", " Playing Video was Seeked ", 50000);
            //Handing occurrence of loading spinner
            advancedPlaybackSampleApp.loadingSpinner(driver);
            //Verifying the current time of scrubber pointer
            advancedPlaybackSampleApp.readTime(driver);
            //Clicking on play button to resume the playback
            advancedPlaybackSampleApp.resumeVideoInNormalscreen(driver);
            //Verifying playing event to confirm that video is resumed
            eventVerification.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 60000);
            // video completed event verification.
            eventVerification.verifyEvent("playCompleted", "video play completed", 120000);
        } catch (Exception e) {
            System.out.println("insertAtRunTime throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"insertAtRunTime");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void changeVideoProgramatically() throws Exception {
        try {
            // wait till home screen of AdvancedPlaybackSampleApp is opened
            advancedPlaybackSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            advancedPlaybackSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //Wait till name of assets are displaying
            advancedPlaybackSampleApp.waitForTextView(driver,"Play With InitialTime");
            // Select Change Video Programatically video
            advancedPlaybackSampleApp.clickBasedOnText(driver, "Change Video Programatically");
            //verify if player was loaded
            advancedPlaybackSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            System.out.println("after wait for presence");
            advancedPlaybackSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.ChangeVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            Thread.sleep(1000);
            // Waiting to display start screen of video
            advancedPlaybackSampleApp.waitForTextView(driver, "00:00");
            //click on play button to start playback of video
            advancedPlaybackSampleApp.playInNormalScreen(driver);
            //Creating an object of EventVerification class
            EventVerification eventVerification = new EventVerification();
            //Verifying play started event
            eventVerification.verifyEvent("playStarted", " Video Started to Play ", 30000);
            //Tapping on screen to display control bar
            advancedPlaybackSampleApp.screenTap(driver);
            // clicking on pause button to pause the video
            advancedPlaybackSampleApp.pauseInNormalScreen(driver);
            //Verifying pause event after video is paused
            eventVerification.verifyEvent("stateChanged - state: PAUSED", "Video has been paused", 70000);
            //Read the time when video is paused
            advancedPlaybackSampleApp.readTime(driver);
            //Seek the video to specific location
            advancedPlaybackSampleApp.seekVideo(driver);
            //Verifying seek completed event after completion of seek
            eventVerification.verifyEvent("seekCompleted", " Playing Video was Seeked ", 75000);
            //Handing occurrence of loading spinner
            advancedPlaybackSampleApp.loadingSpinner(driver);
            //Verifying the current time of scrubber pointer
            advancedPlaybackSampleApp.readTime(driver);
            //Clicking on play button to resume the playback
            advancedPlaybackSampleApp.resumeVideoInNormalscreen(driver);
            //Verifying playing event to confirm that video is resumed
            eventVerification.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 50000);
            // video completed event verification.
            eventVerification.verifyEvent("playCompleted", "video play completed", 120000);
        } catch (Exception e) {
            System.out.println("changeVideoProgramatically throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"changeVideoProgramatically");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void customPluginSample() throws Exception {
        try {
            // wait till home screen of AdvancedPlaybackSampleApp is opened
            advancedPlaybackSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            advancedPlaybackSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //Wait till name of assets are displaying
            advancedPlaybackSampleApp.waitForTextView(driver,"Play With InitialTime");
            // Select Custom Plugin Sample video
            advancedPlaybackSampleApp.clickBasedOnText(driver, "Custom Plugin Sample");
            //verify if player was loaded
            advancedPlaybackSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            System.out.println("after wait for presence");
            advancedPlaybackSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PluginPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            Thread.sleep(1000);
            // Waiting to display start screen of video
            advancedPlaybackSampleApp.waitForTextView(driver, "00:00");
            //click on play button to start playback of video
            advancedPlaybackSampleApp.playInNormalScreen(driver);
            //Creating an object of EventVerification class
            EventVerification eventVerification = new EventVerification();
            //Verifying play started event
            eventVerification.verifyEvent("playStarted", " Video Started to Play ", 30000);
            advancedPlaybackSampleApp.loadingSpinner(driver);
            eventVerification.verifyEvent("playCompleted", "video play completed",300000);
        } catch (Exception e) {
            System.out.println("customPluginSample throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"customPluginSample");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void customControls() throws Exception {
        try {
            // wait till home screen of AdvancedPlaybackSampleApp is opened
            advancedPlaybackSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            advancedPlaybackSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //Wait till name of assets are displaying
            advancedPlaybackSampleApp.waitForTextView(driver,"Play With InitialTime");
            // Select Custom Controls video
            advancedPlaybackSampleApp.clickBasedOnText(driver, "Custom Controls");
            //verify if player was loaded
            advancedPlaybackSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            System.out.println("after wait for presence");
            advancedPlaybackSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.CustomControlsPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            Thread.sleep(2000);
            //click on play button to start playback of video
            advancedPlaybackSampleApp.playInNormalScreen(driver);
            //Creating an object of EventVerification class
            EventVerification eventVerification = new EventVerification();
            //Verifying play started event
            eventVerification.verifyEvent("playStarted", " Video Started to Play ", 30000);
            // clicking on pause button to pause the video
            advancedPlaybackSampleApp.pauseInNormalScreen(driver);
            //Verifying pause event after video is paused
            eventVerification.verifyEvent("stateChanged - state: PAUSED", "Video has been paused", 70000);
            //Clicking on play button to resume the playback
            advancedPlaybackSampleApp.resumeVideoInNormalscreen(driver);
            //Verifying playing event to confirm that video is resumed
            eventVerification.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 50000);
            // video completed event verification.
            eventVerification.verifyEvent("playCompleted", "video play completed", 120000);
        } catch (Exception e) {
            System.out.println("customControls throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"customControls");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void customOverlay() throws Exception {
        try {
            // wait till home screen of AdvancedPlaybackSampleApp is opened
            advancedPlaybackSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            advancedPlaybackSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //Wait till name of assets are displaying
            advancedPlaybackSampleApp.waitForTextView(driver,"Play With InitialTime");
            // Select Custom Overlay video
            advancedPlaybackSampleApp.clickBasedOnText(driver, "Custom Overlay");
            //verify if player was loaded
            advancedPlaybackSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            System.out.println("after wait for presence");
            advancedPlaybackSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.CustomOverlayPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            Thread.sleep(1000);
            // Waiting to display start screen of video
            advancedPlaybackSampleApp.waitForTextView(driver, "00:00");
            //click on play button to start playback of video
            advancedPlaybackSampleApp.playInNormalScreen(driver);
            //Creating an object of EventVerification class
            EventVerification eventVerification = new EventVerification();
            //Verifying play started event
            eventVerification.verifyEvent("playStarted", " Video Started to Play ", 30000);
            //Tapping on screen to display control bar
            advancedPlaybackSampleApp.screenTap(driver);
            // clicking on pause button to pause the video
            advancedPlaybackSampleApp.pauseInNormalScreen(driver);
            //Verifying pause event after video is paused
            eventVerification.verifyEvent("stateChanged - state: PAUSED", "Video has been paused", 40000);
            //Read the time when video is paused
            advancedPlaybackSampleApp.readTime(driver);
            //Seek the video to specific location
            advancedPlaybackSampleApp.seekVideo(driver);
            //Verifying seek completed event after completion of seek
            eventVerification.verifyEvent("seekCompleted", " Playing Video was Seeked ", 75000);
            //Handing occurrence of loading spinner
            advancedPlaybackSampleApp.loadingSpinner(driver);
            //Verifying the current time of scrubber pointer
            advancedPlaybackSampleApp.readTime(driver);
            //Clicking on play button to resume the playback
            advancedPlaybackSampleApp.resumeVideoInNormalscreen(driver);
            //Verifying playing event to confirm that video is resumed
            eventVerification.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 50000);
            //Clicking on play button to resume the playback
            advancedPlaybackSampleApp.resumeVideoInNormalscreen(driver);
            //Verifying playing event to confirm that video is resumed
            eventVerification.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 50000);
            // video completed event verification.
            eventVerification.verifyEvent("playCompleted", "video play completed", 100000);
        } catch (Exception e) {
            System.out.println("customOverlay throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"customOverlay");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void insertAtRunTime_VastAd() throws Exception {
        try {
            // wait till home screen of AdvancedPlaybackSampleApp is opened
            advancedPlaybackSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            advancedPlaybackSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //Wait till name of assets are displaying
            advancedPlaybackSampleApp.waitForTextView(driver,"Play With InitialTime");
            // Select Insert Ad at Runtime video
            advancedPlaybackSampleApp.clickBasedOnText(driver, "Insert Ad at Runtime");
            //verify if player was loaded
            advancedPlaybackSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            System.out.println("after wait for presence");
            advancedPlaybackSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.InsertAdPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            Thread.sleep(1000);
            // Waiting to display start screen of video
            advancedPlaybackSampleApp.waitForTextView(driver, "00:00");
            //click on play button to start playback of video
            advancedPlaybackSampleApp.playInNormalScreen(driver);
            //Creating an object of EventVerification class
            EventVerification eventVerification = new EventVerification();
            //Verifying play started event
            eventVerification.verifyEvent("playStarted", " Video Started to Play ", 30000);
            //clicking on InsertVastAD option , inserting VAST Ad
            advancedPlaybackSampleApp.clickOnVastAd(driver);
            // Verifying if VAST Ad started
            eventVerification.verifyEvent("adStarted", " Vast Ad Started to Play ", 30000);
            // Verifying if VAST Ad completed
            eventVerification.verifyEvent("adCompleted","Vast Ad Playback Completed",30000);
            // Veirfying if video completed
            eventVerification.verifyEvent("playCompleted", "video play completed", 120000);
        } catch (Exception e) {
            System.out.println("insertAtRunTime_VastAd throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"insertAtRunTime_VastAd");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void insertAtRunTime_OoyalaAd() throws Exception {
        try {
            // wait till home screen of AdvancedPlaybackSampleApp is opened
            advancedPlaybackSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            advancedPlaybackSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //Wait till name of assets are displaying
            advancedPlaybackSampleApp.waitForTextView(driver,"Play With InitialTime");
            // Select Insert Ad at Runtime video
            advancedPlaybackSampleApp.clickBasedOnText(driver, "Insert Ad at Runtime");
            //verify if player was loaded
            advancedPlaybackSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            System.out.println("after wait for presence");
            advancedPlaybackSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.InsertAdPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            Thread.sleep(1000);
            // Waiting to display start screen of video
            advancedPlaybackSampleApp.waitForTextView(driver, "00:00");
            //click on play button to start playback of video
            advancedPlaybackSampleApp.playInNormalScreen(driver);
            //Creating an object of EventVerification class
            EventVerification eventVerification = new EventVerification();
            //Verifying play started event
            eventVerification.verifyEvent("playStarted", " Video Started to Play ", 30000);
            //clicking on InsertOoyalaAD option , inserting Ooyala Ad
            advancedPlaybackSampleApp.clickOnOoyalaAd(driver);
            // Verifying if Ooyala Ad started
            eventVerification.verifyEvent("adStarted", " Ooyala Ad Started to Play ", 30000);
            // Verifying if Ooyala Ad completed
            eventVerification.verifyEvent("adCompleted","Ooyala Ad Playback Completed",30000);
            // Veirfying if video completed
            eventVerification.verifyEvent("playCompleted", "video play completed", 120000);
        } catch (Exception e) {
            System.out.println("insertAtRunTime_VastAd Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"insertAtRunTime_VastAd");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void changeVideoProgramatically_P1() throws Exception {
        try {
            // wait till home screen of AdvancedPlaybackSampleApp is opened
            advancedPlaybackSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            advancedPlaybackSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //Wait till name of assets are displaying
            advancedPlaybackSampleApp.waitForTextView(driver,"Play With InitialTime");
            // Select Change Video Programatically video
            advancedPlaybackSampleApp.clickBasedOnText(driver, "Change Video Programatically");
            //verify if player was loaded
            advancedPlaybackSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            System.out.println("after wait for presence");
            advancedPlaybackSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.ChangeVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            Thread.sleep(1000);
            // Waiting to display start screen of video
            advancedPlaybackSampleApp.waitForTextView(driver, "00:00");
            //click on play button to start playback of video
            advancedPlaybackSampleApp.playInNormalScreen(driver);
            //Creating an object of EventVerification class
            EventVerification eventVerification = new EventVerification();
            //Verifying play started event
            eventVerification.verifyEvent("playStarted", " Video Started to Play ", 30000);
            //Inserting Video 1 to play
            advancedPlaybackSampleApp.clickOnP1(driver);
            // verifing that next video started to play
            eventVerification.verifyEvent("playStarted", "Inserted video1 started to play", 50000);
            //Next video play completed.
            eventVerification.verifyEvent("playCompleted", "Inserted video1 ended play", 120000);
        } catch (Exception e) {
            System.out.println("changeVideoProgramatically_P1 Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"changeVideoProgramatically_P1");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void changeVideoProgramatically_P2() throws Exception {
        try {
            // wait till home screen of AdvancedPlaybackSampleApp is opened
            advancedPlaybackSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            advancedPlaybackSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //Wait till name of assets are displaying
            advancedPlaybackSampleApp.waitForTextView(driver,"Play With InitialTime");
            // Select Change Video Programatically video
            advancedPlaybackSampleApp.clickBasedOnText(driver, "Change Video Programatically");
            //verify if player was loaded
            advancedPlaybackSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            System.out.println("after wait for presence");
            advancedPlaybackSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.ChangeVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            Thread.sleep(1000);
            // Waiting to display start screen of video
            advancedPlaybackSampleApp.waitForTextView(driver, "00:00");
            //click on play button to start playback of video
            advancedPlaybackSampleApp.playInNormalScreen(driver);
            //Creating an object of EventVerification class
            EventVerification eventVerification = new EventVerification();
            //Verifying play started event
            eventVerification.verifyEvent("playStarted", " Video Started to Play ", 30000);
            //Inserting Video 1 to play
            advancedPlaybackSampleApp.clickOnP2(driver);
            // verifing that next video started to play
            eventVerification.verifyEvent("playStarted", "Inserted video2 started to play", 50000);
            //Next video play completed.
            eventVerification.verifyEvent("playCompleted", "Inserted video2 ended play", 120000);
        } catch (Exception e) {
            System.out.println("changeVideoProgramatically_P2 throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"changeVideoProgramatically_P2");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void customPluginSample_Adverfication() throws Exception {
        try {
            // wait till home screen of AdvancedPlaybackSampleApp is opened
            advancedPlaybackSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            advancedPlaybackSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //Wait till name of assets are displaying
            advancedPlaybackSampleApp.waitForTextView(driver,"Play With InitialTime");
            // Select Custom Plugin Sample video
            advancedPlaybackSampleApp.clickBasedOnText(driver, "Custom Plugin Sample");
            //verify if player was loaded
            advancedPlaybackSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            System.out.println("after wait for presence");
            advancedPlaybackSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PluginPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            Thread.sleep(1000);
            // Waiting to display start screen of video
            advancedPlaybackSampleApp.waitForTextView(driver, "00:00");
            //click on play button to start playback of video
            advancedPlaybackSampleApp.playInNormalScreen(driver);
            //Creating an object of EventVerification class
            EventVerification eventVerification = new EventVerification();
            //verifing Ad, Ad event
            eventVerification.verifyEvent("adPodStarted","Preroll Ad is playing",20000);
            // Ad completed event verification
            eventVerification.verifyEvent("adPodCompleted","Preroll Ad play completed",30000);
            // video playing start event verification
            eventVerification.verifyEvent("playStarted", " Video Started to Play ", 80000);
            // Midroll Ad start event verification
            eventVerification.verifyEvent("adPodStarted","Midroll Ad is playing",90000);
            // Midrill Ad completed Verification
            eventVerification.verifyEvent("adPodCompleted","Midroll Ad play completed",100000);
            // Post Ad start event verification
            eventVerification.verifyEvent("adPodStarted","Postroll Ad is playing",120000);
            // Post  Ad completed Verification
            eventVerification.verifyEvent("adPodCompleted","Postroll Ad play completed",130000);
            //Verifying Play complete event
            eventVerification.verifyEvent("playCompleted", "video play completed", 130000);
        } catch (Exception e) {
            System.out.println("customPluginSample_Adverfication throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"customPluginSample_Adverfication");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void notificationsDemostration() throws Exception {
        try {
            // wait till home screen of AdvancedPlaybackSampleApp is opened
            advancedPlaybackSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            advancedPlaybackSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //Wait till name of assets are displaying
            advancedPlaybackSampleApp.waitForTextView(driver,"Play With InitialTime");
            // Select Notifications Demonstration video
            advancedPlaybackSampleApp.clickBasedOnText(driver, "Notifications Demonstration");
            //verify if player was loaded
            advancedPlaybackSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            System.out.println("after wait for presence");
            advancedPlaybackSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.NotificationsPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            Thread.sleep(1000);
            //click on play button to start playback of video
            advancedPlaybackSampleApp.playInNormalScreen(driver);
            //Creating an object of EventVerification class
            EventVerification eventVerification = new EventVerification();
            //Play Started Verification
            eventVerification.verifyEvent("playStarted", " Video Started to Play ", 30000);
            //Tapping on screen to display control bar
            advancedPlaybackSampleApp.screenTap(driver);
            // clicking on pause button to pause the video
            advancedPlaybackSampleApp.pauseInNormalScreen(driver);
            //Verifying pause event after video is paused
            eventVerification.verifyEvent("stateChanged - state: PAUSED", "Video has been paused", 70000);
            //Read the time when video is paused
            advancedPlaybackSampleApp.readTime(driver);
            //Seek the video to specific location
            advancedPlaybackSampleApp.seekVideo(driver);
            //Verifying seek completed event after completion of seek
            eventVerification.verifyEvent("seekCompleted", " Playing Video was Seeked ", 75000);
            //Handing occurrence of loading spinner
            advancedPlaybackSampleApp.loadingSpinner(driver);
            //Verifying the current time of scrubber pointer
            advancedPlaybackSampleApp.readTime(driver);
            //Clicking on play button to resume the playback
            advancedPlaybackSampleApp.resumeVideoInNormalscreen(driver);
            //Verifying playing event to confirm that video is resumed
            eventVerification.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 50000);
            //Play completed event verification
            eventVerification.verifyEvent("playCompleted", "video play completed",120000);
        }catch (Exception e){

        }
    }
}