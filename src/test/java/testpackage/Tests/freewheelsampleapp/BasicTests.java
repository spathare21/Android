package testpackage.tests.freewheelsampleapp;

import testpackage.tests.SDK;
import testpackage.utils.LoadPropertyValues;

import java.io.IOException;

/**
 * Created by Vertis on 14/01/16.
 */
public class BasicTests extends SDK{

        public BasicTests() throws IOException {
            System.out.printf("In Basic Tests");
            LoadPropertyValues config = new LoadPropertyValues();
            propFileName = "./src/test/resources/config/freewheelSampleApp.properties";
            //propFileName = "./src/test/resources/config.properties";
            propertyReader =  config.loadProperty(propFileName);
        }

}
