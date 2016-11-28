package testpackage.tests.exoPlayerSampleApp;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import testpackage.pageobjects.exoPlayerSampleApp;
import testpackage.utils.*;
import java.io.IOException;
import java.util.Properties;

public class BasicPlayBackBasicTest2 extends EventLogTest {

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
        // remove events log file from the device
        RemoveEventsLogFile.removeEventsFileLog();
        Thread.sleep(5000);
    }

    @Test
    public void Multi_Ad() throws Exception{
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
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video Multi Ad Combination
            po.clickBasedOnTextScrollTo(driver, "Multi Ad Combination");
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
            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);
            // Ad playback has been completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);
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
            ScreenshotDevice.screenshot(driver,"Multi_Ad Combination");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void VAST_3Podded() throws Exception{
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
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video VAST 3.0 2 Podded Ad
            po.clickBasedOnTextScrollTo(driver, "VAST 3.0 2 Podded Ad");
            Thread.sleep(5000);
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
            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 20000);
            //Ad Completed event Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);
            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 35000);
            //Ad Completed event Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 45000);
            //Play Started event verification
            ev.verifyEvent("playStarted", " Video Started to Play ", 45000);
            Thread.sleep(2000);
            //tapping on video screen
            po.screentapping(driver);
            //pause the video in normal screen
            po.pausingVideo(driver);
            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 40000);
            //seek video in normal screen
            po.seek_video(driver,400);
            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 50000);
            //handling loading spinner
            po.loadingSpinner(driver);
            //resume playback in normal screen
            po.getPlay(driver);
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 60000);
            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);
            //Ad Completed event Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 200000);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VAST_3Podded");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void VAST_AdWithIcon() throws Exception{
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
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video VAST 3.0 Ad With Icon
            po.clickBasedOnTextScrollTo(driver, "VAST 3.0 Ad With Icon");
            Thread.sleep(5000);
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
            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 20000);
            //Ad Completed event Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 40000);
            //Play Started event verification
            ev.verifyEvent("playStarted", " Video Started to Play ", 55000);
            Thread.sleep(2000);
            //tapping on video screen
            po.screentapping(driver);
            //pause the video in normal screen
            po.pausingVideo(driver);
            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 60000);
            //seek video in normal screen
            po.seek_video(driver,400);
            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);
            //handling loading spinner
            po.loadingSpinner(driver);
            //resume playback in normal screen
            po.getPlay(driver);
            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 200000);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VAST_AdWithIcon");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void VAST_Skippable_Ad_Long() throws Exception{
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
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video VAST 3.0 Skippable Ad Long
            po.clickBasedOnTextScrollTo(driver, "VAST 3.0 Skippable Ad Long");
            Thread.sleep(5000);
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
            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 15000);
            //Ad Completed event Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 50000);
            //Play Started event verification
            ev.verifyEvent("playStarted", " Video Started to Play ", 50000);
            Thread.sleep(2000);
            //tapping on video screen
            po.screentapping(driver);
            //pause the video in normal screen
            po.pausingVideo(driver);
            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 60000);
            //seek video in normal screen
            po.seek_video(driver,150);
            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);
            //handling loading spinner
            po.loadingSpinner(driver);
            //resume playback in normal screen
            po.getPlay(driver);
            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 70000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 200000);
        }
        catch(Exception e){
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VAST_Skippable_Ad_Long");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void VAST_AD_With_NewEvents() throws Exception{
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
            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnTextScrollTo(driver, "VAST 3.0 Ad With All Of The New Events");
            Thread.sleep(2000);
            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //wait for the presence of start screen
            po.waitForPresenceOfText(driver, "h");
            //Clicking on Play button
            po.getPlay(driver);
            //creating object of EventVerification Class
            EventVerification ev = new EventVerification();
            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 15000);
            //Ad Completed event Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 40000);
            //Play Started event verification
            ev.verifyEvent("playStarted", " Video Started to Play ", 50000);
            Thread.sleep(2000);
            //tapping on video screen
            po.screentapping(driver);
            //pause the video in normal screen
            po.pausingVideo(driver);
            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 60000);
            //seek video in normal screen
            po.seek_video(driver,150);
            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);
            //handling loading spinner
            po.loadingSpinner(driver);
            //resume playback in normal screen
            po.getPlay(driver);
            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 70000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 200000);
        }
        catch(Exception e){
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VAST_AD_With_NewEvents");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void VAST_AD_Wrapper() throws Exception{
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
            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnTextScrollTo(driver, "VAST 3.0 Ad Wrapper");
            Thread.sleep(5000);
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
            ev.verifyEvent("adStarted", " Ad Started to Play ", 15000);
            //Ad Completed event Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 40000);
            //Play Started event verification
            ev.verifyEvent("playStarted", " Video Started to Play ", 50000);
            Thread.sleep(2000);
            //tapping on video screen
            po.screentapping(driver);
            //pause the video in normal screen
            po.pausingVideo(driver);
            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 60000);
            //seek video in normal screen
            po.seek_video(driver,940);
            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);
            //handling loading spinner
            po.loadingSpinner(driver);
            //resume playback in normal screen
            po.getPlay(driver);
            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 70000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 200000);
        }
        catch(Exception e){
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VAST_AD_Wrapper");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void VAMP_VastAD_PreMidPost() throws Exception{
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
            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video VMAP PreMidPost Single
            po.clickBasedOnTextScrollTo(driver, "VMAP PreMidPost Single");
            Thread.sleep(5000);
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
            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 20000);
            //Ad Completed event Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);
            //Play Started event verification
            ev.verifyEvent("playStarted", " Video Started to Play ", 20000);
            Thread.sleep(2000);
            //tapping on video screen
            po.screentapping(driver);
            //pause the video in normal screen
            po.pausingVideo(driver);
            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 60000);
            //seek video in normal screen
            po.seek_video(driver,100);
            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);
            //handling loading spinner
            po.loadingSpinner(driver);
            //resume playback in normal screen
            po.getPlay(driver);
            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 70000);
            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 40000);
            //Ad Completed event Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);
            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 40000);
            //Ad Completed event Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 200000);
        }
        catch(Exception e){
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VAMP_VastAD_PreMidPost");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void VMAP_PreMidVASTAdData() throws Exception {
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
            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video VMAP PreMid VASTAdDATA
            po.clickBasedOnTextScrollTo(driver, "VMAP PreMid VASTAdDATA");
            Thread.sleep(5000);
            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //wait for the presence of start screen
            po.waitForPresenceOfText(driver, "h");
            //Clicking on Play button
            po.getPlay(driver);
            //creating object of EventVerification Class
            EventVerification ev = new EventVerification();
            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 20000);
            //Ad Completed event Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);
            //Play Started event verification
            ev.verifyEvent("playStarted", " Video Started to Play ", 80000);
            Thread.sleep(2000);
            //tapping on video screen
            po.screentapping(driver);
            //pause the video in normal screen
            po.pausingVideo(driver);
            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 60000);
            //seek video in normal screen
            po.seek_video(driver,940);
            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);
            //handling loading spinner
            po.loadingSpinner(driver);
            //resume playback in normal screen
            po.getPlay(driver);
            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 70000);
            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 80000);
            ////Ad Completed event Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 120000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 200000);
        } catch (Exception e){
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VMAP_PreMidPostSingle");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void clearHLSMain() throws Exception{
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
            // Select one of the video Clear HLS Main Profile
            po.clickBasedOnText(driver, "Clear HLS Main Profile");
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
            po.seek_video(driver,940);
            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);
            //handling loading spinner
            po.loadingSpinner(driver);
            //resume playback in normal screen
            po.getPlay(driver);
            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        }
        catch(Exception e){
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"clearHLSMain");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void widevineBase() throws Exception{
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
            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Clear HLS Baseline");
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
            po.seek_video(driver,940);
            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);
            //handling loading spinner
            po.loadingSpinner(driver);
            //resume playback in normal screen
            po.getPlay(driver);
            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        }
        catch(Exception e){
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"widevineBase");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    // TODO Asset has been removed.
    //Test
    public void VAST_Podded_Preroll_skippable() throws Exception{
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

            //po.waitForPresenceOfText(driver,"VAST 3.0 2 Podded Ad");
            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnTextScrollTo(driver, "VAST 3.0 Podded Preroll with Skippable Ad");
            Thread.sleep(5000);


            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver,"h");

            //Clicking on Play button in Ooyala Skin
            po.clickBasedOnText(driver,"h");

            //Ad Started Verification
            EventVerification ev = new EventVerification();

            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 50000);


            //Play Started
            ev.verifyEvent("playStarted", " Video Started to Play ", 55000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 200000);

        }
        catch(Exception e)
        {
            System.out.println("VAST_Podded_Preroll_skippable throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VAST_Podded_Preroll_skippable");
            Assert.assertTrue(false, "This will fail!");
        }

    }

    //@Test
    public void VAST_Skippable_Ad() throws Exception{
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

            //po.waitForPresenceOfText(driver,"VAST 3.0 2 Podded Ad");
            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnTextScrollTo(driver, "VAST 3.0 Skippable Ad");
            Thread.sleep(5000);


            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver,"h");


            //Clicking on Play button in Ooyala Skin
            po.clickBasedOnText(driver,"h");

            //Ad Started Verification
            EventVerification ev = new EventVerification();



            //Play Started
            ev.verifyEvent("playStarted", " Video Started to Play ", 20000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 200000);

        }
        catch(Exception e)
        {
            System.out.println("VAST_Skippable_Ad throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VAST_Skippable_Ad");
            Assert.assertTrue(false, "This will fail!");
        }

    }

    //@Test
    public void VOD_CC() throws Exception{
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
            po.clickBasedOnTextScrollTo(driver, "VOD with CCs");
            Thread.sleep(2000);


            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver,"h");


            //Clicking on Play button in Ooyala Skin
            po.clickBasedOnText(driver,"h");

            //Ad Started Verification
            EventVerification ev = new EventVerification();

            //Play Started
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 80000);

        }
        catch(Exception e)
        {
            System.out.println("VOD_CC throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VOD_CC");
            Assert.assertTrue(false, "This will fail!");
        }

    }
}
