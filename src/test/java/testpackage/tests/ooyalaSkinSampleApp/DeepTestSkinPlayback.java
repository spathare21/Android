package testpackage.tests.ooyalaSkinSampleApp;

/**
 * Created by Sameer on 4/29/2016.
 */

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import sun.awt.windows.ThemeReader;
import testpackage.pageobjects.ooyalaSkinSampleApp;
import testpackage.utils.*;

import java.io.IOException;
import java.util.Properties;

public class DeepTestSkinPlayback extends EventLogTest{

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


    @org.testng.annotations.Test
    public void AspectRatioTest() throws Exception {
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
            po.clickBasedOnText(driver, "4:3 Aspect Ratio");
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
            ev.verifyEvent("playStarted", " Video Started to Play ", 50000);

            // Click on the web area so that player screen shows up
            Thread.sleep(2000);
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
            ev.verifyEvent("stateChanged - state: PLAYING", " Video resume its playback ", 100000);
           // Thread.sleep(60000);
            ev.verifyEvent("playCompleted", " Video Completed Play ", 200000);

            //     Extra Work //
            /*driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

            // Pause state verification
            ev.verifyEvent("PAUSED", " Playing Video Was Paused ", 30000);

            //    end  //
            System.out.println("Looking for Discovery");
            po.discoverUpNext(driver);
            Thread.sleep(2000);

            //Clicking on close button
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

            // Tap coordinates again to play
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

            // Play state verification
            ev.verifyEvent("stateChanged - state: PLAYING", " Video resume its playback ", 30000);
            Thread.sleep(2000);


            //Clicking on close button
            *//*po.discoverElement(driver);  //Wait till it found
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            // Restarting video//

            po.clickBasedOnText(driver, "c");
            //Thread.sleep(5000);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video replay start ", 70000);

            //Wait for video to finish and verify the playCompleted event .
           // ev.verifyEvent("playCompleted", " Video Completed Play ", 30000);*/


        } catch (Exception e) {
            System.out.println("AspectRatioTest throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"AspectRatioTest");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void SkinPlaybackMP4Video() throws Exception {
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
            po.clickBasedOnText(driver, "MP4 Video");
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

            //Click on the web area so that player screen shows up
            Thread.sleep(2000);
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

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

            //Click on Share button
            po.clickBasedOnText(driver, "o");
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: SUSPENDED", " Share asset ", 70000);

            //Sharing on Gmail
            po.shareOnGmail(driver);
            Thread.sleep(1000);
            ev.verifyEvent("READY", " Mail sent, Back to SDK ", 70000);
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
            Thread.sleep(1000);

            // Tap coordinates again to play
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

            // Play state verification
            ev.verifyEvent("stateChanged - state: PLAYING", " Video resume its playback ", 30000);

            ev.verifyEvent("playCompleted", " Video Completed Play ", 200000);

            /* Thread.sleep(30000);
            //  Same Logic As Above //
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

            // Pause state verification
            ev.verifyEvent("PAUSED", " Playing Video Was Paused ", 30000);

            //    end  //
            System.out.println("Looking for Discovery");
            po.discoverUpNext(driver);
            Thread.sleep(2000);

            //Clicking on close button
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

            // Tap coordinates again to play
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

            // Play state verification
            ev.verifyEvent("stateChanged - state: PLAYING", " Video resume its playback ", 30000);
            Thread.sleep(2000);


            //Clicking on close button
            po.discoverElement(driver);  //Wait till it found
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            // Restarting video//

            po.clickBasedOnText(driver, "c");
            //Thread.sleep(5000);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video replay start ", 70000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 150000);

*/
        } catch (Exception e) {
            System.out.println("SkinPlaybackMP4Video throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"SkinPlaybackMP4Video");
            Assert.assertTrue(false, "This will fail!");
        }
    }

  @org.testng.annotations.Test
    public void SkinPlaybackHLSVideo() throws Exception {
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
            po.clickBasedOnText(driver, "HLS Video");
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

            // Tap to pause the video //
            Thread.sleep(2000);
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(1000);

            //  Tap again //
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);


            // Verify the pause event //
            ev.verifyEvent("PAUSED", " Video is paused ", 30000);

            // Get from Recent app //
            po.getBackFromRecentApp(driver);

            // Verify player ready event //
            ev.verifyEvent("READY", "Now player is ready ", 30000);
            po.powerKeyClick(driver);
            //ev.verifyEvent("READY" , "Now player is ready", 30000);

          //  Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(2000);

            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(1000);

            //Click on option button
            po.clickBasedOnText(driver, "f");
            Thread.sleep(2000);

            //Click on Share button
            po.clickBasedOnText(driver, "o");
            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: SUSPENDED", " Share asset ", 70000);

            //Sharing on Gmail
            po.shareOnGmail(driver);
            Thread.sleep(1000);
            ev.verifyEvent("READY", " Mail sent, Back to SDK ", 70000);
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
            Thread.sleep(1000);

            // Tap coordinates again to play
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

            // Play state verification
            ev.verifyEvent("stateChanged - state: PLAYING", " Video resume its playback ", 30000);
           /* Thread.sleep(50000);

            // Now again tap to pause the video //
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);

            // Verify for Pause Event //
            ev.verifyEvent("PAUSED", " Video had been paused ", 30000);

            // Verify for Discover Pop Up window //
            System.out.println("Will now verify for discovery");
            po.discoverUpNext(driver);
            Thread.sleep(2000);

            // Click on cancel button of pop up //
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            // Tap to play the video //
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);

            //Verify the play event of video //
            ev.verifyEvent("PLAYING", "Video resume its playback", 30000);

            // Wait for the discovery window at the end of video //
            po.discoverElement(driver);  // Wait till discovery elemnt is found //
            Thread.sleep(1000);

            // Click on Cancel button of Discovery Tray //
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            // Clicking to Restart the video //

            po.clickBasedOnText(driver, "c");
            //Thread.sleep(5000);

            // Verify for Replay Event //
            ev.verifyEvent("stateChanged - state: PLAYING", " Video replay start ", 70000);*/

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 150000);


        } catch (Exception e) {
            System.out.println("SkinPlaybackHLSVideo throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"SkinPlaybackHLSVideo");
            Assert.assertTrue(false, "This will fail!");
        }

    }

    @org.testng.annotations.Test
    public void SkinPlaybackVODCCVideo() throws Exception {
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
            po.clickBasedOnText(driver, "VOD with CCs");
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

            // Tap to pause the video //
            Thread.sleep(2000);
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(1000);

            //  Tap again //
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);


            // Verify the pause event //
            ev.verifyEvent("PAUSED", " Video is paused ", 30000);

            // Get from Recent app //
            po.getBackFromRecentApp(driver);

            // Verify player ready event //
            ev.verifyEvent("READY", "Now player is ready ", 30000);
            po.powerKeyClick(driver);
            //ev.verifyEvent("READY" , "Now player is ready", 30000);

            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 3000);
            Thread.sleep(2000);

            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(1000);


            //Click on option button
            po.clickBasedOnText(driver, "f");
            Thread.sleep(2000);

            //Click on Share button
            po.clickBasedOnText(driver, "o");
            Thread.sleep(2000);
            ev.verifyEvent("SUSPENDED", " Share asset ", 30000);

            // Video Sharing Through Gmail //
            po.shareOnGmail(driver);
            Thread.sleep(1000);

            // Verify the sent event //
            ev.verifyEvent("READY", " Mail sent, Back to SDK ", 50000);
            Thread.sleep(2000);

            // Clicking on Discovery Event //
            System.out.println(" Clicking on Discovery ");
            po.clickBasedOnText(driver, "l");
            Thread.sleep(2000);

            // Clicking on  Cancel button  of discovery tray //
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            // Clicking on CC button //
            System.out.println("Clicking on CC");
            po.clickBasedOnText(driver, "k");
            Thread.sleep(2000);

            // Clicking on Cancel button of CC Window //
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            //Clicking on Cancel Button of Option Screen //
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            // Tap on the Screen //
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

            // Tap once again to play the video //
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

            // Verify the video resume event //
            ev.verifyEvent("PLAYING ", "Video is resume", 30000);
           // Thread.sleep(50000);

           // Now again tap to pause the video //
           /* driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);

            // Verify for Pause Event //
            ev.verifyEvent("PAUSED", " Video had been paused ", 30000);

            // Verify for Discover Pop Up window //
            System.out.println("Will now verify for discovery");
            po.discoverUpNext(driver);
            Thread.sleep(2000);

            // Click on cancel button of pop up //
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            // Tap to play the video //
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);

            //Verify the play event of video //
            ev.verifyEvent("PLAYING", "Video resume its playback", 20000);

            // Wait for the discovery window at the end of video //
            po.discoverElement(driver);  // Wait till discovery elemnt is found //
            Thread.sleep(1000);

            // Click on Cancel button of Discovery Tray //
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            // Clicking to Restart the video //

            po.clickBasedOnText(driver, "c");
            //Thread.sleep(5000);

            // Verify for Replay Event //
            ev.verifyEvent("stateChanged - state: PLAYING", " Video replay start ", 70000);*/

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 50000);


        } catch (Exception e) {
            System.out.println("SkinPlaybackVODCCVideo throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"SkinPlaybackVODCCVideo");
            Assert.assertTrue(false, "This will fail!");
        }

    }

    @org.testng.annotations.Test
    public void SkinPlayBackVAST2ADPreroll() throws Exception {
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

            // Verify the ad event  //
            EventVerification ev = new EventVerification();
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            Thread.sleep(5000);

            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);

            Thread.sleep(5000);
            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 2000);


            // Tap to pause the video //
            //Thread.sleep(2000);
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

            //  Tap again //
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);


            // Verify the pause event //
            ev.verifyEvent("PAUSED", " Video is paused ", 30000);

            // Get from Recent app //
            po.getBackFromRecentApp(driver);

            // Verify player ready event //
            ev.verifyEvent("READY", "Now player is ready ", 30000);
            po.powerKeyClick(driver);
            //ev.verifyEvent("READY" , "Now player is ready", 30000);

            Thread.sleep(2000);
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
            Thread.sleep(2000);

            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(1000);
         //   driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
         //   Thread.sleep(1000);



             //Click on option button
            po.clickBasedOnText(driver, "f");
            Thread.sleep(2000);

            //Click on Share button
            po.clickBasedOnText(driver, "o");
            Thread.sleep(2000);
            ev.verifyEvent("SUSPENDED", " Share asset ", 30000);

            // Video Sharing Through Gmail //
            po.shareOnGmail(driver);
            Thread.sleep(1000);

            // Verify the sent event //
            ev.verifyEvent("READY", " Mail sent, Back to SDK ", 70000);
            Thread.sleep(2000);

            // Clicking on Discovery Event //
            System.out.println(" Clicking on Discovery ");
            po.clickBasedOnText(driver, "l");
            Thread.sleep(2000);

            // Clicking on  Cancel button  of discovery tray //
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            // Clicking on CC button //
            System.out.println("Clicking on CC");
            po.clickBasedOnText(driver, "k");
            Thread.sleep(2000);

            // Clicking on Cancel button of CC Window //
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            //Clicking on Cancel Button of Option Screen //
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            // Tap on the Screen //
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

            // Tap once again to play the video //
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(2000);

            // Verify the video resume event //
            ev.verifyEvent("PLAYING ", "Video is resume", 30000);
            //Thread.sleep(50000);

            // Now again tap to pause the video //
           /* driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(1000);
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);


            // Verify for Pause Event //
            ev.verifyEvent("PAUSED", " Video had been paused ", 30000);

            // Verify for Discover Pop Up window //
            System.out.println("Will now verify for discovery");
            po.discoverUpNext(driver);
            Thread.sleep(2000);

            // Click on cancel button of pop up //
            po.clickBasedOnText(driver, "e");
            Thread.sleep(1000);

            // Tap to play the video //
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);
            Thread.sleep(1000);
            driver.tap(1, locationPlayButton[0], locationPlayButton[1], 2);

            //Verify the play event of video //
            ev.verifyEvent("PLAYING", "Video resume its playback", 2000);

            // Wait for the discovery window at the end of video //
            po.discoverElement(driver);  // Wait till discovery elemnt is found //
            Thread.sleep(1000);

            // Click on Cancel button of Discovery Tray //
            po.clickBasedOnText(driver, "e");
            Thread.sleep(2000);

            // Clicking to Restart the video //

            po.clickBasedOnText(driver, "c");
            //Thread.sleep(5000);

            // Verify for Replay Event //
            ev.verifyEvent("stateChanged - state: PLAYING", " Video replay start ", 70000);*/
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 50000);


        } catch (Exception e) {
            System.out.println("SkinPlayBackVAST2ADPreroll throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"SkinPlayBackVAST2ADPreroll");
            Assert.assertTrue(false, "This will fail!");
        }


    }


}







