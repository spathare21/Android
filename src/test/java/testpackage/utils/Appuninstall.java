package testpackage.utils;
import org.apache.log4j.Logger;

import java.util.*;
import java.io.IOException;
import java.io.*;
/**
 * Created by Sachin on 4/19/2016.
 */
public class Appuninstall {
    final static Logger logger = Logger.getLogger(Appuninstall.class);

    public static void uninstall(String prop) throws IOException {
        logger.info("in app uninstallation");
        final String command  = "adb uninstall " ;
        //System.out.println("command which we are executin is " +command);
        String[] final_command = CommandLine.command(command+ " " + prop);
       // System.out.println("final command is " +final_command);
        Runtime run = Runtime.getRuntime();
        Process pr = run.exec(final_command);
        logger.info("uninstalltion process done");
    }


}
