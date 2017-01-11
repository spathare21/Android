package testpackage.pageobjects;

import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testpackage.utils.CommandLine;
import java.io.IOException;
import java.util.List;

public class FreewheelSampleApp {

    int[] playCoordinates= new int[2];
    final static Logger logger = Logger.getLogger(FreewheelSampleApp.class);

    // Wait for app home screen get open
    public void waitForAppHomeScreen(AndroidDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.
                xpath("//android.widget.TextView[@text='Freewheel Preroll']")));
    }

    //For check the app acivity
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

    // For wait to appear any text on player screen
    public void waitForPresenceOfText(AndroidDriver driver,String waitString) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        String xpath = "//android.widget.TextView[@text='" + waitString + "']";
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
    }

    // For click on any Image button
    public void clickImagebuttons(AndroidDriver driver, int index) {
        List<WebElement> imageButtons = driver.findElements(By.xpath("//android.widget.ImageButton"));
        imageButtons.get(index).click();
    }

    // For click on lean more button
    public void clickLearnMore(AndroidDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.TextView[@text='Learn More']")));
        driver.findElement(By.xpath("//android.widget.TextView[@text='Learn More']")).click();

    }

    // send the app in recent app and get back from recent app
    public void getBackFromRecentApp (AndroidDriver driver) throws InterruptedException {
        driver.sendKeyEvent(187);   //key 187 is used to go on recent app
        logger.info("key sent");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//android.view.View[@index= '0']")).click();  // here clicking on system ui to get back the sample app
        logger.info("back to SDK");
    }

    // For lock the screen and unlock
    public void powerKeyClick (AndroidDriver driver) throws InterruptedException, IOException {
        driver.sendKeyEvent(26);            // key 26 is used to lock the screen
        logger.info("key sent");
        logger.info("screen lock");
        Thread.sleep(5000);
        driver.sendKeyEvent(82);            // key 82 is used to unlock the screen
        String command = "adb shell input keyevent KEYCODE_WAKEUP";
        String[] final_command = CommandLine.command(command);
        Runtime run = Runtime.getRuntime();
        Process pr = run.exec(final_command);
        Thread.sleep(3000);
        logger.info("showing screen unlock");
        logger.info("Back to Sample App screen ");
        Thread.sleep(2000);
    }

    // Specific DeepDive Code
    public void videoPlay (AndroidDriver driver) {
        driver.findElement(By.xpath("//android.widget.ImageButton[@index ='0']")).click();
    }

    public void verifyOverlay (AndroidDriver driver) {
        WebElement element = driver.findElement(By.xpath("//android.view.View[@index = '0']"));
        if (element.isDisplayed())
        {
            logger.info("overlay displayed");
        }
        Assert.assertTrue(element.isDisplayed());
        logger.info("Element is displayed Found");
    }

    //For ad Control asset
    public void cuepointOff (AndroidDriver driver) {
        driver.findElement(By.id("com.ooyala.sample.FreewheelSampleApp:id/toggleButton1")).click();
        logger.info("cue point off");
    }

    // For AdControl asset
    public void adControlOff(AndroidDriver driver) {
        driver.findElement(By.id("com.ooyala.sample.FreewheelSampleApp:id/toggleButton2")).click();
        logger.info("Ad controls off");
    }

    // For cut down the volume
    public void  volumeDownClick (AndroidDriver driver)throws InterruptedException, IOException {
        driver.sendKeyEvent(25);   //25 is the Keyevent used to decrease the volume
        logger.info("Volume  Decreased");
    }

    // For increase the volume
    public void volumeUpClick (AndroidDriver driver) throws  InterruptedException, IOException {
        driver.sendKeyEvent(24);     //24 is the keyevent used to increase the volume.
        logger.info("Volume Increased");

    }

    // For mute the volume
    public void volumeMute(AndroidDriver driver)  throws  InterruptedException, IOException {
        driver.sendKeyEvent(91);
        logger.info("Volume is mute");
    }

    //For start the playback and get the playback coordinates
    public void playInNormalScreen(AndroidDriver driver) {
        int[] play = new int[2];
        List<WebElement> imageButtons = driver.findElements(By.xpath("//android.widget.ImageButton"));
        play[0]=imageButtons.get(0).getLocation().getX();
        play[1]=imageButtons.get(0).getLocation().getY();
        playCoordinates[0]=play[0]+imageButtons.get(0).getSize().getWidth()/2 ;
        playCoordinates[1]=play[1]+imageButtons.get(0).getSize().getHeight()/2 ;
        logger.info("X playCoordinates"+playCoordinates[0]);
        logger.info("Y playCoordinates"+playCoordinates[1]);
        driver.tap(1, playCoordinates[0] , playCoordinates[1], 2);
    }

    // For pause the video
    public void pauseInNormalScreen(AndroidDriver driver) throws InterruptedException {
        Thread.sleep(2000);
        logger.info("X pauseCoordinates"+playCoordinates[0]);
        logger.info("Y pauseCoordinates"+playCoordinates[1]);
        driver.tap(1, playCoordinates[0] , playCoordinates[1], 2);
        Thread.sleep(1000);
        driver.tap(1, playCoordinates[0] , playCoordinates[1], 2);
    }

    // For resume the playback after pause
    public void resumeInNormalScreen(AndroidDriver driver){
        logger.info("X resumeCoordinates"+playCoordinates[0]);
        logger.info("Y resumeCoordinates"+playCoordinates[1]);
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

    // For handle the loading spinner
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

    //For read the time on player
    public void readTime(AndroidDriver driver) {
        List<WebElement> startTime = driver.findElementsByClassName("android.widget.TextView");
        logger.info("Size:" + startTime.size());
        if (startTime.size() > 0) {
            String startTimetext = startTime.get(1).getText();
            logger.info("The Start time of video is:" + startTimetext);
        }
    }

    // For wait any specific text
    public void waitForTextView(AndroidDriver driver, String text) {

        WebDriverWait wait = new WebDriverWait(driver, 50);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.
                xpath("//android.widget.TextView[@text='" + text + "']")));

    }

    //For tap on the playerscreen
    public void screenTap(AndroidDriver driver){
        // Click on the web area so that player screen shows up
        WebElement viewarea = driver.findElementByClassName("android.view.View");
        viewarea.click();
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
