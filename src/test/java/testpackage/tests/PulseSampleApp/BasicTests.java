package testpackage.tests.PulseSampleApp;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import testpackage.pageobjects.pulseSampleApp;
import testpackage.utils.*;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Sachin on 09/08/16.
 */
public class BasicTests extends EventLogTest {
    @BeforeClass
    public void beforeTest() throws Exception {
        // closing all recent app from background.
        CloserecentApps.closeApps();
        System.out.println("BeforeTest \n");

        System.out.println(System.getProperty("user.dir"));
        // Get Property Values
        LoadPropertyValues prop = new LoadPropertyValues();
        Properties p = prop.loadProperty("pulsesampleapp.properties");

        System.out.println("Device id from properties file " + p.getProperty("deviceName"));
        System.out.println("PortraitMode from properties file " + p.getProperty("PortraitMode"));
        System.out.println("Path where APK is stored"+ p.getProperty("appDir"));
        System.out.println("APK name is "+ p.getProperty("appValue"));
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
        driver.manage().logs().get("logcat");
        PushLogFileToDevice logpush = new PushLogFileToDevice();
        logpush.pushLogFile();
        if (driver.currentActivity() != "com.ooyala.sample.lists.PulseListActivity") {
            driver.startActivity("com.ooyala.sample.PulseSampleApp", "com.ooyala.sample.lists.PulseListActivity");
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

    @AfterMethod
    public void afterMethod(ITestResult result) throws Exception {
        // Waiting for all the events from sdk to come in .
        System.out.println("AfterMethod \n");
        RemoveEventsLogFile.removeEventsFileLog();
        Thread.sleep(10000);

    }

    @Test
    public void NoAds() throws Exception{
        int[] locPlayButon;

        try {

            // Creating an Object of PulseSampleApp class
            pulseSampleApp po = new pulseSampleApp();
            // wait till home screen of Pulse is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.PulseListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Pulse Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            Thread.sleep(2000);
            // Select one of the video .
            po.clickBasedOnText(driver, "No ads");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PulsePlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver,"h");

            locPlayButon=po.locationTextOnScreen(driver,"h");

            //Clicking on Play button in Pulse
            po.getPlay(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //pause the video
            Thread.sleep(3000);
            //pausing the video.
            po.pauseVideo(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            Thread.sleep(1000);

            //seeking the video
            po.seek_video(driver, 800);

            ev.verifyEvent("seekCompleted", " Video seeking completed ", 70000);

            Thread.sleep(7000);
            // playing video again.
            po.getPlay(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 150000);

        }
        catch(Exception e)
        {
            System.out.println("No ads throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"Noads");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    //TODO : AdCompleted event not hance commented all adCompleted event verification getting https://jira.corp.ooyala.com/browse/PBA-4388
    @Test
    public void PrerollDemo() throws Exception{
        int[] locPlayButon;

        try {

            // Creating an Object of PulseSampleApp class
            pulseSampleApp po = new pulseSampleApp();
            // wait till home screen of Pulse is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.PulseListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Pulse Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            Thread.sleep(2000);
            // Select one of the video .
            po.clickBasedOnText(driver, "Preroll demo");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PulsePlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver,"h");

            locPlayButon=po.locationTextOnScreen(driver,"h");

            //Clicking on Play button in Pulse
            po.getPlay(driver);

            Thread.sleep(5000);

            //Ad Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            //Thread sleep time is equivalent to the length of the AD
            Thread.sleep(5000);

            //Ad Completed Verification
            //ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);
            Thread.sleep(1000);

            //Play Started
            ev.verifyEvent("playStarted", " Video Started to Play ", 60000);
            //pause the video
            Thread.sleep(3000);
            po.pauseVideo(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            Thread.sleep(1000);

            //seeking the video
            po.seek_video(driver, 700);

            ev.verifyEvent("seekCompleted", " Video seeking completed ", 70000);

            Thread.sleep(7000);

            // playing video again.
            po.getPlay(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 150000);

        }
        catch(Exception e)
        {
            System.out.println("PrerollDemo throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"PrerollDemo");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void MidrollDemo() throws Exception{
        int[] locPlayButon;

        try {

            // Creating an Object of PulseSampleApp class
            pulseSampleApp po = new pulseSampleApp();
            // wait till home screen of Pulse is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.PulseListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Pulse Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            Thread.sleep(2000);
            // Select one of the video .
            po.clickBasedOnText(driver, "Midroll demo");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PulsePlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver,"h");

            locPlayButon=po.locationTextOnScreen(driver,"h");

            //Clicking on Play button in Pulse
            po.getPlay(driver);

            Thread.sleep(2000);

            //Ad Started Verification
            EventVerification ev = new EventVerification();

            //Play Started
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            Thread.sleep(16000);

            //ad started
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
            //Thread sleep time is equivalent to the length of the AD
            Thread.sleep(5000);
            //Ad Completed Verification
            //ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);
            Thread.sleep(20000);

            //pause the video
            Thread.sleep(3000);
            po.pauseVideo(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            Thread.sleep(1000);

            //seeking the video
            po.seek_video(driver, 700);

            ev.verifyEvent("seekCompleted", " Video seeking completed ", 70000);

            Thread.sleep(7000);

            // playing video again.
            po.getPlay(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 150000);

        }
        catch(Exception e)
        {
            System.out.println("MidrollDemo throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"MidrollDemo");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void PostrollDemo() throws Exception{
        int[] locPlayButon;

        try {

            // Creating an Object of PulseSampleApp class
            pulseSampleApp po = new pulseSampleApp();
            // wait till home screen of Pulse is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.PulseListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Pulse Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            Thread.sleep(2000);
            // Select one of the video .
            po.clickBasedOnText(driver, "Postroll demo");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PulsePlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver,"h");

            locPlayButon=po.locationTextOnScreen(driver,"h");

            //Clicking on Play button in Pulse
            po.getPlay(driver);

            Thread.sleep(2000);

            //Ad Started Verification
            EventVerification ev = new EventVerification();

            //Play Started
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
            //pause the video
            Thread.sleep(3000);
            po.pauseVideo(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            Thread.sleep(1000);

            //seeking the video
            po.seek_video(driver, 800);

            ev.verifyEvent("seekCompleted", " Video seeking completed ", 70000);

            Thread.sleep(7000);

            // playing video again.
            po.getPlay(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

            Thread.sleep(2000);
            //Ad Started
            ev.verifyEvent("adStarted", " Ad Started to Play ", 150000);
            //Thread sleep time is equivalent to the length of the AD
            Thread.sleep(10000);
            //Ad Completed Verification
            //ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);
            Thread.sleep(1000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 250000);

        }
        catch(Exception e)
        {
            System.out.println("PostrollDemo throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"PostrollDemo");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void PreMidPostrollDemo() throws Exception{
        int[] locPlayButon;

        try {

            // Creating an Object of PulseSampleApp class
            pulseSampleApp po = new pulseSampleApp();
            // wait till home screen of Pulse is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.PulseListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Pulse Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            Thread.sleep(2000);
            // Select one of the video .
            po.clickBasedOnText(driver, "Pre-/mid-/postroll demo");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PulsePlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver,"h");

            locPlayButon=po.locationTextOnScreen(driver,"h");

            //Clicking on Play button in Pulse
            po.getPlay(driver);

            Thread.sleep(2000);

            //Play Started
            EventVerification ev = new EventVerification();
            //Ad Started Verification
            ev.verifyEvent("adStarted", " Pre - Ad Started to Play ", 30000);

            Thread.sleep(11000);

            //ev.verifyEvent("adCompleted", " Pre - Ad Playback Completed ", 30000);

            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //Thread sleep time is equivalent to the length of the video
            Thread.sleep(5000);

            //Ad Started Verification
            ev.verifyEvent("adPodStarted", " Mid - Ad Started to Play ", 30000);

            Thread.sleep(10000);

            //Ad Completed Verification
            //ev.verifyEvent("adCompleted", " Mid - Ad Playback Completed ", 30000);

            //Ad Started Verification
            ev.verifyEvent("adPodStarted", "Second Mid - Ad  Started to Play ", 90000);
            Thread.sleep(10000);
            //Ad Completed Verification
            //ev.verifyEvent("adCompleted", " Post - Ad Playback Completed ", 60000);
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video Started play", 70000);

            Thread.sleep(15000);

            po.pauseVideo(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 40000);

            Thread.sleep(1000);

            po.seek_video(driver,700);

            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 50000);

            Thread.sleep(2000);
            po.getPlay(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 70000);

            ev.verifyEvent("adPodStarted", "Post - Ad  Started to Play ", 150000);
            //Ad Completed Verification
            //ev.verifyEvent("adCompleted", " Post - Ad Playback Completed ", 60000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 250000);
        }
        catch(Exception e)
        {
            System.out.println("PreMidPostrollDemo throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"PreMidPostrollDemo");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    //@Test
    public void PreMidPostSkippable() throws Exception{
        int[] locPlayButon;

        try {

            // Creating an Object of PulseSampleApp class
            pulseSampleApp po = new pulseSampleApp();
            // wait till home screen of Pulse is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.PulseListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Pulse Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            Thread.sleep(2000);
            // Select one of the video .
            po.clickBasedOnText(driver, "Pre-/mid-/postroll skippable");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PulsePlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver,"h");

            locPlayButon=po.locationTextOnScreen(driver,"h");

            //Clicking on Play button in Pulse
            po.getPlay(driver);

            Thread.sleep(2000);

            //Play Started
            EventVerification ev = new EventVerification();
            //Ad Started Verification
            ev.verifyEvent("adStarted", " Pre - Ad Started to Play ", 30000);

            String skiptext="Skip Ad";
            driver.findElementByAndroidUIAutomator("new UiSelector().textContains(\""+skiptext+"\")");
            po.clickBasedOnText(driver,"Skip Ad");
            Thread.sleep(5000);
            //Ad Completed Verification
            //ev.verifyEvent("adCompleted", " Pre - Ad Playback Completed ", 30000);

            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //Thread sleep time is equivalent to the length of the video
            Thread.sleep(5000);

            //Ad Started Verification
            ev.verifyEvent("adPodStarted", " Mid - Ad Started to Play ", 30000);

            Thread.sleep(10000);

            //Ad Completed Verification
            //ev.verifyEvent("adCompleted", " Mid - Ad Playback Completed ", 30000);

            //Ad Started Verification
            ev.verifyEvent("adPodStarted", "Second Mid - Ad  Started to Play ", 90000);
            Thread.sleep(10000);
            //Ad Completed Verification
            //ev.verifyEvent("adCompleted", " Post - Ad Playback Completed ", 60000);
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video Started play", 70000);

            Thread.sleep(15000);

            po.pauseVideo(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 40000);

            Thread.sleep(1000);

            po.seek_video(driver, 700);

            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 50000);

            Thread.sleep(2000);
            po.getPlay(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING", "Video resumed", 70000);

            ev.verifyEvent("adPodStarted", "Post - Ad  Started to Play ", 90000);
            //Ad Completed Verification
            //ev.verifyEvent("adCompleted", " Post - Ad Playback Completed ", 60000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 250000);
        }
        catch(Exception e)
        {
            System.out.println("PreMidPostSkippable throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"PreMidPostSkippable");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @Test
    public void FrequencyCappingDemo() throws Exception{
        int[] locPlayButon;

        try {

            // Creating an Object of PulseSampleApp class
            pulseSampleApp po = new pulseSampleApp();
            // wait till home screen of Pulse is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.PulseListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Pulse Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            Thread.sleep(2000);
            // Select one of the video .
            po.clickBasedOnText(driver, "Frequency capping demo");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PulsePlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver,"h");

            locPlayButon=po.locationTextOnScreen(driver,"h");

            //Clicking on Play button in Pulse
            po.getPlay(driver);

            Thread.sleep(5000);

            //Ad Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            //Thread sleep time is equivalent to the length of the AD
            Thread.sleep(5000);

            //Ad Completed Verification
            //ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);
            Thread.sleep(1000);

            //Play Started
            ev.verifyEvent("playStarted", " Video Started to Play ", 60000);

            //pause the video
            Thread.sleep(25000);
            po.pauseVideo(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            Thread.sleep(1000);

            //seeking the video
            po.seek_video(driver, 700);

            ev.verifyEvent("seekCompleted", " Video seeking completed ", 70000);

            Thread.sleep(3000);
            // playing video again.
            po.getPlay(driver);
            System.out.println("clicked on play button");

            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 150000);

        }
        catch(Exception e)
        {
            System.out.println("FrequencyCappingDemo throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"FrequencyCappingDemo");
            Assert.assertTrue(false, "This will fail!");
        }
    }


    // app crash due to error log
    //@Test
    public void PrerolldemoAdFileInvalid() throws Exception{
        int[] locPlayButon;

        try {

            // Creating an Object of PulseSampleApp class
            pulseSampleApp po = new pulseSampleApp();
            // wait till home screen of Pulse is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.PulseListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Pulse Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            Thread.sleep(2000);
            // Select one of the video .
            po.clickBasedOnText(driver, "Preroll demo - Ad media file invalid");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PulsePlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver,"h");

            locPlayButon=po.locationTextOnScreen(driver,"h");

            //Clicking on Play button in Pulse
            po.getPlay(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //pause the video
            Thread.sleep(3000);
            //pausing the video.
            po.pauseVideo(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            Thread.sleep(1000);

            //seeking the video
            po.seek_video(driver, 700);

            ev.verifyEvent("seekCompleted", " Video seeking completed ", 70000);

            Thread.sleep(7000);

            // playing video again.
            po.getPlay(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);

        }
        catch(Exception e)
        {
            System.out.println("No ads throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"Noads");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    //@Test app crash
    public void PrerolldemoAdtimeout() throws Exception{
        int[] locPlayButon;

        try {

            // Creating an Object of PulseSampleApp class
            pulseSampleApp po = new pulseSampleApp();
            // wait till home screen of Pulse is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.PulseListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Pulse Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            Thread.sleep(2000);
            // Select one of the video .
            po.clickBasedOnText(driver, "Preroll demo - Ad media file timeout");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PulsePlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver,"h");

            locPlayButon=po.locationTextOnScreen(driver,"h");

            //Clicking on Play button in Pulse
            po.getPlay(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //pause the video
            Thread.sleep(3000);
            //pausing the video.
            po.pauseVideo(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            Thread.sleep(1000);

            //seeking the video
            po.seek_video(driver, 700);

            ev.verifyEvent("seekCompleted", " Video seeking completed ", 70000);

            Thread.sleep(7000);

            // playing video again.
            po.getPlay(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("Notification Received: playCompleted - state: INIT", " Video Completed Play ", 90000);

        }
        catch(Exception e)
        {
            System.out.println("No ads throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"Noads");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    //@Test app crash
    public void PrerolldemoAdFile404() throws Exception{
        int[] locPlayButon;

        try {

            // Creating an Object of PulseSampleApp class
            pulseSampleApp po = new pulseSampleApp();
            // wait till home screen of Pulse is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.PulseListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Pulse Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            Thread.sleep(2000);
            // Select one of the video .
            po.clickBasedOnText(driver, "Preroll demo - Ad media file 404");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PulsePlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver,"h");

            locPlayButon=po.locationTextOnScreen(driver,"h");

            //Clicking on Play button in Pulse
            po.getPlay(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //pause the video
            Thread.sleep(3000);
            //pausing the video.
            po.pauseVideo(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            Thread.sleep(1000);

            //seeking the video
            po.seek_video(driver, 700);

            ev.verifyEvent("seekCompleted", " Video seeking completed ", 70000);

            Thread.sleep(7000);

            // playing video again.
            po.getPlay(driver);

            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("Notification Received: playCompleted - state: INIT", " Video Completed Play ", 90000);

        }
        catch(Exception e)
        {
            System.out.println("No ads throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"Noads");
            Assert.assertTrue(false, "This will fail!");
        }
    }



}
