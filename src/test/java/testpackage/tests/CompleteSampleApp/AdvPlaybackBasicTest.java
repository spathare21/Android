package testpackage.tests.CompleteSampleApp;

import io.appium.java_client.android.AndroidDriver;
import javafx.scene.layout.Priority;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import testpackage.pageobjects.CompleteSampleApp;
import testpackage.utils.*;

import java.io.IOException;
import java.util.Properties;

public class AdvPlaybackBasicTest extends EventLogTest {


    @BeforeClass
    public void beforeTest() throws Exception {

        System.out.println("BeforeTest \n");

        System.out.println(System.getProperty("user.dir"));
        // Get Property Values
        LoadPropertyValues prop = new LoadPropertyValues();
        Properties p = prop.loadProperty("completesampleapp.properties");

        System.out.println("Device id from properties file " + p.getProperty("deviceName"));
        System.out.println("PortraitMode from properties file " + p.getProperty("PortraitMode"));
        System.out.println("Path where APK is stored"+ p.getProperty("appDir"));
        System.out.println("APK name is "+ p.getProperty("app"));
        System.out.println("Platform under Test is "+ p.getProperty("platformName"));
        System.out.println("Mobile OS Version is "+ p.getProperty("OSVERSION"));
        System.out.println("Package Name of the App is "+ p.getProperty("appPackage"));
        System.out.println("Activity Name of the App is "+ p.getProperty("appActivity"));

        SetUpAndroidDriver setUpdriver = new SetUpAndroidDriver();
        driver = setUpdriver.setUpandReturnAndroidDriver(p.getProperty("udid"), p.getProperty("appDir"), p.getProperty("appValue"), p.getProperty("platformName"), p.getProperty("platformVersion"), p.getProperty("appPackage"), p.getProperty("appActivity"));
        Thread.sleep(2000);
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        System.out.println("beforeMethod \n");
        driver.manage().logs().get("logcat");
        PushLogFileToDevice logpush = new PushLogFileToDevice();
        logpush.pushLogFile();
        if (driver.currentActivity() != "com.ooyala.sample.complete.MainActivity") {
            driver.startActivity("com.ooyala.sample.CompleteSampleApp", "com.ooyala.sample.complete.MainActivity");
        }

        // Get Property Values
        LoadPropertyValues prop1 = new LoadPropertyValues();
        Properties p1 = prop1.loadProperty();

        System.out.println(" Screen Mode " + p1.getProperty("ScreenMode"));

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
    public void afterMethod(ITestResult result) throws Exception {
        // Waiting for all the events from sdk to come in .
        System.out.println("AfterMethod \n");
        RemoveEventsLogFile.removeEventsFileLog();
        Thread.sleep(10000);

    }

    @org.testng.annotations.Test
    public void playWithIntitialTime() throws Exception {

        try
        {
            // Creating an Object of CompleteSampleApp class
            CompleteSampleApp po = new CompleteSampleApp();
            // wait till home screen of CompleteSampleApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");
            // Wrire to console activity name of home screen app
            System.out.println("CompleteSampleApp App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

           //Selecting Adv Sample App from Complete Sample App
            po.clickBasedOnText(driver,"Advanced Playback");

            //waiting for loading assets from Advanced sample app
            po.waitForTextView(driver,"Play With InitialTime");

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
            //Sleep for brief time
            Thread.sleep(3000);

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            ev.verifyEvent("playCompleted", "video play completed",150000);

        }
        catch (Exception e) {
            System.out.println("playWithIntitialTime throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"playWithIntitialTime");
            Assert.assertTrue(false, "This will fail!");
        }

    }

    //TODO Asset is failing becasue of PBA-4592
    @org.testng.annotations.Test
    public void multipleVideoPlayback() throws Exception {

        try {
            // Creating an Object of CompleteSampleApp class
            CompleteSampleApp po = new CompleteSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");
            // Wrire to console activity name of home screen app
            System.out.println("CompleteSampleApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Selecting Adv Sample App from Complete Sample App
            po.clickBasedOnText(driver,"Advanced Playback");

            po.waitForTextView(driver,"Play With InitialTime");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Multiple Video Playback");
            Thread.sleep(1000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            System.out.println("after wait for presence");

            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.MultipleVideosPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForTextView(driver, "00:00");

            //1st video Play Started Verification
            po.playInNormalScreen(driver);
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
            Thread.sleep(7000);

            po.screenTap(driver);
            Thread.sleep(1000);

            po.pauseInNormalScreen(driver);
            ev.verifyEvent("stateChanged - state: PAUSED", "Video has been paused", 70000);

            po.readTime(driver);
            Thread.sleep(1000);

            po.seekVideo(driver);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 75000);
            Thread.sleep(3000);

            po.loadingSpinner(driver);

            po.readTime(driver);
            Thread.sleep(1000);

            //po.playInNormalScreen(driver);
            po.resumeVideoInNormalscreen(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 90000);
            Thread.sleep(30000);

            //2nd video start playing in queue
            ev.verifyEvent(" playStarted - state: READY", "2nd video start playing in queue",90000);
            Thread.sleep(30000);
            // video completed event verification.
            ev.verifyEvent("playCompleted", "video play completed", 130000);

        } catch (Exception e) {
            System.out.println("multipleVideoPlayback throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"multipleVideoPlayback");
            Assert.assertTrue(false, "This will fail!");
        }

    }

    @org.testng.annotations.Test
    public void insertAtRunTime() throws Exception {

        try {
            // Creating an Object of CompleteSampleApp class
            CompleteSampleApp po = new CompleteSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");

            // Wrire to console activity name of home screen app
            System.out.println("CompleteSampleApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Selecting Adv Sample App from Complete Sample App
            po.clickBasedOnText(driver,"Advanced Playback");

            po.waitForTextView(driver,"Play With InitialTime");

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

            po.waitForTextView(driver,"00:00");

            po.playInNormalScreen(driver);
            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
            Thread.sleep(7000);

            po.screenTap(driver);
            Thread.sleep(1000);

            po.pauseInNormalScreen(driver);
            ev.verifyEvent("stateChanged - state: PAUSED", "Video has been paused", 70000);

            po.readTime(driver);
            Thread.sleep(1000);

            po.seekVideo(driver);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 75000);
            Thread.sleep(3000);

            po.loadingSpinner(driver);

            po.readTime(driver);
            Thread.sleep(1000);
            //po.playInNormalScreen(driver);
            po.resumeVideoInNormalscreen(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video resume to Play ", 55000);
            Thread.sleep(20000);
            ev.verifyEvent("playCompleted", "video play completed", 95000);

        } catch (Exception e) {
            System.out.println("insertAtRunTime throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"insertAtRunTime");
            Assert.assertTrue(false, "This will fail!");
        }

    }

    @org.testng.annotations.Test
    public void changeVideoProgramatically() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            CompleteSampleApp po = new CompleteSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");

            // Wrire to console activity name of home screen app
            System.out.println("CompleteSampleApp App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Selecting Adv Sample App from Complete Sample App
            po.clickBasedOnText(driver,"Advanced Playback");

            po.waitForTextView(driver,"Play With InitialTime");

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

            po.waitForTextView(driver,"00:00");

            po.playInNormalScreen(driver);
            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
            Thread.sleep(7000);

            po.screenTap(driver);
            Thread.sleep(1000);

            po.pauseInNormalScreen(driver);
            ev.verifyEvent("stateChanged - state: PAUSED", "Video has been paused", 70000);

            po.readTime(driver);
            Thread.sleep(1000);

            po.seekVideo(driver);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 75000);
            Thread.sleep(3000);

            po.loadingSpinner(driver);

            po.readTime(driver);
            Thread.sleep(1000);

            po.resumeVideoInNormalscreen(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video resume to Play ", 55000);
            Thread.sleep(20000);
            ev.verifyEvent("playCompleted", "video play completed", 95000);


        } catch (Exception e) {
            System.out.println("changeVideoProgramatically throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"changeVideoProgramatically");
            Assert.assertTrue(false, "This will fail!");
        }

    }

    @org.testng.annotations.Test
    public void customPluginSample() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            CompleteSampleApp po = new CompleteSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");

            // Wrire to console activity name of home screen app
            System.out.println("CompleteSampleApp App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Selecting Adv Sample App from Complete Sample App
            po.clickBasedOnText(driver, "Advanced Playback");

            po.waitForTextView(driver,"Play With InitialTime");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Custom Plugin Sample");
            Thread.sleep(3000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            System.out.println("after wait for presence");

            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PluginPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForTextView(driver,"00:00");

            po.playInNormalScreen(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video resume to Play ", 30000);
            po.loadingSpinner(driver);
            Thread.sleep(60000);
            po.loadingSpinner(driver);
            ev.verifyEvent("playCompleted", "video play completed", 120000);

        } catch (Exception e) {
            System.out.println("customPluginSample throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"customPluginSample");
            Assert.assertTrue(false, "This will fail!");
        }

    }

    @org.testng.annotations.Test
    public void customControls() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            CompleteSampleApp po = new CompleteSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");

            // Wrire to console activity name of home screen app
            System.out.println("CompleteSampleApp App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Selecting Adv Sample App from Complete Sample App
            po.clickBasedOnText(driver, "Advanced Playback");
            po.waitForTextView(driver,"Play With InitialTime");

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
            Thread.sleep(3000);

            po.playInNormalScreen(driver);
            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
            Thread.sleep(7000);

            po.screenTap(driver);

            po.pauseInNormalScreen(driver);
            ev.verifyEvent("stateChanged - state: PAUSED", "Video has been paused", 40000);
            Thread.sleep(2000);

            po.resumeVideoInNormalscreen(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video resume to Play ", 45000);

            ev.verifyEvent("playCompleted", "video play completed", 90000);

        } catch (Exception e) {
            System.out.println("customControls throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"customControls");
            Assert.assertTrue(false, "This will fail!");
        }

    }

    @org.testng.annotations.Test
    public void customOverlay() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            CompleteSampleApp po = new CompleteSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");

            // Wrire to console activity name of home screen app
            System.out.println("CompleteSampleApp App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Selecting Adv Sample App from Complete Sample App
            po.clickBasedOnText(driver, "Advanced Playback");
            po.waitForTextView(driver, "Play With InitialTime");

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

            po.waitForTextView(driver,"00:00");

            po.playInNormalScreen(driver);
            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
            Thread.sleep(7000);

            po.screenTap(driver);

            po.pauseInNormalScreen(driver);
            ev.verifyEvent("stateChanged - state: PAUSED", "Video has been paused", 40000);
            Thread.sleep(2000);

            po.resumeVideoInNormalscreen(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video resume to Play ", 45000);

            ev.verifyEvent("playCompleted", "video play completed", 90000);

        } catch (Exception e) {
            System.out.println("customOverlay throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"customOverlay");
            Assert.assertTrue(false, "This will fail!");
        }

    }

    @org.testng.annotations.Test
    public void insertAtRunTime_VastAd() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            CompleteSampleApp po = new CompleteSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");

            // Wrire to console activity name of home screen app
            System.out.println("CompleteSampleApp App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Selecting Adv Sample App from Complete Sample App
            po.clickBasedOnText(driver, "Advanced Playback");

            po.waitForTextView(driver,"Play With InitialTime");

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
            po.waitForTextView(driver,"00:00");

            po.playInNormalScreen(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
            Thread.sleep(5000);

            po.screenTap(driver);

            po.pauseInNormalScreen(driver);
            ev.verifyEvent("stateChanged - state: PAUSED", "Video has been paused", 70000);

            //clicking on InsertVastAD option , inserting VAST Ad
            po.clickOnVastAd(driver);
            Thread.sleep(2000);

            po.resumeVideoInNormalscreen(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 80000);

            // Verifying if VAST Ad started
            ev.verifyEvent("adStarted", " Vast Ad Started to Play ", 85000);
            Thread.sleep(5000);
            // Verifying if VAST Ad completed
            ev.verifyEvent("adCompleted","Vast Ad Playback Completed",85000);
            // Veirfying if video completed
            ev.verifyEvent("playCompleted", "video play completed", 120000);

            Thread.sleep(2000);

        } catch (Exception e) {
            System.out.println("insertAtRunTime_VastAd throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"insertAtRunTime_VastAd");
            Assert.assertTrue(false, "This will fail!");
        }

    }

    @org.testng.annotations.Test
    public void insertAtRunTime_OoyalaAd() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            CompleteSampleApp po = new CompleteSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");

            // Wrire to console activity name of home screen app
            System.out.println("CompleteSampleApp App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Selecting Adv Sample App from Complete Sample App
            po.clickBasedOnText(driver, "Advanced Playback");

            po.waitForTextView(driver,"Play With InitialTime");

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
            po.waitForTextView(driver,"00:00");
            po.playInNormalScreen(driver);
            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
            Thread.sleep(5000);

            po.screenTap(driver);

            po.pauseInNormalScreen(driver);
            ev.verifyEvent("stateChanged - state: PAUSED", "Video has been paused", 70000);

            //clicking on InsertOoyalaAD option , inserting Ooyala Ad
            po.clickOnOoyalaAd(driver);
            Thread.sleep(2000);

            po.resumeVideoInNormalscreen(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 80000);


            // Verifying if Ooyala Ad started
            ev.verifyEvent("adStarted", " Ooyala Ad Started to Play ", 85000);
            Thread.sleep(5000);
            // Verifying if Ooyala Ad completed
            ev.verifyEvent("adCompleted","Ooyala Ad Playback Completed",85000);
            // Veirfying if video completed
            ev.verifyEvent("playCompleted", "video play completed", 120000);

            Thread.sleep(2000);


        } catch (Exception e) {
            System.out.println("insertAtRunTime_VastAd Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"insertAtRunTime_VastAd");
            Assert.assertTrue(false, "This will fail!");
        }

    }

    @org.testng.annotations.Test
    public void changeVideoProgramatically_P1() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            CompleteSampleApp po = new CompleteSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");

            // Wrire to console activity name of home screen app
            System.out.println("CompleteSampleApp App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Selecting Adv Sample App from Complete Sample App
            po.clickBasedOnText(driver, "Advanced Playback");

            po.waitForTextView(driver,"Play With InitialTime");

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

            po.waitForTextView(driver,"00:00");

            po.playInNormalScreen(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Main Video Started to Play ", 30000);

            Thread.sleep(5000);

            po.screenTap(driver);

            po.pauseInNormalScreen(driver);
            ev.verifyEvent("stateChanged - state: PAUSED", "Video has been paused", 70000);

            po.clickOnP1(driver);
            // verifing that next video started to play
            ev.verifyEvent("playStarted", "Inserted video1 started to play", 50000);

            //Next video play completed.
            ev.verifyEvent("playCompleted", "Inserted video1 ended play", 150000);


        } catch (Exception e) {
            System.out.println("changeVideoProgramatically_P1 Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"changeVideoProgramatically_P1");
            Assert.assertTrue(false, "This will fail!");
        }

    }

    @org.testng.annotations.Test
    public void changeVideoProgramatically_P2() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            CompleteSampleApp po = new CompleteSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");

            // Wrire to console activity name of home screen app
            System.out.println("CompleteSampleApp App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Selecting Adv Sample App from Complete Sample App
            po.clickBasedOnText(driver, "Advanced Playback");

            po.waitForTextView(driver,"Play With InitialTime");

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

            po.waitForTextView(driver,"00:00");

            po.playInNormalScreen(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Main Video Started to Play ", 30000);

            Thread.sleep(5000);

            po.screenTap(driver);

            po.pauseInNormalScreen(driver);
            ev.verifyEvent("stateChanged - state: PAUSED", "Video has been paused", 70000);

            po.clickOnP2(driver);
            // verifing that next video started to play
            ev.verifyEvent("playStarted", "Inserted video2 started to play", 50000);

            //Next video play completed.
            ev.verifyEvent("playCompleted", "Inserted video2 ended play", 150000);

        } catch (Exception e) {
            System.out.println("changeVideoProgramatically_P2 throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"changeVideoProgramatically_P2");
            Assert.assertTrue(false, "This will fail!");
        }

    }
}
