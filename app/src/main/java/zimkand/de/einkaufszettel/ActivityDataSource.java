package zimkand.de.einkaufszettel;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.renderscript.Sampler;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class ActivityDataSource extends AsyncTask<String, Void, String> {

    public static final String POST_PARAM_KEYVALUE_SEPARATOR = "=";
    public static final String POST_PARAM_SEPARATOR = "&";
    private static final String DESTINATION_METHOD_SHOW = "allEntrys";
    private static final String DESTINATION_METHOD_INSERT = "Insert";
    private static final String DESTINATION_METHOD_DELETE = "Delete";
    private static final String DESTINATION_METHOD_DELETE_SELECTED = "SelectedDelete";
    private static final String VALUE = "value";
    private TextView textView;
    private AlertDialog alertDialog;
    private URLConnection conn;
    private char form;

    public ActivityDataSource(TextView textView, char c, AlertDialog alertDialog) {
        this.form = c;
        this.textView = textView;
        this.alertDialog = alertDialog;
    }


    @Override
    protected String doInBackground(String... strings) {
        try {
            if (form == 's') {
                if (strings[0].isEmpty()) {
                    openConnection();
                } else  {
                    openConnection(strings[0]);
                }
            }
            else if (form == 'd') {
                if (strings[0].isEmpty()) {
                    openConnectionForDelete();
                }
                else  {
                    openConnectionForDelete(strings[0]);
                }
            }
            return readResult();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void openConnection() throws IOException {
        //StringBuffer für das zusammensetzen der URL
        StringBuffer dataBuffer = new StringBuffer();
        dataBuffer.append(URLEncoder.encode("method", "UTF-8"));
        dataBuffer.append(POST_PARAM_KEYVALUE_SEPARATOR);
        dataBuffer.append(URLEncoder.encode(DESTINATION_METHOD_SHOW, "UTF-8"));
        //Adresse der PHP Schnittstelle für die Verbindung zur MySQL Datenbank
        URL url = new URL("http://10.33.11.134/PHPServerEinkaufsliste/reader.php");
        conn = url.openConnection();
        conn.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(dataBuffer.toString());
        wr.flush();
    }

    private void openConnection(String text) throws IOException {
        //StringBuffer für das zusammensetzen der URL
        StringBuffer dataBuffer = new StringBuffer();
        dataBuffer.append(URLEncoder.encode("method", "UTF-8"));
        dataBuffer.append(POST_PARAM_KEYVALUE_SEPARATOR);
        dataBuffer.append(URLEncoder.encode(DESTINATION_METHOD_INSERT, "UTF-8"));
        dataBuffer.append(POST_PARAM_SEPARATOR);
        dataBuffer.append(URLEncoder.encode(VALUE,"UTF-8"));
        dataBuffer.append(POST_PARAM_KEYVALUE_SEPARATOR);
        dataBuffer.append(URLEncoder.encode(text,"UTF-8"));
        //Adresse der PHP Schnittstelle für die Verbindung zur MySQL Datenbank
        URL url = new URL("http://10.33.11.134/PHPServerEinkaufsliste/reader.php");
        conn = url.openConnection();
        conn.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(dataBuffer.toString());
        wr.flush();
    }

    private void openConnectionForDelete() throws IOException {
        //StringBuffer für das zusammensetzen der URL
        StringBuffer dataBuffer = new StringBuffer();
        dataBuffer.append(URLEncoder.encode("method", "UTF-8"));
        dataBuffer.append(POST_PARAM_KEYVALUE_SEPARATOR);
        dataBuffer.append(URLEncoder.encode(DESTINATION_METHOD_DELETE, "UTF-8"));
        //Adresse der PHP Schnittstelle für die Verbindung zur MySQL Datenbank
        URL url = new URL("http://10.33.11.134/PHPServerEinkaufsliste/reader.php");
        conn = url.openConnection();
        conn.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(dataBuffer.toString());
        wr.flush();
    }

    private void openConnectionForDelete(String text) throws IOException {
        //StringBuffer für das zusammensetzen der URL
        StringBuffer dataBuffer = new StringBuffer();
        dataBuffer.append(URLEncoder.encode("method", "UTF-8"));
        dataBuffer.append(POST_PARAM_KEYVALUE_SEPARATOR);
        dataBuffer.append(URLEncoder.encode(DESTINATION_METHOD_DELETE_SELECTED, "UTF-8"));
        dataBuffer.append(POST_PARAM_SEPARATOR);
        dataBuffer.append(URLEncoder.encode(VALUE,"UTF-8"));
        dataBuffer.append(POST_PARAM_KEYVALUE_SEPARATOR);
        dataBuffer.append(URLEncoder.encode(text,"UTF-8"));
        //Adresse der PHP Schnittstelle für die Verbindung zur MySQL Datenbank
        URL url = new URL("http://10.33.11.134/PHPServerEinkaufsliste/reader.php");
        conn = url.openConnection();
        conn.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(dataBuffer.toString());
        wr.flush();
    }

    private String readResult() throws IOException{
        String result = null;
        //Lesen der Rückgabewerte vom Server
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line = null;
        //Solange Daten bereitstehen werden diese gelesen.
        while ((line=reader.readLine()) != null){
            sb.append(line);
        }
        return sb.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        if ( (!isBlank(result))) {
            String[] arr = result.split("[|]");
            String results = "";
            Boolean date = false;
            for (String i: arr ) {
                if(date){
                    results += i + "\n";
                } else {
                    results += i + ": ";
                }
                date = !date;
            }
            this.textView.setText(results);
        }
        alertDialog.show();
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
