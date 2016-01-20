package testpackage.utils;

import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by bsondur on 12/1/15.
 */
public class setUpAndroidDriver {

    AndroidDriver driver;

    public AndroidDriver setUpandReturnAndroidDriver(String deviceName,String appDirValue,String appValue,String platformName,String OSVERSION,String appPackage,String appActivity) throws MalformedURLException {

        File classpathRoot = new File(System.getProperty("user.dir")); // path to Appium Project
        File appDir = new File(classpathRoot, appDirValue); // path to <project folder>
        File app = new File(appDir, appValue);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        //Name of mobile web browser to automate. Should be an empty string if automating an app instead.
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        //which mobile OS to use: Android, iOS or FirefoxOS
        capabilities.setCapability("platformName", platformName);

        //Mobile OS version – in this case 4.4 since my device is running Android 4.4.2
        capabilities.setCapability(CapabilityType.VERSION, OSVERSION);

        //device name – since this is an actual device name is found using ADB
        capabilities.setCapability("deviceName", deviceName);

        //the absolute local path to the APK
        capabilities.setCapability("app", app.getAbsolutePath());
        //Java package of the tested Android app
        capabilities.setCapability("appPackage", appPackage);

        // activity name for the Android activity you want to run from your package. This need to be preceded by a . (example: .MainActivity)
        capabilities.setCapability("appActivity", appActivity);

        // Sets the timeout duration for driver between commands
        capabilities.setCapability("newCommandTimeout", 50000);

        // constructor to initialize driver object //0.0.0.0:4723
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        //driver.manage().timeouts().implicitlyWait(3000,TimeUnit.SECONDS);
        return driver;
    }

    public AndroidDriver setUpandReturnAndroidDriver(String deviceName,String appDirValue,String appValue,String platformName,String OSVERSION,String appPackage,String appActivity, int portNumber) throws MalformedURLException {
        System.out.println("Intializing with port" + portNumber);
        File classpathRoot = new File(System.getProperty("user.dir")); // path to Appium Project
        File appDir = new File(classpathRoot, appDirValue); // path to <project folder>
        File app = new File(appDir, appValue);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        //Name of mobile web browser to automate. Should be an empty string if automating an app instead.
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        //which mobile OS to use: Android, iOS or FirefoxOS
        capabilities.setCapability("platformName", platformName);

        //Mobile OS version – in this case 4.4 since my device is running Android 4.4.2
        capabilities.setCapability(CapabilityType.VERSION, OSVERSION);

        //device name – since this is an actual device name is found using ADB
        capabilities.setCapability("deviceName", deviceName);

        //the absolute local path to the APK
        capabilities.setCapability("app", app.getAbsolutePath());
        //Java package of the tested Android app
        capabilities.setCapability("appPackage", appPackage);

        // activity name for the Android activity you want to run from your package. This need to be preceded by a . (example: .MainActivity)
        capabilities.setCapability("appActivity", appActivity);

        // Sets the timeout duration for driver between commands
        capabilities.setCapability("newCommandTimeout", 50000);

        System.out.println("ALOHA" + "http://127.0.0.1:" + portNumber + "/wd/hub");

        System.out.println("capability" + capabilities.toString());

        // constructor to initialize driver object //0.0.0.0:4723
        driver = new AndroidDriver(new URL("http://127.0.0.1:" + portNumber + "/wd/hub"), capabilities);

        // driver = new AndroidDriver(new URL("http://127.0.0.1:4724/wd/hub"), capabilities);
        //driver.manage().timeouts().implicitlyWait(3000,TimeUnit.SECONDS);
        return driver;
    }

}
