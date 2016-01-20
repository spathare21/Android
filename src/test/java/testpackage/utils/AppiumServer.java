package testpackage.utils;

import testpackage.tests.SDK;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * Created by Vertis on 19/01/16.
 */
public class AppiumServer implements Runnable {
    public int portNumber;

     public AppiumServer(int portNumber) {
        this.portNumber = portNumber;
    }

    public void run() {
        System.out.printf("Running appium server");
        synchronized(this) {
            String cmd = "appium -p " + this.portNumber;
            System.out.println("cmd" + cmd);
            try {
                Process p = Runtime.getRuntime().exec(cmd);
                Thread.sleep(10000);
                System.out.println("started appium server");
                BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line = "";
                String allLine = "";
                int i=1;
                try {
                    while((line=r.readLine()) != null){
                           allLine=allLine+""+line+"\n";
                        if(line.contains("Console LogLevel: debug"))
                            break;
                        i++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(allLine);
                notify();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }



}
