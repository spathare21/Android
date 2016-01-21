package testpackage.utils;

/**
 * Created by bsondur on 1/11/16.
 */
public class PushLogFileToDevice {

    public static void pushLogFile(){

        try
        {
              String[] command ={"/bin/sh", "-c","adb push log.file /sdcard/"};
            Runtime run = Runtime.getRuntime();
            Process pr = run.exec(command);

        }
        catch (Exception e)
        {
            System.out.println("Exception " + e);
            e.printStackTrace();
        }


    }
}
