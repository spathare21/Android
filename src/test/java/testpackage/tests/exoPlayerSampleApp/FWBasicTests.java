package testpackage.tests.exoPlayerSampleApp;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import testpackage.pageobjects.exoPlayerSampleApp;
import testpackage.utils.*;
import java.io.IOException;
import java.util.Properties;

public class FWBasicTests extends EventLogTest{

    LoadPropertyValues prop = new LoadPropertyValues();
    Properties p;

        @BeforeClass
        public void beforeTest() throws Exception {
            // closing all recent app from background.
            CloserecentApps.closeApps();
            // Get Property Values
            p = prop.loadProperty("exoPlayerSampleApp.properties");
            //setup and initialize android driver
            SetUpAndroidDriver setUpdriver = new SetUpAndroidDriver();
            driver = setUpdriver.setUpandReturnAndroidDriver(p.getProperty("udid"), p.getProperty("appDir"), p.getProperty("appValue"), p.getProperty("platformName"), p.getProperty("platformVersion"), p.getProperty("appPackage"), p.getProperty("appActivity"));
            Thread.sleep(2000);
        }

        @BeforeMethod
        public void beforeMethod() throws Exception {
            //push the log file to the device
            driver.manage().logs().get("logcat");
            PushLogFileToDevice logpush=new PushLogFileToDevice();
            logpush.pushLogFile();
            if(driver.currentActivity()!= "com.ooyala.sample.complete.MainExoPlayerActivity") {
                driver.startActivity("com.ooyala.sample.ExoPlayerSampleApp","com.ooyala.sample.complete.MainExoPlayerActivity");
            }
            // Get Property Values
             p = prop.loadProperty();
            // Display the screen mode to console
            System.out.println(" Screen Mode "+ p.getProperty("ScreenMode"));
        }
        @AfterClass
        public void afterTest() throws InterruptedException, IOException {
            //close the app
            driver.closeApp();
            // quit the android driver
            driver.quit();
            //load the property values
            p = prop.loadProperty();
            String prop = p.getProperty("appPackage");
            //uninstall the app
            Appuninstall.uninstall(prop);
        }

        @AfterMethod
        public void afterMethod(ITestResult result) throws Exception {
            // Remove the events log file from the device
            RemoveEventsLogFile.removeEventsFileLog();
            Thread.sleep(10000);
        }

         @org.testng.annotations.Test
        public void FreeWheelIntegrationPreRoll() throws Exception{
            try {
                // Creating an Object of ExoPlayerSampleApp class
                exoPlayerSampleApp po = new exoPlayerSampleApp();

                // wait till home screen of ExoPlayerSampleApp is opened
                po.waitForAppHomeScreen(driver);

                // Assert if current activity is indeed equal to the activity name of app home screen
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");

                // Wrire to console activity name of home screen app
                System.out.println("Exo Player Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

                //Pause the running of test for a brief time .
                Thread.sleep(3000);

                //click on freewheel Integration
                po.clickBasedOnText(driver, "Freewheel Integration");
                Thread.sleep(2000);

                // Assert if current activity is Freewheel list activity
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");

                // Select one of the video.
                po.clickBasedOnText(driver, "Freewheel Preroll");
                Thread.sleep(2000);

                //verify if player was loaded
                po.waitForPresence(driver, "className", "android.view.View");

                // Assert if current activity is indeed equal to the activity name of the video player
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");

                // Print to console output current player activity
                System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

                //wait for the start screen of the video to appear
                po.waitForPresenceOfText(driver,"h");

                //Clicking on Play button in Ooyala Skin
                po.getPlay(driver);

                //Play Started Event Verification
                EventVerification ev = new EventVerification();
                ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

                // Verify event for ad completed
                ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);

                //Wait for video to start and verify the playStarted event .
                ev.verifyEvent("playStarted", " Video Started Play ", 30000);
                Thread.sleep(2000);

                // tap on the screen
                po.screentapping(driver);

                //pause the playing video
                po.pausingVideo(driver);

                //verify pause event
                ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

                //seek the video
                po.seek_video(driver,100);

                //verify seek completed event
                ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);

                //handle the loading spinner
                po.loadingSpinner(driver);

                //resume the video playback
                po.getPlay(driver);
                ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

                //Wait for video to finish and verify the playCompleted event .
                ev.verifyEvent("playCompleted", " Video Completed Play ", 70000);
            }
            catch(Exception e){
                System.out.println("FreeWheelIntegrationPreRoll throws Exception "+e);
                e.printStackTrace();
                ScreenshotDevice.screenshot(driver,"FreeWheelIntegrationPreRoll");
                Assert.assertTrue(false, "This will fail!");
            }
        }

        @org.testng.annotations.Test
        public void FreeWheelIntegrationMidroll() throws Exception {
        try {
            // Creating an Object of ExoPlayerSampleApp class
            exoPlayerSampleApp po = new exoPlayerSampleApp();

            // wait till home screen of ExoPlayerSampleApp is opened
            po.waitForAppHomeScreen(driver);

            // Assert if current activity is indeed equal to the activity name of app home screen
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");

            // Wrire to console activity name of home screen app
            System.out.println("Exo Player Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

            //Pause the running of test for a brief time .
            Thread.sleep(3000);

            //click on freewheel Integration
            po.clickBasedOnText(driver, "Freewheel Integration");
            Thread.sleep(2000);

            // Assert if current activity is Freewheel list activity
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");

            // Select one of the video.
            po.clickBasedOnText(driver, "Freewheel Midroll");
            Thread.sleep(2000);

            //verify if player was loaded
            po.waitForPresence(driver, "className", "android.view.View");

            // Assert if current activity is indeed equal to the activity name of the video player
            po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");

            // Print to console output current player activity
            System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

            //wait for the start screen to appear
            po.waitForPresenceOfText(driver, "h");

            //Clicking on Play button in Ooyala Skin
            po.getPlay(driver);

            //Play Started Verification
            EventVerification ev = new EventVerification();

            //Wait for video to start and verify the playStarted event .
            ev.verifyEvent("playStarted", " Video Started Play ", 30000);
            Thread.sleep(2000);

            //tap on the screen
            po.screentapping(driver);

            //pause the video
            po.pausingVideo(driver);

            //verify paused event
            ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 40000);

            //seek video in normal screen
            po.seek_video(driver,100);

            //verify seek completed event
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 50000);

            //handle the loading spinner
            po.loadingSpinner(driver);

            //resume the video playback in normal screen
            po.getPlay(driver);

            //verify playing event
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 60000);

            //verify ad started event
            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

            // verify ad completed event
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
        } catch (Exception e){
            System.out.println("FreeWheelIntegrationMidroll throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"FreeWheelIntegrationMidroll");
            Assert.assertTrue(false, "This will fail!");
        }
    }

        @org.testng.annotations.Test
        public void FreeWheelIntegrationPostroll() throws Exception {
            try {
                // Creating an Object of ExoPlayerSampleApp class
                exoPlayerSampleApp po = new exoPlayerSampleApp();

                // wait till home screen of ExoPlayerSampleApp is opened
                po.waitForAppHomeScreen(driver);

                // Assert if current activity is indeed equal to the activity name of app home screen
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");

                // Wrire to console activity name of home screen app
                System.out.println("Exo Player Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

                //Pause the running of test for a brief time .
                Thread.sleep(3000);

                //click on freewheel Integration
                po.clickBasedOnText(driver, "Freewheel Integration");
                Thread.sleep(2000);

                // Assert if current activity is Freewheel list activity
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");

                // Select one of the video HLS,MP4 etc .
                po.clickBasedOnText(driver, "Freewheel Postroll");
                Thread.sleep(2000);

                //verify if player was loaded
                po.waitForPresence(driver, "className", "android.view.View");

                // Assert if current activity is indeed equal to the activity name of the video player
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");

                // Print to console output current player activity
                System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

                //wait for the start screen to appear
                po.waitForPresenceOfText(driver, "h");

                //Clicking on Play button in Ooyala Skin
                po.getPlay(driver);

                //Play Started event Verification
                EventVerification ev = new EventVerification();

                //Wait for video to start and verify the playStarted event .
                ev.verifyEvent("playStarted", " Video Started Play ", 30000);
                Thread.sleep(2000);

                //tap on the screen
                po.screentapping(driver);

                //pause the video in normal screen
                po.pausingVideo(driver);

                //pause event verification
                ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 40000);

                //seek the video in normal screen
                po.seek_video(driver,100);

                //seek completed event verification
                ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 50000);

                //handle the loading spinner
                po.loadingSpinner(driver);

                //resume the video playback in normal screen
                po.getPlay(driver);

                //playing event verification
                ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 60000);

                //ad started event verification
                ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

                // Ad completed event verification
                ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

                //Wait for video to finish and verify the playCompleted event .
                ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
            } catch (Exception e){
                System.out.println("FreeWheelIntegrationPostroll throws Exception " + e);
                e.printStackTrace();
                ScreenshotDevice.screenshot(driver,"FreeWheelIntegrationPostroll");
                Assert.assertTrue(false, "This will fail!");
            }
        }

        @org.testng.annotations.Test
        public void FreeWheelIntegrationPreMidPostroll() throws Exception {
            try {

                // Creating an Object of ExoPlayerSampleApp class
                exoPlayerSampleApp po = new exoPlayerSampleApp();

                // wait till home screen of ExoPlayerSampleApp is opened
                po.waitForAppHomeScreen(driver);

                // Assert if current activity is indeed equal to the activity name of app home screen
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");

                // Wrire to console activity name of home screen app
                System.out.println("Exo Player Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

                //Pause the running of test for a brief time .
                Thread.sleep(3000);

                po.clickBasedOnText(driver, "Freewheel Integration");
                Thread.sleep(2000);

                // Assert if current activity is Freewheel list activity
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");

                // Select one of the video HLS,MP4 etc .
                po.clickBasedOnText(driver, "Freewheel PreMidPost");
                Thread.sleep(2000);

                //verify if player was loaded
                po.waitForPresence(driver, "className", "android.view.View");

                // Assert if current activity is indeed equal to the activity name of the video player
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");

                // Print to console output current player activity
                System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

                //wait for the start screen to appear
                po.waitForPresenceOfText(driver, "h");

                //Clicking on Play button in Ooyala Skin
                po.getPlay(driver);

                //create an object of event verification
                EventVerification ev = new EventVerification();

                //Ad Started event Verification
                ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

                // Ad completed event verification
                ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

                //Wait for video to start and verify the playStarted event .
                ev.verifyEvent("playStarted", " Video Started Play ", 30000);
                Thread.sleep(2000);

                //tapping on the screen
                po.screentapping(driver);

                //pause the video in normal screen
                po.pausingVideo(driver);

                //pause event verification
                ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 40000);

                //seek video in normal screen
                po.seek_video(driver,100);

                //seek completed event verification
                ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 50000);

                //handle the loading spinner
                po.loadingSpinner(driver);

                //resume the video playback in normal screen
                po.getPlay(driver);

                //video playing event verfication
                ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 60000);

                //ad started event verification
                ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

                // Ad completed event verification
                ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

                //ad started event verification
                ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);

                // Ad completed event verification
                ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

                //Wait for video to finish and verify the playCompleted event .
                ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);
            } catch (Exception e){
                System.out.println("FreeWheelIntegrationPreMidPostroll throws Exception " + e);
                e.printStackTrace();
                ScreenshotDevice.screenshot(driver,"FreeWheelIntegrationPreMidPostroll");
                Assert.assertTrue(false, "This will fail!");
            }
        }
        @org.testng.annotations.Test
        public void FreeWheelIntegrationOverlay() throws Exception {

            try {
                // Creating an Object of FreeWheelSampleApp class
                exoPlayerSampleApp po = new exoPlayerSampleApp();
                // wait till home screen of basicPlayBackApp is opened
                po.waitForAppHomeScreen(driver);


                // Assert if current activity is indeed equal to the activity name of app home screen
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
                // Wrire to console activity name of home screen app
                System.out.println("Exo Player Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

                //Pause the running of test for a brief time .
                Thread.sleep(3000);

                po.clickBasedOnText(driver, "Freewheel Integration");
                Thread.sleep(2000);

                // Assert if current activity is Freewheel list activity
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");

                // Select one of the video HLS,MP4 etc .
                po.clickBasedOnText(driver, "Freewheel Overlay");
                Thread.sleep(2000);

                System.out.println("clicked on based text");


                //verify if player was loaded
                po.waitForPresence(driver, "className", "android.view.View");
                // Assert if current activity is indeed equal to the activity name of the video player
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");
                // Print to console output current player activity
                System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

                po.waitForPresenceOfText(driver, "h");

                //Clicking on Play button in Ooyala Skin
                po.getPlay(driver);

                //Play Started Verification
                EventVerification ev = new EventVerification();

                //Wait for video to start and verify the playStarted event .
                ev.verifyEvent("playStarted", " Video Started Play ", 30000);

                po.verifyOverlay(driver);

                //Wait for video to finish and verify the playCompleted event .
                ev.verifyEvent("playCompleted", " Video Completed Play ", 50000);


            } catch (Exception e) {
                System.out.println("FreeWheelIntegrationOverlay throws Exception " + e);
                e.printStackTrace();
                ScreenshotDevice.screenshot(driver,"FreeWheelIntegrationOverlay");
                Assert.assertTrue(false, "This will fail!");
            }

        }

        @org.testng.annotations.Test
        public void FreeWheelIntegrationMultiMidroll() throws Exception {

            try {
                // Creating an Object of FreeWheelSampleApp class
                exoPlayerSampleApp po = new exoPlayerSampleApp();
                // wait till home screen of basicPlayBackApp is opened
                po.waitForAppHomeScreen(driver);


                // Assert if current activity is indeed equal to the activity name of app home screen
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
                // Wrire to console activity name of home screen app
                System.out.println("Exo Player Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

                //Pause the running of test for a brief time .
                Thread.sleep(3000);

                po.clickBasedOnText(driver, "Freewheel Integration");
                Thread.sleep(2000);

                // Assert if current activity is Freewheel list activity
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");

                // Select one of the video HLS,MP4 etc .
                po.clickBasedOnText(driver, "Freewheel Multi Midroll");
                Thread.sleep(2000);

                System.out.println("clicked on based text");


                //verify if player was loaded
                po.waitForPresence(driver, "className", "android.view.View");
                // Assert if current activity is indeed equal to the activity name of the video player
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");
                // Print to console output current player activity
                System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

                po.waitForPresenceOfText(driver, "h");

                //Clicking on Play button in Ooyala Skin
                po.getPlay(driver);


                //Play Started Verification
                EventVerification ev = new EventVerification();

                //Wait for video to start and verify the playStarted event .
                ev.verifyEvent("playStarted", " Video Started Play ", 30000);

                Thread.sleep(2000);

                po.screentapping(driver);

                po.pausingVideo(driver);

                ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 40000);

                po.seek_video(driver,100);
                ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 45000);
                po.loadingSpinner(driver);
                po.getPlay(driver);
                ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 50000);


                //Wait for Ad to start and verify the adStarted event .
                ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);

                //Wait for Ad to complete and verify the adCompleted event .
                ev.verifyEvent("adCompleted", " Ad Playback Completed ", 55000);


                ev.verifyEvent("adStarted", " Ad Started to Play ", 55000);

                ev.verifyEvent("adCompleted", " Ad Playback Completed ", 65000);

                //Wait for video to finish and verify the playCompleted event .
                ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);

            } catch (Exception e) {
                System.out.println("FreeWheelIntegrationMultiMidroll throws Exception " + e);
                e.printStackTrace();
                ScreenshotDevice.screenshot(driver,"FreeWheelIntegrationMultiMidroll");
                Assert.assertTrue(false, "This will fail!");
            }

        }

        @org.testng.annotations.Test
        public void FreeWheelIntegrationPreMidPostroll_overlay() throws Exception {

            try {
                // Creating an Object of FreeWheelSampleApp class
                exoPlayerSampleApp po = new exoPlayerSampleApp();
                // wait till home screen of basicPlayBackApp is opened
                po.waitForAppHomeScreen(driver);


                // Assert if current activity is indeed equal to the activity name of app home screen
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.complete.MainExoPlayerActivity");
                // Wrire to console activity name of home screen app
                System.out.println("Exo Player Sample App Launched successfully. Activity :- " + driver.currentActivity() + "\n");

                //Pause the running of test for a brief time .
                Thread.sleep(3000);

                po.clickBasedOnText(driver, "Freewheel Integration");
                Thread.sleep(2000);

                // Assert if current activity is Freewheel list activity
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.lists.FreewheelListActivity");

                // Select one of the video HLS,MP4 etc .
                po.clickBasedOnText(driver, "Freewheel PreMidPost");
                Thread.sleep(2000);

                System.out.println("clicked on based text");


                //verify if player was loaded
                po.waitForPresence(driver, "className", "android.view.View");
                // Assert if current activity is indeed equal to the activity name of the video player
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");
                // Print to console output current player activity
                System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

                po.waitForPresenceOfText(driver, "h");

                //Clicking on Play button in Ooyala Skin
                po.getPlay(driver);


                //Play Started Verification
                EventVerification ev = new EventVerification();
                //Wait for video to start and verify the playStarted event .
                ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);

                ev.verifyEvent("adCompleted", " Ad Playback Completed ", 35000);


                //Wait for video to start and verify the playStarted event .
                ev.verifyEvent("playStarted", " Video Started Play ", 35000);

                po.verifyOverlay(driver);

                //Wait for Ad to start and verify the adStarted event .
                ev.verifyEvent("adStarted", " Ad Started to Play ", 50000);

                //Wait for Ad to complete and verify the adCompleted event .
                ev.verifyEvent("adCompleted", " Ad Playback Completed ", 55000);

                Thread.sleep(2000);

                po.screentapping(driver);

                po.pausingVideo(driver);

                ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 55000);

                po.seek_video(driver,100);
                ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 60000);
                po.loadingSpinner(driver);
                po.getPlay(driver);
                ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 65000);

                ev.verifyEvent("adStarted", " Ad Started to Play ", 70000);

                ev.verifyEvent("adCompleted", " Ad Playback Completed ", 75000);

                //Wait for video to finish and verify the playCompleted event .
                ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);


            } catch (Exception e) {
                System.out.println("FreeWheelIntegrationPreMidPostroll_overlay throws Exception " + e);
                e.printStackTrace();
                ScreenshotDevice.screenshot(driver,"FreeWheelIntegrationPreMidPostroll_overlay");
                Assert.assertTrue(false, "This will fail!");
            }

        }

}
