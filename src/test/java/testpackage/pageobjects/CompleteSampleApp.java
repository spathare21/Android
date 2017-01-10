package testpackage.pageobjects;

import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CompleteSampleApp {
    int[] playCoordinates = new int[2];
    final static Logger logger = Logger.getLogger(CompleteSampleApp.class);

    public void waitForAppHomeScreen(AndroidDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.
                xpath("//android.widget.TextView[@text='Advanced Playback']")));

    }

    public void assertCurrentActivityAgainst(AndroidDriver driver, String activityName) {

        Assert.assertEquals(driver.currentActivity(), activityName);
    }

    public void clickButtons(AndroidDriver driver, int index) {

        List<WebElement> buttons = driver.findElements(By.xpath("//android.widget.Button"));
        buttons.get(index).click();
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
        String xpath = "//android.widget.TextView[@text='" + waitString + "']";
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
    }


    public void waitForTextView(AndroidDriver driver, String text) {

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.
                xpath("//android.widget.TextView[@text='" + text + "']")));

    }

    public void playInNormalScreen(AndroidDriver driver) throws Exception {
        int[] play = new int[2];
        Thread.sleep(5000);
        List<WebElement> imageButtons = driver.findElementsByClassName("android.widget.ImageButton");
        logger.info("\nSize : " + imageButtons.size());
        if (imageButtons.size() > 0) {
            play[0] = imageButtons.get(0).getLocation().getX();
            play[1] = imageButtons.get(0).getLocation().getY();
            playCoordinates[0] = play[0] + imageButtons.get(0).getSize().getWidth() / 2;
            playCoordinates[1] = play[1] + imageButtons.get(0).getSize().getHeight() / 2;
            logger.info("X playCoordinates" + playCoordinates[0]);
            logger.info("Y playCoordinates" + playCoordinates[1]);
            driver.tap(1, playCoordinates[0], playCoordinates[1], 2);
        }
    }

    public void screenTap(AndroidDriver driver) throws InterruptedException {
            WebElement screentap = driver.findElementByXPath("//android.view.View");
            screentap.click();
            logger.info("Scrubber bar is displaying after click");

    }

    public void smallScreenTap(AndroidDriver driver, int index) throws InterruptedException{
        logger.info("Clicking on screen");
        driver.tap(1,playCoordinates[0], playCoordinates[1], 2);

    }

    public void pauseInNormalScreen(AndroidDriver driver) {
        //playButton.click();
        logger.info("X pauseCoordinates : " + playCoordinates[0]);
        logger.info("Y pauseCoordinates : " + playCoordinates[1]);
        driver.tap(1, playCoordinates[0], playCoordinates[1], 2);
        logger.info("Video paused");
    }

    public void readTime(AndroidDriver driver) {
        List<WebElement> startTime = driver.findElementsByClassName("android.widget.TextView");
        logger.info("Size:" + startTime.size());
        if (startTime.size() > 0) {
            String startTimetext = startTime.get(1).getText();
            logger.info("The Start time of video is:" + startTimetext);
        }
    }

    public void seekVideo(AndroidDriver driver) {
        WebElement seekBarField = driver.findElement(By.xpath("//android.widget.SeekBar"));

        int seekBarFieldWidth = seekBarField.getLocation().getX();
        int seekBarFieldHeigth = seekBarField.getLocation().getY();
        logger.info(" Dimensions bounds value is :-" + seekBarFieldHeigth);
        logger.info(" Dimensions bounds value is :-" + seekBarFieldWidth);
        logger.info(" Dimensions bounds value is :-" + seekBarField.getSize().getHeight());
        logger.info(" Dimensions bounds value is :-" + seekBarField.getSize().getWidth());
        logger.info(" Seeking -------------------------  ");
        driver.swipe(seekBarFieldWidth + 20, seekBarFieldHeigth, seekBarFieldWidth + 100, seekBarFieldHeigth, 3);
    }

    public void loadingSpinner(AndroidDriver driver) {
        int i = 0;
        try {
            while (driver.findElement(By.className("android.widget.ProgressBar")).isDisplayed()) {
                //logger.info("Handling Loading Spinner");
                if (i<25){
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

    public void resumeVideoInNormalscreen(AndroidDriver driver) {
        logger.info("X resumeCoordinates" + playCoordinates[0]);
        logger.info("Y resumeCoordinates" + playCoordinates[1]);
        driver.tap(1, playCoordinates[0], playCoordinates[1], 2);
    }

    public void clickOnOoyalaAd(AndroidDriver driver) throws InterruptedException {
        Thread.sleep(1000);
        logger.info("in Ooyala ad");
        driver.findElementById("com.ooyala.sample.CompleteSampleApp:id/doubleRightButton").click();
        logger.info("clicked");
    }

    public void clickOnP1(AndroidDriver driver) throws InterruptedException {
        Thread.sleep(1000);
        logger.info("in Video1 ad");
        driver.findElementById("com.ooyala.sample.CompleteSampleApp:id/doubleLeftButton").click();
        logger.info("clicked");

    }

    public void clickOnP2(AndroidDriver driver) throws InterruptedException {
        Thread.sleep(1000);
        logger.info("in Video2 ad");
        driver.findElementById("com.ooyala.sample.CompleteSampleApp:id/doubleRightButton").click();
        logger.info("clicked");

    }

    public void clickOnVastAd(AndroidDriver driver) throws InterruptedException {
        Thread.sleep(1000);
        logger.info("in vast ad");
        driver.findElementById("com.ooyala.sample.CompleteSampleApp:id/doubleLeftButton").click();
        logger.info("clicked");
    }

    public void clickOnCreateVideo(AndroidDriver driver) throws InterruptedException{
        Thread.sleep(1000);
        logger.info("Clicking on Create Video button");
        driver.findElementById("com.ooyala.sample.CompleteSampleApp:id/setButton").click();
        logger.info("Player has been created");
    }

    public void clickBasedOnTextScrollTo(AndroidDriver driver, String clickText) {

        driver.scrollTo(clickText).click();
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

    public void cuepointOff (AndroidDriver driver)
    {
        driver.findElementById("com.ooyala.sample.CompleteSampleApp:id/toggleButton1").click();
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

}
