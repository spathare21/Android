package testpackage.tests.secureplayersampleapp;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import testpackage.pageobjects.SecurePlayerSampleApp;
import testpackage.utils.*;
import java.io.IOException;
import java.util.Properties;

public class BasicTests extends EventLogTest {

    LoadPropertyValues prop;
    Properties p;

    @BeforeClass
    public void beforeTest() throws Exception {

        // Get the Property Values
        prop = new LoadPropertyValues();
        p=prop.loadProperty("secureplayersampleapp.properties");
        // Set up and initialize android driver
        SetUpAndroidDriver setUpdriver = new SetUpAndroidDriver();
        driver = setUpdriver.setUpandReturnAndroidDriver(p.getProperty("udid"), p.getProperty("appDir"), p.getProperty("appValue"), p.getProperty("platformName"), p.getProperty("platformVersion"), p.getProperty("appPackageName"), p.getProperty("appActivityName"));
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        driver.manage().logs().get("logcat");
        // Push the log file to device
        PushLogFileToDevice logpush=new PushLogFileToDevice();
        logpush.pushLogFile();
        // check the current activity and start activity
        if(driver.currentActivity()!= p.get("appActivityName").toString()) {
            driver.startActivity(p.get("appPackageName").toString(),p.get("appActivityName").toString());
        }
        // display the screen mode to console
        System.out.println(" Screen Mode " + p.getProperty("ScreenMode"));
    }

    @AfterClass
    public void afterTest() throws InterruptedException, IOException {
        // close the app
        driver.closeApp();
        // quit the driver
        driver.quit();
        String prop = p.getProperty("appPackage");
    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws Exception {
        // removing the events log file after test is completed
        RemoveEventsLogFile.removeEventsFileLog();
    }

    @Test
    public void ooyalaIngestedPlayreadySmoothVod() throws Exception{

        try {
            // Creating an Object of SecurePlayerSampleApp class
            SecurePlayerSampleApp po = new SecurePlayerSampleApp();
            // wait till home screen of SecurePlayerSampleApp is opened
            po.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.SecurePlayerListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Secure Player Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video
            po.clickBasedOnText(driver, "Ooyala-Ingested Playready Smooth VOD");
            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.SecurePlayerPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            // wait for the start screen to appear
            po.waitForTextView(driver,"00:00");
            //play asset in normal screen
            po.playInNormalScreen(driver);
            //handle the loading spinner
            po.loadingSpinner(driver);
            EventVerification ev = new EventVerification();
            //verify play event
            ev.verifyEvent("playStarted", " Video Started to Play ", 50000);
            //pause the running of the script for brief time
            Thread.sleep(5000);
            // Tapping video for activate the controls.
            po.tap(driver);
            // Pausing video in normal screem
            po.pauseInNormalScreen(driver);
            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 50000);
            //seek the asset
            po.seekVideo(driver);
            //verify seek event
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 50000);
            //resume playback in normal screen
            po.resumeInNormalScreen(driver);
            //verify playing event
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 50000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 300000);
        }
        catch(Exception e){
            System.out.println("ooyalaIngestedPlayreadySmoothVOD  throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"ooyalaIngestedPlayreadySmoothVOD");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void playreadyHLSVodWithClosedCaptions() throws Exception{

        try {
            // Creating an Object of SecurePlayerSampleApp class
            SecurePlayerSampleApp po = new SecurePlayerSampleApp();
            // wait till home screen of SecurePlayerSampleApp is opened
            po.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.SecurePlayerListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Secure Player Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video
            po.clickBasedOnText(driver, "Playready HLS VOD with Closed Captions");
            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.SecurePlayerPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //wait for the start screen to appear
            po.waitForTextView(driver, "00:00");
            //play in the asset in normal screen
            po.playInNormalScreen(driver);
            //handle the loading spinner
            po.loadingSpinner(driver);
            EventVerification ev = new EventVerification();
            //verify play event
            ev.verifyEvent("playStarted", " Video Started to Play ", 50000);
            //pause the running of the script for brief time
            Thread.sleep(5000);
            // Tapping video for activate the controls.
            po.tap(driver);
            // Pausing video in normal screem
            po.pauseInNormalScreen(driver);
            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 50000);
            // click on cc button
            po.clickImagebuttons(driver, (2 - 1));
            // wait for languages to appear
            po.waitForTextView(driver,"Languages");
            po.clickRadiobuttons(driver, (7 - 1));
            //Checking the redio button and applying the assertion
            Assert.assertTrue(po.radioButtonChecked(driver, (7 - 1)));
            // closed Captions event verification
            ev.verifyEvent("closedCaptionsLanguageChanged", " CC of the Video Was Changed ", 30000);
            // come back to video playback
            driver.navigate().back();
            // seek the video
            po.seekVideo(driver);
            // verify the seek event
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 30000);
            // handle loading spinner
            po.loadingSpinner(driver);
            // resume the video playback
            po.resumeInNormalScreen(driver);
            // verify the playing event
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 45000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 300000);
        }
        catch(Exception e){
            System.out.println("Playready HLS VOD with Closed Captions  throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"playreadyHLSVodWithClosedCaptions");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void prePersonalize() throws Exception{
        try {
            // Creating an Object of SecurePlayerSampleApp class
            SecurePlayerSampleApp po = new SecurePlayerSampleApp();
            // wait till home screen of SecurePlayerSampleApp is opened
            po.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.SecurePlayerListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Secure Player Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video.
            po.clickBasedOnText(driver, "Pre-Personalize");
            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.SecurePlayerPrePersonalizedPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //wait for the start screen to appear
            po.waitForTextView(driver,"00:00");
            //play the video in normal screen
            po.playInNormalScreen(driver);
            //handle the loading spinner
            po.loadingSpinner(driver);
            //Play Started event Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 50000);
            //pause the running of the script for brief time
            Thread.sleep(5000);
            // Tapping video for activate the controls.
            po.tap(driver);
            // Pausing video in normal screem
            po.pauseInNormalScreen(driver);
            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 50000);
            // seek the video playback
            po.seekVideo(driver);
            // verify the seek completed event
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 30000);
            //handle the loading spinner
            po.loadingSpinner(driver);
            //resume the playback in normal screen
            po.resumeInNormalScreen(driver);
            //verify the playing event
            ev.verifyEvent("stateChanged - state: PLAYING", "Video Started to Play ", 45000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 300000);
        }
        catch(Exception e){
            System.out.println("Pre-Personalize  throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"prePersonalize");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void microsoftIngestedPlayreadySmoothVod() throws Exception{
        try {
            // Creating an Object of SecurePlayerSampleApp class
            SecurePlayerSampleApp po = new SecurePlayerSampleApp();
            // wait till home screen of SecurePlayerPlayBackApp is opened
            po.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.SecurePlayerListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Secure Player Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video.
            po.clickBasedOnText(driver, "Microsoft-Ingested Playready Smooth VOD");
            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.SecurePlayerPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            // wait for the start screen to appear
            po.waitForTextView(driver,"00:00");
            //play the video in normal screen
            po.playInNormalScreen(driver);
            //handle the loading spinner
            po.loadingSpinner(driver);
            //Play Started event Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 50000);
            //pause the running of the script for brief time
            Thread.sleep(5000);
            // Tapping video for activate the controls.
            po.tap(driver);
            // Pausing video in normal screem
            po.pauseInNormalScreen(driver);
            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 50000);
            //seek the video
            po.seekVideo(driver);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 30000);
            //handle the loading spinner
            po.loadingSpinner(driver);
            //resume the playback of the video
            po.resumeInNormalScreen(driver);
            //verify playing event
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 45000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 300000);
        }
        catch(Exception e){
            System.out.println("Microsoft-Ingested Playready Smooth VOD  throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"microsoftIngestedPlayreadySmoothVod");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void microsoftIngestedClearSmoothVod() throws Exception{
        try {
            // Creating an Object of SecurePlayerSampleApp class
            SecurePlayerSampleApp po = new SecurePlayerSampleApp();
            // wait till home screen of SecurePlayerBackApp is opened
            po.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.SecurePlayerListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Secure Player Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video.
            po.clickBasedOnText(driver, "Microsoft-Ingested Clear Smooth VOD");
            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.SecurePlayerPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //wait for the start screen to appear
            po.waitForTextView(driver,"00:00");
            //play the video in normal screen
            po.playInNormalScreen(driver);
            //handle the loading spinner
            po.loadingSpinner(driver);
            //Play Started event Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 50000);
            //pause the running of the script for brief time
            Thread.sleep(5000);
            // Tapping video for activate the controls.
            po.tap(driver);
            // Pausing video in normal screem
            po.pauseInNormalScreen(driver);
            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 50000);
            // seek the video
            po.seekVideo(driver);
            // verify the seek completed event
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 30000);
            //handle loading spinner
            po.loadingSpinner(driver);
            // resume the video playback in normal screen
            po.resumeInNormalScreen(driver);
            //verify the playing event
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 45000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 300000);
        }
        catch(Exception e){
            System.out.println("Microsoft-Ingested Clear Smooth VOD  throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"microsoftIngestedClearSmoothVod");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void ooyalaIngestedClearHLSVod() throws Exception{
        try {
            // Creating an Object of SecurePlayerSampleApp class
            SecurePlayerSampleApp po = new SecurePlayerSampleApp();
            // wait till home screen of SecurePlayaerSampleApp is opened
            po.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.SecurePlayerListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Secure Player Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video.
            po.clickBasedOnText(driver, "Ooyala-Ingested Clear HLS VOD");
            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.SecurePlayerPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //wait for the start screen to appear
            po.waitForTextView(driver,"00:00");
            //play the video in normal screen
            po.playInNormalScreen(driver);
            //handle the loading spinner
            po.loadingSpinner(driver);
            //Play Started event Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 50000);
            //pause the running of the script for brief time
            Thread.sleep(5000);
            // Tapping video for activate the controls.
            po.tap(driver);
            // Pausing video in normal screem
            po.pauseInNormalScreen(driver);
            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 50000);
            // seek the video playback
            po.seekVideo(driver);
            // verify the seek completed event
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 30000);
            //handle the loading spinner
            po.loadingSpinner(driver);
            //resume the playback in normal screen
            po.resumeInNormalScreen(driver);
            //verify the playing event
            ev.verifyEvent("stateChanged - state: PLAYING", "Video Started to Play ", 45000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 300000);
        }
        catch(Exception e){
            System.out.println("Ooyala-Ingested Clear HLS VOD  throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"ooyalaIngestedClearHLSVod");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void ooyalaSampleEncryptedHlsVod() throws Exception{
        try {
            // Creating an Object of SecurePlayerSampleApp class
            SecurePlayerSampleApp po = new SecurePlayerSampleApp();
            // wait till home screen of SecurePlayerSampleApp is opened
            po.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.SecurePlayerListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Secure Player Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Ooyala Sample Encrypted HLS VOD");
            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.SecurePlayerEHLSPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            // wait for the start screen to appear
            po.waitForTextView(driver, "00:00");
            //play asset in normal screen
            po.playInNormalScreen(driver);
            //handle the loading spinner
            po.loadingSpinner(driver);
            EventVerification ev = new EventVerification();
            //verify play event
            ev.verifyEvent("playStarted", " Video Started to Play ", 50000);
            //pause the running of the script for brief time
            Thread.sleep(5000);
            // Tapping video for activate the controls.
            po.tap(driver);
            // Pausing video in normal screem
            po.pauseInNormalScreen(driver);
            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 50000);
            //seek the asset
            po.seekVideo(driver);
            //verify seek event
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 50000);
            //resume playback in normal screen
            po.resumeInNormalScreen(driver);
            //verify playing event
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 50000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 300000);
        }
        catch(Exception e){
            System.out.println("ooyalaSampleEncryptedHlsVod  throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"ooyalaSampleEncryptedHlsVod");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void visualOnConfigurationOptions() throws Exception{
        try {
            // Creating an Object of SecurePlayerSampleApp class
            SecurePlayerSampleApp po = new SecurePlayerSampleApp();
            // wait till home screen of SecurePlayerSampleApp is opened
            po.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.SecurePlayerListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Secure Player Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video.
            po.clickBasedOnText(driver, "VisualOn Configuration Options");
            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.SecurePlayerOptionsPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //wait for the start screen to appear
            po.waitForTextView(driver,"00:00");
            //play the video in normal screen
            po.playInNormalScreen(driver);
            po.loadingSpinner(driver);
            //Play Started event Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 50000);
            //pause the running of the script for brief time
            Thread.sleep(5000);
            // Tapping video for activate the controls.
            po.tap(driver);
            // Pausing video in normal screem
            po.pauseInNormalScreen(driver);
            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 50000);
            //seek the video
            po.seekVideo(driver);
            //verify the seek completed event
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 30000);
            //handle the loading spinner
            po.loadingSpinner(driver);
            //resume the video playback
            po.resumeInNormalScreen(driver);
            //verify the playing event
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 45000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 300000);
        }
        catch(Exception e){
            System.out.println("VisualOn Configuration Options throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"visualOnConfigurationOptions");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void playreadyHlsWithOoyalaPlayerToken() throws Exception{
        try {
            // Creating an Object of SecurePlayerSampleApp class
            SecurePlayerSampleApp po = new SecurePlayerSampleApp();
            // wait till home screen of SecurePlayerSampleApp is opened
            po.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.SecurePlayerListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Secure Player Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Select one of the video.
            po.clickBasedOnText(driver, "Playready HLS with Ooyala Player Token");
            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.SecurePlayerOPTPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");
            //wait for the start screen to appear
            po.waitForTextView(driver,"00:00");
            //play the video in normal screen
            po.playInNormalScreen(driver);
            po.loadingSpinner(driver);
            //Play Started event Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 50000);
            //pause the running of the script for brief time
            Thread.sleep(5000);
            // Tapping video for activate the controls.
            po.tap(driver);
            // Pausing video in normal screem
            po.pauseInNormalScreen(driver);
            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 50000);
            //seek the video
            po.getXYSeekBarAndSeek(driver,20,400);
            //verify the seek completed event
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 30000);
            //handle the loading spinner
            po.loadingSpinner(driver);
           //resume the video playback
            po.resumeInNormalScreen(driver);
            //verify the playing event
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 45000);
            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 1500000);
        }
        catch(Exception e){
            System.out.println("Playready HLS with Ooyala Player Token throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"playreadyHlsWithOoyalaPlayerToken");
            Assert.assertTrue(false, "This will fail!");
        }
    }
}
