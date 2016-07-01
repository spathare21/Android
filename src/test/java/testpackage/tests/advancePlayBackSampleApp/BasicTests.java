package testpackage.tests.advancePlayBackSampleApp;

import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import testpackage.pageobjects.advancePlayBackSampleApp;
import testpackage.utils.*;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Sachin on 3/31/2016.
 */
public class BasicTests {
    private static AndroidDriver driver;

    @BeforeClass
    public void beforeTest() throws Exception {
        // closing all recent app from background.
        //CloserecentApps.closeApps();
        System.out.println("BeforeTest \n");

        System.out.println(System.getProperty("user.dir"));
        // Get Property Values
        LoadPropertyValues prop = new LoadPropertyValues();
        Properties p = prop.loadProperty("advanceplaybacksampleapp.properties");

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
        if (driver.currentActivity() != "com.ooyala.sample.lists.AdvancedPlaybackListActivity") {
            driver.startActivity("com.ooyala.sample.AdvancedPlaybackSampleApp", "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
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
    //public void afterTest() throws InterruptedException, IOException {
    public void afterMethod() throws InterruptedException, IOException {
        // Waiting for all the events from sdk to come in .
        System.out.println("AfterMethod \n");
        //ScreenshotDevice.screenshot(driver);
        RemoveEventsLogFile.removeEventsFileLog();
        Thread.sleep(10000);

    }

    //TODO : create unique file names for snapshots taken .
    @org.testng.annotations.Test
    public void playWithIntitialTime() throws Exception {

        try
        {
            // Creating an Object of BasicPlaybackSampleApp class
            advancePlayBackSampleApp po = new advancePlayBackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Play With InitialTime");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            System.out.println("after wait for presence");

            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PlayWithInitialTimePlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.playInNormalScreen(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            Thread.sleep(7000);

            po.playInNormalScreen(driver);

            po.screenTap(driver);

            po.pauseInNormalScreen(driver);

            ev.verifyEvent("stateChanged - state: PAUSED", "Video has been paused", 40000);

            po.seekVideoFullscreen(driver);
            po.playInNormalScreen(driver);

            ev.verifyEvent("seekCompleted - state: PLAYING", "video starting from predefined intial time",70000);

            po.pauseInNormalScreen(driver);
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video started playing again", 80000);

            ev.verifyEvent("playCompleted - state: LOADING", "video play completed",100000);

        }
        catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }

    }


    @org.testng.annotations.Test
    public void multipleVideoPlayback() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            advancePlayBackSampleApp po = new advancePlayBackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Multiple Video Playback");
            Thread.sleep(000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            System.out.println("after wait for presence");

           // po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.MultipleVideosPlayerActivity");
            // Print to console output current player activity
            //System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //1st video Play Started Verification

            Thread.sleep(5000);

            po.playInNormalScreen(driver);
            Thread.sleep(1000);
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //2nd video start playing in queue
            ev.verifyEvent(" playStarted - state: READY", "2nd video start playing in queue",60000);

            // video completed event verification.
            ev.verifyEvent("playCompleted - state: LOADING", "video play completed", 90000);
            Thread.sleep(50000);

        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }

    }

    @org.testng.annotations.Test
    public void insertAtRunTime() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            advancePlayBackSampleApp po = new advancePlayBackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Insert Ad at Runtime");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            System.out.println("after wait for presence");

            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.InsertAdPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            ev.verifyEvent("playCompleted - state: LOADING", "video play completed", 90000);

        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }

    }
/*
    @org.testng.annotations.Test
    public void changeVideoProgramatically() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            advancePlayBackSampleApp po = new advancePlayBackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Change Video Programatically");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            System.out.println("after wait for presence");

            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.ChangeVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            ev.verifyEvent("playCompleted - state: LOADING", "video play completed", 90000);

        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }

    }

    @org.testng.annotations.Test
    public void customPluginSample() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            advancePlayBackSampleApp po = new advancePlayBackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Custom Plugin Sample");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            System.out.println("after wait for presence");

            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PluginPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            ev.verifyEvent("playCompleted - state: LOADING", "video play completed", 90000);

        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }

    }

    @org.testng.annotations.Test
    public void customControls() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            advancePlayBackSampleApp po = new advancePlayBackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Custom Controls");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            System.out.println("after wait for presence");

            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.CustomControlsPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            ev.verifyEvent("playCompleted - state: LOADING", "video play completed", 90000);

        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }

    }

    @org.testng.annotations.Test
    public void customOverlay() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            advancePlayBackSampleApp po = new advancePlayBackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Custom Overlay");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            System.out.println("after wait for presence");

            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.CustomOverlayPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            ev.verifyEvent("playCompleted - state: LOADING", "video play completed", 90000);

        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }

    }

    @org.testng.annotations.Test
    public void insertAtRunTime_VastAd() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            advancePlayBackSampleApp po = new advancePlayBackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Insert Ad at Runtime");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            System.out.println("after wait for presence");

            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.InsertAdPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            Thread.sleep(5000);

            //clicking on InsertVastAD option , inserting VAST Ad
            po.clickOnVastAd(driver);

            // Verifying if VAST Ad started
            ev.verifyEvent("adStarted", " Vast Ad Started to Play ", 30000);
            // Verifying if VAST Ad completed
            ev.verifyEvent("adCompleted","Vast Ad completed to play",30000);
            // Veirfying if video completed
            ev.verifyEvent("playCompleted", "video play completed", 90000);

            Thread.sleep(2000);

        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }

    }

    @org.testng.annotations.Test
    public void insertAtRunTime_OoyalaAd() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            advancePlayBackSampleApp po = new advancePlayBackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Insert Ad at Runtime");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            System.out.println("after wait for presence");

            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.InsertAdPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            Thread.sleep(5000);

            //clicking on InsertOoyalaAD option , inserting Ooyala Ad
            po.clickOnOoyalaAd(driver);

            // Verifying if Ooyala Ad started
            ev.verifyEvent("adStarted", " Ooyala Ad Started to Play ", 30000);
            // Verifying if Ooyala Ad completed
            ev.verifyEvent("adCompleted","Ooyala Ad completed to play",30000);
            // Veirfying if video completed
            ev.verifyEvent("playCompleted", "video play completed", 90000);

            Thread.sleep(2000);


        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }

    }

    @org.testng.annotations.Test
    public void changeVideoProgramatically_P1() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            advancePlayBackSampleApp po = new advancePlayBackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Change Video Programatically");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            System.out.println("after wait for presence");

            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.ChangeVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Main Video Started to Play ", 30000);

            Thread.sleep(2000);

            po.clickOnP1(driver);

            // verifing that next video started to play
            ev.verifyEvent("playStarted", "Inserted video1 started to play", 50000);

            //Next video play completed.
            ev.verifyEvent("playCompleted", "Inserted video1 ended play", 50000);


        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }

    }

    @org.testng.annotations.Test
    public void changeVideoProgramatically_P2() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            advancePlayBackSampleApp po = new advancePlayBackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Change Video Programatically");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            System.out.println("after wait for presence");

            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.ChangeVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("playStarted", " Main Video Started to Play ", 30000);

            Thread.sleep(2000);

            po.clickOnP2(driver);

            // verifing that next video started to play
            ev.verifyEvent("playStarted", "Inserted video2 started to play", 50000);

            //Next video play completed.
            ev.verifyEvent("playCompleted", "Inserted video2 ended play", 50000);

        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }

    }

    @org.testng.annotations.Test
    public void customPluginSample_Adverfication() throws Exception {

        try {
            // Creating an Object of BasicPlaybackSampleApp class
            advancePlayBackSampleApp po = new advancePlayBackSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.AdvancedPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("AdvancePlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Custom Plugin Sample");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            System.out.println("after wait for presence");

            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PluginPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started Verification
            EventVerification ev = new EventVerification();

            //verifing Ad, Ad event
            ev.verifyEvent("adStarted","Preroll Ad is playing",20000);

            // Ad completed event verification
            ev.verifyEvent("adCompleted","Preroll Ad play completed",30000);

            // video playing start event verification
            ev.verifyEvent("playStarted", " Video Started to Play ", 60000);

            // Midroll Ad start event verification
            ev.verifyEvent("adStarted","Midroll Ad is playing",60000);

            // Midrill Ad completed Verification
            ev.verifyEvent("adCompleted","Midroll Ad play completed",80000);

            // Post Ad start event verification
            ev.verifyEvent("adStarted","Postroll Ad is playing",80000);

            // Post  Ad completed Verification
            ev.verifyEvent("adCompleted","Postroll Ad play completed",90000);

            ev.verifyEvent("playCompleted", "video play completed", 90000);

        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }

    }

*/
}