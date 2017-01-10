package testpackage.tests.NielsenSampleApp;

import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import testpackage.pageobjects.NeilsenSampleApp;
import testpackage.tests.exoPlayerSampleApp.BasicPlayBackBasicTest2;
import testpackage.utils.*;

import java.awt.*;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Sachin on 8/5/2016.
 */
public class BasicTest extends EventLogTest {
    final static Logger logger = Logger.getLogger(BasicTest.class);

    @BeforeClass
    public void beforeTest() throws Exception {
        // closing all recent app from background.
        CloserecentApps.closeApps();


        logger.info("BeforeTest \n");

        System.out.println(System.getProperty("user.dir"));
        // Get Property Values
        LoadPropertyValues prop = new LoadPropertyValues();
        Properties p = prop.loadProperty("NielsenSampleApp.properties");
        logger.debug("Device id from properties file " + p.getProperty("deviceName"));
        logger.debug("PortraitMode from properties file " + p.getProperty("PortraitMode"));
        logger.debug("Path where APK is stored" + p.getProperty("appDir"));
        logger.debug("APK name is " + p.getProperty("appValue"));
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
        //removeEventsLogFile.removeEventsFileLog(); create events file
        PushLogFileToDevice logpush = new PushLogFileToDevice();
        logpush.pushLogFile();
        if(driver.currentActivity()!= "com.ooyala.sample.lists.NielsenListActivity") {
            driver.startActivity("com.ooyala.sample.NielsenSampleApp","com.ooyala.sample.lists.NielsenListActivity");
        }

        // Get Property Values
        LoadPropertyValues prop1 = new LoadPropertyValues();
        Properties p1=prop1.loadProperty();

        logger.debug(" Screen Mode "+ p1.getProperty("ScreenMode"));

    }
    @AfterClass
    public void afterTest() throws InterruptedException, IOException {
        logger.info("AfterTest \n");
        driver.closeApp();
        driver.quit();

    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws Exception {
        // Waiting for all the events from sdk to come in .
        logger.info("AfterMethod \n");
        //ScreenshotDevice.screenshot(driver);
        RemoveEventsLogFile.removeEventsFileLog();
        Thread.sleep(10000);

    }

    @org.testng.annotations.Test
    public static void iDDemo()throws Exception{
        try
        {
            NeilsenSampleApp NS = new NeilsenSampleApp();
            NS.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            NS.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.NielsenListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("NielsenSamppApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select Asset
            NS.clickBasedOnText(driver, "ID3-Demo");
            Thread.sleep(2000);


            //verify if player was loaded
            NS.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            NS.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.NielsenDefaultPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            NS.waitForPresence(driver, "className", "android.widget.ImageButton");
            Thread.sleep(1000);
            logger.info("Now will play in normal screen");
           NS.playInNormalScreen(driver);
            Thread.sleep(1000);
            EventVerification e = new EventVerification();
            e.verifyEvent("playStarted", " Video Started Play ", 5000);
            NS.loadingSpinner(driver);

            //ToDo Failing because fo issue PBA-4367 //
          /*  NS.pauseInNormalScreen(driver);
            Thread.sleep(6000);
            e.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 9000);
            NS.seekVideo(driver);
            logger.info("Hi..");
            e.verifyEvent("bufferingStarted" ," Playing Video was Seeked " , 15000);
            NS.loadingSpinner(driver);
            Thread.sleep(1000);
            NS.resumeInNormalScreen(driver);*/
            e.verifyEvent("playCompleted", " Video Completed  ",500000);

        }
        catch (Exception e)
        {
            logger.error("ID3-Demo throws Exception "+e);
            e.printStackTrace();
            //ScreenshotDevice.screenshot(driver,"IMAAdRulePreroll");
            Assert.assertTrue(false, "This will fail!");

        }
    }

    @org.testng.annotations.Test
    public static void iDTravelEast ()throws  Exception {
        try {
            NeilsenSampleApp Ns = new NeilsenSampleApp();
            Ns.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            Ns.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.NielsenListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("NielsenSamppApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select Asset
            Ns.clickBasedOnText(driver, "ID3-TravelEast");
            Thread.sleep(2000);


            //verify if player was loaded
            Ns.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            Ns.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.NielsenDefaultPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            Ns.waitForPresence(driver, "className", "android.widget.ImageButton");
            Thread.sleep(1000);
            logger.info("Now will play in normal screen");
            Ns.playInNormalScreen(driver);
            Thread.sleep(1000);
            EventVerification e = new EventVerification();
            e.verifyEvent("playStarted", " Video Started Play ", 5000);
            Ns.loadingSpinner(driver);
            //ToDo Failing because fo issue PBA-4367 //
          /*  NS.pauseInNormalScreen(driver);
            Thread.sleep(6000);
            e.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 9000);
            NS.seekVideo(driver);
            logger.info("Hi..");
            e.verifyEvent("bufferingStarted" ," Playing Video was Seeked " , 15000);
            NS.loadingSpinner(driver);
            Thread.sleep(1000);
            NS.resumeInNormalScreen(driver);*/
           e.verifyEvent("playCompleted", " Video Completed  ",300000);

        }catch (Exception e)
        {
            logger.error("ID3-TravelEast throws Exception "+e);
            e.printStackTrace();
            //ScreenshotDevice.screenshot(driver,"IMAAdRulePreroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public static  void cmsDemo () throws Exception {

        try {
            NeilsenSampleApp NS = new NeilsenSampleApp();
            NS.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            NS.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.NielsenListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("NielsenSamppApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select Assets.
            NS.clickBasedOnText(driver, "CMS-Demo");
            Thread.sleep(2000);


            //verify if player was loaded
            NS.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            NS.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.NielsenDefaultPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            NS.waitForPresence(driver, "className", "android.widget.ImageButton");
            Thread.sleep(1000);
            logger.info("Now will play in normal screen");
            NS.playInNormalScreen(driver);
            Thread.sleep(1000);
            EventVerification e = new EventVerification();
            e.verifyEvent("playStarted", " Video Started Play ", 5000);
            NS.loadingSpinner(driver);
            //ToDo Failing because fo issue PBA-4367 //
          /*  NS.pauseInNormalScreen(driver);
            Thread.sleep(6000);
            e.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 9000);
            NS.seekVideo(driver);
            logger.info("Hi..");
            e.verifyEvent("bufferingStarted" ," Playing Video was Seeked " , 15000);
            NS.loadingSpinner(driver);
            Thread.sleep(1000);
            NS.resumeInNormalScreen(driver);*/
            e.verifyEvent("playCompleted", " Video Completed  ",700000);

        }catch (Exception e)
        {
            logger.error("CMS-Demo throws Exception "+e);
            e.printStackTrace();
            //ScreenshotDevice.screenshot(driver,"IMAAdRulePreroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public static  void cmsNoAds () throws Exception{
        try{
            NeilsenSampleApp NS = new NeilsenSampleApp();
            NS.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            NS.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.NielsenListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("NielsenSamppApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select Assets.
            NS.clickBasedOnText(driver, "CMS-NoAds");
            Thread.sleep(2000);


            //verify if player was loaded
            NS.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            NS.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.NielsenDefaultPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            NS.waitForPresence(driver, "className", "android.widget.ImageButton");
            Thread.sleep(1000);
            logger.info("Now will play in normal screen");
            NS.playInNormalScreen(driver);
            Thread.sleep(1000);
            EventVerification e = new EventVerification();
            e.verifyEvent("playStarted", " Video Started Play ", 5000);
            NS.loadingSpinner(driver);

            //ToDo Failing because fo issue PBA-4367 //
          /*  NS.pauseInNormalScreen(driver);
            Thread.sleep(6000);
            e.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 9000);
            NS.seekVideo(driver);
            logger.info("Hi..");
            e.verifyEvent("bufferingStarted" ," Playing Video was Seeked " , 15000);
            NS.loadingSpinner(driver);
            Thread.sleep(1000);
            NS.resumeInNormalScreen(driver);*/
            e.verifyEvent("playCompleted", " Video Completed  ",500000);


        }catch (Exception e)
        {
            logger.error("CMS-NoAds throws Exception "+e);
            e.printStackTrace();
            //ScreenshotDevice.screenshot(driver,"IMAAdRulePreroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public static void cmsWithAds () throws  Exception{
        try{
            NeilsenSampleApp NS = new NeilsenSampleApp();
            NS.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            NS.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.NielsenListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("NielsenSamppApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select Assets
            NS.clickBasedOnText(driver, "CMS-WithAds");
            Thread.sleep(2000);
            //verify if player was loaded
            NS.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            NS.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.NielsenDefaultPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            NS.waitForPresence(driver, "className", "android.widget.ImageButton");
            Thread.sleep(1000);
            logger.info("Now will play in normal screen");
            NS.playInNormalScreen(driver);
            Thread.sleep(1000);
            EventVerification ev = new EventVerification();
            ev.verifyEvent("adStarted","Ad Started to Play",5000);
            NS.loadingSpinner(driver);
            // Thread.sleep(5000);

            ev.verifyEvent("adCompleted", " Ad Completed ", 35000);

            NS.loadingSpinner(driver);

            ev.verifyEvent("playStarted", " Video Started Play ", 40000);

            NS.loadingSpinner(driver);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);
            NS.loadingSpinner(driver);
            // Thread.sleep(5000);

            ev.verifyEvent("adCompleted", " Ad Completed ", 70000);
            NS.loadingSpinner(driver);
            Thread.sleep(50000);
            //ToDo Failing because fo issue PBA-4367 //
          /*  NS.pauseInNormalScreen(driver);
            Thread.sleep(6000);
            e.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 9000);
            NS.seekVideo(driver);
            logger.info("Hi..");
            e.verifyEvent("bufferingStarted" ," Playing Video was Seeked " , 15000);
            NS.loadingSpinner(driver);
            Thread.sleep(1000);
            NS.resumeInNormalScreen(driver);*/
            ev.verifyEvent("adStarted", " Ad Started to Play ", 100000);
            NS.loadingSpinner(driver);

            ev.verifyEvent("adCompleted", " Ad Completed ", 150000);
            NS.loadingSpinner(driver);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed  ", 120000);
            Thread.sleep(2000);

        }
        catch (Exception e)
        {
            logger.error("CMS-WithAds throws Exception "+e);
            e.printStackTrace();
            //ScreenshotDevice.screenshot(driver,"IMAAdRulePreroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public  static void cmsLive () throws  Exception{
        try
        {
            NeilsenSampleApp NS = new NeilsenSampleApp();
            NS.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            NS.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.NielsenListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("NielsenSamppApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select Assets.
            NS.clickBasedOnText(driver, "CMS-Live");
            Thread.sleep(2000);
            //verify if player was loaded
            NS.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            NS.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.NielsenDefaultPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            NS.waitForPresence(driver, "className", "android.widget.ImageButton");
            Thread.sleep(1000);
            logger.info("Now will play in normal screen");
            NS.playInNormalScreen(driver);
            Thread.sleep(1000);
            EventVerification e = new EventVerification();
            e.verifyEvent("playStarted","Video started to play ",5000);
            Thread.sleep(2000);
            NS.pauseInNormalScreen(driver);
            e.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 60000);
            Thread.sleep(2000);
            NS.resumeInNormalScreen(driver);
            e.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 80000);


        }
        catch (Exception e)
        {
            logger.error("CMS-Live throws Exception "+e);
            e.printStackTrace();
            //ScreenshotDevice.screenshot(driver,"IMAAdRulePreroll");
            Assert.assertTrue(false, "This will fail!");

        }
    }

    @org.testng.annotations.Test
    public  static  void idLive () throws Exception
    {
        try
        {
            NeilsenSampleApp NS = new NeilsenSampleApp();
            NS.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            NS.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.NielsenListActivity");
            // Wrire to console activity name of home screen app
            logger.debug("NielsenSamppApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select the aseets.
            NS.clickBasedOnText(driver, "ID3-Live");
            Thread.sleep(2000);
            //verify if player was loaded
            NS.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            NS.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.NielsenDefaultPlayerActivity");
            // Print to console output current player activity
            logger.debug("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            NS.waitForPresence(driver, "className", "android.widget.ImageButton");
            Thread.sleep(1000);
            logger.info("Now will play in normal screen");
            NS.playInNormalScreen(driver);
            Thread.sleep(1000);
            EventVerification e = new EventVerification();
            e.verifyEvent("playStarted","Video started to play ",5000);
            Thread.sleep(2000);
            NS.pauseInNormalScreen(driver);
            e.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 60000);
            Thread.sleep(2000);
            NS.resumeInNormalScreen(driver);
            e.verifyEvent("stateChanged - state: PLAYING", " Video Started to Play ", 80000);

        }
        catch (Exception e)
        {
            logger.error("ID3-Live throws Exception "+e);
            e.printStackTrace();
            //ScreenshotDevice.screenshot(driver,"IMAAdRulePreroll");
            Assert.assertTrue(false, "This will fail!");
        }

    }



}
