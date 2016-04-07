package testpackage.utils;

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
            System.out.println("Exception is"+e);
            e.printStackTrace();

        }
    }
}
