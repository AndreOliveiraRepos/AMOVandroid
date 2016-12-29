package a21200800isec.cmcticket2.Model;

import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import a21200800isec.cmcticket2.Assets.GPSTracker;
import a21200800isec.cmcticket2.Assets.Ticket;
import a21200800isec.cmcticket2.Assets.User;

/**
 * Created by red_f on 28/12/2016.
 */

public class Model {
    private static Model modelInstance = null;
    private User user;
    private Ticket ticket;
    private GoogleApiClient client;
    private double lat;
    private double lon;
    private Context context;
    private GPSTracker tracker;
    private GoogleMap mapa;

    public Model(Context context) {
        this.context = context;
        this.tracker = new GPSTracker(context,this);
        this.tracker.setSensors();
        this.tracker.startTracker();
        this.lat = this.tracker.getLatitude();
        this.lon = this.tracker.getLongitude();

        //this.mapa ;
    }
    /*public static Model getInstance(){
        if(modelInstance == null)
        {
            modelInstance = new Model();
        }
        return modelInstance;
    }*/

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

    public void pauseTracker(){
        //needs to pause

    }
}
