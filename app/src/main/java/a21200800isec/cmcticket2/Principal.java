package a21200800isec.cmcticket2;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import a21200800isec.cmcticket2.Model.Model;
import layout.MyMapFragment;
import layout.LoginFragment;

public class Principal extends FragmentActivity implements OnMapReadyCallback, OnFragmentInteractionListener {
    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    MyMapFragment sMapFragment;
    LoginFragment loginFrag;
    ArrayList<String> sideMenuText;
    ListView mSideListView;
    DrawerLayout drawerLayout;
    Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(a21200800isec.cmcticket2.R.layout.activity_principal);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION}, 19);
        }
        //model = new Model();
        model= model.getInstance();
        model.setContext(this);
        fragmentManager = this.getSupportFragmentManager();
        //sMapFragment = new MyMapFragment();

        //ticketForm = new TicketForm();
        //ticketForm = this.getFragmentManager().findFragmentById(R.id.formTicket);
        setLayout();
        setControllers();



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
        //end sidebar
        //loginfrag

        this.setCurrentFragment(loginFrag.newInstance("",""));
    }

    public void setControllers(){
        mSideListView.setOnItemClickListener(new DrawerItemClickListener());

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.model.setMap(googleMap);
        Log.d("LAT",String.valueOf(model.getCurrentLatitude()));
        Log.d("LON",String.valueOf(model.getCurrentLongitude()));
        LatLng local = new LatLng(model.getCurrentLatitude(), model.getCurrentLongitude());
        this.model.getMap().addMarker(new MarkerOptions().position(local).title("HERE"));
        this.model.getMap().moveCamera(CameraUpdateFactory.newLatLng(local));

    }

    @Override
    public void onFragmentMessage(FRAGMENT_TAG TAG, String msg) {
        switch (TAG){
            case ABOUT:
                break;
            case LOGIN:
                if(msg.equalsIgnoreCase("OK"))
                    setCurrentFragment(sMapFragment);

                break;
            case SEND:
                if(msg.equalsIgnoreCase("OK"))
                    //setCurrentFragment(sMapFragment);
                break;
            case TICKET:
                if(msg.equalsIgnoreCase("OK"))
                    //setCurrentFragment(sMapFragment);
                break;

        }
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //onclick get position change fragments
            Log.d("D","Click");
        }
    }

    private void setCurrentFragment(Fragment frag) {

        /*TicketForm ticketForm= new TicketForm();
        LoginFragment loginFrag = new LoginFragment();*/

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragContainer, frag);





        transaction.commit();
    }




    @Override
    protected void onResume() {
        super.onResume();

        //map frag code
        /*sMapFragment.getMapAsync(this);
        fragmentManager = this.getSupportFragmentManager();

        transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.fragContainer,sMapFragment);

        transaction.commit();*/
        /*HttpClient httpClient = new HttpClient(this.model);
        httpClient.execute("/token" , HttpClient.RequestType.POST.toString());*/

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //kill app our change to map
    }


}
