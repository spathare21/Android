package testpackage.tests.chromeCastSampleApp;

import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import testpackage.pageobjects.chromeCastSampleApp;
import testpackage.utils.*;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by bsondur on 8/16/16.
 */
public class BasicTests extends EventLogTest {
    final static Logger logger = Logger.getLogger(BasicTests.class);

    @BeforeClass
    public void beforeTest() throws Exception {

        logger.info("BeforeTest \n");

        logger.debug(System.getProperty("user.dir"));
        // Get Property Values
        LoadPropertyValues prop = new LoadPropertyValues();
        Properties p = prop.loadProperty("chromeCastSampleApp.properties");

        logger.debug("Device id from properties file " + p.getProperty("deviceName"));
        logger.debug("PortraitMode from properties file " + p.getProperty("PortraitMode"));
        logger.debug("Path where APK is stored" + p.getProperty("appDir"));
        logger.debug("APK name is " + p.getProperty("app"));
        logger.debug("Platform under Test is " + p.getProperty("platformName"));
        logger.debug("Mobile OS Version is " + p.getProperty("OSVERSION"));
        logger.debug("Package Name of the App is " + p.getProperty("appPackage"));
        logger.debug("Activity Name of the App is " + p.getProperty("appActivity"));

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
        if (driver.currentActivity() != "com.ooyala.sample.lists.ChromecastListActivity") {
            driver.startActivity("com.ooyala.sample.ChromecastSampleApp", "com.ooyala.sample.lists.ChromecastListActivity");
        }
        // Get Property Values
        LoadPropertyValues prop1 = new LoadPropertyValues();
        Properties p1 = prop1.loadProperty();

        logger.debug(" Screen Mode " + p1.getProperty("ScreenMode"));
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

    @AfterMethod
    //public void afterTest() throws InterruptedException, IOException {
    public void afterMethod(ITestResult result) throws Exception {
        // Waiting for all the events from sdk to come in .
        logger.info("AfterMethod \n");
        //RemoveEventsLogFile.removeEventsFileLog();
        Thread.sleep(10000);
    }

    @org.testng.annotations.Test
    public void HLSAssetTest() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            chromeCastSampleApp po = new chromeCastSampleApp();

            //get Cast ID String from properties file
            // Get Property Values
            LoadPropertyValues prop = new LoadPropertyValues();
            Properties p = prop.loadProperty("chromeCastSampleApp.properties");

            String castDeviceID = p.getProperty("castDeviceID");

            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.ChromecastListActivity");

            // Wrire to console activity name of home screen app
            logger.debug("ChromeCastSampleApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            //Select HLS Asset
            po.clickBasedOnText(driver, "HLS Asset");

            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.ChromecastPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForTextView(driver, "00:00");
            Thread.sleep(1000);

            po.playCoordinatesInNormalScreen(driver);

            po.clickCastButton(driver);
            po.waitForTextView(driver, castDeviceID);

            Thread.sleep(2000);

            po.clickBasedOnText(driver, castDeviceID);

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 40000);
            Thread.sleep(15000);

            // Click on the web area so that player screen shows up
            po.clickBasedOnText(driver, "This video is encoded with only HLS Streams");
            Thread.sleep(2000);

            po.pauseInNormalScreen(driver);
            //Pausing Video in Normal screen.
            Thread.sleep(2000);
            ev.verifyEvent("PAUSED", " Playing Video was Seeked ", 30000);

            po.readTime(driver);
            Thread.sleep(3000);

            po.seekVideo(driver,20,200);
            Thread.sleep(5000);
            //ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 30000);
            //Thread.sleep(5000);

            po.readTime(driver);

            Thread.sleep(2000);

            po.resumeFromPauseToPlayVideoInNormalscreen(driver);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 70000);
            Thread.sleep(3000);

            po.clickCastButton(driver);
            po.waitForTextView(driver, castDeviceID);

            Thread.sleep(1000);
            po.clickImagebuttons(driver, 0);

        } catch (Exception e) {
            logger.error(" HLS Asset Test throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver, "HLSAssetTest");
            Assert.assertTrue(false, "This will fail!");
        }

    }

    @org.testng.annotations.Test
    public void MP4VideoTest() throws Exception{

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            chromeCastSampleApp po = new chromeCastSampleApp();

            //get Cast ID String from properties file
            // Get Property Values
            LoadPropertyValues prop = new LoadPropertyValues();
            Properties p = prop.loadProperty("chromeCastSampleApp.properties");

            String castDeviceID = p.getProperty("castDeviceID");

            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.ChromecastListActivity");

            // Wrire to console activity name of home screen app
            logger.debug("ChromeCastSampleApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            //Select HLS Asset
            po.clickBasedOnText(driver,"MP4 Video");

            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.ChromecastPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForTextView(driver,"00:00");
            Thread.sleep(1000);

            po.playCoordinatesInNormalScreen(driver);

            po.clickCastButton(driver);
            po.waitForTextView(driver,castDeviceID);

            Thread.sleep(2000);

            po.clickBasedOnText(driver,castDeviceID);

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 40000);
            Thread.sleep(15000);

            // Click on the web area so that player screen shows up
            po.clickBasedOnText(driver,"This video is encoded with only MP4 Streams");
            Thread.sleep(2000);

            po.pauseInNormalScreen(driver);
            //Pausing Video in Normal screen.
            Thread.sleep(2000);
            ev.verifyEvent("PAUSED", " Playing Video was Seeked " , 30000);

            po.readTime(driver);
            Thread.sleep(3000);

            po.seekVideo(driver,20,200);
            Thread.sleep(5000);
            //ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 30000);
            //Thread.sleep(5000);

            po.readTime(driver);

            Thread.sleep(2000);

            po.resumeFromPauseToPlayVideoInNormalscreen(driver);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 70000);
            Thread.sleep(3000);

            po.clickCastButton(driver);
            po.waitForTextView(driver,castDeviceID);

            Thread.sleep(1000);
            po.clickImagebuttons(driver,0);

        }
        catch(Exception e)
        {
            logger.error(" MP4 Video Test throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"MP4VideoTest");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void EncryptedHLSAssetTest() throws Exception{

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            chromeCastSampleApp po = new chromeCastSampleApp();

            //get Cast ID String from properties file
            // Get Property Values
            LoadPropertyValues prop = new LoadPropertyValues();
            Properties p = prop.loadProperty("chromeCastSampleApp.properties");

            String castDeviceID = p.getProperty("castDeviceID");

            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.ChromecastListActivity");

            // Wrire to console activity name of home screen app
            logger.debug("ChromeCastSampleApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            //Select HLS Asset
            po.clickBasedOnText(driver,"Encrypted HLS Asset");

            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.ChromecastPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForTextView(driver,"00:00");
            Thread.sleep(1000);

            po.playCoordinatesInNormalScreen(driver);

            po.clickCastButton(driver);
            po.waitForTextView(driver,castDeviceID);

            Thread.sleep(2000);

            po.clickBasedOnText(driver,castDeviceID);

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 40000);
            Thread.sleep(15000);

            // Click on the web area so that player screen shows up
            po.clickBasedOnText(driver,"This video is protected with Encrypted HLS");
            Thread.sleep(2000);

            po.pauseInNormalScreen(driver);
            //Pausing Video in Normal screen.
            Thread.sleep(2000);
            ev.verifyEvent("PAUSED", " Playing Video was Seeked " , 30000);

            po.readTime(driver);
            Thread.sleep(3000);

            po.seekVideo(driver,20,200);
            Thread.sleep(5000);
            //ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 30000);
            //Thread.sleep(5000);

            po.readTime(driver);

            Thread.sleep(2000);

            po.resumeFromPauseToPlayVideoInNormalscreen(driver);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 70000);
            Thread.sleep(3000);

            po.clickCastButton(driver);
            po.waitForTextView(driver,castDeviceID);

            Thread.sleep(1000);
            po.clickImagebuttons(driver,0);

        }
        catch(Exception e)
        {
            logger.error(" Encrypted HLS Test throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"EncryptedHLSAssetTest");
            Assert.assertTrue(false, "This will fail!");
        }
    }


    @org.testng.annotations.Test
    public void VODCCTest() throws Exception{

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            chromeCastSampleApp po = new chromeCastSampleApp();

            //get Cast ID String from properties file
            // Get Property Values
            LoadPropertyValues prop = new LoadPropertyValues();
            Properties p = prop.loadProperty("chromeCastSampleApp.properties");

            String castDeviceID = p.getProperty("castDeviceID");

            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.ChromecastListActivity");

            // Wrire to console activity name of home screen app
            logger.debug("ChromeCastSampleApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            //Select HLS Asset
            po.clickBasedOnText(driver,"VOD CC");

            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.ChromecastPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForTextView(driver,"00:00");
            Thread.sleep(1000);

            po.playCoordinatesInNormalScreen(driver);

            po.clickCastButton(driver);
            po.waitForTextView(driver,castDeviceID);

            Thread.sleep(2000);

            po.clickBasedOnText(driver,castDeviceID);

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 40000);
            Thread.sleep(15000);

            // Click on the web area so that player screen shows up
            po.clickBasedOnText(driver,"VOD with Closed Captions");
            Thread.sleep(2000);

            po.pauseInNormalScreen(driver);
            //Pausing Video in Normal screen.
            Thread.sleep(2000);
            ev.verifyEvent("PAUSED", " Playing Video was Seeked " , 30000);

            Thread.sleep(1000);

            po.readTime(driver);

            Thread.sleep(1000);

            po.clickImagebuttonsCC(driver,2);

            Thread.sleep(2000);

            po.waitForTextView(driver,"Languages");

            po.clickRadiobuttons(driver,(7-1));

            Thread.sleep(2000);

            Assert.assertTrue(po.radioButtonChecked(driver,(7-1)));

            // closed Captions event verification
            ev.verifyEvent("closedCaptionsLanguageChanged", " CC of the Video Was Changed ", 30000);

            driver.navigate().back();

            // Pause the running of the test for a brief amount of time
            Thread.sleep(3000);

            po.readTime(driver);
            Thread.sleep(3000);

            po.seekVideo(driver,20,200);
            Thread.sleep(5000);
            //ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 30000);
            //Thread.sleep(5000);

            po.readTime(driver);

            Thread.sleep(2000);

            po.resumeFromPauseToPlayVideoInNormalscreen(driver);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 70000);
            Thread.sleep(3000);

            po.clickCastButton(driver);
            po.waitForTextView(driver,castDeviceID);

            Thread.sleep(1000);
            po.clickImagebuttons(driver,0);

        }
        catch(Exception e)
        {
            logger.error(" VOD CC Test throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"VODCCTest");
            Assert.assertTrue(false, "This will fail!");
        }
    }


    @org.testng.annotations.Test
    public void PlayreadySmoothClearHLSBackupTest() throws Exception{

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            chromeCastSampleApp po = new chromeCastSampleApp();

            //get Cast ID String from properties file
            // Get Property Values
            LoadPropertyValues prop = new LoadPropertyValues();
            Properties p = prop.loadProperty("chromeCastSampleApp.properties");

            String castDeviceID = p.getProperty("castDeviceID");

            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.ChromecastListActivity");

            // Wrire to console activity name of home screen app
            logger.debug("ChromeCastSampleApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            //Select Playready Smooth, Clear HLS Backup Asset
            po.clickBasedOnText(driver,"Playready Smooth, Clear HLS Backup");

            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.ChromecastPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForTextView(driver,"00:00");
            Thread.sleep(1000);

            po.playCoordinatesInNormalScreen(driver);

            po.clickCastButton(driver);
            po.waitForTextView(driver,castDeviceID);

            Thread.sleep(2000);

            po.clickBasedOnText(driver,castDeviceID);

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 40000);
            Thread.sleep(15000);

            // Click on the web area so that player screen shows up
            po.clickBasedOnText(driver,"Playready Smooth (HLS Backup)");
            Thread.sleep(2000);

            po.pauseInNormalScreen(driver);
            //Pausing Video in Normal screen.
            Thread.sleep(2000);
            ev.verifyEvent("PAUSED", " Playing Video was Seeked " , 30000);

            po.readTime(driver);
            Thread.sleep(3000);

            po.seekVideo(driver,20,100);
            Thread.sleep(5000);
            //ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 30000);
            Thread.sleep(5000);

            po.readTime(driver);

            Thread.sleep(2000);

            po.resumeFromPauseToPlayVideoInNormalscreen(driver);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 70000);
            Thread.sleep(3000);

            po.clickCastButton(driver);
            po.waitForTextView(driver,castDeviceID);

            Thread.sleep(1000);
            po.clickImagebuttons(driver,0);

        }
        catch(Exception e)
        {
            logger.error(" PlayreadySmoothClearHLSBackup Test throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"PlayreadySmoothClearHLSBackupTest");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void TwoAssetsAutoPlayed() throws Exception{

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            chromeCastSampleApp po = new chromeCastSampleApp();

            //get Cast ID String from properties file
            // Get Property Values
            LoadPropertyValues prop = new LoadPropertyValues();
            Properties p = prop.loadProperty("chromeCastSampleApp.properties");

            String castDeviceID = p.getProperty("castDeviceID");

            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.ChromecastListActivity");

            // Wrire to console activity name of home screen app
            logger.debug("ChromeCastSampleApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            //Select Playready Smooth, Clear HLS Backup Asset
            po.clickBasedOnText(driver,"2 Assets autoplayed");

            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.ChromecastPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForTextView(driver,"00:00");
            Thread.sleep(1000);

            po.playCoordinatesInNormalScreen(driver);

            po.clickCastButton(driver);
            po.waitForTextView(driver,castDeviceID);

            Thread.sleep(2000);

            po.clickBasedOnText(driver,castDeviceID);

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 40000);
            Thread.sleep(15000);

            // Click on the web area so that player screen shows up
            po.clickBasedOnText(driver,"This video is encoded with only HLS Streams");
            Thread.sleep(2000);

            po.pauseInNormalScreen(driver);
            //Pausing Video in Normal screen.
            Thread.sleep(2000);
            ev.verifyEvent("PAUSED", " Playing Video was Seeked " , 30000);

            po.readTime(driver);
            Thread.sleep(3000);

            po.seekVideo(driver,20,200);
            Thread.sleep(5000);
            //ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 30000);
            Thread.sleep(5000);

            po.readTime(driver);

            Thread.sleep(2000);

            po.resumeFromPauseToPlayVideoInNormalscreen(driver);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 70000);
            Thread.sleep(3000);

            // Verify play of second video

            //Play Started Verification
            ev.verifyEvent("playStarted", " Video Started to Play ", 40000);
            Thread.sleep(15000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
            Thread.sleep(3000);

            po.clickCastButton(driver);
            po.waitForTextView(driver,castDeviceID);

            Thread.sleep(1000);
            po.clickImagebuttons(driver,0);

        }
        catch(Exception e)
        {
            logger.error(" TwoAssetsAutoPlayed Test throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"PlayreadySmoothClearHLSBackupTest");
            Assert.assertTrue(false, "This will fail!");
        }
    }





}
