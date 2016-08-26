package testpackage.tests.CompleteSampleApp;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import testpackage.pageobjects.CompleteSampleApp;
import testpackage.utils.*;

import java.io.IOException;
import java.util.Properties;

public class PlayerConfigOptionsQL extends EventLogTest {

    @BeforeClass
    public void beforeTest() throws Exception {

        System.out.println("BeforeTest \n");

        System.out.println(System.getProperty("user.dir"));
        // Get Property Values
        LoadPropertyValues prop = new LoadPropertyValues();
        Properties p = prop.loadProperty("completesampleapp.properties");

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
        PushLogFileToDevice logpush = new PushLogFileToDevice();
        logpush.pushLogFile();
        if (driver.currentActivity() != "com.ooyala.sample.complete.MainActivity") {
            driver.startActivity("com.ooyala.sample.CompleteSampleApp", "com.ooyala.sample.complete.MainActivity");
        }

        // Get Property Values
        LoadPropertyValues prop1 = new LoadPropertyValues();
        Properties p1 = prop1.loadProperty();

        System.out.println(" Screen Mode " + p1.getProperty("ScreenMode"));

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
    public void afterMethod(ITestResult result) throws Exception {
        // Waiting for all the events from sdk to come in .
        System.out.println("AfterMethod \n");
        RemoveEventsLogFile.removeEventsFileLog();
        Thread.sleep(10000);

    }

    @Test
    public void CuePointsAndAdsControlOptions() throws Exception {

        try {
            // Creating an Object of optionsSampleApp class
            CompleteSampleApp po = new CompleteSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");

            // Wrire to console activity name of home screen app
            System.out.println("CompleteSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            //Click on  Player Configuration with Options
            po.clickBasedOnText(driver,"Player Configuration with Options");

            //waiting for loading assets from options sample app
            po.waitForTextView(driver,"CuePoints and AdsControl Options");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "CuePoints and AdsControl Options");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.CuePointsOptionsFreewheelPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Click on Video create button
            po.clickOnCreateVideo(driver);
            Thread.sleep(1000);

            // Wait for the video to be generated
            po.waitForPresenceOfText(driver, "00:00");

            // Click on video play icon after video has been generated .
            po.playInNormalScreen(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();

            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Playing ", 36000);
            Thread.sleep(5000);

            po.smallScreenTap(driver,0);

            po.pauseInNormalScreen(driver);
            ev.verifyEvent("stateChanged - state: PAUSED", "Video has been paused", 40000);

            po.readTime(driver);

            po.seekVideo(driver);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 45000);
            Thread.sleep(1000);

            po.loadingSpinner(driver);

            po.readTime(driver);
            Thread.sleep(1000);

            po.resumeVideoInNormalscreen(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video resumed playback after pause ", 50000);

            //Wait for Ad to start and verify the adStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 65000);

            ev.verifyEvent("stateChanged - state: PLAYING", " Video resumed playback after Ad ", 66000);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 75000);

            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 80000);


        } catch (Exception e) {
            System.out.println("CuePointsAndAdsControlOptions throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"CuePointsAndAdsControlOptions");
            Assert.assertTrue(false, "This will fail!");
        }

    }

    @Test
    public void preload_PromoImage_options() throws Exception {

        try {
            // Creating an Object of optionsSampleApp class
            CompleteSampleApp po = new CompleteSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");
            // Wrire to console activity name of home screen app
            System.out.println("CompleteSampleApp App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            //Click on  Player Configuration with Options
            po.clickBasedOnText(driver,"Player Configuration with Options");

            //waiting for loading assets from options sample app
            po.waitForTextView(driver,"CuePoints and AdsControl Options");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Preload and PromoImage Options");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreloadOptionsPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Click on Video create button
            po.clickOnCreateVideo(driver);

            // Wait for the video to be generated
            po.waitForPresenceOfText(driver, "00:00");

            // Click on video play icon after video has been generated .
            po.playInNormalScreen(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();

            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 40000);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Playing ", 36000);
            Thread.sleep(5000);

            po.smallScreenTap(driver,0);

            po.pauseInNormalScreen(driver);
            ev.verifyEvent("stateChanged - state: PAUSED", "Video has been paused", 40000);

            po.readTime(driver);

            po.seekVideo(driver);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 45000);
            Thread.sleep(1000);

            po.loadingSpinner(driver);

            po.readTime(driver);
            Thread.sleep(1000);

            po.resumeVideoInNormalscreen(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video resumed playback after pause ", 50000);

            //Wait for Ad to start and verify the adStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 65000);

            ev.verifyEvent("stateChanged - state: PLAYING", " Video resumed playback after Ad ", 66000);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 75000);

            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 80000);

        } catch (Exception e) {
            System.out.println("preload_PromoImage_options throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"preload_PromoImage_options");
            Assert.assertTrue(false, "This will fail!");
        }

    }

    @Test
    public void preload_promo_IntialTime() throws Exception {

        try {
            // Creating an Object of optionsSampleApp class
            CompleteSampleApp po = new CompleteSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");
            // Wrire to console activity name of home screen app
            System.out.println("CompleteSampleApp App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            //Click on  Player Configuration with Options
            po.clickBasedOnText(driver,"Player Configuration with Options");

            //waiting for loading assets from options sample app
            po.waitForTextView(driver,"CuePoints and AdsControl Options");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Preload and Promo Options with Initial Time");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreloadWithInitTimePlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Click on Video create button
            po.clickOnCreateVideo(driver);

            //Wait for the video to be generated
            //po.waitForPresenceOfText(driver, "00:00");

            // Click on video play icon after video has been generated .
            //po.playInNormalScreen(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            ev.verifyEvent("seekCompleted", "video starting from predefined intial time",60000);
            Thread.sleep(2000);

            ev.verifyEvent("playCompleted", "video play completed",150000);

        } catch (Exception e) {
            System.out.println("preload_promo_IntialTime throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"preload_promo_IntialTime");
            Assert.assertTrue(false, "This will fail!");
        }


    }

    @Test
    public void timeout_Options() throws Exception {

        try {
            // Creating an Object of optionsSampleApp class
            CompleteSampleApp po = new CompleteSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);


            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");
            // Wrire to console activity name of home screen app
            System.out.println("CompleteSampleApp App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            //Click on  Player Configuration with Options
            po.clickBasedOnText(driver,"Player Configuration with Options");

            //waiting for loading assets from options sample app
            po.waitForTextView(driver,"CuePoints and AdsControl Options");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Timeout Options");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.TimeoutOptionsPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Click on Video create button
            po.clickOnCreateVideo(driver);

            // Wait for the video to be generated
            po.waitForPresenceOfText(driver, "00:00");

            // Click on video play icon after video has been generated .
            po.playInNormalScreen(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();

            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Playing ", 36000);
            Thread.sleep(5000);

            po.smallScreenTap(driver,0);

            po.pauseInNormalScreen(driver);
            ev.verifyEvent("stateChanged - state: PAUSED", "Video has been paused", 40000);

            po.readTime(driver);

            po.seekVideo(driver);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 45000);
            Thread.sleep(1000);

            po.loadingSpinner(driver);

            po.readTime(driver);
            Thread.sleep(1000);

            po.resumeVideoInNormalscreen(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video resumed playback after pause ", 50000);

            //Wait for Ad to start and verify the adStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 65000);

            ev.verifyEvent("stateChanged - state: PLAYING", " Video resumed playback after Ad ", 66000);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 75000);

            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 80000);


        } catch (Exception e) {
            System.out.println("timeout_Options throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"timeout_Options");
            Assert.assertTrue(false, "This will fail!");
        }

    }

    @Test
    public void server_side_TvRating() throws Exception {

        try {
            // Creating an Object of optionsSampleApp class
            CompleteSampleApp po = new CompleteSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");
            // Wrire to console activity name of home screen app
            System.out.println("CompleteSampleApp App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            //Click on  Player Configuration with Options
            po.clickBasedOnText(driver,"Player Configuration with Options");

            //waiting for loading assets from options sample app
            po.waitForTextView(driver,"CuePoints and AdsControl Options");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Server-Side TV Ratings");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.ServerConfiguredTVRatingsPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForTextView(driver,"00:00");

            //playing video
            po.playInNormalScreen(driver);
            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
            Thread.sleep(5000);

            po.screenTap(driver);

            po.pauseInNormalScreen(driver);
            ev.verifyEvent("stateChanged - state: PAUSED", "Video has been paused", 45000);

            po.readTime(driver);
            Thread.sleep(1000);

            po.seekVideo(driver);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 45000);

            po.loadingSpinner(driver);

            po.readTime(driver);
            Thread.sleep(1000);

            po.resumeVideoInNormalscreen(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 50000);

            ev.verifyEvent("playCompleted - state: LOADING", "video play completed",90000);

        } catch (Exception e) {
            System.out.println("server_side_TvRating throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"server_side_TvRating");
            Assert.assertTrue(false, "This will fail!");
        }


    }

    @Test
    public void tv_rating_config() throws Exception {

        try {
            // Creating an Object of optionsSampleApp class
            CompleteSampleApp po = new CompleteSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);


            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");
            // Wrire to console activity name of home screen app
            System.out.println("CompleteSampleApp App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            //Click on  Player Configuration with Options
            po.clickBasedOnText(driver,"Player Configuration with Options");

            //waiting for loading assets from options sample app
            po.waitForTextView(driver,"CuePoints and AdsControl Options");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "TV Ratings Configuration");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.TVRatingsPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Click on Video create button
            po.clickOnCreateVideo(driver);

            // Wait for the video to be generated
            po.waitForPresenceOfText(driver, "00:00");

            // Click on video play icon after video has been generated .
            po.playInNormalScreen(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();
            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(5000);

            po.smallScreenTap(driver,0);
            Thread.sleep(1000);

            po.pauseInNormalScreen(driver);
            ev.verifyEvent("stateChanged - state: PAUSED", "Video has been paused", 40000);

            po.readTime(driver);
            Thread.sleep(1000);

            po.seekVideo(driver);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 50000);
            Thread.sleep(3000);

            po.loadingSpinner(driver);

            po.readTime(driver);
            Thread.sleep(1000);

            po.resumeVideoInNormalscreen(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 60000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);


        } catch (Exception e) {
            System.out.println("tv_rating_config throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"tv_rating_config");
            Assert.assertTrue(false, "This will fail!");
        }

    }



}
