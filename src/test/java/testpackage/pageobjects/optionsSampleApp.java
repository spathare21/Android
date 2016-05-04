package testpackage.pageobjects;

import io.appium.java_client.android.AndroidDriver;
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
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name(waitString)));
    }

    public void clickImagebuttons(AndroidDriver driver, int index) {

        List<WebElement> imageButtons = driver.findElements(By.xpath("//android.widget.ImageButton"));
        imageButtons.get(index).click();
    }



    public void clickAlignBottom (AndroidDriver driver) throws InterruptedException, IOException {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        driver.findElements(By.name("Align Bottom"));
        driver.findElement(By.name("Align Bottom")).click();
        Thread.sleep(2000);

        String alignTopString =  driver.findElement(By.name("Align Top")).getText();
        System.out.println(alignTopString);

        if(alignTopString=="Align Top"){
            System.out.println("Align Top Button Found");
        }
    }

    public void clickAlignRight (AndroidDriver driver) throws InterruptedException, IOException {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        driver.findElements(By.name("Align Right"));
        driver.findElement(By.name("Align Right")).click();
        Thread.sleep(2000);

        String alignTopString =  driver.findElement(By.name("Align Left")).getText();
        System.out.println(alignTopString);

        if(alignTopString=="Align Left"){
            System.out.println("Align Left Button Found");
        }
    }

    public void clickAlignTop (AndroidDriver driver) throws InterruptedException, IOException {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        driver.findElements(By.name("Align Top"));
        driver.findElement(By.name("Align Top")).click();
        Thread.sleep(2000);

        String alignTopString =  driver.findElement(By.name("Align Bottom")).getText();
        System.out.println(alignTopString);

        if(alignTopString=="Align Bottom"){
            System.out.println("Align Left Button Found");
        }
    }

    public void clickOnPreloadOn (AndroidDriver driver) throws InterruptedException, IOException {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        driver.findElements(By.name("Preload On"));
        driver.findElement(By.name("Preload On")).click();
        Thread.sleep(2000);

        String preloadOnString =  driver.findElement(By.name("Preload Off")).getText();
        System.out.println(preloadOnString);

        if(preloadOnString=="Preload Off"){
            System.out.println("Preload Off Button Found");
        }
    }

    public void clickOnPromoImgOn (AndroidDriver driver) throws InterruptedException, IOException {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        driver.findElements(By.name("Show PromoImage On"));
        driver.findElement(By.name("Show PromoImage On")).click();
        Thread.sleep(2000);

        String promoImageonString =  driver.findElement(By.name("Show PromoImage Off")).getText();
        System.out.println(promoImageonString);

        if(promoImageonString=="Show PromoImage Off"){
            System.out.println("Show PromoImage Off Button Found");
        }
    }

    public void clickOnPreloadOff (AndroidDriver driver) throws InterruptedException, IOException {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        driver.findElements(By.name("Preload Off"));
        driver.findElement(By.name("Preload Off")).click();
        Thread.sleep(2000);

        String preloadOffString =  driver.findElement(By.name("Preload On")).getText();
        System.out.println(preloadOffString);

        if(preloadOffString=="Preload On"){
            System.out.println("Preload On Button Found");
        }
    }

    public void clickOnViewarea(AndroidDriver driver)
    {
        WebDriverWait wait = new WebDriverWait(driver,30);
        String viewxpath = "//android.widget.TextView[@text='Learn More']/parent::android.widget.RelativeLayout/following-sibling::android.view.View";
        WebElement web = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(viewxpath)));
        // WebElement web = driver.findElement(By.xpath(viewxpath));

        // List<WebElement> view =  driver.findElements(By.className("android.view.View"));
        //System.out.println(">>>>>>>>>" +view);

        web.click();
    }

    public void cuepointOff (AndroidDriver driver)
    {
        driver.findElement(By.id("com.ooyala.sample.OptionsSampleApp:id/toggleButton1")).click();
        System.out.println("cue point off");
    }

    public void adControlOff    (AndroidDriver driver)
    {
        driver.findElement(By.id("com.ooyala.sample.OptionsSampleApp:id/toggleButton2")).click();
        System.out.println("Ad controls off");
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
        System.out.println("key sent");
        System.out.println("screen lock");
        Thread.sleep(5000);
        //driver.sendKeyEvent(82);            // key 82 is used to unlock the screen
        String command = "adb shell am start -n io.appium.unlock/.Unlock";
        String[] final_command = CommandLine.command(command);
        Runtime run = Runtime.getRuntime();
        Process pr = run.exec(final_command);
        Thread.sleep(3000);
        System.out.println("showing screen unlock");
        driver.navigate().back();
        System.out.println("Back to Sample App screen ");
        Thread.sleep(2000);
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
        System.out.println("showing recent app screen");
        driver.findElement(By.xpath("//android.view.View[@index= '0']")).click();  // here clicking on system ui to get back the sample app
        System.out.println("back to SDK");
    }
}
