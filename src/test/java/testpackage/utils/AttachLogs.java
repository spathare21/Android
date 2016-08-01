package testpackage.utils;

import org.apache.commons.io.FileUtils;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.io.File;
import java.io.IOException;
import java.time.Instant;



public class AttachLogs implements IHookable {

    @Override
    public void run(IHookCallBack callBack, ITestResult testResult){

        callBack.runTestMethod(testResult);
            try {
                appendLogToAllure(testResult.getMethod().getMethodName());
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Attachment(value = "{0}", type = "text/plain")
    public static byte[] appendLogToAllure(String fname) throws Exception {
        try {
            String filename = storeLogFile(fname);
            File logfilename = new File(filename);
            System.out.println("attach loggile "+ logfilename + " to allure");
            Thread.sleep(5000);
            return FileUtils.readFileToByteArray(logfilename);
        } catch (IOException ignored) {
            return null;
        }
    }

    public static String storeLogFile(String logfilename)
    {
        String filePath = null;
        try{
            System.out.println("\n Storing logfile to machine \n");
            String currentDir = System.getProperty("user.dir");
            String filename = logfilename + "_" + Instant.now().toEpochMilli();
            String logspath = currentDir + "/res/snapshot/"+filename;
            String[] final_command = CommandLine.command("adb pull /mnt/sdcard/log.file "+logspath);
            Runtime run=Runtime.getRuntime();
            Process pr = run.exec(final_command);
            Thread.sleep(2000);
            File logfile = new File(logspath);
            filePath = logfile.getAbsolutePath();
        }
        catch(Exception e)
        {
            System.out.println("Unable to store log file as Exception is  : "+e);
            e.printStackTrace();
        }
        return filePath;

    }


}
