package testpackage.tests.freewheelsampleapp;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import testpackage.pageobjects.FreewheelSampleApp;
import testpackage.utils.*;
import java.util.Properties;
import java.io.IOException;

    public class BasicTests extends EventLogTest{

    FreewheelSampleApp freewheelSampleApp = new FreewheelSampleApp();
    LoadPropertyValues prop = new LoadPropertyValues();
    Properties p;

    @BeforeClass
    public void beforeTest() throws Exception {
        System.out.println("BeforeTest \n");

        System.out.println(System.getProperty("user.dir"));
        // Get Property Values
        p=prop.loadProperty("freewheelsampleapp.properties");

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
        driver.manage().logs().get("logcat");
        PushLogFileToDevice logpush=new PushLogFileToDevice();
        logpush.pushLogFile();
        if(driver.currentActivity()!= "com.ooyala.sample.lists.FreewheelListActivity") {
            driver.startActivity("com.ooyala.sample.FreewheelSampleApp","com.ooyala.sample.lists.FreewheelListActivity");
        }
        // Get Property Values
        p =prop.loadProperty();
        System.out.println(" Screen Mode "+ p.getProperty("ScreenMode"));
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

    @Test
    public void freeWheelPreRoll() throws Exception{
        try {
            // wait till home screen of basicPlayBackApp is opened
            freewheelSampleApp.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            freewheelSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("FreeWheelSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Selecting the FW Preroll asset
            freewheelSampleApp.clickBasedOnText(driver, "Freewheel Preroll");

            //verify if player was loaded
            freewheelSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            freewheelSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            freewheelSampleApp.waitForTextView(driver,"00:00");

            freewheelSampleApp.playInNormalScreen(driver);
            //Play Started Verification
            EventVerification ev = new EventVerification();
            //AdPlayback started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
            //Adplayback completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 40000);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 50000);
            Thread.sleep(4000);

            //pausing the video
            freewheelSampleApp.pauseInNormalScreen(driver);
            //Verifying the pause event
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 60000);
            //Reading the time after pause the video
            freewheelSampleApp.readTime(driver);

            //Seeking the video
            freewheelSampleApp.seekVideoForLong(driver);
            // After seek the video, Verifying the seek completed event
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 65000);
            //Handling the loading spinner after seek the video, If displayed
            freewheelSampleApp.loadingSpinner(driver);

            // Reading the time after pause and seek the video
            freewheelSampleApp.readTime(driver);
            //Resuming the video after pause and seek function
            freewheelSampleApp.resumeInNormalScreen(driver);
            //Verifying the video playback resuming event
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

    @Test
    public void freeWheelMidRoll() throws Exception{
        try {
            // wait till home screen of basicPlayBackApp is opened
            freewheelSampleApp.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            freewheelSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");
            // Write to console activity name of home screen app
            System.out.println("FreeWheelSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Selecting FW Midroll asset
            freewheelSampleApp.clickBasedOnText(driver, "Freewheel Midroll");

            //verify if player was loaded
            freewheelSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            freewheelSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            freewheelSampleApp.waitForTextView(driver,"00:00");
            //clicking on playbutton
            freewheelSampleApp.playInNormalScreen(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(4000);

            //pausing the video
            freewheelSampleApp.pauseInNormalScreen(driver);
            //Verifying the pause event
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 50000);
            // Reading the time after pause the video
            freewheelSampleApp.readTime(driver);
            //Seeking the video
            freewheelSampleApp.seekVideo(driver);
            //Verifying the seek completed event
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 60000);
            // Handling the loading spinner after seek the video, If displayed
            freewheelSampleApp.loadingSpinner(driver);
            //Reading the time after seek the video
            freewheelSampleApp.readTime(driver);
            // Resuming the playback after seek and pause functionality
            freewheelSampleApp.resumeInNormalScreen(driver);
            //Verifying the video resume event
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 70000);
            //Wait for Ad to start and verify the adStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 75000);
            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 85000);
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

    @Test
    public void freeWheelPostRoll() throws Exception{
        try {
            // wait till home screen of basicPlayBackApp is opened
            freewheelSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            freewheelSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("FreeWheelSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Selecting the FW Postroll asset
            freewheelSampleApp.clickBasedOnText(driver, "Freewheel Postroll");
            //verify if player was loaded
            freewheelSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            freewheelSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            freewheelSampleApp.waitForTextView(driver,"00:00");

            //Clicking on play button
            freewheelSampleApp.playInNormalScreen(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(4000);

            //pausing the video
            freewheelSampleApp.pauseInNormalScreen(driver);
            //verifying the pause event
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 50000);
            //Reading the time after pause the video
            freewheelSampleApp.readTime(driver);
            //Seeking the video
            freewheelSampleApp.seekVideoForLong(driver);
            //Verifying the seek completed event after seek the video
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 60000);
            // Handling the loading spinner after seek the video, If displayed
            freewheelSampleApp.loadingSpinner(driver);
            //Reading the time after seek and pause functionality
            freewheelSampleApp.readTime(driver);
            //Resuming the playback after seek
            freewheelSampleApp.resumeInNormalScreen(driver);
            // Verifying the resuming playback event
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 70000);

            //Wait for Ad to start and verify the adStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 80000);
            freewheelSampleApp.loadingSpinner(driver);

            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 90000);
            freewheelSampleApp.loadingSpinner(driver);

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

    @Test
    public void freeWheelPreMidPostRoll() throws Exception{
        try {
            // wait till home screen of basicPlayBackApp is opened
            freewheelSampleApp.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            freewheelSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");
            // Write to console activity name of home screen app
            System.out.println("FreeWheelSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Selecting the FW PreMidPost roll asset
            freewheelSampleApp.clickBasedOnText(driver, "Freewheel PreMidPost");

            //verify if player was loaded
            freewheelSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            freewheelSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            freewheelSampleApp.waitForTextView(driver,"00:00");
            //Clicking on play button
            freewheelSampleApp.playInNormalScreen(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();
            //Adplayback start event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
            //Adplayback start event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 40000);
            freewheelSampleApp.loadingSpinner(driver);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 45000);

            //Wait for Ad to start and verify the adStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 65000);

            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 75000);

            Thread.sleep(4000);

            //pausing the video
            freewheelSampleApp.pauseInNormalScreen(driver);
            //Verifying the pause event
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 60000);
            //Reading the time after pause the video
            freewheelSampleApp.readTime(driver);

            //Seeking the video
            freewheelSampleApp.seekVideoForLong(driver);
            // After seek the video, Verifying the seek completed event
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 65000);
            //Handling the loading spinner after seek the video, If displayed
            freewheelSampleApp.loadingSpinner(driver);

            // Reading the time after pause and seek the video
            freewheelSampleApp.readTime(driver);
            //Resuming the video after pause and seek function
            freewheelSampleApp.resumeInNormalScreen(driver);
            //Verifying the video playback resuming event
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 70000);

            //Adplayback start event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 95000);
            //adPlayback completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 110000);

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

    @Test
    public void freeWheelOverlay() throws Exception{
        try {
            // wait till home screen of basicPlayBackApp is opened
            freewheelSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            freewheelSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");
            // Write to console activity name of home screen app
            System.out.println("FreeWheelSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Selecting FW Overlay asset
            freewheelSampleApp.clickBasedOnText(driver, "Freewheel Overlay");

            //verify if player was loaded
            freewheelSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            freewheelSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            freewheelSampleApp.waitForTextView(driver,"00:00");
            // String the playback by click on play button
            freewheelSampleApp.playInNormalScreen(driver);
            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 50000);
            Thread.sleep(4000);

            //pausing the video
            freewheelSampleApp.pauseInNormalScreen(driver);
            //Verifying the pause event
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 60000);
            //Reading the time after pause the video
            freewheelSampleApp.readTime(driver);

            //Seeking the video
            freewheelSampleApp.seekVideoForLong(driver);
            // After seek the video, Verifying the seek completed event
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 65000);
            //Handling the loading spinner after seek the video, If displayed
            freewheelSampleApp.loadingSpinner(driver);

            // Reading the time after pause and seek the video
            freewheelSampleApp.readTime(driver);
            //Resuming the video after pause and seek function
            freewheelSampleApp.resumeInNormalScreen(driver);
            //Verifying the video playback resuming event
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 70000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 100000);
        }
        catch(Exception e)
        {
            System.out.println("FreeWheelOverlay throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"FreeWheelOverlay");
            Assert.assertTrue(false, "This will fail!");

        }
    }
    
    @Test
    public void freeWheelMultiMidRoll() throws Exception{
        try {
            // wait till home screen of basicPlayBackApp is opened
            freewheelSampleApp.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            freewheelSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");
            // Write to console activity name of home screen app
            System.out.println("FreeWheelSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Selecting FW MultiMidroll asset
            freewheelSampleApp.clickBasedOnText(driver, "Freewheel Multi Midroll");
            //verify if player was loaded
            freewheelSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            freewheelSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            freewheelSampleApp.waitForTextView(driver,"00:00");

            //Clicking on play button
            freewheelSampleApp.playInNormalScreen(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);

            //Wait for Ad to start and verify the adStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);

            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 60000);

            Thread.sleep(4000);

            //pausing the video
            freewheelSampleApp.pauseInNormalScreen(driver);
            //Verifying the pause event
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 50000);
            // Reading the time after pause the video
            freewheelSampleApp.readTime(driver);
            //Seeking the video
            freewheelSampleApp.seekVideoForLong(driver);
            //Verifying the seek completed event
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 60000);
            // Handling the loading spinner after seek the video, If displayed
            freewheelSampleApp.loadingSpinner(driver);
            //Reading the time after seek the video
            freewheelSampleApp.readTime(driver);
            // Resmung the playback after seek and pause functionality
            freewheelSampleApp.resumeInNormalScreen(driver);
            //Verifying the video resume event
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 70000);

            //Wait for Ad to start and verify the adStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 100000);

            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 110000);

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

    @Test
    public void freeWheelPreMidPostRollOverlay() throws Exception{
        try {
            // wait till home screen of basicPlayBackApp is opened
            freewheelSampleApp.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            freewheelSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("FreeWheelSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Selecting FW PreMidPost Overlay asset
            freewheelSampleApp.clickBasedOnText(driver, "Freewheel PreMidPost Overlay");

            //verify if player was loaded
            freewheelSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            freewheelSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            freewheelSampleApp.waitForTextView(driver,"00:00");

            freewheelSampleApp.playInNormalScreen(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();
            //AdPlayback started event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
            //AdPlayback Completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 40000);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 50000);
            Thread.sleep(4000);

            //pausing the video
            freewheelSampleApp.pauseInNormalScreen(driver);
            //Verifying the pause event
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 60000);
            //Reading the time after pause the video
            freewheelSampleApp.readTime(driver);

            //Seeking the video
            freewheelSampleApp.seekVideoForLong(driver);
            // After seek the video, Verifying the seek completed event
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 65000);
            //Handling the loading spinner after seek the video, If displayed
            freewheelSampleApp.loadingSpinner(driver);

            // Reading the time after pause and seek the video
            freewheelSampleApp.readTime(driver);
            //Resuming the video after pause and seek function
            freewheelSampleApp.resumeInNormalScreen(driver);
            //Verifying the video playback resuming event
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 70000);


            //Wait for Ad to start and verify the adStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 120000);
            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 90000);


            //Wait for Ad to start and verify the adStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 120000);
            //AdPlayback Completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 130000);


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
    
    @Test
    public void freeWheelApplicationConfigured() throws Exception{
        try {
            // wait till home screen of basicPlayBackApp is opened
            freewheelSampleApp.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            freewheelSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");
            // Write to console activity name of home screen app
            System.out.println("FreeWheelSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");
            // Selecting FW Application configured asset
            freewheelSampleApp.clickBasedOnText(driver, "Freewheel Application-Configured");

            //verify if player was loaded
            freewheelSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            freewheelSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.CustomConfiguredFreewheelPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            freewheelSampleApp.waitForTextView(driver,"00:00");
            //Clicking on play button
            freewheelSampleApp.playInNormalScreen(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();
            //Adplayback start event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
            //Adplayback start event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 40000);
            freewheelSampleApp.loadingSpinner(driver);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 45000);

            //Wait for Ad to start and verify the adStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 65000);

            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 75000);

            Thread.sleep(4000);

            //pausing the video
            freewheelSampleApp.pauseInNormalScreen(driver);
            //Verifying the pause event
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 60000);
            //Reading the time after pause the video
            freewheelSampleApp.readTime(driver);

            //Seeking the video
            freewheelSampleApp.seekVideoForLong(driver);
            // After seek the video, Verifying the seek completed event
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 65000);
            //Handling the loading spinner after seek the video, If displayed
            freewheelSampleApp.loadingSpinner(driver);

            // Reading the time after pause and seek the video
            freewheelSampleApp.readTime(driver);
            //Resuming the video after pause and seek function
            freewheelSampleApp.resumeInNormalScreen(driver);
            //Verifying the video playback resuming event
            ev.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 70000);

            //Adplayback start event verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 95000);
            //adPlayback completed event verification
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 110000);

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

    @Test
    public void freeWheelCuePointsAndAdsControlOptions() throws Exception{
        try {
            // wait till home screen of basicPlayBackApp is opened
            freewheelSampleApp.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            freewheelSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");
            // Write to console activity name of home screen app
            System.out.println("FreeWheelSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Selecting FW cue Point and Ads control asset
            freewheelSampleApp.clickBasedOnText(driver, "CuePoints and AdsControl Options");

            //verify if player was loaded
            freewheelSampleApp.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            freewheelSampleApp.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.OptionsFreewheelPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Click on Video create button
            freewheelSampleApp.clickButtons(driver,0);

            // Wait for the video to be generated
            freewheelSampleApp.waitForPresenceOfText(driver,"00:00");

            // Click on video play icon after video has been generated .
            freewheelSampleApp.playInNormalScreen(driver);

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
            freewheelSampleApp.pauseSmallplayer(driver);
            //Verifying the pause event
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 60000);
            //Reading the time after pause the video
            freewheelSampleApp.readTime(driver);

            //Seeking the video
            freewheelSampleApp.seekVideo(driver);
            // After seek the video, Verifying the seek completed event
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked ", 65000);
            //Handling the loading spinner after seek the video, If displayed
            freewheelSampleApp.loadingSpinner(driver);

            // Reading the time after pause and seek the video
            freewheelSampleApp.readTime(driver);
            //Resuming the video after pause and seek function
            freewheelSampleApp.resumeInNormalScreen(driver);
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
            System.out.println("FreeWheelCuePointsAndAdsControlOptions throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"FreeWheelCuePointsAndAdsControlOptions");
            Assert.assertTrue(false, "This will fail!");

        }
    }

}
