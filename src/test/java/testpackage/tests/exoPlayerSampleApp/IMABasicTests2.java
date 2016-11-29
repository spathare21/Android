package testpackage.tests.exoPlayerSampleApp;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import testpackage.pageobjects.exoPlayerSampleApp;
import testpackage.utils.*;
import java.io.IOException;
import java.util.Properties;

public class IMABasicTests2 extends EventLogTest {
    LoadPropertyValues prop = new LoadPropertyValues();
    Properties p;

    @BeforeClass
    public void beforeTest() throws Exception {
        // closing all recent app from background.
        CloserecentApps.closeApps();
        // Get Property Values
        p = prop.loadProperty("exoPlayerSampleApp.properties");
        //setup and initialize android driver
        SetUpAndroidDriver setUpdriver = new SetUpAndroidDriver();
        driver = setUpdriver.setUpandReturnAndroidDriver(p.getProperty("udid"), p.getProperty("appDir"), p.getProperty("appValue"), p.getProperty("platformName"), p.getProperty("platformVersion"), p.getProperty("appPackage"), p.getProperty("appActivity"));
        Thread.sleep(2000);
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        //push log file to the device
        PushLogFileToDevice logpush=new PushLogFileToDevice();
        logpush.pushLogFile();
        if(driver.currentActivity()!= "com.ooyala.sample.complete.MainExoPlayerActivity") {
            driver.startActivity("com.ooyala.sample.ExoPlayerSampleApp","com.ooyala.sample.complete.MainExoPlayerActivity");
        }
        // Get Property Values
        Properties p=prop.loadProperty();
        System.out.println(" Screen Mode "+ p.getProperty("ScreenMode"));
    }
    @AfterClass
    public void afterTest() throws InterruptedException, IOException {
        //close the app
        driver.closeApp();
        //quit the android driver
        driver.quit();
        //get the propert values
        p = prop.loadProperty();
        String prop = p.getProperty("appPackage");
        //unistall the app from the device
        Appuninstall.uninstall(prop);
    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws Exception {
        // remove or delete events log file from the device
        RemoveEventsLogFile.removeEventsFileLog();
        Thread.sleep(10000);
    }

    @Test
    public void imaNonAdRulesPreroll() throws Exception{
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

            //click on Google IMA Integration
            po.clickBasedOnText(driver, "Google IMA Integration");
            Thread.sleep(2000);

            //display the current activity to console
            System.out.println(" Print current activity name"+driver.currentActivity());

            if(driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")){
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);
            }

            //wait for the assets to load properly
            po.waitForPresenceOfText(driver,"IMA Non Ad-Rules Preroll");

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");

            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin - Google IMA List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video IMA Non Ad-Rules Preroll.
            po.clickBasedOnTextScrollTo(driver, "IMA Ad-Rules Preroll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredIMAPlayerActivity");

            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //wait for the presence of start screen
            po.waitForPresenceOfText(driver,"h");

            //Clicking on Play button
            po.getPlay(driver);

            //ad Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            // Ad playback completed event verification.
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);

            //tapping on the screen
            po.screentapping(driver);

            //pause the video in normal screen
            po.pausingVideo(driver);

            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            //seek the video in normal screen
            po.seek_video(driver,100);

            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);

            //handle the loading spinner
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
            ScreenshotDevice.screenshot(driver,"IMA Non Ad-Rules Preroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void imaNonAdRulesMidroll() throws Exception{
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

            //click on Google IMA Integration
            po.clickBasedOnTextScrollTo(driver, "Google IMA Integration");
            Thread.sleep(2000);

            System.out.println(" Print current activity name"+driver.currentActivity());
            if(driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")){
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);
            }

            //wait for the assets to load properly
            po.waitForPresenceOfText(driver,"IMA Non Ad-Rules Midroll");

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");

            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin - Google IMA List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video as IMA Non Ad-Rules Midroll.
            po.clickBasedOnTextScrollTo(driver, "IMA Non Ad-Rules Midroll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredIMAPlayerActivity");

            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //wait for the presence of start screen
            po.waitForPresenceOfText(driver,"h");

            //Clicking on Play button
            po.getPlay(driver);

            //create object of EventVerification
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);

            //tapping on the screen
            po.screentapping(driver);

            //pause the video in normal screen
            po.pausingVideo(driver);

            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 40000);

            //seek video in normal screen
            po.seek_video(driver,100);

            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 50000);

            //handle the loading spinner
            po.loadingSpinner(driver);

            //resume the video playback in normal screen
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
            ScreenshotDevice.screenshot(driver,"IMA Non Ad-Rules Midroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void imaNonAdRulesPostoll() throws Exception{
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

            //click on Google IMA Integration
            po.clickBasedOnTextScrollTo(driver, "Google IMA Integration");
            Thread.sleep(2000);

            System.out.println(" Print current activity name"+driver.currentActivity());
            if(driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")){
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);
            }

            //wait for the assets to load properly
            po.waitForPresenceOfText(driver,"IMA Non Ad-Rules Postroll");

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");

            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin - Google IMA List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video as IMA Non Ad-Rules Postroll
            po.clickBasedOnTextScrollTo(driver, "IMA Non Ad-Rules Postroll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredIMAPlayerActivity");

            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //wait for the presence of start screen
            po.waitForPresenceOfText(driver,"h");

            //Clicking on Play button in Ooyala Skin
            po.getPlay(driver);

            //Creating object of EventVerification class
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);

            //tapping on the video screen
            po.screentapping(driver);

            //pause the video in normal screen
            po.pausingVideo(driver);

            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 40000);

            //seek video in normal screen
            po.seek_video(driver,100);

            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 50000);

            //handle the loading spinner
            po.loadingSpinner(driver);

            //resume the video playback in normal screen
            po.getPlay(driver);

            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 60000);

            //ad started event verification
            ev.verifyEvent("adStarted", "Ad Started to Play", 60000);

            // Ad playback has been completed.
            ev.verifyEvent("adCompleted", "Ad Playback Completed", 70000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        }
        catch(Exception e){
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"IMA Non Ad-Rules Postroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void imaNonAdRulesPreMidMidPost() throws Exception{
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

            //click on Google IMA Integration
            po.clickBasedOnTextScrollTo(driver, "Google IMA Integration");
            Thread.sleep(2000);

            System.out.println(" Print current activity name"+driver.currentActivity());
            if(driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")){
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);
            }

            //wait for the assets to load properly
            po.waitForPresenceOfText(driver,"IMA Non Ad-Rules Preroll");

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");

            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin - Google IMA List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video IMA Non Ad-Rules Pre-Mid-Mid-post
            po.clickBasedOnTextScrollTo(driver, "IMA Non Ad-Rules Pre-Mid-Mid-Post");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredIMAPlayerActivity");

            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //wait for start screen to appear
            po.waitForPresenceOfText(driver,"h");

            //Clicking on Play button
            po.getPlay(driver);

            //Creating object of EventVerification Class
            EventVerification ev = new EventVerification();

            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            // Ad playback has been completed.
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 40000);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 40000);
            Thread.sleep(2000);

            //tapping on the video screen
            po.screentapping(driver);

            //pausing the video in normal screen
            po.pausingVideo(driver);

            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 40000);

            //seek video in normal screen
            po.seek_video(driver,70);

            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 50000);

            //resume the video playback in normal screen
            po.getPlay(driver);

            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 60000);

            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            // Ad playback has been completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            // Ad playback has been completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

            //ad started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            // Ad playback has been completed.
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        }
        catch(Exception e){
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"IMA Non Ad-Rules Pre-Mid-Mid-Post");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    //@Test
    public void noAds() throws Exception{
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

            //click on Google IMA Integration
            po.clickBasedOnTextScrollTo(driver, "Google IMA Integration");
            Thread.sleep(2000);

            //display the current activity to console
            System.out.println(" Print current activity name"+driver.currentActivity());

            if(driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")){
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);
            }

            //wait for the assets to load properly
            po.waitForPresenceOfText(driver,"No Ads");

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");

            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin - Google IMA List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video No Ads.
            po.clickBasedOnTextScrollTo(driver, "No Ads");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredIMAPlayerActivity");

            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //wait for the presence of start screen
            po.waitForPresenceOfText(driver,"h");

            //Clicking on Play button
            po.getPlay(driver);

            //ad Started Verification
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);

            //tapping on the screen
            po.screentapping(driver);

            //pause the video in normal screen
            po.pausingVideo(driver);

            //pause event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            //seek the video in normal screen
            po.seek_video(driver,1090);

            //seek completed event verification
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);

            //handle the loading spinner
            po.loadingSpinner(driver);

            //resume the playback in normal screen
            po.getPlay(driver);

            //playing event verification
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 190000);
        }
        catch(Exception e){
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"No Ads");
            Assert.assertTrue(false, "This will fail!");
        }
    }
}
