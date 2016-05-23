package testpackage.tests.exoPlayerSampleApp;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import testpackage.pageobjects.exoPlayerSampleApp;
import testpackage.utils.*;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by sumit on 03/05/16.
 */
public class DeepTestsFW {
    private static AndroidDriver driver;

    @BeforeClass

    public void beforeTest() throws Exception {

        // closing all recent app from background.
        CloserecentApps.closeApps();
        System.out.println("BeforeTest \n");

        System.out.println(System.getProperty("user.dir"));
        // Get Property Values
        LoadPropertyValues prop = new LoadPropertyValues();
        Properties p=prop.loadProperty("exoPlayerSampleApp.properties");

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
        if(driver.currentActivity()!= "com.ooyala.sample.complete.MainExoPlayerActivity") {
            driver.startActivity("com.ooyala.sample.ExoPlayerSampleApp","com.ooyala.sample.complete.MainExoPlayerActivity");
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
    public void FreeWheelIntegrationPreRoll() throws Exception{

        try {
            // Creating an Object of FreeWheelSampleApp class
            exoPlayerSampleApp po = new exoPlayerSampleApp();
            // wait till home screen of exo sample app is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Exo Player Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            po.clickBasedOnText(driver, "Freewheel Integration");
            Thread.sleep(2000);

            // Assert if current activity is Freewheel list activity
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Freewheel Preroll");
            Thread.sleep(2000);

            System.out.println("<<<<<<<<<<<<<<<<<Clicked on Freewhell Preroll>>>>>>>>>>>>>>>>>>");


            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver,"h");

            //Getting Play button coordinates
            po.getPlay(driver);
            Thread.sleep(1000);

            //Clicking on Play button in Ooyala Skin
            po.clickBasedOnText(driver,"h");

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            Thread.sleep(5000);

            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 30000);
            Thread.sleep(1000);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 35000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 70000);

            po.replayVideo(driver);
            Thread.sleep(1000);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video replay start ", 70000);
            Thread.sleep(2000);

            //Tapping on screen to pause the Video
            po.screentap(driver);
            Thread.sleep(1000);

            po.pauseVideo(driver);
            // verifing video get paused
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            Thread.sleep(5000);

            po.getBackFromRecentApp(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

            Thread.sleep(2000);

            po.powerKeyClick(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

            Thread.sleep(5000);

            //clicking on view area
            WebElement viewarea = driver.findElementByClassName("android.view.View");
            viewarea.click();
            Thread.sleep(1000);

            // clicking on more button
            po.moreButton(driver);

            Thread.sleep(2000);

            // clicking on Share button
            po.shareAsset(driver);

            System.out.println("clicked on share button");

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: SUSPENDED", " Sharing the asset ", 70000);
            po.shareOnGmail(driver);
            Thread.sleep(1000);

            ev.verifyEvent("stateChanged - state: READY", " Mail sent, Back to SDK ", 70000);
            Thread.sleep(2000);

            System.out.println("clicking on discovery");
            po.clickOnDiscovery(driver);

            Thread.sleep(2000);

            po.clickOnCloseButton(driver);

            Thread.sleep(2000);

            System.out.println("clicking on CC");
            po.clickOnCC(driver);

            Thread.sleep(2000);

            po.clickOnCloseButton(driver);
            Thread.sleep(2000);
            po.clickOnCloseButton(driver);

            Thread.sleep(5000);
            po.screentap(driver);
            Thread.sleep(1000);
//            po.seek_video(driver);

//            Thread.sleep(5000);

            po.playVideo(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video start ", 70000);

            po.getBackFromRecentApp(driver);
            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 80000);

            Thread.sleep(1000);

            po.powerKeyClick(driver);
            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 80000);
            Thread.sleep(1000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);

        }
        catch(Exception e)
        {
            System.out.println(" Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }

    }

  @org.testng.annotations.Test
    public void FreeWheelIntegrationMidroll() throws Exception {

        try {
            // Creating an Object of FreeWheelSampleApp class
            exoPlayerSampleApp po = new exoPlayerSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);


            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Exo Player Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            po.clickBasedOnText(driver, "Freewheel Integration");
            Thread.sleep(2000);

            // Assert if current activity is Freewheel list activity
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Freewheel Midroll");
            Thread.sleep(2000);

            System.out.println("<<<<<<<<<<<<<<<<<<<<clicked on Freewheel Midroll>>>>>>>>>>>>>>>>>>>>");


            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver, "h");

            //Getting Play button coordinates
            po.getPlay(driver);
            Thread.sleep(1000);

            //Clicking on Play button in Ooyala Skin
            po.clickBasedOnText(driver, "h");


            //Play Started Verification
            EventVerification ev = new EventVerification();
            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(5000);

            //Wait for Ad to start and verify the adStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 49000);

            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 49000);
            Thread.sleep(5000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 50000);

            po.replayVideo(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video replay start ", 70000);
            Thread.sleep(2000);

            //Tapping on screen to pause the Video
            po.screentap(driver);
            Thread.sleep(1000);

            po.pauseVideo(driver);
            // verifing video get paused
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            Thread.sleep(5000);

            po.getBackFromRecentApp(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

            Thread.sleep(2000);

            po.powerKeyClick(driver);
            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

            Thread.sleep(5000);

            //clicking on view area
            WebElement viewarea = driver.findElementByClassName("android.view.View");
            viewarea.click();
            Thread.sleep(1000);

            // clicking on more button
            po.moreButton(driver);

            Thread.sleep(2000);

            // clicking on Share button
            po.shareAsset(driver);

            System.out.println("clicked on share button");

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: SUSPENDED", " Sharing the asset ", 70000);
            po.shareOnGmail(driver);
            Thread.sleep(1000);

            ev.verifyEvent("stateChanged - state: READY", " Mail sent, Back to SDK ", 70000);
            Thread.sleep(2000);

            System.out.println("clicking on discovery");
            po.clickOnDiscovery(driver);

            Thread.sleep(2000);

            po.clickOnCloseButton(driver);

            Thread.sleep(2000);

            System.out.println("clicking on CC");
            po.clickOnCC(driver);

            Thread.sleep(2000);

            po.clickOnCloseButton(driver);
            Thread.sleep(2000);
            po.clickOnCloseButton(driver);

            Thread.sleep(5000);
            po.screentap(driver);
            Thread.sleep(1000);
//            po.seek_video(driver);

//            Thread.sleep(5000);

            po.playVideo(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video start ", 70000);
            Thread.sleep(2000);

            po.getBackFromRecentApp(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 80000);
            Thread.sleep(1000);

            po.powerKeyClick(driver);
            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 90000);
            Thread.sleep(1000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);

        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }

    }

   @org.testng.annotations.Test
    public void FreeWheelIntegrationPostroll() throws Exception {

        try {
            // Creating an Object of FreeWheelSampleApp class
            exoPlayerSampleApp po = new exoPlayerSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);


            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Exo Player Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            po.clickBasedOnText(driver, "Freewheel Integration");
            Thread.sleep(2000);

            // Assert if current activity is Freewheel list activity
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Freewheel Postroll");
            Thread.sleep(2000);

            System.out.println("<<<<<<<<<<<<<<<<clicked on Freewheel Postroll>>>>>>>>>>>>>>>>>>");


            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver, "h");

            //Getting Play button coordinates
            po.getPlay(driver);
            Thread.sleep(1000);

            //Clicking on Play button in Ooyala Skin
            po.clickBasedOnText(driver, "h");

            //Play Started Verification
            EventVerification ev = new EventVerification();
            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(10000);

            //Wait for Ad to start and verify the adStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 49000);
            Thread.sleep(5000);

            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 49000);
            Thread.sleep(1000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 50000);
            Thread.sleep(2000);

            po.replayVideo(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video replay start ", 70000);
            Thread.sleep(5000);

            //Tapping on screen to pause the Video
            po.screentap(driver);
            Thread.sleep(2000);

            po.pauseVideo(driver);
            // verifing video get paused
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            Thread.sleep(5000);

            po.getBackFromRecentApp(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

            Thread.sleep(2000);

            po.powerKeyClick(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

            Thread.sleep(5000);

            //clicking on view area
            WebElement viewarea = driver.findElementByClassName("android.view.View");
            viewarea.click();
            Thread.sleep(1000);

            // clicking on more button
            po.moreButton(driver);

            Thread.sleep(2000);

            // clicking on Share button
            po.shareAsset(driver);

            System.out.println("clicked on share button");

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: SUSPENDED", " Sharing the asset ", 70000);
            po.shareOnGmail(driver);
            Thread.sleep(1000);

            ev.verifyEvent("stateChanged - state: READY", " Mail sent, Back to SDK ", 70000);
            Thread.sleep(2000);

            System.out.println("clicking on discovery");
            po.clickOnDiscovery(driver);

            Thread.sleep(2000);

            po.clickOnCloseButton(driver);

            Thread.sleep(2000);

            System.out.println("clicking on CC");
            po.clickOnCC(driver);

            Thread.sleep(2000);

            po.clickOnCloseButton(driver);
            Thread.sleep(2000);
            po.clickOnCloseButton(driver);

            Thread.sleep(5000);
            po.screentap(driver);
            Thread.sleep(1000);
//            po.seek_video(driver);

//            Thread.sleep(5000);

            po.playVideo(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video start ", 70000);
            Thread.sleep(1000);

            po.getBackFromRecentApp(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 60000);
            Thread.sleep(1000);

            po.powerKeyClick(driver);
            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 60000);
            Thread.sleep(1000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);

        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }

    }

    @org.testng.annotations.Test
    public void FreeWheelIntegrationPreMidPostroll() throws Exception {

        try {
            // Creating an Object of FreeWheelSampleApp class
            exoPlayerSampleApp po = new exoPlayerSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);


            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Exo Player Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            po.clickBasedOnText(driver, "Freewheel Integration");
            Thread.sleep(2000);

            // Assert if current activity is Freewheel list activity
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Freewheel PreMidPost");
            Thread.sleep(2000);

            System.out.println("<<<<<<clicked on PreMidPost>>>>>>>>>>>>");


            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver, "h");

            //Getting Play button coordinates
            po.getPlay(driver);
            Thread.sleep(1000);

            //Clicking on Play button in Ooyala Skin
            po.clickBasedOnText(driver, "h");


            //Play Started Verification
            EventVerification ev = new EventVerification();
            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 35000);
            Thread.sleep(5000);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 35000);
            Thread.sleep(5000);

            //Wait for Ad to start and verify the adStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 49000);
            Thread.sleep(5000);

            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 49000);
            Thread.sleep(1000);

            ev.verifyEvent("playStarted", " Video Started Play ", 49000);
            Thread.sleep(5000);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);
            Thread.sleep(5000);
            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 50000);
            Thread.sleep(1000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 60000);

            po.replayVideo(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video replay start ", 70000);
            Thread.sleep(2000);

            //Tapping on screen to pause the Video
            po.screentap(driver);
            Thread.sleep(1000);

            po.pauseVideo(driver);
            // verifing video get paused
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            Thread.sleep(5000);

            po.getBackFromRecentApp(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

            Thread.sleep(2000);

            po.powerKeyClick(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

            Thread.sleep(5000);

            //clicking on view area
            WebElement viewarea = driver.findElementByClassName("android.view.View");
            viewarea.click();
            Thread.sleep(1000);

            // clicking on more button
            po.moreButton(driver);

            Thread.sleep(2000);

            // clicking on Share button
            po.shareAsset(driver);
            System.out.println("clicked on share button");
            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: SUSPENDED", " Sharing the asset ", 70000);
            po.shareOnGmail(driver);
            Thread.sleep(1000);

            ev.verifyEvent("stateChanged - state: READY", " Mail sent, Back to SDK ", 70000);
            Thread.sleep(2000);

            System.out.println("clicking on discovery");
            po.clickOnDiscovery(driver);

            Thread.sleep(2000);

            po.clickOnCloseButton(driver);

            Thread.sleep(2000);

            System.out.println("clicking on CC");
            po.clickOnCC(driver);

            Thread.sleep(2000);

            po.clickOnCloseButton(driver);
            Thread.sleep(2000);
            po.clickOnCloseButton(driver);

            Thread.sleep(5000);
            po.screentap(driver);
            Thread.sleep(1000);
//            po.seek_video(driver);

//            Thread.sleep(5000);

            po.playVideo(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video start ", 70000);
            Thread.sleep(1000);

            po.getBackFromRecentApp(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 60000);
            Thread.sleep(1000);

            po.powerKeyClick(driver);
            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 60000);
            Thread.sleep(1000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);

        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }

    }

    @org.testng.annotations.Test
    public void FreeWheelIntegrationOverlay() throws Exception {

        try {
            // Creating an Object of FreeWheelSampleApp class
            exoPlayerSampleApp po = new exoPlayerSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);


            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Exo Player Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            po.clickBasedOnText(driver, "Freewheel Integration");
            Thread.sleep(2000);

            // Assert if current activity is Freewheel list activity
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Freewheel Overlay");
            Thread.sleep(2000);

            System.out.println("<<<<<<<<<<<<<<<Clicked on Freewheel Overlay>>>>>>>>>>>>");


            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver, "h");

            //Getting Play button coordinates
            po.getPlay(driver);
            Thread.sleep(1000);

            //Clicking on Play button in Ooyala Skin
            po.clickBasedOnText(driver, "h");

            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(5000);

            po.verifyOverlay(driver);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 30000);


        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }

    }

    @org.testng.annotations.Test
    public void FreeWheelIntegrationMultiMidroll() throws Exception {

        try {
            // Creating an Object of FreeWheelSampleApp class
            exoPlayerSampleApp po = new exoPlayerSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);


            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Exo Player Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            po.clickBasedOnText(driver, "Freewheel Integration");
            Thread.sleep(2000);

            // Assert if current activity is Freewheel list activity
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Freewheel Multi Midroll");
            Thread.sleep(2000);

            System.out.println("<<<<<<Clicked on Freewheel MultiMidroll>>>>>>>>>>>");

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver, "h");

            //Getting Play button coordinates
            po.getPlay(driver);
            Thread.sleep(1000);

            //Clicking on Play button in Ooyala Skin
            po.clickBasedOnText(driver, "h");

            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);

            //Wait for Ad to start and verify the adStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 40000);
            Thread.sleep(5000);

            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 40000);
            Thread.sleep(5000);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);
            Thread.sleep(5000);
            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 50000);
            Thread.sleep(1000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 50000);
            Thread.sleep(2000);

            po.replayVideo(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video replay start ", 70000);
            Thread.sleep(2000);

            //Tapping on screen to pause the Video
            po.screentap(driver);
            Thread.sleep(1000);

            po.pauseVideo(driver);
            // verifing video get paused
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            Thread.sleep(5000);

            po.getBackFromRecentApp(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

            Thread.sleep(2000);

            po.powerKeyClick(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

            Thread.sleep(5000);

            //clicking on view area
            WebElement viewarea = driver.findElementByClassName("android.view.View");
            viewarea.click();
            Thread.sleep(1000);

            // clicking on more button
            po.moreButton(driver);

            Thread.sleep(2000);

            // clicking on Share button
            po.shareAsset(driver);

            System.out.println("clicked on share button");

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: SUSPENDED", " Sharing the asset ", 70000);
            po.shareOnGmail(driver);
            Thread.sleep(1000);

            ev.verifyEvent("stateChanged - state: READY", " Mail sent, Back to SDK ", 70000);
            Thread.sleep(2000);

            System.out.println("clicking on discovery");
            po.clickOnDiscovery(driver);

            Thread.sleep(2000);

            po.clickOnCloseButton(driver);

            Thread.sleep(2000);

            System.out.println("clicking on CC");
            po.clickOnCC(driver);

            Thread.sleep(2000);

            po.clickOnCloseButton(driver);
            Thread.sleep(2000);
            po.clickOnCloseButton(driver);

            Thread.sleep(5000);
            po.screentap(driver);
            Thread.sleep(1000);
//            po.seek_video(driver);

//            Thread.sleep(5000);

            po.playVideo(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video start ", 70000);

            Thread.sleep(2000);
            po.getBackFromRecentApp(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 60000);
            Thread.sleep(1000);

            po.powerKeyClick(driver);
           // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 60000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);

        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }

    }

    @org.testng.annotations.Test
    public void FreeWheelIntegrationPreMidPostroll_overlay() throws Exception {

        try {
            // Creating an Object of FreeWheelSampleApp class
            exoPlayerSampleApp po = new exoPlayerSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);


            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Exo Player Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            po.clickBasedOnText(driver, "Freewheel Integration");
            Thread.sleep(2000);

            // Assert if current activity is Freewheel list activity
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Freewheel PreMidPost Overlay");
            Thread.sleep(2000);

            System.out.println("<<<<<<<<<<<<<clicked on PreMidPost Overlay>>>>>>>>>>>>>");


            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver, "h");

            //Getting Play button coordinates
            po.getPlay(driver);
            Thread.sleep(1000);

            //Clicking on Play button in Ooyala Skin
            po.clickBasedOnText(driver, "h");


            //Play Started Verification
            EventVerification ev = new EventVerification();
            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 35000);
            Thread.sleep(1000);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 35000);

            Thread.sleep(3000);

            po.verifyOverlay(driver);

            //Wait for Ad to start and verify the adStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 49000);
            Thread.sleep(5000);

            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 49000);
            Thread.sleep(1000);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 49000);
            Thread.sleep(5000);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);
            Thread.sleep(5000);

            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 50000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 60000);
            Thread.sleep(2000);

            po.replayVideo(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video replay start ", 70000);
            Thread.sleep(5000);

            //Tapping on screen to pause the Video
            po.screentap(driver);
            Thread.sleep(1000);

            po.pauseVideo(driver);
            // verifing video get paused
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            Thread.sleep(5000);

            po.getBackFromRecentApp(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);
            Thread.sleep(2000);

            po.powerKeyClick(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

            Thread.sleep(5000);

            //clicking on view area
            WebElement viewarea = driver.findElementByClassName("android.view.View");
            viewarea.click();
            Thread.sleep(1000);

            // clicking on more button
            po.moreButton(driver);

            Thread.sleep(2000);

            // clicking on Share button
            po.shareAsset(driver);

            System.out.println("clicked on share button");

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: SUSPENDED", " Sharing the asset ", 70000);
            po.shareOnGmail(driver);
            Thread.sleep(1000);

            ev.verifyEvent("stateChanged - state: READY", " Mail sent, Back to SDK ", 70000);
            Thread.sleep(2000);

            System.out.println("clicking on discovery");
            po.clickOnDiscovery(driver);

            Thread.sleep(2000);

            po.clickOnCloseButton(driver);

            Thread.sleep(2000);

            System.out.println("clicking on CC");
            po.clickOnCC(driver);

            Thread.sleep(2000);

            po.clickOnCloseButton(driver);
            Thread.sleep(2000);
            po.clickOnCloseButton(driver);

            Thread.sleep(5000);
            po.screentap(driver);
            Thread.sleep(1000);
//            po.seek_video(driver);

//            Thread.sleep(5000);

            po.playVideo(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video start ", 70000);

            Thread.sleep(2000);

            po.getBackFromRecentApp(driver);
            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 60000);

            Thread.sleep(1000);
            po.powerKeyClick(driver);
            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 60000);

            Thread.sleep(1000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);

        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }

    }

}
