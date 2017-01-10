package testpackage.pageobjects;

import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Logger;
import org.apache.xpath.operations.And;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testpackage.utils.CommandLine;

import java.io.IOException;
import java.util.List;

/**
 * Created by Sachin on 4/5/2016.
 */
public class exoPlayerSampleApp {
    Point replay, more, close_button, share_asset, discovery_button, cc_button, volume_button, enablecc_button,play;
    final static Logger logger = Logger.getLogger(exoPlayerSampleApp.class);

    public void waitForAppHomeScreen(AndroidDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.
                xpath("//android.widget.TextView[@text='Basic Playback']")));

    }

    public void assertCurrentActivityAgainst(AndroidDriver driver, String activityName) {

        Assert.assertEquals(driver.currentActivity(), activityName);
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

    public void clickButtons(AndroidDriver driver, int index) {

        List<WebElement> buttons = driver.findElements(By.xpath("//android.widget.Button"));
        buttons.get(index).click();
    }

    public void waitForPresenceOfText(AndroidDriver driver, String waitString) {
        WebDriverWait wait = new WebDriverWait(driver, 30);

        String xpath = "//android.widget.TextView[@text='" + waitString + "']";

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
    }

    public void verifyOverlay(AndroidDriver driver) {
        WebElement element = driver.findElement(By.xpath("//android.view.View[@index = '0']"));
        if (element.isDisplayed()) {
            logger.info("overlay displayed");
            Assert.assertEquals(true, element.isDisplayed());
        }
    }

    public void skipAd(AndroidDriver driver) throws InterruptedException {
        logger.info("in skip ad");

        driver.findElementByXPath("//android.widget.TextView[@name='Skip Ad']").click();
                // driver.tap(1,585,1524,2);
    }

    public void adPause(AndroidDriver driver) {
        driver.findElement(By.className("android.view.View")).click();
    }

    public void clickBasedOnTextScrollTo(AndroidDriver driver, String clickText) {

        driver.scrollTo(clickText).click();
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

        logger.info("Clicking on back button to close share options");
        driver.navigate().back();
        logger.info("Clicked");

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

        Thread.sleep(1000);

        logger.info("printed all the locations");

        ele.click();
    }

    public void clickThrough(AndroidDriver driver) throws InterruptedException {
        driver.findElement(By.xpath("//android.view.View[@index='2']")).click();
        Thread.sleep(5000);
        driver.navigate().back();

    }

    public void learnMore(AndroidDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver,30);
        String path  = "//android.widget.TextView[@text='Learn More']";
        WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
        logger.info("Learn more displayed");
        //ele.click();
    }

    public  void pauseVideo(AndroidDriver driver) throws InterruptedException {
        logger.info("moved to pause method");

        logger.info("replay.x value is "+replay.getX());
        logger.info("replay.x value is "+replay.getY());
        driver.tap(1,replay.getX(),replay.getY(),2);
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
        driver.tap(1,cc_button.getX(),cc_button.getY(),1);
    }

    public void volumeButton (AndroidDriver driver)
    {
        driver.findElementByXPath("//android.widget.TextView[@text='b']");

    }

    public void enableCC (AndroidDriver driver) throws InterruptedException {
        Thread.sleep(2000);
        logger.info("in enable CC method");
        driver.findElementByXPath("//android.widget.Switch[@index='4']");
        driver.tap(1,enablecc_button.getX(),enablecc_button.getY(),2);

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
        driver.tap(1,play.getX(),play.getY(),2);
    }

    public void seek_video (AndroidDriver driver)

    {
        WebElement element=   driver.findElement(By.xpath("android.widget.TextView[@text='\uF111']"));
        logger.info("element>>>>>>>>>>>>>>>>>>>>>>>"+element);

    }

    public void getBackFromRecentApp (AndroidDriver driver) throws InterruptedException, IOException {


        String command = "adb shell input keyevent KEYCODE_APP_SWITCH";
        String[] final_command = CommandLine.command(command);
        Runtime run = Runtime.getRuntime();
        Process pr = run.exec(final_command);
        Thread.sleep(3000);
        logger.info("showing recent app screen");
        driver.findElement(By.xpath("//android.view.View[@index= '0']")).click();  // here clicking on system ui to get back the sample app
        logger.info("back to SDK");
    }

    public void powerKeyClick (AndroidDriver driver) throws InterruptedException,IOException {

        driver.sendKeyEvent(26);            // key 26 is used to lock the screen
        logger.info("key sent");
        logger.info("screen lock");
        Thread.sleep(2000);
        driver.sendKeyEvent(82);            // key 82 is used to unlock the screen
        String command = "adb shell input keyevent KEYCODE_WAKEUP";
        String[] final_command = CommandLine.command(command);
        Runtime run = Runtime.getRuntime();
        Process pr = run.exec(final_command);
        Thread.sleep(3000);
        logger.info("showing screen unlock");
        logger.info("Back to Sample App screen ");
        Thread.sleep(2000);
    }

    public void screentap(AndroidDriver driver) throws InterruptedException {
        logger.info("in screen tapped method");
        Thread.sleep(1500);
        driver.tap(1,replay.getX(),replay.getY(),2);
        logger.info("out of the screen tapped method");
    }

    public void overlay (AndroidDriver driver)
    {
        logger.info("in overlay method");
        WebElement ele = driver.findElement(By.xpath("//android.view.View[@content-desc='1?s=g002&n=380912%3B380912&t=1461829324669617003&f=&r=380912&adid=6772707&reid=3129141&arid=0&auid=&cn=defaultClick&et=c&_cc=&tpos=0&sr=0&cr=']"));
        if (ele.isDisplayed())
        {
            logger.info("overlay displayed");

        }
        else
            logger.info("not diplayed failed ");
    }

    public void discoverUpNext (AndroidDriver driver) throws InterruptedException {
        //logger.info("Discovery up next banner displayed");
       //  List<WebElement> list = driver.findElementsByXPath("//android.widget.FrameLayout[@resource-id='com.ooyala.sample.SkinCompleteSampleApp:id/ooyalaSkin']");
       // List<WebElement> list1 = list.get(0).findElements(By.className("android.view.View"));
        WebDriverWait wait = new WebDriverWait(driver,80);
        String  path = "//android.widget.ImageView";
        WebElement ele =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
        Assert.assertEquals(true, ele.isDisplayed());
        if (ele.isDisplayed())
        {
            logger.info("Discovery is displayed");
            Thread.sleep(1000);
            WebElement discoveryclose = driver.findElement(By.xpath("//android.widget.TextView[@text='e']"));
            discoveryclose.click();
            logger.info("Discovery is closed");

        }
        else
            logger.info("not displayed failed ");
    }

    public void getPlay (AndroidDriver driver) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver,30);
        String path  = "//android.widget.TextView[@text='h']";
        WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
        play= ele.getLocation();
        logger.info("play.x value is " + play.getX());
        logger.info("play.y value is " + play.getY());
        Thread.sleep(1000);
        driver.tap(1, play.getX(),play.getY(),2);
    }

    public void screentapping(AndroidDriver driver) throws InterruptedException{
        logger.info("Tapping using Play coordinates");
        Thread.sleep(1000);
        driver.tap(1,play.getX(),play.getY(),2);
        logger.info("Tapped on screen");
    }

    public void pausingVideo(AndroidDriver driver) throws InterruptedException{
        logger.info("Pausing Video using play coordinate");
        Thread.sleep(1000);
        driver.tap(1,play.getX(),play.getY(),2);
        logger.info("Paused Video using play coordinates");
    }

    public void discoveryTray(AndroidDriver driver) throws InterruptedException{
        WebDriverWait wait = new WebDriverWait(driver,30);
        String path = "//android.widget.TextView[@text='Discovery']";
        WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
        logger.info("Discovery tray displayed");
        Assert.assertEquals(true, ele.isDisplayed());
        WebElement discoveryclose = driver.findElement(By.xpath("//android.widget.TextView[@text='e']"));
        discoveryclose.click();
        logger.info("Discovery tray closed");
    }
}
