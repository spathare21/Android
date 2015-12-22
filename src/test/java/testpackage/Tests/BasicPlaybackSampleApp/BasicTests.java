package testpackage.Tests.BasicPlaybackSampleApp;
/**
 * Created by bsondur on 11/16/15.
 */

import org.junit.Assert;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;
import io.appium.java_client.android.AndroidDriver;
import testpackage.BasicPlaybackSampleApp.pageObjects.pageObjectsBasicPlayback;
import testpackage.utils.eventVerification;
import testpackage.utils.removeEventsLogFile;
import testpackage.utils.screenshotDevice;
import testpackage.utils.setUpAndroidDriver;
import testpackage.utils.loadPropertyValues;
import java.util.Properties;
import java.io.IOException;


public class BasicTests {

    private static AndroidDriver driver;

    @BeforeTest
    public void beforeTest() throws Exception {
        System.out.println("BeforeTest \n");

        System.out.println(System.getProperty("user.dir"));
        // Get Property Values
        loadPropertyValues prop = new loadPropertyValues();
        Properties p=prop.loadProperty();

        //System.out.println("Device id from properties file " + p.getProperty("deviceName"));
        //System.out.println("PortraitMode from properties file " + p.getProperty("PortraitMode"));
        //System.out.println("Path where APK is stored"+ p.getProperty("appDir"));
        //System.out.println("APK name is "+ p.getProperty("app"));
        //System.out.println("Platform under Test is "+ p.getProperty("platformName"));
        //System.out.println("Mobile OS Version is "+ p.getProperty("OSVERSION"));
        //System.out.println("Package Name of the App is "+ p.getProperty("appPackage"));
        //System.out.println("Activity Name of the App is "+ p.getProperty("appActivity"));

        setUpAndroidDriver setUpdriver = new setUpAndroidDriver();
        driver = setUpdriver.setUpandReturnAndroidDriver(p.getProperty("deviceName"),p.getProperty("appDir"),p.getProperty("app"),p.getProperty("platformName"),p.getProperty("OSVERSION"),p.getProperty("appPackage"),p.getProperty("appActivity"));
        Thread.sleep(2000);
    }

    @BeforeMethod
    //public void beforeTest() throws Exception{
    public void beforeMethod() throws Exception {
        System.out.println("beforeMethod \n");
        removeEventsLogFile.removeEventsFileLog();
        if(driver.currentActivity()!= "com.ooyala.sample.lists.BasicPlaybackListActivity") {
            driver.startActivity("com.ooyala.sample.BasicPlaybackSampleApp","com.ooyala.sample.lists.BasicPlaybackListActivity");
        }

        // Get Property Values
        loadPropertyValues prop1 = new loadPropertyValues();
        Properties p1=prop1.loadProperty();

        System.out.println(" Screen Mode "+ p1.getProperty("ScreenMode"));

        //if(p1.getProperty("ScreenMode") != "P"){
        //    System.out.println("Inside landscape Mode ");
        //    driver.rotate(ScreenOrientation.LANDSCAPE);
        //}

        //driver.rotate(ScreenOrientation.LANDSCAPE);
        //driver.rotate(ScreenOrientation.LANDSCAPE);

    }

    @AfterTest
    public void afterTest() throws InterruptedException, IOException {
        System.out.println("AfterTest \n");
        driver.closeApp();
        driver.quit();

    }

    @AfterMethod
    //public void afterTest() throws InterruptedException, IOException {
    public void afterMethod() throws InterruptedException, IOException {
        // Waiting for all the events from sdk to come in .
        System.out.println("AfterMethod \n");
        //screenshotDevice.screenshot(driver);
        Thread.sleep(10000);
        removeEventsLogFile.removeEventsFileLog();
    }



    //TODO : create unique file names for snapshots taken .
    @org.testng.annotations.Test
    public void AspectRatioTest() throws Exception{

        try {
            // Creating an Object of pageObjectsBasicPlayback class
            pageObjectsBasicPlayback po = new pageObjectsBasicPlayback();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("BasicPlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "4:3 Aspect Ratio");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started Verification
            eventVerification ev = new eventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            // Click on the web area so that player screen shows up
            WebElement viewarea = driver.findElementByClassName("android.view.View");
            viewarea.click();

            // Tap coordinates to pause
            String dimensions = driver.manage().window().getSize().toString();
            //System.out.println(" Dimensions are "+dimensions);
            String[] dimensionsarray=dimensions.split(",");
            int length = dimensionsarray[1].length();
            String ydimensions=dimensionsarray[1].substring(0,length-1);
            String ydimensionstrimmed=ydimensions.trim();
            int ydimensionsInt= Integer.parseInt(ydimensionstrimmed);
            driver.tap(1, 35 , (ydimensionsInt-25), 2);

            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);

            // Pause the running of the test for a brief amount of time
            Thread.sleep(3000);

            //Seek and Verify seek event
            po.getXYSeekBarAndSeek(driver,20,120);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 30000);
            Thread.sleep(3000);

            // Tap coordinates again to play
            driver.tap(1, 35 , (ydimensionsInt-25), 2);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 70000);
        }
        catch(Exception e)
        {
            System.out.println(" Exception "+e);
            e.printStackTrace();
            screenshotDevice.screenshot(driver);
        }
    }



    //TODO : create unique file names for snapshots taken .
    @org.testng.annotations.Test
    public void HLSVideoTest() throws Exception{

        try {
            // Creating an Object of pageObjectsBasicPlayback class
            pageObjectsBasicPlayback po = new pageObjectsBasicPlayback();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("BasicPlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "HLS Video");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started Verification
            eventVerification ev = new eventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            // Click on the web area so that player screen shows up
            WebElement viewarea = driver.findElementByClassName("android.view.View");
            viewarea.click();

            // Tap coordinates to pause
            String dimensions = driver.manage().window().getSize().toString();
            //System.out.println(" Dimensions are "+dimensions);
            String[] dimensionsarray=dimensions.split(",");
            int length = dimensionsarray[1].length();
            String ydimensions=dimensionsarray[1].substring(0,length-1);
            String ydimensionstrimmed=ydimensions.trim();
            int ydimensionsInt= Integer.parseInt(ydimensionstrimmed);
            driver.tap(1, 35 , (ydimensionsInt-25), 2);

            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);

            // Pause the running of the test for a brief amount of time
            Thread.sleep(3000);

            //Seek and Verify seek event
            po.getXYSeekBarAndSeek(driver,20,120);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 30000);
            Thread.sleep(3000);

            // Tap coordinates again to play
            driver.tap(1, 35 , (ydimensionsInt-25), 2);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);
        }
        catch(Exception e)
        {
            System.out.println(" Exception "+e);
            e.printStackTrace();
            screenshotDevice.screenshot(driver);
        }
    }

    @org.testng.annotations.Test
    public void MP4VideoTest() throws Exception{

        try {
            // Creating an Object of pageObjectsBasicPlayback class
            pageObjectsBasicPlayback po = new pageObjectsBasicPlayback();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("BasicPlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "MP4 Video");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started Verification
            eventVerification ev = new eventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            // Click on the web area so that player screen shows up
            WebElement viewarea = driver.findElementByClassName("android.view.View");
            viewarea.click();

            // Tap coordinates to Pause
            String dimensions = driver.manage().window().getSize().toString();
            String[] dimensionsarray=dimensions.split(",");
            int length = dimensionsarray[1].length();
            String ydimensions=dimensionsarray[1].substring(0,length-1);
            String ydimensionstrimmed=ydimensions.trim();
            int ydimensionsInt= Integer.parseInt(ydimensionstrimmed);
            driver.tap(1, 35 , (ydimensionsInt-25), 2);

            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);

            // Pause the running of the test for a brief amount of time
            Thread.sleep(3000);

            //Seek and Verify seek event
            po.getXYSeekBarAndSeek(driver,20,120);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 30000);
            Thread.sleep(3000);

            // Tap coordinates again to play
            driver.tap(1, 35 , (ydimensionsInt-25), 2);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);
        }
        catch(Exception e)
        {
            System.out.println(" Exception "+e);
            e.printStackTrace();
            screenshotDevice.screenshot(driver);
        }
    }


    @org.testng.annotations.Test
    public void VODwithCCTest() throws Exception{

        try {
            // Creating an Object of pageObjectsBasicPlayback class
            pageObjectsBasicPlayback po = new pageObjectsBasicPlayback();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("BasicPlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            //po.clickBasedOnText(driver, "VOD with CCs");
            po.clickBasedOnTextScrollTo(driver, "VOD with CCs");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started Verification
            eventVerification ev = new eventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            // Click on the web area so that player screen shows up
            WebElement viewarea = driver.findElementByClassName("android.view.View");
            viewarea.click();

            // Tap coordinates to pause
            String dimensions = driver.manage().window().getSize().toString();
            //System.out.println(" Dimensions are "+dimensions);
            String[] dimensionsarray=dimensions.split(",");
            int length = dimensionsarray[1].length();
            String ydimensions=dimensionsarray[1].substring(0,length-1);
            String ydimensionstrimmed=ydimensions.trim();
            int ydimensionsInt= Integer.parseInt(ydimensionstrimmed);
            driver.tap(1, 35 , (ydimensionsInt-25), 2);

            // Pause state verification
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 30000);

            Thread.sleep(1000);

            po.clickImagebuttons(driver,(2-1));

            po.waitForTextView(driver,"Languages");

            po.clickRadiobuttons(driver,(7-1));

            Thread.sleep(2000);

            Assert.assertTrue(po.radioButtonChecked(driver,(7-1)));

            // closed Captions event verification
            ev.verifyEvent("closedCaptionsLanguageChanged", " CC of the Video Was Changed ", 30000);

            driver.navigate().back();

            // Pause the running of the test for a brief amount of time
            Thread.sleep(3000);

            //Seek and Verify seek event
            //po.getXYSeekBarAndSeek(driver,20,120);
            //ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 30000);
            //Thread.sleep(3000);

            // Tap coordinates again to play
            driver.tap(1, 35 , (ydimensionsInt-25), 2);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);
        }
        catch(Exception e)
        {
            System.out.println(" Exception "+e);
            e.printStackTrace();
            screenshotDevice.screenshot(driver);
        }
    }


    @org.testng.annotations.Test
    public void VASTAdPreRollTest() throws Exception{

        try {
            // Creating an Object of pageObjectsBasicPlayback class
            pageObjectsBasicPlayback po = new pageObjectsBasicPlayback();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("BasicPlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            //po.clickBasedOnText(driver, "VAST Ad Pre-roll");
            po.clickBasedOnTextScrollTo(driver, "VAST Ad Pre-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Ad Started Verification
            eventVerification ev = new eventVerification();
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            //Thread sleep time is equivalent to the length of the AD
            Thread.sleep(5000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 30000);

            //Time out
            Thread.sleep(1000);

            //Play Started
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //Timeout for the duration of the video
            Thread.sleep(11000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);
        }
        catch(Exception e)
        {
            System.out.println(" Exception "+e);
            e.printStackTrace();
            screenshotDevice.screenshot(driver);
        }
    }


    @org.testng.annotations.Test
    public void VASTADMidRollTest() throws Exception{

        try {
            // Creating an Object of pageObjectsBasicPlayback class
            pageObjectsBasicPlayback po = new pageObjectsBasicPlayback();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("BasicPlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            //po.clickBasedOnText(driver, "VAST Ad Mid-roll");
            po.clickBasedOnTextScrollTo(driver, "VAST Ad Mid-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started
            eventVerification ev = new eventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //Thread sleep time is equivalent to the length of the half of the video
            Thread.sleep(11000);

            //Ad Started Verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            Thread.sleep(2000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 30000);

            //Thread sleep time is equivalent to the length of the half of the video
            Thread.sleep(11000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);
        }
        catch(Exception e)
        {
            System.out.println(" Exception "+e);
            e.printStackTrace();
            screenshotDevice.screenshot(driver);
        }
    }


    @org.testng.annotations.Test
    public void VASTADPostRollTest() throws Exception{

        try {
            // Creating an Object of pageObjectsBasicPlayback class
            pageObjectsBasicPlayback po = new pageObjectsBasicPlayback();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("BasicPlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            //po.clickBasedOnText(driver, "VAST Ad Post-roll");
            po.clickBasedOnTextScrollTo(driver, "VAST Ad Post-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started
            eventVerification ev = new eventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //Thread sleep time is equivalent to the length of the video
            Thread.sleep(11000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);

            //Ad Started Verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            Thread.sleep(5000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 30000);


        }
        catch(Exception e)
        {
            System.out.println(" Exception "+e);
            e.printStackTrace();
            screenshotDevice.screenshot(driver);
        }
    }

    @org.testng.annotations.Test
    public void VASTAdWrapperTest() throws Exception{

        try {
            // Creating an Object of pageObjectsBasicPlayback class
            pageObjectsBasicPlayback po = new pageObjectsBasicPlayback();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("BasicPlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "VAST Ad Wrapper");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started Verification
            eventVerification ev = new eventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);
        }
        catch(Exception e)
        {
            System.out.println(" Exception "+e);
            e.printStackTrace();
            screenshotDevice.screenshot(driver);
        }
    }

    @org.testng.annotations.Test
    public void OoyalaAdPreRollTest() throws Exception{

        try {
            // Creating an Object of pageObjectsBasicPlayback class
            pageObjectsBasicPlayback po = new pageObjectsBasicPlayback();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("BasicPlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            //po.clickBasedOnText(driver, "VAST Ad Pre-roll");
            po.clickBasedOnTextScrollTo(driver, "Ooyala Ad Pre-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Ad Started Verification
            eventVerification ev = new eventVerification();
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            //Thread sleep time is equivalent to the length of the AD
            Thread.sleep(5000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 30000);

            //Time out
            Thread.sleep(1000);

            //Play Started
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //Timeout for the duration of the video
            Thread.sleep(11000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);
        }
        catch(Exception e)
        {
            System.out.println(" Exception "+e);
            e.printStackTrace();
            screenshotDevice.screenshot(driver);
        }
    }

    @org.testng.annotations.Test
    public void OoyalaADMidRollTest() throws Exception{

        try {
            // Creating an Object of pageObjectsBasicPlayback class
            pageObjectsBasicPlayback po = new pageObjectsBasicPlayback();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("BasicPlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            //po.clickBasedOnText(driver, "VAST Ad Mid-roll");
            po.clickBasedOnTextScrollTo(driver, "Ooyala Ad Mid-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started
            eventVerification ev = new eventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //Thread sleep time is equivalent to the length of the half of the video
            Thread.sleep(11000);

            //Ad Started Verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            Thread.sleep(2000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 30000);

            //Thread sleep time is equivalent to the length of the half of the video
            Thread.sleep(11000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);
        }
        catch(Exception e)
        {
            System.out.println(" Exception "+e);
            e.printStackTrace();
            screenshotDevice.screenshot(driver);
        }
    }


    @org.testng.annotations.Test
    public void OoyalaADPostRollTest() throws Exception{

        try {
            // Creating an Object of pageObjectsBasicPlayback class
            pageObjectsBasicPlayback po = new pageObjectsBasicPlayback();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("BasicPlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            //po.clickBasedOnText(driver, "VAST Ad Post-roll");
            po.clickBasedOnTextScrollTo(driver, "Ooyala Ad Post-roll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Play Started
            eventVerification ev = new eventVerification();
            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //Thread sleep time is equivalent to the length of the video
            Thread.sleep(11000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);

            //Ad Started Verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            Thread.sleep(5000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 30000);


        }
        catch(Exception e)
        {
            System.out.println(" Exception "+e);
            e.printStackTrace();
            screenshotDevice.screenshot(driver);
        }
    }



    @org.testng.annotations.Test
    public void MultiAdCombinationTest() throws Exception{

        try {
            // Creating an Object of pageObjectsBasicPlayback class
            pageObjectsBasicPlayback po = new pageObjectsBasicPlayback();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.BasicPlaybackListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("BasicPlaybackSample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            //po.clickBasedOnText(driver, "VAST Ad Mid-roll");
            po.clickBasedOnTextScrollTo(driver, "Multi Ad combination");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.BasicPlaybackVideoPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //Ad Started Verification
            eventVerification ev = new eventVerification();
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            Thread.sleep(2000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 30000);

            //Play Started

            ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

            //Thread sleep time is equivalent to the length of the half of the video
            Thread.sleep(11000);

            //Ad Started Verification
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            Thread.sleep(2000);

            //Ad Completed Verification
            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 30000);

            //Thread sleep time is equivalent to the length of the half of the video
            Thread.sleep(11000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);
        }
        catch(Exception e)
        {
            System.out.println(" Exception "+e);
            e.printStackTrace();
            screenshotDevice.screenshot(driver);
        }
    }




}
