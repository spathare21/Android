package testpackage.tests.optionsSampleApp;


import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import testpackage.pageobjects.OptionsSampleApp;
import testpackage.utils.*;
import java.io.IOException;
import java.util.Properties;

public class BasicTests extends EventLogTest {
    //Creating an Object of optionsSampleApp class
    OptionsSampleApp optionsSampleApp = new OptionsSampleApp();
    LoadPropertyValues prop = new LoadPropertyValues();
    Properties p ;

    final static Logger logger = Logger.getLogger(BasicTests.class);
    @BeforeClass
    public void beforeTest() throws Exception {
        // closing all recent app from background.
        CloserecentApps.closeApps();
        logger.info("BeforeTest \n");

        logger.debug(System.getProperty("user.dir"));
        // Get Property Values
        p = prop.loadProperty("optionsSampleApp.properties");
        SetUpAndroidDriver setUpdriver = new SetUpAndroidDriver();
        driver = setUpdriver.setUpandReturnAndroidDriver(p.getProperty("udid"), p.getProperty("appDir"), p.getProperty("appValue"), p.getProperty("platformName"), p.getProperty("platformVersion"), p.getProperty("appPackage"), p.getProperty("appActivity"));
        Thread.sleep(2000);
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        logger.info("beforeMethod \n");
        PushLogFileToDevice logpush = new PushLogFileToDevice();
        logpush.pushLogFile();
        if (driver.currentActivity() != "com.ooyala.sample.lists.OptionsListActivity") {
            driver.startActivity("com.ooyala.sample.OptionsSampleApp", "com.ooyala.sample.lists.OptionsListActivity");
        }
        // Get Property Values
        p = prop.loadProperty();
        logger.debug(" Screen Mode " + p.getProperty("ScreenMode"));

    }

    @AfterClass
    public void afterTest() throws InterruptedException, IOException {
        logger.info("AfterTest \n");
        driver.closeApp();
        driver.quit();
        p = prop.loadProperty();
        String prop = p.getProperty("appPackage");
        Appuninstall.uninstall(prop);
    }
    @AfterMethod
    //public void afterTest() throws InterruptedException, IOException {
    public void afterMethod(ITestResult result) throws Exception {
        // Waiting for all the events from sdk to come in .
        logger.info("AfterMethod \n");
        RemoveEventsLogFile.removeEventsFileLog();
        Thread.sleep(10000);

    }

    @Test
    public void CuePointsAndAdsControlOptions() throws Exception {
        try {
            // wait till home screen of basicPlayBackApp is opened
            optionsSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            optionsSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OptionsListActivity");
            // Write to console activity name of home screen app
            logger.debug("OptionsSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Selecting CuePoint and AdsControl Options
            optionsSampleApp.clickBasedOnText(driver, "CuePoints and AdsControl Options");

            //verify if player was loaded
            optionsSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            optionsSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.CuePointsOptionsFreewheelPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Click on Video create button
            optionsSampleApp.clickButtons(driver, 0);

            // Wait for the video to be generated
            optionsSampleApp.waitForPresenceOfText(driver, "00:00");

            // Click on video play icon after video has been generated .
            optionsSampleApp.playInNormalScreen(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Preroll ad Playback started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
            //Preroll ad Playback completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 40000);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 40000);
            Thread.sleep(4000);
            //pausing the video
            optionsSampleApp.pauseSmallplayer(driver);
            //Verifying the pause event
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 60000);
            //Reading the time after pause the video
            optionsSampleApp.readTime(driver);

            //Seeking the video
            optionsSampleApp.seekVideo(driver);
            // After seek the video, Verifying the seek completed event
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 65000);
            //Handling the loading spinner after seek the video, If displayed
            optionsSampleApp.loadingSpinner(driver);

            // Reading the time after pause and seek the video
            optionsSampleApp.readTime(driver);
            //Resuming the video after pause and seek function
            optionsSampleApp.resumeInNormalScreen(driver);
            //Verifying the video playback resuming event
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 70000);


            //Midroll ad Playback started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 55000);
            //Midroll ad Playback completed event verification.
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 65000);


            //Postroll ad Playback started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 80000);
            //Postroll ad Playback completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 90000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 100000);
        } catch (Exception e) {
            logger.error("CuePointsAndAdsControlOptions throws Exception " + e);
            ScreenshotDevice.screenshot(driver,"CuePointsAndAdsControlOptions");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void preload_PromoImage_options() throws Exception {
        try {
            // wait till home screen of basicPlayBackApp is opened
            optionsSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            optionsSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OptionsListActivity");
            // Write to console activity name of home screen app
            logger.debug("OptionsSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //Pause the running of test for a brief time .
            // Selecting Preload and Promo Image Options Asset
            optionsSampleApp.clickBasedOnText(driver, "Preload and PromoImage Options");

            //verify if player was loaded
            optionsSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            optionsSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreloadOptionsPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Click on Video create button
            optionsSampleApp.clickButtons(driver, 0);

            // Wait for the video to be generated
            optionsSampleApp.waitForPresenceOfText(driver, "00:00");

            // Click on video play icon after video has been generated .
            optionsSampleApp.playInNormalScreen(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Preroll ad Playback started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
            //Preroll ad Playback completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 40000);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 40000);
            Thread.sleep(4000);
            //pausing the video
            optionsSampleApp.pauseSmallplayer(driver);
            //Verifying the pause event
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 60000);
            //Reading the time after pause the video
            optionsSampleApp.readTime(driver);

            //Seeking the video
            optionsSampleApp.seekVideo(driver);
            // After seek the video, Verifying the seek completed event
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 65000);
            //Handling the loading spinner after seek the video, If displayed
            optionsSampleApp.loadingSpinner(driver);

            // Reading the time after pause and seek the video
            optionsSampleApp.readTime(driver);
            //Resuming the video after pause and seek function
            optionsSampleApp.resumeInNormalScreen(driver);
            //Verifying the video playback resuming event
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 70000);


            //Midroll ad Playback started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 55000);
            //Midroll ad Playback completed event verification.
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 65000);


            //Postroll ad Playback started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 80000);
            //Postroll ad Playback completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 90000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 100000);
        } catch (Exception e) {
            logger.error("preload_PromoImage_options throws Exception " + e);
            ScreenshotDevice.screenshot(driver,"preload_PromoImage_options");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void preload_promo_InitialTime() throws Exception {
        try {
            // wait till home screen of basicPlayBackApp is opened
            optionsSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            optionsSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OptionsListActivity");
            // Write to console activity name of home screen app
            logger.debug("OptionsSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Selecting Preload and Promo Initial time asset
            optionsSampleApp.clickBasedOnText(driver, "Preload and Promo Options with Initial Time");

            //verify if player was loaded
            optionsSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            optionsSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreloadWithInitTimePlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Click on Video create button
            optionsSampleApp.clickButtons(driver, 0);

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            ev.verifyEvent("seekCompleted - state: PLAYING", "video starting from predefined initial time",60000);

            ev.verifyEvent("playCompleted", "video play completed",90000);

        } catch (Exception e) {

            logger.error("preload_promo_IntialTime throws Exception " + e);
            ScreenshotDevice.screenshot(driver,"preload_promo_InitialTime");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void server_side_TvRating() throws Exception {
        try {
            // wait till home screen of basicPlayBackApp is opened
            optionsSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            optionsSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OptionsListActivity");
            // Write to console activity name of home screen app
            logger.debug("OptionsSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Selecting Server side TV Rating asset
            optionsSampleApp.clickBasedOnText(driver, "Server-Side TV Ratings");
            //verify if player was loaded
            optionsSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            optionsSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.ServerConfiguredTVRatingsPlayerActivity");

            // Wait for the video to be generated
            optionsSampleApp.waitForPresenceOfText(driver, "00:00");
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            // Click on video play icon after video has been generated .
            optionsSampleApp.playInNormalScreen(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 40000);
            Thread.sleep(4000);
            //pausing the video
            optionsSampleApp.pauseInNormalScreen(driver);
            //Verifying the pause event
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 60000);
            //Reading the time after pause the video
            optionsSampleApp.readTime(driver);

            //Seeking the video
            optionsSampleApp.seekVideoForLong(driver);
            // After seek the video, Verifying the seek completed event
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 65000);
            //Handling the loading spinner after seek the video, If displayed
            optionsSampleApp.loadingSpinner(driver);

            // Reading the time after pause and seek the video
            optionsSampleApp.readTime(driver);
            //Resuming the video after pause and seek function
            optionsSampleApp.resumeInNormalScreen(driver);
            //Verifying the video playback resuming event
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 70000);
            // Video playback completed event verification
            ev.verifyEvent("playCompleted", "video play completed",90000);
        } catch (Exception e) {
            logger.error("server_side_TvRating throws Exception " + e);
            ScreenshotDevice.screenshot(driver, "server_side_TvRating");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void tv_rating_config() throws Exception {
        try {
            // wait till home screen of basicPlayBackApp is opened
            optionsSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            optionsSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OptionsListActivity");
            // Selecting Tv Rating and configuration asset
            optionsSampleApp.clickBasedOnText(driver, "TV Ratings Configuration");
            // Write to console activity name of home screen app
            logger.debug("OptionsSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            //verify if player was loaded
            optionsSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            optionsSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.TVRatingsPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Click on Video create button
            optionsSampleApp.clickButtons(driver, 0);

            // Wait for the video to be generated
            optionsSampleApp.waitForPresenceOfText(driver, "00:00");

            // Click on video play icon after video has been generated .
            optionsSampleApp.playInNormalScreen(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 40000);
            Thread.sleep(4000);
            //pausing the video
            optionsSampleApp.pauseSmallplayer(driver);
            //Verifying the pause event
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 60000);
            //Reading the time after pause the video
            optionsSampleApp.readTime(driver);

            //Seeking the video
            optionsSampleApp.seekVideo(driver);

            // After seek the video, Verifying the seek completed event
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 65000);
            //Handling the loading spinner after seek the video, If displayed
            optionsSampleApp.loadingSpinner(driver);

            // Reading the time after pause and seek the video
            optionsSampleApp.readTime(driver);
            //Resuming the video after pause and seek function
            optionsSampleApp.resumeInNormalScreen(driver);
            //Verifying the video playback resuming event
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 70000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 100000);
        } catch (Exception e) {
            logger.error("tv_rating_config throws Exception " + e);
            ScreenshotDevice.screenshot(driver,"tv_rating_config");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void prevent_video_view_sharing_options() throws Exception {
        try {
            // wait till home screen of basicPlayBackApp is opened
            optionsSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            optionsSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OptionsListActivity");
            // Write to console activity name of home screen app
            logger.debug("OptionsSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Selecting IQ Configuration asset
            optionsSampleApp.clickBasedOnText(driver, "Prevent Video View Sharing Option");
            //verify if player was loaded
            optionsSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            optionsSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreventVideoViewSharingPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            // Wait for the video to be generated
            optionsSampleApp.waitForPresenceOfText(driver, "00:00");

            // Click on video play icon after video has been generated .
            optionsSampleApp.playInNormalScreen(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 40000);
            Thread.sleep(4000);
            //pausing the video
            optionsSampleApp.pauseInNormalScreen(driver);
            //Verifying the pause event
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 60000);
            //Reading the time after pause the video
            optionsSampleApp.readTime(driver);

            //Seeking the video
            optionsSampleApp.seekVideoForLong(driver);
            // After seek the video, Verifying the seek completed event
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 65000);
            //Handling the loading spinner after seek the video, If displayed
            optionsSampleApp.loadingSpinner(driver);

            // Reading the time after pause and seek the video
            optionsSampleApp.readTime(driver);
            //Resuming the video after pause and seek function
            optionsSampleApp.resumeInNormalScreen(driver);
            //Verifying the video playback resuming event
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 70000);
            // Video playback completed event verification
            ev.verifyEvent("playCompleted", "video play completed",90000);
        } catch (Exception e) {
            logger.error("present_video_view_sharing_options throws Exception " + e);
            ScreenshotDevice.screenshot(driver,"present_video_view_sharing_options");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void timeout_Options() throws Exception {
        try {
            // wait till home screen of basicPlayBackApp is opened
            optionsSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            optionsSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OptionsListActivity");
            // Selecting Timeout Options asset
            optionsSampleApp.clickBasedOnText(driver, "Timeout Options");
            logger.debug("optionsSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //verify if player was loaded
            optionsSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            optionsSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.TimeoutOptionsPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Click on Video create button
            optionsSampleApp.clickButtons(driver, 0);

            // Wait for the video to be generated
            optionsSampleApp.waitForPresenceOfText(driver, "00:00");

            // Click on video play icon after video has been generated .
            optionsSampleApp.playInNormalScreen(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Preroll ad Playback started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
            //Preroll ad Playback completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 40000);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 40000);
            Thread.sleep(4000);
            //pausing the video
            optionsSampleApp.pauseSmallplayer(driver);
            //Verifying the pause event
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 60000);
            //Reading the time after pause the video
            optionsSampleApp.readTime(driver);

            //Seeking the video
            optionsSampleApp.seekVideo(driver);
            // After seek the video, Verifying the seek completed event
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 65000);
            //Handling the loading spinner after seek the video, If displayed
            optionsSampleApp.loadingSpinner(driver);

            // Reading the time after pause and seek the video
            optionsSampleApp.readTime(driver);
            //Resuming the video after pause and seek function
            optionsSampleApp.resumeInNormalScreen(driver);
            //Verifying the video playback resuming event
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 70000);


            //Midroll ad Playback started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 55000);
            //Midroll ad Playback completed event verification.
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 65000);


            //Postroll ad Playback started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 80000);
            //Postroll ad Playback completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 90000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 100000);
        }
        catch(Exception e)
        {
            logger.error("CuePointsAndAdsControlOptions_cuePointOff throws Exception "+e);
            ScreenshotDevice.screenshot(driver,"CuePointsAndAdsControlOptions_cuePointOff");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void iq_Configuration() throws Exception {
        try {
            // wait till home screen of basicPlayBackApp is opened
            optionsSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            optionsSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.OptionsListActivity");
            // Write to console activity name of home screen app
            logger.debug("OptionsSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Selecting IQ Configuration asset
            optionsSampleApp.clickBasedOnText(driver, "IQ Configuration");
            //verify if player was loaded
            optionsSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            optionsSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.IQConfigurationPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            // Wait for the video to be generated
            optionsSampleApp.waitForPresenceOfText(driver, "00:00");

            // Click on video play icon after video has been generated .
            optionsSampleApp.playInNormalScreen(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 40000);
            Thread.sleep(4000);
            //pausing the video
            optionsSampleApp.pauseInNormalScreen(driver);
            //Verifying the pause event
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 60000);
            //Reading the time after pause the video
            optionsSampleApp.readTime(driver);

            //Seeking the video
            optionsSampleApp.seekVideoForLong(driver);
            // After seek the video, Verifying the seek completed event
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 65000);
            //Handling the loading spinner after seek the video, If displayed
            optionsSampleApp.loadingSpinner(driver);

            // Reading the time after pause and seek the video
            optionsSampleApp.readTime(driver);
            //Resuming the video after pause and seek function
            optionsSampleApp.resumeInNormalScreen(driver);
            //Verifying the video playback resuming event
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 70000);
            // Video playback completed event verification
            ev.verifyEvent("playCompleted", "video play completed",90000);
        } catch (Exception e) {
            logger.error("timeout_Options throws Exception " + e);
            ScreenshotDevice.screenshot(driver, "iq_Configuration");
            Assert.assertTrue(false, "This will fail!");
        }
    }
}