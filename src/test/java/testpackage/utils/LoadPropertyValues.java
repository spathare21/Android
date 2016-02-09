package testpackage.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

/**
 * Created by bsondur on 12/8/15.
 */
public class LoadPropertyValues {

    public Properties loadProperty() throws IOException {

        String result = "" ;
        Properties prop = new Properties();
        //String propFileName = "./resources/config.properties";
        //String propFileName = "./resources/config.properties";
        //src/main/resources
        String propFileName = "./src/test/resources/config/basicplaybacksampleapp.properties";

        //InputStream inputstream = getClass().getClassLoader().getResourceAsStream(propFileName);
        InputStream inputstream = new FileInputStream(propFileName);

        if(inputstream != null) {
            prop.load(inputstream);
        }else{
            throw new FileNotFoundException("Property file '"+propFileName+"'not found in ClassPath");
        }

        Date time = new Date(System.currentTimeMillis());

        //get the property Value and print it

        return prop;
    }

    public Properties loadProperty(String propFileName) throws IOException {

        String result = "" ;
        Properties prop = new Properties();
        propFileName="./src/test/resources/config/"+propFileName;
        InputStream inputstream = new FileInputStream(propFileName);

        if(inputstream != null) {
            prop.load(inputstream);
        }else{
            throw new FileNotFoundException("Property file '"+ propFileName + "'not found in ClassPath");
        }

        return prop;


    }
}
