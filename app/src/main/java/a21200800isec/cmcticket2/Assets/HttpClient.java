package a21200800isec.cmcticket2.Assets;

import android.icu.text.LocaleDisplayNames;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.UnknownHostException;

import a21200800isec.cmcticket2.Model.Model;

import static a21200800isec.cmcticket2.Assets.HttpClient.RequestType.GET;
import static a21200800isec.cmcticket2.Assets.HttpClient.RequestType.POST;

/**
 * Created by red_f on 29/12/2016.
 */

public class HttpClient extends AsyncTask<String, String, Boolean>{
    private String apiurl = "https://microsoft-apiapp1cd90a6c784049c7bb4559183af4c343.azurewebsites.net";
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

    @Override
    protected Boolean doInBackground(String... strings) {
        String req = strings[0].toString();
        String type = strings[1].toString();
        switch(type){
            case "GET":
                sendGet();
                break;
            case "POST":
                if(req.contains("tickets")){
                    sendPost("");
                }else if(req.contains("register")){
                    sendPost("");
                }else if(req.contains("token")){

                    //connection.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
                    try {
                        sendPost("username="+this.model.getUser().getUserName()+"&password="+this.model.getUser().getPassword()+"&grant_type=password");
                        if(this.responseCode == 200) {
                            this.model.getUser().setAuthToken(jsonObject.getString("access_token"));
                            return true;
                        }else
                            return false;
                    } catch (JSONException e) {
                        Log.d("Error","IO exception: " + e);
                    }
                }
                //sendPost();
                break;
            default:
                break;
        }
        return null;
    }


    public enum RequestType{
        PUT,DELETE,GET,POST
    }


    public HttpClient(Model m){
        //this.model = m;
        /*try {
            this.connection = new TCPConnection(InetAddress.getByName(url),80);
        } catch (UnknownHostException e) {
            Log.d("Error","host not found");
        }*/

    }

    public void sendRequest(RequestType type){






    }

    public String getResponse(){ return this.response;}

    public int getResponseCode(){ return this.responseCode;}

    private void sendPost(String params){
        try {
            url = new URL(apiurl + "/token");
            this.connection = (HttpURLConnection)url.openConnection();

            this.connection.setReadTimeout(10000);
            this.connection.setConnectTimeout(15000);
            this.connection.setRequestMethod("POST");
            this.connection.setDoInput(true);
            this.connection.setDoOutput(true);

            Log.d("DEBUG---------",String.valueOf(this.connection.getDoOutput()));
            //this.connection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
            //this.connection.setRequestProperty( "charset", "utf-8");

            DataOutputStream wr = new DataOutputStream( this.connection.getOutputStream());

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(wr, "UTF-8"));
            writer.write(params);

            writer.flush();

            writer.close();

            responseCode = connection.getResponseCode();

            if(responseCode==200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                responseOutput = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                }
                br.close();

                jsonObject = new JSONObject(responseOutput.toString());
                this.model.getUser().setAuthToken("Bearer " + jsonObject.getString("access_token"));
            }
            /*response = jsonObject.getString("access_token");
            Log.d("Request",response);*/
        } catch (IOException e) {
            Log.d("Error","IO exception: " + e);
        } catch (JSONException e) {
            Log.d("Error","JSON exception: " + e);
        }

    }

    private void Login(String params){
        try {
            connection.setRequestMethod(GET.toString());
            connection.setDoInput(true);
            connection.setDoOutput(true);

            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(params);

            writer.flush();

            writer.close();

            responseCode = connection.getResponseCode();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            responseOutput = new StringBuilder();
            while((line = br.readLine()) != null ) {
                responseOutput.append(line);
            }
            br.close();

            jsonObject = new JSONObject(responseOutput.toString());
            this.model.getUser().setAuthToken("Bearer " + jsonObject.getString("access_token"));
            //response = jsonObject.getString("message");
        } catch (IOException e) {
            Log.d("Error","IO exception: " + e);
        } catch (JSONException e) {
            Log.d("Error","IO exception: " + e);
        }
    }
    private boolean sendGet(){
        try {
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            responseCode = connection.getResponseCode();
            InputStream is=connection.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            String line;
            responseOutput = new StringBuilder();
            while ( (line = br.readLine()) != null )
                responseOutput.append(line + "\n");

            jsonObject = new JSONObject(responseOutput.toString());
            response = jsonObject.getString("descricao");
        } catch (IOException e) {
            Log.d("Error","IO exception: " + e);
            return false;
        } catch (JSONException e) {
            Log.d("Error","IO exception: " + e);
            return false;
        }
        return true;
    }

    @Override
    protected void onPreExecute() {
       this.model = model.getInstance();

    }

}
