package testpackage.tests.ooyalaSkinSampleApp;

import io.appium.java_client.android.AndroidDriver;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import testpackage.pageobjects.ooyalaSkinSampleApp;
import testpackage.utils.*;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Shivam on 25/07/16.
 */
public class BasicTests2SkinPlayback extends EventLogTest{
    private static AndroidDriver driver;

    @BeforeClass
    public void beforeTest() throws Exception {
        // closing all recent app from background.
        //CloserecentApps.closeApps();
        System.out.println("BeforeTest \n");

        System.out.println(System.getProperty("user.dir"));
        // Get Property Values
        LoadPropertyValues prop = new LoadPropertyValues();
        Properties p=prop.loadProperty("ooyalaSkinSampleApp.properties");

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
        //removeEventsLogFile.removeEventsFileLog(); create events file
        PushLogFileToDevice logpush=new PushLogFileToDevice();
        logpush.pushLogFile();
        if(driver.currentActivity()!= "com.ooyala.sample.complete.MainActivity") {
            driver.startActivity("com.ooyala.sample.SkinCompleteSampleApp","com.ooyala.sample.complete.MainActivity");
        }

        // Get Property Values
        LoadPropertyValues prop1 = new LoadPropertyValues();
        Properties p1=prop1.loadProperty();

        System.out.println(" Screen Mode "+ p1.getProperty("ScreenMode"));

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
    public void afterMethod(ITestResult result) throws Exception {
        // Waiting for all the events from sdk to come in .
        System.out.println("AfterMethod \n");
        //ScreenshotDevice.screenshot(driver);
        RemoveEventsLogFile.removeEventsFileLog();
        Thread.sleep(10000);

    }

    @org.testng.annotations.Test
    public void SkinPlaybackEncryptedHLS() throws Exception{
        int[] locPlayButon;

        try {

            // Creating an Object of FreeWheelSampleApp class
            ooyalaSkinSampleApp po = new ooyalaSkinSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            po.clickBasedOnText(driver, "Skin Playback");
            Thread.sleep(2000);

            System.out.println(" Print current activity name"+driver.currentActivity());
            if(driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")){
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);

            }

            po.waitForPresenceOfText(driver,"4:3 Aspect Ratio");

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OoyalaSkinListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Ooyala Encrypted HLS");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver,"h");

            locPlayButon=po.locationTextOnScreen(driver,"h");

            //Clicking on Play button in Ooyala Skin
            po.getPlay(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //pause the video
            Thread.sleep(3000);
            //pausing the video.
            po.pauseVideo(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            Thread.sleep(1000);

            //seeking the video
            po.seek_video(driver, 400);

            ev.verifyEvent("seekCompleted", " Video seeking completed ", 70000);

            Thread.sleep(7000);

            // playing video again.
            po.getPlay(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("Notification Received: playCompleted - state: INIT", " Video Completed Play ", 90000);

        }
        catch(Exception e)
        {
            System.out.println("SkinPlaybackEncryptedHLS throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"SkinPlaybackEncryptedHLS");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void SkinPlaybackClearHLSHighProfile() throws Exception{
        int[] locPlayButon;

        try {

            // Creating an Object of FreeWheelSampleApp class
            ooyalaSkinSampleApp po = new ooyalaSkinSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            po.clickBasedOnText(driver, "Skin Playback");
            Thread.sleep(2000);

            System.out.println(" Print current activity name"+driver.currentActivity());
            if(driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")){
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);

            }

            po.waitForPresenceOfText(driver,"4:3 Aspect Ratio");

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OoyalaSkinListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Clear HLS High Profile");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver,"h");

            locPlayButon=po.locationTextOnScreen(driver,"h");

            //Clicking on Play button in Ooyala Skin
            po.getPlay(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //pause the video
            Thread.sleep(3000);
            //pausing the video.
            po.pauseVideo(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            Thread.sleep(1000);

            //seeking the video
            po.seek_video(driver, 950);

            ev.verifyEvent("seekCompleted", " Video seeking completed ", 70000);

            Thread.sleep(7000);

            // playing video again.
            po.getPlay(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("Notification Received: playCompleted - state: INIT", " Video Completed Play ", 90000);

        }
        catch(Exception e)
        {
            System.out.println("SkinPlaybackClearHLSHighProfile throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"SkinPlaybackClearHLSHighProfile");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void SkinPlaybackHLSMainProfile() throws Exception{
        int[] locPlayButon;

        try {

            // Creating an Object of FreeWheelSampleApp class
            ooyalaSkinSampleApp po = new ooyalaSkinSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            po.clickBasedOnText(driver, "Skin Playback");
            Thread.sleep(2000);

            System.out.println(" Print current activity name"+driver.currentActivity());
            if(driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")){
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);

            }

            po.waitForPresenceOfText(driver,"4:3 Aspect Ratio");

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OoyalaSkinListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Clear HLS Main Profile");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver,"h");

            locPlayButon=po.locationTextOnScreen(driver,"h");

            //Clicking on Play button in Ooyala Skin
            po.getPlay(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //pause the video
            Thread.sleep(3000);
            //pausing the video.
            po.pauseVideo(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            Thread.sleep(1000);

            //seeking the video
            po.seek_video(driver, 950);

            ev.verifyEvent("seekCompleted", " Video seeking completed ", 70000);

            Thread.sleep(7000);

            // playing video again.
            po.getPlay(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("Notification Received: playCompleted - state: INIT", " Video Completed Play ", 90000);

        }
        catch(Exception e)
        {
            System.out.println("SkinPlaybackHLSMainProfile throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"SkinPlaybackHLSMainProfile");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void SkinPlaybackHLSBaseLine() throws Exception{
        int[] locPlayButon;

        try {

            // Creating an Object of FreeWheelSampleApp class
            ooyalaSkinSampleApp po = new ooyalaSkinSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            po.clickBasedOnText(driver, "Skin Playback");
            Thread.sleep(2000);

            System.out.println(" Print current activity name"+driver.currentActivity());
            if(driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")){
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);

            }

            po.waitForPresenceOfText(driver,"4:3 Aspect Ratio");

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OoyalaSkinListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Clear HLS Baseline");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver,"h");

            locPlayButon=po.locationTextOnScreen(driver,"h");

            //Clicking on Play button in Ooyala Skin
            po.getPlay(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //pause the video
            Thread.sleep(3000);
            //pausing the video.
            po.pauseVideo(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            Thread.sleep(1000);

            //seeking the video
            po.seek_video(driver, 950);

            ev.verifyEvent("seekCompleted", " Video seeking completed ", 70000);

            Thread.sleep(7000);

            // playing video again.
            po.getPlay(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("Notification Received: playCompleted - state: INIT", " Video Completed Play ", 90000);

        }
        catch(Exception e)
        {
            System.out.println("SkinPlaybackHLSBaseLine throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"SkinPlaybackHLSBaseLine");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void SkinPlaybackVAST3PoddedAd() throws Exception{
        int[] locPlayButon;

        try {

            // Creating an Object of FreeWheelSampleApp class
            ooyalaSkinSampleApp po = new ooyalaSkinSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            po.clickBasedOnText(driver, "Skin Playback");
            Thread.sleep(2000);

            if(driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")){
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);

            }

            po.waitForPresenceOfText(driver,"4:3 Aspect Ratio");

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OoyalaSkinListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnTextScrollTo(driver, "VAST 3.0 2 Podded Ad");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver,"h");

            locPlayButon=po.locationTextOnScreen(driver,"h");

            //Clicking on Play button in Ooyala Skin
            po.getPlay(driver);

            //Ad Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            //Thread sleep time is equivalent to the length of the AD
            Thread.sleep(5000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            //Thread sleep time is equivalent to the length of the AD
            Thread.sleep(5000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);

            //Time out
            Thread.sleep(1000);

            //Play Started
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //pause the video
            Thread.sleep(2000);
            //pausing the video.
            po.pauseVideo(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            Thread.sleep(1000);

            //seeking the video
            po.seek_video(driver, 450);

            ev.verifyEvent("seekCompleted", " Video seeking completed ", 70000);

            Thread.sleep(5000);

            // playing video again.
            po.getPlay(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);

        }
        catch(Exception e)
        {
            System.out.println("SkinPlaybackVAST3PoddedAd throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"SkinPlaybackVAST3PoddedAd");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void SkinPlaybackVAST3AllNewEvents() throws Exception{
        int[] locPlayButon;

        try {

            // Creating an Object of FreeWheelSampleApp class
            ooyalaSkinSampleApp po = new ooyalaSkinSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            po.clickBasedOnText(driver, "Skin Playback");
            Thread.sleep(2000);

            if(driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")){
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);

            }

            po.waitForPresenceOfText(driver,"4:3 Aspect Ratio");

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OoyalaSkinListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnTextScrollTo(driver, "VAST 3.0 Ad With All Of The New Events");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver,"h");

            locPlayButon=po.locationTextOnScreen(driver,"h");

            //Clicking on Play button in Ooyala Skin
            po.getPlay(driver);

            //Ad Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            //Thread sleep time is equivalent to the length of the AD
            Thread.sleep(15000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);

            //Time out
            Thread.sleep(1000);

            //Play Started
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //pause the video
            Thread.sleep(2000);
            //pausing the video.
            po.pauseVideo(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            Thread.sleep(1000);

            //seeking the video
            po.seek_video(driver, 450);

            ev.verifyEvent("seekCompleted", " Video seeking completed ", 70000);

            Thread.sleep(5000);

            // playing video again.
            po.getPlay(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);

        }
        catch(Exception e)
        {
            System.out.println("SkinPlaybackVAST3AllNewEvents throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"SkinPlaybackVAST3AllNewEvents");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void SkinPlaybackVAST3AdWithIcon() throws Exception{
        int[] locPlayButon;

        try {

            // Creating an Object of FreeWheelSampleApp class
            ooyalaSkinSampleApp po = new ooyalaSkinSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            po.clickBasedOnText(driver, "Skin Playback");
            Thread.sleep(2000);

            if(driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")){
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);

            }

            po.waitForPresenceOfText(driver,"4:3 Aspect Ratio");

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OoyalaSkinListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnTextScrollTo(driver, "VAST 3.0 Ad With Icon");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver,"h");

            locPlayButon=po.locationTextOnScreen(driver,"h");

            //Clicking on Play button in Ooyala Skin
            po.getPlay(driver);

            //Ad Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            //Thread sleep time is equivalent to the length of the AD
            Thread.sleep(15000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);

            //Time out
            Thread.sleep(1000);

            //Play Started
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //pause the video
            Thread.sleep(2000);
            //pausing the video.
            po.pauseVideo(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            Thread.sleep(1000);

            //seeking the video
            po.seek_video(driver, 450);

            ev.verifyEvent("seekCompleted", " Video seeking completed ", 70000);

            Thread.sleep(5000);

            // playing video again.
            po.getPlay(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);

        }
        catch(Exception e)
        {
            System.out.println("SkinPlaybackVAST3AdWithIcon throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"SkinPlaybackVAST3AdWithIcon");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void SkinPlaybackVAST3AdWrapper() throws Exception{
        int[] locPlayButon;

        try {

            // Creating an Object of FreeWheelSampleApp class
            ooyalaSkinSampleApp po = new ooyalaSkinSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            po.clickBasedOnText(driver, "Skin Playback");
            Thread.sleep(2000);

            if(driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")){
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);

            }

            po.waitForPresenceOfText(driver,"4:3 Aspect Ratio");

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OoyalaSkinListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnTextScrollTo(driver, "VAST 3.0 Ad Wrapper");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver,"h");

            locPlayButon=po.locationTextOnScreen(driver,"h");

            //Clicking on Play button in Ooyala Skin
            po.getPlay(driver);

            //Ad Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            //Thread sleep time is equivalent to the length of the AD
            Thread.sleep(5000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);

            //Time out
            Thread.sleep(1000);

            //Play Started
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //pause the video
            Thread.sleep(2000);
            //pausing the video.
            po.pauseVideo(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            Thread.sleep(1000);

            //seeking the video
            po.seek_video(driver, 950);

            ev.verifyEvent("seekCompleted", " Video seeking completed ", 70000);

            Thread.sleep(5000);

            // playing video again.
            po.getPlay(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);

        }
        catch(Exception e)
        {
            System.out.println("SkinPlaybackVAST3AdWrapper throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"SkinPlaybackVAST3AdWrapper");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void SkinPlaybackVMAPPreMidVASTAdData() throws Exception{
        int[] locPlayButon;

        try {

            // Creating an Object of FreeWheelSampleApp class
            ooyalaSkinSampleApp po = new ooyalaSkinSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            po.clickBasedOnText(driver, "Skin Playback");
            Thread.sleep(2000);

            if(driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")){
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);

            }

            po.waitForPresenceOfText(driver,"4:3 Aspect Ratio");

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OoyalaSkinListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnTextScrollTo(driver, "VMAP PreMid VASTAdData");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver,"h");

            locPlayButon=po.locationTextOnScreen(driver,"h");

            //Clicking on Play button in Ooyala Skin
            po.getPlay(driver);

            //Ad Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            //Thread sleep time is equivalent to the length of the AD
            Thread.sleep(30000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ",45000);

            //Time out
            Thread.sleep(1000);

            //Play Started
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //pause the video
            Thread.sleep(2000);
            //pausing the video.
            po.pauseVideo(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            Thread.sleep(1000);

            //seeking the video
            po.seek_video(driver, 950);

            ev.verifyEvent("seekCompleted", " Video seeking completed ", 70000);

            Thread.sleep(5000);

            // playing video again.
            po.getPlay(driver);
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

            Thread.sleep(1000);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 90000);

            //Thread sleep time is equivalent to the length of the AD
            Thread.sleep(30000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 90000);


            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 120000);

        }
        catch(Exception e)
        {
            System.out.println("SkinPlaybackVMAPPreMidVASTAdData throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"SkinPlaybackVMAPPreMidVASTAdData");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void SkinPlaybackVMAPPreMidPostSingle() throws Exception{
        int[] locPlayButon;

        try {

            // Creating an Object of FreeWheelSampleApp class
            ooyalaSkinSampleApp po = new ooyalaSkinSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            po.clickBasedOnText(driver, "Skin Playback");
            Thread.sleep(2000);

            if(driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")){
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);

            }

            po.waitForPresenceOfText(driver,"4:3 Aspect Ratio");

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OoyalaSkinListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnTextScrollTo(driver, "VMAP PreMidPost Single");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver,"h");

            locPlayButon=po.locationTextOnScreen(driver,"h");

            //Clicking on Play button in Ooyala Skin
            po.getPlay(driver);

            //Ad Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            //Thread sleep time is equivalent to the length of the AD
            Thread.sleep(30000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 50000);

            //Time out
            Thread.sleep(1000);

            //Play Started
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //pause the video
            Thread.sleep(3000);
            //pausing the video.
            po.pauseVideo(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            Thread.sleep(1000);

            //seeking the video
            po.seek_video(driver, 500);

            ev.verifyEvent("seekCompleted", " Video seeking completed ", 70000);

            Thread.sleep(5000);

            // playing video again.
            po.getPlay(driver);
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 100000);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 70000);

            //Thread sleep time is equivalent to the length of the AD
            Thread.sleep(30000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 80000);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 90000);

            //Thread sleep time is equivalent to the length of the AD
            Thread.sleep(30000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 90000);


            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);

        }
        catch(Exception e)
        {
            System.out.println("SkinPlaybackVMAPPreMidPostSingle throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"SkinPlaybackVMAPPreMidPostSingle");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    // TODO Asset Is not play (Pcode and Embed Code owner do not match )
    //@org.testng.annotations.Test
    public void SkinPlaybackVAST3AdSkippableAdLong() throws Exception{
        int[] locPlayButon;

        try {

            // Creating an Object of FreeWheelSampleApp class
            ooyalaSkinSampleApp po = new ooyalaSkinSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            po.clickBasedOnText(driver, "Skin Playback");
            Thread.sleep(2000);

            if(driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")){
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);

            }

            po.waitForPresenceOfText(driver,"4:3 Aspect Ratio");

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OoyalaSkinListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnTextScrollTo(driver, "VAST 3.0 Skippable Ad Long");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver,"h");

            locPlayButon=po.locationTextOnScreen(driver,"h");

            //Clicking on Play button in Ooyala Skin
            po.getPlay(driver);

            //Ad Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            //Thread sleep time is equivalent to the length of the AD
            Thread.sleep(15000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            //Thread sleep time is equivalent to the length of the AD
            Thread.sleep(5000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);

            //Time out
            Thread.sleep(1000);

            //Play Started
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //pause the video
            Thread.sleep(2000);
            //pausing the video.
            po.pauseVideo(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            Thread.sleep(1000);

            //seeking the video
            po.seek_video(driver, 450);

            ev.verifyEvent("seekCompleted", " Video seeking completed ", 70000);

            Thread.sleep(5000);

            // playing video again.
            po.getPlay(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);

        }
        catch(Exception e)
        {
            System.out.println("SkinPlaybackVAST3AdSkippableAdLong throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"SkinPlaybackVAST3AdSkippableAdLong");
            Assert.assertTrue(false, "This will fail!");
        }
    }






}
