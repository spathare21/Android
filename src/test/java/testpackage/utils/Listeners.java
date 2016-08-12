package testpackage.utils;


import org.junit.internal.runners.statements.Fail;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Listeners implements  IReporter {

    public static Map<String,String> Testdata = new HashMap<String, String>();

    @Override
    public void generateReport(List<XmlSuite> xmlSuite, List<ISuite> iSuites, String s){
        for(ISuite suits : iSuites)  {
            System.out.println("Suite Name : " + suits.getName());
            Map <String ,ISuiteResult>result =  suits.getResults();
            for (ISuiteResult r : result.values()) {
                ITestContext context = r.getTestContext();
                int psize = context.getSuite().getXmlSuite().getTests().size();
                String [] packagename = new String[psize];
                for(int i=0 ; i< psize ; i++){
                    packagename[i] = context.getSuite().getXmlSuite().getTests().get(i).getClasses().get(i).getName();
                }
                setTestResult(Integer.toString(context.getPassedTests().size()),Integer.toString(context.getFailedTests().size()),Integer.toString(context.getSkippedTests().size()),Integer.toString(context.getAllTestMethods().length),context.getName(),packagename);

            }
        }

    }

    public void setTestResult(String pass, String fail, String skip, String total, String suiteName,String[]Package){
        Testdata.put("Pass",pass);
        Testdata.put("Fail",fail);
        Testdata.put("Skip",skip);
        Testdata.put("Total",total);
        Testdata.put("SuiteName",suiteName);
        for (int i =0 ; i< Package.length ; i++){
            Testdata.put("Package"+i,Package[i]);
        }
        Testdata.put("Android Device Version",Adblogcat.deviceVersion);
        Testdata.put("Device Name",Adblogcat.deviceName);
        Testdata.put("SDK Version",Adblogcat.sdkVersion);
        Testdata.put("Jenkins URL",ParseJenkinsBuild.getJenkinsBuild());
        System.out.println("size of map : " + Testdata.size());
        for (String key : Testdata.keySet()){
            String value = Testdata.get(key);
            System.out.println(key + " " + value);
        }

    }

    /*public static Map<String, String> getTestResult(){
        return Testdata;
    }*/



}
