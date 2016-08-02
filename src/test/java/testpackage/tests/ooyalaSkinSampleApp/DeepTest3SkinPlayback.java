package testpackage.tests.ooyalaSkinSampleApp;

/**
 * Created by Shivam on 26/05/16.
 */



import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import testpackage.pageobjects.ooyalaSkinSampleApp;
import testpackage.utils.*;

import java.io.IOException;
import java.util.Properties;
public class DeepTest3SkinPlayback extends EventLogTest{


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
    public void afterMethod(ITestResult result) throws Exception {
        // Waiting for all the events from sdk to come in .
        System.out.println("AfterMethod \n");
        //ScreenshotDevice.screenshot(driver);
        RemoveEventsLogFile.removeEventsFileLog();
        Thread.sleep(10000);

    }

// Have to file the issue its Bug //
   /* @org.testng.annotations.Test
    public void VastPoddedAd() throws Exception {

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
            po.clickBasedOnText(driver, "VAST 3.0 2 Podded Ad");
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
            ev.verifyEvent("adStarted", " Ad Started to Play ", 10000);

            //Thread sleep time is equivalent to the length of the AD
            Thread.sleep(1000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 20000);

            //Time out
            Thread.sleep(1000);
            ev.verifyEvent("adStarted", " Ad Started to Play ", 20000);

            //Thread sleep time is equivalent to the length of the AD
            Thread.sleep(5000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 20000);

            ev.verifyEvent("playStarted", " Video Started to Play ", 50000);

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


        } catch (Exception e) {
            System.out.println("VastPoddedAd throws Exception " + e);
             e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VastPoddedAd");
            Assert.assertTrue(false, "This will fail!");
        }


    }
*/

  @org.testng.annotations.Test
    public void VastAdWtihNewEvents() throws Exception {

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
            po.clickBasedOnText(driver, "VAST 3.0 Ad With All Of The New Events");
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
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);

            //Time out
            Thread.sleep(1000);

            ev.verifyEvent("playStarted", " Video Started to Play ", 50000);

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


        } catch (Exception e) {
            System.out.println("VastAdWtihNewEvents throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VastAdWtihNewEvents");
            Assert.assertTrue(false, "This will fail!");
        }


    }


    @org.testng.annotations.Test
    public void VastAdWithIcon() throws Exception {

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
            po.clickBasedOnText(driver, "VAST 3.0 Ad With Icon");
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
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 20000);

            //Time out
            Thread.sleep(1000);

            ev.verifyEvent("playStarted", " Video Started to Play ", 50000);

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


        } catch (Exception e) {
            System.out.println("VastAdWithIcon throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VastAdWithIcon");
            Assert.assertTrue(false, "This will fail!");
        }


    }


    @org.testng.annotations.Test
    public void VastSkippableAd() throws Exception {

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
            po.clickBasedOnTextScrollTo(driver, "VAST 3.0 Skippable Ad");
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


        } catch (Exception e) {
            System.out.println("VastSkippableAd throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VastSkippableAd");
            Assert.assertTrue(false, "This will fail!");
        }


    }


    @org.testng.annotations.Test
    public void VastSkippableAdLong() throws Exception {

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
            po.clickBasedOnTextScrollTo(driver, "VAST 3.0 Skippable Ad Long");
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
            Thread.sleep(8000);

            // Pause the Ad//
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

            //Tap on Skip button //
            po.clickBasedOnText(driver, "Skip Ad");
            ev.verifyEvent("adSkipped", "Ad is skipped", 30000);


            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);

            ev.verifyEvent("playStarted", " Video Started to Play ", 50000);

            //Timeout for the duration of the video


            //Wait for video to finish and verify the playCompleted event .
            // ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);

            Thread.sleep(2000);
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
            ev.verifyEvent("stateChanged - state: PLAYING", " Video resume its playback ", 100000);
            // Thread.sleep(60000);
            ev.verifyEvent("playCompleted", " Video Completed Play ", 200000);


        } catch (Exception e) {
            System.out.println("VastSkippableAdLong throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VastSkippableAdLong");
            Assert.assertTrue(false, "This will fail!");
        }


    }


   /*@org.testng.annotations.Test
    public void VAMPPreMidPostSingle() throws Exception {


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
            po.clickBasedOnTextScrollTo(driver, "VMAP PreMidPost Single");
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

            Thread.sleep(8000);

            // Pause the Ad//
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

            //Tap on Skip button //
            po.clickBasedOnText(driver, "Skip Ad");
            ev.verifyEvent("adSkipped", "Ad is skipped", 30000);


            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);

            ev.verifyEvent("playStarted", " Video Started to Play ", 50000);

            //Timeout for the duration of the video


            //Wait for video to finish and verify the playCompleted event .
            // ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);

            Thread.sleep(2000);
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
            ev.verifyEvent("stateChanged - state: PLAYING", " Video resume its playback ", 100000);

            //Ad Started Verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 200000);

            Thread.sleep(8000);

            // Pause the Ad//
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

            //Tap on Skip button //
            po.clickBasedOnText(driver, "Skip Ad");
            ev.verifyEvent("adSkipped", "Ad is skipped", 300000);


            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 300000);

            ev.verifyEvent("playStarted", " Video Started to Play ", 500000);

            //Timeout for the duration of the video
            Thread.sleep(1000);
            ev.verifyEvent("adStarted", " Ad Started to Play ", 500000);

            Thread.sleep(8000);

            // Pause the Ad//
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

            //Tap on Skip button //
            po.clickBasedOnText(driver, "Skip Ad");
            ev.verifyEvent("adSkipped", "Ad is skipped", 700000);


            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 700000);
            ev.verifyEvent("playCompleted", " Video Completed Play ", 800000);


        } catch (Exception e) {
            System.out.println("VAMPPreMidPostSingle throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VAMPPreMidPostSingle");
            Assert.assertTrue(false, "This will fail!");
        }
    }*/
}

















