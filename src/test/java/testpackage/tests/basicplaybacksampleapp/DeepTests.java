package testpackage.tests.basicplaybacksampleapp;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import testpackage.pageobjects.BasicPlaybackSampleApp;
import testpackage.utils.*;
import testpackage.utils.*;

import java.io.IOException;
import java.sql.Driver;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by Sachin on 1/28/2016.
 */
public class DeepTests {

    private static AndroidDriver driver;

    @BeforeTest
    public void beforeTest() throws Exception {
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

    @AfterTest
    public void afterTest() throws InterruptedException, IOException {
        System.out.println("AfterTest \n");
        driver.closeApp();
        driver.quit();

    }

    @AfterMethod
    //public void afterTest() throws InterruptedException, IOException {
    public void afterMethod() throws InterruptedException, IOException {
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
                //Play Started Verification
                EventVerification ev = new EventVerification();
                ev.verifyEvent("playStarted", "Video Started to Play", 30000);
                // Click on the web area so that player screen shows up
                WebElement viewarea = driver.findElementByClassName("android.view.View");
                viewarea.click();

                // Tap coordinates to pause
                String dimensions = driver.manage().window().getSize().toString();
                //System.out.println(" Dimensions are "+dimensions);
                String[] dimensionsarray = dimensions.split(",");
                int length = dimensionsarray[1].length();
                String ydimensions = dimensionsarray[1].substring(0, length - 1);
                String ydimensionstrimmed = ydimensions.trim();
                int ydimensionsInt = Integer.parseInt(ydimensionstrimmed);
                driver.tap(1, 35, (ydimensionsInt - 25), 0);
                ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);

                Thread.sleep(2000);

                // After pausing clicking on recent app button and getting sample app back
                po.getBackFromRecentApp(driver);

                Thread.sleep(2000);

                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

                po.powerKeyClick(driver);

                Thread.sleep(2000);

                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);


                // move to full screen
                po.gotoFullScreen(driver);

                Thread.sleep(2000);
                // event verification for full screen
                ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in full screen", 30000);

                Thread.sleep(2000);

                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

                // going back again in normal screen
                po.gotoNormalScreen(driver);

                Thread.sleep(2000);
                // event verification for normal screen
                ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in normal screen", 30000);

                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 40000);

                Thread.sleep(3000);

                // seeking backward
                po.getXYSeekBarAndSeek(driver, 155, 50);

                // verifing seek event
                ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 30000);

                Thread.sleep(10000);


                // playing video in normal screen
                po.playInNormalScreen(driver);

                //verifing event for play

                ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 30000);

               Thread.sleep(10000);

                po.powerKeyClick(driver);

                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

                Thread.sleep(5000);

                po.getBackFromRecentApp(driver);

                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);


                // verifing for video completed played
                ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);

            } catch (Exception e) {
                System.out.println(" Exception " + e);
                e.printStackTrace();
                ScreenshotDevice.screenshot(driver);
            }
        }

    //TODO : create unique file names for snapshots taken .

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
                EventVerification ev = new EventVerification();
                ev.verifyEvent("playStarted", "Video Started to Play", 30000);
                // Click on the web area so that player screen shows up
                WebElement viewarea = driver.findElementByClassName("android.view.View");
                viewarea.click();

                // Tap coordinates to pause
                String dimensions = driver.manage().window().getSize().toString();
                //System.out.println(" Dimensions are "+dimensions);
                String[] dimensionsarray = dimensions.split(",");
                int length = dimensionsarray[1].length();
                String ydimensions = dimensionsarray[1].substring(0, length - 1);
                String ydimensionstrimmed = ydimensions.trim();
                int ydimensionsInt = Integer.parseInt(ydimensionstrimmed);
                driver.tap(1, 35, (ydimensionsInt - 25), 0);

                //verifing pause event
                ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);


                Thread.sleep(2000);

                // After pausing clicking on recent app button and getting sample app back
                po.getBackFromRecentApp(driver);

                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

                Thread.sleep(3000);

                po.powerKeyClick(driver);

                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

                Thread.sleep(3000);



                // MOVING to full screen
                po.gotoFullScreen(driver);

                Thread.sleep(1000);

                // verifing event for full screen
                ev.verifyEvent("stateChanged - state: SUSPENDED", " Playing Video moved fullscreen ", 30000);

                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

                Thread.sleep(2000);

                // moving back to normal screen
                po.gotoNormalScreen(driver);

                // verifing event for back in normal screen
                ev.verifyEvent("stateChanged - state: SUSPENDED","Playing video moved normalscreen",3000);

                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

                Thread.sleep(2000);

                //seeking backward scrubber bar
                po.getXYSeekBarAndSeek(driver, 155, 50);

                // verifing event for seek
                ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 30000);

                Thread.sleep(10000);


                // playing asset in normal screen
                po.playInNormalScreen(driver);

                // verifing event fot video play
                ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 30000);

                Thread.sleep(10000);

                po.powerKeyClick(driver);

                Thread.sleep(2000);

                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 40000);

                Thread.sleep(5000);

                po.getBackFromRecentApp(driver);

                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 40000);


                // verifing event for video completion

                ev.verifyEvent("playCompleted", " Video Completed Play ", 70000);



            } catch (Exception e) {
                System.out.println(" Exception " + e);
                e.printStackTrace();
                ScreenshotDevice.screenshot(driver);
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
                EventVerification ev = new EventVerification();
                ev.verifyEvent("playStarted", "Video Started to Play", 30000);
                // Click on the web area so that player screen shows up
                WebElement viewarea = driver.findElementByClassName("android.view.View");
                viewarea.click();

                // Tap coordinates to pause
                String dimensions = driver.manage().window().getSize().toString();
                //System.out.println(" Dimensions are "+dimensions);
                String[] dimensionsarray = dimensions.split(",");
                int length = dimensionsarray[1].length();
                String ydimensions = dimensionsarray[1].substring(0, length - 1);
                String ydimensionstrimmed = ydimensions.trim();
                int ydimensionsInt = Integer.parseInt(ydimensionstrimmed);
                driver.tap(1, 35, (ydimensionsInt - 25), 0);
                ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);

                Thread.sleep(2000);

                // After pausing clicking on recent app button and getting sample app back
                po.getBackFromRecentApp(driver);

                 ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

                 Thread.sleep(2000);

                po.powerKeyClick(driver);

                 ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 40000);

                Thread.sleep(2000);

                po.gotoFullScreen(driver);


                Thread.sleep(2000);

                ev.verifyEvent("stateChanged - state: SUSPENDED", " Playing Video moved fullscreen ", 30000);

                Thread.sleep(1000);

                 ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 40000);


                Thread.sleep(2000);

                po.gotoNormalScreen(driver);
                ev.verifyEvent("stateChanged - state: SUSPENDED", " Playing Video moved Normal screen ", 30000);

                Thread.sleep(2000);

                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 40000);



                po.getXYSeekBarAndSeek(driver, 155, 50);
                ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 30000);

                Thread.sleep(10000);


                po.playInNormalScreen(driver);
                ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 30000);

                Thread.sleep(10000);


                po.powerKeyClick(driver);

                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 40000);

                Thread.sleep(5000);

                po.getBackFromRecentApp(driver);

                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 40000);

                ev.verifyEvent("playCompleted", " Video Completed Play ", 70000);


            } catch (Exception e) {
                System.out.println(" Exception " + e);
                e.printStackTrace();
                ScreenshotDevice.screenshot(driver);
            }
        }


        @org.testng.annotations.Test
        public void multiAdCombination() throws Exception {

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
                po.clickBasedOnTextScrollTo(driver, "Multi Ad combination");
                Thread.sleep(2000);

                //verify if player was loaded
                po.waitForPresence(driver, "className", "android.view.View");

                // Assert if current activity is indeed equal to the activity name of the video player
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");

                // Print to console output current player activity
                System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
                Thread.sleep(3000);

                // creating Event verification object
                EventVerification ev = new EventVerification();

                // Ad play starting event
                ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
                Thread.sleep(1000);

                // Tap coordinates to pause
                String dimensions = driver.manage().window().getSize().toString();
                //System.out.println(" Dimensions are "+dimensions);
                String[] dimensionsarray = dimensions.split(",");
                int length = dimensionsarray[1].length();
                String ydimensions = dimensionsarray[1].substring(0, length - 1);
                String ydimensionstrimmed = ydimensions.trim();
                int ydimensionsInt = Integer.parseInt(ydimensionstrimmed);
                driver.tap(1, 35, (ydimensionsInt - 25), 0);

                // Ad pause verifying
                ev.verifyEvent("stateChanged - state: PAUSED", " Playing Ad Was Paused ", 30000);

                Thread.sleep(2000);

                // After pausing clicking on recent app button and getting sample app back
                po.getBackFromRecentApp(driver);

                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 40000);

                Thread.sleep(2000);

                po.powerKeyClick(driver);

                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 40000);

                Thread.sleep(2000);

                // click on learn more button
                po.clickLearnMore(driver);

                //verifing event
                ev.verifyEvent("stateChanged - state: SUSPENDED", "clicked on learn more", 40000);

                Thread.sleep(2000);

                // coming back to SDK
                driver.navigate().back();

                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 40000);


                Thread.sleep(10000);

                // Playing the video in normal screen
                po.playInNormalScreen(driver);

                //verifing Ad started playing event
                ev.verifyEvent("adStarted", " Ad Started to Play ", 40000);

                //Thread sleep time is equivalent to the completetion of the Ad
                Thread.sleep(3000);

                // Ad completed verifing event
                ev.verifyEvent("adCompleted", " Ad Completed to Play ", 40000);

                Thread.sleep(1000);


                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 40000);


                // Video playing started event verification
                ev.verifyEvent("playStarted", "Video Started to Play", 40000);

                po.powerKeyClick(driver);

                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 40000);

                Thread.sleep(2000);

                po.getBackFromRecentApp(driver);

                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 40000);

                // Ad playing strat event verification
                ev.verifyEvent("adStarted", " Ad Started to Play ", 40000);

                Thread.sleep(2000);

                // pausing
                driver.tap(1, 35, (ydimensionsInt - 25), 0);

                ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);


                Thread.sleep(2000);

                // clicking on learn more button
                po.clickLearnMore(driver);

                // verifing the event
                ev.verifyEvent("stateChanged - state: SUSPENDED", "clicked on learn more", 70000);

                Thread.sleep(2000);

                // navigating back to SDK
                driver.navigate().back();

                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 40000);

                Thread.sleep(10000);

                // Playing asset in normal screen
                po.playInNormalScreen(driver);

                // Ad start playing verification
                ev.verifyEvent("stateChanged - state: PLAYING", " Ad Started to Play ", 30000);

                Thread.sleep(3000);


                // Ad completed verification
                ev.verifyEvent("adCompleted", " Ad Completed to Play ", 30000);

                Thread.sleep(2000);

                ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 30000);


                // video completed event verification
                ev.verifyEvent("playCompleted", " Video Completed Play ", 70000);




            } catch (Exception e) {
                System.out.println(" Exception " + e);
                e.printStackTrace();
                ScreenshotDevice.screenshot(driver);
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
                po.clickBasedOnText(driver, "VAST Ad Wrapper");
                Thread.sleep(2000);

                //verify if player was loaded
                po.waitForPresence(driver, "className", "android.view.View");
                // Assert if current activity is indeed equal to the activity name of the video player
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
                // Print to console output current player activity
                System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
                // Thread.sleep(10000);
                //Play Started Verification
                EventVerification ev = new EventVerification();
                ev.verifyEvent("playStarted", "Video Started to Play", 30000);
                // Click on the web area so that player screen shows up
                WebElement viewarea = driver.findElementByClassName("android.view.View");
                viewarea.click();

                // Tap coordinates to pause
                String dimensions = driver.manage().window().getSize().toString();
                //System.out.println(" Dimensions are "+dimensions);
                String[] dimensionsarray = dimensions.split(",");
                int length = dimensionsarray[1].length();
                String ydimensions = dimensionsarray[1].substring(0, length - 1);
                String ydimensionstrimmed = ydimensions.trim();
                int ydimensionsInt = Integer.parseInt(ydimensionstrimmed);
                driver.tap(1, 35, (ydimensionsInt - 25), 0);
                ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);

                // After pausing clicking on recent app button and getting sample app back
                po.getBackFromRecentApp(driver);

                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 40000);

                Thread.sleep(2000);

                po.powerKeyClick(driver);

                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 40000);

                Thread.sleep(2000);

                // move to full screen
                po.gotoFullScreen(driver);

                ev.verifyEvent("stateChanged - state: SUSPENDED","Player moved in full screen",40000);

                Thread.sleep(1000);

                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 40000);

                Thread.sleep(2000);

                po.gotoNormalScreen(driver);

                ev.verifyEvent("stateChanged - state: SUSPENDED", "Player moved in normal screen", 30000);

                Thread.sleep(1000);

                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 40000);

                Thread.sleep(2000);

                po.getXYSeekBarAndSeek(driver, 155, 50);
                ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 30000);

                Thread.sleep(10000);

                po.playInNormalScreen(driver);
                ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 30000);

                Thread.sleep(5000);

                po.powerKeyClick(driver);

                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 40000);

                Thread.sleep(5000);

                po.getBackFromRecentApp(driver);


                ev.verifyEvent("playCompleted", " Video Completed Play ", 70000);


            } catch (Exception e) {
                System.out.println(" Exception " + e);
                e.printStackTrace();
                ScreenshotDevice.screenshot(driver);
            }
        }

    @org.testng.annotations.Test
    public void OoyalaAdPreroll() throws Exception {

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
            po.clickBasedOnTextScrollTo(driver, "Ooyala Ad Pre-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            Thread.sleep(3000);
            EventVerification ev = new EventVerification();
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
            Thread.sleep(2000);
            // Tap coordinates to pause
            String dimensions = driver.manage().window().getSize().toString();
            //System.out.println(" Dimensions are "+dimensions);
            String[] dimensionsarray = dimensions.split(",");
            int length = dimensionsarray[1].length();
            String ydimensions = dimensionsarray[1].substring(0, length - 1);
            String ydimensionstrimmed = ydimensions.trim();
            int ydimensionsInt = Integer.parseInt(ydimensionstrimmed);
            driver.tap(1, 35, (ydimensionsInt - 25), 0);
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);
            Thread.sleep(2000);

            // After pausing clicking on recent app button and getting sample app back
            po.getBackFromRecentApp(driver);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 40000);

            Thread.sleep(2000);

            po.powerKeyClick(driver);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 40000);

            Thread.sleep(2000);

            po.clickLearnMore(driver);

            ev.verifyEvent("stateChanged - state: SUSPENDED", "clicked on learn more", 40000);

            Thread.sleep(2000);

            // navigating back to SDK
            driver.navigate().back();

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 40000);

            Thread.sleep(2000);

            // playing asset in normal screen
            po.playInNormalScreen(driver);

            // AD playing started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 40000);
            Thread.sleep(3000);

            // ad completed event verificaiton
            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 40000);

            // video playing started again event verification
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 40000);

            Thread.sleep(1000);

            po.powerKeyClick(driver);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 40000);

            Thread.sleep(2000);

            po.getBackFromRecentApp(driver);


            // video completed event verificaiton
            ev.verifyEvent("playCompleted", " Video Completed Play ", 70000);

        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }


    }

    @org.testng.annotations.Test
    public void OoyalaAdMidroll() throws Exception {

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
            po.clickBasedOnTextScrollTo(driver, "Ooyala Ad Mid-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            Thread.sleep(3000);
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
            Thread.sleep(11000);
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
            Thread.sleep(2000);

            String dimensions = driver.manage().window().getSize().toString();
            //System.out.println(" Dimensions are "+dimensions);
            String[] dimensionsarray = dimensions.split(",");
            int length = dimensionsarray[1].length();
            String ydimensions = dimensionsarray[1].substring(0, length - 1);
            String ydimensionstrimmed = ydimensions.trim();
            int ydimensionsInt = Integer.parseInt(ydimensionstrimmed);
            driver.tap(1, 35, (ydimensionsInt - 25), 0);
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Ad Was Paused ", 30000);

            Thread.sleep(2000);

            // After pausing clicking on recent app button and getting sample app back
            po.getBackFromRecentApp(driver);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 40000);

            Thread.sleep(2000);

            po.powerKeyClick(driver);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 40000);

            Thread.sleep(2000);

         // clicking on learn more
            po.clickLearnMore(driver);

            // verifing event for learn more
            ev.verifyEvent("stateChanged - state: SUSPENDED", "clicked on learn more", 30000);

            Thread.sleep(2000);

            // nagivating back to SDK
            driver.navigate().back();

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 40000);

            Thread.sleep(10000);

            // playing asset in normal screen
            po.playInNormalScreen(driver);

            // verifing event for ad playing started
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
            Thread.sleep(3000);

            // AD completed event  verification
            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 30000);

            // event verification of video is start playing
           ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 30000);

            Thread.sleep(1000);

            po.powerKeyClick(driver);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 40000);

            Thread.sleep(3000);

            po.getBackFromRecentApp(driver);

            // video completed event verification
            ev.verifyEvent("playCompleted", " Video Completed Play ", 70000);

        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }


    }



    @org.testng.annotations.Test
    public void OoyalaAdPostroll() throws Exception {

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
            po.clickBasedOnTextScrollTo(driver, "Ooyala Ad Post-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            Thread.sleep(2000);
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

             po.powerKeyClick(driver);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            Thread.sleep(2000);

            po.getBackFromRecentApp(driver);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);



            // event verification for ad started
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
            Thread.sleep(1500);


            String dimensions = driver.manage().window().getSize().toString();
            //System.out.println(" Dimensions are "+dimensions);
            String[] dimensionsarray = dimensions.split(",");
            int length = dimensionsarray[1].length();
            String ydimensions = dimensionsarray[1].substring(0, length - 1);
            String ydimensionstrimmed = ydimensions.trim();
            int ydimensionsInt = Integer.parseInt(ydimensionstrimmed);

            // Pausing the video
            driver.tap(1, 35, (ydimensionsInt - 25), 0);

            // event verification for pause
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);

            // After pausing clicking on recent app button and getting sample app back
            po.getBackFromRecentApp(driver);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            Thread.sleep(2000);

            po.powerKeyClick(driver);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            Thread.sleep(2000);

           // Clicking on learn more button
            po.clickLearnMore(driver);

            // event verification for learn more
            ev.verifyEvent("stateChanged - state: SUSPENDED", "clicked on learn more", 30000);

            Thread.sleep(2000);

            //Navigating back to SDK
            driver.navigate().back();

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            Thread.sleep(5000);

            //Playing in normal state
            po.playInNormalScreen(driver);

            // Ad started again event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
            Thread.sleep(3000);

            // AD completed event verificatio
            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 30000);

            // video completed event verification
            ev.verifyEvent("playCompleted", " Video Completed Play ", 70000);



        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }


    }



    @org.testng.annotations.Test
    public void VASTAdPreRollTest() throws Exception {

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
            po.clickBasedOnTextScrollTo(driver, "VAST Ad Pre-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            Thread.sleep(2000);

            EventVerification ev = new EventVerification();
            ev.verifyEvent("adStarted"," Ad Started to Play ", 30000);

            Thread.sleep(2000);

            String dimensions = driver.manage().window().getSize().toString();
            //System.out.println(" Dimensions are "+dimensions);
            String[] dimensionsarray=dimensions.split(",");
            int length = dimensionsarray[1].length();
            String ydimensions=dimensionsarray[1].substring(0,length-1);
            String ydimensionstrimmed=ydimensions.trim();
            int ydimensionsInt= Integer.parseInt(ydimensionstrimmed);

            driver.tap(1, 35 , (ydimensionsInt-25), 0);

            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Ad Was Paused ", 30000);

            Thread.sleep(2000);

            // After pausing clicking on recent app button and getting sample app back
            po.getBackFromRecentApp(driver);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            Thread.sleep(2000);

            po.powerKeyClick(driver);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            Thread.sleep(2000);


            po.clickLearnMore(driver);
            ev.verifyEvent("stateChanged - state: SUSPENDED", "clicked on learn more",30000);
            Thread.sleep(2000);

            driver.navigate().back();

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            Thread.sleep(4000);

            po.playInNormalScreen(driver);

            ev.verifyEvent("adStarted"," Ad Started to Play ", 30000);

            Thread.sleep(3000);



            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 30000);

            Thread.sleep(1000);

            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 30000);


            po.powerKeyClick(driver);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            Thread.sleep(5000);

            po.getBackFromRecentApp(driver);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);

        }
        catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }

    }


    @org.testng.annotations.Test

    public void VASTAdMidroll() throws Exception {

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
            po.clickBasedOnText(driver, "VAST Ad Mid-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", "Video Started to Play", 30000);

            Thread.sleep(11000);

            ev.verifyEvent("adStarted"," Ad Started to Play ", 30000);

            Thread.sleep(1000);

            // Tap coordinates to pause
            String dimensions = driver.manage().window().getSize().toString();
            //System.out.println(" Dimensions are "+dimensions);
            String[] dimensionsarray = dimensions.split(",");
            int length = dimensionsarray[1].length();
            String ydimensions = dimensionsarray[1].substring(0, length - 1);
            String ydimensionstrimmed = ydimensions.trim();
            int ydimensionsInt = Integer.parseInt(ydimensionstrimmed);

            driver.tap(1, 35, (ydimensionsInt - 25), 0);

            ev.verifyEvent("stateChanged - state: PAUSED", " Playing ad Was Paused ", 30000);

            Thread.sleep(2000);

            // After pausing clicking on recent app button and getting sample app back
            po.getBackFromRecentApp(driver);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            Thread.sleep(2000);

            po.powerKeyClick(driver);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            Thread.sleep(2000);

            po.clickLearnMore(driver);
            ev.verifyEvent("stateChanged - state: SUSPENDED", "clicked on learn more",30000);
            Thread.sleep(2000);

            driver.navigate().back();

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            Thread.sleep(10000);


            po.playInNormalScreen(driver);

            ev.verifyEvent("adStarted"," Ad Started to Play ", 30000);


            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 30000);

            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 30000);

            Thread.sleep(2000);

            po.powerKeyClick(driver);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            Thread.sleep(3000);

            po.getBackFromRecentApp(driver);


            ev.verifyEvent("playCompleted", " Video Completed Play ", 70000);



        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }
    }



    @org.testng.annotations.Test
    public void VASTAdPostroll() throws Exception {

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
            po.clickBasedOnText(driver, "VAST Ad Post-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", "Video Started to Play", 30000);

            po.powerKeyClick(driver);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            Thread.sleep(2000);

            po.getBackFromRecentApp(driver);

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);


            ev.verifyEvent("adStarted"," Ad Started to Play ", 40000);
            Thread.sleep(2000);

            // Tap coordinates to pause
            String dimensions = driver.manage().window().getSize().toString();
            //System.out.println(" Dimensions are "+dimensions);
            String[] dimensionsarray = dimensions.split(",");
            int length = dimensionsarray[1].length();
            String ydimensions = dimensionsarray[1].substring(0, length - 1);
            String ydimensionstrimmed = ydimensions.trim();
            int ydimensionsInt = Integer.parseInt(ydimensionstrimmed);
            driver.tap(1, 35, (ydimensionsInt - 25), 0);

            // event verification for pause
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Ad Was Paused ", 40000);

            Thread.sleep(1000);
            // After pausing clicking on recent app button and getting sample app back
            po.getBackFromRecentApp(driver);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 40000);

            Thread.sleep(2000);

            po.powerKeyClick(driver);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 40000);

            Thread.sleep(2000);
            //clicking on learn more
            po.clickLearnMore(driver);
            Thread.sleep(2000);

            // verifing event for learn more
            ev.verifyEvent("stateChanged - state: SUSPENDED", "clicked on learn more",40000);

            //  navigating back to SDK
            driver.navigate().back();

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 40000);
            Thread.sleep(10000);

            //playing again in normal screen
            po.playInNormalScreen(driver);

            //verifing event for Ad start playing
            ev.verifyEvent("adStarted"," Ad Started to Play ", 40000);
            Thread.sleep(3000);



            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 40000);

            ev.verifyEvent("playCompleted", " Video Completed Play ", 40000);



        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
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
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", "Video Started to Play", 30000);
            // Click on the web area so that player screen shows up
            WebElement viewarea = driver.findElementByClassName("android.view.View");
            viewarea.click();

            // Tap coordinates to pause
            String dimensions = driver.manage().window().getSize().toString();
            //System.out.println(" Dimensions are "+dimensions);
            String[] dimensionsarray = dimensions.split(",");
            int length = dimensionsarray[1].length();
            String ydimensions = dimensionsarray[1].substring(0, length - 1);
            String ydimensionstrimmed = ydimensions.trim();
            int ydimensionsInt = Integer.parseInt(ydimensionstrimmed);
            driver.tap(1, 35, (ydimensionsInt - 25), 0);
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);

            Thread.sleep(2000);

            // After pausing clicking on recent app button and getting sample app back
            po.getBackFromRecentApp(driver);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            Thread.sleep(3000);

            po.powerKeyClick(driver);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            Thread.sleep(5000);

            po.getXYSeekBarAndSeek(driver, 155, 50);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 30000);

            Thread.sleep(10000);


            po.playInNormalScreen(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 30000);

            Thread.sleep(5000);

            po.powerKeyClick(driver);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            Thread.sleep(5000);

            po.getBackFromRecentApp(driver);



            ev.verifyEvent("playCompleted", " Video Completed Play ", 70000);


        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }
    }

}






