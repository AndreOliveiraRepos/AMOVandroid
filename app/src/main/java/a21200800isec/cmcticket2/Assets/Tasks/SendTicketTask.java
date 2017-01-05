package a21200800isec.cmcticket2.Assets.Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import a21200800isec.cmcticket2.Assets.AsyncTaskCompleteListener;
import a21200800isec.cmcticket2.Model.Model;

/**
 * Created by red_f on 02/01/2017.
 */

public class SendTicketTask extends AsyncTask<Void, String, Boolean> {
    private String ticketParameters;
    private AsyncTaskCompleteListener mListener;

    private String apiurl = "http://10.0.2.2/API/v1";
    private String request;
    private String response;
    //private TCPConnection connection;
    private HttpURLConnection connection;
    private int timeout = 10000;
    private JSONObject jsonObject;
    private int responseCode;
    private StringBuilder responseOutput;
    private Model model;
    URL url;

    public SendTicketTask(AsyncTaskCompleteListener l)
    {
        this.model = Model.getInstance();
        this.mListener = l;
        this.ticketParameters = "Description="+this.model.getTicket().getDescription()+"&Location="+this.model.getTicket().getLocation()+"&Date="+this.model.getTicket().getData();
    }



    protected Boolean doInBackground(Void... voids) {
        try {
            url = new URL(apiurl + "/tickets");
            this.connection = (HttpURLConnection)url.openConnection();

            this.connection.setReadTimeout(10000);
            this.connection.setConnectTimeout(15000);
            this.connection.setRequestMethod("POST");
            this.connection.setDoInput(true);
            this.connection.setDoOutput(true);
            this.connection.addRequestProperty("Authorization", this.model.getUser().getAuthToken());
            this.connection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

            DataOutputStream wr = new DataOutputStream( this.connection.getOutputStream());

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(wr, "UTF-8"));
            writer.write(ticketParameters);

            writer.flush();

            writer.close();

            responseCode = connection.getResponseCode();

            if(responseCode==201) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                responseOutput = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                }
                br.close();

                jsonObject = new JSONObject(responseOutput.toString());
                //this.model.getUser().setAuthToken("Bearer " + jsonObject.getString("access_token"));
                return true;
            }else{
                return false;
            }
            /*response = jsonObject.getString("access_token");
            Log.d("Request",response);*/
        } catch (IOException e) {
            Log.d("Error","IO exception: " + e);
            return false;
        } catch (JSONException e) {

            Log.d("Error","JSON exception: " + e);
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        mListener.onTaskComplete(result);
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }
}
