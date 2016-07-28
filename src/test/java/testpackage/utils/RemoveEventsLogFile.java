package testpackage.utils;

import com.sun.jmx.snmp.Timestamp;

import javax.print.attribute.standard.DateTimeAtCreation;
import java.io.File;
import java.time.Instant;

/**
 * Created by bsondur on 11/30/15.
 */
// TOD0 : Check if file exists, only then , remove/delete the file , Check the return value

public class RemoveEventsLogFile {

    public static void removeEventsFileLog()
    {
        try{
            String[] final_command = CommandLine.command("adb shell rm /sdcard/log.file");
            Runtime run=Runtime.getRuntime();
            Process pr = run.exec(final_command);

        }
        catch(Exception e)
        {
            System.out.println("Exception is : "+e);
            e.printStackTrace();

        }
    }
    public static void storeLogFile(String logfilename)
    {
        try{
            System.out.println("\n Storing logfile to machine \n");
            String filename = logfilename + Instant.now().toEpochMilli();
            String[] final_command = CommandLine.command("adb pull /mnt/sdcard/log.file ../../appiumProj/appium-android/res/snapshot/"+filename);
            Runtime run=Runtime.getRuntime();
            Process pr = run.exec(final_command);
            Thread.sleep(5000);
            File logfile = new File("./res/snapshot/"+filename);
            System.out.println("Log File Path : " + logfile.getAbsolutePath());
            Thread.sleep(5000);
            ScreenshotDevice.appendLogToAllure(logfile);
        }
        catch(Exception e)
        {
            System.out.println("Unable to store log file as Exception is  : "+e);
            e.printStackTrace();

        }
    }

}
