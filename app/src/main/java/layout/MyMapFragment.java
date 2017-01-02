package layout;

//import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;

import a21200800isec.cmcticket2.Model.Model;
import a21200800isec.cmcticket2.R;

/**
 * Created by red_f on 28/12/2016.
 */

public class MyMapFragment extends SupportMapFragment {
    private GoogleMap mapa;
    private Model model;
    private double lat;
    private double lon;
    public MyMapFragment(){

    }


    @Override
    public void onResume() {
        super.onResume();
        //((LinearLayout)this.getView().findViewById(R.id.mapOverlay)).setVisibility(View.VISIBLE);
    }

    public void setLat(double l){this.lat = l;}
    public void setLon(double l){this.lon = l;}

    public void setController(){
        /*ImageButton btn = ((ImageButton)getView().findViewById(R.id.btnTicket));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LinearLayout)getView().findViewById(R.id.mapOverlay)).setVisibility(View.INVISIBLE);
                //set new instance of ticketfragment
                //setCurrentFragment()
            }
        });*/

    }
}
