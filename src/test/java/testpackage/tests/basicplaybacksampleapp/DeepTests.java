package testpackage.tests.basicplaybacksampleapp;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import testpackage.pageobjects.BasicPlaybackSampleApp;
import testpackage.utils.*;
import testpackage.utils.*;
import org.testng.annotations.*;

import java.io.IOException;
import java.sql.Driver;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by Sachin on 1/28/2016.
 */
public class DeepTests extends EventLogTest{





    @BeforeClass
    public void beforeTest() throws Exception {

        // closing all recent app from background.
        CloserecentApps.closeApps();

        System.out.println("BeforeTest \n");

        System.out.println(System.getProperty("user.dir"));
        // Get Property Values
        LoadPropertyValues prop = new LoadPropertyValues();
        Properties p = prop.loadProperty();

        //System.out.println("Device id from properties file " + p.getProperty("deviceName"));
        //System.out.println("PortraitMode from properties file " + p.getProperty("PortraitMode"));
        //System.out.println("Path where APK is stored"+ p.getProperty("appDir"));
        //System.out.println("APK name is "+ p.getProperty("app"));
        //System.out.println("Platform under Test is "+ p.getProperty("platformName"));
        //System.out.println("Mobile OS Version is "+ p.getProperty("OSVERSION"));
        //System.out.println("Package Name of the App is "+ p.getProperty("appPackage"));
        //System.out.println("Activity Name of the App is "+ p.getProperty("appActivity"));

        SetUpAndroidDriver setUpdriver = new SetUpAndroidDriver();
        driver = setUpdriver.setUpandReturnAndroidDriver(p.getProperty("udid"), p.getProperty("appDir"), p.getProperty("appValue"), p.getProperty("platformName"), p.getProperty("platformVersion"), p.getProperty("appPackage"), p.getProperty("appActivity"));
        Thread.sleep(2000);
    }

    @BeforeMethod
    //public void beforeTest() throws Exception{
    public void beforeMethod() throws Exception {
        System.out.println("beforeMethod \n");
        //removeEventsLogFile.removeEventsFileLog(); create events file
        PushLogFileToDevice logpush = new PushLogFileToDevice();
        logpush.pushLogFile();
        if (driver.currentActivity() != "com.ooyala.sample.lists.BasicPlaybackListActivity") {
            driver.startActivity("com.ooyala.sample.BasicPlaybackSampleApp", "com.ooyala.sample.lists.BasicPlaybackListActivity");
        }

        // Get Property Values
        LoadPropertyValues prop1 = new LoadPropertyValues();
        Properties p1 = prop1.loadProperty();

        System.out.println(" Screen Mode " + p1.getProperty("ScreenMode"));

//        if(p1.getProperty("ScreenMode") != "P"){
//            System.out.println("Inside landscape Mode ");
//            driver.rotate(ScreenOrientation.LANDSCAPE);
//        }
//
//        driver.rotate(ScreenOrientation.LANDSCAPE);
//        driver.rotate(ScreenOrientation.LANDSCAPE);

    }

    @AfterClass
    public void afterTest() throws InterruptedException, IOException {
        System.out.println("AfterTest \n");
        driver.closeApp();
        driver.quit();
        LoadPropertyValues prop1 = new LoadPropertyValues();
        Properties p1 = prop1.loadProperty();
        String prop = p1.getProperty("appPackage");
       // Appuninstall.uninstall(prop);

    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws Exception {
        // Waiting for all the events from sdk to come in .
        System.out.println("AfterMethod \n");
        //ScreenshotDevice.screenshot(driver);
        RemoveEventsLogFile.removeEventsFileLog();
        Thread.sleep(10000);

    }

    //TODO : create unique file names for snapshots taken .

    @org.testng.annotations.Test
    public void AspectRatioTest() throws Exception {


            try {// Creating an Object of BasicPlaybackSampleApp class
                BasicPlaybackSampleApp po = new BasicPlaybackSampleApp();
                // wait till home screen of basicPlayBackApp is opened
                po.waitForAppHomeScreen(driver);

                // Assert if current activity is indeed equal to the activity name of app home screen
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
                // Wrire to console activity name of home screen app
                System.out.println("BasicPlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

                //Pause the running of test for a brief time .
                Thread.sleep(3000);

                // Select one of the video HLS,MP4 etc .
                po.clickBasedOnText(driver, "4:3 Aspect Ratio");
                Thread.sleep(2000);

                //verify if player was loaded
                po.waitForPresence(driver, "className", "android.view.View");
                // Assert if current activity is indeed equal to the activity name of the video player
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
                // Print to console output current player activity
                System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
                // Thread.sleep(10000);

                //waitting for start screen
                po.waitForTextView(driver,"00:00");
                Thread.sleep(1000);

                // move to full screen
                po.gotoFullScreen(driver);
                Thread.sleep(2000);

                // event verification for full screen
                EventVerification ev = new EventVerification();
                ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in full screen", 30000);
                Thread.sleep(2000);
                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
                Thread.sleep(3000);

                //Play video in full screen
                po.playInFullScreen(driver);
                Thread.sleep(1000);

                //Play Started Verification
                ev.verifyEvent("playStarted", "Video Started to Play", 35000);
                Thread.sleep(7000);

                //Tapping on screen
                po.screenTap(driver);
                Thread.sleep(1000);

                //Pausing video in full screen
                po.pauseInFullScreen(driver);
                Thread.sleep(1000);
                ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 40000);

                //Seeking the video in full screen
                po.seekVideoFullscreen(driver);
                Thread.sleep(1000);
                ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 45000);
                Thread.sleep(3000);

                // going back again in normal screen
                po.gotoNormalScreen(driver);
                Thread.sleep(2000);

                // event verification for normal screen
                ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in normal screen", 50000);
                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);
                Thread.sleep(3000);

                //Play video in normal screen
                po.playInNormalScreen(driver);
                Thread.sleep(1000);
                ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 55000);
                Thread.sleep(5000);

                //Tapping on screen
                po.screenTap(driver);
                Thread.sleep(1000);

                //Pausing video in normal screen
                po.pauseInNormalScreen(driver);
                Thread.sleep(1000);
                ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 60000);
                Thread.sleep(1000);

                //Seeking video in normal screen
                po.seekVideo(driver);
                Thread.sleep(1000);
                ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 65000);
                Thread.sleep(3000);

//                // Tap coordinates to pause
//                String dimensions = driver.manage().window().getSize().toString();
//                //System.out.println(" Dimensions are "+dimensions);
//                String[] dimensionsarray = dimensions.split(",");
//                int length = dimensionsarray[1].length();
//                String ydimensions = dimensionsarray[1].substring(0, length - 1);
//                String ydimensionstrimmed = ydimensions.trim();
//                int ydimensionsInt = Integer.parseInt(ydimensionstrimmed);
//                driver.tap(1, 35, (ydimensionsInt - 25), 0);

                // After pausing clicking on recent app button and getting sample app back
//                po.getBackFromRecentApp(driver);
//                Thread.sleep(2000);
//                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 65000);
//
//                //Clicking on power button
//                po.powerKeyClick(driver);
//                Thread.sleep(2000);
//                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 70000);

//                // seeking backward
//                po.getXYSeekBarAndSeek(driver, 155, 50);
//
//                // verifing seek event
//                ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 30000);
//
//                Thread.sleep(10000);


                // playing video in normal screen
                po.playInNormalScreen(driver);
                //verifing event for play
                ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 75000);
                Thread.sleep(10000);

//                //key lock
//                po.powerKeyClick(driver);
//                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 75000);
//                Thread.sleep(5000);
//
//                //Go to recent app
//                po.getBackFromRecentApp(driver);
//                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 75000);

                // verifing for video completed played
                ev.verifyEvent("playCompleted", " Video Completed Play ", 100000);

            } catch (Exception e) {
                System.out.println("AspectRatioTest throws Exception " + e);
                e.printStackTrace();
                ScreenshotDevice.screenshot(driver,"AspectRatioTest");
                Assert.assertTrue(false, "This will fail!");
            }
        }

    @org.testng.annotations.Test
    public void HLSVideoTest() throws Exception {

            try {
                // Creating an Object of BasicPlaybackSampleApp class
                BasicPlaybackSampleApp po = new BasicPlaybackSampleApp();
                // wait till home screen of basicPlayBackApp is opened
                po.waitForAppHomeScreen(driver);

                // Assert if current activity is indeed equal to the activity name of app home screen
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
                // Wrire to console activity name of home screen app
                System.out.println("BasicPlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

                //Pause the running of test for a brief time .
                Thread.sleep(3000);

                // Select one of the video HLS,MP4 etc .
                po.clickBasedOnText(driver, "HLS Video");
                Thread.sleep(2000);

                //verify if player was loaded
                po.waitForPresence(driver, "className", "android.view.View");
                // Assert if current activity is indeed equal to the activity name of the video player
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
                // Print to console output current player activity
                System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

                //waitting for start screen
                po.waitForTextView(driver,"00:00");
                Thread.sleep(1000);

                // move to full screen
                po.gotoFullScreen(driver);
                Thread.sleep(2000);

                // event verification for full screen
                EventVerification ev = new EventVerification();
                ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in full screen", 30000);
                Thread.sleep(2000);
                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
                Thread.sleep(3000);

                //Play video in full screen
                po.playInFullScreen(driver);
                Thread.sleep(1000);

                //Play Started Verification
                ev.verifyEvent("playStarted", "Video Started to Play", 35000);
                Thread.sleep(7000);

                //Tapping on screen
                po.screenTap(driver);
                Thread.sleep(1000);

                //Pausing video in full screen
                po.pauseInFullScreen(driver);
                Thread.sleep(1000);
                ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 40000);

                //Seeking the video in full screen
                po.seekVideoFullscreen(driver);
                Thread.sleep(1000);
                ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 45000);
                Thread.sleep(3000);

                // going back again in normal screen
                po.gotoNormalScreen(driver);
                Thread.sleep(2000);

                // event verification for normal screen
                ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in normal screen", 50000);
                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);
                Thread.sleep(3000);

                //Play video in normal screen
                po.playInNormalScreen(driver);
                Thread.sleep(4000);
                ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 55000);

                //Tapping on screen
                po.screenTap(driver);
                Thread.sleep(1000);

                //Pausing video in normal screen
                po.pauseInNormalScreen(driver);
                Thread.sleep(1000);
                ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 60000);
                Thread.sleep(1000);

                //Seeking video in normal screen
                po.seekVideo(driver);
                Thread.sleep(1000);
                ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 65000);
                Thread.sleep(3000);

//                // Tap coordinates to pause
//                String dimensions = driver.manage().window().getSize().toString();
//                //System.out.println(" Dimensions are "+dimensions);
//                String[] dimensionsarray = dimensions.split(",");
//                int length = dimensionsarray[1].length();
//                String ydimensions = dimensionsarray[1].substring(0, length - 1);
//                String ydimensionstrimmed = ydimensions.trim();
//                int ydimensionsInt = Integer.parseInt(ydimensionstrimmed);
//                driver.tap(1, 35, (ydimensionsInt - 25), 0);

                // After pausing clicking on recent app button and getting sample app back
//                po.getBackFromRecentApp(driver);
//                Thread.sleep(2000);
//                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 65000);
//
//                //Clicking on power button
//                po.powerKeyClick(driver);
//                Thread.sleep(2000);
//                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 70000);

//                // seeking backward
//                po.getXYSeekBarAndSeek(driver, 155, 50);
//
//                // verifing seek event
//                ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 30000);
//
//                Thread.sleep(10000);


                // playing video in normal screen
                po.playInNormalScreen(driver);
                //verifing event for play
                ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 75000);
                Thread.sleep(10000);

//                //key lock
//                po.powerKeyClick(driver);
//                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 75000);
//                Thread.sleep(5000);
//
//                //Go to recent app
//                po.getBackFromRecentApp(driver);
//                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 75000);

                // verifing for video completed played
                ev.verifyEvent("playCompleted", " Video Completed Play ", 100000);

            } catch (Exception e) {
                System.out.println("HLSVideoTest throws Exception " + e);
                e.printStackTrace();
                ScreenshotDevice.screenshot(driver,"HLSVideoTest");
                Assert.assertTrue(false, "This will fail!");
            }
        }

    @org.testng.annotations.Test
    public void MP4() throws Exception {

            try {


                // Creating an Object of BasicPlaybackSampleApp class
                BasicPlaybackSampleApp po = new BasicPlaybackSampleApp();
                // wait till home screen of basicPlayBackApp is opened
                po.waitForAppHomeScreen(driver);

                // Assert if current activity is indeed equal to the activity name of app home screen
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
                // Wrire to console activity name of home screen app
                System.out.println("BasicPlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

                //Pause the running of test for a brief time .
                Thread.sleep(3000);

                // Select one of the video HLS,MP4 etc .
                po.clickBasedOnText(driver, "MP4 Video");
                Thread.sleep(2000);

                //verify if player was loaded
                po.waitForPresence(driver, "className", "android.view.View");
                // Assert if current activity is indeed equal to the activity name of the video player
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
                // Print to console output current player activity
                System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
                //waitting for start screen
                po.waitForTextView(driver,"00:00");
                Thread.sleep(1000);

                // move to full screen
                po.gotoFullScreen(driver);
                Thread.sleep(2000);

                // event verification for full screen
                EventVerification ev = new EventVerification();
                ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in full screen", 30000);
                Thread.sleep(2000);
                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
                Thread.sleep(3000);

                //Play video in full screen
                po.playInFullScreen(driver);
                Thread.sleep(1000);

                //Play Started Verification
                ev.verifyEvent("playStarted", "Video Started to Play", 35000);
                Thread.sleep(7000);

                //Tapping on screen
                po.screenTap(driver);
                Thread.sleep(1000);

                //Pausing video in full screen
                po.pauseInFullScreen(driver);
                Thread.sleep(1000);
                ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 40000);

                //Seeking the video in full screen
                po.seekVideoFullscreen(driver);
                Thread.sleep(1000);
                ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 45000);
                Thread.sleep(3000);

                // going back again in normal screen
                po.gotoNormalScreen(driver);
                Thread.sleep(2000);

                // event verification for normal screen
                ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in normal screen", 50000);
                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);
                Thread.sleep(3000);

                //Play video in normal screen
                po.playInNormalScreen(driver);
                Thread.sleep(4000);
                ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 55000);

                //Tapping on screen
                po.screenTap(driver);
                Thread.sleep(1000);

                //Pausing video in normal screen
                po.pauseInNormalScreen(driver);
                Thread.sleep(1000);
                ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 60000);
                Thread.sleep(1000);

                //Seeking video in normal screen
                po.seekVideo(driver);
                Thread.sleep(1000);
                ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 65000);
                Thread.sleep(3000);

//                // Tap coordinates to pause
//                String dimensions = driver.manage().window().getSize().toString();
//                //System.out.println(" Dimensions are "+dimensions);
//                String[] dimensionsarray = dimensions.split(",");
//                int length = dimensionsarray[1].length();
//                String ydimensions = dimensionsarray[1].substring(0, length - 1);
//                String ydimensionstrimmed = ydimensions.trim();
//                int ydimensionsInt = Integer.parseInt(ydimensionstrimmed);
//                driver.tap(1, 35, (ydimensionsInt - 25), 0);

                // After pausing clicking on recent app button and getting sample app back
//                po.getBackFromRecentApp(driver);
//                Thread.sleep(2000);
//                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 65000);
//
//                //Clicking on power button
//                po.powerKeyClick(driver);
//                Thread.sleep(2000);
//                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 70000);

//                // seeking backward
//                po.getXYSeekBarAndSeek(driver, 155, 50);
//
//                // verifing seek event
//                ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 30000);
//
//                Thread.sleep(10000);


                // playing video in normal screen
                po.playInNormalScreen(driver);
                //verifing event for play
                ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 75000);
                Thread.sleep(10000);

//                //key lock
//                po.powerKeyClick(driver);
//                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 75000);
//                Thread.sleep(5000);
//
//                //Go to recent app
//                po.getBackFromRecentApp(driver);
//                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 75000);

                // verifing for video completed played
                ev.verifyEvent("playCompleted", " Video Completed Play ", 100000);

            } catch (Exception e) {
                System.out.println("MP4 throws Exception " + e);
                e.printStackTrace();
                ScreenshotDevice.screenshot(driver,"MP4");
                Assert.assertTrue(false, "This will fail!");
            }
        }

    @org.testng.annotations.Test
    public void VastAdWrapper() throws Exception {

            try {
                // Creating an Object of BasicPlaybackSampleApp class
                BasicPlaybackSampleApp po = new BasicPlaybackSampleApp();
                // wait till home screen of basicPlayBackApp is opened
                po.waitForAppHomeScreen(driver);

                // Assert if current activity is indeed equal to the activity name of app home screen
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
                // Wrire to console activity name of home screen app
                System.out.println("BasicPlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

                //Pause the running of test for a brief time .
                Thread.sleep(3000);

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
                Thread.sleep(1000);

                // move to full screen
                po.gotoFullScreen(driver);
                Thread.sleep(2000);

                // event verification for full screen
                EventVerification ev = new EventVerification();
                ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in full screen", 30000);
                Thread.sleep(2000);
                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
                Thread.sleep(3000);

                //Play video in full screen
                po.playInFullScreen(driver);
                Thread.sleep(2000);

                ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

                //Thread sleep time is equivalent to the length of the AD
                Thread.sleep(5000);

                //Ad Completed Verification
                ev.verifyEvent("adCompleted", " Ad Playback Completed ", 40000);
                Thread.sleep(1000);

                //Play Started Verification
                ev.verifyEvent("playStarted", "Video Started to Play", 35000);
                Thread.sleep(7000);

                //Tapping on screen
                po.screenTap(driver);
                Thread.sleep(1000);

                //Pausing video in full screen
                po.pauseInFullScreen(driver);
                Thread.sleep(1000);
                ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 40000);

                //Seeking the video in full screen
                po.seekVideoFullscreen(driver);
                Thread.sleep(1000);
                ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 45000);
                Thread.sleep(3000);

                // going back again in normal screen
                po.gotoNormalScreen(driver);
                Thread.sleep(2000);

                // event verification for normal screen
                ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in normal screen", 50000);
                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);
                Thread.sleep(3000);

                //Play video in normal screen
                po.playInNormalScreen(driver);
                Thread.sleep(4000);
                ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 55000);

                //Tapping on screen
                po.screenTap(driver);
                Thread.sleep(1000);

                //Pausing video in normal screen
                po.pauseInNormalScreen(driver);
                Thread.sleep(1000);
                ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 60000);
                Thread.sleep(1000);

                //Seeking video in normal screen
                po.seekVideo(driver);
                Thread.sleep(1000);
                ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 65000);
                Thread.sleep(3000);

//                // Tap coordinates to pause
//                String dimensions = driver.manage().window().getSize().toString();
//                //System.out.println(" Dimensions are "+dimensions);
//                String[] dimensionsarray = dimensions.split(",");
//                int length = dimensionsarray[1].length();
//                String ydimensions = dimensionsarray[1].substring(0, length - 1);
//                String ydimensionstrimmed = ydimensions.trim();
//                int ydimensionsInt = Integer.parseInt(ydimensionstrimmed);
//                driver.tap(1, 35, (ydimensionsInt - 25), 0);

                // After pausing clicking on recent app button and getting sample app back
//                po.getBackFromRecentApp(driver);
//                Thread.sleep(2000);
//                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 65000);
//
//                //Clicking on power button
//                po.powerKeyClick(driver);
//                Thread.sleep(2000);
//                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 70000);

//                // seeking backward
//                po.getXYSeekBarAndSeek(driver, 155, 50);
//
//                // verifing seek event
//                ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 30000);
//
//                Thread.sleep(10000);


                // playing video in normal screen
                po.playInNormalScreen(driver);
                //verifing event for play
                ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 75000);
                Thread.sleep(10000);

//                //key lock
//                po.powerKeyClick(driver);
//                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 75000);
//                Thread.sleep(5000);
//
//                //Go to recent app
//                po.getBackFromRecentApp(driver);
//                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 75000);

                // verifing for video completed played
                ev.verifyEvent("playCompleted", " Video Completed Play ", 100000);

            } catch (Exception e) {
                System.out.println("VastAdWrapper throws Exception " + e);
                e.printStackTrace();
                ScreenshotDevice.screenshot(driver,"VastAdWrapper");
                Assert.assertTrue(false, "This will fail!");
            }
        }

    @org.testng.annotations.Test
    public void VOD() throws Exception {

        try {


            // Creating an Object of BasicPlaybackSampleApp class
            BasicPlaybackSampleApp po = new BasicPlaybackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("BasicPlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "VOD with CCs");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //waitting for start screen
            po.waitForTextView(driver,"00:00");
            Thread.sleep(1000);

            // move to full screen
            po.gotoCCFullScreen(driver);
            Thread.sleep(2000);

            // event verification for full screen
            EventVerification ev = new EventVerification();
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in full screen", 30000);
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(3000);

            //Play video in full screen
            po.playInFullScreen(driver);
            Thread.sleep(1000);

            //Play Started Verification
            ev.verifyEvent("playStarted", "Video Started to Play", 35000);
            Thread.sleep(7000);

            //Tapping on screen
            po.screenTap(driver);
            Thread.sleep(1000);

            //Pausing video in full screen
            po.pauseInFullScreen(driver);
            Thread.sleep(1000);
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 40000);

            //Seeking the video in full screen
            po.seekVideoFullscreen(driver);
            Thread.sleep(1000);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 45000);
            Thread.sleep(3000);

            // going back again in normal screen
            po.gotoCCNormalScreen(driver);
            Thread.sleep(2000);

            // event verification for normal screen
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in normal screen", 50000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);
            Thread.sleep(3000);

            //Play video in normal screen
            po.playInNormalScreen(driver);
            Thread.sleep(4000);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 55000);

            //Tapping on screen
            po.screenTap(driver);
            Thread.sleep(1000);

            //Pausing video in normal screen
            po.pauseInNormalScreen(driver);
            Thread.sleep(1000);
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 60000);
            Thread.sleep(1000);

            po.clickImagebuttons(driver,(2-1));

            po.waitForTextView(driver,"Languages");

            po.clickRadiobuttons(driver,(7-1));

            Thread.sleep(2000);

            Assert.assertTrue(po.radioButtonChecked(driver, (7 - 1)));

            // closed Captions event verification
            ev.verifyEvent("closedCaptionsLanguageChanged", " CC of the Video Was Changed ", 30000);

            driver.navigate().back();

            // Pause the running of the test for a brief amount of time
            Thread.sleep(3000);

            //Seeking video in normal screen
            po.seekVideo(driver);
            Thread.sleep(1000);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 65000);
            Thread.sleep(3000);

//                // Tap coordinates to pause
//                String dimensions = driver.manage().window().getSize().toString();
//                //System.out.println(" Dimensions are "+dimensions);
//                String[] dimensionsarray = dimensions.split(",");
//                int length = dimensionsarray[1].length();
//                String ydimensions = dimensionsarray[1].substring(0, length - 1);
//                String ydimensionstrimmed = ydimensions.trim();
//                int ydimensionsInt = Integer.parseInt(ydimensionstrimmed);
//                driver.tap(1, 35, (ydimensionsInt - 25), 0);

            // After pausing clicking on recent app button and getting sample app back
//                po.getBackFromRecentApp(driver);
//                Thread.sleep(2000);
//                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 65000);
//
//                //Clicking on power button
//                po.powerKeyClick(driver);
//                Thread.sleep(2000);
//                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 70000);

//                // seeking backward
//                po.getXYSeekBarAndSeek(driver, 155, 50);
//
//                // verifing seek event
//                ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 30000);
//
//                Thread.sleep(10000);


            // playing video in normal screen
            po.playInNormalScreen(driver);
            //verifing event for play
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 75000);
            Thread.sleep(10000);

//                //key lock
//                po.powerKeyClick(driver);
//                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 75000);
//                Thread.sleep(5000);
//
//                //Go to recent app
//                po.getBackFromRecentApp(driver);
//                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 75000);

            // verifing for video completed played
            ev.verifyEvent("playCompleted", " Video Completed Play ", 100000);


        } catch (Exception e) {
            System.out.println("VOD throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VOD");
            Assert.assertTrue(false, "This will fail!");
        }
    }

}