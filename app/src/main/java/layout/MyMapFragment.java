package layout;

//import android.support.v4.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import a21200800isec.cmcticket2.Model.Model;

/**
 * Created by red_f on 28/12/2016.
 */

public class MyMapFragment extends MapFragment {
    private GoogleMap mapa;
    private Model model;
    private double lat;
    private double lon;
    public MyMapFragment(){

    }


    public void setLat(double l){this.lat = l;}
    public void setLon(double l){this.lon = l;}


}
