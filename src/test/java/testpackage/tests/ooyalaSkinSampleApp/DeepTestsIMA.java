package testpackage.tests.ooyalaSkinSampleApp;

import io.appium.java_client.android.AndroidDriver;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import testpackage.pageobjects.ooyalaSkinSampleApp;
import testpackage.utils.*;

/**
 * Created by sumit on 02/05/16.
 */
public class DeepTestsIMA {
    private static AndroidDriver driver;

    @BeforeClass
    public void beforeTest() throws Exception {

        // closing all recent app from background.
        CloserecentApps.closeApps();
        System.out.println("BeforeTest \n");

        System.out.println(System.getProperty("user.dir"));
        // Get Property Values
        LoadPropertyValues prop = new LoadPropertyValues();
        Properties p=prop.loadProperty("ooyalaSkinSampleApp.properties");

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
        if(driver.currentActivity()!= "com.ooyala.sample.complete.MainActivity") {
            driver.startActivity("com.ooyala.sample.SkinCompleteSampleApp","com.ooyala.sample.complete.MainActivity");
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
    public void afterMethod() throws InterruptedException, IOException {
        // Waiting for all the events from sdk to come in .
        System.out.println("AfterMethod \n");
        //ScreenshotDevice.screenshot(driver);
        RemoveEventsLogFile.removeEventsFileLog();
        Thread.sleep(10000);

    }


    @org.testng.annotations.Test
    public void GoogleIMAIntegrationIMAAdRulesPreroll() throws Exception{
        int[] locPlayButon;

        try {

            // Creating an Object of FreeWheelSampleApp class
            ooyalaSkinSampleApp po = new ooyalaSkinSampleApp();
            // wait till home screen of basicPlayBackApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            po.clickBasedOnText(driver, "Google IMA Integration");
            Thread.sleep(2000);

            System.out.println(" Print current activity name"+driver.currentActivity());
            if(driver.currentActivity().toString().equals(".Settings$AppDrawOverlaySettingsActivity")){
                //Navigate back to Skin playback activity
                driver.navigate().back();
                Thread.sleep(2000);

            }

            po.waitForPresenceOfText(driver,"IMA Ad-Rules Preroll");

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("Ooyala Skin - Google IMA List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "IMA Ad-Rules Preroll");
            Thread.sleep(2000);


            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredIMAPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver,"h");

            locPlayButon=po.locationTextOnScreen(driver,"h");

            //Clicking on Play button in Ooyala Skin
            po.clickBasedOnText(driver,"h");

            //Ad Started Verification
            EventVerification ev = new EventVerification();
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
            Thread.sleep(30000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);

            po.replayVideo(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video replay start ", 70000);


            Thread.sleep(1000);

            //clicking on view area
            WebElement viewarea = driver.findElementByClassName("android.view.View");
            viewarea.click();

            Thread.sleep(1000);

            po.pauseVideo(driver);
            // verifing video get paused
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

            Thread.sleep(5000);

            po.getBackFromRecentApp(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

            po.powerKeyClick(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

            Thread.sleep(5000);
            viewarea.click();
            Thread.sleep(1000);


            // clicking on more button
            po.moreButton(driver);


            Thread.sleep(2000);

            // clicking on Share button
            po.shareAsset(driver);

            System.out.println("clicked on share button");

            Thread.sleep(2000);

            ev.verifyEvent("stateChanged - state: SUSPENDED", " Sharing the asset ", 70000);

            po.shareOnGmail(driver);
            Thread.sleep(1000);
            ev.verifyEvent("bufferChanged - state: READY", " Mail sent, Back to SDK ", 70000);
            Thread.sleep(2000);

            System.out.println("clicking on discovery");
            po.clickOnDiscovery(driver);

            Thread.sleep(2000);

            po.clickOnCloseButton(driver);

            Thread.sleep(2000);

            System.out.println("clicking on CC");
            po.clickOnCC(driver);

            Thread.sleep(2000);


            po.clickOnCloseButton(driver);
            Thread.sleep(2000);

            po.clickOnCloseButton(driver);


            Thread.sleep(5000);
            viewarea.click();
            Thread.sleep(1000);
//            po.seek_video(driver);

//            Thread.sleep(5000);


            po.playVideo(driver);
            ev.verifyEvent("stateChanged - state: PLAYING", " Video start ", 70000);

            po.getBackFromRecentApp(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 60000);

            po.powerKeyClick(driver);

            // verifing event that player has been get ready
            ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 60000);


            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);

        }
        catch(Exception e)
        {
            System.out.println(" Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver);
        }
    }
}
