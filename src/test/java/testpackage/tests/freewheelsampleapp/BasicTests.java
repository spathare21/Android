package testpackage.tests.freewheelsampleapp;


import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import io.appium.java_client.android.AndroidDriver;
import testpackage.pageobjects.FreewheelSampleApp;
import testpackage.utils.*;

import java.util.Properties;
import java.io.IOException;

public class BasicTests extends EventLogTest{

    private static AndroidDriver driver;

    @BeforeClass


    public void beforeTest() throws Exception {


        System.out.println("BeforeTest \n");

        System.out.println(System.getProperty("user.dir"));
        // Get Property Values
        LoadPropertyValues prop = new LoadPropertyValues();
        Properties p=prop.loadProperty("freewheelsampleapp.properties");

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
        PushLogFileToDevice logpush=new PushLogFileToDevice();
        logpush.pushLogFile();
        if(driver.currentActivity()!= "com.ooyala.sample.lists.FreewheelListActivity") {
            driver.startActivity("com.ooyala.sample.FreewheelSampleApp","com.ooyala.sample.lists.FreewheelListActivity");
        }

        // Get Property Values
        LoadPropertyValues prop1 = new LoadPropertyValues();
        Properties p1=prop1.loadProperty();

        System.out.println(" Screen Mode "+ p1.getProperty("ScreenMode"));

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
        //ScreenshotDevice.screenshot(driver);
        RemoveEventsLogFile.removeEventsFileLog();
        Thread.sleep(10000);

    }

    @org.testng.annotations.Test
    public void FreeWheelPreRoll() throws Exception{

        try {
            // Creating an Object of FreeWheelSampleApp class
            FreewheelSampleApp po = new FreewheelSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);


            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("FreeWheelSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Freewheel Preroll");
            Thread.sleep(2000);


            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForTextView(driver,"00:00");
            Thread.sleep(1000);

            po.playInNormalScreen(driver);
            Thread.sleep(1000);
            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 40000);

            po.loadingSpinner(driver);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 50000);
            Thread.sleep(6000);

            // Click on the web area so that player screen shows up
            po.screenTap(driver);
            Thread.sleep(1000);

            //pausing the video
            po.pauseInNormalScreen(driver);
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 60000);
            Thread.sleep(3000);

            po.loadingSpinner(driver);

            po.readTime(driver);

            po.seekVideo(driver);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 65000);
            Thread.sleep(2000);

            po.loadingSpinner(driver);

            po.readTime(driver);

            po.resumeInNormalScreen(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 70000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 100000);

        }
        catch(Exception e)
        {
            System.out.println("FreeWheelPreRoll throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"FreeWheelPreRoll");
            Assert.assertTrue(false, "This will fail!");

        }
    }

    @org.testng.annotations.Test
    public void FreeWheelMidRoll() throws Exception{

        try {
            // Creating an Object of FreeWheelSampleApp class
            FreewheelSampleApp po = new FreewheelSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);


            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("FreeWheelSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Freewheel Midroll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForTextView(driver,"00:00");
            Thread.sleep(1000);

            po.playInNormalScreen(driver);
            Thread.sleep(1000);

            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(6000);

            po.loadingSpinner(driver);

            // Click on the web area so that player screen shows up
            po.screenTap(driver);
            Thread.sleep(1000);

            //pausing the video
            po.pauseInNormalScreen(driver);
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 50000);
            Thread.sleep(2000);

            po.loadingSpinner(driver);

            po.readTime(driver);

            po.seekVideo(driver);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 60000);
            Thread.sleep(2000);

            po.loadingSpinner(driver);

            po.readTime(driver);

            po.resumeInNormalScreen(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 70000);

            //Wait for Ad to start and verify the adStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 75000);
            po.loadingSpinner(driver);
            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 85000);
            po.loadingSpinner(driver);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 120000);

        }
        catch(Exception e)
        {
            System.out.println("FreeWheelMidRoll throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"FreeWheelMidRoll");
            Assert.assertTrue(false, "This will fail!");

        }
    }

    @org.testng.annotations.Test
    public void FreeWheelPostRoll() throws Exception{

        try {
            // Creating an Object of FreeWheelSampleApp class
            FreewheelSampleApp po = new FreewheelSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);


            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("FreeWheelSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Freewheel Postroll");
            Thread.sleep(2000);


            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForTextView(driver,"00:00");
            Thread.sleep(1000);

            po.playInNormalScreen(driver);
            Thread.sleep(1000);

            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(6000);

            po.loadingSpinner(driver);

            // Click on the web area so that player screen shows up
            po.screenTap(driver);

            //pausing the video
            po.pauseInNormalScreen(driver);
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 50000);
            Thread.sleep(2000);

            po.loadingSpinner(driver);

            po.readTime(driver);

            po.seekVideo(driver);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 60000);
            Thread.sleep(2000);

            po.loadingSpinner(driver);

            po.readTime(driver);

            po.resumeInNormalScreen(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 70000);

            po.loadingSpinner(driver);

            //Wait for Ad to start and verify the adStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 80000);
            po.loadingSpinner(driver);

            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 90000);
            po.loadingSpinner(driver);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 100000);

        }
        catch(Exception e)
        {
            System.out.println("FreeWheelPostRoll throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"FreeWheelPostRoll");
            Assert.assertTrue(false, "This will fail!");

        }
    }

    @org.testng.annotations.Test
    public void FreeWheelPreMidPostRoll() throws Exception{

        try {
            // Creating an Object of FreeWheelSampleApp class
            FreewheelSampleApp po = new FreewheelSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);


            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("FreeWheelSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Freewheel PreMidPost");
            Thread.sleep(2000);


            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForTextView(driver,"00:00");
            Thread.sleep(1000);

            po.playInNormalScreen(driver);
            Thread.sleep(1000);

            //Play Started Verification
            EventVerification ev = new EventVerification();

            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
            po.loadingSpinner(driver);

            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 40000);
            po.loadingSpinner(driver);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 45000);
            po.loadingSpinner(driver);

            //Wait for Ad to start and verify the adStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 65000);
            po.loadingSpinner(driver);

            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 75000);
            po.loadingSpinner(driver);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 95000);
            po.loadingSpinner(driver);

            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 110000);
            po.loadingSpinner(driver);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 150000);

        }
        catch(Exception e)
        {
            System.out.println("FreeWheelPreMidPostRoll throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"FreeWheelPreMidPostRoll");
            Assert.assertTrue(false, "This will fail!");

        }
    }


    @org.testng.annotations.Test
    public void FreeWheelOverlay() throws Exception{

        try {
            // Creating an Object of FreeWheelSampleApp class
            FreewheelSampleApp po = new FreewheelSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);


            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("FreeWheelSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Freewheel Overlay");
            Thread.sleep(2000);


            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForTextView(driver,"00:00");
            Thread.sleep(1000);

            po.playInNormalScreen(driver);
            Thread.sleep(1000);

            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);


            po.loadingSpinner(driver);
            Thread.sleep(7000);
            // Click on the web area so that player screen shows up
            WebElement viewarea = driver.findElementByClassName("android.widget.RelativeLayout");
            viewarea.click();
            Thread.sleep(1000);

            //pausing the video
            po.pauseInNormalScreen(driver);
            Thread.sleep(1000);

            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 50000);
            Thread.sleep(3000);

            po.loadingSpinner(driver);

            po.readTime(driver);

            po.seekVideo(driver);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 60000);
            Thread.sleep(2000);

            po.loadingSpinner(driver);

            po.readTime(driver);

            po.resumeInNormalScreen(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 70000);

            po.loadingSpinner(driver);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 120000);

        }
        catch(Exception e)
        {
            System.out.println("FreeWheelOverlay throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"FreeWheelOverlay");
            Assert.assertTrue(false, "This will fail!");

        }
    }


    @org.testng.annotations.Test
    public void FreeWheelMultiMidRoll() throws Exception{

        try {
            // Creating an Object of FreeWheelSampleApp class
            FreewheelSampleApp po = new FreewheelSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);


            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("FreeWheelSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Freewheel Multi Midroll");
            Thread.sleep(2000);


            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForTextView(driver,"00:00");

            po.playInNormalScreen(driver);
            Thread.sleep(1000);

            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);

            po.loadingSpinner(driver);

            //Wait for Ad to start and verify the adStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);

            po.loadingSpinner(driver);

            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 60000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 100000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 110000);

            po.loadingSpinner(driver);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 150000);

        }
        catch(Exception e)
        {
            System.out.println("FreeWheelMultiMidRoll throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"FreeWheelMultiMidRoll");
            Assert.assertTrue(false, "This will fail!");

        }
    }


    @org.testng.annotations.Test
    public void FreeWheelPreMidPostRollOverlay() throws Exception{

        try {
            // Creating an Object of FreeWheelSampleApp class
            FreewheelSampleApp po = new FreewheelSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);


            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("FreeWheelSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Freewheel PreMidPost Overlay");
            Thread.sleep(2000);


            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForTextView(driver,"00:00");
            Thread.sleep(1000);

            po.playInNormalScreen(driver);
            Thread.sleep(1000);

            //Play Started Verification
            EventVerification ev = new EventVerification();

            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
            po.loadingSpinner(driver);

            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 40000);

            po.loadingSpinner(driver);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 50000);

            po.loadingSpinner(driver);

            //Wait for Ad to start and verify the adStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 80000);

            po.loadingSpinner(driver);

            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 90000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 120000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 130000);

            po.loadingSpinner(driver);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 160000);

        }
        catch(Exception e)
        {
            System.out.println("FreeWheelPreMidPostRollOverlay throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"FreeWheelPreMidPostRollOverlay");
            Assert.assertTrue(false, "This will fail!");

        }
    }


    @org.testng.annotations.Test
    public void FreeWheelApplicationConfigured() throws Exception{

        try {
            // Creating an Object of FreeWheelSampleApp class
            FreewheelSampleApp po = new FreewheelSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);


            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("FreeWheelSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Freewheel Application-Configured");
            Thread.sleep(2000);


            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.CustomConfiguredFreewheelPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForTextView(driver,"00:00");
            Thread.sleep(1000);

            po.playInNormalScreen(driver);
            Thread.sleep(1000);

            //Play Started Verification
            EventVerification ev = new EventVerification();

            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
            po.loadingSpinner(driver);

            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 40000);

            po.loadingSpinner(driver);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 40000);
            po.loadingSpinner(driver);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 70000);
            po.loadingSpinner(driver);

            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 80000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 100000);
            po.loadingSpinner(driver);

            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 110000);

            po.loadingSpinner(driver);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 150000);




        }
        catch(Exception e)
        {
            System.out.println("FreeWheelApplicationConfigured throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"FreeWheelApplicationConfigured");
            Assert.assertTrue(false, "This will fail!");

        }
    }

    @org.testng.annotations.Test
    public void FreeWheelCuePointsAndAdsControlOptions() throws Exception{

        try {
            // Creating an Object of FreeWheelSampleApp class
            FreewheelSampleApp po = new FreewheelSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);


            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("FreeWheelSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "CuePoints and AdsControl Options");
            Thread.sleep(2000);


            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OptionsFreewheelPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Click on Video create button
            po.clickButtons(driver,0);

            // Wait for the video to be generated
            po.waitForPresenceOfText(driver,"00:00");

            po.loadingSpinner(driver);

            // Click on video play icon after video has been generated .
            //po.clickImagebuttons(driver,0);
            po.playInNormalScreen(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();

            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 40000);

            po.loadingSpinner(driver);


            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 40000);

            po.loadingSpinner(driver);

            //Wait for Ad to start and verify the adStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 55000);

            po.loadingSpinner(driver);

            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 65000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 80000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 90000);

            po.loadingSpinner(driver);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 100000);

        }
        catch(Exception e)
        {
            System.out.println("FreeWheelCuePointsAndAdsControlOptions throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"FreeWheelCuePointsAndAdsControlOptions");
            Assert.assertTrue(false, "This will fail!");

        }
    }

}
