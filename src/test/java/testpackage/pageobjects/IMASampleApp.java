package testpackage.pageobjects;

/**
 * Created by dulari on 3/14/16.
 */

import io.appium.java_client.android.AndroidDriver;
import org.junit.Assert;
import org.omg.PortableInterceptor.AdapterNameHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testpackage.utils.CommandLine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;


public class IMASampleApp {

    //WebElement playButton;
    int[] playCoordinates= new int[2];

    public void waitForAppHomeScreen(AndroidDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.
                xpath("//android.widget.TextView[@text='IMA Ad-Rules Preroll']")));

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

    public void clickLearnMore(AndroidDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.TextView[@text='Learn More']")));
        driver.findElement(By.xpath("//android.widget.TextView[@text='Learn More']")).click();

    }
    public void adPause (AndroidDriver driver)
    {
        driver.findElement(By.id("android:id/pause")).click();
    }

    public void adPlay (AndroidDriver driver)
    {
        driver.findElement(By.id("android:id/pause")).click();
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


    public void videoPlay (AndroidDriver driver)
    {
        driver.findElement(By.xpath("//android.widget.ImageButton[@index ='0']")).click();
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

    public  void click_LearnMore(AndroidDriver driver)
    {
        String l_xpath = "//android.view.View[@index='0']/parend::android.webkit.WebView[@index='0']";
        WebElement ele = driver.findElement(By.xpath(l_xpath));
        ele.click();
    }

//   // public void ad_Play(AndroidDriver driver)
//    {
//        driver.findE
//    }

    public void skip_Button(AndroidDriver driver)
    {
        WebDriverWait wait = new WebDriverWait(driver,30);
        System.out.println("in skip Ad");
        WebElement ele = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//android.widget.Button[@content-desc='Skip Ad']"))));
        System.out.println("skip button displayed");
        driver.tap(1,0,725,2);
        ele.click();
    }

    public void playInNormalScreen(AndroidDriver driver)
    {
      /*  WebElement element = driver.findElement(By.xpath("//android.widget.FrameLayout[@index= '0']"));
        List<WebElement> play = element.findElements(By.className("android.widget.ImageButton"));
        playButton = play.get(0);
        System.out.println("Play:"+playButton);
        playButton.click();*/
        int[] play = new int[2];
        List<WebElement> imageButtons = driver.findElements(By.xpath("//android.widget.ImageButton"));
        WebElement button = imageButtons.get(0);
        Assert.assertEquals(true, button.isDisplayed());
        play[0]=imageButtons.get(0).getLocation().getX();
        play[1]=imageButtons.get(0).getLocation().getY();

        playCoordinates[0]=play[0]+imageButtons.get(0).getSize().getWidth()/2 ;
        playCoordinates[1]=play[1]+imageButtons.get(0).getSize().getHeight()/2 ;
        System.out.println("X playCoordinates"+playCoordinates[0]);
        System.out.println("Y playCoordinates"+playCoordinates[1]);
        driver.tap(1, playCoordinates[0] , playCoordinates[1], 2);

    }

    public void pauseInNormalScreen(AndroidDriver driver) throws InterruptedException {
        //playButton.click();
        // Click on the web area so that player screen shows up
        WebElement viewarea = driver.findElementByClassName("android.view.View");
        viewarea.click();
        Thread.sleep(1000);
        System.out.println("X pauseCoordinates"+playCoordinates[0]);
        System.out.println("Y pauseCoordinates"+playCoordinates[1]);
        driver.tap(1, playCoordinates[0] , playCoordinates[1], 2);
    }

    public void seekVideo(AndroidDriver driver){
        WebElement seekBarField = driver.findElement(By.xpath("//android.widget.SeekBar"));

        int seekBarFieldWidth = seekBarField.getLocation().getX();
        int seekBarFieldHeigth = seekBarField.getLocation().getY();
        System.out.println(" Dimensions bounds value is :-"+seekBarFieldHeigth);
        System.out.println(" Dimensions bounds value is :-"+seekBarFieldWidth);
        System.out.println(" Dimensions bounds value is :-"+seekBarField.getSize().getHeight());
        System.out.println(" Dimensions bounds value is :-"+seekBarField.getSize().getWidth());
        System.out.println(" Seeking -------------------------  ");
        driver.swipe(seekBarFieldWidth + 20, seekBarFieldHeigth, seekBarFieldWidth + 100, seekBarFieldHeigth, 3);
    }

}
