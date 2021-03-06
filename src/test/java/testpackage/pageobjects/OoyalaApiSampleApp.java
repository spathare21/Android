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

/**
 * Created by Vertis on 11/11/16.
 */
public class OoyalaApiSampleApp {

    //WebElement playButton;
    int[] playCoordinates= new int[2];
    int[] fullscreenPlayButton= new int[2];
    final static Logger logger = Logger.getLogger(OoyalaApiSampleApp.class);

    /*
        Function Description :-
        Description of Input Parameters :-
        Description of the Return Value :-
        */
    public void waitForAppHomeScreen(AndroidDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.
                xpath("//android.widget.TextView[@text='ContentTree for Channel']")));

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

    public void clickImagebuttons(AndroidDriver driver, int index) {

        List<WebElement> imageButtons = driver.findElements(By.xpath("//android.widget.ImageButton"));
        imageButtons.get(index).click();
    }

    public void screenTap(AndroidDriver driver){
        // Click on the web area so that player screen shows up
        //driver.tap(1, fullscreenPlayButton[0], fullscreenPlayButton
        WebElement viewarea = driver.findElementByClassName("android.view.View");
        viewarea.click();
    }

    public void Tap(AndroidDriver driver){
        // Click on the web area so that player screen shows up
        //driver.tap(1, fullscreenPlayButton[0], fullscreenPlayButton[2],2);
        driver.tap(1,playCoordinates[0],playCoordinates[1],2);
    }

    public void playInNormalScreen(AndroidDriver driver) throws InterruptedException {
        //get x,y co-ordinates
        int[] play = new int[2];
        List<WebElement> imageButtons = driver.findElements(By.xpath("//android.widget.ImageButton"));
        play[0]=imageButtons.get(0).getLocation().getX();
        play[1]=imageButtons.get(0).getLocation().getY();

        playCoordinates[0]=play[0]+imageButtons.get(0).getSize().getWidth()/2 ;
        playCoordinates[1]=play[1]+imageButtons.get(0).getSize().getHeight()/2 ;
        logger.info("X playCoordinates"+playCoordinates[0]);
        logger.info("Y playCoordinates"+playCoordinates[1]);
        Thread.sleep(1000);
        driver.tap(1, playCoordinates[0] , playCoordinates[1], 2);

    }

    public void pauseInNormalScreen(AndroidDriver driver){

        logger.info("X pauseCoordinates"+playCoordinates[0]);
        logger.info("Y pauseCoordinates"+playCoordinates[1]);
        driver.tap(1, playCoordinates[0] , playCoordinates[1], 2);
    }

    public void resumeInNormalScreen(AndroidDriver driver){
        logger.info("X resumeCoordinates"+playCoordinates[0]);
        logger.info("Y resumeCoordinates"+playCoordinates[1]);
        driver.tap(1, playCoordinates[0] , playCoordinates[1], 2);
    }

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

    public void powerKeyClick (AndroidDriver driver) throws InterruptedException,IOException {

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

    public void closeApp(AndroidDriver driver){
        driver.quit();
    }

    public void loadingSpinner(AndroidDriver driver) {
        int i = 0;
        try {
            while (driver.findElement(By.className("android.widget.ProgressBar")).isDisplayed()) {
                //logger.info("Handling Loading Spinner");
                if (i<20){
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

}
