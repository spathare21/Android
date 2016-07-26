package testpackage.utils;

/**
 * Created by bsondur on 11/16/15.
 */

import java.io.File;
import java.io.IOException;
import java.time.Instant;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import io.appium.java_client.AppiumDriver;

public class ScreenshotDevice {

    // Taking Device ScreenShot
    // function goes to TestUtils package ??
    public static void screenshot(AppiumDriver driver,String testMethodName) throws IOException
    {
        try {
            String currentDir = System.getProperty("user.dir");
            String Screenshotpath = currentDir + "/res/snapshot/";
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(Screenshotpath + testMethodName + Instant.now().toEpochMilli() + ".jpg"));
            System.out.println("Screen shot taken successfully..!!! \n\n\n\n");
        }
        catch (Exception e)
        {
            System.out.println("\n Exception while taking screenshot : " + e.getMessage());
        }
    }


}
