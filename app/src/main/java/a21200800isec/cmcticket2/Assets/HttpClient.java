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

public class HttpClient{
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


    public enum RequestType{
        PUT,DELETE,GET,POST
    }


    public HttpClient(){
        this.model = model.getInstance();

    }

    public void PostRequest(){

    }

    public void getRequest(){

    }



}
