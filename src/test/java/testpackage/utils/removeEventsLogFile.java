package testpackage.utils;

/**
 * Created by bsondur on 11/30/15.
 */
// TOD0 : Check if file exists, only then , remove/delete the file , Check the return value

public class RemoveEventsLogFile {

    public static void removeEventsFileLog()
    {
        try{

            String[] command={"/bin/sh","-c" ,"adb shell rm /sdcard/log.file"};
            Runtime run=Runtime.getRuntime();
            Process pr = run.exec(command);

        }
        catch(Exception e)
        {
            System.out.println("Exception is"+e);
            e.printStackTrace();

        }
    }
}
