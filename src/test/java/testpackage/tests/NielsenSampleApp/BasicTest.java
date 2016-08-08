package testpackage.tests.NielsenSampleApp;

import io.appium.java_client.android.AndroidDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import testpackage.pageobjects.NeilsenSampleApp;
import testpackage.utils.*;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Sachin on 8/5/2016.
 */
public class BasicTest extends EventLogTest {

    @BeforeClass
    public void beforeTest() throws Exception {
        // closing all recent app from background.
        CloserecentApps.closeApps();


        System.out.println("BeforeTest \n");

        System.out.println(System.getProperty("user.dir"));
        // Get Property Values
        LoadPropertyValues prop = new LoadPropertyValues();
        Properties p = prop.loadProperty("NielsenSampleApp.properties");
        System.out.println("Device id from properties file " + p.getProperty("deviceName"));
        System.out.println("PortraitMode from properties file " + p.getProperty("PortraitMode"));
        System.out.println("Path where APK is stored" + p.getProperty("appDir"));
        System.out.println("APK name is " + p.getProperty("appValue"));
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
        if(driver.currentActivity()!= "com.ooyala.sample.lists.NielsenListActivity") {
            driver.startActivity("com.ooyala.sample.NielsenSampleApp","com.ooyala.sample.lists.NielsenListActivity");
        }

        // Get Property Values
        LoadPropertyValues prop1 = new LoadPropertyValues();
        Properties p1=prop1.loadProperty();

        System.out.println(" Screen Mode "+ p1.getProperty("ScreenMode"));

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
    public static void iDDemo()throws Exception{
        try
        {
            NeilsenSampleApp NS = new NeilsenSampleApp();
            NS.waitForAppHomeScreen(driver);
            // Assert if current activity is indeed equal to the activity name of app home screen
            NS.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.NielsenListActivity");
            // Wrire to console activity name of home screen app
            System.out.println("NielsenSamppApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            // Select one of the video HLS,MP4 etc .
            NS.clickBasedOnText(driver, "ID3-Demo");
            Thread.sleep(2000);


            //verify if player was loaded
            NS.waitForPresence(driver, "className", "android.view.View");
            // Assert if current activity is indeed equal to the activity name of the video player
            NS.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.NielsenDefaultPlayerActivity");
            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            NS.waitForPresence(driver, "className", "android.widget.ImageButton");
            Thread.sleep(1000);
            System.out.println("Now will play in normal screen");
           NS.playInNormalScreen(driver);
            Thread.sleep(1000);
            EventVerification e = new EventVerification();
            e.verifyEvent("playStarted", " Video Started Play ", 5000);
            NS.loadingSpinner(driver);
            NS.pauseInNormalScreen(driver);
            Thread.sleep(6000);
            e.verifyEvent("stateChanged - state: PAUSED", " Playing Video Was Paused ", 9000);
            NS.seekVideo(driver);
            System.out.println("Hi..");
            e.verifyEvent("bufferingStarted" ," Playing Video was Seeked " , 15000);
            NS.loadingSpinner(driver);
            Thread.sleep(1000);
            NS.resumeInNormalScreen(driver);
            e.verifyEvent("playCompleted", " Video Completed  ",500000);








        }
        catch (Exception e)
        {

        }
    }

}
