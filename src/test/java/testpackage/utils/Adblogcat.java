package testpackage.utils;

/**
 * Created by bsondur on 11/16/15.
 */

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.logging.LogEntry;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;


public class Adblogcat {

    public static String deviceVersion;
    public static String deviceName;
    public static String sdkVersion;

    public static void captureLogcatEvents() throws IOException, InterruptedException {


        try {


            //String[] command ={"/bin/sh", "-c","adb logcat | grep '.*Notification Received:.*' | sed -e \"s/^/$(date -r) /\" > test5.txt"};
            //String[] command ={"/bin/sh", "-c","adb logcat | grep '.*Notification Received:.*'"};
            String[] command = {"/bin/sh", "-c", "adb logcat"};
            Runtime run = Runtime.getRuntime();
            Process pr = run.exec(command);
            //pr.waitFor();

            //Thread.sleep(5000);
            String line = "";
            BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));

            while (true) {
                //BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
                //String line = "";
                line = buf.readLine();
                //while (line!=null)
                //while (true) {
                if (line.contains("Notification Received:")) {

                    StringBuilder strout = new StringBuilder(line);

                    Date date = new Date();

                    strout.append(date.toString());

                    System.out.println(strout);

                    //return true;
                }
                //System.out.println(line);
                //line = buf.readLine();
                //}
            }
        } catch (Exception e) {
            System.out.println("Exception " + e);
            e.printStackTrace();
        }
    }

    public static void androidVersion() throws IOException {
        final String command = "adb shell getprop ro.build.version.release";
        String[] version = CommandLine.command(command);
        Runtime run = Runtime.getRuntime();
        Process pr = run.exec(version);

        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(pr.getInputStream()));

        // read the output from the command
        String sdk_version = null;
        while ((sdk_version = stdInput.readLine()) != null) {
            if(!sdk_version.contains("daemon")) {
                System.out.println(" Android SDK Vesion is   :" + sdk_version + "\n");
                deviceVersion = sdk_version;
            }
        }
    }

    public static void deviceinfo() throws IOException {
        final String command = "adb shell getprop ro.product.model";
        String[] device = CommandLine.command(command);
        Runtime run = Runtime.getRuntime();
        Process pr = run.exec(device);
        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(pr.getInputStream()));

        // read the output from the command
        String devicename = null;
        while ((devicename = stdInput.readLine()) != null) {
            if(!devicename.contains("daemon")) {
                System.out.println(" Android Device name  is   :" + devicename + "\n");
                deviceName = devicename;
            }
        }
    }

    public static void sdkVersion(AndroidDriver driver) throws IOException {

        List<LogEntry> logEntries = driver.manage().logs().get("logcat").getAll();
        for (int i=0;i<logEntries.size();i++)
        {
            if(logEntries.get(i).toString().contains("Ooyala SDK Version:"))
            {
                System.out.println(logEntries.get(i).toString());
                sdkVersion = logEntries.get(i).toString();
                String[] version = sdkVersion.split(":");
                if(version.length>6)
                    sdkVersion = version[6];

            }
        }


    }

}

