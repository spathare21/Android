package testpackage.tests.freewheelsampleapp;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import testpackage.pageobjects.FreewheelSampleApp;
import testpackage.utils.*;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Sachin on 2/29/2016.
 */
public class DeepTests3 {

    private static AndroidDriver driver;

    @BeforeClass
    public void beforeTest() throws Exception {
        System.out.println("BeforeTest \n");

        System.out.println(System.getProperty("user.dir"));
        // Get Property Values
        LoadPropertyValues prop = new LoadPropertyValues();
        Properties p = prop.loadProperty("freewheelsampleapp.properties");

        System.out.println("Device id from properties file " + p.getProperty("deviceName"));
        System.out.println("PortraitMode from properties file " + p.getProperty("PortraitMode"));
        System.out.println("Path where APK is stored" + p.getProperty("appDir"));
        System.out.println("APK name is " + p.getProperty("app"));
        System.out.println("Platform under Test is " + p.getProperty("platformName"));
        System.out.println("Mobile OS Version is " + p.getProperty("OSVERSION"));
        System.out.println("Package Name of the App is " + p.getProperty("appPackage"));
        System.out.println("Activity Name of the App is " + p.getProperty("appActivity"));

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
        if (driver.currentActivity() != "com.ooyala.sample.lists.FreewheelListActivity") {
            driver.startActivity("com.ooyala.sample.FreewheelSampleApp", "com.ooyala.sample.lists.FreewheelListActivity");
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

    }

    @AfterMethod
    public void afterMethod() throws InterruptedException, IOException {
        // Waiting for all the events from sdk to come in .
        System.out.println("AfterMethod \n");
        //ScreenshotDevice.screenshot(driver);
        RemoveEventsLogFile.removeEventsFileLog();
        Thread.sleep(10000);

    }

    @org.testng.annotations.Test
    public void FreeWheelOverlay() throws Exception {

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

            System.out.println("FW overlay");
            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);

            Thread.sleep(5000);

            po.verifyOverlay(driver);

            Thread.sleep(5000);

            po.getBackFromRecentApp(driver);

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            po.powerKeyClick(driver);

            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 30000);

            Thread.sleep(2000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 60000);

        } catch (Exception e) {
            System.out.println(" Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }
    }

/*
    @org.testng.annotations.Test
    public void FWCuePointsAndAdsControlOptions_On() throws Exception{

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

            // Click on video play icon after video has been generated .
            po.clickImagebuttons(driver,0);

            System.out.println("FWCuePointsAndAdsControlOptions_On");
            //Play Started Verification
            EventVerification ev = new EventVerification();

            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
//            WebDriverWait wait = new WebDriverWait(driver,30);
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//")))

            // Click on the web area so that player screen shows up
            WebElement viewarea = driver.findElementByClassName("android.view.View");

            po.clickOnViewarea(driver);

            Thread.sleep(800);

            //pausing ad
            po.adPause(driver);

            //verifing event for pause
            ev.verifyEvent("stateChanged - state: PAUSED", "Ad paused", 3000);

            Thread.sleep(2000);

            po.getBackFromRecentApp(driver);

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);

            po.powerKeyClick(driver);

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);

            // Click on the web area so that player screen shows up

            po.clickOnViewarea(driver);

            po.adPlay(driver);

            Thread.sleep(4000);

            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 35000);


            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);

            // Thread.sleep(1000);

            //Wait for Ad to start and verify the adStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 49000);



            // Click on the web area so that player screen shows up
            po.clickOnViewarea(driver);
            // viewarea.click();

            Thread.sleep(1000);

            //pausing ad
            po.adPause(driver);

            //verifing event for pause
            ev.verifyEvent("stateChanged - state: PAUSED", "Ad paused", 3000);

            Thread.sleep(2000);

            po.getBackFromRecentApp(driver);

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);

            po.powerKeyClick(driver);

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);

            // Click on the web area so that player screen shows up
            po.clickOnViewarea(driver);
            //viewarea.click();

            po.adPlay(driver);

            Thread.sleep(4000);

            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 35000);


            ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);


            // Click on the web area so that player screen shows up

            po.clickOnViewarea(driver);

            Thread.sleep(800);

            //pausing ad
            po.adPause(driver);

            //verifing event for pause
            ev.verifyEvent("stateChanged - state: PAUSED", "Ad paused", 3000);

            Thread.sleep(2000);

            po.getBackFromRecentApp(driver);

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);

            po.powerKeyClick(driver);

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 30000);

            // Click on the web area so that player screen shows up

            po.clickOnViewarea(driver);

            po.adPlay(driver);

            Thread.sleep(4000);

            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 35000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 30000);

        }
        catch(Exception e)
        {
            System.out.println(" Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }
    }

    @org.testng.annotations.Test
    public void FWCuePointsAndAdsControlOptions_cuePointOff_leanmore() throws Exception{
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

            System.out.println("FWCuePointsAndAdsControlOptions_cuePointOff_leanmore");
            //turning off cue point
            po.cuepointOff(driver);

            //Click on Video create button
            po.clickButtons(driver,0);

            // Wait for the video to be generated
            po.waitForPresenceOfText(driver,"00:00");

            // Click on video play icon after video has been generated .
            po.clickImagebuttons(driver,0);



            //Play Started Verification
            EventVerification ev = new EventVerification();

            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);


            // clicking on learn more button
            po.clickLearnMore(driver);

            // verifing event that we have clicked on learn more
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Clicked on learn more", 30000);


            Thread.sleep(5000);
            // getting back to SDK
            driver.navigate().back();

            // verifing event that get back to SDK and ad start playing again
            ev.verifyEvent("adStarted - state: PLAYING", "Back to SDK and ad start playing again", 30000);




            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 35000);



            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);

            // Thread.sleep(1000);

            //Wait for Ad to start and verify the adStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 49000);

            Thread.sleep(5000);
            // clicking on learn more button
            po.clickLearnMore(driver);

            // verifing event that we have clicked on learn more
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Clicked on learn more", 30000);


            Thread.sleep(5000);
            // getting back to SDK
            driver.navigate().back();

            // verifing event that get back to SDK and ad start playing again
            ev.verifyEvent("adStarted - state: PLAYING", "Back to SDK and ad start playing again", 30000);



            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 35000);


            ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);

            Thread.sleep(4000);

            // clicking on learn more button
            po.clickLearnMore(driver);

            // verifing event that we have clicked on learn more
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Clicked on learn more", 30000);


            Thread.sleep(5000);
            // getting back to SDK
            driver.navigate().back();

            // verifing event that get back to SDK and ad start playing again
            ev.verifyEvent("adStarted - state: PLAYING", "Back to SDK and ad start playing again", 30000);


            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 35000);


            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 30000);

        }
        catch(Exception e)
        {
            System.out.println(" Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }
    }

    @org.testng.annotations.Test
    public void FWCuePointsAndAdsControlOptions_adControlsOff_leanmore() throws Exception{
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

            System.out.println("FWCuePointsAndAdsControlOptions_adControlsOff_leanmore");
            //turning off cue point
            po.adControlOff(driver);

            //Click on Video create button
            po.clickButtons(driver,0);

            // Wait for the video to be generated
            po.waitForPresenceOfText(driver,"00:00");

            // Click on video play icon after video has been generated .
            po.clickImagebuttons(driver,0);

            //Play Started Verification
            EventVerification ev = new EventVerification();

            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);



            //clicking on view area for click threw
            po.clickOnViewarea(driver);
            ev.verifyEvent("stateChanged - state: SUSPENDED","click on screen and click through",3000);

            Thread.sleep(5000);
            // getting back to SDK
            driver.navigate().back();

            // verifing event that get back to SDK and ad start playing again
            ev.verifyEvent("adStarted - state: PLAYING", "Back to SDK and ad start playing again", 30000);

            // clicking on learn more button
            po.clickLearnMore(driver);

            // verifing event that we have clicked on learn more
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Clicked on learn more", 30000);


            Thread.sleep(5000);
            // getting back to SDK
            driver.navigate().back();

            // verifing event that get back to SDK and ad start playing again
            ev.verifyEvent("adStarted - state: PLAYING", "Back to SDK and ad start playing again", 30000);


            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 35000);



            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);

            // Thread.sleep(1000);

            //Wait for Ad to start and verify the adStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 49000);



            po.clickOnViewarea(driver);
            ev.verifyEvent("stateChanged - state: SUSPENDED","click on screen and click through",3000);

            Thread.sleep(5000);
            // getting back to SDK
            driver.navigate().back();

            // verifing event that get back to SDK and ad start playing again
            ev.verifyEvent("adStarted - state: PLAYING", "Back to SDK and ad start playing again", 30000);

            Thread.sleep(5000);
            // clicking on learn more button
            po.clickLearnMore(driver);

            // verifing event that we have clicked on learn more
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Clicked on learn more", 30000);


            Thread.sleep(5000);
            // getting back to SDK
            driver.navigate().back();

            // verifing event that get back to SDK and ad start playing again
            ev.verifyEvent("adStarted - state: PLAYING", "Back to SDK and ad start playing again", 30000);



            //Wait for Ad to complete and verify the adCompleted event .
            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 35000);

            Thread.sleep(5000);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);

            po.clickOnViewarea(driver);
            ev.verifyEvent("stateChanged - state: SUSPENDED","click on screen and click through",3000);

            Thread.sleep(5000);
            // getting back to SDK
            driver.navigate().back();

            // verifing event that get back to SDK and ad start playing again
            ev.verifyEvent("adStarted - state: PLAYING", "Back to SDK and ad start playing again", 30000);

            // clicking on learn more button
            po.clickLearnMore(driver);

            // verifing event that we have clicked on learn more
            ev.verifyEvent("stateChanged - state: SUSPENDED", "Clicked on learn more", 30000);


            Thread.sleep(5000);
            // getting back to SDK
            driver.navigate().back();

            // verifing event that get back to SDK and ad start playing again
            ev.verifyEvent("adStarted - state: PLAYING", "Back to SDK and ad start playing again", 30000);


            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 35000);


            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 30000);

        }
        catch(Exception e)
        {
            System.out.println(" Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }
    }
*/
    }
