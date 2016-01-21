package testpackage.tests;


import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.*;
import testpackage.utils.AppiumServer;
import testpackage.utils.RemoveEventsLogFile;
import testpackage.utils.PushLogFileToDevice;
import testpackage.utils.SetUpAndroidDriver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.Properties;

/**
 * Created by Vertis on 15/01/16.
 */
public class SDK{

    protected Properties propertyReader;
    protected AndroidDriver driver;
    protected String propFileName;
    private static int portNumber = 4723;


    public SDK() throws IOException {
        System.out.printf("In SDK");
    }

    @BeforeSuite
    public void beforeSuite() throws InterruptedException, IOException {

        System.out.println("In Before Suite");
        System.out.println("Starting server thread");
          Thread server = null;
        server = new Thread(new AppiumServer(portNumber));
        server.start();
        synchronized(server){
            try{
                System.out.println("Waiting for server to start...");
                server.wait(100000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        System.out.println("Again in Before Suite");

    }}


    @BeforeTest
    public void beforeTest() throws IOException {
        System.out.printf("property" + propertyReader);
        SetUpAndroidDriver androiddriver =  new SetUpAndroidDriver();

        driver = androiddriver.setUpandReturnAndroidDriver(propertyReader.getProperty("udid"), propertyReader.getProperty("appDir"), propertyReader.getProperty("appValue"), propertyReader.getProperty("platformName"), propertyReader.getProperty("platformVersion"), propertyReader.getProperty("appPackage"), propertyReader.getProperty("appActivity"), portNumber);
    }

    @BeforeMethod
    public void beforeMethod() throws IOException {
        PushLogFileToDevice.pushLogFile();
        System.out.println("current activity:" + driver.currentActivity());
        if (driver.currentActivity() != propertyReader.getProperty("appActivity")){
            driver.startActivity(propertyReader.getProperty("appPackage"), propertyReader.getProperty("appActivity"));
        }

    }

    @AfterMethod
    public void afterMethod() throws InterruptedException {
        System.out.println("After Method");
        RemoveEventsLogFile.removeEventsFileLog();
        Thread.sleep(10000);
    }

    @AfterTest
    public void afterTest() {
        System.out.println("After Test");
        driver.closeApp();
        driver.quit();
    }

    @AfterSuite
    public void afterSuite(){
        System.out.printf("In After Class");
        System.out.println();
        try {
            Process findID = Runtime.getRuntime().exec("lsof -i tcp:" +  portNumber + " | sed -n 2p | awk '{ print $2 }'");
            findID.waitFor();
            BufferedReader read = new BufferedReader(new InputStreamReader(findID.getErrorStream()));
            String pID = "";
            String getline = "";

            while ((getline = read.readLine()) != null){
            //    if (findID.exitValue() == 0)
                pID = getline ;
            }
            System.out.println("Process Id" + pID);
            Process kill = Runtime.getRuntime().exec("kill" + "3405");
            kill.waitFor();
            System.out.println("exit value" + kill.exitValue());
           // Thread.sleep(10000);
            BufferedReader r = new BufferedReader(new InputStreamReader(kill.getErrorStream()));
            String line = "";
            String allLine = "";
            int i=1;
            try {
                while((line=r.readLine()) != null){
                    allLine=allLine+""+line+"\n";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(allLine);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int getFreePort() {
        ServerSocket socket = null;
        int port = 0;

        try {
            socket = new ServerSocket(0);
            socket.setReuseAddress(true);
            port = socket.getLocalPort();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return port;
    }

}
