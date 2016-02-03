package testpackage.utils;
import java.util.*;
import java.io.*;

/**
 * Created by bsondur on 11/18/15.
 */
public class ParseLogCatFile {



        public static boolean readLogCatFile()
        {
            File file =new File("test5.txt");
            Scanner in = null;
            try {
                    in = new Scanner(file);
                    while(in.hasNext())
                        {
                        String line=in.nextLine();
                        if(line.contains("playStarted"))
                        {
                            System.out.println(line);
                            return true;
                        }

                }
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return false;
        }
}
