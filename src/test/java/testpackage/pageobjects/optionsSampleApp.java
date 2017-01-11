package testpackage.pageobjects;

import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testpackage.utils.CommandLine;
import testpackage.utils.EventVerification;

import java.io.IOException;
import java.util.List;

/**
 * Created by Sachin on 4/11/2016.
 */
public class optionsSampleApp {
    final static Logger logger = Logger.getLogger(optionsSampleApp.class);

    public void waitForAppHomeScreen(AndroidDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.
                xpath("//android.widget.TextView[@text='CuePoints and AdsControl Options']")));

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

    public void waitForPresenceOfText(AndroidDriver driver,String waitString) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        String xpath = "//android.widget.TextView[@text='" + waitString + "']";
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
    }

    public void clickImagebuttons(AndroidDriver driver, int index) {

        List<WebElement> imageButtons = driver.findElements(By.xpath("//android.widget.ImageButton"));
        imageButtons.get(index).click();
    }

    public void pauseSmallPlayer (AndroidDriver driver) throws InterruptedException {
        Thread.sleep(2000);
        logger.info("Pausing the Video");
        //Pausing Video
        String dimensions = driver.manage().window().getSize().toString();
        String[] dimensionsarray=dimensions.split(",");
        int length = dimensionsarray[1].length();
        String ydimensions=dimensionsarray[1].substring(0,length-1);
        String ydimensionstrimmed=ydimensions.trim();
        int ydimensionsInt= Integer.parseInt(ydimensionstrimmed);
        driver.tap(1, 90 , (ydimensionsInt-718), 2);
    }

    public void clickOnCuePointsOn (AndroidDriver driver) throws InterruptedException, IOException {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        driver.findElements(By.name("CuePoints On"));
        driver.findElement(By.name("CuePoints On")).click();
        Thread.sleep(2000);

        String alignTopString =  driver.findElement(By.name("CuePoints Off")).getText();
        logger.info(alignTopString);

        if(alignTopString=="CuePoints Off"){
            logger.info("CuePoints Off Button Found");
        }
    }

    public void clickOnAdsControlsOn (AndroidDriver driver) throws InterruptedException, IOException {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        driver.findElements(By.name("AdsControls On"));
        driver.findElement(By.name("AdsControls On")).click();
        Thread.sleep(2000);

        String alignTopString =  driver.findElement(By.name("AdsControls Off")).getText();
        logger.info(alignTopString);

        if(alignTopString=="AdControls Off"){
            logger.info("AdsControls Off Button Found");
        }
    }

    public void clickOnCuePointsOff (AndroidDriver driver) throws InterruptedException, IOException {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        driver.findElements(By.name("CuePoints Off"));
        driver.findElement(By.name("CuePoints Off")).click();
        Thread.sleep(2000);

        String alignTopString =  driver.findElement(By.name("CuePoints On")).getText();
        logger.info(alignTopString);

        if(alignTopString=="CuePoints On"){
            logger.info("CuePoints On Button Found");
        }
    }

    public void clickAlignBottom (AndroidDriver driver) throws InterruptedException, IOException {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        driver.findElements(By.name("Align Bottom"));
        driver.findElement(By.name("Align Bottom")).click();
        Thread.sleep(2000);

        String alignTopString =  driver.findElement(By.name("Align Top")).getText();
        logger.info(alignTopString);

        if(alignTopString=="Align Top"){
            logger.info("Align Top Button Found");
        }
    }

    public void clickAlignRight (AndroidDriver driver) throws InterruptedException, IOException {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        driver.findElements(By.name("Align Right"));
        driver.findElement(By.name("Align Right")).click();
        Thread.sleep(2000);

        String alignTopString =  driver.findElement(By.name("Align Left")).getText();
        logger.info(alignTopString);

        if(alignTopString=="Align Left"){
            logger.info("Align Left Button Found");
        }
    }

    public void clickAlignTop (AndroidDriver driver) throws InterruptedException, IOException {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        driver.findElements(By.name("Align Top"));
        driver.findElement(By.name("Align Top")).click();
        Thread.sleep(2000);

        String alignTopString =  driver.findElement(By.name("Align Bottom")).getText();
        logger.info(alignTopString);

        if(alignTopString=="Align Bottom"){
            logger.info("Align Left Button Found");
        }
    }

    public void clickOnPreloadOn (AndroidDriver driver) throws InterruptedException, IOException {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        driver.findElements(By.name("Preload On"));
        driver.findElement(By.name("Preload On")).click();
        Thread.sleep(2000);

        String preloadOnString =  driver.findElement(By.name("Preload Off")).getText();
        logger.info(preloadOnString);

        if(preloadOnString=="Preload Off"){
            logger.info("Preload Off Button Found");
        }
    }

    public void clickOnPromoImgOn (AndroidDriver driver) throws InterruptedException, IOException {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        driver.findElements(By.name("Show PromoImage On"));
        driver.findElement(By.name("Show PromoImage On")).click();
        Thread.sleep(2000);

        String promoImageonString =  driver.findElement(By.name("Show PromoImage Off")).getText();
        logger.info(promoImageonString);

        if(promoImageonString=="Show PromoImage Off"){
            logger.info("Show PromoImage Off Button Found");
        }
    }

    public void clickOnPreloadOff (AndroidDriver driver) throws InterruptedException, IOException {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        driver.findElements(By.name("Preload Off"));
        driver.findElement(By.name("Preload Off")).click();
        Thread.sleep(2000);

        String preloadOffString =  driver.findElement(By.name("Preload On")).getText();
        logger.info(preloadOffString);

        if(preloadOffString=="Preload On"){
            logger.info("Preload On Button Found");
        }
    }

    public void clickOnViewarea(AndroidDriver driver)
    {
        WebDriverWait wait = new WebDriverWait(driver,30);
        String viewxpath = "//android.widget.TextView[@text='Learn More']/parent::android.widget.RelativeLayout/following-sibling::android.view.View";
        WebElement web = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(viewxpath)));
        // WebElement web = driver.findElement(By.xpath(viewxpath));

        // List<WebElement> view =  driver.findElements(By.className("android.view.View"));
        //logger.info(">>>>>>>>>" +view);

        web.click();
    }

    public void cuepointOff (AndroidDriver driver)
    {
        driver.findElement(By.id("com.ooyala.sample.OptionsSampleApp:id/toggleButton1")).click();
        logger.info("cue point off");
    }

    public void adControlOff    (AndroidDriver driver)
    {
        driver.findElement(By.id("com.ooyala.sample.OptionsSampleApp:id/toggleButton2")).click();
        logger.info("Ad controls off");
    }

    public void adPause (AndroidDriver driver)
    {
        driver.findElement(By.id("android:id/pause")).click();
    }

    public void adPlay (AndroidDriver driver)
    {
        driver.findElement(By.id("android:id/pause")).click();
    }

    public void powerKeyClick (AndroidDriver driver) throws InterruptedException, IOException {

        driver.sendKeyEvent(26);            // key 26 is used to lock the screen
        logger.info("key sent");
        logger.info("screen lock");
        Thread.sleep(5000);
        //driver.sendKeyEvent(82);            // key 82 is used to unlock the screen
        String command = "adb shell am start -n io.appium.unlock/.Unlock";
        String[] final_command = CommandLine.command(command);
        Runtime run = Runtime.getRuntime();
        Process pr = run.exec(final_command);
        Thread.sleep(3000);
        logger.info("showing screen unlock");
        driver.navigate().back();
        logger.info("Back to Sample App screen ");
        Thread.sleep(2000);
    }

    public void getXYSeekBarAndSeek(AndroidDriver driver, int widthOffSet1, int widthOffSet2) {
        WebElement seekBarField = driver.findElement(By.xpath("//android.widget.SeekBar"));

        int seekBarFieldWidth = seekBarField.getLocation().getX();
        int seekBarFieldHeigth = seekBarField.getLocation().getY();
        //logger.info(" Dimensions bounds value is :-"+seekBarFieldHeigth);
        //logger.info(" Dimensions bounds value is :-"+seekBarFieldWidth);
        logger.info(" Seeking -------------------------  ");
        driver.swipe(seekBarFieldWidth + widthOffSet1, seekBarFieldHeigth, seekBarFieldWidth + widthOffSet2, seekBarFieldHeigth, 3);
    }

    public void videoPlay (AndroidDriver driver)
    {
        driver.findElement(By.xpath("//android.widget.ImageButton[@index ='0']")).click();
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
}
