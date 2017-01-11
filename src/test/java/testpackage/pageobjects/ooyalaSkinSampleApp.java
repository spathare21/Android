package testpackage.pageobjects;

/**
 * Created by bsondur on 2/23/16.
 */

import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testpackage.utils.CommandLine;
import java.io.IOException;
import java.io.InputStream;
import java.lang.Exception;
import java.util.Date;
import java.util.List;

public class ooyalaSkinSampleApp {

    Point replay,more,close_button,share_asset,discovery_button,cc_button,volume_button,enablecc_button,play, PlayEle ;
    final static Logger logger = Logger.getLogger(ooyalaSkinSampleApp.class);
    public void waitForAppHomeScreen(AndroidDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.
                xpath("//android.widget.TextView[@text='Skin Playback']")));

    }

    public void assertCurrentActivityAgainst(AndroidDriver driver, String activityName) {

        Assert.assertEquals(driver.currentActivity(), activityName);
    }

    public int[] locationTextOnScreen(AndroidDriver driver, String clickText) {
        int[] loc = new int[2];
        WebElement clickTextField = driver.findElement(By.xpath("//android.widget.TextView[@text='" + clickText + "']"));
        loc[0] = clickTextField.getLocation().getX();
        loc[1] = clickTextField.getLocation().getY();
        logger.info(" X coordinate of the Text " + clickTextField.getLocation().getX());
        logger.info(" Y coordinate of the Text" + clickTextField.getLocation().getY());
        return loc;
    }

    public void SeekOoyalaSkin(AndroidDriver driver, int widthOffSet1, int widthOffSet2) {

        //List<WebElement> viewGroups = driver.findElements(By.xpath("//android.view.ViewGroup"));
        List<WebElement> viewGroups = driver.findElements(By.xpath("//android.view.View"));
        logger.info("Loc of Seek Bar Cue Point - X " + viewGroups.get(7).getLocation().getX());
        logger.info("Loc of Seek Bar Cue Point - Y " + viewGroups.get(7).getLocation().getY());

        int seekBarFieldWidth = viewGroups.get(7).getLocation().getX();
        int seekBarFieldHeigth = viewGroups.get(7).getLocation().getY();
        logger.info(" Seeking -------------------------  ");
        driver.swipe(seekBarFieldWidth + widthOffSet1, seekBarFieldHeigth, seekBarFieldWidth + widthOffSet2, seekBarFieldHeigth, 3);

    }

    public void clickBasedOnText(AndroidDriver driver, String clickText) {

        WebElement clickTextField = driver.findElement(By.xpath("//android.widget.TextView[@text='" + clickText + "']"));
        PlayEle = clickTextField.getLocation();
//        logger.info("x cordinate is " +PlayEle.getX());
//        logger.info("x cordinate is " +PlayEle.getY());
        clickTextField.click();

    }

    public void waitForPresence(AndroidDriver driver, String typeOf, String waitString) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        if (typeOf == "className") {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className(waitString)));
        }

    }

    public void waitForPresenceOfText(AndroidDriver driver, String waitString) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        String xpath = "//android.widget.TextView[@text='" + waitString + "']";
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));

    }

    public void seek_video (AndroidDriver driver,int time) throws Exception {
        try {
            List<WebElement> views = driver.findElements(By.className("android.view.ViewGroup"));
            logger.info("number of views present are : " + views.size());
            Point p = views.get(6).getLocation();
            driver.swipe(p.getX(), p.getY(), p.getX() + time, p.getY(), 5);
        } catch (Exception e) {
            List<WebElement> views = driver.findElements(By.className("android.view.View"));
            logger.info("number of views present are : " + views.size());
//            Point p = views.get(6).getLocation();
//            driver.swipe(p.getX(), p.getY(), p.getX() + time, p.getY(), 5);
//
            Point p1 = views.get(6).getLocation();
            driver.swipe(p1.getX(), p1.getY(), p1.getX() + time, p1.getY(), 5);
        }
    }


    public void replayVideo(AndroidDriver driver) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        String path = "//android.widget.TextView[@text='c']";

        WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
        replay = ele.getLocation();
        logger.info("replay.x value is " + replay.getX());
        logger.info("replay.y value is " + replay.getY());

        Thread.sleep(2000);

        // more button location
        WebElement button = driver.findElement(By.xpath("//android.widget.TextView[@text='f']"));
        more = button.getLocation();
        logger.info("more button's X  cordinates" + more.getX());
        logger.info("more button's y  cordinates" + more.getY());
        driver.tap(1, more.getX(), more.getY(), 2);

        Thread.sleep(2000);
        // click on close button
        WebElement close = driver.findElement(By.xpath("//android.widget.TextView[@text='e']"));
        close_button = close.getLocation();
        logger.info("close button's X  cordinates" + close_button.getX());
        logger.info("close button's y  cordinates" + close_button.getY());
//       // driver.tap(1,close_button.getX(),close_button.getY(),2);

        Thread.sleep(2000);
        // shareAsset button location
        WebElement share = driver.findElement(By.xpath("//android.widget.TextView[@text='o']"));
        share_asset = share.getLocation();
        logger.info("share button's X  cordinates" + share_asset.getX());
        logger.info("share button's y  cordinates" + share_asset.getY());
        driver.tap(1, share_asset.getX(), share_asset.getY(), 2);
        Thread.sleep(5000);
        logger.info("clicked on shared button");

        Thread.sleep(2000);

        logger.info("clicking on back button");

        //    driver.tap(1,0,75,2);
        driver.navigate().back();
        logger.info("Going back to option screen");

        Thread.sleep(2000);
        // Discovery button lcoation
        WebElement discovery = driver.findElementByXPath("//android.widget.TextView[@text='l']");
        discovery_button = discovery.getLocation();
        logger.info("discovery button's X  cordinates" + discovery_button.getX());
        logger.info("discovery button's y  cordinates" + discovery_button.getY());

        Thread.sleep(2000);

        //CC button location
        WebElement CC = driver.findElementByXPath("//android.widget.TextView[@text='k']");
        cc_button = CC.getLocation();
        logger.info("CC button's X  cordinates" + cc_button.getX());
        logger.info(" CC bbutton's y  cordinates" + cc_button.getY());
        driver.tap(1, cc_button.getX(), cc_button.getY(), 2);
        Thread.sleep(2000);

        //emable CC locations

        WebElement enablecc = driver.findElementByXPath("//android.widget.Switch[@index='4']");
        enablecc_button = enablecc.getLocation();
        logger.info("enablecc button's X  cordinates" + enablecc_button.getX());
        logger.info(" enablecc button's y  cordinates" + enablecc_button.getY());


        Thread.sleep(2000);
        driver.tap(1, close_button.getX(), close_button.getY(), 2);
        logger.info("CC option closed");

        Thread.sleep(2000);
        driver.tap(1, close_button.getX(), close_button.getY(), 2);
        logger.info("more option closed");

        Thread.sleep(2000);

        // volume button location
        boolean isElement1Present = true;

        try{
            WebElement volume = driver.findElementByXPath("//android.widget.TextView[@text='b']");
            volume_button = volume.getLocation();
            logger.info("volume button's X  cordinates" + volume_button.getX());
            logger.info(" volume button's y  cordinates" + volume_button.getY());
            Thread.sleep(1000);

        }catch (org.openqa.selenium.NoSuchElementException e){
            isElement1Present = false;
        }

        if(isElement1Present == false) {
            WebElement volume = driver.findElementByXPath("//android.widget.TextView[@text='p']");
            volume_button = volume.getLocation();
            logger.info("volume button's X  cordinates" + volume_button.getX());
            logger.info(" volume button's y  cordinates" + volume_button.getY());
            Thread.sleep(1000);
        }

        logger.info("printed all the locations");

        ele.click();
    }

    public void clickThrough(AndroidDriver driver) throws InterruptedException {
        driver.findElement(By.xpath("//android.view.View[@index='2']")).click();
        Thread.sleep(5000);
        driver.navigate().back();

    }

    public void learnMore(AndroidDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        String path = "//android.widget.TextView[@text='Learn More']";
        WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
        logger.info("Learn more displayed");
        //ele.click();
    }

    public void pauseVideo(AndroidDriver driver) throws InterruptedException {
        logger.info("moved to pause method");
//        logger.info("play.x value is " + play.getX());
//        logger.info("play.y value is " + play.getY());
        Thread.sleep(800);
        driver.tap(2, play.getX(), play.getY(), 3);
//        Thread.sleep(2000);
//        driver.tap(2,play.getX(),play.getY(),3);
        logger.info("clicked pause");
    }


    public void moreButton(AndroidDriver driver) throws InterruptedException {
        logger.info("in more method");
//        String more_button = "//android.view.View[@index='7']";
//       WebElement ele = driver.findElement(By.xpath(more_button));
         driver.findElement(By.xpath("//android.widget.TextView[@text='f']"));
//        logger.info(button.getLocation());
       // button.click();
        Thread.sleep(2000);
        logger.info("more button's X  cordinates" +more.getX());
        logger.info("more button's y  cordinates" +more.getY());
        driver.tap(1, more.getX(), more.getY() + 54, 2);

    }

    public void  clickOnCloseButton (AndroidDriver driver) throws InterruptedException {

         //driver.findElement(By.xpath("//android.widget.TextView[@text='e']")).click();
        Thread.sleep(2000);
        driver.tap(1, close_button.getX(), close_button.getY() + 54, 2);
   }

    public void shareAsset (AndroidDriver driver) throws InterruptedException {
        Thread.sleep(2000);
       // driver.findElementByXPath("//android.widget.TextView[@text='o']");
        driver.tap(1, share_asset.getX(), share_asset.getY(), 2);

    }

    public void clickOnDiscovery(AndroidDriver driver) throws InterruptedException {
        Thread.sleep(2000);
        //driver.findElementByXPath("//android.widget.TextView[@text='l']");
        driver.tap(1, discovery_button.getX(), discovery_button.getY(), 2);
    }

    public void clickOnCC (AndroidDriver driver) throws InterruptedException {
        Thread.sleep(2000);
        //driver.findElementByXPath("//android.widget.TextView[@text='k']");
        driver.tap(1, cc_button.getX(), cc_button.getY(), 1);
    }

    public void volumeButton (AndroidDriver driver)
    {
        driver.findElementByXPath("//android.widget.TextView[@text='b']");

    }

    public void enableCC (AndroidDriver driver) throws InterruptedException {
        Thread.sleep(2000);
        logger.info("in enable CC method");
        driver.findElementByXPath("//android.widget.Switch[@index='4']");
        driver.tap(1, enablecc_button.getX(), enablecc_button.getY(), 2);

    }

    public void shareOnGmail (AndroidDriver driver) throws InterruptedException {
        Thread.sleep(2000);
        driver.findElementByXPath("//android.widget.TextView[@text='Gmail']").click();
        Thread.sleep(1000);
        driver.findElementById("com.google.android.gm:id/to").sendKeys("shivam.gupta@vertisinfotech.com");
        Thread.sleep(2000);
        driver.findElementById("com.google.android.gm:id/send").click();
    }

    public void playVideo (AndroidDriver driver) throws InterruptedException {
        logger.info("Clicking on Play button");
        Thread.sleep(2000);
        driver.tap(1, play.getX(), play.getY(), 2);
        logger.info("Clicked on Play button");
        driver.tap(1, 450, 867, 2);
    }

//    public void seek_video (AndroidDriver driver) throws Exception
//    {
//        logger.info("\n---------seek video------\n");
//        pauseVideo(driver);
//        List<WebElement>  l = driver.findElements(By.className("android.view.View"));
//        logger.info("size of view : " + l.size());
//        Point p = l.get(2).getLocation();
//        logger.info("locatation of scrubber pointer is :" + p.getX() + " " + p.getY());
//        logger.info(" Seeking -------------------------  ");
//        driver.swipe(p.getX() + 20, p.getY(), p.getX() + 100, p.getY(), 3);
//        driver.tap(1,658,700,2);
//        getPlay(driver);
//    }

    public void getBackFromRecentApp (AndroidDriver driver) throws InterruptedException, IOException {

        String command = "adb shell input keyevent KEYCODE_APP_SWITCH";
        String[] final_command = CommandLine.command(command);
        Runtime run = Runtime.getRuntime();
        Process pr = run.exec(final_command);
        Thread.sleep(2000);
        logger.info("showing recent app screen");
        driver.findElement(By.xpath("//android.view.View[@index= '0']")).click();  // here clicking on system ui to get back the sample app
        logger.info("back to SDK");

        /*driver.sendKeyEvent(187);   //key 187 is used to go on recent app
        logger.info("key sent");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//android.view.View[@index= '0']")).click();  // here clicking on system ui to get back the sample app
        logger.info("back to SDK");*/
    }



    public void powerKeyClick (AndroidDriver driver) throws InterruptedException,IOException {

        //driver.lockScreen(5);
        driver.sendKeyEvent(26);            // key 26 is used to lock the screen
        logger.info("key sent");
        logger.info("screen lock");
        Thread.sleep(2000);
        driver.sendKeyEvent(82);            // key 82 is used to unlock the screen
        String command = "adb shell input keyevent KEYCODE_WAKEUP";
        String[] final_command = CommandLine.command(command);
        Runtime run = Runtime.getRuntime();
        Process pr = run.exec(final_command);
        Thread.sleep(1000);
        logger.info("showing screen unlock");
        logger.info("Back to Sample App screen ");
        Thread.sleep(1000);
    }


    public void screentap(AndroidDriver driver) throws InterruptedException {
        Thread.sleep(1000);
        driver.tap(1, play.getX(), play.getY(), 2);

    }


    public void overlay (AndroidDriver driver) throws Exception {
        logger.info("in overlay method");
        pauseVideo(driver);
        driver.tap(1,658,700,2);
        Thread.sleep(2000);
        WebElement ele = driver.findElementByXPath("//android.webkit.WebView[@content-desc='Web View']");
        Boolean over = false;
        over =   ele.isDisplayed();
        if (over)
        {
            logger.info("over value is" +over);
            Assert.assertTrue(over);
            logger.info("Overlay displayed, Assertion pass");
        }
        else
        {
            logger.info("Overlay NOt displayed");
            Assert.assertTrue(over);
        }
        driver.tap(1,658,700,2);
        Thread.sleep(2000);
        getPlay(driver);
    }

    public void discoverElement (AndroidDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver,30);
        String path  = "//android.widget.TextView[@text='Discovery']";
        WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
        logger.info("Discovery displayed");
        //ele.click();
    }

    public void clickBasedOnTextScrollTo(AndroidDriver driver, String clickText) {

        driver.scrollTo(clickText).click();
    }

    public void getPlay (AndroidDriver driver) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver,30);
        String path  = "//android.widget.TextView[@text='h']";
        WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
        play= ele.getLocation();
        logger.info("play.x value is " + play.getX());
        logger.info("play.y value is " + play.getY());
        Thread.sleep(2000);
        driver.tap(3,play.getX(),play.getY(),7);
        //Thread.sleep(1000);
        //driver.tap(1, play.getX(), play.getY(),5);
    }

    public void upnextDis(AndroidDriver driver) throws InterruptedException {
        boolean flag = true;
        List<WebElement> l;
        while (flag) {
            l = driver.findElementsByClassName("android.widget.ImageView");
            l.size();
            logger.info("size of it is " + l.size());
            ;
            if (l.size() == 2) {
                //logger.info("inside while wait.. " + l.get(1).isDisplayed());
                logger.info("Waiting for discovery");
                flag = false;
            }
        }
        logger.info("Up-Next Discovery displayed");
        logger.info("Closing the discovery");
        boolean flag2 = true;

        while (driver.findElementByXPath("//android.widget.TextView[@text='e']").isDisplayed()) {
            flag2 = driver.findElementByXPath("//android.widget.TextView[@text='e']").isDisplayed();
            logger.info("value of flag2 is " + flag2);

            driver.findElementByXPath("//android.widget.TextView[@text='e']").click();
            logger.info("up-next discovery closed");

        }
        int n = driver.findElementsByClassName("android.widget.ImageView").size();
        logger.info("value of n is " +n);
        if (n == 1) {

            Assert.assertTrue(flag2);
            logger.info("AssertPass, Up-Next Discovery closed");
            flag2 = false;
        } else {
            Assert.assertTrue(flag2);
            logger.info("Assert Failed, Up-Next discovery closed");
        }
    }



}

