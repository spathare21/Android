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
 * Created by bsondur on 8/16/16.
 */
public class chromeCastSampleApp {
    int[] playCoordinates = new int[2];
    final static Logger logger = Logger.getLogger(chromeCastSampleApp.class);

    public void waitForAppHomeScreen(AndroidDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.
                xpath("//android.widget.TextView[@text='HLS Asset']")));

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

    public void clickCastButton(AndroidDriver driver) {

        WebElement clickClassName = driver.findElement(By.xpath("//android.view.View"));
        clickClassName.click();
    }

    public void waitForTextView(AndroidDriver driver, String text) {

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.
                xpath("//android.widget.TextView[@text='" + text + "']")));

    }

    public void clickImagebuttons(AndroidDriver driver, int index) {

        List<WebElement> imageButtons = driver.findElements(By.xpath("//android.widget.Button"));
        imageButtons.get(index).click();
    }

    public void playCoordinatesInNormalScreen(AndroidDriver driver) throws Exception {
        int[] play = new int[2];
        Thread.sleep(5000);
        List<WebElement> imageButtons = driver.findElementsByClassName("android.widget.ImageButton");
        logger.info("Size : " + imageButtons.size());
        if (imageButtons.size() > 0) {
            play[0] = imageButtons.get(1).getLocation().getX();
            play[1] = imageButtons.get(1).getLocation().getY();

            playCoordinates[0] = play[0] + imageButtons.get(0).getSize().getWidth() / 2;
            playCoordinates[1] = play[1] + imageButtons.get(0).getSize().getHeight() / 2;
            logger.info("X playCoordinates" + playCoordinates[0]);
            logger.info("Y playCoordinates" + playCoordinates[1]);

        }
    }

    public void pauseInNormalScreen(AndroidDriver driver) {
        //playButton.click();
        logger.info("X pauseCoordinates" + playCoordinates[0]);
        logger.info("Y pauseCoordinates" + playCoordinates[1]);
        driver.tap(1, playCoordinates[0], playCoordinates[1], 3);
    }

    public void resumeFromPauseToPlayVideoInNormalscreen(AndroidDriver driver) {
        logger.info("X resumeCoordinates" + playCoordinates[0]);
        logger.info("Y resumeCoordinates" + playCoordinates[1]);
        driver.tap(1, playCoordinates[0], playCoordinates[1], 2);
    }

    public void screenTap(AndroidDriver driver) throws InterruptedException {
        WebElement screentap = driver.findElementByXPath("//android.view.View");
        screentap.click();
        logger.info("Scrubber bar is displaying after click");

    }

    public void seekVideo(AndroidDriver driver, int diff1 , int diff2) {
        WebElement seekBarField = driver.findElement(By.xpath("//android.widget.SeekBar"));

        int seekBarFieldWidth = seekBarField.getLocation().getX();
        int seekBarFieldHeigth = seekBarField.getLocation().getY();
        logger.info(" Dimensions bounds value is :-" + seekBarFieldHeigth);
        logger.info(" Dimensions bounds value is :-" + seekBarFieldWidth);
        logger.info(" Dimensions bounds value is :-" + seekBarField.getSize().getHeight());
        logger.info(" Dimensions bounds value is :-" + seekBarField.getSize().getWidth());
        logger.info(" Seeking -------------------------  ");
        //driver.swipe(seekBarFieldWidth + 20, seekBarFieldHeigth, seekBarFieldWidth + (seekBarField.getSize().getWidth()-100), seekBarFieldHeigth, 5);
        driver.swipe(seekBarFieldWidth + diff1, seekBarFieldHeigth, seekBarFieldWidth + (seekBarField.getSize().getWidth()-diff2), seekBarFieldHeigth, 5);
    }

    public void readTime(AndroidDriver driver) {
        List<WebElement> startTime = driver.findElementsByClassName("android.widget.TextView");
        logger.info("Size:" + startTime.size());
        if (startTime.size() > 0) {
            String startTimetext = startTime.get(4).getText();
            logger.info("The Start time of video is:" + startTimetext);
        }
    }

    public void clickRadiobuttons(AndroidDriver driver, int index) {

        List<WebElement> radioButtons = driver.findElements(By.xpath("//android.widget.RadioButton"));
        radioButtons.get(index).click();
    }

    public boolean radioButtonChecked(AndroidDriver driver, int index) {

        List<WebElement> radioButtons = driver.findElements(By.xpath("//android.widget.RadioButton"));
        return radioButtons.get(index).isEnabled();
    }

    public void clickImagebuttonsCC(AndroidDriver driver, int index) {

        List<WebElement> imageButtons = driver.findElements(By.xpath("//android.widget.ImageButton"));
        imageButtons.get(index).click();
    }
}
