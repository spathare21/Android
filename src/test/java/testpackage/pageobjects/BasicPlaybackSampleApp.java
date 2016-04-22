package testpackage.pageobjects;

import io.appium.java_client.android.AndroidDeviceActionShortcuts;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testpackage.tests.basicplaybacksampleapp.BasicTests;
import testpackage.utils.CommandLine;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by bsondur on 11/30/15.
 */
public class BasicPlaybackSampleApp {


    /*
    Function Description :-
    Description of Input Parameters :-
    Description of the Return Value :-
    */
    public void waitForAppHomeScreen(AndroidDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.
                xpath("//android.widget.TextView[@text='4:3 Aspect Ratio']")));

    }


    public void waitForTextView(AndroidDriver driver, String text) {

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.
                xpath("//android.widget.TextView[@text='" + text + "']")));

    }


    public void assertCurrentActivityAgainst(AndroidDriver driver, String activityName) {

        Assert.assertEquals(driver.currentActivity(), activityName);
    }

    public void clickBasedOnText(AndroidDriver driver, String clickText) {

        WebElement clickTextField = driver.findElement(By.xpath("//android.widget.TextView[@text='" + clickText + "']"));
        clickTextField.click();

    }

    public void clickBasedOnTextScrollTo(AndroidDriver driver, String clickText) {

        driver.scrollTo(clickText).click();
    }

    public void waitForPresence(AndroidDriver driver, String typeOf, String waitString) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        if (typeOf == "className") {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className(waitString)));
        }

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

    public void clickImagebuttons(AndroidDriver driver, int index) {

        List<WebElement> imageButtons = driver.findElements(By.xpath("//android.widget.ImageButton"));
        imageButtons.get(index).click();
    }

    public void clickRadiobuttons(AndroidDriver driver, int index) {

        List<WebElement> radioButtons = driver.findElements(By.xpath("//android.widget.RadioButton"));
        radioButtons.get(index).click();
    }

    public boolean radioButtonChecked(AndroidDriver driver, int index) {

        List<WebElement> radioButtons = driver.findElements(By.xpath("//android.widget.RadioButton"));
        return radioButtons.get(index).isEnabled();
    }

    public void gotoFullScreen(AndroidDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        driver.findElement(By.xpath("//android.widget.ImageButton[@index=2]")).click();

    }

    public void gotoNormalScreen(AndroidDriver driver) {
        WebElement frameLayout = driver.findElement(By.id("content"));
        // System.out.println("layout" +frameLayout);
        List<WebElement> layout = frameLayout.findElements(By.className("android.widget.LinearLayout"));
        WebElement normalScreen = layout.get(0).findElement(By.className("android.widget.ImageButton"));
        normalScreen.click();


    }

    public void playInFullScreen(AndroidDriver driver) {
        WebElement layout = driver.findElement(By.xpath("//android.widget.LinearLayout[@index=1]"));
        List<WebElement> play = layout.findElements(By.className("android.widget.ImageButton"));

        play.get(1).click();

    }

    public void clickLearnMore(AndroidDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.TextView[@text='Learn More']")));
        driver.findElement(By.xpath("//android.widget.TextView[@text='Learn More']")).click();


    }

    public void playInNormalScreen(AndroidDriver driver)
    {
        WebElement element = driver.findElement(By.xpath("//android.widget.FrameLayout[@index= '0']"));
        List<WebElement> play = element.findElements(By.className("android.widget.ImageButton"));
        play.get(0).click();
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
        String command = "adb shell am start -n io.appium.unlock/.Unlock";
        String[] final_command = CommandLine.command(command);
        Runtime run = Runtime.getRuntime();
        Process pr = run.exec(final_command);
        Thread.sleep(3000);
        System.out.println("showing screen unlock");
        //driver.navigate().back();
        System.out.println("Back to Sample App screen ");
        Thread.sleep(2000);
    }

}