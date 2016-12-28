package a21200800isec.cmcticket2;

import android.*;
import android.app.Activity;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
/*import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;*/

import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import a21200800isec.cmcticket2.Model.Singleton;

public class Principal extends FragmentActivity implements OnMapReadyCallback{
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    //GoogleMap mapa;
    MyMapFragment sMapFragment;
    ArrayList<String> sideMenuText;
    ListView mSideListView;
    DrawerLayout drawerLayout;
    Singleton singleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION}, 19);
        }
        //singleton = new Singleton();
        setLayout();
        setControllers();
        singleton = new Singleton(this);
        sMapFragment = new MyMapFragment();

        sMapFragment.getMapAsync(this);


        fragmentManager = this.getFragmentManager();

        transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.fragContainer,sMapFragment,"Mapa_tag");

        transaction.commit();
        //mapa.getMapAsync();
        //if(transaction.(MapFragment)){}

    }

    public void setLayout(){
        sideMenuText= new ArrayList<>();
        sideMenuText.add("Sent");
        sideMenuText.add("Beacon?");
        sideMenuText.add("About");
        sideMenuText.add("Quit");
        drawerLayout = (DrawerLayout) findViewById(R.id.side_menu_layout);
        mSideListView = (ListView) findViewById(R.id.side_menu_view);
        mSideListView.setAdapter(new ArrayAdapter<String>(this, R.layout.list_text_view, sideMenuText));

    }

    public void setControllers(){
        mSideListView.setOnItemClickListener(new DrawerItemClickListener());

    }
    public void switchFragment(){
        /*
         FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                   .replace(R.id.content_frame, fragment)
                   .commit();
        * */
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap mapa = googleMap;
        Log.d("LAT",String.valueOf(singleton.getCurrentLatitude()));
        Log.d("LON",String.valueOf(singleton.getCurrentLongitude()));
        LatLng local = new LatLng(singleton.getCurrentLatitude(), singleton.getCurrentLongitude());
        mapa.addMarker(new MarkerOptions().position(local).title("HERE"));
        mapa.moveCamera(CameraUpdateFactory.newLatLng(local));
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //onclick get position change fragments
            Log.d("D","Click");
        }
    }



}
