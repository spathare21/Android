package testpackage.tests.ooyalaSkinSampleApp;

/**
 * Created by Shivam on 26/05/16.
 *
 *
 */

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;
import sun.awt.windows.ThemeReader;
import testpackage.pageobjects.ooyalaSkinSampleApp;
import testpackage.utils.*;

import java.io.IOException;
import java.util.Properties;

public class DeepTest2SkinPlayback {

    private static AndroidDriver driver;

    @BeforeClass
    public void beforeTest() throws Exception {

        // closing all recent app from background.
        //CloserecentApps.closeApps();
        System.out.println("BeforeTest \n");

        System.out.println(System.getProperty("user.dir"));
        // Get Property Values
        LoadPropertyValues prop = new LoadPropertyValues();
        Properties p = prop.loadProperty("ooyalaSkinSampleApp.properties");

        System.out.println("Device id from properties file " + p.getProperty("deviceName"));
        System.out.println("PortraitMode from properties file " + p.getProperty("PortraitMode"));
        System.out.println("Path where APK is stored" + p.getProperty("appDir"));
        System.out.println("APK name is " + p.getProperty("app"));
        System.out.println("Platform under Test is " + p.getProperty("platformName"));
        System.out.println("Mobile OS Version is " + p.getProperty("OSVERSION"));
        System.out.println("Package Name of the App is " + p.getProperty("appPackage"));
        System.out.println("Activity Name of the App is " + p.getProperty("appActivity"));

        SetUpAndroidDriver setUpdriver = new SetUpAndroidDriver();
        driver = setUpdriver.setUpandReturnAndroidDriver(p.getProperty("udid"), p.getProperty("appDir"), p.getProperty("appValue"), p.getProperty("platformName"), p.getProperty("platformVersion"), p.getProperty("appPackage"), p.getProperty("appActivity"));
        Thread.sleep(2000);
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        System.out.println("beforeMethod \n");
        //removeEventsLogFile.removeEventsFileLog(); create events file
        PushLogFileToDevice logpush = new PushLogFileToDevice();
        logpush.pushLogFile();
        if (driver.currentActivity() != "com.ooyala.sample.complete.MainActivity") {
            driver.startActivity("com.ooyala.sample.SkinCompleteSampleApp", "com.ooyala.sample.complete.MainActivity");
        }

        // Get Property Values
        LoadPropertyValues prop1 = new LoadPropertyValues();
        Properties p1 = prop1.loadProperty();

        System.out.println(" Screen Mode " + p1.getProperty("ScreenMode"));

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
        Thread.sleep(5000);
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
    public void VastAdPreroll () throws Exception{

        int[] locationPlayButton;

        try {

            // Creating an Object of SkinSampleApp class
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

            System.out.println(" Print current activity name" + driver.currentActivity());
            if (driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")) {
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);
            }

            // Assert if current activity is Skin Playback list activity
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OoyalaSkinListActivity");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "VAST2 Ad Pre-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver, "h");

            locationPlayButton = po.locationTextOnScreen(driver, "h");

            //Clicking on Play button in Ooyala Skin
            po.clickBasedOnText(driver, "h");

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("adStarted", " Ad Started to Play ", 20000);

            //Thread sleep time is equivalent to the length of the AD
            Thread.sleep(5000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 20000);

            //Time out
            Thread.sleep(1000);

            //Play Started
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //Timeout for the duration of the video
            Thread.sleep(1000);

            //Wait for video to finish and verify the playCompleted event .
           // ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);

            Thread.sleep(1000);
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(1000);

            //  Tap again //

            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);



            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 50000);

            // Pause the running of the test for a brief amount of time
            Thread.sleep(3000);

            po.getBackFromRecentApp(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

            po.powerKeyClick(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

            Thread.sleep(5000);

            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(1000);

            //Click on option button
            po.clickBasedOnText(driver, "f");
            Thread.sleep(2000);

            //Click on Discovery button
            po.clickBasedOnText(driver, "o");
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: SUSPENDED", " Share asset ", 70000);

            //Sharing on Gmail
            po.shareOnGmail(driver);
            Thread.sleep(1000);
            ev.verifyEvent("state: READY", " Mail sent, Back to SDK ", 70000);
            // ev.verifyEvent("stateChanged - state: READY", " Mail sent, Back to SDK ", 70000);
            Thread.sleep(2000);

            //Clicking on Discovery
            System.out.println("clicking on discovery");
            po.clickBasedOnText(driver, "l");
            Thread.sleep(2000);

            //Clicking on close button
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            //Clicking CC button
            System.out.println("clicking on CC");
            po.clickBasedOnText(driver, "k");
            Thread.sleep(2000);

            //Clicking on close button
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            //Closing option pannel
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

            // Tap coordinates again to play
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

            // Play state verification
            ev.verifyEvent("stateChanged - state: PLAYING", " Video resume its playback ", 100000);
            // Thread.sleep(60000);
            ev.verifyEvent("playCompleted", " Video Completed Play ", 200000);


        }
        catch(Exception e)
        {
            System.out.println("VastAdPreroll throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VastAdPreroll");
        }
    }

   @org.testng.annotations.Test
    public void VastAdMidroll () throws Exception{

        int[] locationPlayButton;

       try {

           // Creating an Object of SkinSampleApp class
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

           System.out.println(" Print current activity name" + driver.currentActivity());
           if (driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")) {
               //Navigate back to Skin playback activity
               driver.navigate().back();
               Thread.sleep(2000);
           }

           // Assert if current activity is Skin Playback list activity
           po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OoyalaSkinListActivity");

           // Select one of the video HLS,MP4 etc .
           po.clickBasedOnText(driver, "VAST2 Ad Mid-roll");
           Thread.sleep(2000);

           //verify if player was loaded
           po.waitForPresence(driver, "className", "android.view.View");
           // Assert if current activity is indeed equal to the activity name of the video player
           po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
           // Print to console output current player activity
           System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

           po.waitForPresenceOfText(driver, "h");

           locationPlayButton = po.locationTextOnScreen(driver, "h");

           //Clicking on Play button in Ooyala Skin
           po.clickBasedOnText(driver, "h");

           //Play Started Verification
           EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 20000);

            //Thread sleep time is equivalent to the length of the half of the video
           // Thread.sleep(1000);

            //Thread.sleep(1000);

            //Wait for video to finish and verify the playCompleted event .
            // ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);

            Thread.sleep(1000);
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(1000);

            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);





            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);

            // Pause the running of the test for a brief amount of time
            Thread.sleep(3000);

            po.getBackFromRecentApp(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

            po.powerKeyClick(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

            Thread.sleep(2000);

            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(1000);

            //Click on option button
            po.clickBasedOnText(driver, "f");
            Thread.sleep(2000);

            //Click on Discovery button
            po.clickBasedOnText(driver, "o");
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: SUSPENDED", " Share asset ", 70000);

            //Sharing on Gmail
            po.shareOnGmail(driver);
            Thread.sleep(1000);
            ev.verifyEvent("state: READY", " Mail sent, Back to SDK ", 70000);
            // ev.verifyEvent("stateChanged - state: READY", " Mail sent, Back to SDK ", 70000);
            Thread.sleep(2000);

            //Clicking on Discovery
            System.out.println("clicking on discovery");
            po.clickBasedOnText(driver, "l");
            Thread.sleep(2000);

            //Clicking on close button
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            //Clicking CC button
            System.out.println("clicking on CC");
            po.clickBasedOnText(driver, "k");
            Thread.sleep(2000);

            //Clicking on close button
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            //Closing option pannel
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

            // Tap coordinates again to play
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

            //Ad Started Verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);

            Thread.sleep(2000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 30000);

            //Thread sleep time is equivalent to the length of the half of the video
            Thread.sleep(1000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);
        }
        catch(Exception e)
        {
            System.out.println("VastAdMidroll throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VastAdMidroll");
        }
    }

    @org.testng.annotations.Test
    public void VastAdPostroll () throws Exception{

        int[] locationPlayButton;

        try {

            // Creating an Object of SkinSampleApp class
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

            System.out.println(" Print current activity name" + driver.currentActivity());
            if (driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")) {
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);
            }

            // Assert if current activity is Skin Playback list activity
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OoyalaSkinListActivity");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "VAST2 Ad Post-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver, "h");

            locationPlayButton = po.locationTextOnScreen(driver, "h");

            //Clicking on Play button in Ooyala Skin
            po.clickBasedOnText(driver, "h");

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //Thread sleep time is equivalent to the length of the video


            Thread.sleep(1000);
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

            //  Tap again //

            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);



            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 40000);

            // Pause the running of the test for a brief amount of time
            Thread.sleep(3000);

            po.getBackFromRecentApp(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

            po.powerKeyClick(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

            Thread.sleep(5000);

            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(1000);

            //Click on option button
            po.clickBasedOnText(driver, "f");
            Thread.sleep(2000);

            //Click on Discovery button
            po.clickBasedOnText(driver, "o");
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: SUSPENDED", " Share asset ", 70000);

            //Sharing on Gmail
            po.shareOnGmail(driver);
            Thread.sleep(1000);
            ev.verifyEvent("state: READY", " Mail sent, Back to SDK ", 70000);
            // ev.verifyEvent("stateChanged - state: READY", " Mail sent, Back to SDK ", 70000);
            Thread.sleep(2000);

            //Clicking on Discovery
            System.out.println("clicking on discovery");
            po.clickBasedOnText(driver, "l");
            Thread.sleep(2000);

            //Clicking on close button
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            //Clicking CC button
            System.out.println("clicking on CC");
            po.clickBasedOnText(driver, "k");
            Thread.sleep(2000);

            //Clicking on close button
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            //Closing option pannel
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

            // Tap coordinates again to play
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

            //Ad Started Verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            Thread.sleep(5000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 30000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);
        }
        catch(Exception e)
        {
            System.out.println("VastAdPostroll throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VastAdPostroll");
        }
    }


    @org.testng.annotations.Test
    public void VastAdWrapper () throws Exception{

        int[] locationPlayButton;

        try {

            // Creating an Object of SkinSampleApp class
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

            System.out.println(" Print current activity name" + driver.currentActivity());
            if (driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")) {
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);
            }

            // Assert if current activity is Skin Playback list activity
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OoyalaSkinListActivity");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "VAST2 Ad Wrapper");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver, "h");

            locationPlayButton = po.locationTextOnScreen(driver, "h");

            //Clicking on Play button in Ooyala Skin
            po.clickBasedOnText(driver, "h");

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            //Thread sleep time is equivalent to the length of the AD
            Thread.sleep(5000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 20000);

            //Time out
            Thread.sleep(1000);

            //Play Started
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //Timeout for the duration of the video
            Thread.sleep(1000);

            //Wait for video to finish and verify the playCompleted event .
            // ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);

            Thread.sleep(1000);
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(1000);

            //  Tap again //

            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);



            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);

            // Pause the running of the test for a brief amount of time
            Thread.sleep(3000);

            po.getBackFromRecentApp(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

            po.powerKeyClick(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

            Thread.sleep(5000);

            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(1000);

            //Click on option button
            po.clickBasedOnText(driver, "f");
            Thread.sleep(2000);

            //Click on Discovery button
            po.clickBasedOnText(driver, "o");
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: SUSPENDED", " Share asset ", 70000);

            //Sharing on Gmail
            po.shareOnGmail(driver);
            Thread.sleep(1000);
            ev.verifyEvent("state: READY", " Mail sent, Back to SDK ", 70000);
            // ev.verifyEvent("stateChanged - state: READY", " Mail sent, Back to SDK ", 70000);
            Thread.sleep(2000);

            //Clicking on Discovery
            System.out.println("clicking on discovery");
            po.clickBasedOnText(driver, "l");
            Thread.sleep(2000);

            //Clicking on close button
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            //Clicking CC button
            System.out.println("clicking on CC");
            po.clickBasedOnText(driver, "k");
            Thread.sleep(2000);

            //Clicking on close button
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            //Closing option pannel
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

            // Tap coordinates again to play
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

            // Play state verification
            ev.verifyEvent("stateChanged - state: PLAYING", " Video resume its playback ", 100000);
            // Thread.sleep(60000);
            ev.verifyEvent("playCompleted", " Video Completed Play ", 200000);


        }
        catch(Exception e)
        {
            System.out.println("VastAdWrapper throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VastAdWrapper");
        }

    }


    @org.testng.annotations.Test
    public void OoyalaAdPreroll () throws  Exception{


        int[] locationPlayButton;

        try {

            // Creating an Object of SkinSampleApp class
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

            System.out.println(" Print current activity name" + driver.currentActivity());
            if (driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")) {
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);
            }

            // Assert if current activity is Skin Playback list activity
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OoyalaSkinListActivity");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Ooyala Ad Pre-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver, "h");

            locationPlayButton = po.locationTextOnScreen(driver, "h");

            //Clicking on Play button in Ooyala Skin
            po.clickBasedOnText(driver, "h");

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            //Thread sleep time is equivalent to the length of the AD
            Thread.sleep(5000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 20000);

            //Time out
            Thread.sleep(1000);

            //Play Started
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //Timeout for the duration of the video


            //Wait for video to finish and verify the playCompleted event .
            // ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);

            Thread.sleep(1000);
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(1000);

            //  Tap again //

            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);



            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);

            // Pause the running of the test for a brief amount of time
            Thread.sleep(3000);

            po.getBackFromRecentApp(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

            po.powerKeyClick(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

            Thread.sleep(5000);

            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(1000);

            //Click on option button
            po.clickBasedOnText(driver, "f");
            Thread.sleep(2000);

            //Click on Discovery button
            po.clickBasedOnText(driver, "o");
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: SUSPENDED", " Share asset ", 70000);

            //Sharing on Gmail
            po.shareOnGmail(driver);
            Thread.sleep(1000);
            ev.verifyEvent("state: READY", " Mail sent, Back to SDK ", 70000);
            // ev.verifyEvent("stateChanged - state: READY", " Mail sent, Back to SDK ", 70000);
            Thread.sleep(2000);

            //Clicking on Discovery
            System.out.println("clicking on discovery");
            po.clickBasedOnText(driver, "l");
            Thread.sleep(2000);

            //Clicking on close button
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            //Clicking CC button
            System.out.println("clicking on CC");
            po.clickBasedOnText(driver, "k");
            Thread.sleep(2000);

            //Clicking on close button
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            //Closing option pannel
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

            // Tap coordinates again to play
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

            // Play state verification
            ev.verifyEvent("stateChanged - state: PLAYING", " Video resume its playback ", 100000);
            // Thread.sleep(60000);
            ev.verifyEvent("playCompleted", " Video Completed Play ", 200000);


        }
        catch(Exception e)
        {
            System.out.println("OoyalaAdPreroll throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"OoyalaAdPreroll");
        }

    }


   @org.testng.annotations.Test
    public void OoyalaAdMidroll () throws  Exception{

        int[] locationPlayButton;

        try {

            // Creating an Object of SkinSampleApp class
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

            System.out.println(" Print current activity name" + driver.currentActivity());
            if (driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")) {
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);
            }

            // Assert if current activity is Skin Playback list activity
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OoyalaSkinListActivity");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Ooyala Ad Mid-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver, "h");

            locationPlayButton = po.locationTextOnScreen(driver, "h");

            //Clicking on Play button in Ooyala Skin
            po.clickBasedOnText(driver, "h");

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 20000);

            //Thread sleep time is equivalent to the length of the half of the video
            // Thread.sleep(1000);

            //Thread.sleep(1000);

            //Wait for video to finish and verify the playCompleted event .
            // ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);


            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

            //  Tap again //
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);







            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);

            // Pause the running of the test for a brief amount of time
            Thread.sleep(3000);

            po.getBackFromRecentApp(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

            po.powerKeyClick(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

            Thread.sleep(2000);

            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(1000);

            //Click on option button
            po.clickBasedOnText(driver, "f");
            Thread.sleep(2000);

            //Click on Discovery button
            po.clickBasedOnText(driver, "o");
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: SUSPENDED", " Share asset ", 70000);

            //Sharing on Gmail
            po.shareOnGmail(driver);
            Thread.sleep(1000);
            ev.verifyEvent("state: READY", " Mail sent, Back to SDK ", 70000);
            // ev.verifyEvent("stateChanged - state: READY", " Mail sent, Back to SDK ", 70000);
            Thread.sleep(2000);

            //Clicking on Discovery
            System.out.println("clicking on discovery");
            po.clickBasedOnText(driver, "l");
            Thread.sleep(2000);

            //Clicking on close button
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            //Clicking CC button
            System.out.println("clicking on CC");
            po.clickBasedOnText(driver, "k");
            Thread.sleep(2000);

            //Clicking on close button
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            //Closing option pannel
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

            // Tap coordinates again to play
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

            //Ad Started Verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            Thread.sleep(2000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 30000);

            //Thread sleep time is equivalent to the length of the half of the video
            Thread.sleep(1000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);
        }
        catch(Exception e)
        {
            System.out.println("OoyalaAdMidroll throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"OoyalaAdMidroll");
        }



    }


    @org.testng.annotations.Test
    public void OoyalaAdPostroll () throws Exception{

        int[] locationPlayButton;

        try {

            // Creating an Object of SkinSampleApp class
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

            System.out.println(" Print current activity name" + driver.currentActivity());
            if (driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")) {
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);
            }

            // Assert if current activity is Skin Playback list activity
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OoyalaSkinListActivity");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Ooyala Ad Post-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver, "h");

            locationPlayButton = po.locationTextOnScreen(driver, "h");

            //Clicking on Play button in Ooyala Skin
            po.clickBasedOnText(driver, "h");

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //Thread sleep time is equivalent to the length of the video


            Thread.sleep(1000);
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

            //  Tap again //

            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);



            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 40000);

            // Pause the running of the test for a brief amount of time
            Thread.sleep(3000);

            po.getBackFromRecentApp(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

            po.powerKeyClick(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

            Thread.sleep(5000);

            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(1000);

            //Click on option button
            po.clickBasedOnText(driver, "f");
            Thread.sleep(2000);

            //Click on Discovery button
            po.clickBasedOnText(driver, "o");
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: SUSPENDED", " Share asset ", 70000);

            //Sharing on Gmail
            po.shareOnGmail(driver);
            Thread.sleep(1000);
            ev.verifyEvent("state: READY", " Mail sent, Back to SDK ", 70000);
            // ev.verifyEvent("stateChanged - state: READY", " Mail sent, Back to SDK ", 70000);
            Thread.sleep(2000);

            //Clicking on Discovery
            System.out.println("clicking on discovery");
            po.clickBasedOnText(driver, "l");
            Thread.sleep(2000);

            //Clicking on close button
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            //Clicking CC button
            System.out.println("clicking on CC");
            po.clickBasedOnText(driver, "k");
            Thread.sleep(2000);

            //Clicking on close button
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            //Closing option pannel
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

            // Tap coordinates again to play
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

            //Ad Started Verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            Thread.sleep(5000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 30000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);
        }
        catch(Exception e)
        {
            System.out.println("OoyalaAdPostroll throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"OoyalaAdPostroll");
        }

    }


    @org.testng.annotations.Test
    public void MultiAdCombination () throws  Exception{

        int[] locationPlayButton;

        try {

            // Creating an Object of SkinSampleApp class
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

            System.out.println(" Print current activity name" + driver.currentActivity());
            if (driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")) {
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);
            }

            // Assert if current activity is Skin Playback list activity
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OoyalaSkinListActivity");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Multi Ad combination");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OoyalaSkinPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver, "h");

            locationPlayButton = po.locationTextOnScreen(driver, "h");

            //Clicking on Play button in Ooyala Skin
            po.clickBasedOnText(driver, "h");

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            //Thread sleep time is equivalent to the length of the AD
            Thread.sleep(5000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 20000);

            //Time out
            Thread.sleep(1000);

            //Play Started
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            Thread.sleep(1000);
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(1000);

            //  Tap again //

            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);



            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);

            // Pause the running of the test for a brief amount of time
            Thread.sleep(3000);

            po.getBackFromRecentApp(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

            po.powerKeyClick(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

            Thread.sleep(2000);

            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(1000);

            //Click on option button
            po.clickBasedOnText(driver, "f");
            Thread.sleep(2000);

            //Click on Discovery button
            po.clickBasedOnText(driver, "o");
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: SUSPENDED", " Share asset ", 70000);

            //Sharing on Gmail
            po.shareOnGmail(driver);
            Thread.sleep(1000);
            ev.verifyEvent("state: READY", " Mail sent, Back to SDK ", 70000);
            // ev.verifyEvent("stateChanged - state: READY", " Mail sent, Back to SDK ", 70000);
            Thread.sleep(2000);

            //Clicking on Discovery
            System.out.println("clicking on discovery");
            po.clickBasedOnText(driver, "l");
            Thread.sleep(2000);

            //Clicking on close button
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            //Clicking CC button
            System.out.println("clicking on CC");
            po.clickBasedOnText(driver, "k");
            Thread.sleep(2000);

            //Clicking on close button
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            //Closing option pannel
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

            // Tap coordinates again to play
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

            // Play state verification
            ev.verifyEvent("stateChanged - state: PLAYING", " Video resume its playback ", 50000);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            Thread.sleep(2000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 30000);

            //Thread sleep time is equivalent to the length of the half of the video
            Thread.sleep(1000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);
        }
        catch(Exception e)
        {
            System.out.println("MultiAdCombination throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"MultiAdCombination");
        }



        }

















}









