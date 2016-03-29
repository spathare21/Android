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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;

public class ooyalaSkinSampleApp {

    public void waitForAppHomeScreen(AndroidDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.
                xpath("//android.widget.TextView[@text='Skin Playback']")));

    }

    public void assertCurrentActivityAgainst(AndroidDriver driver, String activityName) {

        Assert.assertEquals(driver.currentActivity(), activityName);
    }

    public int[] locationTextOnScreen(AndroidDriver driver, String clickText){
        int[] loc= new int[2];
        WebElement clickTextField = driver.findElement(By.xpath("//android.widget.TextView[@text='" + clickText + "']"));
        loc[0]=clickTextField.getLocation().getX();
        loc[1]=clickTextField.getLocation().getY();
        System.out.println(" X coordinate of the Text "+clickTextField.getLocation().getX());
        System.out.println(" Y coordinate of the Text"+clickTextField.getLocation().getY());
        return loc;
    }

    public void SeekOoyalaSkin(AndroidDriver driver,int widthOffSet1, int widthOffSet2){

        //List<WebElement> viewGroups = driver.findElements(By.xpath("//android.view.ViewGroup"));
        List<WebElement> viewGroups = driver.findElements(By.xpath("//android.view.View"));
        System.out.println("Loc of Seek Bar Cue Point - X "+ viewGroups.get(7).getLocation().getX());
        System.out.println("Loc of Seek Bar Cue Point - Y "+ viewGroups.get(7).getLocation().getY());

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

    public void waitForPresenceOfText(AndroidDriver driver,String waitString) {
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

    public void replayVideo (AndroidDriver driver)
    {
        WebDriverWait wait = new WebDriverWait(driver,30);
        String path  = "//android.widget.TextView[@text='c']";

       WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
        Point replay= ele.getLocation();
       // replay.y
        ele.click();
    }

    public void clickThrough(AndroidDriver driver) throws InterruptedException {
        driver.findElement(By.xpath("//android.view.View[@index='2']")).click();
        Thread.sleep(5000);
        driver.navigate().back();

        System.out.println();

    }

    public void learnMore(AndroidDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver,30);
        String path  = "//android.widget.TextView[@text='Learn More']";
        WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
        System.out.println("Learn more displayed");
        //ele.click();
    }


    public  void pauseVideo(AndroidDriver driver) throws InterruptedException {
        System.out.println("moved to pause method");


       //  public void checkelement(int timeout 300) {
         //WebElement web = driver.findElementByXPath("//android.widget.TextView[@text='g']");
        //long time = 1000
        int i = 0;
        while (i < 10) {
            try
            {
                driver.tap(1,0,652,2);
                Thread.sleep(500);
                System.out.println("in try block");
               WebElement web = driver.findElementByXPath("//android.widget.TextView");
                //driver.tap(1,450,867,1);
               System.out.println("element displayed : " + web.getLocation());
              // web.click();
               driver.tap(1,480,860,2);
                System.out.println("clicked pause");
                break;
              //  System.out.println("element displayed" + foo);

            }
            catch (Exception e){
             System.out.println("Waiting for element");
             i++;
            }
        }



//        JavascriptExecutor js = (JavascriptExecutor) driver ;
//        js.executeScript("arguments[0].click();", web);
//        //String g = "//android.widget.FrameLayout"

    }


    public void moreButton(AndroidDriver driver)
    {
        String more_button = "//android.view.View[@index='7']";
       WebElement ele = driver.findElement(By.xpath(more_button));
        WebElement button = ele.findElement(By.xpath("//android.widget.TextView[@text='f']"));
        button.click();

    }

    public void  clickOnCloseButton (AndroidDriver driver)
    {

        driver.findElement(By.xpath("//android.widget.TextView[@text='i']")).click();
    }
}
