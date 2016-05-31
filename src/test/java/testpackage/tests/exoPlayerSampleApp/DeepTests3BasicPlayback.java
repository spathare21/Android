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
 * Created by Shivam on 27/05/16.
 */
public class DeepTests3BasicPlayback {

    private static AndroidDriver driver;

    @BeforeClass
    public void beforeTest() throws Exception {

        // closing all recent app from background.
        //CloserecentApps.closeApps();
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
    public void MultiAd() throws Exception{
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
            po.clickBasedOnTextScrollTo(driver, "Multi Ad combination");
            Thread.sleep(2000);

            System.out.println("<<<<<Clicked on MultiAd Combination Video>>>>>>");

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver, "h");

            //Get coordinates and click on play button.
            po.getPlay(driver);
            Thread.sleep(8000);

            //Clicking on Play button in Ooyala Skin
            //po.clickBasedOnText(driver,"h");

            //Ad Started Verification
            EventVerification ev = new EventVerification();

            //Ad Started
            ev.verifyEvent("adStarted", " Ad has been started ", 10000);

            //Adcompleted Verficatrion
            ev.verifyEvent("adCompleted","Ad has been completed",20000 );

            ev.verifyEvent("playStarted", "Video playing started", 25000);

            //Ad Started
            ev.verifyEvent("adStarted", " Ad has been started ", 30000);

            //Adcompleted Verficatrion
            ev.verifyEvent("adCompleted","Ad has been completed",40000 );
            //Timeout for the duration of the video

            Thread.sleep(3000);


            po.screentapping(driver);
            Thread.sleep(1000);

            po.pausingVideo(driver);
            Thread.sleep(1000);

            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", "Video has been paused", 300000);
            Thread.sleep(1000);

            po.discoverUpNext(driver);
            Thread.sleep(1000);

            po.screentapping(driver);
            Thread.sleep(1000);

            po.getPlay(driver);
            Thread.sleep(1000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 40000);

            po.discoveryTray(driver);
            Thread.sleep(3000);

            po.replayVideo(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video replay start ", 70000);
            Thread.sleep(2000);

            po.screentap(driver);

            Thread.sleep(1000);

            // pausing video
            po.pauseVideo(driver);
            // verifing video get paused
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            Thread.sleep(3000);

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
            Thread.sleep(1500);

            // clicking on more button
            po.moreButton(driver);

            Thread.sleep(2000);

            // clicking on Share button
            po.shareAsset(driver);

            System.out.println("clicked on share button");

            Thread.sleep(2000);

            // sharing asset on gmail.
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

            // closing more option
            po.clickOnCloseButton(driver);
            Thread.sleep(2000);
            po.clickOnCloseButton(driver);

            Thread.sleep(5000);

            // tapping on screen for get the scrubber bar and play/pause button
            po.screentap(driver);
            Thread.sleep(2000);


            // playing the video
            po.playVideo(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video start ", 70000);

            Thread.sleep(2000);

            po.getBackFromRecentApp(driver);
            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 60000);

            Thread.sleep(4000);

            po.powerKeyClick(driver);
            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 60000);


            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 100000);
            Thread.sleep(1000);

            po.discoveryTray(driver);
            Thread.sleep(3000);

            System.out.println("<<<<<<<<<<<<<<Completed MultiAd Combination Asset playback>>>>>>>>>>>>>");

        }
        catch(Exception e)
        {
            System.out.println(" Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }

    }

   //TODO AdCompleted Event not getting fired PBA-3704

    @org.testng.annotations.Test
    public void VAST_3_Podded() throws Exception{
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
            po.clickBasedOnTextScrollTo(driver, "VAST 3.0 2 Podded Ad");
            Thread.sleep(2000);

            System.out.println("<<<<<Clicked on VAST 3.0.2 Podded Video>>>>>>");

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver, "h");

            //Get coordinates and click on play button.
            po.getPlay(driver);
            Thread.sleep(1000);

            //Clicking on Play button in Ooyala Skin
            //po.clickBasedOnText(driver,"h");

            //Ad Started Verification
            EventVerification ev = new EventVerification();

            //Ad Started
            ev.verifyEvent("adStarted", " Ad has been started ", 10000);

            //Adcompleted Verficatrion
            ev.verifyEvent("adCompleted","Ad has been completed",20000 );

            //Ad Started
            ev.verifyEvent("adStarted", " Ad has been started ", 20000);

            //Adcompleted Verficatrion
            ev.verifyEvent("adCompleted","Ad has been completed",30000 );

            ev.verifyEvent("playStarted", "Video playing started", 35000);

           //Timeout for the duration of the video

            Thread.sleep(66000);


            po.screentapping(driver);
            Thread.sleep(1000);

            po.pausingVideo(driver);
            Thread.sleep(1000);

            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", "Video has been paused", 80000);
            Thread.sleep(1000);

            po.discoverUpNext(driver);
            Thread.sleep(1000);

            po.screentapping(driver);
            Thread.sleep(1000);

            po.getPlay(driver);
            Thread.sleep(1000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);

            po.discoveryTray(driver);
            Thread.sleep(3000);

            po.replayVideo(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video replay start ", 90000);
            Thread.sleep(10000);

            po.screentap(driver);

            Thread.sleep(1000);

            // pausing video
            po.pauseVideo(driver);
            // verifing video get paused
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 150000);

            Thread.sleep(3000);

            po.getBackFromRecentApp(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 150000);

            Thread.sleep(2000);

            po.powerKeyClick(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 150000);

            Thread.sleep(5000);

            //clicking on view area
            WebElement viewarea = driver.findElementByClassName("android.view.View");
            viewarea.click();
            Thread.sleep(1500);

            // clicking on more button
            po.moreButton(driver);

            Thread.sleep(2000);

            // clicking on Share button
            po.shareAsset(driver);

            System.out.println("clicked on share button");

            Thread.sleep(2000);

            // sharing asset on gmail.
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

            // closing more option
            po.clickOnCloseButton(driver);
            Thread.sleep(2000);
            po.clickOnCloseButton(driver);

            Thread.sleep(5000);

            // tapping on screen for get the scrubber bar and play/pause button
            po.screentap(driver);
            Thread.sleep(2000);


            // playing the video
            po.playVideo(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video start ", 170000);

            Thread.sleep(2000);

            po.getBackFromRecentApp(driver);
            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 160000);

            Thread.sleep(4000);

            po.powerKeyClick(driver);
            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 180000);


            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 200000);
            Thread.sleep(1000);

            po.discoveryTray(driver);
            Thread.sleep(3000);

            System.out.println("<<<<<<<<<<<<<<Completed MultiAd Combination Asset playback>>>>>>>>>>>>>");

        }
        catch(Exception e)
        {
            System.out.println(" Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }

    }

    @org.testng.annotations.Test
    public void VAST_AD_With_NewEvents() throws Exception{
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
            po.clickBasedOnTextScrollTo(driver, "VAST 3.0 Ad With All Of The New Events");
            Thread.sleep(2000);

            System.out.println("<<<<<Clicked on VAST 3.0 Ad With All Of The New Events Video>>>>>>");

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver, "h");

            //Get coordinates and click on play button.
            po.getPlay(driver);
            Thread.sleep(1000);

            //Clicking on Play button in Ooyala Skin
            //po.clickBasedOnText(driver,"h");

            //Ad Started Verification
            EventVerification ev = new EventVerification();

            //Ad Started
            ev.verifyEvent("adStarted", " Ad has been started ", 10000);

            //Adcompleted Verficatrion
            ev.verifyEvent("adCompleted","Ad has been completed",35000 );

            ev.verifyEvent("playStarted", "Video playing started", 40000);

            //Timeout for the duration of the video

            Thread.sleep(66000);


            po.screentapping(driver);
            Thread.sleep(1000);

            po.pausingVideo(driver);
            Thread.sleep(1000);

            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", "Video has been paused", 80000);
            Thread.sleep(1000);

            po.discoverUpNext(driver);
            Thread.sleep(1000);

            po.screentapping(driver);
            Thread.sleep(1000);

            po.getPlay(driver);
            Thread.sleep(1000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);

            po.discoveryTray(driver);
            Thread.sleep(3000);

            po.replayVideo(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video replay start ", 90000);
            Thread.sleep(10000);

            po.screentap(driver);

            Thread.sleep(1000);

            // pausing video
            po.pauseVideo(driver);
            // verifing video get paused
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 150000);

            Thread.sleep(3000);

            po.getBackFromRecentApp(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 150000);

            Thread.sleep(2000);

            po.powerKeyClick(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 150000);

            Thread.sleep(5000);

            //clicking on view area
            WebElement viewarea = driver.findElementByClassName("android.view.View");
            viewarea.click();
            Thread.sleep(1500);

            // clicking on more button
            po.moreButton(driver);

            Thread.sleep(2000);

            // clicking on Share button
            po.shareAsset(driver);

            System.out.println("clicked on share button");

            Thread.sleep(2000);

            // sharing asset on gmail.
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

            // closing more option
            po.clickOnCloseButton(driver);
            Thread.sleep(2000);
            po.clickOnCloseButton(driver);

            Thread.sleep(5000);

            // tapping on screen for get the scrubber bar and play/pause button
            po.screentap(driver);
            Thread.sleep(2000);


            // playing the video
            po.playVideo(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video start ", 170000);

            Thread.sleep(2000);

            po.getBackFromRecentApp(driver);
            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 160000);

            Thread.sleep(4000);

            po.powerKeyClick(driver);
            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 180000);


            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 200000);
            Thread.sleep(1000);

            po.discoveryTray(driver);
            Thread.sleep(3000);

            System.out.println("<<<<<<<<<<<<<<Completed VAST 3.0 Ad With All Of The New Events Asset playback>>>>>>>>>>>>>");

        }
        catch(Exception e)
        {
            System.out.println(" Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }

    }

    @org.testng.annotations.Test
    public void VAST_AD_With_Icon() throws Exception{
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
            po.clickBasedOnTextScrollTo(driver, "VAST 3.0 Ad With Icon");
            Thread.sleep(2000);

            System.out.println("<<<<<Clicked on VAST 3.0 Ad With Icon Video>>>>>>");

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver, "h");

            //Get coordinates and click on play button.
            po.getPlay(driver);
            Thread.sleep(1000);

            //Clicking on Play button in Ooyala Skin
            //po.clickBasedOnText(driver,"h");

            //Ad Started Verification
            EventVerification ev = new EventVerification();

            //Ad Started
            ev.verifyEvent("adStarted", " Ad has been started ", 10000);

            //Adcompleted Verficatrion
            ev.verifyEvent("adCompleted","Ad has been completed",35000 );

            ev.verifyEvent("playStarted", "Video playing started", 40000);

            //Timeout for the duration of the video

            Thread.sleep(66000);


            po.screentapping(driver);
            Thread.sleep(1000);

            po.pausingVideo(driver);
            Thread.sleep(1000);

            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", "Video has been paused", 80000);
            Thread.sleep(1000);

            po.discoverUpNext(driver);
            Thread.sleep(1000);

            po.screentapping(driver);
            Thread.sleep(1000);

            po.getPlay(driver);
            Thread.sleep(1000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);

            po.discoveryTray(driver);
            Thread.sleep(3000);

            po.replayVideo(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video replay start ", 90000);
            Thread.sleep(10000);

            po.screentap(driver);

            Thread.sleep(1000);

            // pausing video
            po.pauseVideo(driver);
            // verifing video get paused
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 150000);

            Thread.sleep(3000);

            po.getBackFromRecentApp(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 150000);

            Thread.sleep(2000);

            po.powerKeyClick(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 150000);

            Thread.sleep(5000);

            //clicking on view area
            WebElement viewarea = driver.findElementByClassName("android.view.View");
            viewarea.click();
            Thread.sleep(1500);

            // clicking on more button
            po.moreButton(driver);

            Thread.sleep(2000);

            // clicking on Share button
            po.shareAsset(driver);

            System.out.println("clicked on share button");

            Thread.sleep(2000);

            // sharing asset on gmail.
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

            // closing more option
            po.clickOnCloseButton(driver);
            Thread.sleep(2000);
            po.clickOnCloseButton(driver);

            Thread.sleep(5000);

            // tapping on screen for get the scrubber bar and play/pause button
            po.screentap(driver);
            Thread.sleep(2000);


            // playing the video
            po.playVideo(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video start ", 170000);

            Thread.sleep(2000);

            po.getBackFromRecentApp(driver);
            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 160000);

            Thread.sleep(4000);

            po.powerKeyClick(driver);
            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 180000);


            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 200000);
            Thread.sleep(1000);

            po.discoveryTray(driver);
            Thread.sleep(3000);

            System.out.println("<<<<<<<<<<<<<<Completed VAST 3.0 Ad With Icon Asset playback>>>>>>>>>>>>>");

        }
        catch(Exception e)
        {
            System.out.println(" Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }

    }

    @org.testng.annotations.Test
    public void VAST_AD_Skippable_Long() throws Exception{
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
            po.clickBasedOnTextScrollTo(driver, "VAST 3.0 Skippable Ad Long");
            Thread.sleep(2000);

            System.out.println("<<<<<Clicked on VAST 3.0 Skippable Ad Long Video>>>>>>");

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver, "h");

            //Get coordinates and click on play button.
            po.getPlay(driver);
            Thread.sleep(1000);

            //Clicking on Play button in Ooyala Skin
            //po.clickBasedOnText(driver,"h");

            //Ad Started Verification
            EventVerification ev = new EventVerification();

            //Ad Started
            ev.verifyEvent("adStarted", " Ad has been started ", 10000);

            Thread.sleep(7000);
            po.screentapping(driver);
            Thread.sleep(1000);

            po.pausingVideo(driver);
            Thread.sleep(1000);

            po.clickBasedOnText(driver, "Skip Ad");

            ev.verifyEvent("adSkipped", "Ad has been skipped", 20000);

            //Adcompleted Verficatrion
            ev.verifyEvent("adCompleted","Ad has been completed",35000 );

            ev.verifyEvent("playStarted", "Video playing started", 40000);

            //Timeout for the duration of the video

            Thread.sleep(30000);


            po.screentapping(driver);
            Thread.sleep(1000);

            po.pausingVideo(driver);
            Thread.sleep(1000);

            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", "Video has been paused", 80000);
            Thread.sleep(1000);

            po.discoverUpNext(driver);
            Thread.sleep(1000);

            po.screentapping(driver);
            Thread.sleep(1000);

            po.getPlay(driver);
            Thread.sleep(1000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);

            po.discoveryTray(driver);
            Thread.sleep(3000);

            po.replayVideo(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video replay start ", 90000);
            Thread.sleep(10000);

            po.screentap(driver);

            Thread.sleep(1000);

            // pausing video
            po.pauseVideo(driver);
            // verifing video get paused
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 150000);

            Thread.sleep(3000);

            po.getBackFromRecentApp(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 150000);

            Thread.sleep(2000);

            po.powerKeyClick(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 150000);

            Thread.sleep(5000);

            //clicking on view area
            WebElement viewarea = driver.findElementByClassName("android.view.View");
            viewarea.click();
            Thread.sleep(1500);

            // clicking on more button
            po.moreButton(driver);

            Thread.sleep(2000);

            // clicking on Share button
            po.shareAsset(driver);

            System.out.println("clicked on share button");

            Thread.sleep(2000);

            // sharing asset on gmail.
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

            // closing more option
            po.clickOnCloseButton(driver);
            Thread.sleep(2000);
            po.clickOnCloseButton(driver);

            Thread.sleep(5000);

            // tapping on screen for get the scrubber bar and play/pause button
            po.screentap(driver);
            Thread.sleep(2000);


            // playing the video
            po.playVideo(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video start ", 170000);

            Thread.sleep(2000);

            po.getBackFromRecentApp(driver);
            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 160000);

            Thread.sleep(4000);

            po.powerKeyClick(driver);
            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 180000);


            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 200000);
            Thread.sleep(1000);

            po.discoveryTray(driver);
            Thread.sleep(3000);

            System.out.println("<<<<<<<<<<<<<<Completed VAST 3.0 Skippable Ad Long Asset playback>>>>>>>>>>>>>");

        }
        catch(Exception e)
        {
            System.out.println(" Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }

    }

}
