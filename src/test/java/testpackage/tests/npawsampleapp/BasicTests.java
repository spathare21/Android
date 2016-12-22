package testpackage.tests.npawsampleapp;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import testpackage.pageobjects.NpawSampleApp;
import testpackage.tests.exoPlayerSampleApp.BasicPlayBackBasicTest2;
import testpackage.utils.*;
import java.io.IOException;
import java.util.Properties;

    public class BasicTests extends EventLogTest {

    final static Logger logger = Logger.getLogger(BasicTests.class);

    LoadPropertyValues prop = new LoadPropertyValues();
    Properties p;

    @BeforeClass
    public void beforeTest() throws Exception {
        // closing all recent app from background.
        CloserecentApps.closeApps();
        logger.info("BeforeTest \n");

        logger.debug(System.getProperty("user.dir"));
        // Get Property Values
        LoadPropertyValues prop = new LoadPropertyValues();
        Properties p = prop.loadProperty("npawsampleapp.properties");

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
    public void beforeMethod() throws Exception {
        logger.info("beforeMethod \n");
        driver.manage().logs().get("logcat");
        PushLogFileToDevice logpush = new PushLogFileToDevice();
        logpush.pushLogFile();
        if(driver.currentActivity()!= "com.ooyala.sample.lists.NPAWYouboraListActivity") {
            driver.startActivity("com.ooyala.sample.npaw","com.ooyala.sample.lists.NPAWYouboraListActivity");
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
            p  = prop.loadProperty();
            String prop = p.getProperty("appPackage");
            Appuninstall.uninstall(prop);
        }

    @AfterMethod
    public void afterMethod(ITestResult result) throws Exception {
            // Waiting for all the events from sdk to come in .
            logger.info("AfterMethod \n");
            //ScreenshotDevice.screenshot(driver);
            RemoveEventsLogFile.removeEventsFileLog();
            Thread.sleep(10000);
        }

    //TODO Test Assets
    @Test
    public void aspectRatioTest() throws Exception{
          try
             {
            // Creating an Object of Npaw page object class
            NpawSampleApp po = new NpawSampleApp();
            // wait till home screen of Npaw Sample app is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.NPAWYouboraListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("Npaw Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "4:3 Aspect Ratio");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.NPAWDefaultPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");


            po.waitForTextView(driver,"00:00");
            Thread.sleep(1000);

            // Play started, Clicking on play button
            po.playInNormalScreen(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
            Thread.sleep(2000);

            //Pausing Video in Normal screen.
            po.pauseInNormalScreen(driver);

            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);

            po.seekVideo(driver);
            // Verifying event that seek has been done.
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 30000);

            //for hold the code if loading spinner displayed
            po.loadingSpinner(driver);
            // Starting video playback again
            po.resumeInNormalScreen(driver);
            // Verifying that playback has been started.
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 45000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 300000);
        }
        catch(Exception e){
            logger.error(" AspectRatioTest throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"AspectRatioTest");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void mp4() throws Exception{
        try
        {
            // Creating an Object of Npaw page object class
            NpawSampleApp po = new NpawSampleApp();
            // wait till home screen of Npaw Sample app is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.NPAWYouboraListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("Npaw Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "MP4 Video");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.NPAWDefaultPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForTextView(driver,"00:00");
            Thread.sleep(1000);

            po.playInNormalScreen(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
            Thread.sleep(2000);

            //Pausing Video in Normal screen.
            po.pauseInNormalScreen(driver);

            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);

            po.seekVideo(driver);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 30000);

            //for hold the code if loading spinner displayed
            po.loadingSpinner(driver);

            po.resumeInNormalScreen(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 45000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 300000);
        }
        catch(Exception e){
            logger.error(" MP4 throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"MP4");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void hls() throws Exception{
        try
        {
            // Creating an Object of Npaw page object class
            NpawSampleApp po = new NpawSampleApp();
            // wait till home screen of Npaw Sample app is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.NPAWYouboraListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("Npaw Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "HLS Video");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.NPAWDefaultPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForTextView(driver,"00:00");
            Thread.sleep(1000);

            po.playInNormalScreen(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
            Thread.sleep(2000);

            //Pausing Video in Normal screen.
            po.pauseInNormalScreen(driver);

            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);

            po.seekVideo(driver);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 30000);

            //for hold the code if loading spinner displayed
            po.loadingSpinner(driver);

            po.resumeInNormalScreen(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 45000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 300000);
        }
        catch(Exception e){
            logger.error(" HLS throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"hls");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void vodwithCCTest() throws Exception{
             try
             {
                 // Creating an Object of Npaw page object class
                 NpawSampleApp po = new NpawSampleApp();
                 // wait till home screen of Npaw Sample app is opened
                 po.waitForAppHomeScreen(driver);

             // Assert if current activity is indeed equal to the activity name of app home screen
             po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.NPAWYouboraListActivity");
             // Wrire to console activity name of home screen app
             logger.debug("Npaw Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

             //Pause the running of test for a brief time .
             Thread.sleep(3000);

             // Select one of the video HLS,MP4 etc .
             po.clickBasedOnText(driver, "VOD with CCs");
             Thread.sleep(2000);

             //verify if player was loaded
             po.waitForPresence(driver, "className", "android.view.View");
             // Assert if current activity is indeed equal to the activity name of the video player
             po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.NPAWDefaultPlayerActivity");
             // Print to console output current player activity
             logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

             po.waitForTextView(driver,"00:00");
             Thread.sleep(1000);

             po.playInNormalScreen(driver);

             Thread.sleep(1000);

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
            Thread.sleep(2000);

            //pausing the video
            po.pauseInNormalScreen(driver);
            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);

            // clicking on CC button
            po.clickImagebuttons(driver,(2-1));

             // Waiting for display the language
            po.waitForTextView(driver,"Languages");

             // Selecting the language by radio button
            po.clickRadiobuttons(driver,(7-1));

            Thread.sleep(500);

             // Assertion that has language has been selected
            Assert.assertTrue(po.radioButtonChecked(driver,(7-1)));

            // closed Captions event verification
            ev.verifyEvent("closedCaptionsLanguageChanged", " CC of the Video Was Changed ", 30000);

             // Getting back to player screen
            driver.navigate().back();

            // Pause the running of the test for a brief amount of time
            Thread.sleep(1000);

             // Seeking the video.
            po.seekVideo(driver);
             // Verifying that video has been seeked by event.
            ev.verifyEvent("seekCompleted","Playing Video was Seeked" ,30000);

            //for hold the code if loading spinner displayed
            po.loadingSpinner(driver);

            // Tap coordinates again to play
            po.resumeInNormalScreen(driver);
             // Verifying that video has been started playing from the pause state.
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 45000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        }
        catch(Exception e)
        {
            logger.error("VODwithCCTest throws Exception \n"+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VODwithCCTest");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void vastAdPreRollTest() throws Exception{

        try {
            // Creating an Object of Npaw page object class
            NpawSampleApp po = new NpawSampleApp();
            // wait till home screen of Npaw Sample app is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.NPAWYouboraListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("Npaw Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            //po.clickBasedOnText(driver, "VAST Ad Pre-roll");
            po.clickBasedOnTextScrollTo(driver, "VAST2 Ad Pre-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.NPAWDefaultPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForTextView(driver,"00:00");

            po.playInNormalScreen(driver);
            Thread.sleep(1000);
            //Ad Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("adStarted", " Ad Started to Play ", 20000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);

            //Play Started event verification
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            Thread.sleep(2000);

            //Pausing Video in Normal screen.
            po.pauseInNormalScreen(driver);
            // Verifying that video has been get paused
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);

            // Seeking the video
            po.seekVideo(driver);

            // Verifying the event that video has been seekeed
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 30000);

            //for hold the code if loading spinner displayed
            po.loadingSpinner(driver);

            // After seeking the video, resuming it from the pause state.
            po.playInNormalScreen(driver);
            // Verifying that video has been start playing again from the pause mode.
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 45000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        }
        catch(Exception e)
        {
            logger.error("VASTAdPreRollTest throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VASTAdPreRollTest");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void vastADMidRollTest() throws Exception{
             try
             {
             // Creating an Object of Npaw page object class
             NpawSampleApp po = new NpawSampleApp();
             // wait till home screen of Npaw Sample app is opened
             // po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.NPAWYouboraListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("Npaw Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnTextScrollTo(driver, "VAST2 Ad Mid-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.NPAWDefaultPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForTextView(driver,"00:00");

            // Video playback is starting.
            po.playInNormalScreen(driver);
            Thread.sleep(1000);

            //Play Started event verifying.
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            Thread.sleep(2000);

            //Pausing Video in Normal screen.
            po.pauseInNormalScreen(driver);
            // Verifying that video has been get paused
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);

            // Seeking the video
            po.seekVideo(driver);

            // Verifying the event that video has been seekeed
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 30000);

            //for hold the code if loading spinner displayed
            po.loadingSpinner(driver);

            // After seeking the video, resuming it from the pause state.
            po.playInNormalScreen(driver);
            // Verifying that video has been start playing again from the pause mode.
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 45000);

            //Ad Started Verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 100000);
        }
        catch(Exception e)
        {
            logger.error("VASTADMidRollTest throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VASTADMidRollTest");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void vastADPostRollTest() throws Exception{
        try
        {
            // Creating an Object of Npaw page object class
            NpawSampleApp po = new NpawSampleApp();
            // wait till home screen of Npaw Sample app is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.NPAWYouboraListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("Npaw Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnTextScrollTo(driver, "VAST2 Ad Post-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.NPAWDefaultPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForTextView(driver,"00:00");

            // PlayStarted. Started the playback
            po.playInNormalScreen(driver);
            Thread.sleep(1000);

            //Play Started
            EventVerification ev = new EventVerification();
            // Verifying event that play has been started.
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            Thread.sleep(2000);

            //Pausing Video in Normal screen.
            po.pauseInNormalScreen(driver);
            // Verifying that video has been get paused
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);

            // Seeking the video
            po.seekVideo(driver);

            // Verifying the event that video has been seekeed
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 30000);

            // After seeking the video, resuming it from the pause state.
            po.playInNormalScreen(driver);
            // Verifying that video has been start playing again from the pause mode.
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 45000);

            //Ad Started Verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 100000);
        }
        catch(Exception e)
        {
            logger.error("VASTADPostRollTest throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VASTADPostRollTest");
            Assert.assertTrue(false, "This will fail!");
        }
    }






}
