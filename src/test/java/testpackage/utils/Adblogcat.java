package testpackage.utils;

/**
 * Created by bsondur on 11/16/15.
 */

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;


public class Adblogcat {

    public static void captureLogcatEvents() throws IOException, InterruptedException{


        try
        {


            //String[] command ={"/bin/sh", "-c","adb logcat | grep '.*Notification Received:.*' | sed -e \"s/^/$(date -r) /\" > test5.txt"};
            //String[] command ={"/bin/sh", "-c","adb logcat | grep '.*Notification Received:.*'"};
            String[] command ={"/bin/sh", "-c","adb logcat"};
            Runtime run = Runtime.getRuntime();
            Process pr = run.exec(command);
            //pr.waitFor();

            //Thread.sleep(5000);
            String line = "";
            BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));

            while(true) {
                //BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
                //String line = "";
                line = buf.readLine();
                //while (line!=null)
                //while (true) {
                if(line.contains("Notification Received:"))
                {

                    StringBuilder strout=new StringBuilder(line);

                    Date date = new Date();

                    strout.append(date.toString());

                    System.out.println(strout);

                    //return true;
                }
                    //System.out.println(line);
                    //line = buf.readLine();
                //}
            }
        }
        catch (Exception e)
        {
            System.out.println("Exception " + e);
            e.printStackTrace();
        }
    }

}


