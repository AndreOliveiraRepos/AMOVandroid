package a21200800isec.cmcticket2;

import android.support.v4.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import a21200800isec.cmcticket2.Model.Singleton;

/**
 * Created by red_f on 28/12/2016.
 */

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Singleton singleton;
    private double lat;
    private double lon;
    public MapFragment(){
        //singleton = Singleton.getInstance();
        /*lat = singleton.getCurrentLatitude();
        lon = singleton.getCurrentLongitude();*/
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng local = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(local).title("HERE"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(local));
    }
    public void setLat(double l){this.lat = l;}
    public void setLon(double l){this.lon = l;}
}
