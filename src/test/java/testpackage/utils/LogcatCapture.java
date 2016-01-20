package testpackage.utils;

/**
 * Created by bsondur on 11/16/15.
 */

import java.io.File;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import org.openqa.selenium.logging.LogEntry;

import io.appium.java_client.AppiumDriver;

public class LogcatCapture {


    public static void captureLog(AppiumDriver driver, String testName) throws Exception {
        DateFormat df = new SimpleDateFormat("dd_MM_yyyy_HH-mm-ss");
        Date today =  Calendar.getInstance().getTime();
        String reportDate = df.format(today);
        String logPath = "/Users/bsondur/Documents/appiumPOC/logcatappium";

        //log.info(driver.getSessionId() + ": Saving device log...");
        List<LogEntry> logEntries = driver.manage().logs().get("logcat").filter(Level.ALL);

        //List<LogEntry> logEntries = driver.manage().logs().get("logcat").filter(Level.parse("Notification Received: "));
        File logFile = new File(logPath + reportDate + "_" + testName + ".txt");
        PrintWriter log_file_writer = new PrintWriter(logFile);
        log_file_writer.println(logEntries );
        log_file_writer.flush();
        //log.info(driver.getSessionId() + ": Saving device log - Done.");
    }

}
