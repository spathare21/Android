package testpackage.pageobjects;


import io.appium.java_client.android.AndroidDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testpackage.utils.CommandLine;

import java.io.IOException;
import java.util.List;

public class pulseSampleApp {

    Point replay,more,close_button,share_asset,discovery_button,cc_button,volume_button,enablecc_button,play, PlayEle ;
    public void waitForAppHomeScreen(AndroidDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.
                xpath("//android.widget.TextView[@text='No ads']")));

    }

    public void assertCurrentActivityAgainst(AndroidDriver driver, String activityName) {

        Assert.assertEquals(driver.currentActivity(), activityName);
    }

    public int[] locationTextOnScreen(AndroidDriver driver, String clickText) {
        int[] loc = new int[2];
        WebElement clickTextField = driver.findElement(By.xpath("//android.widget.TextView[@text='" + clickText + "']"));
        loc[0] = clickTextField.getLocation().getX();
        loc[1] = clickTextField.getLocation().getY();
        System.out.println(" X coordinate of the Text " + clickTextField.getLocation().getX());
        System.out.println(" Y coordinate of the Text" + clickTextField.getLocation().getY());
        return loc;
    }

    public void SeekOoyalaPulse(AndroidDriver driver, int widthOffSet1, int widthOffSet2) {

        //List<WebElement> viewGroups = driver.findElements(By.xpath("//android.view.ViewGroup"));
        List<WebElement> viewGroups = driver.findElements(By.xpath("//android.view.View"));
        System.out.println("Loc of Seek Bar Cue Point - X " + viewGroups.get(7).getLocation().getX());
        System.out.println("Loc of Seek Bar Cue Point - Y " + viewGroups.get(7).getLocation().getY());

        int seekBarFieldWidth = viewGroups.get(7).getLocation().getX();
        int seekBarFieldHeigth = viewGroups.get(7).getLocation().getY();
        System.out.println(" Seeking -------------------------  ");
        driver.swipe(seekBarFieldWidth + widthOffSet1, seekBarFieldHeigth, seekBarFieldWidth + widthOffSet2, seekBarFieldHeigth, 3);

    }

    public void clickBasedOnText(AndroidDriver driver, String clickText) {

        WebElement clickTextField = driver.findElement(By.xpath("//android.widget.TextView[@text='" + clickText + "']"));
        PlayEle = clickTextField.getLocation();
//        System.out.println("x cordinate is " +PlayEle.getX());
//        System.out.println("x cordinate is " +PlayEle.getY());
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
            System.out.println("number of views present are : " + views.size());
            Point p = views.get(6).getLocation();
            driver.swipe(p.getX(), p.getY(), p.getX() + time, p.getY(), 5);
        } catch (Exception e) {
            List<WebElement> views = driver.findElements(By.className("android.view.View"));
            System.out.println("number of views present are : " + views.size());
            Point p1 = views.get(6).getLocation();
            driver.swipe(p1.getX(), p1.getY(), p1.getX() + time, p1.getY(), 5);
        }
    }


    public void replayVideo(AndroidDriver driver) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        String path = "//android.widget.TextView[@text='c']";

        WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
        replay = ele.getLocation();
        System.out.println("replay.x value is " + replay.getX());
        System.out.println("replay.y value is " + replay.getY());

        Thread.sleep(2000);

        // more button location
        WebElement button = driver.findElement(By.xpath("//android.widget.TextView[@text='f']"));
        more = button.getLocation();
        System.out.printf("more button's X  cordinates" + more.getX());
        System.out.printf("more button's y  cordinates" + more.getY());
        driver.tap(1, more.getX(), more.getY(), 2);

        Thread.sleep(2000);
        // click on close button
        WebElement close = driver.findElement(By.xpath("//android.widget.TextView[@text='e']"));
        close_button = close.getLocation();
        System.out.printf("close button's X  cordinates" + close_button.getX());
        System.out.printf("close button's y  cordinates" + close_button.getY());
//       // driver.tap(1,close_button.getX(),close_button.getY(),2);

        Thread.sleep(2000);
        // shareAsset button location
        WebElement share = driver.findElement(By.xpath("//android.widget.TextView[@text='o']"));
        share_asset = share.getLocation();
        System.out.printf("share button's X  cordinates" + share_asset.getX());
        System.out.printf("share button's y  cordinates" + share_asset.getY());
        driver.tap(1, share_asset.getX(), share_asset.getY(), 2);
        Thread.sleep(5000);
        System.out.println("clicked on shared button");

        Thread.sleep(2000);

        System.out.println("clicking on back button");

        //    driver.tap(1,0,75,2);
        driver.navigate().back();
        System.out.println("Going back to option screen");

        Thread.sleep(2000);
        // Discovery button lcoation
        WebElement discovery = driver.findElementByXPath("//android.widget.TextView[@text='l']");
        discovery_button = discovery.getLocation();
        System.out.printf("discovery button's X  cordinates" + discovery_button.getX());
        System.out.printf("discovery button's y  cordinates" + discovery_button.getY());

        Thread.sleep(2000);

        //CC button location
        WebElement CC = driver.findElementByXPath("//android.widget.TextView[@text='k']");
        cc_button = CC.getLocation();
        System.out.printf("CC button's X  cordinates" + cc_button.getX());
        System.out.printf(" CC bbutton's y  cordinates" + cc_button.getY());
        driver.tap(1, cc_button.getX(), cc_button.getY(), 2);
        Thread.sleep(2000);

        //emable CC locations

        WebElement enablecc = driver.findElementByXPath("//android.widget.Switch[@index='4']");
        enablecc_button = enablecc.getLocation();
        System.out.printf("enablecc button's X  cordinates" + enablecc_button.getX());
        System.out.printf(" enablecc button's y  cordinates" + enablecc_button.getY());


        Thread.sleep(2000);
        driver.tap(1, close_button.getX(), close_button.getY(), 2);
        System.out.println("CC option closed");

        Thread.sleep(2000);
        driver.tap(1, close_button.getX(), close_button.getY(), 2);
        System.out.println("more option closed");

        Thread.sleep(2000);

        // volume button location
        boolean isElement1Present = true;

        try{
            WebElement volume = driver.findElementByXPath("//android.widget.TextView[@text='b']");
            volume_button = volume.getLocation();
            System.out.printf("volume button's X  cordinates" + volume_button.getX());
            System.out.printf(" volume button's y  cordinates" + volume_button.getY());
            Thread.sleep(1000);

        }catch (NoSuchElementException e){
            isElement1Present = false;
        }

        if(isElement1Present == false) {
            WebElement volume = driver.findElementByXPath("//android.widget.TextView[@text='p']");
            volume_button = volume.getLocation();
            System.out.printf("volume button's X  cordinates" + volume_button.getX());
            System.out.printf(" volume button's y  cordinates" + volume_button.getY());
            Thread.sleep(1000);
        }

        System.out.println("printed all the locations");

        ele.click();
    }

    public void clickThrough(AndroidDriver driver) throws InterruptedException {
        driver.findElement(By.xpath("//android.view.View[@index='2']")).click();
        Thread.sleep(5000);
        driver.navigate().back();

        System.out.println();

    }

    public void learnMore(AndroidDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        String path = "//android.widget.TextView[@text='Learn More']";
        WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
        System.out.println("Learn more displayed");
        //ele.click();
    }

    public void pauseVideo(AndroidDriver driver) throws InterruptedException {
        System.out.println("moved to pause method");
        System.out.println("play.x value is " + play.getX());
        System.out.println("play.y value is " + play.getY());
        Thread.sleep(2000);
        driver.tap(1, play.getX(), play.getY(), 5);
        Thread.sleep(2000);
        driver.tap(1,play.getX(),play.getY(),5);
        System.out.println("clicked pause");
    }


    public void moreButton(AndroidDriver driver) throws InterruptedException {
        System.out.println("in more method");
//        String more_button = "//android.view.View[@index='7']";
//       WebElement ele = driver.findElement(By.xpath(more_button));
         driver.findElement(By.xpath("//android.widget.TextView[@text='f']"));
//        System.out.println(button.getLocation());
       // button.click();
        Thread.sleep(2000);
        System.out.printf("more button's X  cordinates" +more.getX());
        System.out.printf("more button's y  cordinates" +more.getY());
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
        System.out.println("in enable CC method");
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
        System.out.println("Clicking on Play button");
        Thread.sleep(2000);
        // driver.tap(1,450,867,2);
        /*String dimensions = driver.manage().window().getSize().toString();
        String[] dimensionsarray=dimensions.split(",");
        int length = dimensionsarray[1].length();
        String ydimensions = dimensionsarray[1].substring(0,length-1);
        String ydimensionstrimmed=ydimensions.trim();
        int ydimensionsInt= Integer.parseInt(ydimensionstrimmed);
        driver.tap(1, 500 , (ydimensionsInt-821), 2);*/
        driver.tap(1, play.getX(), play.getY(), 2);
        System.out.println("Clicked on Play button");
        driver.tap(1, 450, 867, 2);
    }

    public void seek_video (AndroidDriver driver) throws Exception
    {
        System.out.println("\n---------seek video------\n");
        pauseVideo(driver);
        List<WebElement>  l = driver.findElements(By.className("android.view.View"));
        System.out.println("size of view : " + l.size());
        Point p = l.get(2).getLocation();
        System.out.println("locatation of scrubber pointer is :" + p.getX() + " " + p.getY());
        System.out.println(" Seeking -------------------------  ");
        driver.swipe(p.getX() + 20, p.getY(), p.getX() + 100, p.getY(), 3);
        driver.tap(1,658,700,2);
        getPlay(driver);
    }

    public void getBackFromRecentApp (AndroidDriver driver) throws InterruptedException, IOException {

        String command = "adb shell input keyevent KEYCODE_APP_SWITCH";
        String[] final_command = CommandLine.command(command);
        Runtime run = Runtime.getRuntime();
        Process pr = run.exec(final_command);
        Thread.sleep(2000);
        System.out.println("showing recent app screen");
        driver.findElement(By.xpath("//android.view.View[@index= '0']")).click();  // here clicking on system ui to get back the sample app
        System.out.println("back to SDK");

        /*driver.sendKeyEvent(187);   //key 187 is used to go on recent app
        System.out.println("key sent");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//android.view.View[@index= '0']")).click();  // here clicking on system ui to get back the sample app
        System.out.println("back to SDK");*/
    }



    public void powerKeyClick (AndroidDriver driver) throws InterruptedException,IOException {

        //driver.lockScreen(5);
        driver.sendKeyEvent(26);            // key 26 is used to lock the screen
        System.out.println("key sent");
        System.out.println("screen lock");
        Thread.sleep(2000);
        driver.sendKeyEvent(82);            // key 82 is used to unlock the screen
        String command = "adb shell input keyevent KEYCODE_WAKEUP";
        String[] final_command = CommandLine.command(command);
        Runtime run = Runtime.getRuntime();
        Process pr = run.exec(final_command);
        Thread.sleep(1000);
        System.out.println("showing screen unlock");
        System.out.println("Back to Sample App screen ");
        Thread.sleep(1000);
    }


    public void screentap(AndroidDriver driver) throws InterruptedException {
        //System.out.println("in screen tapped method");
        Thread.sleep(1000);
        driver.tap(1, play.getX(), play.getY(), 2);
        //System.out.println("out of the screen tapped method");
    }


    public void overlay (AndroidDriver driver) throws Exception {
        System.out.println("in overlay method");
        pauseVideo(driver);
        driver.tap(1,658,700,2);
        Thread.sleep(2000);
        WebElement ele = driver.findElementByXPath("//android.webkit.WebView[@content-desc='Web View']");
        Boolean over = false;
        over =   ele.isDisplayed();
        if (over)
        {
            System.out.println("over value is" +over);
            Assert.assertTrue(over);
            System.out.println("Overlay displayed, Assertion pass");
        }
        else
        {
            System.out.println("Overlay NOt displayed");
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
        System.out.println("Discovery displayed");
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
        System.out.println("play.x value is " + play.getX());
        System.out.println("play.y value is " + play.getY());
        Thread.sleep(1000);
        ele.click();

    }

    public void upnextDis(AndroidDriver driver) throws InterruptedException {
        boolean flag = true;
        List<WebElement> l;
        while (flag) {
            l = driver.findElementsByClassName("android.widget.ImageView");
            l.size();
            System.out.println("size of it is " + l.size());
            ;
            if (l.size() == 2) {
                //System.out.println("inside while wait.. " + l.get(1).isDisplayed());
                System.out.println("Waiting for discovery");
                flag = false;
            }
        }
        System.out.println("Up-Next Discovery displayed");
        System.out.println("Closing the discovery");
        boolean flag2 = true;

        while (driver.findElementByXPath("//android.widget.TextView[@text='e']").isDisplayed()) {
            flag2 = driver.findElementByXPath("//android.widget.TextView[@text='e']").isDisplayed();
            System.out.println("value of flag2 is " + flag2);

            driver.findElementByXPath("//android.widget.TextView[@text='e']").click();
            System.out.println("up-next discovery closed");

        }
        int n = driver.findElementsByClassName("android.widget.ImageView").size();
        System.out.println("value of n is " +n);
        if (n == 1) {

            Assert.assertTrue(flag2);
            System.out.println("AssertPass, Up-Next Discovery closed");
            flag2 = false;
        } else {
            Assert.assertTrue(flag2);
            System.out.println("Assert Failed, Up-Next discovery closed");
        }
    }



}
