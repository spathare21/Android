package testpackage.utils;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Sachin on 12/08/16.
 */
public class ParseJenkinsBuild {
    final static Logger logger = Logger.getLogger(ParseJenkinsBuild.class);

    public static String buildno;
    public static String buildNumber;
    public static String jenkinsJobLink;

    public static String getJenkinsBuild(){

        String lnk = "http://jenkins-master1.services.ooyala.net:8080/job/appium-android-test-2-dev/lastBuild/api/json";

        try {
            URL url = new URL(lnk);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null)
            {
                if(inputLine.contains("\"id\""))
                {
                    String[] _build = inputLine.split(":");
                    //logger.debug("Length of array : " +  _build.length); //lenght of array for all values
                    for (int i =0 ; i<_build.length;i++){
                        if(_build[i].contains("\"id\""))   {
                            String [] buildno = _build[i+1].split(",");
                            //logger.debug("\"","")); //get the latest jenkins build number
                            buildNumber = buildno[0].replace("\"","");
                        }
                    }
                    break;
                }
            }
            in.close();
        }
        catch (Exception ex) {
            logger.error("Error occured while reading V4 version in Utils.getV4Version()");
        }
        jenkinsJobLink= "http://jenkins-master1.services.ooyala.net:8080/job/appium-android-test-2-dev/"+buildNumber+"/console";
        return  jenkinsJobLink;
    }

}
