package testpackage.pageobjects;

import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testpackage.utils.CommandLine;
import java.io.IOException;
import java.util.List;



public class IMASampleApp {

    //WebElement playButton;
    int[] playCoordinates= new int[2];
    final static Logger logger = Logger.getLogger(IMASampleApp.class);

    // Waiting for Home screen appear.
    public void waitForAppHomeScreen(AndroidDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.
                xpath("//android.widget.TextView[@text='IMA Ad-Rules Preroll']")));

    }

    // Click on test with scroll
    public void clickBasedOnTextScrollTo(AndroidDriver driver, String clickText) throws InterruptedException {
        Thread.sleep(2000);
        driver.scrollTo(clickText).click();
    }

    // Assertion for check the app activity. 
    public void assertCurrentActivityAgainst(AndroidDriver driver, String activityName) {

        Assert.assertEquals(driver.currentActivity(), activityName);
    }

    public void clickBasedOnText(AndroidDriver driver, String clickText) throws InterruptedException {

        Thread.sleep(2000);
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

    // Not in use, later on For deepdive
    public void clickImagebuttons(AndroidDriver driver, int index) {

        List<WebElement> imageButtons = driver.findElements(By.xpath("//android.widget.ImageButton"));
        imageButtons.get(index).click();
    }

    // For Deepdive,
    public void adPause (AndroidDriver driver)
    {
        driver.findElement(By.id("android:id/pause")).click();
    }

    // For Deepdive,
    public void adPlay (AndroidDriver driver)
    {
        driver.findElement(By.id("android:id/pause")).click();
    }

    // For Deepdive,
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

    // For Deepdive,
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

    // For deepdive test
    public void videoPlay (AndroidDriver driver) {
        driver.findElement(By.xpath("//android.widget.ImageButton[@index ='0']")).click();
    }

    // For Deepdive test,
    public void getXYSeekBarAndSeek(AndroidDriver driver, int widthOffSet1, int widthOffSet2) {
        WebElement seekBarField = driver.findElement(By.xpath("//android.widget.SeekBar"));
        int seekBarFieldWidth = seekBarField.getLocation().getX();
        int seekBarFieldHeigth = seekBarField.getLocation().getY();
        logger.debug(" Seeking -------------------------  ");
        driver.swipe(seekBarFieldWidth + widthOffSet1, seekBarFieldHeigth, seekBarFieldWidth + widthOffSet2, seekBarFieldHeigth, 3);
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

    // For click on skip button, for skip the adplayback
    public void skip_Button(AndroidDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver,30);
        logger.info("in skip Ad");
        WebElement ele = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//android.widget.Button[@content-desc='Skip Ad']"))));
        logger.info("skip button displayed");
        driver.tap(1,0,725,2);
        ele.click();
    }

    // Play the video in normal size player
    public void playInNormalScreen(AndroidDriver driver){
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

    // Pause the video in normal screen player
    public void pauseInNormalScreen(AndroidDriver driver) throws InterruptedException {
        WebElement viewarea = driver.findElementByClassName("android.view.View");
        viewarea.click();
        Thread.sleep(1000);
        logger.info("X pauseCoordinates"+playCoordinates[0]);
        logger.info("Y pauseCoordinates"+playCoordinates[1]);
        driver.tap(1, playCoordinates[0] , playCoordinates[1], 2);
    }

    // After pause the video, resume the playback in normal screen player
    public void resumeInNormalScreen(AndroidDriver driver){
        logger.info("X resumeCoordinates"+playCoordinates[0]);
        logger.info("Y resumerCoordinates"+playCoordinates[1]);
        driver.tap(1, playCoordinates[0] , playCoordinates[1], 2);
    }

    // For seek the video
    public void seekVideo(AndroidDriver driver){
        WebElement seekBarField = driver.findElement(By.xpath("//android.widget.SeekBar"));
        int seekBarFieldWidth = seekBarField.getLocation().getX();
        int seekBarFieldHeigth = seekBarField.getLocation().getY();
        logger.info(" Dimensions bounds value is :-"+seekBarFieldHeigth);
        logger.info(" Dimensions bounds value is :-"+seekBarFieldWidth);
        logger.info(" Dimensions bounds value is :-"+seekBarField.getSize().getHeight());
        logger.info(" Dimensions bounds value is :-"+seekBarField.getSize().getWidth());
        logger.info(" Seeking -------------------------  ");
        driver.swipe(seekBarFieldWidth + 20, seekBarFieldHeigth, seekBarFieldWidth + 100, seekBarFieldHeigth, 3);
    }

    // For handling the loading spinner
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

    public void readTime(AndroidDriver driver) {
        List<WebElement> startTime = driver.findElementsByClassName("android.widget.TextView");
        logger.info("Size:" + startTime.size());
        if (startTime.size() > 0) {
            String startTimetext = startTime.get(1).getText();
            logger.info("The Start time of video is:" + startTimetext);
        }
    }
    // For wait particular ant test
    public void waitForTextView(AndroidDriver driver, String text) {
        WebDriverWait wait = new WebDriverWait(driver, 50);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.
                xpath("//android.widget.TextView[@text='" + text + "']")));
    }

    // For seek the video for more time
    public void seekVideoForLong(AndroidDriver driver){
        WebElement seekBarField = driver.findElement(By.xpath("//android.widget.SeekBar"));
        int seekBarFieldWidth = seekBarField.getLocation().getX();
        int seekBarFieldHeigth = seekBarField.getLocation().getY();
        logger.debug(" Dimensions bounds value is :-"+seekBarFieldHeigth);
        logger.debug(" Dimensions bounds value is :-"+seekBarFieldWidth);
        logger.debug(" Dimensions bounds value is :-"+seekBarField.getSize().getHeight());
        logger.debug(" Dimensions bounds value is :-"+seekBarField.getSize().getWidth());
        logger.debug(" Seeking -------------------------  ");
        driver.swipe(seekBarFieldWidth + 20, seekBarFieldHeigth, seekBarFieldWidth + 700, seekBarFieldHeigth, 3);
    }

}
