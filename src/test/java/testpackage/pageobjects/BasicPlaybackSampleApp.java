package testpackage.pageobjects;

import io.appium.java_client.android.AndroidDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Created by bsondur on 11/30/15.
 */
public class BasicPlaybackSampleApp {


        /*
        Function Description :-
        Description of Input Parameters :-
        Description of the Return Value :-
        */
        public void waitForAppHomeScreen(AndroidDriver driver){

            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.
                    xpath("//android.widget.TextView[@text='4:3 Aspect Ratio']")));

        }


        public void waitForTextView(AndroidDriver driver, String text){

            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.
                xpath("//android.widget.TextView[@text='"+text+"']")));

        }


        public void assertCurrentActivityAgainst(AndroidDriver driver,String activityName){

            Assert.assertEquals(driver.currentActivity(),activityName);
        }

        public void clickBasedOnText(AndroidDriver driver, String clickText){

            WebElement clickTextField=driver.findElement(By.xpath("//android.widget.TextView[@text='"+clickText+"']"));
            clickTextField.click();

        }

        public void clickBasedOnTextScrollTo(AndroidDriver driver, String clickText){

            driver.scrollTo(clickText).click();
        }

        public void waitForPresence(AndroidDriver driver,String typeOf,String waitString){
            WebDriverWait wait = new WebDriverWait(driver, 30);
            if(typeOf=="className") {
                wait.until(ExpectedConditions.presenceOfElementLocated(By.className(waitString)));
            }

        }

        public void getXYSeekBarAndSeek(AndroidDriver driver,int widthOffSet1,int widthOffSet2){
            WebElement seekBarField=driver.findElement(By.xpath("//android.widget.SeekBar"));

            int seekBarFieldWidth=seekBarField.getLocation().getX();
            int seekBarFieldHeigth=seekBarField.getLocation().getY();
            //System.out.println(" Dimensions bounds value is :-"+seekBarFieldHeigth);
            //System.out.println(" Dimensions bounds value is :-"+seekBarFieldWidth);
            System.out.println(" Seeking -------------------------  ");
            driver.swipe(seekBarFieldWidth+widthOffSet1,seekBarFieldHeigth,seekBarFieldWidth+widthOffSet2,seekBarFieldHeigth,3);
        }

        public void clickImagebuttons(AndroidDriver driver, int index){

            List<WebElement> imageButtons= driver.findElements(By.xpath("//android.widget.ImageButton"));
            imageButtons.get(index).click();
        }

        public void clickRadiobuttons(AndroidDriver driver, int index){

            List<WebElement> radioButtons= driver.findElements(By.xpath("//android.widget.RadioButton"));
            radioButtons.get(index).click();
        }

        public boolean radioButtonChecked(AndroidDriver driver, int index){

            List<WebElement> radioButtons= driver.findElements(By.xpath("//android.widget.RadioButton"));
            return radioButtons.get(index).isEnabled();
        }

}
