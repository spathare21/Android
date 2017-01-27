package testpackage.tests.exoPlayerSampleApp;

import org.apache.log4j.Logger;
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
    final static Logger logger = Logger.getLogger(BasicPlayBackBasicTest2.class);
    exoPlayerSampleApp exoplayersampleapp = new exoPlayerSampleApp();

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
    public void multi_Ad() throws Exception{
        try {
            // wait till home screen of ExoPlayerApp is opened
            exoplayersampleapp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoplayersampleapp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
            // Write to console activity name of home screen app
            System.out.println("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //click on basic playback
            exoplayersampleapp.clickBasedOnText(driver, "Basic Playback");
            //display the current activity to console
            System.out.println(" Print current activity name"+driver.currentActivity());
             //wait for the assets to load properly
            exoplayersampleapp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Write to console activity name of home screen app
            System.out.println("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video Multi Ad Combination
            exoplayersampleapp.clickBasedOnTextScrollTo(driver, "Multi Ad Combination");
            //verify if player was loaded
            exoplayersampleapp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            exoplayersampleapp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //wait for the presence of start screen
            exoplayersampleapp.waitForPresenceOfText(driver,"h");
            //Clicking on Play button
            exoplayersampleapp.getPlay(driver);
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
            exoplayersampleapp.screentapping(driver);
            //pause the video in normal screen
            exoplayersampleapp.pausingVideo(driver);
            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 40000);
            //seek video in normal screen
            exoplayersampleapp.seek_video(driver,300);
            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 50000);
            //handling loading spinner
            exoplayersampleapp.loadingSpinner(driver);
            //check whether play button is visible or not
            exoplayersampleapp.checkPlayButton(driver);
            //resume playback in normal screen
            exoplayersampleapp.getPlay(driver);
            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 60000);
            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);
            // Ad playback has been completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 250000);
        }
        catch(Exception e){
            logger.error("Multi_Ad Combination throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"Multi_Ad Combination");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void vast_3Podded() throws Exception{
        try {
            // wait till home screen of ExoPlayerApp is opened
            exoplayersampleapp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoplayersampleapp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
            // Write to console activity name of home screen app
            System.out.println("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //click on basic playback
            exoplayersampleapp.clickBasedOnText(driver, "Basic Playback");
            //display the current activity to console
            System.out.println(" Print current activity name"+driver.currentActivity());
            //wait for the assets to load properly
            exoplayersampleapp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Write to console activity name of home screen app
            System.out.println("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video VAST 3.0 2 Podded Ad
            exoplayersampleapp.clickBasedOnTextScrollTo(driver, "VAST 3.0 2 Podded Ad");
            //verify if player was loaded
            exoplayersampleapp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            exoplayersampleapp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //wait for the presence of start screen
            exoplayersampleapp.waitForPresenceOfText(driver,"h");
            //Clicking on Play button
            exoplayersampleapp.getPlay(driver);
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
            exoplayersampleapp.screentapping(driver);
            //pause the video in normal screen
            exoplayersampleapp.pausingVideo(driver);
            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 40000);
            //seek video in normal screen
            exoplayersampleapp.seek_video(driver,600);
            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 50000);
            //handling loading spinner
            exoplayersampleapp.loadingSpinner(driver);
            //check whether play button is visible or not
            exoplayersampleapp.checkPlayButton(driver);
            //resume playback in normal screen
            exoplayersampleapp.getPlay(driver);
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 60000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 120000);
        }
        catch(Exception e)
        {
            logger.error("VAST_3Podded throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"VAST_3Podded");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void vast_AdWithIcon() throws Exception{
        try {
            // wait till home screen of ExoPlayerApp is opened
            exoplayersampleapp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoplayersampleapp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
            // Write to console activity name of home screen app
            System.out.println("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //click on basic playback
            exoplayersampleapp.clickBasedOnText(driver, "Basic Playback");
            //display the current activity to console
            System.out.println(" Print current activity name"+driver.currentActivity());
            //wait for the assets to load properly
            exoplayersampleapp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Write to console activity name of home screen app
            System.out.println("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video VAST 3.0 Ad With Icon
            exoplayersampleapp.clickBasedOnTextScrollTo(driver, "VAST 3.0 Ad With Icon");
            Thread.sleep(5000);
            //verify if player was loaded
            exoplayersampleapp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            exoplayersampleapp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //wait for the presence of start screen
            exoplayersampleapp.waitForPresenceOfText(driver,"h");
            //Clicking on Play button
            exoplayersampleapp.getPlay(driver);
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
            exoplayersampleapp.screentapping(driver);
            //pause the video in normal screen
            exoplayersampleapp.pausingVideo(driver);
            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 60000);
            //seek video in normal screen
            exoplayersampleapp.seek_video(driver,600);
            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);
            //handling loading spinner
            exoplayersampleapp.loadingSpinner(driver);
            //resume playback in normal screen
            exoplayersampleapp.getPlay(driver);
            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 230000);
        }
        catch(Exception e)
        {
            logger.error("VAST_AdWithIcon throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"VAST_AdWithIcon");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void vast_Skippable_Ad_Long() throws Exception{
        try {
            // wait till home screen of ExoPlayerApp is opened
            exoplayersampleapp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoplayersampleapp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
            // Write to console activity name of home screen app
            System.out.println("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //click on basic playback
            exoplayersampleapp.clickBasedOnText(driver, "Basic Playback");
            //display the current activity to console
            System.out.println(" Print current activity name"+driver.currentActivity());
            //wait for the assets to load properly
            exoplayersampleapp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Write to console activity name of home screen app
            System.out.println("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video VAST 3.0 Skippable Ad Long
            exoplayersampleapp.clickBasedOnTextScrollTo(driver, "VAST 3.0 Skippable Ad Long");
            //verify if player was loaded
            exoplayersampleapp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            exoplayersampleapp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //wait for the presence of start screen
            exoplayersampleapp.waitForPresenceOfText(driver,"h");
            //Clicking on Play button
            exoplayersampleapp.getPlay(driver);
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
            exoplayersampleapp.screentapping(driver);
            //pause the video in normal screen
            exoplayersampleapp.pausingVideo(driver);
            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 60000);
            //seek video in normal screen
            exoplayersampleapp.seek_video(driver,600);
            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);
            //handling loading spinner
            exoplayersampleapp.loadingSpinner(driver);
            //check whether play button is visible or not
            exoplayersampleapp.checkPlayButton(driver);
            //resume playback in normal screen
            exoplayersampleapp.getPlay(driver);
            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 70000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 230000);
        }
        catch(Exception e){
            logger.error("VAST_Skippable_Ad_Long throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"VAST_Skippable_Ad_Long");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void vast_AD_With_NewEvents() throws Exception{
        try {
            // wait till home screen of ExoPlayerApp is opened
            exoplayersampleapp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoplayersampleapp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
            // Write to console activity name of home screen app
            System.out.println("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //click on basic playback
            exoplayersampleapp.clickBasedOnText(driver, "Basic Playback");
            //display the current activity to console
            System.out.println(" Print current activity name"+driver.currentActivity());
            //wait for the assets to load properly
            exoplayersampleapp.waitForPresenceOfText(driver,"4:3 Aspect Ratio");
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoplayersampleapp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Write to console activity name of home screen app
            System.out.println("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video HLS,MP4 etc .
            exoplayersampleapp.clickBasedOnTextScrollTo(driver, "VAST 3.0 Ad With All Of The New Events");
            //verify if player was loaded
            exoplayersampleapp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            exoplayersampleapp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //wait for the presence of start screen
            exoplayersampleapp.waitForPresenceOfText(driver, "h");
            //Clicking on Play button
            exoplayersampleapp.getPlay(driver);
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
            exoplayersampleapp.screentapping(driver);
            //pause the video in normal screen
            exoplayersampleapp.pausingVideo(driver);
            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 60000);
            //seek video in normal screen
            exoplayersampleapp.seek_video(driver,600);
            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);
            //handling loading spinner
            exoplayersampleapp.loadingSpinner(driver);
            //check whether play button is visible or not
            exoplayersampleapp.checkPlayButton(driver);
            //resume playback in normal screen
            exoplayersampleapp.getPlay(driver);
            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 70000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 230000);
        }
        catch(Exception e){
            logger.error("VAST_AD_With_NewEvents throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"VAST_AD_With_NewEvents");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void vast_AD_Wrapper() throws Exception{
        try {
            // wait till home screen of ExoPlayerApp is opened
            exoplayersampleapp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoplayersampleapp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
            // Write to console activity name of home screen app
            System.out.println("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //click on basic playback
            exoplayersampleapp.clickBasedOnText(driver, "Basic Playback");
            //display the current activity to console
            System.out.println(" Print current activity name"+driver.currentActivity());
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoplayersampleapp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Write to console activity name of home screen app
            System.out.println("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video HLS,MP4 etc .
            exoplayersampleapp.clickBasedOnTextScrollTo(driver, "VAST 3.0 Ad Wrapper");
            //verify if player was loaded
            exoplayersampleapp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            exoplayersampleapp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //wait for the presence of start screen
            exoplayersampleapp.waitForPresenceOfText(driver,"h");
            //Clicking on Play button in Ooyala Skin
            exoplayersampleapp.getPlay(driver);
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
            exoplayersampleapp.screentapping(driver);
            //pause the video in normal screen
            exoplayersampleapp.pausingVideo(driver);
            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 60000);
            //seek video in normal screen
            exoplayersampleapp.seek_video(driver,920);
            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);
            //handling loading spinner
            exoplayersampleapp.loadingSpinner(driver);
            //check whether play button is visible or not
            exoplayersampleapp.checkPlayButton(driver);
            //resume playback in normal screen
            exoplayersampleapp.getPlay(driver);
            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 70000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 330000);
        }
        catch(Exception e){
            logger.error("VAST_AD_Wrapper throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"VAST_AD_Wrapper");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void vamp_VastAD_PreMidPost() throws Exception{
        try {
            // wait till home screen of ExoPlayerApp is opened
            exoplayersampleapp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoplayersampleapp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
            // Write to console activity name of home screen app
            System.out.println("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //click on basic playback
            exoplayersampleapp.clickBasedOnText(driver, "Basic Playback");
            //display the current activity to console
            System.out.println(" Print current activity name"+driver.currentActivity());
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoplayersampleapp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Write to console activity name of home screen app
            System.out.println("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video VMAP PreMidPost Single
            exoplayersampleapp.clickBasedOnTextScrollTo(driver, "VMAP PreMidPost Single");
            //verify if player was loaded
            exoplayersampleapp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            exoplayersampleapp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //wait for the presence of start screen
            exoplayersampleapp.waitForPresenceOfText(driver,"h");
            //Clicking on Play button
            exoplayersampleapp.getPlay(driver);
            //creating object of EventVerification Class
            EventVerification ev = new EventVerification();
            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 20000);
            //Ad Completed event Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);
            //Play Started event verification
            ev.verifyEvent("playStarted", " Video Started to Play ", 20000);
            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 40000);
            //Ad Completed event Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 90000);
            Thread.sleep(2000);
            //tapping on video screen
            exoplayersampleapp.screentapping(driver);
            //pause the video in normal screen
            exoplayersampleapp.pausingVideo(driver);
            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 60000);
            //seek video in normal screen
            exoplayersampleapp.seek_video(driver,400);
            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);
            //handling loading spinner
            exoplayersampleapp.loadingSpinner(driver);
            //check whether play button is visible or not
            exoplayersampleapp.checkPlayButton(driver);
            //resume playback in normal screen
            exoplayersampleapp.getPlay(driver);
            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 70000);
            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);
            //Ad Completed event Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 90000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 130000);
        }
        catch(Exception e){
            logger.error("VAMP_VastAD_PreMidPost throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"VAMP_VastAD_PreMidPost");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void vamp_PreMidVASTAdData() throws Exception {
        try {
            // wait till home screen of ExoPlayerApp is opened
            exoplayersampleapp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoplayersampleapp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
            // Write to console activity name of home screen app
            System.out.println("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //click on basic playback
            exoplayersampleapp.clickBasedOnText(driver, "Basic Playback");
            //display the current activity to console
            System.out.println(" Print current activity name"+driver.currentActivity());
            //wait for the assets to load properly
            exoplayersampleapp.waitForPresenceOfText(driver,"4:3 Aspect Ratio");
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoplayersampleapp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Write to console activity name of home screen app
            System.out.println("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video VMAP PreMid VASTAdDATA
            exoplayersampleapp.clickBasedOnTextScrollTo(driver, "VMAP PreMid VASTAdDATA");
            //verify if player was loaded
            exoplayersampleapp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            exoplayersampleapp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //wait for the presence of start screen
            exoplayersampleapp.waitForPresenceOfText(driver, "h");
            //Clicking on Play button
            exoplayersampleapp.getPlay(driver);
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
            exoplayersampleapp.screentapping(driver);
            //pause the video in normal screen
            exoplayersampleapp.pausingVideo(driver);
            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 60000);
            //seek video in normal screen
            exoplayersampleapp.seek_video(driver,970);
            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);
            //handling loading spinner
            exoplayersampleapp.loadingSpinner(driver);
            //check whether play button is visible or not
            exoplayersampleapp.checkPlayButton(driver);
            //resume playback in normal screen
            exoplayersampleapp.getPlay(driver);
            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 70000);
            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 80000);
            ////Ad Completed event Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 120000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 230000);
        } catch (Exception e){
            logger.error(" vamp_PreMidVASTAdData throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"vamp_PreMidVASTAdData");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void clearHLSMain() throws Exception{
        try {
            // wait till home screen of ExoPlayerApp is opened
            exoplayersampleapp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoplayersampleapp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
            // Write to console activity name of home screen app
            System.out.println("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //click on basic playback
            exoplayersampleapp.clickBasedOnText(driver, "Basic Playback");
            //display the current activity to console
            System.out.println(" Print current activity name"+driver.currentActivity());
            //wait for the assets to load properly
            exoplayersampleapp.waitForPresenceOfText(driver,"4:3 Aspect Ratio");
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoplayersampleapp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Write to console activity name of home screen app
            System.out.println("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video Clear HLS Main Profile
            exoplayersampleapp.clickBasedOnText(driver, "Clear HLS Main Profile");
            //verify if player was loaded
            exoplayersampleapp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            exoplayersampleapp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //wait for the presence of start screen
            exoplayersampleapp.waitForPresenceOfText(driver,"h");
            //Clicking on Play button
            exoplayersampleapp.getPlay(driver);
            //creating object of EventVerification Class
            EventVerification ev = new EventVerification();
            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);
            //tapping on video screen
            exoplayersampleapp.screentapping(driver);
            //pause the video in normal screen
            exoplayersampleapp.pausingVideo(driver);
            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);
            //seek video in normal screen
            exoplayersampleapp.seek_video(driver,960);
            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);
            //handling loading spinner
            exoplayersampleapp.loadingSpinner(driver);
            //check whether play button is visible or not
            exoplayersampleapp.checkPlayButton(driver);
            //resume playback in normal screen
            exoplayersampleapp.getPlay(driver);
            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 230000);
        }
        catch(Exception e){
            logger.error("clearHLSMain throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"clearHLSMain");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void widevineBase() throws Exception{
        try {
            // wait till home screen of ExoPlayerApp is opened
            exoplayersampleapp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoplayersampleapp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
            // Write to console activity name of home screen app
            System.out.println("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //click on basic playback
            exoplayersampleapp.clickBasedOnText(driver, "Basic Playback");
            //display the current activity to console
            System.out.println(" Print current activity name"+driver.currentActivity());
            //wait for the assets to load properly
            exoplayersampleapp.waitForPresenceOfText(driver,"4:3 Aspect Ratio");
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoplayersampleapp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Write to console activity name of home screen app
            System.out.println("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video HLS,MP4 etc .
            exoplayersampleapp.clickBasedOnText(driver, "Clear HLS Baseline");
            //verify if player was loaded
            exoplayersampleapp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            exoplayersampleapp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //wait for the presence of start screen
            exoplayersampleapp.waitForPresenceOfText(driver,"h");
            //Clicking on Play button
            exoplayersampleapp.getPlay(driver);
            //creating object of EventVerification Class
            EventVerification ev = new EventVerification();
            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);
            //tapping on video screen
            exoplayersampleapp.screentapping(driver);
            //pause the video in normal screen
            exoplayersampleapp.pausingVideo(driver);
            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);
            //seek video in normal screen
            exoplayersampleapp.seek_video(driver,940);
            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);
            //handling loading spinner
            exoplayersampleapp.loadingSpinner(driver);
            //resume playback in normal screen
            exoplayersampleapp.getPlay(driver);
            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 200000);
        }
        catch(Exception e){
            logger.error(" widevineBase throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"widevineBase");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    //@Test
    public void VOD_CC() throws Exception{
        try {

            // Creating an Object of FreeWheelSampleApp class
            exoPlayerSampleApp exoplayersampleapp = new exoPlayerSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            exoplayersampleapp.waitForAppHomeScreen(driver);


            // Assert if current activity is indeed equal to the activity name of app home screen
            exoplayersampleapp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
            // Write to console activity name of home screen app
            System.out.println("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);


            exoplayersampleapp.clickBasedOnText(driver, "Basic Playback");
            Thread.sleep(2000);

            System.out.println(" Print current activity name"+driver.currentActivity());
            if(driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")){
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);

            }

            exoplayersampleapp.waitForPresenceOfText(driver,"4:3 Aspect Ratio");
            // Assert if current activity is indeed equal to the activity name of app home screen
            exoplayersampleapp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Write to console activity name of home screen app
            System.out.println("Ooyala Skin - Basic PlayBack List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video HLS,MP4 etc .
            exoplayersampleapp.clickBasedOnTextScrollTo(driver, "VOD with CCs");
            Thread.sleep(2000);


            //verify if player was loaded
            exoplayersampleapp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            exoplayersampleapp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            exoplayersampleapp.waitForPresenceOfText(driver,"h");


            //Clicking on Play button in Ooyala Skin
            exoplayersampleapp.clickBasedOnText(driver,"h");

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