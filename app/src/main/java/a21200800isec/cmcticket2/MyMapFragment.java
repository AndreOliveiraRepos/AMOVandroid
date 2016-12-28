package a21200800isec.cmcticket2;

//import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import a21200800isec.cmcticket2.Model.Singleton;

/**
 * Created by red_f on 28/12/2016.
 */

public class MyMapFragment extends MapFragment {
    private GoogleMap mapa;
    private Singleton singleton;
    private double lat;
    private double lon;
    public MyMapFragment(){

    }


    public void setLat(double l){this.lat = l;}
    public void setLon(double l){this.lon = l;}


}
