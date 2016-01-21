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

   
    public Properties loadProperty(String propFileName) throws IOException {

        String result = "" ;
        Properties prop = new Properties();
        InputStream inputstream = new FileInputStream(propFileName);

        if(inputstream != null) {
            prop.load(inputstream);
        }else{
            throw new FileNotFoundException("Property file '"+ propFileName + "'not found in ClassPath");
        }

        return prop;


    }
}
