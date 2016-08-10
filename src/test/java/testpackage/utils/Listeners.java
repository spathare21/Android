package testpackage.utils;


import org.testng.*;
import org.testng.xml.XmlSuite;

import java.util.Date;
import java.util.List;
import java.util.Map;


public class Listeners implements  IReporter {

    @Override
    public void generateReport(List<XmlSuite> xmlSuite, List<ISuite> iSuites, String s){
        Date today = new Date();
        for(ISuite suits : iSuites)  {
            System.out.println("Suite Name : " + suits.getName());
            Map <String ,ISuiteResult>result =  suits.getResults();
            for (ISuiteResult r : result.values()) {
                ITestContext context = r.getTestContext();
                System.out.println("Date : " + today.toString());
                System.out.println("Passed Tests : " + context.getPassedTests().size());
                System.out.println("Failed Tests : "+ context.getFailedTests().size());
                System.out.println("Skipped Tests : "+ context.getSkippedTests().size());
                System.out.println("Total Test Run : " + context.getAllTestMethods().length ); //OR context.getSuite().getAllMethods().size()
                System.out.println("Suite Test Name : " + context.getName().toString() );
                int psize = context.getSuite().getXmlSuite().getTests().size();
                for(int i=0 ; i< psize ; i++){
                    System.out.println("Package Name : " + context.getSuite().getXmlSuite().getTests().get(i).getClasses().get(i).getName());
                }

                //System.out.println("Package Name : " + context.getSuite().getXmlSuite().getTests().get(0).getClasses().get(0).getName());
            }

        }
       /* for (XmlSuite xsuite : xmlSuite){
            System.out.println("File Name : " + xsuite.getFileName());
        }
*/

    }
}
