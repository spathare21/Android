package testpackage.tests.exoPlayerSampleApp;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import testpackage.pageobjects.exoPlayerSampleApp;
import testpackage.utils.*;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Sameer on 5/3/2016.
 */
public class DeepTestIMA {
    private static AndroidDriver driver;

        @BeforeClass
        public void beforeTest() throws Exception {

            // closing all recent app from background.
            CloserecentApps.closeApps();

            System.out.println("BeforeTest \n");

            System.out.println(System.getProperty("user.dir"));
            // Get Property Values
            LoadPropertyValues prop = new LoadPropertyValues();
            Properties p=prop.loadProperty("exoPlayerSampleApp.properties");

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
            if(driver.currentActivity()!= "com.ooyala.sample.complete.MainExoPlayerActivity") {
                driver.startActivity("com.ooyala.sample.ExoPlayerSampleApp","com.ooyala.sample.complete.MainExoPlayerActivity");
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
        public void IMAAdRulesPreroll() throws Exception{
            int[] locPlayButon;

            try {

                // Creating an Object of FreeWheelSampleApp class
                exoPlayerSampleApp po = new exoPlayerSampleApp();
                // wait till home screen of basicPlayBackApp is opened
                po.waitForAppHomeScreen(driver);


                // Assert if current activity is indeed equal to the activity name of app home screen
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
                // Wrire to console activity name of home screen app
                System.out.println("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

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
                System.out.println("Exo Player - Google IMA List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

                // Select one of the video HLS,MP4 etc .
                po.clickBasedOnText(driver, "IMA Ad-Rules Preroll");
                Thread.sleep(2000);

                System.out.println("<<<<<<<<<Clicked on IMA Ad-Rules Preroll>>>>>>>>>>>");


                //verify if player was loaded
                po.waitForPresence(driver, "className", "android.view.View");
                // Assert if current activity is indeed equal to the activity name of the video player
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredIMAPlayerActivity");
                // Print to console output current player activity
                System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

                po.waitForPresenceOfText(driver,"h");

                //Getting Play button coordinates
                po.getPlay(driver);
                Thread.sleep(1000);

                //Clicking on Play button in Ooyala Skin
               // po.clickBasedOnText(driver,"h");

                //Ad Started Verification
                EventVerification ev = new EventVerification();
                ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
                Thread.sleep(5000);

                //Ad Completed Verification
                ev.verifyEvent("adCompleted", " Ad Completed to Play ", 30000);
                Thread.sleep(1000);

                //Play Started
                ev.verifyEvent("playStarted", " Video Started to Play ", 35000);

                //Timeout for the duration of the video
                Thread.sleep(30000);

                //Wait for video to finish and verify the playCompleted event .
                ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);
                Thread.sleep(2000);

                po.replayVideo(driver);
                ev.verifyEvent("stateChanged - state: PLAYING", " Video replay start ", 70000);

                Thread.sleep(3000);

                //Tapping on screen to pause the Video
                po.screentap(driver);

                Thread.sleep(1000);

                // pausing video
                po.pauseVideo(driver);
                // verifing video get paused
                ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

                Thread.sleep(3000);

                po.getBackFromRecentApp(driver);

                // verifing event that player has been get ready
                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

                Thread.sleep(2000);

                po.powerKeyClick(driver);

                // verifing event that player has been get ready
                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

                Thread.sleep(5000);

                //clicking on view area
                WebElement viewarea = driver.findElementByClassName("android.view.View");
                viewarea.click();
                Thread.sleep(1500);

                // clicking on more button
                po.moreButton(driver);

                Thread.sleep(2000);

                // clicking on Share button
                po.shareAsset(driver);

                System.out.println("clicked on share button");

                Thread.sleep(2000);

                // sharing asset on gmail.
                po.shareOnGmail(driver);
                Thread.sleep(1000);

                ev.verifyEvent("stateChanged - state: READY", " Mail sent, Back to SDK ", 70000);
                Thread.sleep(2000);

                System.out.println("clicking on discovery");
                po.clickOnDiscovery(driver);

                Thread.sleep(2000);

                po.clickOnCloseButton(driver);

                Thread.sleep(2000);

                System.out.println("clicking on CC");
                po.clickOnCC(driver);

                Thread.sleep(2000);

                // closing more option
                po.clickOnCloseButton(driver);
                Thread.sleep(2000);
                po.clickOnCloseButton(driver);

                Thread.sleep(5000);

                // tapping on screen for get the scrubber bar and play/pause button
                po.screentap(driver);
                Thread.sleep(1000);


                // playing the video
                po.playVideo(driver);
                ev.verifyEvent("stateChanged - state: PLAYING", " Video start ", 70000);

                Thread.sleep(2000);

                po.getBackFromRecentApp(driver);
                // verifing event that player has been get ready
                ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 60000);

                Thread.sleep(1000);

                po.powerKeyClick(driver);
                // verifing event that player has been get ready
                ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 60000);

                //Wait for video to finish and verify the playCompleted event .
                ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);

            }
            catch(Exception e)
            {
                System.out.println("IMAAdRulesPreroll throws Exception "+e);
                e.printStackTrace();
                Assert.assertTrue(false, "This will fail!");
                ScreenshotDevice.screenshot(driver,"IMAAdRulesPreroll");
            }
        }

        @org.testng.annotations.Test
        public void IMAAdRulesMidroll() throws Exception{
            int[] locPlayButon;

            try {

                // Creating an Object of FreeWheelSampleApp class
                exoPlayerSampleApp po = new exoPlayerSampleApp();
                // wait till home screen of basicPlayBackApp is opened
                po.waitForAppHomeScreen(driver);


                // Assert if current activity is indeed equal to the activity name of app home screen
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
                // Wrire to console activity name of home screen app
                System.out.println("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

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

                po.waitForPresenceOfText(driver,"IMA Ad-Rules Midroll");

                // Assert if current activity is indeed equal to the activity name of app home screen
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");
                // Wrire to console activity name of home screen app
                System.out.println("Exo Player - Google IMA List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

                // Select one of the video HLS,MP4 etc .
                po.clickBasedOnText(driver, "IMA Ad-Rules Midroll");
                Thread.sleep(2000);

                System.out.println("<<<<<<<<<Clicked on IMA Ad-Rules Midroll>>>>>>>>>>>");

                //verify if player was loaded
                po.waitForPresence(driver, "className", "android.view.View");
                // Assert if current activity is indeed equal to the activity name of the video player
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredIMAPlayerActivity");
                // Print to console output current player activity
                System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

                po.waitForPresenceOfText(driver,"h");

                po.getPlay(driver);
                Thread.sleep(1000);

                //Clicking on Play button in Ooyala Skin
              // po.clickBasedOnText(driver,"h");

                //Ad Started Verification
                EventVerification ev = new EventVerification();

                //Play Started
                ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

                Thread.sleep(11000);

                ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

                //Thread sleep time is equivalent to the length of the AD
                Thread.sleep(5000);

                //Ad Completed Verification
                ev.verifyEvent("adCompleted", " Ad Completed to Play ", 30000);

               //Timeout for the duration of the video
                Thread.sleep(30000);

                //Wait for video to finish and verify the playCompleted event .
                ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);
                Thread.sleep(2000);

                po.replayVideo(driver);
                ev.verifyEvent("stateChanged - state: PLAYING", " Video replay start ", 70000);
                Thread.sleep(3000);

                //Tapping on screen to pause the Video
                po.screentap(driver);
                Thread.sleep(1000);

                // pausing video
                po.pauseVideo(driver);
                // verifing video get paused
                ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

                Thread.sleep(4000);

                po.getBackFromRecentApp(driver);

                // verifing event that player has been get ready
                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

                Thread.sleep(1000);

                po.powerKeyClick(driver);

                // verifing event that player has been get ready
                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

                Thread.sleep(5000);

                //clicking on view area
                WebElement viewarea = driver.findElementByClassName("android.view.View");
                viewarea.click();
                Thread.sleep(1500);

                // clicking on more button
                po.moreButton(driver);


                Thread.sleep(2000);

                // clicking on Share button
                po.shareAsset(driver);

                System.out.println("clicked on share button");

                Thread.sleep(2000);

                // sharing asset on gmail.
                po.shareOnGmail(driver);
                Thread.sleep(1000);

                ev.verifyEvent("stateChanged - state: READY", " Mail sent, Back to SDK ", 70000);
                Thread.sleep(2000);

                System.out.println("clicking on discovery");
                po.clickOnDiscovery(driver);

                Thread.sleep(2000);

                po.clickOnCloseButton(driver);

                Thread.sleep(2000);

                System.out.println("clicking on CC");
                po.clickOnCC(driver);

                Thread.sleep(2000);

                // closing more option
                po.clickOnCloseButton(driver);
                Thread.sleep(2000);
                po.clickOnCloseButton(driver);

                Thread.sleep(5000);

                // tapping on screen for get the scrubber bar and play/pause button
                po.screentap(driver);

                Thread.sleep(1000);


                // playing the video
                po.playVideo(driver);
                ev.verifyEvent("stateChanged - state: PLAYING", " Video start ", 70000);

                Thread.sleep(2000);

                po.getBackFromRecentApp(driver);
                // verifing event that player has been get ready
                ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 60000);

                Thread.sleep(1000);

                po.powerKeyClick(driver);
                // verifing event that player has been get ready
                ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 60000);

                //Wait for video to finish and verify the playCompleted event .
                ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);

            }
            catch(Exception e)
            {
                System.out.println("IMAAdRulesMidroll throws Exception "+e);
                e.printStackTrace();
                Assert.assertTrue(false, "This will fail!");
                ScreenshotDevice.screenshot(driver,"IMAAdRulesMidroll");
            }
        }

        @org.testng.annotations.Test
        public void IMAAdRulesPostoll() throws Exception{
            int[] locPlayButon;

            try {

                // Creating an Object of FreeWheelSampleApp class
                exoPlayerSampleApp po = new exoPlayerSampleApp();
                // wait till home screen of basicPlayBackApp is opened
                po.waitForAppHomeScreen(driver);


                // Assert if current activity is indeed equal to the activity name of app home screen
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
                // Wrire to console activity name of home screen app
                System.out.println("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

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

                po.waitForPresenceOfText(driver,"IMA Ad-Rules Postroll");

                // Assert if current activity is indeed equal to the activity name of app home screen
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");
                // Wrire to console activity name of home screen app
                System.out.println("Exo Player - Google IMA List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

                // Select one of the video HLS,MP4 etc .
                po.clickBasedOnText(driver, "IMA Ad-Rules Postroll");
                Thread.sleep(2000);

                System.out.println("<<<<<Clicked on IMA Ad Rules Postroll>>>>>>>");

                //verify if player was loaded
                po.waitForPresence(driver, "className", "android.view.View");
                // Assert if current activity is indeed equal to the activity name of the video player
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredIMAPlayerActivity");
                // Print to console output current player activity
                System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

                po.waitForPresenceOfText(driver,"h");

                po.getPlay(driver);
                Thread.sleep(1000);

                //Clicking on Play button in Ooyala Skin
              //  po.clickBasedOnText(driver,"h");

                //Ad Started Verification
                EventVerification ev = new EventVerification();
                //Play Started
                ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
                Thread.sleep(30000);
                ev.verifyEvent("adStarted", " Ad Started to Play ", 40000);

                //Thread sleep time is equivalent to the length of the AD
                Thread.sleep(5000);

                //Ad Completed Verification
                ev.verifyEvent("adCompleted", " Ad Completed to Play ", 30000);


                //Wait for video to finish and verify the playCompleted event .
                ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);
                Thread.sleep(2000);

                po.replayVideo(driver);
                ev.verifyEvent("stateChanged - state: PLAYING", " Video replay start ", 70000);

                Thread.sleep(3000);

                //Tapping on screen to pause the Video
                po.screentap(driver);

                Thread.sleep(1000);

                // pausing video
                po.pauseVideo(driver);
                // verifing video get paused
                ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

                Thread.sleep(3000);

                po.getBackFromRecentApp(driver);

                // verifing event that player has been get ready
                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

                Thread.sleep(2000);

                po.powerKeyClick(driver);

                // verifing event that player has been get ready
                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

                Thread.sleep(5000);

                //clicking on view area
                WebElement viewarea = driver.findElementByClassName("android.view.View");
                viewarea.click();
                Thread.sleep(1500);

                // clicking on more button
                po.moreButton(driver);


                Thread.sleep(2000);

                // clicking on Share button
                po.shareAsset(driver);

                System.out.println("clicked on share button");

                Thread.sleep(2000);

                // sharing asset on gmail.
                po.shareOnGmail(driver);
                Thread.sleep(1000);

                ev.verifyEvent("stateChanged - state: READY", " Mail sent, Back to SDK ", 70000);
                Thread.sleep(2000);

                System.out.println("clicking on discovery");
                po.clickOnDiscovery(driver);

                Thread.sleep(2000);

                po.clickOnCloseButton(driver);

                Thread.sleep(2000);

                System.out.println("clicking on CC");
                po.clickOnCC(driver);

                Thread.sleep(2000);

                // closing more option
                po.clickOnCloseButton(driver);
                Thread.sleep(2000);
                po.clickOnCloseButton(driver);

                Thread.sleep(5000);

                // tapping on screen for get the scrubber bar and play/pause button
                po.screentap(driver);

                Thread.sleep(1000);


                // playing the video
                po.playVideo(driver);
                ev.verifyEvent("stateChanged - state: PLAYING", " Video start ", 70000);

                Thread.sleep(2000);

                po.getBackFromRecentApp(driver);
                // verifing event that player has been get ready
                ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 60000);

                Thread.sleep(1000);

                po.powerKeyClick(driver);
                // verifing event that player has been get ready
                ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 60000);

                //Wait for video to finish and verify the playCompleted event .
                ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
            }
            catch(Exception e)
            {
                System.out.println("IMAAdRulesPostoll throws Exception \n"+e);
                e.printStackTrace();
                Assert.assertTrue(false, "This will fail!");
                ScreenshotDevice.screenshot(driver,"IMAAdRulesPostoll");
            }
        }


        @org.testng.annotations.Test
        public void IMASkippable() throws Exception{
            int[] locPlayButon;

            try {

                // Creating an Object of FreeWheelSampleApp class
                exoPlayerSampleApp po = new exoPlayerSampleApp();
                // wait till home screen of basicPlayBackApp is opened
                po.waitForAppHomeScreen(driver);


                // Assert if current activity is indeed equal to the activity name of app home screen
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
                // Wrire to console activity name of home screen app
                System.out.println("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

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

                po.waitForPresenceOfText(driver,"IMA Skippable");

                // Assert if current activity is indeed equal to the activity name of app home screen
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");
                // Wrire to console activity name of home screen app
                System.out.println("Ooyala Skin - Google IMA List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

                // Select one of the video HLS,MP4 etc .
                po.clickBasedOnText(driver, "IMA Skippable");
                Thread.sleep(2000);

                System.out.println("<<<<<<<Clicked on IMA Skippable>>>>>>");


                //verify if player was loaded
                po.waitForPresence(driver, "className", "android.view.View");
                // Assert if current activity is indeed equal to the activity name of the video player
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredIMAPlayerActivity");
                // Print to console output current player activity
                System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

                po.waitForPresenceOfText(driver,"h");

                //Get play button coordinates
                po.getPlay(driver);
                Thread.sleep(1000);

                //Clicking on Play button in Ooyala Skin
             //   po.clickBasedOnText(driver,"h");

                //Ad Started Verification
                EventVerification ev = new EventVerification();
                ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
                Thread.sleep(5000);
    //            //skipping the ad
    //
    //            po.skipAd(driver);

                //Ad Completed Verification
                ev.verifyEvent("adCompleted", " Ad Completed to Play ", 40000);

                //Time out
                Thread.sleep(1000);

                //Play Started
                ev.verifyEvent("playStarted", " Video Started to Play ", 40000);

                //Timeout for the duration of the video
                Thread.sleep(11000);

                //Ad Started Verification
                ev.verifyEvent("adStarted", " Post - Ad Started to Play ", 45000);

                Thread.sleep(5000);

                //Ad Completed Verification
                ev.verifyEvent("adCompleted", " Post - Ad Completed to Play ", 50000);

                //Wait for video to finish and verify the playCompleted event .
                ev.verifyEvent("playCompleted", " Video Completed Play ", 50000);
                Thread.sleep(2000);

                po.replayVideo(driver);
                ev.verifyEvent("stateChanged - state: PLAYING", " Video replay start ", 70000);
                Thread.sleep(1500);

                //Tapping on screen to pause the Video
                po.screentap(driver);
                Thread.sleep(1000);

                // pausing video
                po.pauseVideo(driver);
                // verifing video get paused
                ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

                Thread.sleep(3000);

                po.getBackFromRecentApp(driver);

                // verifing event that player has been get ready
                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

                Thread.sleep(2000);

                po.powerKeyClick(driver);

                // verifing event that player has been get ready
                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

                Thread.sleep(5000);

                //clicking on view area
                WebElement viewarea = driver.findElementByClassName("android.view.View");
                viewarea.click();
                Thread.sleep(1500);

                // clicking on more button
                po.moreButton(driver);


                Thread.sleep(2000);

                // clicking on Share button
                po.shareAsset(driver);

                System.out.println("clicked on share button");

                Thread.sleep(2000);

                // sharing asset on gmail.
                po.shareOnGmail(driver);
                Thread.sleep(1000);

                ev.verifyEvent("stateChanged - state: READY", " Mail sent, Back to SDK ", 70000);
                Thread.sleep(2000);

                System.out.println("clicking on discovery");
                po.clickOnDiscovery(driver);

                Thread.sleep(2000);

                po.clickOnCloseButton(driver);

                Thread.sleep(2000);

                System.out.println("clicking on CC");
                po.clickOnCC(driver);

                Thread.sleep(2000);

                // closing more option
                po.clickOnCloseButton(driver);
                Thread.sleep(2000);
                po.clickOnCloseButton(driver);

                Thread.sleep(5000);

                // tapping on screen for get the scrubber bar and play/pause button
                po.screentap(driver);

                Thread.sleep(1000);


                // playing the video
                po.playVideo(driver);
                ev.verifyEvent("stateChanged - state: PLAYING", " Video start ", 70000);

                Thread.sleep(1000);

                po.getBackFromRecentApp(driver);
                // verifing event that player has been get ready
                ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 60000);

                po.powerKeyClick(driver);
                // verifing event that player has been get ready
                ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 60000);

                //Wait for video to finish and verify the playCompleted event .
                ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);

            }
            catch(Exception e)
            {
                System.out.println("IMASkippable throws Exception "+e);
                e.printStackTrace();
                Assert.assertTrue(false, "This will fail!");
                ScreenshotDevice.screenshot(driver,"IMASkippable");
            }
        }

        @org.testng.annotations.Test
        public void IMAPreMidPostSkippable() throws Exception{
            int[] locPlayButon;

            try {

                // Creating an Object of FreeWheelSampleApp class
                exoPlayerSampleApp po = new exoPlayerSampleApp();
                // wait till home screen of basicPlayBackApp is opened
                po.waitForAppHomeScreen(driver);


                // Assert if current activity is indeed equal to the activity name of app home screen
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
                // Wrire to console activity name of home screen app
                System.out.println("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

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

                po.waitForPresenceOfText(driver,"IMA Pre, Mid and Post Skippable");

                // Assert if current activity is indeed equal to the activity name of app home screen
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");
                // Wrire to console activity name of home screen app
                System.out.println("Ooyala Skin - Google IMA List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

                // Select one of the video HLS,MP4 etc .
                po.clickBasedOnText(driver, "IMA Pre, Mid and Post Skippable");
                Thread.sleep(2000);

                System.out.println("<<<<<<<Clicked on IMA Pre, Mid and Post Skippable >>>>>>");

                //verify if player was loaded
                po.waitForPresence(driver, "className", "android.view.View");
                // Assert if current activity is indeed equal to the activity name of the video player
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredIMAPlayerActivity");
                // Print to console output current player activity
                System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

                po.waitForPresenceOfText(driver,"h");

                //Get Play button coordinates
                po.getPlay(driver);
                Thread.sleep(2000);

                //Clicking on Play button in Ooyala Skin
               // po.clickBasedOnText(driver,"h");

                //Ad Started Verification
                EventVerification ev = new EventVerification();
                ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
                Thread.sleep(5000);
                //Ad skipping Verification
                ev.verifyEvent("adCompleted", " Ad play completed ", 40000);
                Thread.sleep(1000);

                //Play Started
                ev.verifyEvent("playStarted", " Video Started to Play ", 40000);
                Thread.sleep(5000);

                // Midroll event varification
                ev.verifyEvent("adStarted", " Ad Started to Play ", 45000);
                Thread.sleep(5000);

                //Ad Completed Verification
                ev.verifyEvent("adCompleted", "Ad play completed", 55000);
                Thread.sleep(1000);

                Thread.sleep(35000);

                ev.verifyEvent("adStarted", " Ad Started to Play ", 70000);
                Thread.sleep(5000);

                //Ad Completed Verification
                ev.verifyEvent("adCompleted", "Ad play completed", 80000);
                Thread.sleep(1000);

                //Wait for video to finish and verify the playCompleted event .
                ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
                Thread.sleep(2000);

                po.replayVideo(driver);
                ev.verifyEvent("stateChanged - state: PLAYING", " Video replay start ", 70000);

                Thread.sleep(3000);

                //Tapping on screen to pause the Video
                po.screentap(driver);

                Thread.sleep(1000);

                // pausing video
                po.pauseVideo(driver);
                // verifing video get paused
                ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

                Thread.sleep(3000);

                po.getBackFromRecentApp(driver);

                // verifing event that player has been get ready
                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

                Thread.sleep(2000);

                po.powerKeyClick(driver);

                // verifing event that player has been get ready
                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

                Thread.sleep(5000);

                //clicking on view area
                // WebElement viewarea = driver.findElementByClassName("android.view.View");
                //viewarea.click();
                //Thread.sleep(1500);

                //Tapping on screen
                po.screentap(driver);
                Thread.sleep(1000);

                // clicking on more button
                po.moreButton(driver);

                Thread.sleep(2000);

                // clicking on Share button
                po.shareAsset(driver);

                System.out.println("clicked on share button");

                Thread.sleep(2000);

                // sharing asset on gmail.
                po.shareOnGmail(driver);
                Thread.sleep(1000);

                ev.verifyEvent("stateChanged - state: READY", " Mail sent, Back to SDK ", 70000);
                Thread.sleep(2000);

                System.out.println("clicking on discovery");
                po.clickOnDiscovery(driver);

                Thread.sleep(2000);

                po.clickOnCloseButton(driver);

                Thread.sleep(2000);

                System.out.println("clicking on CC");
                po.clickOnCC(driver);

                Thread.sleep(2000);

                // closing more option
                po.clickOnCloseButton(driver);
                Thread.sleep(2000);
                po.clickOnCloseButton(driver);

                Thread.sleep(5000);

                // tapping on screen for get the scrubber bar and play/pause button
                po.screentap(driver);

                Thread.sleep(1000);


                // playing the video
                po.playVideo(driver);
                ev.verifyEvent("stateChanged - state: PLAYING", " Video start ", 70000);

                Thread.sleep(2000);

                po.getBackFromRecentApp(driver);
                // verifing event that player has been get ready
                ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 60000);

                Thread.sleep(1000);

                po.powerKeyClick(driver);
                // verifing event that player has been get ready
                ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 60000);

                //Wait for video to finish and verify the playCompleted event .
                ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);

            }
            catch(Exception e)
            {
                System.out.println("IMAPreMidPostSkippable throws Exception "+e);
                e.printStackTrace();
                Assert.assertTrue(false, "This will fail!");
                ScreenshotDevice.screenshot(driver,"IMAPreMidPostSkippable");
            }
        }

       @org.testng.annotations.Test
        public void IMAAdRulesPoddedMidroll() throws Exception{
            int[] locPlayButon;

            try {

                // Creating an Object of FreeWheelSampleApp class
                exoPlayerSampleApp po = new exoPlayerSampleApp();
                // wait till home screen of basicPlayBackApp is opened
                po.waitForAppHomeScreen(driver);


                // Assert if current activity is indeed equal to the activity name of app home screen
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
                // Wrire to console activity name of home screen app
                System.out.println("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

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

                po.waitForPresenceOfText(driver,"IMA Podded Midroll");

                // Assert if current activity is indeed equal to the activity name of app home screen
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");
                // Wrire to console activity name of home screen app
                System.out.println("Ooyala Skin - Google IMA List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

                // Select one of the video HLS,MP4 etc .
                po.clickBasedOnText(driver, "IMA Podded Midroll");
                Thread.sleep(2000);

                System.out.println("<<<<<<Clicked on IMA Podded Midroll>>>>>>");

                //verify if player was loaded
                po.waitForPresence(driver, "className", "android.view.View");
                // Assert if current activity is indeed equal to the activity name of the video player
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredIMAPlayerActivity");
                // Print to console output current player activity
                System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

                po.waitForPresenceOfText(driver,"h");

                //Get coordinates on play button
                po.getPlay(driver);
                Thread.sleep(1000);

                //Clicking on Play button in Ooyala Skin
               // po.clickBasedOnText(driver,"h");

                //Ad Started Verification
                EventVerification ev = new EventVerification();
                //Play Started
                ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
                Thread.sleep(10000);

                ev.verifyEvent("adStarted", " Ad Started to Play ", 35000);
                Thread.sleep(5000);
                //Ad Completed Verification
                ev.verifyEvent("adCompleted", " Ad Completed to Play ", 45000);

                //Time out
                Thread.sleep(1000);

                ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);
                Thread.sleep(5000);
                //Ad Completed Verification
                ev.verifyEvent("adCompleted", " Ad Completed to Play ", 55000);

                Thread.sleep(30000);

                //Wait for video to finish and verify the playCompleted event .
                ev.verifyEvent("playCompleted", " Video Completed Play ", 60000);
                Thread.sleep(2000);

                po.replayVideo(driver);
                ev.verifyEvent("stateChanged - state: PLAYING", " Video replay start ", 70000);

                Thread.sleep(3000);

                //Tapping on screen to pause the Video
                po.screentap(driver);

                Thread.sleep(1000);

                // pausing video
                po.pauseVideo(driver);
                // verifing video get paused
                ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

                Thread.sleep(2000);

                po.getBackFromRecentApp(driver);

                // verifing event that player has been get ready
                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

                Thread.sleep(2000);

                po.powerKeyClick(driver);

                // verifing event that player has been get ready
                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

                Thread.sleep(5000);

                //clicking on view area
                WebElement viewarea = driver.findElementByClassName("android.view.View");
                viewarea.click();
                Thread.sleep(1500);

                // clicking on more button
                po.moreButton(driver);

                Thread.sleep(2000);

                // clicking on Share button
                po.shareAsset(driver);

                System.out.println("clicked on share button");

                Thread.sleep(2000);

                // sharing asset on gmail.
                po.shareOnGmail(driver);
                Thread.sleep(1000);

                ev.verifyEvent("stateChanged - state: READY", " Mail sent, Back to SDK ", 70000);
                Thread.sleep(2000);

                System.out.println("clicking on discovery");
                po.clickOnDiscovery(driver);

                Thread.sleep(2000);

                po.clickOnCloseButton(driver);

                Thread.sleep(2000);

                System.out.println("clicking on CC");
                po.clickOnCC(driver);

                Thread.sleep(2000);

                // closing more option
                po.clickOnCloseButton(driver);
                Thread.sleep(2000);
                po.clickOnCloseButton(driver);

                Thread.sleep(5000);

                // tapping on screen for get the scrubber bar and play/pause button
                po.screentap(driver);

                Thread.sleep(1000);


                // playing the video
                po.playVideo(driver);
                ev.verifyEvent("stateChanged - state: PLAYING", " Video start ", 70000);

                Thread.sleep(2000);

                po.getBackFromRecentApp(driver);
                // verifing event that player has been get ready
                ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 60000);

                Thread.sleep(1000);

                po.powerKeyClick(driver);
                // verifing event that player has been get ready
                ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 60000);

                //Wait for video to finish and verify the playCompleted event .
                ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);

            }
            catch(Exception e)
            {
                System.out.println("IMAAdRulesPoddedMidroll throws Exception\n "+e);
                e.printStackTrace();
                Assert.assertTrue(false, "This will fail!");
                ScreenshotDevice.screenshot(driver,"IMAAdRulesPoddedMidroll");
            }
        }

        @org.testng.annotations.Test
        public void IMAAdRulesPoddedPostroll() throws Exception{
            int[] locPlayButon;

            try {

                // Creating an Object of FreeWheelSampleApp class
                exoPlayerSampleApp po = new exoPlayerSampleApp();
                // wait till home screen of basicPlayBackApp is opened
                po.waitForAppHomeScreen(driver);


                // Assert if current activity is indeed equal to the activity name of app home screen
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
                // Wrire to console activity name of home screen app
                System.out.println("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

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

                po.waitForPresenceOfText(driver,"IMA Podded Postroll");

                // Assert if current activity is indeed equal to the activity name of app home screen
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");
                // Wrire to console activity name of home screen app
                System.out.println("Exo Player - Google IMA List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

                // Select one of the video HLS,MP4 etc .
                po.clickBasedOnText(driver, "IMA Podded Postroll");
                Thread.sleep(2000);

                System.out.println("<<<<<<<<<<Clicked on IMA Podded Postroll>>>>>>>>>>>");


                //verify if player was loaded
                po.waitForPresence(driver, "className", "android.view.View");
                // Assert if current activity is indeed equal to the activity name of the video player
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredIMAPlayerActivity");
                // Print to console output current player activity
                System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

                po.waitForPresenceOfText(driver,"h");

                //Coordinates of Play button
                po.getPlay(driver);
                Thread.sleep(1000);

                //Clicking on Play button in Ooyala Skin
              //  po.clickBasedOnText(driver,"h");

                //Ad Started Verification
                EventVerification ev = new EventVerification();
                //Play Started
                ev.verifyEvent("playStarted", " Video Started to Play ", 30000);
                Thread.sleep(30000);

                ev.verifyEvent("adStarted", " Ad Started to Play ", 35000);
                Thread.sleep(5000);
                //Ad Completed Verification
                ev.verifyEvent("adCompleted", " Ad Completed to Play ", 40000);

                //Time out
                Thread.sleep(1000);

                ev.verifyEvent("adStarted", " Ad Started to Play ", 45000);
                Thread.sleep(5000);
                //Ad Completed Verification
                ev.verifyEvent("adCompleted", " Ad Completed to Play ", 50000);

                //Wait for video to finish and verify the playCompleted event .
                ev.verifyEvent("playCompleted", " Video Completed Play ", 60000);
                Thread.sleep(2000);

                po.replayVideo(driver);
                ev.verifyEvent("stateChanged - state: PLAYING", " Video replay start ", 70000);

                Thread.sleep(3000);

                //Tapping on screen to pause the Video
                po.screentap(driver);

                Thread.sleep(1000);

                // pausing video
                po.pauseVideo(driver);
                // verifing video get paused
                ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

                Thread.sleep(2000);

                po.getBackFromRecentApp(driver);

                // verifing event that player has been get ready
                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

                Thread.sleep(2000);

                po.powerKeyClick(driver);

                // verifing event that player has been get ready
                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

                Thread.sleep(5000);

                //clicking on view area
                WebElement viewarea = driver.findElementByClassName("android.view.View");
                viewarea.click();
                Thread.sleep(1500);

                // clicking on more button
                po.moreButton(driver);

                Thread.sleep(2000);

                // clicking on Share button
                po.shareAsset(driver);

                System.out.println("clicked on share button");

                Thread.sleep(2000);

                // sharing asset on gmail.
                po.shareOnGmail(driver);
                Thread.sleep(1000);

                ev.verifyEvent("stateChanged - state: READY", " Mail sent, Back to SDK ", 70000);
                Thread.sleep(2000);

                System.out.println("clicking on discovery");
                po.clickOnDiscovery(driver);

                Thread.sleep(2000);

                po.clickOnCloseButton(driver);

                Thread.sleep(2000);

                System.out.println("clicking on CC");
                po.clickOnCC(driver);

                Thread.sleep(2000);

                // closing more option
                po.clickOnCloseButton(driver);
                Thread.sleep(2000);
                po.clickOnCloseButton(driver);

                Thread.sleep(5000);

                // tapping on screen for get the scrubber bar and play/pause button
                po.screentap(driver);

                Thread.sleep(1000);


                // playing the video
                po.playVideo(driver);
                ev.verifyEvent("stateChanged - state: PLAYING", " Video start ", 70000);

                Thread.sleep(2000);

                po.getBackFromRecentApp(driver);
                // verifing event that player has been get ready
                ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 60000);

                Thread.sleep(1000);

                po.powerKeyClick(driver);
                // verifing event that player has been get ready
                ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 60000);

                //Wait for video to finish and verify the playCompleted event .
                ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);

            }
            catch(Exception e)
            {
                System.out.println("IMAAdRulesPoddedPostroll throws Exception \n"+e);
                e.printStackTrace();
                Assert.assertTrue(false, "This will fail!");
                ScreenshotDevice.screenshot(driver,"IMAAdRulesPoddedPostroll");
            }
        }

      @org.testng.annotations.Test
        public void IMAAdRulesPoddedPreMidPost() throws Exception{
            int[] locPlayButon;

            try {

                // Creating an Object of FreeWheelSampleApp class
                exoPlayerSampleApp po = new exoPlayerSampleApp();
                // wait till home screen of basicPlayBackApp is opened
                po.waitForAppHomeScreen(driver);


                // Assert if current activity is indeed equal to the activity name of app home screen
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
                // Wrire to console activity name of home screen app
                System.out.println("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

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

                po.waitForPresenceOfText(driver,"IMA Podded Pre-Mid-Post");

                // Assert if current activity is indeed equal to the activity name of app home screen
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");
                // Wrire to console activity name of home screen app
                System.out.println("Ooyala Skin - Google IMA List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

                // Select one of the video HLS,MP4 etc .
                po.clickBasedOnText(driver, "IMA Podded Pre-Mid-Post");
                Thread.sleep(2000);

                System.out.println("<<<<<<<Clicked on IMA Podded Pre-Mid-Post>>>>>>>>");


                //verify if player was loaded
                po.waitForPresence(driver, "className", "android.view.View");
                // Assert if current activity is indeed equal to the activity name of the video player
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredIMAPlayerActivity");
                // Print to console output current player activity
                System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

                po.waitForPresenceOfText(driver,"h");

                po.getPlay(driver);
                Thread.sleep(1000);

                //Clicking on Play button in Ooyala Skin
               // po.clickBasedOnText(driver,"h");

                //Ad Started Verification
                EventVerification ev = new EventVerification();

                ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
                Thread.sleep(5000);
                //Ad Completed Verification
                ev.verifyEvent("adCompleted", " Ad Completed to Play ", 40000);

                ev.verifyEvent("adStarted", " Ad Started to Play ", 41000);
                Thread.sleep(5000);
                //Ad Completed Verification
                ev.verifyEvent("adCompleted", " Ad Completed to Play ", 46000);

                ev.verifyEvent("adStarted", " Ad Started to Play ", 47000);
                Thread.sleep(5000);
                //Ad Completed Verification
                ev.verifyEvent("adCompleted", " Ad Completed to Play ", 52000);

                //Play Started
                ev.verifyEvent("playStarted", " Video Started to Play ", 53000);
                Thread.sleep(10000);

                ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);
                Thread.sleep(5000);
                //Ad Completed Verification
                ev.verifyEvent("adCompleted", " Ad Completed to Play ", 65000);

                ev.verifyEvent("adStarted", " Ad Started to Play ", 66000);
                //Time out
                Thread.sleep(5000);
                //Ad Completed Verification
                ev.verifyEvent("adCompleted", " Ad Completed to Play ", 71000);

                ev.verifyEvent("adStarted", " Ad Started to Play ", 72000);
                //Time out
                Thread.sleep(5000);
                //Ad Completed Verification
                ev.verifyEvent("adCompleted", " Ad Completed to Play ", 77000);
                Thread.sleep(40000);

                ev.verifyEvent("adStarted", " Ad Started to Play ", 80000);
                Thread.sleep(5000);
                //Ad Completed Verification
                ev.verifyEvent("adCompleted", " Ad Completed to Play ", 85000);

                ev.verifyEvent("adStarted", " Ad Started to Play ", 86000);
                //Time out
                Thread.sleep(5000);
                //Ad Completed Verification
                ev.verifyEvent("adCompleted", " Ad Completed to Play ", 91000);

                ev.verifyEvent("adStarted", " Ad Started to Play ", 92000);
                //Time out
                Thread.sleep(5000);
                //Ad Completed Verification
                ev.verifyEvent("adCompleted", " Ad Completed to Play ", 97000);

                //Wait for video to finish and verify the playCompleted event .
                ev.verifyEvent("playCompleted", " Video Completed Play ", 100000);
                Thread.sleep(2000);

                po.replayVideo(driver);
                ev.verifyEvent("stateChanged - state: PLAYING", " Video replay start ", 70000);

                Thread.sleep(3000);

                //Tapping on screen to pause the Video
                po.screentap(driver);
                Thread.sleep(1000);

                // pausing video
                po.pauseVideo(driver);
                // verifing video get paused
                ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

                Thread.sleep(2000);

                po.getBackFromRecentApp(driver);

                // verifing event that player has been get ready
                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

                Thread.sleep(2000);

                po.powerKeyClick(driver);

                // verifing event that player has been get ready
                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

                Thread.sleep(5000);

                //clicking on view area
                WebElement viewarea = driver.findElementByClassName("android.view.View");
                viewarea.click();
                Thread.sleep(1500);

                // clicking on more button
                po.moreButton(driver);

                Thread.sleep(2000);

                // clicking on Share button
                po.shareAsset(driver);

                System.out.println("clicked on share button");

                Thread.sleep(2000);

                // sharing asset on gmail.
                po.shareOnGmail(driver);
                Thread.sleep(1000);

                ev.verifyEvent("stateChanged - state: READY", " Mail sent, Back to SDK ", 70000);
                Thread.sleep(2000);

                System.out.println("clicking on discovery");
                po.clickOnDiscovery(driver);

                Thread.sleep(2000);

                po.clickOnCloseButton(driver);

                Thread.sleep(2000);

                System.out.println("clicking on CC");
                po.clickOnCC(driver);

                Thread.sleep(2000);

                // closing more option
                po.clickOnCloseButton(driver);
                Thread.sleep(2000);
                po.clickOnCloseButton(driver);

                Thread.sleep(5000);

                // tapping on screen for get the scrubber bar and play/pause button
                po.screentap(driver);

                Thread.sleep(1000);

                // playing the video
                po.playVideo(driver);
                ev.verifyEvent("stateChanged - state: PLAYING", " Video start ", 70000);

                Thread.sleep(2000);

                po.getBackFromRecentApp(driver);
                // verifing event that player has been get ready
                ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 60000);

                Thread.sleep(1000);

                po.powerKeyClick(driver);
                // verifing event that player has been get ready
                ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 60000);

                //Wait for video to finish and verify the playCompleted event .
                ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
            }
            catch(Exception e)
            {
                System.out.println("IMAAdRulesPoddedPreMidPost throws Exception "+e);
                e.printStackTrace();
                Assert.assertTrue(false, "This will fail!");
                ScreenshotDevice.screenshot(driver,"IMAAdRulesPoddedPreMidPost");
            }
        }

        @org.testng.annotations.Test
        public void IMAAdRulesPoddedPreroll() throws Exception{
            int[] locPlayButon;

            try {

                // Creating an Object of FreeWheelSampleApp class
                exoPlayerSampleApp po = new exoPlayerSampleApp();
                // wait till home screen of basicPlayBackApp is opened
                po.waitForAppHomeScreen(driver);


                // Assert if current activity is indeed equal to the activity name of app home screen
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
                // Wrire to console activity name of home screen app
                System.out.println("ExoPlayerApp Launched successfully. Activity :- " + driver.currentActivity() + "\n");

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

                po.waitForPresenceOfText(driver,"IMA Podded Preroll");

                // Assert if current activity is indeed equal to the activity name of app home screen
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.IMAListActivity");
                // Wrire to console activity name of home screen app
                System.out.println("Exo Player - Google IMA List Activity Launched successfully. Activity :- " + driver.currentActivity() + "\n");

                // Select one of the video HLS,MP4 etc .
                po.clickBasedOnText(driver, "IMA Podded Preroll");
                Thread.sleep(2000);

                System.out.println("<<<<<<Clicked on IMA Podded Preroll>>>>>");

                //verify if player was loaded
                po.waitForPresence(driver, "className", "android.view.View");
                // Assert if current activity is indeed equal to the activity name of the video player
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredIMAPlayerActivity");
                // Print to console output current player activity
                System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

                po.waitForPresenceOfText(driver,"h");

                po.getPlay(driver);
                Thread.sleep(1000);

                //Clicking on Play button in Ooyala Skin
               // po.clickBasedOnText(driver,"h");

                //Ad Started Verification
                EventVerification ev = new EventVerification();
                ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
                Thread.sleep(5000);
                //Ad Completed Verification
                ev.verifyEvent("adCompleted", " Ad Completed to Play ", 40000);

                //Time out
                Thread.sleep(1000);

                ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
                Thread.sleep(5000);
                //Ad Completed Verification
                ev.verifyEvent("adCompleted", " Ad Completed to Play ", 40000);
                Thread.sleep(1000);

                //Play Started
                ev.verifyEvent("playStarted", " Video Started to Play ", 30000);

                //Timeout for the duration of the video
                Thread.sleep(30000);

                //Wait for video to finish and verify the playCompleted event .
                ev.verifyEvent("playCompleted", " Video Completed Play ", 45000);
                Thread.sleep(2000);

                po.replayVideo(driver);
                ev.verifyEvent("stateChanged - state: PLAYING", " Video replay start ", 70000);
                Thread.sleep(3000);

                //Tapping on screen to pause the Video
                po.screentap(driver);
                Thread.sleep(1000);

                // pausing video
                po.pauseVideo(driver);
                // verifing video get paused
                ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

                Thread.sleep(2000);

                po.getBackFromRecentApp(driver);

                // verifing event that player has been get ready
                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

                Thread.sleep(2000);

                po.powerKeyClick(driver);

                // verifing event that player has been get ready
                ev.verifyEvent("stateChanged - state: READY", "Now player is ready", 50000);

                Thread.sleep(5000);

                //clicking on view area
                WebElement viewarea = driver.findElementByClassName("android.view.View");
                viewarea.click();
                Thread.sleep(1500);

                // clicking on more button
                po.moreButton(driver);

                Thread.sleep(2000);

                // clicking on Share button
                po.shareAsset(driver);

                System.out.println("clicked on share button");

                Thread.sleep(2000);

                // sharing asset on gmail.
                po.shareOnGmail(driver);
                Thread.sleep(1000);

                ev.verifyEvent("stateChanged - state: READY", " Mail sent, Back to SDK ", 70000);
                Thread.sleep(2000);

                System.out.println("clicking on discovery");
                po.clickOnDiscovery(driver);

                Thread.sleep(2000);

                po.clickOnCloseButton(driver);

                Thread.sleep(2000);

                System.out.println("clicking on CC");
                po.clickOnCC(driver);

                Thread.sleep(2000);

                // closing more option
                po.clickOnCloseButton(driver);
                Thread.sleep(2000);
                po.clickOnCloseButton(driver);

                Thread.sleep(5000);

                // tapping on screen for get the scrubber bar and play/pause button
                po.screentap(driver);
                Thread.sleep(1000);

                // playing the video
                po.playVideo(driver);
                ev.verifyEvent("stateChanged - state: PLAYING", " Video start ", 70000);

                Thread.sleep(2000);

                po.getBackFromRecentApp(driver);
                // verifing event that player has been get ready
                ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 60000);

                Thread.sleep(1000);

                po.powerKeyClick(driver);
                // verifing event that player has been get ready
                ev.verifyEvent("stateChanged - state: PLAYING", "Now player is ready", 60000);

                //Wait for video to finish and verify the playCompleted event .
                ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);

            }
            catch(Exception e)
            {
                System.out.println("IMAAdRulesPoddedPreroll throws Exception "+e);
                e.printStackTrace();
                Assert.assertTrue(false, "This will fail!");
                ScreenshotDevice.screenshot(driver,"IMAAdRulesPoddedPreroll");
            }
        }
}
