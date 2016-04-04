package testpackage.utils;

import org.junit.Assert;
import org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by bsondur on 11/24/15.
 */
public class ParseEventsFile {
    

    public int latestCount(String line){
        int count1;
        String[] tokens = line.split(":");
        String trimToken = tokens[3].trim();
        count1=Integer.parseInt(trimToken);
        return count1;
    }

    public int parseeventfile(String comp, int count ){

        try{
            String[] final_command = CommandLine.command("adb shell cat /sdcard/log.file");
            Runtime run = Runtime.getRuntime();
            Process pr = run.exec(final_command);

            String line = "";
            BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));

            line = buf.readLine();

            while(line != null){
                //System.out.println(line);
                if(line.contains("state: ERROR"))
                {
                    System.out.println("App crashed");
                    System.exit(0);
                }
                if(line.contains(comp))
                {
                  if (latestCount(line)>count) {

                        System.out.println("Event Recieved From SDK AND Sample App :- " + line);
                        count=latestCount(line);
                        return count;
                    }
                    
                }
                line = buf.readLine();

            }

        }
        catch (Exception e)
        {
            System.out.println("Exception " + e);
            e.printStackTrace();
        }

        return -1;
    }

}
