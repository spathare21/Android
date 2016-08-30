package testpackage.utils;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.Date;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.*;

public class DBFunction {

    private MongoDatabase db;

    //10.11.70.242:27017
    public void ConnectDB(String DBName, String Server, int Port)
    {
        if(DBName == "")
            DBName = "playerv4";
        if(Server == "")
            Server = "10.11.70.242";
        if(Port ==0)
            Port=27017;

        MongoClient mongoClient = new MongoClient(Server, Port);

        db = mongoClient.getDatabase(DBName);
    }

    public void insertData(String collectionName, Document data)
    {
        if(collectionName=="")
            collectionName ="testresults";
        if(data == null)
        {
            data= new Document().append("Plugin","Main")
                    .append("TestCaseName","Sachin_Demo")
                    .append("TestLink","http:1234")
                    .append("Result","Fail")
                    .append("Date",new Date().toString())
                    .append("Platform","OS X Chrome")
                    .append("V4_Version","4.5.2")
                    .append("Tester","Automation")
                    .append("Console_Log","");
        }

        /*db.getCollection(collectionName).insertOne(
                new Document().append("TestcaseName","IMAMidroll")
                        .append("TestLink","http://123_3")
                        .append("TestResult","Pass") */

        db.getCollection(collectionName).insertOne(data);
    }

    public Document findData() {
        return null;
    }

    public void DisconnectDB() {}

}

