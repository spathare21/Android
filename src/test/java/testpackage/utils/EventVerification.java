package testpackage.utils;

import org.junit.Assert;

/**
 * Created by bsondur on 11/30/15.
 */

//TODO:  add timeout variable to function signature
public class EventVerification {

    public void verifyEvent(String eventType,String consoleMessage,int timeout){

        // Paused  Verification
        boolean status=false;
        long startTime = System.currentTimeMillis(); //fetch starting time
        while(!status && (System.currentTimeMillis()-startTime)<timeout) {

            //status = ParseEventsFile.parseeventfile("stateChanged - state: PAUSED");
            status = ParseEventsFile.parseeventfile(eventType);
            if (status == true) {
                System.out.println(consoleMessage);
                System.out.println("\n");
            }
        }
        if(!status){
            Assert.assertTrue(status);
        }
    }
}
