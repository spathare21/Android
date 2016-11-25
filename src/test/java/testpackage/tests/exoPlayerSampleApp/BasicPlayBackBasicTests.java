package testpackage.tests.exoPlayerSampleApp;

import io.appium.java_client.android.AndroidDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import testpackage.pageobjects.exoPlayerSampleApp;
import testpackage.utils.*;

import java.io.IOException;
import java.util.Properties;

public class BasicPlayBackBasicTests extends EventLogTest{
    LoadPropertyValues prop = new LoadPropertyValues();
    Properties p;
    @BeforeClass
    public void beforeTest() throws Exception {
        // closing all recent app from background.
        CloserecentApps.closeApps();
        // Get Property Values
        p = prop.loadProperty("exoPlayerSampleApp.properties");
        //setup and update android driver
        SetUpAndroidDriver setUpdriver = new SetUpAndroidDriver();
        driver = setUpdriver.setUpandReturnAndroidDriver(p.getProperty("udid"), p.getProperty("appDir"), p.getProperty("appValue"), p.getProperty("platformName"), p.getProperty("platformVersion"), p.getProperty("appPackage"), p.getProperty("appActivity"));
        Thread.sleep(2000);
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        //push the log file to the device
        PushLogFileToDevice logpush=new PushLogFileToDevice();
        logpush.pushLogFile();
        if(driver.currentActivity()!= "com.ooyala.sample.complete.MainExoPlayerActivity") {
            driver.startActivity("com.ooyala.sample.ExoPlayerSampleApp","com.ooyala.sample.complete.MainExoPlayerActivity");
        }
        // Get Property Values
        p=prop.loadProperty();
        //display the screen mode to the console
        System.out.println(" Screen Mode "+ p.getProperty("ScreenMode"));
    }

    @AfterClass
    public void afterTest() throws InterruptedException, IOException {
        //close the app
        driver.closeApp();
        //quit the android driver
        driver.quit();
        //load the property values
        p = prop.loadProperty();
        String prop = p.getProperty("appPackage");
        //uninstall the app
        Appuninstall.uninstall(prop);
    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws Exception {
        // remove or delete the log file from the device
        RemoveEventsLogFile.removeEventsFileLog();
        Thread.sleep(10000);
    }

    @Test
    public void AspectRatio() throws Exception{
        try {

            // Creating an Object of ExoPlayerSampleApp class
            exoPlayerSampleApp po = new exoPlayerSampleApp();

            // wait till home screen of ExoPlayerApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");

            // Wrire to console activity name of home screen app
            System.out.println("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            //click on basic playback
            po.clickBasedOnText(driver, "Basic Playback");
            Thread.sleep(2000);

            //display the current activity to console
            System.out.println(" Print current activity name"+driver.currentActivity());

            if(driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")){
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);
            }

            //wait for the assets to load properly
            po.waitForPresenceOfText(driver,"4:3 Aspect Ratio");

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");

            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video 4:3 Aspect Ratio
            po.clickBasedOnText(driver, "4:3 Aspect Ratio");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");

            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //wait for the presence of start screen
            po.waitForPresenceOfText(driver,"h");

            //Clicking on Play button
            po.getPlay(driver);

            //creating object of EventVerification Class
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);

            //tapping on video screen
            po.screentapping(driver);

            //pause the video in normal screen
            po.pausingVideo(driver);

            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            //seek video in normal screen
            po.seek_video(driver,100);

            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);

            //handling the loading spinner
            po.loadingSpinner(driver);

            //resume the playback in normal screen
            po.getPlay(driver);

            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        }
        catch(Exception e){
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"4:3 Aspect Ratio");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void MP4() throws Exception{
        try {

            // Creating an Object of ExoPlayerSampleApp class
            exoPlayerSampleApp po = new exoPlayerSampleApp();

            // wait till home screen of ExoPlayerApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");

            // Wrire to console activity name of home screen app
            System.out.println("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            //click on basic playback
            po.clickBasedOnText(driver, "Basic Playback");
            Thread.sleep(2000);

            //display the current activity to console
            System.out.println(" Print current activity name"+driver.currentActivity());

            if(driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")){
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);
            }

            //wait for the assets to load properly
            po.waitForPresenceOfText(driver,"MP4 Video");

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");

            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video MP4 Video.
            po.clickBasedOnTextScrollTo(driver, "MP4 Video");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");

            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //wait for the presence of start screen
            po.waitForPresenceOfText(driver,"h");

            //Clicking on Play button in Ooyala Skin
            po.getPlay(driver);

            //creating object of EventVerification Class
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);

            //tapping on video screen
            po.screentapping(driver);

            //pause the video in normal screen
            po.pausingVideo(driver);

            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            //seek video in normal screen
            po.seek_video(driver,100);

            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);

            //handling loading spinner
            po.loadingSpinner(driver);

            //resume playback in normal screen
            po.getPlay(driver);

            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING", "Video resumed", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"MP4");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void HLS() throws Exception{
        try {

            // Creating an Object of ExoPlayerSampleApp class
            exoPlayerSampleApp po = new exoPlayerSampleApp();

            // wait till home screen of ExoPlayerApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");

            // Wrire to console activity name of home screen app
            System.out.println("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            //click on basic playback
            po.clickBasedOnText(driver, "Basic Playback");
            Thread.sleep(2000);

            //display the current activity to console
            System.out.println(" Print current activity name"+driver.currentActivity());

            if(driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")){
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);
            }

            //wait for the assets to load properly
            po.waitForPresenceOfText(driver,"HLS Video");

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");

            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video HLS Video.
            po.clickBasedOnTextScrollTo(driver, "HLS Video");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");

            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //wait for the presence of start screen
            po.waitForPresenceOfText(driver,"h");

            //Clicking on Play button
            po.clickBasedOnText(driver,"h");

            //Clicking on Play button in Ooyala Skin
            po.getPlay(driver);

            //creating object of EventVerification Class
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);

            //tapping on video screen
            po.screentapping(driver);

            //pause the video in normal screen
            po.pausingVideo(driver);

            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            //seek video in normal screen
            po.seek_video(driver,100);

            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);

            //handling loading spinner
            po.loadingSpinner(driver);

            //resume playback in normal screen
            po.getPlay(driver);

            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING", "Video resumed", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        }
        catch(Exception e){
            System.out.println("HLS throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"HLS");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void encrypted_HLS() throws Exception{
        try {

            // Creating an Object of ExoPlayerSampleApp class
            exoPlayerSampleApp po = new exoPlayerSampleApp();

            // wait till home screen of ExoPlayerApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");

            // Wrire to console activity name of home screen app
            System.out.println("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            //click on basic playback
            po.clickBasedOnText(driver, "Basic Playback");
            Thread.sleep(2000);

            //display the current activity to console
            System.out.println(" Print current activity name"+driver.currentActivity());

            if(driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")){
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);
            }

            //wait for the assets to load properly
            po.waitForPresenceOfText(driver,"Ooyala Encrypted HLS");

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnTextScrollTo(driver, "Ooyala Encrypted HLS");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");

            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //wait for the presence of start screen
            po.waitForPresenceOfText(driver,"h");

            //Clicking on Play button in Ooyala Skin
            po.clickBasedOnText(driver,"h");

            //Clicking on Play button in Ooyala Skin
            po.getPlay(driver);

            //creating object of EventVerification Class
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);

            //tapping on video screen
            po.screentapping(driver);

            //pause the video in normal screen
            po.pausingVideo(driver);

            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            //seek video in normal screen
            po.seek_video(driver,100);

            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);

            //handling loading spinner
            po.loadingSpinner(driver);

            //resume playback in normal screen
            po.getPlay(driver);

            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING", "Video resumed", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        }
        catch(Exception e){
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"encrypted_HLS");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void VAST2_Preroll() throws Exception{
        try {

            // Creating an Object of ExoPlayerSampleApp class
            exoPlayerSampleApp po = new exoPlayerSampleApp();

            // wait till home screen of ExoPlayerApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");

            // Wrire to console activity name of home screen app
            System.out.println("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            //click on basic playback
            po.clickBasedOnText(driver, "Basic Playback");
            Thread.sleep(2000);

            //display the current activity to console
            System.out.println(" Print current activity name"+driver.currentActivity());

            if(driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")){
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);
            }

            //wait for the assets to load properly
            po.waitForPresenceOfText(driver,"VAST2 Ad Pre-roll");

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");

            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video VAST2 Ad Pre-roll
            po.clickBasedOnTextScrollTo(driver, "VAST2 Ad Pre-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");

            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //wait for the presence of start screen
            po.waitForPresenceOfText(driver,"h");

            //Clicking on Play button in Ooyala Skin
            po.getPlay(driver);

            //creating object of EventVerification Class
            EventVerification ev = new EventVerification();

            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            // Ad playback has been completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);

            //tapping on video screen
            po.screentapping(driver);

            //pause the video in normal screen
            po.pausingVideo(driver);

            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            //seek video in normal screen
            po.seek_video(driver,100);

            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);

            //handling loading spinner
            po.loadingSpinner(driver);

            //resume playback in normal screen
            po.getPlay(driver);

            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 70000);
        }
        catch(Exception e){
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VAST2_Preroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void VAST2_Midroll() throws Exception{
        try {

            // Creating an Object of ExoPlayerSampleApp class
            exoPlayerSampleApp po = new exoPlayerSampleApp();

            // wait till home screen of ExoPlayerApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");

            // Wrire to console activity name of home screen app
            System.out.println("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            //click on basic playback
            po.clickBasedOnText(driver, "Basic Playback");
            Thread.sleep(2000);

            //display the current activity to console
            System.out.println(" Print current activity name"+driver.currentActivity());

            if(driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")){
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);
            }

            //wait for the assets to load properly
            po.waitForPresenceOfText(driver,"VAST2 Ad Mid-roll");

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");

            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video VAST2 Ad Mid-roll
            po.clickBasedOnTextScrollTo(driver, "VAST2 Ad Mid-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");

            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //wait for the presence of start screen
            po.waitForPresenceOfText(driver,"h");

            //Clicking on Play button in Ooyala Skin
            po.getPlay(driver);

            //creating object of EventVerification Class
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);

            //tapping on video screen
            po.screentapping(driver);

            //pause the video in normal screen
            po.pausingVideo(driver);

            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 40000);

            //seek video in normal screen
            po.seek_video(driver,100);

            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 50000);

            //handling loading spinner
            po.loadingSpinner(driver);

            //resume playback in normal screen
            po.getPlay(driver);

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
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VAST2_Midroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void VAST2_Postroll() throws Exception{
        try {

            // Creating an Object of ExoPlayerSampleApp class
            exoPlayerSampleApp po = new exoPlayerSampleApp();

            // wait till home screen of ExoPlayerApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");

            // Wrire to console activity name of home screen app
            System.out.println("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            //click on basic playback
            po.clickBasedOnText(driver, "Basic Playback");
            Thread.sleep(2000);

            //display the current activity to console
            System.out.println(" Print current activity name"+driver.currentActivity());

            if(driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")){
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);
            }

            //wait for the assets to load properly
            po.waitForPresenceOfText(driver,"VAST2 Ad Post-roll");

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");

            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video VAST2 Ad Post-roll
            po.clickBasedOnTextScrollTo(driver, "VAST2 Ad Post-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");

            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //wait for the presence of start screen
            po.waitForPresenceOfText(driver,"h");

            //Clicking on Play button
            po.getPlay(driver);

            //creating object of EventVerification Class
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);

            //tapping on video screen
            po.screentapping(driver);

            //pause the video in normal screen
            po.pausingVideo(driver);

            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 40000);

            //seek video in normal screen
            po.seek_video(driver,100);

            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 50000);

            //handling loading spinner
            po.loadingSpinner(driver);

            //resume playback in normal screen
            po.getPlay(driver);

            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 60000);

            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);
            // Ad playback has been completed.
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        }
        catch(Exception e){
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VAST2_Postroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void VAST_Wrapper() throws Exception{
        try {

            // Creating an Object of ExoPlayerSampleApp class
            exoPlayerSampleApp po = new exoPlayerSampleApp();

            // wait till home screen of ExoPlayerApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");

            // Wrire to console activity name of home screen app
            System.out.println("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            //click on basic playback
            po.clickBasedOnText(driver, "Basic Playback");
            Thread.sleep(2000);

            //display the current activity to console
            System.out.println(" Print current activity name"+driver.currentActivity());

            if(driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")){
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);
            }

            //wait for the assets to load properly
            po.waitForPresenceOfText(driver,"VAST2 Ad Wrapper");

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");

            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video VAST2 Ad Wrapper
            po.clickBasedOnTextScrollTo(driver, "VAST2 Ad Wrapper");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");

            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //wait for the presence of start screen
            po.waitForPresenceOfText(driver,"h");

            //Clicking on Play button
            po.clickBasedOnText(driver,"h");

            //Clicking on Play button
            po.getPlay(driver);

            //creating object of EventVerification Class
            EventVerification ev = new EventVerification();

            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            // Ad playback has been completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);

            //tapping on video screen
            po.screentapping(driver);

            //pause the video in normal screen
            po.pausingVideo(driver);

            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            //seek video in normal screen
            po.seek_video(driver,100);

            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);

            //handling loading spinner
            po.loadingSpinner(driver);

            //resume playback in normal screen
            po.getPlay(driver);

            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 70000);
        }
        catch(Exception e){
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VAST_Wrapper");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void ooyalaAd_Preroll() throws Exception{
        try {

            // Creating an Object of ExoPlayerSampleApp class
            exoPlayerSampleApp po = new exoPlayerSampleApp();

            // wait till home screen of ExoPlayerApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");

            // Wrire to console activity name of home screen app
            System.out.println("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            //click on basic playback
            po.clickBasedOnText(driver, "Basic Playback");
            Thread.sleep(2000);

            //display the current activity to console
            System.out.println(" Print current activity name"+driver.currentActivity());

            if(driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")){
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);
            }

            //wait for the assets to load properly
            po.waitForPresenceOfText(driver,"Ooyala Ad Pre-roll");

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");

            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video Ooyala Ad Pre-roll
            po.clickBasedOnTextScrollTo(driver, "Ooyala Ad Pre-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");

            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //wait for the presence of start screen
            po.waitForPresenceOfText(driver,"h");

            //Clicking on Play button in Ooyala Skin
            po.getPlay(driver);

            //creating object of EventVerification Class
            EventVerification ev = new EventVerification();

            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            // Ad playback has been completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);

            //tapping on video screen
            po.screentapping(driver);

            //pause the video in normal screen
            po.pausingVideo(driver);

            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            //seek video in normal screen
            po.seek_video(driver,100);

            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);

            //handling loading spinner
            po.loadingSpinner(driver);

            //resume playback in normal screen
            po.getPlay(driver);

            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 70000);
        }
        catch(Exception e){
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"ooyalaAd_Preroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void ooyalaAd_Midroll() throws Exception{
        try {

            // Creating an Object of ExoPlayerSampleApp class
            exoPlayerSampleApp po = new exoPlayerSampleApp();

            // wait till home screen of ExoPlayerApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");

            // Wrire to console activity name of home screen app
            System.out.println("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            //click on basic playback
            po.clickBasedOnText(driver, "Basic Playback");
            Thread.sleep(2000);

            //display the current activity to console
            System.out.println(" Print current activity name"+driver.currentActivity());

            if(driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")){
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);
            }

            //wait for the assets to load properly
            po.waitForPresenceOfText(driver,"Ooyala Ad Mid-roll");

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video Ooyala Ad Mid-roll
            po.clickBasedOnTextScrollTo(driver, "Ooyala Ad Mid-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");

            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //wait for the presence of start screen
            po.waitForPresenceOfText(driver,"h");

            //Clicking on Play button
            po.getPlay(driver);

            //creating object of EventVerification Class
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);

            //tapping on video screen
            po.screentapping(driver);

            //pause the video in normal screen
            po.pausingVideo(driver);

            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 40000);

            //seek video in normal screen
            po.seek_video(driver,100);

            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 50000);

            //handling loading spinner
            po.loadingSpinner(driver);

            //resume playback in normal screen
            po.getPlay(driver);

            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 60000);

            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);
            // Ad playback has been completed.
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        }
        catch(Exception e){
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"ooyalaAd_Midroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void ooyalaAd_Postroll() throws Exception{
        try {

            // Creating an Object of FreeWheelSampleApp class
            exoPlayerSampleApp po = new exoPlayerSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);


            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
            // Wrire to console activity name of home screen app
            System.out.println("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);


            po.clickBasedOnText(driver, "Basic Playback");
            Thread.sleep(2000);

            System.out.println(" Print current activity name"+driver.currentActivity());
            if(driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")){
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);

            }

            po.waitForPresenceOfText(driver,"4:3 Aspect Ratio");
            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

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


            //Clicking on Play button in Ooyala Skin
            po.getPlay(driver);
            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);

            Thread.sleep(2000);

            po.screentapping(driver);

            po.pausingVideo(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 40000);

            po.seek_video(driver,100);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 50000);
            po.loadingSpinner(driver);
            po.getPlay(driver);
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 60000);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);
            // Ad playback has been completed.
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);

        }
        catch(Exception e)
        {
            System.out.println("ooyalaAd_Postroll Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"ooyalaAd_Postroll");
            Assert.assertTrue(false, "This will fail!");
        }

    }

    @org.testng.annotations.Test
    public void widevineDASH() throws Exception{
        try {

            // Creating an Object of FreeWheelSampleApp class
            exoPlayerSampleApp po = new exoPlayerSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);


            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
            // Wrire to console activity name of home screen app
            System.out.println("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);


            po.clickBasedOnText(driver, "Basic Playback");
            Thread.sleep(2000);

            System.out.println(" Print current activity name"+driver.currentActivity());
            if(driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")){
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);
            }

            po.waitForPresenceOfText(driver,"4:3 Aspect Ratio");

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Widevine DASH");
            Thread.sleep(2000);


            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver,"h");


            //Clicking on Play button in Ooyala Skin
            po.getPlay(driver);
            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);

            Thread.sleep(2000);

            po.screentapping(driver);

            po.pausingVideo(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            po.seek_video(driver,940);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);
            po.loadingSpinner(driver);
            po.getPlay(driver);
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);

        }
        catch(Exception e)
        {
            System.out.println("widevineDASH throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"widevineDASH");
            Assert.assertTrue(false, "This will fail!");
        }

    }

    @org.testng.annotations.Test
    public void clearHLSHigh() throws Exception{
        try {

            // Creating an Object of FreeWheelSampleApp class
            exoPlayerSampleApp po = new exoPlayerSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);


            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
            // Wrire to console activity name of home screen app
            System.out.println("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);


            po.clickBasedOnText(driver, "Basic Playback");
            Thread.sleep(2000);

            System.out.println(" Print current activity name"+driver.currentActivity());
            if(driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")){
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);
            }

            po.waitForPresenceOfText(driver,"4:3 Aspect Ratio");

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

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


            //Clicking on Play button in Ooyala Skin
            po.getPlay(driver);
            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);

            Thread.sleep(2000);

            po.screentapping(driver);

            po.pausingVideo(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            po.seek_video(driver,940);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);
            po.loadingSpinner(driver);
            po.getPlay(driver);
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);

        }
        catch(Exception e)
        {
            System.out.println("clearHLSHigh throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"clearHLSHigh");
            Assert.assertTrue(false, "This will fail!");
        }

    }

}
