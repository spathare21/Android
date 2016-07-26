package testpackage.tests.ooyalaSkinSampleApp;

/**
 * Created by bsondur on 2/24/16.
 */

import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import io.appium.java_client.android.AndroidDriver;
import testpackage.pageobjects.ooyalaSkinSampleApp;
import testpackage.utils.*;

import java.util.Properties;
import java.io.IOException;


public class BasicTestsSkinPlayback {

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
    public void afterMethod() throws InterruptedException, IOException {
        // Waiting for all the events from sdk to come in .
        System.out.println("AfterMethod \n");
        //ScreenshotDevice.screenshot(driver);
        RemoveEventsLogFile.removeEventsFileLog();
        Thread.sleep(10000);

    }


    @org.testng.annotations.Test
    public void SkinPlaybackAspectRatio() throws Exception{
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
            po.clickBasedOnText(driver, "4:3 Aspect Ratio");
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

            Thread.sleep(5000);

            // playing video again.
            po.getPlay(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("Notification Received: playCompleted - state: INIT", " Video Completed Play ", 90000);

        }
        catch(Exception e)
        {
            System.out.println("SkinPlaybackAspectRatio throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"SkinPlaybackAspectRatio");
            Assert.assertTrue(false, "This will fail!");
        }
    }

        @org.testng.annotations.Test
    public void SkinPlaybackMP4Video() throws Exception{
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

            po.waitForPresenceOfText(driver,"MP4 Video");

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OoyalaSkinListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "MP4 Video");
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

            Thread.sleep(5000);

            // playing video again.
            po.getPlay(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);

        }
        catch(Exception e)
        {
            System.out.println("SkinPlaybackMP4Video throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"SkinPlaybackMP4Video");
            Assert.assertTrue(false, "This will fail!");
        }
    }

   @org.testng.annotations.Test
    public void SkinPlaybackHLSVideo() throws Exception{
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

            po.waitForPresenceOfText(driver,"HLS Video");

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OoyalaSkinListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "HLS Video");
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

            Thread.sleep(5000);

            // playing video again.
            po.getPlay(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);

        }
        catch(Exception e)
        {
            System.out.println("SkinPlaybackHLSVideo throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"SkinPlaybackHLSVideo");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void SkinPlaybackVASTAdPreRollTest() throws Exception{
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

            po.waitForPresenceOfText(driver,"VAST2 Ad Pre-roll");

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OoyalaSkinListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "VAST2 Ad Pre-roll");
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
            po.seek_video(driver, 20);

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
            System.out.println("SkinPlaybackVASTAdPreRollTest throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"SkinPlaybackVASTAdPreRollTest");
            Assert.assertTrue(false, "This will fail!");
        }
    }

       @org.testng.annotations.Test
    public void SkinPlaybackVASTADMidRollTest() throws Exception{
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

            po.waitForPresenceOfText(driver,"VAST2 Ad Mid-roll");

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OoyalaSkinListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "VAST2 Ad Mid-roll");
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

            //Play Started
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //Thread sleep time is equivalent to the length of the half of the video
            Thread.sleep(11000);

            //Ad Started Verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            Thread.sleep(2000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);

            //pause the video
            Thread.sleep(3000);
            //pausing the video.
            po.pauseVideo(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            Thread.sleep(1000);

            //seeking the video
            po.seek_video(driver, 10);

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
            System.out.println("SkinPlaybackVASTADMidRollTest throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"SkinPlaybackVASTADMidRollTest");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void SkinPlaybackVASTADPostRollTest() throws Exception{
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

            po.waitForPresenceOfText(driver,"VAST2 Ad Post-roll");

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OoyalaSkinListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "VAST2 Ad Post-roll");
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

            //Play Started
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //pause the video
            Thread.sleep(3000);
            //pausing the video.
            po.pauseVideo(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            Thread.sleep(1000);

            //seeking the video
            po.seek_video(driver, 20);

            ev.verifyEvent("seekCompleted", " Video seeking completed ", 70000);

            Thread.sleep(5000);

            // playing video again.
            po.getPlay(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

            //Ad Started Verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            Thread.sleep(5000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);
        }
        catch(Exception e)
        {
            System.out.println("SkinPlaybackVASTADPostRollTest throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"SkinPlaybackVASTADPostRollTest");
            Assert.assertTrue(false, "This will fail!");
        }
    }

       @org.testng.annotations.Test
    public void SkinPlaybackOoyalaAdPreRollTest() throws Exception{
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
            System.out.println("Ooyala Skin List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnTextScrollTo(driver, "Ooyala Ad Pre-roll");
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
            Thread.sleep(3000);
            //pausing the video.
            po.pauseVideo(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            Thread.sleep(1000);

            //seeking the video
            po.seek_video(driver, 10);

            ev.verifyEvent("seekCompleted", " Video seeking completed ", 70000);

            Thread.sleep(5000);

            // playing video again.
            po.getPlay(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        }
        catch(Exception e)
        {
            System.out.println("SkinPlaybackOoyalaAdPreRollTest throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"SkinPlaybackOoyalaAdPreRollTest");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void SkinPlaybackOoyalaADMidRollTest() throws Exception{
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
            System.out.println("Ooyala Skin List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnTextScrollTo(driver, "Ooyala Ad Mid-roll");
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

            //Play Started
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //Thread sleep time is equivalent to the length of the half of the video
            Thread.sleep(11000);

            //Ad Started Verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            Thread.sleep(2000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);

            //pause the video
            Thread.sleep(3000);
            //pausing the video.
            po.pauseVideo(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            Thread.sleep(1000);

            //seeking the video
            po.seek_video(driver, 10);

            ev.verifyEvent("seekCompleted", " Video seeking completed ", 70000);

            Thread.sleep(5000);

            // playing video again.
            po.getPlay(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        }
        catch(Exception e)
        {
            System.out.println("SkinPlaybackOoyalaADMidRollTest throws Exception "+e);
             e.printStackTrace();             Assert.assertTrue(false, "This will fail!");
            ScreenshotDevice.screenshot(driver,"SkinPlaybackOoyalaADMidRollTest");
        }
    }

    @org.testng.annotations.Test
    public void SkinPlaybackOoyalaADPostRollTest() throws Exception{
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
            System.out.println("Ooyala Skin List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnTextScrollTo(driver, "Ooyala Ad Post-roll");
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

            //Play Started
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //pause the video
            Thread.sleep(3000);
            //pausing the video.
            po.pauseVideo(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            Thread.sleep(1000);

            //seeking the video
            po.seek_video(driver, 10);

            ev.verifyEvent("seekCompleted", " Video seeking completed ", 70000);

            Thread.sleep(5000);

            // playing video again.
            po.getPlay(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

            //Ad Started Verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 90000);

            Thread.sleep(5000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 90000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 95000);
        }
        catch(Exception e)
        {
            System.out.println("SkinPlaybackOoyalaADPostRollTest throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"SkinPlaybackOoyalaADPostRollTest");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void SkinPlaybackMultiAdCombinationTest() throws Exception{
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
            System.out.println("Ooyala Skin List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnTextScrollTo(driver, "Multi Ad combination");
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

            Thread.sleep(2000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);

            //Play Started
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //pause the video
            Thread.sleep(3000);
            //pausing the video.
            po.pauseVideo(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            Thread.sleep(1000);

            //seeking the video
            po.seek_video(driver, 10);

            ev.verifyEvent("seekCompleted", " Video seeking completed ", 70000);

            Thread.sleep(5000);

            // playing video again.
            po.getPlay(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);


            //Ad Started Verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 90000);

            Thread.sleep(2000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 90000);

            //Thread sleep time is equivalent to the length of the half of the video
            Thread.sleep(11000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 95000);
        }
        catch(Exception e)
        {
            System.out.println("SkinPlaybackMultiAdCombinationTest throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"SkinPlaybackMultiAdCombinationTest");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void SkinPlaybackVASTAdWrapperTest() throws Exception{
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

            po.waitForPresenceOfText(driver,"VAST2 Ad Wrapper");

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OoyalaSkinListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "VAST2 Ad Wrapper");
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

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);
        }
        catch(Exception e)
        {
            System.out.println("SkinPlaybackVASTAdWrapperTest throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"SkinPlaybackVASTAdWrapperTest");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    // TODO Unstable
    //@org.testng.annotations.Test
    public void SkinPlaybackVODwithCCTest() throws Exception{
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

            po.waitForPresenceOfText(driver,"VOD with CCs");

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OoyalaSkinListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "VOD with CCs");
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

            // Click on the web area so that player screen shows up
            WebElement viewarea = driver.findElementByClassName("android.view.View");
            viewarea.click();

            Thread.sleep(2000);

            // Tap coordinates to Pause
            driver.tap(1,locPlayButon[0],locPlayButon[1],2);
            Thread.sleep(2000);

            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);

            // Pause the running of the test for a brief amount of time
            Thread.sleep(3000);


            po.SeekOoyalaSkin(driver,0,200);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 30000);
            Thread.sleep(3000);

            //Clicking on CC option showing button in Ooyala Skin
            po.clickBasedOnText(driver,"f");

            //wait for the presence of CC option
            po.waitForPresenceOfText(driver,"k");

            //Click on CC option
            po.clickBasedOnText(driver,"k");

            // Wait for CC options for different languages
            po.waitForPresenceOfText(driver,"CLOSE CAPTION PREVIEW");

            //click on Japanese Language
            po.clickBasedOnText(driver,"ja");

            // Sleep for some time after the language selection
            Thread.sleep(3000);

            // closed Captions event verification
            //ev.verifyEvent("closedCaptionsLanguageChanged", " CC of the Video Was Changed ", 30000);

            // Close the CC window
            po.clickBasedOnText(driver,"e");

            // Click on the web area so that player screen shows up
            viewarea.click();

            Thread.sleep(2000);


            // Tap coordinates again to play
            driver.tap(1,locPlayButon[0],locPlayButon[1],2);
            Thread.sleep(2000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);

        }
        catch(Exception e)
        {
            System.out.println("SkinPlaybackVODwithCCTest throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"SkinPlaybackVODwithCCTest");
            Assert.assertTrue(false, "This will fail!");
        }
    }


}
