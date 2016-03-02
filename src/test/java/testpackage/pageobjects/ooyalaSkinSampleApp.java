package testpackage.pageobjects;

/**
 * Created by bsondur on 2/23/16.
 */

import io.appium.java_client.android.AndroidDriver;
import org.junit.Assert;
import org.omg.PortableInterceptor.AdapterNameHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
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

}
