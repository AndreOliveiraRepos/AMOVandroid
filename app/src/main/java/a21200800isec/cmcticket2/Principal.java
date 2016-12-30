package a21200800isec.cmcticket2;


import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;

import android.app.Fragment;
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

import java.net.HttpURLConnection;
import java.util.ArrayList;

import a21200800isec.cmcticket2.Assets.HttpClient;
import a21200800isec.cmcticket2.Model.Model;
import a21200800isec.cmcticket2.R;
import layout.MyMapFragment;
import layout.TicketForm;
import layout.loginFragment;

public class Principal extends FragmentActivity implements OnMapReadyCallback, OnFragmentInteractionListener {
    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    MyMapFragment sMapFragment;
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
        setLayout();
        setControllers();
        sMapFragment = new MyMapFragment();
        //ticketForm = new TicketForm();
        //ticketForm = this.getFragmentManager().findFragmentById(R.id.formTicket);

        model = new Model(this);


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


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.model.setMap(googleMap);
        Log.d("LAT",String.valueOf(model.getCurrentLatitude()));
        Log.d("LON",String.valueOf(model.getCurrentLongitude()));
        LatLng local = new LatLng(model.getCurrentLatitude(), model.getCurrentLongitude());
        this.model.getMap().addMarker(new MarkerOptions().position(local).title("HERE"));
        this.model.getMap().moveCamera(CameraUpdateFactory.newLatLng(local));
        ImageButton btn = ((ImageButton)findViewById(R.id.writeNewTicket));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LinearLayout)findViewById(R.id.overlay)).setVisibility(View.INVISIBLE);
                changeFragment();
            }
        });
    }

    @Override
    public void onFragmentMessage(String TAG, Object data) {
        Log.d("Entered here","OK!");
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //onclick get position change fragments
            Log.d("D","Click");
        }
    }

    private void changeFragment() {
        /*ArticleFragment newFragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putInt(ArticleFragment.ARG_POSITION, position);
        newFragment.setArguments(args);*/
        TicketForm ticketForm= new TicketForm();
        loginFragment loginFrag = new loginFragment();

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragContainer, loginFrag);


        //transaction.replace(R.id.fragContainer, ticketForm);
        //transaction.addToBackStack(null);


        transaction.commit();
    }




    @Override
    protected void onResume() {
        super.onResume();
        sMapFragment.getMapAsync(this);
        fragmentManager = this.getSupportFragmentManager();

        transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.fragContainer,sMapFragment);

        transaction.commit();
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
