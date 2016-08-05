package testpackage.pageobjects;

import io.appium.java_client.android.AndroidDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CompleteSampleApp {
    int[] playCoordinates = new int[2];

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
        System.out.printf("Size : " + imageButtons.size());
        if (imageButtons.size() > 0) {
            play[0] = imageButtons.get(0).getLocation().getX();
            play[1] = imageButtons.get(0).getLocation().getY();

            playCoordinates[0] = play[0] + imageButtons.get(0).getSize().getWidth() / 2;
            playCoordinates[1] = play[1] + imageButtons.get(0).getSize().getHeight() / 2;
            System.out.println("X playCoordinates" + playCoordinates[0]);
            System.out.println("Y playCoordinates" + playCoordinates[1]);
            driver.tap(1, playCoordinates[0], playCoordinates[1], 2);
        }
    }

    public void screenTap(AndroidDriver driver) throws InterruptedException {
            WebElement screentap = driver.findElementByXPath("//android.view.View");
            screentap.click();
            System.out.println("Scrubber bar is displaying after click");

    }

    public void pauseInNormalScreen(AndroidDriver driver) {
        //playButton.click();
        System.out.println("X pauseCoordinates" + playCoordinates[0]);
        System.out.println("Y pauseCoordinates" + playCoordinates[1]);
        driver.tap(1, playCoordinates[0], playCoordinates[1], 2);
    }

    public void readTime(AndroidDriver driver) {
        List<WebElement> startTime = driver.findElementsByClassName("android.widget.TextView");
        System.out.println("Size:" + startTime.size());
        if (startTime.size() > 0) {
            String startTimetext = startTime.get(1).getText();
            System.out.println("The Start time of video is:" + startTimetext);
        }
    }

    public void seekVideo(AndroidDriver driver) {
        WebElement seekBarField = driver.findElement(By.xpath("//android.widget.SeekBar"));

        int seekBarFieldWidth = seekBarField.getLocation().getX();
        int seekBarFieldHeigth = seekBarField.getLocation().getY();
        System.out.println(" Dimensions bounds value is :-" + seekBarFieldHeigth);
        System.out.println(" Dimensions bounds value is :-" + seekBarFieldWidth);
        System.out.println(" Dimensions bounds value is :-" + seekBarField.getSize().getHeight());
        System.out.println(" Dimensions bounds value is :-" + seekBarField.getSize().getWidth());
        System.out.println(" Seeking -------------------------  ");
        driver.swipe(seekBarFieldWidth + 20, seekBarFieldHeigth, seekBarFieldWidth + 100, seekBarFieldHeigth, 3);
    }

    public void loadingSpinner(AndroidDriver driver) {
        int i = 0;
        try {
            while (driver.findElement(By.className("android.widget.ProgressBar")).isDisplayed()) {
                //System.out.println("Handling Loading Spinner");
                if (i<25){
                    System.out.println("Handling Loading Spinner");
                    Thread.sleep(1000);
                    i++;
                }
                else{
                    System.out.println("Loading spinner occured more than "+i+" seconds");
                    break;
                }

            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void resumeVideoInNormalscreen(AndroidDriver driver) {
        System.out.println("X resumeCoordinates" + playCoordinates[0]);
        System.out.println("Y resumeCoordinates" + playCoordinates[1]);
        driver.tap(1, playCoordinates[0], playCoordinates[1], 2);
    }

    public void clickOnOoyalaAd(AndroidDriver driver) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("in Ooyala ad");
        driver.findElementById("com.ooyala.sample.CompleteSampleApp:id/doubleRightButton").click();
        System.out.println("clicked");
    }

    public void clickOnP1(AndroidDriver driver) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("in Video1 ad");
        driver.findElementById("com.ooyala.sample.CompleteSampleApp:id/doubleLeftButton").click();
        System.out.println("clicked");

    }

    public void clickOnP2(AndroidDriver driver) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("in Video2 ad");
        driver.findElementById("com.ooyala.sample.CompleteSampleApp:id/doubleRightButton").click();
        System.out.println("clicked");

    }

    public void clickOnVastAd(AndroidDriver driver) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("in vast ad");
        driver.findElementById("com.ooyala.sample.CompleteSampleApp:id/doubleLeftButton").click();
        System.out.println("clicked");
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

}
