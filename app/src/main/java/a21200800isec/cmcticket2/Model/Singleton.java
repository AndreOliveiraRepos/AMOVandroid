package a21200800isec.cmcticket2.Model;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.appindexing.Thing;


import java.util.List;

import a21200800isec.cmcticket2.Assets.Ticket;
import a21200800isec.cmcticket2.Assets.User;

import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by red_f on 28/12/2016.
 */

public class Singleton implements LocationListener{
    private static Singleton singletonInstance = null;
    private User user;
    private Ticket ticket;
    private GoogleApiClient apiClient;
    private double lat;
    private double lon;
    private LocationManager locationManager;
    private Context context;
    private Location location;

    public Singleton(Context context){
        this.context = context;

    }
    public static Singleton getInstance(){
        /*if(singletonInstance == null)
        {
            singletonInstance = new Singleton();
        }*/
        return singletonInstance;
    }

    public void setUser(){}
    public void getUser(){}
    public void setTicket(){}
    public void getTicket(){}
    public void sendTicket(){}
    public void setConnection(){}

    @Override
    public void onLocationChanged(Location location) {
        Log.d("d","erro"); Log.d("d","erro"); Log.d("d","erro"); Log.d("d","erro"); Log.d("d","erro"); Log.d("d","erro"); Log.d("d","erro");
        this.lat = location.getLatitude();
        this.lon = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    public boolean askPremissions(){

            if (ContextCompat.checkSelfPermission(this.context,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this.context,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return false;


            }

        return true;
    }

    public LocationManager setSensors(){
        SensorManager sm = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        List<Sensor> lst = sm.getSensorList(Sensor.TYPE_ALL);

        if (lst == null || lst.size() == 0){
            Log.d("d","erro");
        }else {
            for (Sensor s : lst){

            }
        }

        LocationManager lm = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        //Log.d("d",lm.getLastKnownLocation(lm.));
        return lm;
    }

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    /*@Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }

        @Override
    public void onStop() {
            super.onStop();

            // ATTENTION: This was auto-generated to implement the App Indexing API.
            // See https://g.co/AppIndexing/AndroidStudio for more information.
            AppIndex.AppIndexApi.end(apiClient, getIndexApiAction());
            apiClient.disconnect();
        }*/

    public void startSensor(){
        locationManager = setSensors();

        if (ContextCompat.checkSelfPermission(context,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context,android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;


        }
        this.location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 20, this);
        apiClient = new GoogleApiClient.Builder(context).addApi(AppIndex.API).build();
        apiClient.connect();
        AppIndex.AppIndexApi.start(apiClient, getIndexApiAction());


    }
    public double getCurrentLatitude(){return this.lat;}
    public double getCurrentLongitude(){return this.lon;}
    public void stopSensor(){
        AppIndex.AppIndexApi.end(apiClient, getIndexApiAction());
        apiClient.disconnect();
    }
}
