package testpackage.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import org.testng.annotations.BeforeSuite;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;



public class EventLogTest implements IHookable {

    public static AndroidDriver driver;


    @BeforeSuite
    public void beforeSuit() throws Exception {
        System.out.println("\n Before suit\n ");
        Adblogcat.deviceinfo();
        Adblogcat.androidVersion();
    }

    @Override
    public void run(IHookCallBack callBack, ITestResult testResult){

        callBack.runTestMethod(testResult);
            try {
                appendLogToAllure(testResult.getMethod().getMethodName());
                screenshot(testResult.getMethod().getMethodName(),driver);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Attachment(value = "{0}", type = "text/plain")
    public static byte[] appendLogToAllure(String fname) throws Exception {
        try {
            String filename = storeLogFile(fname);
            File logfilename = new File(filename);
            System.out.println("attach loggile "+ logfilename + " to allure");
            Thread.sleep(5000);
            return FileUtils.readFileToByteArray(logfilename);
        } catch (IOException ignored) {
            return null;
        }
    }

    public static String storeLogFile(String logfilename)
    {
        String filePath = null;
        try{
            System.out.println("\n Storing logfile to machine \n");
            String currentDir = System.getProperty("user.dir");
            String filename = logfilename + "_" + Instant.now().toEpochMilli();
            String logspath = currentDir + "/res/snapshot/"+filename;
            String[] final_command = CommandLine.command("adb pull /mnt/sdcard/log.file "+logspath);
            Runtime run=Runtime.getRuntime();
            Process pr = run.exec(final_command);
            Thread.sleep(2000);
            File logfile = new File(logspath);
            filePath = logfile.getAbsolutePath();
        }
        catch(Exception e)
        {
            System.out.println("Unable to store log file as Exception is  : "+e);
            e.printStackTrace();
        }
        return filePath;

    }


    @Attachment(value = "{0}", type = "image/png")
    public static byte[] screenshot(String testMethodName,AndroidDriver dr) throws Exception
    {
        try {
            String currentDir = System.getProperty("user.dir");
            String Screenshotpath = currentDir + "/res/snapshot/";
            File scrFile = ((TakesScreenshot) dr).getScreenshotAs(OutputType.FILE);
            String imgFile= Screenshotpath + testMethodName + Instant.now().toEpochMilli() + ".jpg";
            File imgf = new File(imgFile);
            FileUtils.copyFile(scrFile, new File(imgFile));
            return toByteArray(imgf);
        }
        catch(Exception e)
        {
            System.out.println("Exception while taking screenshot : " + e.getMessage());
        }
        return new byte[0];
    }

    private static byte[] toByteArray(File file) throws IOException {
        return Files.readAllBytes(Paths.get(file.getPath()));
    }



}
