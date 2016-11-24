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

        @BeforeClass
        public void beforeTest() throws Exception {
            // closing all recent app from background.
            //CloserecentApps.closeApps();
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
            driver.manage().logs().get("logcat");
            PushLogFileToDevice logpush=new PushLogFileToDevice();
            logpush.pushLogFile();
            if(driver.currentActivity()!= "com.ooyala.sample.complete.MainExoPlayerActivity") {
                driver.startActivity("com.ooyala.sample.ExoPlayerSampleApp","com.ooyala.sample.complete.MainExoPlayerActivity");
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


        @org.testng.annotations.Test
        public void FreeWheelIntegrationPreRoll() throws Exception{

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
                po.clickBasedOnText(driver, "Freewheel Preroll");
                Thread.sleep(2000);

                System.out.println("clicked on based text");


                //verify if player was loaded
                po.waitForPresence(driver, "className", "android.view.View");
                // Assert if current activity is indeed equal to the activity name of the video player
                po.assertCurrentActivityAgainst(driver, "com.ooyala.sample.players.PreconfiguredFreewheelPlayerActivity");
                // Print to console output current player activity
                System.out.println("Player Video was loaded successfully . Activity  :- " + driver.currentActivity() + "\n");

                po.waitForPresenceOfText(driver,"h");

                //Clicking on Play button in Ooyala Skin
                po.getPlay(driver);
                //Play Started Verification
                EventVerification ev = new EventVerification();
                ev.verifyEvent("adStarted", " Ad Started to Play ", 30000);
                // Ad playback has been completed.
                ev.verifyEvent("adCompleted", " Ad Playback Completed ", 30000);

                //Wait for video to start and verify the playStarted event .
                ev.verifyEvent("playStarted", " Video Started Play ", 30000);

                Thread.sleep(2000);

                po.screentapping(driver);

                po.pausingVideo(driver);

                ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 70000);

                po.seek_video(driver,100);
                ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 70000);
                po.loadingSpinner(driver);
                po.getPlay(driver);
                ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 80000);

                //Wait for video to finish and verify the playCompleted event .
                ev.verifyEvent("playCompleted", " Video Completed Play ", 70000);

            }
            catch(Exception e)
            {
                System.out.println("FreeWheelIntegrationPreRoll throws Exception "+e);
                e.printStackTrace();
                ScreenshotDevice.screenshot(driver,"FreeWheelIntegrationPreRoll");
                Assert.assertTrue(false, "This will fail!");

            }

        }

        @org.testng.annotations.Test
        public void FreeWheelIntegrationMidroll() throws Exception {

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
            po.clickBasedOnText(driver, "Freewheel Midroll");
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
            ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 50000);
            po.loadingSpinner(driver);
            po.getPlay(driver);
            ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 60000);

            ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);
            // Ad playback has been completed.
            ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

            //Wait for video to finish and verify the playCompleted event .
            ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);


        } catch (Exception e) {
            System.out.println("FreeWheelIntegrationMidroll throws Exception " + e);
            e.printStackTrace();
            ScreenshotDevice.screenshot(driver,"FreeWheelIntegrationMidroll");
            Assert.assertTrue(false, "This will fail!");

        }

    }

        @org.testng.annotations.Test
        public void FreeWheelIntegrationPostroll() throws Exception {

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
                po.clickBasedOnText(driver, "Freewheel Postroll");
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
                ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 50000);
                po.loadingSpinner(driver);
                po.getPlay(driver);
                ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 60000);

                ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);
                // Ad playback has been completed.
                ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

                //Wait for video to finish and verify the playCompleted event .
                ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);


            } catch (Exception e) {
                System.out.println("FreeWheelIntegrationPostroll throws Exception " + e);
                e.printStackTrace();
                ScreenshotDevice.screenshot(driver,"FreeWheelIntegrationPostroll");
                Assert.assertTrue(false, "This will fail!");
            }

        }

        @org.testng.annotations.Test
        public void FreeWheelIntegrationPreMidPostroll() throws Exception {

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

                ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);
                // Ad playback has been completed.
                ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

                //Wait for video to start and verify the playStarted event .
                ev.verifyEvent("playStarted", " Video Started Play ", 30000);

                Thread.sleep(2000);

                po.screentapping(driver);

                po.pausingVideo(driver);

                ev.verifyEvent("Notification Received: stateChanged - state: PAUSED", " Video paused ", 40000);

                po.seek_video(driver,100);
                ev.verifyEvent("seekCompleted", " Playing Video was Seeked " , 50000);
                po.loadingSpinner(driver);
                po.getPlay(driver);
                ev.verifyEvent("Notification Received: stateChanged - state: PLAYING","Video resumed", 60000);

                ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);
                // Ad playback has been completed.
                ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

                ev.verifyEvent("adStarted", " Ad Started to Play ", 60000);
                // Ad playback has been completed.
                ev.verifyEvent("adCompleted", " Ad Playback Completed ", 70000);

                //Wait for video to finish and verify the playCompleted event .
                ev.verifyEvent("playCompleted", " Video Completed Play ", 90000);


            } catch (Exception e) {
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
