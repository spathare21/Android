package testpackage.tests.CompleteSampleApp;

import io.appium.java_client.android.AndroidDriver;
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

public class BasicSampleQL extends EventLogTest {



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
    //public void beforeTest() throws Exception{
    public void beforeMethod() throws Exception {
        System.out.println("beforeMethod \n");
        driver.manage().logs().get("logcat");
        PushLogFileToDevice logpush=new PushLogFileToDevice();
        logpush.pushLogFile();
        if(driver.currentActivity()!= "com.ooyala.sample.complete.MainActivity") {
            driver.startActivity("com.ooyala.sample.CompleteSampleApp","com.ooyala.sample.complete.MainActivity");
        }
        // Get Property Values
        LoadPropertyValues prop1 = new LoadPropertyValues();
        Properties p1=prop1.loadProperty();

        System.out.println(" Screen Mode "+ p1.getProperty("ScreenMode"));
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
    public void afterMethod(ITestResult result) throws Exception {
        // Waiting for all the events from sdk to come in .
        System.out.println("AfterMethod \n");
        //RemoveEventsLogFile.removeEventsFileLog();
        Thread.sleep(10000);
    }

    @org.testng.annotations.Test
    public void AspectRatioTest() throws Exception{

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            CompleteSampleApp po = new CompleteSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");

            // Wrire to console activity name of home screen app
            System.out.println("CompleteSampleApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            po.clickBasedOnText(driver,"Basic Playback");

            po.waitForTextView(driver,"4:3 Aspect Ratio");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "4:3 Aspect Ratio");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForTextView(driver,"00:00");
            Thread.sleep(1000);

            po.playInNormalScreen(driver);
            Thread.sleep(1000);
            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
            Thread.sleep(3000);

            // Click on the web area so that player screen shows up
            po.screenTap(driver);
            Thread.sleep(1000);
            //Pausing Video in Normal screen.
            po.pauseInNormalScreen(driver);
            Thread.sleep(1000);
            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);
            Thread.sleep(1000);
            // Pause the running of the test for a brief amount of time
            po.readTime(driver);
            Thread.sleep(3000);

            po.seekVideo(driver);
            Thread.sleep(500);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 30000);
            Thread.sleep(5000);

            po.loadingSpinner(driver);

            po.readTime(driver);

            po.resumeVideoInNormalscreen(driver);
            Thread.sleep(1000);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 45000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 300000);
        }
        catch(Exception e)
        {
            System.out.println(" AspectRatioTest throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"AspectRatioTest");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void HLSVideoTest() throws Exception{

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            CompleteSampleApp po = new CompleteSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");
            // Wrire to console activity name of home screen app
            System.out.println("CompleteSampleApp App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            po.clickBasedOnText(driver,"Basic Playback");

            po.waitForTextView(driver,"4:3 Aspect Ratio");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "HLS Video");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForTextView(driver,"00:00");
            Thread.sleep(1000);

            //Playing the Video
            po.playInNormalScreen(driver);
            Thread.sleep(1000);

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
            Thread.sleep(2000);

            // Click on the web area so that player screen shows up
            po.screenTap(driver);
            Thread.sleep(1000);
            //Pausing Video in Normal screen.
            po.pauseInNormalScreen(driver);
            Thread.sleep(1000);
            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);
            Thread.sleep(1000);
            // Pause the running of the test for a brief amount of time
            po.readTime(driver);
            Thread.sleep(3000);

            po.seekVideo(driver);
            Thread.sleep(500);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 30000);
            Thread.sleep(5000);

            po.loadingSpinner(driver);

            po.readTime(driver);

            po.resumeVideoInNormalscreen(driver);
            Thread.sleep(1000);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 45000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        }
        catch(Exception e)
        {
            System.out.println(" HLSVideoTest throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"HLSVideoTest");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void MP4VideoTest() throws Exception{

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            CompleteSampleApp po = new CompleteSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");
            // Wrire to console activity name of home screen app
            System.out.println("CompleteSampleApp App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            po.clickBasedOnText(driver,"Basic Playback");

            po.waitForTextView(driver,"4:3 Aspect Ratio");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "MP4 Video");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForTextView(driver,"00:00");
            Thread.sleep(1000);

            po.playInNormalScreen(driver);
            Thread.sleep(1000);
            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
            Thread.sleep(3000);

            // Click on the web area so that player screen shows up
            po.screenTap(driver);
            Thread.sleep(1000);
            //Pausing Video in Normal screen.
            po.pauseInNormalScreen(driver);
            Thread.sleep(1000);
            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);
            Thread.sleep(1000);
            // Pause the running of the test for a brief amount of time
            po.readTime(driver);
            Thread.sleep(3000);

            po.seekVideo(driver);
            Thread.sleep(500);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 30000);
            Thread.sleep(5000);

            po.loadingSpinner(driver);

            po.readTime(driver);

            po.resumeVideoInNormalscreen(driver);
            Thread.sleep(1000);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 45000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        }
        catch(Exception e)
        {
            System.out.println("MP4VideoTest thows Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"MP4VideoTest");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void VODwithCCTest() throws Exception{

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            CompleteSampleApp po = new CompleteSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");
            // Wrire to console activity name of home screen app
            System.out.println("CompleteSampleApp App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            po.clickBasedOnText(driver,"Basic Playback");

            po.waitForTextView(driver,"4:3 Aspect Ratio");

            // Select one of the video HLS,MP4 etc .
            //po.clickBasedOnText(driver, "VOD with CCs");
            po.clickBasedOnTextScrollTo(driver, "VOD with CCs");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForTextView(driver,"00:00");
            Thread.sleep(1000);

            //Playing the video
            po.playInNormalScreen(driver);
            Thread.sleep(1000);

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
            Thread.sleep(3000);

            po.screenTap(driver);
            Thread.sleep(1000);
            //pausing the video
            po.pauseInNormalScreen(driver);
            Thread.sleep(1000);
            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);
            Thread.sleep(1000);

            po.readTime(driver);

            Thread.sleep(1000);

            po.clickImagebuttons(driver,(2-1));

            po.waitForTextView(driver,"Languages");

            po.clickRadiobuttons(driver,(7-1));

            Thread.sleep(2000);

            Assert.assertTrue(po.radioButtonChecked(driver,(7-1)));

            // closed Captions event verification
            ev.verifyEvent("closedCaptionsLanguageChanged", " CC of the Video Was Changed ", 30000);

            driver.navigate().back();

            // Pause the running of the test for a brief amount of time
            Thread.sleep(3000);

            po.seekVideo(driver);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 30000);
            Thread.sleep(3000);

            po.loadingSpinner(driver);

            po.readTime(driver);

            // Tap coordinates again to play
            po.resumeVideoInNormalscreen(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 45000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        }
        catch(Exception e)
        {
            System.out.println("VODwithCCTest throws Exception \n"+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VODwithCCTest");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void VASTAdPreRollTest() throws Exception{

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            CompleteSampleApp po = new CompleteSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");
            // Wrire to console activity name of home screen app
            System.out.println("CompleteSampleApp App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            po.clickBasedOnText(driver, "Basic Playback");

            po.waitForTextView(driver,"4:3 Aspect Ratio");

            // Select one of the video HLS,MP4 etc .
            //po.clickBasedOnText(driver, "VAST Ad Pre-roll");
            po.clickBasedOnTextScrollTo(driver, "VAST2 Ad Pre-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForTextView(driver,"00:00");

            po.playInNormalScreen(driver);
            Thread.sleep(1000);
            //Ad Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            //Thread sleep time is equivalent to the length of the AD
            Thread.sleep(5000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 40000);

            //Time out
            Thread.sleep(1000);

            //Play Started
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
            Thread.sleep(6000);

            po.loadingSpinner(driver);

            po.screenTap(driver);
            Thread.sleep(500);
            //Pausing Video in Normal screen.
            po.pauseInNormalScreen(driver);
            Thread.sleep(1000);
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);
            Thread.sleep(1000);

            po.readTime(driver);

            po.seekVideo(driver);
            Thread.sleep(1000);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 30000);
            Thread.sleep(3000);

            po.loadingSpinner(driver);

            po.readTime(driver);

            po.resumeVideoInNormalscreen(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 45000);
            //Timeout for the duration of the video

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        }
        catch(Exception e)
        {
            System.out.println("VASTAdPreRollTest throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VASTAdPreRollTest");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void VASTADMidRollTest() throws Exception{

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            CompleteSampleApp po = new CompleteSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");
            // Wrire to console activity name of home screen app
            System.out.println("CompleteSampleApp App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            po.clickBasedOnText(driver, "Basic Playback");
            Thread.sleep(1000);

            po.waitForTextView(driver,"4:3 Aspect Ratio");

            // Select one of the video HLS,MP4 etc .
            //po.clickBasedOnText(driver, "VAST Ad Mid-roll");
            po.clickBasedOnTextScrollTo(driver, "VAST2 Ad Mid-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForTextView(driver,"00:00");

            po.playInNormalScreen(driver);
            Thread.sleep(1000);

            //Play Started
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            po.loadingSpinner(driver);

            //Ad Started Verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            po.loadingSpinner(driver);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

            po.loadingSpinner(driver);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 100000);
        }
        catch(Exception e)
        {
            System.out.println("VASTADMidRollTest throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VASTADMidRollTest");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void VASTADPostRollTest() throws Exception{

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            CompleteSampleApp po = new CompleteSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");
            // Wrire to console activity name of home screen app
            System.out.println("CompleteSampleApp App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            po.clickBasedOnText(driver,"Basic Playback");
            Thread.sleep(500);

            po.waitForTextView(driver,"4:3 Aspect Ratio");

            // Select one of the video HLS,MP4 etc .
            //po.clickBasedOnText(driver, "VAST Ad Post-roll");
            po.clickBasedOnTextScrollTo(driver, "VAST2 Ad Post-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForTextView(driver,"00:00");

            po.playInNormalScreen(driver);
            Thread.sleep(1000);

            //Play Started
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            po.loadingSpinner(driver);
            //Ad Started Verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            po.loadingSpinner(driver);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

            po.loadingSpinner(driver);


            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 80000);

        }
        catch(Exception e)
        {
            System.out.println("VASTADPostRollTest throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VASTADPostRollTest");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void OoyalaAdPreRollTest() throws Exception{

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            CompleteSampleApp po = new CompleteSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");
            // Wrire to console activity name of home screen app
            System.out.println("CompleteSampleApp App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            po.clickBasedOnText(driver,"Basic Playback");
            Thread.sleep(500);

            po.waitForTextView(driver,"4:3 Aspect Ratio");

            // Select one of the video HLS,MP4 etc .
            //po.clickBasedOnText(driver, "VAST Ad Pre-roll");
            po.clickBasedOnTextScrollTo(driver, "Ooyala Ad Pre-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForTextView(driver,"00:00");
            Thread.sleep(1000);

            po.playInNormalScreen(driver);
            Thread.sleep(1000);

            //Ad Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            po.loadingSpinner(driver);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 50000);

            po.loadingSpinner(driver);

            //Time out
            Thread.sleep(1000);

            //Play Started
            ev.verifyEvent("playStarted", " Video Started to Play ", 55000);

            po.loadingSpinner(driver);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 70000);
        }
        catch(Exception e)
        {
            System.out.println("OoyalaAdPreRollTest throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"OoyalaAdPreRollTest");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void OoyalaADMidRollTest() throws Exception{

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            CompleteSampleApp po = new CompleteSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");
            // Wrire to console activity name of home screen app
            System.out.println("CompleteSampleApp App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            po.clickBasedOnText(driver,"Basic Playback");
            Thread.sleep(500);

            po.waitForTextView(driver,"4:3 Aspect Ratio");

            // Select one of the video HLS,MP4 etc .
            //po.clickBasedOnText(driver, "VAST Ad Mid-roll");
            po.clickBasedOnTextScrollTo(driver, "Ooyala Ad Mid-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForTextView(driver,"00:00");

            po.playInNormalScreen(driver);
            Thread.sleep(1000);

            //Play Started
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            po.loadingSpinner(driver);

            //Ad Started Verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            po.loadingSpinner(driver);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

            po.loadingSpinner(driver);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 300000);
        }
        catch(Exception e)
        {
            System.out.println("OoyalaADMidRollTest throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"OoyalaADMidRollTest");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void OoyalaADPostRollTest() throws Exception{

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            CompleteSampleApp po = new CompleteSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");
            // Wrire to console activity name of home screen app
            System.out.println("CompleteSampleApp App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            po.clickBasedOnText(driver,"Basic Playback");
            Thread.sleep(500);

            po.waitForTextView(driver,"4:3 Aspect Ratio");

            // Select one of the video HLS,MP4 etc .
            //po.clickBasedOnText(driver, "VAST Ad Post-roll");
            po.clickBasedOnTextScrollTo(driver, "Ooyala Ad Post-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForTextView(driver,"00:00");

            po.playInNormalScreen(driver);
            Thread.sleep(1000);

            //Play Started
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            po.loadingSpinner(driver);

            //Ad Started Verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);

            po.loadingSpinner(driver);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 60000);

            po.loadingSpinner(driver);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 80000);

        }
        catch(Exception e)
        {
            System.out.println("OoyalaADPostRollTest throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"OoyalaADPostRollTest");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void VASTAdWrapperTest() throws Exception{

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            CompleteSampleApp po = new CompleteSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");
            // Wrire to console activity name of home screen app
            System.out.println("CompleteSampleApp App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            po.clickBasedOnText(driver,"Basic Playback");
            Thread.sleep(500);

            po.waitForTextView(driver,"4:3 Aspect Ratio");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "VAST2 Ad Wrapper");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForTextView(driver,"00:00");

            po.playInNormalScreen(driver);
            Thread.sleep(1000);

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            po.loadingSpinner(driver);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        }
        catch(Exception e)
        {
            System.out.println("VASTAdWrapperTest throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VASTAdWrapperTest");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void MultiAdCombinationTest() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            CompleteSampleApp po = new CompleteSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");
            // Wrire to console activity name of home screen app
            System.out.println("CompleteSampleApp App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            po.clickBasedOnText(driver,"Basic Playback");
            Thread.sleep(500);

            po.waitForTextView(driver,"4:3 Aspect Ratio");

            // Select one of the video HLS,MP4 etc .
            //po.clickBasedOnText(driver, "VAST Ad Mid-roll");
            po.clickBasedOnTextScrollTo(driver, "Multi Ad combination");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresence(driver,"className","android.widget.ImageButton");
            Thread.sleep(1000);

            po.playInNormalScreen(driver);
            Thread.sleep(1000);

            //Ad Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            po.loadingSpinner(driver);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 40000);

            po.loadingSpinner(driver);

            //Play Started

            ev.verifyEvent("playStarted", " Video Started to Play ", 41000);

            //Thread sleep time is equivalent to the length of the half of the video
            po.loadingSpinner(driver);

            //Ad Started Verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            po.loadingSpinner(driver);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

            //Thread sleep time is equivalent to the length of the half of the video
            po.loadingSpinner(driver);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 400000);
        } catch (Exception e) {
            System.out.println("VASTADPostRollTest throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VASTADPostRollTest");
            Assert.assertTrue(false, "This will fail!");
        }
    }


}
