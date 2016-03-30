package testpackage.tests.ooyalaSkinSampleApp;

import com.sun.jna.platform.unix.X11;
import io.appium.java_client.android.AndroidDriver;
import org.omg.IOP.TAG_JAVA_CODEBASE;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import testpackage.pageobjects.ooyalaSkinSampleApp;
import testpackage.utils.*;

import java.awt.*;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Sachin on 3/4/2016.
 */
public class FWBasicDeepTests1 {

    private static AndroidDriver driver;

    @BeforeClass
    public void beforeTest() throws Exception {
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
    public void FreeWheelIntegrationPreRoll() throws Exception{

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

            po.clickBasedOnText(driver, "Freewheel Integration");
            Thread.sleep(2000);

            // Assert if current activity is Freewheel list activity
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "Freewheel Preroll");
            Thread.sleep(2000);


            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresenceOfText(driver,"h");

            //Clicking on Play button in Ooyala Skin
            po.clickBasedOnText(driver,"h");



            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

//            po.learnMore(driver);
//            Thread.sleep(1000);
//            po.clickThrough(driver);

            //verifing event that click through working
   //         ev.verifyEvent("stateChanged - state: SUSPENDED", "click through working",30000);
           // Thread.sleep(1000);

            //verifing that back to SDK and ad start playing again
          //  ev.verifyEvent("adStarted - state: PLAYING","Back to SDK ad playing start again",30000);

       //     Thread.sleep(5000);

            ev.verifyEvent("adCompleted", " Ad Completed to Play ", 30000);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);

            Thread.sleep(1000);
//            String  fj = "//android.widget.RelativeLayout[@index='0']";
//            WebElement viewarea = driver.findElement(By.xpath(fj));
//            WebElement view = viewarea.findElement(By.className("android.view.View"));
            //System.out.println("viewarea>>>>>>>" + view);

           // Thread.sleep(2000);
            //driver.tap(1,0,652,2);
            //driver.tap(1,view,1);
           // System.out.println("moving to pause method");


           // po.moreButton(driver);
           // po.clickOnCloseButton(driver);



            ev.verifyEvent("playCompleted", " Video Completed Play ", 70000);

            po.replayVideo(driver);



            //wait for video to replay and verifing the playing event
            ev.verifyEvent("stateChanged - state: PLAYING","video replaying",70000);
            Thread.sleep(5000);

            po.pauseVideo(driver);
            ev.verifyEvent("stateChanged - state: PAUSED", " Video get paused ", 70000);

            po.moreButton(driver);

            po.shareAsset(driver);
            ev.verifyEvent("stateChanged - state: SUSPENDED", " clicked on share button ", 70000);
            Thread.sleep(1000);

            po.shareOnGmail(driver);
            ev.verifyEvent("bufferChanged - state: READY", " Asset shared via Gmail successfully. ", 70000);

            po.clickOnCC(driver);

            Thread.sleep(2000);

            po.enableCC(driver);

            Thread.sleep(1000);
            po.clickOnCloseButton(driver);

            Thread.sleep(2000);
            po.clickOnCloseButton(driver);

            System.out.println("on main video screen");

            driver.tap(1,0,652,2);

           po.playVideo(driver);
            Thread.sleep(1000);

            ev.verifyEvent("stateChanged - state: PLAYING","video playing again",70000);



            Thread.sleep(1000);
//            po.clickOnCC(driver);
//            Thread.sleep(1000);
//            po.enableCC(driver);





            Thread.sleep(1000);



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
