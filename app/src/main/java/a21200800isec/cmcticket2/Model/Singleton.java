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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.List;

import a21200800isec.cmcticket2.Assets.GPSTracker;
import a21200800isec.cmcticket2.Assets.Ticket;
import a21200800isec.cmcticket2.Assets.User;

import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by red_f on 28/12/2016.
 */

public class Singleton  {
    private static Singleton singletonInstance = null;
    private User user;
    private Ticket ticket;
    private GoogleApiClient client;
    private double lat;
    private double lon;
    private Context context;
    private GPSTracker tracker;
    private GoogleMap mapa;

    public Singleton(Context context) {
        this.context = context;
        this.tracker = new GPSTracker(context,this);
        this.tracker.setSensors();
        this.tracker.startTracker();
        this.lat = this.tracker.getLatitude();
        this.lon = this.tracker.getLongitude();

        //this.mapa ;
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

    public void stopTracker(){}
    public void startTracker(){}

    public double getCurrentLatitude(){
        return this.lat;
    }
    public double getCurrentLongitude(){
        return this.lon;
    }

    public void refreshMap(){
        mapa.clear();
        LatLng local = new LatLng(this.getCurrentLatitude(),this.getCurrentLongitude());
        mapa.addMarker(new MarkerOptions().position(local).title("HERE"));
        mapa.moveCamera(CameraUpdateFactory.newLatLng(local));
    }

    public GoogleMap getMap(){return this.mapa;}
    public void setMap(GoogleMap m){this.mapa = m;}
    public void setCurretLatitude(double l){this.lat = l;}
    public void setCurretLongitude(double l){this.lon = l;}
}
