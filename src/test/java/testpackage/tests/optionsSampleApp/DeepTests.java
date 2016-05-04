package testpackage.tests.optionsSampleApp;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import testpackage.pageobjects.optionsSampleApp;
import testpackage.utils.*;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Rohan R on 4/29/2016.
 */
public class DeepTests {

    private static AndroidDriver driver;

    @BeforeClass
    public void beforeTest() throws Exception {
        // closing all recent app from background.
        CloserecentApps.closeApps();
        System.out.println("BeforeTest \n");

        System.out.println(System.getProperty("user.dir"));
        // Get Property Values
        LoadPropertyValues prop = new LoadPropertyValues();
        Properties p = prop.loadProperty("optionsSampleApp.properties");

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
        if (driver.currentActivity() != "com.ooyala.sample.lists.OptionsListActivity") {
            driver.startActivity("com.ooyala.sample.OptionsSampleApp", "com.ooyala.sample.lists.OptionsListActivity");
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
        driver.quit();
        LoadPropertyValues prop1 = new LoadPropertyValues();
        Properties p1 = prop1.loadProperty();
        String prop = p1.getProperty("appPackage");
        Appuninstall.uninstall(prop);
    }

//    @org.testng.annotations.Test
//    public void CuePointsAndAdsControlOptions() throws Exception {
//
//        try {
//            // Creating an Object of optionsSampleApp class
//            optionsSampleApp po = new optionsSampleApp();
//            // wait till home screen of basicPlayBackApp is opened
//            po.waitForAppHomeScreen(driver);
//
//            // Assert if current activity is indeed equal to the activity name of app home screen
//            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OptionsListActivity");
//            // Wrire to console activity name of home screen app
//            System.out.println("OptionsSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
//            //Pause the running of test for a brief time .
//            Thread.sleep(3000);
//
//            // Select one of the video HLS,MP4 etc .
//            po.clickBasedOnText(driver, "CuePoints and AdsControl Options");
//            Thread.sleep(2000);
//
//            //verify if player was loaded
//            po.waitForPresence(driver, "className", "android.view.View");
//            // Assert if current activity is indeed equal to the activity name of the video player
//            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.CuePointsOptionsFreewheelPlayerActivity");
//            // Print to console output current player activity
//            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
//
//            //Click on Video create button
//            po.clickButtons(driver, 0);
//
//            // Wait for the video to be generated
//            po.waitForPresenceOfText(driver, "00:00");
//
//            // Click on video play icon after video has been generated .
//            po.clickImagebuttons(driver, 0);
//
//            //Play Started Verification
//            EventVerification ev = new EventVerification();
//
//            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
//
//            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 35000);
//
//            //Wait for video to start and verify the playStarted event .
//            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
//
//            Thread.sleep(5000);
//
//            //Wait for Ad to start and verify the adStarted event .
//            ev.verifyEvent("adStarted", " Ad Started to Play ", 49000);
//
//            //Wait for Ad to complete and verify the adCompleted event .
//            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 35000);
//
//            ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);
//
//            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 35000);
//
//            //Wait for video to finish and verify the playCompleted event .
//            ev.verifyEvent("playCompleted", " Video Completed Play ", 30000);
//
//        } catch (Exception e) {
//            System.out.println(" Exception " + e);
//            e.printStackTrace();
//            ScreenshotDevice.screenshot(driver);
//        }
//
//    }
//
//        @org.testng.annotations.Test
//    public void Preload_and_PromoImage_Options() throws Exception {
//
//        try {
//            // Creating an Object of optionsSampleApp class
//            optionsSampleApp po = new optionsSampleApp();
//
//            // wait till home screen of basicPlayBackApp is opened
//            po.waitForAppHomeScreen(driver);
//
//            // Assert if current activity is indeed equal to the activity name of app home screen
//            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OptionsListActivity");
//
//            // Wrire to console activity name of home screen app
//            System.out.println("Preload and PromoImage Options. Activity :- " + driver.currentActivity() + "\n");
//
//            //Pause the running of test for a brief time .
//            Thread.sleep(3000);
//
//            // Select one of the video HLS,MP4 etc .
//            po.clickBasedOnText(driver, "Preload and PromoImage Options");
//
//            Thread.sleep(2000);
//
//            //Preload On + Show Promoimage On
//            //verify if player was loaded
//            po.waitForPresence(driver, "className", "android.view.View");
//
//            // Assert if current activity is indeed equal to the activity name of the video player
//            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreloadOptionsPlayerActivity");
//
//            // Print to console output current player activity
//            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
//
//            System.out.println("Preload On + Show Promoimage On test started");
//
//            //Click on Video create button
//            po.clickButtons(driver, 0);
//
//            //Wait for the video to be generated
//            po.waitForPresenceOfText(driver, "00:00");
//
//            //Click on video play icon after video has been generated .
//            po.clickImagebuttons(driver, 0);
//
//            //Verification of  PreMidPost bAd and Video Playback
//            EventVerification ev = new EventVerification();
//
//            //Wait for Ad to start and verify the adStarted event.
//            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);
//
//            //Wait for Ad to complete and verify the adCompleted event.
//            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 60000);
//
//            //Wait for video to start and verify the playStarted event .
//            ev.verifyEvent("playStarted", " Video Started Play ", 60000);
//
////            po.getBackFromRecentApp(driver);
////
////            Thread.sleep(5000);
////
////            // Verify that player is ready.
////            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
////
////            // Click on power key and unlock again
////            po.powerKeyClick(driver);
////
////            Thread.sleep(2000);
////
////            // Verify that player is ready.
////            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
////
////            Thread.sleep(5000);
//
//            //Wait for Ad to start and verify the adStarted event.
//            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);
//
//            //Wait for Ad to complete and verify the adCompleted event.
//            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 60000);
//
//            //Wait for Ad to start and verify the adStarted event.
//            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);
//
//            //Wait for Ad to complete and verify the adCompleted event.
//            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 60000);
//
//            //Wait for Video to complete and verify the playCompleted event.
//            ev.verifyEvent("playCompleted", " Video Completed Play ", 60000);
//
//            System.out.println("Preload On + Show Promoimage On test completed");
//
//            Thread.sleep(2000);
//
//            // Preload Off + Show Promoimage On
//            // Click on Preload On button
//            System.out.println("Preload Off + Show Promoimage On test started");
//
//            //Click onPreload On button
//            po.clickOnPreloadOn(driver);
//
//            //Click on Video create button
//            po.clickButtons(driver, 0);
//
//            // Wait for the video to be generated
//            po.waitForPresenceOfText(driver, "00:00");
//
//            // Click on video play icon after video has been generated .
//            po.clickImagebuttons(driver, 0);
//
//            //Verification of  PreMidPost bAd and Video Playback
//
//            //Wait for Ad to start and verify the adStarted event.
//            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);
//
//            //Wait for Ad to complete and verify the adCompleted event.
//            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 60000);
//
//            //Wait for video to start and verify the playStarted event .
//            ev.verifyEvent("playStarted", " Video Started Play ", 60000);
//
////            po.getBackFromRecentApp(driver);
////
////            Thread.sleep(2000);
////
////            // Verify that player is ready.
////            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
////
////            // Click on power key and unlock again
////            po.powerKeyClick(driver);
////
////            Thread.sleep(2000);
//
//            //Wait for Ad to start and verify the adStarted event.
//            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);
//
//            //Wait for Ad to complete and verify the adCompleted event.
//            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 60000);
//
//            //Wait for Ad to start and verify the adStarted event.
//            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);
//
//            //Wait for Ad to complete and verify the adCompleted event.
//            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 60000);
//
//            //Wait for Video to complete and verify the playCompleted event.
//            ev.verifyEvent("playCompleted", " Video Completed Play ", 60000);
//
//            System.out.println("Preload Off + Show Promoimage On completed");
//
//            Thread.sleep(3000);
//
//            // Preload Off + Show Promoimage off
//            // Click on align bottom button
//            System.out.println("Preload Off + Show Promoimage off test started");
//
//            Thread.sleep(2000);
//
//            //Click on PromoImageOn button
//            po.clickOnPromoImgOn(driver);
//
//            //Click on Video create button
//            po.clickButtons(driver, 0);
//
//            // Wait for the video to be generated
//            po.waitForPresenceOfText(driver, "00:00");
//
//            // Click on video play icon after video has been generated .
//            po.clickImagebuttons(driver, 0);
//
//            //Verification of  PreMidPost bAd and Video Playback
//
//            //Wait for Ad to start and verify the adStarted event.
//            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);
//
//            //Wait for Ad to complete and verify the adCompleted event.
//            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 60000);
//
//            //Wait for video to start and verify the playStarted event .
//            ev.verifyEvent("playStarted", " Video Started Play ", 60000);
//
//            //Wait for Ad to start and verify the adStarted event.
//            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);
//
//            //Wait for Ad to complete and verify the adCompleted event.
//            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 60000);
//
//            //Wait for Ad to start and verify the adStarted event.
//            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);
//
//            //Wait for Ad to complete and verify the adCompleted event.
//            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 60000);
//
//            //Wait for Video to complete and verify the playCompleted event.
//            ev.verifyEvent("playCompleted", " Video Completed Play ", 60000);
//
//            System.out.println("Preload Off + Show Promoimage off test completed");
//
//            Thread.sleep(3000);
//
//            // Preload On + Show Promoimage off
//            // Click on align bottom button
//            System.out.println("Preload On + Show Promoimage off test started");
//
//            //Click on PreloadOff button
//            po.clickOnPreloadOff(driver);
//
//            Thread.sleep(2000);
//
//            //Click on Video create button
//            po.clickButtons(driver, 0);
//
//            // Wait for the video to be generated
//            po.waitForPresenceOfText(driver, "00:00");
//
//            // Click on video play icon after video has been generated .
//            po.clickImagebuttons(driver, 0);
//
//            //Verification of  PreMidPost bAd and Video Playback
//
//            //Wait for Ad to start and verify the adStarted event.
//            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);
//
//            //Wait for Ad to complete and verify the adCompleted event.
//            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 60000);
//
//            //Wait for video to start and verify the playStarted event .
//            ev.verifyEvent("playStarted", " Video Started Play ", 60000);
//
//            //Wait for Ad to start and verify the adStarted event.
//            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);
//
//            //Wait for Ad to complete and verify the adCompleted event.
//            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 60000);
//
//            //Wait for Ad to start and verify the adStarted event.
//            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);
//
//            //Wait for Ad to complete and verify the adCompleted event.
//            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 60000);
//
//            //Wait for Video to complete and verify the playCompleted event.
//            ev.verifyEvent("playCompleted", " Video Completed Play ", 60000);
//
//            Thread.sleep(3000);
//
//        } catch (Exception e) {
//            System.out.println(" Exception " + e);
//            e.printStackTrace();
//            ScreenshotDevice.screenshot(driver);
//        }
//
//    }
//
//
//    @org.testng.annotations.Test
//    public void Preload_and_Promo_Options_With_Initial_Time() throws Exception {
//
//        try {
//            // Creating an Object of optionsSampleApp class
//            optionsSampleApp po = new optionsSampleApp();
//
//            // wait till home screen of basicPlayBackApp is opened
//            po.waitForAppHomeScreen(driver);
//
//            // Assert if current activity is indeed equal to the activity name of app home screen
//            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OptionsListActivity");
//
//            // Wrire to console activity name of home screen app
//            System.out.println("Preload and Promo Options with Initial Time. Activity :- " + driver.currentActivity() + "\n");
//
//            //Pause the running of test for a brief time .
//            Thread.sleep(3000);
//
//            // Select one of the video HLS,MP4 etc .
//            po.clickBasedOnText(driver, "Preload and Promo Options with Initial Time");
//
//            Thread.sleep(2000);
//
//            //Preload On + Show Promoimage On
//            //verify if player was loaded
//            po.waitForPresence(driver, "className", "android.view.View");
//
//            // Assert if current activity is indeed equal to the activity name of the video player
//            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreloadWithInitTimePlayerActivity");
//
//            // Print to console output current player activity
//            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
//
//            System.out.println("Preload On + Show Promoimage On test started");
//
//            //Click on Video create button
//            po.clickButtons(driver, 0);
//
//            //Wait for the video to be generated
//            po.waitForPresenceOfText(driver, "00:00");
//
//            //Verification of  PreMidPost Ad and Video Playback
//            EventVerification ev = new EventVerification();
//
//            //Wait for video to start and verify the playStarted event .
//            ev.verifyEvent("playStarted", " Video Started Play ", 60000);
//
//            //Wait for Video to complete and verify the playCompleted event.
//            ev.verifyEvent("playCompleted", " Video Completed Play ", 60000);
//
//            System.out.println("Preload On + Show Promoimage On test completed");
//
//            Thread.sleep(2000);
//
//            // Preload Off + Show Promoimage On
//            // Click on Preload On button
//            System.out.println("Preload Off + Show Promoimage On test started");
//
//            //Click onPreload On button
//            po.clickOnPreloadOn(driver);
//
//            //Click on Video create button
//            po.clickButtons(driver, 0);
//
//            // Wait for the video to be generated
//            po.waitForPresenceOfText(driver, "00:00");
//
//            Thread.sleep(2000);
//
//            //Wait for video to start and verify the playStarted event .
//            ev.verifyEvent("playStarted", " Video Started Play ", 60000);
//
//            //Wait for Video to complete and verify the playCompleted event.
//            ev.verifyEvent("playCompleted", " Video Completed Play ", 60000);
//
//            System.out.println("Preload Off + Show Promoimage On completed");
//
//            Thread.sleep(3000);
//
//            // Preload Off + Show Promoimage off
//            // Click on align bottom button
//            System.out.println("Preload Off + Show Promoimage off test started");
//
//            Thread.sleep(2000);
//
//            //Click on PromoImageOn button
//            po.clickOnPromoImgOn(driver);
//
//            //Click on Video create button
//            po.clickButtons(driver, 0);
//
//            // Wait for the video to be generated
//            po.waitForPresenceOfText(driver, "00:00");
//
//            //Wait for video to start and verify the playStarted event .
//            ev.verifyEvent("playStarted", " Video Started Play ", 60000);
//
//            //Wait for Video to complete and verify the playCompleted event.
//            ev.verifyEvent("playCompleted", " Video Completed Play ", 60000);
//
//            System.out.println("Preload Off + Show Promoimage off test completed");
//
//            Thread.sleep(3000);
//
//            // Preload On + Show Promoimage off
//            // Click on align bottom button
//            System.out.println("Preload On + Show Promoimage off test started");
//
//            //Click on PreloadOff button
//            po.clickOnPreloadOff(driver);
//
//            Thread.sleep(2000);
//
//            //Click on Video create button
//            po.clickButtons(driver, 0);
//
//            // Wait for the video to be generated
//            po.waitForPresenceOfText(driver, "00:00");
//
//            //Wait for video to start and verify the playStarted event .
//            ev.verifyEvent("playStarted", " Video Started Play ", 60000);
//
//            //Wait for Video to complete and verify the playCompleted event.
//            ev.verifyEvent("playCompleted", " Video Completed Play ", 60000);
//
//            System.out.println("Preload On + Show Promoimage off test completed");
//
//            Thread.sleep(3000);
//
//    } catch (Exception e) {
//        System.out.println(" Exception " + e);
//        e.printStackTrace();
//        ScreenshotDevice.screenshot(driver);
//        }
//    }

    @org.testng.annotations.Test
    public void ServersideTVRatings() throws Exception {

        try {
            // Creating an Object of optionsSampleApp class
            optionsSampleApp po = new optionsSampleApp();

            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OptionsListActivity");

            // Wrire to console activity name of home screen app
            System.out.println("Server-side TV Ratings. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Server-Side TV Ratings");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.ServerConfiguredTVRatingsPlayerActivity");

            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            Thread.sleep(5000);

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

            // Verify that player is ready.
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            // Click on power key and unlock again
            po.powerKeyClick(driver);

            Thread.sleep(2000);

            // Verify that player is ready.
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            // Start video playback
            po.videoPlay(driver);

            // verify that video starts playing
            ev.verifyEvent("stateChanged - state: PLAYING","Video has been start playing again",35000);

            // verify that video playback is completed
            ev.verifyEvent("playCompleted - state: LOADING", "video play completed",90000);

        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }

    }

//    @org.testng.annotations.Test
//    public void TV_Ratings_Configuration() throws Exception {
//
//        try {
//            // Creating an Object of optionsSampleApp class
//            optionsSampleApp po = new optionsSampleApp();
//
//            // wait till home screen of basicPlayBackApp is opened
//            po.waitForAppHomeScreen(driver);
//
//            // Assert if current activity is indeed equal to the activity name of app home screen
//            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OptionsListActivity");
//
//            // Wrire to console activity name of home screen app
//            System.out.println("TV Ratings Configuration. Activity :- " + driver.currentActivity() + "\n");
//
//            //Pause the running of test for a brief time .
//            Thread.sleep(3000);
//
//            // Select one of the video HLS,MP4 etc .
//            po.clickBasedOnText(driver, "TV Ratings Configuration");
//
//            Thread.sleep(2000);
//
//             //Align Bottom + Align Right
//            //verify if player was loaded
//            po.waitForPresence(driver, "className", "android.view.View");
//
//            // Assert if current activity is indeed equal to the activity name of the video player
//            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.TVRatingsPlayerActivity");
//
//            // Print to console output current player activity
//            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
//
//            System.out.println("Align Bottom + Align Right test started");
//
//            //Click on Video create button
//            po.clickButtons(driver, 0);
//
//            // Wait for the video to be generated
//            po.waitForPresenceOfText(driver, "00:00");
//
//            // Click on video play icon after video has been generated .
//            po.clickImagebuttons(driver, 0);
//
//            //Play Started Verification
//            EventVerification ev = new EventVerification();
//
//            //Wait for video to start and verify the playStarted event .
//            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
//
//            //Wait for video to finish and verify the playCompleted event .
//            ev.verifyEvent("playCompleted", " Video Completed Play ", 50000);
//
//            System.out.println("Align Bottom + Align Right test completed");
//
//            Thread.sleep(2000);
//
//            // Align Top + Align Right
//            // Click on align right button
//            System.out.println("Align Top + Align Right test started");
//
//            po.clickAlignBottom(driver);
//
//            //Click on Video create button
//            po.clickButtons(driver, 0);
//
//            // Wait for the video to be generated
//            po.waitForPresenceOfText(driver, "00:00");
//
//            // Click on video play icon after video has been generated .
//            po.clickImagebuttons(driver, 0);
//
//            //Wait for video to start and verify the playStarted event .
//            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
//
//            //Wait for video to finish and verify the playCompleted event .
//            ev.verifyEvent("playCompleted", " Video Completed Play ", 50000);
//
//            System.out.println("Align Top + Align Right test completed");
//
//            Thread.sleep(3000);
//
//            // Align Top + Align Left
//            // Click on align bottom button
//            System.out.println("Align Top + Align Left test started");
//
//            Thread.sleep(2000);
//
//            po.clickAlignRight(driver);
//
//            //Click on Video create button
//            po.clickButtons(driver, 0);
//
//            // Wait for the video to be generated
//            po.waitForPresenceOfText(driver, "00:00");
//
//            // Click on video play icon after video has been generated .
//            po.clickImagebuttons(driver, 0);
//
//            //Wait for video to start and verify the playStarted event .
//            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
//
//            //Wait for video to finish and verify the playCompleted event .
//            ev.verifyEvent("playCompleted", " Video Completed Play ", 50000);
//
//            System.out.println("Align Top + Align Left test completed");
//
//            Thread.sleep(3000);
//
//            // Align Bottom + Align Left
//            // Click on align bottom button
//            System.out.println("Align Bottom + Align Left test started");
//
//            po.clickAlignTop(driver);
//
//            Thread.sleep(2000);
//
//            //Click on Video create button
//            po.clickButtons(driver, 0);
//
//            // Wait for the video to be generated
//            po.waitForPresenceOfText(driver, "00:00");
//
//            // Click on video play icon after video has been generated .
//            po.clickImagebuttons(driver, 0);
//
//            //Wait for video to start and verify the playStarted event .
//            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
//
//            //Wait for video to finish and verify the playCompleted event .
//            ev.verifyEvent("playCompleted", " Video Completed Play ", 50000);
//
//            System.out.println("Align Bottom + Align Left test completed");
//
//            Thread.sleep(3000);
//
//        } catch (Exception e) {
//            System.out.println(" Exception " + e);
//            e.printStackTrace();
//            ScreenshotDevice.screenshot(driver);
//        }
//
//    }

    @org.testng.annotations.Test
    public void present_video_view_sharing_options() throws Exception {

        try {
            // Creating an Object of optionsSampleApp class
            optionsSampleApp po = new optionsSampleApp();

            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OptionsListActivity");

            // Wrire to console activity name of home screen app
            System.out.println("OptionsSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Prevent Video View Sharing Option");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreventVideoViewSharingPlayerActivity");

            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            Thread.sleep(5000);

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

            // Verify that player is ready.
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            // Click on power key and unlock again
            po.powerKeyClick(driver);

            Thread.sleep(2000);

            // Verify that player is ready.
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            // Start video playback
            po.videoPlay(driver);

            // verify that video starts playing
            ev.verifyEvent("stateChanged - state: PLAYING","Video has been start playing again",35000);

            // verify that video playback is completed
            ev.verifyEvent("playCompleted - state: LOADING", "video play completed",90000);

        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }

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
}