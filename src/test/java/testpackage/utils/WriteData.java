package testpackage.utils;

/**
 * Created by Vertis on 12/08/16.
 */
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import com.google.api.services.sheets.v4.Sheets;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class WriteData {
    /** Application name. */
    private static final String APPLICATION_NAME =
            "Google Sheets API Java Quickstart";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/sheets.googleapis.com-java-quickstart.json");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/sheets.googleapis.com-java-quickstart.json
     */
    private static final List<String> SCOPES =
            Arrays.asList(SheetsScopes.SPREADSHEETS);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        try {
            InputStream in =
                    WriteData.class.getResourceAsStream("/client_secret.json");
            GoogleClientSecrets clientSecrets =
                    GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

            // Build flow and trigger user authorization request.
            GoogleAuthorizationCodeFlow flow =
                    new GoogleAuthorizationCodeFlow.Builder(
                            HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                            .setDataStoreFactory(DATA_STORE_FACTORY)
                            .setAccessType("offline")
                            .build();
            Credential credential = new AuthorizationCodeInstalledApp(
                    flow, new LocalServerReceiver()).authorize("sachin.pathare@forgeahead.io");
            System.out.println(
                    "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
            return credential;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Build and return an authorized Sheets API client service.
     * @return an authorized Sheets API client service
     * @throws IOException
     */
    public static Sheets getSheetsService() throws IOException {
        Credential credential = authorize();
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static void writetosheet(LinkedHashMap<String,String> map)
    {
        try {
            Sheets service = getSheetsService();
            String spreadsheetId = "1oJj7IZbXhDmxlMZMAdBW9t_FJqg1uSkuxPPK2KWvJmY";
            int num_of_sheets = service.spreadsheets().get(spreadsheetId).execute().getSheets().size();
            for (int i = 0; i < num_of_sheets; i++) {
                String sheetNames = service.spreadsheets().get(spreadsheetId).execute().getSheets().get(i).getProperties().getTitle();
                int sheet_Id = service.spreadsheets().get(spreadsheetId).execute().getSheets().get(i).getProperties().getSheetId();
                System.out.println("Sheet Name : " + sheetNames + ", Sheet ID : " + sheet_Id);
            }

            //service.spreadsheets().get(spreadsheetId).execute().getSheets().get(0).getProperties().clone().setTitle("First sheet");
            //service.spreadsheets().get(spreadsheetId).execute().getSheets().get(1).getProperties().clone().setIndex(12345).setTitle("foo").setSheetId(212121).setSheetType("GRID")
            String range = "SDK_Release_Reports!A2:E";
            ValueRange response = service.spreadsheets().values()
                    .get(spreadsheetId, range)
                    .execute();
            // Read Data From Spreadsheet
            List<List<Object>> readvalues = response.getValues();
            System.out.println("Number of values in sheet :" + readvalues.size());
            // write data to spreadsheet

            List<Request> requests = new ArrayList<>();

            List<CellData> values = new ArrayList<>();

            for (String key : map.keySet()){
                String value = map.get(key);
                System.out.println(key + " : " + value);
                values.add(new CellData().setUserEnteredValue(new ExtendedValue().setStringValue(value)));
            }

            requests.add(new Request()
                    .setUpdateCells(new UpdateCellsRequest()
                            .setStart(new GridCoordinate()
                                    .setSheetId(660618606)
                                    .setRowIndex(readvalues.size() + 1)
                                    .setColumnIndex(0))
                            .setRows(Arrays.asList(
                                    new RowData().setValues(values)))
                            .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));

            BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
                    .setRequests(requests);
            service.spreadsheets().batchUpdate(spreadsheetId, batchUpdateRequest)
                    .execute();
            System.out.println("Data written to spreadsheet");
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }



    public static void main(String[] args) throws IOException {
        // Build a new authorized API client service.
        Sheets service = getSheetsService();
        // Prints the names and majors of students in a sample spreadsheet:
        // https://docs.google.com/spreadsheets/d/1oJj7IZbXhDmxlMZMAdBW9t_FJqg1uSkuxPPK2KWvJmY/edit#gid=0
        String spreadsheetId = "1oJj7IZbXhDmxlMZMAdBW9t_FJqg1uSkuxPPK2KWvJmY";

        int num_of_sheets = service.spreadsheets().get(spreadsheetId).execute().getSheets().size();
        //System.out.println(num_of_sheets);
        for (int i=0;i<num_of_sheets;i++){
            String sheetNames = service.spreadsheets().get(spreadsheetId).execute().getSheets().get(i).getProperties().getTitle();
            int sheet_Id = service.spreadsheets().get(spreadsheetId).execute().getSheets().get(i).getProperties().getSheetId();
            System.out.println("Sheet Name : " + sheetNames + ", Sheet ID : " + sheet_Id);
        }

        //service.spreadsheets().get(spreadsheetId).execute().getSheets().get(0).getProperties().clone().setTitle("First sheet");
        //service.spreadsheets().get(spreadsheetId).execute().getSheets().get(1).getProperties().clone().setIndex(12345).setTitle("foo").setSheetId(212121).setSheetType("GRID")

        String range = "SDK_Release_Reports!A2:E";
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();

        // Read Data From Spreadsheet
        List<List<Object>> readvalues = response.getValues();
        System.out.println("Number of values :" + readvalues.size());

        // write data to spreadsheet

        List<Request> requests = new ArrayList<>();

        List<CellData> values = new ArrayList<>();

        values.add(new CellData().setUserEnteredValue(new ExtendedValue().setStringValue("World!")));
        values.add(new CellData().setUserEnteredValue(new ExtendedValue().setStringValue("Welcome!")));
        values.add(new CellData().setUserEnteredValue(new ExtendedValue().setStringValue("Hey!")));
        values.add(new CellData().setUserEnteredValue(new ExtendedValue().setStringValue("Hi!")));

        requests.add(new Request()
                .setUpdateCells(new UpdateCellsRequest()
                        .setStart(new GridCoordinate()
                                .setSheetId(180487968)
                                .setRowIndex(readvalues.size()+1)
                                .setColumnIndex(0))
                        .setRows(Arrays.asList(
                                new RowData().setValues(values)))
                        .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));

        BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
                .setRequests(requests);
        service.spreadsheets().batchUpdate(spreadsheetId, batchUpdateRequest)
                .execute();

        System.out.println("Data written to spreadsheet");

    }


}