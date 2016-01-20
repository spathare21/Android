package testpackage.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by bsondur on 11/24/15.
 */
public class ParseEventsFile {

    public static boolean parseeventfile(String comp ){

        try{

            String[] command ={"/bin/sh", "-c","adb shell cat /sdcard/log.file"};
            Runtime run = Runtime.getRuntime();
            Process pr = run.exec(command);

            String line = "";
            BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));

            line = buf.readLine();

            while(line != null){
                //System.out.println(line);
                if(line.contains(comp))
                {
                    System.out.println("Event Recieved From SDK AND Sample App :- "+line);
                    return true;
                }
                line = buf.readLine();

            }

        }
        catch (Exception e)
        {
            System.out.println("Exception " + e);
            e.printStackTrace();
        }

        return false;
    }

}
