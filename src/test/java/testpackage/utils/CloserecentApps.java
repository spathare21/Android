package testpackage.utils;

import java.io.IOException;

/**
 * Created by Sachin on 4/22/2016.
 */
public class CloserecentApps {

    public static void closeApps() throws IOException, InterruptedException {
        System.out.println("in Closing all recent apps");
        final String command = "adb shell input keyevent KEYCODE_APP_SWITCH";
        String[] APP_SWITCHER = CommandLine.command(command);
        Runtime run = Runtime.getRuntime();
        Process pr = run.exec(APP_SWITCHER);

        final String keyevent = "adb shell input keyevent 20";
        String[] key_event = CommandLine.command(keyevent);
        Runtime run2 = Runtime.getRuntime();
        Process pr2 = run2.exec(key_event);
        Thread.sleep(2000);
        Process pr3 = run2.exec(key_event);

        int i = 0;
        final String DEL = "adb shell input keyevent DEL";
        String[] Del_app = CommandLine.command(DEL);
        Runtime run4 = Runtime.getRuntime();

        for(i=0;i<=30;i++)
        {
            Process pr4 = run4.exec(Del_app);
            Thread.sleep(1000);
        }

        System.out.println("closed all app in background");


    }
}