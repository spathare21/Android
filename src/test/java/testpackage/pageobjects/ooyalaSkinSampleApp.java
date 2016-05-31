package testpackage.pageobjects;

/**
 * Created by bsondur on 2/23/16.
 */

import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.apache.xpath.operations.And;
import org.junit.Assert;
import org.omg.PortableInterceptor.AdapterNameHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testpackage.utils.CommandLine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;

public class ooyalaSkinSampleApp {

    Point replay, more, close_button, share_asset, discovery_button, cc_button, volume_button, enablecc_button, play;

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
        System.out.println(" X coordinate of the Text " + clickTextField.getLocation().getX());
        System.out.println(" Y coordinate of the Text" + clickTextField.getLocation().getY());
        return loc;
    }

    public void SeekOoyalaSkin(AndroidDriver driver, int widthOffSet1, int widthOffSet2) {

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
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name(waitString)));
    }

    public void getXYSeekBarAndSeek(AndroidDriver driver, int widthOffSet1, int widthOffSet2) {
        WebElement seekBarField = driver.findElement(By.xpath("//android.widget.SeekBar"));

        int seekBarFieldWidth = seekBarField.getLocation().getX();
        int seekBarFieldHeigth = seekBarField.getLocation().getY();
        //System.out.println(" Dimensions bounds value is :-"+seekBarFieldHeigth);
        //System.out.println(" Dimensions bounds value is :-"+seekBarFieldWidth);
        System.out.println(" Seeking -------------------------  ");
        driver.swipe(seekBarFieldWidth + widthOffSet1, seekBarFieldHeigth, seekBarFieldWidth + widthOffSet2, seekBarFieldHeigth, 3);
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

        System.out.println("clicking on screen");

        driver.tap(1, 0, 75, 2);
        System.out.println("tapped");

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
        WebElement volume = driver.findElementByXPath("//android.widget.TextView[@text='b']");
        volume_button = volume.getLocation();
        System.out.printf("volume button's X  cordinates" + volume_button.getX());
        System.out.printf(" volume button's y  cordinates" + volume_button.getY());

        Thread.sleep(1000);

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



    public  void pauseVideo(AndroidDriver driver) throws InterruptedException {
        System.out.println("moved to pause method");

        System.out.println("replay.x value is "+replay.getX());
        System.out.println("replay.x value is "+replay.getY());
        driver.tap(1,replay.getX(),replay.getY(),2);
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
        System.out.printf("more button's y  cordinates" + more.getY());
        driver.tap(1,more.getX(),more.getY()+54,2);

    }

    public void  clickOnCloseButton (AndroidDriver driver) throws InterruptedException {

         //driver.findElement(By.xpath("//android.widget.TextView[@text='e']")).click();
        Thread.sleep(2000);
        driver.tap(1,close_button.getX(),close_button.getY()+54,2);
   }

    public void shareAsset (AndroidDriver driver) throws InterruptedException {
        Thread.sleep(2000);
       // driver.findElementByXPath("//android.widget.TextView[@text='o']");
        driver.tap(1,share_asset.getX(),share_asset.getY(),2);

    }

    public void clickOnDiscovery(AndroidDriver driver) throws InterruptedException {
        Thread.sleep(2000);
        //driver.findElementByXPath("//android.widget.TextView[@text='l']");
        driver.tap(1,discovery_button.getX(),discovery_button.getY(),2);
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
        Thread.sleep(2000);
        driver.tap(1, 450, 867, 2);
    }

    public void seek_video (AndroidDriver driver)
    {
      WebElement element=   driver.findElement(By.xpath("android.widget.TextView[@text='\uF111']"));
        System.out.println("element>>>>>>>>>>>>>>>>>>>>>>>"+element);

    }

    public void getBackFromRecentApp (AndroidDriver driver) throws InterruptedException, IOException {

        String command = "adb shell input keyevent KEYCODE_APP_SWITCH";
        String[] final_command = CommandLine.command(command);
        Runtime run = Runtime.getRuntime();
        Process pr = run.exec(final_command);
        Thread.sleep(3000);
        System.out.println("showing recent app screen");
        driver.findElement(By.xpath("//android.view.View[@index= '0']")).click();  // here clicking on system ui to get back the sample app
        System.out.println("back to SDK");
    }

    public void powerKeyClick (AndroidDriver driver) throws InterruptedException,IOException {

        driver.sendKeyEvent(26);            // key 26 is used to lock the screen
        System.out.println("key sent");
        System.out.println("screen lock");
        Thread.sleep(5000);
        driver.sendKeyEvent(82);            // key 82 is used to unlock the screen
        String command = "adb shell input keyevent KEYCODE_WAKEUP";
        String[] final_command = CommandLine.command(command);
        Runtime run = Runtime.getRuntime();
        Process pr = run.exec(final_command);
        Thread.sleep(3000);
        System.out.println("showing screen unlock");
        System.out.println("Back to Sample App screen ");
        Thread.sleep(2000);
    }

    public void screentap(AndroidDriver driver) throws InterruptedException {
        System.out.println("in screen tapped method");
        Thread.sleep(1000);
        driver.tap(1,replay.getX(),replay.getY(),2);
        System.out.println("out of the screen tapped method");
    }

    public void overlay (AndroidDriver driver)
    {
        System.out.println("in overlay method");
        WebElement ele = driver.findElement(By.xpath("//android.view.View[@content-desc='1?s=g002&n=380912%3B380912&t=1461829324669617003&f=&r=380912&adid=6772707&reid=3129141&arid=0&auid=&cn=defaultClick&et=c&_cc=&tpos=0&sr=0&cr=']"));
        if (ele.isDisplayed())
        {
            System.out.println("overlay displayed");

        }
        else
            System.out.println("not diplayed failed ");
    }

    public void discoverUpNext (AndroidDriver driver)
    {
       //System.out.println("Discovery up next banner displayed");
        List<WebElement> list = driver.findElementsByXPath("//android.widget.FrameLayout[@resource-id='com.ooyala.sample.SkinCompleteSampleApp:id/ooyalaSkin']");
        List<WebElement> list1 = list.get(0).findElements(By.className("android.view.View"));
        if (list1.get(2).isDisplayed())
        {
            System.out.println("Discovery is displayed");

        }
        else
            System.out.println("not displayed failed ");
    }

    public void getPlay (AndroidDriver driver){
        WebDriverWait wait = new WebDriverWait(driver,30);
        String path  = "//android.widget.TextView[@text='h']";

        WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
        play= ele.getLocation();
        System.out.println("play.x value is " + play.getX());
        System.out.println("play.y value is " + play.getY());

    }
}

