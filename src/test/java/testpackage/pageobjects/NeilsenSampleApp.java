package testpackage.pageobjects;

import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Created by Sachin on 8/5/2016.
 */
public class NeilsenSampleApp {

    int[] playCoordinates= new int[2];
    final static Logger logger = Logger.getLogger(NeilsenSampleApp.class);


    public void waitForAppHomeScreen(AndroidDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.
                xpath("//android.widget.TextView[@text='ID3-Demo']")));

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
    public void playInNormalScreen(AndroidDriver driver)
    {

        int[] play = new int[2];
        List<WebElement> imageButtons = driver.findElements(By.className("android.widget.ImageButton"));
        WebElement button = imageButtons.get(0);
        Assert.assertEquals(true, button.isDisplayed());
        play[0]=imageButtons.get(0).getLocation().getX();
        play[1]=imageButtons.get(0).getLocation().getY();

        playCoordinates[0]=play[0]+imageButtons.get(0).getSize().getWidth()/2 ;
        playCoordinates[1]=play[1]+imageButtons.get(0).getSize().getHeight()/2 ;
        logger.info("X playCoordinates"+playCoordinates[0]);
        logger.info("Y playCoordinates"+playCoordinates[1]);
        driver.tap(1, playCoordinates[0] , playCoordinates[1], 2);

    }
    public void pauseInNormalScreen(AndroidDriver driver) throws InterruptedException {

        WebElement viewarea = driver.findElementByClassName("android.view.View");
        viewarea.click();
        Thread.sleep(1000);
        logger.info("X pauseCoordinates"+playCoordinates[0]);
        logger.info("Y pauseCoordinates"+playCoordinates[1]);
        driver.tap(1, playCoordinates[0] , playCoordinates[1], 2);
    }

    public void seekVideo(AndroidDriver driver){
        WebElement seekBarField = driver.findElement(By.xpath("//android.widget.SeekBar"));

        int seekBarFieldWidth = seekBarField.getLocation().getX();
        int seekBarFieldHeigth = seekBarField.getLocation().getY();
       /* logger.info(" Dimensions bounds value is :-"+seekBarFieldHeigth);
        logger.info(" Dimensions bounds value is :-"+seekBarFieldWidth);
        logger.info(" Dimensions bounds value is :-"+seekBarField.getSize().getHeight());
        logger.info(" Dimensions bounds value is :-"+seekBarField.getSize().getWidth());*/
        logger.info(" Seeking -------------------------  ");
        driver.swipe(seekBarFieldWidth + 20, seekBarFieldHeigth, seekBarFieldWidth + 100, seekBarFieldHeigth, 3);
    }

    public void resumeInNormalScreen(AndroidDriver driver){
        logger.info("X resumeCoordinates"+playCoordinates[0]);
        logger.info("Y resumerCoordinates"+playCoordinates[1]);
        driver.tap(1, playCoordinates[0] , playCoordinates[1], 2);
    }
    public void loadingSpinner(AndroidDriver driver) {
        int i = 0;
        try {
            while (driver.findElement(By.className("android.widget.ProgressBar")).isDisplayed()) {
                //logger.info("Handling Loading Spinner");
                if (i<10){
                    logger.info("Handling Loading Spinner");
                    Thread.sleep(1000);
                    i++;
                }
                else{
                    logger.info("Loading spinner occured more than "+i+" seconds");
                    break;
                }

            }
        } catch (Exception e) {
            e.getMessage();
        }
    }


}
