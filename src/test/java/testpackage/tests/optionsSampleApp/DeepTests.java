package testpackage.tests.optionsSampleApp;

import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.*;
import testpackage.utils.*;
import java.io.IOException;
import java.util.Properties;

public class DeepTests extends EventLogTest {
    final static Logger logger = Logger.getLogger(DeepTests.class);


    @BeforeClass
    public void beforeTest() throws Exception {
        // closing all recent app from background.
        CloserecentApps.closeApps();
        logger.info("BeforeTest \n");

        logger.debug(System.getProperty("user.dir"));
        // Get Property Values
        LoadPropertyValues prop = new LoadPropertyValues();
        Properties p = prop.loadProperty("optionsSampleApp.properties");

        //logger.debug("Device id from properties file " + p.getProperty("deviceName"));
        //logger.debug("PortraitMode from properties file " + p.getProperty("PortraitMode"));
        //logger.debug("Path where APK is stored"+ p.getProperty("appDir"));
        //logger.debug("APK name is "+ p.getProperty("app"));
        //logger.debug("Platform under Test is "+ p.getProperty("platformName"));
        //logger.debug("Mobile OS Version is "+ p.getProperty("OSVERSION"));
        //logger.debug("Package Name of the App is "+ p.getProperty("appPackage"));
        //logger.debug("Activity Name of the App is "+ p.getProperty("appActivity"));

        SetUpAndroidDriver setUpdriver = new SetUpAndroidDriver();
        driver = setUpdriver.setUpandReturnAndroidDriver(p.getProperty("udid"), p.getProperty("appDir"), p.getProperty("appValue"), p.getProperty("platformName"), p.getProperty("platformVersion"), p.getProperty("appPackage"), p.getProperty("appActivity"));
        Thread.sleep(2000);
    }

    @BeforeMethod
    //public void beforeTest() throws Exception{
    public void beforeMethod() throws Exception {
        logger.info("beforeMethod \n");
        driver.manage().logs().get("logcat");
        PushLogFileToDevice logpush = new PushLogFileToDevice();
        logpush.pushLogFile();
        if (driver.currentActivity() != "com.ooyala.sample.lists.OptionsListActivity") {
            driver.startActivity("com.ooyala.sample.OptionsSampleApp", "com.ooyala.sample.lists.OptionsListActivity");
        }

        // Get Property Values
        LoadPropertyValues prop1 = new LoadPropertyValues();
        Properties p1 = prop1.loadProperty();

        logger.debug(" Screen Mode " + p1.getProperty("ScreenMode"));

        //if(p1.getProperty("ScreenMode") != "P"){
        //    logger.info("Inside landscape Mode ");
        //    driver.rotate(ScreenOrientation.LANDSCAPE);
        //}

        //driver.rotate(ScreenOrientation.LANDSCAPE);
        //driver.rotate(ScreenOrientation.LANDSCAPE);

    }

    @AfterClass
    public void afterTest() throws InterruptedException, IOException {
        logger.info("AfterTest \n");
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
//            logger.debug("OptionsSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
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
//            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
//
//            // CuePoints On + AdControls On Test case.
//
//            //Click on Video create button
//            po.clickButtons(driver, 0);
//            logger.info("Creating Video to Play");
//
//            // Wait for the video to be generated
//            po.waitForPresenceOfText(driver, "00:00");
//            Thread.sleep(2000);
//            logger.info("Initial Time is 00:00");
//
//            // Click on video play icon after video has been generated .
//            po.clickImagebuttons(driver, 0);
//            Thread.sleep(2000);
//
//            //Play Started Verification
//            EventVerification ev = new EventVerification();
//
//            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
//
//            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);
//
//            //Wait for video to start and verify the playStarted event .
//            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
//
//            // Pause and Play Video
//            Thread.sleep(2000);
//
//            po.pauseSmallPlayer(driver);
//
//            //Wait for pause to video and verify the video is paused.
//            ev.verifyEvent("PAUSED", "Video paused", 50000);
//
//            Thread.sleep(2000);
//
//            po.clickImagebuttons(driver, 0);
//
//            //Wait for play the video and verify the video starts playing again.
//            ev.verifyEvent("PLAYING", "Video Started", 50000);
//
//            //Wait for Ad to start and verify the adStarted event .
//            ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);
//
//            //Wait for Ad to complete and verify the adCompleted event .
//            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);
//
//            ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);
//
//            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);
//
//            //Wait for video to finish and verify the playCompleted event .
//            ev.verifyEvent("playCompleted", " Video Completed Play ", 30000);
//
//            Thread.sleep(2000);
//
//            // CuePoints Off + AdControls On Test case.
//            // Click on CurPoints On
//
//            po.clickOnCuePointsOn(driver);
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
//            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
//
//            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);
//
//            //Wait for video to start and verify the playStarted event .
//            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
//
//            // Pause and Play Video
//            Thread.sleep(2000);
//
//            po.pauseSmallPlayer(driver);
//
//            //Wait for pause to video and verify the video is paused.
//            ev.verifyEvent("PAUSED", "Video paused", 50000);
//
//            Thread.sleep(2000);
//
//            po.clickImagebuttons(driver, 0);
//
//            //Wait for play the video and verify the video starts playing again.
//            ev.verifyEvent("PLAYING", "Video Started", 50000);
//
//            Thread.sleep(2000);
//
//            //Wait for Ad to start and verify the adStarted event .
//            ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);
//
//            //Wait for Ad to complete and verify the adCompleted event .
//            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);
//
//            ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);
//
//            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);
//
//            //Wait for video to finish and verify the playCompleted event .
//            ev.verifyEvent("playCompleted", " Video Completed Play ", 30000);
//
//            Thread.sleep(2000);
//
//            // CuePoints Off + AdControls Off Test case.
//            // Click on CurPoints On
//
//            po.clickOnAdsControlsOn(driver);
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
//
//            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
//
//            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);
//
//            //Wait for video to start and verify the playStarted event .
//            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
//
//            // Pause and Play Video
//            Thread.sleep(2000);
//
//            po.pauseSmallPlayer(driver);
//
//            //Wait for pause to video and verify the video is paused.
//            ev.verifyEvent("PAUSED", "Video paused", 50000);
//
//            Thread.sleep(2000);
//
//            po.clickImagebuttons(driver, 0);
//
//            //Wait for play the video and verify the video starts playing again.
//            ev.verifyEvent("PLAYING", "Video Started", 50000);
//
//            Thread.sleep(2000);
//
//            //Wait for Ad to start and verify the adStarted event .
//            ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);
//
//            //Wait for Ad to complete and verify the adCompleted event .
//            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);
//
//            ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);
//
//            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);
//
//            //Wait for video to finish and verify the playCompleted event .
//            ev.verifyEvent("playCompleted", " Video Completed Play ", 30000);
//
//            Thread.sleep(2000);
//
//            // CuePoints On + AdControls Off Test case.
//            // Click on CurPoints On
//
//            po.clickOnCuePointsOff(driver);
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
//
//            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
//
//            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);
//
//            //Wait for video to start and verify the playStarted event .
//            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
//
//            // Pause and Play Video
//            Thread.sleep(2000);
//
//            po.pauseSmallPlayer(driver);
//
//            //Wait for pause to video and verify the video is paused.
//            ev.verifyEvent("PAUSED", "Video paused", 50000);
//
//            Thread.sleep(2000);
//
//            po.clickImagebuttons(driver, 0);
//
//            //Wait for play the video and verify the video starts playing again.
//            ev.verifyEvent("PLAYING", "Video Started", 50000);
//
//            Thread.sleep(2000);
//
//            //Wait for Ad to start and verify the adStarted event .
//            ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);
//
//            //Wait for Ad to complete and verify the adCompleted event .
//            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);
//
//            ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);
//
//            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);
//
//            //Wait for video to finish and verify the playCompleted event .
//            ev.verifyEvent("playCompleted", " Video Completed Play ", 30000);
//
//            Thread.sleep(2000);
//
//        } catch (Exception e) {
//            logger.error("CuePointsAndAdsControlOptions throws Exception " + e);
//            e.printStackTrace();
//            ScreenshotDevice.screenshot(driver,"CuePointsAndAdsControlOptions");
//            Assert.assertTrue(false, "This will fail!");
//        }
//
//    }
/*
    @org.testng.annotations.Test
    public void Preload_and_PromoImage_Options() throws Exception {

        try {
            // Creating an Object of optionsSampleApp class
            optionsSampleApp po = new optionsSampleApp();

            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OptionsListActivity");

            // Wrire to console activity name of home screen app
            logger.debug("Preload and PromoImage Options. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Preload and PromoImage Options");

            Thread.sleep(2000);

            //Preload On + Show Promoimage On
            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreloadOptionsPlayerActivity");

            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            logger.info("Preload On + Show Promoimage On test started");

            //Click on Video create button
            po.clickButtons(driver, 0);

            //Wait for the video to be generated
            po.waitForPresenceOfText(driver, "00:00");

            //Click on video play icon after video has been generated .
            po.clickImagebuttons(driver, 0);

            //Verification of  PreMidPost bAd and Video Playback
            EventVerification ev = new EventVerification();

            //Wait for Ad to start and verify the adStarted event.
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            //Wait for Ad to complete and verify the adCompleted event.
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 60000);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 60000);

            // Pause and Play Video
            Thread.sleep(2000);

            po.pauseSmallPlayer(driver);

            //Wait for pause to video and verify the video is paused.
            ev.verifyEvent("PAUSED", "Video paused", 50000);

            Thread.sleep(2000);

            po.clickImagebuttons(driver, 0);

            //Wait for play the video and verify the video starts playing again.
            ev.verifyEvent("PLAYING", "Video Started", 50000);

            Thread.sleep(2000);

//            po.getBackFromRecentApp(driver);
//
//            Thread.sleep(5000);
//
//            // Verify that player is ready.
//            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
//
//            // Click on power key and unlock again
//            po.powerKeyClick(driver);
//
//            Thread.sleep(2000);
//
//            // Verify that player is ready.
//            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
//
//            Thread.sleep(5000);

            //Wait for Ad to start and verify the adStarted event.
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            //Wait for Ad to complete and verify the adCompleted event.
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 60000);

            //Wait for Ad to start and verify the adStarted event.
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            //Wait for Ad to complete and verify the adCompleted event.
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 60000);

            //Wait for Video to complete and verify the playCompleted event.
            ev.verifyEvent("playCompleted", " Video Completed Play ", 60000);

            logger.debug("Preload On + Show Promoimage On test completed");

            Thread.sleep(2000);

            // Preload Off + Show Promoimage On
            // Click on Preload On button
            logger.debug("Preload Off + Show Promoimage On test started");

            //Click onPreload On button
            po.clickOnPreloadOn(driver);

            //Click on Video create button
            po.clickButtons(driver, 0);

            // Wait for the video to be generated
            po.waitForPresenceOfText(driver, "00:00");

            // Click on video play icon after video has been generated .
            po.clickImagebuttons(driver, 0);

            //Verification of  PreMidPost bAd and Video Playback

            //Wait for Ad to start and verify the adStarted event.
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            //Wait for Ad to complete and verify the adCompleted event.
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 60000);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 60000);

            // Pause and Play Video
            Thread.sleep(2000);

            po.pauseSmallPlayer(driver);

            //Wait for pause to video and verify the video is paused.
            ev.verifyEvent("PAUSED", "Video paused", 50000);

            Thread.sleep(2000);

            po.clickImagebuttons(driver, 0);

            //Wait for play the video and verify the video starts playing again.
            ev.verifyEvent("PLAYING", "Video Started", 50000);

            Thread.sleep(2000);

//            po.getBackFromRecentApp(driver);
//
//            Thread.sleep(2000);
//
//            // Verify that player is ready.
//            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);
//
//            // Click on power key and unlock again
//            po.powerKeyClick(driver);
//
//            Thread.sleep(2000);

            //Wait for Ad to start and verify the adStarted event.
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            //Wait for Ad to complete and verify the adCompleted event.
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 60000);

            //Wait for Ad to start and verify the adStarted event.
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            //Wait for Ad to complete and verify the adCompleted event.
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 60000);

            //Wait for Video to complete and verify the playCompleted event.
            ev.verifyEvent("playCompleted", " Video Completed Play ", 60000);

            logger.debug("Preload Off + Show Promoimage On completed");

            Thread.sleep(3000);

            // Preload Off + Show Promoimage off
            // Click on align bottom button
            logger.debug("Preload Off + Show Promoimage off test started");

            Thread.sleep(2000);

            //Click on PromoImageOn button
            po.clickOnPromoImgOn(driver);

            //Click on Video create button
            po.clickButtons(driver, 0);

            // Wait for the video to be generated
            po.waitForPresenceOfText(driver, "00:00");

            // Click on video play icon after video has been generated .
            po.clickImagebuttons(driver, 0);

            //Verification of  PreMidPost bAd and Video Playback

            //Wait for Ad to start and verify the adStarted event.
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            //Wait for Ad to complete and verify the adCompleted event.
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 60000);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 60000);

            // Pause and Play Video
            Thread.sleep(2000);

            po.pauseSmallPlayer(driver);

            //Wait for pause to video and verify the video is paused.
            ev.verifyEvent("PAUSED", "Video paused", 50000);

            Thread.sleep(2000);

            po.clickImagebuttons(driver, 0);

            //Wait for play the video and verify the video starts playing again.
            ev.verifyEvent("PLAYING", "Video Started", 50000);

            Thread.sleep(2000);

            //Wait for Ad to start and verify the adStarted event.
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            //Wait for Ad to complete and verify the adCompleted event.
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 60000);

            //Wait for Ad to start and verify the adStarted event.
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            //Wait for Ad to complete and verify the adCompleted event.
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 60000);

            //Wait for Video to complete and verify the playCompleted event.
            ev.verifyEvent("playCompleted", " Video Completed Play ", 60000);

            logger.debug("Preload Off + Show Promoimage off test completed");

            Thread.sleep(3000);

            // Preload On + Show Promoimage off
            // Click on align bottom button
            logger.debug("Preload On + Show Promoimage off test started");

            //Click on PreloadOff button
            po.clickOnPreloadOff(driver);

            Thread.sleep(2000);

            //Click on Video create button
            po.clickButtons(driver, 0);

            // Wait for the video to be generated
            po.waitForPresenceOfText(driver, "00:00");

            // Click on video play icon after video has been generated .
            po.clickImagebuttons(driver, 0);

            //Verification of  PreMidPost bAd and Video Playback

            //Wait for Ad to start and verify the adStarted event.
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            //Wait for Ad to complete and verify the adCompleted event.
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 60000);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 60000);

            // Pause and Play Video
            Thread.sleep(2000);

            po.pauseSmallPlayer(driver);

            //Wait for pause to video and verify the video is paused.
            ev.verifyEvent("PAUSED", "Video paused", 50000);

            Thread.sleep(2000);

            po.clickImagebuttons(driver, 0);

            //Wait for play the video and verify the video starts playing again.
            ev.verifyEvent("PLAYING", "Video Started", 50000);

            Thread.sleep(2000);

            //Wait for Ad to start and verify the adStarted event.
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            //Wait for Ad to complete and verify the adCompleted event.
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 60000);

            //Wait for Ad to start and verify the adStarted event.
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            //Wait for Ad to complete and verify the adCompleted event.
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 60000);

            //Wait for Video to complete and verify the playCompleted event.
            ev.verifyEvent("playCompleted", " Video Completed Play ", 60000);

            Thread.sleep(3000);

        } catch (Exception e) {
            logger.info("Preload_and_PromoImage_Options throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"Preload_and_PromoImage_Options");
            Assert.assertTrue(false, "This will fail!");
        }

    }

    @org.testng.annotations.Test
    public void Preload_and_Promo_Options_With_Initial_Time() throws Exception {

        try {
            // Creating an Object of optionsSampleApp class
            optionsSampleApp po = new optionsSampleApp();

            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OptionsListActivity");

            // Wrire to console activity name of home screen app
            logger.debug("Preload and Promo Options with Initial Time. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Preload and Promo Options with Initial Time");

            Thread.sleep(2000);

            //Preload On + Show Promoimage On
            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreloadWithInitTimePlayerActivity");

            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            logger.debug("Preload On + Show Promoimage On test started");

            //Click on Video create button
            po.clickButtons(driver, 0);

            //Wait for the video to be generated
            po.waitForPresenceOfText(driver, "00:00");

            //Verification of  PreMidPost Ad and Video Playback
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 60000);

            // Pause and Play Video
            Thread.sleep(2000);

            po.pauseSmallPlayer(driver);

            //Wait for pause to video and verify the video is paused.
            ev.verifyEvent("PAUSED", "Video paused", 50000);

            Thread.sleep(2000);

            po.clickImagebuttons(driver, 0);

            //Wait for play the video and verify the video starts playing again.
            ev.verifyEvent("PLAYING", "Video Started", 50000);

            Thread.sleep(2000);

            //Wait for Video to complete and verify the playCompleted event.
            ev.verifyEvent("playCompleted", " Video Completed Play ", 60000);

            logger.debug("Preload On + Show Promoimage On test completed");

            Thread.sleep(2000);

            // Preload Off + Show Promoimage On
            // Click on Preload On button
            logger.debug("Preload Off + Show Promoimage On test started");

            //Click onPreload On button
            po.clickOnPreloadOn(driver);

            //Click on Video create button
            po.clickButtons(driver, 0);

            // Wait for the video to be generated
            po.waitForPresenceOfText(driver, "00:00");

            Thread.sleep(2000);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 60000);

            // Pause and Play Video
            Thread.sleep(2000);

            po.pauseSmallPlayer(driver);

            //Wait for pause to video and verify the video is paused.
            ev.verifyEvent("PAUSED", "Video paused", 50000);

            Thread.sleep(2000);

            po.clickImagebuttons(driver, 0);

            //Wait for play the video and verify the video starts playing again.
            ev.verifyEvent("PLAYING", "Video Started", 50000);

            Thread.sleep(2000);

            //Wait for Video to complete and verify the playCompleted event.
            ev.verifyEvent("playCompleted", " Video Completed Play ", 60000);

            logger.info("Preload Off + Show Promoimage On completed");

            Thread.sleep(3000);

            // Preload Off + Show Promoimage off
            // Click on align bottom button
            logger.info("Preload Off + Show Promoimage off test started");

            Thread.sleep(2000);

            //Click on PromoImageOn button
            po.clickOnPromoImgOn(driver);

            //Click on Video create button
            po.clickButtons(driver, 0);

            // Wait for the video to be generated
            po.waitForPresenceOfText(driver, "00:00");

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 60000);

            // Pause and Play Video
            Thread.sleep(2000);

            po.pauseSmallPlayer(driver);

            //Wait for pause to video and verify the video is paused.
            ev.verifyEvent("PAUSED", "Video paused", 50000);

            Thread.sleep(2000);

            po.clickImagebuttons(driver, 0);

            //Wait for play the video and verify the video starts playing again.
            ev.verifyEvent("PLAYING", "Video Started", 50000);

            Thread.sleep(2000);

            //Wait for Video to complete and verify the playCompleted event.
            ev.verifyEvent("playCompleted", " Video Completed Play ", 60000);

            logger.info("Preload Off + Show Promoimage off test completed");

            Thread.sleep(3000);

            // Preload On + Show Promoimage off
            // Click on align bottom button
            logger.info("Preload On + Show Promoimage off test started");

            //Click on PreloadOff button
            po.clickOnPreloadOff(driver);

            Thread.sleep(2000);

            //Click on Video create button
            po.clickButtons(driver, 0);

            // Wait for the video to be generated
            po.waitForPresenceOfText(driver, "00:00");

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 60000);

            // Pause and Play Video
            Thread.sleep(2000);

            po.pauseSmallPlayer(driver);

            //Wait for pause to video and verify the video is paused.
            ev.verifyEvent("PAUSED", "Video paused", 50000);

            Thread.sleep(2000);

            po.clickImagebuttons(driver, 0);

            //Wait for play the video and verify the video starts playing again.
            ev.verifyEvent("PLAYING", "Video Started", 50000);

            Thread.sleep(2000);

            //Wait for Video to complete and verify the playCompleted event.
            ev.verifyEvent("playCompleted", " Video Completed Play ", 60000);

            logger.info("Preload On + Show Promoimage off test completed");

            Thread.sleep(3000);

    } catch (Exception e) {
        logger.error("Preload_and_Promo_Options_With_Initial_Time throws Exception " + e);
        e.printStackTrace();
        ScreenshotDevice.screenshot(driver,"Preload_and_Promo_Options_With_Initial_Time");
        Assert.assertTrue(false, "This will fail!");
        }
    }

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
            logger.debug("Server-side TV Ratings. Activity :- " + driver.currentActivity() + "\n");

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
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            Thread.sleep(5000);

            WebElement viewarea = driver.findElementByClassName("android.view.View");
            viewarea.click();

            // Tap coordinates to pause
            String dimensions = driver.manage().window().getSize().toString();

            //logger.info(" Dimensions are "+dimensions);
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
            ev.verifyEvent("playCompleted", "video play completed",90000);

        } catch (Exception e) {
            logger.error("ServersideTVRatings throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"ServersideTVRatings");
            Assert.assertTrue(false, "This will fail!");
        }

    }

    @org.testng.annotations.Test
    public void TV_Ratings_Configuration() throws Exception {

        try {
            // Creating an Object of optionsSampleApp class
            optionsSampleApp po = new optionsSampleApp();

            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OptionsListActivity");

            // Wrire to console activity name of home screen app
            logger.debug("TV Ratings Configuration. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "TV Ratings Configuration");

            Thread.sleep(2000);

             //Align Bottom + Align Right
            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.TVRatingsPlayerActivity");

            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            logger.info("Align Bottom + Align Right test started");

            //Click on Video create button
            po.clickButtons(driver, 0);

            // Wait for the video to be generated
            po.waitForPresenceOfText(driver, "00:00");

            // Click on video play icon after video has been generated .
            po.clickImagebuttons(driver, 0);

            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);

            // Pause and Play Video
            Thread.sleep(2000);

            po.pauseSmallPlayer(driver);

            //Wait for pause to video and verify the video is paused.
            ev.verifyEvent("PAUSED", "Video paused", 50000);

            Thread.sleep(2000);

            po.clickImagebuttons(driver, 0);

            //Wait for play the video and verify the video starts playing again.
            ev.verifyEvent("PLAYING", "Video Started", 50000);

            Thread.sleep(2000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 50000);

            logger.info("Align Bottom + Align Right test completed");

            Thread.sleep(2000);

            // Align Top + Align Right
            // Click on align right button
            logger.info("Align Top + Align Right test started");

            po.clickAlignBottom(driver);

            //Click on Video create button
            po.clickButtons(driver, 0);

            // Wait for the video to be generated
            po.waitForPresenceOfText(driver, "00:00");

            // Click on video play icon after video has been generated .
            po.clickImagebuttons(driver, 0);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);

            // Pause and Play Video
            Thread.sleep(2000);

            po.pauseSmallPlayer(driver);

            //Wait for pause to video and verify the video is paused.
            ev.verifyEvent("PAUSED", "Video paused", 50000);

            Thread.sleep(2000);

            po.clickImagebuttons(driver, 0);

            //Wait for play the video and verify the video starts playing again.
            ev.verifyEvent("PLAYING", "Video Started", 50000);

            Thread.sleep(2000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 50000);

            logger.info("Align Top + Align Right test completed");

            Thread.sleep(3000);

            // Align Top + Align Left
            // Click on align bottom button
            logger.info("Align Top + Align Left test started");

            Thread.sleep(2000);

            po.clickAlignRight(driver);

            //Click on Video create button
            po.clickButtons(driver, 0);

            // Wait for the video to be generated
            po.waitForPresenceOfText(driver, "00:00");

            // Click on video play icon after video has been generated .
            po.clickImagebuttons(driver, 0);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);

            // Pause and Play Video
            Thread.sleep(2000);

            po.pauseSmallPlayer(driver);

            //Wait for pause to video and verify the video is paused.
            ev.verifyEvent("PAUSED", "Video paused", 50000);

            Thread.sleep(2000);

            po.clickImagebuttons(driver, 0);

            //Wait for play the video and verify the video starts playing again.
            ev.verifyEvent("PLAYING", "Video Started", 50000);

            Thread.sleep(2000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 50000);

            logger.info("Align Top + Align Left test completed");

            Thread.sleep(3000);

            // Align Bottom + Align Left
            // Click on align bottom button
            logger.info("Align Bottom + Align Left test started");

            po.clickAlignTop(driver);

            Thread.sleep(2000);

            //Click on Video create button
            po.clickButtons(driver, 0);

            // Wait for the video to be generated
            po.waitForPresenceOfText(driver, "00:00");

            // Click on video play icon after video has been generated .
            po.clickImagebuttons(driver, 0);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);

            // Pause and Play Video
            Thread.sleep(2000);

            po.pauseSmallPlayer(driver);

            //Wait for pause to video and verify the video is paused.
            ev.verifyEvent("PAUSED", "Video paused", 50000);

            Thread.sleep(2000);

            po.clickImagebuttons(driver, 0);

            //Wait for play the video and verify the video starts playing again.
            ev.verifyEvent("PLAYING", "Video Started", 50000);

            Thread.sleep(2000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 50000);

            logger.info("Align Bottom + Align Left test completed");

            Thread.sleep(3000);

        } catch (Exception e) {
            logger.error("TV_Ratings_Configuration throws Exception " + e);
             e.printStackTrace();             Assert.assertTrue(false, "This will fail!");
            ScreenshotDevice.screenshot(driver,"TV_Ratings_Configuration");
        }

    }

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
            logger.debug("OptionsSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

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
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            Thread.sleep(5000);

            WebElement viewarea = driver.findElementByClassName("android.view.View");
            viewarea.click();

            // Tap coordinates to pause
            String dimensions = driver.manage().window().getSize().toString();

            //logger.info(" Dimensions are "+dimensions);
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
            ev.verifyEvent("playCompleted", "video play completed",90000);

        } catch (Exception e) {
            logger.error("present_video_view_sharing_options throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"present_video_view_sharing_options");
            Assert.assertTrue(false, "This will fail!");
        }
    }
*/
    @AfterMethod
    public void afterMethod(ITestResult result) throws Exception {
        // Waiting for all the events from sdk to come in .
        logger.info("AfterMethod \n");
        //ScreenshotDevice.screenshot(driver);
        RemoveEventsLogFile.removeEventsFileLog();
        Thread.sleep(10000);

    }
}