package testpackage.tests.IMASampleApp;

/**
 * Created by dulari on 3/14/16.
 */

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import io.appium.java_client.android.AndroidDriver;
import testpackage.pageobjects.FreewheelSampleApp;
import testpackage.pageobjects.IMASampleApp;
import testpackage.utils.*;
//import testpackage.utils.JIRAUtils;


import java.util.Properties;
import java.io.IOException;



public class BasicTests extends EventLogTest{

    private static AndroidDriver driver;

    @BeforeClass
    public void beforeTest() throws Exception {
        // closing all recent app from background.
        CloserecentApps.closeApps();


        System.out.println("BeforeTest \n");

        System.out.println(System.getProperty("user.dir"));
        // Get Property Values
        LoadPropertyValues prop = new LoadPropertyValues();
        Properties p=prop.loadProperty("IMASampleapp.properties");

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
        //removeEventsLogFile.removeEventsFileLog(); create events file
        PushLogFileToDevice logpush=new PushLogFileToDevice();
        logpush.pushLogFile();
        if(driver.currentActivity()!= "com.ooyala.sample.lists.IMAListActivity") {
            driver.startActivity("com.ooyala.sample.IMASampleApp","com.ooyala.sample.lists.IMAListActivity");
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
    public void afterMethod(ITestResult result) throws Exception {
        // Waiting for all the events from sdk to come in .
        System.out.println("AfterMethod \n");
        //ScreenshotDevice.screenshot(driver);
        RemoveEventsLogFile.removeEventsFileLog();
        Thread.sleep(10000);

    }

   @org.testng.annotations.Test
    public void IMAAdRulePreroll() throws Exception{

        try {
            // Creating an Object of IMA class
            IMASampleApp po = new IMASampleApp();
            // wait till home screen of IMASampleApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("IMASample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "IMA Ad-Rules Preroll");
            Thread.sleep(2000);


            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredIMAPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresence(driver,"className","android.widget.ImageButton");
            Thread.sleep(1000);

            //Clicking on play button
            po.playInNormalScreen(driver);
            Thread.sleep(1000);

            //Play Started Verification
            EventVerification ev = new EventVerification();
            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adCompleted", " Ad Completed ", 40000);

            po.loadingSpinner(driver);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 50000);

            po.loadingSpinner(driver);
            Thread.sleep(7000);

            po.pauseInNormalScreen(driver);
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 65000);

            po.loadingSpinner(driver);

            po.seekVideo(driver);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);

            po.loadingSpinner(driver);
            Thread.sleep(3000);

            po.resumeInNormalScreen(driver);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed  ", 90000);
            Thread.sleep(2000);
        }
        catch(Exception e)
        {
            System.out.println("IMAAdRulePreroll throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"IMAAdRulePreroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void IMAAdRuleMidroll() throws Exception{

        try {
            // Creating an Object of IMA class
            IMASampleApp po = new IMASampleApp();
            // wait till home screen of IMASampleApp is opened
            po.waitForAppHomeScreen(driver);


            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("IMASample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "IMA Ad-Rules Midroll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredIMAPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresence(driver,"className","android.widget.ImageButton");
            Thread.sleep(1000);

            //Clicking on play button
            po.playInNormalScreen(driver);
            Thread.sleep(1000);

            //Play Started Verification
            EventVerification ev = new EventVerification();

            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            po.loadingSpinner(driver);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 45000);
            po.loadingSpinner(driver);
            ev.verifyEvent("adCompleted", " Ad Completed ", 55000);
            po.loadingSpinner(driver);

            po.pauseInNormalScreen(driver);
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 65000);

            po.loadingSpinner(driver);

            po.readTime(driver);

            po.seekVideo(driver);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);
            po.loadingSpinner(driver);
            Thread.sleep(3000);

            po.readTime(driver);

            po.resumeInNormalScreen(driver);

            po.loadingSpinner(driver);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed  ", 150000);
            Thread.sleep(2000);
        }
        catch(Exception e)
        {
            System.out.println("IMAAdRuleMidroll throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"IMAAdRuleMidroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void IMAAdRulePostroll() throws Exception{

        try {
            // Creating an Object of IMA class
            IMASampleApp po = new IMASampleApp();
            // wait till home screen of IMASampleApp is opened
            po.waitForAppHomeScreen(driver);


            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("IMASample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "IMA Ad-Rules Postroll");
            Thread.sleep(2000);


            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredIMAPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresence(driver,"className","android.widget.ImageButton");
            Thread.sleep(1000);

            //Clicking on play button
            po.playInNormalScreen(driver);
            Thread.sleep(1000);

            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);

            po.loadingSpinner(driver);

            po.pauseInNormalScreen(driver);
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 35000);

            po.loadingSpinner(driver);

            po.readTime(driver);

            po.seekVideo(driver);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 55000);

            po.loadingSpinner(driver);
            Thread.sleep(3000);

            po.readTime(driver);

            po.resumeInNormalScreen(driver);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 90000);
            po.loadingSpinner(driver);

            ev.verifyEvent("adCompleted", " Ad Completed ", 100000);

            po.loadingSpinner(driver);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed  ", 150000);
        }
        catch(Exception e)
        {
            System.out.println("IMAAdRulePostroll throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"IMAAdRulePostroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void IMAPoddedPreroll() throws Exception{

        try {
            // Creating an Object of IMA class
            IMASampleApp po = new IMASampleApp();
            // wait till home screen of IMASampleApp is opened
            po.waitForAppHomeScreen(driver);


            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("IMASample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "IMA Podded Preroll");
            Thread.sleep(2000);


            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredIMAPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresence(driver,"className","android.widget.ImageButton");
            Thread.sleep(1000);

            //Clicking on play button
            po.playInNormalScreen(driver);
            Thread.sleep(1000);

            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("adStarted", " Ad Started to Play ", 15000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adCompleted", " Ad Completed ", 25000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 25000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adCompleted", " Ad Completed ", 35000);

            po.loadingSpinner(driver);

            ev.verifyEvent("playStarted", " Video Started Play ", 35000);
            Thread.sleep(7000);

            po.loadingSpinner(driver);

            po.pauseInNormalScreen(driver);
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 65000);

            po.readTime(driver);

            po.seekVideo(driver);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 75000);
            Thread.sleep(3000);

            po.loadingSpinner(driver);

            po.readTime(driver);

            po.resumeInNormalScreen(driver);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed  ", 120000);
            Thread.sleep(2000);
        }
        catch(Exception e)
        {
            System.out.println("IMAPoddedPreroll throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"IMAPoddedPreroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void IMAPoddedMidroll() throws Exception{

        try {
            // Creating an Object of IMA class
            IMASampleApp po = new IMASampleApp();
            // wait till home screen of IMASampleApp is opened
            po.waitForAppHomeScreen(driver);


            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("IMASample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "IMA Podded Midroll");
            Thread.sleep(2000);


            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredIMAPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresence(driver,"className","android.widget.ImageButton");
            Thread.sleep(1000);

            //Clicking on play button
            po.playInNormalScreen(driver);
            Thread.sleep(1000);

            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 15000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adCompleted", " Ad Completed ", 40000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 40000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adCompleted", " Ad Completed ", 50000);
            Thread.sleep(6000);

            po.loadingSpinner(driver);

            po.pauseInNormalScreen(driver);
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 65000);

            po.readTime(driver);

            po.seekVideo(driver);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 80000);
            Thread.sleep(3000);

            po.loadingSpinner(driver);

            po.readTime(driver);

            po.resumeInNormalScreen(driver);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed  ", 120000);
            Thread.sleep(2000);
        }
        catch(Exception e)
        {
            System.out.println("IMAPoddedMidroll throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"IMAPoddedMidroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

   @org.testng.annotations.Test
    public void IMAPoddedPostroll() throws Exception{

        try {
            // Creating an Object of IMA class
            IMASampleApp po = new IMASampleApp();
            // wait till home screen of IMASampleApp is opened
            po.waitForAppHomeScreen(driver);


            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("IMASample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "IMA Podded Postroll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredIMAPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresence(driver,"className","android.widget.ImageButton");
            Thread.sleep(1000);

            //Clicking on play button
            po.playInNormalScreen(driver);
            Thread.sleep(1000);

            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 15000);
            Thread.sleep(6000);

            po.loadingSpinner(driver);

            po.pauseInNormalScreen(driver);
            ev.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 35000);
            Thread.sleep(2000);

            po.loadingSpinner(driver);

            po.readTime(driver);

            po.seekVideo(driver);
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 45000);
            Thread.sleep(3000);

            po.loadingSpinner(driver);

            po.readTime(driver);

            po.resumeInNormalScreen(driver);

            po.loadingSpinner(driver);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 85000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adCompleted", " Ad Completed ", 95000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 95000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adCompleted", " Ad Completed ", 120000);

            po.loadingSpinner(driver);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed  ", 130000);
        }
        catch(Exception e)
        {
            System.out.println("IMAPoddedPostroll throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"IMAPoddedPostroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void IMAPoddedPreMidPost() throws Exception{

        try {
            // Creating an Object of IMA class
            IMASampleApp po = new IMASampleApp();
            // wait till home screen of IMASampleApp is opened
            po.waitForAppHomeScreen(driver);


            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("IMASample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "IMA Podded Pre-Mid-Post");
            Thread.sleep(2000);


            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredIMAPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresence(driver,"className","android.widget.ImageButton");
            Thread.sleep(1000);

            //Clicking on play button
            po.playInNormalScreen(driver);
            Thread.sleep(1000);

            //Play Started Verification
            EventVerification ev = new EventVerification();

            // verify preroll
            ev.verifyEvent("adStarted", " Ad Started to Play ", 15000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adCompleted", " Ad Completed ", 25000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 25000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adCompleted", " Ad Completed ", 35000);

            po.loadingSpinner(driver);

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 40000);

            po.loadingSpinner(driver);

            // verify midroll
            ev.verifyEvent("adStarted", " Ad Started to Play ", 80000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adCompleted", " Ad Completed ", 90000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 90000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adCompleted", " Ad Completed ", 100000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 100000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adCompleted", " Ad Completed ", 110000);

            po.loadingSpinner(driver);

            // verify postroll
            ev.verifyEvent("adStarted", " Ad Started to Play ", 170000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adCompleted", " Ad Completed  ", 180000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 180000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adCompleted", " Ad Completed  ", 190000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 190000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adCompleted", " Ad Completed ", 200000);

            po.loadingSpinner(driver);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed  ", 210000);
        }
        catch(Exception e)
        {
            System.out.println("IMAPoddedPreMidPost throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"IMAPoddedPreMidPost");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void IMASkippable() throws Exception{

        try {
            // Creating an Object of IMA class
            IMASampleApp po = new IMASampleApp();
            // wait till home screen of IMASampleApp is opened
            po.waitForAppHomeScreen(driver);


            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("IMASample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "IMA Skippable");
            Thread.sleep(2000);


            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredIMAPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresence(driver,"className","android.widget.ImageButton");
            Thread.sleep(1000);

            //Clicking on play button
            po.playInNormalScreen(driver);
            Thread.sleep(1000);

            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .


            ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
            po.loadingSpinner(driver);
            // Thread.sleep(5000);

            ev.verifyEvent("adCompleted", " Ad Completed ", 45000);

            po.loadingSpinner(driver);

            ev.verifyEvent("playStarted", " Video Started Play ", 50000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 70000);

            po.loadingSpinner(driver);
            // Thread.sleep(5000);

            ev.verifyEvent("adCompleted", " Ad Completed ", 90000);

            po.loadingSpinner(driver);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed  ", 100000);
        }
        catch(Exception e)
        {
            System.out.println("IMASkippable throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"IMASkippable");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void IMAPreMidPostSkippable() throws Exception{

        try {
            // Creating an Object of IMA class
            IMASampleApp po = new IMASampleApp();
            // wait till home screen of IMASampleApp is opened
            po.waitForAppHomeScreen(driver);


            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("IMASample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "IMA Pre, Mid and Post Skippable");
            Thread.sleep(2000);


            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredIMAPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresence(driver,"className","android.widget.ImageButton");
            Thread.sleep(1000);

            //Clicking on play button
            po.playInNormalScreen(driver);
            Thread.sleep(1000);

            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .

            ev.verifyEvent("adStarted", " Ad Started to Play ", 20000);
            po.loadingSpinner(driver);
            // Thread.sleep(5000);

            ev.verifyEvent("adCompleted", " Ad Completed ", 35000);

            po.loadingSpinner(driver);

            ev.verifyEvent("playStarted", " Video Started Play ", 40000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);
            po.loadingSpinner(driver);
            // Thread.sleep(5000);

            ev.verifyEvent("adCompleted", " Ad Completed ", 60000);
            po.loadingSpinner(driver);

            po.loadingSpinner(driver);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 85000);
            po.loadingSpinner(driver);

            ev.verifyEvent("adCompleted", " Ad Completed ", 95000);
            po.loadingSpinner(driver);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed  ", 100000);
            Thread.sleep(2000);
        }
        catch(Exception e)
        {
            System.out.println("IMAPreMidPostSkippable throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"IMAPreMidPostSkippable");
            Assert.assertTrue(false, "This will fail!");
        }
    }

    @org.testng.annotations.Test
    public void IMAApplicationConfigured() throws Exception{

        try {
            // Creating an Object of IMA class
            IMASampleApp po = new IMASampleApp();
            // wait till home screen of IMASampleApp is opened
            po.waitForAppHomeScreen(driver);


            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("IMASample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            po.clickBasedOnText(driver, "IMA Application-Configured");
            Thread.sleep(2000);


            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.CustomConfiguredIMAPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            po.waitForPresence(driver,"className","android.widget.ImageButton");

            po.playInNormalScreen(driver);
            Thread.sleep(1000);

            po.loadingSpinner(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 10000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 35000);

            po.loadingSpinner(driver);

            ev.verifyEvent("adCompleted", " Ad Completed ", 50000);

            po.loadingSpinner(driver);

            ev.verifyEvent("playCompleted", " Video Completed  ", 90000);

        }
        catch(Exception e)
        {
            System.out.println("IMAApplicationConfigured throws Exception "+e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"IMAApplicationConfigured");
            Assert.assertTrue(false, "This will fail!");
        }
    }


}
