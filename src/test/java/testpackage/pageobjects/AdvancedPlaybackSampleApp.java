package testpackage.pageobjects;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.apache.log4j.Logger;
import testpackage.utils.CommandLine;
import java.io.IOException;
import java.util.List;

public class AdvancedPlaybackSampleApp {

    //WebElement playButton;
    int[] playCoordinates = new int[2];
    final static Logger logger = Logger.getLogger(AdvancedPlaybackSampleApp.class);

    public void waitForAppHomeScreen(AndroidDriver driver) {
        //waiting for homescreen to load specific text to ensure homescreen is loaded
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.
                xpath("//android.widget.TextView[@text='Play With InitialTime']")));
    }

    public void assertCurrentActivityAgainst(AndroidDriver driver, String activityName) {
        //Verifying correct current activity is loading
        Assert.assertEquals(driver.currentActivity(), activityName);
    }

    public void clickBasedOnText(AndroidDriver driver, String clickText) {
        //Selecting the asset based on displayed text
        WebElement clickTextField = driver.findElement(By.xpath("//android.widget.TextView[@text='" + clickText + "']"));
        clickTextField.click();
    }

    public void waitForPresence(AndroidDriver driver, String typeOf, String waitString) {
        //Waiting for presence of specific element using classname
        WebDriverWait wait = new WebDriverWait(driver, 30);
        if (typeOf == "className") {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className(waitString)));
        }
    }

    public void waitForTextView(AndroidDriver driver, String text) {
        //waiting for text to appear
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.
                xpath("//android.widget.TextView[@text='" + text + "']")));
    }

    public void clickOnVastAd(AndroidDriver driver) throws InterruptedException {
        //Inserting Vast Ad in video
        Thread.sleep(1000);
        logger.info("in vast ad");
        driver.findElementById("com.ooyala.sample.AdvancedPlaybackSampleApp:id/doubleLeftButton").click();
        logger.info("clicked");
    }

    //TODO: Need to delete this after refactoring code of Deeplook
    public void playVideo(AndroidDriver driver) throws InterruptedException {
        //Clicking on play button
        Thread.sleep(2000);
        driver.findElementByXPath("//android.widget.ImageButton[@index='0']").click();
    }

    //TODO: Need to delete this after refactoring code of Deeplook
    public void pauseVideo(AndroidDriver driver) throws InterruptedException {
        //clicking on pasue button using dimensions
        Thread.sleep(2000);
        logger.info("Pausing the Video");
        // Tap coordinates to pause
        String dimensions = driver.manage().window().getSize().toString();
        //logger.info(" Dimensions are "+dimensions);
        String[] dimensionsarray = dimensions.split(",");
        int length = dimensionsarray[1].length();
        String ydimensions = dimensionsarray[1].substring(0, length - 1);
        String ydimensionstrimmed = ydimensions.trim();
        int ydimensionsInt = Integer.parseInt(ydimensionstrimmed);
        driver.tap(1, 35, (ydimensionsInt - 25), 2);
    }

    public void getBackFromRecentApp(AndroidDriver driver) throws InterruptedException, IOException {
        String command = "adb shell input keyevent KEYCODE_APP_SWITCH";
        String[] final_command = CommandLine.command(command);
        Runtime run = Runtime.getRuntime();
        Process pr = run.exec(final_command);
        Thread.sleep(3000);
        logger.info("showing recent app screen");
        driver.findElement(By.xpath("//android.view.View[@index= '0']")).click();  // here clicking on system ui to get back the sample app
        logger.info("back to SDK");
    }

    public void powerKeyClick(AndroidDriver driver) throws InterruptedException, IOException {
        driver.sendKeyEvent(26); // key 26 is used to lock the screen
        logger.info("key sent");
        logger.info("screen lock");
        Thread.sleep(5000);
        driver.sendKeyEvent(82); // key 82 is used to unlock the screen
        String command = "adb shell input keyevent KEYCODE_WAKEUP";
        String[] final_command = CommandLine.command(command);
        Runtime run = Runtime.getRuntime();
        Process pr = run.exec(final_command);
        Thread.sleep(3000);
        logger.info("showing screen unlock");
        logger.info("Back to Sample App screen ");
        Thread.sleep(2000);
    }

    public void clickOnOoyalaAd(AndroidDriver driver) throws InterruptedException {
        Thread.sleep(1000);
        logger.info("in Ooyala ad");
        driver.findElementById("com.ooyala.sample.AdvancedPlaybackSampleApp:id/doubleRightButton").click();
        logger.info("clicked");
    }

    public void clickOnP1(AndroidDriver driver) throws InterruptedException {
        Thread.sleep(1000);
        logger.info("in Video1 ad");
        driver.findElementById("com.ooyala.sample.AdvancedPlaybackSampleApp:id/doubleLeftButton").click();
        logger.info("clicked");
    }

    public void clickOnP2(AndroidDriver driver) throws InterruptedException {
        Thread.sleep(1000);
        logger.info("in Video2 ad");
        driver.findElementById("com.ooyala.sample.AdvancedPlaybackSampleApp:id/doubleRightButton").click();
        logger.info("clicked");

    }

    public void clickFullScreen(AndroidDriver driver) throws InterruptedException {
        Thread.sleep(1000);
        logger.info("Clicking on full screen button");
        driver.findElementByXPath("//android.widget.ImageButton[@index='2']").click();
    }

    public void clickNormalScreen(AndroidDriver driver) throws InterruptedException {
        Thread.sleep(1000);
        logger.info("Clicking on normal screen button");
        WebElement frameLayout = driver.findElement(By.id("content"));
        List<WebElement> layout = frameLayout.findElements(By.className("android.widget.LinearLayout"));
        WebElement normalScreen = layout.get(0).findElement(By.className("android.widget.ImageButton"));
        normalScreen.click();
        logger.info("Clicked");
    }

    //Todo: Need to implement this in Deeplook
    public void backSeekInFullScreen(AndroidDriver driver) throws InterruptedException {
        logger.info("Clicking on Back seek button");
        WebElement layout = driver.findElement(By.xpath("//android.widget.LinearLayout[@index=1]"));
        List<WebElement> seek = layout.findElements(By.className("android.widget.ImageButton"));
        seek.get(0).click();
        logger.info("Back seek button clicked");
    }

    //Todo: Need to implement this in Deeplook
    public void playVideoFullScreen(AndroidDriver driver) throws InterruptedException {
        logger.info("Clicking on play button in full screen");
        WebElement layout = driver.findElement(By.xpath("//android.widget.LinearLayout[@index=1]"));
        List<WebElement> play = layout.findElements(By.className("android.widget.ImageButton"));
        play.get(1).click();
        logger.info("Play button clicked");
    }

    //Todo: Need to implement this in Deeplook
    public void pauseVideoFullScreen(AndroidDriver driver) throws InterruptedException {
        //  Thread.sleep(2000);
        logger.info("Pausing the Video in Full Screen");
        //Pausing Video
        String dimensions = driver.manage().window().getSize().toString();
        String[] dimensionsarray = dimensions.split(",");
        int length = dimensionsarray[1].length();
        String ydimensions = dimensionsarray[1].substring(0, length - 1);
        String ydimensionstrimmed = ydimensions.trim();
        int ydimensionsInt = Integer.parseInt(ydimensionstrimmed);
        driver.tap(1, 600, (ydimensionsInt - 100), 2);
    }

    //TODO: Need to delete this after refactoring code of Deeplook
    public void pauseSmallPlayer(AndroidDriver driver) throws InterruptedException {
         Thread.sleep(2000);
        logger.info("Pausing the Video");
        //Pausing Video
        String dimensions = driver.manage().window().getSize().toString();
        String[] dimensionsarray = dimensions.split(",");
        int length = dimensionsarray[1].length();
        String ydimensions = dimensionsarray[1].substring(0, length - 1);
        String ydimensionstrimmed = ydimensions.trim();
        int ydimensionsInt = Integer.parseInt(ydimensionstrimmed);
        driver.tap(1, 35, (ydimensionsInt - 265), 2);

    }

    //TODO: Need to delete this after refactoring code of Deeplook
    public void customControlPlayButton(AndroidDriver driver) throws InterruptedException {
        Thread.sleep(2000);
        logger.info("Playing Paused Video");
        //Play Video
        String dimensions = driver.manage().window().getSize().toString();
        String[] dimensionsarray = dimensions.split(",");
        int length = dimensionsarray[1].length();
        int length1 = dimensionsarray[0].length();
        String ydimensions = dimensionsarray[1].substring(0, length - 1);
        String xdimentions = dimensionsarray[0].substring(1, length1);
        String ydimensionstrimmed = ydimensions.trim();
        String xdimentiontrimmed = xdimentions.trim();
        int xdimensionsInt = Integer.parseInt(xdimentiontrimmed);
        int ydimensionsInt = Integer.parseInt(ydimensionstrimmed);
        int buttonxdimentions = xdimensionsInt - 545;
        int buttonydimentions = ydimensionsInt - 60;
        logger.info("Playbutton X Axis:" + buttonxdimentions);
        logger.info("Playbutton X Axis:" + buttonydimentions);
        driver.tap(1, buttonxdimentions, buttonydimentions, 2);
    }

    //TODO: Need to delete this after refactoring code of Deeplook
    public void customControlPauseButton(AndroidDriver driver) throws InterruptedException {
        Thread.sleep(2000);
        logger.info("Clicking on Pause Video");
        //Pausing Video
        String dimensions = driver.manage().window().getSize().toString();
        String[] dimensionsarray = dimensions.split(",");
        int length = dimensionsarray[1].length();
        int length1 = dimensionsarray[0].length();
        String ydimensions = dimensionsarray[1].substring(0, length - 1);
        String xdimentions = dimensionsarray[0].substring(1, length1);
        String ydimensionstrimmed = ydimensions.trim();
        String xdimentiontrimmed = xdimentions.trim();
        int xdimensionsInt = Integer.parseInt(xdimentiontrimmed);
        int ydimensionsInt = Integer.parseInt(ydimensionstrimmed);
        int buttonxdimentions = xdimensionsInt - 545;
        int buttonydimentions = ydimensionsInt - 60;
        logger.info("Playbutton X Axis:" + buttonxdimentions);
        logger.info("Playbutton X Axis:" + buttonydimentions);
        driver.tap(1, buttonxdimentions, buttonydimentions, 2);
    }

    public void overlay(AndroidDriver driver) throws InterruptedException {
        Thread.sleep(1000);
        logger.info("Check Overlay Present or not");
        WebElement overlay1 = driver.findElement(By.xpath("//android.widget.TextView[@text='This is an overlay']"));
        Assert.assertEquals(true, overlay1.isDisplayed());
        logger.info("Overlay  diplayed");
    }

    //TODO: Need to delete this after refactoring code of Deeplook
    public void clickBasedOnIndex(AndroidDriver driver, String clickIndex) {
        WebElement clickIndexField = driver.findElement(By.xpath("//android.widget.ListView/android.widget.TextView[@index='" + clickIndex + "']"));
        clickIndexField.click();
    }

    public void screenTap(AndroidDriver driver) throws InterruptedException {
        //Checking if control bar is displaying
        boolean isElement1Present = true;
        try {
            WebElement scrubberBar = driver.findElementByXPath("//android.widget.SeekBar");
            Thread.sleep(1000);
            logger.info("The scrubber bar is displaying");
        } catch (org.openqa.selenium.NoSuchElementException e) {
            isElement1Present = false;
        }
        if (isElement1Present == false) {
            //clicking on screen to make control bar visible again
            WebElement screentap = driver.findElementByXPath("//android.view.View");
            screentap.click();
            logger.info("Scrubber bar is displaying after click");
        }
    }

    //Todo: Need to implement this in Deeplook
    public void seekVideoFullscreen(AndroidDriver driver) {
        WebElement seekBarField = driver.findElementByClassName("//android.widget.SeekBar");
        int seekBarFieldWidth = seekBarField.getLocation().getX();
        int seekBarFieldHeigth = seekBarField.getLocation().getY();
        logger.info(" Dimensions bounds value is :-" + seekBarFieldHeigth);
        logger.info(" Dimensions bounds value is :-" + seekBarFieldWidth);
        logger.info(" Dimensions bounds value is :-" + seekBarField.getSize().getHeight());
        logger.info(" Dimensions bounds value is :-" + seekBarField.getSize().getWidth());
        logger.info(" Seeking ------------------------- ");
        driver.swipe(seekBarFieldWidth + 20, seekBarFieldHeigth, seekBarFieldWidth + 100, seekBarFieldHeigth, 3);

    }

    public void playInNormalScreen(AndroidDriver driver) throws Exception {
        //Clicking on play button
        int[] play = new int[2];
        Thread.sleep(2000);
        List<WebElement> imageButtons = driver.findElementsByClassName("android.widget.ImageButton");
        logger.info("Size : " + imageButtons.size());
        if (imageButtons.size() > 0) {
            //Getting Coordinates of play button
            play[0] = imageButtons.get(0).getLocation().getX();
            play[1] = imageButtons.get(0).getLocation().getY();
            //Getting center of play button
            playCoordinates[0] = play[0] + imageButtons.get(0).getSize().getWidth() / 2;
            playCoordinates[1] = play[1] + imageButtons.get(0).getSize().getHeight() / 2;
            logger.info("X playCoordinates" + playCoordinates[0]);
            logger.info("Y playCoordinates" + playCoordinates[1]);
            driver.tap(1, playCoordinates[0], playCoordinates[1], 2);
        }
    }

    public void pauseInNormalScreen(AndroidDriver driver) {
        //clicking on play button to pause video
        logger.info("X pauseCoordinates" + playCoordinates[0]);
        logger.info("Y pauseCoordinates" + playCoordinates[1]);
        driver.tap(1, playCoordinates[0], playCoordinates[1], 2);
    }

    public void seekVideo(AndroidDriver driver) {
        WebElement seekBarField = driver.findElement(By.xpath("//android.widget.SeekBar"));
        //Getting scrubber bar's height and width
        int seekBarFieldWidth = seekBarField.getLocation().getX();
        int seekBarFieldHeigth = seekBarField.getLocation().getY();
        logger.info(" Dimensions bounds value is :-" + seekBarFieldHeigth);
        logger.info(" Dimensions bounds value is :-" + seekBarFieldWidth);
        logger.info(" Dimensions bounds value is :-" + seekBarField.getSize().getHeight());
        logger.info(" Dimensions bounds value is :-" + seekBarField.getSize().getWidth());
        logger.info(" Seeking -------------------------  ");
        driver.swipe(seekBarFieldWidth + 20, seekBarFieldHeigth, seekBarFieldWidth + 100, seekBarFieldHeigth, 3);
    }

    public void readTime(AndroidDriver driver) {
        // Read playback time
        List<WebElement> startTime = driver.findElementsByClassName("android.widget.TextView");
        logger.info("Size:" + startTime.size());
        if (startTime.size() > 0) {
            String startTimetext = startTime.get(1).getText();
            logger.info("The Start time of video is:" + startTimetext);
        }
    }

    public void resumeVideoInNormalscreen(AndroidDriver driver) {
        logger.info("X resumeCoordinates" + playCoordinates[0]);
        logger.info("Y resumeCoordinates" + playCoordinates[1]);
        driver.tap(1, playCoordinates[0], playCoordinates[1], 2);
    }

    public void loadingSpinner(AndroidDriver driver) {
        //Handing wait on occurrence of loading spinner
        int i = 0;
        try {
            while (driver.findElement(By.className("android.widget.ProgressBar")).isDisplayed()) {
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


